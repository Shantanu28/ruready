/*****************************************************************************************
 * Source File: LogoutAction.java
 ****************************************************************************************/
package net.ruready.web.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.user.entity.User;
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
 * Logs a user out of the site.
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
 * ------------------------------------------------------------------------- *
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 5, 2007
 */
public class LogoutAction extends Action
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(LogoutAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= METHODS ===================================

	/**
	 * Setup the necessary data and go to the editable view of the children
	 * (edit view).
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

		// -------------------------------------------
		// Read data from request & session
		// -------------------------------------------
		User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------
		if (user == null)
		{
			logger.debug("Attempted to logout but no user found in the session");
		}
		else
		{
			// Log the user out
			AbstractUserBD bdUser = new StrutsUserBD(StrutsUtil
					.getWebApplicationContext(request));
			bdUser.logout(user);
		}

		// -------------------------------------------
		// Put data in request & session
		// -------------------------------------------

		// Create a new session if doesn't exist yet
		HttpSession session = request.getSession(true);

		// Remove the user bean and preference objects from the session
		session.removeAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_ID);
		session.removeAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_CURRENT_PERSPECTIVE);

		// Go back to the front page
		return mapping.findForward("userHome");
	}
}
