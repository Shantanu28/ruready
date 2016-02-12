/*****************************************************************************************
 * Source File: LineReaderTarget.java
 ****************************************************************************************/
package net.ruready.common.junit.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An test file line target constructed by test file line parser.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public class LineReaderTarget implements PubliclyCloneable
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(LineReaderTarget.class);

	// ========================= FIELDS ====================================

	/**
	 * Evaluated string built by the parametric evaluation parser.
	 */
	private StringBuffer evalString = TextUtil.emptyStringBuffer();

	// Type of line read
	private LineID lineID = null;

	/**
	 * Customizable parameter map.
	 */
	private Map<String, String> parameters = new HashMap<String, String>();

	// Specific parameters

	// Stop on first error or continue processing the entire file in any event
	private boolean stopOnFirstError;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an initial test file line target.
	 */
	public LineReaderTarget()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#String()
	 */
	@Override
	public String toString()
	{
		return "eval string " + evalString + " type " + lineID;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to
	 * serve as a target (or part of a target) of an assembly.
	 * <p>
	 * Note: the <code>request</code> field is soft-copied here.
	 * 
	 * @return a deep copy of this object.
	 */
	@Override
	public LineReaderTarget clone()
	{
		try
		{
			LineReaderTarget copy = (LineReaderTarget) super.clone();
			// Copy non-final properties
			copy.evalString = new StringBuffer(evalString);
			copy.lineID = lineID;
			copy.parameters = new HashMap<String, String>(parameters);
			return copy;
		}

		catch (CloneNotSupportedException e)
		{
			// this shouldn't happen,
			// because we are Cloneable
			throw new InternalError("clone() failed: " + e.getMessage());
		}

	}

	// ========================= METHODS ===================================

	/**
	 * Append a string to the evaluated string.
	 * 
	 * @param suffix
	 *            string to be appended
	 */
	public void appendToEvalString(final String suffix)
	{
		evalString.append(suffix);
	}

	/**
	 * Clear the evaluated string and set the line type to null.
	 */
	public void clear()
	{
		this.lineID = null;
		this.evalString = TextUtil.emptyStringBuffer();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the evaluated string
	 */
	public StringBuffer getEvalString()
	{
		return evalString;
	}

	/**
	 * @param evalString
	 *            the evalString to set
	 */
	public void setEvalString(StringBuffer evalString)
	{
		this.evalString = evalString;
	}

	/**
	 * @return the stopOnFirstError
	 */
	public boolean isStopOnFirstError()
	{
		return stopOnFirstError;
	}

	/**
	 * @param stopOnFirstError
	 *            the stopOnFirstError to set
	 */
	public void setStopOnFirstError(boolean stopOnFirstError)
	{
		this.stopOnFirstError = stopOnFirstError;
		this.parameters.put(CommonNames.JUNIT.PARAMS.STOP_ON_FIRST_ERROR, new Boolean(
				stopOnFirstError).toString());
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.Map#get(java.lang.String)
	 */
	public String getParameter(String arg0)
	{
		return parameters.get(arg0);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.String, java.lang.String)
	 */
	public void setParameter(String name, String value)
	{
		parameters.put(name, value);
		try
		{
			if (CommonNames.JUNIT.PARAMS.STOP_ON_FIRST_ERROR.equals(name))
			{
				this.setStopOnFirstError(Boolean.parseBoolean(value));
				logger.info("Set parameter '"
						+ CommonNames.JUNIT.PARAMS.STOP_ON_FIRST_ERROR + "' to "
						+ isStopOnFirstError());
			}
			else
			{
				// Unrecognized parameter, do nothing
				logger.info("Ignoring unrecognized parameter setting name '" + name
						+ "' value = '" + value + "', skipping");
			}
		}
		catch (Exception e)
		{
			// most likely an invalid format exception has occurred, ignore
			// this parameter
			logger.warn("Invalid parameter setting name '" + name + "' value = '" + value
					+ "', skipping");
		}
	}

	/**
	 * @return
	 * @see java.util.Map#keySet()
	 */
	public Set<String> getParameterNames()
	{
		return parameters.keySet();
	}

	/**
	 * @return the lineID
	 */
	public LineID getLineID()
	{
		return lineID;
	}

	/**
	 * @param lineID
	 *            the lineID to set
	 */
	public void setLineID(LineID lineID)
	{
		this.lineID = lineID;
	}

}
