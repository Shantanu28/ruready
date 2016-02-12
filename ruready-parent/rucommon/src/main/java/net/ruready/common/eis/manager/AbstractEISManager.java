/*****************************************************************************************
 * Source File: AbstractEISManager.java
 ****************************************************************************************/
package net.ruready.common.eis.manager;

import java.io.Serializable;
import java.util.Properties;

import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.exports.AbstractEISBounder;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.Resource;

/**
 * An abstract EIS manager. It is a DAO factory that different persistent layers should
 * implement. Also contains utilities provided by the persistent layer and helps set up an
 * application context.
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
 * @version Jul 31, 2007
 */
public interface AbstractEISManager extends Resource<Properties>, AbstractEISBounder
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Initialize the attached context with custom DAOs. Called the first time a
	 * {@link DAO} instance is requested using the {@link #getDAO(Class)} method.
	 * 
	 * @param context
	 *            application context to set up
	 */
	void setUpContextDAO(final ApplicationContext context);

	/**
	 * Initialize the attached context with custom {@link AssociationManager}s. Called
	 * the first time a {@link AssociationManager} instance is requested using the
	 * {@link #getDAO(Class)} method.
	 * 
	 * @param context
	 *            application context to set up
	 */
	void setUpContextAssociationManager(final ApplicationContext context);

	/**
	 * Produces a DAO class. If a custom DAO class is not found (say) a {@link #daos} map
	 * property of the implementing class, it is deferred to a default instantiation of a
	 * {@link DAO} implementation for the particular class and identifier types.
	 * 
	 * @param <T>
	 *            class type, e.g. <code>Item</code>
	 * @param <ID>
	 *            identifier type, e.g. Long
	 * @param entityClass
	 *            persistent entity type, e.g. <code>Item.class</code>. Must match
	 *            <code><T></code>
	 * @param context
	 *            application context that caches DAO objects
	 * @return requested DAO
	 */
	<T extends PersistentEntity<ID>, ID extends Serializable> DAO<T, ID> getDAO(
			final Class<T> entityClass, final ApplicationContext context);

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
	 * @param context
	 *            application context that caches DAO objects
	 * @return requested entity association manager
	 */
	<T extends PersistentEntity<ID>, ID extends Serializable> AssociationManager<T, ID> getAssociationManager(
			final Class<T> entityClass, final ApplicationContext context);

	/**
	 * Check if the proxy or persistent collection is initialized.
	 * 
	 * @param proxy
	 *            a persistable object, proxy, persistent collection or <code>null</code>
	 * @return <code>true</code> if the argument is already initialized, or is not a
	 *         proxy or collection
	 */
	boolean isInitialized(Object proxy);
}
