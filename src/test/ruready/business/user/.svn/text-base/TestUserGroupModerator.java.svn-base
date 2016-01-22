package test.ruready.business.user;

import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2;
import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.demo.manager.DemoWorldCreator;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupModerator;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.manager.AbstractUserGroupManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import test.ruready.business.ContentTestFixture;
import test.ruready.business.UserUtil;
import test.ruready.rl.TestEnvTestBase;

public class TestUserGroupModerator extends TestEnvTestBase
{
	protected final Log logger = LogFactory.getLog(getClass());

	static ContentTestFixture content;

	static UserUtil util;

	private UserGroup group1;

	private UserGroup group2;

	private UserGroup group3;

	private User student;

	private User teacher;

	private User administrator;

	private UserGroupModerator moderator1;

	private UserGroupModerator moderator2;

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
				content.buildCatalog();
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
				content.deleteCatalog();
				content.deleteWorld();
			}
		});
	}

	@Override
	protected void initialize()
	{
		student = util.getUser("student");
		student.addRole(RoleType.STUDENT.newInstance());
		util.saveUser(student);

		teacher = util.getUser("teacher");
		teacher.addRole(RoleType.TEACHER.newInstance());
		util.saveUser(teacher);

		administrator = util.getUser("administrator");
		administrator.addRole(RoleType.ADMIN.newInstance());
		util.saveUser(administrator);

		final Course course1 = content.getCourse(COURSE_1);
		final Course course2 = content.getCourse(COURSE_2);

		final School school1 = content.getSchool(DemoWorldCreator.SCHOOL_UTAH);
		final School school2 = content.getSchool(DemoWorldCreator.SCHOOL_UTAHSTATE);

		group1 = new UserGroup(course1, school1);
		util.saveGroup(group1);
		group2 = new UserGroup(course2, school1);
		util.saveGroup(group2);
		group3 = new UserGroup(course2, school2);
		util.saveGroup(group3);
	}

	@Override
	protected void cleanUp()
	{
		// delete moderator links
		util.deleteModerator(moderator1);
		util.deleteModerator(moderator2);

		// delete groups
		util.deleteGroup(group1);
		util.deleteGroup(group2);
		util.deleteGroup(group3);

		// delete users
		util.deleteUser(student);
		util.deleteUser(teacher);
		util.deleteUser(administrator);
	}

	/**
	 * Tests adding a <code>UserGroupModerator</code> using
	 * <code>manager.addModerator(UserGroup,StudentRole)</code>
	 * 
	 * @see AbstractUserGroupManager#addModerator(UserGroup, TeacherRole)
	 * @throws Exception
	 */
	@Test
	public void testAddModerator1() throws Exception
	{
		final TeacherRole teacherRole = (TeacherRole) teacher.getRole(RoleType.TEACHER);
		moderator1 = util.getUserGroupBD().addModerator(group1, teacherRole);

		Assert.assertFalse(moderator1.isOwner());
		Assert.assertEquals(1, moderator1.getGroup().getModerators().size());
		Assert.assertEquals(1, teacherRole.getGroups().size());
	}

	/**
	 * Tests adding a <code>UserGroupModerator</code> using
	 * <code>manager.addModerator(UserGroupModerator)</code>
	 * 
	 * @see AbstractUserGroupManager#addModerator(UserGroupModerator)
	 * @throws Exception
	 */
	@Test
	public void testAddModerator2() throws Exception
	{
		final TeacherRole teacherRole = (TeacherRole) teacher.getRole(RoleType.TEACHER);
		moderator1 = new UserGroupModerator(teacherRole, group1, true);

		util.getUserGroupBD().addModerator(moderator1);
		Assert.assertTrue(moderator1.isOwner());
		Assert.assertEquals(1, moderator1.getGroup().getModerators().size());
		Assert.assertEquals(1, teacherRole.getGroups().size());
	}

	/**
	 * Tests creating a <code>UserGroupModerator</code> using a
	 * <code>User</code> object in the constructor instead of a
	 * <code>TeacherRole</code>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddModerator3() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1);

		Assert.assertFalse(moderator1.isOwner());
		Assert.assertEquals(1, moderator1.getGroup().getModerators().size());
		Assert.assertEquals(1, moderator1.getModerator().getGroups().size());
	}

	/**
	 * Tests adding the same user to two different groups
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddModeratorTwoGroups() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1);
		moderator2 = new UserGroupModerator(teacher, group2, true);

		Assert.assertFalse(moderator1.isOwner());
		Assert.assertEquals(1, moderator1.getGroup().getModerators().size());

		Assert.assertTrue(moderator2.isOwner());
		Assert.assertEquals(1, moderator2.getGroup().getModerators().size());

		Assert.assertEquals(2, moderator1.getModerator().getGroups().size());
	}

	/**
	 * Tests adding the same user to the same group twice
	 * 
	 * @throws Exception
	 */
	@Test(expected = BusinessRuleException.class)
	public void testAddModeratorTwoGroupsSameGroup() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1);
		moderator2 = new UserGroupModerator(teacher, group1, true);
	}

	/**
	 * Tests trying to add the user to two different groups that share the same
	 * course
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddModeratorTwoGroupsSameCourse() throws Exception
	{
		final TeacherRole teacherRole = (TeacherRole) teacher.getRole(RoleType.TEACHER);
		moderator1 = new UserGroupModerator(teacher, group2);
		moderator2 = new UserGroupModerator(teacher, group3);

		Assert.assertEquals(2, teacherRole.getGroups().size());
	}

	/**
	 * Tests adding an administrator as a moderator of the group. Since admins
	 * are also teachers, this should work.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddAdministrator() throws Exception
	{
		moderator1 = new UserGroupModerator(administrator, group1);

		util.getUserGroupBD().addModerator(moderator1);
		Assert.assertFalse(moderator1.isOwner());
		Assert.assertEquals(1, moderator1.getGroup().getModerators().size());
		Assert.assertEquals(1, moderator1.getModerator().getGroups().size());
	}

	/**
	 * Tests adding a student as a moderator of the group. Since students are
	 * not teachers this should not work.
	 * 
	 * @throws Exception
	 */
	@Test(expected = BusinessRuleException.class)
	public void testAddStudent() throws Exception
	{
		moderator1 = new UserGroupModerator(student, group1);
	}

	@Test
	public void testAddTwoModerators() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1);
		moderator2 = new UserGroupModerator(administrator, group1);
		util.getUserGroupBD().addModerator(moderator1);
		util.getUserGroupBD().addModerator(moderator2);

		Assert.assertEquals(2, moderator2.getGroup().getModerators().size());
		Assert.assertEquals(1, moderator1.getModerator().getGroups().size());
		Assert.assertEquals(1, moderator2.getModerator().getGroups().size());
	}

	@Test
	public void testRemoveModerator1() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1);
		util.getUserGroupBD().addModerator(moderator1);

		final TeacherRole teacherRole = (TeacherRole) teacher.getRole(RoleType.TEACHER);

		Assert.assertEquals(1, teacherRole.getGroups().size());
		Assert.assertEquals(1, group1.getModerators().size());

		util.getUserGroupBD().removeModerator(moderator1);

		Assert.assertEquals(0, teacherRole.getGroups().size());
		Assert.assertEquals(0, group1.getModerators().size());
	}

	@Test
	public void testRemoveModerator2() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1);
		moderator2 = new UserGroupModerator(teacher, group2);

		util.getUserGroupBD().addModerator(moderator1);
		util.getUserGroupBD().addModerator(moderator2);

		final TeacherRole teacherRole = (TeacherRole) teacher.getRole(RoleType.TEACHER);

		Assert.assertEquals(2, teacherRole.getGroups().size());
		Assert.assertEquals(1, group1.getModerators().size());
		Assert.assertEquals(1, group2.getModerators().size());

		util.getUserGroupBD().removeModerator(moderator1);

		Assert.assertEquals(1, teacherRole.getGroups().size());
		Assert.assertEquals(0, group1.getModerators().size());
		Assert.assertEquals(1, group2.getModerators().size());
	}

	@Test
	public void testRemoveGroupUpdatedModerators() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1);
		moderator2 = new UserGroupModerator(administrator, group1);
		util.getUserGroupBD().addModerator(moderator1);
		util.getUserGroupBD().addModerator(moderator2);

		Assert.assertFalse(group1.isDeleted());
		Assert.assertEquals(2, group1.getModerators().size());
		util.getUserGroupBD().deleteUserGroup(group1);
		Assert.assertTrue(group1.isDeleted());
		Assert.assertEquals(0, group1.getModerators().size());
	}

	@Test
	public void testPrimaryModerator1() throws Exception
	{
		final TeacherRole teacherRole = (TeacherRole) teacher.getRole(RoleType.TEACHER);
		moderator1 = util.getUserGroupBD().addModerator(group1, teacherRole);

		Assert.assertFalse(group1.hasPrimaryModerator());
	}

	@Test
	public void testPrimaryModerator2() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1, true);

		Assert.assertTrue(group1.hasPrimaryModerator());
		Assert.assertEquals(moderator1, group1.getPrimaryModerator());
	}

	@Test
	public void testPrimaryModerator3() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1, true);
		moderator2 = new UserGroupModerator(administrator, group1);

		Assert.assertTrue(group1.hasPrimaryModerator());
		Assert.assertEquals(moderator1, group1.getPrimaryModerator());
	}

	@Test(expected = BusinessRuleException.class)
	public void testPrimaryModeratorTwoPrimaryModerators() throws Exception
	{
		moderator1 = new UserGroupModerator(teacher, group1, true);
		moderator2 = new UserGroupModerator(administrator, group1, true);
	}
}
