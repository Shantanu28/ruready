package net.ruready.web.user.webflow;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.search.DefaultSearchCriteria;
import net.ruready.common.search.Logic;
import net.ruready.common.search.MatchType;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;
import net.ruready.common.search.SortType;
import net.ruready.web.user.beans.ResultsPagingBean;
import net.ruready.web.user.beans.SchoolFormBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class StudentSchoolMembershipsFormAction extends FormAction
{
	private static final String SCHOOL_ID_KEY = "schoolId";
	private static final String USER_ID_KEY = "userId";
	private static final String RESULTS_BEAN = "results";

	private final WebFlowSupport webFlowSupport = new WebFlowSupport();

	public StudentSchoolMembershipsFormAction()
	{
		setFormObjectClass(SchoolFormBean.class);
	}

	public Event setUserToSelf(final RequestContext context) throws Exception
	{
		getForm(context).setMatchingUser(getWebFlowSupport().getSessionUser(context));
		return success();
	}

	public Event setUser(final RequestContext context) throws Exception
	{
		final SchoolFormBean form = getForm(context);
		final User user = getUserByUrlId(context);
		if (user == null)
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return error();
		}
		form.setMatchingUser(user);
		return success();
	}

	public Event setSchool(final RequestContext context) throws Exception
	{
		final SchoolFormBean form = getForm(context);
		final School school = getSchoolByUrlId(context);
		if (school == null)
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return error();
		}
		form.setMatchingSchool(school);
		return success();
	}

	public Event performSearch(final RequestContext context) throws Exception
	{
		final SchoolFormBean form = getForm(context);
		final User user = refreshUser(context, form.getMatchingUser());
		if (form.getSearchString().length() >= form.getMinimumSearchStringLength())
		{
			final List<School> results = getMatchingSchools(context, user, form
					.getSearchString());
			context.getFlashScope().put(RESULTS_BEAN,
					getPagedResults(form.getPagingBean(), results));
		}
		return success();
	}

	public Event performUserSearch(final RequestContext context) throws Exception
	{
		final SchoolFormBean form = getForm(context);
		final List<User> results = getMatchingUsers(context, form.getMatchingSchool(),
				form.getSearchString());
		context.getFlashScope().put(RESULTS_BEAN,
				getPagedUserResults(form.getPagingBean(), results));
		return success();
	}

	public Event add(final RequestContext context) throws Exception
	{
		final SchoolFormBean form = getForm(context);
		final User user = refreshUser(context, form.getMatchingUser());
		final School school = refreshSchool(context, form.getMatchingSchool());
		addSchool(context, user, school);
		return success();
	}

	public Event delete(final RequestContext context) throws Exception
	{
		final SchoolFormBean form = getForm(context);
		final User user = refreshUser(context, form.getMatchingUser());
		final School school = refreshSchool(context, form.getMatchingSchool());
		removeSchool(context, user, school);
		return success();
	}

	public Event doCancel(final RequestContext context) throws Exception
	{
		getWebFlowSupport().addCancelMessage(context);
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

	public Event exposeErrors(final RequestContext context) throws Exception
	{
		getWebFlowSupport().exposeErrors(context, getFormErrors(context));
		return success();
	}

	private void addSchool(final RequestContext context, final User user,
			final School school) throws Exception
	{
		final StudentRole student = getStudentFromUser(user);
		student.addSchool(school);
		getWebFlowSupport().getUserBD(context).updateUser(user);
		getWebFlowSupport().addSuccessMessage(context, school.getName(), user.getEmail());
	}

	private void removeSchool(final RequestContext context, final User user,
			final School school) throws Exception
	{
		final StudentRole student = getStudentFromUser(user);
		student.removeSchool(school);
		getWebFlowSupport().getUserBD(context).updateUser(user);
		getWebFlowSupport().addSuccessMessage(context, school.getName(), user.getEmail());
	}

	private User refreshUser(final RequestContext context, final User user)
			throws Exception
	{
		return getUserById(context, user.getId());
	}

	private School refreshSchool(final RequestContext context, final School school)
			throws Exception
	{
		return getSchoolById(context, school.getId());
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

	private School getSchoolByUrlId(final RequestContext context) throws Exception
	{
		final Long schoolId = getIdFromUrl(context);
		if (schoolId == null)
			return null;
		return getSchoolById(context, schoolId);
	}

	private Long getIdFromUrl(final RequestContext context) throws Exception
	{
		return context.getRequestParameters().getLong(SCHOOL_ID_KEY);
	}

	private School getSchoolById(final RequestContext context, final Long id)
			throws Exception
	{
		return getWebFlowSupport().getWorldBD(context).findSchoolById(id);
	}

	private List<School> getMatchingSchools(final RequestContext context,
			final User user, final String searchString)
	{
		final List<School> matches = getWebFlowSupport().getWorldBD(context)
				.findSchoolByPartialName(searchString);
		final StudentRole student = getStudentFromUser(user);
		for (School school : student.getSchools())
		{
			if (matches.contains(school))
			{
				matches.remove(school);
			}
		}
		return matches;
	}

	private List<School> getPagedResults(final ResultsPagingBean pagingBean,
			final List<School> results)
	{
		pagingBean.setTotalResults(results.size());
		final List<School> pageResults = new ArrayList<School>();
		for (int i = pagingBean.getCurrentPageFirstResultZeroBased(); i < pagingBean
				.getCurrentPageLastResult(); i++)
		{
			pageResults.add(results.get(i));
		}
		return pageResults;
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
			final School school, final String searchString)
	{
		final List<User> matches = getAllMatchingUsers(context, searchString);
		return filterUsersForSchoolMemberships(context, school, matches);
	}

	/**
	 * @param context
	 * @param school
	 * @param matches
	 * @return
	 */
	private List<User> filterUsersForSchoolMemberships(final RequestContext context,
			final School school, final List<User> matches)
	{
		final List<User> filteredMatches = new ArrayList<User>();
		for (User match : matches)
		{
			final StudentRole studentRole = (StudentRole) match.getRole(RoleType.STUDENT);
			if (!studentRole.getSchools().contains(school))
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
		criteria.add(SearchCriterionFactory.<RoleType> createCollectionExpression(
				SearchType.IN, RoleType.class, "highestRole", EnumSet
						.allOf(RoleType.class)));
		criteria.addSortCriterion(SearchCriterionFactory.createSortCriterion(User.EMAIL,
				SortType.ASCENDING));
		return getWebFlowSupport().getUserBD(context).findByCriteria(criteria);
	}

	private StudentRole getStudentFromUser(final User user)
	{
		return (StudentRole) user.getRole(RoleType.STUDENT);
	}

	private SchoolFormBean getForm(final RequestContext context) throws Exception
	{
		return (SchoolFormBean) getFormObject(context);
	}

	private ResultsPagingBean getResultsPagingBean(final RequestContext context)
			throws Exception
	{
		return getForm(context).getPagingBean();
	}

	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}
}
