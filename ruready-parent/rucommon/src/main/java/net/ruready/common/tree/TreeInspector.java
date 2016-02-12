/*****************************************************************************************
 * Source File: SimpleTreeVisitor.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Determines whether a structure is a tree that can be traversed so that every node is
 * visited only once.
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
public class TreeInspector<D extends Serializable & Comparable<? super D>, T extends ImmutableTreeNode<D, T>>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TreeInspector.class);

	// ========================= FIELDS ====================================

	// ------------------
	// Options
	// ------------------

	/**
	 * If true, checks that every node in the tree has one parent only.
	 */
	private boolean checkMultipleParents = true;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree inspector.
	 */
	public TreeInspector()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Return <code>true</code> if and only if the tree can be traversed so that every
	 * node is visited only once. *
	 * 
	 * @param rootNode
	 *            the tree to be inspected
	 * @return is {@link #rootNode} a tree
	 */
	public boolean isTree(final T rootNode)
	{
		return isTree(rootNode, new HashSet<T>());
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Return <code>true</code> if and only if the tree can be traversed so that every
	 * node is visited only once.
	 * 
	 * @param node
	 *            the tree to be inspected
	 * @param visited
	 *            all nodes traversed so far. Updated in this method to include
	 *            <code>node</code>
	 * @return is {@link #rootNode} a tree
	 */
	private boolean isTree(final T node, Set<T> visited)
	{
		// Don't print anything in this method because the toString() methods
		// might cause a stack overflow for trees with cycles.

		// logger.debug("Currently at node " + node.getData()
		// + ", previously visited nodes " + visited);
		// Add node to the list of nodes visited so far
		visited.add(node);

		// A composite node is a tree if and only if its children are trees,
		// and it they haven't yet been visited.
		Collection<T> children = node.getChildren();
		for (T child : children)
		{
			if (visited.contains(child))
			{
				// logger.debug("node " + node.getData()
				// + " is not a tree because of child " + child.getData()
				// + " was already visited: " + visited);
				//return false;
				return !checkMultipleParents;
			}
			if (!isTree(child, visited))
			{
				// logger.debug("node " + node.getData()
				// + " is not a tree because of child " + child.getData()
				// + " is not a tree");
				return false;
			}
		}

		return true;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the checkMultipleParents
	 */
	public boolean isCheckMultipleParents()
	{
		return checkMultipleParents;
	}

	/**
	 * @param checkMultipleParents
	 *            the checkMultipleParents to set
	 */
	public void setCheckMultipleParents(boolean checkMultipleParents)
	{
		this.checkMultipleParents = checkMultipleParents;
	}

}
