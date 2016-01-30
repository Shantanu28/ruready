/*****************************************************************************************
 * Source File: SystemUserFactory.java
 ****************************************************************************************/
package net.ruready.business.user.entity.system;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.common.misc.Singleton;
import net.ruready.common.password.Encryptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A factory of "fictitious users" representing internal system operations. Contains an
 * instance of each system user.
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
public class SystemUserFactory extends User implements Singleton
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SystemUserFactory.class);

	// Singleton instances of each system user

	/**
	 * A system user responsible for internal operations.
	 */
	private static User systemUserInstance;

	/**
	 * A demo user.
	 */
	private static User demoUserInstance;

	/**
	 * An administrator mock user.
	 */
	private static User adminUserInstance;

	/**
	 * An administrator mock user for JWebUnit test cases.
	 */
	private static User testUserInstance;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Prevent instantiation of the factory class.
	 */
	private SystemUserFactory()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * A factory method that returns a system user by type
	 * 
	 * @param encryptor
	 *            encryption service to encrypt the generated password
	 * @param context
	 *            application context
	 * @return corresponding system user
	 */
	synchronized public static void initialize(final Encryptor encryptor,
			final AbstractUserBD bdUser)
	{
		systemUserInstance = bdUser.createSystemUser(SystemUserID.SYSTEM
				.userSpecification(encryptor));
		demoUserInstance = bdUser.createSystemUser(SystemUserID.DEMO
				.userSpecification(encryptor));
		adminUserInstance = bdUser.createSystemUser(SystemUserID.ADMIN
				.userSpecification(encryptor));
		testUserInstance = bdUser.createSystemUser(SystemUserID.TEST
				.userSpecification(encryptor));
	}

	/**
	 * A factory method that returns a system user by type
	 * 
	 * @param type
	 *            system user type
	 * @return corresponding system user
	 */
	public static User getSystemUser(SystemUserID type)
	{
		if (type == null)
		{
			return null;
		}
		switch (type)
		{
			case SYSTEM:
			{
				return systemUserInstance;
			}

			case DEMO:
			{
				return demoUserInstance;
			}

			case ADMIN:
			{
				return adminUserInstance;
			}

			case TEST:
			{
				return testUserInstance;
			}
		}
		return null;
	}
}
