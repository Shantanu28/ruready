/*****************************************************************************************
 * Source File: Visitable.java
 ****************************************************************************************/
package net.ruready.common.visitor;

/**
 * An object that accept a visitor in a visitor pattern. Uses a call-back method ({@link #accept(Visitor)})
 * that calls {@link Visitor#visit(Visitable)}. This interface is parameterized by the
 * type of the visitor object, so that only certain (as well as multiple-type) visitors
 * are allowed in certain visitables.
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
 * @version Sep 8, 2007
 */
public interface Visitable<T extends Visitor<? extends Visitable<T>>>
{
	// ========================= ABSTRACT METHODS ===========================

	/**
	 * Allow a visitor to access and process this object. calls
	 * <code>visitable.accept(this)</code>). Uses a call-back method (<code>accept(visitor)</code>
	 * calls <code>visitor.visit(this)</code>).
	 * 
	 * @param <B>
	 *            specific visitor type; this method may be implemented for multiple
	 *            classes of {@link Visitable}.
	 * @param visitor
	 *            visitor accepted by this object
	 */
	<B extends T> void accept(B visitor);
}
