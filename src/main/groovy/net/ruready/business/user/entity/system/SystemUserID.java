/*****************************************************************************************
 * Source File: SystemUserID.java
 ****************************************************************************************/
package net.ruready.business.user.entity.system;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.entity.property.UserStatus;
import net.ruready.business.user.rl.UserNames;
import net.ruready.common.discrete.Gender;
import net.ruready.common.discrete.Identifier;
import net.ruready.common.password.Encryptor;

/**
 * Possible system-user types: system, demo, etc. This is a naming convention
 * used by {@link SystemUserFactory}.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 11, 2007
 */
public enum SystemUserID implements Comparable<SystemUserID>, Identifier
{
	// ========================= ENUMERATED TYPES ==========================

	// Represents system processes. Cannot log in.
	SYSTEM
	{
		@Override
		public String toString()
		{
			return UserNames.USER.SYSTEM_NAME;
		}

		/**
		 * Get the un-encrypted password of this user.
		 * 
		 * @return the un-encrypted password of this user
		 */
		@Override
		public String getPasswordClearText()
		{
			return UserNames.USER.SYSTEM_EMAIL;
		}

		/**
		 * @param encryptor
		 * @return
		 * @see net.ruready.business.user.entity.system.SystemUserID#userSpecification(net.ruready.common.password.Encryptor)
		 */
		@Override
		public User userSpecification(Encryptor encryptor)
		{
			User user = new User(UserNames.USER.SYSTEM_EMAIL, Gender.FEMALE,
					AgeGroup.GT_25, Ethnicity.UNSPECIFIED, Language.OTHER);
			user.setUserStatus(UserStatus.LOCKED);
			user.setPassword(encryptor.encrypt(getPasswordClearText()));
			user.setFirstName(UserNames.USER.SYSTEM_NAME);
			user.addRole(RoleType.ADMIN.newInstance());
			return user;
		}
	},

	/**
	 * Represents a demo student account. Can log in.
	 */
	DEMO
	{
		@Override
		public String toString()
		{
			return UserNames.USER.DEMO_NAME;
		}

		/**
		 * Get the un-encrypted password of this user.
		 * 
		 * @return the un-encrypted password of this user
		 */
		@Override
		public String getPasswordClearText()
		{
			return UserNames.USER.DEMO_EMAIL;
		}

		@Override
		public User userSpecification(Encryptor encryptor)
		{
			User user = new User(UserNames.USER.DEMO_EMAIL, Gender.FEMALE,
					AgeGroup.GT_25, Ethnicity.UNSPECIFIED, Language.OTHER);
			user.setPassword(encryptor.encrypt(getPasswordClearText()));
			user.setFirstName(UserNames.USER.DEMO_NAME);
			user.addRole(RoleType.STUDENT.newInstance());
			return user;
		}
	},

	/**
	 * Initial admin account (like Unix's "root"). Has full access.
	 */
	ADMIN
	{
		@Override
		public String toString()
		{
			return UserNames.USER.ADMIN_NAME;
		}

		/**
		 * Get the un-encrypted password of this user.
		 * 
		 * @return the un-encrypted password of this user
		 */
		@Override
		public String getPasswordClearText()
		{
			return UserNames.USER.ADMIN_EMAIL;
		}

		@Override
		public User userSpecification(Encryptor encryptor)
		{
			User user = new User(UserNames.USER.ADMIN_EMAIL, Gender.FEMALE,
					AgeGroup.GT_25, Ethnicity.UNSPECIFIED, Language.OTHER);
			user.setPassword(encryptor.encrypt(getPasswordClearText()));
			user.setFirstName(UserNames.USER.ADMIN_NAME);
			user.addRole(RoleType.ADMIN.newInstance());
			return user;
		}
	},

	/**
	 * Admin account for JWebUnit test cases. Has full access.
	 */
	TEST
	{
		@Override
		public String toString()
		{
			return UserNames.USER.TEST_NAME;
		}

		/**
		 * Get the un-encrypted password of this user.
		 * 
		 * @return the un-encrypted password of this user
		 */
		@Override
		public String getPasswordClearText()
		{
			return UserNames.USER.TEST_EMAIL;
		}

		@Override
		public User userSpecification(Encryptor encryptor)
		{
			User user = new User(UserNames.USER.TEST_EMAIL, Gender.MALE, AgeGroup.LT_16,
					Ethnicity.UNSPECIFIED, Language.OTHER);
			user.setPassword(encryptor.encrypt(getPasswordClearText()));
			user.setFirstName(UserNames.USER.TEST_NAME);
			user.addRole(RoleType.ADMIN.newInstance());
			return user;
		}
	};

	// ========================= FIELDS ====================================

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * Return the object's type. This would normally be the object's
	 * <code>getClass().getSimpleName()</code>, but objects might be wrapped
	 * by Hibernate to return unexpected names. As a rule, the type string
	 * should not contain spaces and special characters.
	 */
	public String getType()
	{
		// The enumerated type's name satisfies all the type criteria, so use
		// it.
		return name();
	}

	// ========================= METHODS ===================================

	/**
	 * Create a user instance under the specification of this system user type.
	 * 
	 * @param encryptor
	 *            encryption service to encrypt the generated password
	 */
	public abstract User userSpecification(Encryptor encryptor);

	/**
	 * Get the un-encrypted password of this user.
	 * 
	 * @return the un-encrypted password of this user
	 */
	public abstract String getPasswordClearText();
}
