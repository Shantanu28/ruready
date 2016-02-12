package test.ruready.business.user;

import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2;

import java.util.Date;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.demo.manager.DemoWorldCreator;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserGroupMembership;
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

public class TestUserGroupMemberships extends TestEnvTestBase
{
	protected final Log logger = LogFactory.getLog(getClass());

	// Test Fixtures
	static ContentTestFixture content;
	static UserUtil util;

	private UserGroup group1;
	private UserGroup group2;
	private UserGroup group3;

	private User student;
	private User teacher;

	private UserGroupMembership membership1;
	private UserGroupMembership membership2;

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
		// initialize test fixtures
		student = util.getUser("student");
		student.addRole(RoleType.STUDENT.newInstance());
		util.saveUser(student);

		teacher = util.getUser("teacher");
		teacher.addRole(RoleType.TEACHER.newInstance());
		util.saveUser(teacher);

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
		// delete memberships
		util.deleteMembership(membership1);
		util.deleteMembership(membership2);

		// delete groups
		util.deleteGroup(group1);
		util.deleteGroup(group2);
		util.deleteGroup(group3);

		// delete users
		util.deleteUser(student);
		util.deleteUser(teacher);
	}

	/**
	 * Tests adding a <code>StudentGroupMembership</code> using
	 * <code>manager.addMembership(UserGroup,StudentRole)</code>
	 * 
	 * @see AbstractUserGroupManager#addMembership(UserGroup, StudentRole)
	 * @throws Exception
	 */
	@Test
	public void testAddMembership1()
	{
		membership1 = util.getUserGroupBD().addMembership(group1,
				(StudentRole) student.getRole(RoleType.STUDENT));
		Assert.assertEquals(ActiveStatus.PENDING, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getGroup().getMembership().size());
		Assert.assertEquals(1, membership1.getMember().getGroups().size());
	}

	/**
	 * Tests adding a <code>StudentGroupMembership</code> using
	 * <code>manager.addMembership(StudentGroupMembership)</code>
	 * 
	 * @see AbstractUserGroupManager#addMembership(UserGroupMembership)
	 * @throws Exception
	 */
	@Test
	public void testAddMembership2()
	{
		membership1 = new UserGroupMembership((StudentRole) student
				.getRole(RoleType.STUDENT), group1, ActiveStatus.ACTIVE);
		util.getUserGroupBD().addMembership(membership1);
		Assert.assertEquals(ActiveStatus.ACTIVE, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getGroup().getMembership().size());
		Assert.assertEquals(1, membership1.getMember().getGroups().size());
	}

	/**
	 * Tests creating a <code>UserGroupMembership</code> using a
	 * <code>User</code> object in the constructor instead of a
	 * <code>StudentRole</code>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddMembership3()
	{
		membership1 = new UserGroupMembership(student, group1);
		Assert.assertEquals(ActiveStatus.PENDING, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getGroup().getMembership().size());
		Assert.assertEquals(1, membership1.getMember().getGroups().size());
	}

	/**
	 * Tests adding the same user to two different groups
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddMembershipTwoGroups()
	{
		membership1 = new UserGroupMembership(student, group1);
		membership2 = new UserGroupMembership(student, group2, ActiveStatus.ACTIVE);

		Assert.assertEquals(ActiveStatus.PENDING, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getGroup().getMembership().size());

		Assert.assertEquals(ActiveStatus.ACTIVE, membership2.getMemberStatus());
		Assert.assertEquals(1, membership2.getGroup().getMembership().size());

		Assert.assertEquals(2, membership1.getMember().getGroups().size());
	}

	/**
	 * Tests adding the same user to the same group twice
	 * 
	 * @throws Exception
	 */
	@Test(expected = BusinessRuleException.class)
	public void testAddMembershipTwoGroupsSameGroup()
	{
		membership1 = new UserGroupMembership(student, group1);
		membership2 = new UserGroupMembership(student, group1, ActiveStatus.ACTIVE);
	}

	/**
	 * Tests trying to add the user to two different groups that share the same
	 * course
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddMembershipTwoGroupsSameCourse()
	{
		membership1 = new UserGroupMembership(student, group2);
		membership2 = new UserGroupMembership(student, group3);
		Assert.assertEquals(2, ((StudentRole) student.getRole(RoleType.STUDENT))
				.getGroups().size());
	}

	/**
	 * Tests adding a teacher as a member of the group. Since teachers are also
	 * students, this should work.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddTeacher()
	{
		membership1 = new UserGroupMembership(teacher, group1);
		util.getUserGroupBD().addMembership(membership1);
		Assert.assertEquals(ActiveStatus.PENDING, membership1.getMemberStatus());
		Assert.assertEquals(1, membership1.getGroup().getMembership().size());
		Assert.assertEquals(1, membership1.getMember().getGroups().size());
	}

	@Test
	public void testAddTwoMembers()
	{
		membership1 = new UserGroupMembership(student, group1);
		membership2 = new UserGroupMembership(teacher, group1);
		util.getUserGroupBD().addMembership(membership1);
		util.getUserGroupBD().addMembership(membership2);

		Assert.assertEquals(2, membership2.getGroup().getMembership().size());
		Assert.assertEquals(1, membership1.getMember().getGroups().size());
		Assert.assertEquals(1, membership2.getMember().getGroups().size());
	}

	@Test
	public void testUpdateMembershipStatus() throws InterruptedException
	{
		membership1 = new UserGroupMembership(student, group1);

		final Date pendingDate = membership1.getStatusDate();
		// induce a small delay to ensure that we can detect the status date
		// change if there is any.
		Thread.sleep(50);

		membership1.updateMemberStatus(ActiveStatus.ACTIVE);
		final Date activeDate = membership1.getStatusDate();
		Assert.assertEquals(ActiveStatus.ACTIVE, membership1.getMemberStatus());
		Assert.assertTrue(activeDate.getTime() > pendingDate.getTime());
	}

	@Test(expected = BusinessRuleException.class)
	public void testUpdateMembershipStatusSameStatus()
	{
		membership1 = new UserGroupMembership(student, group1);
		membership1.updateMemberStatus(ActiveStatus.PENDING);
	}

	@Test
	public void testRemoveMembership1()
	{
		membership1 = new UserGroupMembership(student, group1);
		util.getUserGroupBD().addMembership(membership1);

		final StudentRole studentRole = (StudentRole) student.getRole(RoleType.STUDENT);

		Assert.assertEquals(1, studentRole.getGroups().size());
		Assert.assertEquals(1, group1.getMembership().size());

		util.getUserGroupBD().removeMembership(membership1);

		Assert.assertEquals(0, studentRole.getGroups().size());
		Assert.assertEquals(0, group1.getMembership().size());
	}

	@Test
	public void testRemoveMembership2()
	{
		membership1 = new UserGroupMembership(student, group1);
		membership2 = new UserGroupMembership(student, group2);

		util.getUserGroupBD().addMembership(membership1);
		util.getUserGroupBD().addMembership(membership2);

		final StudentRole studentRole = (StudentRole) student.getRole(RoleType.STUDENT);

		Assert.assertEquals(2, studentRole.getGroups().size());
		Assert.assertEquals(1, group1.getMembership().size());
		Assert.assertEquals(1, group2.getMembership().size());

		util.getUserGroupBD().removeMembership(membership1);

		Assert.assertEquals(1, studentRole.getGroups().size());
		Assert.assertEquals(0, group1.getMembership().size());
		Assert.assertEquals(1, group2.getMembership().size());
	}

	@Test
	public void testRemoveGroupUpdatedMembership()
	{
		membership1 = new UserGroupMembership(student, group1);
		membership2 = new UserGroupMembership(teacher, group1);
		util.getUserGroupBD().addMembership(membership1);
		util.getUserGroupBD().addMembership(membership2);

		Assert.assertFalse(group1.isDeleted());
		Assert.assertEquals(2, group1.getMembership().size());
		util.getUserGroupBD().deleteUserGroup(group1);
		Assert.assertTrue(group1.isDeleted());
		Assert.assertEquals(0, group1.getMembership().size());
	}
}
