/*****************************************************************************************
 * Source File: Forum.java
 ****************************************************************************************/
package test.ruready.eis;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import net.ruready.common.eis.entity.PersistentEntity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Represents a group of persons.
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
public class Forum implements PersistentEntity<Long>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	// ---------------- An entity must have an identifier ----------------
	@Id
	@GeneratedValue
	protected Long id;

	// ---------------- Participants ---------------------------------------

	/**
	 * The list's moderator.
	 */
	@OneToOne(optional = true)
	@Cascade(
	{
			CascadeType.SAVE_UPDATE, CascadeType.PERSIST
	})
	private Person moderator;

	/**
	 * Forum groups ("classes") that the user belongs to and have personalized views.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade(
	{
			CascadeType.ALL, CascadeType.PERSIST
	})
	private List<Person> persons = new ArrayList<Person>();

	// ---------------- Data Fields ----------------------------------------

	/**
	 * Forum's name (optional).
	 */
	@Column(length = 80)
	private String name;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty item whose items are held as a sorted tree with toggling. Must be
	 * public because <code>Forum</code> is a JavaBean.
	 */
	public Forum()
	{
		super();
	}

	/**
	 * Construct a user from required fields.
	 * 
	 * @param name
	 * @param moderator
	 */
	public Forum(Person moderator)
	{
		super();
		this.moderator = moderator;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a phone number.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[" + id + "] " + name;
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the id
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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the persons
	 */
	public List<Person> getPersons()
	{
		return persons;
	}

	/**
	 * @param persons
	 *            the persons to set
	 */
	public void setPersons(List<Person> persons)
	{
		this.persons = persons;
	}

	/**
	 * @return the moderator
	 */
	public Person getArithmeticModerator()
	{
		return moderator;
	}

	/**
	 * @return the persons
	 */
	public void addPerson(Person person)
	{
		persons.add(person);
	}

	/**
	 * @return the persons
	 */
	public void removePerson(Person person)
	{
		persons.remove(person);
	}

	/**
	 * @param moderator
	 *            the moderator to set
	 */
	public void setModerator(Person moderator)
	{
		this.moderator = moderator;
	}

}
