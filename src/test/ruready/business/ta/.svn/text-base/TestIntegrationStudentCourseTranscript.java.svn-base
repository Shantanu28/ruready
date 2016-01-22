package test.ruready.business.ta;

import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_2;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionFormat;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.eis.exception.RecordNotFoundException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import test.ruready.business.ContentTestFixture;
import test.ruready.business.UserUtil;
import test.ruready.rl.TestEnvTestBase;

public class TestIntegrationStudentCourseTranscript extends TestEnvTestBase
{
	// Test Fixtures
	static ContentTestFixture content;
	static TAUtil taUtil;
	static UserUtil userUtil;

	private AbstractStudentTranscriptBD transcriptBD;

	private StudentCourseTranscript transcript;
	private Course course1;
	private Course course2;
	private User student1;
	private User student2;

	private ExpectationAssessment expectationAssessment1;
	private ExpectationAssessment expectationAssessment2;

	private KnowledgeAssessment knowledgeAssessment1;
	private KnowledgeAssessment knowledgeAssessment2;
	
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

		student1 = userUtil.getUser("student1");
		student1.addRole(RoleType.STUDENT.newInstance());
		userUtil.saveUser(student1);

		student2 = userUtil.getUser("student2");
		student2.addRole(RoleType.STUDENT.newInstance());
		userUtil.saveUser(student2);

		expectationAssessment1 = new ExpectationAssessment();
		expectationAssessment2 = new ExpectationAssessment();
		final List<Expectation> expectations = content.getExpectationsForCourse(COURSE_1);
		for (Expectation expectation : expectations)
		{
			expectationAssessment1.addTestItem(expectation);
			expectationAssessment2.addTestItem(expectation);
		}

