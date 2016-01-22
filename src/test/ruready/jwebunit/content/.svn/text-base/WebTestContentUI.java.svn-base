/*****************************************************************************************
 * Source File: WebTestContentUI.java
 ****************************************************************************************/

package test.ruready.jwebunit.content;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.JUnit4TestAdapter;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.common.rl.Environment;
import net.ruready.common.rl.MinimalEnvironment;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;

import test.ruready.jwebunit.rl.WebTestBase;

/**
 * Tests various aspects of the content management system web UI.
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

public class WebTestContentUI extends WebTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(WebTestContentUI.class);

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
	public WebTestContentUI()
	{
		super();
	}

	/**
	 * @param name
	 */
	public WebTestContentUI(String name)
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
	 * Test getting to the CMS front page.
	 */
	public void testContentFrontPage()
	{
		// Login as an admin
		loginAsTestUser();

		// Click on the CMC link
		clickLink("cms");

		// We're supposed to be at the admin main page now
		assertKeyPresent("content.header");
		assertKeyPresent("content.root.label");
		assertKeyPresent("content.root.childrendescription");

		// This depends on the specific mock objects created upon site setup.
		// TODO: i18n labels here? or add ids on item links based on their item
		// type
		// and id, and use them here.
		assertLinkPresentWithText(ContentNames.BASE_NAME.CATALOG);
	}

	/**
	 * Test browsing to a subject, going to its edit screen and verifying that
	 * children serial numbers are non-null.
	 */
	public void testEditSubjectScreen()
	{
		// Get to the CMC front page
		testContentFrontPage();

		// The following depend on the specific mock objects created upon site
		// setup.
		// TODO: i18n labels here? or add ids on item links based on their item
		// type
		// and id, and use them here.

		// Click on the catalog link
		clickLinkWithText(ContentNames.BASE_NAME.CATALOG);

		// Click on the subject link
		assertLinkPresentWithText(SUBJECT_NAME);
		clickLinkWithText(SUBJECT_NAME);

		// Click on the edit button in the item toolbar
		clickButton("itemToolbarEdit");

		// Verify that we are at the edit page
		assertTitleEqualsKey("content.editItemFull.title");
		assertTextPresent(SUBJECT_NAME);

		// Get to the children list (table) and check that there are no
		// null serial numbers
		assertTablePresent(TABLE_NAME);
		assertTextNotInTable(TABLE_NAME, "null.");
	}

	/**
	 * Test subject editing: selecting tag collections, adding a new course,
	 * removing a course, moving a course in the course list (by serial number).
	 * <p>
	 * Part 1: adding tag collections.
	 */
	public void testEditSubjectAddTagCollections()
	{
		// Get to the edit screen
		testEditSubjectScreen();

		// Get to subject editing screen
		int oldVersion = getItemVersion(getPageSource());
		logger.debug("Old V" + oldVersion);

		// The following depend on the specific mock objects created upon site
		// setup.
		// TODO: i18n labels here? or add ids on item links based on their item
		// type
		// and id, and use them here.

		// Select tag collections
		selectTagCollections();

		// Save subject
		submit("action_save");

		// Save succeeded if the version number is incremented
		int newVersion = getItemVersion(getPageSource());
		logger.debug("New V" + newVersion);
		Assert.assertEquals(oldVersion + 1, newVersion);
	}

	/**
	 * Test subject editing: selecting tag collections, adding a new course,
	 * removing a course, moving a course in the course list (by serial number).
	 * <p>
	 * Part 2: adding a course.
	 */
	public void testEditSubjectAddNewCourse()
	{
		// Get to the edit screen
		testEditSubjectScreen();

		// Get to subject editing screen
		int oldVersion = getItemVersion(getPageSource());
		logger.debug("Old V" + oldVersion);

		// The following depend on the specific mock objects created upon site
		// setup.
		// TODO: i18n labels here? or add ids on item links based on their item
		// type
		// and id, and use them here.

		// Select tag collections
		selectTagCollections();

		// Add a new course

		// Save subject
		submit("action_save");

		// Save succeeded if the version number is incremented
		int newVersion = getItemVersion(getPageSource());
		logger.debug("New V" + newVersion);
		Assert.assertEquals(oldVersion + 1, newVersion);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Test that we can get to the proper page and see the correct information.
	 */
	private void getToFrontPage()
	{
		beginAt("/");
		assertTitleEqualsKey("user.home.title");
	}

	/**
	 * Test logging an admin user into the site.
	 */
	public void loginAsTestUser()
	{
		// Get to the front page
		getToFrontPage();

		// Fill in the login form and submit
		assertFormPresent("loginForm");
		setWorkingForm("loginForm");
		setTextField("email", "test");
		setTextField("password", "test");
		// Required hidden fields
		clickLink("action_login_link");

		// We're supposed to be at the admin main page now
		assertTitleEqualsKey("user.adminMain.title");
	}

	// ========================= UTILITIES =================================

	/**
	 * Extract an item's current version from an edit/view page source.
	 * 
	 * @param pageSource
	 *            page source
	 * @return item version; if not found, returns
	 *         <code>CommonNames.MISC.INVALID_VALUE_INTEGER</code>
	 */
	private static int getItemVersion(final String pageSource)
	{
		Pattern p = Pattern.compile("<span id=\"auditMessageVersion\">[0-9]*</span>");
		Matcher m = p.matcher(pageSource);
		// Find the first match of the id element
		Assert.assertTrue(m.find());
		// Strip the id element and extract the version
		String versionStr = m.group().replaceAll("<span id=\"auditMessageVersion\">", "")
				.replaceAll("</span>", "");
		return TextUtil.getStringAsInteger(versionStr);
	}

	/**
	 * A commonly used selection of tag collections on the subject editing
	 * screen.
	 */
	private void selectTagCollections()
	{
		// Select tag collections
		final String interestCollectionMenu = "itemForm.tagCollectionMenuForm.interestCollectionId";
		selectOption(interestCollectionMenu, "Student interests");

		final String skillCollectionMenu = "itemForm.tagCollectionMenuForm.skillCollectionId";
		selectOption(skillCollectionMenu, "Math skills");

		final String conceptCollectionMenu = "itemForm.tagCollectionMenuForm.conceptCollectionId";
		selectOption(conceptCollectionMenu, "Math concepts");

	}


	// ========================= TESTING ===================================

	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(WebTestContentUI.class);
	}
}
