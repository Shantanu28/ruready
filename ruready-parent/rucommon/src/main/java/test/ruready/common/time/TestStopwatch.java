/*****************************************************************************************
 * Source File: TestStopwatch.java
 ****************************************************************************************/
package test.ruready.common.time;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

import com.commsen.stopwatch.Report;
import com.commsen.stopwatch.Stopwatch;

/**
 * Tests the Stopwatch timing library.
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
 * @version Sep 8, 2007
 */
public class TestStopwatch extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestStopwatch.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test some loop timing functions of the Stopwatch library.
	 */
	@Test
	public void testLoopTiming()
	{
		Stopwatch.setActive(true);
		for (int count = 0; count < 21; count++)
		{
			long id = Stopwatch.start("my group", "my label");
			// some code to be measured
			Stopwatch.stop(id);
		}

		Report report = Stopwatch.getSingleReport("my group", "my label");
		logger.debug(report);
		Assert.assertNotNull(report);
	}

	// ========================= PRIVATE METHODS ===========================

	// ========================= TESTING ====================================
}
