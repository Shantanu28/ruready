/*****************************************************************************************
 * Source File: School.java
 ****************************************************************************************/
package net.ruready.business.content.world.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.business.content.world.entity.property.InstitutionType;
import net.ruready.business.content.world.entity.property.Sector;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.common.pointer.ShallowCloneable;

/**
 * An institution item representing a school, e.g. Olympus High School or University of
 * Utah.
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
public class School extends Item
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * School's city. Will be deprecated very soon. Required for backward compatibility
	 * with world xsd 1.3.
	 */
	@Deprecated
	private String city;

	/**
	 * County that the school resides in. Will be deprecated very soon. Required for
	 * backward compatibility with world xsd 1.3.
	 */
	@Deprecated
	private String county;

	/**
	 * School's address (Line 1).
	 */
	private String address1;

	/**
	 * School's address (Line 2).
	 */
	private String address2;

	/**
	 * Institution type.
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	private InstitutionType institutionType;

	/**
	 * Sector (private/public/...).
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	private Sector sector;

	/**
	 * School zip-code.
	 */
	private String zipCode;

	/**
	 * School district of this school.
	 */
	private String district;

	/**
	 * School primary phone number.
	 */
	private String phone1;

	/**
	 * School secondary phone number.
	 */
	private String phone2;

	/**
	 * School fax number.
	 */
	private String fax;

	/**
	 * School's home page.
	 */
	@Column(length = 255)
	private String url;

	/**
	 * Teachers who are teach in this school.
	 */
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private Set<TeacherSchoolMembership> teachers = new HashSet<TeacherSchoolMembership>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected School()
	{
		super();
	}

	/**
	 * Create a school with a name and comment.
	 * 
	 * @param name
	 *            course name
	 * @param comment
	 *            course comment
	 */
	public School(String name, String comment)
	{
		super(name, comment);
	}

	// ========================= IMPLEMENTATION: Item ======================

	/**
	 * @return the type of this item
	 */
	@Override
	public ItemType getIdentifier()
	{
		return ItemType.SCHOOL;
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

	// ========================= IMPLEMENTATION: ShallowCloneable ==========

	/**
	 * @return
	 * @see net.ruready.business.common.tree.entity.Node#shallowClone()
	 */
	@Override
	public Item shallowClone()
	{
		School copy = (School) super.shallowClone();

		// Custom field setting
		copy.teachers = new HashSet<TeacherSchoolMembership>();

		return copy;
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

		School dest = (School) destination;
		dest.address1 = address1;
		dest.address2 = address2;
		dest.institutionType = institutionType;
		dest.sector = sector;
		dest.zipCode = zipCode;
		dest.county = county;
		dest.district = district;
		dest.phone1 = phone1;
		dest.phone2 = phone2;
		dest.fax = fax;
		dest.url = url;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the zipCode
	 */
	public String getZipCode()
	{
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the county
	 */
	public String getCounty()
	{
		return county;
	}

	/**
	 * @param county
	 *            the county to set
	 */
	public void setCounty(String county)
	{
		this.county = county;
	}

	/**
	 * @return the district
	 */
	public String getDistrict()
	{
		return district;
	}

	/**
	 * @param district
	 *            the district to set
	 */
	public void setDistrict(String district)
	{
		this.district = district;
	}

	/**
	 * @return the sector
	 */
	public Sector getSector()
	{
		return sector;
	}

	/**
	 * @param sector
	 *            the sector to set
	 */
	public void setSector(Sector sector)
	{
		this.sector = sector;
	}

	/**
	 * @return the institutionType
	 */
	public InstitutionType getInstitutionType()
	{
		return institutionType;
	}

	/**
	 * @param institutionType
	 *            the institutionType to set
	 */
	public void setInstitutionType(InstitutionType institutionType)
	{
		this.institutionType = institutionType;
	}

	/**
	 * @return the phone1
	 */
	public String getPhone1()
	{
		return phone1;
	}

	/**
	 * @param phone1
	 *            the phone1 to set
	 */
	public void setPhone1(String phone1)
	{
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2()
	{
		return phone2;
	}

	/**
	 * @param phone2
	 *            the phone2 to set
	 */
	public void setPhone2(String phone2)
	{
		this.phone2 = phone2;
	}

	/**
	 * @return the fax
	 */
	public String getFax()
	{
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	/**
	 * @param teachers
	 *            the teachers to set
	 */
	public void setTeachers(Set<TeacherSchoolMembership> teachers)
	{
		this.teachers = teachers;
	}

	public Set<TeacherSchoolMembership> getTeachers()
	{
		return teachers;
	}

	/**
	 * Return the address1 property.
	 * 
	 * @return the address1
	 */
	public String getAddress1()
	{
		return address1;
	}

	/**
	 * Set a new value for the address1 property.
	 * 
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}

	/**
	 * Return the address2 property.
	 * 
	 * @return the address2
	 */
	public String getAddress2()
	{
		return address2;
	}

	/**
	 * Set a new value for the address2 property.
	 * 
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	/**
	 * Returns the city property.
	 * 
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * Sets a new city property value.
	 * 
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * Creates a friendly description of course for dropdowns, page views, etc.
	 * 
	 * @return the friendly description
	 */
	public String getDescription()
	{
		return addWorldElement(this);
	}

	/**
	 * Recursive helper method used to generate a friendly, fully-qualified name for this
	 * School
	 * 
	 * @param item
	 *            the current item to visit
	 * @return a friendly description for item and its ancestors
	 */
	private String addWorldElement(final Item item)
	{
		switch (item.getIdentifier())
		{
			case SCHOOL:
				return item.getName() + addWorldElement(item.getParent());
			case CITY:
			case STATE:
				return ", " + item.getName() + addWorldElement(item.getParent());
			case COUNTRY:
			case FEDERATION:
				return ", " + item.getName();
			default:
				return "";
		}
	}
}
