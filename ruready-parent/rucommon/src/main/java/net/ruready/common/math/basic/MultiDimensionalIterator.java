/*******************************************************************************
 * Source File: MultiDimensionalIterator.java
 ******************************************************************************/
package net.ruready.common.math.basic;

import java.util.Iterator;

import net.ruready.common.exception.SystemException;
import net.ruready.common.util.ArrayUtil;

/**
 * An d-dimensional subscript iterator. This is useful when looping over a
 * volume or an area. Supports both periodic looping and one-time looping
 * (reaches EOF when the "upper-left" vertex of the cube is reached).
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Apr 20, 2007
 */
public class MultiDimensionalIterator implements Iterator<int[]>
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	private int[] lower;

	private int[] upper;

	private int[] sub;

	private boolean periodic = false;

	private int numDims;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize a multi-dimensional iterator
	 * 
	 * @param lower
	 *            lower-left corner of box (in d dimensions)
	 * @param upper
	 *            upper-right corner of box (in d dimensions)
	 * @param sub
	 *            initial subscript in the box
	 * @param periodic
	 *            if true, loops periodically over the box
	 */
	public MultiDimensionalIterator(int[] lower, int[] upper, int[] sub,
			boolean periodic)
	{
		if ((lower == null) || (upper == null) || (sub == null)) {
			throw new IllegalArgumentException(
					"lower, upper, sub must all be non-null");
		}
		if ((lower.length != upper.length) || (upper.length != sub.length)) {
			throw new IllegalArgumentException(
					"lower, upper, sub must have the same length");
		}

		this.lower = lower;
		this.upper = upper;
		this.sub = sub;
		this.periodic = periodic;

		this.numDims = lower.length;
	}

	/**
	 * Initialize a multi-dimensional iterator
	 * 
	 * @param lower
	 *            lower-left corner of box (in d dimensions)
	 * @param upper
	 *            upper-right corner of box (in d dimensions)
	 * @param sub
	 *            initial subscript in the box
	 */
	public MultiDimensionalIterator(int[] lower, int[] upper, int[] sub)
	{
		this(lower, upper, sub, false);
	}

	/**
	 * Initialize a multi-dimensional iterator
	 * 
	 * @param lower
	 *            lower-left corner of box (in d dimensions)
	 * @param upper
	 *            upper-right corner of box (in d dimensions)
	 * @param periodic
	 *            if true, loops periodically over the box
	 */
	public MultiDimensionalIterator(int[] lower, int[] upper, boolean periodic)
	{
		this(lower, upper, ArrayUtil.clone(lower), periodic);
	}

	/**
	 * Initialize a multi-dimensional iterator
	 * 
	 * @param lower
	 *            lower-left corner of box (in d dimensions)
	 * @param upper
	 *            upper-right corner of box (in d dimensions)
	 */
	public MultiDimensionalIterator(int[] lower, int[] upper)
	{
		this(lower, upper, ArrayUtil.clone(lower), false);
	}

	// ========================= IMPLEMENTATION: Iterator ==================

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext()
	{
		// Periodic looping ==> always true
		if (periodic) {
			return true;
		}
		// Create a copy so that we don't override the sub iterator
		int[] newSub = ArrayUtil.clone(sub);
		int d = 0;
		newSub[d]++;
		if (newSub[d] > upper[d]) {
			while (newSub[d] > upper[d]) {
				newSub[d] = lower[d];
				d++;
				if (d == numDims) { // end of box reached
					newSub[0] -= 1; // So that iter is now Box::end() that is
					// outside the box
					return false;
				}
				newSub[d]++;
			}
		}
		return true;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public int[] next()
	{
		int d = 0;
		sub[d]++;
		if (sub[d] > upper[d]) {
			while (sub[d] > upper[d]) {
				sub[d] = lower[d];
				d++;
				if (d == numDims) { // end of box reached
					if (periodic) {
						// Return to beginning of box
						sub = ArrayUtil.clone(lower);
					}
					else {
						sub[0] -= 1; // So that iter is now Box::end() that
						// is
						// outside the box
					}
					return sub;
				}
				sub[d]++;
			}
		}
		return sub;
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove()
	{
		throw new SystemException(
				"Multi-dimensional looping cannot remove elements");
	}

}
