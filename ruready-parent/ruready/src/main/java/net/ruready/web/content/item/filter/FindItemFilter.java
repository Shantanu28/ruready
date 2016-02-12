/*****************************************************************************************
 * Source File: FindItemFilter.java
 ****************************************************************************************/
package net.ruready.web.content.item.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.util.exports.AbstractItemUtilBD;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.util.EnumUtil;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsItemUtilBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;

/**
 * Finds an item by the request parameters
 * <code>WebAppNames.REQUEST.PARAM.ITEM_ID</code> and
 * <code>WebAppNames.REQUEST.PARAM.ITEM_TYPE</code>. There are three cases:
 * <p>
 * 1. The ID is non-negative. We search for the item in the database and load it
 * via the DAO corresponding to its type.<br>
 * 2. The ID is not found in the request, or equals
 * <code>CommonNames.MISC.INVALID_VALUE_INTEGER</code>. We try to find the
 * unique item of the type parameter (e.g. the root node or the trash can) in
 * the database, if this is a unique type.<br>
 * 3. The ID is not found in the request, or equals
 * <code>CommonNames.MISC.INVALID_VALUE_INTEGER</code>, and 2 failed. Throw
 * an exception.<br>
 * <p>
 * If the item is found, we attach the item to the request and continue the
 * processing chain. Otherwise, we forward the request to an error page.
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
 * @version Jul 28, 2007
 */
public class FindItemFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FindItemFilter.class);

	// ========================= FIELDS ====================================

	/**
	 * Hard-coded item type (optional).
	 */
	private String hardCodedItemType;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions.
	 * Sub-classes should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public FindItemFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);
	}

	// ========================= IMPLEMENTATION: FilterAction ==============

	/**
	 * Filter a URL: decide whether to increment the hit counter; if yes, update
	 * the hit count in the request and in the database.
	 * 
	 * @param saContext
	 * @param action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @see net.ruready.web.chain.filter.FilterAction#doFilter(org.apache.struts.chain.contexts.ServletActionContext,
	 *      org.apache.struts.action.Action,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doFilter(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);

		// ---------------------------------------
		// Read data from request parameters
		// ---------------------------------------
		long itemId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_ID);
		String itemTypeString = (hardCodedItemType != null) ? hardCodedItemType : request
				.getParameter(WebAppNames.REQUEST.PARAM.ITEM_TYPE);
		ItemType itemType = EnumUtil.createFromString(ItemType.class, itemTypeString);
		long parentId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_PARENT_ID);
		logger.debug("Request params: itemId " + itemId + " itemType " + itemType
				+ " parentId " + parentId);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		// Find existing/create a new item by ID or type
		AbstractItemUtilBD bdItemUtil = new StrutsItemUtilBD(context, user);
		Item item = bdItemUtil.findItem(itemId, itemType, parentId);
		logger.debug("item = " + item);

		// ---------------------------------------
		// Attach results to request
		// ---------------------------------------
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, item);
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Find an Item";
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @param hardCodedItemType
	 *            the hardCodedItemType to set
	 */
	public void setHardCodedItemType(String hardCodedItemType)
	{
		this.hardCodedItemType = hardCodedItemType;
	}

}
