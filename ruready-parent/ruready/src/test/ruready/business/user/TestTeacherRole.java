package test.ruready.business.user;

import java.util.Date;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.demo.manager.DemoWorldCreator;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.TeacherSchoolMembership;
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

public class TestTeacherRole extends TestEnvTestBase
{

	@SuppressWarnings("unused")
	protected Log logger = LogFactory.getLog(getClass());

	// Test Fixtures
	static ContentTestFixture content;
	static UserUtil util;

	private User student;
	private User teacher;
	private User editor;

	private School school1;
	private School school2;

	private TeacherSchoolMembership membership1;
	private TeacherSchoolMembership membership2;

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

		teacher = util.getUser("teacher");
		teacher.addRole(RoleType.TEACHER.newInstance());
		util.saveUser(teacher);

		editor = util.getUser("editor");
		editor.addRole(RoleType.EDITOR.newInstance());
		util.saveUser(editor);

		school1 = content.getSchool(DemoWorldCreator.SCHOOL_UTAH);
		school2 = content.getSchool(DemoWorldCreator.SCHOOL_UTAHSTATE);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		util.deleteMembership(membership1);
		util.deleteMembership(membership2);

		// delete users
		util.deleteUser(student);
		util.deleteUser(teacher);
		util.deleteUser(editor);
	}

	// test add school
	// test add two schools
	// test add duplicate school
	// test add same school to two teachers
	// test remove school
	// test remove school not enrolled at

	@Test
	public void testAddSchool()
	{
		membership1 = util.getUserBD().addSchoolMembership(school1,
				(TeacherRole) teacher.getRole(RoleType.TEACHER));

		Assert.assertEquals(ActiveStatus.PENDING, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getSchool().getTeachers().size());
		Assert.assertEquals(1, membership1.getMember().getSchools().size());
	}

	@Test
	public void testAddSchool2()
	{
		membership1 = new TeacherSchoolMembership((TeacherRole) teacher
				.getRole(RoleType.TEACHER), school1, ActiveStatus.ACTIVE);
		util.getUserBD().addSchoolMembership(membership1);
		Assert.assertEquals(ActiveStatus.ACTIVE, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getSchool().getTeachers().size());
		Assert.assertEquals(1, membership1.getMember().getSchools().size());
	}

	@Test
	public void testAddSchool3()
	{
		membership1 = new TeacherSchoolMembership(teacher, school1);
		Assert.assertEquals(ActiveStatus.PENDING, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getSchool().getTeachers().size());
		Assert.assertEquals(1, membership1.getMember().getSchools().size());
	}

	@Test
	public void testAddTwoSchools()
	{
		membership1 = new TeacherSchoolMembership(teacher, school1);
		membership2 = new TeacherSchoolMembership(teacher, school2, ActiveStatus.ACTIVE);

		Assert.assertEquals(ActiveStatus.PENDING, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getSchool().getTeachers().size());

		Assert.assertEquals(ActiveStatus.ACTIVE, membership2.getMemberStatus());
		Assert.assertEquals(1, membership2.getSchool().getTeachers().size());

		Assert.assertEquals(2, membership1.getMember().getSchools().size());
	}

	@Test(expected = BusinessRuleException.class)
	public void testAddDuplicateSchool()
	{
		membership1 = new TeacherSchoolMembership(teacher, school1);
		membership2 = new TeacherSchoolMembership(teacher, school1, ActiveStatus.ACTIVE);
		logger.debug("Memberships: " + school1.getTeachers().size());
	}

	@Test(expected = BusinessRuleException.class)
	public void testAddStudent()
	{
		membership1 = new TeacherSchoolMembership(student, school1);
	}

	@Test
	public void testAddEditor()
	{
		membership1 = new TeacherSchoolMembership(editor, school1);
		util.getUserBD().addSchoolMembership(membership1);
		Assert.assertEquals(ActiveStatus.PENDING, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getSchool().getTeachers().size());
		Assert.assertEquals(1, membership1.getMember().getSchools().size());
	}

	@Test
	public void testAddTwoMembers()
	{
		membership1 = new TeacherSchoolMembership(teacher, school1);
		membership2 = new TeacherSchoolMembership(editor, school1);
		util.getUserBD().addSchoolMembership(membership1);
		util.getUserBD().addSchoolMembership(membership2);

		Assert.assertEquals(2, membership2.getSchool().getTeachers().size());
		Assert.assertEquals(1, membership1.getMember().getSchools().size());
		Assert.assertEquals(1, membership2.getMember().getSchools().size());
	}

	@Test
	public void testUpdateMembershipStatus() throws Exception
	{
		membership1 = new TeacherSchoolMembership(teacher, school1);

		final Date pendingDate = membership1.getStatusDate();
		// induce a small delay to ensure that we can detect the status date
		// change if there is any.
		Thread.sleep(50);

		membership1.updateMemberStatus(ActiveStatus.ACTIVE,
				"School membership was approved.");
		final Date activeDate = membership1.getStatusDate();
		Assert.assertEquals(ActiveStatus.ACTIVE, membership1.getMemberStatus());
		Assert.assertTrue(activeDate.getTime() > pendingDate.getTime());
	}

	@Test(expected = BusinessRuleException.class)
	public void testUpdateMembershipStatusSameStatus()
	{
		membership1 = new TeacherSchoolMembership(teacher, school1);
		membership1.updateMemberStatus(ActiveStatus.PENDING, "Pending status");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateMembershipStatusNoReason()
	{
		membership1 = new TeacherSchoolMembership(teacher, school1);
		membership1.updateMemberStatus(ActiveStatus.ACTIVE, "");
	}

	@Test
	public void testRemoveMembership1()
	{
		membership1 = new TeacherSchoolMembership(teacher, school1);
		util.getUserBD().addSchoolMembership(membership1);

		final TeacherRole teacherRole = (TeacherRole) teacher.getRole(RoleType.TEACHER);

		Assert.assertEquals(1, teacherRole.getSchools().size());
		Assert.assertEquals(1, school1.getTeachers().size());

		util.getUserBD().removeSchoolMembership(membership1);

		Assert.assertEquals(0, teacherRole.getSchools().size());
		Assert.assertEquals(0, school1.getTeachers().size());
	}

	@Test
	public void testRemoveMembership2()
	{
		membership1 = new TeacherSchoolMembership(teacher, school1);
		membership2 = new TeacherSchoolMembership(teacher, school2);

		util.getUserBD().addSchoolMembership(membership1);
		util.getUserBD().addSchoolMembership(membership2);

		final TeacherRole teacherRole = (TeacherRole) teacher.getRole(RoleType.TEACHER);

		Assert.assertEquals(2, teacherRole.getSchools().size());
		Assert.assertEquals(1, school1.getTeachers().size());
		Assert.assertEquals(1, school2.getTeachers().size());

		util.getUserBD().removeSchoolMembership(membership1);

		Assert.assertEquals(1, teacherRole.getSchools().size());
		Assert.assertEquals(0, school1.getTeachers().size());
		Assert.assertEquals(1, school2.getTeachers().size());
	}
}
