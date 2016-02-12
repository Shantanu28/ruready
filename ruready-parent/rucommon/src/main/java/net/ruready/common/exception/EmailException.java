/*****************************************************************************************
 * Source File: EmailException.java
 ****************************************************************************************/
package net.ruready.common.exception;

/**
 * This is a special type of an application exception that is thrown by e-mail services.
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
public class EmailException extends ApplicationException
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Creates an e-mail messaging exception.
	 * 
	 * @param message
	 *            literal error message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public EmailException(final String message, Object... args)
	{
		super(message, args);
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
	public EmailException(final Throwable exception, final String message,
			Object... args)
	{
		super(exception, message, args);
	}
}
