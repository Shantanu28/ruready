/*****************************************************************************************
 * Source File: FilterPackage.java
 ****************************************************************************************/
package net.ruready.common.parser.xml;

import net.ruready.common.stack.AbstractStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An XML element with a body that can be pushed and popped into an {@link AbstractStack}.
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
public class XmlBodyElement implements TagAttachment
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
	private static final Log logger = LogFactory.getLog(XmlBodyElement.class);

	// ========================= FIELDS ====================================

	/**
	 * Tag name.
	 */
	private final String tagName;

	/**
	 * Tag body.
	 */
	private final Object tagBody;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a new map entry
	 * 
	 * @param tagName
	 *            entry tagName
	 * @param tagBody
	 *            entry tagBody
	 */
	public XmlBodyElement(String tagName, Object tagBody)
	{
		this.tagName = tagName;
		this.tagBody = tagBody;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer s = new StringBuffer("{");
		s.append(tagName);
		s.append(":");
		s.append(tagBody);
		s.append("}");
		return s.toString();
	}

	// ========================= METHODS (@see Map.Entry<K,V>) =============

	/**
	 * Returns the tagName corresponding to this entry.
	 * 
	 * @return the tagName corresponding to this entry
	 * @throws IllegalStateException
	 *             implementations may, but are not required to, throw this exception if
	 *             the entry has been removed from the backing map.
	 */
	public String getTagName()
	{
		return tagName;
	}

	/**
	 * Returns the tagBody corresponding to this entry. If the mapping has been removed
	 * from the backing map (by the iterator's <tt>remove</tt> operation), the results
	 * of this call are undefined.
	 * 
	 * @return the tagBody corresponding to this entry
	 * @throws IllegalStateException
	 *             implementations may, but are not required to, throw this exception if
	 *             the entry has been removed from the backing map.
	 */
	public Object getValue()
	{
		return tagBody;
	}

	/**
	 * Compares the specified object with this entry for equality. Returns <tt>true</tt>
	 * if the given object is also a map entry and the two entries represent the same
	 * mapping. More formally, two entries <tt>e1</tt> and <tt>e2</tt> represent the
	 * same mapping if
	 * 
	 * <pre>
	 * (e1.getTagName() == null ? e2.getTagName() == null : e1.getTagName().equals(
	 * 		e2.getTagName()))
	 * 		&amp;&amp; (e1.getValue() == null ? e2.getValue() == null : e1.getValue().equals(
	 * 				e2.getValue()))
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
		XmlBodyElement other = (XmlBodyElement) obj;

		return (tagName == null ? other.tagName == null : tagName.equals(other.tagName));
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
		return (tagName == null ? 0 : tagName.hashCode());
	}
}
