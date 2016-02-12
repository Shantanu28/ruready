/*****************************************************************************************
 * Source File: InternationalizableErrorMessage.java
 ****************************************************************************************/
package net.ruready.common.exception;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.pointer.ValueObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An error message. Supports internationalization, hence called dynamic.
 * 
 * @immutable
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 14, 2007
 */
public class InternationalizableErrorMessage implements ValueObject, PubliclyCloneable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 5047333642934998202L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(InternationalizableErrorMessage.class);

	// ========================= FIELDS ====================================

	/**
	 * Literal error message.
	 */
	private final String message;

	/**
	 * A key into an error message resource bundle such as Struts' application resources
	 * bundle.
	 */
	private final String key;

	/**
	 * Pass these optional arguments to the actual error message specified by the
	 * "message" reference label
	 */
	private final List<String> args;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a syntax error.
	 * 
	 * @param message
	 *            literal error message
	 * @param key
	 *            a key into an error message resource bundle such as Struts' application
	 *            resources bundle
	 * @param reference
	 *            a key into an error message resource bundle such as Struts' application
	 *            resources bundle is the message field a label or is it the literal error
	 *            message
	 * @param args
	 *            pass these optional arguments to the actual error message specified by
	 *            the "message" reference label
	 */
	public InternationalizableErrorMessage(final String message, final String key,
			Object... args)
	{
		super();
		this.message = message;
		this.key = key;
		this.args = new ArrayList<String>();
		if (args != null)
		{
			for (Object arg : args)
			{
				this.args.add((arg == null) ? null : arg.toString());
			}
		}
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return message;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to serve as
	 * a target of an assembly.
	 * 
	 * @return a deep copy of this object
	 */
	@Override
	public InternationalizableErrorMessage clone()
	{
		return new InternationalizableErrorMessage(message, key, args
				.toArray(new Object[1]));
	}

	// ========================= METHODS ===================================

	/**
	 * Return the number of arguments.
	 * 
	 * @return the number of arguments.
	 * @see java.util.List#size()
	 */
	public int getNumArgs()
	{
		return args.size();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Returns the key property.
	 * 
	 * @return the key
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#get(int)
	 */
	public String getArg(int index)
	{
		return args.get(index);
	}

	/**
	 * @return the args
	 */
	public List<String> getArgs()
	{
		return args;
	}

}
