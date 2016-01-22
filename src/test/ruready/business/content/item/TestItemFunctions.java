/*****************************************************************************************
 * Source File: TestItemFunctions.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.item.util.ItemUtil;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.eis.content.item.ItemDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.rl.TestEnvTestBase;

/**
 * Tests {@link Question} object database persistence. Populates the database
 * with a set of mock questions.
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
public class TestItemFunctions extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestItemFunctions.class);

	// ========================= FIELDS ====================================

	/**
	 * System user used in all item operations.
	 */
	private User systemUser;

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
		// ---------------------------------------
		// Clean up - delete all items
		// ---------------------------------------
		logger.info("Deleting items ...");
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		List<Item> items = bdItem.findAll(Item.class);
		for (Item item : items)
		{
			bdItem.deleteAll(item, false);
		}
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test basic item creation and deletion using a query.
	 */
	@Test
	public void testDAO_createAndDelete()
	{
		// Session session = environment.getSession();

		// use itemDAO
		ItemDAO<Item> itemDAO = createItemDAO();

		Item item = createMockItem();

		// Save item
		itemDAO.update(item, systemUser.getId());
		logger.debug(item.getMessages());
		Assert.assertEquals(1, item.getMessages().size());

		// Clean-up
		itemDAO.delete(item, systemUser.getId());
	}

	/**
	 * Test basic item creation and deletion using a query.
	 */
	@Test
	public void testDAO_deleteUsingQuery() throws Exception
	{
		Session session = environment.getSession();

		// use itemDAO
		ItemDAO<Item> itemDAO = createItemDAO();

		Item item = createMockItem();

		// Save item
		itemDAO.update(item, systemUser.getId());
		session.flush();

		logger.info("Deleting item ...");
		// This cannot work because of item-message constraint
		// Query query = session.createQuery("delete Item item where id = ?");
		// query.setParameter(0, item.getId());
		// query.executeUpdate();
		session.delete(item);
	}

	/**
	 * Test the method generating a new child to be created under a parent.
	 */
	@Test
	public void testGenerateNewChildName()
	{
		// Create a parent with a list of children
		Item parent = new Item("Parent", null);
		parent.addChild(new Item("Child #1", null));
		parent.addChild(new Item("Some child", null));
		parent.addChild(new Item("A child", null));
		parent.addChild(new Item("Just a child", null));

		// Check name generations
		Assert.assertEquals("New Child", ItemUtil.generateNewChildName("New Child",
				parent.getChildren()));

		// Add a new child and check some more name generations
		String newChildName2 = ItemUtil.generateNewChildName("Child #1", parent
				.getChildren());
		Assert.assertEquals("Child #1 [2]", newChildName2);
		parent.addChild(new Item(newChildName2, null));

		String newChildName3 = ItemUtil.generateNewChildName("Child #1", parent
				.getChildren());
		Assert.assertEquals("Child #1 [3]", newChildName3);
		parent.addChild(new Item(newChildName3, null));

		String newChildName4 = ItemUtil.generateNewChildName("Child #1 [3]", parent
				.getChildren());
		Assert.assertEquals("Child #1 [4]", newChildName4);

		String newChildName4a = ItemUtil.generateNewChildName("Child #1", parent
				.getChildren());
		Assert.assertEquals("Child #1 [4]", newChildName4a);

		// parent.addChild(new Item(newChildName3, null));
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * @param methodString
	 * @return
	 */
	private static Item createMockItem(final String comment)
	{
		/*
		 * create my question
		 */
		Item item = new Item();

		item.setName("test question");
		item.setComment(comment);

		return item;
	}

	/**
	 * @return
	 */
	private static Item createMockItem()
	{
		return createMockItem("Comment " + System.currentTimeMillis());
	}

	/**
	 * helper method for create item dao.
	 */
	private ItemDAO<Item> createItemDAO()
	{
		AbstractEISManager eisManager = environment.getDAOFactory();
		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, environment
				.getContext());
		return itemDAO;
	}
}
