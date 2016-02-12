package net.ruready.web.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.exports.AbstractUserGroupBD;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.user.beans.GroupsViewBean;
import net.ruready.web.user.beans.ModeratedGroupsViewBean;
import net.ruready.web.user.support.StrutsSupport;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

public class GroupsViewAction extends MappingDispatchAction
{
	private static final String BEAN_NAME = "viewBean";
	
	private final StrutsSupport strutsSupport = new StrutsSupport();

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewModeratedGroups(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)	throws Exception
	{
		final User user = HttpRequestUtil.findUser(request);
		final TeacherRole teacherRole = (TeacherRole) user.getRole(RoleType.TEACHER);
		
		final ModeratedGroupsViewBean viewBean = new ModeratedGroupsViewBean();
		viewBean.setCanManage(teacherRole.hasActiveSchoolMemberships());
		viewBean.setUserGroups(teacherRole.getGroups());
		
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
	public ActionForward viewMemberships(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response)	throws Exception
	{
		final User user = HttpRequestUtil.findUser(request);

		final AbstractUserGroupBD userGroupBD = getStrutsSupport().getUserGroupBD(request);
		final List<UserGroup> groups = userGroupBD.findMemberships(user);
		
		final GroupsViewBean viewBean = new GroupsViewBean();
		viewBean.setCanManage(user.getName().hasName());
		viewBean.setUserGroups(groups);

		request.setAttribute(BEAN_NAME, viewBean);
		return mapping.findForward("success");
	}
	
	private StrutsSupport getStrutsSupport()
	{
		return this.strutsSupport;
	}
}
