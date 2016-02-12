package net.ruready.web.user.webflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.TranscriptActiveStatus;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupMembership;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.entity.property.UserField;
import net.ruready.business.user.exports.AbstractUserGroupBD;
import net.ruready.common.search.DefaultSearchCriteria;
import net.ruready.common.search.Logic;
import net.ruready.common.search.MatchType;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;
import net.ruready.common.search.SortType;
import net.ruready.web.user.beans.GroupSubscriptionFormBean;
import net.ruready.web.user.beans.ResultsPagingBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class GroupSubscriptionFormAction extends FormAction
{	
	//private static final String UNSUBSCRIBE_CONFLICT_SUCCESS_MESSAGE = ".unsubscribeConflict.success.message";
	private static final String GROUP_DELETED_ERROR_MESSAGE = ".deleted.error.message";
	
	private static final String GROUP_ID_KEY = "groupId";
	private static final String USER_ID_KEY = "userId";
	private static final String RESULTS_BEAN = "results";
	
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();
	
	public GroupSubscriptionFormAction()
	{
		setFormObjectClass(GroupSubscriptionFormBean.class);
	}
	
	/**
	 * Called to assign the logged-in user as the target of the subscribe/un-subscribe process
	 * @param context the request context
	 * @return success event
	 * @throws Exception
	 */
	public Event initializeSelfManagement(final RequestContext context) throws Exception
	{
		final GroupSubscriptionFormBean form = getForm(context);
		form.setMatchingUser(getWebFlowSupport().getSessionUser(context));
		return success();
	}
	
	public Event hasSubscriptionRights(final RequestContext context) throws Exception
	{
		final User user = getForm(context).getMatchingUser();
		if (user.getName().hasName())
		{
			return yes();
		}
		else
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return no();
		}
	}
	
	public Event lookupGroupById(final RequestContext context) throws Exception
	{
		final GroupSubscriptionFormBean form = getForm(context);
		final UserGroup userGroup = getGroupByUrlId(context);
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
		form.setMatchingGroup(userGroup);
		return success();
	}
	
	public Event performGroupSearch(final RequestContext context) throws Exception
	{
		final GroupSubscriptionFormBean form = getForm(context);
		final User user = refreshUser(context, form.getMatchingUser());
		final List<UserGroup> results = 
			getMatchingUserGroups(
					context, 
					user, 
					form.getSearch().getSearchString(), 
					form.getSearch().getSchoolFilter());
		context.getFlashScope().put(
				RESULTS_BEAN, 
				getPagedUserGroupResults(form.getSearch().getPagingBean(), results));
		return success();
	}
	
	public Event performUserSearch(final RequestContext context) throws Exception
	{
		final GroupSubscriptionFormBean form = getForm(context);
		final UserGroup userGroup = refreshUserGroup(context, form.getMatchingGroup());
		final List<User> results = 
			getMatchingUsers(
					context, 
					userGroup, 
					form.getSearch().getSearchString(), 
					form.getSearch().getSchoolFilter());
		context.getFlashScope().put(
				RESULTS_BEAN, 
				getPagedUserResults(form.getSearch().getPagingBean(), results));
		return success();
	}
	
	public Event setUser(final RequestContext context) throws Exception
	{
		final GroupSubscriptionFormBean form = getForm(context);
		final User user = getUserByUrlId(context);
		if (user == null)
		{
			getFormErrors(context).reject(getWebFlowSupport().getErrorMessage(context));
			return error();
		}
		form.setMatchingUser(user);
		return success();
	}
	
	public Event setUserGroup(final RequestContext context) throws Exception
	{
		final GroupSubscriptionFormBean form = getForm(context);
		final UserGroup userGroup = getGroupByUrlId(context);
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
		form.setMatchingGroup(userGroup);
		return success();
	}
	
	/**
	 * Subscribe user to specified group
	 * 
	 * This method assumes that the user has been set on the form object previously,
	 * and that this method is being called as the result of selecting a group via a
	 * link.
	 * 
	 * @param context the request context
	 * @return 
	 * @throws Exception
	 */
	public Event subscribe(final RequestContext context) throws Exception
	{
		final GroupSubscriptionFormBean form = getForm(context);
		final User user = refreshUser(context, form.getMatchingUser());
		final UserGroup userGroup = refreshUserGroup(context, form.getMatchingGroup());	
		// verify that user is not already in a group with the same course
		/*final List<UserGroup> memberships = getWebFlowSupport().getUserGroupBD(context).findMemberships(user);
		for (UserGroup membership : memberships)
		{
			if (membership.getCourse().equals(userGroup.getCourse()))
			{
				form.setConflictingGroup(membership);
				return result("conflictConfirm");
			}
		}*/
		// add the subscription
		subscribeUser(context, user, userGroup);
		return success();
	}
	
	/*public Event subscribeRemoveConflict(final RequestContext context) throws Exception
	{
		final GroupSubscriptionFormBean form = getForm(context);
		final User user = refreshUser(context, form.getMatchingUser());
		// there is not a clean way to reattach object so have to look it up again.
		final UserGroup newUserGroup = refreshUserGroup(context, form.getMatchingGroup());
		// remove the existing subscription
		unsubscribeUser(context, user, form.getConflictingGroup());
		getWebFlowSupport().addMessage(context, 
				getWebFlowSupport().getMessageKey(context, UNSUBSCRIBE_CONFLICT_SUCCESS_MESSAGE), 
				form.getConflictingGroup().getName(),
				form.getMatchingUser().getEmail());
		// add the new subscription
		subscribeUser(context, user, newUserGroup);
		return success();
	}*/
	
	public Event unsubscribe(final RequestContext context) throws Exception
	{
		final GroupSubscriptionFormBean form = getForm(context);
		final User user = refreshUser(context, form.getMatchingUser());
		unsubscribeUser(context, user, form.getMatchingGroup());
		getWebFlowSupport().addSuccessMessage(context, form.getMatchingGroup().getName());
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
	
	private void subscribeUser(final RequestContext context, final User user, final UserGroup userGroup) throws Exception
	{
		final UserGroupMembership membership = new UserGroupMembership(user, userGroup);
		getWebFlowSupport().getUserGroupBD(context).addMembership(membership);
		createAndOpenTranscript(context, user, userGroup);
		getWebFlowSupport().addSuccessMessage(context, userGroup.getName(), user.getEmail());
	}
	
	private void unsubscribeUser(final RequestContext context, final User user, final UserGroup userGroup) throws Exception
	{
		final AbstractUserGroupBD userGroupBD = getWebFlowSupport().getUserGroupBD(context);
		final UserGroupMembership membership = userGroupBD.findMembership(userGroup, user);
		userGroupBD.removeMembership(membership);
		closeTranscript(context, user, userGroup);
	}
	
	/**
	 * Creates and opens a transcript for this group and user
	 * 
	 * If a transcript already exists, it will simply be re-opened.
	 * 
	 * @param context the request context
	 * @param user
	 * @param userGroup
	 * @throws Exception
	 */
	private void createAndOpenTranscript(final RequestContext context, final User user, final UserGroup userGroup) throws Exception
	{
		final AbstractStudentTranscriptBD transcriptBD = getWebFlowSupport().getStudentTranscriptBD(context);
		final StudentGroupTranscript transcript = transcriptBD.createGroupTranscriptIfNotExists(user, userGroup);
		if (transcript.getActiveStatus() == TranscriptActiveStatus.CLOSED)
		{
			transcript.openTranscript();
			transcriptBD.updateTranscript(transcript);
		}
	}
	
	/**
	 * Closes a user's transcript to this group
	 * @param context the request context
	 * @param user
	 * @param userGroup
	 * @throws Exception
	 */
	private void closeTranscript(final RequestContext context, final User user, final UserGroup userGroup) throws Exception
	{
		final AbstractStudentTranscriptBD transcriptBD = getWebFlowSupport().getStudentTranscriptBD(context);
		final StudentGroupTranscript transcript = transcriptBD.findGroupTranscript(user, userGroup);
		transcript.closeTranscript();
		transcriptBD.updateTranscript(transcript);
	}
	
	private UserGroup getGroupByUrlId(final RequestContext context) throws Exception
	{
		final Long groupId = context.getRequestParameters().getLong(GROUP_ID_KEY);
		if (groupId == null) return null;
		return getGroupById(context, groupId);
	}
	
	private User getUserByUrlId(final RequestContext context) throws Exception
	{
		final Long userId = context.getRequestParameters().getLong(USER_ID_KEY);
		if (userId == null) return null;
		return getUserById(context, userId);
	}
	
	private UserGroup refreshUserGroup(final RequestContext context, final UserGroup userGroup) throws Exception
	{
		return getGroupById(context, userGroup.getId());
	}
	
	private User refreshUser(final RequestContext context, final User user) throws Exception
	{
		return getUserById(context, user.getId());
	}
	
	private User getUserById(final RequestContext context, final Long id) throws Exception
	{
		return getWebFlowSupport().getUserBD(context).findById(id);
	}
	
	private UserGroup getGroupById(final RequestContext context, final Long id) throws Exception
	{
		return getWebFlowSupport().getUserGroupBD(context).findUserGroupById(id);
	}
	
	private List<User> getPagedUserResults(final ResultsPagingBean pagingBean, final List<User> results)
	{
		pagingBean.setTotalResults(results.size());
		final List<User> pageResults = new ArrayList<User>(pagingBean.getResultsPerPage());
		for (int i = pagingBean.getCurrentPageFirstResultZeroBased(); i < pagingBean.getCurrentPageLastResult(); i++)
		{
			pageResults.add(results.get(i));
		}
		return pageResults;
	}
	
	private List<UserGroup> getPagedUserGroupResults(final ResultsPagingBean pagingBean, final List<UserGroup> results)
	{
		pagingBean.setTotalResults(results.size());
		final List<UserGroup> pageResults = new ArrayList<UserGroup>();
		for (int i = pagingBean.getCurrentPageFirstResultZeroBased(); i < pagingBean.getCurrentPageLastResult(); i++)
		{
			pageResults.add(results.get(i));
		}
		return pageResults;
	}
	
	private List<User> getMatchingUsers(final RequestContext context, final UserGroup userGroup, final String searchString, final Boolean filterForSchools)
	{
		final List<User> matches = getAllMatchingUsers(context, searchString);
		filterMembershipsFromUsers(context, userGroup, matches);
		if (filterForSchools)
		{
			return filterUsersForSchoolMemberships(context, userGroup, matches);
		}
		return matches;
	}
	
	private List<UserGroup> getMatchingUserGroups(final RequestContext context, final User user, final String searchString, final Boolean filterForSchools)
	{
		final List<UserGroup> matches = getAllMatchingUserGroups(context, searchString);
		filterMembershipsFromGroups(context, user, matches);
		if (filterForSchools)
		{
			return filterGroupsForSchoolMemberships(context, user, matches);
		}
		return matches;
	}
	
	private List<User> getAllMatchingUsers(final RequestContext context, final String searchString)
	{
		final SearchCriteria criteria = new DefaultSearchCriteria(Logic.AND.createCriterion());
		criteria.add(
				SearchCriterionFactory.createStringExpression(
						SearchType.ILIKE,
						User.EMAIL, 
						searchString, 
						MatchType.STARTS_WITH));
		criteria.add(
				SearchCriterionFactory.createBinaryCriterion(SearchType.OR,
						SearchCriterionFactory.createNoArgExpression(SearchType.IS_NOT_NULL, String.class, UserField.LAST_NAME.getName()),
						SearchCriterionFactory.createNoArgExpression(SearchType.IS_NOT_NULL, String.class, UserField.FIRST_NAME.getName())));
		criteria.addSortCriterion(
				SearchCriterionFactory.createSortCriterion(
						User.EMAIL, 
						SortType.ASCENDING));
		return getWebFlowSupport().getUserBD(context).findByCriteria(criteria);
	}
	
	private List<UserGroup> getAllMatchingUserGroups(final RequestContext context, final String searchString)
	{
		return getWebFlowSupport().getUserGroupBD(context).findActiveByPartialName(searchString); 
	}
	
	/**
	 * @param context
	 * @param userGroup
	 * @param matches
	 * @return
	 */
	private List<User> filterMembershipsFromUsers(final RequestContext context, final UserGroup userGroup, final List<User> matches)
	{
		final Set<UserGroupMembership> memberships = userGroup.getMembership();
		for (UserGroupMembership membership : memberships)
		{
			if (matches.contains(membership.getMember().getUser()))
			{
				matches.remove(membership.getMember().getUser());
			}
		}
		return matches;
	}
	
	private List<UserGroup> filterMembershipsFromGroups(final RequestContext context, final User user, final List<UserGroup> matches)
	{
		final List<UserGroup> groups = getWebFlowSupport().getUserGroupBD(context).findMemberships(user);
		for (UserGroup group : groups)
		{
			if (matches.contains(group))
			{
				matches.remove(group);
			}
		}
		return matches;
	}
	
	/**
	 * @param context
	 * @param userGroup
	 * @param matches
	 * @return
	 */
	private List<User> filterUsersForSchoolMemberships(final RequestContext context, final UserGroup userGroup, final List<User> matches)
	{
		final School school = userGroup.getSchool();
		final List<User> filteredMatches = new ArrayList<User>();
		for (User match : matches)
		{
			if (((StudentRole)match.getRole(RoleType.STUDENT)).getSchools().contains(school))
			{
				filteredMatches.add(match);
			}
		}
		return filteredMatches;
	}
	
	/**
	 * @param context
	 * @param user
	 * @param matches
	 * @return
	 */
	private List<UserGroup> filterGroupsForSchoolMemberships(
			final RequestContext context, final User user, final List<UserGroup> matches)
	{
		final Collection<School> schools = ((StudentRole)user.getRole(RoleType.STUDENT)).getSchools();
		final List<UserGroup> filteredMatches = new ArrayList<UserGroup>();
		for (UserGroup match : matches)
		{
			if (schools.contains(match.getSchool()))
			{
				filteredMatches.add(match);
			}
		}
		return filteredMatches;
	}
	
	private GroupSubscriptionFormBean getForm(final RequestContext context) throws Exception
	{
		return (GroupSubscriptionFormBean) getFormObject(context);
	}
	
	private ResultsPagingBean getResultsPagingBean(final RequestContext context) throws Exception
	{
		return getForm(context).getSearch().getPagingBean();
	}
	
	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}
}
