/*******************************************************
 * Source File: Pointer.java
 *******************************************************/
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
 * A pointer to an object. Useful to pass in as a parameter of a method, which
 * allows it to point to some object upon returning from the method. A pointer
 * wrapper may also be used to hide/expose/adapt the referenced object's
 * interface.
 */
public interface Pointer<E>
{
	/**
	 * Get the value of this pointer.
	 * 
	 * @return object that this pointer holds a reference to
	 */
	E getValue();

	/**
	 * Set the value of this pointer.
	 * 
	 * @param value
	 *            new object to reference by this pointer
	 */
	void setValue(E value);
}
