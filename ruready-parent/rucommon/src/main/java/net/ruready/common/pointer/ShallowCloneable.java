/*****************************************************************************************
 * Source File: PubliclyCloneable.java
 ****************************************************************************************/
package net.ruready.common.pointer;

/**
 * Defines a type of object which anybody can clone (deep copy).
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
 * @version Oct 1, 2007
 */
public interface ShallowCloneable extends Cloneable
{
	/**
	 * Return a shallow copy of this object. Does not copy sub-entities.
	 * 
	 * @return a shallow copy of the receiving object
	 */
	Object shallowClone();

	/**
	 * Merge this object's fields with another object. This object's fields are copied
	 * over to <code>destination</code> unless they are <code>null</code> (or zero, or
	 * other default values of primitive fields).
	 * <p>
	 * WARNING: this method might be deprecated in a future release.
	 * 
	 * @param destination
	 *            this object's fields are copied over to this object
	 */
	void mergeInto(final ShallowCloneable destination);
}
