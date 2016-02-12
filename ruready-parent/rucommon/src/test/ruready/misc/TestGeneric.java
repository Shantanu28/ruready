/*****************************************************************************************
 * Source File: TestStopwatch.java
 ****************************************************************************************/
package test.ruready.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

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
public class TestGeneric extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestGeneric.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Get the class of a generic object, then try to instantiate a new by its
	 * class.
	 */
	@SuppressWarnings(
	{ "cast", "unchecked" })
	@Test
	public void testReflection() throws Exception
	{
		List<Integer> object = new ArrayList<Integer>();
		logger.debug("object " + object);
		logger.debug("" + object.getClass());

		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		List<Integer> newObject = (List<Integer>) object.getClass().getConstructor(
				Collection.class).newInstance(list);
		logger.debug("newObject " + newObject);

		List<Double> badList = new ArrayList<Double>();
		badList.add(2.0);
		List<Integer> badObject = (List<Integer>) object.getClass().getConstructor(
				Collection.class).newInstance(badList);
		logger.debug("badObject " + badObject);

		// badObject started as a list of integers, but now is a list of
		// doubles.
		// Don't expect its ELEMENTS to stay integer!
		try
		{
			badObject.get(0);
			Assert.assertEquals(Double.class, badObject.get(0).getClass());

			// If ClassCastException not caught above, that's bad
			throw new IllegalStateException("ClassCastException not caught");
		}
		catch (ClassCastException e)
		{
			// Expected to be caught
		}
	}

	/**
	 * Test whether the assignment operation returns the value of the assigned
	 * operand.
	 */
	@Test
	public void testWhile()
	{
		int i;
		String s = "arrrbcdeaaaab";
		logger.info("s = " + s);
		logger.info("result of assignment = " + (i = s.indexOf('b')));
		while ((i = s.indexOf('b')) >= 0)
		{
			s = s.substring(i + 1, s.length());
			logger.info("s = " + s);
		}
	}

	// ========================= PRIVATE METHODS ===========================
}
