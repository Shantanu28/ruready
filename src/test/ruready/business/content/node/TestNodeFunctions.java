/*****************************************************************************************
 * Source File: TestNodeFunctions.java
 ****************************************************************************************/
package test.ruready.business.content.node;

import java.util.ArrayList;
import java.util.List;

import net.ruready.business.common.tree.comparator.TreeNodeComparatorID;
import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.common.tree.util.NodeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tests {@link Node} object database persistence and business functions, e.g.
 * changing the order of children {@link Node}s at run-time.
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
 * @version Aug 5, 2007
 */
public class TestNodeFunctions extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestNodeFunctions.class);

	// ========================= FIELDS ====================================

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test the default node children comparator (by name).
	 */
	@Test
	public void testNameComparator()
	{
		// Create a new node with a set of children.
		Node root = new Node("root", null);
		Node child1 = new Node("child 1", null);
		Node child2 = new Node("child 2", null);
		Node child3 = new Node("child 3", null);
		Node child4 = new Node("child 4", null);
		root.addChild(child4);
		root.addChild(child3);
		root.addChild(child2);
		root.addChild(child1);

		// The default comparator sorts children by name. See that
		// that's indeed the case.
		List<Node> expectedChildrenByName = new ArrayList<Node>();
		expectedChildrenByName.add(child1);
		expectedChildrenByName.add(child2);
		expectedChildrenByName.add(child3);
		expectedChildrenByName.add(child4);
		List<Node> actualChildren = new ArrayList<Node>(root.getChildren());
		Assert.assertEquals(expectedChildrenByName, actualChildren);
		logger.debug(root.getChildren());
	}

	/**
	 * Test changing a node's children comparator at run-time.
	 */
	@Test
	public void testComparatorChange()
	{
		// Create a new node with a set of children.
		Node root = new Node("root", null);
		Node child1 = new Node("child 1", null);
		Node child2 = new Node("child 2", null);
		Node child3 = new Node("child 3", null);
		Node child4 = new Node("child 4", null);
		root.addChild(child4);
		root.addChild(child3);
		root.addChild(child2);
		root.addChild(child1);
		logger.debug(root.getChildren());

		// The default comparator sorts children by name. Switch
		// to a custom order.
		child4.setSerialNo(1);
		child3.setSerialNo(2);
		child2.setSerialNo(3);
		child1.setSerialNo(4);
		root.setComparatorType(TreeNodeComparatorID.SERIAL_NO);

		List<Node> expectedChildrenBySN = new ArrayList<Node>();
		expectedChildrenBySN.add(child4);
		expectedChildrenBySN.add(child3);
		expectedChildrenBySN.add(child2);
		expectedChildrenBySN.add(child1);
		List<Node> actualChildren = new ArrayList<Node>(root.getChildren());
		Assert.assertEquals(expectedChildrenBySN, actualChildren);
		logger.debug(root.getChildren());
	}

	/**
	 * Test merging a tree into another tree.
	 */
	@Test
	public void testMerge()
	{
		// Construct a source node hierarchy
		Node source = new Node("source", null);
		{
			Node child1 = new Node("source child 1", null);
			Node child11 = new Node("source child 11", null);
			Node child12 = new Node("source child 12", null);
			child1.addChild(child11);
			child1.addChild(child12);
			source.addChild(child1);
			Node child2 = new Node("source child 2", null);
			source.addChild(child2);
			Node child3 = new Node("source child 3", null);
			source.addChild(child3);
			Node child4 = new Node("source child 4", null);
			source.addChild(child4);
			Node child5 = new Node("source child 5", null);
			source.addChild(child5);
		}
		logger.debug(source);

		// Construct a destination node hierarchy
		Node destination = new Node("destination", null);
		{
			Node child1 = new Node("source child 1", null);
			Node child11 = new Node("destination child 11", null);
			Node child12 = new Node("source child 12", null);
			child1.addChild(child11);
			child1.addChild(child12);
			destination.addChild(child1);
			Node child2 = new Node("destination child 2", null);
			destination.addChild(child2);
			Node child3 = new Node("destination child 3", null);
			destination.addChild(child3);
			Node child4 = new Node("source child 4", null);
			destination.addChild(child4);
			Node child5 = new Node("destination child 5", null);
			destination.addChild(child5);
		}

		logger.debug("destination = " + destination);
		logger.debug("source      = " + source);

		logger.debug("Merging...");
		NodeUtil.merge(source, destination);
		logger.debug("destination = " + destination);
	}
}
