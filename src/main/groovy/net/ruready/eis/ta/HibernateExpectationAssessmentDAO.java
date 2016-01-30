package net.ruready.eis.ta;

import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.imports.HibernateDAO;

public class HibernateExpectationAssessmentDAO extends HibernateDAO<ExpectationAssessment, Long> implements ExpectationAssessmentDAO 
{

	public HibernateExpectationAssessmentDAO(
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(ExpectationAssessment.class, sessionFactory, context);
	}
	
	public void reattach(final ExpectationAssessment assessment)
	{
		getSession().refresh(assessment);
	}

}
