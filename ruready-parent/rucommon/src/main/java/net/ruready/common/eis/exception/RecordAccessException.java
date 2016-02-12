/*****************************************************************************************
 * Source File: RecordAccessException.java
 ****************************************************************************************/
package net.ruready.common.eis.exception;

import java.io.Serializable;

import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.rl.CommonNames;

/**
 * Thrown when a read-only record is attempted to be modified at the persistence layer.
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
 * @version Sep 23, 2007
 */
public class RecordAccessException extends EISException
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * Entity in conflict, if known.
	 */
	private final PersistentEntity<?> entity;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an exception with a message and a cause (wrap and rethrow the cause).
	 * 
	 * @param message
	 *            message to be displayed
	 * @param cause
	 *            cause of the exception
	 * @param entity
	 *            entity in conflict, if known
	 */
	public RecordAccessException(final String message, final Throwable cause,
			final PersistentEntity<?> entity)
	{
		super(cause, message, (Object[])null);
		this.entity = entity;
	}

	/**
	 * Create an exception with a message.
	 * 
	 * @param message
	 *            message to be displayed
	 * @param entity
	 *            entity in conflict, if known
	 */
	public RecordAccessException(final String message, final PersistentEntity<?> entity)
	{
		super(message);
		this.entity = entity;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the id of the entity.
	 * 
	 * @return the id of the entity
	 */
	public Serializable getId()
	{
		return (entity == null) ? CommonNames.MISC.INVALID_VALUE_INTEGER : entity.getId();
	}

	/**
	 * Return the entity.
	 * 
	 * @return the entity
	 */
	public PersistentEntity<?> getEntity()
	{
		return entity;
	}
}
