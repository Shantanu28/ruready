package test.ruready.business.ta;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionFormat;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;
import net.ruready.business.ta.entity.property.DefaultKnowledgeAssessmentFactory;
import net.ruready.business.ta.entity.property.KnowledgeAssessmentFactory;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;
import org.junit.Before;
import org.junit.Test;

public class TestDefaultKnowledgeAssessmentFactory
{

	private KnowledgeAssessmentFactory assessmentFactory;
	private List<Question> questions;
	private List<Question> notEnoughQuestions;
	
	@Before
	public void initializeFactory()
	{
		assessmentFactory = new DefaultKnowledgeAssessmentFactory();
	}
	
	/**
	 * Create a question list with enough questions to create a test
	 */
	@Before
	public void createQuestions()
	{
		questions = new ArrayList<Question>();
		for (int level=1; level <= 4; level++)
		{
			for (QuestionType questionType : EnumSet.allOf(QuestionType.class))
			{
				for (int i=1; i <= 5; i++)
				{	
					Question question = new Question("Question L" + level + " Type-" + questionType + " #" + i, null);
					question.setId(Integer.valueOf(level*i).longValue());
					question.setQuestionType(questionType);
					question.setLevel(level);
					questions.add(question);
				}
			}
		}
	}
	
	/**
	 * Create a question list with enough questions to create a test
	 */
	@Before 
	public void createQuestionsNoEnough()
	{
		notEnoughQuestions = new ArrayList<Question>();
		for (int level=1; level <= 4; level++)
		{
			for (QuestionType questionType : EnumSet.allOf(QuestionType.class))
			{
				for (int i=1; i <= 1; i++)
				{	
					Question question = new Question("Question L" + level + " Type-" + questionType + " #" + i, null);
					question.setId(Integer.valueOf(level*i).longValue());
					question.setQuestionType(questionType);
					question.setLevel(level);
					notEnoughQuestions.add(question);
				}
			}
		}
	}
	
	/**
	 * Test that the created knowledge assessment has enough of each type of question
	 * @throws Exception
	 */
	@Test
	public void testCreateKnowledgeAssessment() throws Exception
	{
		final KnowledgeAssessment assessment = assessmentFactory.createKnowledgeAssessment(questions);
		assertEquals(16, assessment.getTestItems().size());
		
		// determine that the right amounts of each question type is in the assessment
		final Bag questionTypeBag = new HashBag();
		final Bag questionFormatBag = new HashBag();
		final Bag questionLevelBag = new HashBag();
		for (KnowledgeAssessmentItem item : assessment.getTestItems())
		{
			questionTypeBag.add(item.getQuestionType());
			questionFormatBag.add(item.getQuestionFormat());
			questionLevelBag.add(item.getLevel());
		}
		assertEquals(8, questionTypeBag.getCount(QuestionType.ACADEMIC));
		assertEquals(8, questionTypeBag.getCount(QuestionType.CREATIVE));
		assertEquals(8, questionFormatBag.getCount(QuestionFormat.MULTIPLE_CHOICE));
		assertEquals(8, questionFormatBag.getCount(QuestionFormat.OPEN_ENDED));
		assertEquals(4, questionLevelBag.getCount(1));
		assertEquals(4, questionLevelBag.getCount(2));
		assertEquals(4, questionLevelBag.getCount(3));
		assertEquals(4, questionLevelBag.getCount(4));
	}
	
	/**
	 * Test that the factory throws the correct exception when not enough questions are given to it
	 * @throws Exception
	 */
	@Test(expected=BusinessRuleException.class)
	public void testCreateKnowledgeAssessmentNotEnoughQuestions() throws Exception
	{
		assessmentFactory.createKnowledgeAssessment(notEnoughQuestions);		
	}
	
	/**
	 * Test creating a level 1 practice assessment
	 * @throws Exception
	 */
	@Test
	public void testCreatePracticeAssessmentLevel1() throws Exception
	{
		final KnowledgeAssessment assessment = assessmentFactory.createPracticeAssessment(1, questions);
		assertEquals(8, assessment.getTestItems().size());
		
		// determine that the right amounts of each question type is in the assessment
		final Bag questionTypeBag = new HashBag();
		final Bag questionFormatBag = new HashBag();
		final Bag questionLevelBag = new HashBag();
		for (KnowledgeAssessmentItem item : assessment.getTestItems())
		{
			questionTypeBag.add(item.getQuestionType());
			questionFormatBag.add(item.getQuestionFormat());
			questionLevelBag.add(item.getLevel());
		}
		assertEquals(4, questionTypeBag.getCount(QuestionType.ACADEMIC));
		assertEquals(4, questionTypeBag.getCount(QuestionType.CREATIVE));
		assertEquals(8, questionFormatBag.getCount(QuestionFormat.OPEN_ENDED));
		assertEquals(8, questionLevelBag.getCount(1));
	}
	
	/**
	 * Test creating a level 4 assessment
	 * @throws Exception
	 */
	@Test
	public void testCreatePracticeAssessmentLevel4() throws Exception
	{
		final KnowledgeAssessment assessment = assessmentFactory.createPracticeAssessment(4, questions);
		assertEquals(8, assessment.getTestItems().size());
		
		// determine that the right amounts of each question type is in the assessment
		final Bag questionTypeBag = new HashBag();
		final Bag questionFormatBag = new HashBag();
		final Bag questionLevelBag = new HashBag();
		for (KnowledgeAssessmentItem item : assessment.getTestItems())
		{
			questionTypeBag.add(item.getQuestionType());
			questionFormatBag.add(item.getQuestionFormat());
			questionLevelBag.add(item.getLevel());
		}
		assertEquals(4, questionTypeBag.getCount(QuestionType.ACADEMIC));
		assertEquals(4, questionTypeBag.getCount(QuestionType.CREATIVE));
		assertEquals(8, questionFormatBag.getCount(QuestionFormat.OPEN_ENDED));
		assertEquals(8, questionLevelBag.getCount(4));
	}
	
	/**
	 * Test creating a level 5 (non-existant) assessment
	 * @throws Exception
	 */
	@Test(expected=BusinessRuleException.class)
	public void testCreatePracticeAssessmentLevel5() throws Exception
	{
		assessmentFactory.createPracticeAssessment(5, questions);
	}
	
	/**
	 * Test creating a level 1 assessment without enough questions
	 * @throws Exception
	 */
	@Test(expected=BusinessRuleException.class)
	public void testCreatePracticeAssessmentNotEnoughQuestions() throws Exception
	{
		assessmentFactory.createPracticeAssessment(1, notEnoughQuestions);
	}
}
