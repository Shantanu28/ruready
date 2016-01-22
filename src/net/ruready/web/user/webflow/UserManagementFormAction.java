package net.ruready.web.user.webflow;

import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.entity.property.UserStatus;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.user.beans.UserAccountFormBean;
import net.ruready.web.user.beans.UserDemographicOptions;
import net.ruready.web.user.beans.UserManagementFormBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.hibernate.StaleObjectStateException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class UserManagementFormAction extends FormAction implements MessageSourceAware
{
	private static final String ID_KEY = "userId";
	private static final String USER_ALREADY_MESSAGE = ".already.error.message";
	
	// hard-code utility class for now. May want to inject it later
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();
	private MessageSource messageSource;
	
	public UserManagementFormAction()
	{
		setFormObjectClass(UserManagementFormBean.class);
	}
	
	public Event getNewFormObject(final RequestContext context) throws Exception
	{
		final UserManagementFormBean form = getForm(context);
		form.setRole(RoleType.STUDENT);
		form.setOptions(
				new UserDemographicOptions(
						getWebFlowSupport().getMessageResources(context)));
		return success();
	}
	
	public Event getUserSelfFormObject(final RequestContext context) throws Exception
	{
		final UserManagementFormBean form = getForm(context);
		final User user = getWebFlowSupport().getSessionUser(context);
		form.setUser(user);
		form.setRole(user.getHighestRole());
		form.setOriginalEmailAddress(user.getEmail());
		form.setOptions(
				new UserDemographicOptions(
						getWebFlowSupport().getMessageResources(context)));
		return success();
	}
	
	public Event getExistingFormObject(final RequestContext context) throws Exception
	{
		final UserManagementFormBean form = getForm(context);
		final Long userId = getIdFromRequest(context);
		final AbstractUserBD userBD = getWebFlowSupport().getUserBD(context);
		final User user = userBD.findById(userId);
		if (user == null)
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return error();
		}
		form.setUser(user);
		form.setRole(user.getHighestRole());
		form.setOriginalEmailAddress(user.getEmail());
		form.setOptions(
				new UserDemographicOptions(
						getWebFlowSupport().getMessageResources(context)));
		return success();
	}
	
	public Event determineEntryFormType(final RequestContext context) throws Exception
	{
		// RULE: if user is not a student, then they get the "specify", or required name form
		// if user is a student and is subscribed to at least one group, then they get the "specify" form
		// otherwise, user can get the anonymous/optional form.
		final UserManagementFormBean form = getForm(context);
		if (form.getUser().getHighestRole() == RoleType.STUDENT)
		{
			final StudentRole student = (StudentRole) form.getUser().getRole(RoleType.STUDENT);
			if (student.getGroups().size() > 0)
			{
				return result("specify");
			}
			else
			{
				return result("anonymous");
			}
		}
		else
		{
			return result("specify");
		}
	}
	
	public Event assertNotSelf(final RequestContext context) throws Exception
	{
		final UserAccountFormBean form = getForm(context);
		final User user = getWebFlowSupport().getSessionUser(context);
		if (user.equals(form.getUser()))
		{
			getFormErrors(context).reject(getWebFlowSupport().getSelfErrorMessage(context));
			return error();
		}
		else
		{
			return success();
		}
	}
	
	public Event assertLocked(final RequestContext context) throws Exception
	{
		final UserAccountFormBean form = getForm(context);
		if (form.getUser().getUserStatus() != UserStatus.LOCKED)
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, USER_ALREADY_MESSAGE), 
					new Object[] {form.getUser().getEmail()}, 
					getWebFlowSupport().getMissingKey(context, USER_ALREADY_MESSAGE));
			return error();
		}
		return success();
	}
	
	public Event assertUnlocked(final RequestContext context) throws Exception
	{
		final UserAccountFormBean form = getForm(context);
		if (form.getUser().getUserStatus() == UserStatus.LOCKED)
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, USER_ALREADY_MESSAGE), 
					new Object[] {form.getUser().getEmail()}, 
					getWebFlowSupport().getMissingKey(context, USER_ALREADY_MESSAGE));
			return error();
		}
		return success();
	}
	
	public Event assertUserDoesntExist(final RequestContext context) throws Exception
	{
		final AbstractUserBD bdUser = getWebFlowSupport().getUserBD(context);
		final User user = getForm(context).getUser();
		if (bdUser.findByUniqueEmail(user.getEmail()) != null)
		{
			addDuplicateUserErrorMessage(context);
			return error();
		}
		return success();
	}
	
	public Event createUser(final RequestContext context) throws Exception
	{
		final UserManagementFormBean form = getForm(context);
		final AbstractUserBD userBD = getWebFlowSupport().getUserBD(context);
		form.getUser().setPassword(userBD.generatePassword(form.getUser()));
		form.getUser().addRole(form.getRole().newInstance());
		userBD.createUser(form.getUser());
		getWebFlowSupport().addSuccessMessage(context, form.getUser().getEmail());
		return success();
	}
	
	public Event updateUser(final RequestContext context) throws Exception
	{
		final UserManagementFormBean form = getForm(context);
		final AbstractUserBD userBD = getWebFlowSupport().getUserBD(context);
		// if user has changed their e-mail address, then verify it isn't the same
		// as someone elses
		if (!form.getOriginalEmailAddress().equals(form.getUser().getEmail()))
		{
			if (userBD.findByUniqueEmail(form.getUser().getEmail()) != null)
			{
				addDuplicateUserErrorMessage(context);
				return result("duplicateEmail");
			}
		}
		userBD.updateUserAndRolesMerge(form.getUser(), form.getRole());
		getWebFlowSupport().addSuccessMessage(context, form.getUser().getEmail());
		return success();
	}
	
	public Event deleteUser(final RequestContext context) throws Exception
	{
		final UserAccountFormBean form = getForm(context);
		final AbstractUserBD userBD = getWebFlowSupport().getUserBD(context);
		final User deleteUser = userBD.findById(form.getUser().getId());
		getWebFlowSupport().getUserBD(context).deleteUser(deleteUser);
		getWebFlowSupport().addSuccessMessage(context, deleteUser.getEmail());
		return success();
	}
	
	public Event lockUser(final RequestContext context) throws Exception
	{
		final User user = getForm(context).getUser();
		changeLockStatus(context, UserStatus.LOCKED);
		getWebFlowSupport().addSuccessMessage(context, user.getEmail());
		return success();
	}
	
	public Event unlockUser(final RequestContext context) throws Exception
	{
		final User user = getForm(context).getUser();
		changeLockStatus(context, UserStatus.ACTIVE);
		getWebFlowSupport().addSuccessMessage(context, user.getEmail());
		return success();
	}
	
	public Event resetPassword(final RequestContext context) throws Exception
	{
		final UserManagementFormBean form = getForm(context);
		final AbstractUserBD userBD = getWebFlowSupport().getUserBD(context);
		form.getUser().setPassword(userBD.generatePassword(form.getUser()));
		// try to update as a simple merge
		try {
			userBD.updateUserMerge(form.getUser());
		}
		// failed because there is a newer version - reload and update
		catch (final StaleObjectStateException sose)
		{
			final User dbUser = userBD.findById(form.getUser().getId());
			dbUser.setPassword(userBD.generatePassword(dbUser));
			userBD.updateUser(dbUser);
		}
		getWebFlowSupport().addSuccessMessage(context, form.getUser().getEmail());
		mailPasswordResetMessage(context, form.getUser());
		return success();
	}
	
	public Event addMailErrorMessage(final RequestContext context) throws Exception
	{
		final UserManagementFormBean form = getForm(context);
		getWebFlowSupport().addMessage(
				context, 
				getWebFlowSupport().getMessageKey(context, ".error.mail"), 
				new Object[] { form.getUser().getEmail()});
		return success();
	}
	
	public Event doCancel(final RequestContext context) throws Exception
	{
		getWebFlowSupport().addCancelMessage(context);
		return success();
	}
	
	public Event exposeErrors(final RequestContext context) throws Exception
	{
		getWebFlowSupport().exposeErrors(context, getFormErrors(context));
		return success();
	}
	
	public Event addEditConflictErrorMessages(final RequestContext context) throws Exception
	{
		getFormErrors(context).reject(getWebFlowSupport().getMessageKey(context, ".conflictInfo.error.message"));
		getFormErrors(context).reject(getWebFlowSupport().getMessageKey(context, ".conflictPrompt.error.message"));
		return success();
	}

	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	
	private void changeLockStatus(final RequestContext context, final UserStatus userStatus) throws Exception
	{
		final User user = getForm(context).getUser();
		user.setUserStatus(userStatus);
		final AbstractUserBD userBD = getWebFlowSupport().getUserBD(context);
		// try to update the object using a simple merge
		try {
			userBD.updateUserMerge(user);
		}
		// object was stale -- load a new version and update the status
		catch (final StaleObjectStateException sose) {
			final User dbUser = userBD.findById(user.getId());
			dbUser.setUserStatus(userStatus);
			userBD.updateUser(dbUser);
		}
	}
	
	private Long getIdFromRequest(final RequestContext context) throws Exception
	{
		return context.getRequestScope().getLong(ID_KEY);
	}
	
	private void mailPasswordResetMessage(final RequestContext context, final User user)
	{
		final AbstractUserBD bdUser = getWebFlowSupport().getUserBD(context);
		final String subject = this.messageSource.getMessage(
				WebAppNames.KEY.MAIL_RESET_SUBJECT, 
				null, 
				getWebFlowSupport().getLocale(context));
		final String content = this.messageSource.getMessage(
				WebAppNames.KEY.MAIL_RESET_CONTENT, 
				new Object[] { 
						user.getEmail(), 
						bdUser.decryptPassword(user.getPassword())}, 
				getWebFlowSupport().getLocale(context));
		bdUser.mailMessage(user, subject, content);
	}
	
	private void addDuplicateUserErrorMessage(final RequestContext context) throws Exception
	{
		final User user = getForm(context).getUser();
		getFormErrors(context).reject(
				WebAppNames.KEY.EXCEPTION.RECORD_EXISTS_EDIT_USER, 
				new Object[] { user.getEmail()}, 
				"");
	}
	
	private UserManagementFormBean getForm(final RequestContext context) throws Exception
	{
		return (UserManagementFormBean) getFormObject(context);
	}
	
	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}
}
