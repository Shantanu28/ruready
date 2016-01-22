package test.ruready.business.ta;

import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2;
import static net.ruready.business.content.demo.manager.DemoWorldCreator.SCHOOL_UTAH;

import java.util.List;

import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionFormat;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.entity.TranscriptActiveStatus;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.eis.exception.RecordNotFoundException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import test.ruready.business.ContentTestFixture;
import test.ruready.business.UserUtil;
import test.ruready.rl.TestEnvTestBase;

public class TestIntegrationStudentGroupTranscript extends TestEnvTestBase
{
	// Test Fixtures
	static ContentTestFixture content;
	static TAUtil taUtil;
	static UserUtil userUtil;

	private AbstractStudentTranscriptBD transcriptBD;

	private StudentGroupTranscript transcript;

	private UserGroup group1;
	private UserGroup group2;

	private User student1;
	private User student2;

	private ExpectationAssessment expectationAssessment1;
	private KnowledgeAssessment knowledgeAssessment1;

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

		group1 = new UserGroup(content.getCourse(COURSE_1), content
				.getSchool(SCHOOL_UTAH));
		userUtil.saveGroup(group1);
		group2 = new UserGroup(content.getCourse(COURSE_2), content
				.getSchool(SCHOOL_UTAH));
		userUtil.saveGroup(group2);

		student1 = userUtil.getUser("student1");
		student1.addRole(RoleType.STUDENT.newInstance());
		userUtil.saveUser(student1);

		student2 = userUtil.getUser("student2");
		student2.addRole(RoleType.STUDENT.newInstance());
		userUtil.saveUser(student2);

		expectationAssessment1 = new ExpectationAssessment();
		final List<Expectation> expectations = content.getExpectationsForCourse(COURSE_1);
		for (Expectation expectation : expectations)
		{
			expectationAssessment1.addTestItem(expectation);
		}

		knowledgeAssessment1 = new KnowledgeAssessment();
		final List<Question> questions = content.getQuestionsForCourse(COURSE_1);
		for (Question question : questions)
		{
			knowledgeAssessment1.addTestItem(question, QuestionFormat.MULTIPLE_CHOICE);
		}
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
	public void testCreateTranscript1() throws Exception
	{
		transcript = new StudentGroupTranscript(student1, group1);
		Assert.assertNull(transcript.getId());
		transcriptBD.createTranscript(transcript);
		Assert.assertNotNull(transcript.getId());
	}

	@Test
	public void testCreateTranscript2() throws Exception
	{
		transcript = transcriptBD.createGroupTranscript(student1, group1);
		Assert.assertNotNull(transcript.getId());
	}

	@Test
	public void testCreateTranscript3() throws Exception
	{
		transcript = transcriptBD.createGroupTranscript((StudentRole) student1
				.getRole(RoleType.STUDENT), group1);
		Assert.assertNotNull(transcript.getId());
	}

	@Test
	public void testCreateTranscriptIfNotExists1() throws Exception
	{
		transcriptBD.createGroupTranscriptIfNotExists(student1, group1);
		Assert.assertEquals(1, transcriptBD.findAllGroupTranscripts().size());
	}

	@Test
	public void testCreateTranscriptIfNotExists2() throws Exception
	{
		transcriptBD.createGroupTranscript(student1, group1);
		Assert.assertEquals(1, transcriptBD.findAllGroupTranscripts().size());
		transcriptBD.createGroupTranscriptIfNotExists(student1, group1);
		Assert.assertEquals(1, transcriptBD.findAllGroupTranscripts().size());
	}

	@Test
	public void testCreateTranscriptWithAssessment() throws Exception
	{
		transcript = new StudentGroupTranscript(student1, group1);
		transcript.addExpectationAssessment(expectationAssessment1);
		transcriptBD.createTranscript(transcript);
		Assert.assertNotNull(transcript.getId());
		Assert.assertEquals(1, transcript.getExpectationAssessments().size());
	}

