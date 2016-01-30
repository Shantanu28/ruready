package net.ruready.business.ta.entity.property;

import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.ExpectationAssessmentItem;

public class DefaultExpectationAssessmentScoringService implements
		ExpectationAssessmentScoringService
{

	public void scoreAssessment(final ExpectationAssessment assessment)
	{
		Double total = 0d;
		if (assessment.getTestItems().isEmpty())
		{
			assessment.setScore(0d);
		}
		else
		{
			// score the individual items
			for (ExpectationAssessmentItem item : assessment.getTestItems())
			{
				scoreAssessmentItem(item);
				total += item.getScore();
			}
			// score the whole assessment
			assessment.setScore(total / assessment.getTestItems().size());
		}
	}

	public void scoreAssessmentItem(final ExpectationAssessmentItem assessmentItem)
	{
		final Double distance = Math.abs(assessmentItem.getValue() - assessmentItem.getBaseline());
		assessmentItem.setScore((5 - distance)/ 5);
	}
}
