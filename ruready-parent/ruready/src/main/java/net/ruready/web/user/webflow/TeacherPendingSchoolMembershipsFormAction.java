package net.ruready.web.user.webflow;

import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.web.user.beans.TeacherSchoolMembershipFormBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class TeacherPendingSchoolMembershipsFormAction extends FormAction implements MessageSourceAware
{
	private static final String SCHOOL_ID_KEY = "schoolId";
	private static final String TEACHER_ID_KEY = "userId";
	
	private static final String ACTIVE_ERROR_MESSAGE = ".active.error.message";
	private static final String DISABLED_ERROR_MESSAGE = ".disabled.error.message";
	
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();

	private MessageSource messageSource;
	
	public TeacherPendingSchoolMembershipsFormAction()
	{
		setFormObjectClass(TeacherSchoolMembershipFormBean.class);
	}
	
	public Event getReviewFormObject(final RequestContext context) throws Exception
	{
		final TeacherSchoolMembershipFormBean form = getForm(context);
		
		final TeacherRole teacher = getTeacherByUrlId(context);
		if (teacher == null)
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return error();
		}
		final TeacherSchoolMembership membership = getMembershipByUrlId(context, teacher);
		// if membership is missing, error
		if (membership == null)
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return error();
		}
		// if status is active, error
		else if (membership.getMemberStatus() == ActiveStatus.ACTIVE)
		{
			getFormErrors(context).reject(getWebFlowSupport().getMessageKey(context, ACTIVE_ERROR_MESSAGE));
			return error();
		}
		// if status is disabled, error
		else if (membership.getMemberStatus() == ActiveStatus.DISABLED)
		{
			getFormErrors(context).reject(getWebFlowSupport().getMessageKey(context, DISABLED_ERROR_MESSAGE));
			return error();
		}
		form.setMembership(membership);
		// TODO figure out another way to do this.
		// prime the object
		membership.getMember().getUser().getEmail();
		return success();
	}
	
	public Event processForm(final RequestContext context) throws Exception
	{
		final TeacherSchoolMembershipFormBean form = getForm(context);
		final AbstractUserBD userBD = getWebFlowSupport().getUserBD(context);
		ActiveStatus activeStatus;
		switch(form.getApprovalStatus())
		{
			case APPROVE:
				activeStatus = ActiveStatus.ACTIVE;
				break;
			case DENY:
				activeStatus = ActiveStatus.DISABLED;
				break;
			// should never reach this
			default:
				getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return error();
		}
		
		final TeacherSchoolMembership membership = form.getMembership();
		// set status and reason
		membership.updateMemberStatus(activeStatus, form.getReason());
		userBD.updateSchoolMembership(membership);
		
		getWebFlowSupport().addSuccessMessage(context);
		
		// e-mail user with status
		mailStatusUpdateMessage(context, membership.getMember().getUser());
		return success();
	}
	
	public Event doCancel(final RequestContext context) throws Exception
	{
		getWebFlowSupport().addCancelMessage(context);
		return success();
	}
	
	public Event addMailErrorMessage(final RequestContext context) throws Exception
	{
		final TeacherSchoolMembershipFormBean form = getForm(context);
		final User user = form.getMembership().getMember().getUser();
		getWebFlowSupport().addMessage(
				context, 
				getWebFlowSupport().getMessageKey(context, ".error.mail"), 
				new Object[] { user.getEmail()});
		return success();
	}
	
	public Event exposeErrors(final RequestContext context) throws Exception
	{
		getWebFlowSupport().exposeErrors(context, getFormErrors(context));
		return success();
	}
	
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	
	private TeacherSchoolMembership getMatchingMembership(final TeacherRole teacher, final Long schoolId)
	{
		for (TeacherSchoolMembership membership : teacher.getSchools())
		{
			if (membership.getSchool().getId().longValue() == schoolId)
			{
				return membership;
			}
		}		
		return null;
	}
	
	private void mailStatusUpdateMessage(final RequestContext context, final User user) throws Exception
	{
		final TeacherSchoolMembershipFormBean form = getForm(context);
		final AbstractUserBD bdUser = getWebFlowSupport().getUserBD(context);
		String messagePrefix;
		switch (form.getApprovalStatus())
		{
			case APPROVE:
				messagePrefix = ".approved";
				break;
			case DENY:
				messagePrefix = ".denied";
				break;
			default:
				throw new IllegalArgumentException("This method only supports APPROVE and DENY statuses");
		}
		final String subject = this.messageSource.getMessage(
				getWebFlowSupport().getMessageKey(context, messagePrefix + ".subject"), 
				null, 
				getWebFlowSupport().getLocale(context));
		final String content = this.messageSource.getMessage(
				getWebFlowSupport().getMessageKey(context, messagePrefix + ".content"), 
				new Object[] { 
					form.getMembership().getSchool().getDescription(), 
					form.getReason() }, 
					getWebFlowSupport().getLocale(context));
		bdUser.mailMessage(user, subject, content);
	}
	
	private TeacherSchoolMembershipFormBean getForm(final RequestContext context) throws Exception
	{
		return (TeacherSchoolMembershipFormBean) getFormObject(context);
	}
	
	private TeacherRole getTeacherByUrlId(final RequestContext context) throws Exception
	{
		final Long teacherId = getTeacherIdFromUrl(context);
		if (teacherId == null) return null;
		return getTeacherById(context, teacherId);
	}
	
	private TeacherRole getTeacherById(final RequestContext context, final Long teacherId) throws Exception
	{
		return getWebFlowSupport().getUserRoleBD(context).findTeacherRoleById(teacherId);		
	}
	
	private Long getTeacherIdFromUrl(final RequestContext context) throws Exception
	{
		return context.getRequestParameters().getLong(TEACHER_ID_KEY);
	}
	
	private TeacherSchoolMembership getMembershipByUrlId(final RequestContext context, final TeacherRole teacher) throws Exception
	{
		final Long schoolId = getSchoolIdFromUrl(context);
		if (schoolId == null) return null;
		return getMatchingMembership(teacher, schoolId);
	}
	
	private Long getSchoolIdFromUrl(final RequestContext context) throws Exception
	{
		return context.getRequestParameters().getLong(SCHOOL_ID_KEY);
	}
	
	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}
}
