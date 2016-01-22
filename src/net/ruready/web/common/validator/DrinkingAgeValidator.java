/*****************************************************************************************
 * Source File: ParametersValidator.java
 ****************************************************************************************/

package net.ruready.web.common.validator;

import javax.servlet.http.HttpServletRequest;

import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

/**
 * Example of a validation rule for a drinking age parameter.
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

public class DrinkingAgeValidator implements CustomValidator
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
	private static final Log logger = LogFactory.getLog(DrinkingAgeValidator.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize this validation rule.
	 */
	public DrinkingAgeValidator()
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
	public static boolean validate(Object bean, ValidatorAction va, Field field,
			ActionMessages errors, Validator validator, HttpServletRequest request)
	{

		String value = null;
		if (TextUtil.isString(bean))
		{
			value = (String) bean;
		}
		else
		{
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		}
		String sMin = field.getVarValue("drinkingAge");

		if (!GenericValidator.isBlankOrNull(value))
		{
			try
			{
				int iValue = Integer.parseInt(value);
				int drinkingAge = Integer.parseInt(sMin);

				if (iValue < drinkingAge)
				{
					errors.add(field.getKey(), Resources.getActionMessage(validator,
							request, va, field));
					return false;
				}
			}
			catch (Exception e)
			{
				errors.add(field.getKey(), Resources.getActionMessage(validator, request,
						va, field));
				return false;
			}
		}
		return true;
	}

	// ========================= PRIVATE METHODS ===========================
}
