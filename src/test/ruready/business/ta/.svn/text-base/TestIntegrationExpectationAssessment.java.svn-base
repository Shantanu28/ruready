package test.ruready.business.ta;

import static net.ruready.business.content.demo.manager.DemoCatalogCreator.COURSE_1;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.EXPECTATION_1_COUNT;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.EXPECTATION_1_PREFIX;
import static net.ruready.business.content.demo.manager.DemoCatalogCreator.EXPECTATION_2_COUNT;

import java.util.List;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.ta.entity.AssessmentStatus;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.ExpectationAssessmentItem;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import test.ruready.business.ContentTestFixture;
import test.ruready.rl.TestEnvTestBase;

public class TestIntegrationExpectationAssessment extends TestEnvTestBase
{

	// Test Fixtures
	static ContentTestFixture content;
	static TAUtil util;

	private ExpectationAssessment testWithNoItems;
	private ExpectationAssessment testWithManyItems;
	private ExpectationAssessment testWithOneItem;

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

		testWithNoItems = new ExpectationAssessment();

		testWithOneItem = new ExpectationAssessment();
		final Expectation expectation = content.getExpectation(EXPECTATION_1_PREFIX
				+ " #1");
		testWithOneItem.addTestItem(new ExpectationAssessmentItem(expectation));

		testWithManyItems = new ExpectationAssessment();
		final List<Expectation> expectations = content.getExpectationsForCourse(COURSE_1);
		for (Expectation e : expectations)
		{
			testWithManyItems.addTestItem(new ExpectationAssessmentItem(e));
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
		util.getAssessmentBD().createExpectationAssessment(testWithNoItems);
		Assert.assertNotNull(testWithNoItems.getId());
	}

	@Test
	public void testCreateAssessmentOneItem() throws Exception
	{
		Assert.assertNull(testWithOneItem.getId());
		Assert.assertEquals(1, testWithOneItem.getTestItems().size());
		util.getAssessmentBD().createExpectationAssessment(testWithOneItem);
		Assert.assertNotNull(testWithOneItem.getId());
	}

	@Test
	public void testCreateAssessmentManyItems() throws Exception
	{
		Assert.assertNull(testWithManyItems.getId());
		Assert.assertEquals(EXPECTATION_1_COUNT + EXPECTATION_2_COUNT, testWithManyItems
				.getTestItems().size());
		util.getAssessmentBD().createExpectationAssessment(testWithManyItems);
		Assert.assertNotNull(testWithManyItems.getId());
	}

	@Test
	public void testFindAssessments() throws Exception
	{
		util.getAssessmentBD().createExpectationAssessment(testWithNoItems);
		util.getAssessmentBD().createExpectationAssessment(testWithOneItem);
		util.getAssessmentBD().createExpectationAssessment(testWithManyItems);
		final List<ExpectationAssessment> assessments = util.getAssessmentBD()
				.findAllExpectationAssessments();
		Assert.assertEquals(3, assessments.size());
	}

	@Test
	public void testDeleteAssessment1() throws Exception
	{
		util.getAssessmentBD().createExpectationAssessment(testWithNoItems);
		final List<ExpectationAssessment> assessments = util.getAssessmentBD()
				.findAllExpectationAssessments();
		Assert.assertEquals(1, assessments.size());
		util.getAssessmentBD().deleteExpectationAssessment(testWithNoItems);
		// set object to null so that my cleanUp code doesn't try to delete it
		// twice
		testWithNoItems = null;
		final List<ExpectationAssessment> assessments2 = util.getAssessmentBD()
				.findAllExpectationAssessments();
		Assert.assertEquals(0, assessments2.size());
	}

	@Test
	public void testDeleteAssessment2() throws Exception
	{
		util.getAssessmentBD().createExpectationAssessment(testWithNoItems);
		util.getAssessmentBD().createExpectationAssessment(testWithOneItem);
		util.getAssessmentBD().createExpectationAssessment(testWithManyItems);
		final List<ExpectationAssessment> assessments = util.getAssessmentBD()
				.findAllExpectationAssessments();
		Assert.assertEquals(3, assessments.size());
		util.getAssessmentBD().deleteExpectationAssessment(testWithManyItems);
		// set object to null so that my cleanUp code doesn't try to delete it
		// twice
		testWithManyItems = null;
		final List<ExpectationAssessment> assessments2 = util.getAssessmentBD()
				.findAllExpectationAssessments();
		Assert.assertEquals(2, assessments2.size());
	}

	@Test
	public void testUpdateAssessmentRunAssessment() throws Exception
	{
		util.getAssessmentBD().createExpectationAssessment(testWithOneItem);
		testWithOneItem.beginTest();
		util.getAssessmentBD().updateExpectationAssessment(testWithOneItem);
		testWithOneItem.completeTest();
		util.getAssessmentBD().updateExpectationAssessment(testWithOneItem);
		Assert.assertEquals(AssessmentStatus.COMPLETE, testWithOneItem.getStatus());
	}

	@Test
	public void testUpdateAssessmentAddAssessmentItem() throws Exception
	{
		util.getAssessmentBD().createExpectationAssessment(testWithOneItem);
		Assert.assertEquals(1, testWithOneItem.getTestItems().size());
		testWithOneItem.addTestItem(new ExpectationAssessmentItem(content
				.getExpectation(EXPECTATION_1_PREFIX + " #2")));
		util.getAssessmentBD().updateExpectationAssessment(testWithOneItem);
		Assert.assertEquals(2, testWithOneItem.getTestItems().size());
	}

	@Test(expected = BusinessRuleException.class)
	public void testUpdateAssessmentAddDuplicateAssessmentItem() throws Exception
	{
		util.getAssessmentBD().createExpectationAssessment(testWithOneItem);
		Assert.assertEquals(1, testWithOneItem.getTestItems().size());
		testWithOneItem.addTestItem(new ExpectationAssessmentItem(content
				.getExpectation(EXPECTATION_1_PREFIX + " #1")));
	}
}
