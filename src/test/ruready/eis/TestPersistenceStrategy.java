/*****************************************************************************************
 * Source File: TestPersistenceStrategy.java
 ****************************************************************************************/
package test.ruready.eis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Testing strategy pattern feasibility in Hibernate mappings.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-D<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 23, 2007
 */
public class TestPersistenceStrategy extends StandAloneEnvTestBase
{

	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestPersistenceStrategy.class);

	// ========================= FIELDS ====================================

	// ========================= PUBLIC TESTING METHODS ====================

	// Not completely formulated as a test case yet, just look at output for now
	@Test
	public void testPersistenceStrategy()
	{
		Session session = environment.getSession();

		// Create a user and a dependent phone object
		logger.info("Creating a new user object...");
		AbstractUser user = new SimpleUser("Oren Livne");
		user.setPhone(new LocalPhone(1112222, "SLC"));
		// Print the user
		logger.info(user);
		logger.info(user.getPhone());

		// Save user [+ phone, using cascading] to database
		logger.info("Saving user to database...");
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();

		// Find & print all users & phones
		listUsers();
		listPhones();

		// Delete user [+ phone, using cascading] from database
		logger.info("Deleting user from database...");
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();

		// Find & print all users & phones
		listUsers();
		listPhones();
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Find & print all users.
	 * 
	 * @return the list of all users in the database.
	 */
	@SuppressWarnings("unchecked")
	private List<SimpleUser> listUsers()
	{
		Session session = environment.getSession();
		logger.info("All users:");
		session.beginTransaction();
		List<SimpleUser> users = session.createQuery("from SimpleUser").list();
		for (SimpleUser user : users)
		{
			logger.info(user);
		}
		session.getTransaction().commit();
		return users;
	}

	/**
	 * Find & print all phones.
	 * 
	 * @return the list of all phones in the database.
	 */
	@SuppressWarnings("unchecked")
	private List<AbstractPhone> listPhones()
	{
		Session session = environment.getSession();
		logger.info("All phones:");
		session.beginTransaction();
		List<AbstractPhone> phones = session.createQuery("from AbstractPhone").list();
		for (AbstractPhone phone : phones)
		{
			logger.info(phone);
		}
		session.getTransaction().commit();
		return phones;
	}
}
