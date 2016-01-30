/*****************************************************************************************
 * Source File: Question.java
 ****************************************************************************************/
package net.ruready.business.content.catalog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.business.content.question.entity.Question;
import net.ruready.common.pointer.ShallowCloneable;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A single expectation statement. Equivalent to {@link Question} in the expectation
 * sub-hierarchy under a course.
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
 * @version Jul 31, 2007
 */
@Entity
public class Expectation extends Item
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Expectation.class);

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * A statement regarding the course expectations of the student.
	 */
	@Column(length = 1000)
	private String formulation = CommonNames.MISC.EMPTY_STRING;

	/**
	 * Is the statement negatively formulated.
	 */
	@Column
	private boolean negative = false;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected Expectation()
	{
		super();
	}

	/**
	 * Create an expectation statement with a name and comment.
	 * 
	 * @param name
	 *            course name
	 * @param comment
	 *            course comment
	 */
	public Expectation(String name, String comment)
	{
		super(name, comment);
	}

	// ========================= METHODS ===================================

	/**
	 * @return the type of this item
	 */
	@Override
	public ItemType getIdentifier()
	{
		return ItemType.EXPECTATION;
	}

	/**
	 * Let a visitor process this item. Part of the TreeVisitor pattern. This calls back
	 * the visitor's <code>visit()</code> method with this item type. Must be overridden
	 * by every item sub-class.
	 * 
	 * @param visitor
	 *            item visitor whose <code>visit()</code> method is invoked
	 */
	@Override
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}

	/**
	 * Does not copy all fields over - only data fields of this node. Children are not
	 * copied. Identity is not copied.
	 * 
	 * @param destination
	 * @see net.ruready.common.pointer.ShallowCloneable#mergeInto(net.ruready.common.pointer.ShallowCloneable)
	 */
	@Override
	public void mergeInto(final ShallowCloneable destination)
	{
		super.mergeInto(destination);

		Expectation dest = (Expectation) destination;
		dest.formulation = formulation;
		dest.negative = negative;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the formulation
	 */
	public String getFormulation()
	{
		return formulation;
	}

	/**
	 * @param formulation
	 *            the formulation to set
	 */
	public void setFormulation(String formulation)
	{
		this.formulation = formulation;
	}

	/**
	 * @return the negative
	 */
	public boolean isNegative()
	{
		return negative;
	}

	/**
	 * @param negative
	 *            the negative to set
	 */
	public void setNegative(boolean negative)
	{
		this.negative = negative;
	}

}
