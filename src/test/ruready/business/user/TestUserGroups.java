package test.ruready.business.user;

import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1_T2_ST1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2_T1_ST1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2_T1_ST2;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2_T2_ST1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2_T2_ST2;
import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.demo.manager.DemoWorldCreator;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.user.entity.UserGroup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import test.ruready.business.ContentTestFixture;
import test.ruready.business.UserUtil;
import test.ruready.rl.TestEnvTestBase;

public class TestUserGroups extends TestEnvTestBase
{
	protected final Log logger = LogFactory.getLog(getClass());

	static UserUtil util;
	static ContentTestFixture content;

	private UserGroup group1;
	private UserGroup group2;
	private UserGroup group3;

	private Course course1;
	private Course course2;

	private School school1;
	private School school2;

	private SubTopic subtopic1_1;
	private SubTopic subtopic2_1;
	private SubTopic subtopic2_2;
	private SubTopic subtopic2_3;
	private SubTopic subtopic2_4;


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
		environment.getSession().flush();

		course1 = content.getCourse(COURSE_1);
		course2 = content.getCourse(COURSE_2);

		school1 = content.getSchool(DemoWorldCreator.SCHOOL_UTAH);
		school2 = content.getSchool(DemoWorldCreator.SCHOOL_UTAHSTATE);

		subtopic1_1 = content.getSubTopic(COURSE_1_T2_ST1);
		subtopic2_1 = content.getSubTopic(COURSE_2_T1_ST1);
		subtopic2_2 = content.getSubTopic(COURSE_2_T1_ST2);
		subtopic2_3 = content.getSubTopic(COURSE_2_T2_ST1);
		subtopic2_4 = content.getSubTopic(COURSE_2_T2_ST2);

		group1 = new UserGroup(course1, school1);
		group2 = new UserGroup(course2, school1);
		group3 = new UserGroup(course2, school2);
	}

	@Override
	protected void cleanUp()
	{
		// delete groups
		util.deleteGroup(group1);
		util.deleteGroup(group2);
		util.deleteGroup(group3);
	}

	@Test
	public void testDefaultGroupName() throws Exception
	{
		Assert.assertEquals(course1.getName(), group1.getName());
	}

	@Test
	public void testCreateGroupOneSubTopic() throws Exception
	{
		group1.addSubTopic(subtopic1_1);
		Assert.assertEquals(1, group1.getSubTopics().size());
	}

	@Test
	public void testActiveAtCreation() throws Exception
	{
		Assert.assertFalse(group1.isDeleted());
	}

	@Test
	public void testDeleteGroup() throws Exception
	{
		Assert.assertFalse(group1.isDeleted());
		group1.delete();
		Assert.assertTrue(group1.isDeleted());
	}

	@Test
	public void testCreateGroupMultipleSubTopics() throws Exception
	{
		group2.addSubTopic(subtopic2_1);
		group2.addSubTopic(subtopic2_2);
		Assert.assertEquals(2, group2.getSubTopics().size());
	}

	@Test
	public void testCreateGroupDuplicateSubTopics() throws Exception
	{
		group2.addSubTopic(subtopic2_1);
		group2.addSubTopic(subtopic2_1);
		Assert.assertEquals(1, group2.getSubTopics().size());
	}

	@Test(expected = BusinessRuleException.class)
	public void testCreateGroupInvalidSubTopic() throws Exception
	{
		group1.addSubTopic(subtopic2_1);
	}

	@Test
	public void testCreateGroupUpdateSubTopic() throws Exception
	{
		group2.addSubTopic(subtopic2_1);
		group2.addSubTopic(subtopic2_2);
		util.saveGroup(group2);

		final UserGroup loadedGroup2 = util.loadGroup(group2);
		assertGroupEquality(group2, loadedGroup2);

		// update the group by removing a subtopic and adding two more
		loadedGroup2.removeSubTopic(subtopic2_1);
		loadedGroup2.addSubTopic(subtopic2_3);
		loadedGroup2.addSubTopic(subtopic2_4);
		util.updateGroup(loadedGroup2);

		final UserGroup reloadedGroup2 = util.loadGroup(loadedGroup2);
		assertGroupEquality(loadedGroup2, reloadedGroup2);
	}

	@Test(expected = AssertionError.class)
	public void testCreateGroupSameCourseDifferentSchools() throws Exception
	{
		group2.addSubTopic(subtopic2_1);
		group3.addSubTopic(subtopic2_3);
		group3.addSubTopic(subtopic2_4);
		util.saveGroup(group2);
		util.saveGroup(group3);

		final UserGroup loadedGroup2 = util.loadGroup(group2);
		final UserGroup loadedGroup3 = util.loadGroup(group3);
		assertGroupEquality(group2, loadedGroup2);
		assertGroupEquality(group3, loadedGroup3);
		// verify that the two different groups are not equal
		assertGroupEquality(group2, loadedGroup3);
	}

	@Test
	public void testCreateGroupDuplicateGroup1() throws Exception
	{

	}

	@Test
	public void testCreateGroupDuplicateGroup2() throws Exception
	{

	}

	@Test
	public void testDeleteExistingGroup() throws Exception
	{
		util.saveGroup(group1);
		Assert.assertFalse(group1.isDeleted());
		Assert.assertEquals(1, util.getUserGroupBD().findAllUserGroups().size());
		Assert.assertEquals(1, util.getUserGroupBD().findActiveUserGroups().size());
		util.getUserGroupBD().deleteUserGroup(group1);
		Assert.assertTrue(group1.isDeleted());
		Assert.assertEquals(1, util.getUserGroupBD().findAllUserGroups().size());
		Assert.assertEquals(0, util.getUserGroupBD().findActiveUserGroups().size());
	}

	private void assertGroupEquality(final UserGroup userGroup,
			final UserGroup loadedUserGroup)
	{
		Assert.assertNotNull(userGroup);
		Assert.assertNotNull(loadedUserGroup);
		Assert.assertEquals(userGroup.getCourse(), loadedUserGroup.getCourse());
		Assert.assertEquals(userGroup.getSchool(), loadedUserGroup.getSchool());
		Assert.assertEquals(userGroup.getSubTopics().size(), loadedUserGroup
				.getSubTopics().size());
		for (SubTopic subTopic : userGroup.getSubTopics())
		{
			Assert.assertTrue(loadedUserGroup.getSubTopics().contains(subTopic));
		}
	}
}
