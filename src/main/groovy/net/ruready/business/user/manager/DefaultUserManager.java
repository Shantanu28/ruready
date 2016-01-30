/*****************************************************************************************
 * Source File: DefaultUserManager.java
 ****************************************************************************************/
package net.ruready.business.user.manager;

import static net.ruready.business.user.manager.Messages.createMessage;
import static net.ruready.business.user.manager.Messages.deleteMessage;
import static net.ruready.business.user.manager.Messages.emptyMessage;
import static net.ruready.business.user.manager.Messages.findAllMessage;
import static net.ruready.business.user.manager.Messages.findByIdMessage;
import static net.ruready.business.user.manager.Messages.nullMessage;
import static net.ruready.business.user.manager.Messages.readMessage;
import static net.ruready.business.user.manager.Messages.updateMessage;
import static org.apache.commons.lang.Validate.notEmpty;
import static org.apache.commons.lang.Validate.notNull;

import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.audit.UserSession;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.entity.property.UserStatus;
import net.ruready.business.user.exception.AuthenticationException;
import net.ruready.business.user.rl.UserNames;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.exception.RecordExistsException;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.exception.EmailException;
import net.ruready.common.password.Encryptor;
import net.ruready.common.password.RandPass;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchEngine;
import net.ruready.eis.user.UserDAO;
import net.ruready.port.mail.Mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This service allows user manipulation: creating a new user, updating an existing user,
 * deleting an user, and listing users by certain criteria. *
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
public class DefaultUserManager implements AbstractUserManager
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultUserManager.class);

	private static final String ENTITY_NAME = "User";

	private static final String EMAIL_NOT_EMPTY = emptyMessage("email");

	private static final String EXAMPLE_NOT_NULL = nullMessage("example");

	private static final String ID_NOT_NULL = nullMessage("id");

	private static final String PASSWORD_NOT_EMPTY = emptyMessage("password");

	private static final String SEARCH_CRITERIA_NOT_NULL = nullMessage("searchCriteria");

	private static final String USER_NOT_NULL = nullMessage("user");

	private static final String MEMBERSHIP_NOT_NULL = nullMessage("membership");

	// ========================= FIELDS ====================================

	/**
	 * Locator of the DAO factory that creates DAOs for TreeNodes.
	 */
	protected final ResourceLocator resourceLocator;

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
	 * Create a service that will use a resource locator and a DAO factory to read/write
	 * users to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            application context
	 */
	public DefaultUserManager(final ResourceLocator resourceLocator,
			final ApplicationContext context)
	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
	}

	// ========================= IMPLEMENTATION: AbstractUserManager ===

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
	public void createUser(final User user)
	{
		notNull(user, USER_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(createMessage(ENTITY_NAME, user.getEmail()));
		}
		getUserDAO().create(user);
	}

	/**
	 * Update user's details in database. Transient fields will not be affected.
	 * 
	 * @param user
	 *            user to be updated
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public void updateUser(final User user)
	{
		notNull(user, USER_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.info(updateMessage(ENTITY_NAME, user.getEmail()));
		}

		// TODO: Do not update user if it is read-only; notify client of a
		// "soft" error
		getUserDAO().update(user);
	}

	public void updateUserMerge(final User user)
	{
		getUserDAO().merge(user);
	}

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
	public User readUser(final Long id, final User user)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(readMessage(ENTITY_NAME, id, ((user == null) ? "NULL" : user
					.getEmail())));
		}

		// ============================================================
		// Load user from database
		// ============================================================
		getUserDAO().readInto(id, user);

		// ============================================================
		// Post processing and population of extra useful properties
		// ============================================================

		return user;
	}

	/**
	 * Delete an existing user from the the database. If the user doesn't exist, this
	 * method has no effect.
	 * 
	 * @param user
	 *            user to be deleted
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public void deleteUser(final User user)
	{
		notNull(user, USER_NOT_NULL);
		if (logger.isInfoEnabled())
		{
			logger.debug(deleteMessage(ENTITY_NAME, user.getEmail()));
		}
		// TODO: Do not delete user if it is read-only; notify client of a
		// "soft" error

		// Remove user from any associations before attempting to delete it
		// from the database, because the deleted object would be re-saved by
		// cascade and throw an org.hibernate.ObjectDeletedException.
		getUserDAO().delete(user);
	}

	/**
	 * List all users in the database.
	 * 
	 * @return a list of all users
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public List<User> findAllUsers()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(findAllMessage(ENTITY_NAME));
		}
		return getUserDAO().findAll();
	}

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
	public User findById(final Long id)
	{
		notNull(id, ID_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug(findByIdMessage(ENTITY_NAME, id));
		}
		return getUserDAO().read(id);
	}

	/**
	 * Find the user by unique email identifier. This assumes that there is a unique
	 * catalog matching the email.
	 * 
	 * @param email
	 *            the user unique email identifier to match
	 * @return user with this email or <code>null</code> if no user was found
	 * 
	 */
	public User findByUniqueEmail(final String email)
	{
		notEmpty(email, EMAIL_NOT_EMPTY);
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding user by unique email '" + email + "' ...");
		}
		return getUserDAO().findByUniqueProperty(User.EMAIL, email);
	}

	/**
	 * Search for a user that matches an example.
	 * 
	 * @param example
	 *            example criterion
	 * @return list of users matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public List<User> findByExampleObject(final Object example)
	{
		notNull(example, EXAMPLE_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding existing user by example '" + example + "' ...");
		}
		return getUserDAO().findByExampleObject(example);
	}

	/**
	 * Search for an user that matches an example instance.
	 * 
	 * @param example
	 *            example user instance
	 * @return list of users matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public List<User> findByExample(final User user)
	{
		notNull(user, USER_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding existing user by example instance '" + user.getEmail()
					+ "' ...");
		}
		return getUserDAO().findByExample(user);
	}

	/**
	 * Search for a user by criteria.
	 * 
	 * @param searchCriteria
	 *            an object holding a list of search criteria
	 * @return list of users matching the criteria
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public List<User> findByCriteria(final SearchCriteria searchCriteria)
	{
		notNull(searchCriteria, SEARCH_CRITERIA_NOT_NULL);

		SearchEngine<User> searchEngine = resourceLocator.getSearchEngine(User.class,
				context);
		List<User> users = searchEngine.search(searchCriteria);
		return users;
	}

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
	 * @throws AuthenticationException
	 *             if user is not found
	 */
	public User authenticate(final String email, final String password)
	{
		notEmpty(email, EMAIL_NOT_EMPTY);
		notEmpty(password, PASSWORD_NOT_EMPTY);
		if (logger.isInfoEnabled())
		{
			logger.info("Authenticating user, email '" + email + "' password " + "'"
					+ password + "'");
		}
		Encryptor encryptor = resourceLocator.getEncryptor();

		// Search by email only first
		User foundUser = findByUniqueEmail(email);
		if (foundUser == null)
		{
			throw new AuthenticationException("Login failed", email, password,
					AuthenticationException.TYPE.LOGIN_FAILED);
		}
		else if (foundUser.getUserStatus() == UserStatus.LOCKED)
		{
			// Database password value is the "locked" value (unencrypted)
			if (logger.isInfoEnabled())
			{
				logger.info("User account " + email + " is locked");
			}
			throw new AuthenticationException("User account is locked", email, password,
					AuthenticationException.TYPE.ACCOUNT_LOCKED);
		}

		// If email found: the database stores encrypted passwords, so
		// encrypt the suggested password to compare against database value
		String encryptedPassword = encryptor.encrypt(password);
		foundUser = getUserDAO().authenticate(email, encryptedPassword);
		if (foundUser == null)
		{
			throw new AuthenticationException("Login failed", email, password,
					AuthenticationException.TYPE.LOGIN_FAILED);
		}

		// Initialize user.roles
		final Set<UserRole> roles = foundUser.getRoles();
		if (logger.isDebugEnabled())
		{
			logger.debug("User's roles: " + roles + " size " + roles.size());
		}
		return foundUser;
	}

	/**
	 * Decrypt user password. User sparingly to minimize security risks.
	 * 
	 * @param encryptedPassword
	 *            encrypted password
	 * @return decrypted password
	 */
	public String decryptPassword(final String encryptedPassword)
	{
		notEmpty(encryptedPassword, PASSWORD_NOT_EMPTY);

		return resourceLocator.getEncryptor().decrypt(encryptedPassword);
	}
	
	/**
	 * Encrypt user password.
	 * 
	 * @param decryptedPassword decrypted password
	 * @return encrypted password
	 */
	public String encryptPassword(final String decryptedPassword)
	{
		notEmpty(decryptedPassword, PASSWORD_NOT_EMPTY);
		return resourceLocator.getEncryptor().encrypt(decryptedPassword);
	}

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
	public void login(final User user, final UserSession userSession)
	{
		notNull(user, USER_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug("Logging in user '" + user.getEmail() + "'");
		}

		if (userSession != null)
		{
			user.addUserSession(userSession);
			getUserDAO().update(user);
		}
	}

	/**
	 * Perform cleaning operations upon a user logout (e.g., closing open tests and saving
	 * all changes to database).
	 * 
	 * @param user
	 *            user to log out
	 * 
	 */
	public void logout(final User user)
	{
		notNull(user, USER_NOT_NULL);
		if (logger.isDebugEnabled())
		{
			logger.debug("Logging out user '" + user.getEmail() + "'");
		}

		// Seal the latest user session and update database values.
		final UserSession latestUserSession = user.getLatestUserSession();
		if (latestUserSession != null)
		{
			latestUserSession.logout();
		}
		getUserDAO().update(user);
	}

	/**
	 * Generate a new password for a user. In principle, the users' role can be taken into
	 * account to determine the minimum length of the password, but for now all user
	 * passwords are generated using the same algorithm.
	 * 
	 * @param user
	 *            user for which the password is generated (user object is not changed as
	 *            a result of this method)
	 * @return secure password of the necessary length
	 */
	public String generatePassword(final User user)
	{
		notNull(user, USER_NOT_NULL);

		// Generate a secure password made up of lower case letters
		// and numbers. Size = minimum size for this type of admin level.
		String clearText = new RandPass(RandPass.LOWERCASE_LETTERS_AND_NUMBERS_ALPHABET)
				.getPass(UserNames.PASSWORD.LENGTH_GENERATED);
		return resourceLocator.getEncryptor().encrypt(clearText);
	}

	/**
	 * Send a welcome email to the user's email address.
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
	public void mailMessage(final User user, final String subject, final String content)
	{
		notNull(user, USER_NOT_NULL);
		notEmpty(subject);
		notEmpty(content);

		Session mailSession = (Session) resourceLocator.getMailSession();

		// Set to, from addresses and send contents
		String[] recipients =
		{
			user.getEmail()
		};
		String attachedFileName[] = {};
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("Sending mail message to <" + user.getEmail() + ">");
				logger.debug("Subject: " + subject);
				logger.debug(content);
			}
			Mail.send(recipients, subject, content, attachedFileName, mailSession);
		}
		catch (AddressException e)
		{
			throw new EmailException("Failed sending welcome email: "
					+ e.toString());
		}
		catch (MessagingException e)
		{
			throw new EmailException("Failed sending welcome email: "
					+ e.toString());
		}
	}

	// ========================= CASCADED METHODS ==========================

	// ========================= UTILITY AND TESTING METHODS ===============

	/**
	 * Create a system user in the database if not found. Does not throw an exception if
	 * the user already exists.
	 * 
	 * @param systemUser
	 *            system user to be created.
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public User createSystemUser(final User systemUser)
	{
		notNull(systemUser, USER_NOT_NULL);

		// Delete previous catalog with this name if found
		User storedUser = findByUniqueEmail(systemUser.getEmail());

		// Don't override existing user
		if (storedUser == null)
		{
			// Save new user
			if (logger.isDebugEnabled())
			{
				logger.debug("System user '" + systemUser.getEmail()
						+ "' not found, creating a new one");
			}
			createUser(systemUser);
			return findByUniqueEmail(systemUser.getEmail());
		}
		else
		{
			return storedUser;
		}
	}

	public void removeUserRole(final User user, final RoleType role)
	{
		notNull(user, USER_NOT_NULL);
		notNull(role, "role cannot be null.");

		final List<UserRole> rolesToRemove = user.removeRole(role);
		getUserDAO().deleteRoles(rolesToRemove);
		getUserDAO().update(user);
	}
	
	public void removeUserRoleMerge(final User user, final RoleType role)
	{
		notNull(user, USER_NOT_NULL);
		notNull(role, "role cannot be null.");
		
		final List<UserRole> rolesToRemove = user.removeRole(role);
		getUserDAO().deleteRoles(rolesToRemove);
		getUserDAO().merge(user);
	}

	public List<UserRole> findStudentSchoolMembershipsBySchool(final School school)
	{
		return getUserDAO().getStudentSchoolMembershipsBySchool(school);
	}

	public List<TeacherSchoolMembership> findTeacherSchoolMembershipsByStatus(
			final ActiveStatus activeStatus)
	{
		return getUserDAO().getTeacherSchoolMembershipsByStatus(activeStatus);
	}

	public TeacherSchoolMembership findTeacherSchoolMembership(final Long teacherId,
			final Long schoolId)
	{
		return getTeacherSchoolMembershipDAO().read(
				new TeacherSchoolMembership.Id(teacherId, schoolId));
	}

	public TeacherSchoolMembership findTeacherSchoolMembership(final User user,
			final School school)
	{
		return findTeacherSchoolMembership(user.getRole(RoleType.TEACHER).getId(), school
				.getId());
	}
	
	public List<School> findTeacherSchoolMemberships(final User user, final ActiveStatus activeStatus)
	{
		return getUserDAO().getTeacherSchoolMembershipsForUser(user, activeStatus);
	}

	public TeacherSchoolMembership addTeacherSchoolMembership(final School school,
			final User user)
	{
		final TeacherSchoolMembership membership = new TeacherSchoolMembership(user,
				school);
		addSchoolMembership(membership);
		return membership;
	}

	public TeacherSchoolMembership addSchoolMembership(final School school,
			final TeacherRole teacherRole)
	{
		final TeacherSchoolMembership membership = new TeacherSchoolMembership(
				teacherRole, school);
		addSchoolMembership(membership);
		return membership;
	}

	public void addSchoolMembership(final TeacherSchoolMembership membership)
	{
		notNull(membership, MEMBERSHIP_NOT_NULL);
		getTeacherSchoolMembershipDAO().create(membership);
	}

	public void updateSchoolMembership(final TeacherSchoolMembership membership)
	{
		notNull(membership, MEMBERSHIP_NOT_NULL);
		getTeacherSchoolMembershipDAO().update(membership);
	}

	public void removeSchoolMembership(final TeacherSchoolMembership membership)
	{
		notNull(membership, MEMBERSHIP_NOT_NULL);

		if (membership.getSchool() != null)
		{
			membership.getSchool().getTeachers().remove(membership);
		}
		if (membership.getMember() != null)
		{
			membership.getMember().getSchools().remove(membership);
		}
		getTeacherSchoolMembershipDAO().delete(membership);
	}

	private UserDAO getUserDAO()
	{
		return (UserDAO) eisManager.getDAO(User.class, context);
	}

	private DAO<TeacherSchoolMembership, TeacherSchoolMembership.Id> getTeacherSchoolMembershipDAO()
	{
		return eisManager.getDAO(TeacherSchoolMembership.class, context);
	}
}
