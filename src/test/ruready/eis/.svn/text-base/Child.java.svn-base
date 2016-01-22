/*****************************************************************************************
 * Source File: Child.java
 ****************************************************************************************/
package test.ruready.eis;

import javax.persistence.*;

import net.ruready.common.eis.entity.PersistentEntity;

/**
 * A child sub-class of <code>Base</code> for Hibernate inheritance tests.
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
public class Child extends Base implements PersistentEntity<Long>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	@Column(name = "child_name")
	private String childName;

	// @Column(name = "base_name2")
	// private String baseName;

	/**
	 * @return the baseName
	 */
	@Override
	public String getBaseName()
	{
		return super.getBaseName();
	}

	/**
	 * @return the childName
	 */
	public String getChildName()
	{
		return childName;
	}

	/**
	 * @param childName
	 *            the childName to set
	 */
	public void setChildName(String childName)
	{
		this.childName = childName;
	}

}
