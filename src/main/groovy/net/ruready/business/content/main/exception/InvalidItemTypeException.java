/*****************************************************************************************
 * Source File: InvalidItemTypeException.java
 ****************************************************************************************/
package net.ruready.business.content.main.exception;

import java.io.Serializable;

import net.ruready.common.exception.SystemException;

/**
 * An exception to be thrown by DAOs to flag that an invalid item type has been requested.
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
public class InvalidItemTypeException extends SystemException
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
	 * Create an exception with a message and a cause (wrap and rethrow the cause).
	 * 
	 * @param message
	 *            message to be displayed
	 * @param cause
	 *            cause of the exception
	 * @param id
	 *            identifier of the entity that was not found
	 */
	public InvalidItemTypeException(String message, Throwable cause, final Serializable id)
	{
		super(message, cause);
		this.id = id;
	}

	/**
	 * Create an exception with a message.
	 * 
	 * @param message
	 *            message to be displayed
	 * @param id
	 *            identifier of the entity that was not found
	 */
	public InvalidItemTypeException(String message, final Serializable id)
	{
		super(message);
		this.id = id;
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
