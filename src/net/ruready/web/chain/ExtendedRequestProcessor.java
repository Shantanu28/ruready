/*****************************************************************************************
 * Source File: ExtendedRequestProcessor.java
 ****************************************************************************************/

package net.ruready.web.chain;

import javax.servlet.ServletException;

import net.ruready.web.chain.filter.FilterPackage;
import net.ruready.web.chain.filter.FilterPackageParser;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.chain.ComposableRequestProcessor;
import org.apache.struts.config.ModuleConfig;

/**
 * Our own request processor that also loads all filter definitions and mappings from the
 * <code>struts-filter.xml</code> init file into an application-scope attribute.
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
 * @version Oct 8, 2007
 */
public class ExtendedRequestProcessor extends ComposableRequestProcessor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ExtendedRequestProcessor.class);

	// ========================= IMPLEMENTATION: ComposableRequestProcessor

	/**
	 * @param servlet
	 * @param aModuleConfig
	 * @throws ServletException
	 * @see org.apache.struts.chain.ComposableRequestProcessor#init(org.apache.struts.action.ActionServlet,
	 *      org.apache.struts.config.ModuleConfig)
	 */
	@Override
	public void init(ActionServlet aServlet, ModuleConfig aModuleConfig)
		throws ServletException
	{
		// Do all the Struts initializations
		super.init(aServlet, aModuleConfig);

		// Read the filter mapping and place it in the application context
		if (aServlet.getServletContext().getAttribute(
				WebAppNames.APPLICATION.ATTRIBUTE.FILTER_PACKAGE) == null)
		{
			FilterPackageParser parser = new FilterPackageParser();
			String fileName = "/struts-filter.xml";
			String absoluteFileName = Thread.currentThread().getContextClassLoader()
					.getResource(fileName).toString();
			logger.debug("Using definition file " + absoluteFileName);

			FilterPackage filterPackage = null;
			try
			{
				parser.parse(absoluteFileName);
				filterPackage = parser.getTarget();
				logger.info("Loaded filter definitions");
				logger.debug(filterPackage);
			}
			catch (Throwable e)
			{
				throw new ServletException("Failed to load filter definitions", e);
			}

			// Cache filter mapping in the application context
			aServlet.getServletContext().setAttribute(
					WebAppNames.APPLICATION.ATTRIBUTE.FILTER_PACKAGE, filterPackage);
		}
	}
}
