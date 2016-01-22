/*****************************************************************************************
 * Source File: TreeNodeComparator.java
 ****************************************************************************************/
package net.ruready.business.common.tree.comparator;

import java.util.Comparator;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.common.discrete.Identifiable;
import net.ruready.common.pointer.ValueObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A base type for tree node comparators.
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
public abstract class TreeNodeComparator implements Comparator<Node>,
		Identifiable<TreeNodeComparatorID>, ValueObject
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TreeNodeComparator.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Refresh node's children list after sorting it.
	 * 
	 * @param node
	 *            node to be refreshed
	 */
	public abstract void refresh(final Node node);

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * Return the item type. Delegates to the ID (which is obtained via an abstract method
	 * in this class).
	 */
	public String getType()
	{
		return getIdentifier().getType();
	}
}
