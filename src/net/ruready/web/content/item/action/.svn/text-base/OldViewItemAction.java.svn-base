/*****************************************************************************************
 * Source File: ViewItemAction.java
 ****************************************************************************************/
package net.ruready.web.content.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.EditItemForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * A setup action that provides several views for a catalog item. This action is
 * not validated because these are ready-only views.
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
 * @version Jul 29, 2007
 */
public class OldViewItemAction extends Action
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(OldViewItemAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * Go to a read-only view of an item (browse view).
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
		// ---------------------------------------
		// Read data from view, retrieve beans
		// ---------------------------------------

		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------
		switch (item.getIdentifier())
		{
			// In the new paradigm of FindItemFilter finding unique items like
			// the root
			// node, there is no longer a need to call a custom method here for
			// the root.
			// Just for the trash can.
			//
			// case ROOT:
			// {
			// this.browseRoot(mapping, form, request, response);
			// break;
			// }

			case DEFAULT_TRASH:
			{
				OldViewItemAction.browseTrash(mapping, form, request, response);
				break;
			}

			default:
			{
				OldViewItemAction.setup(mapping, form, request, response);
				break;
			}
		}

		// ---------------------------------------
		// Attach results to response;
		// forward to the appropriate view
		// ---------------------------------------
		return mapping.findForward("view");
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Common object retrieval and attachment for viewing a catalog item. This
	 * method does not forward to a view.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return ActionErrors
	 */
	@SuppressWarnings("unchecked")
	private static ActionErrors setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("[private] setup()");

		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		// The form may be of type EditItemForm or EditItemFullForm.
		ValueBean<Item> editItemForm = (ValueBean<Item>) form;
		logger.debug("editItemForm bean: " + editItemForm);
		// The FindItemFilter should have attached an item
		// to the request by now
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		logger.debug("item.tags = " + item.getTags());
		// User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		// Copy results from most updated entity to form
		editItemForm.copyFrom(item, StrutsUtil.getWebApplicationContext(request));
		logger.debug("editItemForm bean after copying from item: " + editItemForm);

		return null;
	}

	/**
	 * Go to a non-editable view of the trash can. Does not require the
	 * FindItemFilter prior to this action.
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
	public static void browseTrash(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("[private] browseTrash()");

		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		EditItemForm editItemForm = null;
		if ((form != null) && (form instanceof EditItemForm))
		{
			editItemForm = (EditItemForm) form;
			logger.debug("editItemForm bean: " + editItemForm);
		}
		// The FindItemFilter should have attached an item
		// to the request by now. This should be the trash can.
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		// User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		// Copy results from most updated entity to form
		if (editItemForm != null)
		{
			editItemForm.copyFrom(item, context);
			logger.debug("editItemForm bean after copying from item: " + editItemForm);
		}
	}
}
