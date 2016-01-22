/*****************************************************************************************
 * Source File: AuthenticationException.java
 ****************************************************************************************/
package net.ruready.business.user.exception;

import net.ruready.common.exception.ApplicationException;

/**
 * An exception to be thrown by when a user's authentication has failed.
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
 * @version Aug 11, 2007
 */
public class AuthenticationException extends ApplicationException
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	public enum TYPE
	{
		LOGIN_FAILED, ACCOUNT_LOCKED;
	}

	// ========================= FIELDS ====================================

	// Fields that caused the authentication to fail
	private String email;

	private String password;

	private TYPE type;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an exception with a message and a cause (wrap and rethrow the cause).
	 * 
	 * @param message
	 *            message to be displayed
	 * @param cause
	 *            cause of the exception
	 * @param email
	 *            email address of the login attempt
	 * @param password
	 *            password of the login attempt
	 * @param type
	 *            type of authentication failure
	 */
	public AuthenticationException(String message, Throwable cause, String email,
			String password, TYPE type)
	{
		super(message, cause);
		this.email = email;
		this.password = password;
		this.type = type;
	}

	/**
	 * Create an exception with a message.
	 * 
	 * @param message
	 *            message to be displayed
	 * @param email
	 *            email address of the login attempt
	 * @param password
	 *            password of the login attempt
	 * @param type
	 *            type of authentication failure
	 */
	public AuthenticationException(String message, String email, String password,
			TYPE type)
	{
		super(message);
		this.email = email;
		this.password = password;
		this.type = type;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return the type
	 */
	public TYPE getType()
	{
		return type;
	}

}