		knowledgeAssessment1 = new KnowledgeAssessment();
		knowledgeAssessment2 = new KnowledgeAssessment();
		final List<Question> questions = content.getQuestionsForCourse(COURSE_1);
		for (Question question : questions)
		{
			knowledgeAssessment1.addTestItem(question, QuestionFormat.MULTIPLE_CHOICE);
			knowledgeAssessment2.addTestItem(question, QuestionFormat.MULTIPLE_CHOICE);
		}
	}

	@Override
	protected void cleanUp()
	{
		taUtil.deleteAllTranscripts();

		userUtil.deleteUser(student1);
		userUtil.deleteUser(student2);
	}

	@Test
	public void testCreateTranscript1() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		Assert.assertNull(transcript.getId());
		transcriptBD.createTranscript(transcript);
		Assert.assertNotNull(transcript.getId());
	}

	@Test
	public void testCreateTranscript2() throws Exception
	{
		transcript = transcriptBD.createCourseTranscript(student1, course1);
		Assert.assertNotNull(transcript.getId());
	}

	@Test
	public void testCreateTranscript3() throws Exception
	{
		transcript = transcriptBD.createCourseTranscript((StudentRole) student1
				.getRole(RoleType.STUDENT), course1);
		Assert.assertNotNull(transcript.getId());
	}

	@Test
	public void testCreateTranscriptIfNotExists1() throws Exception
	{
		transcriptBD.createCourseTranscriptIfNotExists(student1, course1);
		Assert.assertEquals(1, transcriptBD.findAllCourseTranscripts().size());
	}

	@Test
	public void testCreateTranscriptIfNotExists2() throws Exception
	{
		transcriptBD.createCourseTranscript(student1, course1);
		Assert.assertEquals(1, transcriptBD.findAllCourseTranscripts().size());
		transcriptBD.createCourseTranscriptIfNotExists(student1, course1);
		Assert.assertEquals(1, transcriptBD.findAllCourseTranscripts().size());
	}

	@Test
	public void testCreateTranscriptWithAssessment() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcript.addExpectationAssessment(expectationAssessment1);
		transcriptBD.createTranscript(transcript);
		Assert.assertNotNull(transcript.getId());
		Assert.assertEquals(1, transcript.getExpectationAssessments().size());
	}

	@Test
	public void testCreateTranscriptWithTwoAssessments() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
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
		transcript = new StudentCourseTranscript(student1, course1);
		transcriptBD.createTranscript(transcript);
		Assert.assertNotNull(transcript.getId());
		transcript.addExpectationAssessment(expectationAssessment1);
		transcriptBD.updateTranscript(transcript);
		Assert.assertEquals(1, transcript.getExpectationAssessments().size());
	}

	@Test
	public void testHasExpectationAssessment() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcriptBD.createTranscript(transcript);
		Assert.assertFalse(transcript.hasExpectationAssessment());
		transcript.addExpectationAssessment(expectationAssessment1);
		transcriptBD.updateTranscript(transcript);
		Assert.assertTrue(transcript.hasExpectationAssessment());
	}

	@Test
	public void testHasKnowledgeAssessment() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcriptBD.createTranscript(transcript);
		Assert.assertFalse(transcript.hasKnowledgeAssessment());
		transcript.addKnowledgeAssessment(knowledgeAssessment1);
		transcriptBD.updateTranscript(transcript);
		Assert.assertTrue(transcript.hasKnowledgeAssessment());
	}

	@Test
	public void testExpectationAssessmentOrder() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcript.addExpectationAssessment(expectationAssessment1);
		expectationAssessment1.completeTest();
		transcript.addExpectationAssessment(expectationAssessment2);
		transcriptBD.createTranscript(transcript);
		final StudentTranscript loadedTranscript = transcriptBD.findById(transcript
				.getId());
		Assert.assertEquals(2, loadedTranscript.getExpectationAssessments().size());
		Assert.assertSame(expectationAssessment1, loadedTranscript
				.getExpectationAssessment(0));
		Assert.assertSame(expectationAssessment2, loadedTranscript
				.getExpectationAssessment(1));
		Assert.assertNotSame(expectationAssessment1, loadedTranscript
				.getExpectationAssessment(1));
		Assert.assertSame(expectationAssessment2, loadedTranscript
				.getCurrentExpectationAssessment());
	}

	@Test
	public void testKnowledgeAssessmentOrder() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcript.addKnowledgeAssessment(knowledgeAssessment1);
		knowledgeAssessment1.completeTest();
		transcript.addKnowledgeAssessment(knowledgeAssessment2);
		transcriptBD.createTranscript(transcript);
		final StudentTranscript loadedTranscript = transcriptBD.findById(transcript
				.getId());
		Assert.assertEquals(2, loadedTranscript.getKnowledgeAssessments().size());
		Assert.assertSame(knowledgeAssessment1, loadedTranscript
				.getKnowledgeAssessment(0));
		Assert.assertSame(knowledgeAssessment2, loadedTranscript
				.getKnowledgeAssessment(1));
		Assert.assertNotSame(knowledgeAssessment1, loadedTranscript
				.getKnowledgeAssessment(1));
		Assert.assertSame(knowledgeAssessment2, loadedTranscript
				.getCurrentKnowledgeAssessment());
	}

	@Test
	public void testFindAllTranscripts() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcriptBD.createTranscript(transcript);
		final List<StudentTranscript> loadedTranscript = transcriptBD
				.findAllTranscripts();
		Assert.assertEquals(1, loadedTranscript.size());
		Assert
				.assertEquals(course1.getName(),
						((StudentCourseTranscript) loadedTranscript.get(0)).getCourse()
								.getName());
	}

	@Test
	public void testFindAllCourseTranscripts() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcriptBD.createTranscript(transcript);
		final List<StudentCourseTranscript> loadedTranscript = transcriptBD
				.findAllCourseTranscripts();
		Assert.assertEquals(1, loadedTranscript.size());
		Assert.assertEquals(course1.getName(), loadedTranscript.get(0).getCourse()
				.getName());
	}

	@Test
	public void testFindAllGroupTranscripts() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcriptBD.createTranscript(transcript);
		final List<StudentGroupTranscript> loadedTranscript = transcriptBD
				.findAllGroupTranscripts();
		Assert.assertEquals(0, loadedTranscript.size());
	}

	@Test
	public void testFindByIdTranscript() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcriptBD.createTranscript(transcript);
		final StudentTranscript loadedTranscript = transcriptBD.findById(transcript
				.getId());
		Assert.assertNotNull(loadedTranscript);
		Assert.assertEquals(transcript.getId(), loadedTranscript.getId());
	}

	@Test(expected = RecordNotFoundException.class)
	public void testFindByIdTranscriptNotFound() throws Exception
	{
		transcript = new StudentCourseTranscript(student1, course1);
		transcriptBD.createTranscript(transcript);
		transcriptBD.findById(0L);
	}

	@Test
	public void testFindByStudentCourseTranscripts() throws Exception
	{
		transcriptBD.createTranscript(new StudentCourseTranscript(student1, course1));
		transcriptBD.createTranscript(new StudentCourseTranscript(student1, course2));
		transcriptBD.createTranscript(new StudentCourseTranscript(student2, course1));
		Assert.assertEquals(2, transcriptBD.findStudentCourseTranscriptsForUser(student1)
				.size());
		Assert.assertEquals(1, transcriptBD.findStudentCourseTranscriptsForUser(student2)
				.size());
	}

	@Test
	public void testFindByStudentCourseTranscriptsEmpty() throws Exception
	{
		Assert.assertEquals(0, transcriptBD.findStudentCourseTranscriptsForUser(student1)
				.size());
	}

	@Test
	public void testFindStudentCourseTranscript() throws Exception
	{
		transcript = transcriptBD.createCourseTranscript(student1, course1);
		Assert.assertNotNull(transcriptBD.findCourseTranscript(student1, course1));
		Assert.assertEquals(transcript, transcriptBD.findCourseTranscript(student1,
				course1));
	}

	@Test
	public void testFindStudentCourseTranscriptNotExists() throws Exception
	{
		Assert.assertNull(transcriptBD.findCourseTranscript(student1, course1));
	}

	@Test
	public void testDeleteTranscript() throws Exception
	{
		final StudentCourseTranscript testTranscript = new StudentCourseTranscript(
				student1, course1);
		final StudentCourseTranscript testTranscript2 = new StudentCourseTranscript(
				student1, course2);
		transcriptBD.createTranscript(testTranscript);
		transcriptBD.createTranscript(testTranscript2);
		Assert.assertEquals(2, transcriptBD.findAllTranscripts().size());
		transcriptBD.deleteTranscript(testTranscript2);
		Assert.assertEquals(1, transcriptBD.findAllTranscripts().size());
		transcriptBD.deleteTranscript(testTranscript);
		Assert.assertEquals(0, transcriptBD.findAllTranscripts().size());
	}
}
