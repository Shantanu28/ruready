/*****************************************************************************************
 * Source File: UserDAO.java
 ****************************************************************************************/
package net.ruready.eis.user;

import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserRole;
import net.ruready.common.eis.dao.DAO;

/**
 * A custom Data Access Object (DAO) for a user entity.
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
 * @see net.ruready.item.entity.User
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 11, 2007
 */
public interface UserDAO extends DAO<User, Long>
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Find a unique user by email and password.
	 * 
	 * @param email
	 *            user's email identifier to match
	 * @param password
	 *            user's email identifier to match
	 * @return user with this email and password or <code>null</code> if no user was
	 *         found
	 */
	User authenticate(String email, String password);

	/**
	 * Delete roles from a user.
	 * 
	 * @param rolesToRemove
	 */
	void deleteRoles(final List<UserRole> rolesToRemove);

	public List<UserRole> getStudentSchoolMembershipsBySchool(final School school);
	
	/**
	 * Fetch the teacher school memberships.
	 * 
	 * @param activeStatus
	 *            membership activation status
	 * @return teacher school memberships list
	 */
	List<TeacherSchoolMembership> getTeacherSchoolMembershipsByStatus(
			final ActiveStatus activeStatus);
	
	public List<School> getTeacherSchoolMembershipsForUser(final User user, final ActiveStatus activeStatus);
}
