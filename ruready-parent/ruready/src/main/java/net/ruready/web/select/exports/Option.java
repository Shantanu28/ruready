package net.ruready.web.select.exports;

import java.util.Map;

import net.ruready.common.pointer.ValueObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * A decorator of a {@link Option} that includes some more properties.
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
 * @see Map#entrySet()
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 27, 2007
 */
public class Option implements ValueObject, ReadOnlyOption, Comparable<Option>
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
	private static final Log logger = LogFactory.getLog(Option.class);

	// ========================= FIELDS ====================================

	/**
	 * We decorate this object.
	 */
	private final LabelValueBean bean;

	/**
	 * Is this an empty selection. Normally the value will be empty or
	 * <code>null</code>, but not necessarily.
	 */
	private boolean empty = false;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a non-empty option in a drop-down menu selection.
	 * 
	 * @param label
	 *            option's label
	 * @param value
	 *            option's value
	 */
	public Option(final String label, final String value)
	{
		this.bean = new LabelValueBean(label, value);
	}

	/**
	 * Create an option in a drop-down menu selection.
	 * 
	 * @param label
	 *            option's label
	 * @param value
	 *            option's value
	 * @param empty
	 *            Is this an empty selection. Normally the value will be empty
	 *            or <code>null</code>, but not necessarily
	 */
	public Option(final String label, final String value, final boolean empty)
	{
		this(label, value);
		this.empty = empty;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @return
	 * @see org.apache.struts.util.Option#toString()
	 */
	@Override
	public String toString()
	{
		return bean.toString() + (empty ? " empty" : "");
	}

	/**
	 * @param obj
	 * @return
	 * @see org.apache.struts.util.Option#equals(java.lang.Object)
	 */
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
		Option other = (Option) obj;

		return bean.equals(other.bean);
	}

	/**
	 * @return
	 * @see org.apache.struts.util.Option#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return bean.hashCode();
	}

	// ========================= IMPLEMENTATION: Comparable<Option> ========

	/**
	 * @param o
	 * @return
	 * @see org.apache.struts.util.Option#compareTo(java.lang.Object)
	 */
	public int compareTo(Option o)
	{
		return bean.compareTo(o.bean);
	}

	// ========================= METHODS (@see Option) =============

	/**
	 * @return
	 * @see org.apache.struts.util.Option#getLabel()
	 */
	public String getLabel()
	{
		return bean.getLabel();
	}

	/**
	 * @return
	 * @see org.apache.struts.util.Option#getValue()
	 */
	public String getValue()
	{
		return bean.getValue();
	}

	/**
	 * @param label
	 * @see org.apache.struts.util.Option#setLabel(java.lang.String)
	 */
	public void setLabel(String label)
	{
		bean.setLabel(label);
	}

	/**
	 * @param value
	 * @see org.apache.struts.util.Option#setValue(java.lang.String)
	 */
	public void setValue(String value)
	{
		bean.setValue(value);
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the empty property.
	 * 
	 * @return the empty
	 */
	public boolean isEmpty()
	{
		return empty;
	}

	/**
	 * Set a new value for the empty property.
	 * 
	 * @param empty
	 *            the empty to set
	 */
	public void setEmpty(boolean empty)
	{
		this.empty = empty;
	}

}
