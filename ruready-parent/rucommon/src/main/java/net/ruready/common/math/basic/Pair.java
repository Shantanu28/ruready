/*****************************************************************************************
 * Source File: Pair.java
 ****************************************************************************************/
package net.ruready.common.math.basic;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and Continuing
 *         Education (AOCE) 1901 East South Campus Dr., Room 2197-E (c) 2006-07 Continuing
 *         Education , University of Utah . All copyrights reserved. U.S. Patent Pending
 *         DOCKET NO. 00846 25702.PROV
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E, University of
 *         Utah University of Utah, Salt Lake City, UT 84112 (c) 2006-07 Continuing
 *         Education , University of Utah . All copyrights reserved. U.S. Patent Pending
 *         DOCKET NO. 00846 25702.PROV
 * @version 10/01/2006 A pair of comparable objects.
 */
public class Pair<A extends Comparable<A>, B extends Comparable<B>> implements
		Comparable<Pair<A, B>>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	public static final int LEFT = 0;

	public static final int RIGHT = 1;

	// ========================= FIELDS ====================================

	private A left;

	private B right;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param left
	 * @param right
	 */
	public Pair(A left, B right)
	{
		this.left = left;
		this.right = right;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((left == null) ? 0 : left.hashCode());
		result = PRIME * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	/**
	 * Compare two Pairs based on data.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Pair<?, ?> other = (Pair<?, ?>) obj;
		if (left == null)
		{
			if (other.left != null)
				return false;
		}
		else if (!left.equals(other.left))
			return false;
		if (right == null)
		{
			if (other.right != null)
				return false;
		}
		else if (!right.equals(other.right))
			return false;
		return true;
	}

	// ========================= IMPLEMENTATION: Comparable<Pair> ==========

	/**
	 * Compare two pairs by lexicographic order (first in, left, then in right).
	 * Compatible with the <code>equals()</code> method if and only if the
	 * <code>compareTo()</code> method in types <code>A</code> and <code>B</code>
	 * are Compatible with the corresponding <code>equals()</code> methods therein.
	 * 
	 * @param other
	 *            the ther <code>Pairs</code> to be compared with this one.
	 * @return the result of comparison
	 */
	public int compareTo(Pair<A, B> other)
	{
		// Compare lefts
		int compareLeft = (left == null) ? ((other.left == null) ? 0 : -1) : left
				.compareTo(other.left);
		if (compareLeft != 0)
		{
			return compareLeft;
		}

		// Compare rights
		int compareRight = (right == null) ? ((other.right == null) ? 0 : -1) : right
				.compareTo(other.right);
		if (compareRight != 0)
		{
			return compareRight;
		}

		// Objects are equal
		return 0;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return Returns the left.
	 */
	public A getLeft()
	{
		return left;
	}

	/**
	 * @param left
	 *            The left to set.
	 */
	public void setLeft(A left)
	{
		this.left = left;
	}

	/**
	 * @return Returns the right.
	 */
	public B getRight()
	{
		return right;
	}

	/**
	 * @param right
	 *            The right to set.
	 */
	public void setRight(B right)
	{
		this.right = right;
	}

	/**
	 * @param left
	 *            The left to set.
	 * @param right
	 *            The right to set.
	 */
	public void set(A left, B right)
	{
		this.left = left;
		this.right = right;
	}

	// ################## METHODS #######################
	/**
	 * Print a pair.
	 */
	@Override
	public String toString()
	{
		return "(" + left + "," + right + ")";
	}
}
