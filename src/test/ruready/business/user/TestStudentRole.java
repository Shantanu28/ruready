package test.ruready.business.user;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.demo.manager.DemoWorldCreator;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import test.ruready.business.ContentTestFixture;
import test.ruready.business.UserUtil;
import test.ruready.rl.TestEnvTestBase;

/**
 * Unit test the management of student roles
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
 * @author Jeremy Lund
 * @version Sep 24, 2007
 */
public class TestStudentRole extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	protected Log logger = LogFactory.getLog(getClass());

	// ========================= FIELDS ====================================

	// Test Fixtures
	static ContentTestFixture content;
	static UserUtil util;

	private User student;

	private School school1;
	private School school2;

	// ========================= SETUP METHODS =============================

	/**
	 * Set up operations that are done once per test class.
	 */
	@BeforeClass
	public static void initializeSuite()
	{
		environment.initialize(new StandAloneEnvironment.CallBack()
		{
			public void run(@SuppressWarnings("unused")
			StandAloneEnvironment env)
			{
				// initialize test fixtures
				util = new UserUtil(env);

				content = new ContentTestFixture(env);
				content.buildWorld();
			}
		});
	}

	/**
	 * Clean-up operations that are done once per test class.
	 */
	@AfterClass
	public static void cleanUpSuite()
	{
		environment.cleanUp(new StandAloneEnvironment.CallBack()
		{
			public void run(@SuppressWarnings("unused")
			StandAloneEnvironment env)
			{
				content.deleteWorld();
			}
		});
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{

		// initialize test fixtures

		student = util.getUser("student");
		student.addRole(RoleType.STUDENT.newInstance());
		util.saveUser(student);

		school1 = content.getSchool(DemoWorldCreator.SCHOOL_UTAH);
		school2 = content.getSchool(DemoWorldCreator.SCHOOL_UTAHSTATE);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// delete users
		util.deleteUser(student);
	}

	// ========================= PUBLIC TESTING METHODS ====================

	@Test
	public void testAddSchool() throws Exception
	{
		final StudentRole studentRole = (StudentRole) student.getRole(RoleType.STUDENT);
		studentRole.addSchool(school1);
		Assert.assertEquals(1, studentRole.getSchools().size());
	}

	@Test
	public void testAddTwoSchools() throws Exception
	{
		final StudentRole studentRole = (StudentRole) student.getRole(RoleType.STUDENT);
		studentRole.addSchool(school1);
		studentRole.addSchool(school2);
		Assert.assertEquals(2, studentRole.getSchools().size());
	}

	@Test(expected = BusinessRuleException.class)
	public void testAddDuplicateSchool() throws Exception
	{
		final StudentRole studentRole = (StudentRole) student.getRole(RoleType.STUDENT);
		studentRole.addSchool(school1);
		studentRole.addSchool(school1);
	}

	@Test
	public void testRemoveSchool() throws Exception
	{
		final StudentRole studentRole = (StudentRole) student.getRole(RoleType.STUDENT);
		studentRole.addSchool(school1);
		util.updateUser(student);

		final User loadedStudent = util.getUserFromDB(student);
		Assert.assertTrue(loadedStudent.hasRole(RoleType.STUDENT));
		final StudentRole loadedStudentRole = (StudentRole) loadedStudent
				.getRole(RoleType.STUDENT);
		Assert.assertEquals(1, loadedStudentRole.getSchools().size());

		loadedStudentRole.removeSchool(school1);
		Assert.assertEquals(0, loadedStudentRole.getSchools().size());
	}

	@Test(expected = BusinessRuleException.class)
	public void testRemoveSchoolNotEnrolled() throws Exception
	{
		final StudentRole studentRole = (StudentRole) student.getRole(RoleType.STUDENT);
		studentRole.removeSchool(school1);
	}
}
