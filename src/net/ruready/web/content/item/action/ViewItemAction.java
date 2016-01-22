/*****************************************************************************************
 * Source File: ViewItemAction.java
 ****************************************************************************************/
package net.ruready.web.content.item.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.util.exports.AbstractItemUtilBD;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.util.EnumUtil;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsItemUtilBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Handles AJAX events of expanding an ExtJS tree of CN items.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 7, 2007
 */
public class ViewItemAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ViewItemAction.class);

	// ========================= IMPLEMENTATION: Action ====================

	/**
	 * Load the children of an item whose ID is passed in the request parameter
	 * NODE. If no parameter is found, falls back to the root node.
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see net.ruready.web.common.action.LoggedActionWithDispatcher#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// ======================================================
		// Read context & parameters
		// ======================================================
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);

		long itemId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_ID);
		String itemTypeString = request.getParameter(WebAppNames.REQUEST.PARAM.ITEM_TYPE);
		ItemType itemType = EnumUtil.createFromString(ItemType.class, itemTypeString);
		long parentId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_PARENT_ID);
		logger.debug("Request params: itemId " + itemId + " itemType " + itemType);

		// ======================================================
		// Business logic
		// ======================================================

		// Find existing/create a new item by ID or type
		AbstractItemUtilBD bdItemUtil = new StrutsItemUtilBD(context, user);
		Item item = bdItemUtil.findItem(itemId, itemType, parentId);
		logger.debug("item = " + item);

		// ======================================================
		// Prepare response
		// ======================================================
		// Attach item to request
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, item);
		return mapping.findForward("view");
	}
}
