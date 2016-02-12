package test.ruready.business;

import java.util.List;

import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupMembership;
import net.ruready.business.user.entity.UserGroupModerator;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.business.user.exports.AbstractUserGroupBD;
import net.ruready.common.discrete.Gender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.ruready.imports.StandAloneUserBD;
import test.ruready.imports.StandAloneUserGroupBD;
import test.ruready.rl.StandAloneApplicationContext;

/**
 * A utility class for user management test cases.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Jeremy Lund
 * @version Oct 3, 2007
 */
public class UserUtil
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(UserUtil.class);

	// ========================= FIELDS ====================================

	private final StandAloneApplicationContext context;

	private final AbstractUserBD userBD;

	private final AbstractUserGroupBD userGroupBD;

	// ========================= CONSTRUCTORS ==============================

	public UserUtil(final StandAloneEnvironment environment)
	{
		this.context = environment.getContext();
		this.userBD = new StandAloneUserBD(context);
		this.userGroupBD = new StandAloneUserGroupBD(context);
	}

	// ========================= METHODS ===================================

	public final String getUserEmail(final String username)
	{
		return username + "@" + getClass().getSimpleName();
	}

	public final User getUser(final String username)
	{
		return new User(getUserEmail(username), Gender.MALE, AgeGroup.GT_25,
				Ethnicity.UNSPECIFIED, Language.OTHER);
	}

	public final void saveUser(final User user)
	{
		userBD.createUser(user);
	}

	public final void updateUser(final User user)
	{
		userBD.updateUser(user);
	}
	
	public final void updateUserMerge(final User user)
	{
		userBD.updateUserMerge(user);
	}
	
	public final void updateUserAndRole(final User user, final RoleType roleLevel)
	{
		userBD.updateUserAndRoles(user, roleLevel);
	}

	public final void updateUserAndRoleMerge(final User user, final RoleType roleLevel)
	{
		userBD.updateUserAndRolesMerge(user, roleLevel);
	}
	
	public final User getUserFromDB(final User user)
	{
		return userBD.findByUniqueEmail(user.getEmail());
	}

	public final void deleteUser(final User user)
	{
		userBD.deleteUser(user);
	}

	public final void removeUserRole(final User user, final UserRole userRole)
	{
		userBD.removeUserRole(user, userRole.getRoleType());
	}

	public final UserGroup loadGroup(final UserGroup userGroup)
	{
		return userGroupBD.findUserGroupById(userGroup.getId());
	}

	public final void saveGroup(final UserGroup userGroup)
	{
		userGroupBD.createUserGroup(userGroup);
	}

	public final void updateGroup(final UserGroup userGroup)
	{
		userGroupBD.updateUserGroup(userGroup);
	}

	public final void deleteGroup(final UserGroup userGroup)
	{
		if (userGroup.getId() != null)
		{
			userGroupBD.forceDeleteUserGroup(userGroup);
		}
	}
	
	public final void deleteAllGroups()
	{
		final List<UserGroup> groups = userGroupBD.findAllUserGroups();
		for (UserGroup group : groups)
		{
			userGroupBD.forceDeleteUserGroup(group);
		}
	}

	public final void deleteMembership(final UserGroupMembership membership)
	{
		if (membership != null && membership.getId() != null)
		{
			userGroupBD.removeMembership(membership);
		}
	}

	public final void deleteModerator(final UserGroupModerator moderator)
	{
		if (moderator != null && moderator.getId() != null)
		{
			userGroupBD.removeModerator(moderator);
		}
	}

	public final void deleteMembership(final TeacherSchoolMembership membership)
	{
		if (membership != null && membership.getId() != null)
		{
			userBD.removeSchoolMembership(membership);
		}
	}

	public final AbstractUserBD getUserBD()
	{
		return userBD;
	}

	public final AbstractUserGroupBD getUserGroupBD()
	{
		return userGroupBD;
	}
}
