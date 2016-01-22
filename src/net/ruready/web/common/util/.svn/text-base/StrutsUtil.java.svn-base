/*****************************************************************************************
 * Source File: StrutsUtil.java
 ****************************************************************************************/
package net.ruready.web.common.util;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.XmlUtil;
import net.ruready.web.common.action.LoggedActionDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionList;
import net.ruready.web.select.exports.OptionListSource;
import net.ruready.web.select.exports.OptionListSourceFactory;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.util.MessageResources;

/**
 * Centralizes useful methods for Struts actions and form beans.
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
 * @version Jul 29, 2007
 */
public class StrutsUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StrutsUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private StrutsUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	// ------------------------- ACTION-RELATED METHODS --------------------

	/**
	 * Match a uri against a url pattern according to the Servlet URL
	 * specification rules.
	 * 
	 * @see http://www.caucho.com/resin-3.1/doc/plugin-dispatch.xtp#url-pattern
	 * @see http://www.caucho.com/resin-3.0/servlet/servlet.xtp#url-pattern
	 * @see http://blog.lo-fi.net/2006/06/url-pattern-matching.html
	 * @see http://www.roguewave.com/support/docs/hydraexpress/3.5.0/html/rwsfservletug/4-3.html
	 * @param urlToMatch
	 *            uri to match
	 * @param urlPattern
	 *            url pattern to match against
	 * @return does urlToMatch match the urlPattern pattern
	 */
	public static boolean urlMatches(final String urlToMatch, final String urlPattern)
	{
		boolean hasWildCard = urlPattern.indexOf("*") > -1;

		// If the pattern is an empty string,
		// interpret it as "nothing", not "everything"
		if ((urlPattern.trim().length() == 0) || (urlToMatch == null))
		{
			return false;
		}

		// Test to see if its the same
		if (urlToMatch.equals(urlPattern))
		{
			return true;
		}

		// Patterns without wild cards must match exactly
		if (!hasWildCard)
		{
			return false;
		}

		// URL pattern has a wild card
		String[] parts = urlPattern.split("\\*");
		if (parts.length == 1)
		{
			// Pattern like "/prefix/*"
			if ((parts[0].length() == 0) || (!parts[0].endsWith("/")))
			{
				logger.warn("Bad URL pattern: if it ends with *, it must end with /*");
				return false;

			}
			if (urlToMatch.startsWith(parts[0]))
			{
				return true;
			}
			else
			{
				// Case of "/prefix" matched against "/prefix/*" - should return
				// true
				// Case of "/prefix/" won't match.
				if (urlToMatch.endsWith("/"))
				{
					return urlToMatch.substring(0, urlToMatch.length() - 1).startsWith(
							parts[0].substring(0, parts[0].length() - 1));
				}
				else
				{
					return urlToMatch.startsWith(parts[0].substring(0,
							parts[0].length() - 1));
				}
			}
		}
		else if (parts.length == 2)
		{
			// Pattern like "*.ext"
			return urlToMatch.endsWith(parts[1]);
		}
		else
		{
			// Too many wildcards, bad URL pattern
			logger.warn("Bad URL pattern: too many wildcards");
			return false;
		}
	}

	/**
	 * Append a request parameter to a Struts forward.
	 * 
	 * @param forward
	 *            action forward object (will be cloned upon returning from this
	 *            method)
	 * @param paramName
	 *            parameter's name
	 * @param paramValue
	 *            parameter's value
	 * @return cloned, modified forward (also good for method chaining)
	 */
	public static ActionForward appendParameter(final ActionForward forward,
			final String paramName, final String paramValue)
	{
		// Make a copy of the forward action
		ActionForward newForward = new ActionForward(forward);

		// Add parameter to the forward action
		String newPath = HtmlUtil.buildUrl(forward.getPath(), paramName, paramValue);
		newForward.setPath(newPath);

		return newForward;
	}

	/**
	 * Return the web application context attribute of this request.
	 * 
	 * @param request
	 *            client's request object
	 * @return web application context of this client
	 */
	public static WebApplicationContext getWebApplicationContext(
			final ServletRequest request)
	{
		return (WebApplicationContext) request
				.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.WEB_APPLICATION_CONTEXT);
	}

	/**
	 * Find out if an Action method should be validated inside a lookup Action
	 * or not, according to our naming conventions. If the entire Action is
	 * configured not to be validated, this call has no effect. If the Action is
	 * validated, this method will override validation by returning
	 * <code>false</code>, provided that the form bean's
	 * <code>validate()</code> method calls this method (<code>isMethodValidated</code>)
	 * at its beginning. This method returns <code>false</code> if and only if
	 * the request contains a <code>WebAppNames.ACTION.VALIDATE_TOKEN</code>
	 * attribute whose value is the hard-coded string
	 * <code>WebAppNames.ACTION.VALUE_FALSE</code>. If an attribute is found
	 * with a <code>WebAppNames.ACTION.VALUE_TRUE</code> value, the method
	 * returns <code>true</code>. If the attribute is not found, a similar
	 * lookup is performed in request parameters; if a
	 * <code>WebAppNames.ACTION.VALIDATE_TOKEN</code> attribute is found with
	 * the value <code>WebAppNames.ACTION.VALUE_FALSE</code>, the method
	 * returns <code>false</code>. If its value is
	 * <code>WebAppNames.ACTION.VALUE_TRUE</code>, the method return
	 * <code>true</code>. In all other cases, <code>true</code> is
	 * returned.
	 * 
	 * @param request
	 * @return true if and only if the corresponding Action method should be
	 *         validated
	 */
	public static boolean isMethodValidated(final ServletRequest request)
	{
		// Perform negative checks

		// ===========================================================
		// Determine validation using the validate request parameter
		// ===========================================================

		// Search in request attributes; these may most commonly be set by a
		// servlet filter that serves as a setup action to the struts Action.
		try
		{
			String validate = (String) request
					.getAttribute(WebAppNames.ACTION.VALIDATE_TOKEN);
			if ((validate != null) && validate.equals(WebAppNames.ACTION.VALUE_FALSE))
			{
				return false;
			}
			if ((validate != null) && validate.equals(WebAppNames.ACTION.VALUE_TRUE))
			{
				return true;
			}
		}
		catch (Exception e)
		{
			// A ClassCastException may be thrown if the attribute is not a
			// string. Ignore the token in this case.
		}

		// Search in request parameters; these may be submitted in a form
		// or in a URL query string
		String validate = request.getParameter(WebAppNames.ACTION.VALIDATE_TOKEN);
		if ((validate != null) && validate.equals(WebAppNames.ACTION.VALUE_FALSE))
		{
			return false;
		}

		// ===========================================================
		// Determine validation using action parameter conventions
		// ===========================================================
		if (StrutsUtil.isNoValidationActionName(request))
		{
			return false;
		}

		// No need to check if validate equals true -- simply return true here.

		return true;
	}

	/**
	 * Identify the method name to be dispatched to. Strip off the
	 * <code>WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX</code> prefix from
	 * the request parameter's name.
	 * 
	 * @param request
	 *            client's request
	 * @return method name to be dispatched to or <code>null</code> if no
	 *         parameter matches the
	 *         <code>WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX</code>
	 *         prefix.
	 */
	@Deprecated
	public static String getActionNameThatEqualsTrue(final ServletRequest request)
	{
		Enumeration<?> parameters = request.getParameterNames();
		while (parameters.hasMoreElements())
		{
			String item = (String) parameters.nextElement();
			String value = request.getParameter(item);
			int i = item.indexOf(WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX);
			if ((i >= 0) && WebAppNames.ACTION.VALUE_TRUE.equals(value))
			{
				// Parameter must start with DISPATCH_PARAMETER_PREFIX and must
				// equal to
				// VALUE_TRUE to activate its dispatch method.
				return item.substring(i + 7);
			}
		}
		return null;
	}

	/**
	 * Identify whether the method name to be dispatched to is not to be
	 * validated.
	 * 
	 * @param request
	 *            client's request
	 * @return <code>true</code> iff the method name to be dispatched to is
	 *         not to be validated, i.e. starts with
	 *         <code>WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX_NO_VALIDATION</code>;
	 *         <i>or if no method name is found (an unspecified action)</i>
	 */
	public static boolean isNoValidationActionName(final ServletRequest request)
	{
		String name = LoggedActionDispatcher.getActionName(request);
		return LoggedActionDispatcher.DEFAULT_METHOD_KEY.equals(name)
				|| name
						.startsWith(WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX_NO_VALIDATION);
	}

	/**
	 * Adds a log entry at the start of a dispatch action method's execution.
	 * 
	 * @param actionLogger
	 *            logger
	 * @param actionName
	 *            name of action method; does not include the common action name
	 *            prefix. For instance, if the action parameter value is
	 *            <code>&quot;action_setup_method&quot;</code>,
	 *            <code>actionName = &quot;method&quot;</code>; or if the
	 *            action parameter value is
	 *            <code>&quot;action_method&quot;</code>,
	 *            <code>actionName = &quot;method&quot;</code>
	 */
	public static void logAction(final Log actionLogger, final String actionName)
	{
		actionLogger.info("[" + WebAppNames.REQUEST.PARAM.ACTION_PREFIX + "]" + " "
				+ actionName);
	}

	// ------------------------- INTERNATIONALIZATION ----------------------

	/**
	 * Convert a list of items to a drop-down selection list. The labels are the
	 * items' IDs and the values are their names.
	 * 
	 * @param items
	 *            list of item
	 * @param required
	 *            a boolean indicating whether or not input is required for this
	 *            field. Setting this indicator to false will cause the
	 *            generation of an additional option for no selection.
	 * @param selectedId
	 *            selected value's id
	 * @return selection list
	 */
	public static OptionList items2OLS(final Collection<? extends Node> items,
			final boolean required, final long selectedId)
	{
		return StrutsUtil.items2OLS(items, required, CommonNames.MISC.EMPTY_STRING,
				selectedId);
	}

	/**
	 * Convert a list of items to a drop-down selection list. The labels are the
	 * items' IDs and the values are their names.
	 * 
	 * @param items
	 *            list of item
	 * @param required
	 *            a boolean indicating whether or not input is required for this
	 *            field. Setting this indicator to false will cause the
	 *            generation of an additional option for no selection.
	 * @param emptySelectionValue
	 *            value of empty selection to use if required =
	 *            <code>false</code>
	 * @param selectedId
	 *            selected value's id
	 * @return selection list
	 */
	public static OptionList items2OLS(final Collection<? extends Node> items,
			final boolean required, final String emptySelectionValue,
			final long selectedId)
	{
		OptionListSource source;
		if (items == null)
		{
			source = OptionListSourceFactory.getOptionListSource(
					WebAppNames.OLS.TYPE_ARRAYS, null, null);
			// Do not provide a default option (e.g. "Choose one...")
			OptionList options = source.getOptions(true);
			return options;
		}
		else
		{
			// Convert list to OLS (option list)
			int numCatalogs = items.size();
			Object[] labels = new Object[numCatalogs];
			Object[] values = new Object[numCatalogs];
			int i = 0;
			for (Node catalog : items)
			{
				labels[i] = catalog.getName();
				values[i] = catalog.getId();
				i++;
			}
			source = OptionListSourceFactory.getOptionListSource(
					WebAppNames.OLS.TYPE_ARRAYS, labels, values);
			OptionList options = source.getOptions(required, emptySelectionValue);

			// Set selected value
			if (selectedId != CommonNames.MISC.INVALID_VALUE_INTEGER)
			{
				options.setSelectedValue(CommonNames.MISC.EMPTY_STRING + selectedId);
			}
			else
			{
				options.selectEmptySelection();
			}

			return options;
		}
	}

	/**
	 * Internationalize the empty selection options from an option list.
	 * 
	 * @param options
	 *            list of options (generated by the OLS framework)
	 * @param messageResources
	 *            resource bundle
	 * @param noSelectionKey
	 *            key to use for i18n message of the no-selection options. The
	 *            labels of all such options will be overridden.
	 * @return modified option list
	 */
	public static OptionList i18NEmptySelectionOptions(final OptionList options,
			final MessageResources messageResources, final String noSelectionKey)
	{
		// Modify empty selection label for internationalization
		for (Option vl : options)
		{
			if (vl.isEmpty())
			{
				// Found empty selection key, override label
				vl.setLabel(messageResources.getMessage(noSelectionKey));
			}
		}
		return options;
	}

	/**
	 * Generate an option list for an enumerated type, using application
	 * resources for label i18n.
	 * 
	 * @param options
	 *            existing options to i18nalize whose values are enumerated type
	 *            class names
	 * @param clazz
	 *            enumerated class type
	 * @param required
	 *            if false, will add an empty selection to the option list
	 * @param prefix
	 * @param messageResources
	 *            resource bundle
	 */
	public static void i18NEnumeratedSelection(final OptionList options,
			final Class<?> clazz, final String suffix, final String separator,
			final MessageResources messageResources, final String noSelectionKey)
	{
		// Modify the selection labels for internationalization
		for (Option vl : options)
		{
			if (TextUtil.isEmptyTrimmedString(vl.getValue()))
			{
				// Empty selection key
				vl.setLabel(messageResources.getMessage(noSelectionKey));
			}
			else
			{
				vl.setLabel(messageResources.getMessage(clazz.getCanonicalName()
						+ separator + vl.getValue() + separator + suffix));
			}
		}
	}

	/**
	 * Generate an option list for an enumerated type, using application
	 * resources for label i18n.
	 * 
	 * @param clazz
	 *            enumerated class type
	 * @param required
	 *            if false, will add an empty selection to the option list
	 * @param prefix
	 * @param messageResources
	 *            resource bundle
	 * @return
	 */
	public static OptionList i18NSelection(Class<?> clazz, boolean required,
			String suffix, String separator, MessageResources messageResources)
	{
		OptionListSource enumListOptions = OptionListSourceFactory.getOptionListSource(
				WebAppNames.OLS.TYPE_ENUM, clazz.getCanonicalName());
		OptionList options = enumListOptions.getOptions(required);
		StrutsUtil.i18NEnumeratedSelection(options, clazz, suffix, separator,
				messageResources, WebAppNames.KEY.MESSAGE.OLS_NO_SELECTION_LABEL);
		return options;
	}

	public static <E extends Enum<E>> Map<E, String> i18NMap(final Class<E> clazz,
			final String suffix, final String separator,
			final MessageResources messageResources)
	{
		final Map<E, String> map = new EnumMap<E, String>(clazz);
		for (E item : EnumSet.allOf(clazz))
		{
			map.put(item, messageResources.getMessage(clazz.getCanonicalName()
					+ separator + item.toString() + separator + suffix));
		}
		return map;
	}

	/**
	 * Convert OLS data to XML, including attribute tags.
	 * 
	 * @param groupId
	 *            id of the select group
	 * @param options
	 *            list drop-down menu options
	 * @return XML representation
	 */
	public static String options2Xml(final String groupId, final OptionList options)
	{
		StringBuffer xmlString = TextUtil.emptyStringBuffer();
		// Group opening tag
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put(WebAppNames.XML.ATTRIBUTE.ID, groupId);
		xmlString.append(XmlUtil.openTag(WebAppNames.XML.SELECT, attributes));

		// Append attributes
		for (Map.Entry<String, String> attribute : options.attributeEntrySet())
		{
			xmlString.append(XmlUtil.openTag(WebAppNames.XML.ATTRIB));
			xmlString.append(XmlUtil.fullTag(WebAppNames.XML.NAME, attribute.getKey()));
			xmlString
					.append(XmlUtil.fullTag(WebAppNames.XML.VALUE, attribute.getValue()));
			xmlString.append(XmlUtil.closeTag(WebAppNames.XML.ATTRIB));
		}

		// Append options
		for (Option option : options)
		{
			xmlString.append(XmlUtil.openTag(WebAppNames.XML.OPTION));
			xmlString.append(XmlUtil.fullTag(WebAppNames.XML.LABEL, option.getLabel()));
			xmlString.append(XmlUtil.fullTag(WebAppNames.XML.VALUE, option.getValue()));

			if (options.isSelected(option))
			{
				xmlString.append(XmlUtil.openTag(WebAppNames.XML.SELECTED_ATTRIBUTE));
				xmlString.append(WebAppNames.XML.SELECTED_VALUE);
				xmlString.append(XmlUtil.closeTag(WebAppNames.XML.SELECTED_ATTRIBUTE));
			}
			xmlString.append(XmlUtil.closeTag(WebAppNames.XML.OPTION));
		}

		// Group closing tag
		xmlString.append(XmlUtil.closeTag(WebAppNames.XML.SELECT));
		return xmlString.toString();
	}

	// ------------------------- FORM-RELATED METHODS ----------------------

	/**
	 * Print a form bean using our custom formatter.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public static String formToString(final Object object)
	{
		ToStringStyle formatter = new FormToStringStyle();
		return ReflectionToStringBuilder.toString(object, formatter);
	}

	// ------------------------- RESOURCE LOCATORS -------------------------

	/**
	 * Convenience method for retrieving the system user account
	 * 
	 * @return the system user's user object
	 */
	public final User getSystemUser()
	{
		return SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
	}
}
