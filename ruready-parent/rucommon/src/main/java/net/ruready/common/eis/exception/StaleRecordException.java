/*****************************************************************************************
 * Source File: StaleRecordException.java
 ****************************************************************************************/
package net.ruready.common.eis.exception;

import java.io.Serializable;

import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.rl.CommonNames;

/**
 * An exception to be thrown by DAOs to flag that an entity version conflict has arisen.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 25, 2007
 */
public class StaleRecordException extends EISException
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

	/**
	 * Local copy version number, if known.
	 */
	private final Integer localVersion;

	/**
	 * Stored copy version number, if known.
	 */
	private final Integer storedVersion;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Creates a persistence layer exception.
	 * 
	 * @param entity
	 *            entity in conflict, if known
	 * @param localVersion
	 *            local copy version number, if known.
	 * @param storedVersion
	 *            stored copy version number, if known
	 * @param message
	 *            literal error message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public StaleRecordException(final PersistentEntity<?> entity,
			final Integer localVersion, final Integer storedVersion,
			final String message, Object... args)
	{
		super(message, args);
		this.entity = entity;
		this.localVersion = localVersion;
		this.storedVersion = storedVersion;
	}

	/**
	 * Wraps and re-throws an exception.
	 * 
	 * @param entity
	 *            entity in conflict, if known
	 * @param localVersion
	 *            local copy version number, if known.
	 * @param storedVersion
	 *            stored copy version number, if known
	 * @param exception
	 *            wrapped exception
	 * @param message
	 *            literal error message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public StaleRecordException(final PersistentEntity<?> entity,
			final Integer localVersion, final Integer storedVersion,
			final Throwable exception, final String message, Object... args)
	{
		super(exception, message, args);
		this.entity = entity;
		this.localVersion = localVersion;
		this.storedVersion = storedVersion;
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

	/**
	 * Returns the localVersion property.
	 * 
	 * @return the localVersion
	 */
	public Integer getLocalVersion()
	{
		return localVersion;
	}

	/**
	 * Returns the storedVersion property.
	 * 
	 * @return the storedVersion
	 */
	public Integer getStoredVersion()
	{
		return storedVersion;
	}

}
