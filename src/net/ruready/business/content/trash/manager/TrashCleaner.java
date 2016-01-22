/*****************************************************************************************
 * Source File: TrashCleaner.java
 ****************************************************************************************/
package net.ruready.business.content.trash.manager;

import net.ruready.business.content.trash.entity.AbstractTrash;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.eis.exports.AbstractEISBounder;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.web.common.imports.WebAppResourceLocator;
import net.ruready.web.content.item.imports.StrutsTrashBD;
import net.ruready.web.content.question.imports.StrutsEditQuestionBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Trash cleaning job to be scheduled in the Quartz scheduler.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 25, 2007
 */
public class TrashCleaner implements Job
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TrashCleaner.class);

	// ========================= IMPLEMENTATION: Job =======================

	/**
	 * Execute this job.
	 * 
	 * @param context
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context)
	{
		// Every job has it's own job detail
		// JobDetail jobDetail = context.getJobDetail();
		// The name is defined in the job definition
		// String jobName = jobDetail.getName();
		// Every job has a Job Data map for storing extra information
		// JobDataMap data = context.getJobDetail().getJobDataMap();
		// logger.info("resource locator = " + data.get("resourceLocator"));

		// Create a context for initialization
		// Initialize the resource locator before anything else
		ResourceLocator rl = WebAppResourceLocator.getInstance();
		ApplicationContext applicationContext = new WebApplicationContext(rl
				.getDAOFactory());

		logger.info("Cleaning");
		AbstractEISBounder bounder = rl.getDAOFactory();
		try
		{
			User user = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);

			// Generic item trash
			AbstractTrash bdTrash = new StrutsTrashBD(applicationContext, user);
			bdTrash.expunge();

			// Question trash
			AbstractTrash bdQuestion = new StrutsEditQuestionBD(applicationContext, user);
			bdQuestion.expunge();
		}
		catch (Throwable e)
		{
			logger.info("Trash cleaning failed: " + e.getMessage());
			bounder.rollbackTransaction();
		}
		finally
		{
			bounder.closeSession();
		}

	}
}
