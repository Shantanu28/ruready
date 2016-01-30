package net.ruready.business.ta.entity.property;

import java.util.ArrayList;
import java.util.List;

import net.ruready.business.ta.entity.AssessmentAnswer;
import net.ruready.business.ta.entity.AssessmentChoice;
import net.ruready.business.ta.entity.AssessmentItemResponse;
import net.ruready.business.ta.entity.AssessmentItemResponseType;
import net.ruready.business.ta.entity.AssessmentStatus;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The scoring service for scoring knowledge assessments (online tests)
 * <p>
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Jeremy Lund
 * @version Dec 6, 2007
 */
public class DefaultKnowledgeAssessmentScoringService implements
		KnowledgeAssessmentScoringService
{

	protected final Log logger = LogFactory.getLog(getClass());

	public void scoreAssessment(final KnowledgeAssessment assessment)
	{
		Double total = 0d;
		if (assessment.getTestItems().isEmpty())
		{
			assessment.setScore(0d);
		}
		else
		{
			// score the individual items
			for (KnowledgeAssessmentItem item : assessment.getTestItems())
			{
				scoreAssessmentItem(item);
				total += item.getScore();
			}
			// score the whole assessment
			assessment.setScore(total / assessment.getTestItems().size());
		}
	}

	public void scoreAssessmentItem(final KnowledgeAssessmentItem assessmentItem)
	{
		switch (assessmentItem.getQuestionFormat())
		{
			case MULTIPLE_CHOICE:
				scoreMultipleChoiceItem(assessmentItem);
				break;
			case OPEN_ENDED:
				scoreOpenEndedItem(assessmentItem);
				break;
			default:
				throw new IllegalArgumentException("The "
						+ assessmentItem.getQuestionFormat()
						+ " question format type is not supported.");
		}
	}

	private void scoreMultipleChoiceItem(final KnowledgeAssessmentItem assessmentItem)
	{
		Boolean isCorrectAnswer = false;
		final AssessmentItemResponse lastResponse = assessmentItem.getLastResponse();

		// if there is no response or the response is empty, set the score to 0
		if (lastResponse == null || StringUtils.isBlank(lastResponse.getAnswer()))
		{
			isCorrectAnswer = false;
		}
		else
		{
			for (AssessmentChoice choice : assessmentItem.getChoices())
			{
				if (choice.isCorrect()
						&& lastResponse.getAnswer().equals(choice.getChoiceText()))
				{
					isCorrectAnswer = true;
				}
			}
		}
		if (isCorrectAnswer)
		{
			assessmentItem.setScore(1d);
		}
		else
		{
			assessmentItem.setScore(0d);
		}
		logger.debug("Multiple Choice Score: " + assessmentItem.getScore());
	}

	@SuppressWarnings("fallthrough")
	private void scoreOpenEndedItem(final KnowledgeAssessmentItem assessmentItem)
	{
		Integer hintCount = 0;
		Boolean isCorrectAnswer = false;
		// if there is no response or the response is empty, set the score to 0
		if (assessmentItem.getResponses().isEmpty())
		{
			assessmentItem.setScore(0d);
		}
		else
		{
			for (AssessmentItemResponse response : assessmentItem.getResponses())
			{
				if (response.getResponseType() == AssessmentItemResponseType.HINT)
				{
					hintCount++;
				}
				if (determineResponseScore(assessmentItem, response))
				{
					isCorrectAnswer = true;
				}
			}
			if (isCorrectAnswer)
			{
				assessmentItem.setScore(1.0d - (hintCount * 0.1d));
				if (assessmentItem.getScore() < 0)
				{
					assessmentItem.setScore(0d);
				}
			}
			else
			{
				assessmentItem.setScore(0d);
			}
		}
	}

	protected final Boolean determineResponseScore(
			final KnowledgeAssessmentItem assessmentItem,
			final AssessmentItemResponse response)
	{
		switch (response.getResponseType())
		{
			case HINT:
				response.setScore(0d);
				return false;
			case TRY:
			case ANSWER:
				for (AssessmentAnswer answer : assessmentItem.getAnswers())
				{
					if (answer.getAnswerText().equals(response.getAnswer()))
					{
						response.setScore(1d);
						return true;
					}
				}
		}
		return false;
	}

	/**
	 * Determines recommended practice level given a scored knowledge
	 * assessment.
	 * <p>
	 * The scoring algorithm is as follows:
	 * <ul>
	 * <li>Find the lowest level score that is not at least 80%, and set the
	 * level to that level</li>
	 * <li>If the student has passed (>=80%) all levels, set the level to 5</li>
	 * <li>If the recommended level is lower than the current level, return the
	 * current level</li>
	 * </ul>
	 * 
	 * @see net.ruready.business.ta.entity.property.KnowledgeAssessmentScoringService#determineRecommendedLevel(net.ruready.business.ta.entity.KnowledgeAssessment,
	 *      java.lang.Integer)
	 */
	public Integer determineRecommendedLevel(final KnowledgeAssessment assessment,
			final Integer currentLevel)
	{
		// if the assessment is not complete then return the current level
		if (assessment.getStatus() == AssessmentStatus.INCOMPLETE)
		{
			return currentLevel;
		}
		final Integer levels = 4;
		final List<Double> scores = new ArrayList<Double>(levels);
		final List<Integer> scoresCount = new ArrayList<Integer>(levels);
		for (int i = 1; i <= levels; i++)
		{
			scores.add(0d);
			scoresCount.add(0);
		}
		for (KnowledgeAssessmentItem item : assessment.getTestItems())
		{
			Integer setLevel = item.getLevel() - 1;
			scores.set(setLevel, scores.get(setLevel) + item.getScore());
			scoresCount.set(setLevel, scoresCount.get(setLevel) + 1);
		}
		Integer recommendedLevel = 5;
		for (int i = scores.size() - 1; i >= 0; i--)
		{
			// if the score is less than 80% then set to this level
			if ((scores.get(i) / scoresCount.get(i)) < 0.80d)
			{
				recommendedLevel = i + 1;
			}
		}

		// if the recommended level is less than the current level, set to the
		// current level
		if (recommendedLevel < currentLevel)
		{
			recommendedLevel = currentLevel;
		}
		return recommendedLevel;
	}

	/**
	 * Default implementation - not used in knowledge assessments
	 * 
	 * @param assessmentItem
	 * @return
	 * @see net.ruready.business.ta.entity.property.KnowledgeAssessmentScoringService#scoreLastResponse(net.ruready.business.ta.entity.KnowledgeAssessmentItem)
	 */
	public Double scoreLastResponse(final KnowledgeAssessmentItem assessmentItem)
	{
		return 0d;
	}
}
