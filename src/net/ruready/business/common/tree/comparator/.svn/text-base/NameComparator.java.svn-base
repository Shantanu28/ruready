/*****************************************************************************************
 * Source File: NameComparator.java
 ****************************************************************************************/
package net.ruready.business.common.tree.comparator;

import net.ruready.business.common.tree.entity.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The default comparator for sorted trees. They are sorted by lexicographic ordering of
 * their root node names.
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
class NameComparator extends TreeNodeComparator
{
	// ========================= CONSTANTS ====================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = -6696031332479050443L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(NameComparator.class);

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * @return the TreeNodeComparatorID corresponding to this type
	 */
	public TreeNodeComparatorID getIdentifier()
	{
		return TreeNodeComparatorID.NAME;
	}

	// ========================= IMPLEMENTATION: Comparator<Node> ====================

	/**
	 * Compare two trees based on root nodes' name comparison.
	 * 
	 * @param element1
	 *            left operand to be compared
	 * @param element2
	 *            right operand to be compared
	 * @return the result of comparison
	 */
	public int compare(final Node element1, final Node element2)
	{
		if (element1 == null)
		{
			return -1;
		}
		if (element2 == null)
		{
			return 1;
		}
		String name1 = element1.getName();
		String name2 = element2.getName();
		int result = (name1 == null) ? ((name2 == null) ? 0 : -1) : ((name2 == null) ? 1
				: name1.compareTo(name2));
		// logger.debug("name1 " + name1 + " name2 " + name2 + " result " + result);
		return result;
	}

	// ========================= IMPLEMENTATION: TreeNodeComparator ===========

	/**
	 * Refresh node's children list after sorting it. In this case: do nothing.
	 * 
	 * @param node
	 * @see net.ruready.business.common.tree.comparator.TreeNodeComparator#refresh(net.ruready.business.common.tree.entity.Node)
	 */
	@Override
	public void refresh(final Node node)
	{

	}
}
