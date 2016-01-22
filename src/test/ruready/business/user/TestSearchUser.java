/*****************************************************************************************
 * Source File: TestSearchUser.java
 ****************************************************************************************/
package test.ruready.business.user;

import java.util.List;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.UserField;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.common.discrete.Gender;
import net.ruready.common.search.DefaultSearchCriteria;
import net.ruready.common.search.Logic;
import net.ruready.common.search.MatchType;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.imports.StandAloneUserBD;
import test.ruready.rl.TestEnvTestBase;

/**
 * A test of the generic search framework (search criteria by an entity's
 * fields). Applied to searching users.
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
public class TestSearchUser extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestSearchUser.class);

	// Fictitious email domain name for mock users
	private static final String TEST_EMAIL_DOMAIN = "test.nomail.com";

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		// -----------------------------------------------------------------------
		// Delete mock users from database before attempting to create them
		// Note: doesn't seem to find the users. Hmm. Using our search framework
		// instead.
		// -----------------------------------------------------------------------
		// User userExample =
		// new User("%" + "@" + TEST_EMAIL_DOMAIN, null, null, null, null);
		// Example criterion = Example.create(userExample);
		// criterion.enableLike();
		// List<User> mockUsers = bdUser.findByExampleObject(criterion);
		SearchCriteria searchCriteria = new DefaultSearchCriteria(Logic.AND
				.createCriterion());

		searchCriteria.add(SearchCriterionFactory.createStringExpression(SearchType.LIKE,
				UserField.EMAIL.getName(), "@" + TEST_EMAIL_DOMAIN, MatchType.ENDS_WITH));

		AbstractUserBD bdUser = new StandAloneUserBD(environment.getContext());
		logger.debug("Search Criteria = " + searchCriteria);

		// Carry out the user search
		List<User> mockUsers = bdUser.findByCriteria(searchCriteria);

		logger.debug("Deleting mockUsers " + mockUsers);
		for (User user : mockUsers)
		{
			bdUser.deleteUser(user);
		}

		// Make sure changes are persistent before attempting to create the
		// users
		// we've just deleted.
		Session session = environment.getSession();
		session.flush();

		// Create fresh mock users

		User user = new User("user1@" + TEST_EMAIL_DOMAIN, Gender.MALE,
				AgeGroup.BETWEEN_16_19, Ethnicity.ASIAN, Language.ARABIC);
		bdUser.createUser(user);

		user = new User("user2@" + TEST_EMAIL_DOMAIN, Gender.FEMALE,
				AgeGroup.BETWEEN_16_19, Ethnicity.HISPANIC, Language.CHINESE);
		bdUser.createUser(user);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		Session session = environment.getSession();
		session.beginTransaction();

		session.createQuery("delete Product p").executeUpdate();
		session.createQuery("delete Supplier s").executeUpdate();

		// Delete mock users from database
		User userExample = new User("%" + "@" + TEST_EMAIL_DOMAIN, null, null, null, null);
		Example criterion = Example.create(userExample);
		criterion.enableLike();

		AbstractUserBD bdUser = new StandAloneUserBD(environment.getContext());
		List<User> mockUsers = bdUser.findByExampleObject(criterion);
		logger.debug("Deleting mockUsers " + mockUsers);
		for (User user : mockUsers)
		{
			bdUser.deleteUser(user);
		}
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test user search by one String field (email).
	 */
	@Test
	public void testSearchByEmail()
	{
		// Create search criteria. In this example, a single search criterion
		// is used to find all users whose emails end with the test domain
		// string.
		SearchCriteria searchCriteria = new DefaultSearchCriteria(Logic.AND
				.createCriterion());

		searchCriteria.add(SearchCriterionFactory.createStringExpression(SearchType.LIKE,
				UserField.EMAIL.getName(), "@" + TEST_EMAIL_DOMAIN, MatchType.ENDS_WITH));

		AbstractUserBD bdUser = new StandAloneUserBD(environment.getContext());
		logger.debug("Search Criteria = " + searchCriteria);

		// Carry out the user search
		List<User> resultSet = bdUser.findByCriteria(searchCriteria);

		// Display and check the result set
		logger.debug("result set = " + resultSet);
		// There should be two users in the result set: user1 and user2 created
		// in setUp().
		Assert.assertEquals(2, resultSet.size());
	}

	/**
	 * Test user search by gender and email.
	 */
	@Test
	public void testSearchByEmailAndGender()
	{
		// Create search criteria. In this example, a two search criteria
		// is used to find all users whose emails end with the test domain
		// string and that have a specified gender.
		SearchCriteria searchCriteria = new DefaultSearchCriteria(Logic.AND
				.createCriterion());

		searchCriteria.add(SearchCriterionFactory.createStringExpression(SearchType.LIKE,
				UserField.EMAIL.getName(), "@" + TEST_EMAIL_DOMAIN, MatchType.ENDS_WITH));

		searchCriteria.add(SearchCriterionFactory.<Gender> createSimpleExpression(
				SearchType.EQ, Gender.class, UserField.GENDER.getName(), Gender.MALE));

		AbstractUserBD bdUser = new StandAloneUserBD(environment.getContext());
		logger.debug("Search Criteria = " + searchCriteria);

		// Carry out the user search
		List<User> resultSet = bdUser.findByCriteria(searchCriteria);

		// Display and check the result set
		logger.debug("result set = " + resultSet);
		// There should be one users in the result set: user1 created
		// in setUp().
		Assert.assertEquals(1, resultSet.size());
	}

	/**
	 * Test directly Hibernate search criterion that involves an enum field of
	 * an entity. Disabled for now because we might find users from other tests
	 * or system users as well. Use {@link #testSearchByEmailAndGender()}
	 * instead.
	 */
	public void xxxTestHibernateSearchCriterionEnum()
	{
		Session session = environment.getSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(User.class);
		// crit.add(Restrictions.eq("gender", "MALE")); // BAD
		// crit.add(Restrictions.eq("gender", Gender.MALE)); // OK
		// crit.add(Restrictions.like("gender", "MALE")); // BAD
		crit.add(Restrictions.like("gender", Gender.MALE)); // OK
		List<?> resultSet = crit.list();

		// Display and check the result set
		logger.debug("result set = " + resultSet);
		// There should be one users in the result set: user1 reated
		// in setUp().
		Assert.assertEquals(1, resultSet.size());
	}

}
