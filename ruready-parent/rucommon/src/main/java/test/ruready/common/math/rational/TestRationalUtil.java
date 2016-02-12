/*****************************************************************************************
 * Source File: TestRationalUtil.java
 ****************************************************************************************/
package test.ruready.common.math.rational;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.math.rational.Rational;
import net.ruready.common.math.rational.RationalUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test procedures related to rational arithmetic.
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
public class TestRationalUtil extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestRationalUtil.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test the interval arithmetic methods.
	 */
	@Test
	public void testRationalArithmetic()
	{
		// Rational approximations
		logger.info("=========== RATIONAL APPROXIMATIONS =============");
		Assert.assertEquals(new Rational(4, 3), RationalUtil.roundDoubleToRational(
				1.33333333333, 1e-2)); // 4/3
		Assert.assertEquals(new Rational(355, 113), RationalUtil.roundDoubleToRational(
				Math.PI, 1e-5)); // 355/113
		Assert.assertEquals(new Rational(1264, 465), RationalUtil.roundDoubleToRational(
				Math.E, 1e-5)); // 1264/465

		// poitive modulus test
		logger.info("=========== POSITIVE MODULUS =============");
		Assert.assertEquals(0, RationalUtil.positiveMod(-30, 10));
		Assert.assertEquals(1, RationalUtil.positiveMod(-29, 10));
		Assert.assertEquals(0, RationalUtil.positiveMod(0, 10));
		Assert.assertEquals(8, RationalUtil.positiveMod(8, 10));
		Assert.assertEquals(0, RationalUtil.positiveMod(10, 10));
		Assert.assertEquals(1, RationalUtil.positiveMod(11, 10));

		// digits
		logger.info("=========== DIGITS =============");
		testDigits(0, 10, 0);
		testDigits(123, 10, 3, 2, 1);
		testDigits(-123, 10, 3, 2, 1);
		testDigits(16, 16, 0, 1);
		testDigits(256, 16, 0, 0, 1);
		testDigits(100, 16, 4, 6);
	}

	/**
	 * @param number
	 * @param base
	 * @param digits
	 */
	private void testDigits(int number, int base, Integer... digits)
	{
		List<Integer> expected = new ArrayList<Integer>();
		for (Integer digit : digits)
		{
			expected.add(digit);
		}
		List<Integer> actual = RationalUtil.digits(number, base);
		logger.info("digits(" + base + ", " + number + ") actual " + actual
				+ " expected " + expected);
		Assert.assertEquals(expected, actual);
	}

	// ========================= TESTING ====================================
}
