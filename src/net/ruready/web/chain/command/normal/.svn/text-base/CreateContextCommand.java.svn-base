/*****************************************************************************************
 * Source File: CreateContextCommand.java
 ****************************************************************************************/

package net.ruready.web.chain.command.normal;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.web.chain.command.BaseCommand;
import net.ruready.web.common.imports.WebAppResourceLocator;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.chain.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.hibernate.Hibernate;

/**
 * Create a new web application context object and place it in the request scope.
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
 * @see Hibernate CaveatEmptor project - auction.persistence.SessionTransactionInterceptor
 * @author Christian Bauer
 * @version Oct 2, 2007
 */
public class CreateContextCommand extends BaseCommand
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CreateContextCommand.class);

	// ========================= CONSTRUCTORS ==============================

	// ========================= IMPLEEMNTATION: BaseActionCommand =========

	/**
	 * Create a new web application context object and place it in the request scope.
	 * 
	 * @param arg0
	 * @return
	 * @throws Exception
	 * @see org.apache.commons.chain.Command#execute(org.apache.commons.chain.Context)
	 */
	@Override
	public boolean executeCommand(Context arg0) throws Exception
	{
		// This will initialize the resource locator if it is not yet initialized
		ResourceLocator rl = WebAppResourceLocator.getInstance();
		ApplicationContext context = new WebApplicationContext(rl.getDAOFactory());

		logger.debug("Placing a new context in application scope");
		ServletActionContext saContext = (ServletActionContext) arg0;
		saContext.getRequest().setAttribute(
				WebAppNames.REQUEST.ATTRIBUTE.WEB_APPLICATION_CONTEXT, context);

		return false;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Creating web context";
	}

	// ========================= GETTERS & SETTERS =========================
}
