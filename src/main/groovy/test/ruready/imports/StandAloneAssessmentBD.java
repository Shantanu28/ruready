package test.ruready.imports;

import net.ruready.business.ta.exports.DefaultAssessmentBD;
import net.ruready.business.ta.manager.DefaultAssessmentManager;
import net.ruready.common.rl.ApplicationContext;

public class StandAloneAssessmentBD extends DefaultAssessmentBD
{

	public StandAloneAssessmentBD(final ApplicationContext context)
	{
		super(
				new DefaultAssessmentManager(
						TestResourceLocator.getInstance(), context), 
						TestResourceLocator.getInstance());
	}
}
