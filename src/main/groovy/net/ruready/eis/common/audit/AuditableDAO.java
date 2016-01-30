/*****************************************************************************************
 * Source File: ItemDAO.java
 ****************************************************************************************/
package net.ruready.eis.common.audit;

import java.io.Serializable;
import java.util.List;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.common.audit.Auditable;
import net.ruready.common.audit.Message;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.entity.PersistentEntity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A custom Data Access Object (DAO) for a item. To use custom functions of the same node
 * as a {@link Node} that do not appear in this DAO, use a TreeNodeDAO instead.
 * <p>
 * Deprecated - replaced by cascade annotations.
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
 * -------------------------------------------------------------------------
 * 
 * @see net.ruready.item.entity.DemoCatalogCreator
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 24, 2007
 */
@Deprecated
public interface AuditableDAO<I extends Auditable<V, M> & PersistentEntity<ID>, ID extends Serializable, V extends Comparable<? super V>, M extends Message>
		extends DAO<I, ID>
{
	// ========================= CONSTANTS =================================

	static final Log logger = LogFactory.getLog(AuditableDAO.class);

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Read the all audit message associated with an item.
	 * 
	 * @todo add lower and upper limits on the result set here for efficiency when there
	 *       are a lot of messages per item.
	 * @param item
	 *            item whose messages we search
	 * @return revision history sorted by descending date
	 * 
	 */
	List<M> readAuditMessages(I item);

	/**
	 * Delete all audit message associated with an item.
	 * 
	 * @param item
	 *            item whose messages we search
	 * @return revision history sorted by descending date
	 * 
	 */
	void deleteAuditMessages(I item);
}
