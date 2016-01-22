/*****************************************************************************************
 * Source File: OpenSessionCommand.java
 ****************************************************************************************/

package net.ruready.web.chain.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * A base class for all of our commands. An alternative to decorating {@link Command}
 * objects. Consists of some common debugging printouts.
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
public abstract class BaseCommand implements Command, NamedCommand
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(BaseCommand.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param command
	 */
	public BaseCommand()
	{
		super();
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Perform this action's business. This is called internally by
	 * {@link #execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}.
	 * 
	 * @param arg0
	 * @return
	 * @throws Exception
	 * @see org.apache.commons.chain.Command#execute(org.apache.commons.chain.Context)
	 * @see NamedCommand#execute(Context)
	 */
	protected abstract boolean executeCommand(Context arg0) throws Exception;

	// ========================= IMPLEEMNTATION: Action ====================

	/**
	 * @param arg0
	 * @return
	 * @throws Exception
	 * @see org.apache.commons.chain.Command#execute(org.apache.commons.chain.Context)
	 */
	public final boolean execute(Context arg0) throws Exception
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Executing command '" + this.getDescription() + " ("
					+ this.getClass().getSimpleName() + ")'");
		}
		return this.executeCommand(arg0);
	}

}
