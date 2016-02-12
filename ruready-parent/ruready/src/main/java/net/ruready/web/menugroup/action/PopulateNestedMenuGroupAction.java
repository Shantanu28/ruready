/*****************************************************************************************
 * Source File: PopulateMenuAction.java
 ****************************************************************************************/
package net.ruready.web.menugroup.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.util.XmlUtil;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.menugroup.entity.MenuEvent;
import net.ruready.web.menugroup.entity.NestedMenuGroup;
import net.ruready.web.menugroup.util.NestedMenuGroupUtil;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * Handle AJAX events of populating and updating a menu group.
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
public abstract class PopulateNestedMenuGroupAction extends Action
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(PopulateNestedMenuGroupAction.class);

	// ========================= IMPLEMENTATION: Action ====================

	/**
	 * Populate all menus from a top-level to a bottom-level menu's children.
	 * The top, bottom levels and current selections are searched for in the
	 * request attribute map, and if not found, in the request parameter map.
	 * Otherwise, default values are used to populate the top menu only.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		logger.debug("execute()");

		// ======================================================
		// Read context & parameters
		// ======================================================
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);
		MessageResources messageResources = (MessageResources) this.getServlet()
				.getServletContext().getAttribute(Globals.MESSAGES_KEY);
		MenuEvent menuEvent = new MenuEvent();
		menuEvent.populate(request);

		// ======================================================
		// Business logic
		// ======================================================
		NestedMenuGroup group = getNestedMenuGroup(context, user);
		Map<String, OptionList> selection = generateMenuSelection(context, user, group,
				menuEvent);

		// ======================================================
		// Prepare response
		// ======================================================
		// Write selections to response in XML format
		HttpRequestUtil.prepareXMLResponse(response);
		response.getWriter().write(XmlUtil.openTag(WebAppNames.XML.RESPONSE).toString());
		for (Map.Entry<String, OptionList> entry : selection.entrySet())
		{
			// Internationalize option labels and update their value to be
			// "parentId23" if the parent ID is 23 and this is not the top menu.
			int level = group.indexOf(entry.getKey());
			OptionList i18nOptions = StrutsUtil.i18NEmptySelectionOptions(entry
					.getValue(), messageResources, group.getNoSelectionKey(level));
			// Append the options to the response
			response.getWriter().write(
					StrutsUtil.options2Xml(entry.getKey(), i18nOptions));
		}
		response.getWriter().write(XmlUtil.closeTag(WebAppNames.XML.RESPONSE).toString());

		// Custom post-processing
		this.extraProcess(mapping, form, request, response, group, selection);

		return null;
	}

	// ========================= HOOKS =====================================

	/**
	 * Return the menu group managed by this action.
	 * 
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations
	 * @return the menu group
	 */
	abstract protected NestedMenuGroup getNestedMenuGroup(
			final ApplicationContext context, final User user);

	/**
	 * Generate menu selections following a menu change event.
	 * 
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations
	 * @param group
	 *            the menu group association with this action
	 * @param menuEvent
	 *            menu change event
	 * @return map of selections ({@link OptionList}s) to be transferred back
	 *         to the client
	 */
	protected Map<String, OptionList> generateMenuSelection(
			final ApplicationContext context, final User user,
			final NestedMenuGroup group, final MenuEvent menuEvent)
	{
		return NestedMenuGroupUtil.generateMenuSelection(group, menuEvent);
	}

	/**
	 * Extra action processing to invoke at the end of
	 * {@link #execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
	 * (right before returning the <code>null</code> forward).
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param group
	 *            menu group, which is already populated in the body of
	 *            {@link #execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
	 * @param selection
	 *            menu selection, which is already populated in the body of
	 *            {@link #execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
	 */
	protected void extraProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			final NestedMenuGroup group, final Map<String, OptionList> selection)
	{

	}

	// ========================= PRIVATE METHODS ===========================

}
