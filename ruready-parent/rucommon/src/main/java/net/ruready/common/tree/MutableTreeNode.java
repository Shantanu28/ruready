/*****************************************************************************************
 * Source File: AbstractListTreeNode.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;
import java.util.Collection;

import net.ruready.common.pointer.PubliclyCloneable;

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
public interface MutableTreeNode<D extends Serializable & Comparable<? super D>, T extends MutableTreeNode<D, T>>
		extends ImmutableTreeNode<D, T>, PubliclyCloneable
{
	// ========================= CONSTANTS =================================

	// ========================= METHODS ===================================

	/**
	 * Add a child to the children list.
	 * 
	 * @param child
	 *            The child to be added.
	 */
	void addChild(T child);

	/**
	 * Add all children in a list to this tree. This operation is delegated to the
	 * specific implementation of <code>children</code>.
	 * 
	 * @param newChildren
	 *            collection to be added
	 */
	void addChilds(Collection<T> newChildren);

	/**
	 * Remove this node from its parent's cghildren list. This should be done before
	 * deleting a node from the database.
	 * 
	 * @param comparator
	 *            the comparator to set
	 */
	void removeFromParent();

	/**
	 * Set the parent of this node to <code>null</code>.
	 * <p>
	 * WARNING: use this method only if you know that there is indeed no parent.
	 * Otherwise, a parent still might have a child reference to this node whereas this
	 * node doesn't point to it any longer.
	 */
	void removeParentReference();

	/**
	 * Remove a child from the children list.
	 * 
	 * @param child
	 *            The child to be removed.
	 */
	void removeChild(T child);

	/**
	 * Remove a child node under this node; all grandchildren (children of this child) are
	 * added under this node, at position <code>indexOf(child)</code>.
	 * 
	 * @param child
	 *            the child to remove
	 */
	void removeChildTree(T child);

	/**
	 * Remove all children.
	 */
	void removeAllChilds();

	/**
	 * Set child at position <code>children.indexOf(oldChild)</code> with
	 * <code>newChild</code>. <code>newChild</code>'s parent is set to
	 * <code>this</code>. If <code>old</code> is not found, this method does nothing.
	 * 
	 * @param oldChild
	 *            the old child in the children list
	 * @param newChild
	 *            The new child
	 */
	void replaceChild(T oldChild, T newChild);

	/**
	 * Return the list of nodes in the syntax tree.
	 * 
	 * @param traversalOrder
	 *            node traversal order (e.g. pre/post)
	 * @return list of tree nodes
	 */
	Collection<T> toNodeList(final TraversalOrder traversalOrder);

	// ========================= PRINTOUT METHODS ==========================

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @param data
	 *            The data to set.
	 */
	void setData(D data);

	/**
	 * @return the printer
	 */
	Printer<T> getPrinter();

	/**
	 * @param printer
	 *            the printer to set
	 */
	void setPrinter(Printer<T> printer);
}
