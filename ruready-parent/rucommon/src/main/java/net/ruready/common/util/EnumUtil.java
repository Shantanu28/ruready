/*****************************************************************************************
 * Source File: EnumUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities related to enumerated types and in particular to type conversions.
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
 * @version Jul 29, 2007
 */
public class EnumUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EnumUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private EnumUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Returns the enum constant of the specified enum type with the specified name. The
	 * name must match exactly an identifier used to declare an enum constant in this
	 * type. If it does not, this method returns <code>null</code>.
	 * 
	 * @param enumType
	 *            the Class object of the enum type from which to return a constant
	 * @param name
	 *            the name of the constant to return
	 * @return the enum constant of the specified enum type with the specified name or
	 *         <code>null</code> if not found
	 * @see {link Enum.valueOf}
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name)
	{
		try
		{
			return Enum.valueOf(enumType, name);
		}
		catch (IllegalArgumentException e)
		{
			return null;
		}
		catch (NullPointerException e)
		{
			return null;
		}
	}

	/**
	 * Create an enumerated type from string representation (factory method).
	 * 
	 * @param enumType
	 *            the Class object of the enum type from which to return a constant
	 * @param s
	 *            string to match
	 * @return enum constant whose <code>toString()</code> matches s
	 * @see {link Enum.valueOf}
	 */
	public static <T extends Enum<T>> T createFromString(Class<T> enumType, String s)
	{
		if (s == null)
		{
			return null;
		}
		for (T t : enumType.getEnumConstants())
		{
			if (s.equals(t.toString()))
			{
				return t;
			}
		}
		return null;
	}
}
