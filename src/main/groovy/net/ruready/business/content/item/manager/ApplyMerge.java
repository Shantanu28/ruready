/*****************************************************************************************
 * Source File: ApplySaveOrUpdate.java
 ****************************************************************************************/
package net.ruready.business.content.item.manager;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.eis.common.tree.LimitedDepthTreeVisitor;

/**
 * Apply a Hibernate merge operation to all nodes in a tree. Note: treats all tree items
 * as raw Items, not as specific sub-classes (e.g. Question) when saving them.
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
public class ApplyMerge<B extends Item> extends LimitedDepthTreeVisitor
{
	// ========================= FIELDS =====================================

	/**
	 * DAO to use for saving a single node.
	 */
	private AbstractEditItemManager<B> manager;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an application object.
	 * 
	 * @param manager
	 *            the business manager object that calls this object
	 * @param thisNode
	 *            tree root node
	 */
	private ApplyMerge(AbstractEditItemManager<B> manager, Item thisNode)
	{
		super();
		this.manager = manager;
	}

	/**
	 * A facade to be called instead of constructing this object. Saves or updates a tree.
	 * 
	 * @param manager
	 *            the business manager object that calls this object
	 * @param thisNode
	 *            tree root node
	 */
	public static <B extends Item> void merge(AbstractEditItemManager<B> manager,
			Item thisNode)
	{
		new ApplyMerge<B>(manager, thisNode).executeOnTree(thisNode);
	}

	// ========================= IMPLEMENTATION: LimitedDepthTreeVisitor
	// =============

	/**
	 * @see net.ruready.eis.common.tree.LimitedDepthTreeVisitor#executePre(net.ruready.eis.entity.DemoCatalogCreator)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object executePre(Node thisNode)
	{
		// Use pre-traversal ordering: merge parent before children can be saved
		manager.merge((B) thisNode, false);
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
