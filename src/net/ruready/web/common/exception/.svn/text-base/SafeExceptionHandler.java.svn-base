/*****************************************************************************************
 * Source File: SafeExceptionHandler.java
 ****************************************************************************************/
package net.ruready.web.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.exception.InternationalizbleException;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.ErrorUtil;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.ParameterMap;
import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

/**
 * A base class for all our Struts {@link ExceptionHandler}s. Catches
 * exceptions thrown during exception handling.
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
 * @version Oct 9, 2007
 */
public class SafeExceptionHandler extends BaseExceptionHandler
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
	private static final Log logger = LogFactory.getLog(SafeExceptionHandler.class);

	// ========================= FIELDS =====================================

	/**
	 * The user that encountered the exception.
	 */
	protected User user;

	// ========================= CONSTRUCTORS ===============================

	/**
	 * Instantiate this exception handler.
	 */
	public SafeExceptionHandler()
	{
		super();
		if (logger.isDebugEnabled())
		{
			logger.debug("Initializing");
		}
	}

	// ========================= ABSTRACT METHODS ===========================

	/**
	 * Handle the exception. Prepare error messages.
	 * 
	 * @param e
	 * @param ex
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param mappingName
	 *            path of action that threw the exception, if available
	 * @param errors
	 *            optional: add errors to be displayed on the error page
	 * @return
	 * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception,
	 *      org.apache.struts.config.ExceptionConfig,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void handle(Exception e, ExceptionConfig ex, ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response,
			final String mappingName, final ActionErrors errors) throws Exception
	{

	}

	// ========================= IMPLEMENTATION: ExceptionHandler ===========

	/**
	 * Execute the exception handler. This is a template method.
	 * 
	 * @param e
	 * @param ex
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception,
	 *      org.apache.struts.config.ExceptionConfig,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public final ActionForward execute(Exception e, ExceptionConfig origEx,
			ActionMapping origMapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		Boolean ajax = (Boolean) context.get(WebAppNames.CONTEXT.AJAX);
		if ((ajax != null) && (ajax))
		{
			return this
					.executeAjaxAction(e, origEx, origMapping, form, request, response);
		}
		else
		{
			return this.executeNormalAction(e, origEx, origMapping, form, request,
					response);
		}
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Execute the exception handler for normal Struts actions. This is a
	 * template method.
	 * 
	 * @param e
	 * @param ex
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception,
	 *      org.apache.struts.config.ExceptionConfig,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	private ActionForward executeNormalAction(Exception e, ExceptionConfig origEx,
			ActionMapping origMapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		ActionForward forward = null;
		String mappingName = null;
		ExceptionConfig ex = origEx;
		ActionMapping mapping = origMapping;
		try
		{
			// Make sure super.execute() is called only once
			// forward = super.execute(e, ex, mapping, form, request, response);
			if (request.getAttribute("exceptionHandlerStarted") == null)
			{
				request.setAttribute("exceptionHandlerStarted", "true");
				// Save all arguments
				request.setAttribute("exceptionHandlerException", e);
				request.setAttribute("exceptionHandlerExceptionConfig", ex);
				request.setAttribute("exceptionHandlerForm", form);
				request.setAttribute("exceptionHandlerMapping", mapping);
				forward = super.execute(e, ex, mapping, form, request, response);
			}
			if ((forward == null) && (mapping != null))
			{
				// Restore arguments if forward was lost in super.execute() due
				// to an exception thrown there
				// mapping = (ActionMapping)
				// request.getAttribute("exceptionHandlerMapping");
				// ex = (ExceptionConfig) request
				// .getAttribute("exceptionHandlerExceptionConfig");
				forward = mapping.findForward("errorSystem");
			}

			mappingName = ((mapping == null) ? WebAppNames.KEY.MESSAGE.EXCEPTION_HANDLER_MAPPING_NOT_FOUND
					: mapping.getName());

			user = HttpRequestUtil.findUser(request);

			String path = request.getRequestURI().toString();
			logger.info("Requested path: " + path);
			logger.info("Handling "
					+ ((e == null) ? CommonNames.MISC.NULL_TO_STRING : e.getClass()
							.getSimpleName())
					+ ", Action "
					+ mappingName
					+ ", user "
					+ ((user == null) ? CommonNames.MISC.NOT_APPLICABLE : (user
							.getEmail()
							+ " id " + user.getId() + ".")));

			// Attach default error messages
			ActionErrors errors = new ActionErrors();
			request.setAttribute(Globals.ERROR_KEY, errors);

			if (e != null)
			{
				// Convention: global message uses the key
				// "error.ExceptionClassSimpleName"
				// the resource bundle.
				String defaultKey = CommonNames.KEY.EXCEPTION.PREFIX
						+ e.getClass().getSimpleName();

				if (e instanceof InternationalizbleException)
				{
					InternationalizbleException exception = (InternationalizbleException) e;
					// Prepare an i18ned error message
					ActionMessage error = (exception.getKey() != null) ?
					// Exception parser error refers to the application
					// resources
					ErrorUtil.dynamicToActionMessage(exception.getErrorMessage())
							:
							// Literal error message, use default exception key
							new ActionMessage(defaultKey, e.getMessage());
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				}
				else
				{
					// Don't add any error message.
					// errors.add(ActionMessages.GLOBAL_MESSAGE, new
					// ActionMessage(
					// defaultKey, e.getMessage()));
				}
			}

			// Attach exception object to request for display
			request.setAttribute("exception", e);

			// Execute custom handling
			this.handle(e, ex, mapping, form, request, response, mappingName, errors);
		}
		catch (Throwable exception)
		{
			logger
					.error("=====================================================================================");
			logger.error("An error has occurred in " + this.getClass().getSimpleName()
					+ " while trying to process Action: " + mappingName);
			logger.error("Exception raised is : " + exception.getMessage());
			if (e != null)
			{
				logger.error("Original Exception: " + e.getMessage());
			}
			logger
					.error("=====================================================================================");
		}

		return forward;
	}

	/**
	 * Execute the exception handler for AJAX actions. This is a template
	 * method.
	 * 
	 * @param e
	 * @param origEx
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception,
	 *      org.apache.struts.config.ExceptionConfig,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	private ActionForward executeAjaxAction(Exception e, ExceptionConfig origEx,
			ActionMapping origMapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		ActionForward forward = null;
		String mappingName = null;
		ActionMapping mapping = origMapping;
		try
		{
			mappingName = ((mapping == null) ? WebAppNames.KEY.MESSAGE.EXCEPTION_HANDLER_MAPPING_NOT_FOUND
					: mapping.getName());

			ParameterMap map = new ParameterMap();
			map.put("exception", "" + e.getClass().getSimpleName());

			// ======================================================
			// Prepare response
			// ======================================================
			// Write the record list in JSON format. Use our own toJSONString()
			// method that does not quote the property names (map keys in this
			// case). This is due to problems of parsing quoted property names
			// on
			// the client's side.
			String encoded = map.toJSONString();
			logger.debug("encoded " + encoded);
			response.getWriter().write(encoded);
			return null;
		}
		catch (Throwable exception)
		{
			logger
					.error("=====================================================================================");
			logger.error("An error has occurred in " + this.getClass().getSimpleName()
					+ " while trying to process Action: " + mappingName);
			logger.error("Exception raised is : " + exception.getMessage());
			if (e != null)
			{
				logger.error("Original Exception: " + e.getMessage());
			}
			logger
					.error("=====================================================================================");
		}

		return forward;
	}
}
