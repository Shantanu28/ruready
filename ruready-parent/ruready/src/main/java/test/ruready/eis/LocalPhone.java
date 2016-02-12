/*****************************************************************************************
 * Source File: LocalPhone.java
 ****************************************************************************************/
package test.ruready.eis;

import javax.persistence.*;

/**
 * A concrete implementation of a component. This is a normal entity inheriting from the
 * phone abstraction.
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
 * @version Aug 12, 2007
 */
@Entity
public class LocalPhone extends AbstractPhone
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * Locale of the phone number.
	 */
	@Column
	private String locale = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Required for this to be a persistent entity.
	 */
	protected LocalPhone()
	{

	}

	/**
	 * Construct phone from fields.
	 * 
	 * @param number
	 *            phone number
	 * @param locale
	 *            locale
	 */
	public LocalPhone(int number, String locale)
	{
		this.number = number;
		this.locale = locale;
	}

	// ========================= METHODS ===================================

	/**
	 * Print a phone number.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[" + getPhoneType() + "]" + " id " + id + " number " + number
				+ " locale " + locale;
	}

	/**
	 * @see test.ruready.eis.AbstractPhone#getType()
	 */
	@Override
	String getPhoneType()
	{
		return "Local Phone";
	}

}
