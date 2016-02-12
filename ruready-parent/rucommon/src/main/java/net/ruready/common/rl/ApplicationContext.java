/*****************************************************************************************
 * Source File: ResourceLocator.java
 ****************************************************************************************/
package net.ruready.common.rl;

import java.io.Serializable;

import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.exports.AbstractAssociationManagerFactory;
import net.ruready.common.eis.exports.AbstractDAOFactory;
import net.ruready.common.eis.manager.AssociationManager;

import org.apache.commons.chain.Context;

/**
 * A context that can be used to get and set methods for resources obtained through the
 * {@link ResourceLocator}. This object should be synchronized across multiple threads.
 * For instance, in a web environment it is typically placed in a request object to avoid
 * thread-safety issues. {@link ApplicationContext} builds on the commons chain
 * {@link Context} interface.
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
 * @version Jul 21, 2007
 */
public interface ApplicationContext extends Context, AbstractDAOFactory,
		AbstractAssociationManagerFactory
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Get a DAO class from the cache. If a custom DAO class is not found (say) a
	 * {@link #entityMangers} map property of the implementing class, it is deferred to a
	 * default instantiation of a {@link DAO} implementation for the particular class and
	 * identifier types. -- Obsolete, now using the entity to instantiate the correct type
	 * of association manager. <i>Only EIS managers should call this method.</i>
	 * 
	 * @param <T>
	 *            class type, e.g. <code>Item</code>
	 * @param <ID>
	 *            identifier type, e.g. Long
	 * @param entityClass
	 *            persistent entity type, e.g. <code>Item.class</code>. Must match
	 *            <code><T></code>
	 * @return requested DAO
	 */
	<T extends PersistentEntity<ID>, ID extends Serializable> DAO<T, ID> readDAOFromCache(
			Class<T> entityClass);

	/**
	 * Cache a DAO. <i>Only EIS managers should call this method.</i>
	 * 
	 * @param <T>
	 * @param <ID>
	 * @param entityClass
	 * @param customObject
	 * @see net.ruready.common.eis.manager.GenericEntityDependentMap#put(java.lang.Class,
	 *      net.ruready.common.eis.EntityDependent)
	 */
	<T extends PersistentEntity<ID>, ID extends Serializable> void putDAOInCache(
			Class<T> entityClass, DAO<?, ?> customObject);

	/**
	 * Produces an entity association manager class. If a custom entity manager class is
	 * not found (say) a {@link #entityMangers} map property of the implementing class, it
	 * is deferred to a default instantiation of a {@link AssociationManager}
	 * implementation for the particular class and identifier types. -- Obsolete, now
	 * using the entity to instantiate the correct type of association manager.
	 * 
	 * @param <T>
	 *            class type, e.g. <code>Item</code>
	 * @param <ID>
	 *            identifier type, e.g. Long
	 * @param entityClass
	 *            persistent entity type, e.g. <code>Item.class</code>. Must match
	 *            <code><T></code>
	 * @return requested entity association manager
	 */
	<T extends PersistentEntity<ID>, ID extends Serializable> AssociationManager<T, ID> readAssociationManagerFromCache(
			Class<T> entityClass);

	/**
	 * Cache an association manager. <i>Only EIS managers should call this method.</i>
	 * 
	 * @param <T>
	 * @param <ID>
	 * @param entityClass
	 * @param customObject
	 * @see net.ruready.common.eis.manager.GenericEntityDependentMap#put(java.lang.Class,
	 *      net.ruready.common.eis.EntityDependent)
	 */
	<T extends PersistentEntity<ID>, ID extends Serializable> void putAssociationManagerInCache(
			Class<T> entityClass, AssociationManager<?, ?> customObject);

	/**
	 * @return the eisLock
	 */
	boolean isEisLock();

	/**
	 * @param eisLock
	 *            the eisLock to set
	 */
	void setEisLock(boolean eisLock);
}
