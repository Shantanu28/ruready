/*****************************************************************************************
 * Source File: Hint.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.pointer.ShallowCloneable;

/**
 * A hint for solving a question. A bastard object of a {@link Question}.
 * {@link Question}-{@link Hint} is a one-to-many relationship.
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
 * @author Greg Felice <code>&lt;greg.felice@gmail.com&gt;</code>
 * @version Aug 12, 2007
 */
@Entity
public class Hint implements PersistentEntity<Long>, PubliclyCloneable, ShallowCloneable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * The unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The part of this hint referring to solution 1.
	 */
	@Column(length = 255)
	private String hint1Text;

	/**
	 * The part of this hint referring to solution 2.
	 */
	@Column(length = 255)
	private String hint2Text;

	/**
	 * A list of keywords referring to solution 1.
	 */
	@Column(length = 255)
	private String keyword1Text;

	/**
	 * A list of keywords referring to solution 2.
	 */
	@Column(length = 255)
	private String keyword2Text;

	// ========================= FIELDS ====================================

	/**
	 * Create an empty hint.
	 */
	public Hint()
	{
		super();
	}

	/**
	 * Create a hint from fields.
	 * 
	 * @param hint1Text
	 *            the part of this hint referring to solution 1
	 * @param hint2Text
	 *            the part of this hint referring to solution 2
	 * @param keyword1Text
	 *            a list of keywords referring to solution 1
	 * @param keyword2Text
	 *            a list of keywords referring to solution 2
	 */
	public Hint(final String hint1Text, final String hint2Text,
			final String keyword1Text, final String keyword2Text)
	{
		super();
		this.hint1Text = hint1Text;
		this.hint2Text = hint2Text;
		this.keyword1Text = keyword1Text;
		this.keyword2Text = keyword2Text;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @return
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Hint clone()
	{
		try
		{
			Hint copy = (Hint) super.clone();
			copy.id = null;
			return copy;
		}
		catch (CloneNotSupportedException e)
		{
			// This shouldn't happen, because we are Cloneable
			throw new InternalError("clone() failed: " + e.getMessage());
		}
	}
	
	// ========================= IMPLEMENTATION: ShallowCloneable ==========

	/**
	 * @return
	 * @see net.ruready.common.pointer.ShallowCloneable#shallowClone()
	 */
	public Object shallowClone()
	{
		return clone();
	}

	/**
	 * Does not copy all fields over - only data fields of this node. Children are not
	 * copied. Identity is not copied.
	 * 
	 * @param destination
	 * @see net.ruready.common.pointer.ShallowCloneable#mergeInto(net.ruready.common.pointer.ShallowCloneable)
	 */
	public void mergeInto(final ShallowCloneable destination)
	{
		Hint dest = (Hint) destination;
		dest.hint1Text = hint1Text;
		dest.hint2Text = hint2Text;
		dest.keyword1Text = keyword1Text;
		dest.keyword2Text = keyword2Text;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return
	 */
	public String getHint1Text()
	{
		return hint1Text;
	}

	/**
	 * @param hint1Text
	 */
	public void setHint1Text(String hint1Text)
	{
		this.hint1Text = hint1Text;
	}

	/**
	 * @return
	 */
	public String getHint2Text()
	{
		return hint2Text;
	}

	/**
	 * @param hint2Text
	 */
	public void setHint2Text(String hint2Text)
	{
		this.hint2Text = hint2Text;
	}

	/**
	 * @return
	 * @see net.ruready.common.eis.entity.PersistentEntity#getId()
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
	// public AssociationManager createAssociationManager()
	// {
	// return null;
	// }

	/**
	 * @param id
	 */
	public void setId(Long id)
	{
		if (id != null && id == 0)
			return;

		this.id = id;
	}

	/**
	 * @return
	 */
	public String getKeyword1Text()
	{
		return keyword1Text;
	}

	/**
	 * @param keyword1Text
	 */
	public void setKeyword1Text(String keyword1Text)
	{
		this.keyword1Text = keyword1Text;
	}

	/**
	 * @return
	 */
	public String getKeyword2Text()
	{
		return keyword2Text;
	}

	/**
	 * @param keyword2Text
	 */
	public void setKeyword2Text(String keyword2Text)
	{
		this.keyword2Text = keyword2Text;
	}

}
