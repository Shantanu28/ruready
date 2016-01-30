/*****************************************************************************************
 * Source File: NodeUtil.java
 ****************************************************************************************/
package net.ruready.business.common.tree.util;

import java.util.Comparator;
import java.util.SortedSet;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Centralizes utilities related to node naming, copying and merging.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 29, 2007
 */
public class NodeUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(NodeUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private NodeUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * Merge an item tree into another item tree. We assume that the source and
	 * destination are at the same hierarchy in the content management (e.g.
	 * World vs. World).
	 * 
	 * @param source
	 *            item tree to copy from
	 * @param destination
	 *            list of items to verify the new name against.
	 */
	public static final void merge(final Node source, final Node destination)
	{
		// ==============================================
		// Merge this node's properties
		// ==============================================
		source.mergeInto(destination);

		// ==============================================
		// Merge children of this node
		// ==============================================
		for (Node sourceChild : source.getChildren())
		{
			if (sourceChild == null)
			{
				// Invalid source child, ignore
				continue;
			}
			Node destinationChild = destination.findChild(sourceChild);
			if (destinationChild == null)
			{
				// Source child not found, add a copy of its entire tree under
				// the
				// destination item.
				destination.addChild(sourceChild.clone());
			}
			else
			{
				// Source child found under the destination item, merge it
				NodeUtil.merge(sourceChild, destinationChild);
			}
		}
	}

	/**
	 * Return the child of this item using the comparator.
	 * 
	 * @param example
	 *            example child to look for. If a child and example have a
	 *            comparison result of 0 using the node's children comparator,
	 *            this child is returned
	 * @param children
	 *            list of children
	 * @return the corresponding child if found, or <code>null</code> if not
	 *         found
	 */
	public static <T extends Node> T findElementByExample(final SortedSet<T> children,
			T example)
	{
		if (children.size() == 0)
		{
			return null;
		}
		// Comparator<? super T> childComparator = children.comparator();
		Comparator<? super T> childComparator = children.first().getParent()
				.getComparator();
		for (T child : children)
		{
			if (childComparator.compare(child, example) == 0)
			{
				return child;
			}
		}
		return null;
	}

	// ========================= PRIVATE METHODS ===========================
}
