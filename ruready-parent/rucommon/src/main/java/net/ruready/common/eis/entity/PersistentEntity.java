/*****************************************************************************************
 * Source File: ValueObject.java
 ****************************************************************************************/
package net.ruready.common.eis.entity;

import java.io.Serializable;

import net.ruready.common.pointer.ValueObject;

/**
 * An EIS persistent entity (can be saved and loaded from a database).
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
public interface PersistentEntity<ID extends Serializable> extends ValueObject
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Returns the identifier of this entity. In most cases, it is {@link Long}.
	 * 
	 * @return the identifier of this entity. Must be {@link Serializable}
	 */
	ID getId();

	/**
	 * Create and return a manager of the associations of this entity.
	 * 
	 * @return a manager of the associations of this entity.
	 */
	// AssociationManager<PersistentEntity<ID>, ID> createAssociationManager();
}
