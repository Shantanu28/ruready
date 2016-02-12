/*****************************************************************************************
 * Source File: ArrayUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import net.ruready.common.misc.Utility;

/**
 * Utilities related to logical expressions and assertions.
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
public class LogicalUtil implements Utility
{
	// ========================= CONSTANTS =================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private LogicalUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Does <code>a</code> imply <code>b</code>. This is equivalent to
	 * <code>!a || b</code>.
	 * 
	 * @param a
	 *            reason
	 * @param b
	 *            result
	 * @return true if and only if code>a</code> implies <code>b</code>
	 */
	public static boolean implies(final boolean a, final boolean b)
	{
		return !a || b;
	}
}
