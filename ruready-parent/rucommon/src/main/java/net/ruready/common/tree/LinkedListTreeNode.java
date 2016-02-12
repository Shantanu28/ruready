/*****************************************************************************************
 * Source File: ListTreeNode.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import net.ruready.common.exception.SystemException;
import net.ruready.common.pointer.GenericCloner;
import net.ruready.common.pointer.ShallowCloneable;
import net.ruready.common.rl.CommonNames;

/**
 * A tree node implementation that uses a linked list for the children list. This class is
 * independent of the persistent layer.
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
public final class LinkedListTreeNode<D extends Serializable & Comparable<? super D>>
		implements AbstractListTreeNode<D, LinkedListTreeNode<D>>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	// Data held at this tree node
	protected D data;

	// The parent node
	protected LinkedListTreeNode<D> parent;

	protected List<LinkedListTreeNode<D>> children;

	// Comparator according to which children are sorted
	protected Comparator<? super LinkedListTreeNode<D>> comparator;

	protected Printer<LinkedListTreeNode<D>> printer;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Prevent no-argument construction.
	 */
	protected LinkedListTreeNode()
	{

	}

	/**
	 * Construct a node from basic data.
	 * 
	 * @param data
	 */
	public LinkedListTreeNode(D data)
	{
		initialize();
		this.data = data;
	}

	// ========================= IMPLEMENTATION: AbstractListTreeNode ==========

	/**
	 * Print the data and other properties of this node.
	 * 
	 * @return data and properties of this node, represented as a string
	 */
	public String printData()
	{
		// return "[" + getClass().getSimpleName() + getData() +
		// CommonNames.MISC.TAB_CHAR + "(" +
		// getNumChildren() + ")";
		return CommonNames.MISC.EMPTY_STRING + getData();
	}

	/**
	 * @return parent node of this node
	 */
	public LinkedListTreeNode<D> getParent()
	{
		return parent;
	}

	/**
	 * Returns the [grand-...-grand-] parent node of this node.
	 * 
	 * @param height
	 *            number of tree levels to go up. {@link AbstractListTreeNode#getParent()}
	 *            means <code>height = 1</code> (the direct parent), and so on. A
	 *            non-positive <code>height</code> will return this node.
	 * @return [grand-...-grand-] parent node of this node
	 * @see net.ruready.common.tree.AbstractListTreeNode#getSuperParent(int)
	 */
	public LinkedListTreeNode<D> getSuperParent(final int height)
	{
		LinkedListTreeNode<D> node = this;
		for (int i = 1; i <= height; i++)
		{
			if (node == null)
			{
				return null;
			}
			node = node.parent;
		}
		return node;
	}

	/**
	 * Return the list of siblings of this node. This will return <code>null</code> if
	 * the this node's parent field is <code>null</code>.
	 * 
	 * @return list of siblings of this node
	 * @see net.ruready.common.tree.AbstractListTreeNode#getSiblings()
	 */
	public List<LinkedListTreeNode<D>> getSiblings()
	{
		return (parent == null) ? null : parent.getChildren();
	}

	/**
	 * @return the printer
	 */
	public Printer<LinkedListTreeNode<D>> getPrinter()
	{
		return printer;
	}

	/**
	 * @param printer
	 *            the printer to set
	 */
	public void setPrinter(Printer<LinkedListTreeNode<D>> printer)
	{
		this.printer = printer;
	}

	/**
	 * @return the children
	 */
	public List<LinkedListTreeNode<D>> getChildren()
	{
		return children;
	}

	/**
	 * Add a child to the children list.
	 * 
	 * @param child
	 *            The child to be added.
	 */
	public void addChild(LinkedListTreeNode<D> child)
	{
		children.add(child);
		child.setParent(this);
		refresh();
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#addChild(int,
	 *      net.ruready.common.tree.AbstractListTreeNode)
	 */
	public void addChild(int index, LinkedListTreeNode<D> child)
	{
		// May be slow: we are not using a linked list
		children.add(index, child);
		child.setParent(this);
		refresh();
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#getChild(int)
	 */
	public LinkedListTreeNode<D> getChild(int index)
	{
		return children.get(index);
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#indexOf(net.ruready.common.tree.AbstractListTreeNode)
	 */
	public int indexOf(LinkedListTreeNode<D> child)
	{
		return children.indexOf(child);
	}

	/**
	 * @param childId
	 * @return
	 * @see net.ruready.common.tree.AbstractListTreeNode#indexOf(java.lang.Long)
	 */
	public int indexOf(Long childId)
	{
		throw new SystemException(
				"Cannot be implemented until this tree node type has a unique identifier of type Long");
		// int i = 0;
		// for (LinkedListTreeNode<D> child : children)
		// {
		// if (childId.equals(child.getId()))
		// {
		// return i;
		// }
		// i++;
		// }
		// return CommonNames.MISC.INVALID_VALUE_INTEGER;
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#getData()
	 */
	public D getData()
	{
		return data;
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#setChildAt(int, java.lang.Object)
	 */
	public void setChildAt(int id, LinkedListTreeNode<D> child)
	{
		children.set(id, child);
		child.setParent(this);
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#replaceChild(net.ruready.common.tree.AbstractListTreeNode,
	 *      net.ruready.common.tree.AbstractListTreeNode)
	 */
	public void replaceChild(LinkedListTreeNode<D> oldChild,
			LinkedListTreeNode<D> newChild)
	{
		int index = children.indexOf(oldChild);
		if (index >= 0)
		{
			children.set(index, newChild);
			newChild.setParent(this);
		}
	}

	/**
	 * Remove a child node under this node; all grandchildren (children of this child) are
	 * added under this node, at position <code>indexOf(child)</code>.
	 * 
	 * @param child
	 *            the child to remove
	 * @see net.ruready.common.tree.AbstractListTreeNode#removeChildTree(net.ruready.common.tree.AbstractListTreeNode)
	 */
	public void removeChildTree(LinkedListTreeNode<D> child)
	{
		int index = children.indexOf(child);
		if (index >= 0)
		{
			// Get a reference to grandchildren
			List<LinkedListTreeNode<D>> grandChildren = child.getChildren();
			// Remove the child
			children.remove(index);
			// Add grandchildren in place of the child
			children.addAll(index, grandChildren);
			// Set the grandchildren's new parent
			for (LinkedListTreeNode<D> grandChild : grandChildren)
			{
				grandChild.setParent(this);
			}
		}
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#setData(java.lang.Comparable)
	 */
	public void setData(D data)
	{
		this.data = data;
	}

	/**
	 * Set the parent of this node to <code>null</code>.
	 * <p>
	 * WARNING: use this method only if you know that there is indeed no parent.
	 * Otherwise, a parent still might have a child reference to this node whereas this
	 * node doesn't point to it any longer.
	 */
	public void removeParentReference()
	{
		parent = null;
	}

	/**
	 * Remove a child from the children list.
	 * 
	 * @param child
	 *            The child to be removed.
	 */
	public void removeChild(LinkedListTreeNode<D> child)
	{
		child.setParent(null);
		children.remove(child);
		refresh();
	}

	/**
	 * Add all children in a list to this tree. This operation is delegated to the
	 * specific implementation of <code>children</code>.
	 * 
	 * @param newChildren
	 *            collection to be added
	 */
	public void addChilds(Collection<LinkedListTreeNode<D>> newChildren)
	{
		children.addAll(newChildren);
		for (LinkedListTreeNode<D> child : newChildren)
		{
			child.setParent(this);
		}
		refresh();
	}

	/**
	 * Remove all children from this tree. This operation is delegated to the specific
	 * implementation of <code>children</code>.
	 */
	public void removeAllChilds()
	{
		for (LinkedListTreeNode<D> child : getChildren())
		{
			child.setParent(null);
		}
		children = new LinkedList<LinkedListTreeNode<D>>();
	}

	/**
	 * Returns the size of the tree, which is the total number of nodes in the tree.
	 * 
	 * @return the total number of nodes in the tree
	 */
	public int getSize()
	{
		return TreeNodeCounter.countNodes(this);
	}

	/**
	 * Returns the number of children (branches of this tree node).
	 * 
	 * @return Returns the number of children.
	 */
	public int getNumChildren()
	{
		return children.size();
	}

	/**
	 * Returns <code>true</code> if and only if the number of children is positive.
	 * 
	 * @return <code>true</code> if and only if this node has children
	 */
	public boolean hasChildren()
	{
		return children.size() > 0;
	}

	/**
	 * Remove this node from its parent's cghildren list. This should be done before
	 * deleting a node from the database.
	 * 
	 * @param comparator
	 *            the comparator to set
	 */
	public void removeFromParent()
	{
		if (parent != null)
		{
			parent.removeChild(this);
		}
	}

	/**
	 * Return the list of nodes in the syntax tree.
	 * 
	 * @param traversalOrder
	 *            node traversal order (e.g. pre/post)
	 * @return list of tree nodes
	 */
	public List<LinkedListTreeNode<D>> toNodeList(final TraversalOrder traversalOrder)
	{
		return new TreeNodeTraverser<D, LinkedListTreeNode<D>>(traversalOrder)
				.traverse(this);
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * A shallow copy of this object, as opposed to {@link #clone()}, which is a deep
	 * copy.
	 * 
	 * @return a shallow copy of the receiving object
	 */
	@SuppressWarnings("unchecked")
	public LinkedListTreeNode<D> shallowClone()
	{
		// Unfortunately, clone() cannot be called generically on
		// non-PubliclyCloneable types D; see
		// http://www.ibm.com/developerworks/java/library/j-jtp01255.html
		// Hence, we suppress an unchecked warning here.
		LinkedListTreeNode<D> copy = new LinkedListTreeNode<D>((D) GenericCloner
				.clone(data));

		// Must clone printer otherwise printouts are weird. That's because
		// the printer has a reference to the tree. Assumes that the original
		// printer is of traversal printer type
		copy.setPrinter(new TraversalPrinter(copy, (TraversalPrinter) printer));

		// Just copy references to these TreeNode fields
		copy.setComparator(comparator);

		return copy;
	}

	/**
	 * Merge this object's fields with another object. This object's fields are copied
	 * over to <code>destination</code> unless they are <code>null</code> (or zero, or
	 * other default values of primitive fields).
	 * <p>
	 * WARNING: this method has not been tested yet and might be buggy.
	 * 
	 * @param destination
	 *            this object's fields are copied over to this object
	 * @see net.ruready.common.pointer.ShallowCloneable#mergeInto(net.ruready.common.pointer.ShallowCloneable)
	 */
	@SuppressWarnings("unchecked")
	public void mergeInto(final ShallowCloneable destination)
	{
		// Unfortunately, mergeInto() cannot be called generically on
		// non-ShallowCloneable types D; see
		// http://www.ibm.com/developerworks/java/library/j-jtp01255.html
		// Hence, we suppress an unchecked warning here.
		LinkedListTreeNode<D> dest = (LinkedListTreeNode<D>) destination;
		GenericCloner.mergeInto(data, dest.getData());

		// Must clone printer otherwise printouts are weird. That's because
		// the printer has a reference to the tree. Assumes that the original
		// printer is of traversal printer type
		dest.setPrinter(new TraversalPrinter(dest, (TraversalPrinter) printer));

		// Just copy references to these TreeNode fields
		dest.setComparator(comparator);
	}

	/**
	 * Return a deep copy of this object. Must be implemented for this object to serve as
	 * a target (or part of a target) of an assembly.
	 * 
	 * @return a deep copy of this object.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedListTreeNode<D> clone()
	{
		// try {
		// Copy root node data
		LinkedListTreeNode<D> copy = this.shallowClone();

		// Copy child trees (recursive calls)
		for (LinkedListTreeNode<D> child : children)
		{
			copy.addChild(child.clone());
		}

		return copy;
		// }
		/*
		 * catch (CloneNotSupportedException e) { // this shouldn't happen, because we are
		 * Cloneable throw new InternalError("clone() failed: " + e.getMessage()); }
		 */
	}

	// ========================= METHODS ===================================

	/**
	 * Perform initializations: initialize the data structure holding the collection of
	 * children, set a default printer, etc.
	 */
	private void initialize()
	{
		// Initialize children
		children = new LinkedList<LinkedListTreeNode<D>>();

		// Default printout style
		setPrinter(new TraversalPrinter<D, LinkedListTreeNode<D>>(this));

		// Default children ordering
		// setComparator(new TreeNodeComparator());
	}

	/**
	 * Refresh this tree node. This re-sorts children.
	 */
	public void refresh()
	{
		if ((comparator != null) && (children != null))
		{
			// Sort children
			Collections.sort(children, comparator);
		}
	}

	// ========================= PRINTOUT METHODS ==========================

	/**
	 * Print a tree in pre-traversal order.
	 * 
	 * @return a string with this tree in pre-traversal order.
	 */
	@Override
	public String toString()
	{
		return printer.toString();
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * Set the parent node of this node. The depth of this node is set to the parent's
	 * depth plus one. Depth updates are not cascaded to the children of this node; use
	 * refreshAll() to achieve that.<br>
	 * To keep the integrity of parent-child bi-directional references, child cannot stay
	 * under both the old parent (this.parent) and new parent (parent). Clone it and put
	 * the clone under the old parent, and move it under the new parent.
	 * 
	 * @param parent
	 *            parent node of this node to set
	 */
	private void setParent(LinkedListTreeNode<D> parent)
	{
		// if (parent == null)
		// {
		// logger.debug("setParent(this @" + Integer.toHexString(this.hashCode()) + " "
		// + this + ", parent null )");
		// }
		// else
		// {
		// logger.debug("setParent(this @" + Integer.toHexString(this.hashCode()) + " "
		// + this + ", parent @" + Integer.toHexString(parent.hashCode()) + " "
		// + parent + ")");
		// }
		// if (this.parent != null)
		// ============================================================
		// Disabled for now; see also SyntaxTreeNode
		// ============================================================
		// if ((this.parent != null) && (this.parent != parent))
		// {
		// // throw new SystemException("Cannot add child " + this
		// // + " ; remove it first from child's current parent" + " "
		// // + this.parent);
		//
		// // logger
		// // .warn("Cannot add child " + this + " under new parent " + parent
		// // + "; remove it first from child's current parent" + " "
		// // + this.parent);
		// // this.parent.children.remove(this);
		// // this.parent.refresh();
		//
		// // To keep the integrity of parent-child bi-directional references,
		// // child cannot stay under both the old parent (this.parent) and new parent
		// // (parent). Clone it and put the clone under the old parent, and move it
		// // under the new parent.
		// LinkedListTreeNode<D> copy = this.clone();
		// int index = this.parent.indexOf(this);
		// copy.parent = this.parent;
		// // copy.setData(data);
		// this.parent.children.set(index, copy);
		//
		// // this.removeFromParent();
		// }
		this.parent = parent;
	}

	/**
	 * @return the comparator
	 */
	public Comparator<? super LinkedListTreeNode<D>> getComparator()
	{
		return comparator;
	}

	/**
	 * Set a new comparator to define a new children ordering and refresh the node.
	 * Setting the comparator to <code>null</code> will keep the current children
	 * ordering.
	 * 
	 * @param comparator
	 *            the comparator to set
	 */
	public void setComparator(Comparator<? super LinkedListTreeNode<D>> comparator)
	{
		setComparator(comparator, true);
	}

	/**
	 * Set a new comparator to define a new children ordering and refresh the node.
	 * Setting the comparator to <code>null</code> will keep the current children
	 * ordering.
	 * 
	 * @param comparator
	 *            the comparator to set
	 * @param doRefresh
	 *            refresh tree if and only if this flag is true
	 */
	private void setComparator(Comparator<? super LinkedListTreeNode<D>> comparator,
			boolean doRefresh)
	{
		this.comparator = comparator;
		if (doRefresh)
		{
			refresh();
		}
	}
}
