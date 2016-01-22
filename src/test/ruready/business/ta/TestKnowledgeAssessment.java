package test.ruready.business.ta;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.question.entity.property.QuestionFormat;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.ta.entity.AssessmentStatus;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestKnowledgeAssessment
{
	private KnowledgeAssessment testWithNoItems;
	private KnowledgeAssessment testWithItems;

	@Before
	public void setUp()
	{
		testWithNoItems = new KnowledgeAssessment();

		testWithItems = new KnowledgeAssessment();
		for (int i = 0; i < 5; i++)
		{
			KnowledgeAssessmentItem item = new KnowledgeAssessmentItem(ItemDemoUtil
					.createMockQuestion("Question " + i, QuestionType.values()[i % 2],
							(i % 4) + 1), QuestionFormat.MULTIPLE_CHOICE);
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
