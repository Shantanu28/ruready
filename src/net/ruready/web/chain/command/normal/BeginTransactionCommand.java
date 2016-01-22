/*****************************************************************************************
 * Source File: OpenSessionCommand.java
 ****************************************************************************************/

package net.ruready.web.chain.command.normal;

import net.ruready.common.rl.ResourceLocator;
import net.ruready.web.chain.command.BaseCommand;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.chain.Context;

/**
 * This command begins a Hibernate transaction.
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

public class BeginTransactionCommand extends BaseCommand
{
	// ========================= IMPLEEMNTATION: BaseActionCommand =========

	/**
	 * Begin a Hibernate transaction.
	 * 
	 * @param arg0
	 * @return
	 * @throws Exception
	 * @see org.apache.commons.chain.Command#execute(org.apache.commons.chain.Context)
	 */
	@Override
	protected boolean executeCommand(Context arg0) throws Exception
	{
		ResourceLocator rl = WebAppResourceLocator.getInstance();
		rl.getDAOFactory().beginTransaction();
		return false;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.BaseCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Begin a transaction";
	}

}
