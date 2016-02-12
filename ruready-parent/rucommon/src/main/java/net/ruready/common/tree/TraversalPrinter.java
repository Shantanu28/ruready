/*****************************************************************************************
 * Source File: TraversalPrinter.java
 ****************************************************************************************/
package net.ruready.common.tree;

import java.io.Serializable;
import java.util.Collection;

import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Print a tree in pre-traversal ordering. Not coupled to the persistent layer.
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
public class TraversalPrinter<D extends Serializable & Comparable<? super D>, T extends ImmutableTreeNode<D, T>>
		implements Printer<T>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TraversalPrinter.class);

	// Amount of space to add upon each depth increment
	private static final int INDENT_PER_DEPTH = 3;

	// ========================= FIELDS ====================================

	// The tree to be printed
	private T tree;

	// The following fields are used to construct a formatted tree string.
	// Using StringBuffers instead of String for faster concatenation.
	// When we are done, we use the output.toString() method to output
	// the tree as a String.
	private StringBuffer output = TextUtil.emptyStringBuffer();

	private StringBuffer openingBracket = new StringBuffer(CommonNames.TREE.BRACKET_OPEN);

	private StringBuffer closingBracket = new StringBuffer(CommonNames.TREE.BRACKET_CLOSE);

	private StringBuffer space = new StringBuffer(" ");

	private StringBuffer lineFeed = new StringBuffer(CommonNames.MISC.NEW_LINE_CHAR);

	private boolean prePrint = true;

	private boolean postPrint = false;

	private boolean printBrackets = false;

	// Convenient local variables
	private int depth;

	private int indent;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree printer with default formatting values.
	 * 
	 * @param tree
	 *            the tree to be printed
	 */
	public TraversalPrinter(T tree)
	{
		this.tree = tree;
	}

	/**
	 * Construct a tree printer.
	 * 
	 * @param tree
	 *            the tree to be printed
	 * @param openingBracket
	 *            print this bracket before entering a child node depth in the tree
	 * @param closingBracket
	 *            print this bracket before going back to a parent node depth in the tree
	 * @param space
	 *            characters to fill indented space before each node
	 * @param lineFeed
	 *            line feed characters to insert after each node
	 * @param prePrint
	 *            if true, prints nodes in pre-traversal order
	 * @param postPrint
	 *            if true, prints nodes in post-traversal order
	 * @param printBrackets
	 *            if true, prints opening- and closing- node indentation bracket symbols
	 */
	public TraversalPrinter(T tree, String openingBracket, String closingBracket,
			String space, String lineFeed, boolean prePrint, boolean postPrint,
			boolean printBrackets)
	{
		this.tree = tree;
		this.openingBracket = new StringBuffer(openingBracket);
		this.closingBracket = new StringBuffer(closingBracket);
		this.space = new StringBuffer(space);
		this.lineFeed = new StringBuffer(lineFeed);
		this.prePrint = prePrint;
		this.postPrint = postPrint;
		this.printBrackets = printBrackets;
	}

	/**
	 * Copy constructor. Copies all printer fields except the tree reference.
	 * 
	 * @param newTree
	 *            tree reference of the constructed object
	 * @param other
	 *            copy all other printer fields from this object
	 */
	public TraversalPrinter(T newTree, TraversalPrinter<D, T> other)
	{
		this.tree = newTree;

		this.openingBracket = other.openingBracket;
		this.closingBracket = other.closingBracket;
		this.space = other.space;
		this.lineFeed = other.lineFeed;
		this.prePrint = other.prePrint;
		this.postPrint = other.postPrint;
		this.printBrackets = other.printBrackets;
	}

	// ========================= IMPLEMENTATION: TreeVisitor ===============
	/**
	 * Print a tree in pre-traversal order.
	 * 
	 * @param thisNode
	 *            the root node of the tree to be printed.
	 * @return string representation of the tree under the specified root node
	 */
	public Object visitTo(T thisNode)
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		StringBuffer indentStr = TextUtil.emptyStringBuffer();
		for (int i = 0; i < indent; i++)
		{
			indentStr.append(space);
		}

		// -----------------------------------
		// Pre-traversal node printout
		// -----------------------------------
		if (prePrint)
		{
			s.append(indentStr);
			// Call back to node data printout function
			s.append(thisNode.printData());
			s.append(lineFeed);
		}

		// -----------------------------------
		// Indent
		// -----------------------------------
		if (printBrackets)
		{
			s.append(indentStr);
			s.append(openingBracket);
			s.append(lineFeed);
		}
		depth++;
		indent += INDENT_PER_DEPTH;

		// -----------------------------------
		// Process child nodes
		// -----------------------------------
		Collection<? extends T> children = thisNode.getChildren();
		if (children != null)
		{
			for (T child : children)
			{
				s.append((StringBuffer) visitTo(child));
			}
		}
		else
		{
			// Children have not yet been loaded, don't processs them
			s.append(indentStr);
			s.append(" [children not initialized]\n");
		}

		// -----------------------------------
		// Unindent
		// -----------------------------------
		depth--;
		indent -= INDENT_PER_DEPTH;
		if (printBrackets)
		{
			s.append(indentStr);
			s.append(closingBracket);
			s.append(lineFeed);
		}

		// -----------------------------------
		// Post-traversal node printout
		// -----------------------------------
		if (postPrint)
		{
			s.append(indentStr);
			// Call back to node data printout function
			s.append(thisNode.printData());
			s.append(lineFeed);
		}

		return s;
	}

	// ========================= METHODS ===================================

	/**
	 * @return Output the tree in pre-traversal order as a string.
	 */
	@Override
	public String toString()
	{
		depth = 0;
		indent = 0;
		// logger.debug("Printing tree " + "@" +
		// Integer.toHexString(tree.hashCode()));
		// logger.debug("root node children list " + tree.getChildren());
		output = (StringBuffer) visitTo(tree);
		// logger.debug("output " + output);
		return output.toString();
	}
}
