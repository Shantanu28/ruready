/*****************************************************************************************
 * Source File: StrutsUtil.java
 ****************************************************************************************/
package net.ruready.web.common.util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.common.misc.Utility;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

/**
 * Centralizes useful methods related to AJAX actions on CMS items.
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
public class AjaxUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AjaxUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private AjaxUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	// ------------------------- DROP-DOWN MENUS ---------------------------

	/**
	 * Prepare XML-formatted drop-down menu data for a list of items.
	 * 
	 * @param items
	 *            list of items (may be <code>null</code>)
	 * @param groupId
	 *            id of the select group
	 * @param messageResources
	 *            i18n resources
	 * @return XML string with menu data
	 */
	public static String prepareXMLMenuData(final Collection<? extends Node> items,
			final String groupId, final MessageResources messageResources,
			final long selectedId)
	{
		logger.debug("Populating XML menu data, #items "
				+ ((items == null) ? "NULL" : items.size()));
		OptionList childrenOptions = StrutsUtil.items2OLS(items, false, selectedId);
		// Apply i18n to labels
		childrenOptions = StrutsUtil.i18NEmptySelectionOptions(childrenOptions,
				messageResources, WebAppNames.KEY.MESSAGE.OLS_NO_SELECTION_LABEL);

		// Prepare an XML formatted string representing the children
		// names
		/*
		 * XmlPrinter printer = new XmlPrinter(item, 1);
		 * printer.setItemDataPrinter(new XmlPrinter.ItemDataPrinter() { public
		 * void print(Item item) { } });
		 */
		String responseStr = StrutsUtil.options2Xml(groupId, childrenOptions);

		return responseStr;
	}

	/**
	 * Set a request attribute (token) that tells the view page that menu data
	 * is available.
	 * 
	 * @param request
	 * @param item
	 *            item (may be null)
	 * @param itemType
	 *            item's type
	 */
	public static void setToken(HttpServletRequest request, Item item, ItemType itemType)
	{
		Collection<Node> children = (item == null) ? null : item.getChildren();
		if (children != null)
		{
			String tokenName = WebAppNames.REQUEST.ATTRIBUTE.TOKEN.FOUND_ITEM_PREFIX
					+ itemType;
			request.setAttribute(tokenName, "true");
			logger.debug("Set request token [" + tokenName + "=" + "true" + "]");
		}
		else
		{
			// Remove all tokens in the hierarchy below this item type
			ItemType childType = itemType;
			while (childType != null)
			{
				String tokenName = WebAppNames.REQUEST.ATTRIBUTE.TOKEN.FOUND_ITEM_PREFIX
						+ childType;
				request.removeAttribute(tokenName);
				childType = childType.getChildType();
			}
		}

	}
}
