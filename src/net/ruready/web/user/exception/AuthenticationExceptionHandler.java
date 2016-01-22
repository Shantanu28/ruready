/*****************************************************************************************
 * Source File: AuthenticationExceptionHandler.java
 ****************************************************************************************/
package net.ruready.web.user.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.user.exception.AuthenticationException;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.exception.SafeExceptionHandler;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.ExceptionConfig;

/**
 * Handles failed login attempts.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 10, 2007
 */
public class AuthenticationExceptionHandler extends SafeExceptionHandler
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(AuthenticationExceptionHandler.class);

	// ========================= IMPLEMENTATION: SafeExceptionHandler =======

	/**
	 * @param e
	 * @param ex
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param mappingName
	 * @param errors
	 * @see net.ruready.web.common.exception.SafeExceptionHandler#handle(java.lang.Exception,
	 *      org.apache.struts.config.ExceptionConfig,
	 *      org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 *      java.lang.String, org.apache.struts.action.ActionErrors)
	 */
	@Override
	protected void handle(Exception e, ExceptionConfig ex, ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response,
			String mappingName, ActionErrors errors) throws Exception
	{
		String path = request.getRequestURI().toString();
		logger.info("Failed login, user "
				+ ((user == null) ? CommonNames.MISC.NOT_APPLICABLE : user.getEmail())
				+ " requested path " + path);

		// Prepare an error message
		AuthenticationException ae = (AuthenticationException) e;
		String key = null;
		switch (ae.getType())
		{
			case LOGIN_FAILED:
			{
				key = WebAppNames.KEY.EXCEPTION.AUTHENTICATION_LOGIN_FAILED;
				break;
			}
			case ACCOUNT_LOCKED:
			{
				key = WebAppNames.KEY.EXCEPTION.AUTHENTICATION_ACCOUNT_LOCKED;
				break;
			}
			default:
			{
				key = WebAppNames.KEY.EXCEPTION.AUTHENTICATION_LOGIN_FAILED;
			}
		}
		errors.clear();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key, path));
	}

}
