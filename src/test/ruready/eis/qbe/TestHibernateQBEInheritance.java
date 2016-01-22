/*****************************************************************************************
 * Source File: InactiveTestHibernateQBEInheritance.java
 ****************************************************************************************/
package test.ruready.eis.qbe;

import java.util.Iterator;
import java.util.List;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.SubTopic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Testing Hibernate Query By Example (QBE) on a table with multiple entity
 * types inheriting from a base type ({@link Node} in this case). We search for
 * all {@link Node}s by certain criteria and expect to find <i>all</i>
 * {@link Node} sub-types matching these criteria.
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
 * @version Aug 25, 2007
 */
public class TestHibernateQBEInheritance extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(TestHibernateQBEInheritance.class);

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		// Initialize
		Session session = environment.getSession();
		Node item1 = new Course("New Course", "0000");
		session.save(item1);

		Node item2 = new SubTopic("New Topic", null);
		session.save(item2);

		Node item3 = new SubTopic("Old Topic", null);
		session.save(item3);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// Delete all tree nodes
		Session session = environment.getSession();
		session.createQuery("delete Node node").executeUpdate();
	}

	// ========================= PUBLIC TESTING METHODS ====================

	// Not completely formulated as a test case yet, just look at output for now
	@Test
	public void testPartialPopulation()
	{
		List<?> results = findItems();
		displayProductsList(results);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Create a QBE with multiple criteria that use the association between
	 * <code>Product</code> and <code>Supplier</code>. In this case, we
	 * search for all products starting with "P" regardless of their price, that
	 * are supplied by a supplier with the name "Supplier Name 1".
	 * 
	 * @return list of products found
	 */
	private List<?> findItems()
	{
		Session session = environment.getSession();
		session.beginTransaction();
		Criteria itemCrit = session.createCriteria(Node.class);
		Node item = new Node("New%");
		Example itemExample = Example.create(item);
		itemExample.excludeProperty("price");
		itemExample.enableLike();
		/*
		 * Criteria suppCrit = prdCrit.createCriteria("supplier"); Supplier
		 * supplier = new Supplier(); supplier.setName("Supplier Name 1");
		 * suppCrit.add(Example.create(supplier));
		 */
		itemCrit.add(itemExample);
		List<?> results = itemCrit.list();
		session.getTransaction().commit();

		return results;
	}

	/**
	 * Display a result list of products
	 * 
	 * @param list
	 *            list of products obtained from a Hibernate QBE query
	 */
	public void displayProductsList(List<?> list)
	{
		Iterator<?> iter = list.iterator();
		if (!iter.hasNext())
		{
			logger.info("No products to display.");
			return;
		}
		while (iter.hasNext())
		{
			Node item = (Node) iter.next();
			String msg = item.getId() + "\t";
			msg += item.getName();
			logger.info(msg);
		}
	}
}
