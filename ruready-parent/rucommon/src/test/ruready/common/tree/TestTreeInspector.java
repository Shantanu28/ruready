/*****************************************************************************************
 * Source File: TestItemList.java
 ****************************************************************************************/
package test.ruready.common.tree;

import net.ruready.common.tree.ListTreeNode;
import net.ruready.common.tree.TreeException;
import net.ruready.common.tree.TreeInspector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tests tree inspection (determining whether a tree is cyclic or not
 * traversable).
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
 * @see http://today.java.net/pub/a/today/2006/11/07/nuances-of-java-5-for-each-loop.html
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 4, 2007
 */
public class TestTreeInspector extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestTreeInspector.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test an OK tree.
	 */
	@Test
	public void testOKTree()
	{
		ListTreeNode<Integer> node0 = new ListTreeNode<Integer>(1);
		ListTreeNode<Integer> node00 = new ListTreeNode<Integer>(2);
		ListTreeNode<Integer> node000 = new ListTreeNode<Integer>(3);
		ListTreeNode<Integer> node001 = new ListTreeNode<Integer>(4);
		ListTreeNode<Integer> node01 = new ListTreeNode<Integer>(5);
		ListTreeNode<Integer> node010 = new ListTreeNode<Integer>(6);
		ListTreeNode<Integer> node011 = new ListTreeNode<Integer>(7);

		node00.addChild(node000);
		node00.addChild(node001);
		node01.addChild(node010);
		node01.addChild(node011);
		node0.addChild(node00);
		node0.addChild(node01);

		TreeInspector<Integer, ListTreeNode<Integer>> inspector = new TreeInspector<Integer, ListTreeNode<Integer>>();
		Assert.assertEquals(true, inspector.isTree(node0));
	}

	/**
	 * Test a cyclic tree.
	 */
	@Test
	public void testCyclicTree()
	{
		ListTreeNode<Integer> node0 = new ListTreeNode<Integer>(1);
		ListTreeNode<Integer> node00 = new ListTreeNode<Integer>(2);
		ListTreeNode<Integer> node000 = new ListTreeNode<Integer>(3);
		ListTreeNode<Integer> node001 = new ListTreeNode<Integer>(4);
		ListTreeNode<Integer> node01 = new ListTreeNode<Integer>(5);
		ListTreeNode<Integer> node010 = new ListTreeNode<Integer>(6);
		ListTreeNode<Integer> node011 = new ListTreeNode<Integer>(7);

		node00.addChild(node000);
		node00.addChild(node001);
		node01.addChild(node010);
		node01.addChild(node011);
		node0.addChild(node00);
		node0.addChild(node01);
		// This is the cycle
		node011.addChild(node0);

		TreeInspector<Integer, ListTreeNode<Integer>> inspector = new TreeInspector<Integer, ListTreeNode<Integer>>();
		Assert.assertEquals(false, inspector.isTree(node0));
	}

	/**
	 * Test a cyclic tree with cycle of size 1.
	 */
	@Test
	public void testCycleSize1Tree()
	{
		ListTreeNode<Integer> node0 = new ListTreeNode<Integer>(1);
		try
		{
			node0.addChild(node0);
		}
		catch (TreeException e)
		{
			return;
		}
		throw new IllegalStateException("We shouldn't be here");
	}

	/**
	 * Test a cyclic tree with cycle of size 2.
	 */
	@Test
	public void testCycleSize2Tree()
	{
		ListTreeNode<Integer> node0 = new ListTreeNode<Integer>(1);
		ListTreeNode<Integer> node1 = new ListTreeNode<Integer>(2);
		node0.addChild(node1);
		node1.addChild(node0);

		TreeInspector<Integer, ListTreeNode<Integer>> inspector = new TreeInspector<Integer, ListTreeNode<Integer>>();
		Assert.assertEquals(false, inspector.isTree(node0));

		// Now relax the single-parent condition.
		inspector.setCheckMultipleParents(false);
		Assert.assertEquals(true, inspector.isTree(node0));
	}

	/**
	 * Test a tree that doesn't have a cycle, but has a node with multiple
	 * parents.
	 */
	@Test
	public void testMultipleParentButNotCyclicTree()
	{
		ListTreeNode<Integer> node0 = new ListTreeNode<Integer>(1);
		ListTreeNode<Integer> node00 = new ListTreeNode<Integer>(2);
		ListTreeNode<Integer> node000 = new ListTreeNode<Integer>(3);
		ListTreeNode<Integer> node001 = new ListTreeNode<Integer>(4);
		ListTreeNode<Integer> node01 = new ListTreeNode<Integer>(5);
		ListTreeNode<Integer> node010 = new ListTreeNode<Integer>(6);
		ListTreeNode<Integer> node011 = new ListTreeNode<Integer>(7);

		node00.addChild(node000);
		node00.addChild(node001);
		node01.addChild(node010);
		node01.addChild(node011);
		node0.addChild(node00);
		node0.addChild(node01);
		// This is where we made a node be under a grand-parent in addition to
		// its parent
		node0.addChild(node011);

		TreeInspector<Integer, ListTreeNode<Integer>> inspector = new TreeInspector<Integer, ListTreeNode<Integer>>();
		// OK, because our implementation has a bi-directional parent-child
		// association,
		// it should in principle not let a child be under more than one parent.
		// But
		// ListTreeNode#setParent() doesn't do that. See also SyntaxTreeNode,
		// which does.
		Assert.assertEquals(false, inspector.isTree(node0));

		// Now relax the single-parent condition.
		inspector.setCheckMultipleParents(false);
		Assert.assertEquals(true, inspector.isTree(node0));
	}

	// ========================= TESTING ====================================
}
