/*****************************************************************************************
 * Source File: InitListenerMinimal.java
 ****************************************************************************************/
package net.ruready.web.common.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.web.common.imports.WebAppResourceLocator;
import net.ruready.web.user.imports.StrutsUserBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Contains functions that are run on init and shot down of the web application. Doesn't
 * run the full initialization -- faster, for debugging of components other than the
 * catalog + user components.
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
 * @version Aug 24, 2007
 */
@Deprecated
public class InitListenerMinimal implements ServletContextListener
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(InitListenerMinimal.class);

	// ========================= IMPLEMENTATION: ServletContextListener ====

	public void contextInitialized(ServletContextEvent cse)
	{
		// Initialize the resource locator before anything else
		ResourceLocator rl = WebAppResourceLocator.getInstance();

		String contextName = cse.getServletContext().getServletContextName();
		logger.info("========================== " + contextName
				+ " ==========================");
		logger.info(contextName + ": Minimal initialization begin");
		rl.info();

		// TODO: add EIS session + transaction demarcation
		// Create a context for initialization
		ApplicationContext context = new WebApplicationContext(rl.getDAOFactory());

		// Test email. Creates a new Mail session.
		// Mail.mailTest((Session) rl.getMailSession());

		// Test the RL cash by sending a
		// second email that uses a cached session
		// Mail.mailTest((Session) rl.getMailSession());

		// ServletContext application = cse.getServletContext();
		// ----------------------------------------------------------------
		// Initialize system users in database and factory.
		// Must be initialized first, because catalog items depend on them
		// -----------------------------------------------------------------
		AbstractUserBD userBD = new StrutsUserBD(context);
		SystemUserFactory.initialize(rl.getEncryptor(), userBD);

		// Turning off everything because we're currently not working on the
		// content component

		logger.info(contextName + ": Minimal initialization completed");
	}

	public void contextDestroyed(ServletContextEvent cse)
	{
		String contextName = cse.getServletContext().getServletContextName();
		logger.info(contextName + ": Shut down begin");

		// Add shut down functions here

		logger.info(contextName + ": Shut down completed");
	}
}
