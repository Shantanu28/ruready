/*****************************************************************************************
 * Source File: InactiveTestTree.java
 ****************************************************************************************/
package test.ruready.eis.entity;

import net.ruready.business.common.tree.comparator.TreeNodeComparatorID;
import net.ruready.business.common.tree.entity.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test some tree data structures in tree package. Instantiates some specific
 * trees and prints them.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 9, 2007
 */
public class TestTree extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestTree.class);

	// ========================= FIELDS ====================================

	// ========================= TESTING METHODS ===========================

	@Test
	public void testListTree()
	{
		// Construct an a-sorted tree with several nodes
		Node t = new Node("C");

		Node child1 = new Node("11");
		child1.addChild(new Node("5"));
		child1.addChild(new Node("3"));
		t.addChild(child1);

		Node child2 = new Node("B");
		child2.addChild(new Node("4"));
		child2.addChild(new Node("6"));
		t.addChild(child2);

		Node child3 = new Node("A");
		child3.addChild(new Node("4"));
		child3.addChild(new Node("3"));
		t.addChild(child3);

		// Print the tree
		logger.info("A Tree:\n" + t);

		// t.refresh();
		// logger.info("Tree after refreshing:\n" + t);

		// t.refreshAll();
		logger.info("Tree after refreshing all:\n" + t);
	}

	@Test
	public void testSortedTree()
	{
		// Construct an a-sorted tree with several nodes
		Node t = new Node("10");

		Node child1 = new Node("11");
		child1.addChild(new Node("5"));
		child1.addChild(new Node("3"));
		t.addChild(child1);

		Node child2 = new Node("12");
		child2.addChild(new Node("4"));
		child2.addChild(new Node("6"));
		t.addChild(child2);

		Node child3 = new Node("12");
		child3.addChild(new Node("4"));
		child3.addChild(new Node("3"));
		t.addChild(child3);

		// Print the tree
		logger.info("The original Tree:\n" + t);

		t.setComparatorAll(TreeNodeComparatorID.NAME);
		logger.info("Tree after applying a name comparator to all nodes:\n" + t);

		child3.setName("1");
		logger.info("Renamed child3; new tree should not be automatically sorted:\n" + t);

		// t.refreshAll();
		logger.info("Refreshed all; new tree should be automatically sorted:\n" + t);

		t.addChild(new Node("1"));
		logger.info("Added node; tree should be automatically sorted:\n" + t);
	}
}
