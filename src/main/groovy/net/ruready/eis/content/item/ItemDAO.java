/*****************************************************************************************
 * Source File: ItemDAO.java
 ****************************************************************************************/
package net.ruready.eis.content.item;

import java.util.List;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.common.audit.Versioned;
import net.ruready.common.eis.dao.DAO;

/**
 * A fully-functional custom Data Access Object (DAO) for a CMS item. To use custom
 * functions of the same node as a {@link Node} that do not appear in this DAO, use a
 * TreeNodeDAO instead.
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
 * @see net.ruready.item.entity.DemoCatalogCreator
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 9, 2007
 */
public interface ItemDAO<T extends Item> extends DAO<T, Long>, ItemReadDAO<T>
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Create or update an existing transient or persistent entity from the database.
	 * Transient fields will not be persisted. This method will not fail even if the
	 * object has never been saved. This method must be used for all item types to get the
	 * proper version control behavior.
	 * 
	 * @param entity
	 *            entity to be refreshed
	 * @param userId
	 *            identifier of the user requesting this operation
	 * 
	 */
	void update(final Item entity, final Long userId);

	/**
	 * Copy the state of the given object onto the persistent object with the same
	 * identifier. If there is no persistent instance currently associated with the
	 * session, it will be loaded. Return the persistent instance. If the given instance
	 * is unsaved, save a copy of and return it as a newly persistent instance. The given
	 * instance does not become associated with the session. This operation cascades to
	 * associated instances if the association is mapped with <code>cascade="merge"</code>.
	 * 
	 * @param object
	 *            a detached instance with state to be copied
	 * @param userId
	 *            identifier of the user requesting this operation
	 * 
	 *             when a HibernateException is thrown, an in particular when a version
	 *             conflict is detected, for entities that implement {@link Versioned}
	 */
	void merge(final Item object, final Long userId);

	/**
	 * Create or update an existing transient or persistent entity from the database as
	 * well as its children. Transient fields will not be persisted. This method will not
	 * fail even if the object has never been saved.
	 * 
	 * @param entity
	 *            root of item tree to be updated
	 * @param user
	 *            identifier of the user requesting this operation
	 * 
	 */
	void updateWithChildren(final Item entity, final Long userId,
			final boolean generateAuditMessage);

	/**
	 * Delete entity from database. This method must be used for all item types to get the
	 * proper version control behavior.
	 * 
	 * @param entity
	 *            entity to be deleted
	 * @param user
	 *            identifier of the user requesting this operation
	 * 
	 */
	void delete(T entity, final Long userId);

	/**
	 * Find an item by property.
	 * 
	 * @param propertyName
	 *            property name
	 * @param value
	 *            property value
	 * @return list of items that have this property value
	 * 
	 */
	List<T> findByLikeProperty(final String propertyName, final Object value);
}
