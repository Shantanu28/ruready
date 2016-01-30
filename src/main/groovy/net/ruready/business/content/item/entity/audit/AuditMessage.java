/*****************************************************************************************
 * Source File: AuditMessage.java
 ****************************************************************************************/
package net.ruready.business.content.item.entity.audit;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import net.ruready.business.common.audit.entity.AbstractMessage;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.user.entity.Name;
import net.ruready.business.user.entity.User;
import net.ruready.common.audit.Message;
import net.ruready.common.exception.SystemException;
import net.ruready.common.pointer.PubliclyCloneable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A message generated every time a revision is made to an entity. It is used for version
 * control and auditing. Immutable.
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
public class AuditMessage extends AbstractMessage implements PubliclyCloneable
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
	private static final Log logger = LogFactory.getLog(AuditMessage.class);

	// ========================= FIELDS ====================================

	/**
	 * User that generated this message: E-mail address. A unique business key of the
	 * user.
	 */
	@Column(name = User.EMAIL, length = 255)
	private String email;

	/**
	 * User's name (optional for a normal user but normally required for users that are
	 * allowed to create messages).
	 */
	@Embedded
	private Name name;

	/**
	 * Type of revision/action of entity that this message represents.
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	private AuditAction action;

	/**
	 * Version number of entity at the time of message creation.
	 */
	@Column
	private Integer version;

	// /**
	// * Parent item.
	// */
	// @ManyToOne
	// private Item item;
	//
	// ========================= CONSTRUCTORS ===============================

	/**
	 * Construct an empty message. A no-argument constructor with at least protected scope
	 * visibility is required for Hibernate and portability to other EJB 3 containers.
	 * This constructor populates no properties inside this class.
	 */
	protected AuditMessage()
	{

	}

	/**
	 * Create a message from fields.
	 * 
	 * @param item
	 *            audited item
	 * @param user
	 *            user that generated this message
	 * @param action
	 *            describes the type of action applied to the entity
	 * @param comment
	 *            optional comment
	 * @param version
	 *            version of entity at the time of this message creation
	 */
	public AuditMessage(final Item item, final User user, final AuditAction action,
			final String comment, final Integer version)
	{
		super(comment);
		// this.item = item;
		this.email = user.getEmail();
		this.name = user.getName();
		this.action = action;
		this.version = version;
	}

	// ========================= IMPLEMENTATION: Object =====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[Audit] ID " + id + " " + action + " revision " + version;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to serve as
	 * a target (or part of a target) of an assembly. Copies all fields except the
	 * identifier.
	 * 
	 * @return a deep copy of this object.
	 */
	@Override
	public AuditMessage clone()
	{
		try
		{
			AuditMessage copy = (AuditMessage) super.clone();
			copy.id = null;
			return copy;
		}

		catch (CloneNotSupportedException e)
		{
			// this shouldn't happen, because we are Cloneable
			throw new InternalError("clone() failed: " + e.getMessage());
		}
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
		AuditMessage other = (AuditMessage) o;

		// Compare by descending date
		int compareDate = -date.compareTo(other.date);
		if (compareDate != 0)
		{
			return compareDate;
		}

		// Compare by descending version
		// logger.debug("this " + this + " time " + this.getDate().getTime());
		// logger.debug("other " + other + " time " + other.getDate().getTime());
		int compareVersion = ((version == null) ? ((other.version == null) ? 0 : 1)
				: ((other.version == null) ? -1 : version.compareTo(other.version)));
		// logger.debug("compareVersion " + compareVersion);
		// if ((this.version != null) && !version.equals(other.version) && (result == 0))
		// {
		// logger.error("this " + this + " time " + this.getDate().getTime());
		// logger.error("other " + other + " time " + other.getDate().getTime());
		// logger.error("Result of comparison " + result);
		// }
		return compareVersion;
		// return 0;
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the action
	 */
	public AuditAction getAction()
	{
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	protected void setAction(AuditAction action)
	{
		this.action = action;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	protected void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return name.getFirstName();
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		name.setFirstName(firstName);
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return name.getLastName();
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName)
	{
		name.setLastName(lastName);
	}

	/**
	 * @return the middleInitial
	 */
	public String getMiddleInitial()
	{
		return name.getMiddleInitial();
	}

	/**
	 * @param middleInitial
	 *            the middleInitial to set
	 */
	public void setMiddleInitial(String middleInitial)
	{
		name.setMiddleInitial(middleInitial);
	}
	//
	// /**
	// * @return the item
	// */
	// public Item getItem()
	// {
	// return item;
	// }

}
