/*****************************************************************************************
 * Source File: PopulateChildrenMenuAction.java
 ****************************************************************************************/
package net.ruready.web.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.XmlUtil;
import net.ruready.web.common.action.AjaxAction;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.AjaxUtil;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * Ajax population of hierarchical drop-down menus referring to the CMS item
 * hierarchy.
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
 * 
 */
public class PopulateChildrenMenuAction extends LoggedActionWithDispatcher implements
		AjaxAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(PopulateChildrenMenuAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * This action pre-populates all menus from the user's teacher link
	 * properties, if they are already non-null. This is the default method.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		logger.debug("Port: " + request.getServerPort());

		// ---------------------------------------
		// Read data from view
		// ---------------------------------------
		User user = HttpRequestUtil.findUser(request);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		// If schoolId is present, use it to load the teacher link.
		// Otherwise, retrieve the user's teacher link
		long schoolId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_ID);
		// TODO re-factor this once schools are moved to teachers/students
		Item school = null;
		long schoolIdToLoad = (schoolId != CommonNames.MISC.INVALID_VALUE_INTEGER) ? schoolId
				: CommonNames.MISC.INVALID_VALUE_INTEGER;
		if (schoolIdToLoad == CommonNames.MISC.INVALID_VALUE_INTEGER)
		{
			// Didn't find the relevant user and/or form data
			return null;
		}

		// Reload teacher link and its parent hierarchy, each including its
		// children array
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		school = bdItem.read(Item.class, schoolIdToLoad);
		Item city = bdItem.read(Item.class, school.getParent().getId());
		Item state = bdItem.read(Item.class, city.getParent().getId());
		Item country = bdItem.read(Item.class, state.getParent().getId());

		// Prepare all drop-down menu data
		MessageResources messageResources = (MessageResources) this.getServlet()
				.getServletContext().getAttribute(Globals.MESSAGES_KEY);
		String responseStrSchool = AjaxUtil.prepareXMLMenuData(school.getChildren(),
				school.getType().toString(), messageResources,
				CommonNames.MISC.INVALID_VALUE_INTEGER);
		String responseStrCity = AjaxUtil.prepareXMLMenuData(city.getChildren(), city
				.getType().toString(), messageResources,
				CommonNames.MISC.INVALID_VALUE_INTEGER);
		String responseStrState = AjaxUtil.prepareXMLMenuData(state.getChildren(), state
				.getType().toString(), messageResources,
				CommonNames.MISC.INVALID_VALUE_INTEGER);
		String responseStrCountry = AjaxUtil.prepareXMLMenuData(country.getChildren(),
				country.getType().toString(), messageResources,
				CommonNames.MISC.INVALID_VALUE_INTEGER);

		// ---------------------------------------
		// Attach results to request & response
		// ---------------------------------------
		// Set tokens telling the view that menu data exists
		AjaxUtil.setToken(request, school, school.getIdentifier());
		AjaxUtil.setToken(request, city, city.getIdentifier());
		AjaxUtil.setToken(request, state, state.getIdentifier());
		AjaxUtil.setToken(request, country, country.getIdentifier());

		// The response contains all menus' data
		HttpRequestUtil.prepareXMLResponse(response);
		StringBuffer s = TextUtil.emptyStringBuffer();
		s.append(XmlUtil.openTag(WebAppNames.XML.MENU));
		s.append(responseStrSchool);
		s.append(responseStrCity);
		s.append(responseStrState);
		s.append(responseStrCountry);
		s.append(XmlUtil.closeTag(WebAppNames.XML.MENU));
		response.getWriter().write(s.toString());

		// Not forwarding to anywhere, response is fully-cooked
		return null;

	} // unspecified()

	/**
	 * This action pre-populates the editing form from the user bean properties,
	 * and forwards to the editing view.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward action_populate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		logger.debug("Port: " + request.getServerPort());

		// ---------------------------------------
		// Read data from view
		// ---------------------------------------
		User user = HttpRequestUtil.findUser(request);
		long parentId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_PARENT_ID);
		String itemType = request.getParameter(WebAppNames.REQUEST.PARAM.ITEM_TYPE);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		// Retrieve the parent catalog item
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		Item item = null;
		// TODO: replace this try-catch block by some smart exception handling?
		try
		{
			if (parentId != CommonNames.MISC.INVALID_VALUE_INTEGER)
			{
				item = bdItem.read(Item.class, parentId);
			}
		}
		catch (Exception e)
		{

		}

		// Prepare an OLS drop-down menu data object from item's children
		// We use here item.getType() rather than item type so that we can
		// distinguish in the view between sub-classes of a generic item in the
		// hierarchy.
		// For instance, if itemType=COUNTRY, item.getType() might be
		// FEDERATION.
		MessageResources messageResources = (MessageResources) this.getServlet()
				.getServletContext().getAttribute(Globals.MESSAGES_KEY);
		String responseStr = AjaxUtil.prepareXMLMenuData((item == null) ? null : item
				.getChildren(), (item == null) ? itemType : item.getType(),
				messageResources, CommonNames.MISC.INVALID_VALUE_INTEGER);

		// ---------------------------------------
		// Attach results to request & response
		// ---------------------------------------
		// Set tokens telling the view that menu data exists
		AjaxUtil.setToken(request, item, (item == null) ? ItemType.valueOf(itemType)
				: item.getIdentifier());

		HttpRequestUtil.prepareXMLResponse(response);
		response.getWriter().write(responseStr);

		// Not forwarding to anywhere, response is fully-cooked
		return null;

	} // populate()

	// ========================= PRIVATE METHODS ===========================

}
