package net.ruready.business.user.exports;

import java.util.List;

import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupMembership;
import net.ruready.business.user.entity.UserGroupModerator;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.manager.AbstractUserGroupManager;
import net.ruready.common.rl.ResourceLocator;
/**
 * Default business delegate for User Group management
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
* @version Oct 15, 2007
*/
public abstract class DefaultUserGroupBD implements AbstractUserGroupBD
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractUserGroupManager manager;

	/**
	 * Use this specific resource locator.
	 */
	protected final ResourceLocator resourceLocator;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize this BD from fields.
	 * 
	 * @param manager
	 *            wrapped manager
	 * @param resourceLocator
	 *            using this specific resource locator
	 */
	protected DefaultUserGroupBD(final AbstractUserGroupManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	// ========================= IMPLEMENTATION: AbstractUserGroupBD =======

	public void createUserGroup(final UserGroup userGroup)
	{
		manager.createUserGroup(userGroup);
	}

	public void deleteUserGroup(final UserGroup userGroup)
	{
		manager.deleteUserGroup(userGroup);
	}

	public void forceDeleteUserGroup(final UserGroup userGroup)
	{
		manager.forceDeleteUserGroup(userGroup);
	}

	public List<UserGroup> findAllUserGroups()
	{
		return manager.findAllUserGroups();
	}
	
	public List<UserGroup> findActiveUserGroups()
	{
		return manager.findActiveUserGroups();
	}

	public List<UserGroup> findAllByPartialName(final String searchString)
	{
		return manager.findAllByPartialName(searchString);
	}
	
	public List<UserGroup> findActiveByPartialName(String searchString)
	{
		return manager.findActiveByPartialName(searchString);
	}

	public List<UserGroup> findAllByMatchingProperties(final UserGroup userGroup)
	{
		return manager.findAllByMatchingProperties(userGroup);
	}
	
	public UserGroup findUserGroupById(final Long id)
	{
		return manager.findUserGroupById(id);
	}

	
	public List<UserRole> findMembers(UserGroup userGroup)
	{
		return manager.findMembers(userGroup);
	}

	public List<UserGroup> findMemberships(User user)
	{
		return manager.findMemberships(user);
	}

	public UserGroupMembership findMembership(Long groupId, Long memberId)
	{
		return manager.findMembership(groupId, memberId);
	}

	public UserGroupMembership findMembership(UserGroup userGroup, User user)
	{
		return manager.findMembership(userGroup, user);
	}

	public List<UserGroup> findModeratedUserGroups(final User user)
	{
		return manager.findModeratedUserGroups(user);
	}
	
	public UserGroupModerator findModerator(Long groupId, Long moderatorId)
	{
		return manager.findModerator(groupId, moderatorId);
	}

	public UserGroupModerator findModerator(final UserGroupModerator moderator)
	{
		return manager.findModerator(moderator.getGroup().getId(), moderator.getModerator().getId());
	}

	public List<UserRole> findModerators(final UserGroup userGroup)
	{
		return manager.findModerators(userGroup);
	}

	public UserGroup readUserGroup(final Long id, final UserGroup userGroup)
	{
		return manager.readUserGroup(id, userGroup);
	}

	public void updateUserGroup(final UserGroup userGroup)
	{
		manager.updateUserGroup(userGroup);
	}

	public UserGroupMembership addMembership(final UserGroup userGroup, final User user)
	{
		return manager.addMembership(userGroup, user);
	}

	public UserGroupMembership addMembership(final UserGroup userGroup,
			final StudentRole studentRole)
	{
		return manager.addMembership(userGroup, studentRole);
	}

	public void addMembership(final UserGroupMembership membership)
	{
		manager.addMembership(membership);
	}

	public void removeMembership(final UserGroupMembership membership)
	{
		manager.removeMembership(membership);
	}

	public void updateMembership(final UserGroupMembership membership)
	{
		manager.updateMembership(membership);
	}

	public UserGroupModerator addModerator(final UserGroup userGroup, final User user)
	{
		return manager.addModerator(userGroup, user);
	}

	public UserGroupModerator addModerator(final UserGroup userGroup,
			final TeacherRole teacherRole)
	{
		return manager.addModerator(userGroup, teacherRole);
	}

	public void addModerator(final UserGroupModerator moderator)
	{
		manager.addModerator(moderator);
	}

	public void updateModerator(final UserGroupModerator moderator)
	{
		manager.updateModerator(moderator);
	}

	public void removeModerator(final UserGroupModerator moderator)
	{
		manager.removeModerator(moderator);
	}
}
