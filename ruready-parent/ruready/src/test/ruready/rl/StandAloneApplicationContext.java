/*****************************************************************************************
 * Source File: StandAloneApplicationContext.java
 ****************************************************************************************/

package test.ruready.rl;

import java.io.Serializable;
import java.util.Properties;

import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.eis.manager.AssociationManager;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.manager.GenericEntityDependentMap;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.rl.ApplicationContext;

import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Stand-alone application context that is bound to the request. A custom pre-processing
 * command places it in the request scope the beginning of the web layer processing chain.
 * It can be used to set database locking flags, etc. This object assumes we are within a
 * single-thread context. It should be passed as a final instance field to the
 * constructors of all applicable business delegates, managers and DAOs.
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
 * @version Oct 3, 2007
 */

public class StandAloneApplicationContext extends ContextBase implements
		ApplicationContext
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
	private static final Log logger = LogFactory
			.getLog(StandAloneApplicationContext.class);

	// ========================= FIELDS ====================================

	/**
	 * Used to cache DAO instances and get rid of some weird compilation errors on Linux.
	 */
	private final GenericEntityDependentMap<DAO<?, ?>> daoMap = new GenericEntityDependentMap<DAO<?, ?>>();

	/**
	 * Holds references to custom entity managers.
	 */
	private final GenericEntityDependentMap<AssociationManager<?, ?>> associationManagers = new GenericEntityDependentMap<AssociationManager<?, ?>>();

	/**
	 * EIS manager that has access to a session factory required to instantiate DAOs and
	 * association managers.
	 */
	protected final AbstractEISManager eisFactory;

	/**
	 * A flag that signals to the database layer that the current operations (the or
	 * transaction they are in) are read-only.
	 */
	private boolean eisLock = false;

	// /**
	// * A flag that signals that the {@link DAO} map is initialized. Once it is set to
	// * <code>true</code>, it is sticky.
	// */
	// private boolean initializedDAO = false;
	//
	// /**
	// * A flag that signals that the {@link AssociationManager} map is initialized. Once
	// it
	// * is set to <code>true</code>, it is sticky.
	// */
	// private boolean initializedAssociationManager = false;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Set up a context. Initialize DAO and association manager caches.
	 * 
	 * @param eisFactory
	 *            EIS factory (manager) that helps initialize this context's cache maps
	 */
	public StandAloneApplicationContext(final AbstractEISManager eisFactory)
	{
		// logger.info("Initializing new context");
		this.eisFactory = eisFactory;
		// Populate caches with custom objects
		eisFactory.setUpContextDAO(this);
		eisFactory.setUpContextAssociationManager(this);
	}

	// ========================= IMPLEMENTATION: Resource ==================

	/**
	 * @param config
	 * @see net.ruready.common.rl.Resource#setUp(java.lang.Object)
	 */
	public void setUp(Properties config)
	{

	}

	/**
	 * @see net.ruready.common.rl.Resource#tearDown()
	 */
	public void tearDown()
	{

	}

	// ========================= IMPLEMENTATION: AbstractDAOFactory ========

	/**
	 * @param <T>
	 * @param <ID>
	 * @param <D>
	 * @param entityClass
	 * @return
	 * @see net.ruready.common.eis.manager.GenericEntityDependentMap#get(java.lang.Class)
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable> DAO<T, ID> getDAO(
			Class<T> entityClass)
	{
		return eisFactory.getDAO(entityClass, this);
	}

	// ========================= IMPLEMENTATION: AbstractAssociationManagerFactory

	/**
	 * @param <T>
	 * @param <ID>
	 * @param entityClass
	 * @return
	 * @see net.ruready.common.eis.AbstractAssociationManagerFactory#getAssociationManager(java.lang.Class)
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable> AssociationManager<T, ID> getAssociationManager(
			Class<T> entityClass)
	{
		return eisFactory.getAssociationManager(entityClass, this);
	}

	// ========================= IMPLEMENTATION: ApplicationContext ========

	/**
	 * @param <T>
	 * @param <ID>
	 * @param entityClass
	 * @return
	 * @see net.ruready.common.rl.ApplicationContext#readDAOFromCache(java.lang.Class)
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable> DAO<T, ID> readDAOFromCache(
			Class<T> entityClass)
	{
		return (DAO<T, ID>) daoMap.get(entityClass);
	}

	/**
	 * @param <T>
	 * @param <ID>
	 * @param entityClass
	 * @param customObject
	 * @see net.ruready.common.rl.ApplicationContext#putDAOInCache(java.lang.Class,
	 *      net.ruready.common.eis.dao.DAO)
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable> void putDAOInCache(
			Class<T> entityClass, DAO<?, ?> customObject)
	{
		daoMap.put(entityClass, customObject);
	}

	/**
	 * @param <T>
	 * @param <ID>
	 * @param entityClass
	 * @return
	 * @see net.ruready.common.rl.ApplicationContext#readAssociationManagerFromCache(java.lang.Class)
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable> AssociationManager<T, ID> readAssociationManagerFromCache(
			Class<T> entityClass)
	{
		return (AssociationManager<T, ID>) associationManagers.get(entityClass);
	}

	/**
	 * @param <T>
	 * @param <ID>
	 * @param entityClass
	 * @param customObject
	 * @see net.ruready.common.rl.ApplicationContext#putAssociationManagerInCache(java.lang.Class,
	 *      net.ruready.common.eis.manager.AssociationManager)
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable> void putAssociationManagerInCache(
			Class<T> entityClass, AssociationManager<?, ?> customObject)
	{
		associationManagers.put(entityClass, customObject);
	}

	/**
	 * @return the eisLock
	 */
	public boolean isEisLock()
	{
		return eisLock;
	}

	/**
	 * @param eisLock
	 *            the eisLock to set
	 */
	public void setEisLock(boolean eisLock)
	{
		this.eisLock = eisLock;
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================
}
