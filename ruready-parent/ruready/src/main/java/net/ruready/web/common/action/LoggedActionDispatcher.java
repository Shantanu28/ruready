/*****************************************************************************************
 * Source File: LoggedActionDispatcher.java
 ****************************************************************************************/

package net.ruready.web.common.action;

import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ActionDispatcher;
import org.apache.struts.actions.EventActionDispatcher;

/**
 * Original $Id: EventActionDispatcher.java 384134 2006-03-08 06:43:55Z niallp $
 * Copyright 2006 The Apache Software Foundation. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * <p>
 * <p>
 * An Action helper class that dispatches to to one of the public methods that
 * are named in the <code>parameter</code> attribute of the corresponding
 * ActionMapping and matches a submission parameter. This is useful for
 * developers who prefer to use many submit buttons, images, or submit links on
 * a single form and whose related actions exist in a single Action class.
 * </p>
 * <p>
 * The method(s) in the associated <code>Action</code> must have the same
 * signature (other than method name) of the standard Action.execute method.
 * </p>
 * <p>
 * To configure the use of this action in your <code>struts-config.xml</code>
 * file, create an entry like this:
 * </p>
 * 
 * <pre><code>
 *   &lt;action path=&quot;/saveSubscription&quot;
 *           type=&quot;org.example.SubscriptionAction&quot;
 *           name=&quot;subscriptionForm&quot;
 *          scope=&quot;request&quot;
 *          input=&quot;/subscription.jsp&quot;
 *      parameter=&quot;save,back,recalc=recalculate,unspecified=save&quot;/&gt;
 * </code></pre>
 * 
 * <p>
 * where <code>parameter</code> contains three possible methods and one
 * default method if nothing matches (such as the user pressing the enter key).
 * </p>
 * <p>
 * For utility purposes, you can use the <code>key=value</code> notation to
 * alias methods so that they are exposed as different form element names, in
 * the event of a naming conflict or otherwise. In this example, the
 * <em>recalc</em> button (via a request parameter) will invoke the
 * <code>recalculate</code> method. The security-minded person may find this
 * feature valuable to obfuscate and not expose the methods.
 * </p>
 * <p>
 * The <em>unspecified</em> default key is purely optional. If this is not
 * specified and no parameters match the list of method keys, <code>null</code>
 * is returned which means the <code>unspecified</code> method will be
 * invoked.
 * </p>
 * <p>
 * The order of the parameters are guaranteed to be iterated in the order
 * specified. If multiple buttons were accidently submitted, the first match in
 * the list will be dispatched.
 * </p>
 * <p>
 * To implement this <i>dispatch</i> behavior in an <code>Action</code>,
 * class create your custom Action as follows, along with the methods you
 * require (and optionally "cancelled" and "unspecified" methods):
 * </p>
 * <p/>
 * 
 * <pre>
 * public class MyCustomAction extends Action
 * {
 * 
 * 	protected ActionDispatcher dispatcher = new EventActionDispatcher(this);
 * 
 * 	public ActionForward execute(ActionMapping mapping, ActionForm form,
 * 			HttpServletRequest request, HttpServletResponse response) throws Exception
 * 	{
 * 		return dispatcher.execute(mapping, form, request, response);
 * 	}
 * }
 * </pre>
 * 
 * <p>
 * This class should have been a decorator of {@link ActionDispatcher} that logs
 * dispatch actions invoked, but there's not enough visibility in
 * {@link ActionDispatcher} to make this happen. So we inherit instead. We also
 * ignore methods whose request parameter values are empty.
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
 * @see http://wiki.apache.org/struts/EventActionDispatcher
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 30, 2007
 * @since Struts 1.2.9
 */
