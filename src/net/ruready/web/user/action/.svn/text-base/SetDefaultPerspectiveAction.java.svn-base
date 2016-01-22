/*****************************************************************************************
 * Source File: SetDefaultPerspectiveAction.java
 ****************************************************************************************/
package net.ruready.web.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.user.imports.StrutsUserBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Sets/manipulates the user's preferable perspective of the main page. This
 * applies to users that have multiple roles.
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
 * @version Aug 5, 2007
 */
public class SetDefaultPerspectiveAction extends Action
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(SetDefaultPerspectiveAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= METHODS ===================================

	// ========================= ACTION METHODS ============================
	/**
	 * This action forwards to the system user main page.
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
		logger.info("execute()");

		// ---------------------------------------
		// Read data from request & session
		// ---------------------------------------
		User user = HttpRequestUtil.findUser(request);

		String newPerspectiveStr = request
				.getParameter(WebAppNames.REQUEST.PARAM.USER_PERSPECTIVE);

		// ---------------------------------------
		// Business ParserInputFormat
		// ---------------------------------------

		// Update default perspective using request parameter
		RoleType requestedPerspective = null;
		try
		{
			requestedPerspective = RoleType.valueOf(newPerspectiveStr);
		}
		catch (final Exception e)
		{
			// TODO should an exception really be squashed here?
		}
		if (requestedPerspective != null)
		{
			AbstractUserBD bdUser = new StrutsUserBD(StrutsUtil
					.getWebApplicationContext(request));
			user.setDefaultPerspective(requestedPerspective);
			bdUser.updateUser(user);
			logger.info("User " + user.getEmail() + ": new default perspective is "
					+ user.getDefaultPerspective());
		}

		// ---------------------------------------
		// Attach results to response & forward
		// ---------------------------------------

		// Forward back to the main page.
		// TODO: make this action useful in multiple actions/page,
		// and use a request parameter to go back to the page we came from.
		return mapping.findForward("userMain");
	}
}
