package net.ruready.business.ta.entity.property;

import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;

public interface KnowledgeAssessmentScoringService
{
	public void scoreAssessment(final KnowledgeAssessment assessment);
	public void scoreAssessmentItem(final KnowledgeAssessmentItem assessmentItem);

	public Double scoreLastResponse(final KnowledgeAssessmentItem assessmentItem);
	public Integer determineRecommendedLevel(final KnowledgeAssessment assessment, final Integer currentLevel);
}
