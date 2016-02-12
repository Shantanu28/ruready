/*****************************************************************************************
 * Source File: HandlerMessage.java
 ****************************************************************************************/
package net.ruready.common.chain;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.pointer.ValueObject;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A message placed in the request by a handler to indicate an important milestone during
 * request handling.
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
 * @version Jul 16, 2007
 */
public final class HandlerMessage implements ValueObject, PubliclyCloneable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HandlerMessage.class);

	/**
	 * These fields handle format of date in message printouts.
	 */
	public static final String PRETTY_DATE_FORMAT = "MMM dd, yyyy hh:mm:ss";

	public static final SimpleDateFormat PRETTY_DATE_FORMATTER = new SimpleDateFormat(
			PRETTY_DATE_FORMAT);

	// ========================= FIELDS ====================================

	// Name of handler that created the message
	private final String name;

	// Message body
	private final String message;

	// Time of message generation
	private final Date date;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a handler message.
	 * 
	 * @param name
	 *            name of handler that created the message
	 * @param message
	 *            message body
	 */
	public HandlerMessage(final String name, final String message)
	{
		super();
		this.name = name;
		this.message = message;
		this.date = new Date();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return PRETTY_DATE_FORMATTER.format(date) + CommonNames.MISC.TAB_CHAR + name
				+ CommonNames.MISC.TAB_CHAR + message;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to serve as
	 * a target of an assembly.
	 * <p>
	 * WARNING: the parameter class E must be immutable for this class to properly be
	 * cloned (and serve as part of a parser's target).
	 * 
	 * @return a deep copy of this object.
	 */
	@Override
	public HandlerMessage clone()
	{
		return new HandlerMessage(name, message);
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}
}
