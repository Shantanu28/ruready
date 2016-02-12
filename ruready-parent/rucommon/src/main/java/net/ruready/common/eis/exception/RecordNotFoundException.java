/*****************************************************************************************
 * Source File: RecordNotFoundException.java
 ****************************************************************************************/
package net.ruready.common.eis.exception;

import java.io.Serializable;

/**
 * An exception to be thrown by DAOs to flag that a record with this identifier is not
 * found.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-D<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 26, 2007
 */
public class RecordNotFoundException extends EISException
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * Identifier of the entity that was not found.
	 */
	private final Serializable id;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Creates a persistence layer exception.
	 * 
	 * @param id
	 *            identifier of the entity that was not found
	 * @param message
	 *            literal error message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public RecordNotFoundException(final Serializable id, final String message,
			Object... args)
	{
		super(message, args);
		this.id = id;
	}

	/**
	 * Creates an i18nlizable exception with a custom key.
	 * 
	 * @param id
	 *            identifier of the entity that was not found
	 * @param message
	 *            literal error message
	 * @param key
	 *            a key into an error message resource bundle such as Struts' application
	 *            resources bundle
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public RecordNotFoundException(final Serializable id, final String message,
			String key, Object... args)
	{
		super(message, key, args);
		this.id = id;
	}

	/**
	 * Wraps and re-throws an exception.
	 * 
	 * @param id
	 *            identifier of the entity that was not found
	 * @param exception
	 *            wrapped exception
	 * @param message
	 *            literal error message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public RecordNotFoundException(final Serializable id, final Throwable exception,
			final String message, Object... args)
	{
		super(exception, message, args);
		this.id = id;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @return
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString()
	{
		return getMessage() + ": id " + id;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the id property.
	 * 
	 * @return the id
	 */
	public Serializable getId()
	{
		return id;
	}

}
