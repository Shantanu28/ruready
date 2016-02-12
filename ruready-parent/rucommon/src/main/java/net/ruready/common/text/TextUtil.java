/*****************************************************************************************
 * Source File: TextUtil.java
 ****************************************************************************************/
package net.ruready.common.text;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities related to strings and text processing.
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
public class TextUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TextUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private TextUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Is object a string.
	 * 
	 * @param o
	 *            object
	 * @return is object a string
	 */
	public static boolean isString(final Object o)
	{
		if (o == null)
		{
			return (true);
		}
		return (String.class.isInstance(o));
	}

	/**
	 * Returns <code>true</code> iff a string is null or equals the empty
	 * string (no trimming is appleid).
	 * 
	 * @param low
	 *            lower bound (inclusive)
	 * @param high
	 *            upper bound (inclusive)
	 * @return random interger in [low:high]
	 */
	public static boolean isEmptyString(final String s)
	{
		return (s == null) || CommonNames.MISC.EMPTY_STRING.equals(s);
	}

	/**
	 * Returns <code>true</code> iff a string is null or equals the empty
	 * string after trimming.
	 * 
	 * @param low
	 *            lower bound (inclusive)
	 * @param high
	 *            upper bound (inclusive)
	 * @return random interger in [low:high]
	 */
	public static boolean isEmptyTrimmedString(final String s)
	{
		return (s == null) || CommonNames.MISC.EMPTY_STRING.equals(s.trim());
	}

	/**
	 * Is a value a valid ID or not.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isValidId(final Long value)
	{
		return (value != CommonNames.MISC.INVALID_VALUE_LONG);
	}

	/**
	 * Convert a {@link String} to {@link Integer}.
	 * 
	 * @param value
	 *            string value
	 * @return integer value. If fail to convert, returns
	 *         <code>INVALID_VALUE_INTEGER</code>
	 */
	public static int getStringAsInteger(final String value)
	{
		try
		{
			return Integer.parseInt(value);
		}
		catch (Exception e)
		{
			return CommonNames.MISC.INVALID_VALUE_INTEGER;
		}
	}

	/**
	 * Convert a {@link String} to {@link Long}.
	 * 
	 * @param value
	 *            string value
	 * @return long value. If fail to convert, returns
	 *         <code>INVALID_VALUE_INTEGER</code>
	 */
	public static long getStringAsLong(final String value)
	{
		try
		{
			return Long.parseLong(value);
		}
		catch (Exception e)
		{
			return CommonNames.MISC.INVALID_VALUE_LONG;
		}
	}

	/**
	 * Convert a {@link String} to {@link Boolean}.
	 * 
	 * @param value
	 *            string value
	 * @return boolean value. If fail to convert, returns <code>false</code>
	 */
	public static boolean getStringAsBoolean(final String value)
	{
		try
		{
			return Boolean.parseBoolean(value);
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Return an empty string buffer.
	 * 
	 * @return
	 */
	public static StringBuffer emptyStringBuffer()
	{
		return new StringBuffer(CommonNames.MISC.EMPTY_STRING);
	}
}
