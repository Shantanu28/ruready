/*****************************************************************************************
 * Source File: AddQuestionStaticMenusFilter.java
 ****************************************************************************************/
package net.ruready.web.content.question.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.exception.SystemException;
import net.ruready.common.util.EnumUtil;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.entity.WebScope;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.question.form.EditQuestionFullForm;
import net.ruready.web.content.question.form.QuestionStaticMenuForm;
import net.ruready.web.content.question.form.SearchQuestionForm;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.util.MessageResources;

/**
 * Adds data for a drop-down menus related to question properties. Should be
 * employed before the edit question page is displayed. These menus contain
 * static data that only depends on the code and the application resource
 * message bundle, e.g., question type descriptions.
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
public class AddQuestionStaticMenusFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(AddQuestionStaticMenusFilter.class);

	// ========================= FIELDS ====================================

	/**
	 * Init parameter attribute (name of form bean) to retrieve.
	 */
	private String attribute;

	/**
	 * Init parameter attribute (name of form bean) to retrieve.
	 */
	@SuppressWarnings("unused")
	private String scope;

	/**
	 * Scope of form bean. Must be a valid web scope. Derived from the scope
	 * parameter; there is no direct setter for it.
	 */
	private WebScope webScope;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions.
	 * Sub-classes should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public AddQuestionStaticMenusFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);

		// attribute is required; perform check
		this.checkRequiredInitParam("attribute", attribute);

		// scope is required; perform check
		this.checkRequiredInitParam("scope", scope);
	}

	// ========================= IMPLEMENTATION: FilterAction ==============

	/**
	 * Add a child item type drop-down menu data to the request.
	 * 
	 * @param saContext
	 * @param action
	 * @param mapping
	 * @param formIgnored
	 * @param request
	 * @param response
	 * @return
	 * @see net.ruready.web.chain.filter.FilterAction#doFilter(org.apache.struts.chain.contexts.ServletActionContext,
	 *      org.apache.struts.action.Action,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doFilter(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm formIgnored,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		// ---------------------------------------
		// Read data from request & parameters
		// ---------------------------------------
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);
		HttpSession session = request.getSession(false);

		// Retrieve form from web scope
		// Note: we don't look for form in page scope
		ServletContext application = saContext.getActionServlet().getServletContext();
		Object form = HttpRequestUtil.getAttribute(webScope, attribute, null, request,
				session, application);

		// TODO: replace the following switch by reflection
		if (form == null)
		{
			form = AddQuestionStaticMenusFilter.createForm(attribute);
			HttpRequestUtil.setAttribute(webScope, attribute, form, null, request,
					session, application);
		}

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		QuestionStaticMenuForm qForm = null;
		boolean validForm = true;
		try
		{
			if ("editQuestionFullForm".equals(attribute))
			{
				qForm = (QuestionStaticMenuForm) ((EditQuestionFullForm) form)
						.getItemForm();
			}
			else
			{
				qForm = (QuestionStaticMenuForm) form;
			}
			validForm = (qForm != null);
		}
		catch (Exception e)
		{
			logger.warn("Invalid form class or form not found in scope");
			validForm = false;
		}

		// Use the OLS framework to construct a Option option list for
		// each property drop-down menu
		MessageResources messageResources = (MessageResources) application
				.getAttribute(Globals.MESSAGES_KEY);
		if (validForm)
		{
			this.populateMenus(request, session, context, user, qForm, messageResources);
		}

		// -------------------------------------------
		// Attach results to request
		// -------------------------------------------

		// Attach default form to appropriate scope.
		// Note: this is the FULL form, not qForm, which might only be a nested
		// field of
		// form.
		HttpRequestUtil.setAttribute(webScope, attribute, form, null, request, session,
				application);
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Add question static menu data";
	}

	// ========================= METHODS ===================================

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Populate drop-down menus. Struts forward mapping context object
	 * 
	 * @param request
	 *            client's request object
	 * @param session
	 *            client's session object
	 * @param context
	 *            web application context object
	 * @param user
	 *            user requesting the business operations
	 * @param form
	 *            Struts form bean attached to this action/filter.
	 * @param messageResources
	 *            request's Struts resource bundle
	 */
	private void populateMenus(final HttpServletRequest request,
			final HttpSession session, final WebApplicationContext context,
			final User user, final QuestionStaticMenuForm form,
			final MessageResources messageResources)
	{
		logger.debug("Populating drop-down menu data");

		// ====================================================================
		// Use the OLS framework to populate drop-down menus (option lists)
		// ====================================================================
		// Modify the selection labels for internationalization

		String separator = WebAppNames.KEY.MESSAGE.SEPARATOR;
		String suffix = WebAppNames.KEY.MESSAGE.OLS_ENUM_OPTION_SUFFIX;

		// Question types
		OptionList questionTypeOptions = StrutsUtil.i18NSelection(
				net.ruready.business.content.question.entity.property.QuestionType.class,
				true, suffix, separator, messageResources);

		// ---------------------------------------
		// Attach results to form
		// ---------------------------------------
		form.setQuestionTypeOptions(questionTypeOptions);
	}

	/**
	 * A factory method that creates a form.
	 * 
	 * @param attribute
	 *            indicates the form type
	 * @return a form
	 */
	private static Object createForm(final String attribute)
	{
		if ("editQuestionFullForm".equals(attribute))
		{
			return new EditQuestionFullForm();
		}
		else if ("searchQuestionForm".equals(attribute))
		{
			return new SearchQuestionForm();
		}
		else
		{
			throw new SystemException("Unsupported form type " + attribute);
		}
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Sets a new attribute property value.
	 * 
	 * @param attribute
	 *            the attribute to set
	 */
	public void setAttribute(String attribute)
	{
		this.attribute = attribute;
	}

	/**
	 * Sets a new scope property value.
	 * 
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope)
	{
		this.scope = scope;

		// Type conversion
		webScope = EnumUtil.createFromString(WebScope.class, scope);
		if (scope == null)
		{
			throw new IllegalArgumentException("init parameter value 'scope' = " + scope
					+ " is invalid for filter " + getClass().getSimpleName());
		}
	}
}
