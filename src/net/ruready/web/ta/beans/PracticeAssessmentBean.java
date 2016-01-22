package net.ruready.web.ta.beans;

import net.ruready.business.ta.entity.AssessmentItem;
import net.ruready.business.ta.entity.AssessmentItemStatus;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;

public class PracticeAssessmentBean extends KnowledgeAssessmentBean
{
	private static final long serialVersionUID = -6171301938176459297L;
	
	private Integer practiceLevel;
	private Integer currentHintNumber = 0;
	private Double lastResponseScore = null;
	
	/**
	 * Forwards the assessment pointer to the next question to answer
	 * @return true if there is a question to continue to (the assessment is not finished), false if they're finished
	 */
	public Boolean continueTest()
	{
		AssessmentItem item = getCurrentQuestion();
		while (item != null && item.getStatus() == AssessmentItemStatus.ANSWERED)
		{
			item = getNextQuestion();
		}
		return (item != null);
	}
	
	@Override
	public KnowledgeAssessmentItem getNextQuestion()
	{
		// clear the current hint number out for the next question
		setCurrentHintNumber(0);
		// clear out the last response score
		setLastResponseScore(null);
		return super.getNextQuestion();
	}
	
	public Integer getPracticeLevel()
	{
		return practiceLevel;
	}

	public void setPracticeLevel(final Integer practiceLevel)
	{
		this.practiceLevel = practiceLevel;
	}

	/**
	 * Returns the currently-selected hint number
	 * 
	 * @return the currently-selected hint number
	 */
	public Integer getCurrentHintNumber()
	{
		return currentHintNumber;
	}

	public void setCurrentHintNumber(final Integer currentHintNumber)
	{
		this.currentHintNumber = currentHintNumber;
	}

	/**
	 * Returns the currently-selected hint
	 * 
	 * @return the currently-selected hint
	 */
	public String getCurrentHint()
	{
		if (currentHintNumber == 0)
		{
			return null;
		}
		return getCurrentQuestion().getHints().get(getCurrentHintNumber() - 1).getHint1Text();
	}

	/**
	 * Returns the max hint level that is currently available to the user
	 * 
	 * @return the max hint level that is currently available to the user
	 */
	public Integer getAvailableHintLevel()
	{
		return getCurrentQuestion().getHintRequestCount();
	}
	
	/**
	 * Returns the total number of hints available for the question
	 * 
	 * @return the total number of hints available for the question
	 */
	public Integer getMaxHintLevel()
	{
		return getCurrentQuestion().getHints().size();
	}
	
	public Double getLastResponseScore()
	{
		return lastResponseScore;
	}

	public void setLastResponseScore(final Double lastResponseScore)
	{
		this.lastResponseScore = lastResponseScore;
	}
}
