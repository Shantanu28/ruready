/*****************************************************************************************
 * Source File: HibernateTreeNodeDAO.java
 ****************************************************************************************/
package net.ruready.eis.common.tree;

import java.util.List;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.imports.HibernateDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Data access object (DAO) Hibernate implementation for domain model class {@link Node}.
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
 * @see net.ruready.business.common.tree.entity.Node
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 9, 2007
 */
@Deprecated
public class HibernateNodeDAO extends HibernateDAO<Node, Long> implements NodeDAO
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateNodeDAO.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a DAO from a session and class type.
	 * 
	 * @param persistentClass
	 *            entity's class type
	 * @param session
	 *            Hibernate session
	 * @param sessionFactory
	 *            a heavy-weight session factory that can provide new sessions after old
	 *            ones has closed or have been corrupted.
	 * @param associationManagerFactory
	 *            Produces entity association manager objects.
	 */
	public HibernateNodeDAO(final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(Node.class, sessionFactory, context);
	}

	// ========================= IMPLEMENTATION: NodeDAO ===================

	/**
	 * Find the parent tree node by a unique node identifier. Will return null if node is
	 * not found or if node is found but has no parent (root node).
	 * 
	 * @return nodeId node id
	 * 
	 */
	public Node findParentNodeById(long nodeId) 
	{
		logger.info("Finding tree by node data id");
		Session session = getSession();
		Query query = session.createQuery("from Tree tree where tree.id = ?");
		query.setLockMode("tree", lockMode);
		query.setParameter(0, nodeId);
		List<?> list = query.list();
		logger.debug("list size = " + list.size());
		logger.debug("list = " + list);
		Node node = (Node) query.uniqueResult();
		return (node == null) ? null : node.getParent();
	}
}
