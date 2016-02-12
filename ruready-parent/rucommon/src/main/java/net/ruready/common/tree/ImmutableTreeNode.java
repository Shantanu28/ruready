/*****************************************************************************************
 * Source File: AbstractListTreeNode.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.util.Collection;

import net.ruready.common.pointer.ShallowCloneable;
import net.ruready.common.pointer.ValueObject;

/**
 * An abstraction of a basic immutable tree node type. Its generic parameters are:<br>
 * D = data type<br>
 * T = this tree type<br>
 * All this tree knows is that it has a list of children of the same type, and a data
 * type.
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
public interface ImmutableTreeNode<D extends Comparable<? super D>, T extends ImmutableTreeNode<D, T>>
		extends ValueObject, ShallowCloneable
{
	// ========================= CONSTANTS =================================

	// ========================= METHODS ===================================

	/**
	 * Returns the size of the tree, which is the total number of nodes in the tree.
	 * 
	 * @return the total number of nodes in the tree
	 */
	int getSize();

	/**
	 * Returns the number of children (branches of this tree node).
	 * 
	 * @return Returns the number of children.
	 */
	int getNumChildren();

	/**
	 * Returns <code>true</code> if and only if the number of children is positive.
	 * 
	 * @return <code>true</code> if and only if this node has children
	 */
	boolean hasChildren();

	// ========================= PRINTOUT METHODS ==========================

	/**
	 * Print the data and other properties of this node.
	 * 
	 * @return data and properties of this node, represented as a string
	 */
	String printData();

	// ========================= METHODS ====================================

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return Returns the data.
	 */
	D getData();

	/**
	 * Returns the parent node of this node.
	 * 
	 * @return parent node of this node
	 */
	T getParent();

	/**
	 * Returns the [grand-...-grand-] parent node of this node.
	 * 
	 * @param height
	 *            number of tree levels to go up. {@link AbstractSetTreeNode#getParent()}
	 *            means <code>height = 1</code> (the direct parent), and so on. A
	 *            non-positive <code>height</code> will return this node.
	 * @return [grand-...-grand-] parent node of this node
	 */
	T getSuperParent(final int height);

	/**
	 * Return the list of children of this node.
	 * 
	 * @return the children of this node
	 */
	Collection<T> getChildren();

	/**
	 * Return the list of siblings of this node. This will return <code>null</code> if
	 * the this node's parent field is <code>null</code>.
	 * 
	 * @return list of siblings of this node
	 */
	Collection<T> getSiblings();
}
