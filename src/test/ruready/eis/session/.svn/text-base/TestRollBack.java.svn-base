/*****************************************************************************************
 * Source File: InactiveTestPersistenceTree1.java
 ****************************************************************************************/
package test.ruready.eis.session;

import java.util.List;

import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.exports.AbstractEISBounder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Test rolling back transactions.
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
public class TestRollBack extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestRollBack.class);

	// ========================= FIELDS ====================================

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// Delete all items
		logger.debug("Deleting all items");
		DAO<SimpleItem, Long> dao = environment.getDAOFactory().getDAO(SimpleItem.class,
				environment.getContext());
		List<SimpleItem> items = dao.findAll();
		for (SimpleItem item : items)
		{
			dao.delete(item);
		}
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test a bug we encountered: loading an item, changing it, saving, and
	 * rolling back. The bug is that the item's local changes are saved to the
	 * database. So test that this doesn't happen in stand-alone conditions.
	 */
	@Test
	public void testRollback1()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();

		SimpleItem item = new SimpleItem("old item");

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
		SimpleItem item2 = (SimpleItem) environment.getSession().get(SimpleItem.class,
				item.getId());
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
		SimpleItem item3 = (SimpleItem) environment.getSession().get(SimpleItem.class,
				item.getId());
		logger.debug("item3 " + item3);
		bounder.commitTransaction();
		bounder.closeSession();

		Assert.assertEquals("old item", item3.getName());
	}

	/**
	 * Test a bug we encountered: loading an item, changing it, saving, and
	 * rolling back. The bug is that the item's local changes are saved to the
	 * database. So test that this doesn't happen in stand-alone conditions.
	 */
	@Test
	public void testRollbackSameSession()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();
		bounder.openSession();

		SimpleItem item = new SimpleItem("old item");
		Session session = environment.getSession();

		// Save item
		logger.debug("-------------- Transaction #1 --------------");
		session.beginTransaction();
		session.saveOrUpdate(item);
		session.getTransaction().commit();
		logger.debug("item " + item);

		// Load item, change it, but roll back.
		logger.debug("-------------- Transaction #2 --------------");
		session.beginTransaction();
		SimpleItem item2 = (SimpleItem) session.get(SimpleItem.class, item.getId());
		item2.setName("new item");
		logger.debug("item2 before update " + item2);
		session.saveOrUpdate(item2);
		logger.debug("item2 before roll back " + item2);
		session.getTransaction().rollback();
		logger.debug("item2 after roll back " + item2);

		// Re-load item, check if the old or new copy was saved to the database
		// in
		// transaction 2
		logger.debug("-------------- Transaction #3 --------------");
		session.beginTransaction();
		session.evict(item2);
		SimpleItem item3 = (SimpleItem) session.get(SimpleItem.class, item.getId());
		logger.debug("item3 " + item3);
		session.getTransaction().commit();

		session.close();
		logger.debug("item3 after closing the session" + item3);
		Assert.assertEquals("old item", item3.getName());
	}

	/**
	 * Test a bug we encountered: loading an item, changing it, saving, and
	 * rolling back. The bug is that the item's local changes are saved to the
	 * database. So test that this doesn't happen in stand-alone conditions.
	 */
	@Test
	public void testRollbackNewSession()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();
		bounder.openSession();

		SimpleItem item = new SimpleItem("old item");
		Session session = environment.getSession();

		// Save item
		logger.debug("-------------- Transaction #1 --------------");
		session.beginTransaction();
		session.saveOrUpdate(item);
		session.getTransaction().commit();
		logger.debug("item " + item);

		// Load item, change it, but roll back.
		logger.debug("-------------- Transaction #2 --------------");
		session.beginTransaction();
		SimpleItem item2 = (SimpleItem) session.get(SimpleItem.class, item.getId());
		item2.setName("new item");
		logger.debug("item2 before update " + item2);
		session.saveOrUpdate(item2);
		logger.debug("item2 before roll back " + item2);
		session.getTransaction().rollback();
		session.close();
		logger.debug("item2 after roll back " + item2);

		// Re-load item, check if the old or new copy was saved to the database
		// in
		// transaction 2
		logger.debug("-------------- Transaction #3 --------------");
		session = environment.getSession();
		session.beginTransaction();
		// session.evict(item2);
		SimpleItem item3 = (SimpleItem) session.get(SimpleItem.class, item.getId());
		logger.debug("item3 " + item3);
		session.getTransaction().commit();

		session.close();
		logger.debug("item3 after closing the session" + item3);
		Assert.assertEquals("old item", item3.getName());
	}

	/**
	 * Test a bug we encountered: loading an item, changing it, saving, and
	 * rolling back. The bug is that the item's local changes are saved to the
	 * database. So test that this doesn't happen in stand-alone conditions.
	 */
	@Test
	public void testRollbackThreeSessions()
	{
		SimpleItem item = new SimpleItem("old item");
		Session session;

		// Save item
		logger.debug("-------------- Session #1 --------------");
		session = environment.getSession();
		session.beginTransaction();
		session.saveOrUpdate(item);
		session.getTransaction().commit();
		session.close();
		logger.debug("item " + item);

		// Load item, change it, but roll back.
		logger.debug("-------------- Session #2 --------------");
		session = environment.getSession();
		session.beginTransaction();
		SimpleItem item2 = (SimpleItem) session.get(SimpleItem.class, item.getId());
		item2.setName("new item");
		logger.debug("item2 before update " + item2);
		session.saveOrUpdate(item2);
		logger.debug("item2 before roll back " + item2);
		session.getTransaction().rollback();
		logger.debug("item2 after roll back " + item2);
		session.close();
		logger.debug("item2 after closing the session " + item2);

		// Re-load item, check if the old or new copy was saved to the database
		// in
		// transaction 2
		logger.debug("-------------- Session #3 --------------");
		session = environment.getSession();
		session.beginTransaction();
		// session.evict(item2);
		SimpleItem item3 = (SimpleItem) session.get(SimpleItem.class, item.getId());
		logger.debug("item3 " + item3);
		session.getTransaction().commit();
		session.close();

		logger.debug("item3 after closing the session" + item3);
		Assert.assertEquals("old item", item3.getName());
	}

	// ========================= PRIVATE METHODS ===========================
}
