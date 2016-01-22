package test.ruready.business.ta;

import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2;
import static net.ruready.business.content.demo.manager.DemoWorldCreator.SCHOOL_UTAH;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.property.RoleType;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import test.ruready.business.ContentTestFixture;
import test.ruready.business.UserUtil;
import test.ruready.rl.TestEnvTestBase;

public class TestIntegrationStudentTranscript extends TestEnvTestBase
{

	// Test Fixtures
	static ContentTestFixture content;
	static TAUtil taUtil;
	static UserUtil userUtil;

	private AbstractStudentTranscriptBD transcriptBD;

	private Course course1;
	private Course course2;
	private UserGroup group1;
	private UserGroup group2;

	private User student1;
	private User student2;
	
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
				taUtil = new TAUtil(env);

				userUtil = new UserUtil(env);
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
		transcriptBD = taUtil.getTranscriptBD();

		environment.getSession().flush();

		course1 = content.getCourse(COURSE_1);
		course2 = content.getCourse(COURSE_2);

		group1 = new UserGroup(course1, content.getSchool(SCHOOL_UTAH));
		userUtil.saveGroup(group1);
		group2 = new UserGroup(course2, content.getSchool(SCHOOL_UTAH));
		userUtil.saveGroup(group2);

		student1 = userUtil.getUser("student1");
		student1.addRole(RoleType.STUDENT.newInstance());
		userUtil.saveUser(student1);

		student2 = userUtil.getUser("student2");
		student2.addRole(RoleType.STUDENT.newInstance());
		userUtil.saveUser(student2);
	}

	@Override
	protected void cleanUp()
	{
		taUtil.deleteAllTranscripts();

		userUtil.deleteAllGroups();

		userUtil.deleteUser(student1);
		userUtil.deleteUser(student2);
	}

	@Test
	public void testFindAllCourseTranscripts() throws Exception
	{
		transcriptBD.createTranscript(new StudentCourseTranscript(student1, course1));
		transcriptBD.createTranscript(new StudentCourseTranscript(student1, course2));
		transcriptBD.createTranscript(new StudentGroupTranscript(student1, group1));

		Assert.assertEquals(2, transcriptBD.findAllCourseTranscripts().size());
	}

	@Test
	public void testFindAllCourseTranscriptsEmpty() throws Exception
	{
		Assert.assertEquals(0, transcriptBD.findAllCourseTranscripts().size());
	}

	@Test
	public void testFindAllGroupTranscripts() throws Exception
	{
		transcriptBD.createTranscript(new StudentCourseTranscript(student1, course1));
		transcriptBD.createTranscript(new StudentGroupTranscript(student1, group1));
		transcriptBD.createTranscript(new StudentGroupTranscript(student1, group2));

		Assert.assertEquals(2, taUtil.getTranscriptBD().findAllGroupTranscripts().size());
	}

	@Test
	public void testFindAllGroupTranscriptsEmpty() throws Exception
	{

		Assert.assertEquals(0, taUtil.getTranscriptBD().findAllGroupTranscripts().size());
	}

	@Test
	public void testFindAllStudentTranscripts() throws Exception
	{
		transcriptBD.createTranscript(new StudentCourseTranscript(student1, course1));
		transcriptBD.createTranscript(new StudentCourseTranscript(student1, course2));
		transcriptBD.createTranscript(new StudentCourseTranscript(student2, course1));
		transcriptBD.createTranscript(new StudentGroupTranscript(student1, group1));
		transcriptBD.createTranscript(new StudentGroupTranscript(student1, group2));
		transcriptBD.createTranscript(new StudentGroupTranscript(student2, group1));
		transcriptBD.createTranscript(new StudentGroupTranscript(student2, group2));

		Assert.assertEquals(7, transcriptBD.findAllTranscripts().size());
		Assert.assertEquals(4, transcriptBD.findStudentTranscriptsForUser(student1)
				.size());
		Assert.assertEquals(3, transcriptBD.findStudentTranscriptsForUser(student2)
				.size());
	}

	@Test
	public void testFindAllStudentTranscriptsEmpty() throws Exception
	{
		Assert.assertEquals(0, transcriptBD.findAllTranscripts().size());
		Assert.assertEquals(0, transcriptBD.findStudentTranscriptsForUser(student1)
				.size());
		Assert.assertEquals(0, transcriptBD.findStudentTranscriptsForUser(student2)
				.size());
	}
}
