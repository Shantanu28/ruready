/*****************************************************************************************
 * Source File: ApplyComparator.java
 ****************************************************************************************/
package net.ruready.business.common.tree.entity;

import net.ruready.business.common.tree.comparator.TreeNodeComparatorID;
import net.ruready.common.misc.Auxiliary;
import net.ruready.eis.common.tree.LimitedDepthTreeVisitor;

/**
 * Apply a tree node comparator to an entire tree. This will affect children sorting below
 * each tree node.
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
 * @version Aug 11, 2007
 */
class ApplyComparator extends LimitedDepthTreeVisitor implements Auxiliary
{

	// ========================= FIELDS =====================================

	/**
	 * Object holding comparator type to be applied to all tree nodes.
	 */
	private TreeNodeComparatorID comparatorType;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an application object.
	 * 
	 * @param comparatorType
	 *            comparator type to be applied to a tree
	 */
	private ApplyComparator(final TreeNodeComparatorID comparatorType)
	{
		super();
		this.comparatorType = comparatorType;
	}

	/**
	 * A facade to be called instead of constructing this object. Applies the comparator
	 * to an entire tree.
	 * 
	 * @param thisNode
	 *            tree root node
	 * @param comparatorType
	 *            comparator type to be applied to a tree
	 */
	public static void applyComparator(final Node thisNode,
			final TreeNodeComparatorID comparatorType)
	{
		new ApplyComparator(comparatorType).executeOnTree(thisNode);
	}

	// ========================= IMPLEMENTATION: LimitedDepthTreeVisitor ===

	/**
	 * @see net.ruready.eis.common.tree.LimitedDepthTreeVisitor#executePre(net.ruready.business.common.tree.entity.Node)
	 */
	@Override
	protected Object executePre(Node thisNode)
	{
		// Use pre-traversal ordering: save parent before children can be saved
		thisNode.setComparatorType(comparatorType);
		return null;
	}

	/**
	 * @param thisNode
	 * @return
	 * @see net.ruready.eis.common.tree.LimitedDepthTreeVisitor#executePost(net.ruready.business.common.tree.entity.Node)
	 */
	@Override
	protected Object executePost(Node thisNode)
	{
		return null;
	}
}
