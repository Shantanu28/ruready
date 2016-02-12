/*****************************************************************************************
 * Source File: TreeAction.java
 ****************************************************************************************/
package net.ruready.web.content.item.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.entity.ItemDTO;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.item.imports.StrutsMainItemBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

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
public class TreeAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TreeAction.class);

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
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);
		AbstractMainItemBD bdMainItem = new StrutsMainItemBD(context, user);

		long parentId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.NODE);
		// Used for programmatic i18n and icon reference. Customizing icons
		// doesn't seem to work on the extjs side.
		String contextPath = request.getContextPath();
		MessageResources messageResources = this.getResources(request);

		// ======================================================
		// Business logic
		// ======================================================

		// Load parent
		Item item = (parentId == CommonNames.MISC.INVALID_VALUE_LONG) ? bdMainItem
				.findUnique(Root.class) : bdItem.read(Item.class, parentId);

		// Prepare DTO hierarchy that mirrors the parent + children list
		ItemDTO parentDto = new ItemDTO(item, contextPath, messageResources);
		for (Node object : item.getChildren())
		{
			Item child = (Item) object;
			parentDto.add(new ItemDTO(child, contextPath, messageResources));
		}

		// Prepare JSON output for children list
		String encoded = parentDto.toJSONString(true);
		if (logger.isDebugEnabled())
		{
			logger.debug("encoded " + encoded);
		}

		// ======================================================
		// Prepare response
		// ======================================================
		// Write the record list in JSON format
		// String encoded = JSONArray.fromObject(children).toString();
		// logger.debug("encoded " + encoded);
		response.getWriter().write(encoded.toString());
		return null;
	}

	/**
	 * Load the children of the entire parent hierarchy (ancestors) of an item
	 * upon root node load. This happens on page refresh.
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
	public ActionForward action_root(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);
		AbstractMainItemBD bdMainItem = new StrutsMainItemBD(context, user);
		// Used for programmatic i18n and icon reference. Customizing icons
		// doesn't seem to work on the extjs side.
		String contextPath = request.getContextPath();
		MessageResources messageResources = this.getResources(request);

		long itemId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_ID);

		// ======================================================
		// Business logic
		// ======================================================

		// Load item
		Item item = (itemId == CommonNames.MISC.INVALID_VALUE_LONG) ? bdMainItem
				.findUnique(Root.class) : bdItem.read(Item.class, itemId);
		ItemDTO mainDto = new ItemDTO(item, contextPath, messageResources);
		if (item.getParent() == null)
		{
			// Root item, load children list as in {@link #unspecified()}
			// Prepare DTO hierarchy that mirrors the parent + children list
			for (Node object : item.getChildren())
			{
				Item child = (Item) object;
				mainDto.add(new ItemDTO(child, contextPath, messageResources));
			}
		}
		else
		{
			List<Item> parents = item.getParents();
			ItemDTO parentDto = new ItemDTO(item, contextPath, messageResources); // dummy
			for (int i = 0; i < parents.size(); i++)
			{
				Item parent = parents.get(i);
				if (i == 0)
				{
					parentDto = new ItemDTO(parent, contextPath, messageResources);
					parentDto.setExpanded(true);
					mainDto = parentDto;
				}
				for (Node object : parent.getChildren())
				{
					Item child = (Item) object;
					parentDto.add(new ItemDTO(child, contextPath, messageResources));
				}

				if (i < parents.size() - 1)
				{
					Item nextParent = parents.get(i + 1);
					parentDto = parentDto.childWithId(nextParent.getId());
					parentDto.setExpanded(true);
				}
			}
		}

		// Prepare JSON output for children list
		String encoded = mainDto.toJSONString(true);
		if (logger.isDebugEnabled())
		{
			logger.debug("encoded " + encoded);
		}

		// ======================================================
		// Prepare response
		// ======================================================
		// Write the record list in JSON format
		// String encoded = JSONArray.fromObject(children).toString();
		// logger.debug("encoded " + encoded);
		response.getWriter().write(encoded.toString());
		return null;
	}
}
