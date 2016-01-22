/*****************************************************************************************
 * Source File: TransferItemAction.java
 ****************************************************************************************/
package net.ruready.web.content.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.user.entity.User;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.TransferItemForm;
import net.ruready.web.content.item.imports.StrutsMainItemBD;
import net.ruready.web.content.item.util.transfer.CopyItemHelper;
import net.ruready.web.content.item.util.transfer.DeleteItemHelper;
import net.ruready.web.content.item.util.transfer.MoveItemHelper;
import net.ruready.web.content.item.util.transfer.TransferItemHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Item transfer actions: (deep) copy, move, delete, restore.
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
public class TransferItemAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TransferItemAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * Setup the necessary data and go to the editable view of the children
	 * (edit view). This is the default method.
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
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		TransferItemForm transferItemForm = (TransferItemForm) form;
		logger.debug("transferItemForm bean: " + transferItemForm);
		// The TransferItemFilter should have attached a source
		// and destination items to the request by now
		// Item itemSource = (Item)
		// request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM_SOURCE);
		// Item itemDestination = (Item)
		// request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM_DESTINATION);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		// Copy results from most source item to form
		// transferItemForm.copyFrom(itemSource);
		logger
				.debug("transferItemForm bean after copying from item: "
						+ transferItemForm);
		request.setAttribute("transferItemForm", transferItemForm);

		return mapping.findForward("transfer");
	}

	/**
	 * Setup the necessary data and go to the transfer view with source item =
	 * trash can.
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
	public ActionForward action_setup_restore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		TransferItemForm transferItemForm = (TransferItemForm) form;
		// logger.debug("transferItemForm bean: " + transferItemForm);
		User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------
		AbstractMainItemBD bdTrash = new StrutsMainItemBD(StrutsUtil
				.getWebApplicationContext(request), user);
		DefaultTrash trash = bdTrash.readUnique(DefaultTrash.class);

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		// Set source item to trash
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM_SOURCE, trash);
		request.setAttribute("transferItemForm", transferItemForm);

		return mapping.findForward("transfer");
	}

	/**
	 * This action only forwards to the main page. "Done" infers that we
	 * finished transferring items.
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
	public ActionForward action_done(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("root");
	} // done()

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
	public ActionForward action_copy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		TransferItemHelper helper = new CopyItemHelper();
		return helper.execute(mapping, form, request, response);
	} // action_copy()

	/**
	 * Move selected children from the source item to the destination item. This
	 * is only allowed if the children's parent type and the destination item
	 * type are equal.
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
	public ActionForward action_move(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		TransferItemHelper helper = new MoveItemHelper();
		return helper.execute(mapping, form, request, response);
	} // action_move()

	/**
	 * Perform item deletion operation on all selected items.
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
	public ActionForward action_delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		TransferItemHelper helper = new DeleteItemHelper();
		return helper.execute(mapping, form, request, response);
	} // action_delete()

	// ========================= PRIVATE METHODS ===========================
}
