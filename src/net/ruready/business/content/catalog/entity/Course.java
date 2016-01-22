/*****************************************************************************************
 * Source File: Course.java
 ****************************************************************************************/
package net.ruready.business.content.catalog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang.StringUtils;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.common.pointer.ShallowCloneable;

/**
 * A catalog item representing a college course, e.g. Calculus.
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
 * @version Aug 11, 2007
 */
@Entity
public class Course extends Item
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * University catalog number (e.g. "1210" for the "Calculus 1210" course).
	 */
	@Column(length = 20)
	private String univCatalogNumber = null;

	/**
	 * University of Utah link to course comment / information.
	 */
	@Column(length = 255)
	private String syllabusUrl = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected Course()
	{
		super();
	}

	/**
	 * Create a course with a name and comment.
	 * 
	 * @param name
	 *            course name
	 * @param comment
	 *            course comment
	 */
	public Course(String name, String comment)
	{
		super(name, comment);
	}

	/**
	 * Create a course from properties.
	 * 
	 * @param name
	 *            course name
	 * @param comment
	 *            course comment
	 * @param univCatalogNumber
	 *            University catalog number (e.g. "1210" for the "Calculus 1210" course)
	 * @param syllabusUrl
	 *            University of Utah link to course comment / information
	 */
	public Course(String name, String comment, String univCatalogNumber,
			String syllabusUrl)
	{
		super(name, comment);
		this.univCatalogNumber = univCatalogNumber;
		this.syllabusUrl = syllabusUrl;
	}

	// ========================= IMPLEMENTATION: Item ======================

	/**
	 * @return the type of this item
	 */
	@Override
	public ItemType getIdentifier()
	{
		return ItemType.COURSE;
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
	 * @see net.ruready.common.tree.DemoCatalogCreator#printData()
	 */
	@Override
	public String printData()
	{
		return "[" + getType() + " ID " + getId() + "] " + getName() + " "
				+ getUnivCatalogNumber()
				+ ((getSerialNo() != null) ? (" #" + getSerialNo()) : "");
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

		Course dest = (Course) destination;
		dest.syllabusUrl = syllabusUrl;
		dest.univCatalogNumber = univCatalogNumber;
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================
	/**
	 * @return the univCatalogNumber
	 */
	public String getUnivCatalogNumber()
	{
		return univCatalogNumber;
	}

	/**
	 * @param univCatalogNumber
	 *            the univCatalogNumber to set
	 */
	public void setUnivCatalogNumber(String univCatalogNumber)
	{
		this.univCatalogNumber = univCatalogNumber;
	}

	/**
	 * @return the syllabusUrl
	 */
	public String getSyllabusUrl()
	{
		return syllabusUrl;
	}

	/**
	 * @param syllabusUrl
	 *            the syllabusUrl to set
	 */
	public void setSyllabusUrl(String syllabusUrl)
	{
		this.syllabusUrl = syllabusUrl;
	}
	
	/**
	 * Creates a friendly description of course for dropdowns, page views, etc.
	 * @return the friendly description
	 */
	public String getDescription()
	{
		if (StringUtils.isNotBlank(getUnivCatalogNumber()))
		{
			return new StringBuilder(getName())
				.append(" - ")
				.append(getUnivCatalogNumber())
				.toString();
		}
		return getName();
	}

}
