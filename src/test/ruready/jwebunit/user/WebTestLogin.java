/*****************************************************************************************
 * Source File: WebTestLogin.java
 ****************************************************************************************/

package test.ruready.jwebunit.user;

import junit.framework.JUnit4TestAdapter;
import net.ruready.common.rl.Environment;
import net.ruready.common.rl.MinimalEnvironment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.ruready.jwebunit.rl.WebTestBase;

/**
 * A web test unit for basic user login/logout actions.
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
 * @version Oct 13, 2007
 */
public class WebTestLogin extends WebTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(WebTestLogin.class);

	/**
	 * Mock subject (hard-coded string).
	 */
	public static final String SUBJECT_NAME = "Math";

	/**
	 * Children table id on item edit page.
	 */
	public static final String TABLE_NAME = "children_SERIAL_NO";

	// ========================= CONSTRUCTORS ==============================

	/**
	 * 
	 */
	public WebTestLogin()
	{
		super();
	}

	/**
	 * @param name
	 */
	public WebTestLogin(String name)
	{
		super(name);
	}

	// ========================= IMPLEMENTATION: TestBase ==================

	/**
	 * @return
	 * @see net.ruready.business.common.junit.exports.EISTestCase#setEnvironment()
	 */
	@Override
	protected Environment setEnvironment()
	{
		return new MinimalEnvironment();
	}

	// ========================= TESTING METHODS ===========================

	/**
	 * Test that we can get to the proper page and see the correct information.
	 */
	public void testFrontPage()
	{
		beginAt("/");
		assertTitleEqualsKey("user.home.title");
	}

	/**
	 * Test logging an admin user into the site.
	 */
	public void testLoginAdmin()
	{
		// Get to the front page
		testFrontPage();

		// Fill in the login form and submit
		assertFormPresent("loginForm");
		setWorkingForm("loginForm");
		setTextField("email", "test");
		setTextField("password", "test");
		// Required hidden fields
		clickLink("action_login_link");

		// We're supposed to be at the admin main page now
		// logger.debug(getPageSource());
		assertTitleEqualsKey("user.adminMain.title");
	}

	// ========================= TESTING ===================================

	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(WebTestLogin.class);
	}
}
