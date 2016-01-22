/*****************************************************************************************
 * Source File: DemoAction.java
 ****************************************************************************************/
package net.ruready.web.parser.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.parser.form.DemoForm;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * A test: this action includes the setup portion of {@link DemoAction} only.
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
 * -------------------------------------------------------------------------<br>
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 16, 2007
 */
@Deprecated
public class SetupDemoAction extends Action
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SetupDemoAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * Default action - populate drop-down menu data.
	 * 
	 * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		DemoForm demoForm = (DemoForm) form;

		// -------------------------------------------
		// Business logic
		// -------------------------------------------
		populateMenus(mapping, form, request, response);
		// demoForm.reset();

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------
		request.setAttribute("demoForm", demoForm);

		// Always go back to the edit view
		return mapping.findForward("demo.view");
	}

	/**
	 * Resolve editing conflicts using database-stored copy. Like a cancel
	 * operation, but goes back to the edit view.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return ActionForward forward action to forward the request to
	 */
	public ActionForward action_setup_reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		DemoForm demoForm = (DemoForm) form;

		// -------------------------------------------
		// Business logic
		// -------------------------------------------
		populateMenus(mapping, form, request, response);
		demoForm.reset();

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------
		request.setAttribute("demoForm", demoForm);

		// Always go back to the edit view
		return mapping.findForward("demo.view");
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 * @throws Exception
	 */
	private void populateMenus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		logger.debug("Populating drop-down menu data");

		// ====================================================================
		// Use the OLS framework to populate drop-down menus (option lists)
		// ====================================================================
		// Modify the selection labels for internationalization
		MessageResources messageResources = this.getResources(request);

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
		// Attach results to the form
		// ---------------------------------------

		DemoForm demoForm = (DemoForm) form;

		// Arithmetic Mode
		demoForm.setArithmeticModeOptions(arithmeticModeOptions);

		// Analysis type
		demoForm.setAnalysisIDOptions(analysisIDOptions);

		// Parser input formats
		demoForm.setFormatOptions(formatOptions);
	}
}
