/*****************************************************************************************
 * Source File: EditItemFullAction.java
 ****************************************************************************************/
package net.ruready.web.content.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.EditItemFullForm;
import net.ruready.web.content.item.util.edit.EditItemUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CMS item & children full editing actions: updating, adding and deleting
 * categories of the item. This action is validated.
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
 * @version Jul 30, 2007
 */
public class EditItemFullAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EditItemFullAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * Setup the necessary data and go to the editable view of the children
	 * (edit view).
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
		setupInit(request, form);

		// -------------------------------------------
		// Read data from form
		// -------------------------------------------

		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		EditItemFullForm editItemFullForm = (EditItemFullForm) form;
		// The FindItemFilter should have attached an item
		// to the request by now
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		// User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// Copy results from most updated entity to form. If form doesn't
		editItemFullForm.copyFrom(item, context);
		if (logger.isDebugEnabled())
		{
			logger.debug("editItemFullForm bean after copying from item: "
					+ editItemFullForm);
		}

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		return mapping.findForward("edit");
	} // unspecified()

	/**
	 * This action cancels the editing. It forwards to the appropriate view
	 * without changing the item.
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
	public ActionForward action_setup_cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return getExitForward(mapping, form, request);
	} // cancelled()

	/**
	 * Save editing changes on a catalog item and display them in the editing
	 * view.
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
	public ActionForward action_save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		ActionErrors errors = EditItemUtil.editItemFull(mapping, form, request, response);
		saveErrors(request, errors);

		// Always go back to the edit view
		return mapping.findForward("edit");
	}

	/**
	 * Perform editing changes on a catalog item and go to the previous view
	 * (done editing).
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
		ActionErrors errors = EditItemUtil.editItemFull(mapping, form, request, response);

		// Decide where to forward
		if (!errors.isEmpty())
		{
			// There are errors, forward to the input path
			saveErrors(request, errors);
			return mapping.findForward("edit");
		}
		else
		{
			return getExitForward(mapping, form, request);
		}
	}

	/**
	 * Resolve editing conflicts using user's local copy and save to database.
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
	public ActionForward action_resolveUsingMine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// ---------------------------------------
		// Set form version to stored version
		// ---------------------------------------
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		EditItemFullForm editItemFullForm = (EditItemFullForm) form;
		editItemFullForm.copyVersionFrom(item);

		ActionErrors errors = EditItemUtil.editItemFull(mapping, form, request, response);
		saveErrors(request, errors);

		// Always go back to the edit view
		return mapping.findForward("edit");
	}

	/**
	 * Resolve editing conflicts using database-stored copy. Like a cancel
	 * operation, but goes back to the edit view.
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
	public ActionForward action_resolveUsingTheirs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// ---------------------------------------
		// Copy stored entity -> form
		// ---------------------------------------
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		// User user = HttpRequestUtil.findUser(request);
		EditItemFullForm editItemFullForm = (EditItemFullForm) form;
		editItemFullForm.copyFrom(item, context);

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Prepare miscellaneous objects for the view
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------

		// Attach the updated item and anything needed for the view to work
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, item);

		// Always go back to the edit view
		return mapping.findForward("edit");
	}

	// ========================= PRIVATE METHODS ===========================

	// ========================= HOOKS =====================================

	/**
	 * A hook stub method that executes setup-related custom activities.
	 * 
	 * @param request
	 * @param form
	 */
	protected void setupInit(HttpServletRequest request, ActionForm form)
	{
		// Custom forward logic. If we are coming from the search screen, we'll
		// want to return to the search screen after our edit session. this
		// makes that
		// possible. Set session token only if a request parameter is found; if
		// not, leave the current session token intact.
		if (request.getParameter(WebAppNames.SESSION.ATTRIBUTE.TOKEN.CUSTOM_FORWARD) != null)
		{
			HttpRequestUtil.setSessionToken(request,
					WebAppNames.SESSION.ATTRIBUTE.TOKEN.CUSTOM_FORWARD);
		}
	}

	/**
	 * A hook stub method for
	 * {@link #unspecified(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}'s
	 * ActionForward return value.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object Struts action mapping
	 * @param form
	 *            form bean
	 * @param request
	 *            client's request object
	 * @return {@link #unspecified(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}'s
	 *         ActionForward return value
	 */
	protected ActionForward getExitForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request)
	{
		return mapping.findForward("view");
	}
}
