/*****************************************************************************************
 * Source File: ApplyFilterAction.java
 ****************************************************************************************/

package net.ruready.web.chain.command.normal;

import java.util.List;

import net.ruready.common.rl.CommonNames;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.chain.filter.FilterPackage;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.commands.AbstractExecuteAction;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;

/**
 * Invoke all the filters that apply to this action. Cache the returned
 * <code>ActionForward</code>, if it is non-null.
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
 * @version Oct 8, 2007
 */
public class ApplyFilterCommand extends AbstractExecuteAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ApplyFilterCommand.class);

	// ========================= FIELDS ====================================

	// ========================= IMPLEEMNTATION: AbstractExecuteAction =====

	/**
	 * <p>
	 * Invoke the appropriate <code>Action</code> for this request, and cache
	 * the returned <code>ActionForward</code>.
	 * </p>
	 * 
	 * @param actionCtx
	 *            The <code>Context</code> for the current request
	 * @return <code>false</code> so that processing continues
	 * @throws Exception
	 *             if thrown by the Action class
	 */
	@Override
	public boolean execute(ActionContext actionCtx) throws Exception
	{
		logger.debug("Executing command '" + this.getDescription() + " ("
				+ this.getClass().getSimpleName() + ")'");

		// Acquire the resources we will need to send to the filter actions
		ActionConfig actionConfig = actionCtx.getActionConfig();
		ActionForm actionForm = actionCtx.getActionForm();
		// Apply filters BEFORE validation
		ForwardConfig forwardConfig = executeFilter(actionCtx, actionConfig, actionForm);
		if (forwardConfig != null)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("Forwarding to " + forwardConfig);
			}
			actionCtx.setForwardConfig(forwardConfig);
			return (false);
		}

		// Skip processing if the current request is not valid
		Boolean valid = actionCtx.getFormValid();
		if ((valid == null) || !valid.booleanValue())
		{
			return (false);
		}

		// Acquire additional resources we will need to send to the Action
		Action action = actionCtx.getAction();
		if (action == null)
		{
			return (false);
		}

		// Execute the Action for this request, caching returned ActionForward
		forwardConfig = execute(actionCtx, action, actionConfig, actionForm);
		if (forwardConfig != null)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("Forwarding to " + forwardConfig);
			}
			actionCtx.setForwardConfig(forwardConfig);
		}

		return (false);
	}

	/**
	 * <p>
	 * Execute the specified <code>Action</code>, and return the resulting
	 * <code>ActionForward</code>.
	 * </p>
	 * 
	 * @param context
	 *            The <code>Context</code> for this request
	 * @param action
	 *            The <code>Action</code> to be executed
	 * @param actionConfig
	 *            The <code>ActionConfig</code> defining this action
	 * @param actionForm
	 *            The <code>ActionForm</code> (if any) for this action
	 * @throws Exception
	 *             if thrown by the <code>Action</code>
	 * @see org.apache.struts.chain.commands.AbstractExecuteAction#execute(org.apache.struts.chain.contexts.ActionContext,
	 *      org.apache.struts.action.Action,
	 *      org.apache.struts.config.ActionConfig,
	 *      org.apache.struts.action.ActionForm)
	 */
	@Override
	protected ForwardConfig execute(ActionContext context, Action action,
			ActionConfig actionConfig, ActionForm actionForm) throws Exception
	{
		// Get filter mapping
		ServletActionContext saContext = (ServletActionContext) context;
		return (action.execute((ActionMapping) actionConfig, actionForm, saContext
				.getRequest(), saContext.getResponse()));
	}

	/**
	 * Returns a description of this command.
	 * 
	 * @return a description of this command
	 */
	public String getDescription()
	{
		return "Apply filter actions";
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * <p>
	 * Execute the specified <code>Action</code>, and return the resulting
	 * <code>ActionForward</code>.
	 * </p>
	 * 
	 * @param context
	 *            The <code>Context</code> for this request
	 * @param actionConfig
	 *            The <code>ActionConfig</code> defining this action
	 * @param actionForm
	 *            The <code>ActionForm</code> (if any) for this action
	 * @throws Exception
	 *             if thrown by the <code>Action</code>
	 * @see org.apache.struts.chain.commands.AbstractExecuteAction#execute(org.apache.struts.chain.contexts.ActionContext,
	 *      org.apache.struts.action.Action,
	 *      org.apache.struts.config.ActionConfig,
	 *      org.apache.struts.action.ActionForm)
	 */
	protected ForwardConfig executeFilter(ActionContext context,
			ActionConfig actionConfig, ActionForm actionForm) throws Exception
	{
		// Get filter mapping
		ServletActionContext saContext = (ServletActionContext) context;
		FilterPackage filterPackage = (FilterPackage) saContext.getScope(
				ServletActionContext.APPLICATION_SCOPE).get(
				WebAppNames.APPLICATION.ATTRIBUTE.FILTER_PACKAGE);

		// Find all filters that apply to this action's context-relative path
		ModuleConfig moduleConfig = actionConfig.getModuleConfig();
		String modulePrefix = (moduleConfig == null) ? CommonNames.MISC.EMPTY_STRING
				: moduleConfig.getPrefix();
		String actionPath = modulePrefix + actionConfig.getPath();
		String requestPath = saContext.getRequest().getServletPath();
		List<FilterDefinition> filters = filterPackage.getFiltersThatApply(requestPath);
		if (logger.isDebugEnabled())
		{
			logger.debug("Action path  " + actionPath);
			logger.debug("Request path " + requestPath);
			HttpRequestUtil.printRequestParameters(saContext.getRequest());
			logger.debug("filters that apply: " + filters);
		}

		// Apply filters; if a filter has a non-null forward, forward to that
		// URL, otherwise continue
		for (FilterDefinition filter : filters)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("Applying filter " + filter);
			}
			// Construct a filter action instance and pass the filter definition
			// into its constructor for parameter initialization
			FilterAction filterAction = filter.getFilterClass().getConstructor(
					FilterDefinition.class).newInstance(filter);

			// Run the filter
			ForwardConfig forward = (filterAction.execute(saContext,
					(ActionMapping) actionConfig, actionForm, saContext.getRequest(),
					saContext.getResponse()));
			if (logger.isDebugEnabled())
			{
				logger.debug("Filter resulted in forward " + forward);
			}
			if (forward != null)
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("Failed to pass " + filter);
				}
				return forward;
			}
		}

		// If all filters pass, execute action
		return null;
	}
}
