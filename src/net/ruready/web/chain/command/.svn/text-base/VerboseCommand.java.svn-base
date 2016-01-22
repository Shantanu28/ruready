/*****************************************************************************************
 * Source File: OpenSessionCommand.java
 ****************************************************************************************/

package net.ruready.web.chain.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A decorator of a {@link Command} object for debugging printouts. The problem is that
 * common chains does not seem to support command factories, so this decorator cannot be
 * flexibly used at this time like in other verbose factories in our applications.
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
public class VerboseCommand implements Command
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(VerboseCommand.class);

	// ========================= FIELDS ====================================

	/**
	 * The object to be decorated.
	 */
	private final Command command;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param command
	 */
	public VerboseCommand(final Command command)
	{
		super();
		this.command = command;
	}

	// ========================= IMPLEEMNTATION: Command ===================

	/**
	 * Begin a Hibernate transaction.
	 * 
	 * @param arg0
	 * @return
	 * @throws Exception
	 * @see org.apache.commons.chain.Command#execute(org.apache.commons.chain.Context)
	 */
	public boolean execute(Context arg0) throws Exception
	{
		logger.debug("Executing command '" + command.getClass().getSimpleName() + "'");
		return command.execute(arg0);
	}

}
