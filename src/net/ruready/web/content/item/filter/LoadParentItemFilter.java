/*****************************************************************************************
 * Source File: LoadParentItemFilter.java
 ****************************************************************************************/
package net.ruready.web.content.item.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;

/**
 * Load parent item into a request attribute by ID. The ID is specified by
 * request parameter.
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
 * @version Jul 28, 2007
 */
public class LoadParentItemFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(LoadParentItemFilter.class);

	// ========================= FIELDS ====================================

	/**
	 * Request parameter name to retrieve.
	 */
	private String paramName;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions.
	 * Sub-classes should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public LoadParentItemFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);

		// paramName is required; perform check
		this.checkRequiredInitParam("paramName", paramName);
	}

	// ========================= IMPLEMENTATION: FilterAction ==============

	/**
	 * Add a child item type drop-down menu data to the request.
	 * 
	 * @param saContext
	 * @param action
	 * @param mapping
	 * @param form
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
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		// ---------------------------------------
		// Read data from request & parameters
		// ---------------------------------------
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);
		long parentId = HttpRequestUtil.getParameterAsLong(request, paramName);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------
		// Load parent item
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);
		Item item = bdItem.read(Item.class, parentId);

		// -------------------------------------------
		// Attach results to request
		// -------------------------------------------
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, item);

		// ---------------------------------------
		// Success: continue processing chain
		// ---------------------------------------
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Load parent item";
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Sets a new paramName property value.
	 * 
	 * @param paramName
	 *            the paramName to set
	 */
	public void setParamName(String paramName)
	{
		this.paramName = paramName;
	}
}
