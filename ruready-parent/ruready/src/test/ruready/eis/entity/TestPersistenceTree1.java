/*****************************************************************************************
 * Source File: InactiveTestPersistenceTree1.java
 ****************************************************************************************/
package test.ruready.eis.entity;

import java.util.List;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.common.eis.exports.AbstractEISBounder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Test tree persistence using Hibernate. Tree nodes contain basic data types
 * (e.g. Strings).
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 24, 2007
 */
public class TestPersistenceTree1 extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestPersistenceTree1.class);

	// ========================= FIELDS ====================================

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// Delete all tree nodes
		logger.debug("Deleting all nodes");
		Session session = environment.getSession();
		session.createQuery("delete Node node").executeUpdate();
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * @param args
	 */
	@Test
	public void testTreeLoad()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();
		bounder.openSession();
		bounder.beginTransaction();

		// Create a new tree
		Node t = createListTree();
		logger.info("Created a tree t: " + t);

		// Save tree to database
		saveTree(t);

		// t is already bound to the session. Testing loading by its id.
		t = loadTreeById(t.getId());
		logger.info("Printing tree:" + t);

		// Find the tree "t" in the database
		List<?> trees = loadTreeByExample(t);
		logger.info("Found " + trees.size() + " tree(s) matching t: ");
		for (Object o : trees)
		{
			logger.info(o);
			logger.info("==================================================");
		}

		// Delete tree from database
		deleteTree(t);

		bounder.commitTransaction();
		bounder.closeSession();
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Construct an a-sorted tree with several nodes
	 * 
	 * @return a demo tree
	 */
	private Node createListTree()
	{
		Node t = new Node("C");

		Node child1 = new Node("11");
		child1.addChild(new Node("5"));
		child1.addChild(new Node("3"));
		t.addChild(child1);

		Node child2 = new Node("B");
		child2.addChild(new Node("6"));
		child2.addChild(new Node("4"));
		t.addChild(child2);

		Node child3 = new Node("A");
		child3.addChild(new Node("4"));
		child3.addChild(new Node("3"));
		t.addChild(child3);

		return t;
	}

	/**
	 * Save tree to database.
	 * 
	 * @param t
	 *            tree to save
	 */
	private void saveTree(Node t)
	{
		logger.info("Saving tree to database ...");
		Session session = environment.getSession();
		session.save(t);
	}

	/**
	 * Find and load tree by identifier.
	 * 
	 * @param id
	 *            tree id to look for
	 */
	private Node loadTreeById(long id)
	{
		logger.info("Loading tree from database ...");
		Session session = environment.getSession();
		Node tree = (Node) session.load(Node.class, id);
		return tree;
	}

	/**
	 * Find and load tree by example.
	 * 
	 * @param exampleInstance
	 *            tree to look for
	 */
	private List<?> loadTreeByExample(Node exampleInstance)
	{
		logger.info("Loading tree from database by example ...");
		Session session = environment.getSession();
		Criteria crit = session.createCriteria(Node.class);
		crit.add(Example.create(exampleInstance));
		List<?> list = crit.list();
		return list;
	}

	/**
	 * Delete tree from database.
	 * 
	 * @param t
	 *            tree to delete
	 */
	private void deleteTree(Node t)
	{
		logger.info("Deleting tree from database ...");
		Session session = environment.getSession();
		session.delete(t);
	}
}
