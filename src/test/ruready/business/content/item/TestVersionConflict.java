/*****************************************************************************************
 * Source File: InactiveTestPersistenceTree1.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import java.util.List;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.eis.exception.StaleRecordException;
import net.ruready.common.eis.exports.AbstractEISBounder;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.imports.StandAloneMainItemBD;
import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Test version conflict among item instances.
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
public class TestVersionConflict extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestVersionConflict.class);

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

		// Initialize root node
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(
				environment.getContext(), systemUser);
		bdMainItem.createUnique(Root.class, CommonNames.MISC.INVALID_VALUE_INTEGER);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// Delete all tree items
		logger.debug("Deleting all items");
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		// Deleting the root node and its descendants
		Item root = getRoot();
		bdItem.deleteAll(root, false);

		// Delete all other items
		List<Item> items = bdItem.findAll(Item.class);
		for (Item item : items)
		{
			bdItem.deleteAll(item, false);
		}
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test a case where there's no conflict (one user).
	 */
	@Test
	public void testNoConflictOneSession()
	{
		// Create an item
		AbstractEISBounder bounder = environment.getDAOFactory();
		bounder.openSession();
		bounder.beginTransaction();

		Item item = new Catalog("My catalog", "My comment");
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		bdItem.createUnder(getRoot().getId(), item);

		bounder.commitTransaction();
		bounder.closeSession();
	}

	/**
	 * Test a case where there's no conflict between two users.
	 */
	@Test
	public void testNoConflictTwoSessions()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		// First user creates an item
		bounder.openSession();
		bounder.beginTransaction();
		Item item = new Catalog("My catalog", "User 1 comment");
		bdItem.createUnder(getRoot().getId(), item);
		bounder.commitTransaction();
		bounder.closeSession();

		// Second user loads the item, updates it and saves it
		bounder.openSession();
		bounder.beginTransaction();
		Item item2 = bdItem.findById(Item.class, item.getId());
		item2.setComment("User 2 comment");
		bdItem.update(item2, true);
		bounder.commitTransaction();
		bounder.closeSession();
	}

	/**
	 * Test a case where there's a conflict between two users.
	 */
	@Test
	public void testConflictTwoSessions()
	{

		AbstractEISBounder bounder = environment.getDAOFactory();
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		// First user creates an item
		bounder.openSession();
		bounder.beginTransaction();
		Item item = new Catalog("My catalog", "User 1 comment");
		bdItem.createUnder(getRoot().getId(), item);
		bounder.commitTransaction();
		bounder.closeSession();

		// Second user loads the item (doesn't update it yet;
		// had he updated and not even called saveOrUpdate(),
		// Hibernate WILL update the fields and increment the version # when the
		// transaction is committed.
		bounder.openSession();
		bounder.beginTransaction();
		// Item item2 = bdItem.findById(Item.class, item.getId());
		Item item2 = (Item) environment.getSession().get(Item.class, item.getId());
		// item2.setComment("User 2 comment"); // See comment above
		bounder.commitTransaction();
		bounder.closeSession();

		// First user updates the item
		bounder.openSession();
		bounder.beginTransaction();
		item.setComment("User 1 new comment");
		bdItem.update(item, true);
		bounder.commitTransaction();
		bounder.closeSession();

		// Simulate a second user attempts to save his stale copy
		try
		{
			bounder.openSession();
			bounder.beginTransaction();
			// Can't work directly with item2 because it's no longer associated
			// with a session and might throw LazyInitializationExceptions.
			// Re-load
			// it and set the local version to item2's version.
			Item item2a = (Item) environment.getSession().get(Item.class, item2.getId());
			item2a.setLocalVersion(item2.getVersion());
			item2a.setComment("User 2 comment");
			bdItem.update(item2a, true);
			bounder.commitTransaction();
			bounder.closeSession();
		}
		catch (StaleRecordException e)
		{
			logger.debug("Conflict detected");
			return;
		}
		throw new IllegalStateException("Conflict was not detected!");
	}

	/**
	 * Test a bug we encountered: loading an item, changing its child and
	 * rolling back. The bug is that the child's local changes are saved to the
	 * database. So test that this doesn't happen in stand-alone conditions.
	 */
	@Test
	public void testRollbackShouldNotSave()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		Item parent = new Item("parent", null);
		Item child = new Item("child", null);
		parent.addChild(child);

		// Save parent and child
		bounder.openSession();
		bounder.beginTransaction();
		bdItem.updateAll(parent);
		bounder.commitTransaction();
		bounder.closeSession();

		// Load parent, change child, save parent, but roll back. Should
		// certainly
		// not affect child. In fact, should not affect parent as well.
		bounder.openSession();
		bounder.beginTransaction();
		Item parent2 = (Item) environment.getSession().get(Item.class, parent.getId());
		child.setName("new child");
		bdItem.update(parent2, true);
		bounder.rollbackTransaction();
		bounder.closeSession();

		// Re-load parent, check if child was saved to the copy that was rolled
		// back
		bounder.openSession();
		bounder.beginTransaction();
		Item child2 = (Item) environment.getSession().get(Item.class, child.getId());
		bounder.rollbackTransaction();
		bounder.closeSession();

		logger.debug("child2 " + child2);
		Assert.assertEquals("child", child2.getName());
	}

	/**
	 * Test a bug we encountered: loading an item, changing its child and
	 * rolling back. The bug is that the child's local changes are saved to the
	 * database. So test that this doesn't happen in stand-alone conditions.
	 */
	@Test
	public void testRollbackShouldNotSave2()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		Item parent = new Item("parent", null);
		Item child = new Item("old child", null);
		parent.addChild(child);

		// Save parent and child
		logger.debug("-------------- Session #1 --------------");
		bounder.openSession();
		bounder.beginTransaction();
		bdItem.updateAll(parent);
		bounder.commitTransaction();
		bounder.closeSession();
		logger.debug("child " + child);

		// Load parent, change child, but roll back.
		logger.debug("-------------- Session #2 --------------");
		bounder.openSession();
		bounder.beginTransaction();
		logger.debug("Setting autocommit to 0 manually");
		environment.getSession().createSQLQuery("set autocommit = 0").executeUpdate();
		Item parent2 = (Item) environment.getSession().get(Item.class, parent.getId());
		Item parent2child = (Item) parent2.getChildren().first();
		parent2child.setName("new child");
		logger.debug("parent2child before update " + parent2child);
		bdItem.update(parent2child, true);
		logger.debug("parent2child before roll back " + parent2child);
		bounder.rollbackTransaction();
		logger.debug("parent2child after roll back " + parent2child);
		bounder.closeSession();
		logger.debug("parent2child after closing session " + parent2child);

		// Re-load parent, check if child was saved to the copy that was rolled
		// back
		logger.debug("-------------- Session #3 --------------");
		bounder.openSession();
		bounder.beginTransaction();
		logger.debug("Current database items = " + bdItem.findAll(Item.class));
		Item child2 = (Item) environment.getSession().get(Item.class, child.getId());
		logger.debug("child2 " + child2);
		bounder.commitTransaction();
		bounder.closeSession();

		Assert.assertEquals("old child", child2.getName());
	}

	/**
	 * Test a bug we encountered: loading an item, changing it, saving, and
	 * rolling back. The bug is that the item's local changes are saved to the
	 * database. So test that this doesn't happen in stand-alone conditions.
	 */
	@Test
	public void testRollbackOneItem()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		Item item = new Item("old item", null);

		// Save item
		logger.debug("-------------- Session #1 --------------");
		bounder.openSession();
		bounder.beginTransaction();
		bdItem.updateAll(item);
		bounder.commitTransaction();
		bounder.closeSession();
		logger.debug("item " + item);

		// Load item, change it, but roll back.
		logger.debug("-------------- Session #2 --------------");
		bounder.openSession();
		bounder.beginTransaction();
		Item item2 = (Item) environment.getSession().get(Item.class, item.getId());
		item2.setName("new item");
		logger.debug("item2 before update " + item2);
		bdItem.update(item2, true);
		logger.debug("item2 before roll back " + item2);
		bounder.rollbackTransaction();
		logger.debug("item2 after roll back " + item2);
		bounder.closeSession();
		logger.debug("item2 after closing session " + item2);

		// Re-load item, check if the old or new copy was saved to the database
		// in session 2
		logger.debug("-------------- Session #3 --------------");
		bounder.openSession();
		bounder.beginTransaction();
		Item item3 = (Item) environment.getSession().get(Item.class, item.getId());
		logger.debug("item3 " + item3);
		bounder.commitTransaction();
		bounder.closeSession();

		Assert.assertEquals("old item", item3.getName());
	}

	/**
	 * Same as testRollbackOneItem(), but calls Hibernate session commands
	 * directly instead of using our BD, so that audit messages are not
	 * generated and saved, only the item.
	 */
	@Test
	public void testRollbackManualSessionCommands()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();

		Item item = new Item("old item", null);

		// Save item
		logger.debug("-------------- Session #1 --------------");
		bounder.openSession();
		bounder.beginTransaction();
		environment.getSession().saveOrUpdate(item);
		bounder.commitTransaction();
		bounder.closeSession();
		logger.debug("item " + item);

		// Load item, change it, but roll back.
		logger.debug("-------------- Session #2 --------------");
		bounder.openSession();
		bounder.beginTransaction();
		Item item2 = (Item) environment.getSession().get(Item.class, item.getId());
		item2.setName("new item");
		logger.debug("item2 before update " + item2);
		environment.getSession().saveOrUpdate(item2);
		logger.debug("item2 before roll back " + item2);
		bounder.rollbackTransaction();
		logger.debug("item2 after roll back " + item2);
		bounder.closeSession();
		logger.debug("item2 after closing session " + item2);

		// Re-load item, check if the old or new copy was saved to the database
		// in session 2
		logger.debug("-------------- Session #3 --------------");
		bounder.openSession();
		bounder.beginTransaction();
		Item item3 = (Item) environment.getSession().get(Item.class, item.getId());
		logger.debug("item3 " + item3);
		bounder.commitTransaction();
		bounder.closeSession();

		Assert.assertEquals("old item", item3.getName());
	}

	// ========================= PRIVATE METHODS ===========================

	private Item getRoot()
	{
		logger.debug("Deleting all items");
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(
				environment.getContext(), systemUser);
		Item root = bdMainItem.readUnique(Root.class);
		return root;
	}
}
