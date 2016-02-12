/*****************************************************************************************
 * Source File: SimpleUser.java
 ****************************************************************************************/
package test.ruready.eis;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * A concrete implementation of a composite type (user) with an abstract component (phone) --
 * the strategy pattern. Because we would like to map both <code>User</code> and
 * <code>Phone</code> to a database, we must make the phone abstraction an <i>abstract
 * class</i>, not an interface.
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
public class SimpleUser implements AbstractUser
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
	 * The user's name. Had originally a <code>at-Column(nullable = false)</code>
	 * annotation. However, in general, try to refrain from using nullable = false, see
	 * notes in class PersistenTreeNode.
	 */
	private String name = null;

	/**
	 * Phone number of this user.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private AbstractPhone phone;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Required for this to be a persistent entity.
	 */
	protected SimpleUser()
	{

	}

	/**
	 * Construct phone from fields.
	 * 
	 * @param name
	 *            The user's name
	 */
	public SimpleUser(String name)
	{
		super();
		this.name = name;
	}

	// ========================= IMPLEMENTATION: AbstractUser ==============

	/**
	 * @return the phone
	 */
	public AbstractPhone getPhone()
	{
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(AbstractPhone phone)
	{
		this.phone = phone;
	}

	// ========================= METHODS ===================================

	/**
	 * Print a phone number.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[User]" + " id " + id + " name " + name + " phone " + getPhone();
	}

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
	//	}
}
