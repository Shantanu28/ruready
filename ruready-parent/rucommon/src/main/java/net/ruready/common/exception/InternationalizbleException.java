/*****************************************************************************************
 * Source File: InternationalizableException.java
 ****************************************************************************************/
package net.ruready.common.exception;

import java.util.List;

import net.ruready.common.rl.CommonNames;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is a base exception class that can be associated with a key into a resource
 * bundle. The key is by convention
 * <code>CommonNames.KEY.EXCEPTION.PREFIX + this.getClass()</code>.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 16, 2007
 */
public class InternationalizbleException extends NestableRuntimeException
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
	private static final Log logger = LogFactory
			.getLog(InternationalizbleException.class);

	// ========================= FIELDS ====================================

	/**
	 * A delegate object holding a literal error message and a key to an i18n resource
	 * bundle.
	 */
	private final InternationalizableErrorMessage errorMessage;

	/**
	 * For stack trace printout.
	 */
	private Throwable exceptionCause = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Creates an i18nlizable exception from an i18n message.
	 * 
	 * @param errorMessage
	 *            i18n message
	 */
	public InternationalizbleException(final InternationalizableErrorMessage errorMessage)
	{
		super(errorMessage.getMessage());
		this.errorMessage = errorMessage;
	}

	/**
	 * Creates an i18nlizable exception.
	 * 
	 * @param message
	 *            literal error message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public InternationalizbleException(final String message, Object... args)
	{
		super(message);
		// Key convention
		String key = CommonNames.KEY.EXCEPTION.PREFIX + this.getClass().getSimpleName();
		this.errorMessage = new InternationalizableErrorMessage(message, key, args);
	}

	/**
	 * Creates an i18nlizable exception with a custom key.
	 * 
	 * @param message
	 *            literal error message
	 * @param key
	 *            a key into an error message resource bundle such as Struts' application
	 *            resources bundle
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public InternationalizbleException(final String message, String key, Object... args)
	{
		super(message);
		this.errorMessage = new InternationalizableErrorMessage(message, key, args);
	}

	/**
	 * Wraps and re-throws an exception.
	 * 
	 * @param exception
	 *            wrapped exception
	 * @param message
	 *            literal error message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public InternationalizbleException(final Throwable exception, final String message,
			Object... args)
	{
		super(message, exception);
		this.exceptionCause = exception;
		String key = CommonNames.KEY.EXCEPTION.PREFIX + this.getClass();
		this.errorMessage = new InternationalizableErrorMessage(message, key, args);
	}

	// ========================= IMPLEMENTATION: NestableRuntimeException ==

	/**
	 * Prints the stack trace of the exception.
	 * 
	 * @see org.apache.commons.lang.exception.NestableRuntimeException#printStackTrace()
	 */
	@Override
	public void printStackTrace()
	{
		logger.error(getMessage() + " args " + getArgs());
		if (exceptionCause != null)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("An exception has been caused by: "
						+ exceptionCause.toString());
			}
			exceptionCause.printStackTrace();
		}
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return
	 * @see net.ruready.common.exception.InternationalizableErrorMessage#getArgs()
	 */
	public List<String> getArgs()
	{
		return errorMessage.getArgs();
	}

	/**
	 * @param index
	 * @return
	 * @see net.ruready.common.parser.core.parse.InternationalizableErrorMessage#getArg(int)
	 */
	public String getArg(int index)
	{
		return errorMessage.getArg(index);
	}

	/**
	 * @return
	 * @see net.ruready.common.parser.core.parse.InternationalizableErrorMessage#getKey()
	 */
	public String getKey()
	{
		return errorMessage.getKey();
	}

	/**
	 * Returns the errorMessage property.
	 * 
	 * @return the errorMessage
	 */
	public InternationalizableErrorMessage getErrorMessage()
	{
		return errorMessage;
	}
}
