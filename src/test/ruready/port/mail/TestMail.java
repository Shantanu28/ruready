/*****************************************************************************************
 * Source File: TestMail.java
 ****************************************************************************************/
package test.ruready.port.mail;

import javax.mail.Session;

import net.ruready.port.mail.Mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * A simple mail test that can be run to determine if the JavaMail configuration
 * works properly. Sends an email from the help desk to a specified recipient.
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
public class TestMail extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestMail.class);

	// ========================= FIELDS ====================================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test the interval arithmetic methods.
	 */
	@Test
	public void testMailSession()
	{
		// Uncomment for mail testing. Make sure that the mail session's
		// "mail.net.ruready.helpemail" property is set to the receipient.

		// Test email. Creates a new Mail session.
		Assert.assertEquals(true, Mail.mailTest((Session) environment.getMailSession()));

		// Test the RL cash by sending a second email that uses a cached session
		Assert.assertEquals(true, Mail.mailTest((Session) environment.getMailSession()));
	}

	// ========================= PRIVATE METHODS ===========================
}
