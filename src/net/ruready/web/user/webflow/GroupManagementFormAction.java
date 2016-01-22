package net.ruready.web.user.webflow;

import java.util.ArrayList;
import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupModerator;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.exports.AbstractUserGroupBD;
import net.ruready.common.search.DefaultSearchCriteria;
import net.ruready.common.search.Logic;
import net.ruready.common.search.MatchType;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;
import net.ruready.common.search.SortType;
import net.ruready.web.user.beans.GroupManagementFormBean;
import net.ruready.web.user.beans.ResultsPagingBean;
import net.ruready.web.user.beans.SchoolSearchFormBean;
import net.ruready.web.user.beans.SimpleUserSearchBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class GroupManagementFormAction extends FormAction
{
	private static final String GROUP_ID_KEY = "groupId";
	private static final String USER_ID_KEY = "userId";
	private static final String RESULTS_BEAN = "results";
	private static final String MODERATOR_SUCCESS_MESSAGE = ".moderator.success.message";
	private static final String MODERATOR_SELF_SUCCESS_MESSAGE = ".moderator.self.success.message";
	private static final String SCHOOL_MEMBERSHIP_SUCCESS_MESSAGE = ".school.success.message";
	private static final String GROUP_DELETED_ERROR_MESSAGE = ".deleted.error.message";

	// hard-code utility class for now. May want to inject it later
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();

	public GroupManagementFormAction()
	{
		setFormObjectClass(GroupManagementFormBean.class);
	}

	public Event doCancel(final RequestContext context) throws Exception
	{
		getWebFlowSupport().addCancelMessage(context);
		return success();
	}

	public Event getDeleteFormObject(final RequestContext context) throws Exception
	{
		return getExistingFormObject(context);
	}

	public Event getEditFormObject(final RequestContext context) throws Exception
	{
		return getExistingFormObject(context);
	}

	/**
	 * Determines whether user edit rights for the User Group
	 * 
	 * This action must be executed after initializing the UserGroup for the
	 * flow object
	 * 
	 * @param context
	 *            the request context
	 * @return yes event if the user is the primary moderator, false otherwise
	 * @throws Exception
	 */
	public Event hasEditRights(final RequestContext context) throws Exception
	{
		final User user = getWebFlowSupport().getSessionUser(context);
		// User can now edit if they are an administrator
		if (user.hasRole(RoleType.ADMIN))
		{
			return yes();
		}
		final UserGroup userGroup = getForm(context).getUserGroup();
		final User primaryModerator = (userGroup.hasPrimaryModerator() ? userGroup
				.getPrimaryModerator().getModerator().getUser() : null);

		if (user.equals(primaryModerator))
		{
			return yes();
		}
		else
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return no();
		}
	}

	public Event getNewFormObject(final RequestContext context) throws Exception
	{
		final GroupManagementFormBean form = getForm(context);
		form.setUser(getWebFlowSupport().getSessionUser(context));
		form.setCourses(getAvailableCourses(context));
		form.setSchools(getAvailableSchools(context, getWebFlowSupport().getSessionUser(
				context)));
		form.getSchools();
		return success();
	}

	public Event getNewAdminFormObject(final RequestContext context) throws Exception
	{
		final GroupManagementFormBean form = getForm(context);
		form.setCourses(getAvailableCourses(context));
		form.setSchoolSearch(new SchoolSearchFormBean());
		form.setUserSearch(new SimpleUserSearchBean());
		return success();
	}

	public Event performUserSearch(final RequestContext context) throws Exception
	{
		final GroupManagementFormBean form = getForm(context);
		final List<User> results = getMatchingUsers(context, form.getUserGroup(), form
				.getUserSearch().getSearchString(), form.getUserSearch()
				.getSchoolFilter());
		context.getFlashScope().put(RESULTS_BEAN,
				getPagedUserResults(form.getUserSearch().getPagingBean(), results));
		return success();
	}

	public Event deleteGroup(final RequestContext context) throws Exception
	{
		final UserGroup deleteUserGroup = getForm(context).getUserGroup();
		getWebFlowSupport().getUserGroupBD(context).deleteUserGroup(deleteUserGroup);
		getWebFlowSupport().getStudentTranscriptBD(context).closeAllTranscriptsForGroup(deleteUserGroup);
		getWebFlowSupport().addSuccessMessage(context, deleteUserGroup.getName());
		return success();
	}

	public Event setGroupProperties(final RequestContext context) throws Exception
	{
		final GroupManagementFormBean form = getForm(context);
		final UserGroup userGroup = form.getUserGroup();
		userGroup.setCourse(getCourse(context, form));
		userGroup.setSchool(getSchool(context, form));
		return success();
	}

	public Event saveGroup(final RequestContext context) throws Exception
	{
		final GroupManagementFormBean form = getForm(context);
		final User user = refreshUser(context, form.getUser());
		final AbstractUserGroupBD userGroupBD = getWebFlowSupport().getUserGroupBD(
				context);
		final UserGroup userGroup = form.getUserGroup();
		// verify that this doesn't match any other existing group
		if (userGroupBD.findAllByMatchingProperties(userGroup).size() > 0)
		{
			return error();
		}
		userGroupBD.createUserGroup(userGroup);
		addModeratorToGroup(context, user, userGroup);
		addTeacherToSchool(context, user, userGroup.getSchool());
		getWebFlowSupport().addSuccessMessage(context, userGroup.getName());
		if (user.equals(getWebFlowSupport().getSessionUser(context)))
		{
			getWebFlowSupport().addMessage(
					context,
					getWebFlowSupport().getMessageKey(context,
							MODERATOR_SELF_SUCCESS_MESSAGE));
		}
		else
		{
			getWebFlowSupport()
					.addMessage(
							context,
							getWebFlowSupport().getMessageKey(context,
									MODERATOR_SUCCESS_MESSAGE), user.getEmail());
		}
		return success();
	}

	public Event updateGroup(final RequestContext context) throws Exception
	{
		final GroupManagementFormBean form = getForm(context);
		final UserGroup userGroup = form.getUserGroup();
		getWebFlowSupport().getUserGroupBD(context).updateUserGroup(userGroup);
		getWebFlowSupport().addSuccessMessage(context, userGroup.getName());
		return success();
	}

	public Event exposeErrors(final RequestContext context) throws Exception
	{
		getWebFlowSupport().exposeErrors(context, getFormErrors(context));
		return success();
	}

	public Event addEditConflictErrorMessages(final RequestContext context)
			throws Exception
	{
		getFormErrors(context)
				.reject(
						getWebFlowSupport().getMessageKey(context,
								".conflictInfo.error.message"));
		getFormErrors(context).reject(
				getWebFlowSupport().getMessageKey(context,
						".conflictPrompt.error.message"));
		return success();
	}

	public Event addConstraintErrorMessage(final RequestContext context) throws Exception
	{
		getFormErrors(context).reject(
				getWebFlowSupport().getMessageKey(context, ".constraint.error.message"));
		getFormErrors(context).reject(
				getWebFlowSupport().getMessageKey(context,
						".constraintPrompt.error.message"));
		return success();
	}

	public Event setUser(final RequestContext context) throws Exception
	{
		final GroupManagementFormBean form = getForm(context);
		final User user = getUserByUrlId(context);
		if (user == null)
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return error();
		}
		form.setUser(user);
		return success();
	}

	public Event setFirstPage(final RequestContext context) throws Exception
	{
		getResultsPagingBean(context).gotoFirstPage();
		return success();
	}

	public Event setPreviousPage(final RequestContext context) throws Exception
	{
		getResultsPagingBean(context).gotoPreviousPage();
		return success();
	}

	public Event setNextPage(final RequestContext context) throws Exception
	{
		getResultsPagingBean(context).gotoNextPage();
		return success();
	}

	public Event setLastPage(final RequestContext context) throws Exception
	{
		getResultsPagingBean(context).gotoLastPage();
		return success();
	}

	private Event getExistingFormObject(final RequestContext context) throws Exception
	{
		final GroupManagementFormBean form = getForm(context);
		final UserGroup userGroup = getGroupByInputId(context);
		if (userGroup == null)
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
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
		form.setUserGroup(userGroup);
		return success();
	}

	private User refreshUser(final RequestContext context, final User user)
			throws Exception
	{
		return getUserById(context, user.getId());
	}

	private List<User> getPagedUserResults(final ResultsPagingBean pagingBean,
			final List<User> results)
	{
		pagingBean.setTotalResults(results.size());
		final List<User> pageResults = new ArrayList<User>(pagingBean.getResultsPerPage());
		for (int i = pagingBean.getCurrentPageFirstResultZeroBased(); i < pagingBean
				.getCurrentPageLastResult(); i++)
		{
			pageResults.add(results.get(i));
		}
		return pageResults;
	}

	private List<User> getMatchingUsers(final RequestContext context,
			final UserGroup userGroup, final String searchString,
			final Boolean filterForSchools)
	{
		final List<User> matches = getAllMatchingUsers(context, searchString);
		if (filterForSchools)
		{
			return filterUsersForSchoolMemberships(context, userGroup, matches);
		}
		return matches;
	}

	/**
	 * @param context
	 * @param userGroup
	 * @param matches
	 * @return
	 */
	private List<User> filterUsersForSchoolMemberships(final RequestContext context,
			final UserGroup userGroup, final List<User> matches)
	{
		final School school = userGroup.getSchool();
		final List<User> filteredMatches = new ArrayList<User>();
		for (User match : matches)
		{
			final TeacherRole teacherRole = (TeacherRole) match.getRole(RoleType.TEACHER);
			if (teacherRole.hasActiveMembershipToSchool(school))
			{
				filteredMatches.add(match);
			}
		}
		return filteredMatches;
	}

	private List<User> getAllMatchingUsers(final RequestContext context,
			final String searchString)
	{
		final SearchCriteria criteria = new DefaultSearchCriteria(Logic.AND
				.createCriterion());

		criteria.add(SearchCriterionFactory.createStringExpression(SearchType.ILIKE,
				User.EMAIL, searchString, MatchType.STARTS_WITH));
		// search for all accounts that are not students (teachers and above)
		criteria.add(SearchCriterionFactory.<RoleType> createSimpleExpression(
				SearchType.NE, RoleType.class, "highestRole", RoleType.STUDENT));
		criteria.addSortCriterion(SearchCriterionFactory.createSortCriterion(User.EMAIL,
				SortType.ASCENDING));
		return getWebFlowSupport().getUserBD(context).findByCriteria(criteria);
	}

	private User getUserByUrlId(final RequestContext context) throws Exception
	{
		final Long userId = context.getRequestParameters().getLong(USER_ID_KEY);
		if (userId == null)
			return null;
		return getUserById(context, userId);
	}

	private User getUserById(final RequestContext context, final Long id)
			throws Exception
	{
		return getWebFlowSupport().getUserBD(context).findById(id);
	}

	private UserGroup getGroupByInputId(final RequestContext context) throws Exception
	{
		final Long groupId = getIdFromInput(context);
		if (groupId == null)
			return null;
		return getGroupById(context, groupId);
	}

	private Long getIdFromInput(final RequestContext context) throws Exception
	{
		return context.getRequestScope().getLong(GROUP_ID_KEY);
	}

	private UserGroup getGroupById(final RequestContext context, final Long id)
			throws Exception
	{
		return getWebFlowSupport().getUserGroupBD(context).findUserGroupById(id);
	}

	private UserGroupModerator addModeratorToGroup(final RequestContext context,
			final User user, final UserGroup userGroup)
	{
		final UserGroupModerator moderator = new UserGroupModerator(user, userGroup, true);
		getWebFlowSupport().getUserGroupBD(context).addModerator(moderator);
		return moderator;
	}

	private void addTeacherToSchool(final RequestContext context, final User user,
			final School school) throws Exception
	{
		final TeacherRole teacherRole = (TeacherRole) user.getRole(RoleType.TEACHER);
		final School testSchool = getWebFlowSupport().getWorldBD(context).findSchoolById(
				school.getId());
		if (!teacherRole.hasActiveMembershipToSchool(testSchool))
		{
			final TeacherSchoolMembership membership = new TeacherSchoolMembership(
					teacherRole, testSchool, ActiveStatus.ACTIVE);
			getWebFlowSupport().getUserBD(context).addSchoolMembership(membership);
			getWebFlowSupport().addMessage(
					context,
					getWebFlowSupport().getMessageKey(context,
							SCHOOL_MEMBERSHIP_SUCCESS_MESSAGE), user.getEmail(),
					school.getName());
		}
	}

	private GroupManagementFormBean getForm(final RequestContext context)
			throws Exception
	{
		return (GroupManagementFormBean) getFormObject(context);
	}

	private Course getCourse(final RequestContext context,
			final GroupManagementFormBean form)
	{
		return getWebFlowSupport().getCourseBD(context).findById(Course.class,
				form.getCourseId());
	}

	private School getSchool(final RequestContext context,
			final GroupManagementFormBean form)
	{
		return getWebFlowSupport().getWorldBD(context).findSchoolById(form.getSchoolId());
	}

	private List<Course> getAvailableCourses(final RequestContext context)
	{
		return getWebFlowSupport().getCourseBD(context).findAllNonDeleted(Course.class,
				ItemType.CATALOG);
	}

	private List<School> getAvailableSchools(final RequestContext context, final User user)
	{
		return getWebFlowSupport().getUserBD(context).findTeacherSchoolMemberships(user,
				ActiveStatus.ACTIVE);
	}

	private ResultsPagingBean getResultsPagingBean(final RequestContext context)
			throws Exception
	{
		return getForm(context).getUserSearch().getPagingBean();
	}

	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}
}
