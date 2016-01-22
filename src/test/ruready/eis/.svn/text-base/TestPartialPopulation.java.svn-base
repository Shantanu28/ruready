/*****************************************************************************************
 * Source File: TestPartialPopulation.java
 ****************************************************************************************/
package test.ruready.eis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Testing partial population of objects. Some of the fields in Bean are
 * transient; are they preserved after a session loads its persistent fields?
 * Answer: YES. Hibernate loads only the persistent fields and leaves intact
 * transient fields IN ANOTHER Hibernate Session. In the same.
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
 * @version Aug 31, 2007
 */
public class TestPartialPopulation extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestPartialPopulation.class);

	// ========================= FIELDS ====================================

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// Delete all beans
		Session session = environment.getSession();
		session.createQuery("delete Bean bean").executeUpdate();
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test partial population of a persistent class.
	 */
	@Test
	public void testPartialPopulation()
	{
		// =============================
		// Unit of work 1
		// =============================
		Session session = environment.getSession();

		// Create a user and a dependent phone object
		logger.info("Creating a new bean object...");
		Bean bean = new Bean("value1", "value2");
		logger.info("bean = " + bean);

		// Save bean to database
		logger.info("Saving bean to database...");
		session.beginTransaction();
		session.save(bean);
		session.getTransaction().commit();
		logger.info("bean = " + bean);

		// Create another bean and partially populate it
		logger.info("Creating another bean2 and partially populating it...");
		Bean bean2 = new Bean();
		bean2.setField2("new value 2");
		logger.info("bean2 = " + bean2);

		// In a single thread model of HibernateSessionFactory, each user will
		// have its own thread and own session. The following can happen in a
		// *new session*.
		session.close();

		// =============================
		// Unit of work 2
		// =============================
		// Load bean on top of bean2. field2's new value of the transient
		// entity bean2 should be preserved after we make it persistent and load
		// into it.
		logger.info("Loading bean database info into bean2 ...");
		Session session2 = environment.getSession();
		session2.beginTransaction();
		session2.load(bean2, bean.getId());
		session2.getTransaction().commit();
		logger.info("bean2 = " + bean2);
		Assert.assertEquals("new value 2", bean2.getField2());
		session2.close();
	}

	/**
	 * Test saving an entity, then loading it, then changing some fields and
	 * re-loading. Does <code>load()</code> override these fields?
	 */
	@Test
	public void testLoadIntoPersistent()
	{
		Session session = environment.getSession();

		// Create and save a new object
		logger.info("Creating a new object...");
		Bean bean = new Bean("value1", "value2");
		logger.info("bean = " + bean);

		logger.info("Saving bean to database...");
		session.beginTransaction();
		session.save(bean);
		session.getTransaction().commit();
		logger.info("bean = " + bean);

		// Load object and change some fields
		Bean bean2 = (Bean) session.load(Bean.class, bean.getId());
		bean2.setField1("New value 1");
		logger.info("bean2 = " + bean2);

		// Re-load object into bean2
		logger.info("Reloading...");
		session.beginTransaction();
		Bean bean3 = (Bean) session.load(Bean.class, bean.getId());
		logger.info("bean3 = " + bean3);
		// bean3.field1 has the updated value from bean2 because it is the
		// unique
		// entity associated with the session.
		Assert.assertEquals("New value 1", bean3.getField1());
	}

	/**
	 * Test loading into a transient entity. Does <code>load()</code> override
	 * the transient entity's fields if they are different than the database
	 * values?
	 */
	@Test
	public void testLoadIntoTransient()
	{
		Session session = environment.getSession();

		// Create and save a new object
		logger.info("Creating a new object...");
		Bean bean = new Bean("value1", "value2");
		logger.info("bean = " + bean);

		logger.info("Saving bean to database...");
		session.beginTransaction();
		session.save(bean);
		session.getTransaction().commit();
		logger.info("bean = " + bean);

		// Make a new transient object and change some fields
		Bean bean2 = new Bean();
		bean2.setField1("New value 1");
		logger.info("bean2 = " + bean2);

		// Load object into bean2
		logger.info("Reloading...");
		session.beginTransaction();
		bean2 = (Bean) session.get(Bean.class, bean.getId());
		// Yes, all of bean2's persistent fields will be overridden
		Assert.assertEquals("value1", bean2.getField1());
	}

	/**
	 * Test saving an entity, then loading it, then changing some fields and
	 * re-loading. Does <code>load()</code> override these fields?
	 */
	@Test
	public void testMerge()
	{
		Session session = environment.getSession();

		// Create and save a new object
		logger.info("Creating a new object...");
		Bean bean = new Bean("value1", "value2");
		logger.info("bean = " + bean);

		logger.info("Saving bean to database...");
		session.beginTransaction();
		session.save(bean);
		session.getTransaction().commit();
		logger.info("bean = " + bean);

		// Load object
		Bean bean2 = (Bean) session.load(Bean.class, bean.getId());
		bean2.setField1("New value 1");
		logger.info("bean2 = " + bean2);

		// Merge transient object into persistent entity and save to the
		// database
		logger.info("Reloading...");
		session.beginTransaction();
		session.merge(bean2);
		session.getTransaction().commit();
		logger.info("bean2 = " + bean2);

		// Re-load object
		Bean bean3 = (Bean) session.load(Bean.class, bean.getId());
		logger.info("bean3 = " + bean3);
		Assert.assertEquals("New value 1", bean3.getField1());
	}
}
