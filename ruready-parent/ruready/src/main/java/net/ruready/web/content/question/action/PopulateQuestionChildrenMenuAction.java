/*****************************************************************************************
 * Source File: PopulateQuestionChildrenMenu.java
 ****************************************************************************************/
package net.ruready.web.content.question.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.catalog.entity.Topic;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.util.exports.AbstractItemUtilBD;
import net.ruready.business.content.util.util.ItemTypeUtil;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.EnumUtil;
import net.ruready.common.util.XmlUtil;
import net.ruready.web.common.action.AjaxAction;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.AjaxUtil;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.item.imports.StrutsItemUtilBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * AJAX population of a set of dependent drop-down menus in the edit question
 * page.
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
public class PopulateQuestionChildrenMenuAction extends LoggedActionWithDispatcher
		implements AjaxAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(PopulateQuestionChildrenMenuAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * This action pre-populates all menus from the a sub-topic ID request
	 * parameter, if it is already non-<code>null</code>. This is the
	 * default method.
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
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		ActionErrors errors = new ActionErrors();

		// ---------------------------------------
		// Read data from view and request
		// ---------------------------------------
		User user = HttpRequestUtil.findUser(request);

		// Derive and retrieve all question menu data from the item id
		// parameter. This parameter corresponds to a leaf item; all menus
		// including and above the leaf is populated; all menus below the leaf
		// are cleared.
		long leafItemId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_ID);
		ItemType itemType = EnumUtil.createFromString(ItemType.class, request
				.getParameter(WebAppNames.REQUEST.PARAM.ITEM_TYPE));
		AbstractItemUtilBD bdItemUtil = new StrutsItemUtilBD(context, user);
		Item leafItem = bdItemUtil.findItemById(leafItemId, itemType);

		if (!errors.isEmpty() || (leafItem == null))
		{
			// ---------------------------------------
			// Failure: display errors
			// ---------------------------------------
			// Fail silently for now
			// request.setAttribute(Globals.ERROR_KEY, errors);
			return null;
		}

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		this.populateMenusFromLeaf(mapping, form, request, response, leafItem, user);

		// ---------------------------------------
		// Attach results to request & response
		// ---------------------------------------
		// Done inside populateMenusFromTopic

		// Not forwarding to anywhere, response is fully-cooked
		return null;

	} // unspecified()

	/**
	 * This action updates the menus at a certain level in the item hierarchy
	 * and all menus "below" it.
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
		// Read data from view & form
		// ---------------------------------------
		User user = HttpRequestUtil.findUser(request);
		long itemId = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_ID);
		ItemType itemType = EnumUtil.createFromString(ItemType.class, request
				.getParameter(WebAppNames.REQUEST.PARAM.ITEM_TYPE));
		ItemType childType = EnumUtil.createFromString(ItemType.class, request
				.getParameter(WebAppNames.REQUEST.PARAM.CHILD_TYPE));

		// EditQuestionFullForm editQuestionFullform = (EditQuestionFullForm)
		// form;
		// EditQuestionForm eqForm = EditQuestionUtil.getQuestionForm(form);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		// Retrieve the parent catalog item. "Parent" refers here to the
		// parent-menu
		// selection's identifier. This may be more than one level up in the CMS
		// tree,
		// e.g. subTopic -> parentId = course.getId().
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		Item item = null;
		// TODO: replace this try-catch block by some smart exception handling?
		try
		{
			if (itemId != CommonNames.MISC.INVALID_VALUE_INTEGER)
			{
				item = bdItem.read(Item.class, itemId);
			}
		}
		catch (Exception e)
		{

		}

		// Prepare an OLS drop-down menu data object from item's children
		// We use here item.getType() rather than item type so that we can
		// distinguish in the view between sub-classes of a generic item in the
		// hierarchy. For instance, if itemType=COUNTRY, item.getType() might be
		// FEDERATION.
		MessageResources messageResources = (MessageResources) this.getServlet()
				.getServletContext().getAttribute(Globals.MESSAGES_KEY);
		String responseStr = null;
		List<? extends Item> children = (item == null) ? null : bdItem.findChildren(item,
				childType.getItemClass(), childType);
		responseStr = AjaxUtil.prepareXMLMenuData(children, itemType.toString(),
				messageResources, CommonNames.MISC.INVALID_VALUE_INTEGER);

		// ---------------------------------------
		// Attach results to request & response
		// ---------------------------------------

		// Set tokens telling the view that menu data exists
		AjaxUtil.setToken(request, item, itemType);

		HttpRequestUtil.prepareXMLResponse(response);
		response.getWriter().write(responseStr);

		// Not forwarding to anywhere, response is fully-cooked
		return null;

	} // populate()

	// ========================= PRIVATE METHODS ===========================

	/**
	 * This action pre-populates all menus above a leaf level in the item
	 * hierarchy from a leaf item.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param subTopic
	 * @param user
	 *            the user requesting the operation
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	private void populateMenusFromLeaf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			final Item leafItem, final User user) throws IOException
	{
		// Gather data required for drop-down menu population
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		MessageResources messageResources = (MessageResources) this.getServlet()
				.getServletContext().getAttribute(Globals.MESSAGES_KEY);
		final ItemType leafType = leafItem.getIdentifier();

		// Initialize the XML response that will contain all menu data
		HttpRequestUtil.prepareXMLResponse(response);
		StringBuffer s = TextUtil.emptyStringBuffer();
		s.append(XmlUtil.openTag(WebAppNames.XML.MENU));

		// Reload subTopic and its parent hierarchy below. Each parent loading
		// includes
		// its children array.

		// =======================================
		// Course drop-down menu selection
		// =======================================

		int courseHeight = ItemTypeUtil.getPathLength(ItemType.COURSE, leafType);
		Item course = null;
		if (courseHeight >= 0)
		{
			// Read menu selection from the database
			course = bdItem.read(Item.class, leafItem.getSuperParentId(courseHeight));

			// No need to append to the XML response because course selection
			// always
			// exist in the view (JSP)

			// Set token telling the view that menu data exists
			AjaxUtil.setToken(request, course, course.getIdentifier());

			logger.debug("Course     selection: " + course.getName() + " ID "
					+ course.getId());
		}

		// =======================================
		// Topic drop-down menu
		// =======================================

		int topicHeight = ItemTypeUtil.getPathLength(ItemType.TOPIC, leafType);
		Item topic = null;
		if (topicHeight >= 0)
		{
			// Read menu data and selection from the database
			topic = bdItem.read(Item.class, leafItem.getSuperParentId(topicHeight));
			List<? extends Item> topics = bdItem.findChildren(course, Topic.class,
					ItemType.TOPIC);

			// Append menu data to the response
			String responseStr = AjaxUtil
					.prepareXMLMenuData(topics, course.getType().toString(),
							messageResources, CommonNames.MISC.INVALID_VALUE_INTEGER);
			s.append(responseStr);

			// Set token telling the view that menu data exists
			AjaxUtil.setToken(request, topic, topic.getIdentifier());

			logger.debug("Topic      selection: " + topic.getName() + " ID "
					+ topic.getId());
		}

		// =======================================
		// Sub-topic drop-down menu
		// =======================================

		int subTopicHeight = ItemTypeUtil.getPathLength(ItemType.SUB_TOPIC, leafType);
		if (subTopicHeight >= 0)
		{
			// Read data and menu selection from the database
			Item subTopic = leafItem;
			List<? extends Item> subTopics = bdItem.findChildren(topic, SubTopic.class,
					ItemType.SUB_TOPIC);

			// Append menu data to the response
			String responseStr = AjaxUtil
					.prepareXMLMenuData(subTopics, topic.getType().toString(),
							messageResources, CommonNames.MISC.INVALID_VALUE_INTEGER);
			s.append(responseStr);

			// Set token telling the view that menu data exists
			AjaxUtil.setToken(request, subTopic, subTopic.getIdentifier());

			logger.debug("Sub-Topic  selection: " + subTopic.getName() + " ID "
					+ subTopic.getId());
		}

		// ---------------------------------------
		// Attach results to request & response
		// ---------------------------------------

		// Finalize the XML response
		s.append(XmlUtil.closeTag(WebAppNames.XML.MENU));
		response.getWriter().write(s.toString());

	} // populateMenusFromTopic()
}
