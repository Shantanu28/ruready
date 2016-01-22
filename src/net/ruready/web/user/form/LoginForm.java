/*****************************************************************************************
 * Source File: LoginForm.java
 ****************************************************************************************/
package net.ruready.web.user.form;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.user.entity.User;
import net.ruready.common.eis.entity.Resettable;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.text.TextUtil;
import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * Login form (user + password + options) required for user authentication.
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
 * @version Aug 5, 2007
 */
public class LoginForm extends ValidatorActionForm implements ValueBean<User>,
		PubliclyCloneable, Resettable
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
	private static final Log logger = LogFactory.getLog(LoginForm.class);

	// ========================= FIELDS ====================================

	// -------------------------------------------------------------
	// List of input fields
	// -------------------------------------------------------------

	/**
	 * User's email.
	 */
	private String email;

	/**
	 * User's password.
	 */
	private String password;

	// ------------------------- Options -----------------------------------

	/**
	 * Checkbox for saving username + password as cookies in the client's browser.
	 */
	private boolean saveCookies = false;

	/**
	 * Go to this internal link after logging in. Default: main page [action]. Note: due
	 * to Apache handling SSL/no-SSL, this field has been replaced to a session token.
	 */
	// private String bookmark;
	//
	// ------------------------- Client Statistics -------------------------
	/**
	 * Screen resolution (width). Names are purposely unindicative of the variables'
	 * roles, to reduce user anxiety about their privacy intrusion.
	 */
	private String sw;

	/**
	 * Screen resolution (height). Names are purposely unindicative of the variables'
	 * roles, to reduce user anxiety about their privacy intrusion.
	 */
	private String sh;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Required no-constructor argument for a JavaBean.
	 */
	public LoginForm()
	{

	}

	// ========================= IMPLEMENTATION: ValueBean =================

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyFrom(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyFrom(User valueObject, final ApplicationContext context)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("copyFrom()");
		}
		try
		{
			PropertyUtils.copyProperties(this, valueObject);
		}
		catch (Exception e)
		{
			logger.error("Could not copy properties VO -> LoginForm, " + e.toString());
		}
	}

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyTo(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyTo(User valueObject, final ApplicationContext context)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("copyTo()");
		}
		try
		{
			PropertyUtils.copyProperties(valueObject, this);
		}
		catch (Exception e)
		{
			logger.error("Could not copy properties LoginForm -> VO, " + e.toString());
		}
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public LoginForm clone()
	{
		LoginForm dest = new LoginForm();
		try
		{
			PropertyUtils.copyProperties(dest, this);
		}
		catch (Exception e)
		{
			logger.error("Could not clone form");
		}
		return dest;
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
		return TextUtil.isEmptyTrimmedString(email)
				&& TextUtil.isEmptyTrimmedString(password);
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
		// If a setup method has been requested, skip validation
		if (!StrutsUtil.isMethodValidated(request))
		{
			return null;
		}

		// Carry out all the Validator framework validations
		ActionErrors errors = super.validate(mapping, request);

		// Custom validations should be added here
		if (logger.isDebugEnabled())
		{
			logger.debug("validate");
		}
		return errors;
	}

	// ========================= IMPLEMENTATION: Resettable ================

	/**
	 * Reset the form values (this is NOT the Struts
	 * {@link #reset(ActionMapping, HttpServletRequest)} method).
	 */
	public void reset()
	{
		// Make sure we satisfy the validation rules
		email = null;
		password = null;
		saveCookies = false;
		// bookmark = null;
	}

	// ========================= IMPLEMENTATION: ValidatorActionForm =======

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
		if (logger.isDebugEnabled())
		{
			logger.debug("reset");
		}
		reset();
	}

	// ========================= GETTERS & SETTERS =========================
	//
	// /**
	// * @return the bookmark
	// */
	// public String getBookmark()
	// {
	// return bookmark;
	// }
	//
	// /**
	// * @param bookmark
	// * the bookmark to set
	// */
	// public void setBookmark(String bookmark)
	// {
	// this.bookmark = bookmark;
	// }

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
		// Trim spaces
		if (this.email != null)
		{
			this.email = this.email.trim();
		}
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the saveCookies
	 */
	public boolean isSaveCookies()
	{
		return saveCookies;
	}

	/**
	 * @param saveCookies
	 *            the saveCookies to set
	 */
	public void setSaveCookies(boolean saveCookies)
	{
		this.saveCookies = saveCookies;
	}

	/**
	 * @return the sh
	 */
	public String getSh()
	{
		return sh;
	}

	/**
	 * @param sh
	 *            the sh to set
	 */
	public void setSh(String sh)
	{
		this.sh = sh;
	}

	/**
	 * @return the sw
	 */
	public String getSw()
	{
		return sw;
	}

	/**
	 * @param sw
	 *            the sw to set
	 */
	public void setSw(String sw)
	{
		this.sw = sw;
	}
}
