/*****************************************************************************************
 * Source File: AddQuestionTagFilter.java
 ****************************************************************************************/
package net.ruready.web.content.question.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.EnumUtil;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.entity.WebScope;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.question.form.EditQuestionForm;
import net.ruready.web.content.question.form.EditQuestionFullForm;
import net.ruready.web.content.question.util.EditQuestionUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;

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
public class AddQuestionTagFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AddQuestionTagFilter.class);

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
	public AddQuestionTagFilter(FilterDefinition filterDefinition)
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
		EditQuestionFullForm form = (EditQuestionFullForm) HttpRequestUtil.getAttribute(
				webScope, attribute, null, request, session, application);
		if (form == null)
		{
			throw new ApplicationException("Did not find form");
		}
		EditQuestionForm editQuestionForm = EditQuestionUtil.getQuestionForm(form);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// ############################################################
		// Fetch and place the subject ID in the session if not found
		// ############################################################
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);
		long subjectId = TextUtil.getStringAsLong((String) session
				.getAttribute(WebAppNames.SESSION.ATTRIBUTE.SUBJECT_ID));
		if (!TextUtil.isValidId(subjectId))
		{
			Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
			String parentIdStr = request
					.getParameter(WebAppNames.REQUEST.PARAM.ITEM_PARENT_ID);
			if (parentIdStr != null)
			{
				HttpRequestUtil.setSessionToken(request,
						WebAppNames.REQUEST.PARAM.ITEM_PARENT_ID);
			}
			else if (item != null)
			{
				parentIdStr = CommonNames.MISC.EMPTY_STRING + item.getParentId();
				// Make sure to save a String-valued token!
				session.setAttribute(WebAppNames.REQUEST.PARAM.ITEM_PARENT_ID,
						parentIdStr);
			}

			long parentId = TextUtil.getStringAsLong(parentIdStr);
			Item parent = bdItem.read(Item.class, parentId);
			subjectId = parent.getSuperParent(ItemType.SUBJECT).getId();
			// Make sure to save a String-valued token!
			session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.SUBJECT_ID,
					CommonNames.MISC.EMPTY_STRING + subjectId);
			if (logger.isDebugEnabled())
			{
				logger.debug("Attaching subject ID " + subjectId + " to session");
			}
		}

		Subject subject = bdItem.read(Subject.class, subjectId);

		// -------------------------------------------
		// Attach results to request
		// Attach business object handles into form
		// -------------------------------------------

		// Attach results to form
		editQuestionForm.setSubject(subject);

		// Form is already attached to the request and we just updated its
		// internals. Nothing to do for the request.

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
