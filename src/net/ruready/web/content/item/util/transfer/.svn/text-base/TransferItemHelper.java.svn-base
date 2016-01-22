/*****************************************************************************************
 * Source File: TransferItemHelper.java
 ****************************************************************************************/
package net.ruready.web.content.item.util.transfer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.content.trash.exports.AbstractTrashBD;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.TransferItemForm;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.item.imports.StrutsMainItemBD;
import net.ruready.web.content.item.imports.StrutsTrashBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * A helper for item transfer actions.
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
public class TransferItemHelper extends Action
{
	// ========================= CONSTANTS =================================

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TransferItemHelper.class);

	// ========================= FIELDS ====================================

	// ############ CONVENIENT LOCAL VARIABLES ############

	/**
	 * Source item for transfer operation.
	 */
	protected Item itemSource;

	/**
	 * Destination item for transfer operation.
	 */
	protected Item itemDestination;

	/**
	 * Edit Item business delegate.
	 */
	protected AbstractEditItemBD<Item> bdItem;

	/**
	 * Main Item business delegate.
	 */
	protected AbstractMainItemBD bdMainItem;

	/**
	 * Trash can business delegate, for deletion operations.
	 */
	protected AbstractTrashBD bdTrash;

	/**
	 * Trash can item.
	 */
	protected DefaultTrash trash;

	/**
	 * The user requesting the DAO operations.
	 */
	protected User user;

	/**
	 * Accumulates validation errors.
	 */
	protected ActionErrors errors = new ActionErrors();

	/**
	 * form bean associated with the transfer item action.
	 */
	protected TransferItemForm transferItemForm;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a transfer item action helper.
	 * 
	 * @param validateSelection
	 *            validate children selection names or not
	 */
	public TransferItemHelper()
	{
		super();
	}

	// ========================= ACTION METHODS ============================

	/**
	 * Deep-copy selected children from the source item to the destination item.
	 * This is only allowed if the children's parent type and the destination
	 * item type are equal.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return ActionForward forward action to forward the request to
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);

		// The TransferItemFilter should have attached a source
		// and destination items to the request by now
		itemSource = (Item) request
				.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM_SOURCE);
		itemDestination = (Item) request
				.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM_DESTINATION);
		user = HttpRequestUtil.findUser(request);

		// ---------------------------------------
		// Read data from view
		// ---------------------------------------
		transferItemForm = (TransferItemForm) form;
		logger.debug("transferItemForm bean: " + transferItemForm);
		logger.debug("itemSource: " + itemSource);

		// Run custom validations
		if (!preValidate(request))
		{
			// Validation failed, forward back to input page
			if (!errors.isEmpty())
			{
				this.saveErrors(request, errors);
			}
			return mapping.findForward("transfer");
		}

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		// Copy all selected children under the destination item
		bdItem = new StrutsEditItemBD<Item>(Item.class, context, user);
		bdMainItem = new StrutsMainItemBD(context, user);
		bdTrash = new StrutsTrashBD(context, user);
		trash = bdMainItem.readUnique(DefaultTrash.class);
		this.executeTransferAction(request);

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------

		// Attach the updated items and anything else needed for the view to
		// work
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM_SOURCE, itemSource);
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM_DESTINATION,
				itemDestination);

		if (errors.isEmpty())
		{
			// If no errors are reported, reset selection
			transferItemForm.reset(mapping, request);
		}
		else
		{
			saveErrors(request, errors);
		}
		request.setAttribute("transferItemForm", transferItemForm);

		// Forward back to transfer page whether there are errors or not
		return mapping.findForward("transfer");
	} // copy()

	// ========================= ABSTRACT METHODS & HOOKS ==================

	/**
	 * Validate that the children selection for item transfer before starting
	 * the transfer.
	 * 
	 * @param request
	 *            client's request object
	 * @return validation success code
	 */
	protected boolean preValidate(final HttpServletRequest request)
	{
		// Carry out no validation - a stub method
		return true;
	}

	/**
	 * Validate that a selected child for item transfer is valid for the
	 * requested operation.
	 * 
	 * @param request
	 *            client's request object
	 * @param child
	 *            child item to transfer from source to destination
	 * @return validation success code
	 */
	protected boolean validate(final HttpServletRequest request, final Item child)
	{
		// Carry out no validation - a stub method
		return true;
	}

	/**
	 * Carry out the transfer operation.
	 * 
	 * @param child
	 *            child item to transfer from source to destination
	 */
	protected void executeTransferAction(final Item child)
	{
		// Do nothing - a stub method
	}

	// ========================= UTILITY METHODS ===========================

	/**
	 * Validate that the children selection list is non-empty. Ideally,
	 * validations belong to the form bean implementation, but this is a custom
	 * validation that need run only for certain actions and certain conditions.
	 * Hence we place it in the action class.
	 * 
	 * @param request
	 *            client's request object
	 * @param errors
	 *            populated with errors if found
	 * @param selection
	 *            children selection
	 * @return success code of this validation (if false, validation failed)
	 */
	protected boolean validateEmptySelection(final HttpServletRequest request,
			final String[] selection)
	{
		boolean success = true;

		if ((selection == null) || (selection.length == 0))
		{
			// No child was selected
			success = false;
			errors.add("emptyselection",
					new ActionMessage("error.content.emptyselection"));
		}

		return success;
	}

	/**
	 * Validate that the source and destination items are different. Ideally,
	 * validations belong to the form bean implementation, but this is a custom
	 * validation that need run only for certain actions and certain conditions.
	 * Hence we place it in the action class.
	 * 
	 * @param request
	 *            client's request object
	 * @param errors
	 *            populated with errors if found
	 * @return success code of this validation (if false, validation failed)
	 */
	protected boolean validateDifferentDestination(final HttpServletRequest request)
	{
		boolean success = true;

		if (itemSource.getId() == itemDestination.getId())
		{
			// source and destination are identical
			success = false;
			errors.add("equalitems", new ActionMessage("error.content.equalitems"));
		}

		return success;
	}

	/**
	 * Validate that the destination accepts a specified type of child. Ideally,
	 * validations belong to the form bean implementation, but this is a custom
	 * validation that need run only for certain actions and certain conditions.
	 * Hence we place it in the action class.
	 * 
	 * @param request
	 *            client's request object
	 * @param child
	 *            source item to be created under destination item
	 * @return success code
	 */
	protected boolean validateDestinationChildType(final HttpServletRequest request,
			final Item child)
	{
		boolean success = true;

		Item attemptedNewChild = itemDestination.createChild(child.getIdentifier(), null,
				null);

		if (attemptedNewChild == null)
		{
			// child source's type is not an instance of the destination's child
			// type
			logger.debug("Bad child type requested: itemDestination.childType "
					+ itemDestination.getChildType() + " child.childType "
					+ child.getIdentifier());
			success = false;
			errors.add("baddestinationtype", new ActionMessage(
					"error.content.baddestinationtype", child.getName()));
		}

		return success;
	}

	/**
	 * Carry out an item transfer operation.
	 * 
	 * @param transferItemForm
	 *            form bean associated with the transfer item action
	 * @param request
	 *            client's request object
	 * @param user
	 *            user requesting the DAO operations
	 * @param errors
	 *            validation errors (changed upon returning from the method)
	 * @param itemSource
	 *            source item
	 * @param itemDestination
	 *            destination item
	 * @param transferAction
	 *            type of transfer action executed here
	 * @param validate
	 *            item type validations are carried if and only if this flag is
	 *            <code>true</code>
	 */
	private void executeTransferAction(final HttpServletRequest request)
	{
		// Move all selected children under the destination item
		for (int i = 0; i < transferItemForm.getSelectedItems().length; i++)
		{
			long childId = transferItemForm.getSelectedItemId(i);
			Item child = (Item) itemSource.findChildById(childId);
			if (child == null)
			{
				if (!errors.get(WebAppNames.KEY.CONTENT_NOT_FOUND).hasNext())
				{
					errors.add(WebAppNames.KEY.CONTENT_NOT_FOUND, new ActionMessage(
							WebAppNames.KEY.CONTENT_NOT_FOUND));
				}
			}
			else
			{
				boolean success = this.validate(request, child);
				if (success)
				{
					this.executeTransferAction(child);
				}
			}
		}
	}
}
