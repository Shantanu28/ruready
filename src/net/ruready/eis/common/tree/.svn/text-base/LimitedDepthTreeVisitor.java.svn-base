/*****************************************************************************************
 * Source File: LimitedDepthTreeVisitor.java
 ****************************************************************************************/
package net.ruready.eis.common.tree;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.tree.TreeVisitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A simple tree visitor with options for pre-traversal and post-traversal execution,
 * maximum processing node depth, etc. Depends on a Hibernate array initialization check
 * call.
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
 * @version Jul 31, 2007
 */
public abstract class LimitedDepthTreeVisitor implements TreeVisitor<Node>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(LimitedDepthTreeVisitor.class);

	// ========================= FIELDS ====================================

	/**
	 * Maximum depth to process below the root node passed in. Limited by stack size for
	 * recursion. depth = -1 will process nothing; depth = 0 will process the root node
	 * only; depth = 1 will process the root and its children only; and so on.
	 */
	private int maxRelativeDepth = CommonNames.MISC.MAX_RECURSION;

	/**
	 * Convenient local variable: node depth in the tree.
	 */
	private int depth;

	/**
	 * Convenient local variable: depth of the root node.
	 */
	private int rootDepth;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree simple visitor.
	 * 
	 * @param tree
	 *            the tree to be printed
	 */
	public LimitedDepthTreeVisitor()
	{

	}

	/**
	 * Construct a tree simple visitor.
	 * 
	 * @param maxRelativeDepth
	 *            Maximum depth to process below the root node passed in. Limited by stack
	 *            size for recursion. The depth is relative to the root node depth in a
	 *            super-tree and is 0-based, i.e., depth = -1 will process nothing; depth =
	 *            0 will process the root node only; depth = 1 will process the root and
	 *            its children only; and so on.
	 */
	public LimitedDepthTreeVisitor(int maxRelativeDepth)
	{
		this.maxRelativeDepth = maxRelativeDepth;
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Do this operation at a node before operating on its children. This is a hook.
	 * 
	 * @param thisNode
	 *            the root node of the tree.
	 */
	abstract protected Object executePre(Node thisNode);

	/**
	 * Do this operation at a node after operating on its children. This is a hook.
	 * 
	 * @param thisNode
	 *            the root node of the tree.
	 */
	abstract protected Object executePost(Node thisNode);

	// ========================= METHODS ===================================

	/**
	 * Apply the operation <code>execute()</code> at every node of a tree.
	 * 
	 * @param rootNode
	 *            the root node of the tree.
	 */
	protected void executeOnTree(Node rootNode)
	{
		rootDepth = rootNode.getDepth();
		// Tracks absolute depth in a super-tree
		depth = rootDepth;

		visitTo(rootNode);
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
	public Object visitTo(Node thisNode)
	{
		// Check relative depth; if exceeds maximum specified, terminate
		// visitTo() execution
		if (depth > rootDepth + maxRelativeDepth)
		{
			return null;
		}

		// -----------------------------------
		// Pre-traversal node processing
		// -----------------------------------
		executePre(thisNode);

		// -----------------------------------
		// Process child nodes
		// -----------------------------------
		depth++;
		for (Node child : thisNode.getChildren())
		{
			visitTo(child);
		}
		depth--;

		// -----------------------------------
		// Post-traversal node processing
		// -----------------------------------
		executePost(thisNode);

		return null;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the current tree node depth
	 */
	protected int getDepth()
	{
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	protected void setDepth(int depth)
	{
		this.depth = depth;
	}

	/**
	 * @return the maxRelativeDepth
	 */
	public int getMaxRelativeDepth()
	{
		return maxRelativeDepth;
	}

	/**
	 * @param maxRelativeDepth
	 *            the maxRelativeDepth to set
	 */
	protected void setMaxRelativeDepth(int maxRelativeDepth)
	{
		this.maxRelativeDepth = maxRelativeDepth;
	}

}
