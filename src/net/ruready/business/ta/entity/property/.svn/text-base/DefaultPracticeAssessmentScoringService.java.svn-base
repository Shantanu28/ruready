package net.ruready.business.ta.entity.property;

import net.ruready.business.ta.entity.AssessmentItemResponse;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;
/**
 * Represents the scoring service for practice tests
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
* Please contact these numbers immediately if you receive this file without permission
* from the authors. Thank you.<br>
* -------------------------------------------------------------------------
* 
* @author Jeremy Lund
* @version Dec 6, 2007
*/
public class DefaultPracticeAssessmentScoringService extends
		DefaultKnowledgeAssessmentScoringService implements
		KnowledgeAssessmentScoringService
{
	/**
	 * Determines recommended practice level given a scored practice assessement.
	 * <p>
	 * The scoring algorithm is as follows:
	 * <ul>
	 * <li>score >= 80%: move up a level</li>
	 * <li>50% <= score < 80%: stay at the same level</li>
	 * <li>score < 50%: move down a level
	 * </ul>
	 * @see net.ruready.business.ta.entity.property.DefaultKnowledgeAssessmentScoringService#determineRecommendedLevel(net.ruready.business.ta.entity.KnowledgeAssessment, java.lang.Integer)
	 */
	@Override
	public Integer determineRecommendedLevel(final KnowledgeAssessment assessment, final Integer currentLevel)
	{
		Integer recommendedLevel = 0;
		if (assessment.getScore() >= 0.80d)
		{
			recommendedLevel = currentLevel + 1;
		}
		else if (assessment.getScore() >= 0.50d)
		{
			recommendedLevel = currentLevel;
		}
		else
		{
			recommendedLevel = currentLevel - 1;
		}
		// if the recommended level goes outside of the boundaries, then bring it back in
		if (recommendedLevel < 1)
		{
			recommendedLevel = 1;
		}
		else if (recommendedLevel > 5)
		{
			recommendedLevel = 5;
		}
		return recommendedLevel;
	}

	/**
	 * Calculates the score for the last response (always open-ended in a Practice Assessment)
	 * 
	 * @see net.ruready.business.ta.entity.property.DefaultKnowledgeAssessmentScoringService#scoreLastResponse(net.ruready.business.ta.entity.KnowledgeAssessmentItem)
	 */
	@Override
	public Double scoreLastResponse(final KnowledgeAssessmentItem assessmentItem)
	{
		final AssessmentItemResponse lastResponse = assessmentItem.getLastResponse();
		if (lastResponse != null && determineResponseScore(assessmentItem, lastResponse))
		{
			return 1d;
		}
		else
		{
			return 0d;
		}
	}
	
	
}
