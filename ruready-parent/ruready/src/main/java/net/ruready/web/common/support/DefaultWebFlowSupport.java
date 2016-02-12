package net.ruready.web.common.support;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.ScopeType;

/**
 * Contains commons support methods for use in integrating with both Spring Web
 * Flow and Struts.
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
 * @author Jeremy Lund
 * @version Nov 27, 2007
 */
public class DefaultWebFlowSupport
{

	private static final String CANCEL_MESSAGE = ".cancel.message";
	private static final String ERROR_MESSAGE = ".error.message";
	private static final String SUCCESS_MESSAGE = ".success.message";
	private static final String MISSING_SUFFIX = ".MISSING";

	protected final Log logger = LogFactory.getLog(getClass());

	public final void addSuccessMessage(final RequestContext context,
			final Object... args) throws Exception
	{
		addMessage(context, getSuccessMessage(context), args);
	}

	public final void addCancelMessage(final RequestContext context, final Object... args)
			throws Exception
	{
		addMessage(context, getCancelMessage(context), args);
	}

	public final String getSuccessMessage(final RequestContext context) throws Exception
	{
		return getMessageKey(context, SUCCESS_MESSAGE);
	}

	public final String getErrorMessage(final RequestContext context) throws Exception
	{
		return getMessageKey(context, ERROR_MESSAGE);
	}

	public final String getCancelMessage(final RequestContext context) throws Exception
	{
		return getMessageKey(context, CANCEL_MESSAGE);
	}

	public final String getMessageKey(final RequestContext context, final String keySuffix)
			throws Exception
	{
		return getFlowKeyPrefix(context) + keySuffix;
	}

	public final String getMissingKey(final RequestContext context, final String keySuffix)
			throws Exception
	{
		return getMessageKey(context, keySuffix) + MISSING_SUFFIX;
	}

	private String getFlowKeyPrefix(final RequestContext context) throws Exception
	{
		return context.getFlowScope().getString("flowKeyPrefix");
	}

	/**
	 * Adds a success message to the Struts session. Web Flow will only support
	 * adding errors messages to Struts, so this is a workaround.
	 * 
	 * @param context
	 *            the request context
	 * @param message
	 *            the message key for the message to add
	 * @param args
	 *            zero-or-more arguments to add to the message template
	 */
	public final void addMessage(final RequestContext context, final String message,
			final Object... args)
	{
		addMessage(context, Globals.MESSAGE_KEY, message, args);
	}

	public final void addError(final RequestContext context, final String message,
			final Object... args)
	{
		addMessage(context, Globals.ERROR_KEY, message, args);
	}

	public final void exposeErrors(final RequestContext context, final Errors errors)
	{
		if (errors.hasErrors())
		{
			final ActionMessages messages = getMessageList(context, Globals.ERROR_KEY);
			for (Object o : errors.getAllErrors())
			{
				ObjectError error = (ObjectError) o;
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error
						.getCode(), error.getArguments()));
			}
			storeMessages(context, Globals.ERROR_KEY, messages);
		}
	}

	private void addMessage(final RequestContext context, final String messageKey,
			final String message, final Object... args)
	{
		final ActionMessages messages = getMessageList(context, messageKey);
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, args));
		storeMessages(context, messageKey, messages);
	}

	private ActionMessages getMessageList(final RequestContext context,
			final String messageKey)
	{
		ActionMessages messages = (ActionMessages) context.getExternalContext()
				.getSessionMap().get(messageKey, ActionMessages.class);
		if (messages == null)
		{
			messages = new ActionMessages();
		}
		return messages;
	}

	private void storeMessages(final RequestContext context, final String messageKey,
			final ActionMessages messages)
	{
		context.getExternalContext().getSessionMap().put(messageKey, messages);
	}

	/**
	 * Returns the RUReady web application context from the request context
	 * 
	 * @param context
	 *            the request context
	 * @return the RUReady web application context
	 */
	public final WebApplicationContext getWebApplicationContext(
			final RequestContext context)
	{
		return StrutsUtil.getWebApplicationContext(getServletRequest(context));
	}

	/**
	 * Returns the user from the session of the request context
	 * 
	 * @param context
	 *            the request context
	 * @return a user object representing the user for this session
	 */
	public final User getSessionUser(final RequestContext context)
	{
		return HttpRequestUtil.findUser(getServletRequest(context));
	}

	/**
	 * Convenience method for retrieving the system user account
	 * 
	 * @return the system user's user object
	 */
	public final User getSystemUser()
	{
		return SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
	}

	/**
	 * Returns the Struts message resources from the request context
	 * 
	 * @param context
	 *            the request context
	 * @return the message resources
	 */
	public final MessageResources getMessageResources(final RequestContext context)
	{
		return (MessageResources) context.getExternalContext().getRequestMap().get(
				Globals.MESSAGES_KEY, MessageResources.class);
	}

	/**
	 * Returns the user locale (according to Struts) from the request context
	 * 
	 * @param context
	 *            the request context
	 * @return the user locale
	 */
	public final Locale getLocale(final RequestContext context)
	{
		return (Locale) context.getExternalContext().getSessionMap().get(
				Globals.LOCALE_KEY, Locale.class);
	}

	/**
	 * Writes the contents of each Web Flow scope to the logs for debugging
	 * purposes
	 * 
	 * @param context
	 *            the request context
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void logScopes(final RequestContext context) throws Exception
	{
		logger.debug("Values for scope: requestParameters");
		final Map requestMap = context.getRequestParameters().asMap();
		for (Object o : requestMap.keySet())
		{
			logger.debug(o + ": " + requestMap.get(o));
		}

		for (ScopeType scope : new ScopeType[]
		{ ScopeType.REQUEST, ScopeType.FLASH, ScopeType.FLOW, ScopeType.CONVERSATION })
		{
			//logger.debug("Values for scope: " + scope.getLabel());
			final Map scopeMap = scope.getScope(context).asMap();
			for (Object o : scopeMap.keySet())
			{
				logger.debug(o + ": " + scopeMap.get(o));
			}
		}
	}

	/**
	 * Convenience method for get the servlet request from a Web Flow request
	 * context
	 * 
	 * @param context
	 *            the request context
	 * @return the current servlet request
	 */
	private HttpServletRequest getServletRequest(final RequestContext context)
	{
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//return ((ServletExternalContext) context.getExternalContext()).getRequest();
	}
}
