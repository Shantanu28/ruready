package net.ruready.web.ta.webflow;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.entity.TranscriptProgressStatus;
import net.ruready.business.ta.entity.property.ExpectationAssessmentFactory;
import net.ruready.business.ta.entity.property.ExpectationAssessmentScoringService;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.web.ta.beans.ExpectationAssessmentBean;
import net.ruready.web.ta.support.WebFlowSupport;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class ExpectationAssessmentFormAction extends FormAction
{
	private static final String TAKE_KNOWLEDGE_ASSESSMENT_ERROR_MESSAGE = ".knowledgeAssessmentRequired.error.message";
	
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();	
	private ExpectationAssessmentFactory assessmentFactory;
	private ExpectationAssessmentScoringService scoringService;
	
	@Override
	protected Object createFormObject(final RequestContext context) throws Exception
	{
		final ExpectationAssessmentBean form = new ExpectationAssessmentBean();
		final StudentTranscript transcript = getWebFlowSupport().getTranscriptFromConversation(context);
		if (transcript != null)
		{
			form.setTranscript(transcript);
			form.setAssessment(createExpectationAssessmentFromTemplate(context, transcript));
		}
		return form;
	}
	
	public Event assertFormInitialized(final RequestContext context) throws Exception
	{
		final ExpectationAssessmentBean form = getForm(context);
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
		if (!transcript.canTakeExpectationAssessment())
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, TAKE_KNOWLEDGE_ASSESSMENT_ERROR_MESSAGE));
			return no();
		}
		return yes();
	}
	
	public Event startAssessment(final RequestContext context) throws Exception
	{
		getForm(context).beginTest();
		return success();
	}
	
	public Event scoreAssessment(final RequestContext context) throws Exception
	{
		final ExpectationAssessmentBean form = getForm(context);
		final StudentTranscript transcript = form.getTranscript();
		form.completeTest();
		getScoringService().scoreAssessment(form.getAssessment());
		// have they passed the course?
		if (transcript.getMostRecentAssessmentScore() > 0.80d && transcript.getProgressStatus() != TranscriptProgressStatus.PASSED)
		{
			transcript.passCourse();
		}
		return success();
	}
	
	public Event saveAssessment(final RequestContext context) throws Exception
	{
		final ExpectationAssessmentBean form = getForm(context);
		final AbstractStudentTranscriptBD transcriptBD = getWebFlowSupport().getStudentTranscriptBD(context);
		final StudentTranscript transcript = transcriptBD.readTranscript(form.getTranscript().getId(), form.getTranscript());
		
		// save the assessment
		getWebFlowSupport().getAssessmentBD(context).createExpectationAssessment(form.getAssessment());
		
		// add the assessment to the transcript
		transcript.addExpectationAssessment(form.getAssessment());
		
		// start the course if it's not already marked as started
		transcript.startCourseIfNotStarted();
		
		transcriptBD.updateTranscript(transcript);
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

	public ExpectationAssessmentFactory getAssessmentFactory()
	{
		return assessmentFactory;
	}

	public void setAssessmentFactory(final ExpectationAssessmentFactory assessmentFactory)
	{
		this.assessmentFactory = assessmentFactory;
	}

	public ExpectationAssessmentScoringService getScoringService()
	{
		return scoringService;
	}

	public void setScoringService(final ExpectationAssessmentScoringService scoringService)
	{
		this.scoringService = scoringService;
	}
	
	private ExpectationAssessment createExpectationAssessmentFromTemplate(final RequestContext context, final StudentTranscript transcript) throws Exception
	{
		return getAssessmentFactory().createExpectationAssessment(
				getCourseExpectationAssessmentTemplate(context, transcript.getCourse()));
	}

	private ExpectationAssessment getCourseExpectationAssessmentTemplate(final RequestContext context, final Course course) throws Exception
	{
		return getAssessmentFactory().createExpectationAssessment(
				getExpectationsForCourse(context, course));
	}
	
	private List<Expectation> getExpectationsForCourse(final RequestContext context, final Course course) throws Exception
	{
		return getWebFlowSupport().getEditItemBD(context).findChildren(
				course, 
				Expectation.class,
				ItemType.EXPECTATION);
	}
	
	private ExpectationAssessmentBean getForm(final RequestContext context) throws Exception
	{
		return (ExpectationAssessmentBean) getFormObject(context);
	}
	
	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}	
}
