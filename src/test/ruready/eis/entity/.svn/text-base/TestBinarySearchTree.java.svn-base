/*****************************************************************************************
 * Source File: TestBinarySearchTree.java
 ****************************************************************************************/
package test.ruready.eis.entity;

import net.ruready.common.tree.binary.BinarySearchTree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test the binary tree functions of the tree package. Instantiates some
 * specific trees and prints them.
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
 * @version Aug 24, 2007
 */
public class TestBinarySearchTree extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestBinarySearchTree.class);

	// ========================= TESTING METHODS ===========================

	@Test
	public void testIntegerSort()
	{
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
		logger.info("Numbers inserted:");
		for (int i = 0; i < 10; i++)
		{
			int n = (int) (Math.random() * 1000);
			tree.insert(n);
			logger.info(n + " ");
		}
		logger.info("\nPre-order:");
		tree.print(1);
		logger.info("\nIn-order:");
		tree.print();
		logger.info("\nPost-order:");
		tree.print(3);
	}
}
