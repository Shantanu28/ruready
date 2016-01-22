/*****************************************************************************************
 * Source File: Choice.java
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
 * A choice out of a list of multiple choice to a question. A bastard object of a
 * {@link Question}. {@link Question}-{@link Choice} is a one-to-many relationship.
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
public class Choice implements PersistentEntity<Long>, PubliclyCloneable,
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
	 * The choice's text.
	 */
	@Column(length = 150)
	private String choiceText;

	/**
	 * Is this the correct choice in the question.
	 */
	@Column
	private boolean correct;

	// ========================= FIELDS ====================================

	/**
	 * Create an empty choice.
	 */
	public Choice()
	{
		super();
	}

	/**
	 * Create a multiple choice with text.
	 * 
	 * @param answerText
	 *            answer text
	 */
	public Choice(final String choiceText)
	{
		this.choiceText = choiceText;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @return
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Choice clone()
	{
		try
		{
			Choice copy = (Choice) super.clone();
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
		Choice dest = (Choice) destination;
		dest.choiceText = choiceText;
		dest.correct = correct;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return
	 */
	public String getChoiceText()
	{
		return choiceText;
	}

	/**
	 * @param choiceText
	 */
	public void setChoiceText(String choiceText)
	{
		this.choiceText = choiceText;
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
	// @Override
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
	public boolean isCorrect()
	{
		return correct;
	}

	/**
	 * @param correct
	 */
	public void setCorrect(boolean correct)
	{
		this.correct = correct;
	}
}
