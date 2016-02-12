/*****************************************************************************************
 * Source File: TestInterval.java
 ****************************************************************************************/
package test.ruready.common.math.basic;

import net.ruready.common.math.basic.SimpleInterval;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test procedures related to the arithmetic parser (tokenizer, arithmetic
 * compiler + matcher) on strings.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 26, 2007
 */
public class TestInterval extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestInterval.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test {@link SimpleInterval}'s arithmetic methods.
	 */
	@Test
	public void testSimpleIntervalArithmetic()
	{
		SimpleInterval<Integer> a = new SimpleInterval<Integer>(15, 20);
		SimpleInterval<Integer> b = new SimpleInterval<Integer>(25, 30);
		SimpleInterval<Integer> c = new SimpleInterval<Integer>(10, 40);
		SimpleInterval<Integer> d = new SimpleInterval<Integer>(40, 50);

		logger.info("a = " + a);
		logger.info("b = " + b);
		logger.info("c = " + c);
		logger.info("d = " + d);

		Assert.assertEquals(false, b.intersects(a));
		Assert.assertEquals(false, a.intersects(b));
		Assert.assertEquals(true, a.intersects(c));
		Assert.assertEquals(false, a.intersects(d));
		Assert.assertEquals(true, b.intersects(c));
		Assert.assertEquals(false, b.intersects(d));
		Assert.assertEquals(true, c.intersects(d));
	}

	// ========================= TESTING ====================================
}
