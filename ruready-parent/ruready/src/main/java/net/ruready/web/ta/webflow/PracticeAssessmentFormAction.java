package net.ruready.web.ta.webflow;

import net.ruready.business.ta.entity.AssessmentItem;
import net.ruready.business.ta.entity.AssessmentItemResponse;
import net.ruready.business.ta.entity.AssessmentStatus;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.web.ta.beans.PracticeAssessmentBean;

import org.hibernate.Hibernate;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class PracticeAssessmentFormAction extends KnowledgeAssessmentFormAction
{

	private static final String TAKE_PRE_ASSESSMENT_ERROR_MESSAGE = ".preAssessmentRequired.error.message";
	private static final String PRACTICE_LEVEL_KEY = "level";

	@Override
	protected Object createFormObject(final RequestContext context) throws Exception
	{
		// get the selected level (default to one)
		Integer level = context.getRequestParameters().getInteger(PRACTICE_LEVEL_KEY, 1);
		if (level < 1 || level > 4)
		{
			level = 1;
		}
		final PracticeAssessmentBean form = new PracticeAssessmentBean();
		final StudentTranscript transcript = getWebFlowSupport().getTranscriptFromConversation(context);
		//transcript.getCurrentExpectationAssessment();
		if (transcript != null)
		{
			form.setTranscript(transcript);
			
			// determine if there is an assessment already in progress to close/continue
			final KnowledgeAssessment assessment = getAssessmentForLevel(transcript, level);
			if (assessment != null && assessment.getStatus() == AssessmentStatus.INCOMPLETE)
			{
				getWebFlowSupport().getAssessmentBD(context).reattachKnowledgeAssessment(assessment);
				initializeAssessment(assessment);
				form.setAssessment(assessment);
			}
			// new assessment
			else 
			{
				form.setAssessment(createAssessment(context, transcript, level));
			}			
			form.setPracticeLevel(level);
		}
		return form;
	}
	
	private void initializeAssessment(final KnowledgeAssessment assessment)
	{
		Hibernate.initialize(assessment.getTestItems());
		for (KnowledgeAssessmentItem item : assessment.getTestItems())
		{
			Hibernate.initialize(item.getAnswers());
			Hibernate.initialize(item.getChoices());
			Hibernate.initialize(item.getHints());
			Hibernate.initialize(item.getResponses());
		}
	}
	
	@Override
	public Event assertCanTakeAssessment(final RequestContext context) throws Exception
	{
		final PracticeAssessmentBean form = getForm(context);
		if (!form.getTranscript().canTakePracticeAssessment(form.getPracticeLevel()))
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, TAKE_PRE_ASSESSMENT_ERROR_MESSAGE));
			return no();
		}
		return yes();
	}
	
	@Override
	public Event startAssessment(final RequestContext context) throws Exception
	{
		getForm(context).beginTest();
		addAssessmentToTranscript(context);
		// start the first question
		getForm(context).getCurrentQuestion().beginTestItem();
		return success();
	}
	
	public Event continueAssessment(final RequestContext context) throws Exception
	{
		if (getForm(context).continueTest())
		{
			logger.debug("Continue");
			getForm(context).getCurrentQuestion().beginTestItem();
			return result("next");
		}
		// they are finished with the assessment, but pressed stop prematurely
		else
		{
			logger.debug("Finished");
			return result("finished"); 
		}
	}
	
	public Event stopAssessment(final RequestContext context) throws Exception
	{
		//only stop the test item if it hasn't already be answered
		final AssessmentItem item = getForm(context).getCurrentQuestion();
		if (!item.isAnswered())
		{
			item.stopTestItem();
		}
		return success();
	}
	
	public Event showHint(final RequestContext context) throws Exception
	{
		final PracticeAssessmentBean form = getForm(context);
		final KnowledgeAssessmentItem question = form.getCurrentQuestion();
		// get the selected hint number (default to the highest hint level that has currently been requested)
		Integer hintNumber = context.getRequestScope().getInteger("hintNumber",form.getAvailableHintLevel());
		
		// if the hintNumber request is outside of the range, go to the current max hint level
		if (hintNumber < 1 || hintNumber > question.getHints().size())
		{
			hintNumber = form.getAvailableHintLevel();
		}
		// if they are requesting a hint that is more than one higher than the current level, reduce it to one higher than the current one
		if (hintNumber > form.getAvailableHintLevel() + 1)
		{
			hintNumber = form.getAvailableHintLevel() + 1;
		}
		// if they haven't selected any hints previously (level == 0), 
		// and hit a default, make sure that the hint number is 1
		if (hintNumber == 0)
		{
			hintNumber = 1;
		}
		
		// add a hint response to the assessment item object if they haven't yet requested it
		if (hintNumber > form.getAvailableHintLevel())
		{
			form.getCurrentQuestion().addResponse(AssessmentItemResponse.createHintResponse());
		}
		form.setCurrentHintNumber(hintNumber);
		return success();
	}
	
	public Event tryAnswer(final RequestContext context) throws Exception
	{
		final PracticeAssessmentBean form = getForm(context);
		final KnowledgeAssessmentItem question = form.getCurrentQuestion();
		form.getCurrentQuestion().addResponse(AssessmentItemResponse.createTryAnswerResponse(form.getAnswer()));
		final Double responseScore = getScoringService().scoreLastResponse(question);
		form.setLastResponseScore(responseScore);
		return success();
	}
	
	@Override
	public Event scoreAssessment(final RequestContext context) throws Exception
	{
		final PracticeAssessmentBean form = getForm(context);
		getScoringService().scoreAssessment(form.getAssessment());
		return success();
	}
	
	@Override
	public Event determineRecommendedLevel(final RequestContext context) throws Exception
	{
		final PracticeAssessmentBean form = getForm(context);
		final StudentTranscript transcript = form.getTranscript();
		transcript.setRecommendedLevel(getScoringService().determineRecommendedLevel(form.getAssessment(), form.getPracticeLevel()));
		getWebFlowSupport().getStudentTranscriptBD(context).updateTranscriptMerge(transcript);
		return success();
	}
	
	@Override
	public Event saveAssessment(final RequestContext context) throws Exception
	{
		final PracticeAssessmentBean form = getForm(context);
		getWebFlowSupport().getAssessmentBD(context).updateKnowledgeAssessmentMerge(form.getAssessment());
		return success();
	}
	
	private void addAssessmentToTranscript(final RequestContext context) throws Exception
	{
		final PracticeAssessmentBean form = getForm(context);
		final AbstractStudentTranscriptBD transcriptBD = getWebFlowSupport().getStudentTranscriptBD(context);
		getWebFlowSupport().getAssessmentBD(context).createKnowledgeAssessment(form.getAssessment());
		final StudentTranscript transcript = transcriptBD.readTranscript(form.getTranscript().getId(), form.getTranscript());
		addAssessmentToCorrectPracticeLevel(transcript, form.getAssessment(), form.getPracticeLevel());
		transcriptBD.updateTranscript(transcript);
	}
	
	private KnowledgeAssessment getAssessmentForLevel(final StudentTranscript transcript, final Integer practiceLevel)
	{
		switch (practiceLevel)
		{
			case 1:
				return transcript.getCurrentLevel1Practice();
			case 2:
				return transcript.getCurrentLevel2Practice();
			case 3:
				return transcript.getCurrentLevel3Practice();
			case 4:
				return transcript.getCurrentLevel4Practice();
			default:
				throw new IllegalArgumentException("PracticeAssessmentFormAction only supports practices from levels 1 through 4");
		}
	}
	
	private void addAssessmentToCorrectPracticeLevel(final StudentTranscript transcript, final KnowledgeAssessment assessment, final Integer practiceLevel)
	{
		switch (practiceLevel)
		{
			case 1:
				transcript.addLevel1Practice(assessment);
				break;
			case 2:
				transcript.addLevel2Practice(assessment);
				break;
			case 3:
				transcript.addLevel3Practice(assessment);
				break;
			case 4:
				transcript.addLevel4Practice(assessment);
				break;
			default:
				throw new IllegalArgumentException("PracticeAssessmentFormAction only supports practices from levels 1 through 4");
		}
	}
	
	private PracticeAssessmentBean getForm(final RequestContext context) throws Exception
	{
		return (PracticeAssessmentBean) getFormObject(context);
	}

	private KnowledgeAssessment createAssessment(final RequestContext context, final StudentTranscript transcript, final Integer practiceLevel) throws Exception
	{
		return getAssessmentFactory().createPracticeAssessment(
				practiceLevel, 
				getQuestionsForCourse(context, transcript.getCourse()));
	}
}
