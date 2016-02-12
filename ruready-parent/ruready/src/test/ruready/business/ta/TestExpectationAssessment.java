package test.ruready.business.ta;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.ta.entity.AssessmentItem;
import net.ruready.business.ta.entity.AssessmentItemStatus;
import net.ruready.business.ta.entity.AssessmentStatus;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.ExpectationAssessmentItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestExpectationAssessment
{
	private ExpectationAssessment testWithNoItems;
	private ExpectationAssessment testWithItems;

	@Before
	public void setUp()
	{
		testWithNoItems = new ExpectationAssessment();

		testWithItems = new ExpectationAssessment();
		for (int i = 0; i < 5; i++)
		{
			ExpectationAssessmentItem item = new ExpectationAssessmentItem(ItemDemoUtil
					.createMockExpectation("Expectation " + i, (i % 2 == 0)));
			testWithItems.getTestItems().add(item);
		}
	}

	@Test
	public void testCreateTest() throws Exception
	{
		Assert.assertEquals(AssessmentStatus.INCOMPLETE, testWithNoItems.getStatus());
		Assert.assertEquals(0d, testWithNoItems.getScore(), 1e-15);
		Assert.assertNotNull(testWithNoItems.getLastUpdated());
		Assert.assertEquals(0, testWithNoItems.getTestItems().size());
	}

	@Test
	public void testCreateTest2() throws Exception
	{
		Assert.assertEquals(AssessmentStatus.INCOMPLETE, testWithItems.getStatus());
		Assert.assertEquals(0d, testWithItems.getScore(), 1e-15);
		Assert.assertNotNull(testWithItems.getLastUpdated());
		Assert.assertEquals(5, testWithItems.getTestItems().size());
	}
	
	@Test
	public void testCompleteTest() throws Exception
	{
		Assert.assertEquals(AssessmentStatus.INCOMPLETE, testWithItems.getStatus());
		Assert.assertEquals(0d, testWithItems.getScore(), 1e-15);
		testWithItems.completeTest();
		Assert.assertEquals(AssessmentStatus.COMPLETE, testWithItems.getStatus());
		for (AssessmentItem item : testWithItems.getTestItems())
		{
			Assert.assertEquals(AssessmentItemStatus.ANSWERED, item.getStatus());
		}
	}

	@Test
	public void testStopTest() throws Exception
	{
		Assert.assertEquals(AssessmentStatus.INCOMPLETE, testWithItems.getStatus());
		Assert.assertEquals(0d, testWithItems.getScore(), 1e-15);
		testWithItems.stopTest();
		Assert.assertEquals(AssessmentStatus.CLOSED, testWithItems.getStatus());
		Assert.assertEquals(0d, testWithItems.getScore(), 1e-15);
	}
	
	@Test(expected=BusinessRuleException.class)
	public void testCompleteTestAlreadyClosed()
	{
		testWithItems.stopTest();
		Assert.assertEquals(AssessmentStatus.CLOSED, testWithItems.getStatus());
		testWithItems.completeTest();
	}
	
	@Test(expected=BusinessRuleException.class)
	public void testCloseTestAlreadyCompleted()
	{
		testWithItems.completeTest();
		Assert.assertEquals(AssessmentStatus.COMPLETE, testWithItems.getStatus());
		testWithItems.stopTest();		
	}
	
	@Test(expected=BusinessRuleException.class)
	public void testBeginTestAlreadyCompleted()
	{
		testWithItems.completeTest();
		Assert.assertEquals(AssessmentStatus.COMPLETE, testWithItems.getStatus());
		testWithItems.beginTest();		
	}
	
	@Test
	public void testScoreTest() throws Exception
	{
		Assert.assertEquals(AssessmentStatus.INCOMPLETE, testWithItems.getStatus());
		testWithItems.completeTest();
		testWithItems.setScore(0.63d);
		Assert.assertEquals(AssessmentStatus.COMPLETE, testWithItems.getStatus());
		Assert.assertEquals(0.63d, testWithItems.getScore(), 1e-15);
	}
}
