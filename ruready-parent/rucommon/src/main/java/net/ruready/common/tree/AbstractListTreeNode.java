/*****************************************************************************************
 * Source File: AbstractListTreeNode.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;
import java.util.List;

/**
 * An abstraction of a mutable tree node. The node has a list of children. It depends on
 * the following generic parameters:<br>
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
public interface AbstractListTreeNode<D extends Serializable & Comparable<? super D>, T extends AbstractListTreeNode<D, T>>
		extends MutableTreeNode<D, T>
{
	// ========================= CONSTANTS =================================

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * Return the list of siblings of this node. This will return <code>null</code> if
	 * the this node's parent field is <code>null</code>.
	 * 
	 * @return list of siblings of this node
	 */
	List<T> getSiblings();

	/**
	 * Return the list of children of this node.
	 * 
	 * @return the children of this node
	 */
	List<T> getChildren();

	/**
	 * Add a child at a specified index.
	 * 
	 * @param index
	 *            index to add the child at
	 * @param child
	 *            The child to be added.
	 */
	void addChild(int index, T child);

	/**
	 * Returns a child by index.<br>
	 * Note: we restore this method but pay attention to a possible Java reflection
	 * IntrospectionException: "type mismatch between indexed read and indexed write
	 * methods". This can be thrown when using JSTL tags that try to access the "child"
	 * property and use this method to get a wrong getter return type. The reason for this
	 * exception is not fully understood. An old relevant reference is:
	 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4253627 The private method
	 * throwing this exception appears in:
	 * http://www.imn.htwk-leipzig.de/~waldmann/edu/ss04/oo/j2sdk1.5.0/src/java/beans/IndexedPropertyDescriptor.java
	 * It is possible that the only way to get around this is to set the name of this
	 * method to be *different* than the following setter method. That's why it's not
	 * called setChild(int, T).
	 * 
	 * @param index
	 *            index in the children list
	 * @return corresponding child
	 */
	T getChild(int index);

	/**
	 * Return the index of a child in the children list. If not found, returns
	 * <code>-1</code>.
	 * 
	 * @param child
	 *            child object to be found (using the <code>equals()</code> method)
	 * @return child index or <code>-1</code>
	 */
	int indexOf(T child);

	/**
	 * Return the index of a child in the children list that matches this Id. If not
	 * found, returns <code>-1</code>.
	 * 
	 * @param childId
	 *            ID to be found in a child object to be found (using the
	 *            <code>Long.equals()</code> method)
	 * @return child index or <code>-1</code>
	 */
	int indexOf(Long childId);

	/**
	 * Set child at position <code>id</code>. The index has to be within the children
	 * vector bounds. <code>child</code>'s parent is set to <code>this</code>.
	 * 
	 * @param index
	 *            Child index in the children vector.
	 * @param child
	 *            The child to set.
	 */
	void setChildAt(int index, T child);
}
