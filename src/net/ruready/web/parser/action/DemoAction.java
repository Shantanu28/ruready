/*****************************************************************************************
 * Source File: DemoAction.java
 ****************************************************************************************/
package net.ruready.web.parser.action;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.common.math.basic.IntegerPair;
import net.ruready.common.util.EnumUtil;
import net.ruready.parser.marker.entity.Analysis;
import net.ruready.parser.options.exports.ParserOptions;
import net.ruready.parser.port.input.exports.ParserInputFormat;
import net.ruready.parser.rl.ParserNames;
import net.ruready.parser.service.exports.AbstractParserBD;
import net.ruready.parser.service.exports.ParserRequest;
import net.ruready.parser.service.exports.ParserRequestUtil;
import net.ruready.parser.service.exports.SimpleParserServiceProvider;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.parser.form.DemoForm;
import net.ruready.web.parser.imports.StrutsParserBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * A demo Struts action that processes an input from a form, feeds it to the
 * parser, and attaches the analysis results to the request for display. Note:
 * this class depends on a Business Delegate (BD) that uses some default parser
 * service provider. You can use a manager instead, but you will need to pass to
 * its constructor a parser service provider, e.g.
 * {@link SimpleParserServiceProvider}.
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
public class DemoAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DemoAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * Default action - populate drop-down menu data.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see net.ruready.web.common.action.LoggedActionWithDispatcher#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		DemoForm demoForm = (DemoForm) form;

		// -------------------------------------------
		// Business logic
		// -------------------------------------------
		// populateMenus(mapping, form, request, response);
		demoForm.reset();

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------
		request.setAttribute("demoForm", demoForm);

		// Always go back to the edit view
		return mapping.findForward("demo.view");
	}

	/**
	 * Update format following an <code>onchange()</code> event in the
	 * corresponding radio selection group.
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
	public ActionForward action_setup_updateFormat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		DemoForm demoForm = (DemoForm) form;
		ParserInputFormat format = EnumUtil.createFromString(ParserInputFormat.class,
				demoForm.getFormat());

		// Save tokens of submitted data in request
		// The "old" format is the format before update format request
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.PARSER_OLD_FORMAT,
				(format == ParserInputFormat.TEXT) ? ParserInputFormat.EQUATION_EDITOR
						: ParserInputFormat.TEXT);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------
		// populateMenus(mapping, form, request, response);

		// [Re-]run analysis if this is a valid form. Set the action to be
		// validated at this stage by setting a request token to true so that
		// validation really runs
		request.setAttribute(WebAppNames.ACTION.VALIDATE_TOKEN,
				WebAppNames.ACTION.VALUE_TRUE);
		ActionErrors errors = demoForm.validate(mapping, request);
		boolean runAnal = (errors == null) || errors.isEmpty();

		// - Convert (to text) only if the old format was EE,
		// i.e. the new format is text
		// - [Re-]run analysis if this is a valid form
		// - Returned input format = input format after conversion
		runAnalysis(
				mapping,
				demoForm,
				request,
				response,
				(format == ParserInputFormat.TEXT) ? ParserInputFormat.TEXT : null,
				runAnal,
				(format == ParserInputFormat.EQUATION_EDITOR) ? ParserInputFormat.EQUATION_EDITOR
						: null, true);

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------
		// Done in runAnalysis()

		// Always go back to the edit view
		return mapping.findForward("demo.view");
	}

	/**
	 * Run parser demo analysis.
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
	public ActionForward action_analyze(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		DemoForm demoForm = (DemoForm) form;
		ParserInputFormat format = EnumUtil.createFromString(ParserInputFormat.class,
				demoForm.getFormat());

		// Save tokens of submitted data in request
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.PARSER_OLD_FORMAT, format);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------
		// populateMenus(mapping, form, request, response);

		// - Convert (to text) only if the current format is EE
		// - Returned input format = original input format
		runAnalysis(mapping, demoForm, request, response,
				(format == ParserInputFormat.EQUATION_EDITOR) ? ParserInputFormat.TEXT
						: null, true, null, false);

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------
		// Done in runAnalysis()

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
		// populateMenus(mapping, form, request, response);
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
	 * Run parser analysis on a response versus a reference string.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @param convertInputPre
	 *            if non-null, input conversion will be performed with this
	 *            target format in mind before the analysis
	 * @param runAnalysis
	 *            analysis is performed iff this flag is true
	 * @param convertInputPost
	 *            if non-null, input conversion will be performed with this
	 *            target format in mind after the analysis
	 * @param attachConverted
	 *            if true, the inputs after all conversions will be attached to
	 *            the requred. If false, the original input will be attached
	 * @return ActionForward forward action to forward the request to
	 */
	private void runAnalysis(final ActionMapping mapping, final DemoForm demoForm,
			final HttpServletRequest request, final HttpServletResponse response,
			final ParserInputFormat convertFormatPre, final boolean runAnalysis,
			final ParserInputFormat convertFormatPost, final boolean attachConverted)
	{
		logger.debug("[runAnalysis(convertFormatPre=" + convertFormatPre
				+ ", runAnalysis=" + runAnalysis + ", convertFormatPost="
				+ convertFormatPost + ", attachConverted=" + attachConverted + ")]");
		boolean localRunAnalysis = runAnalysis;

		// -------------------------------------------
		// Business logic
		// -------------------------------------------
		// Create a parser control object
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		ParserOptions options = DemoAction.createOptions();
		demoForm.copyTo(options, context);
		logger.debug("demoForm " + demoForm);
		logger.debug("options  " + options);

		AbstractParserBD bdParser = new StrutsParserBD();

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Pre-analysis input conversion
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Pre-analysis input conversion. Make a copy of the original form so
		// the original-formatted data is saved in "demoForm" and reattached to
		// the request at the end of this function
		DemoForm convertedBeforeAnalysisDemoForm = demoForm.clone();
		if (convertFormatPre != null)
		{
			DemoAction.convertInputs(convertedBeforeAnalysisDemoForm, convertFormatPre,
					bdParser, options);
			logger.debug("convertedBeforeAnalysisDemoForm "
					+ convertedBeforeAnalysisDemoForm);
		}
		// Skip the analysis if the conversion revealed an invalid form
		request.setAttribute(WebAppNames.ACTION.VALIDATE_TOKEN,
				WebAppNames.ACTION.VALUE_TRUE);
		ActionErrors errors = demoForm.validate(mapping, request);
		if (((errors != null) && !errors.isEmpty())
				|| (convertedBeforeAnalysisDemoForm.isEmpty()))
		{
			localRunAnalysis = false;
			logger.debug("Invalid form after conversion, localRunAnalysis = "
					+ localRunAnalysis);
			// If no errors are present, create one
			if ((errors == null) || errors.isEmpty())
			{
				errors = new ActionErrors();
				errors.add("unsupportedExpression", new ActionMessage(
						"error.parser.unsupportedException"));
			}
			this.saveErrors(request, errors);
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Post-analysis input conversion
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		DemoForm convertedAfterAnalysisDemoForm = convertedBeforeAnalysisDemoForm.clone();
		if (convertFormatPost != null)
		{
			DemoAction.convertInputs(convertedAfterAnalysisDemoForm, convertFormatPost,
					bdParser, options);
			logger.debug("convertedAfterAnalysisDemoForm "
					+ convertedAfterAnalysisDemoForm);
		}
		// Attach the form already at this point, in case the analysis
		// below fails and throws an exception before we have a chance to
		// save the updated form the request
		request.setAttribute("demoForm", attachConverted ? convertedAfterAnalysisDemoForm
				: demoForm);

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Run the parser on the converted inputs
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		ParserRequest pr = null;
		if (localRunAnalysis)
		{
			pr = bdParser.runDemo(convertedBeforeAnalysisDemoForm.getReferenceString(),
					convertedBeforeAnalysisDemoForm.getResponseString(), options);
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Prepare miscellaneous objects for the view
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

		if (localRunAnalysis && (pr != null))
		{
			Analysis analysis = ParserRequestUtil.getLatestMarkerAnalysis(pr);
			String HTMLReferenceString = ParserRequestUtil.getHTMLReferenceString(pr);
			String HTMLResponseString = ParserRequestUtil.getHTMLResponseString(pr);
			boolean equivalent = ParserRequestUtil.isEquivalent(pr);
			BufferedImage referenceTreeImage = (BufferedImage) pr
					.getAttribute(ParserNames.REQUEST.ATTRIBUTE.PORT.OUTPUT.IMAGE.TREE_REFERENCE);
			BufferedImage responseTreeImage = (BufferedImage) pr
					.getAttribute(ParserNames.REQUEST.ATTRIBUTE.PORT.OUTPUT.IMAGE.TREE_RESPONSE);
			IntegerPair referenceTreeImageSize = (IntegerPair) pr
					.getAttribute(ParserNames.REQUEST.ATTRIBUTE.PORT.OUTPUT.IMAGE.TREE_SIZE_REFERENCE);
			IntegerPair responseTreeImageSize = (IntegerPair) pr
					.getAttribute(ParserNames.REQUEST.ATTRIBUTE.PORT.OUTPUT.IMAGE.TREE_SIZE_RESPONSE);

			// ---------------------------------------
			// Attach results to response
			// ---------------------------------------

			// Attach everything for the view to work
			request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.TOKEN.PARSER_DEMO_RESULT,
					"true");
			request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.PARSER_DEMO_EQUIVALENT,
					equivalent);
			request.setAttribute(
					WebAppNames.REQUEST.ATTRIBUTE.PARSER_DEMO_HTML_REFERENCE_STRING,
					HTMLReferenceString);
			request.setAttribute(
					WebAppNames.REQUEST.ATTRIBUTE.PARSER_DEMO_HTML_RESPONSE_STRING,
					HTMLResponseString);
			request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.PARSER_DEMO_ANALYSIS,
					analysis);
			request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.PARSER_DEMO_ELEMENT_MAP,
					HttpRequestUtil.toStringKeys(analysis.getElementCountMap()));
			request.setAttribute(
					WebAppNames.REQUEST.ATTRIBUTE.PARSER_DEMO_TREE_IMAGE_REFERENCE,
					referenceTreeImage);
			request.setAttribute(
					WebAppNames.REQUEST.ATTRIBUTE.PARSER_DEMO_TREE_IMAGE_RESPONSE,
					responseTreeImage);
			request.setAttribute(
					WebAppNames.REQUEST.ATTRIBUTE.PARSER_DEMO_TREE_SIZE_REFERENCE,
					referenceTreeImageSize);
			request.setAttribute(
					WebAppNames.REQUEST.ATTRIBUTE.PARSER_DEMO_TREE_SIZE_RESPONSE,
					responseTreeImageSize);
		}

	}

	/**
	 * Create a parser control object for the demo
	 * 
	 * @return parser control object
	 */
	private static ParserOptions createOptions()
	{
		// Create a parser control object
		ParserOptions options = new ParserOptions();
		// These variables are hard-coded for demo purposes
		options.addSymbolicVariable("x");
		options.addSymbolicVariable("y");
		options.addSymbolicVariable("z");
		return options;
	}

	/**
	 * Convert inputs from an old to a new parser format
	 * 
	 * @param demoForm
	 *            form containing the reference and response strings in the old
	 *            format. Updated to the new format upon returning from the
	 *            method.
	 * @param targetFormat
	 *            new (target) parser format
	 * @param bdParser
	 * @param options
	 */
	private static void convertInputs(DemoForm demoForm, ParserInputFormat targetFormat,
			AbstractParserBD bdParser, ParserOptions options)
	{
		String parserReferenceString = demoForm.getReferenceString();
		String parserResponseString = demoForm.getResponseString();

		switch (targetFormat)
		{
			case EQUATION_EDITOR:
			{
				logger.debug("Converting input strings from text to EE format");
				parserReferenceString = bdParser.convertParser2MathML(
						parserReferenceString, options);
				parserResponseString = bdParser.convertParser2MathML(
						parserResponseString, options);
				break;
			}

			case TEXT:
			{
				logger.debug("Converting input strings from EE to text format");
				parserReferenceString = bdParser.convertMathML2Parser(
						parserReferenceString, options);
				parserResponseString = bdParser.convertMathML2Parser(
						parserResponseString, options);
				break;
			}
		}
		demoForm.setReferenceString(parserReferenceString);
		demoForm.setResponseString(parserResponseString);
	}
}
