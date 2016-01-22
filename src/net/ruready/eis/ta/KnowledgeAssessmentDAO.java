package net.ruready.eis.ta;

import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.common.eis.dao.DAO;

public interface KnowledgeAssessmentDAO extends DAO<KnowledgeAssessment, Long>
{
	public void reattach(final KnowledgeAssessment assessment);
}
