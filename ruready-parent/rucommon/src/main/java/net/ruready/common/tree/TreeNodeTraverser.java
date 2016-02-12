/*****************************************************************************************
 * Source File: TreeNodeTraverser.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.ruready.common.misc.Auxiliary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Traverses a tree into a list of nodes. Each node is in effect a reference to the
 * sub-tree rooted at that node.
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
public class TreeNodeTraverser<D extends Serializable & Comparable<? super D>, T extends MutableTreeNode<D, T>>
		implements TreeVisitor<T>, Auxiliary
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TreeNodeTraverser.class);

	// ========================= FIELDS =====================================

	// ====================
	// Inputs
	// ====================

	// Node traversal order identifier
	private final TraversalOrder traversalOrder;

	// ====================
	// Outputs
	// ====================

	// Sorted node list
	private List<T> nodes = new ArrayList<T>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize a syntax tree node traverser.
	 * 
	 * @param traversalOrder
	 *            node traversal order (e.g. pre/post)
	 */
	public TreeNodeTraverser(final TraversalOrder traversalOrder)
	{
		this.traversalOrder = traversalOrder;
	}

	/**
	 * A facade to be called instead of constructing this object. Traverses a syntax tree
	 * into a list of nodes.
	 * 
	 * @param syntax
	 *            syntax tree [root node]
	 * @param traversalOrder
	 *            node traversal order (e.g. pre/post)
	 */
	public List<T> traverse(T syntax)
	{
		// Traverse the tree into a node list
		this.visitTo(syntax);
		return nodes;
	}

	// ========================= IMPLEMENTATION: TreeVisitor ===============

	/**
	 * Process tree and execute a function at every node. Do not call this function
	 * directly; used <code>executeOnTree</code> instead.
	 * 
	 * @param thisNode
	 *            the root node of the tree to be printed.
	 * @return null, this should be called as a void method.
	 */
	public Object visitTo(T thisNode)
	{
		// -----------------------------------
		// Pre-traversal node processing
		// -----------------------------------
		executePre(thisNode);

		// -----------------------------------
		// Process child nodes
		// -----------------------------------
		// Process children if hook permits
		for (T child : thisNode.getChildren())
		{
			visitTo(child);
		}

		// -----------------------------------
		// Post-traversal node processing
		// -----------------------------------
		executePost(thisNode);

		return null;
	}

	/**
	 * @see net.ruready.common.tree.SimpleTreeVisitor#executePre(net.ruready.common.tree.MutableTreeNode)
	 */
	protected Object executePre(T thisNode)
	{
		if (traversalOrder == TraversalOrder.PRE)
		{
			// Add token of this node to global node token list
			nodes.add(thisNode);
		}
		return null;
	}

	/**
	 * @see net.ruready.common.tree.SimpleTreeVisitor#executePost(net.ruready.common.tree.MutableTreeNode)
	 */
	protected Object executePost(T thisNode)
	{
		if (traversalOrder == TraversalOrder.POST)
		{
			// Add token of this node to global node token list
			nodes.add(thisNode);
		}
		return null;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the nodes
	 */
	public List<T> getNodes()
	{
		return nodes;
	}
}
