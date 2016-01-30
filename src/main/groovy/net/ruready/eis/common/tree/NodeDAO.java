/*****************************************************************************************
 * Source File: TreeNodeDAO.java
 ****************************************************************************************/
package net.ruready.eis.common.tree;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.common.eis.dao.DAO;

/**
 * Useful queries and finder methods required by business services. This is an interface
 * that must be implemented for every persistence layer (e.g. Hibernate) .
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
public interface NodeDAO extends DAO<Node, Long>
{
	// ========================= CONSTANTS =================================

	/**
	 * Property constants used in queries by criteria.
	 */
	static final String NAME = "name";

	/**
	 * An example of prepared query constants.
	 */
	static final String QUERY_MAXBID = "TreeDAO.QUERY_MAXBID";

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Find the parent tree node by a unique node identifier. Will return null if node is
	 * not found or if node is found but has no parent (root node).
	 * 
	 * @return nodeId node id
	 * 
	 */
	public Node findParentNodeById(long nodeId);
}