public class LoggedActionDispatcher extends EventActionDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(LoggedActionDispatcher.class);

	/**
	 * The method key, if present, to use if other specified method keys do not
	 * match a request parameter.
	 */
	public static final String DEFAULT_METHOD_KEY = "unspecified";

	/**
	 * The method key, if present, to use if other specified method keys do not
	 * match a request parameter.
	 */
	@Deprecated
	public static final String DEFAULT_METHOD_KEY_WITH_VALIDATION = "unspecifiedValidated";

	/**
	 * The action whose methods we are dispatching.
	 */
	private final Action action;

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a new logged object for the specified action.
	 * 
	 * @param action
	 *            the action
	 */
	public LoggedActionDispatcher(final Action action)
	{
		// N.B. MAPPING_FLAVOR causes the getParameter() method
		// in ActionDispatcher to throw an exception if the
		// parameter is missing
		super(action);
		this.action = action;
	}

	// ========================= STATIC METHODS ============================

	/**
	 * Identify the method name to be dispatched to. Strip off the
	 * <code>WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX</code> prefix from
	 * the request parameter's name.
	 * 
	 * @param request
	 *            client's request
	 * @return method name to be dispatched to or
	 *         <code>DEFAULT_METHOD_KEY_WITH_VALIDATION</code> if no parameter
	 *         matches the
	 *         <code>WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX</code>
	 *         prefix.
	 */
	public static String getActionName(ServletRequest request)
	{
		if (!LoggedActionDispatcher.isIgnoredString(request
				.getParameter(DEFAULT_METHOD_KEY)))
		{
			return DEFAULT_METHOD_KEY;
		}

		Enumeration<?> parameters = request.getParameterNames();
		while (parameters.hasMoreElements())
		{
			// If the method key exists as a stand-alone parameter that
			// starts with the action prefix, with or without
			// the image suffixes (.x/.y), the method name has been found.
			String name = (String) parameters.nextElement();
			String value = request.getParameter(name);
			String valueX = request.getParameter(name + ".x");
			int i = name.indexOf(WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX);
			if ((i >= 0)
					&& (!LoggedActionDispatcher.isIgnoredString(value) || !LoggedActionDispatcher
							.isIgnoredString(valueX)))
			{
				// Parameter must start with DISPATCH_PARAMETER_PREFIX and not
				// equal false to activate its dispatch method
				return name;
				// return name.substring(i + 7);
			}
		}

		return DEFAULT_METHOD_KEY;
	}

	// ========================= IMPLEMENTATION: ActionDispatcher ==========

	/**
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it).
	 * Return an <code>ActionForward</code> instance describing where and how
	 * control should be forwarded, or <code>null</code> if the response has
	 * already been completed. Here, the action's parameter is optional, not
	 * required.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @throws Exception
	 *             if an exception occurs
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		// Process a cancellation action
		if (isCancelled(request))
		{
			final Log actionLogger = LogFactory.getLog(action.getClass());
			StrutsUtil.logAction(actionLogger, "cancelled");
			ActionForward af = cancelled(mapping, form, request, response);
			if (af != null)
			{
				return af;
			}
		}

		// Identify the request parameter containing the method name
		String parameter = CommonNames.MISC.EMPTY_STRING;
		try
		{
			parameter = getParameter(mapping, form, request, response);
		}
		catch (ServletException e)
		{
			// Parameter not found, ignored
		}

		// Get the method's name. This could be overridden in subclasses.
		String name = getMethodName(mapping, form, request, response, parameter);

		// Prevent recursive calls
		if ("execute".equals(name) || "perform".equals(name))
		{
			String message = messages.getMessage("dispatch.recursive", mapping.getPath());

			log.error(message);
			throw new ServletException(message);
		}

		// Invoke the named method, and return the result
		try
		{
			return dispatchMethod(mapping, form, request, response, name);
		}
		catch (NoSuchMethodException e)
		{
			// Fall-back
			return dispatchMethod(mapping, form, request, response,
					getDefaultMethodName());
		}

	}

	// /**
	// * @param arg0
	// * @param arg1
	// * @param arg2
	// * @param arg3
	// * @return
	// * @throws Exception
	// * @see
	// org.apache.struts.actions.ActionDispatcher#execute(org.apache.struts.action.ActionMapping,
	// * org.apache.struts.action.ActionForm,
	// javax.servlet.http.HttpServletRequest,
	// * javax.servlet.http.HttpServletResponse)
	// */
	// @Override
	// public ActionForward execute(ActionMapping arg0, ActionForm arg1,
	// HttpServletRequest arg2, HttpServletResponse arg3) throws Exception
	// {
	// return super.execute(arg0, arg1, arg2, arg3);
	// }

	/**
	 * @see org.apache.struts.actions.DispatchAction#dispatchMethod(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	@Override
	protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, String name)
			throws Exception
	{
		// Debugging printouts
		if (logger.isDebugEnabled())
		{
			HttpRequestUtil.printRequestParameters(request);
		}

		// Add a log entry
		final Log actionLogger = LogFactory.getLog(action.getClass());
		StrutsUtil.logAction(actionLogger, name);

		// Call the dispatch method
		return super.dispatchMethod(mapping, form, request, response, name);
	}

	/**
	 * Returns the method name, given a parameter's value.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @param parameter
	 *            The <code>ActionMapping</code> parameter's name
	 * @return The method's name.
	 * @throws Exception
	 *             if an error occurs.
	 */
	@Override
	protected String getMethodName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, String parameter)
			throws Exception
	{
		String defaultMethodKey = getDefaultMethodName();
		String defaultMethodName = defaultMethodKey;

		// ==================================================================
		// 1. Look for a request parameter that exactly matches
		// DEFAULT_METHOD_KEY
		// 2. Look for request parameters that start with the action prefix
		// ==================================================================
		String name = LoggedActionDispatcher.getActionName(request);
		if (name.startsWith(WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX))
		{
			// Found a method key other than the absolute default key
			// (DEFAULT_METHOD_KEY)
			return name;
		}

		// ==================================================================
		// 3. Look for keys in the action's parameter attribute that start
		// with the action prefix
		// ==================================================================
		StringTokenizer st = new StringTokenizer(parameter, ",");
		while (st.hasMoreTokens())
		{
			String methodKey = st.nextToken().trim();
			String methodName = methodKey;

			// The key can either be a direct method name or an alias
			// to a method as indicated by a "key=value" signature
			int equals = methodKey.indexOf('=');
			if (equals > -1)
			{
				methodName = methodKey.substring(equals + 1).trim();
				methodKey = methodKey.substring(0, equals).trim();
			}

			// Set the default if it passes by; otherwise, will use
			// defaultMethodName=default key
			if (methodKey.equals(defaultMethodKey))
			{
				defaultMethodName = methodName;
			}

			// If the method key exists as a stand-alone parameter or with
			// the image suffixes (.x/.y), the method name has been found.

			// ===================================================================
			// This is where we change the behavior of EventDispatchAction:
			// (1) Ignoring any string that equals
			// WebAppNames.ACTION.VALUE_FALSE,
			// not just null strings.
			// (2) Request parameter name is assumed to be prefixed by
			// WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX.
			// ===================================================================

			// if ((request.getParameter(methodKey) != null)
			// || (request.getParameter(methodKey + ".x") != null)) {

			if (methodKey.startsWith(WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX)
					&& (!LoggedActionDispatcher.isIgnoredString(request
							.getParameter(methodKey)) || !LoggedActionDispatcher
							.isIgnoredString(request.getParameter(methodKey + ".x"))))
			{
				return methodName;
			}
		}

		// ==================================================================
		// 4. Fall-back: default method name (might have been set above)
		// ==================================================================

		return defaultMethodName;
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Returns <code>true</code> iff this parameter value string is to be
	 * ignored in the dispatcher. That is, returns <code>true</code> iff a
	 * string is null or equals <code>WebAppNames.ACTION.VALUE_FALSE</code>
	 * after trimming.
	 * 
	 * @return should this parameter value string be ignored in the dispatcher
	 */
	public static boolean isIgnoredString(String s)
	{
		return (s == null) || WebAppNames.ACTION.VALUE_FALSE.equals(s.trim());
	}

	// ========================= HOOKS =====================================

	/**
	 * Returns the default method name (corresponding to an unspecified method
	 * key) for this dispatch action. Must NOT start with
	 * <code>WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX</code>.
	 * 
	 * @return
	 */
	protected String getDefaultMethodName()
	{
		return DEFAULT_METHOD_KEY;
	}

	// ========================= GETTERS & SETTERS =========================

}
