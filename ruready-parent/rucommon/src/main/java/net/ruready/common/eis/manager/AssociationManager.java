/*****************************************************************************************
 * Source File: DAO.java
 ****************************************************************************************/
package net.ruready.common.eis.manager;

import java.io.Serializable;

import net.ruready.common.eis.entity.EntityDependent;
import net.ruready.common.eis.entity.PersistentEntity;

/**
 * An abstract entity association manager. This is part of the DAO pattern. classes.
 * Depends on both the entity type (T) and its identifier type (ID). Is responsible for
 * performing auxiliary operations in DAOs, e.g., removing an entity from all known
 * associations with other entities so that it can safely be removed from the database by
 * the DAO.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 17, 2007
 */
public interface AssociationManager<T extends PersistentEntity<ID>, ID extends Serializable>
		extends EntityDependent<T, ID>
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Remove an entity from all known associations with other entities so that it can
	 * safely be removed from the database by the DAO. This method is meant to be called
	 * during an open session.
	 * 
	 * @param entity
	 *            entity to be created
	 */
	void removeFromAssociations(T entity);
}
