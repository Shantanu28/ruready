package net.ruready.web.user.webflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupModerator;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.exports.AbstractUserGroupBD;
import net.ruready.web.user.beans.ModeratorFormBean;
import net.ruready.web.user.beans.SimpleUserRoleListViewBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.hibernate.Hibernate;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class ModeratorFormAction extends FormAction
{
	private static final String GROUP_ID_KEY = "groupId";
	private static final String MODERATOR_ID_KEY = "userId";
	
	private static final String RESULTS_BEAN = "results";
	
	private static final String SELF_SUCCESS_MESSAGE = ".self.success.message";
	private static final String PRIMARY_MODERATOR_SUCCESS_MESSAGE = ".primaryModerator.success.message";
	
	private static final String GROUP_LOOKUP_ERROR_MESSAGE = ".groupLookup.error.message";
	private static final String GROUP_DELETED_ERROR_MESSAGE = ".deleted.error.message";
	
	private static final String MODERATOR_LOOKUP_ERROR_MESSAGE = ".moderatorLookup.error.message";
	private static final String NO_RIGHTS_ERROR_MESSAGE = ".noRights.error.message";
	private static final String MODERATOR_NO_SCHOOL_MEMBERSHIP_ERROR_MESSAGE = ".noSchoolMembership.error.message";
	private static final String ONLY_MODERATOR_SELF_ERROR_MESSAGE = ".onlyModeratorSelf.error.message";
	private static final String ONLY_MODERATOR_ERROR_MESSAGE = ".onlyModerator.error.message";
	
	// hard-code utility class for now. May want to inject it later
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();
	
	public ModeratorFormAction()
	{
		setFormObjectClass(ModeratorFormBean.class);
	}
	
	public Event getNewFormObject(final RequestContext context) throws Exception
	{
		final ModeratorFormBean form = getForm(context);
		final User user = getWebFlowSupport().getSessionUser(context);
		final UserGroup userGroup = getGroupByInputId(context);
		if (userGroup == null)
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, GROUP_LOOKUP_ERROR_MESSAGE));
			return error();
		}
		// verify that group is not deleted
		if (userGroup.isDeleted())
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, GROUP_DELETED_ERROR_MESSAGE),
					new Object[] {userGroup.getName()},
					null);
			return error();
		}
		// user can add a moderator if they are:
		// * the primary moderator
		// * an administrator
		else if (!hasEditRights(user, userGroup))
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, NO_RIGHTS_ERROR_MESSAGE));
			return error();
		}
		form.setUserGroup(userGroup);
		return success();
	}
	
	public Event getDeleteFormObject(final RequestContext context) throws Exception
	{
		final ModeratorFormBean form = getForm(context);
		final User user = getWebFlowSupport().getSessionUser(context);
		final UserGroup userGroup = getGroupByInputId(context);
		if (userGroup == null)
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, GROUP_LOOKUP_ERROR_MESSAGE));
			return error();
		}
		// verify that group is not deleted
		if (userGroup.isDeleted())
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, GROUP_DELETED_ERROR_MESSAGE),
					new Object[] {userGroup.getName()},
					null);
			return error();
		}
		final UserGroupModerator moderator = getModeratorByInputId(context, userGroup);
		if (moderator == null)
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, MODERATOR_LOOKUP_ERROR_MESSAGE));
			return error();
		}
		// user can delete the moderator if they are:
		// * the user to delete
		// * the primary moderator
		// * an administrator
		else if (!isModeratorSelf(user, moderator) && !hasEditRights(user, userGroup))
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, NO_RIGHTS_ERROR_MESSAGE));
			return error();
		}
		form.setUserGroup(userGroup);
		// force initialization of the proxy object.
		Hibernate.initialize(moderator.getModerator().getUser());
		form.setMatchingUser(moderator);
		return success();
	}
	
	public Event addModerator(final RequestContext context) throws Exception
	{
		final ModeratorFormBean form = getForm(context);
		final TeacherRole moderator = getTeacherByUrlId(context);
		// could not find matching teacher/moderator
		if (moderator == null)
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, MODERATOR_LOOKUP_ERROR_MESSAGE));
			return error();
		}
		// verify that the moderator is has the user group's school as an active membership
		else if (!moderator.hasActiveMembershipToSchool(form.getUserGroup().getSchool()))
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, MODERATOR_NO_SCHOOL_MEMBERSHIP_ERROR_MESSAGE));
			return error();
		}
		final UserGroup userGroup = getWebFlowSupport().getUserGroupBD(context).findUserGroupById(form.getUserGroup().getId());
		getWebFlowSupport().getUserGroupBD(context).addModerator(userGroup, moderator);
		getWebFlowSupport().addSuccessMessage(context, moderator.getUser().getEmail());
		return success();
	}
	
	public Event deleteModerator(final RequestContext context) throws Exception
	{
		final User user = getWebFlowSupport().getSessionUser(context);
		final ModeratorFormBean form = getForm(context);
		final AbstractUserGroupBD userGroupBD = getWebFlowSupport().getUserGroupBD(context);
		// remove the selected moderator
		final UserGroupModerator moderator = userGroupBD.findModerator(form.getMatchingUser());
		userGroupBD.removeModerator(moderator);
		// if there is a primary moderator reassignment, then do it.
		if (form.getNewPrimaryModerator() != null)
		{
			final UserGroupModerator newPrimaryModerator = userGroupBD.findModerator(form.getNewPrimaryModerator());
			newPrimaryModerator.setOwner(true);
			form.setNewPrimaryModerator(newPrimaryModerator);
			userGroupBD.updateModerator(newPrimaryModerator);
		}
		if (isModeratorSelf(user, moderator))
		{
			getWebFlowSupport().addMessage(
					context, 
					getWebFlowSupport().getMessageKey(context, SELF_SUCCESS_MESSAGE), 
					form.getUserGroup().getName());
		}
		else
		{
			getWebFlowSupport().addSuccessMessage(
					context, 
					moderator.getModerator().getUser().getEmail());
		}		
		if (form.getNewPrimaryModerator() != null)
		{
			getWebFlowSupport().addMessage(
					context, 
					getWebFlowSupport().getMessageKey(context, PRIMARY_MODERATOR_SUCCESS_MESSAGE), 
					form.getNewPrimaryModerator().getModerator().getUser().getEmail(),
					form.getUserGroup().getName());
		}
		return success();
	}
	
	public Event assertHasModeratorAfterDeletion(final RequestContext context) throws Exception
	{
		// RULE: can delete user if there is at least one moderator left following deletion
		if (getForm(context).getUserGroup().getModerators().size() > 1)
		{
			return yes();
		}
		else
		{
			if (isModeratorSelf(getWebFlowSupport().getSessionUser(context), getForm(context).getMatchingUser()))
			{
				getFormErrors(context).reject(
						getWebFlowSupport().getMessageKey(context, ONLY_MODERATOR_SELF_ERROR_MESSAGE));
			}
			else
			{
				getFormErrors(context).reject(
						getWebFlowSupport().getMessageKey(context, ONLY_MODERATOR_ERROR_MESSAGE));
			}
			return no();
		}
	}
	
	public Event isUserDeletingSelf(final RequestContext context) throws Exception
	{
		final User user = getWebFlowSupport().getSessionUser(context);
		final UserGroupModerator moderator = getForm(context).getMatchingUser();
		return result(isModeratorSelf(user, moderator));
	}
	
	public Event isModeratorPrimaryModerator(final RequestContext context) throws Exception
	{
		final UserGroup userGroup = getForm(context).getUserGroup();
		final UserGroupModerator moderator = getForm(context).getMatchingUser();
		return result(
				userGroup.hasPrimaryModerator() &&
				userGroup.getPrimaryModerator().equals(moderator));
		/*return result(
				isPrimaryModerator(
						getWebFlowSupport().getSessionUser(context), 
						getForm(context).getUserGroup()));*/
	}
	
	public Event getPotentialModerators(final RequestContext context) throws Exception
	{
		final List<UserRole> results = loadPotentialModerators(context);
		exposeResults(context, results);	
		return success();
	}
	
	public Event getCurrentModerators(final RequestContext context) throws Exception
	{
		final UserGroup userGroup = getForm(context).getUserGroup();
		final UserGroupModerator moderator = getForm(context).getMatchingUser();
		final List<UserRole> results = getWebFlowSupport().getUserGroupBD(context).findModerators(userGroup);
		// remove the moderator to delete as a candidate
		results.remove(moderator.getModerator());
		exposeResults(context, results);
		return success();
	}
	
	public Event setNewPrimaryModerator(final RequestContext context) throws Exception
	{
		// get moderator from url
		final ModeratorFormBean form = getForm(context);
		final TeacherRole teacher = getTeacherByUrlId(context);
		// could not find matching teacher/moderator
		if (teacher == null)
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, MODERATOR_LOOKUP_ERROR_MESSAGE));
			return error();
		}
		final UserGroupModerator moderator = getWebFlowSupport().getUserGroupBD(context).findModerator(form.getUserGroup().getId(), teacher.getId());
		if (moderator == null)
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context, MODERATOR_LOOKUP_ERROR_MESSAGE));
			return error();
		}
		// force initialization of the proxy objects.
		Hibernate.initialize(moderator.getModerator());
		Hibernate.initialize(moderator.getGroup());
		form.setNewPrimaryModerator(moderator);
		return success();
	}
	
	public Event exposeErrors(final RequestContext context) throws Exception
	{
		getWebFlowSupport().exposeErrors(context, getFormErrors(context));
		return success();
	}
	
	public Event doCancel(final RequestContext context) throws Exception
	{
		getWebFlowSupport().addCancelMessage(context);
		return success();
	}

	public WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}
	
	private ModeratorFormBean getForm(final RequestContext context) throws Exception
	{
		return (ModeratorFormBean) getFormObject(context);
	}
	
	private void exposeResults(final RequestContext context, final List<UserRole> results)
	{
		primeModerators(results);
		final SimpleUserRoleListViewBean bean = new SimpleUserRoleListViewBean();
		bean.setUsers(results);
		context.getFlashScope().put(RESULTS_BEAN, bean);
	}
	
	private UserGroupModerator getModeratorByInputId(final RequestContext context, final UserGroup userGroup) throws Exception
	{
		final Long moderatorId = context.getRequestScope().getLong(MODERATOR_ID_KEY);
		if (moderatorId == null) return null;
		return getModeratorById(userGroup, moderatorId);
	}
	
	private TeacherRole getTeacherByUrlId(final RequestContext context) throws Exception
	{
		final Long moderatorId = context.getRequestParameters().getLong(MODERATOR_ID_KEY);
		if (moderatorId == null) return null;
		return getWebFlowSupport()
			.getUserRoleBD(context)
			.findTeacherRoleById(moderatorId);
	}
	
	private UserGroup getGroupByInputId(final RequestContext context) throws Exception
	{
		final Long groupId = context.getRequestScope().getLong(GROUP_ID_KEY);
		if (groupId == null) return null;
		return getWebFlowSupport().getUserGroupBD(context).findUserGroupById(groupId);
	}
	
	private UserGroupModerator getModeratorById(final UserGroup userGroup, final Long id) throws Exception
	{
		for (UserGroupModerator moderator : userGroup.getModerators())
		{
			// figure out why I had to do the long value here (very weird!)
			if (moderator.getModerator().getId().longValue() == id)
			{
				return moderator;
			}
		}
		return null;
	}
	
	private List<UserRole> loadPotentialModerators(final RequestContext context) throws Exception
	{
		final UserGroup userGroup = getWebFlowSupport()
			.getUserGroupBD(context)
			.findUserGroupById(getForm(context).getUserGroup().getId());
		final School school = getWebFlowSupport().getWorldBD(context).findSchoolById(userGroup.getSchool().getId());
		final List<UserRole> moderators = new ArrayList<UserRole>();
		for (TeacherSchoolMembership membership : school.getTeachers())
		{
			// only add teachers that have an active membership to this school
			if (membership.getMemberStatus() == ActiveStatus.ACTIVE)
			{
				moderators.add(membership.getMember());
			}
		}
		// remove existing moderators from the list
		for (UserGroupModerator moderator : userGroup.getModerators())
		{
			moderators.remove(moderator.getModerator());
		}
		return moderators;
	}
	
	private Boolean isPrimaryModerator(final User user, final UserGroup userGroup)
	{
		final User primaryModerator = 
			(userGroup.hasPrimaryModerator() ? 
				userGroup.getPrimaryModerator().getModerator().getUser() : null);
		
		return (user.equals(primaryModerator));
	}
	
	/**
	 * Determines whether a user has the rights to manage moderators
	 * @param user the user to do the check on
	 * @param userGroup the user group that the user is trying to manage moderators on
	 * @return true if the user can manage the moderators of this group, otherwise false
	 */
	private Boolean hasEditRights(final User user, final UserGroup userGroup)
	{
		if (user.hasRole(RoleType.ADMIN))
		{
			return true;
		}
		return isPrimaryModerator(user, userGroup);
	}
	
	private Boolean isModeratorSelf(final User user, final UserGroupModerator moderator)
	{
		final TeacherRole self = (TeacherRole) user.getRole(RoleType.TEACHER);
		return (self.getId().longValue() == moderator.getModerator().getId());
	}
	
	private void primeModerators(final Collection<UserRole> moderators)
	{
		for (UserRole userRole: moderators)
		{
			userRole.getUser().getEmail();
		}
	}
}
