/*****************************************************************************************
 * Source File: TestPartialPopulation.java
 ****************************************************************************************/
package test.ruready.eis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Testing saving a transient entity to the database; after that, examining
 * whether the object shows the new ID.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 30, 2007
 */
public class TestEntitySave extends StandAloneEnvTestBase
{

	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestEntitySave.class);

	// ========================= FIELDS ====================================

	// ========================= SETUP METHODS =============================

	// ========================= PUBLIC TESTING METHODS ====================

	// Not completely formulated as a test case yet, just look at output for now
	@Test
	public void testPartialPopulation()
	{
		Session session = environment.getSession();

		// Create a user and a dependent phone object
		logger.info("Creating a new transient bean object...");
		Bean bean = new Bean("value1", "value2");
		logger.info("bean before saving to database = " + bean);

		// Save bean to database
		logger.info("Saving bean to database...");
		session.beginTransaction();
		session.saveOrUpdate(bean);
		session.getTransaction().commit();
		logger.info("bean after saving to database = " + bean);

		// Delete bean from database
		logger.info("Deleting bean from database...");
		session = environment.getSession();
		session.beginTransaction();
		session.delete(bean);
		session.getTransaction().commit();
	}

	// ========================= PRIVATE METHODS ===========================
}
