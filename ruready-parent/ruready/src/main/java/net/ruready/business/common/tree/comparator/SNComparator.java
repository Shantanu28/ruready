/*****************************************************************************************
 * Source File: SNComparator.java
 ****************************************************************************************/
package net.ruready.business.common.tree.comparator;

import net.ruready.business.common.tree.entity.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A comparator for sorted trees based on the root node's serial number.
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
class SNComparator extends TreeNodeComparator
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1534934238066823958L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SNComparator.class);

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * @return the TreeNodeComparatorID corresponding to this type
	 */
	public TreeNodeComparatorID getIdentifier()
	{
		return TreeNodeComparatorID.SERIAL_NO;
	}

	// ========================= IMPLEMENTATION: Comparator<Node> ====================

	/**
	 * Compare two trees based on root nodes' serial number comparison.
	 * 
	 * @param element1
	 *            left operand to be compared
	 * @param element2
	 *            right operand to be compared
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
		// logger.debug("Both non-null, "
		// + element1.getSerialNo().compareTo(element2.getSerialNo()));
		Integer SN1 = element1.getSerialNo();
		Integer SN2 = element2.getSerialNo();
		return ((SN1 == null) ? ((SN2 == null) ? 0 : -1) : ((SN2 == null) ? 1 : SN1
				.compareTo(SN2)));
	}

	// ========================= IMPLEMENTATION: TreeNodeComparator ===========

	/**
	 * Refresh node's children list after sorting it. In this case: update children's
	 * serial numbers; they are <code>SERIAL_NO_BASE</code>-based indices.
	 * 
	 * @param node
	 *            node to be refreshed
	 * @see net.ruready.business.common.tree.comparator.TreeNodeComparator#refresh(net.ruready.business.common.tree.entity.Node)
	 */
	@Override
	public void refresh(final Node node)
	{
		int i = 0;
		for (Node child : node.getChildren())
		{
			child.setSerialNo(i + Node.SERIAL_NO_BASE);
			i++;
		}
	}
}