	@Test
	public void testCreateTranscriptWithTwoAssessments() throws Exception
	{
		transcript = new StudentGroupTranscript(student1, group1);
		transcript.addExpectationAssessment(expectationAssessment1);
		transcript.addKnowledgeAssessment(knowledgeAssessment1);
		transcriptBD.createTranscript(transcript);
		Assert.assertNotNull(transcript.getId());
		Assert.assertEquals(1, transcript.getExpectationAssessments().size());
		Assert.assertEquals(1, transcript.getKnowledgeAssessments().size());
	}

	@Test
	public void testUpdateTranscriptAddAssessment() throws Exception
	{
		transcript = new StudentGroupTranscript(student1, group1);
		transcriptBD.createTranscript(transcript);
		Assert.assertNotNull(transcript.getId());
		transcript.addExpectationAssessment(expectationAssessment1);
		transcriptBD.updateTranscript(transcript);
		Assert.assertEquals(1, transcript.getExpectationAssessments().size());
	}

	@Test
	public void testFindAllTranscripts() throws Exception
	{
		transcript = new StudentGroupTranscript(student1, group1);
		transcriptBD.createTranscript(transcript);
		final List<StudentTranscript> loadedTranscript = transcriptBD
				.findAllTranscripts();
		Assert.assertEquals(1, loadedTranscript.size());
		Assert.assertEquals(group1.getName(), ((StudentGroupTranscript) loadedTranscript
				.get(0)).getGroup().getName());
	}

	@Test
	public void testFindAllCourseTranscripts() throws Exception
	{
		transcript = new StudentGroupTranscript(student1, group1);
		transcriptBD.createTranscript(transcript);
		final List<StudentCourseTranscript> loadedTranscript = transcriptBD
				.findAllCourseTranscripts();
		Assert.assertEquals(0, loadedTranscript.size());
	}

	@Test
	public void testFindAllGroupTranscripts() throws Exception
	{
		transcript = new StudentGroupTranscript(student1, group1);
		transcriptBD.createTranscript(transcript);
		final List<StudentGroupTranscript> loadedTranscript = transcriptBD
				.findAllGroupTranscripts();
		Assert.assertEquals(1, loadedTranscript.size());
		Assert.assertEquals(group1.getName(), loadedTranscript.get(0).getGroup()
				.getName());
	}

	@Test
	public void testFindByIdTranscript() throws Exception
	{
		transcript = new StudentGroupTranscript(student1, group1);
		transcriptBD.createTranscript(transcript);
		final StudentTranscript loadedTranscript = transcriptBD.findById(transcript
				.getId());
		Assert.assertNotNull(loadedTranscript);
		Assert.assertEquals(transcript.getId(), loadedTranscript.getId());
	}

	@Test(expected = RecordNotFoundException.class)
	public void testFindByIdTranscriptNotFound() throws Exception
	{
		transcript = new StudentGroupTranscript(student1, group1);
		transcriptBD.createTranscript(transcript);
		transcriptBD.findById(0L);
	}

	@Test
	public void testFindByStudentGroupTranscripts() throws Exception
	{
		transcriptBD.createTranscript(new StudentGroupTranscript(student1, group1));
		transcriptBD.createTranscript(new StudentGroupTranscript(student2, group1));
		transcriptBD.createTranscript(new StudentGroupTranscript(student2, group2));

		Assert.assertEquals(1, transcriptBD.findStudentGroupTranscriptsForUser(student1)
				.size());
		Assert.assertEquals(2, transcriptBD.findStudentGroupTranscriptsForUser(student2)
				.size());
	}

	@Test
	public void testFindByStudentGroupTranscriptsEmpty() throws Exception
	{
		Assert.assertEquals(0, transcriptBD.findStudentGroupTranscriptsForUser(student1)
				.size());
	}

	@Test
	public void testFindByGroupTranscripts() throws Exception
	{
		transcriptBD.createTranscript(new StudentGroupTranscript(student1, group1));
		transcriptBD.createTranscript(new StudentGroupTranscript(student2, group1));
		transcriptBD.createTranscript(new StudentGroupTranscript(student2, group2));

		Assert.assertEquals(2, transcriptBD.findTranscriptsForGroup(group1).size());
		Assert.assertEquals(1, transcriptBD.findTranscriptsForGroup(group2).size());
	}

