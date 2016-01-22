/*****************************************************************************************
 * Source File: FilterAction.java
 ****************************************************************************************/
package net.ruready.web.chain.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.common.exception.SystemException;
import net.ruready.common.text.TextUtil;
import net.ruready.web.chain.command.NamedCommand;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;

/**
 * <p>
 * A base class for Struts {@link Action} classes that act like Servlet filters in front
 * of standard Struts {@link Action}. This also serves as a decorator of sub-classes (the
 * template method pattern).
 * </p>
 * <p>
 * Important note:
 * {@link #doFilter(ServletActionContext, ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
 * returns an {@link ActionForward}, but because filter actions are not currently
 * declared in the Struts config files as "real" actions (because they filter another
 * action, so only this action's path currently shows up in all involved classes), always
 * return global forwards (visible to all actions), or throw exceptions to be caught by
 * handlers, if necessary.
 * </p>
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
 * @version Jul 21, 2007
 */
public abstract class FilterAction extends Action implements NamedCommand
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FilterAction.class);

	/**
	 * The default error page forward.
	 */
	protected static final String ERROR_PAGE_DEFAULT = "errorSystem";

	// ========================= FIELDS ====================================

	/**
	 * Error page forward. Has a default value (convention-over-configuration pattern).
	 */
	protected String errorPage;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Prevent construction without a {@link FilterDefinition} argument.
	 */
	@SuppressWarnings("unused")
	private FilterAction()
	{
		super();
	}

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions. Sub-classes
	 * should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public FilterAction(final FilterDefinition filterDefinition)
	{
		super();

		reset();

		try
		{
			// Initialize error page parameter if it exists, overriding the default
			// value potentially set by reset().
			if (filterDefinition.getErrorPage() != null)
			{
				BeanUtils
						.copyProperty(this, "errorPage", filterDefinition.getErrorPage());
			}

			// Initialize parameters
			for (InitParameter param : filterDefinition.getInitParameters())
			{
				BeanUtils.copyProperty(this, param.getParamName(), param.getParamValue());
			}
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(
					"Failed to initialize init parameters for filter " + this.getClass(),
					e);
		}
	}

	// ========================= IMPLEMENTATION: Action ====================

	/**
	 * <p>
	 * Process the specified HTTP request, and create the corresponding HTTP response (or
	 * forward to another web component that will create it), with provision for handling
	 * exceptions thrown by the business logic. Return an {@link ActionForward} instance
	 * describing where and how control should be forwarded, or <code>null</code> if the
	 * response has already been completed.
	 * </p>
	 * <p>
	 * Important note:
	 * {@link #doFilter(ServletActionContext, ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
	 * returns an {@link ActionForward}, but because filter actions are not currently
	 * declared in the Struts config files as "real" actions (because they filter another
	 * action, so only this action's path currently shows up in all involved classes),
	 * always return global forwards (visible to all actions), or throw exceptions to be
	 * caught by handlers, if necessary.
	 * </p>
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred, or <code>null</code>
	 *         if the response has been completed.
	 * @throws Exception
	 *             if the application business logic throws an exception
	 * @since Struts 1.1
	 */
	@Override
	public final ActionForward execute(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception
	{
		throw new SystemException(
				"Use the other execute() method instead");
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * <p>
	 * Process the specified HTTP request, and create the corresponding HTTP response (or
	 * forward to another web component that will create it), with provision for handling
	 * exceptions thrown by the business logic. Return an {@link ActionForward} instance
	 * describing where and how control should be forwarded, or <code>null</code> if the
	 * response has already been completed.
	 * </p>
	 * 
	 * @param context
	 *            Servlet action context
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred, or <code>null</code>
	 *         if the response has been completed.
	 * @throws Exception
	 *             if the application business logic throws an exception
	 * @since Struts 1.1
	 */
	protected abstract ActionForward doFilter(final ServletActionContext servletContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
		throws Exception;

	// ========================= METHODS ===================================

	/**
	 * <p>
	 * Process the specified HTTP request, and create the corresponding HTTP response (or
	 * forward to another web component that will create it), with provision for handling
	 * exceptions thrown by the business logic. Return an {@link ActionForward} instance
	 * describing where and how control should be forwarded, or <code>null</code> if the
	 * response has already been completed.
	 * </p>
	 * 
	 * @param saContext
	 *            Servlet action context
	 * @param action
	 *            the action we're currently filtering
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred, or <code>null</code>
	 *         if the response has been completed.
	 * @throws Exception
	 *             if the application business logic throws an exception
	 */
	public final ActionForward execute(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
		throws Exception
	{
		logger.debug("Executing filter '" + this.getDescription() + " ("
				+ this.getClass().getSimpleName() + ")'");
		return this.doFilter(saContext, mapping, form, request, response);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Set init parameter defaults (convention-over-configuration pattern).
	 */
	protected void reset()
	{
		errorPage = ERROR_PAGE_DEFAULT;
	}

	/**
	 * Check if a init parameter was supplied.
	 * 
	 * @param paramName
	 *            parameter's name
	 * @param paramValue
	 *            parameter's value
	 */
	protected final void checkRequiredInitParam(final String paramName,
			final Object paramValue)
	{
		if ((paramValue == null) || TextUtil.isEmptyTrimmedString(paramValue.toString()))
		{
			throw new IllegalArgumentException("init parameter '" + paramName
					+ "' is required for filter " + getClass().getSimpleName());
		}
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @param errorPage
	 *            the errorPage to set
	 */
	public void setErrorPage(String errorPage)
	{
		this.errorPage = errorPage;
	}

}
