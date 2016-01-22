/*****************************************************************************************
 * Source File: GlobalProperty.java
 ****************************************************************************************/
package net.ruready.business.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import net.ruready.common.eis.entity.PersistentEntity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A global property that is uniquely identified by its name.
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class GlobalProperty implements PersistentEntity<Long>
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
	private static final Log logger = LogFactory.getLog(GlobalProperty.class);

	// Uniquely identifying and other useful property references
	public static final String NAME = "name";

	// ========================= FIELDS ====================================

	// ---------------- An entity must have an identifier ----------------
	@Id
	@GeneratedValue
	protected Long id;

	// A unique name identifier for this main item
	@Column(unique = true, length = 30)
	private String name;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected GlobalProperty()
	{
		super();
	}

	/**
	 * Construct a property with a unique name identifier.
	 * 
	 * @param name
	 *            name of this property
	 */
	public GlobalProperty(String name)
	{
		this.name = name;
	}

	// ========================= IMPLEMENTATION: PersistentEntity<Long> =====

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
	//	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS ==========================

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
	protected void setName(String name)
	{
		this.name = name;
	}

}
