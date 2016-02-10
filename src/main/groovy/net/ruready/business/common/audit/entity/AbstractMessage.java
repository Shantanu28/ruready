/*****************************************************************************************
 * Source File: AbstractMessage.java
 ****************************************************************************************/
package net.ruready.business.common.audit.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import net.ruready.business.common.tree.entity.Commentable;
import net.ruready.common.audit.Message;
import net.ruready.eis.type.UtcTimestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 * An abstraction of message types. A message generated every time a revision is made to
 * an entity in the catalog, or when a user logs in/out, etc. Immutable.
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
//@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@MappedSuperclass
public abstract class AbstractMessage implements Message, Commentable
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
	private static final Log logger = LogFactory.getLog(AbstractMessage.class);

	// ========================= FIELDS ====================================

	/**
	 * The unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	protected Long id;

	/**
	 * An optional comment.
	 */
	@Column(length = 80)
	protected String comment;

	/**
	 * Date of this message. Is a final field (but not declared as such because of
	 * Hibernate's reflection needs).
	 */
	@Type(type = "utcTimestamp")
	@Column(updatable = false, nullable = false)
	protected Timestamp date = new Timestamp(new Date().getTime());

	// ========================= CONSTRUCTORS ===============================

	/**
	 * Construct an empty message. A no-argument constructor with at least protected scope
	 * visibility is required for Hibernate and portability to other EJB 3 containers.
	 * This constructor populates no properties inside this class.
	 */
	protected AbstractMessage()
	{

	}

	/**
	 * Create a message from fields.
	 * 
	 * @param comment
	 *            optional comment
	 */
	public AbstractMessage(String comment)
	{
		super();
		this.comment = comment;
		// logger.debug("created, date " + date.getTime());
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	/**
	 * Consistent with {@link #compareTo(Message)}.
	 * 
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
			// if (!AbstractMessage.class.isAssignableFrom(obj.getClass())) // Alternative
			// that might work better with Hibernate
			return false;
		final AbstractMessage other = (AbstractMessage) obj;
		if (date == null)
		{
			if (other.date != null)
				return false;
		}
		else if (!date.equals(other.date))
			return false;
		return true;
	}

	// ========================= IMPLEMENTATION: PersistentEntity<Long> ====

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	//
	// /**
	// * Create and return an association manager for this entity type.
	// *
	// * @return an association manager for this entity type.
	// * @see net.ruready.common.eis.entity.PersistentEntity#createAssociationManager()
	// */
	// @Override
	// public AssociationManager createAssociationManager()
	// {
	// return null;
	// }

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the comment
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	protected void setComment(String comment)
	{
		this.comment = comment;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate()
	{
		return date;
	}

}
