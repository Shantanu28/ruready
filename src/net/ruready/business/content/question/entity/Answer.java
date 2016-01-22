/*****************************************************************************************
 * Source File: Answer.java
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
 * A solution (final answer) to a question. A bastard object of a {@link Question}.
 * {@link Question}-{@link Answer} is a one-to-many relationship.
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
public class Answer implements PersistentEntity<Long>, PubliclyCloneable,
		ShallowCloneable
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
	 * The answer. :-)
	 */
	@Column(length = 255)
	private String answerText;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty answer.
	 */
	public Answer()
	{

	}

	/**
	 * Create an answer with text.
	 * 
	 * @param answerText
	 *            answer text
	 */
	public Answer(final String answerText)
	{
		super();
		this.answerText = answerText;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @return
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Answer clone()
	{
		try
		{
			Answer copy = (Answer) super.clone();
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
		Answer dest = (Answer) destination;
		dest.answerText = answerText;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return
	 */
	public String getAnswerText()
	{
		return answerText;
	}

	/**
	 * @param answerText
	 */
	public void setAnswerText(String answerText)
	{
		this.answerText = answerText;
	}

	/**
	 * @return
	 * @see net.ruready.common.eis.entity.PersistentEntity#getId()
	 */
	public Long getId()
	{
		return id;
	}

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

}
