/*****************************************************************************************
 * Source File: DefaultUserBD.java
 ****************************************************************************************/
package net.ruready.business.user.exports;

import static org.apache.commons.lang.Validate.notNull;

import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.audit.UserSession;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.manager.AbstractUserManager;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.search.SearchCriteria;

/**
 * A singleton that implements the user BD interface, and relies on a static hook to
 * instantiate a specific manager implementation and a resource locator.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 21, 2007
 */
public abstract class DefaultUserBD implements AbstractUserBD
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractUserManager manager;

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
	protected DefaultUserBD(final AbstractUserManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	// ========================= IMPLEMENTATION: AbstractUserBD =====

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#createSystemUser(net.ruready.business.user.entity.User)
	 */
	public User createSystemUser(final User systemUser)
	{
		return manager.createSystemUser(systemUser);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#createUser(net.ruready.business.user.entity.User)
	 */
	public void createUser(final User user)
	{
		manager.createUser(user);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#deleteUser(net.ruready.business.user.entity.User)
	 */
	public void deleteUser(final User user)
	{
		manager.deleteUser(user);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#findAllUsers()
	 */
	public List<User> findAllUsers()
	{
		return manager.findAllUsers();
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#findByUniqueEmail(java.lang.String)
	 */
	public User findByUniqueEmail(final String email)
	{
		return manager.findByUniqueEmail(email);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#findByExampleObject(org.hibernate.criterion.Example)
	 */
	public List<User> findByExampleObject(final Object example)
	{
		return manager.findByExampleObject(example);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#findByExample(net.ruready.business.user.entity.User)
	 */
	public List<User> findByExample(final User user)
	{
		return manager.findByExample(user);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#findById(long)
	 */
	public User findById(final Long id)
	{
		return manager.findById(id);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#findByCriteria(net.ruready.common.search.SearchCriteria)
	 */
	public List<User> findByCriteria(final SearchCriteria searchCriteria)
	{
		return manager.findByCriteria(searchCriteria);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#readUser(long,
	 *      net.ruready.business.user.entity.User)
	 */
	public User readUser(final Long id, final User user)
	{
		return manager.readUser(id, user);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#updateUser(net.ruready.business.user.entity.User)
	 */
	public void updateUser(final User user)
	{
		manager.updateUser(user);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#authenticate(java.lang.String,
	 *      java.lang.String)
	 */
	public User authenticate(final String email, final String password)
	{
		return manager.authenticate(email, password);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#decryptPassword(java.lang.String)
	 */
	public String decryptPassword(final String encryptedPassword)
	{
		return manager.decryptPassword(encryptedPassword);
	}


	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#encryptPassword(java.lang.String)
	 */
	public String encryptPassword(final String decryptedPassword)
	{
		return manager.encryptPassword(decryptedPassword);
	}
	
	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#login(net.ruready.business.user.entity.User,
	 *      net.ruready.business.user.entity.audit.UserSession)
	 */
	public void login(final User user, final UserSession userSession)
	{
		manager.login(user, userSession);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#logout(net.ruready.business.user.entity.User)
	 */
	public void logout(final User user)
	{
		manager.logout(user);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#generatePassword(net.ruready.business.user.entity.User)
	 */
	public String generatePassword(final User user)
	{
		return manager.generatePassword(user);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#mailMessage(net.ruready.business.user.entity.User,
	 *      java.lang.String, java.lang.String)
	 */
	public void mailMessage(User user, String subject, String content)
	{
		manager.mailMessage(user, subject, content);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#removeUserRole(net.ruready.business.user.entity.User,
	 *      net.ruready.business.user.entity.UserRole)
	 */
	public void removeUserRole(final User user, final RoleType role)
	{
		manager.removeUserRole(user, role);
	}
	
	/**
	 * @see net.ruready.business.user.manager.AbstractUserManager#removeUserRoleMerge(net.ruready.business.user.entity.User, net.ruready.business.user.entity.property.RoleType)
	 */
	public void removeUserRoleMerge(final User user, final RoleType role)
	{
		manager.removeUserRoleMerge(user, role);
	}

	public List<UserRole> findStudentSchoolMembershipsBySchool(School school)
	{
		return manager.findStudentSchoolMembershipsBySchool(school);
	}
	
	public List<TeacherSchoolMembership> findTeacherSchoolMembershipsByStatus(final ActiveStatus activeStatus)
	{
		return manager.findTeacherSchoolMembershipsByStatus(activeStatus);
	}
	
	public TeacherSchoolMembership findTeacherSchoolMembership(final Long teacherId, final Long schoolId)
	{
		return manager.findTeacherSchoolMembership(teacherId, schoolId);
	}

	public TeacherSchoolMembership findTeacherSchoolMembership(final User user, final School school)
	{
		return manager.findTeacherSchoolMembership(user, school);
	}


	public List<School> findTeacherSchoolMemberships(final User user, final ActiveStatus activeStatus)
	{
		return manager.findTeacherSchoolMemberships(user, activeStatus);
	}

	public TeacherSchoolMembership addSchoolMembership(final School school,
			final TeacherRole teacherRole)
	{
		return manager.addSchoolMembership(school, teacherRole);
	}
	
	public TeacherSchoolMembership addTeacherSchoolMembership(final School school,
			final User user)
	{
		return manager.addTeacherSchoolMembership(school, user);
	}

	public void addSchoolMembership(final TeacherSchoolMembership membership)
	{
		manager.addSchoolMembership(membership);
	}

	public void updateSchoolMembership(final TeacherSchoolMembership membership)
	{
		manager.updateSchoolMembership(membership);
	}

	public void removeSchoolMembership(final TeacherSchoolMembership membership)
	{
		manager.removeSchoolMembership(membership);
	}
	

	public void updateUserMerge(final User user)
	{
		manager.updateUserMerge(user);
	}
	

	public void updateUserAndRoles(final User user, final RoleType roleLevel)
	{
		notNull(roleLevel, "roleLevel cannot be null.");
		if (!user.hasRole(roleLevel))
		{
			user.addRole(roleLevel);
			manager.updateUser(user);
		}
		else if (user.hasRole(roleLevel) && roleLevel == user.getHighestRole())
		{
			manager.updateUser(user);
		}
		else
		{
			manager.removeUserRole(user, RoleType.values()[roleLevel.ordinal() + 1]);
		}
	}

	public void updateUserAndRolesMerge(final User user, final RoleType roleLevel)
	{
		notNull(roleLevel, "roleLevel cannot be null.");
		if (!user.hasRole(roleLevel))
		{
			user.addRole(roleLevel);
			manager.updateUserMerge(user);
		}
		else if (user.hasRole(roleLevel) && roleLevel == user.getHighestRole())
		{
			manager.updateUserMerge(user);
		}
		else
		{
			manager.removeUserRoleMerge(user, RoleType.values()[roleLevel.ordinal() + 1]);
		}
	}

	// ========================= GETTERS & SETTERS =========================
}
