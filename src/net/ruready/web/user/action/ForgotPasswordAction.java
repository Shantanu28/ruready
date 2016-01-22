/*****************************************************************************************
 * Source File: ForgotPasswordAction.java
 ****************************************************************************************/
package net.ruready.web.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.user.form.LoginForm;
import net.ruready.web.user.imports.StrutsUserBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * Creates a new password for a user.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * ------------------------------------------------------------------------- *
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 27, 2007
 */
public class ForgotPasswordAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ForgotPasswordAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= METHODS ===================================

	// ========================= ACTION METHODS ============================

	/**
	 * This action sets up an empty form for editing and forwards to the editing
	 * view.
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
		return mapping.findForward("failure");
	}

	/**
	 * Process the submitted user form and send a password reminder to the
	 * user's email address, or attach an error message to the request.
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
	public ActionForward action_send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		ActionErrors errors = new ActionErrors();

		// ---------------------------------------
		// Read data from view
		// ---------------------------------------
		LoginForm loginForm = (LoginForm) form;
		logger.debug("loginForm bean: " + loginForm);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------
		AbstractUserBD bdUser = new StrutsUserBD(StrutsUtil
				.getWebApplicationContext(request));
		// Find user
		User user = bdUser.findByUniqueEmail(loginForm.getEmail());

		if (user == null)
		{
			// Email not found in the database, display an error message
			errors.add(WebAppNames.KEY.EMAIL_NOT_FOUND, new ActionMessage(
					WebAppNames.KEY.EMAIL_NOT_FOUND));
			saveErrors(request, errors);
		}
		else
		{
			// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			// Create a new password and save it in the database.
			// If this fails, the email with the new password won't be sent,
			// yet an error message will be displayed to the user.
			// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

			// Generate a new password
			user.setPassword(bdUser.generatePassword(user));

			// Save it in the database
			bdUser.updateUser(user);

			// Create message content and send it to the user.
			// Decrypt user password.
			String subject = getResources(request).getMessage(
					WebAppNames.KEY.MAIL_REMINDER_SUBJECT);
			String content = getResources(request).getMessage(
					WebAppNames.KEY.MAIL_REMINDER_CONTENT, user.getEmail(),
					bdUser.decryptPassword(user.getPassword()));

			bdUser.mailMessage(user, subject, content);
		}

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------

		// Decide where to forward
		if (!errors.isEmpty())
		{
			// There are errors, forward to the input path
			return mapping.findForward("failure");
		}
		else
		{
			// Go the "previous" item view
			return mapping.findForward("success");
		}
	} // send()
}
