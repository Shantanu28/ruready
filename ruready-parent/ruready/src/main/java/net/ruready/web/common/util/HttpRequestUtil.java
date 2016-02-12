/*****************************************************************************************
 * Source File: StrutsRequestUtil.java
 ****************************************************************************************/
package net.ruready.web.common.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.exception.AuthenticationException;
import net.ruready.common.exception.SystemException;
import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.web.common.entity.WebScope;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities related to HTTP requests in Struts.
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
 * @version Aug 7, 2007
 */
public class HttpRequestUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HttpRequestUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private HttpRequestUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	// ------------------------- DEBUGGING PRINTOUTS -----------------------

	/**
	 * Print all request parameters.
	 * 
	 * @param request
	 *            Client's request object
	 */
	public static void printRequestParameters(final HttpServletRequest request)
	{
		Enumeration<?> parameters = request.getParameterNames();
		final StringBuffer s = TextUtil.emptyStringBuffer();
		StringBuffer line = TextUtil.emptyStringBuffer();
		while (parameters.hasMoreElements())
		{
			String parameterName = (String) parameters.nextElement();
			// logger.info("Parameter Name: " + parameterName);
			// logger.info("Parameter Value: " +
			// request.getParameter(parameterName));
			line.append(parameterName).append("=").append(
					request.getParameter(parameterName));
			if (parameters.hasMoreElements())
			{
				line.append(", ");
			}
			if ((line.length() > 50) || (!parameters.hasMoreElements()))
			{
				s.append(line).append(CommonNames.MISC.NEW_LINE_CHAR);
				line = TextUtil.emptyStringBuffer();
			}
		}
		// logger
		// .info("Request parameters\:n\n################## Http request
		// parameters:
		// ##################\n"
		// + s);
		logger.info("Request parameters: " + s);
	}

	/**
	 * Print all request parameters to a string.
	 * 
	 * @param request
	 *            Client's request object
	 * @return string representation of the request parameter enumeration
	 */
	public static String requestParametersToString(final HttpServletRequest request)
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		Enumeration<?> parameters = request.getParameterNames();
		while (parameters.hasMoreElements())
		{
			String parameterName = (String) parameters.nextElement();
			s.append("Name: " + parameterName + CommonNames.MISC.TAB_CHAR + "Value: "
					+ request.getParameter(parameterName));
			s.append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		return s.toString();
	}

	/**
	 * Print all request attributes.
	 * 
	 * @param request
	 *            Client's request object
	 */
	public static void printRequestAttributes(final HttpServletRequest request)
	{
		logger.info("Printing request attributes:");
		Enumeration<?> e = request.getAttributeNames();
		for (; e.hasMoreElements();)
		{
			// Get the name of the attribute
			String name = (String) e.nextElement();
			// Get the value of the attribute
			Object value = /* (String) */request.getAttribute(name);
			logger.info("Attribute name = " + name + " value = " + value);
		}
	}

	/**
	 * Print all request's session attributes.
	 * 
	 * @param request
	 *            Client's request object
	 */
	public static void printSessionAttributes(final HttpServletRequest request)
	{
		logger.info("Printing session attributes:");
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			Enumeration<?> e = session.getAttributeNames();
			while (e.hasMoreElements())
			{
				// Get the name of the attribute
				String name = (String) e.nextElement();
				// Get the value of the attribute
				Object value = session.getAttribute(name);
				logger.info("Attribute name = " + name + " value = " + value);
			}
		}
	}

	/**
	 * Print all request's application (ServletContext) attributes.
	 * 
	 * @param request
	 *            Client's request object
	 */
	public static void printApplicationAttributes(final HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			return;
		}
		printApplicationAttributes(session.getServletContext());
	}

	/**
	 * Print all application attributes.
	 * 
	 * @param servletContext
	 *            Applcation servlet context object.
	 */
	public static void printApplicationAttributes(final ServletContext servletContext)
	{
		logger.info("Printing serverContext attributes:");
		Enumeration<?> attributes = servletContext.getAttributeNames();
		while (attributes.hasMoreElements())
		{
			String attribute = (String) attributes.nextElement();
			logger.info("Attribute name : " + attribute);
			logger.info("Attribute value: " + servletContext.getAttribute(attribute));
		}
		logger.info("Major version : " + servletContext.getMajorVersion());
		logger.info("Minor version : " + servletContext.getMinorVersion());
		logger.info("Server info : " + servletContext.getServerInfo());
	}

	/**
	 * Dumps request scoped attributes and parameters to debug stream.
	 * 
	 * @author Greg Felice
	 */
	public static void dumpVars(final HttpServletRequest request)
	{
		logger.debug("=================================================");
		logger.debug("                DUMP VARS BEGIN                  ");
		logger.debug("=================================================");

		logger.debug("Request Scoped Parameters");
		logger.debug("-------------------------");
		String s;
		StringBuffer sb = new StringBuffer();
		String[] t;
		for (Enumeration<?> e = request.getParameterNames(); e.hasMoreElements();)
		{
			s = (String) e.nextElement();
			sb.append(s);
			sb.append("=");
			t = request.getParameterValues(s);
			for (int i = 0; i < t.length; i++)
				sb.append(t[i]);
			sb.append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		logger.debug(sb.toString());

		sb = new StringBuffer();
		logger.debug("Request Scoped Attributes");
		logger.debug("-------------------------");
		Object o;
		for (Enumeration<?> e = request.getParameterNames(); e.hasMoreElements();)
		{
			s = (String) e.nextElement();
			sb.append(s);
			sb.append("=");
			o = request.getAttribute(s);
			sb.append(o);
			sb.append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		logger.debug(sb.toString());

		logger.debug("=================================================");
		logger.debug("                DUMP VARS END                    ");
		logger.debug("=================================================");
	}

	// ------------------------- TOKENS AND ATTRIBUTES ---------------------

	/**
	 * Set a token value specified as a request parameter as a request
	 * attribute. The token value is a string.
	 * 
	 * @param request
	 *            client's request
	 * @param tokenName
	 *            name of token parameter and session attribute.
	 */
	public static void setRequestToken(HttpServletRequest request, final String tokenName)
	{
		String tokenValue = request.getParameter(tokenName);
		request.setAttribute(tokenName, tokenValue);
		logger.debug("Set request token [" + tokenName + CommonNames.TREE.SEPARATOR
				+ tokenValue + "]");
	}

	/**
	 * Set a token value specified as a request parameter as a session
	 * attribute. The token value is a string.
	 * 
	 * @param request
	 *            client's request
	 * @param tokenName
	 *            name of token parameter and session attribute.
	 */
	public static void setSessionToken(HttpServletRequest request, final String tokenName)
	{
		String tokenValue = request.getParameter(tokenName);
		HttpSession session = request.getSession(true);
		session.setAttribute(tokenName, tokenValue);
		logger.debug("Set session token [" + tokenName + CommonNames.TREE.SEPARATOR
				+ tokenValue + "]");
	}

	/**
	 * Get a token value from the request attributes and remove it from the
	 * ession. The token value is a string.
	 * 
	 * @param request
	 *            client's request
	 * @param tokenName
	 *            name of token parameter and session attribute.
	 */
	public static String getAndRemoveSessionToken(ServletRequest request,
			final String tokenName)
	{
		String tokenValue = (String) request.getAttribute(tokenName);
		request.removeAttribute(tokenName);
		logger.debug("Removed session token [" + tokenName + CommonNames.TREE.SEPARATOR
				+ tokenValue + "]");
		return tokenValue;
	}

	/**
	 * Get a token value from the session attributes and remove it from the
	 * ession. The token value is a string.
	 * 
	 * @param session
	 *            client's session object
	 * @param tokenName
	 *            name of token parameter and session attribute.
	 */
	public static String getAndRemoveSessionToken(HttpSession session,
			final String tokenName)
	{
		String tokenValue = (String) session.getAttribute(tokenName);
		// if (tokenValue == null)
		// {
		// logger.debug("Session token [" + tokenName +
		// CommonNames.TREE.SEPARATOR
		// + tokenValue + "]" + " not found");
		// }
		session.removeAttribute(tokenName);
		// logger.debug("Removed session token [" + tokenName +
		// CommonNames.TREE.SEPARATOR
		// + tokenValue + "]");
		return tokenValue;
	}

	/**
	 * Get the user from the existing session.
	 * 
	 * @param request
	 *            client's request object
	 * @return user object
	 */
	public static User findUser(HttpServletRequest request)
	{
		return (User) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.USER);
	}

	/**
	 * Get the user from the existing session. If not found, throws an
	 * {@link AuthenticationException}.
	 * 
	 * @param request
	 *            client's request object
	 * @return user object, always non-<code>null</code>
	 * @throws AuthenticationException
	 */
	public static User mustFindUser(HttpServletRequest request)
	{
		User user = (User) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.USER);
		if (user == null)
		{
			throw new AuthenticationException("Authentication required",
					CommonNames.MISC.NOT_APPLICABLE, CommonNames.MISC.NOT_APPLICABLE,
					AuthenticationException.TYPE.LOGIN_FAILED);
		}
		return user;
	}

	/**
	 * Retrieve attribute from a web scope.
	 * 
	 * @param scope
	 *            web scope (page, request, session or application).
	 * @param attributeName
	 *            name of attribute
	 * @return attribute value object
	 */
	public static Object getAttribute(final WebScope scope, final String attributeName,
			final PageContext page, final HttpServletRequest request,
			final HttpSession session, final ServletContext application)
	{
		switch (scope)
		{
			case PAGE:
			{
				return (page == null) ? null : page.getAttribute(attributeName);
			}

			case REQUEST:
			{
				return (request == null) ? null : request.getAttribute(attributeName);
			}

			case SESSION:
			{
				return (session == null) ? null : session.getAttribute(attributeName);
			}

			case APPLICATION:
			{
				return (application == null) ? null : application
						.getAttribute(attributeName);
			}

			default:
			{
				throw new SystemException("Unsupported web scope " + scope);
			}
		}
	}

	/**
	 * Set an attribute in a web scope.
	 * 
	 * @param scope
	 *            web scope (page, request, session or application).
	 * @param attributeName
	 *            name of attribute
	 * @param attributeValue
	 *            value of attribute
	 */
	public static void setAttribute(final WebScope scope, final String attributeName,
			final Object attributeValue, PageContext page, HttpServletRequest request,
			HttpSession session, ServletContext application)
	{
		switch (scope)
		{
			case PAGE:
			{
				if (page != null)
				{
					page.setAttribute(attributeName, attributeValue);
				}
				break;
			}

			case REQUEST:
			{
				if (request != null)
				{
					request.setAttribute(attributeName, attributeValue);
				}
				break;
			}

			case SESSION:
			{
				if (session != null)
				{
					session.setAttribute(attributeName, attributeValue);
				}
				break;
			}

			case APPLICATION:
			{
				if (application != null)
				{
					application.setAttribute(attributeName, attributeValue);
				}
				break;
			}

			default:
			{
				throw new SystemException("Unsupported web scope " + scope);
			}
		}
	}

	// ------------------------- CONVERSION UTILITIES ----------------------

	/**
	 * Convert request parameter to integer.
	 * 
	 * @param request
	 *            HTTP request
	 * @param name
	 *            parameter name
	 * @return integer value of the parameter. If failed to convert, returns
	 *         <code>INVALID_VALUE_INTEGER</code>
	 */
	public static int getParameterAsInteger(HttpServletRequest request, final String name)
	{
		return TextUtil.getStringAsInteger(request.getParameter(name));
	}

	/**
	 * Convert request parameter to long.
	 * 
	 * @param request
	 *            HTTP request
	 * @param name
	 *            parameter name
	 * @return long value of the parameter. If failed to convert, returns
	 *         <code>INVALID_VALUE_LONG</code>
	 */
	public static long getParameterAsLong(HttpServletRequest request, final String name)
	{
		return TextUtil.getStringAsLong(request.getParameter(name));
	}

	/**
	 * Convert request parameter to boolean.
	 * 
	 * @param request
	 *            HTTP request
	 * @param name
	 *            parameter name
	 * @return long value of the parameter. If failed to convert, returns
	 *         <code>false</code>
	 */
	public static boolean getParameterAsBoolean(HttpServletRequest request,
			final String name)
	{
		return TextUtil.getStringAsBoolean(request.getParameter(name));
	}

	/**
	 * Converts map keys to their string representations. Assumes all key string
	 * representations are different from each other.
	 * 
	 * @param original
	 *            original map
	 * @return a copy of the original map where each key is replaced by its
	 *         string representation.
	 */
	public static Map<String, Object> toStringKeys(Map<?, ?> original)
	{
		Map<String, Object> copy = new HashMap<String, Object>();
		for (Map.Entry<?, ?> entry : original.entrySet())
		{
			copy.put(entry.getKey().toString(), entry.getValue());
		}
		return copy;
	}

	/**
	 * Prepare an XML response (usually a response from an AJAX action).
	 * 
	 * @param response
	 *            server's XML response
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static void prepareXMLResponse(final HttpServletResponse response)
			throws IOException
	{
		response.setContentType("application/xml");
		// response.getWriter().write("<?xml version='1.0'
		// encoding='utf-8'?>\n");
	}
}
