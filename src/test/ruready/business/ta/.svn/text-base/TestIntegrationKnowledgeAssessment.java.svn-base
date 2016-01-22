package test.ruready.business.ta;

import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1_T2_ST1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.QUESTION_PREFIX;

import java.util.List;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionFormat;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.ta.entity.AssessmentStatus;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import test.ruready.business.ContentTestFixture;
import test.ruready.rl.TestEnvTestBase;

public class TestIntegrationKnowledgeAssessment extends TestEnvTestBase
{
	// Test Fixtures
	static ContentTestFixture content;
	static TAUtil util;

	private KnowledgeAssessment testWithNoItems;
	private KnowledgeAssessment testWithManyItems;
	private KnowledgeAssessment testWithOneItem;

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
				util = new TAUtil(env);
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
		environment.getSession().flush();

		testWithNoItems = new KnowledgeAssessment();

		testWithOneItem = new KnowledgeAssessment();
		// Computing with Angles: Mock #1 ACADEMIC 1
		final Question question = content.getQuestion(COURSE_1_T2_ST1 + ": "
				+ QUESTION_PREFIX + " #1# ACADEMIC 1");
		testWithOneItem.addTestItem(new KnowledgeAssessmentItem(question, QuestionFormat.MULTIPLE_CHOICE));

		testWithManyItems = new KnowledgeAssessment();
		final List<Question> questions = content.getQuestionsForCourse(COURSE_1);
		for (Question q : questions)
		{
			testWithManyItems.addTestItem(new KnowledgeAssessmentItem(q, QuestionFormat.OPEN_ENDED));
		}
	}

	@Override
	protected void cleanUp()
	{
		util.deleteAssessment(testWithNoItems);
		util.deleteAssessment(testWithOneItem);
		util.deleteAssessment(testWithManyItems);
	}

	@Test
	public void testCreateAssessmentNoItems() throws Exception
	{
		Assert.assertNull(testWithNoItems.getId());
		util.getAssessmentBD().createKnowledgeAssessment(testWithNoItems);
		Assert.assertNotNull(testWithNoItems.getId());
	}

	@Test
	public void testCreateAssessmentOneItem() throws Exception
	{
		Assert.assertNull(testWithOneItem.getId());
		Assert.assertEquals(1, testWithOneItem.getTestItems().size());
		util.getAssessmentBD().createKnowledgeAssessment(testWithOneItem);
		Assert.assertNotNull(testWithOneItem.getId());
	}

	@Test
	public void testCreateAssessmentManyItems() throws Exception
	{
		Assert.assertNull(testWithManyItems.getId());
		Assert.assertEquals(32, testWithManyItems.getTestItems().size());
		util.getAssessmentBD().createKnowledgeAssessment(testWithManyItems);
		Assert.assertNotNull(testWithManyItems.getId());
	}

	@Test
	public void testFindAssessments() throws Exception
	{
		util.getAssessmentBD().createKnowledgeAssessment(testWithNoItems);
		util.getAssessmentBD().createKnowledgeAssessment(testWithOneItem);
		util.getAssessmentBD().createKnowledgeAssessment(testWithManyItems);
		final List<KnowledgeAssessment> assessments = util.getAssessmentBD()
				.findAllKnowledgeAssessments();
		Assert.assertEquals(3, assessments.size());
	}

	@Test
	public void testDeleteAssessment1() throws Exception
	{
		util.getAssessmentBD().createKnowledgeAssessment(testWithNoItems);
		final List<KnowledgeAssessment> assessments = util.getAssessmentBD()
				.findAllKnowledgeAssessments();
		Assert.assertEquals(1, assessments.size());
		util.getAssessmentBD().deleteKnowledgeAssessment(testWithNoItems);
		// set object to null so that my cleanUp code doesn't try to delete it
		// twice
		testWithNoItems = null;
		final List<KnowledgeAssessment> assessments2 = util.getAssessmentBD()
				.findAllKnowledgeAssessments();
		Assert.assertEquals(0, assessments2.size());
	}

	@Test
	public void testDeleteAssessment2() throws Exception
	{
		util.getAssessmentBD().createKnowledgeAssessment(testWithNoItems);
		util.getAssessmentBD().createKnowledgeAssessment(testWithOneItem);
		util.getAssessmentBD().createKnowledgeAssessment(testWithManyItems);
		final List<KnowledgeAssessment> assessments = util.getAssessmentBD()
				.findAllKnowledgeAssessments();
		Assert.assertEquals(3, assessments.size());
		util.getAssessmentBD().deleteKnowledgeAssessment(testWithManyItems);
		// set object to null so that my cleanUp code doesn't try to delete it
		// twice
		testWithManyItems = null;
		final List<KnowledgeAssessment> assessments2 = util.getAssessmentBD()
				.findAllKnowledgeAssessments();
		Assert.assertEquals(2, assessments2.size());
	}

	@Test
	public void testUpdateAssessmentRunAssessment() throws Exception
	{
		util.getAssessmentBD().createKnowledgeAssessment(testWithOneItem);
		testWithOneItem.beginTest();
		util.getAssessmentBD().updateKnowledgeAssessment(testWithOneItem);
		testWithOneItem.completeTest();
		util.getAssessmentBD().updateKnowledgeAssessment(testWithOneItem);
		Assert.assertEquals(AssessmentStatus.COMPLETE, testWithOneItem.getStatus());
	}

	@Test
	public void testUpdateAssessmentAddAssessmentItem() throws Exception
	{
		util.getAssessmentBD().createKnowledgeAssessment(testWithOneItem);
		Assert.assertEquals(1, testWithOneItem.getTestItems().size());
		testWithOneItem
				.addTestItem(new KnowledgeAssessmentItem(content
						.getQuestion(COURSE_1_T2_ST1 + ": " + QUESTION_PREFIX
								+ " #1# ACADEMIC 2"), QuestionFormat.MULTIPLE_CHOICE));
		util.getAssessmentBD().updateKnowledgeAssessment(testWithOneItem);
		Assert.assertEquals(2, testWithOneItem.getTestItems().size());
	}

	@Test(expected = BusinessRuleException.class)
	public void testUpdateAssessmentAddDuplicateAssessmentItem() throws Exception
	{
		util.getAssessmentBD().createKnowledgeAssessment(testWithOneItem);
		Assert.assertEquals(1, testWithOneItem.getTestItems().size());
		testWithOneItem
				.addTestItem(new KnowledgeAssessmentItem(content
						.getQuestion(COURSE_1_T2_ST1 + ": " + QUESTION_PREFIX
								+ " #1# ACADEMIC 1"), QuestionFormat.OPEN_ENDED));
	}
}
