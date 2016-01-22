/*****************************************************************************************
 * Source File: ParametersValidator.java
 ****************************************************************************************/

package net.ruready.web.content.question.validator;

import javax.servlet.http.HttpServletRequest;

import net.ruready.common.text.TextUtil;
import net.ruready.parser.options.exports.ParserOptions;
import net.ruready.parser.param.entity.ParametricEvaluationTarget;
import net.ruready.parser.service.exports.DefaultParserBD;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.validator.CustomValidator;
import net.ruready.web.parser.imports.StrutsParserBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

/**
 * A validation rule for a parametric string, i.e. a string that can be parsed and
 * evaluated by the parametric parser. Relies on a previously-placed request attribute
 * holding the parser options, including the permissible parameter list.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 27, 2007
 */

public class ParametricStringValidator implements CustomValidator
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ParametricStringValidator.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize this validation rule.
	 */
	public ParametricStringValidator()
	{

	}

	// ======================= " IMPLEMENTATION: CustomValidator " =========

	/**
	 * Validation method invoked for this custom validation rule.
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 * @param request
	 * @return
	 */
	@SuppressWarnings("null")
	public static boolean validate(Object bean, ValidatorAction va, Field field,
			ActionMessages errors, Validator validator, HttpServletRequest request)
	{
		// Get request objects necessary for validation; if not found, skip this
		// validation
		ParserOptions options = (ParserOptions) request
				.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.VALIDATOR_PARSER_OPTIONS);
		if (options == null)
		{
			return true;

		}
		// Get the form bean value to be validated
		String value = null;
		if (TextUtil.isString(bean))
		{
			value = (String) bean;
		}
		else
		{
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		}

		// Read fields of the validation rule

		// Validate value to be a variable list string representation
		boolean valid = true;
		try
		{
			// Evaluate the string
			DefaultParserBD bd = new StrutsParserBD();
			ParametricEvaluationTarget pet = bd.evaluate(value, options);
			valid = ((pet != null) && pet.isLegal());
			if (valid)
			{
				logger.debug("Successfully evaluated parametric string field '" + value
						+ "' into : " + pet + " for random parameter pick: "
						+ pet.getVariables());
			}
		}
		catch (Exception e)
		{
			valid = false;
		}

		// Report errors and return result
		if (!valid)
		{
			errors.add(field.getKey(), Resources.getActionMessage(validator, request, va,
					field));

		}
		return valid;
	}

	// ========================= PRIVATE METHODS ===========================
}
