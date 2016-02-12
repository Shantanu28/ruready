/*****************************************************************************************
 * Source File: Triplet.java
 ****************************************************************************************/
package net.ruready.common.draw;

import java.io.Serializable;

import net.ruready.common.util.HashCodeUtil;

/**
 * A triplet of integers.
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
 * @version Jul 16, 2007
 */
public final class Triplet implements Serializable, Comparable<Triplet>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1;

	// ========================= FIELDS ====================================

	private int x;

	private int y;

	private int z;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an <code>(x,y,z)</code> triplet.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Triplet(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a triplet.
	 */
	@Override
	public String toString()
	{
		return "(" + x + "," + y + "," + z + ")";
	}

	/**
	 * Must be overridden when <code>equals()</code> is.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, x);
		result = HashCodeUtil.hash(result, y);
		result = HashCodeUtil.hash(result, z);
		return result;
	}

	// ========================= IMPLEMENTATION: Comparable ================

	/**
	 * Compare two pairs by lexicographic order (first in, x, then in y, then in z).
	 * Consitent with <code>equals()</code>.
	 * 
	 * @param other
	 *            the ther <code>Triplets</code> to be compared with this one.
	 */
	public int compareTo(Triplet other)
	{
		int otherX = other.getX();
		if (x < otherX)
		{
			return -1;
		}
		if (x > otherX)
		{
			return 1;
		}

		int otherY = other.getY();
		if (y < otherY)
		{
			return -1;
		}
		if (y > otherY)
		{
			return 1;
		}

		int otherZ = other.getZ();
		if (z < otherZ)
		{
			return -1;
		}
		if (z > otherZ)
		{
			return 1;
		}

		// All fields are equal
		return 0;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
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
		Triplet other = (Triplet) obj;

		return (this.compareTo(other) == 0);
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return Returns the x.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x
	 *            The x to set.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return Returns the y.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y
	 *            The y to set.
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * @return Returns the z.
	 */
	public int getZ()
	{
		return z;
	}

	/**
	 * @param z
	 *            The z to set.
	 */
	public void setZ(int z)
	{
		this.z = z;
	}

	/**
	 * @param x
	 *            The x to set.
	 * @param y
	 *            The y to set.
	 * @param z
	 *            The z to set.
	 */
	public void set(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
