/*****************************************************************************************
 * Source File: PrepareViewItemFilter.java
 ****************************************************************************************/
package net.ruready.web.content.item.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.EditItemForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;

/**
 * Attach the necessary objects to the request for an item view page.
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
 * @version Jul 30, 2007
 */
public class PrepareViewItemFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(PrepareViewItemFilter.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions.
	 * Sub-classes should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public PrepareViewItemFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);
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
		// The form may be of type EditItemForm or EditItemFullForm.
		EditItemForm editItemForm = (EditItemForm) request.getAttribute("editItemForm");
		logger.debug("editItemForm bean: " + editItemForm);
		// The FindItemFilter should have attached an item
		// to the request by now
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		// User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		// Copy results from most updated entity to form. If form doesn't
		// yet exist (not placed yet by Struts), create a new one
		if (editItemForm == null)
		{
			editItemForm = new EditItemForm();
			request.setAttribute("editItemForm", editItemForm);
		}
		editItemForm.copyFrom(item, context);
		logger.debug("editItemForm bean after copying from item: " + editItemForm);

		// Continue the processing chain
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Pre-processing before an item view page";
	}
}
