/*****************************************************************************************
 * Source File: AsciiPrinter.java
 ****************************************************************************************/
package net.ruready.port.xml.content;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.util.ItemCounter;
import net.ruready.business.content.item.util.ItemPrinter;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.stack.AbstractStack;
import net.ruready.common.stack.GenericStack;
import net.ruready.common.tree.Printer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Print a tree in pre-traversal ordering. Allows customizing the print format by plugging
 * different {@link ItemPrinter}s. Not coupled to the persistent layer.Useful for
 * debugging and output ports.
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
public class ContentPrinter implements Printer<Item>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ContentPrinter.class);

	// ========================= FIELDS ====================================

	/**
	 * The tree to be printed.
	 */
	private final Item tree;

	/**
	 * Output stream to print tree to.
	 */
	private final OutputStream stream;

	/**
	 * Prints a tree node before its children are processed.
	 */
	protected ItemPrinter itemPrinter;

	/**
	 * Space increment upon entering a new tree depth.
	 */
	private int indentPerDepth = 3;

	/**
	 * The following fields are used to construct a formatted tree string. Using
	 * StringBuffers instead of String for faster concatenation. When we are done, we use
	 * the output.toString() method to output the tree as a String.
	 */
	// private StringBuffer output = TextUtil.emptyStringBuffer();
	/**
	 * Convenient local variable: current tree depth.
	 */
	private int depth;

	/**
	 * Convenient local variable: current space indentation.
	 */
	private int indent;

	/**
	 * Convenient local variable: item counts (serial numbers) at all tree depths.
	 */
	private AbstractStack<Integer> counterStack;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree printer with default formatting values.
	 * 
	 * @param tree
	 *            the tree to be printed
	 * @param stream
	 *            output stream to print tree to
	 * @param itemPrinter
	 *            prints a tree node before and after its children are processed
	 */
	public ContentPrinter(final Item tree, final OutputStream stream,
			final ItemPrinter itemPrinter)
	{
		this.tree = tree;
		this.stream = stream;
		this.itemPrinter = itemPrinter;
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
		if (itemPrinter != null)
		{
			itemPrinter.setItem(thisItem);
			itemPrinter.setIndent(indent);
			itemPrinter.setIndentNextDepth(indent + indentPerDepth);
			itemPrinter.setNumber(counterStack.peek());
			try
			{
				stream.write(itemPrinter.toStringBufferPre().toString().getBytes());
			}
			catch (IOException e)
			{
				throw new ApplicationException("Could not write to output stream: "
						+ e.getMessage());
			}
		}

		// -----------------------------------
		// Indent
		// -----------------------------------
		depth++;
		indent += indentPerDepth;

		// -----------------------------------
		// Process child nodes
		// -----------------------------------
		Collection<? extends Node> children = thisItem.getChildren();
		counterStack.push(1);
		for (Node child : children)
		{
			visitTo((Item) child);
			int count = counterStack.pop();
			counterStack.push(count + 1);
		}
		counterStack.pop();

		// -----------------------------------
		// Unindent
		// -----------------------------------
		depth--;
		indent -= indentPerDepth;

		// -----------------------------------
		// Post-traversal node printout
		// -----------------------------------
		if (itemPrinter != null)
		{
			itemPrinter.setItem(thisItem);
			itemPrinter.setIndent(indent);
			itemPrinter.setIndentNextDepth(indent + indentPerDepth);
			itemPrinter.setNumber(counterStack.peek());
			try
			{
				stream.write(itemPrinter.toStringBufferPost().toString().getBytes());
			}
			catch (IOException e)
			{
				throw new ApplicationException("Could not write to output stream: "
						+ e.getMessage());
			}
		}

		return null;
	}

	// ========================= METHODS ===================================

	/**
	 * Output the tree's textual representation to the output stream.
	 * 
	 * @param printCounts
	 *            display item counts at the end of the printout.
	 * @return the tree's textual representation
	 */
	public void print(final boolean printCounts)
	{
		depth = 0;
		indent = 0;
		counterStack = new GenericStack<Integer>();
		counterStack.push(1);
		visitTo(tree);

		if (printCounts)
		{
			// Display total item counts
			try
			{
				stream.write(CommonNames.MISC.NEW_LINE_CHAR);
				stream.write(ItemCounter.generateItemCounts(tree).toString().getBytes());
			}
			catch (IOException e)
			{
				throw new ApplicationException("Could not write to output stream: "
						+ e.getMessage());
			}
		}
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Return the indentPerDepth property.
	 * 
	 * @return the indentPerDepth
	 */
	public int getIndentPerDepth()
	{
		return indentPerDepth;
	}

	/**
	 * Set a new value for the indentPerDepth property.
	 * 
	 * @param indentPerDepth
	 *            the indentPerDepth to set
	 */
	public void setIndentPerDepth(int indentPerDepth)
	{
		this.indentPerDepth = indentPerDepth;
	}

	/**
	 * Returns the itemPrinter property.
	 * 
	 * @return the itemPrinter
	 */
	public ItemPrinter getItemPrinter()
	{
		return itemPrinter;
	}

	/**
	 * Sets a new itemPrinter property value.
	 * 
	 * @param itemPrinter
	 *            the itemPrinter to set
	 */
	public void setItemPrinter(ItemPrinter itemPrinter)
	{
		this.itemPrinter = itemPrinter;
	}

}
