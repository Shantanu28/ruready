package net.ruready.business.ta.entity.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionFormat;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;
/**
 * Implements the default rules for creating knowledge assessments.
* <p>
* The rules for creating knowledge assessments are as follows:
* <ul>
* 	<li>Sixteen questions are to be selected for test</li>
* 	<li>The sixteen questions should be a randomly-ordered, but represent each of the possible combinations of the following elements:
* 		<ul>
* 			<li>Question Type: Academic or Creative</li>
* 			<li>Question Difficulty Level: 1, 2, 3, 4</li>
* 			<li>Question Input Type: Multiple choice or open-ended</li>
*		</ul>
*	</li>
* </ul>
* <p>
* The rules for creating practice knowledge assessments are as follows:
* <ul>
* 	<li>Eight questions are to be selected for the test</li>
* 	<li>The eight questions are to be randomly-ordered, and represent four of each of the following possible combinations:
* 		<ul>
* 			<li>Question Type: Academic or Creative</li>
* 			<li>Question Difficulty Level: specified level only</li>
* 			<li>Question Input Type: Open-ended only</li>
* 		</ul>
* 	</li>
* </ul>
* -------------------------------------------------------------------------<br>
* (c) 2006-2007 Continuing Education, University of Utah<br>
* All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
* <p>
* This file is part of the RUReady Program software.<br>
* Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
* Academic Outreach and Continuing Education (AOCE)<br>
* 1901 East South Campus Dr., Room 2197-E<br>
* University of Utah, Salt Lake City, UT 84112-9359<br>
* U.S.A.<br>
* Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
* <br>
* Please contact these numbers immediately if you receive this file without permission
* from the authors. Thank you.<br>
* -------------------------------------------------------------------------
* 
* @author Jeremy Lund
* @version Nov 30, 2007
*/
/**
 * Long description ...
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-D<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 *
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 30, 2007
 */
public class DefaultKnowledgeAssessmentFactory implements KnowledgeAssessmentFactory
{

	/**
	 * @see net.ruready.business.ta.entity.property.KnowledgeAssessmentFactory#createKnowledgeAssessment(java.util.List)
	 */
	public KnowledgeAssessment createKnowledgeAssessment(final List<Question> questions)
	{
		final KnowledgeAssessment assessment = new KnowledgeAssessment();
		// iterate through levels
		for (int level=1; level <= 4; level++)
		{
			// iterate through question types
			for (QuestionType questionType : EnumSet.allOf(QuestionType.class))
			{
				List<Question> availableQuestions = getQuestionsOfTypeAndLevel(questions, questionType, level);
				assertEnoughAvailableQuestions(availableQuestions, questionType, level, 2);
				// add the Open-ended question
				assessment.addTestItem(availableQuestions.get(0), QuestionFormat.OPEN_ENDED);
				
				// add the Multiple-choice question
				Question multipleChoiceQuestion = availableQuestions.get(1);
				
				// randomize the multiple-choice responses
				final KnowledgeAssessmentItem newItem = new KnowledgeAssessmentItem(multipleChoiceQuestion, QuestionFormat.MULTIPLE_CHOICE);
				Collections.shuffle(newItem.getChoices());
				assessment.addTestItem(newItem);
			}
		}
		// randomize the resulting test items
		Collections.shuffle(assessment.getTestItems());
		return assessment;
	}

	/**
	 * @see net.ruready.business.ta.entity.property.KnowledgeAssessmentFactory#createPracticeAssessment(java.lang.Integer, java.util.List)
	 */
	public KnowledgeAssessment createPracticeAssessment(final Integer practiceLevel, final List<Question> questions)
	{
		final KnowledgeAssessment assessment = new KnowledgeAssessment();
		// iterate through question types
		for (QuestionType questionType : EnumSet.allOf(QuestionType.class))
		{
			List<Question> availableQuestions = getQuestionsOfTypeAndLevel(questions, questionType, practiceLevel);
			assertEnoughAvailableQuestions(availableQuestions, questionType, practiceLevel, 4);
			
			// add the four questions of this type
			for (int i=0; i<4; i++)
			{
				assessment.addTestItem(availableQuestions.get(i), QuestionFormat.OPEN_ENDED);
			}
		}
		// randomize the resulting test items
		Collections.shuffle(assessment.getTestItems());
		return assessment;
	}

	private List<Question> getQuestionsOfTypeAndLevel(final List<Question> questions, final QuestionType questionType, final Integer level)
	{
		final List<Question> filteredQuestions = new ArrayList<Question>(questions.size());
		for (Question question : questions)
		{
			if (question.getQuestionType() == questionType && question.getLevel() == level)
			{
				filteredQuestions.add(question);
			}
		}
		// randomize the questions before returning them
		Collections.shuffle(filteredQuestions);
		return filteredQuestions;
	}
	
	private void assertEnoughAvailableQuestions(final List<Question> questions, final QuestionType questionType, final Integer level, final Integer requiredAmount)
	{
		if (questions.size() < requiredAmount)
		{
			throw new BusinessRuleException("Cannot generate knowledge assessment. There are not enough available questions of type " + questionType + " and level " + level + ".");
		}
	}
}
