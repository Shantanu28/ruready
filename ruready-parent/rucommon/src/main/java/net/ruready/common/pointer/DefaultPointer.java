/*******************************************************
 * Source File: DefaultPointer.java
 *******************************************************/
/**
 * File: DefaultPointer.java
 */
package net.ruready.common.pointer;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah
 * 
 * University of Utah, Salt Lake City, UT 84112 Protected by U.S. Provisional
 * Patent U-4003, February 2006
 * 
 * @version Feb 17, 2007
 * 
 * A simple default implementation of a pointer (handle) to an object.
 */

public class DefaultPointer<E> implements Pointer<E>
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	// Object to be referenced
	private E value = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a pointer with a <code>null</code> reference.
	 */
	public DefaultPointer()
	{

	}

	/**
	 * Construct a pointer to an object
	 * 
	 * @param value
	 *            object to be referenced
	 */
	public DefaultPointer(E value)
	{
		this.value = value;
	}

	// ========================= IMPLEMENTATION: Pointer<E> ================

	/**
	 * @see net.ruready.common.pointer.Pointer#getValue()
	 */
	public E getValue()
	{
		return value;
	}

	/**
	 * @see net.ruready.common.pointer.Pointer#setValue(java.lang.Object)
	 */
	public void setValue(E value)
	{
		this.value = value;
	}

}
