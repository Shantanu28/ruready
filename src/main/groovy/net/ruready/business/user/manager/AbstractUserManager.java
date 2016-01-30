/*****************************************************************************************
 * Source File: AbstractUserManager.java
 ****************************************************************************************/
package net.ruready.business.user.manager;

import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.audit.UserSession;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.eis.exception.RecordExistsException;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.exception.EmailException;
import net.ruready.common.rl.BusinessManager;
import net.ruready.common.search.SearchCriteria;

/**
 * User business service. Includes creating, deleting and updating user operations.
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
 * @version Aug 11, 2007
 */
public interface AbstractUserManager extends BusinessManager
{
	// ========================= ABSTRACT METHODS ==========================

	// ========================= ITEM MANIPULATION METHODS =================

	/**
	 * Add a new user to the database.
	 * 
	 * @param user
	 *            new user to be added under the specified parent node. The constructed
	 *            node for user will be by default a togglable sorted tree.
	 * @throws RecordExistsException
	 *             if a user with the same e-mail already exists in the database
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void createUser(final User user);

	/**
	 * Update user's details in database. Transient fields will not be affected.
	 * 
	 * @param user
	 *            user to be updated
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void updateUser(final User user);
	
	void updateUserMerge(final User user);

	/**
	 * Read a user instance using database values. Pre-populated fields are overridden if
	 * they are persistent, or unaffected if they are transient.
	 * 
	 * @param id
	 *            unique user identifier to search for and load by
	 * @param user
	 *            user to be updated from the database
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	User readUser(final Long id, final User user);

	/**
	 * Delete an existing user from the the database. If the user doesn't exist, this
	 * method has no effect.
	 * 
	 * @param user
	 *            user to be deleted
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void deleteUser(final User user);

	/**
	 * List all users in the database.
	 * 
	 * @return a list of all users
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	List<User> findAllUsers();

	/**
	 * Search for the user that matches a unique ID identifier. If this user is not found,
	 * this method will not throw an exception, but when we try to access the returned
	 * object, Hibernate will throw an <code>org.hibernate.ObjectNotFoundException</code>.
	 * 
	 * @param id
	 *            user ID to search by
	 * @return user, if found
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	User findById(final Long id);

	/**
	 * Find the catalog by unique email identifier. This assumes that there is a unique
	 * catalog matching the email.
	 * 
	 * @param email
	 *            the catalog unique email identifier to match
	 * @return catalog with this email or <code>null</code> if no catalog was found
	 * 
	 */
	User findByUniqueEmail(final String email);

	/**
	 * Search for a user that matches an example.
	 * 
	 * @param example
	 *            example criterion
	 * @return list of users matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	List<User> findByExampleObject(final Object example);

	/**
	 * Search for a user that matches an example.
	 * 
	 * @param user
	 *            example user instance to match
	 * @return list of users matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	List<User> findByExample(final User user);

	/**
	 * Search for a user by criteria.
	 * 
	 * @param searchCriteria
	 *            an object holding a list of search criteria
	 * @return list of users matching the criteria
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	List<User> findByCriteria(final SearchCriteria searchCriteria);
	
	/**
	 * Find a unique user by email and password.
	 * 
	 * @param email
	 *            user's email identifier to match
	 * @param password
	 *            user's email identifier to match
	 * @return user with this email and password or <code>null</code> if no user was
	 *         found
	 * 
	 */
	User authenticate(final String email, final String password);

	/**
	 * Decrypt user password. User sparingly to minimize security risks.
	 * 
	 * @param encryptedPassword
	 *            encrypted password
	 * @return decrypted password
	 */
	String decryptPassword(final String encryptedPassword);

	/**
	 * Encrypt user password.
	 * 
	 * @param decryptedPassword
	 *            decrypted password
	 * @return encrypted password
	 */
	String encryptPassword(final String decryptedPassword);

	/**
	 * Perform cleaning operations upon a user login (e.g., opening tests and loading
	 * information from database).
	 * 
	 * @param user
	 *            user to log in
	 * @param userSession
	 *            optional login auditing message
	 * 
	 */
	void login(final User user, final UserSession userSession);

	/**
	 * Perform cleaning operations upon a user logout (e.g., closing open tests and saving
	 * all changes to database).
	 * 
	 * @param user
	 *            user to log out
	 * @param userSession
	 *            optional logout auditing message
	 * 
	 */
	void logout(final User user);

	/**
	 * Generate a new password for a user. In principle, the users' role can be taken into
	 * account to determine the minimum length of the password, but for now all user
	 * passwords are generated using the same algorithm.
	 * 
	 * @return secure password of the necessary length
	 */
	String generatePassword(final User user);
	
	/**
	 * Send an email message to the user's email address.
	 * 
	 * @param user
	 *            user for which the password is generated (user object is not changed as
	 *            a result of this method)
	 * @param subject
	 *            e-mail message subject
	 * @param content
	 *            e-mail message content
	 * @throws EmailException
	 */
	void mailMessage(final User user, final String subject, final String content);

	// ========================= CASCADED METHODS ==========================

	// ========================= UTILITY AND TESTING METHODS ===============

	/**
	 * Create a system user in the database if not found. Does not throw an exception if
	 * the user already exists.
	 * 
	 * @param systemUser
	 *            example instance of system user to be created.
	 * @return systemUser, either a newly created one or a fully loaded instance from
	 *         database
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	User createSystemUser(final User systemUser);

	/**
	 * Removes a user role from the user object.
	 * 
	 * @param user
	 *            user to remove the <code>UserRole</code> from.
	 * @param role
	 *            the <code>UserRole</code> to remove.
	 */
	public void removeUserRole(final User user, final RoleType role);
	
	public void removeUserRoleMerge(final User user, final RoleType role);
	
	public List<UserRole> findStudentSchoolMembershipsBySchool(final School school);
	
	public List<TeacherSchoolMembership> findTeacherSchoolMembershipsByStatus(final ActiveStatus activeStatus);
	
	public TeacherSchoolMembership findTeacherSchoolMembership(final Long teacherId, final Long schoolId);
	
	public TeacherSchoolMembership findTeacherSchoolMembership(final User user, final School school);
	
	public List<School> findTeacherSchoolMemberships(final User user, final ActiveStatus activeStatus);
	
	public TeacherSchoolMembership addTeacherSchoolMembership(final School school, final User user);
	
	public TeacherSchoolMembership addSchoolMembership(final School school, final TeacherRole teacherRole);
	
	public void addSchoolMembership(final TeacherSchoolMembership membership);
	
	public void updateSchoolMembership(final TeacherSchoolMembership membership);
	
	public void removeSchoolMembership(final TeacherSchoolMembership membership);
}
