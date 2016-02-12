/*****************************************************************************************
 * Source File: SearchItemAction.java
 ****************************************************************************************/
package net.ruready.web.content.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.SearchItemForm;
import net.ruready.web.content.item.imports.StrutsEditItemBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.criterion.Example;

/**
 * An action for finding catalog items by name.
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
public class SearchItemAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SearchItemAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * The default action taken when the method parameter is not present in the
	 * request. Performs the setup action.
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
		return mapping.findForward("view");
	}

	/**
	 * Reset the form and remove the result set from the request.
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
	public ActionForward action_setup_reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		SearchItemForm searchItemForm = (SearchItemForm) form;
		searchItemForm.reset();
		request.setAttribute("searchItemForm", searchItemForm);
		return mapping.findForward("view");
	}

	/**
	 * Process search results and forward to a view of the search results.
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
	public ActionForward action_search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		SearchItemForm searchItemForm = (SearchItemForm) form;
		logger.debug("searchItemForm bean: " + searchItemForm);
		User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// Copy data: form -> entity (including children)
		Item item = new Item(null, null);

		searchItemForm.copyTo(item, context);
		logger.debug("Item after copying from form = " + item);

		// Search for all items that match the "item" example
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		// List<Item> searchItemResult = bdItem.findByExample(item);

		// Customize search criteria to include "LIKE"
		item.setName("%" + item.getName() + "%");
		Example criterion = Example.create(item);
		criterion.excludeZeroes();
		criterion.excludeProperty("parent");
		criterion.excludeProperty("comment");
		criterion.excludeProperty("version");
		criterion.enableLike();
		List<Item> searchItemResult = bdItem.findByExampleObject(Item.class, criterion);

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		// Attach the root node and attach it to the request.
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, item);
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.SEARCH_ITEM_RESULT,
				searchItemResult);

		// Copy results from most updated entity to form
		// searchItemForm.copyFrom(item);
		logger.debug("searchItemForm bean after copying from item: " + searchItemForm);
		request.setAttribute("searchItemForm", searchItemForm);

		return mapping.findForward("view");
	}
}
