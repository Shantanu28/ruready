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
public interface PubliclyCloneable extends Cloneable
{
	/**
	 * A <code>PubliclyCloneable</code> object is one to which any object can send
	 * <code>clone()</code>.
	 * 
	 * @return a <i>deep</i> copy of the receiving object
	 */
	Object clone();
}
