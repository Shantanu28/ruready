/*****************************************************************************************
 * Source File: AbstractListTreeNode.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;
import java.util.Set;

/**
 * An abstraction of a mutable tree node. The node has a collection of children. It
 * depends on the following generic parameters:<br>
 * D = data type<br>
 * T = this tree type<br>
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
 * @version Aug 4, 2007
 */
public interface AbstractSetTreeNode<D extends Serializable & Comparable<? super D>, T extends AbstractSetTreeNode<D, T>>
		extends MutableTreeNode<D, T>
{
	// ========================= CONSTANTS =================================

	// ========================= METHODS ===================================

	// ========================= PRINTOUT METHODS ==========================

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * Return the list of children of this node.
	 * 
	 * @return the children of this node
	 */
	Set<T> getChildren();

	/**
	 * Return the list of siblings of this node. This will return <code>null</code> if
	 * the this node's parent field is <code>null</code>.
	 * 
	 * @return list of siblings of this node
	 */
	Set<T> getSiblings();
}
