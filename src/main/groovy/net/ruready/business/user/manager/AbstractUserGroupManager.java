package net.ruready.business.user.manager;

import java.util.List;

import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupMembership;
import net.ruready.business.user.entity.UserGroupModerator;
import net.ruready.business.user.entity.UserRole;
import net.ruready.common.rl.BusinessManager;

/**
 * User Group business service. Provides basic searching and user group manipulation.
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
public interface AbstractUserGroupManager extends BusinessManager
{
	public void createUserGroup(final UserGroup userGroup);

	public UserGroup readUserGroup(final Long id, final UserGroup userGroup);

	public void updateUserGroup(final UserGroup userGroup);

	/**
	 * Marks a group as deleted, but keeps it around for reports
	 * 
	 * @param userGroup the user group to delete
	 */
	public void deleteUserGroup(final UserGroup userGroup);
	
	/**
	 * Deletes the user group from the database
	 * 
	 * NOTE: for most cases, deleteUserGroup should be used. This method is primarily
	 * around for when the group needs to be completely removed from the database, such as
	 * during unit tests.
	 * 
	 * @param userGroup
	 */
	public void forceDeleteUserGroup(final UserGroup userGroup);

	public List<UserGroup> findAllUserGroups();
	
	public List<UserGroup> findAllByPartialName(final String searchString);
	
	public List<UserGroup> findAllByMatchingProperties(final UserGroup userGroup);
	
	public List<UserGroup> findActiveUserGroups();
	
	public List<UserGroup> findActiveByPartialName(final String searchString);

	public UserGroup findUserGroupById(final Long id);
	
	public List<UserGroup> findMemberships(final User user);
	
	public List<UserRole> findMembers(final UserGroup userGroup);
	
	public UserGroupMembership findMembership(final Long groupId, final Long memberId);
	
	public UserGroupMembership findMembership(final UserGroup userGroup, final User user);
	
	public UserGroupMembership addMembership(final UserGroup userGroup, final User user);

	public UserGroupMembership addMembership(final UserGroup userGroup,	final StudentRole studentRole);

	public void addMembership(final UserGroupMembership membership);

	public void updateMembership(final UserGroupMembership membership);

	public void removeMembership(final UserGroupMembership membership);

	public List<UserGroup> findModeratedUserGroups(final User user);
	
	public List<UserRole> findModerators(final UserGroup userGroup);

	public UserGroupModerator findModerator(final Long groupId, final Long moderatorId);

	public UserGroupModerator addModerator(final UserGroup userGroup, final User user);

	public UserGroupModerator addModerator(final UserGroup userGroup, final TeacherRole teacherRole);

	public void addModerator(final UserGroupModerator moderator);

	public void updateModerator(final UserGroupModerator moderator);

	public void removeModerator(final UserGroupModerator moderator);
}
