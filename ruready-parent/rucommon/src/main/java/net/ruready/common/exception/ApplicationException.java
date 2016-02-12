/*****************************************************************************************
 * Source File: ApplicationException.java
 ****************************************************************************************/
package net.ruready.common.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is a generic application error to be captured by the front-end. It is used to let
 * the presentation tier know that some kind of exception was caught and raised. It
 * signals a recoverable application problem, e.g. non-existent database record.
 * <p>
 * Reference: (Original author) John Carnell et al., from the book "Pro Apache Struts with
 * Ajax", APress, 2006, Chapter 4, page 164. Long description ...
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
 * @version Aug 4, 2007
 */
public class ApplicationException extends InternationalizbleException
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
	private static final Log logger = LogFactory.getLog(ApplicationException.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Creates an i18nlizable exception from an i18n message.
	 * 
	 * @param errorMessage
	 *            i18n message
	 */
	public ApplicationException(final InternationalizableErrorMessage errorMessage)
	{
		super(errorMessage);
	}

	/**
	 * Creates an application exception.
	 * 
	 * @param message
	 *            literal error message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public ApplicationException(final String message, Object... args)
	{
		super(message, args);
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
	public ApplicationException(final String message, String key, Object... args)
	{
		super(message, key, args);
	}

	/**
	 * Wraps and re-throws an exception as an application exception.
	 * 
	 * @param exception
	 *            wrapped exception
	 * @param message
	 *            literal error message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public ApplicationException(final Throwable exception, final String message,
			Object... args)
	{
		super(exception, message, args);
	}

	// ========================= CONSTRUCTORS ==============================
}
