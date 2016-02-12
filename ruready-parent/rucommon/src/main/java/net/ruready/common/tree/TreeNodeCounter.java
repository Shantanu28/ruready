/*****************************************************************************************
 * Source File: TreeNodeCounter.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;

import net.ruready.common.misc.Auxiliary;

/**
 * Computes the number of nodes in a tree.
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
public class TreeNodeCounter<D extends Serializable & Comparable<? super D>, T extends MutableTreeNode<D, T>>
		extends SimpleTreeVisitor<D, T> implements Auxiliary
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS =====================================

	// Convenient class-local variables
	private int numNodes = 0;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Hide constructor. Use the static facade method {@link #countNodes(MutableTreeNode)}
	 * instead.
	 */
	private TreeNodeCounter()
	{
		super();
	}

	/**
	 * A facade to be called instead of constructing this object. Refreshes a tree.
	 * 
	 * @param thisNode
	 *            tree root node
	 * @return number of nodes in the tree
	 */
	public static <D extends Serializable & Comparable<? super D>, T extends MutableTreeNode<D, T>> int countNodes(
			T thisNode)
	{
		TreeNodeCounter<D, T> counter = new TreeNodeCounter<D, T>();
		counter.executeOnTree(thisNode);
		return counter.getNumNodes();
	}

	// ========================= METHODS ===================================

	// ========================= IMPLEMENTATION: TreeCommutativeDepth =========

	/**
	 * @param thisNode
	 * @return
	 * @see net.ruready.common.tree.SimpleTreeVisitor#executePre(net.ruready.common.tree.MutableTreeNode)
	 */
	@Override
	protected Object executePre(T thisNode)
	{
		numNodes++;
		return null;
	}

	/**
	 * @param thisNode
	 * @return
	 * @see net.ruready.common.tree.SimpleTreeVisitor#executePost(net.ruready.common.tree.MutableTreeNode)
	 */
	@Override
	protected Object executePost(T thisNode)
	{
		return null;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the numNodes
	 */
	public int getNumNodes()
	{
		return numNodes;
	}

}
