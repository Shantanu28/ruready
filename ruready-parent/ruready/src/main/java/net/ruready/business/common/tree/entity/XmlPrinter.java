/*****************************************************************************************
 * Source File: XmlPrinter.java
 ****************************************************************************************/
package net.ruready.business.common.tree.entity;

import net.ruready.business.content.item.entity.Item;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.XmlUtil;
import net.ruready.eis.common.tree.LimitedDepthTreeVisitor;

/**
 * Print a tree of nodes in XML format. Each item's tag name is the item type.
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
 * @version Aug 9, 2007
 */
public class XmlPrinter extends LimitedDepthTreeVisitor
{
	// ========================= FIELDS =====================================

	private Item rootNode;

	/**
	 * Convenient class-local variables.
	 */
	private StringBuffer xmlString;

	/**
	 * A function pointer for printing item data.
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
	 * @version Nov 1, 2007
	 */
	public interface ItemDataPrinter
	{
		void print(Item item);
	}

	private ItemDataPrinter itemDataPrinter;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree XML printer.
	 * 
	 * @param rootNode
	 *            root node of tree to be printed
	 * @param maxRelativeDepth
	 *            Maximum depth to process below the root node passed in. Limited by stack
	 *            size for recursion. The depth is relative to the root node depth in a
	 *            super-tree and is 0-based, i.e., depth = -1 will process nothing; depth =
	 *            0 will process the root node only; depth = 1 will process the root and
	 *            its children only; and so on.
	 */
	public XmlPrinter(Item rootNode, int maxRelativeDepth)
	{
		super(maxRelativeDepth);
	}

	// ========================= METHODS ===================================

	/**
	 * Generate an XML string of the tree
	 * 
	 * @param thisNode
	 *            tree root node
	 * @return tree XML representation
	 */
	public String print(Item thisNode)
	{
		xmlString = TextUtil.emptyStringBuffer();
		executeOnTree(thisNode);
		return xmlString.toString();
	}

	// ========================= IMPLEMENTATION: LimitedDepthTreeVisitor
	// =============

	/**
	 * @see net.ruready.eis.common.tree.LimitedDepthTreeVisitor#executePre(net.ruready.business.common.tree.entity.Node)
	 */
	@Override
	protected Object executePre(Node thisNode)
	{
		Item item = (Item) thisNode;

		// Append opening tag to tree XML string
		xmlString.append(XmlUtil.openTag(item.getType()));
		xmlString.append(CommonNames.MISC.NEW_LINE_CHAR);

		// Append item data using call-back from the item data printer
		if (itemDataPrinter != null)
		{
			itemDataPrinter.print(item);
		}
		return null;
	}

	/**
	 * @see net.ruready.eis.common.tree.LimitedDepthTreeVisitor#executePost(net.ruready.business.common.tree.entity.Node)
	 */
	@Override
	protected Object executePost(Node thisNode)
	{
		// Append opening tag to tree XML string
		xmlString.append(XmlUtil.closeTag(((Item) thisNode).getType()));
		return null;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the rootNode
	 */
	public Item getRootNode()
	{
		return rootNode;
	}

	/**
	 * @param rootNode
	 *            the rootNode to set
	 */
	public void setRootNode(Item rootNode)
	{
		this.rootNode = rootNode;
	}

	/**
	 * @param maxRelativeDepth
	 *            the maxRelativeDepth to set
	 */
	@Override
	public void setMaxRelativeDepth(int maxRelativeDepth)
	{
		super.setMaxRelativeDepth(maxRelativeDepth);
	}

	/**
	 * @param itemDataPrinter
	 *            the itemDataPrinter to set
	 */
	public void setItemDataPrinter(ItemDataPrinter itemDataPrinter)
	{
		this.itemDataPrinter = itemDataPrinter;
	}

}
