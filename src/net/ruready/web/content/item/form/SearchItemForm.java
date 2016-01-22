/*****************************************************************************************
 * Source File: SearchItemForm.java
 ****************************************************************************************/
package net.ruready.web.content.item.form;

import javax.servlet.http.HttpServletRequest;

import net.ruready.common.eis.entity.Resettable;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.common.pointer.ValueObject;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.text.TextUtil;
import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * Item search form. Includes all search fields.
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
 * @version Jul 29, 2007
 */
public class SearchItemForm extends ValidatorActionForm implements
		ValueBean<ValueObject>, Resettable
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
	private static final Log logger = LogFactory.getLog(SearchItemForm.class);

	// ========================= FIELDS ====================================

	// ------------------------- Item Search Fields ------------------------

	/**
	 * Item name.
	 */
	private String name;

	// private String comment;

	// ========================= IMPLEMENTATION: ValueBean =================

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyFrom(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	/**
	 * @param valueObject
	 * @param context
	 */
	public void copyFrom(ValueObject valueObject, final ApplicationContext context)
	{
		try
		{
			// TODO: replace with ItemCopier. Use a common form bean interface (naming)
			// for this
			// bean as well as EditItemForm bean.
			BeanUtils.copyProperties(this, valueObject);
			logger.debug("copyFrom() vo = " + valueObject + " new this = " + this);
		}
		catch (Exception e)
		{
			logger.error("Cannot copy from ValueObject: " + e.toString());
		}
	}

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyTo(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyTo(ValueObject valueObject, final ApplicationContext context)
	{
		try
		{
			// Do type conversions
			BeanUtils.copyProperties(valueObject, this);
		}
		catch (Exception e)
		{
			logger.error("Cannot copy to ValueObject: " + e.toString());
		}
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
	 * Check if the form empty or partially filled.
	 * 
	 * @return is the form empty or partially filled
	 */
	public boolean isEmpty()
	{
		return TextUtil.isEmptyTrimmedString(name)
		// && MarkerUtil.isEmptyTrimmedString(comment)
		// && (serialNo == null)
		;
	}

	// ========================= IMPLEMENTATION: ValidatorActionForm =======

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
		// Carry out all the Validator framework validations
		ActionErrors errors = super.validate(mapping, request);

		// Custom validations should be added here
		logger.debug("validate");
		return errors;
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
		reset();
	}

	// ========================= IMPLEMENTATION: Resettable ================

	/**
	 * Reset the form fields. This is not the same as the Struts ActionForm reset method.
	 * 
	 * @see net.ruready.common.eis.Resettable#reset()
	 */
	public void reset()
	{
		// Make sure we satisfy the validation rules
		name = null;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
