/*****************************************************************************************
 * Source File: AddParserDemoMenusFilter.java
 ****************************************************************************************/
package net.ruready.web.parser.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.parser.form.DemoForm;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.util.MessageResources;

/**
 * Adds data for a drop-down menus required by the parser demo page. Filters the parser
 * demo actions as a setup action.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 3, 2007
 */
public class AddParserDemoMenusFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AddParserDemoMenusFilter.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions. Sub-classes
	 * should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public AddParserDemoMenusFilter(FilterDefinition filterDefinition)
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
	 *      org.apache.struts.action.Action, org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doFilter(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		// -------------------------------------------
		// Read data from request
		// -------------------------------------------
		// WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		DemoForm demoForm = (DemoForm) request.getAttribute("demoForm");

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// Reset the form parameters to default values (this is not the same as
		// Struts' reset() method)
		if (demoForm == null)
		{
			// Request-scope form, instantiate a new form and reset its fields
			demoForm = new DemoForm();
		}

		// Use the OLS framework to construct a Option option list for
		// each user property drop-down menu
		MessageResources messageResources = this.getResources(request);
		this.populateMenus(demoForm, messageResources);

		// -------------------------------------------
		// Attach results to request
		// -------------------------------------------

		// Attach default form to request
		request.setAttribute("demoForm", demoForm);
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Add parser demo menu data";
	}

	// ========================= METHODS ===================================

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Populate drop-down menus. Struts forward mapping context object
	 * 
	 * @param form
	 *            Struts form bean attached to this action/filter.
	 * @param messageResources
	 *            request's Struts resource bundle
	 */
	private void populateMenus(DemoForm demoForm, final MessageResources messageResources)
	{
		logger.debug("Populating drop-down menu data");

		// ====================================================================
		// Use the OLS framework to populate drop-down menus (option lists)
		// ====================================================================
		// Modify the selection labels for internationalization

		String separator = WebAppNames.KEY.MESSAGE.SEPARATOR;
		String suffix = WebAppNames.KEY.MESSAGE.OLS_ENUM_OPTION_SUFFIX;

		// Arithmetic Mode
		OptionList arithmeticModeOptions = StrutsUtil.i18NSelection(
				net.ruready.parser.arithmetic.entity.numericalvalue.ArithmeticMode.class,
				true, suffix, separator, messageResources);

		// Analysis type
		OptionList analysisIDOptions = StrutsUtil.i18NSelection(
				net.ruready.parser.analysis.entity.AnalysisID.class, true, suffix,
				separator, messageResources);

		// Parser input formats
		OptionList formatOptions = StrutsUtil.i18NSelection(
				net.ruready.parser.port.input.exports.ParserInputFormat.class, true,
				suffix, separator, messageResources);

		// ---------------------------------------
		// Attach results to form
		// ---------------------------------------

		demoForm.setArithmeticModeOptions(arithmeticModeOptions);
		demoForm.setAnalysisIDOptions(analysisIDOptions);
		demoForm.setFormatOptions(formatOptions);
	}
}
