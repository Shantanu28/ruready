/*****************************************************************************************
 * Source File: TestUrlPattern.java
 ****************************************************************************************/
package test.ruready.web.util;

import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tests servlet url pattern matching.
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
 * @version Aug 13, 2007
 */
public class TestUrlPattern extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestUrlPattern.class);

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test servlet url pattern matching.
	 */
	@Test
	public void testUrlMatching()
	{
		String urlPattern1 = "/status/*";
		Assert.assertFalse(StrutsUtil.urlMatches(null, urlPattern1));
		Assert.assertTrue(StrutsUtil.urlMatches("/status/synopsis", urlPattern1));
		Assert.assertTrue(StrutsUtil.urlMatches("/status/complete?date=today",
				urlPattern1));
		Assert.assertTrue(StrutsUtil.urlMatches("/status", urlPattern1));
		Assert.assertFalse(StrutsUtil.urlMatches("/server/status", urlPattern1));

		String urlPattern2 = "*.map";
		Assert.assertTrue(StrutsUtil.urlMatches("/US/Oregon/Portland.map", urlPattern2));
		Assert.assertTrue(StrutsUtil
				.urlMatches("/US/Washington/Seattle.map", urlPattern2));
		Assert.assertTrue(StrutsUtil.urlMatches("/Paris.France.map", urlPattern2));
		Assert.assertFalse(StrutsUtil.urlMatches("/US/Oregon/Portland.MAP", urlPattern2));
		Assert.assertFalse(StrutsUtil
				.urlMatches("/interface/description/mail.mapi", urlPattern2));

	}
}
