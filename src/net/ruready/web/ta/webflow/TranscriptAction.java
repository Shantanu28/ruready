package net.ruready.web.ta.webflow;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.entity.TranscriptActiveStatus;
import net.ruready.business.ta.entity.TranscriptProgressStatus;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.exception.RecordNotFoundException;
import net.ruready.web.ta.support.WebFlowSupport;

import org.hibernate.Hibernate;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class TranscriptAction extends MultiAction
{
	private static final String COURSE_ID_KEY = "courseId";
	private static final String TRANSCRIPT_ID_KEY = "transcriptId";
	private static final String ASSESSMENT_ID_KEY = "assessmentId";
	private static final String HISTORY_LIST_KEY = "historyList";
	
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();
	
	public Event createCourseTranscript(final RequestContext context) throws Exception
	{
		final Course course = getCourse(context);
		final User user = getWebFlowSupport().getSessionUser(context);
		if (course == null)
		{
			getWebFlowSupport().addError(context, "ta.viewTranscript.nocourse.error.message");
			return error();
		}
		final StudentTranscript transcript = getWebFlowSupport().getStudentTranscriptBD(context).createCourseTranscriptIfNotExists(user, course);
		if (isCourseUnavailable(transcript))
		{
			getWebFlowSupport().addError(context, "ta.viewTranscript.transcriptclosed.error.message");
			return error();
		}
		storeTranscript(context, transcript);
		return success();
	}
	
	public Event lookupTranscript(final RequestContext context) throws Exception
	{
		final StudentTranscript transcript = getTranscriptFromUrlId(context);
		if (transcript == null)
		{
			getWebFlowSupport().addError(context, "ta.viewTranscript.notranscript.error.message");
			return error();
		}
		storeTranscript(context, transcript);
		return success();
	}
	
	public Event determineTranscriptState(final RequestContext context) throws Exception
	{
		// look for existing bean in conversation
		StudentTranscript transcript = getWebFlowSupport().getTranscriptFromConversation(context);
		if (isCourseUnavailable(transcript))
		{
			getWebFlowSupport().addError(context, "ta.viewTranscript.transcriptclosed.error.message");
			return result("unavailable");
		}
		
		switch (transcript.getActiveStatus())
		{
			case CLOSED:
				// read-only
				return result("readonly");
			case OPEN:
				// if user has completed both an expectation assessment and a knowledge assessment
				// and have the same number of assessments of each, show the full transcript
				// show them the whole transcript
				if (transcript.hasExpectationAssessment() && 
						transcript.hasKnowledgeAssessment() &&
						transcript.getExpectationAssessments().size() == transcript.getKnowledgeAssessments().size())
				{
					// whole transcript
					return result("full");
				}
				else
				{
					// only the pre-assessment
					return result("preassessment");
				}
			default:
				throw new IllegalArgumentException("Active status " + transcript.getActiveStatus() + " has not been accounted for in class ViewCourseAction.");
		}
		
	}
	
	public Event reloadTranscript(final RequestContext context) throws Exception
	{
		final StudentTranscript transcript = getWebFlowSupport().getTranscriptFromConversation(context);
		getWebFlowSupport().getStudentTranscriptBD(context).reattachTranscript(transcript);
		storeTranscript(context, transcript);
		return success();
	}
	
	@SuppressWarnings("unchecked")
	public Event getExpectationAssessment(final RequestContext context) throws Exception
	{
		// get the list of assessments in the history
		final List<ExpectationAssessment> historyList = (List<ExpectationAssessment>) context.getFlowScope().get(HISTORY_LIST_KEY);
		if (historyList == null || historyList.isEmpty())
		{
			getWebFlowSupport().addError(context, "ta.viewTranscript.nohistory.error.message");
			return error();
		}
		
		// get the index value -- default to most recent assessment
		Integer index = context.getRequestParameters().getInteger(ASSESSMENT_ID_KEY, historyList.size() - 1);
		if (index >= historyList.size() || index < 0)
		{
			index = historyList.size() - 1;
		}
		final ExpectationAssessment assessment = getExpectationAssessment(context, historyList.get(index).getId());
		if (assessment == null)
		{
			getWebFlowSupport().addError(context, "ta.viewTranscript.noassessment.error.message");
			return error();
		}
		// put results in flow scope
		Hibernate.initialize(assessment.getTestItems());
		context.getFlowScope().put("assessment", assessment);
		return success();
	}
	
	@SuppressWarnings("unchecked")
	public Event getKnowledgeAssessment(final RequestContext context) throws Exception
	{
		// get the list of assessments in the history
		final List<KnowledgeAssessment> historyList = (List<KnowledgeAssessment>) context.getFlowScope().get(HISTORY_LIST_KEY);
		if (historyList == null || historyList.isEmpty())
		{
			getWebFlowSupport().addError(context, "ta.viewTranscript.nohistory.error.message");
			return error();
		}
		
		// get the index value -- default to most recent assessment
		Integer index = context.getRequestParameters().getInteger(ASSESSMENT_ID_KEY, historyList.size() - 1);
		if (index >= historyList.size() || index < 0)
		{
			index = historyList.size() - 1;
		}
		final KnowledgeAssessment assessment = getKnowledgeAssessment(context, historyList.get(index).getId());
		if (assessment == null)
		{
			getWebFlowSupport().addError(context, "ta.viewTranscript.noassessment.error.message");
			return error();
		}
		// put results in flow scope
		Hibernate.initialize(assessment.getTestItems());
		context.getFlowScope().put("assessment", assessment);
		return success();
	}
	
	private void storeTranscript(final RequestContext context, final StudentTranscript transcript) throws Exception
	{
		Hibernate.initialize(transcript.getExpectationAssessments());
		Hibernate.initialize(transcript.getKnowledgeAssessments());
		Hibernate.initialize(transcript.getLevel1Practices());
		Hibernate.initialize(transcript.getLevel2Practices());
		Hibernate.initialize(transcript.getLevel3Practices());
		Hibernate.initialize(transcript.getLevel4Practices());
		getWebFlowSupport().storeTranscriptInConversation(context, transcript);
	}
	
	private ExpectationAssessment getExpectationAssessment(final RequestContext context, final Long id) throws Exception
	{
		ExpectationAssessment assessment = null;
		try
		{
			assessment = getWebFlowSupport().getAssessmentBD(context).findExpectationAssessmentById(id);
		}
		catch(final RecordNotFoundException rnfe)
		{
			logger.warn("Unable to find Expectation Assessment with ID: " + id);
		}
		return assessment;
	}
	
	private KnowledgeAssessment getKnowledgeAssessment(final RequestContext context, final Long id) throws Exception
	{
		KnowledgeAssessment assessment = null;
		try
		{
			assessment = getWebFlowSupport().getAssessmentBD(context).findKnowledgeAssessmentById(id);
		}
		catch(final RecordNotFoundException rnfe)
		{
			logger.warn("Unable to find Expectation Assessment with ID: " + id);
		}
		return assessment;
	}
	
	private Course getCourse(final RequestContext context) throws Exception
	{
		final Long id = context.getRequestParameters().getLong(COURSE_ID_KEY);
		if (id == null)
		{
			return null;
		}
		Course course = null;
		try
		{
			course = getWebFlowSupport().getCourseBD(context).findById(Course.class, id);
		}
		catch(final RecordNotFoundException rnfe)
		{
			logger.warn("Unable to find Course with ID: " + id);
		}
		return course;
	}
	
	private StudentTranscript getTranscriptFromUrlId(final RequestContext context) throws Exception
	{
		final Long id = context.getRequestParameters().getLong(TRANSCRIPT_ID_KEY);
		if (id == null)
		{
			return null;
		}
		return getTranscript(context, id);
	}
	
	private StudentTranscript getTranscript(final RequestContext context, final Long id) throws Exception
	{
		StudentTranscript transcript = null;
		try
		{
			transcript = getWebFlowSupport().getStudentTranscriptBD(context).findById(id);
		}
		catch (final RecordNotFoundException rnfe)
		{
			logger.warn("Unable to find Transcript with ID: " + id);
		}
		return transcript;
	}
	
	/**
	 * Evaluates the specified transcript to determine whether it can be viewed or not.
	 * <p>
	 * A transcript is "unavailable" if:
	 * <ul>
	 * <li>The transcript active status is CLOSED</li>
	 * <li>The transcript progress status is NOT_STARTED</li>
	 * </ul>
	 * @param transcript the transcript to evaluate
	 * @return true if the course is unavailable, otherwise false
	 */
	private Boolean isCourseUnavailable(final StudentTranscript transcript)
	{
		if (transcript.getActiveStatus() == TranscriptActiveStatus.CLOSED && 
				transcript.getProgressStatus() == TranscriptProgressStatus.NOT_STARTED)
		{
			return true;
		}
		return false;
	}
	
	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}	
}
