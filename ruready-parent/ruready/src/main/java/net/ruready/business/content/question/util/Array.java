/*****************************************************************************************
 * Source File: Array.java
 ****************************************************************************************/
package net.ruready.business.content.question.util;

import java.util.Vector;
import java.util.Collection;

/**
 * Our own implementation of a <i>synchronized</i> <code>Vector</code> with a
 * <code>setSize()</code> method. Publicly cloneable. Imported from RU1.
 */
/**
 * Long description ...
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
 * @version Aug 12, 2007
 */
@Deprecated
public class Array<E> extends Vector<E>
{
	// Until further notice, this is
	// a synchronized Vector. Later on, switch
	// to a synchronized ArrayList, if profitable.

	// ################## CONSTANTS #######################
	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1;

	// ################## FIELDS #######################
	// Holds the size, for JSP expression access
	protected int sz;

	// ################## CONSTRUCTORS #######################
	/**
	 * Constructs an empty list with an initial capacity of ten.
	 */
	public Array()
	{
		super();
	}

	/**
	 * Constructs a list containing the elements of the specified collection, in the order
	 * they are returned by the collection's iterator. The <tt>ArrayList</tt> instance
	 * has an initial capacity of 110% the size of the specified collection.
	 * 
	 * @param c
	 *            the collection whose elements are to be placed into this list.
	 * @throws NullPointerException
	 *             if the specified collection is null.
	 */
	public Array(Collection<? extends E> c)
	{
		super(c);
	}

	/**
	 * Constructs an empty list with the specified initial capacity.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of the list.
	 * @exception IllegalArgumentException
	 *                if the specified initial capacity is negative
	 */
	public Array(int initialCapacity)
	{
		super(initialCapacity);
	}

	// ################## METHODS #######################
	/**
	 * Sets the size of this list. If the new size is greater than the current size, new
	 * <code>null</code> items are added to the end of the vector. If the new size is
	 * less than the current size, all components at index <code>newSize</code> and
	 * greater are discarded.
	 * 
	 * @param newSize
	 *            the new size of this list.
	 * @throws ArrayIndexOutOfBoundsException
	 *             if new size is negative.
	 */
	/*
	 * =========== DISABLED FOR NOW =============== if we move to ArrayList instead of
	 * Vector. public synchronized void setSz(int newSize) { int oldSize = size();
	 * ensureCapacity(newSize); if (newSize < oldSize) { removeRange(newSize, oldSize); }
	 * else { for (int i = oldSize; i < newSize; i++) { add(null); } } }
	 */

	/**
	 * Returns the size of the <code>Vector</code> (not necessarily the size of the
	 * internal Object[] array, that is set by the <code>setSize()</code> methods. For
	 * some reason the original collection uses the method size(), which is incompatible
	 * with JSP.
	 * 
	 * @return size of the array
	 */
	public int getSz()
	{
		return size();
	}

	// ################## STATIC METHODS #######################
	/**
	 * Returns an object in an object array of size 1.
	 * 
	 * @param object
	 *            object to return in an array
	 * @return the object in an object array of size 1
	 */
	public static Object[] toObjectArray(Object object)
	{
		Object[] temp = new Object[1];
		temp[0] = object;
		return temp;
	}
}
