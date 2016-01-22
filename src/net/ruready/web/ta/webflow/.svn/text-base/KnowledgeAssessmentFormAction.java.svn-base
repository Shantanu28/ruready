package net.ruready.web.ta.webflow;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.ta.entity.AssessmentItemResponse;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.entity.TranscriptProgressStatus;
import net.ruready.business.ta.entity.property.KnowledgeAssessmentFactory;
import net.ruready.business.ta.entity.property.KnowledgeAssessmentScoringService;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.web.ta.beans.KnowledgeAssessmentBean;
import net.ruready.web.ta.support.WebFlowSupport;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class KnowledgeAssessmentFormAction extends FormAction
{

	private static final String TAKE_EXPECTATION_ASSESSMENT_ERROR_MESSAGE = ".expectationAssessmentRequired.error.message";
	
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();
	private KnowledgeAssessmentFactory assessmentFactory;
	private KnowledgeAssessmentScoringService scoringService;
	
	@Override
	protected Object createFormObject(final RequestContext context) throws Exception
	{
		final KnowledgeAssessmentBean form = new KnowledgeAssessmentBean();
		final StudentTranscript transcript = getWebFlowSupport().getTranscriptFromConversation(context);
		if (transcript != null)
		{
			form.setTranscript(transcript);
			form.setAssessment(createAssessment(context, transcript));
		}
		return form;
	}
	
	public Event assertFormInitialized(final RequestContext context) throws Exception
	{
		final KnowledgeAssessmentBean form = getForm(context);
		if (form.getTranscript() == null)
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return no();
		}
		return yes();
	}
	
	public Event assertCanTakeAssessment(final RequestContext context) throws Exception
	{
		final StudentTranscript transcript = getForm(context).getTranscript();
		if (!transcript.canTakeKnowledgeAssessment())
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, TAKE_EXPECTATION_ASSESSMENT_ERROR_MESSAGE));
			return no();
		}
		return yes();
	}
	
	public Event startAssessment(final RequestContext context) throws Exception
	{
		getForm(context).beginTest();
		// start the first question
		getForm(context).getCurrentQuestion().beginTestItem();
		return success();
	}
	
	public Event completeAssessment(final RequestContext context) throws Exception
	{
		getForm(context).completeTest();
		return success();
	}
	
	public Event nextQuestion(final RequestContext context) throws Exception
	{
		final KnowledgeAssessmentItem item = getForm(context).getNextQuestion();
		if (item == null)
		{
			return result("finished");
		}
		else
		{
			item.beginTestItem();
			return result("next");
		}
	}
	
	public Event determineQuestionFormat(final RequestContext context) throws Exception
	{
		final KnowledgeAssessmentItem item = getForm(context).getCurrentQuestion();
		return result(item.getQuestionFormat().toString());
	}
	
	public Event scoreQuestion(final RequestContext context) throws Exception
	{
		final KnowledgeAssessmentBean form = getForm(context);
		final KnowledgeAssessmentItem item = form.getCurrentQuestion();
		item.answerTestItem();
		item.addResponse(AssessmentItemResponse.createSubmitAnswerResponse(form.getAnswer()));
		
		getScoringService().scoreAssessmentItem(item);
		return success();
	}
	
	public Event closeAssessment(final RequestContext context) throws Exception
	{
		getForm(context).stopTest();
		return success();
	}
	
	public Event scoreAssessment(final RequestContext context) throws Exception
	{
		final KnowledgeAssessmentBean form = getForm(context);
		final StudentTranscript transcript = form.getTranscript();
		getScoringService().scoreAssessment(form.getAssessment());
		// have they passed the course?
		if (transcript.getMostRecentAssessmentScore() > 0.80d && transcript.getProgressStatus() != TranscriptProgressStatus.PASSED)
		{
			transcript.passCourse();
		}
		return success();
	}
	
	public Event determineRecommendedLevel(final RequestContext context) throws Exception
	{
		final KnowledgeAssessmentBean form = getForm(context);
		final StudentTranscript transcript = form.getTranscript();
		transcript.setRecommendedLevel(getScoringService().determineRecommendedLevel(form.getAssessment(), transcript.getRecommendedLevel()));
		getWebFlowSupport().getStudentTranscriptBD(context).updateTranscriptMerge(transcript);
		return success();
	}
	
	public Event saveAssessment(final RequestContext context) throws Exception
	{
		final KnowledgeAssessmentBean form = getForm(context);		
		final StudentTranscript transcript = form.getTranscript();
		final AbstractStudentTranscriptBD transcriptBD = getWebFlowSupport().getStudentTranscriptBD(context);
		
		// save the assessment
		getWebFlowSupport().getAssessmentBD(context).createKnowledgeAssessment(form.getAssessment());
		
		// add the assessment to the transcript
		transcript.addKnowledgeAssessment(form.getAssessment());
		// start the course if it's not already marked as started
		transcript.startCourseIfNotStarted();
		
		transcriptBD.updateTranscriptMerge(transcript);
		return success();
	}
	
	public Event exposeErrors(final RequestContext context) throws Exception
	{
		getWebFlowSupport().exposeErrors(context, getFormErrors(context));
		return success();
	}
	
	@Override
	protected void initAction()
	{
		super.initAction();
		if (getAssessmentFactory() == null)
		{
			throw new BeanInitializationException("assessmentFactory is null.");
		}
		if (getScoringService() == null)
		{
			throw new BeanInitializationException("scoringService is null.");
		}
	}
	
	protected final List<Question> getQuestionsForCourse(final RequestContext context, final Course course) throws Exception
	{
		return getWebFlowSupport().getEditItemBD(context).findChildren(
				course, 
				Question.class,
				ItemType.QUESTION);
	}
	
	public KnowledgeAssessmentFactory getAssessmentFactory()
	{
		return assessmentFactory;
	}

	public void setAssessmentFactory(final KnowledgeAssessmentFactory assessmentFactory)
	{
		this.assessmentFactory = assessmentFactory;
	}
	
	public KnowledgeAssessmentScoringService getScoringService()
	{
		return scoringService;
	}

	public void setScoringService(final KnowledgeAssessmentScoringService scoringService)
	{
		this.scoringService = scoringService;
	}
	
	protected final WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}
	
	private KnowledgeAssessmentBean getForm(final RequestContext context) throws Exception
	{
		return (KnowledgeAssessmentBean) getFormObject(context);
	}

	private KnowledgeAssessment createAssessment(final RequestContext context, final StudentTranscript transcript) throws Exception
	{
		return getAssessmentFactory().createKnowledgeAssessment(
				getQuestionsForCourse(context, transcript.getCourse()));
	}
}
