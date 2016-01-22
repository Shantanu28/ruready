package net.ruready.web.user.action;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.user.beans.SchoolsViewBean;
import net.ruready.web.user.beans.TeacherSchoolMembershipsViewBean;
import net.ruready.web.user.support.StrutsSupport;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

public class SchoolMembershipsViewAction extends MappingDispatchAction
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
	public ActionForward viewAllPendingTeacherSchoolMemberships(
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
		throws Exception
	{
		final List<TeacherSchoolMembership> memberships = getStrutsSupport().getUserBD(
				request).findTeacherSchoolMembershipsByStatus(ActiveStatus.PENDING);
		final TeacherSchoolMembershipsViewBean viewBean = new TeacherSchoolMembershipsViewBean();
		viewBean.setMembers(memberships);
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
	public ActionForward viewStudentSchoolMemberships(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception
	{
		final User user = HttpRequestUtil.findUser(request);
		final StudentRole student = (StudentRole) user.getRole(RoleType.STUDENT);
		final SchoolsViewBean viewBean = new SchoolsViewBean();
		viewBean.setSchools(student.getSchools());
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
	public ActionForward viewTeacherSchoolMemberships(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception
	{
		final User user = HttpRequestUtil.findUser(request);
		final TeacherRole teacher = (TeacherRole) user.getRole(RoleType.TEACHER);
		final Set<TeacherSchoolMembership> memberships = teacher.getSchools();
		final TeacherSchoolMembershipsViewBean viewBean = new TeacherSchoolMembershipsViewBean();
		viewBean.setMembers(memberships);
		request.setAttribute(BEAN_NAME, viewBean);
		return mapping.findForward("success");
	}

	private StrutsSupport getStrutsSupport()
	{
		return this.strutsSupport;
	}

}
