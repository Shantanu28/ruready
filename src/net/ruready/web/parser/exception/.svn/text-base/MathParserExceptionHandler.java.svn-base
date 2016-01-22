/*****************************************************************************************
 * Source File: MathParserExceptionHandler.java
 ****************************************************************************************/
package net.ruready.web.parser.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.parser.port.input.exports.ParserInputFormat;
import net.ruready.parser.service.exception.MathParserException;
import net.ruready.web.common.exception.SafeExceptionHandler;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.parser.form.DemoForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

/**
 * This handler is invoked on throwing a <code>MathParserException</code>.
 * This indicates that parser matching and/or analysis of a string failed.
 * <p>
 * Note 1: the exception handled here <i>must</i> be of
 * <code>MathParserException</code> type.
 * <p>
 * Note 2: this assumes that the form bean is of type {@link DemoForm}.
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
 * @version Aug 14, 2007
 */
public class MathParserExceptionHandler extends SafeExceptionHandler
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
	private static final Log logger = LogFactory.getLog(MathParserExceptionHandler.class);

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
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.String,
	 *      org.apache.struts.action.ActionErrors)
	 */
	@Override
	protected void handle(Exception e, ExceptionConfig ex, ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response,
			String mappingName, ActionErrors errors) throws Exception
	{
		logger.info("Parser failure: " + ((MathParserException) e).getMessage());

		// Fetch the form bean and go back to the old format in case a
		// parser input format change failed due to a parser exception
		ParserInputFormat oldFormat = (ParserInputFormat) request
				.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.PARSER_OLD_FORMAT);
		if (oldFormat != null)
		{
			logger.debug("Going back to saved old format " + oldFormat);
			DemoForm demoForm = (DemoForm) form;
			demoForm.setFormat(oldFormat.toString());
			request.setAttribute("demoForm", demoForm);
		}
	}
}