	@Test
	public void testFindByGroupTranscriptsEmpty() throws Exception
	{
		Assert.assertEquals(0, transcriptBD.findTranscriptsForGroup(group1).size());
	}

	@Test
	public void testFindStudentGroupTranscript() throws Exception
	{
		transcript = transcriptBD.createGroupTranscript(student1, group1);
		Assert.assertNotNull(transcriptBD.findGroupTranscript(student1, group1));
		Assert.assertEquals(transcript, transcriptBD
				.findGroupTranscript(student1, group1));
	}

	@Test
	public void testFindStudentGroupTranscriptNotExists() throws Exception
	{
		Assert.assertNull(transcriptBD.findGroupTranscript(student1, group1));
	}

	@Test
	public void testDeleteTranscript() throws Exception
	{
		final StudentGroupTranscript testTranscript = new StudentGroupTranscript(
				student1, group1);
		final StudentGroupTranscript testTranscript2 = new StudentGroupTranscript(
				student1, group2);
		transcriptBD.createTranscript(testTranscript);
		transcriptBD.createTranscript(testTranscript2);
		Assert.assertEquals(2, transcriptBD.findAllTranscripts().size());
		transcriptBD.deleteTranscript(testTranscript2);
		Assert.assertEquals(1, transcriptBD.findAllTranscripts().size());
		transcriptBD.deleteTranscript(testTranscript);
		Assert.assertEquals(0, transcriptBD.findAllTranscripts().size());
	}

	@Test
	public void testCloseAllTranscriptsForGroup() throws Exception
	{
		final StudentGroupTranscript testTranscript = new StudentGroupTranscript(
				student1, group1);
		final StudentGroupTranscript testTranscript2 = new StudentGroupTranscript(
				student2, group1);
		transcriptBD.createTranscript(testTranscript);
		transcriptBD.createTranscript(testTranscript2);
		transcriptBD.closeAllTranscriptsForGroup(group1);
		Assert.assertEquals(TranscriptActiveStatus.CLOSED, testTranscript
				.getActiveStatus());
		Assert.assertEquals(TranscriptActiveStatus.CLOSED, testTranscript2
				.getActiveStatus());
	}

	@Test
	public void testOpenAllTranscriptsForGroup() throws Exception
	{
		final StudentGroupTranscript testTranscript = new StudentGroupTranscript(
				student1, group1);
		final StudentGroupTranscript testTranscript2 = new StudentGroupTranscript(
				student2, group1);
		testTranscript2.closeTranscript();
		transcriptBD.createTranscript(testTranscript);
		transcriptBD.createTranscript(testTranscript2);
		transcriptBD.openAllTranscriptsForGroup(group1);
		Assert
				.assertEquals(TranscriptActiveStatus.OPEN, testTranscript
						.getActiveStatus());
		Assert.assertEquals(TranscriptActiveStatus.OPEN, testTranscript2
				.getActiveStatus());
	}

	@Test
	public void testReOpenAllTranscriptsForGroup() throws Exception
	{
		final StudentGroupTranscript testTranscript = new StudentGroupTranscript(
				student1, group1);
		final StudentGroupTranscript testTranscript2 = new StudentGroupTranscript(
				student2, group1);
		testTranscript2.closeTranscript();
		transcriptBD.createTranscript(testTranscript);
		transcriptBD.createTranscript(testTranscript2);
		transcriptBD.closeAllTranscriptsForGroup(group1);
		Assert.assertEquals(TranscriptActiveStatus.CLOSED, testTranscript
				.getActiveStatus());
		Assert.assertEquals(TranscriptActiveStatus.CLOSED, testTranscript2
				.getActiveStatus());
		transcriptBD.openAllTranscriptsForGroup(group1);
		Assert
				.assertEquals(TranscriptActiveStatus.OPEN, testTranscript
						.getActiveStatus());
		Assert.assertEquals(TranscriptActiveStatus.OPEN, testTranscript2
				.getActiveStatus());
	}
}
