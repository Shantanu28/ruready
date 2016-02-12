/*****************************************************************************************
 * Source File: AsciiPrinter.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;

import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

/**
 * Print a tree of nodes in a general ASCII format. Each item's tag name is the item type.
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
public class AsciiPrinter<D extends Serializable & Comparable<? super D>, T extends AbstractListTreeNode<D, T>>
		extends SimpleTreeVisitor<D, T>
{
	// ========================= CONSTANTS =================================

	private StringBuffer openingBracket = new StringBuffer(CommonNames.TREE.BRACKET_OPEN);

	private StringBuffer closingBracket = new StringBuffer(CommonNames.TREE.BRACKET_CLOSE);

	private StringBuffer space = new StringBuffer(" ");

	// ========================= FIELDS =====================================

	// Convenient class-local variables
	private StringBuffer ASCIIString;

	// A function pointer for printing item data
	public interface NodeDataPrinter<T extends Comparable<? super T>>
	{
		String print(T data);
	}

	// Prints the tree node data
	private final NodeDataPrinter<D> nodeDataPrinter;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree ASCII printer.
	 * 
	 * @param rootNode
	 *            root node of tree to be printed
	 * @param nodeDataPrinter
	 *            Specified how a tree node data is printed
	 */
	public AsciiPrinter(NodeDataPrinter<D> nodeDataPrinter)
	{
		super();
		this.nodeDataPrinter = nodeDataPrinter;
	}

	// ========================= METHODS ===================================

	/**
	 * Generate an ASCII string of the tree
	 * 
	 * @param thisNode
	 *            tree root node
	 * @return tree ASCII representation
	 */
	public String print(T thisNode)
	{
		ASCIIString = TextUtil.emptyStringBuffer();
		executeOnTree(thisNode);
		return ASCIIString.toString();
	}

	// ========================= IMPLEMENTATION: LimitedDepthTreeVisitor ===

	/**
	 * @see net.ruready.common.tree.SimpleTreeVisitor#executePre(net.ruready.common.tree.AbstractListTreeNode)
	 */
	@Override
	protected Object executePre(T thisNode)
	{
		// Append node data using call-back from the node data printer
		if (nodeDataPrinter != null)
		{
			ASCIIString.append(nodeDataPrinter.print(thisNode.getData()));
		}

		// Opening bracket before node's children are processed
		ASCIIString.append(space);
		ASCIIString.append(openingBracket);
		ASCIIString.append(space);
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
		// Opening bracket at the end of node children processing
		ASCIIString.append(closingBracket);
		ASCIIString.append(space);
		return null;
	}

	// ========================= GETTERS & SETTERS =========================

}
