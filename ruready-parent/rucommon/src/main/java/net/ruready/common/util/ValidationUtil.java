/*****************************************************************************************
 * Source File: ArrayUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import net.ruready.common.misc.Utility;

/**
 * Utilities related to validating strings against standard rules commonly used by a web
 * layer, e.g., integer range.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 19, 2007
 */
public class ValidationUtil implements Utility
{
	// ========================= CONSTANTS =================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private ValidationUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Return text value of a text.
	 * 
	 * @param text
	 *            input text
	 * @return integer value of text
	 * @throws NumberFormatException
	 *             if text is not an integer
	 * @throws NullPointerException
	 *             if text is <code>null</code>
	 */
	public static int toInteger(final String text)
	{
		return Integer.parseInt(text);
	}

	/**
	 * Return <code>true</code> if and only if <code>text</code> is an integer.
	 * 
	 * @param text
	 *            input text
	 * @return is text an integer
	 */
	public static boolean validateInteger(final String text)
	{
		// Format check
		try
		{
			ValidationUtil.toInteger(text);
		}
		catch (Exception e)
		{
			return false;
		}
		
		return true;
	}

	/**
	 * Return <code>true</code> if and only if <code>text</code> is an integer between
	 * <code>low</code> and <code>high</code>.
	 * 
	 * @param text
	 *            input text
	 * @param low
	 *            lower bound
	 * @param high
	 *            upper bound
	 * @return is text and integer and in the range <code>[low,high]</code>
	 */
	public static boolean validateIntRange(final String text, final int low,
			final int high)
	{
		// Format check
		int value = 0;
		try
		{
			value = ValidationUtil.toInteger(text);
		}
		catch (Exception e)
		{
			return false;
		}

		// Range check
		if ((value < low) || (value > high))
		{
			return false;
		}

		return true;
	}
}
