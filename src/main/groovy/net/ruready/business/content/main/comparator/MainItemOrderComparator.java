/*****************************************************************************************
 * Source File: SNComparator.java
 ****************************************************************************************/
package net.ruready.business.content.main.comparator;

import net.ruready.business.common.tree.comparator.TreeNodeComparator;
import net.ruready.business.common.tree.comparator.TreeNodeComparatorID;
import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.MainItemType;
import net.ruready.business.content.main.entity.MainItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A comparator for {@link MainItem}s under the tree node by their order number.
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
public class MainItemOrderComparator extends TreeNodeComparator
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = -8106660330562756272L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(MainItemOrderComparator.class);

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * @return the TreeNodeComparatorID corresponding to this type
	 */
	public TreeNodeComparatorID getIdentifier()
	{
		return TreeNodeComparatorID.MAIN_ITEM_ORDER;
	}

	// ========================= IMPLEMENTATION: Comparator<Node> ====================

	/**
	 * Compare two trees based on {@link MainItemType} descending order number, then by
	 * ascending lexicographic name.
	 * 
	 * @param element1
	 *            left operand to be compared. Must be a {@link MainItem}.
	 * @param element2
	 *            right operand to be compared. Must be a {@link MainItem}.
	 * @return the result of comparison
	 */
	public int compare(Node element1, Node element2)
	{
		if (element1 == null)
		{
			// logger.debug("element1 null, -1");
			return -1;
		}
		if (element2 == null)
		{
			// logger.debug("element2 null, 1");
			return 1;
		}

		// Compare by descending order
		Integer order1 = ((MainItem) element1).getOrder();
		Integer order2 = ((MainItem) element2).getOrder();
		int result = ((order1 == null) ? ((order2 == null) ? 0 : -1) : -order1
				.compareTo(order2));
		// logger.debug("compare(" + element1.getName() + "(" + order1 + ")" + " , "
		// + element2.getName() + "(" + order2 + ")" + ") = " + result);
		if (result != 0)
		{
			return result;
		}

		// Compare by ascending name
		String name1 = element1.getName();
		String name2 = element2.getName();
		return (name1 == null) ? ((name2 == null) ? 0 : -1) : name1.compareTo(name2);
	}

	// ========================= IMPLEMENTATION: TreeNodeComparator ===========

	/**
	 * Refresh node's children list after sorting it. In this case: update children's main
	 * item order numbers; they are <code>SERIAL_NO_BASE</code>-based indices.
	 * 
	 * @param node
	 *            node to be refreshed. Its children must be of type {@link MainItem}
	 * @see net.ruready.business.common.tree.comparator.TreeNodeComparator#refresh(net.ruready.business.common.tree.entity.Node)
	 */
	@Override
	public void refresh(final Node node)
	{

	}
}
