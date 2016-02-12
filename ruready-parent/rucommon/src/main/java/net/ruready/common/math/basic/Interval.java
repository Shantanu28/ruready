/*****************************************************************************************
 * Source File: Interval.java
 ****************************************************************************************/
package net.ruready.common.math.basic;

import net.ruready.common.pointer.PubliclyCloneable;

/**
 * An interval [a,b], where a and b are integers. Immutable.
 * <p>
 * WARNING: the parameter class D must be immutable for this class to properly be cloned
 * (and serve as part of a parser's target).
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 2, 2007
 */
public interface Interval<D extends Comparable<? super D>, T extends Interval<D, T>>
		extends PubliclyCloneable, Comparable<T>, TolerantlyComparable<T>
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Does this interval a intersect another interval
	 * 
	 * @param b
	 *            another interval
	 * @return true iff this intersects b
	 */
	boolean intersects(T b);

	/**
	 * @return Returns the upper bound of the interval.
	 */
	D getHigh();

	/**
	 * @return Returns the lower bound of the interval.
	 */
	D getLow();
}
