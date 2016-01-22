/*****************************************************************************************
 * Source File: ExceptionToBeMailedHandler.java
 ****************************************************************************************/
package net.ruready.web.common.exception;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.util.ExceptionUtil;
import net.ruready.port.mail.Mail;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

/**
 * A simple {@link ExceptionHandler} that send an e-mail regarding the exception.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 14, 2007
 */
public class ExceptionToBeMailedHandler extends SafeExceptionHandler
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
	private static final Log logger = LogFactory.getLog(ExceptionToBeMailedHandler.class);

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
		// Get the email server through the resource locator
		ResourceLocator rl = WebAppResourceLocator.getInstance();
		Session mailSession = (Session) rl.getMailSession();

		// write the exception details to the log file
		logger.error("An Exception has occurred: " + e.toString());
		logger.error(ExceptionUtil.getStackTraceAsString(e));
		Message msg = new MimeMessage(mailSession);

		msg.setFrom();

		// Setting who is suppose to receive the email
		String helpEmail = mailSession.getProperty("mail.net.ruready.helpemail");
		String[] recipients =
		{
			helpEmail
		};

		// Setting the important text
		String subject = "Error message occurred in Action:" + mappingName;
		String content = "An error occurred while trying to invoke execute() on Action:"
				+ mappingName
				+ "."
				+ CommonNames.MISC.NEW_LINE_CHAR
				+ "User may have been: "
				+ ((user == null) ? CommonNames.MISC.NOT_APPLICABLE : (user.getEmail()
						+ " id " + user.getId() + ".")) + CommonNames.MISC.NEW_LINE_CHAR
				+ "Error message: " + e.getMessage() + CommonNames.MISC.NEW_LINE_CHAR
				+ "Stack trace:" + CommonNames.MISC.NEW_LINE_CHAR
				+ ExceptionUtil.getStackTraceAsString(e);

		Mail.send(recipients, subject, content, null, mailSession);
	}

}
