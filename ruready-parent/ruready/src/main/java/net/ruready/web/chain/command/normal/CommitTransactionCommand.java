/*****************************************************************************************
 * Source File: OpenSessionCommand.java
 ****************************************************************************************/

package net.ruready.web.chain.command.normal;

import net.ruready.common.eis.exception.EISException;
import net.ruready.common.eis.exception.RecordExistsException;
import net.ruready.common.eis.exception.RecordNotFoundException;
import net.ruready.common.eis.exception.StaleRecordException;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.web.chain.command.BaseCommand;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.chain.Context;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;
import org.hibernate.exception.ConstraintViolationException;

/**
 * This command commits and ends a Hibernate transaction.
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

public class CommitTransactionCommand extends BaseCommand
{
	// ========================= IMPLEEMNTATION: BaseActionCommand =========

	/**
	 * Commit (and end) a Hibernate transaction.
	 * 
	 * @param arg0
	 * @return
	 * @throws Exception
	 * @see org.apache.commons.chain.Command#execute(org.apache.commons.chain.Context)
	 */
	@Override
	public boolean executeCommand(Context arg0) throws Exception
	{
		ResourceLocator rl = WebAppResourceLocator.getInstance();
		try
		{
			rl.getDAOFactory().commitTransaction();
		}
		catch (ConstraintViolationException e)
		{
			throw new RecordExistsException("Record already exists in database", e, e
					.getConstraintName());
		}
		catch (StaleStateException e)
		{
			throw new StaleRecordException(null, null, null, e, "Version conflict");
		}
		catch (ObjectNotFoundException e)
		{
			throw new RecordNotFoundException(e.getIdentifier(), e,
					"Record was not found in database");
		}
		catch (HibernateException e)
		{
			throw new EISException("Persistence layer error", e);
		}
		return false;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.BaseCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Commit a transaction";
	}
}
