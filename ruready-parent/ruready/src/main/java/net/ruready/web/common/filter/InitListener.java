/*****************************************************************************************
 * Source File: InitListener.java
 ****************************************************************************************/
package net.ruready.web.common.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.content.trash.manager.TrashCleaner;
import net.ruready.business.content.world.entity.World;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.common.eis.exports.AbstractEISBounder;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.eis.common.tree.NodeDAO;
import net.ruready.web.common.imports.WebAppResourceLocator;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.item.imports.StrutsMainItemBD;
import net.ruready.web.user.imports.StrutsUserBD;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Contains functions that are run on initialization and shut down of the web
 * application.
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
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 25, 2007
 */
public class InitListener implements ServletContextListener
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(InitListener.class);

	// ========================= IMPLEMENTATION: ServletContextListener ====

	public void contextInitialized(ServletContextEvent cse)
	{
		// Initialize the resource locator before anything else
		ResourceLocator rl = WebAppResourceLocator.getInstance();

		String contextName = cse.getServletContext().getServletContextName();
		logger.info("========================== " + contextName
				+ " ==========================");
		logger.info(contextName + ": Full initialization begin");
		rl.info();

//		logger.info("Hibernate Core        Version " + Environment.VERSION);
//		logger.info("Hibernate Annotations Version " + Version.VERSION);

		// Create a context for initialization
		ApplicationContext context = new WebApplicationContext(rl.getDAOFactory());

		// Test email. Creates a new Mail session.
		// Mail.mailTest((Session) rl.getMailSession());

		// Test the RL cash by sending a
		// second email that uses a cached session
		// Mail.mailTest((Session) rl.getMailSession());

		// ServletContext application = cse.getServletContext();

		AbstractEISBounder bounder = rl.getDAOFactory();
		try
		{
			// ----------------------------------------------------------------
			// Initialize system users in database and factory.
			// Must be initialized first, because catalog items depend on them
			// -----------------------------------------------------------------
			AbstractUserBD userBD = new StrutsUserBD(context);
			SystemUserFactory.initialize(rl.getEncryptor(), userBD);
			User user = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
			AbstractMainItemBD bdMainItem = new StrutsMainItemBD(context, user);

			// ---------------------------------------
			// Initialize root node
			// ---------------------------------------

			bdMainItem.createUnique(Root.class, CommonNames.MISC.INVALID_VALUE_INTEGER);
			// Prepare root node for processing, including initializing its
			// children
			Item root = bdMainItem.readUnique(Root.class);

			// ---------------------------------------
			// Initialize trash can
			// ---------------------------------------
			bdMainItem.createUnique(DefaultTrash.class, root.getId());

			// ---------------------------------------
			// Initialize unique and base objects
			// ---------------------------------------

			createDemoContent(context, root);

			// ---------------------------------------
			// Schedule regular trash can cleaning
			// ---------------------------------------
//			JobDetail job = new JobDetail("job1", "group1", TrashCleaner.class);
                        JobDetail job = JobBuilder.newJob(TrashCleaner.class)
                                            .withIdentity("job1", "group1").build();                        
			// JobDataMap data = new JobDataMap();
			// data.put("resourceLocator", rl);
			// job.setJobDataMap(data);

//			SimpleTrigger trigger = new SimpleTrigger("Trash Cleaning", user
//					.getFirstName());
                        
			long interval = rl
					.getPropertyAsLong(WebAppNames.RESOURCE_LOCATOR.PROPERTY.TRASH_CLEANING_INTERVAL)
					* CommonNames.TIME.MINS_TO_MS;                        
                        Trigger trigger = TriggerBuilder
                                                .newTrigger()
                                                .withIdentity("Trash Cleaning", user.getFirstName())
                                                .withSchedule(
                                                        SimpleScheduleBuilder.simpleSchedule()
                                                                .withIntervalInSeconds((int) interval).repeatForever())                                
                                                .build();                        
//			trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
//
//			trigger.setRepeatInterval(interval); // milliseconds

			// Schedule the job with the trigger
			try
			{
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler scheduler = sf.getScheduler();
				scheduler.scheduleJob(job, trigger);
			}
			catch (SchedulerException e)
			{
				logger.error("Could not schedule trash cleaning. " + e.toString());
			}

			logger.info(contextName + ": Full initialization completed");
		}
		catch (Throwable e)
		{
			logger.info(contextName + ": Full initialization failed: " + e.toString());
			bounder.rollbackTransaction();
		}
		finally
		{
			bounder.closeSession();
		}

	}

	public void contextDestroyed(ServletContextEvent cse)
	{
		String contextName = cse.getServletContext().getServletContextName();
		logger.info(contextName + ": Shut down begin");

		// --------------------------------------
		// Add shut down functions here
		// --------------------------------------
		// Initialize the resource locator before anything else
		ResourceLocator rl = WebAppResourceLocator.getInstance();
		rl.tearDown();

		logger.info(contextName + ": Shut down end");
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * @param context
	 * @param root
	 */
	private void createDemoContent(final ApplicationContext context, final Item root)
	{
		// Flip flag to true to load world data from a file. Caution: very slow.
		// createItem(context, root, ContentNames.UNIQUE_NAME.WORLD,
		// World.class, true);
		// Dependencies: none
		/* MainItem world = */createItem(context, root, ContentNames.UNIQUE_NAME.WORLD,
				World.class, false);

		// Dependencies: none
		MainItem tagCabinet = createItem(context, root,
				ContentNames.UNIQUE_NAME.TAG_CABINET, TagCabinet.class, false);

		// Dependencies: TagCabinet
		/* MainItem catalog = */createItem(context, root,
				ContentNames.BASE_NAME.CATALOG, Catalog.class, false, tagCabinet);
	}

	/**
	 * Create a base item
	 * 
	 * @param context
	 * @param root
	 * @param name
	 * @param type
	 *            main item type
	 * @param depenencies
	 *            main items that this main item depend on. See the constructors
	 *            of the individual demo creator classes in this package.
	 * @return the base main item
	 * @return created main item
	 */
	private MainItem createItem(final ApplicationContext context, final Item root,
			final String name, final Class<? extends MainItem> type,
			final boolean fromFile, final MainItem... depenencies)
	{
		final AbstractMainItemBD bdMainItem = new StrutsMainItemBD(context,
				getSystemUser());
		final AbstractEditItemBD<MainItem> bdEditItem = new StrutsEditItemBD<MainItem>(
				MainItem.class, context, getSystemUser());
		MainItem item = bdEditItem.findByUniqueProperty(MainItem.class, NodeDAO.NAME,
				name);
		if (item == null)
		{
			item = bdMainItem.createBase(type, root.getId(), name, fromFile, depenencies);
			if (item != null)
			{
				bdEditItem.updateAll(item);
			}
		}
		return item;
	}

	/**
	 * @return
	 */
	private User getSystemUser()
	{
		return SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
	}
}
