/*****************************************************************************************
 * Source File: TestManyToMany.java
 ****************************************************************************************/
package test.ruready.eis;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Test a many-to-many relationship (Person-Forum). What happens when a
 * moderator is removed and its forum(s) are not? Does Hibernate allow that?
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
public class TestManyToMany extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestManyToMany.class);

	// ========================= FIELDS ====================================

	/**
	 * A convenient local variable for a mock moderator object.
	 */
	private Person teacher1;

	/**
	 * A convenient local variable for a mock student object.
	 */
	private Person student5;

	/**
	 * A convenient local variable for a mock forum object.
	 */
	private Forum forum1;

	/**
	 * A convenient local variable for a mock forum object.
	 */
	private Forum forum2;

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		// Create mock objects
		logger.info("Creating objects...");
		teacher1 = new Person("Student 1");
		Person teacher2 = new Person("Student 2");
		Person student3 = new Person("Student 3");
		Person student4 = new Person("Student 4");
		student5 = new Person("Student 5");
		forum1 = new Forum(teacher1);
		forum1.setName("Forum 1");
		forum2 = new Forum(teacher2);
		forum2.setName("Forum 2");
		forum1.addPerson(student3);
		forum1.addPerson(student5);
		forum2.addPerson(student4);
		forum2.addPerson(student5);

		// Save all to database
		logger.info("Saving to database...");
		Session session = environment.getSession();
		// session.saveOrUpdate(teacher1);
		// session.saveOrUpdate(teacher2);
		session.saveOrUpdate(forum1); // cascaded to persons
		session.saveOrUpdate(forum2); // cascaded to persons
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void cleanUp()
	{
		// Delete all forums. I wish we could do:
		// session.createQuery("delete Forum forum").executeUpdate();
		// but need to remove forums from associations first.

		Session session = environment.getSession();
		Query query = session.createQuery("from Forum forum");
		List<Forum> resultSet = query.list();
		for (Forum forum : resultSet)
		{
			forum.getPersons().clear();
			forum.setModerator(null);
			session.delete(forum);
		}

		session.getTransaction().commit();

		// Delete all persons
		session = environment.getSession();
		session.beginTransaction();
		session.createQuery("delete Person person").executeUpdate();
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Not yet completely formulated as a test case with assertions, just look
	 * at the output for now.
	 */
	@Test
	public void testManyToMany()
	{
		Session session = environment.getSession();

		// Delete teacher1 from database. It needs to be removed
		// from all associations that cause a foreign key constraint violation.
		logger.info("Deleting teacher1 from database...");
		session = environment.getSession();
		session.beginTransaction();
		session.refresh(teacher1);
		session.refresh(forum1);
		// teacher1 = forum1.getArithmeticModerator();
		forum1.setModerator(null);
		session.saveOrUpdate(forum1);
		session.delete(teacher1);
		session.getTransaction().commit();
	}

	/**
	 * Test the HQL table join feature to find all forums that a person belongs
	 * to.
	 */
	@Test
	public void testHQLJoin()
	{
		Session session = environment.getSession();

		// Find all forums that student5 belongs to; should be forum1 and forum2
		session.beginTransaction();
		Query query = session
				.createQuery("select f from Forum f join f.persons p where p.id = ?");
		query.setParameter(0, student5.getId());
		List<?> resultSet = query.list();
		Iterator<?> resultSetIterator = resultSet.iterator();
		while (resultSetIterator.hasNext())
		{
			// Object[] row = (Object[]) resultSetIterator.next();
			// logger.debug("Forum " + row[0] + " Person " + row[1]);
			logger.debug("Forum " + resultSetIterator.next());
		}

		Assert.assertEquals(2, resultSet.size());
	}
}
