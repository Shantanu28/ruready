/*****************************************************************************************
 * Source File: DeleteItemAction.java
 ****************************************************************************************/
package net.ruready.web.content.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.content.trash.exports.AbstractTrashBD;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.TransferItemForm;
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
 * Hard-deleting items from the database.
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
public class DeleteItemAction extends Action
{
	// ========================= CONSTANTS =================================

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DeleteItemAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * Perform item deletion operation on all selected items. This is the
	 * default method.
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
		// ActionErrors errors = new ActionErrors();

		// @@@@@@@@@@@@@@@@@@ Testing the system error page
		// @@@@@@@@@@@@@@@@@@@@@@
		// Item badItem = null;
		// logger.error(badItem.getName());
		// @@@@@@@@@@@@@@@@@@ Testing the system error page
		// @@@@@@@@@@@@@@@@@@@@@@

		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);

		// ---------------------------------------
		// Read data from view
		// ---------------------------------------
		TransferItemForm transferItemForm = (TransferItemForm) form;
		AbstractMainItemBD bdItem = new StrutsMainItemBD(context, user);
		DefaultTrash trash = bdItem.readUnique(DefaultTrash.class);

		// Run custom validations
		ActionErrors errors = new ActionErrors();
		if (!validateEmptySelection(transferItemForm.getSelectedItems(), errors))
		{
			// Attach the updated items and anything else needed for the view to
			// work
			request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, trash);
			request.setAttribute("transferItemForm", transferItemForm);

			// Validation failed, forward back to input page
			OldViewItemAction.browseTrash(mapping, form, request, response);
			return mapping.findForward("view");
		}

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		// Move all selected children under the trash can; no validations on
		// child names
		AbstractTrashBD bdTrash = new StrutsTrashBD(StrutsUtil
				.getWebApplicationContext(request), user);

		// Deleted all selected items
		for (int i = 0; i < transferItemForm.getSelectedItems().length; i++)
		{
			long childId = transferItemForm.getSelectedItemId(i);
			Item child = (Item) trash.findChildById(childId);
			if (child != null)
			{
				bdTrash.hardDelete(trash, child);
			}
		}

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------

		// Attach the updated items and anything else needed for the view to
		// work
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, trash);

		if (errors.isEmpty())
		{
			// If no errors are reported, reset selection
			transferItemForm.reset(mapping, request);
		}
		request.setAttribute("transferItemForm", transferItemForm);

		// Forward back to transfer page whether there are errors or not
		OldViewItemAction.browseTrash(mapping, form, request, response);
		return mapping.findForward("view");
	} // delete()

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Validate that the children selection list is non-empty. Ideally,
	 * validations belong to the form bean implementation, but this is a custom
	 * validation that need run only for certain actions and certain conditions.
	 * Hence we place it in the action class.
	 * 
	 * @todo re-use {@link TransferItemAction}'s corresponding method instead
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param selection
	 * @return success code of this validation (if false, validation failed)
	 */
	private boolean validateEmptySelection(final String[] selection,
			final ActionErrors errors)
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
}
