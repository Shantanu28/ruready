package test.ruready.business.ta;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionFormat;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.ta.entity.AssessmentItemResponse;
import net.ruready.business.ta.entity.AssessmentItemStatus;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestKnowledgeAssessmentItem
{

	protected final Log logger = LogFactory.getLog(getClass());

	private Question question1;

	@Before
	public void setUp()
	{
		question1 = ItemDemoUtil.createMockQuestion("Question 1", QuestionType.ACADEMIC,
				1);
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testCreateTestItem() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		assertEquals(question1.getId(), testItem.getReferenceId());
		assertEquals(question1.getName(), testItem.getName());
		assertEquals(question1.getFormulation(), testItem.getFormulation());
		assertEquals(AssessmentItemStatus.NOT_ANSWERED, testItem.getStatus());
		assertEquals(question1.getQuestionType(), testItem.getQuestionType());
		assertEquals(question1.getLevel(), testItem.getLevel());
		assertEquals(question1.getVariables(), testItem.getVariables());
		assertEquals(question1.getParameters(), testItem.getParameters());
		assertEquals(question1.getQuestionPrecision(), testItem
				.getQuestionPrecision());
		assertEquals(question1.getNumberOfChoices(), testItem
						.getNumberOfChoices());
	}

	@Test
	public void testCreateTestItemCheckNestedItems() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		assertEquals(question1.getAnswers().size(), testItem.getAnswers().size());
		for (int i = 0; i < question1.numAnswers(); i++)
		{
			assertEquals(question1.getAnswer(i).getAnswerText(), testItem
					.getAnswers().get(i).getAnswerText());
		}
		assertEquals(question1.getChoices().size(), testItem.getChoices().size());
		for (int i = 0; i < question1.numChoices(); i++)
		{
			assertEquals(question1.getChoice(i).getChoiceText(), testItem
					.getChoices().get(i).getChoiceText());
			assertEquals(question1.getChoice(i).isCorrect(), testItem.getChoices()
					.get(i).isCorrect());
		}
		assertEquals(question1.getHints().size(), testItem.getHints().size());
		for (int i = 0; i < question1.numHints(); i++)
		{
			assertEquals(question1.getHint(i).getHint1Text(), testItem.getHints()
					.get(i).getHint1Text());
			assertEquals(question1.getHint(i).getHint2Text(), testItem.getHints()
					.get(i).getHint2Text());
			assertEquals(question1.getHint(i).getKeyword1Text(), testItem
					.getHints().get(i).getKeyword1Text());
			assertEquals(question1.getHint(i).getKeyword2Text(), testItem
					.getHints().get(i).getKeyword2Text());
		}
	}

	@Test
	public void testCompleteTestItem() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		testItem.beginTestItem();
		assertEquals(AssessmentItemStatus.NOT_ANSWERED, testItem.getStatus());
		assertEquals(0d, testItem.getScore(), 1e-15);
		testItem.answerTestItem();
		assertEquals(AssessmentItemStatus.ANSWERED, testItem.getStatus());
	}

	@Test
	public void testStopTestItem() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		testItem.beginTestItem();
		assertEquals(AssessmentItemStatus.NOT_ANSWERED, testItem.getStatus());
		assertEquals(0d, testItem.getScore(), 1e-15);
		testItem.stopTestItem();
		assertEquals(AssessmentItemStatus.NOT_ANSWERED, testItem.getStatus());
		assertEquals(0d, testItem.getScore(), 1e-15);
	}
	
	@Test
	public void scoreTestItem() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		testItem.beginTestItem();
		testItem.answerTestItem();
		testItem.setScore(0.75d);
		assertEquals(AssessmentItemStatus.ANSWERED, testItem.getStatus());
		assertEquals(0.75d, testItem.getScore(), 1e-15);
	}
	
	@Test
	public void testRespondToTestItem() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		testItem.addResponse(AssessmentItemResponse.createSubmitAnswerResponse("answer"));
		assertEquals(1, testItem.getResponses().size());
	}
	
	@Test
	public void testRespondToTestItemTryAnswer() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		testItem.addResponse(AssessmentItemResponse.createTryAnswerResponse("answer1"));
		testItem.addResponse(AssessmentItemResponse.createTryAnswerResponse("answer2"));
		testItem.addResponse(AssessmentItemResponse.createSubmitAnswerResponse("answer3"));
		assertEquals(3, testItem.getResponses().size());
		assertEquals("answer1", testItem.getResponses().get(0).getAnswer());
		assertEquals("answer2", testItem.getResponses().get(1).getAnswer());
		assertEquals("answer3", testItem.getResponses().get(2).getAnswer());
	}
	
	@Test
	public void testRespondToTestItemHints() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		for (int i=0; i < testItem.getHints().size(); i++)
		{
			testItem.addResponse(AssessmentItemResponse.createHintResponse());
		}
		assertEquals(testItem.getHints().size(), testItem.getResponses().size());
	}
	
	@Test(expected=BusinessRuleException.class)
	public void testRespondToTestItemHintsTooMany() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		for (int i=0; i < testItem.getHints().size() + 1; i++)
		{
			testItem.addResponse(AssessmentItemResponse.createHintResponse());
		}
	}
	
	@Test(expected=BusinessRuleException.class)
	public void testRespondToTestItemTryAfterAnswer() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		testItem.addResponse(AssessmentItemResponse.createSubmitAnswerResponse("answer1"));
		testItem.addResponse(AssessmentItemResponse.createTryAnswerResponse("answer2"));
	}
	
	@Test(expected=BusinessRuleException.class)
	public void testRespondToTestItemHintAfterAnswer() throws Exception
	{
		final KnowledgeAssessmentItem testItem = new KnowledgeAssessmentItem(question1, QuestionFormat.MULTIPLE_CHOICE);
		testItem.addResponse(AssessmentItemResponse.createSubmitAnswerResponse("answer1"));
		testItem.addResponse(AssessmentItemResponse.createHintResponse());
	}
}
