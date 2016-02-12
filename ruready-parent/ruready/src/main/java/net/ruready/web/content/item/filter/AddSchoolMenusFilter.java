/*****************************************************************************************
 * Source File: AddSchoolMenusFilter.java
 ****************************************************************************************/
package net.ruready.web.content.item.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.world.entity.property.InstitutionType;
import net.ruready.business.content.world.entity.property.Sector;
import net.ruready.business.user.entity.User;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.EditItemForm;
import net.ruready.web.content.item.form.EditItemFullForm;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.util.MessageResources;

/**
 * Adds data for a drop-down menus related to school properties. Should be
 * employed before editing pages.
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
public class AddSchoolMenusFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AddSchoolMenusFilter.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions.
	 * Sub-classes should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public AddSchoolMenusFilter(FilterDefinition filterDefinition)
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
		// WebApplicationContext context =
		// StrutsUtil.getWebApplicationContext(request);
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		if (item.getIdentifier() != ItemType.SCHOOL)
		{
			// This filter only applies to school items
			return null;
		}

		// -------------------------------------------
		// Read data from request
		// -------------------------------------------

		EditItemFullForm editItemFullForm = (EditItemFullForm) request
				.getAttribute("editItemFullForm");

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// Reset the form parameters to default values (this is not the same as
		// Struts' reset() method)
		if (editItemFullForm == null)
		{
			// Request-scope form, instantiate a new form and reset its fields
			editItemFullForm = new EditItemFullForm();
			request.setAttribute("editItemFullForm", editItemFullForm);
		}

		// Populate the menus
		MessageResources messageResources = this.getResources(request);
		User user = HttpRequestUtil.findUser(request);
		this.populateMenus(request, editItemFullForm.getItemForm(), user,
				messageResources);
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Add school drop-down menu data";
	}

	// ========================= METHODS ===================================

	/**
	 * A helper method that populates drop-down menus.
	 * 
	 * @param request
	 *            client's request object
	 * @param editItemForm
	 *            Struts form bean attached to this action/filter.
	 * @param user
	 *            user requesting this operation
	 * @param messageResources
	 *            request's Struts resource bundle
	 */
	private void populateMenus(final HttpServletRequest request,
			final EditItemForm editItemForm, final User user,
			final MessageResources messageResources)
	{
		// ---------------------------------------
		// Read data from view, retrieve beans
		// ---------------------------------------

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		// Use the OLS framework to construct a Option option list for
		// each school property drop-down menu

		String separator = WebAppNames.KEY.MESSAGE.SEPARATOR;
		String suffix = WebAppNames.KEY.MESSAGE.OLS_ENUM_OPTION_SUFFIX;

		// InstitutionType
		OptionList institutionTypeOptions = StrutsUtil.i18NSelection(
				InstitutionType.class, false, suffix, separator, messageResources);

		// Sector
		OptionList sectorOptions = StrutsUtil.i18NSelection(Sector.class, false, suffix,
				separator, messageResources);

		// ---------------------------------------
		// Attach results to form
		// ---------------------------------------
		editItemForm.setInstitutionTypeOptions(institutionTypeOptions);
		editItemForm.setSectorOptions(sectorOptions);

	} // populateCountries()
}
