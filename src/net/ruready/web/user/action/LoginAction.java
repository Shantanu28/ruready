/*****************************************************************************************
 * Source File: LoginAction.java
 ****************************************************************************************/
package net.ruready.web.user.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.audit.UserSession;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.user.form.LoginForm;
import net.ruready.web.user.imports.StrutsUserBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Receives a submitted login form and forwards to the authorization filter.
 * Authentication is not yet performed at this stage.
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
public class LoginAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(LoginAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= METHODS ===================================

	/**
	 * Clear the login form. This is the default action.
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
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return this.action_setup_reset(mapping, form, request, response);
	}

	/**
	 * This action resets the login form and forwards back to the login page.
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
	public ActionForward action_setup_reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// ---------------------------------------
		// Read data from view
		// ---------------------------------------
		LoginForm loginForm = (LoginForm) form;

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		loginForm.reset();
		// Remove cookies so that the client doesn't repopulate them after
		// resetting the
		// form
		removeLoginCookies(request, response);

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------

		request.setAttribute("loginForm", loginForm);
		return mapping.findForward("failure");
	} // action_setup_reset()

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
	public ActionForward action_login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();

		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		LoginForm loginForm = (LoginForm) form;
		if (logger.isDebugEnabled())
		{
			logger.debug("loginForm bean: " + loginForm);
		}

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// If user agrees to remember his/her login values, save
		// them as cookies
		if (loginForm.isSaveCookies())
		{
			addLoginCookies(loginForm, request, response);
		}

		// Authenticate. If it fails, an AuthenticationException is thrown
		// and the rest of this action is not executed.
		AbstractUserBD bdUser = new StrutsUserBD(StrutsUtil
				.getWebApplicationContext(request));
		User user = bdUser.authenticate(loginForm.getEmail(), loginForm.getPassword());

		logger.debug("LoginAction user: " + user);

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// This part is executed when the user has successfully authenticated.
		// Log the user in.
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Add a login message. This will also update the user in the database.
		UserSession userSession = new UserSession(user, null, loginForm.getSh(),
				loginForm.getSw());
		bdUser.login(user, userSession);

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		// Form is request-scoped, so need to re-attach it for the
		// authentication
		// filter
		request.setAttribute("loginForm", loginForm);

		session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_ID, user.getId());
		session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_CURRENT_PERSPECTIVE, user
				.getDefaultPerspective());

		// Authentication has yet not been performed. We forward to the
		// authorization filter. If no book-mark was requested, we forward
		// to the default value (application's main [internal] page).
		return mapping.findForward("success");
	}

	/**
	 * Add the login form fields as cookies in the client.
	 * 
	 * @param loginForm
	 *            filled login form bean
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 */
	private void addLoginCookies(LoginForm loginForm, HttpServletRequest request,
			HttpServletResponse response)
	{
		logger.info("Saving login cookies from in the client");

		addCookie(request, response, WebAppNames.COOKIE.USER_EMAIL, loginForm.getEmail());
		addCookie(request, response, WebAppNames.COOKIE.USER_PASSWORD, loginForm
				.getPassword());
	}

	/**
	 * Remove the login cookies from the client.
	 * 
	 * @param loginForm
	 *            filled login form bean
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @see http://www.velocityreviews.com/forums/t129698-jspservlet-remove-cookies.html
	 */
	private void removeLoginCookies(HttpServletRequest request,
			HttpServletResponse response)
	{
		logger.info("Removing login cookies from client");
		removeCookie(request, response, WebAppNames.COOKIE.USER_EMAIL);
		removeCookie(request, response, WebAppNames.COOKIE.USER_PASSWORD);
	}

	private void addCookie(final HttpServletRequest request,
			final HttpServletResponse response, final String name, final String value)
	{
		setCookie(request, response, name, value, WebAppNames.COOKIE.MAX_AGE);
	}

	private void removeCookie(final HttpServletRequest request,
			final HttpServletResponse response, final String name)
	{
		setCookie(request, response, name, CommonNames.MISC.EMPTY_STRING, 0);
	}

	private void setCookie(final HttpServletRequest request,
			final HttpServletResponse response, final String name, final String value,
			final int age)
	{
		final Cookie cookie = new Cookie(name, value);
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(age);
		response.addCookie(cookie);
	}
}
