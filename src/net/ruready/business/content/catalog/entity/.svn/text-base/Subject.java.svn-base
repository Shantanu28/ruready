/*****************************************************************************************
 * Source File: Subject.java
 ****************************************************************************************/
package net.ruready.business.content.catalog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import net.ruready.business.common.tree.comparator.TreeNodeComparatorID;
import net.ruready.business.content.concept.entity.ConceptCollection;
import net.ruready.business.content.interest.entity.InterestCollection;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.business.content.skill.entity.SkillCollection;
import net.ruready.business.content.tag.entity.MainTagItem;
import net.ruready.common.pointer.ShallowCloneable;
import net.ruready.common.visitor.Visitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A catalog item representing a subject, e.g. Mathematics or Chemistry.
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
 * @version Aug 11, 2007
 */
@Entity
public class Subject extends Item
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
	private static final Log logger = LogFactory.getLog(Subject.class);

	// ========================= FIELDS ====================================

	/**
	 * University catalog abbreviation (e.g. "MATH" for the subject
	 * "Mathematics").
	 */
	@Column(length = 20)
	private String abbreviation = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is
	 * required for Hibernate and portability to other EJB 3 containers. This
	 * constructor populates no properties inside this class.
	 */
	protected Subject()
	{
		super();
	}

	/**
	 * Create a subject with a name and comment.
	 * 
	 * @param name
	 *            course name
	 * @param comment
	 *            course comment
	 */
	public Subject(String name, String comment)
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
		return ItemType.SUBJECT;
	}

	/**
	 * Let a visitor process this item. Part of the TreeVisitor pattern. This
	 * calls back the visitor's
	 * {@link Visitor#visit(net.ruready.common.visitor.Visitable)} method with
	 * this item type. Must be overridden by every item sub-class.
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
	 * Does not copy all fields over - only data fields of this node. Children
	 * are not copied. Identity is not copied.
	 * 
	 * @param destination
	 * @see net.ruready.common.pointer.ShallowCloneable#mergeInto(net.ruready.common.pointer.ShallowCloneable)
	 */
	@Override
	public void mergeInto(final ShallowCloneable destination)
	{
		super.mergeInto(destination);

		Subject dest = (Subject) destination;
		dest.abbreviation = abbreviation;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation()
	{
		return abbreviation;
	}

	/**
	 * @param abbreviation
	 *            the abbreviation to set
	 */
	public void setAbbreviation(String abbreviation)
	{
		this.abbreviation = abbreviation;
	}

	/**
	 * Returns the student interest tag folder that all items under this subject
	 * use, and in particular, questions.
	 * 
	 * @return the interestCollection
	 */
	public InterestCollection getInterestCollection()
	{
		return this.getFirstTag(InterestCollection.class);
	}

	/**
	 * @param interestCollection
	 *            the interestCollection to set
	 */
	public void setInterestCollection(InterestCollection interestCollection)
	{
		this.setUniqueTag(InterestCollection.class, interestCollection);
	}

	/**
	 * Returns the concept tag folder that all items under this subject use, and
	 * in particular, questions.
	 * 
	 * @return the conceptCollection
	 */
	public ConceptCollection getConceptCollection()
	{
		return this.getFirstTag(ConceptCollection.class);
	}

	/**
	 * @param conceptCollection
	 *            the conceptCollection to set
	 */
	public void setConceptCollection(ConceptCollection conceptCollection)
	{
		this.setUniqueTag(ConceptCollection.class, conceptCollection);
	}

	/**
	 * Returns the skill tag folder that all items under this subject use, and
	 * in particular, questions.
	 * 
	 * @return the skillCollection
	 */
	public SkillCollection getSkillCollection()
	{
		return this.getFirstTag(SkillCollection.class);
	}

	/**
	 * @param skillCollection
	 *            the skillCollection to set
	 */
	public void setSkillCollection(SkillCollection skillCollection)
	{
		this.setUniqueTag(SkillCollection.class, skillCollection);
	}

	/**
	 * Get a tag collection attached to this item.
	 * 
	 * @param tagType
	 *            tag item type (child type of the returned tag collection item)
	 * @return tag collection item
	 */
	public MainTagItem getTagCollection(final ItemType tagType)
	{
		switch (tagType)
		{
			case INTEREST:
			{
				return getInterestCollection();
			}

			case CONCEPT:
			{
				return getConceptCollection();
			}

			case SKILL:
			{
				return getSkillCollection();
			}

			default:
			{
				logger.error("Unsupported tag type " + tagType);
				return null;
			}
		}

	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Perform initializations at the end of each constructor: initialize the
	 * data structure holding the collection of children, set a default printer,
	 * etc.
	 * 
	 * @see net.ruready.business.common.tree.entity.Node#setUp()
	 */
	@Override
	protected void setUp()
	{
		super.setUp();

		// Allow custom course ordering within a subject
		setComparatorType(TreeNodeComparatorID.SERIAL_NO);
	}

}
