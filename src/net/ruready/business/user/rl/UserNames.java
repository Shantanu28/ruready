/*****************************************************************************************
 * Source File: Names.java
 ****************************************************************************************/
package net.ruready.business.user.rl;

import java.math.BigInteger;

/**
 * This interface centralizes constants, labels and names used throughout the user
 * management component. All strings referring to attributes should of course be different
 * from each other.
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
public interface UserNames
{
	// ========================= CONSTANTS =================================

	// If no trailing slash is attached, file names are relative to the
	// "classes" directory of the application.

	// -----------------------------------------------------------------------
	// Discrete types
	// -----------------------------------------------------------------------

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Types of user login actions
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface LOGIN_ACTION
	{
		static final String NAME = "loginAction";

		// Identifier types (short description of each enumerated value)
		static final String LOGIN_TYPE = "I";

		static final String LOGOUT_TYPE = "O";
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Types of access roles
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface ACCESS_ROLE
	{
		static final String NAME = "accessRole";

		// Identifier types (short description of each enumerated value)
		static final String STUDENT_TYPE = "S";

		static final String TEACHER_TYPE = "T";

		static final String PRINCIPAL_TYPE = "P";

		static final String ADMIN_TYPE = "A";

		static final String SYSTEM_TYPE = "Y";
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Age groups
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface AGE_GROUP
	{
		static final String NAME = "ageGroup";
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Ethnicities
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface ETHNICITY
	{
		static final String NAME = "ethnicity";
	}

	// -----------------------------------------------------------------------
	// System-generated users and other user-related constants
	// -----------------------------------------------------------------------
	public interface USER
	{
		// Display name of the user representing internal system operations
		static final String SYSTEM_EMAIL = "system";

		static final String SYSTEM_NAME = "System Process";

		// Display name of the user representing demo data inserts
		static final String DEMO_EMAIL = "demo";

		static final String DEMO_NAME = "Demo Student Account";

		/**
		 * Mock e-mail of an initial admin account (like unix's "root").
		 */
		static final String ADMIN_EMAIL = "admin";

		/**
		 * Display name of an initial admin account (like unix's "root").
		 */
		static final String ADMIN_NAME = "System Administrator";

		/**
		 * Mock e-mail of an admin account for JWebUnit test cases.
		 */
		static final String TEST_EMAIL = "test";

		/**
		 * Display name of an admin account for JWebUnit test cases.
		 */
		static final String TEST_NAME = "JWebUnit Tester";

		// Estimated average number of criteria in user search
		// static final int SEARCH_CRITERIA_TYPICAL_SIZE = 2;
	}

	// -----------------------------------------------------------------------
	// Password and enryption constants
	// -----------------------------------------------------------------------
	public interface PASSWORD
	{

		// System-generated password length
		static final int LENGTH_GENERATED = 8;

		// Numeral password is generated in the range [0..MAX_PASSWORD-1]
		static final int MAX_NUMBER_PASSWORD = 10000;

		// Public key (fixed). Using a common value in practice: 2^16 + 1.
		// Normally used with RSA encryption. Must be large enough to match
		// LENGTH_GENERATED, so that it can correctly encrypt and decrypt
		// all password up to that length.
		final static BigInteger PUBLIC_KEY = new BigInteger("65537");

		// Hard-wired primes for RSA private key generation . Match the
		// generated password length constant above. Equivalent to N=128.
		final BigInteger RSA_PRIME_P = new BigInteger("16321525218656177573");

		final BigInteger RSA_PRIME_Q = new BigInteger("10428943764034699217");
	}

}
