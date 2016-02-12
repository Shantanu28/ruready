/*****************************************************************************************
 * Source File: TimeSpan.java
 ****************************************************************************************/
package net.ruready.eis.audit;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.ruready.common.exception.SystemException;
import net.ruready.common.pointer.ValueObject;
import net.ruready.eis.type.UtcTimestamp;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 * Time interval <code>[startDate, endDate]</code>.
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
@TypeDefs(
{
	@TypeDef(name = "utcTimestamp", typeClass = UtcTimestamp.class)
})
@Entity
public class TimeSpan implements Comparable<TimeSpan>, ValueObject
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
	private static final Log logger = LogFactory.getLog(TimeSpan.class);

	/**
	 * These fields handle format of date in message printouts.
	 */
	private static final String DATE_FORMAT = "MMM dd, yyyy hh:mm:ss";

	// ========================= FIELDS ====================================

	/**
	 * The unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	protected Long id;

	/**
	 * Start date of this time span.
	 */
	@Type(type = "utcTimestamp")
	private Timestamp startDate;

	/**
	 * End date of this time span.
	 */
	@Type(type = "utcTimestamp")
	private Timestamp endDate;

	// ========================= CONSTRUCTORS ===============================

	/**
	 * Create a time span. Both start, end dates are yet unset.
	 * <p>
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 * 
	 * @param comment
	 *            optional comment
	 */
	public TimeSpan()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		final TimeSpan other = (TimeSpan) obj;
		if (endDate == null)
		{
			if (other.endDate != null)
				return false;
		}
		else if (!endDate.equals(other.endDate))
			return false;
		if (startDate == null)
		{
			if (other.startDate != null)
				return false;
		}
		else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	/**
	 * Print an interval.
	 */
	@Override
	public String toString()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(TimeSpan.DATE_FORMAT);
		dateFormat.setTimeZone(DateUtils.UTC_TIME_ZONE);
		String startStr = (startDate == null) ? "---" : dateFormat.format(startDate);
		String endStr = (endDate == null) ? "---" : dateFormat.format(endDate);
		return "[" + startStr + ", " + endStr + "]";
	}

	// ========================= IMPLEMENTATION: Comparable<Message> =======

	/**
	 * Compare time spans by start date, then by end date (lexicographic ordering).
	 * 
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(TimeSpan o)
	{
		if (o == null)
		{
			return 1;
		}

		if (o.getClass() != this.getClass())
		{
			throw new SystemException("Cannot compare "
					+ this.getClass() + " with " + o.getClass());
		}

		// Compare by start date
		int compareStartDate = (startDate == null) ? 1 : startDate.compareTo(o.startDate);
		if (compareStartDate != 0)
		{
			return compareStartDate;
		}

		// Compare by end date. [startDate, null] < [startDate, endDate].
		int compareEndDate = (endDate == null) ? 1 : endDate.compareTo(o.endDate);
		return compareEndDate;
	}

	// ========================= IMPLEMENTATION: PersistentEntity<Long> ====

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the startDate
	 */
	public Timestamp getStartDate()
	{
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(final Date startDate)
	{
		this.startDate = new Timestamp(startDate.getTime());
	}

	/**
	 * @return the endDate
	 */
	public Timestamp getEndDate()
	{
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(final Date endDate)
	{
		this.endDate = new Timestamp(endDate.getTime());
	}

}
