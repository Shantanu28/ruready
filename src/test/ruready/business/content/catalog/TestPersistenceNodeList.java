/*****************************************************************************************
 * Source File: TestPersistenceTreeNodeList.java
 ****************************************************************************************/
package test.ruready.business.content.catalog;

import java.util.List;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * A simple test that lists all items found in the database using an HQL queries
 * and criteria.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 30, 2007
 */
public class TestPersistenceNodeList extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestPersistenceNodeList.class);

	// ========================= FIELDS ====================================

	// ========================= PUBLIC TESTING METHODS ====================

	// Not completely formulated as a test case yet, just look at output for now
	@Test
	public void testPersistenceTreeNodeList()
	{
		Session session = environment.getSession();
		List<?> treeNodes = null;

		// ===============================================
		// Find all items
		// ===============================================
		/*
		 * session.beginTransaction(); treeNodes = session.createQuery("from
		 * Node").list(); query.lockMode(...); // Print the results
		 * logger.info("All tree nodes found: "); for (Object treeNodeRaw :
		 * treeNodes) { Node treeNode = (Node) treeNodeRaw;
		 * logger.info(treeNode.getId() + " " + treeNode.printData()); }
		 */

		// ===============================================
		// Find by example
		// ===============================================
		Item item = new Item("Calculus", null);
		Example criterion = Example.create(item);
		criterion.excludeZeroes();
		criterion.excludeProperty("parent");
		criterion.excludeProperty("comment");
		criterion.excludeProperty("version");
		// criterion.enableLike();
		Criteria crit = session.createCriteria(Item.class);
		crit.add(criterion);

		session.beginTransaction();
		treeNodes = crit.list();
		session.getTransaction().commit();

		// Print the results
		logger.info("Tree nodes matching example '" + item.getName() + "': ");
		for (Object treeNodeRaw : treeNodes)
		{
			Node treeNode = (Node) treeNodeRaw;
			logger.info(treeNode.getId() + " " + treeNode.printData());
		}

	}
}
