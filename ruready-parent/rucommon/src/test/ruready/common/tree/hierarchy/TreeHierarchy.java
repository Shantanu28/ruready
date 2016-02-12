/*****************************************************************************************
 * Source File: TestHierarchy.java
 ****************************************************************************************/
package test.ruready.common.tree.hierarchy;

import net.ruready.common.rl.CommonNames;
import net.ruready.common.tree.hierarchy.DynamicJunctionNode;
import net.ruready.common.tree.hierarchy.DynamicNode;
import net.ruready.common.tree.hierarchy.TreeDynamicJunctionNode;
import net.ruready.common.tree.hierarchy.TreeDynamicNode;
import net.ruready.common.tree.hierarchy.TreeJunctionNode;
import net.ruready.common.tree.hierarchy.TreeNamedRootNode;
import net.ruready.common.tree.hierarchy.TreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * A JUnit test cases to test a hierarchy of tree node classes.
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
 * @version Sep 26, 2007
 */
public class TreeHierarchy extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TreeHierarchy.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test the hierarchy of nodes and their fields.
	 */
	@Test
	public void testHierarchy()
	{
		TreeNamedRootNode root = new TreeNamedRootNode("name");
		logger.info("root            : " + root);
		logger.info("root children   : " + root.getChildren());
		logger.info(CommonNames.MISC.EMPTY_STRING);

		TreeNode node = new TreeNode();
		logger.info("node            : " + node);
		logger.info("node children   : " + node.getChildren());
		logger.info(CommonNames.MISC.EMPTY_STRING);

		TreeJunctionNode jNode = new TreeJunctionNode(root);
		logger.info("jNode            : " + jNode);
		logger.info("jNode children   : " + jNode.getChildren());
		logger.info("jNode parent name: " + jNode.getParent());
		logger.info(CommonNames.MISC.EMPTY_STRING);

		DynamicNode tdNode = new TreeDynamicNode(node);
		tdNode.setStructure("Sorted");
		logger.info("tdNode            : " + tdNode);
		logger.info("tdNode children   : " + tdNode.getChildren());
		logger.info("tdNode structure  : " + tdNode.getStructure());
		logger.info(CommonNames.MISC.EMPTY_STRING);

		DynamicJunctionNode tdjNode = new TreeDynamicJunctionNode(jNode);
		tdjNode.setStructure("Sorted");
		logger.info("tdjNode            : " + tdjNode);
		logger.info("tdjNode children   : " + tdjNode.getChildren());
		logger.info("tdjNode structure  : " + tdjNode.getStructure());
		logger.info(CommonNames.MISC.EMPTY_STRING);

	}
}
