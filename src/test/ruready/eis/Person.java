/*****************************************************************************************
 * Source File: Person.java
 ****************************************************************************************/
package test.ruready.eis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.ruready.common.eis.entity.PersistentEntity;

/**
 * A person that may be a member of a group.
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
public class Person implements PersistentEntity<Long>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * A unique identifier (could belong instead to AbstractUser had we made it an
	 * abstract class).
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The user's name.
	 */
	@Column
	private String name = null;

	// ========================= CONSTRUCTORS ==============================
	/**
	 * Required for this to be a persistent entity.
	 */
	public Person()
	{
		super();
	}

	/**
	 * Construct phone from fields.
	 * 
	 * @param name
	 *            The user's name
	 */
	public Person(String name)
	{
		super();
		this.name = name;
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

}
