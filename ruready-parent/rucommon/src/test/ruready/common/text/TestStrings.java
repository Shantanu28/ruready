/*****************************************************************************************
 * Source File: TestStrings.java
 ****************************************************************************************/
package test.ruready.common.text;

import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.ArrayUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tests related to String functions.
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
public class TestStrings extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestStrings.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test splitting a string into two parts.
	 */
	@Test
	public void testSplit1()
	{
		String s = "foo:bar";
		String[] expectedParts =
		{ "foo", "bar" };
		String[] actualParts = s.split(CommonNames.VARIABLE.SEPARATOR);
		boolean equals = ArrayUtil.arrayEquals(expectedParts, actualParts);

		logger.info("expectedParts = " + ArrayUtil.arrayToString(expectedParts));
		logger.info("actualParts   = " + ArrayUtil.arrayToString(actualParts));
		logger.info("equals        = " + equals);
		Assert.assertEquals(true, equals);
	}

	/**
	 * Test splitting a string with a trailing regexp.
	 */
	@Test
	public void testSplit2()
	{
		String s = "foo:";
		String[] expectedParts =
		{ "foo" };
		String[] actualParts = s.split(CommonNames.VARIABLE.SEPARATOR);
		boolean equals = ArrayUtil.arrayEquals(expectedParts, actualParts);

		logger.info("expectedParts = " + ArrayUtil.arrayToString(expectedParts));
		logger.info("actualParts   = " + ArrayUtil.arrayToString(actualParts));
		logger.info("equals        = " + equals);
		Assert.assertEquals(true, equals);
	}

	/**
	 * Test splitting a string into at most two parts.
	 */
	@Test
	public void testSplit3()
	{
		String s = "foo:and:bar";
		String[] expectedParts =
		{ "foo", "and:bar" };
		String[] actualParts = s.split(CommonNames.VARIABLE.SEPARATOR, 2);
		boolean equals = ArrayUtil.arrayEquals(expectedParts, actualParts);

		logger.info("expectedParts = " + ArrayUtil.arrayToString(expectedParts));
		logger.info("actualParts   = " + ArrayUtil.arrayToString(actualParts));
		logger.info("equals        = " + equals);
		Assert.assertEquals(true, equals);
	}

	/**
	 * Test splitting a string into at most two parts separated by a space, and
	 * parseing the first part into an integer.
	 */
	@Test
	public void testSplit4()
	{
		int a = 1;
		String part2 = "b { c { } }";
		String s = a + " " + part2;
		String[] actualParts = s.split(" ", 2);
		Assert.assertEquals(a, TextUtil.getStringAsInteger(actualParts[0]));
		Assert.assertEquals(part2, actualParts[1]);
	}

	/**
	 * Test regular expression matching.
	 */
	@Test
	public void testRegularExpressions()
	{
		String name = "Name";
		String regExp = name + "(\\s\\[[0-9]*\\])?";
		String str0 = CommonNames.MISC.EMPTY_STRING;
		String str1 = "Name [1]";
		String str2 = "Name [2]";
		String str3 = "Name [20]";
		String str4 = "Name [2a]";
		String str5 = "Name 2a";
		String str6 = "Name";
		String str7 = "Name ";
		String str8 = "name ";
		String str9 = "name [1]";

		logger.info("regExp = " + regExp);
		logger.info("str0 = '" + str0 + "' matches? " + str0.matches(regExp));
		logger.info("str1 = '" + str1 + "' matches? " + str1.matches(regExp));
		logger.info("str2 = '" + str2 + "' matches? " + str2.matches(regExp));
		logger.info("str3 = '" + str3 + "' matches? " + str3.matches(regExp));
		logger.info("str4 = '" + str4 + "' matches? " + str4.matches(regExp));
		logger.info("str5 = '" + str5 + "' matches? " + str5.matches(regExp));
		logger.info("str6 = '" + str6 + "' matches? " + str6.matches(regExp));
		logger.info("str7 = '" + str7 + "' matches? " + str7.matches(regExp));
		logger.info("str8 = '" + str8 + "' matches? " + str8.matches(regExp));
		logger.info("str9 = '" + str9 + "' matches? " + str9.matches(regExp));

		String reg = name + " \\[";
		logger.info("str1 parts1 = " + str1.split(reg));
	}

	// ========================= TESTING ====================================
}
