/*****************************************************************************************
 * Source File: Interval.java
 ****************************************************************************************/
package net.ruready.common.math.basic;

import net.ruready.common.util.HashCodeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An interval [a,b], where a and b are integers. Immutable.
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
 * @version Jul 27, 2007
 */
public class SimpleInterval<D extends Comparable<? super D>> implements
		Interval<D, SimpleInterval<D>>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SimpleInterval.class);

	// ========================= FIELDS ====================================

	/**
	 * Lower bound of the interval.
	 */
	protected final D low;

	/**
	 * Upper bound of the interval.
	 */
	protected final D high;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct an interval from bounds.
	 * 
	 * @param low
	 *            Lower bound of the interval
	 * @param high
	 *            Upper bound of the interval
	 */
	public SimpleInterval(final D low, final D high)
	{
		if (low.compareTo(high) <= 0)
		{
			this.low = low;
			this.high = high;
		}
		else
			throw new IllegalArgumentException("Illegal interval");
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print an interval.
	 */
	@Override
	public String toString()
	{
		return "[" + low + "," + high + "]";
	}

	/**
	 * Must be overridden when <code>equals()</code> is.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode()
	{
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, low);
		result = HashCodeUtil.hash(result, high);
		return result;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to
	 * serve as a target of an assembly.
	 * <p>
	 * WARNING: the parameter class E must be immutable for this class to
	 * properly be cloned (and serve as part of a parser's target).
	 * 
	 * @return a deep copy of this object.
	 */
	@Override
	public SimpleInterval<D> clone()
	{
		return new SimpleInterval<D>(low, high);
	}

	// ========================= IMPLEMENTATION: Comparable ================

	/**
	 * Result of comparing two intervals [a,b], [c,d]. They are equal if and
	 * only if a=c and b=d. [a,b] is less than [c,d] if a &lt; c or if a = c and
	 * b &lt; d. Otherwise, [a,b] is greater than [c,d] (lexicographic
	 * ordering).
	 * 
	 * @param obj
	 *            the other <code>Interval</code>
	 * @return the result of equality.
	 */
	public int compareTo(SimpleInterval<D> other)
	{
		if (low.equals(other.getLow()) && high.equals(other.getHigh()))
		{
			return 0;
		}

		return (((low.compareTo(other.getLow()) < 0) || (low.equals(other.getLow()) && high
				.compareTo(other.getHigh()) < 0))) ? -1 : 1;
	}

	/**
	 * Result of equality of two intervals. They are equal if and only if their
	 * <code>low,high</code> fields are equal up to a relative tolerance of
	 * 1e-16.
	 * 
	 * @param o
	 *            The other <code>Interval</code> object.
	 * @return boolean The result of equality.
	 */
	@Override
	public boolean equals(Object obj)
	{
		// Standard checks to ensure the adherence to the general contract of
		// the equals() method
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass()))
		{
			return false;
		}
		// Cast to friendlier version
		SimpleInterval<?> other = (SimpleInterval<?>) obj;

		// Do not use compareTo() == 0 for a generic type because of unchecked
		// warning
		return (low.equals(other.getLow()) && high.equals(other.getHigh()));
	}

	// ========================= IMPLEMENTATION: TolerantlyComparable ======

	/**
	 * Result of equality of two interval. They are equal if and only if their
	 * <code>low,high</code> fields are equal up to a relative tolerance of
	 * 1e-16, regardless of <code>tol</code>. This is an implementation of
	 * the <code>TolerantlyComparable</code> interface.
	 * 
	 * @param obj
	 *            The other <code>Interval</code> object.
	 * @param tol
	 *            tolerance of equality; ignored
	 * @return the result of equality
	 */
	public int tolerantlyEquals(SimpleInterval<D> obj, double tol)
	{
		return (this.equals(obj) ? TolerantlyComparable.EQUAL
				: TolerantlyComparable.NOT_EQUAL);
	}

	// ========================= PUBLIC METHODS ============================

	/**
	 * Does this interval a intersect another interval
	 * 
	 * @param b
	 *            another interval
	 * @return true iff this intersects b
	 */
	public boolean intersects(SimpleInterval<D> b)
	{
		if (((b.getLow().compareTo(high) <= 0 && b.getLow().compareTo(low) >= 0))
				|| ((low.compareTo(b.getHigh()) <= 0 && low.compareTo(b.getLow()) >= 0)))
		{
			return true;
		}
		return false;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return Returns the high.
	 */
	public D getHigh()
	{
		return high;
	}

	/**
	 * @return Returns the low.
	 */
	public D getLow()
	{
		return low;
	}
}
