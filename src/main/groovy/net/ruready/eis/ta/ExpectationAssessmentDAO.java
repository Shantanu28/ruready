package net.ruready.eis.ta;

import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.common.eis.dao.DAO;

public interface ExpectationAssessmentDAO extends DAO<ExpectationAssessment, Long>
{
	public void reattach(final ExpectationAssessment assessment);
}
