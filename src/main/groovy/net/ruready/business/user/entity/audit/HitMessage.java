/*****************************************************************************************
 * Source File: HitMessage.java
 ****************************************************************************************/
package net.ruready.business.user.entity.audit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import net.ruready.business.common.audit.entity.AbstractMessage;
import net.ruready.common.audit.Message;
import net.ruready.common.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A message generated per each front page hit.
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
 * @version Aug 1, 2007
 */
@Entity
public class HitMessage extends AbstractMessage
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
	private static final Log logger = LogFactory.getLog(HitMessage.class);

	// ========================= FIELDS ====================================

	// Host from which the hit was received
	// This doesn't seem to be informative because it is the requested resource
	// on the server, which is usually "localhost:8080" or so.
	@Transient
	private String host;

	// Browser type (user-agent HTTP header)
	private String userAgent;

	// ========================= CONSTRUCTORS ===============================

	/**
	 * Construct an empty message. A no-argument constructor with at least protected scope
	 * visibility is required for Hibernate and portability to other EJB 3 containers.
	 * This constructor populates no properties inside this class.
	 */
	protected HitMessage()
	{

	}

	/**
	 * Create a message from fields.
	 * 
	 * @param host
	 *            user host
	 * @param userAgent
	 *            user browser type
	 */
	public HitMessage(String host, String userAgent)
	{
		super();
		this.host = host;
		this.userAgent = userAgent;
	}

	// ========================= IMPLEMENTATION: Object =====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[Hit] host " + host + " user-agent " + userAgent;
	}

	// ========================= IMPLEMENTATION: Comparable<Message> =======

	/**
	 * Compare tags by name (lexicographic ordering).
	 * 
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Message o)
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

		// Cast to friendlier version
		AbstractMessage other = (AbstractMessage) o;

		// Compare by descending date
		int compareDate = -date.compareTo(other.getDate());
		return compareDate;
	}

	// ========================= METHODS ====================================

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the host
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent()
	{
		return userAgent;
	}

	/**
	 * @param userAgent
	 *            the userAgent to set
	 */
	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

}
