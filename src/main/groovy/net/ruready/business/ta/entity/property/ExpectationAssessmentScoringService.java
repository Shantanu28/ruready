package net.ruready.business.ta.entity.property;

import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.ExpectationAssessmentItem;

public interface ExpectationAssessmentScoringService
{
	public void scoreAssessment(final ExpectationAssessment assessment);
	public void scoreAssessmentItem(final ExpectationAssessmentItem assessmentItem);
}
