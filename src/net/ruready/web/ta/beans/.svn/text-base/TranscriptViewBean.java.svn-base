package net.ruready.web.ta.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.ruready.business.ta.entity.AssessmentStatus;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.entity.TranscriptProgressStatus;

public class TranscriptViewBean implements Serializable
{
	private static final long serialVersionUID = -1983653422276624705L;

	// passing score = 80%
	private static final Double passingScore = 0.80d;
	
	private StudentTranscript transcript;

	public TranscriptViewBean() { }
	
	public TranscriptViewBean(final StudentTranscript transcript)
	{
		setTranscript(transcript);
	}
	
	public StudentTranscript getTranscript()
	{
		return transcript;
	}

	public void setTranscript(final StudentTranscript transcript)
	{
		this.transcript = transcript;
		initializeViewBean();
	}
	
	/**
	 * Stop-gap method to ensure that we don't get lazy initialization errors
	 */
	private void initializeViewBean()
	{
		getIsExpectationAssessmentAvailable();
		getIsKnowledgeAssessmentAvailable();
		getHasMasteredPracticeLevel();
		getPracticeScores();
	}
	
	/**
	 * Determines whether the expectation assessment link should be made available.
	 * 
	 * This is a convenience method for use in JSP code.
	 * 
	 * @return true if the expectation assessment link is available, otherwise false.
	 */
	public Boolean getIsExpectationAssessmentAvailable()
	{
		return getTranscript().canTakeExpectationAssessment();
	}
	
	/**
	 * Determines whether the transcript has at least one expectation assessment
	 * 
	 * This is a convenience method for use in JSP code.
	 * 
	 * @return true if there is at least one assessment, otherwise false.
	 */
	public Boolean getHasExpectationAssessment()
	{
		return getTranscript().hasExpectationAssessment();
	}
	
	/**
	 * Determines whether the knowledge assessment link should be made available.
	 * 
	 * This is a convenience method for use in JSP code.
	 * 
	 * @return true if the knowledge assessment link is available, otherwise false.
	 */
	public Boolean getIsKnowledgeAssessmentAvailable()
	{
		return getTranscript().canTakeKnowledgeAssessment();
	}
	
	/**
	 * Determines whether the transcript has at least one knowledge assessment
	 * 
	 * This is a convenience method for use in JSP code.
	 * 
	 * @return true if there is at least one assessment, otherwise false.
	 */
	public Boolean getHasKnowledgeAssessment()
	{
		return getTranscript().hasKnowledgeAssessment();
	}
	
	public List<Double> getPracticeScores()
	{
		final List<Double> scores = new ArrayList<Double>(4);
		scores.add(getScore(getTranscript().getCurrentLevel1Practice()));
		scores.add(getScore(getTranscript().getCurrentLevel2Practice()));
		scores.add(getScore(getTranscript().getCurrentLevel3Practice()));
		scores.add(getScore(getTranscript().getCurrentLevel4Practice()));
		return scores;
	}
	
	public List<Boolean> getHasMasteredPracticeLevel()
	{
		final List<Boolean> mastery = new ArrayList<Boolean>(4);
		for (Double score : getPracticeScores())
		{
			if (score != null)
			{
				mastery.add(score > passingScore);
			}
			else
			{
				mastery.add(false);
			}
			
		}
		return mastery;
	}
	
	public List<Boolean> getHasOpenPractice()
	{
		final List<Boolean> openPractices = new ArrayList<Boolean>(4);
		openPractices.add(hasOpenPractice(getTranscript().getCurrentLevel1Practice()));
		openPractices.add(hasOpenPractice(getTranscript().getCurrentLevel2Practice()));
		openPractices.add(hasOpenPractice(getTranscript().getCurrentLevel3Practice()));
		openPractices.add(hasOpenPractice(getTranscript().getCurrentLevel4Practice()));
		return openPractices;
	}
	
	public Boolean getHasPassedCourse()
	{
		return (getTranscript().getProgressStatus() == TranscriptProgressStatus.PASSED);
	}
	
	/**
	 * Returns the student's best score on the course
	 * 
	 * The score is a combination of the expectation assessment (20%) and knowledge assessment (80%)
	 * 
	 * @return the student's best score on the course
	 */
	public Double getBestScore()
	{
		return getTranscript().getBestAssessmentScore();
	}
	
	/**
	 * Returns whether the most recent assessments are a complete pair (knowledge assessment and an expectation assessment)
	 * @return true if they are a pair, false otherwise
	 */
	public Boolean getIsCurrentAssessmentAPair()
	{
		return getTranscript().isCurrentAssessmentAPair();
	}
	
	private Double getScore(final KnowledgeAssessment assessment)
	{
		if (assessment == null)
		{
			return null;
		}
		return assessment.getScore();
	}
	
	private Boolean hasOpenPractice(final KnowledgeAssessment assessment)
	{
		if (assessment == null)
		{
			return false;
		}
		return (assessment.getStatus() == AssessmentStatus.INCOMPLETE);
	}
}
