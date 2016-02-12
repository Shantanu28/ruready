/*****************************************************************************************
 * Source File: CopyItemHelper.java
 ****************************************************************************************/

package net.ruready.web.content.item.util.transfer;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.content.item.entity.Item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Item copy operation.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 22, 2007
 */
public class CopyItemHelper extends TransferItemHelper
{
	// ========================= CONSTANTS =================================

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TransferItemHelper.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a copy item helper.
	 */
	public CopyItemHelper()
	{
		super();
	}

	// ========================= IMPLEMENTATION: TransferItemHelper ========

	/**
	 * @param request
	 * @return
	 * @see net.ruready.web.content.item.util.transfer.TransferItemHelper#preValidate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean preValidate(HttpServletRequest request)
	{
		if (!validateEmptySelection(request, transferItemForm.getSelectedItems()))
		{
			return false;
		}
		return true;
	}

	/**
	 * @param request
	 * @param child
	 * @return
	 * @see net.ruready.web.content.item.util.transfer.TransferItemHelper#validate(javax.servlet.http.HttpServletRequest,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	@Override
	protected boolean validate(HttpServletRequest request, Item child)
	{
		if (!validateDestinationChildType(request, child))
		{
			return false;
		}

		if (!ItemValidationUtil.validateChildCreation(itemDestination, child, errors))
		{
			return false;
		}

		return true;
	}

	/**
	 * @param child
	 * @see net.ruready.web.content.item.util.transfer.TransferItemHelper#executeTransferAction(net.ruready.business.content.item.entity.Item)
	 */
	@Override
	protected void executeTransferAction(Item child)
	{
		bdItem.copyUnder(itemDestination, child);
	}

}
