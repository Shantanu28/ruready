/*****************************************************************************************
 * Source File: NameComparator.java
 ****************************************************************************************/
package net.ruready.business.common.tree.comparator;

import java.io.Serializable;
import java.util.Comparator;

import net.ruready.business.common.tree.entity.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The comparator used to sort children {@link Node} sets inside a {@link Node} class.
 * Delegates to the parent {@link Node}'s comparator.
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
public class ChildComparator implements Comparator<Node>, Serializable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ChildComparator.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	// ========================= IMPLEMENTATION: Comparator<Node> ====================

	/**
	 * Compare two trees based on their parent node's comparator type.
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
			return -1;
		}
		if (element2 == null)
		{
			return 1;
		}

		Node parent1 = element1.getParent();
		Node parent2 = element2.getParent();
		if ((parent1 == null) || (parent2 == null) || (parent1 != parent2))
		{
			// We don't know what to do in this case: children are not under the same
			// parent
			return 0;
		}
		
		// Children have the same parent, use its comparator if it's available
		Comparator<Node> comparator = parent1.getComparator();
		return (comparator == null) ? 0 : comparator.compare(element1, element2);
	}
}
