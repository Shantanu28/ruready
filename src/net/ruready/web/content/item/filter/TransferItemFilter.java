/*****************************************************************************************
 * Source File: TransferItemFilter.java
 ****************************************************************************************/
package net.ruready.web.content.item.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.item.imports.StrutsMainItemBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;

/**
 * Finds a pair of items (source and destination) by the request parameters "itemSourceId"
 * and "itemDestinationId". This is used by transfer pages that have a "Norton
 * Commander"-like double view.
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
 * @version Aug 11, 2007
 */
public class TransferItemFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TransferItemFilter.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions. Sub-classes
	 * should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public TransferItemFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);
	}

	// ========================= IMPLEMENTATION: FilterAction ==============

	/**
	 * Add a child item type drop-down menu data to the request.
	 * 
	 * @param saContext
	 * @param action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @see net.ruready.web.chain.filter.FilterAction#doFilter(org.apache.struts.chain.contexts.ServletActionContext,
	 *      org.apache.struts.action.Action, org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doFilter(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		// run validations + attach necessary objects to request
		// run the "mini-filters"
		findItem(WebAppNames.REQUEST.PARAM.ITEM_SOURCE_ID,
				WebAppNames.REQUEST.ATTRIBUTE.ITEM_SOURCE, request);

		findItem(WebAppNames.REQUEST.PARAM.ITEM_DESTINATION_ID,
				WebAppNames.REQUEST.ATTRIBUTE.ITEM_DESTINATION, request);
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Transfer item pre-processing";
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Find an item by id and attach it to the request; if not found, attach an error
	 * instead. This is a "mini-filter" or one step in the chain of the entire filter
	 * (which is itself part of the chain of actions processing the request).
	 * 
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 */
	private void findItem(String paramItemId, String attributeItem,
	// String attributeParents,
			HttpServletRequest request)
	{
		User user = HttpRequestUtil.findUser(request);

		// ---------------------------------------
		// Read data from request parameters
		// ---------------------------------------
		long itemId = HttpRequestUtil.getParameterAsLong(request, paramItemId);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------
		Item item = null;
		// List<Item> parents = null;

		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		// Validate that itemId corresponds to a valid item to be
		// viewed/edited
		// Look for the item to edit by the provided itemId
		if (itemId > 0)
		{
			item = bdItem.read(Item.class, itemId);
		}

		if (item == null)
		{
			// Node not found; use root node as a default
			AbstractMainItemBD bdRoot = new StrutsMainItemBD(StrutsUtil
					.getWebApplicationContext(request), user);
			bdRoot.createUnique(Root.class, CommonNames.MISC.INVALID_VALUE_INTEGER);

			// Prepare root node for processing, including initializing its
			// children
			item = bdRoot.readUnique(Root.class);
		}

		// Control children ordering
		// item.setComparatorAll(new TreeNodeComparator());

		// Debugging printouts
		logger.debug("itemId " + itemId + " item "
				+ ((item == null) ? CommonNames.MISC.NULL_TO_STRING : item.getName()));

		// ---------------------------------------
		// Success: Attach results to response
		// ---------------------------------------
		request.setAttribute(attributeItem, item);
	}
}
