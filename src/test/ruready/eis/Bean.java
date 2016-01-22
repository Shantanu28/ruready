/*****************************************************************************************
 * Source File: Bean.java
 ****************************************************************************************/
package test.ruready.eis;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.web.common.util.StrutsUtil;

/**
 * A simple bean for Hibernate tests.
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
public class Bean implements PersistentEntity<Long>
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
	 * A persistent field.
	 */
	private String field1;

	/**
	 * A transient field.
	 */
	@Transient
	private String field2;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected Bean()
	{
		super();
	}

	/**
	 * @param field1
	 * @param field2
	 */
	public Bean(String field1, String field2)
	{
		super();
		this.field1 = field1;
		this.field2 = field2;
	}

	// ========================= IMPLEMENTATION: Object =====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return StrutsUtil.formToString(this);
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
	 * @return the field1
	 */
	public String getField1()
	{
		return field1;
	}

	/**
	 * @param field1
	 *            the field1 to set
	 */
	public void setField1(String field1)
	{
		this.field1 = field1;
	}

	/**
	 * @return the field2
	 */
	public String getField2()
	{
		return field2;
	}

	/**
	 * @param field2
	 *            the field2 to set
	 */
	public void setField2(String field2)
	{
		this.field2 = field2;
	}

}
