/*****************************************************************************************
 * Source File: InactiveTestPersistenceTree2.java
 ****************************************************************************************/
package test.ruready.eis.entity;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.eis.common.tree.NodeDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.junit.Test;

import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.rl.StandAloneEnvTestBase;
import test.ruready.rl.TestingNames;

/**
 * Create and save a demo catalog to the database.
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
public class TestPersistenceTree2 extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestPersistenceTree2.class);

	// ========================= FIELDS ====================================

	/**
	 * System user used in all item operations.
	 */
	private static User systemUser;

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		systemUser = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// ===================================
		// Delete the test catalog
		// ===================================
		AbstractEditItemBD<MainItem> bdMainItem = new StandAloneEditItemBD<MainItem>(
				MainItem.class, environment.getContext(), systemUser);

		// Delete catalog
		logger.info("Deleting catalog ...");
		MainItem catalog = bdMainItem.findByUniqueProperty(MainItem.class, NodeDAO.NAME,
				TestingNames.CONTENT.BASE.CATALOG_NAME);
		bdMainItem.deleteAll(catalog, false);
	}

	// ========================= PUBLIC TESTING METHODS ====================

	// Not completely formulated as a test case yet, just look at output for now
	@Test
	public void testPersistenceTree2()
	{
		Session session = environment.getSession();

		// Will read item's children (it's normally lazy-initialized)

		// Create a tree
		TagCabinet tagCabinet = ItemDemoUtil.createBase(TagCabinet.class,
				ContentNames.UNIQUE_NAME.TAG_CABINET);
		Node tree = ItemDemoUtil.createBase(Catalog.class,
				TestingNames.CONTENT.BASE.CATALOG_NAME, tagCabinet);

		// Print the tree
		logger.info("Printing tree:");
		logger.info(tree);

		// Save tree to database
		logger.info("Saving tree to database...");
		session.save(tree);
	}
}
