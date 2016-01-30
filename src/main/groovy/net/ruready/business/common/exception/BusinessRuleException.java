package net.ruready.business.common.exception;

import net.ruready.common.exception.ApplicationException;

public class BusinessRuleException extends ApplicationException
{

	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1212515184232473516L;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an exception with a message and a cause (wrap and rethrow the
	 * cause).
	 * 
	 * @param message
	 *            message to be displayed
	 * @param cause
	 *            cause of the exception
	 */
	public BusinessRuleException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Create an exception with a message.
	 * 
	 * @param message
	 *            message to be displayed
	 */
	public BusinessRuleException(final String message)
	{
		super(message);
	}
}
