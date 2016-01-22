package test.ruready.business.ta;

import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.ta.entity.AssessmentItemStatus;
import net.ruready.business.ta.entity.ExpectationAssessmentItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

public class TestExpectationAssessmentItem
{
	protected final Log logger = LogFactory.getLog(getClass());

	@Test
	public void testCreateTestItem1() throws Exception
	{
		final Expectation expectation = ItemDemoUtil.createMockExpectation("Expectation",
				true);
		final ExpectationAssessmentItem testItem = new ExpectationAssessmentItem(
				expectation);
		Assert.assertEquals(expectation.getId(), testItem.getReferenceId());
		Assert.assertEquals("Expectation", testItem.getName());
		Assert.assertEquals(true, testItem.isNegative());
		Assert.assertEquals(expectation.getFormulation(), testItem.getFormulation());
		Assert.assertEquals(AssessmentItemStatus.NOT_ANSWERED, testItem.getStatus());
	}

	@Test
	public void testCreateTestItem2() throws Exception
	{
		final Expectation expectation = ItemDemoUtil.createMockExpectation("Expectation",
				false);
		final ExpectationAssessmentItem testItem = new ExpectationAssessmentItem(
				expectation);
		Assert.assertEquals(expectation.getId(), testItem.getReferenceId());
		Assert.assertEquals("Expectation", testItem.getName());
		Assert.assertEquals(false, testItem.isNegative());
		Assert.assertEquals(expectation.getFormulation(), testItem.getFormulation());
		Assert.assertEquals(AssessmentItemStatus.NOT_ANSWERED, testItem.getStatus());
	}

	@Test
	public void testCompleteTestItem() throws Exception
	{
		final Expectation expectation = ItemDemoUtil.createMockExpectation("Expectation",
				true);
		final ExpectationAssessmentItem testItem = new ExpectationAssessmentItem(
				expectation);
		testItem.beginTestItem();
		Assert.assertEquals(AssessmentItemStatus.NOT_ANSWERED, testItem.getStatus());
		Assert.assertEquals(0d, testItem.getScore(), 1e-15);
		testItem.answerTestItem();
		Assert.assertEquals(AssessmentItemStatus.ANSWERED, testItem.getStatus());
	}

	@Test
	public void testStopTestItem() throws Exception
	{
		final Expectation expectation = ItemDemoUtil.createMockExpectation("Expectation",
				true);
		final ExpectationAssessmentItem testItem = new ExpectationAssessmentItem(
				expectation);
		testItem.beginTestItem();
		Assert.assertEquals(AssessmentItemStatus.NOT_ANSWERED, testItem.getStatus());
		Assert.assertEquals(0d, testItem.getScore(), 1e-15);
		testItem.stopTestItem();
		Assert.assertEquals(AssessmentItemStatus.NOT_ANSWERED, testItem.getStatus());
		Assert.assertEquals(0d, testItem.getScore(), 1e-15);
	}
	
	@Test
	public void scoreTestItem() throws Exception
	{
		final Expectation expectation = ItemDemoUtil.createMockExpectation("Expectation",true);
		final ExpectationAssessmentItem testItem = new ExpectationAssessmentItem(
				expectation);
		testItem.beginTestItem();
		testItem.answerTestItem();
		testItem.setScore(0.75d);
		Assert.assertEquals(AssessmentItemStatus.ANSWERED, testItem.getStatus());
		Assert.assertEquals(0.75d, testItem.getScore(), 1e-15);
	}
}
