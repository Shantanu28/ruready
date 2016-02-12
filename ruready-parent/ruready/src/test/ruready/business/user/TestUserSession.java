/*****************************************************************************************
 * Source File: TestUserSession.java
 ****************************************************************************************/
package test.ruready.business.user;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.audit.UserSession;
import net.ruready.business.user.exports.AbstractUserBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.business.UserUtil;
import test.ruready.imports.StandAloneUserBD;
import test.ruready.rl.TestEnvTestBase;

/**
 * Tests the creation and management of {@link UserSession} objects within a
 * {@link User}.
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
 * @version Aug 5, 2007
 */
public class TestUserSession extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestUserSession.class);

	/**
	 * Contains useful utilities related to user management testing.
	 */
	private UserUtil util;

	// ========================= FIELDS ====================================

	/**
	 * User used in all tests below.
	 */
	private User user;

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		util = new UserUtil(environment);
		user = util.getUser("testuser");
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		util.deleteUser(user);
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test user login action and subsequent user session creations and updates.
	 */
	@Test
	public void testLogin() throws Exception
	{
		AbstractUserBD bdUser = new StandAloneUserBD(environment.getContext());

		// Log the user in
		UserSession userSession = new UserSession(user, null, "1", "2");
		bdUser.login(user, userSession);

		// Check for user sessions in the database
		Assert.assertEquals(1, user.getUserSessions().size());
		Assert.assertTrue(user.getUserSessions().get(0) == userSession);
		Assert.assertTrue(user.getLatestUserSession() == userSession);
		Assert.assertNotNull(userSession.getStartDate());
		Assert.assertNull(userSession.getEndDate());
		logger.debug("After login:  " + userSession);

		// Sleep... user think time
		Thread.sleep(1000);

		// Log the user out
		bdUser.logout(user);

		// An end date should appear
		Assert.assertNotNull(userSession.getEndDate());
		logger.debug("After logout: " + userSession);
	}

	// ========================= PRIVATE METHODS ===========================
}
