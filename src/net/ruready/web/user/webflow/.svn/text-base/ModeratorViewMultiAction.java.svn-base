package net.ruready.web.user.webflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserRole;
import net.ruready.web.user.beans.ModeratorManagementViewBean;
import net.ruready.web.user.beans.SchoolMembersViewBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class ModeratorViewMultiAction extends MultiAction
{
	private static final String GROUP_ID_KEY = "groupId";
	private static final String SCHOOL_ID_KEY = "schoolId";
	private static final String RESULTS_BEAN = "results";

	private final WebFlowSupport webFlowSupport = new WebFlowSupport();

	public Event getModerators(final RequestContext context) throws Exception
	{
		final UserGroup userGroup = getUserGroupByInputId(context);
		exposeGroupResults(
				context, 
				userGroup, 
				getWebFlowSupport().getUserGroupBD(context).findModerators(userGroup));
		return success();
	}

	public Event getMembers(final RequestContext context) throws Exception
	{
		final UserGroup userGroup = getUserGroupByInputId(context);
		exposeGroupResults(
				context, 
				userGroup, 
				getWebFlowSupport().getUserGroupBD(context).findMembers(userGroup));
		return success();
	}
	
	public Event getStudents(final RequestContext context) throws Exception
	{
		final School school = getSchoolByInputId(context);
		exposeSchoolResults(
				context, 
				school, 
				getWebFlowSupport().getUserBD(context).findStudentSchoolMembershipsBySchool(school));
		return success();
	}
	
	public Event getTeachers(final RequestContext context) throws Exception
	{
		final School school = getSchoolByInputId(context);
		exposeSchoolResults(
				context, 
				school, 
				getTeachersBySchool(school));
		return success();
	}
	
	private List<UserRole> getTeachersBySchool(final School school)
	{
		final List<UserRole> results = new ArrayList<UserRole>(school.getTeachers().size());
		for (TeacherSchoolMembership membership : school.getTeachers())
		{
			results.add(membership.getMember());
		}
		return results;
	}

	private UserGroup getUserGroupByInputId(final RequestContext context)
	{
		final Long groupId = Long.valueOf(
				context.getFlashScope().getString(GROUP_ID_KEY));
		return getWebFlowSupport().getUserGroupBD(context).findUserGroupById(groupId);
	}
	
	private School getSchoolByInputId(final RequestContext context)
	{
		final Long schoolId = Long.valueOf(
				context.getFlashScope().getString(SCHOOL_ID_KEY));
		return getWebFlowSupport().getWorldBD(context).findSchoolById(schoolId);
	}

	private void exposeGroupResults(
			final RequestContext context, 
			final UserGroup userGroup,
			final List<UserRole> results)
	{
		primeResults(results);
		final ModeratorManagementViewBean bean = new ModeratorManagementViewBean();
		bean.setCanManage(true);
		bean.setGroup(userGroup);
		bean.setSelf(getWebFlowSupport().getSessionUser(context));
		bean.setUsers(results);
		context.getFlashScope().put(RESULTS_BEAN, bean);
	}
	
	private void exposeSchoolResults(
			final RequestContext context, 
			final School school, 
			final List<UserRole> results)
	{
		primeResults(results);
		final SchoolMembersViewBean bean = new SchoolMembersViewBean();
		bean.setSchool(school);
		bean.setUsers(results);
		context.getFlashScope().put(RESULTS_BEAN, bean);
	}

	private void primeResults(final Collection<UserRole> results)
	{
		for (UserRole userRole : results)
		{
			userRole.getUser().getEmail();
		}
	}

	private WebFlowSupport getWebFlowSupport()
	{
		return this.webFlowSupport;
	}
}
