package test.ruready.imports;

import net.ruready.business.ta.exports.DefaultStudentTranscriptBD;
import net.ruready.business.ta.manager.DefaultStudentTranscriptManager;
import net.ruready.common.rl.ApplicationContext;

public class StandAloneStudentTranscriptBD extends DefaultStudentTranscriptBD
{

	public StandAloneStudentTranscriptBD(final ApplicationContext context)
	{
		super(
				new DefaultStudentTranscriptManager(
						TestResourceLocator.getInstance(), context), 
						TestResourceLocator.getInstance());
	}
}
