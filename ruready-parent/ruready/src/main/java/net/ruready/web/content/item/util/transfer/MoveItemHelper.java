/*****************************************************************************************
 * Source File: MoveItemHelper.java
 ****************************************************************************************/

package net.ruready.web.content.item.util.transfer;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;

/**
 * Item move operation.
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
public class MoveItemHelper extends TransferItemHelper
{
	// ========================= CONSTANTS =================================

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TransferItemHelper.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a copy item helper.
	 */
	public MoveItemHelper()
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

		if (!validateDifferentDestination(request))
		{
			return false;
		}

		if (!validateChildNameExistsInDestination(request))
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
		bdItem.moveUnder(itemDestination, child);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Validate that there is no existing child with the same name as the new child under
	 * the destination item.
	 * 
	 * @param request
	 *            client's request object
	 * @param transferItemForm
	 *            form bean containing the children selection to be moved from the source
	 *            to the destination item
	 * @return success code of this validation (if false, validation failed)
	 */
	protected boolean validateChildNameExistsInDestination(
			final HttpServletRequest request)
	{
		boolean success = true;
		for (int i = 0; i < transferItemForm.getSelectedItems().length; i++)
		{
			long childId = transferItemForm.getSelectedItemId(i);
			Item child = (Item) itemSource.findChildById(childId);
			if (child == null)
			{
				continue;
			}
			String childName = child.getName();
			for (Node destChild : itemDestination.getChildren())
			{
				if (destChild.getName().equals(childName))
				{
					// source and destination are identical
					errors.add("equalitems", new ActionMessage("error.content.cannotmove",
							childName));
					if (success)
					{
						success = false;
					}
					break;
				}
			}
		}
		return success;
	}
}
