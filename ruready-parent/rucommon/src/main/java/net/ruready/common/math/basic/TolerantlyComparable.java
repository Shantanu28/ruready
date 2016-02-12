/*****************************************************************************************
 * Source File: TolerantlyComparable.java
 ****************************************************************************************/
package net.ruready.common.math.basic;

/**
 * An object that can be compared with another object up to a finite tolerance.
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
 * @version Jul 17, 2007
 */
public interface TolerantlyComparable<T extends TolerantlyComparable<T>>
{
	// ========================= CONSTANTS =================================

	/**
	 * Signals that tolerant equality cannot be determined. Happens for instance when the
	 * quantities are both infinity, or both NaN.
	 */
	static final int INDETERMINATE = -1;

	/**
	 * Signals that tolerant equality holds.
	 */
	static final int EQUAL = 0;

	/**
	 * Signals that tolerant equality does not hold.
	 */
	static final int NOT_EQUAL = 1;

	/**
	 * This indicates that it's infinity vs. -infinity, infinity vs. nan or nan vs.
	 * -infinity, the quantities are different (compare re part vs. re part, im part vs.
	 * im part).
	 */
	static final int NOT_EQUAL_WHEN_NAN = 2;

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * The result of equality of two evaluable objects up to a tolerance. This checks for
	 * mathematical equivalence of expressions, without analyzing elements or comparing
	 * syntax trees.
	 * 
	 * @param other
	 *            an evaluable object
	 * @param tol
	 *            tolerance of equality, if we compare to a finite precision (for n digits
	 *            of accuracy, use tol = 10^{-n}).
	 * @return the result of tolerant equality of two evaluable quantities. Returns
	 *         {@link #EQUAL} if they are tolerantly equal; returns {@link #INDETERMINATE}
	 *         if tolerant equality cannot be returned; otherwise, returns a number that
	 *         is different from both constants, e.g., {@link #NON_EQUAL}.
	 */
	int tolerantlyEquals(T other, double tol);
}
