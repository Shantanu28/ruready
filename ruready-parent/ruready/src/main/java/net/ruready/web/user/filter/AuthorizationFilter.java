/*****************************************************************************************
 * Source File: AuthorizationFilter.java
 ****************************************************************************************/
package net.ruready.web.user.filter;

import static org.apache.commons.lang.StringUtils.defaultString;
import static org.apache.commons.lang.StringUtils.trim;
import static org.apache.commons.lang.Validate.notNull;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.entity.property.UserStatus;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.text.TextUtil;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.user.imports.StrutsUserBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.chain.contexts.ServletActionContext;

/**
 * Apply security policy (roles) and decide whether user is authorized to access
 * the requested URL.
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
 * @version Aug 11, 2007
 */
public class AuthorizationFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AuthorizationFilter.class);

	/**
	 * Possible authentication outcomes.
	 */
	private enum AuthorizationResult
	{
		AUTHENTICATION_REQUIRED, ACCESS_DENIED, SUCCESS, LOCKED
	}

	/**
	 * Name of Authentication URL property.
	 */
	protected static final String AUTHENTICATION_URL = "authenticationUrl";

	/**
	 * Default value of error page forward property.
	 */
	protected static final String ERROR_PAGE_ACCESS_DENIED = "errorAccessDenied";

	// ========================= FIELDS ====================================

	/**
	 * Authentication URL (login page). Has a default value
	 * (convention-over-configuration pattern).
	 */
	private String authenticationUrl = AUTHENTICATION_URL;

	/**
	 * Comma-delimited list of permitted roles to access the filtered URLs.
	 */
	protected String roles;

	/**
	 * Permitted roles to access the filtered URLs.
	 */
	private Set<RoleType> roleSet;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions.
	 * Sub-classes should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public AuthorizationFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);

		// roles is required; perform check
		this.checkRequiredInitParam("roles", roles);

		// Type conversion of role list
		roleSet = getRolesFromInitString(roles);
		// logger.debug("Filter roles set to: " + roles);
	}

	// ========================= IMPLEMENTATION: FilterAction ==============

	/**
	 * Filter a URL: authenticate and authorize the user to access the URL, or
	 * deny access.
	 * 
	 * @param saContext
	 * @param action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see net.ruready.web.chain.filter.FilterAction#doFilter(org.apache.struts.chain.contexts.ServletActionContext,
	 *      org.apache.struts.action.Action,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doFilter(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception
	{
		final WebApplicationContext context = StrutsUtil
				.getWebApplicationContext(request);
		// Create a new HTTP session
		final HttpSession session = request.getSession(true);

		final AuthorizationResult result = authenticate(request, response, session,
				context);
		final ActionErrors errors = new ActionErrors();
		if (logger.isDebugEnabled())
		{
			logger.debug("User authentication result: " + result);
		}

		switch (result)
		{
			case AUTHENTICATION_REQUIRED:
			{
				// ---------------------------------------
				// User not yet authenticated; go to login
				// page and give a chance to authenticate
				// ---------------------------------------
				// Prepare a new login form Use requested path as the bookmark.
				// Save query parameters as well. Save the entire URL because
				// we will redirect (not forward) to the bookmark.

				// Save requested book-mark as a session token because we'll
				// redirect instead of forward to the main page due to Apache
				// SSL/no-ssl URL rewrite usage restrictions.
				session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.TOKEN.BOOKMARK,
						createBookmark(request));
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.accessDenied.authenticationrequired"));

				// Can't add the errors to the request right now, because we'll
				// redirect to the authentication page and lose the error. Save
				// it in the session (temporarily).
				// req.setAttribute(Globals.ERROR_KEY, errors);
				// TODO put errors in Struts error session object so we don't
				// have to manage this
				session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.ERRORS, errors);

				// This forwards to an authentication JSP that has a Struts
				// redirection tag to the home page. This is when we switch to
				// SSL.
				return mapping.findForward(authenticationUrl);
			}

			case LOCKED:
			{
				// same as AUTHENTICATION_REQUIRED, only return a different
				// error
				session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.TOKEN.BOOKMARK,
						createBookmark(request));
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.accessDenied.accountLocked"));
				session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.ERRORS, errors);
				return mapping.findForward(authenticationUrl);
			}

			case ACCESS_DENIED:
			{
				// ---------------------------------------
				// User authenticated but access denied,
				// go to error page
				// ---------------------------------------
				// Remove user and book-mark from session so that they can log
				// in
				// again
				session.removeAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_ID);
				// session.removeAttribute(WebAppNames.SESSION.ATTRIBUTE.TOKEN.BOOKMARK);

				return mapping.findForward(errorPage);
			}

			case SUCCESS:
			{
				// ---------------------------------------
				// Success: Go to requested URL stored in
				// the session-scope token, if found
				// ---------------------------------------
				// Look for user in the session
				String bookmark = HttpRequestUtil.getAndRemoveSessionToken(session,
						WebAppNames.SESSION.ATTRIBUTE.TOKEN.BOOKMARK);
				session.removeAttribute(WebAppNames.SESSION.ATTRIBUTE.ERRORS);

				// if (TextUtil.isEmptyTrimmedString(user.getBookmark()))
				if (TextUtil.isEmptyTrimmedString(bookmark))
				{
					bookmark = createBookmark(request);
					logger.debug("Continuing filter chain, requested URL " + bookmark);
					return null;
				}
				else
				{
					// User has been authenticated for the first time in this
					// session. Redirect back to the book-mark. This will create
					// a new request so we'll come back to this filter and
					// continue the chain of required filters for the URL.
					logger.debug("Redirecting to requested URL " + bookmark);
					response.sendRedirect(bookmark);
					return null;
				}
			}
		}

		throw new ApplicationException(
				"Authorization filter ended without an authorization result");
	}

	private String createBookmark(final HttpServletRequest request)
	{
		final StringBuffer bookmark = new StringBuffer(request.getRequestURL());
		if (!TextUtil.isEmptyTrimmedString(request.getQueryString()))
		{
			bookmark.append(WebAppNames.HTML.URL_SEPARATOR);
			bookmark.append(request.getQueryString());
		}
		return bookmark.toString();
	}

	// ========================= METHODS ===================================

	/**
	 * Attempt to authenticate and authorize the user to access the requested
	 * URL.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param context
	 * @return
	 */
	private AuthorizationResult authenticate(final HttpServletRequest request,
			final HttpServletResponse response, final HttpSession session,
			final WebApplicationContext context)
	{
		// Look for user in the session
		final Long userId = (Long) session
				.getAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_ID);
		if (userId == null)
		{
			// Authentication failed, go to login page
			return AuthorizationResult.AUTHENTICATION_REQUIRED;
		}

		final AbstractUserBD bdUser = new StrutsUserBD(context);
		User user = bdUser.findById(userId);
		if (user == null)
		{
			// Corrupt user bean
			return AuthorizationResult.AUTHENTICATION_REQUIRED;
		}
		if (user.getUserStatus() == UserStatus.LOCKED)
		{
			// user is locked
			return AuthorizationResult.LOCKED;
		}

		// Save the user in the request
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.USER, user);

		// if (logger.isDebugEnabled())
		// {
		// logger.debug("Filter roles: " + roles);
		// logger.debug("User roles: " + user.getRoles());
		// }

		// Authentication succeeded, but user not authorized to view the
		// requested URL.
		if (!user.hasAnyRole(roleSet))
		{
			return AuthorizationResult.ACCESS_DENIED;
		}

		return AuthorizationResult.SUCCESS;
	}

	/**
	 * Takes in a {@link String} of comma-delimited values and converts them
	 * into a {@link Set} of {@link RoleType} enumerated elements. SPECIAL CASE:
	 * if initString contains the value "ALL", then all roles are added to the
	 * set of roles that are returned.
	 * 
	 * @param initString
	 *            a comma-delimited String of roles
	 * @return The set of {@link RoleType} enumerated elements
	 * @throws IllegalArgumentException
	 *             if initString is null or if one of the roles in the list is
	 *             not a role type
	 */
	private Set<RoleType> getRolesFromInitString(final String initString)
	{
		notNull(initString, "roles cannot be null.");
		final String[] rolesAsString = defaultString(trim(initString)).split("\\s*,\\s*");
		@SuppressWarnings("hiding")
		final Set<RoleType> roles = new HashSet<RoleType>();
		for (String roleAsString : rolesAsString)
		{
			try
			{
				roles.add(RoleType.valueOf(roleAsString));
			}
			catch (final IllegalArgumentException e)
			{
				// special case: role "ALL" equals all roles
				if (roleAsString.equalsIgnoreCase("ALL"))
				{
					roles.addAll(EnumSet.allOf(RoleType.class));
				}
				else
				{
					throw new IllegalArgumentException("\"" + roleAsString
							+ "\" is not a valid role type", e);
				}
			}
		}
		return roles;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Authorization";
	}

	// ========================= HOOKS =====================================

	/**
	 * Set init parameter defaults (convention-over-configuration pattern).
	 */
	@Override
	protected void reset()
	{
		super.reset();
		// Set our custom default values for init parameters
		errorPage = ERROR_PAGE_ACCESS_DENIED;
		authenticationUrl = AUTHENTICATION_URL;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @param authenticationUrl
	 *            the authenticationUrl to set
	 */
	public void setAuthenticationUrl(String authenticationUrl)
	{
		this.authenticationUrl = authenticationUrl;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(String roles)
	{
		this.roles = roles;
	}

}
