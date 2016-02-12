/*****************************************************************************************
 * Source File: AsciiPrinter.java
 ****************************************************************************************/
package net.ruready.business.content.item.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.common.text.TextUtil;
import net.ruready.common.tree.TreeVisitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Print a tree in pre-traversal ordering and ASCII format. Not coupled to the persistent
 * layer. Prints children numbers. Good for debugging purposes.
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
public class ItemCounter implements TreeVisitor<Item>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ItemCounter.class);

	// ========================= FIELDS ====================================

	/**
	 * A list of printout lines generated for the item.
	 */
	private final Map<ItemType, Integer> count = new HashMap<ItemType, Integer>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree printer with default formatting values.
	 * 
	 * @param tree
	 *            the tree to be printed
	 * @param printer
	 *            prints tree nodes
	 */
	public ItemCounter(final Item tree)
	{
		// Carry the counting
		visitTo(tree);
	}

	// ========================= IMPLEMENTATION: TreeVisitor ===============

	/**
	 * Print a tree in pre-traversal order.
	 * 
	 * @param thisItem
	 *            the root node of the tree to be printed.
	 * @return string representation of the tree under the specified root node
	 */
	public Object visitTo(Item thisItem)
	{
		// -----------------------------------
		// Pre-traversal node printout
		// -----------------------------------
		// Reset counter if not yet found in the map
		ItemType type = thisItem.getIdentifier();
		if (count.get(type) == null)
		{
			count.put(type, 0);
		}
		// Increment count
		count.put(type, count.get(type) + 1);

		// -----------------------------------
		// Process child nodes
		// -----------------------------------
		Collection<? extends Node> children = thisItem.getChildren();
		for (Node child : children)
		{
			visitTo((Item) child);
		}

		// -----------------------------------
		// Post-traversal node printout
		// -----------------------------------

		return null;
	}

	// ========================= METHODS ===================================

	/**
	 * Generate a printout with item counts in a tree.
	 * 
	 * @param tree
	 *            the tree to be printed
	 * @return printout with item counts in the tree
	 */
	public static StringBuffer generateItemCounts(final Item tree)
	{
		// Display total item counts
		ItemCounter counter = new ItemCounter(tree);
		StringBuffer output = TextUtil.emptyStringBuffer();
		output.append("Total counts:\n");
		for (ItemType type : counter.getCount().keySet())
		{
			int num = counter.getCount(type);
			output.append("# " + type + ": " + num + "\n");
		}
		return output;
	}

	// ========================= PRIVATE METHODS ===========================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the count property.
	 * 
	 * @return the count
	 */
	public Map<ItemType, Integer> getCount()
	{
		return count;
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Integer getCount(final ItemType key)
	{
		return count.get(key);
	}

}
