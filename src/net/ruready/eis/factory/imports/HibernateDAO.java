/*****************************************************************************************
 * Source File: HibernateDAO.java
 ****************************************************************************************/
package net.ruready.eis.factory.imports;

import java.io.Serializable;
import java.util.List;

import net.ruready.common.audit.Versioned;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.exception.RecordNotFoundException;
import net.ruready.common.eis.exception.StaleRecordException;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

/**
 * An implementation of a generic DAO for Hibernate. Has a reference to its DAO
 * factory in case it needs to retrieve a new session during a failed
 * transaction.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 9, 2007
 */
public class HibernateDAO<T extends PersistentEntity<ID>, ID extends Serializable>
		implements DAO<T, ID>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateDAO.class);

	// ========================= FIELDS ====================================

	/**
	 * Class of the persistent entity.
	 */
	private final Class<T> persistentClass;

	/**
	 * A heavy-weight session factory that can provide new sessions after old
	 * ones has closed or have been corrupted.
	 */
	protected final AbstractHibernateSessionFactory sessionFactory;

	/**
	 * Produces entity association manager objects.
	 */
	protected final ApplicationContext context;

	/**
	 * Lock mode used in read and query operations. Distinguishes between
	 * read-write and read-only transactions.
	 */
	protected final LockMode lockMode;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a DAO from a session and class type.
	 * 
	 * @param persistentClass
	 *            entity's class type
	 * @param session
	 *            Hibernate session
	 * @param sessionFactory
	 *            a heavy-weight session factory that can provide new sessions
	 *            after old ones has closed or have been corrupted.
	 * @param context
	 *            application context
	 */
	public HibernateDAO(final Class<T> persistentClass,
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		this.persistentClass = persistentClass;
		this.sessionFactory = sessionFactory;
		this.context = context;
		this.lockMode = context.isEisLock() ? LockMode.READ : LockMode.NONE;
	}

	// ========================= IMPLEMENTATION: DAO ================

	/**
	 * @see net.ruready.common.eis.dao.DAO#create(java.lang.Object)
	 */
	public void create(T entity)
	{
		Session session = getSession();
		session.save(entity);
	}

	/**
	 * @see net.ruready.common.eis.dao.DAO#update(java.lang.Object)
	 */
	public void update(final T entity)
	{
		checkVersion(entity);
		Session session = getSession();
		session.saveOrUpdate(entity);
	}

	/**
	 * @see net.ruready.common.eis.dao.DAO#update(java.lang.Object)
	 */
	public void merge(final T object)
	{
		Session session = getSession();
		session.merge(object);
		session.flush();
	}

	/**
	 * Read an entity from the database. Pre-populated fields are overridden if
	 * they are persistent, or unaffected if they are transient.
	 * <code>entity</code> must of course be non-<code>null</code> because
	 * Java passes the entity reference by value, so it cannot be changed --
	 * only the object it points to can be. Assumes that a persistent entity
	 * with this <code>id</code> exists in the database.
	 * 
	 * @param id
	 *            unique identifier to search for and load by
	 * @param entity
	 *            entity to be read
	 * @return persisted entity after saving it
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found
	 * 
	 * if another DAO problem occurred
	 * @see net.ruready.common.eis.dao.DAO#readInto(java.io.Serializable,
	 *      net.ruready.common.eis.entity.PersistentEntity)
	 */
	public void readInto(ID id, T entity)
	{
		// Assumes that entity with this id exists in the database
		Session session = getSession();
		session.load(entity, id);
	}

	/**
	 * @see net.ruready.common.eis.dao.DAO#delete(java.lang.Object)
	 */
	public void delete(T entity)
	{
		Session session = getSession();
		session.delete(entity);
	}

	/**
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found
	 * 
	 * if another DAO problem occurred
	 * @see net.ruready.common.eis.dao.DAO#read(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T read(ID id)
	{
		Session session = getSession();
		session.flush();
		T entity = (T) session.get(getPersistentClass(), id, lockMode);
		if (entity == null)
		{
			throw new RecordNotFoundException(id, "Record not found");
		}
		return entity;
	}

	/**
	 * @see net.ruready.common.eis.dao.DAO#findAll()
	 */
	public List<T> findAll()
	{
		return findByCriteria();
	}

	/**
	 * @see net.ruready.common.eis.dao.DAO#findByExampleObject(java.lang.Object)
	 */
	public List<T> findByExampleObject(Object exampleCriterion)
	{
		return findByCriteria((Example) exampleCriterion);
	}

	/**
	 * @see net.ruready.common.eis.dao.DAO#findByExample(java.lang.Object)
	 */
	public List<T> findByExample(T exampleInstance)
	{
		// exclude zero-valued properties
		return findByCriteria(Example.create(exampleInstance).excludeZeroes());
	}

	/**
	 * @see net.ruready.common.eis.dao.DAO#findByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public List<T> findByProperty(String propertyName, Object value)
	{
		Criterion c = Restrictions.eq(propertyName, value);
		return findByCriteria(c);
	}

	/**
	 * @see net.ruready.common.eis.dao.DAO#findByUniqueExample(java.lang.String,
	 *      java.lang.Object)
	 */
	public T findByUniqueExample(T exampleInstance)
	{
		final Criteria crit = createCriteria();
		crit.add(Example.create(exampleInstance));
		return getUniqueResult(crit);
	}

	/**
	 * @see net.ruready.common.eis.dao.DAO#findByUniqueProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public T findByUniqueProperty(String propertyName, Object uniqueValue)
	{
		final Criteria crit = createCriteria();
		crit.add(Restrictions.eq(propertyName, uniqueValue));
		// We know that if we use this method, the results should contain
		// zero or one records, but set the max # of records to 1 just in case.
		// Then uniqueResult() will not throw a NonUniqueResultException.
		return getUniqueResult(crit);
	}

	/**
	 * Plugin point to allow DAOs the ability to manage how unique results are
	 * handled
	 * 
	 * @param crit
	 *            the search criteria to perform against the persistent class
	 * @return unique item matching the specified criteria
	 */
	@SuppressWarnings("unchecked")
	protected T getUniqueResult(final Criteria crit)
	{
		crit.setMaxResults(1);
		return (T) crit.uniqueResult();
	}

	// ========================= METHODS ===================================

	protected final Criteria createCriteria()
	{
		return getSession().createCriteria(getPersistentClass());
	}

	/**
	 * Use this inside subclasses as a convenience method to search for items by
	 * criteria.
	 * 
	 * @param criterion
	 *            a variable list of criteria
	 * @return list of items that match the criteria
	 * 
	 */
	protected List<T> findByCriteria(Criterion... criterion)
	{
		Criteria crit = createCriteria();
		for (Criterion c : criterion)
		{
			crit.add(c);
		}
		return findByCriteria(crit);
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(final Criteria criteria)
	{
		return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	/**
	 * Enforce version control on an entity, if it implements {@link Versioned}.
	 * We assume the version number is of type {@link Integer}.
	 * 
	 * @param rawEntity
	 *            entity
	 * @return list of items that match the criteria
	 * @throws StaleRecordException
	 *             if version conflict is detected
	 */
	@SuppressWarnings("unchecked")
	protected <S extends PersistentEntity<ID>> void checkVersion(final S rawEntity)

	{
		// Check if entity implements Versioned
		if (!Versioned.class.isAssignableFrom(rawEntity.getClass()))
		{
			return;
		}

		// =========================
		// Get the local version
		// =========================
		Versioned<Integer> localEntity = (Versioned<Integer>) rawEntity;
		ID id = rawEntity.getId();
		Integer localVersion = localEntity.getLocalVersion();
		if (id == null)
		{
			// No ID ==> no conflict
			return;
		}
		else if (localVersion == null)
		{
			// No local version ==> no conflict. Used to throw an exception
			// here,
			// but chances are small that users will tamper with the UI's local
			// version hidden field.
			return;
			// throw new EISException("checkVersion(): No local version found
			// for entity "
			// + rawEntity);
		}

		// =========================
		// Get the stored version
		// =========================
		Session session = getSession();
		T storedEntity = (T) session.get(getPersistentClass(), id, lockMode);
		if (storedEntity == null)
		{
			// Entity not found in database ==> no conflict
			return;
		}

		Integer storedVersion = ((Versioned<Integer>) storedEntity).getVersion();

		// ===========================================================================
		// Check if the database version is different than the local version
		// ===========================================================================
		if (!storedVersion.equals(localVersion))
		{
			// ######################
			// Conflict detected
			// ######################
			// Remove local copy from session so that the stored entity can be
			// re-loaded
			// and compared with it in an exception handler.
			// if (session.contains(localEntity))
			// {
			// session.evict(localEntity);
			// }
			throw new StaleRecordException(rawEntity, localVersion, storedVersion,
					"checkVersion(): conflict prevents changing entity " + rawEntity
							+ " ID " + rawEntity.getId() + " local V" + localVersion
							+ " stored V" + storedVersion);
		}
		else
		{
			// ######################
			// No conflict
			// ######################
			// If the local copy is not attached to the session, remove the
			// version that we just loaded, so that we don't get a
			// non-unique-object-exception trying to save the local copy later.
			if (!session.contains(localEntity))
			{
				session.evict(storedEntity);
			}
		}

	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the Hibernate session.
	 * 
	 * @return the session
	 */
	protected final Session getSession()
	{
		return sessionFactory.getSession();
	}

	/**
	 * Return the class type for this DAO.
	 * 
	 * @return the class type for this DAO
	 */
	public final Class<T> getPersistentClass()
	{
		return persistentClass;
	}
}
