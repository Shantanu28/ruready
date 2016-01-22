/*****************************************************************************************
 * Source File: DemoForm.java
 ****************************************************************************************/
package net.ruready.web.parser.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.ruready.common.eis.entity.Resettable;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.common.exception.SystemException;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.EnumUtil;
import net.ruready.parser.analysis.entity.AnalysisID;
import net.ruready.parser.arithmetic.entity.numericalvalue.ArithmeticMode;
import net.ruready.parser.arithmetic.entity.numericalvalue.format.NumericalFormatFactory;
import net.ruready.parser.atpm.entity.CostType;
import net.ruready.parser.atpm.manager.WeightedNodeComparisonCost;
import net.ruready.parser.options.exports.ParserOptions;
import net.ruready.parser.port.input.exports.ParserInputFormat;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * Math parser demo form bean. Holds the reference and response string inputs and parser
 * control options.
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
public class DemoForm extends ValidatorActionForm implements PubliclyCloneable,
		ValueBean<ParserOptions>, Resettable
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
	private static final Log logger = LogFactory.getLog(DemoForm.class);

	/**
	 * Default precision (number of significant figures/digits) for comparing expressions.
	 * Too high precision caused a lot of grief in past demos; use a lower one
	 */
	public static final int DEFAULT_DIGITS = 6;

	// ========================= FIELDS ====================================

	// -------------------
	// Required input
	// -------------------

	/**
	 * Correct answer (reference expression).
	 */
	private String referenceString = null;

	/**
	 * Student's response (expression).
	 */
	private String responseString = null;

	// -------------------
	// Parser options
	// -------------------

	/**
	 * Parser input format.
	 */
	private String format = ParserInputFormat.EQUATION_EDITOR.toString();

	/**
	 * Is implicit * operation syntax like "3 x" allowed in expressions or not.
	 */
	private boolean implicitMultiplication = true;

	/**
	 * Precision of numerical comparison [#significant figures/digits].
	 */
	private int digits = DEFAULT_DIGITS;

	/**
	 * Arithmetic mode (both string and enum types, which are tied by the setters). Use a
	 * setter in constructor instead of static initialization.
	 */
	private ArithmeticMode arithmeticMode = null;

	/**
	 * Arithmetic mode (both string and enum types, which are tied by the setters). Use a
	 * setter in constructor instead of static initialization. This is the string input
	 * obtained from the drop-down menu selection.
	 */
	private String arithmeticModeStr = null;

	/**
	 * Analysis type (both string and enum types, which are tied by the setters). Use a
	 * setter in constructor instead of static initialization.
	 */
	private AnalysisID analysisID = null;

	/**
	 * Analysis type (both string and enum types, which are tied by the setters). Use a
	 * setter in constructor instead of static initialization. This is the string input
	 * obtained from the drop-down menu selection.
	 */
	private String analysisIDStr = null;

	/**
	 * Edit distance minimization - cost function knobs.
	 */
	private Map<String, String> costMap = new HashMap<String, String>();

	// -------------------
	// Drop-down menu data
	// -------------------

	/**
	 * Parser input format: drop-down menu option list.
	 */
	private OptionList formatOptions;

	/**
	 * Arithmetic mode: drop-down menu option list.
	 */
	private OptionList arithmeticModeOptions;

	/**
	 * Type of parser analysis: drop-down menu option list.
	 */
	private OptionList analysisIDOptions;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a new demo form.
	 */
	public DemoForm()
	{
		logger.debug("Creating new form");
		// this.setArithmeticMode(ArithmeticMode.COMPLEX);
		// this.setAnalysisID(AnalysisID.ATPM);
		// this.initializeCostMap();
		reset();
		logger.debug(this);
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to serve as
	 * a target (or part of a target) of an assembly.
	 * 
	 * @return a deep copy of this object.
	 */
	@Override
	public DemoForm clone()
	{
		try
		{
			DemoForm copy = (DemoForm) super.clone();
			copy.costMap = new HashMap<String, String>(costMap);
			copy.formatOptions = (formatOptions == null) ? null : formatOptions.clone();
			copy.analysisIDOptions = (analysisIDOptions == null) ? null
					: analysisIDOptions.clone();
			copy.arithmeticModeOptions = (arithmeticModeOptions == null) ? null
					: arithmeticModeOptions.clone();
			return copy;
		}

		catch (CloneNotSupportedException e)
		{
			// this shouldn't happen, because we are Cloneable
			throw new InternalError("clone() failed: " + e.getMessage());
		}
	}

	// ========================= IMPLEMENTATION: ValueBean =================

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyFrom(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyFrom(ParserOptions valueObject, final ApplicationContext context)
	{
		throw new SystemException(
				"For now, use the parser demo form as an input to the business layer only. Can't copy back from the business layer to the form.");
		// logger.debug("copyFrom()");
	}

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyTo(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyTo(ParserOptions valueObject, final ApplicationContext context)
	{
		logger.debug("copyTo()");

		// Copy properties that don't require type conversions
		// Fails because we have arithmetic mode string here and it's an enum in
		// the VO
		try
		{
			PropertyUtils.copyProperties(valueObject, this);
		}
		catch (Exception e)
		{
			logger.error("Could not copy properties: " + e.toString());
		}

		// Copy properties that require type conversions
		valueObject.setPrecisionTol(NumericalFormatFactory
				.digitsToPrecisionTol(this.digits));
		// parserOptionsVO.setArithmeticMode(EnumUtil.valueOf(ArithmeticMode.class,
		// this.arithmeticMode));
		this.copyCostMapTo(valueObject.getCostMap());
	}

	// ========================= METHODS ===================================

	/**
	 * Print this form bean.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return StrutsUtil.formToString(this);
	}

	/**
	 * Check if the form is empty or partially filled.
	 * 
	 * @return is the form empty or partially filled
	 */
	public boolean isEmpty()
	{
		return TextUtil.isEmptyTrimmedString(referenceString)
				&& TextUtil.isEmptyTrimmedString(responseString);
	}

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		// If a setup method has been requested or this is an empty form, skip validation
		if (!StrutsUtil.isMethodValidated(request) /* || isEmpty() */)
		{
			return null;
		}
		logger.debug("validate");

		// Carry out all the Validator framework validations
		ActionErrors errors = super.validate(mapping, request);

		logger.debug("errors " + errors);

		if ((errors == null) || errors.isEmpty())
		{
			// Check if checkboxes should be cleared; because checkbox
			// values are almost never validated, we can put this code
			// at the end of the validate() method. If validation passes,
			// perform these sets. Otherwise, leave the value set in the reset()
			// method as this may be a setup action that's purposely failing
			// validation to go to the JSP first.
			if (request.getParameter("implicitMultiplication") == null)
			{
				this.setImplicitMultiplication(false);
			}
		}

		// Custom validations should be added here
		return errors;
	}

	// ========================= IMPLEMENTATION: Resettable ================

	/**
	 * Resets the form values to their default values. Use in setup actions and reset
	 * button methods in dispatch actions.
	 * 
	 * @see net.ruready.common.eis.Resettable#reset()
	 */
	public void reset()
	{
		logger.debug("resetting form");
		// Make sure we satisfy the validation rules
		this.setReferenceString(null);
		this.setResponseString(null);
		this.setFormat(ParserInputFormat.EQUATION_EDITOR.toString());
		this.setImplicitMultiplication(true);
		this.setDigits(DEFAULT_DIGITS);
		this.setArithmeticMode(ArithmeticMode.COMPLEX);
		this.setAnalysisID(AnalysisID.ATPM);
		this.initializeCostMap();
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		// Carry out all the Validator framework resets, if applicable
		super.reset(mapping, request);

		// Custom reset operations should be added here
		logger.debug("reset");
		// reset();
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * nitialize cost map from hard-coded defaults.
	 */
	private void initializeCostMap()
	{
		this.copyCostMapFrom(WeightedNodeComparisonCost.getDefaultCostMap());
	}

	/**
	 * Copy (including key type conversion) the cost map from the form bean to a value
	 * object.
	 * 
	 * @param costMapVO
	 *            Value object's cost map using enumerated-type keys
	 */
	private void copyCostMapTo(Map<CostType, Double> costMapVO)
	{
		for (Map.Entry<String, String> entry : costMap.entrySet())
		{
			// Find out if there is a corresponding enum-type key
			// to this string key in costMap
			CostType keyVO = EnumUtil.createFromString(CostType.class, entry.getKey());
			if (keyVO != null)
			{
				costMapVO.put(keyVO, Double.parseDouble(entry.getValue()));
			}
		}
	}

	/**
	 * Copy (including key type conversion) the cost map to the form bean from a value
	 * object.
	 * 
	 * @param costMapVO
	 *            Value object's cost map using enumerated-type keys
	 */
	private void copyCostMapFrom(Map<CostType, Double> costMapVO)
	{
		for (CostType keyVO : costMapVO.keySet())
		{
			// We can copy all VO keys by converting them to strings using
			// their toString() method. We assume that CostType#toString()
			// is a unique identifier, like name().
			costMap.put(keyVO.toString(), costMapVO.get(keyVO).toString());
		}
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Set the number of digits. Trimmed to the range [0..MAX_DIGITS].
	 * 
	 * @param d
	 *            the digits to set
	 */
	public void setDigits(int d)
	{
		digits = d;
	}

	/**
	 * @return the digits
	 */
	public int getDigits()
	{
		return digits;
	}

	/**
	 * @return the arithmeticMode
	 */
	public ArithmeticMode getArithmeticMode()
	{
		return arithmeticMode;
	}

	/**
	 * @param arithmeticMode
	 *            the arithmeticMode to set
	 */
	public void setArithmeticMode(ArithmeticMode arithmeticMode)
	{
		this.arithmeticMode = arithmeticMode;
		this.arithmeticModeStr = (arithmeticMode == null) ? null : arithmeticMode.getType();
	}

	/**
	 * @return the arithmeticModeStr
	 */
	public String getArithmeticModeStr()
	{
		return arithmeticModeStr;
	}

	/**
	 * @param arithmeticModeStr
	 *            the arithmeticModeStr to set
	 */
	public void setArithmeticModeStr(String arithmeticModeStr)
	{
		this.arithmeticModeStr = arithmeticModeStr;
		this.arithmeticMode = EnumUtil.valueOf(ArithmeticMode.class,
				this.arithmeticModeStr);
	}

	/**
	 * @return the analysisID
	 */
	public AnalysisID getAnalysisID()
	{
		return analysisID;
	}

	/**
	 * @param analysisID
	 *            the analysisID to set
	 */
	public void setAnalysisID(AnalysisID analysisID)
	{
		this.analysisID = analysisID;
		this.analysisIDStr = (analysisID == null) ? null : analysisID.name();
	}

	/**
	 * @return the analysisIDStr
	 */
	public String getAnalysisIDStr()
	{
		return analysisIDStr;
	}

	/**
	 * @param analysisIDStr
	 *            the analysisIDStr to set
	 */
	public void setAnalysisIDStr(String analysisIDStr)
	{
		this.analysisIDStr = analysisIDStr;
		this.analysisID = EnumUtil.valueOf(AnalysisID.class, this.analysisIDStr);
	}

	/**
	 * @return the referenceString
	 */
	public String getReferenceString()
	{
		return referenceString;
	}

	/**
	 * @param referenceString
	 *            the referenceString to set
	 */
	public void setReferenceString(String referenceString)
	{
		this.referenceString = referenceString;
	}

	/**
	 * @return the responseString
	 */
	public String getResponseString()
	{
		return responseString;
	}

	/**
	 * @param responseString
	 *            the responseString to set
	 */
	public void setResponseString(String responseString)
	{
		this.responseString = responseString;
	}

	/**
	 * @return the implicitMultiplication
	 */
	public boolean isImplicitMultiplication()
	{
		return implicitMultiplication;
	}

	/**
	 * @param implicitMultiplication
	 *            the implicitMultiplication to set
	 */
	public void setImplicitMultiplication(boolean implicitMultiplication)
	{
		this.implicitMultiplication = implicitMultiplication;
	}

	/**
	 * @return the format
	 */
	public String getFormat()
	{
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format)
	{
		this.format = format;
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public String getCostMap(String key)
	{
		return costMap.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public void setCostMap(String key, String value)
	{
		// logger.debug("putCostMap(" + key + "," + value + ")");
		costMap.put(key, value);
	}

	/**
	 * @return the arithmeticModeOptions
	 */
	public OptionList getArithmeticModeOptions()
	{
		return arithmeticModeOptions;
	}

	/**
	 * @return the formatOptions
	 */
	public OptionList getFormatOptions()
	{
		return formatOptions;
	}

	/**
	 * @param formatOptions
	 *            the formatOptions to set
	 */
	public void setFormatOptions(OptionList formatOptions)
	{
		this.formatOptions = formatOptions;
	}

	/**
	 * @return the analysisIDOptions
	 */
	public OptionList getAnalysisIDOptions()
	{
		return analysisIDOptions;
	}

	/**
	 * @param analysisIDOptions
	 *            the analysisIDOptions to set
	 */
	public void setAnalysisIDOptions(OptionList analysisIDOptions)
	{
		this.analysisIDOptions = analysisIDOptions;
	}

	/**
	 * @param arithmeticModeOptions
	 *            the arithmeticModeOptions to set
	 */
	public void setArithmeticModeOptions(OptionList arithmeticModeOptions)
	{
		this.arithmeticModeOptions = arithmeticModeOptions;
	}

}
