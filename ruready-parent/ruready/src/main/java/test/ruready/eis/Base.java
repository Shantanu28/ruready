/*****************************************************************************************
 * Source File: Base.java
 ****************************************************************************************/
package test.ruready.eis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import net.ruready.common.eis.entity.PersistentEntity;

/**
 * A base class for Hibernate inheritance tests.
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Base implements PersistentEntity<Long>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * Unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	protected Long id;

	/**
	 * A base name field.
	 */
	@Column(name = "base_name", length = 200)
	private String baseName;

	// ========================= CONSTRUCTORS ===============================

	/**
	 * 
	 */
	protected Base()
	{
		super();
	}

	/**
	 * @param baseName
	 */
	public Base(String baseName)
	{
		super();
		this.baseName = baseName;
	}

	// ========================= IMPLEMENTATION: PersistentEntity<Long> ====

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
	// @Override
	// public AssociationManager createAssociationManager()
	// {
	// return null;
	//	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the baseName
	 */
	public String getBaseName()
	{
		return baseName;
	}

	/**
	 * @param baseName
	 *            the baseName to set
	 */
	public void setBaseName(String baseName)
	{
		this.baseName = baseName;
	}

}
