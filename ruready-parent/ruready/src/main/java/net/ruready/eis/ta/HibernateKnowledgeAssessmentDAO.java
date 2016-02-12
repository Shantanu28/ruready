package net.ruready.eis.ta;

import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.imports.HibernateDAO;

public class HibernateKnowledgeAssessmentDAO extends HibernateDAO<KnowledgeAssessment, Long> implements	KnowledgeAssessmentDAO
{

	public HibernateKnowledgeAssessmentDAO(
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(KnowledgeAssessment.class, sessionFactory, context);
	}
	
	public void reattach(final KnowledgeAssessment assessment)
	{
		getSession().refresh(assessment);
	}

}
