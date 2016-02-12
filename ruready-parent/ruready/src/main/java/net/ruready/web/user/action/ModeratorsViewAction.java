package net.ruready.web.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.exports.AbstractUserGroupBD;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.user.beans.ModeratorManagementViewBean;
import net.ruready.web.user.support.StrutsSupport;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.MappingDispatchAction;

public class ModeratorsViewAction extends MappingDispatchAction
{	
	private static final String BEAN_NAME = "viewBean";
	private static final String ID_KEY = "groupId";
	
	private final StrutsSupport strutsSupport = new StrutsSupport();

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewModerators(
			final ActionMapping mapping, 
			final ActionForm form, 
			final HttpServletRequest request, 
			final HttpServletResponse response) 
		throws Exception
	{		
		final User user = HttpRequestUtil.findUser(request);
		final TeacherRole teacherRole = (TeacherRole) user.getRole(RoleType.TEACHER);
		final Long id = getStrutsSupport().getIdFromUrl(request, ID_KEY);
		final AbstractUserGroupBD userGroupBD = getStrutsSupport().getUserGroupBD(request);
		final UserGroup userGroup = userGroupBD.findUserGroupById(id);
		
		// verify that the group exists
		if (userGroup == null)
		{
			addNoGroupErrorMessage(request);
			return mapping.findForward("error");
		}
		
		final List<UserRole> moderators = userGroupBD.findModerators(userGroup);

		// verify that group is not deleted
		if (userGroup.isDeleted())
		{
			addGroupDeletedErrorMessage(request);
			return mapping.findForward("error");
		}
		// verify that user is a moderator for the group
		if (!isUserModeratorOfGroup(user, moderators))
		{
			addNotModeratorErrorMessage(request);
			return mapping.findForward("error");
		}
		final ModeratorManagementViewBean viewBean = new ModeratorManagementViewBean();
		viewBean.setGroup(userGroup);
		viewBean.setUsers(moderators);
		viewBean.setSelf(user);
		viewBean.setCanManage(
				userGroup.hasPrimaryModerator() &&
				userGroup.getPrimaryModerator().getModerator().getId() == teacherRole.getId());
		
		request.setAttribute(BEAN_NAME, viewBean);
		return mapping.findForward("success");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMembers(
			final ActionMapping mapping, 
			final ActionForm form,
			final HttpServletRequest request, 
			final HttpServletResponse response) 
		throws Exception
	{	
		final User user = HttpRequestUtil.findUser(request);
		final Long id = getStrutsSupport().getIdFromUrl(request, ID_KEY);
		final AbstractUserGroupBD userGroupBD = getStrutsSupport().getUserGroupBD(request);
		final UserGroup userGroup = userGroupBD.findUserGroupById(id);
		
		// verify that the group exists
		if (userGroup == null)
		{
			addNoGroupErrorMessage(request);
			return mapping.findForward("error");
		}
		
		final List<UserRole> moderators = userGroupBD.findModerators(userGroup);
		
		// verify that group is not deleted
		if (userGroup.isDeleted())
		{
			addGroupDeletedErrorMessage(request);
			return mapping.findForward("error");
		}
		// verify that user is a moderator for the group
		if (!isUserModeratorOfGroup(user, moderators))
		{
			addNotModeratorErrorMessage(request);
			return mapping.findForward("error");
		}
		
		final List<UserRole> members = userGroupBD.findMembers(userGroup);
		final ModeratorManagementViewBean viewBean = new ModeratorManagementViewBean();
		viewBean.setGroup(userGroup);
		viewBean.setUsers(members);
		viewBean.setSelf(user);
		
		request.setAttribute(BEAN_NAME, viewBean);
		return mapping.findForward("success"); 
	}
	
	private void addNotModeratorErrorMessage(final HttpServletRequest request)
	{
		ActionMessages newMessages = new ActionMessages();
		newMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.groupModerators.view.error.message"));
		saveErrors(request.getSession(false), newMessages);
	}
	
	private void addNoGroupErrorMessage(final HttpServletRequest request)
	{
		ActionMessages newMessages = new ActionMessages();
		newMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.groupModerators.view.nogroup.error.message"));
		saveErrors(request.getSession(false), newMessages);
	}
	
	private void addGroupDeletedErrorMessage(final HttpServletRequest request)
	{
		ActionMessages newMessages = new ActionMessages();
		newMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.groupModerators.view.deleted.error.message"));
		saveErrors(request.getSession(false), newMessages);
	}
	
	private Boolean isUserModeratorOfGroup(final User user, final List<UserRole> moderators)
	{
		final UserRole userRole = user.getRole(RoleType.TEACHER);
		for (UserRole moderator : moderators)
		{
			if (moderator.getId() == userRole.getId())
			{
				return true;
			}
		}
		return false;
	}

	private StrutsSupport getStrutsSupport()
	{
		return this.strutsSupport;
	}
}
