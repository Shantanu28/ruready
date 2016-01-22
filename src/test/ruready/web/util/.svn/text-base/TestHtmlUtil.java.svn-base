/*****************************************************************************************
 * Source File: TestStrings.java
 ****************************************************************************************/
package test.ruready.web.util;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import net.ruready.web.common.util.HtmlUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tests related to HTML and URL manipulation.
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
public class TestHtmlUtil extends TestBase
{

	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestHtmlUtil.class);

	// ========================= TESTING METHODS ===========================

	/**
	 * Test #1 - building a URL from parts.
	 */
	@Test
	public void testUrlBuild1()
	{
		// Make sure parameter ordering is deterministic
		Map<String, String> parameters = Collections
				.synchronizedMap(new TreeMap<String, String>());
		parameters.put("d-12345", "1");
		parameters.put("d-12345", "2");
		parameters.put("d-23456", "a b c");
		parameters.put("d-34567", null);
		String uri = "/dir1/dir2/dir3/servlet";
		String expectedUrl = "/dir1/dir2/dir3/servlet?d-12345=2&d-23456=a+b+c";
		String actualUrl = HtmlUtil.buildUrl(uri, parameters);

		logger.info("expectedUrl = " + expectedUrl);
		logger.info("actualUrl   = " + actualUrl);
		Assert.assertEquals(expectedUrl, actualUrl);
	}

	/**
	 * Test #2 - building a URL from parts. Here the URI already contains some
	 * parameters.
	 */
	@Test
	public void testUrlBuild2()
	{
		// Make sure parameter ordering is deterministic
		Map<String, String> parameters = Collections
				.synchronizedMap(new TreeMap<String, String>());
		parameters.put("d-12345", "1");
		parameters.put("d-12345", "2");
		parameters.put("d-23456", "a b c");
		parameters.put("d-34567", null);
		String uri = "/dir1/dir2/dir3/servlet?aaa=bbb";
		String expectedUrl = "/dir1/dir2/dir3/servlet?aaa=bbb&d-12345=2&d-23456=a+b+c";
		String actualUrl = HtmlUtil.buildUrl(uri, parameters);

		logger.info("expectedUrl = " + expectedUrl);
		logger.info("actualUrl   = " + actualUrl);
		Assert.assertEquals(expectedUrl, actualUrl);
	}
}
