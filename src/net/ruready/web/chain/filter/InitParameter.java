/*****************************************************************************************
 * Source File: FilterPackage.java
 ****************************************************************************************/
package net.ruready.web.chain.filter;

import net.ruready.common.parser.xml.TagAttachment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Holds an init parameter name and value.
 * <p>
 * ------------------------------------------------------------------------- <br>
 * (c) 2006-2007 Continuing Education, University of Utah <br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software. <br>
 * Contact: Nava L. Livne <code>
 * &lt;nlivne@aoce.utah.edu&gt;
 * </code><br>
 * Academic Outreach and Continuing Education (AOCE) <br>
 * 1901 East South Campus Dr., Room 2197-E <br>
 * University of Utah, Salt Lake City, UT 84112-9399 <br>
 * U.S.A. <br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414 <br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you. <br>
 * -------------------------------------------------------------------------
 * 
 * @immutable
 * @author Oren E. Livne <code>
 * &lt;olivne@aoce.utah.edu&gt;
 * </code>
 * @version Oct 5, 2007
 */
class InitParameter implements TagAttachment
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
	private static final Log logger = LogFactory.getLog(InitParameter.class);

	// ========================= FIELDS ====================================

	/**
	 * XML Tag name.
	 */
	private final String tagName;

	/**
	 * Parameter name.
	 */
	private final String paramName;

	/**
	 * Parameter value.
	 */
	private final String paramValue;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a new init parameter. /**
	 * 
	 * @param tagName
	 *            XML Tag name
	 * @param paramName
	 *            parameter name
	 * @param paramValue
	 *            parameter value
	 */
	public InitParameter(String tagName, String paramName, String paramValue)
	{
		super();
		this.tagName = tagName;
		this.paramName = paramName;
		this.paramValue = paramValue;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer s = new StringBuffer(paramName);
		s.append("=");
		s.append(paramValue);
		return s.toString();
	}

	// ========================= METHODS (@see Map.Entry<K,V>) =============

	/**
	 * Compares the specified object with this entry for equality. Returns <tt>true</tt>
	 * if the given object is also a map entry and the two entries represent the same
	 * mapping. More formally, two entries <tt>e1</tt> and <tt>e2</tt> represent the
	 * same mapping if
	 * 
	 * <pre>
	 * (e1.getParamName() == null ? e2.getParamName() == null : e1.getParamName().equals(
	 * 		e2.getParamName()))
	 * </pre>
	 * 
	 * This ensures that the <tt>equals</tt> method works properly across different
	 * implementations of the <tt>Map.Entry</tt> interface.
	 * 
	 * @param o
	 *            object to be compared for equality with this map entry
	 * @return <tt>true</tt> if the specified object is equal to this map entry
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj)
	{
		// Standard checks to ensure the adherence to the general contract of
		// the equals() method
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass()))
		{
			return false;
		}
		// Cast to friendlier version
		InitParameter other = (InitParameter) obj;

		return (paramName == null ? other.paramName == null : paramName
				.equals(other.paramName));
	}

	/**
	 * Returns the hash code value for this map entry. The hash code of a map entry
	 * <tt>e</tt> is defined to be:
	 * 
	 * <pre>
	 * (e.getTagName() == null ? 0 : e.getTagName().hashCode())
	 * </pre>
	 * 
	 * This ensures that <tt>e1.equals(e2)</tt> implies that
	 * <tt>e1.hashCode()==e2.hashCode()</tt> for any two Entries <tt>e1</tt> and
	 * <tt>e2</tt>, as required by the general contract of <tt>Object.hashCode</tt>.
	 * 
	 * @return the hash code value for this map entry
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 * @see #equals(Object)
	 */
	@Override
	public int hashCode()
	{
		return (paramName == null ? 0 : paramName.hashCode());
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the tagName
	 */
	public String getTagName()
	{
		return tagName;
	}

	/**
	 * @return the paramName
	 */
	public String getParamName()
	{
		return paramName;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue()
	{
		return paramValue;
	}
}
