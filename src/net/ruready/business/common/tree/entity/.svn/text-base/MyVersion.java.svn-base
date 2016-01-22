/*****************************************************************************************
 * Source File: Version.java
 ****************************************************************************************/

package net.ruready.business.common.tree.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.ruready.common.eis.entity.PersistentEntity;

/**
 * Created for testing purposes. Turns out that an at-Version annotation requires int,
 * long, short or timestamp, and does not allow embedded objects like the this one.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 1, 2007
 */
@Entity
class MyVersion implements PersistentEntity<Long>, Comparable<MyVersion>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= PERSISTENT FIELDS =========================

	/**
	 * The unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	protected Long id;

	@Column
	private final int value;

	// ========================= CONSTRUCTORS =============================

	public MyVersion(int value)
	{
		super();
		this.value = value;
	}

	// ========================= IMPLEMENTATION: Comparable ===============

	/**
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(MyVersion o)
	{
		return new Integer(value).compareTo(o.value);
	}

	// ========================= GETTERS & SETTERS ========================

	/**
	 * @return the value
	 */
	public int getValue()
	{
		return value;
	}

	// ========================= IMPLEMENTATION: PersistentEntity<Long> =====

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

}
