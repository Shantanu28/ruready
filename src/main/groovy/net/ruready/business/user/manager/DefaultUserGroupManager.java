package net.ruready.business.user.manager;

import static net.ruready.business.user.manager.Messages.*;
import static org.apache.commons.lang.Validate.notNull;

import java.util.ArrayList;
import java.util.List;

import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupMembership;
import net.ruready.business.user.entity.UserGroupModerator;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.UserGroupModerator.Id;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.eis.user.UserGroupDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides the implementation for the user group services interface.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Jeremy Lund
 * @version Sep 25, 2007
 */
public class DefaultUserGroupManager implements AbstractUserGroupManager
{

	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultUserGroupManager.class);

	private static final String ENTITY_NAME = "User Group";

	private static final String ID_NOT_NULL = nullMessage("id");

	private static final String MEMBERSHIP_NOT_NULL = nullMessage("membership");

	private static final String MODERATOR_NOT_NULL = nullMessage("moderator");

	private static final String USER_GROUP_NOT_NULL = nullMessage("userGroup");

	private static final String USER_NOT_NULL = nullMessage("user");

	// ========================= FIELDS ====================================

	/**
	 * DAO factory, obtained from the resource locator.
	 */
	protected final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects.
	 */
	protected final ApplicationContext context;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a user group service that will use a resource locator and a DAO factory to
	 * read/write users to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            application context
	 */
	public DefaultUserGroupManager(final ResourceLocator resourceLocator,
			final ApplicationContext context)
	{
		this.eisManager = resourceLocator.getDAOFactory();
		this.context = context;
	}

	// ========================= IMPLEMENTATION: AbstractUGManager =====

	public void createUserGroup(final UserGroup userGroup)
	{
		notNull(userGroup, USER_GROUP_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			createMessage(ENTITY_NAME, userGroup.getName());
		}
		getUserGroupDAO().create(userGroup);
	}

	public UserGroup readUserGroup(final Long id, final UserGroup userGroup)
	{
		notNull(id, ID_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(readMessage(ENTITY_NAME, id, (userGroup == null ? "NULL"
					: userGroup.getName())));
		}
		getUserGroupDAO().readInto(id, userGroup);
		return userGroup;
	}

	public void updateUserGroup(final UserGroup userGroup)
	{
		notNull(userGroup, USER_GROUP_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(updateMessage(ENTITY_NAME, userGroup.getName()));
		}
		getUserGroupDAO().merge(userGroup);
	}
	
	public void deleteUserGroup(final UserGroup userGroup)
	{
		notNull(userGroup, USER_GROUP_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(deleteMessage(ENTITY_NAME, userGroup.getName()));
		}
		final UserGroup deleteGroup = getUserGroupDAO().read(userGroup.getId());
		deleteGroup.delete();
		getUserGroupDAO().update(deleteGroup);
		
		removeAllMembershipsFromGroup(deleteGroup);
		removeAllModeratorsFromGroup(deleteGroup);
	}

	public void forceDeleteUserGroup(final UserGroup userGroup)
	{
		notNull(userGroup, USER_GROUP_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(deleteMessage(ENTITY_NAME, userGroup.getName()));
		}
		getUserGroupDAO().delete(userGroup);
	}

	public List<UserGroup> findAllUserGroups()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(findAllMessage(ENTITY_NAME));
		}
		return getUserGroupDAO().findAll();
	}

	public List<UserGroup> findAllByPartialName(final String searchString)
	{
		return getUserGroupDAO().findByPartialName(searchString);
	}

	public List<UserGroup> findAllByMatchingProperties(final UserGroup userGroup)
	{
		return getUserGroupDAO().findByMatchingProperties(userGroup);
	}

	public List<UserGroup> findActiveUserGroups()
	{
		return getUserGroupDAO().findActive();
	}

	public List<UserGroup> findActiveByPartialName(String searchString)
	{
		return getUserGroupDAO().findActiveByPartialName(searchString);
	}

	public UserGroup findUserGroupById(final Long id)
	{
		notNull(id, ID_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(findByIdMessage(ENTITY_NAME, id));
		}
		return getUserGroupDAO().read(id);
	}

	public List<UserGroup> findModeratedUserGroups(final User user)
	{
		notNull(user, USER_NOT_NULL);
		return getUserGroupDAO().getModeratedGroupsByUser(user);
	}

	public List<UserRole> findModerators(UserGroup userGroup)
	{
		notNull(userGroup, USER_GROUP_NOT_NULL);
		return getUserGroupDAO().getModeratorsByUserGroup(userGroup);
	}
	
	public UserGroupModerator findModerator(final Long groupId, final Long moderatorId)
	{
		notNull(groupId, nullMessage("groupId"));
		notNull(moderatorId, nullMessage("moderatorId"));
		return getModeratorDAO().read(new Id(moderatorId,groupId));
	}

	public List<UserRole> findMembers(UserGroup userGroup)
	{
		notNull(userGroup, USER_GROUP_NOT_NULL);
		return getUserGroupDAO().getMembershipsByUserGroup(userGroup);
	}

	public List<UserGroup> findMemberships(User user)
	{
		notNull(user, USER_NOT_NULL);
		return getUserGroupDAO().getMembershipsByUser(user);
	}

	public UserGroupMembership findMembership(final Long groupId, final Long memberId)
	{
		return getMembershipDAO().read(new UserGroupMembership.Id(memberId, groupId));
	}

	public UserGroupMembership findMembership(final UserGroup userGroup, final User user)
	{
		return findMembership(userGroup.getId(), user.getRole(RoleType.STUDENT).getId());
	}

	public UserGroupMembership addMembership(final UserGroup userGroup, final User user)
	{
		final UserGroupMembership membership = new UserGroupMembership(user, userGroup);
		addMembership(membership);
		return membership;
	}

	public UserGroupMembership addMembership(final UserGroup userGroup,
			final StudentRole studentRole)
	{
		final UserGroupMembership membership = new UserGroupMembership(studentRole,
				userGroup);
		addMembership(membership);
		return membership;
	}

	public void addMembership(final UserGroupMembership membership)
	{
		notNull(membership, MEMBERSHIP_NOT_NULL);
		getMembershipDAO().create(membership);
	}

	public void updateMembership(final UserGroupMembership membership)
	{
		notNull(membership, MEMBERSHIP_NOT_NULL);
		getMembershipDAO().update(membership);
	}

	public void removeMembership(final UserGroupMembership membership)
	{
		notNull(membership, MEMBERSHIP_NOT_NULL);

		if (membership.getGroup() != null)
		{
			membership.getGroup().getMembership().remove(membership);
		}
		if (membership.getMember() != null)
		{
			membership.getMember().getGroups().remove(membership);
		}
		getMembershipDAO().delete(membership);
	}

	public UserGroupModerator addModerator(final UserGroup userGroup, final User user)
	{
		final UserGroupModerator moderator = new UserGroupModerator(user, userGroup);
		addModerator(moderator);
		return moderator;
	}

	public UserGroupModerator addModerator(final UserGroup userGroup,
			final TeacherRole teacherRole)
	{
		final UserGroupModerator moderator = new UserGroupModerator(teacherRole,
				userGroup);
		addModerator(moderator);
		return moderator;
	}

	public void addModerator(final UserGroupModerator moderator)
	{
		notNull(moderator, MODERATOR_NOT_NULL);
		getModeratorDAO().create(moderator);
	}

	public void updateModerator(final UserGroupModerator moderator)
	{
		notNull(moderator, MODERATOR_NOT_NULL);
		getModeratorDAO().update(moderator);
	}

	public void removeModerator(final UserGroupModerator moderator)
	{
		notNull(moderator, MODERATOR_NOT_NULL);

		if (moderator.getGroup() != null)
		{
			moderator.getGroup().getModerators().remove(moderator);
		}
		if (moderator.getModerator() != null)
		{
			moderator.getModerator().getGroups().remove(moderator);
		}
		getModeratorDAO().delete(moderator);
	}
	
	private void removeAllMembershipsFromGroup(final UserGroup userGroup)
	{
		final List<UserGroupMembership> memberships = new ArrayList<UserGroupMembership>(userGroup.getMembership().size());
		memberships.addAll(userGroup.getMembership());
		for (UserGroupMembership membership : memberships)
		{
			removeMembership(membership);
		}
	}
	
	private void removeAllModeratorsFromGroup(final UserGroup userGroup)
	{
		final List<UserGroupModerator> moderators = new ArrayList<UserGroupModerator>(userGroup.getModerators().size());
		moderators.addAll(userGroup.getModerators());
		for (UserGroupModerator moderator : moderators)
		{
			removeModerator(moderator);
		}
	}

	private UserGroupDAO getUserGroupDAO()
	{
		return (UserGroupDAO) getDAOFactory().getDAO(UserGroup.class, context);
	}

	private DAO<UserGroupMembership, UserGroupMembership.Id> getMembershipDAO()
	{
		return getDAOFactory().getDAO(UserGroupMembership.class, context);
	}

	private DAO<UserGroupModerator, UserGroupModerator.Id> getModeratorDAO()
	{
		return getDAOFactory().getDAO(UserGroupModerator.class, context);
	}

	private AbstractEISManager getDAOFactory()
	{
		return this.eisManager;
	}
}
