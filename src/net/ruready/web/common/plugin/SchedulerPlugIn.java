/*****************************************************************************************
 * Source File: SchedulerPlugIn.java
 ****************************************************************************************/
/**
 * File: SchedulerPlugIn.java
 */
package net.ruready.web.common.plugin;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Quartz scheduling library - Struts plugin. Acts like a ServletListener.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 5, 2007
 */
public class SchedulerPlugIn implements PlugIn
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SchedulerPlugIn.class);

	// ========================= FIELDS ====================================

	private String startOnLoad;

	private String startupDelay;

	private Scheduler scheduler;

	// ========================= IMPLEMENTATION: Plugin ====================

	/**
	 * @param actionServlet
	 * @param moduleConfig
	 * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet,
	 *      org.apache.struts.config.ModuleConfig)
	 */
	public void init(ActionServlet actionServlet, ModuleConfig moduleConfig)
	{
		logger.info("Initializing Scheduler PlugIn for Jobs!");
		// Retrieve the ServletContext
		ServletContext ctx = actionServlet.getServletContext();
		logger.info("startOnLoad " + startOnLoad + " startupDelay " + startupDelay);
		// The Quartz Scheduler
		scheduler = null;

		// Retrieve the factory from the ServletContext.
		// It will be put there by the Quartz Servlet
		StdSchedulerFactory factory = (StdSchedulerFactory) ctx
				.getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);

		try
		{
			// Retrieve the scheduler from the factory
			scheduler = factory.getScheduler();
			// Start the scheduler in case, it isn't started yet
			if (startOnLoad != null && startOnLoad.equals(Boolean.TRUE.toString()))
			{
				logger.info("Scheduler Will start in " + startupDelay + " milliseconds!");
				// TODO: figure out how to start up with a delay. Right
				// now startupDelay has no effect.
				/*
				 * // wait the specified amount of time before // starting the process.
				 * Thread delayedScheduler = new Thread( new
				 * DelayedSchedulerStarted(scheduler, startupDelay)); // give the
				 * scheduler a name. All good code needs a name
				 * delayedScheduler.setName("Delayed_Scheduler"); // Start out scheduler
				 * delayedScheduler.start();
				 */
				scheduler.start();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * @see org.apache.struts.action.PlugIn#destroy()
	 */
	public void destroy()
	{
		logger.debug("Destroyed");
	}

	/**
	 * @return the scheduler
	 */
	public Scheduler getScheduler()
	{
		return scheduler;
	}

	/**
	 * @return the startOnLoad
	 */
	public String getStartOnLoad()
	{
		return startOnLoad;
	}

	/**
	 * @param startOnLoad
	 *            the startOnLoad to set
	 */
	public void setStartOnLoad(String startOnLoad)
	{
		this.startOnLoad = startOnLoad;
	}

	/**
	 * @return the startupDelay
	 */
	public String getStartupDelay()
	{
		return startupDelay;
	}

	/**
	 * @param startupDelay
	 *            the startupDelay to set
	 */
	public void setStartupDelay(String startupDelay)
	{
		this.startupDelay = startupDelay;
	}
}
