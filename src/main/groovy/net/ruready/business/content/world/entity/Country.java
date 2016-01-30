/*****************************************************************************************
 * Source File: Country.java
 ****************************************************************************************/
package net.ruready.business.content.world.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.common.pointer.ShallowCloneable;

/**
 * An institution item representing a country, e.g. United States.
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
@Entity
public class Country extends Item
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * Phone code for this country.
	 */
	@Column
	private Integer phoneCode = 0;

	/**
	 * Indicates how to append city names when we need to resolve name equality by
	 * appending the county name. This string should be the word "County" in the locale's
	 * language.
	 */
	private String countyString;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected Country()
	{
		super();
	}

	/**
	 * Create a country with a name and comment.
	 * 
	 * @param name
	 *            course name
	 * @param comment
	 *            course comment
	 * @param phoneCode
	 *            area phone code for this country (e.g. 1 for the U.S.)
	 */
	public Country(String name, String comment, int phoneCode)
	{
		super(name, comment);
		this.phoneCode = phoneCode;
	}

	// ========================= IMPLEMENTATION: Item ======================

	/**
	 * @return the type of this item
	 */
	@Override
	public ItemType getIdentifier()
	{
		return ItemType.COUNTRY;
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

		Country dest = (Country) destination;
		dest.phoneCode = phoneCode;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the phoneCode
	 */
	public Integer getPhoneCode()
	{
		return phoneCode;
	}

	/**
	 * @param phoneCode
	 *            the phoneCode to set
	 */
	public void setPhoneCode(Integer phoneCode)
	{
		this.phoneCode = phoneCode;
	}

	/**
	 * Return the countyString property.
	 * 
	 * @return the countyString
	 */
	public String getCountyString()
	{
		return countyString;
	}

	/**
	 * Set a new value for the countyString property.
	 * 
	 * @param countyString
	 *            the countyString to set
	 */
	public void setCountyString(String countyString)
	{
		this.countyString = countyString;
	}

}
