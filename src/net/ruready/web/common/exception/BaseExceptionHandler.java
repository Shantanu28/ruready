/*****************************************************************************************
 * Source File: BaseExceptionHandler.java
 ****************************************************************************************/

package net.ruready.web.common.exception;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.util.ModuleException;

/**
 * <p>
 * An <strong>ExceptionHandler</strong> is configured in the Struts configuration file to
 * handle a specific type of exception thrown by an <code>Action.execute</code> method.
 * </p>
 * <p>
 * We modified the original Struts code to set the module of the forward.
 * </p>
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
 * @version Oct 16, 2007
 */

public class BaseExceptionHandler extends ExceptionHandler
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
	private static final Log logger = LogFactory.getLog(BaseExceptionHandler.class);


	// ========================= CONSTRUCTORS ===============================

	/**
	 * Instantiate this exception handler.
	 */
	public BaseExceptionHandler()
	{
		super();
		if (logger.isDebugEnabled())
		{
			logger.debug("Initializing");
		}
	}

	// ========================= IMPLEMENTATION: ExceptionHandler ===========

	/**
	 * <p>
	 * Handle the Exception. Return the ActionForward instance (if any) returned by the
	 * called ExceptionHandler.
	 * </p>
	 * 
	 * @param ex
	 *            The exception to handle
	 * @param ae
	 *            The ExceptionConfig corresponding to the exception
	 * @param mapping
	 *            The ActionMapping we are processing
	 * @param formInstance
	 *            The ActionForm we are processing
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @return The <code>ActionForward</code> instance (if any) returned by the called
	 *         <code>ExceptionHandler</code>.
	 * @throws ServletException
	 *             if a servlet exception occurs
	 * @since Struts 1.1
	 */
	@Override
	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping,
			ActionForm formInstance, HttpServletRequest request,
			HttpServletResponse response)
	{
		logger.debug("ExceptionHandler executing for exception " + ex);

		ActionForward forward;
		ActionMessage error;
		String property;

		// Build the forward from the exception mapping if it exists
		// or from the form input
		if (ae.getPath() != null)
		{
			forward = new ActionForward(ae.getPath());
		}
		else
		{
			forward = mapping.getInputForward();
		}

		// ===========================================================
		// Our custom code - start
		// ===========================================================
		// Allows module-relative paths for this exception handler
		if ((forward != null) && (ae.getProperty("module") != null))
		{
			forward.setModule(ae.getProperty("module"));
		}		
		// ===========================================================
		// Our custom code - end
		// ===========================================================

		// Figure out the error
		if (ex instanceof ModuleException)
		{
			error = ((ModuleException) ex).getActionMessage();
			property = ((ModuleException) ex).getProperty();
		}
		else
		{
			error = new ActionMessage(ae.getKey(), ex.getMessage());
			property = error.getKey();
		}

		this.logException(ex);

		// Store the exception
		request.setAttribute(Globals.EXCEPTION_KEY, ex);
		this.storeException(request, property, error, forward, ae.getScope());

		if (!response.isCommitted())
		{
			return forward;
		}

		logger.debug("Response is already committed, so forwarding will not work."
				+ " Attempt alternate handling.");

		if (!silent(ae))
		{
			handleCommittedResponse(ex, ae, mapping, formInstance, request, response,
					forward);
		}
		else
		{
			logger.warn("ExceptionHandler configured with " + SILENT_IF_COMMITTED
					+ " and response is committed.", ex);
		}

		return null;
	}

	// ========================= PRIVATE METHODS ============================

	/**
	 * <p>
	 * Attempt to give good information when the response has already been committed when
	 * the exception was thrown. This happens often when Tiles is used. Base
	 * implementation will see if the INCLUDE_PATH property has been set, or if not, it
	 * will attempt to use the same path to which control would have been forwarded.
	 * </p>
	 * 
	 * @param ex
	 *            The exception to handle
	 * @param config
	 *            The ExceptionConfig we are processing
	 * @param mapping
	 *            The ActionMapping we are processing
	 * @param formInstance
	 *            The ActionForm we are processing
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param actionForward
	 *            The ActionForward we are processing
	 * @since Struts 1.3
	 */
	@Override
	protected void handleCommittedResponse(Exception ex, ExceptionConfig config,
			ActionMapping mapping, ActionForm formInstance, HttpServletRequest request,
			HttpServletResponse response, ActionForward actionForward)
	{
		String includePath = determineIncludePath(config, actionForward);

		if (includePath != null)
		{
			if (includePath.startsWith("/"))
			{
				logger.debug("response committed, " + "but attempt to include results "
						+ "of actionForward path");

				if ((config != null) && (config.getProperty("module") != null))
				{
					includePath = config.getProperty("module") + includePath;
				}

				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher(includePath);

				try
				{
					requestDispatcher.include(request, response);

					return;
				}
				catch (IOException e)
				{
					logger.error("IOException when trying to include "
							+ "the error page path " + includePath, e);
				}
				catch (ServletException e)
				{
					logger.error("ServletException when trying to include "
							+ "the error page path " + includePath, e);
				}
				catch (Throwable e)
				{
					logger.error(e.getClass() + " when trying to include "
							+ "the error page path " + includePath, e);
				}
			}
			else
			{
				logger.warn("Suspicious includePath doesn't seem likely to work, "
						+ "so skipping it: " + includePath
						+ "; expected path to start with '/'");
			}
		}

		logger.debug("Include not available or failed; "
				+ "try writing to the response directly.");

		try
		{
			response.getWriter().println("Unexpected error: " + ex);
			response.getWriter().println("<!-- ");
			ex.printStackTrace(response.getWriter());
			response.getWriter().println("-->");
		}
		catch (IOException e)
		{
			logger.error("Error giving minimal information about exception", e);
			logger.error("Original exception: ", ex);
		}
	}

	/**
	 * <p>
	 * Indicate whether this Handler has been configured to be silent. In the base
	 * implementation, this is done by specifying the value <code>"true"</code> for the
	 * property "SILENT_IF_COMMITTED" in the ExceptionConfig.
	 * </p>
	 * 
	 * @param config
	 *            The ExceptionConfiguration we are handling
	 * @return True if Handler is silent
	 * @since Struts 1.3
	 */
	private boolean silent(ExceptionConfig config)
	{
		return "true".equals(config.getProperty(SILENT_IF_COMMITTED));
	}
}
