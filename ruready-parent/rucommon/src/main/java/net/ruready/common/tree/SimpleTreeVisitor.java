/*****************************************************************************************
 * Source File: SimpleTreeVisitor.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A simple tree visitor with options for pre-traversal and post-traversal execution. Does
 * not depend on Hibernate.
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
public abstract class SimpleTreeVisitor<D extends Serializable & Comparable<? super D>, T extends MutableTreeNode<D, T>>
		implements TreeVisitor<T>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SimpleTreeVisitor.class);

	// ========================= FIELDS ====================================

	/**
	 * Convenient local variable holding the current node's depth in the tree.
	 */
	protected int depth;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree simple visitor.
	 * 
	 * @param tree
	 *            the tree to be printed
	 */
	public SimpleTreeVisitor()
	{

	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Do this operation at a node before operating on its children. This is a hook.
	 * 
	 * @param thisNode
	 *            the root node of the tree.
	 * @return an object containing intermediate processing results
	 */
	abstract protected Object executePre(T thisNode);

	/**
	 * Do this operation at a node after operating on its children. This is a hook.
	 * 
	 * @param thisNode
	 *            the root node of the tree.
	 * @return an object containing intermediate processing results
	 */
	abstract protected Object executePost(T thisNode);

	// ========================= METHODS ===================================

	/**
	 * Apply the operation <code>execute()</code> at every node of a tree.
	 * 
	 * @param rootNode
	 *            the root node of the tree.
	 */
	protected void executeOnTree(T rootNode)
	{
		// Tracks absolute depth in the visited tree
		depth = 0;

		visitTo(rootNode);
	}

	/**
	 * Decides whether to process the children of this node or not. By default, this hook
	 * returns <code>true</code>.
	 * 
	 * @param thisNode
	 *            currently visited tree node
	 * @return <code>true</code> if and only if children of this node are processed in
	 *         thie visitor
	 */
	protected boolean isProcessChildren(T thisNode)
	{
		return true;
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
		depth++;
		if (isProcessChildren(thisNode))
		{
			// Process children if hook permits
			for (T child : thisNode.getChildren())
			{
				visitTo(child);
			}
		}
		depth--;

		// -----------------------------------
		// Post-traversal node processing
		// -----------------------------------
		executePost(thisNode);

		return null;
	}

	// ========================= GETTERS & SETTERS =========================

}
