/*****************************************************************************************
 * Source File: Auditable.java
 ****************************************************************************************/
package net.ruready.common.visitor;

/**
 * Allows arbitration between different {@link Visitor} sub-classes inside the
 * {@link Visitable#accept()} method.
 * <p>
 * Warning: Doesn't work yet.
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
public interface Arbiter<T extends Visitor<?>>
{
	// ========================= ABSTRACT METHODS ===========================

	/**
	 * Make a visitor object visit a visitable object using a call-back method (<code>visit()</code>
	 * calls <code>visitable.accept(this)</code>).
	 * 
	 * @param visitor
	 *            visitor object that visits visitable
	 */
	void visit(T visitor);
}
