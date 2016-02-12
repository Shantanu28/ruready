/*****************************************************************************************
 * Source File: TraversalPrinter.java
 ****************************************************************************************/
package net.ruready.business.common.tree.entity;

import java.io.Serializable;

import net.ruready.common.exception.SystemException;
import net.ruready.common.misc.Auxiliary;
import net.ruready.common.tree.Printer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Print a the root node of a database-persistent tree only.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 25, 2007
 */
public class NodePrinter implements Printer<Node>, Auxiliary, Serializable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 4003423283402197174L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(NodePrinter.class);

	// ========================= FIELDS ====================================

	/**
	 * The tree to be printed.
	 */
	private final Node tree;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree printer with default formatting values.
	 * 
	 * @param tree
	 *            the tree to be printed
	 */
	public NodePrinter(final Node tree)
	{
		this.tree = tree;
	}

	// ========================= IMPLEMENTATION: TreeVisitor ===============

	/**
	 * Print a tree in pre-traversal order.
	 * 
	 * @param thisNode
	 *            the root node of the tree to be printed.
	 * @return string representation of the tree under the specified root node
	 */
	public Object visitTo(Node thisNode)
	{
		throw new SystemException("This printer prints the top node only");
	}

	// ========================= METHODS ===================================

	/**
	 * @return Output the tree in pre-traversal order as a string.
	 */
	@Override
	public String toString()
	{
		return tree.printData();
	}
}
