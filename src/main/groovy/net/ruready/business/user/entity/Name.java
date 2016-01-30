/*****************************************************************************************
 * Source File: User.java
 ****************************************************************************************/
package net.ruready.business.user.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import net.ruready.common.pointer.ValueObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A person's name. Intended to be embedded in other entities.
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
 * @author Jeremy Lund
 * @version September 19, 2007
 */
@Embeddable
public class Name implements ValueObject
{
	// ========================= CONSTANTS =================================

	private static final long serialVersionUID = 519587831099007726L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Name.class);

	// ========================= FIELDS ====================================

	/**
	 * Person's first name.
	 */
	@Column(length = 40)
	private String firstName;

	/**
	 * Person's middle initial.
	 */
	@Column(length = 1)
	private String middleInitial;

	/**
	 * Person's last name.
	 */
	@Column(length = 40)
	private String lastName;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty item whose items are held as a sorted tree with toggling.
	 * Must be public because {@link Name} is a JavaBean.
	 */
	public Name()
	{
		super();
	}

	/**
	 * Construct a name from required fields.
	 * 
	 * @param firstName
	 *            user's first name
	 * @param middleInitial
	 *            user's middle initial
	 * @param lastName
	 *            user's last name
	 */
	public Name(final String firstName, final String middleInitial, final String lastName)
	{
		super();
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
	}

	// ========================= IMPLEMENTATION: Object =====================

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((middleInitial == null) ? 0 : middleInitial.hashCode());
		return result;
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Name other = (Name) obj;
		if (firstName == null)
		{
			if (other.firstName != null)
				return false;
		}
		else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null)
		{
			if (other.lastName != null)
				return false;
		}
		else if (!lastName.equals(other.lastName))
			return false;
		if (middleInitial == null)
		{
			if (other.middleInitial != null)
				return false;
		}
		else if (!middleInitial.equals(other.middleInitial))
			return false;
		return true;
	}

	/**
	 * Print this bean.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return firstName + " " + middleInitial + ". " + lastName;
	}

	// ========================= METHODS ===================================

	/**
	 * Returns whether a user a specified a name or not. They have specified
	 * their name if either the first name or last name is not empty
	 * 
	 * @return true if they have specified their name, false otherwise
	 */
	public Boolean hasName()
	{
		return (StringUtils.isNotBlank(getFirstName()) || StringUtils
				.isNotBlank(getLastName()));
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the middleInitial
	 */
	public String getMiddleInitial()
	{
		return middleInitial;
	}

	/**
	 * @param middleInitial
	 *            the middleInitial to set
	 */
	public void setMiddleInitial(String middleInitial)
	{
		this.middleInitial = middleInitial;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFormattedName()
	{
		final StringBuilder nameBuilder = new StringBuilder("");
		if (StringUtils.isNotBlank(getLastName()))
		{
			nameBuilder.append(getLastName()).append(", ");
		}
		nameBuilder.append(getFirstName());
		if (StringUtils.isNotBlank(getMiddleInitial()))
		{
			nameBuilder.append(" ").append(getMiddleInitial()).append(".");
		}
		return nameBuilder.toString();
	}
}
