/*****************************************************************************************
 * Source File: DAO.java
 ****************************************************************************************/
package net.ruready.common.eis.dao;

import java.io.Serializable;
import java.util.List;

import net.ruready.common.audit.Versioned;
import net.ruready.common.eis.entity.EntityDependent;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.exception.RecordExistsException;
import net.ruready.common.eis.exception.RecordNotFoundException;

/**
 * An abstract generic DAO pattern. Should be extended by concrete DAOs for domain model
 * classes. Depends on both the entity type (T) and its identifier type (ID).
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
public interface DAO<T extends PersistentEntity<ID>, ID extends Serializable> extends
		EntityDependent<T, ID>
{
	// ========================= CRUD METHODS ==============================

	/**
	 * Create an existing transient to the database. Transient fields will not be
	 * persisted. This method will fail if the object already exists in the database.
	 * 
	 * @param entity
	 *            entity to be created
	 * @throws RecordExistsException
	 *             if the object already exists
	 * 
	 *             if another DAO problem occurred
	 */
	void create(T entity);

	/**
	 * Find an entity by a unique identifier.
	 * 
	 * @param id
	 *            a unique identifier to look for
	 * @return persistent entity, if found. If not found, returns <code>null</code>.
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found
	 * 
	 *             if another DAO problem occurred
	 */
	T read(ID id);

	/**
	 * Read an entity from the database. Pre-populated fields are overridden if they are
	 * persistent, or unaffected if they are transient. <code>entity</code> must of
	 * course be non-<code>null</code> because Java passes the entity reference by
	 * value, so it cannot be changed -- only the object it points to can be. Assumes that
	 * a persistent entity with this <code>id</code> exists in the database.
	 * 
	 * @param id
	 *            unique identifier to search for and load by
	 * @param entity
	 *            entity to be read
	 * @return persisted entity after saving it
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found
	 * 
	 *             if another DAO problem occurred
	 */
	void readInto(ID id, T entity);

	/**
	 * Create or update an existing transient or persistent entity from the database.
	 * Transient fields will not be persisted. This method will not fail even if the
	 * object has never been saved.
	 * 
	 * @param entity
	 *            entity to be refreshed
	 * 
	 */
	void update(T entity);

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
	 * 
	 *             when a HibernateException is thrown, an in particular when a version
	 *             conflict is detected, for entities that implement {@link Versioned}
	 */
	void merge(T object);

	/**
	 * Delete entity from database.
	 * 
	 * @param entity
	 *            entity to be deleted
	 * 
	 */
	void delete(T entity);

	// ========================= FINDER METHODS ============================

	/**
	 * Find all entity of this type.
	 * 
	 * @return a list of all entity of this type
	 * 
	 */
	List<T> findAll();

	/**
	 * Find by an example criterion. Zeroed properties are excluded. Should of the type of
	 * example object of the persistent layer object that implementations of this class
	 * work with.
	 * 
	 * @param exampleInstance
	 *            an example criterion to search for
	 * @return list of items identical to exampleInstance
	 * 
	 */
	List<T> findByExampleObject(Object exampleInstance);

	/**
	 * Find by an example entity. Zeroed properties are excluded.
	 * 
	 * @param exampleInstance
	 *            an example entity to search for
	 * @return list of items identical to exampleInstance
	 * 
	 */
	List<T> findByExample(T exampleInstance);

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
	List<T> findByProperty(String propertyName, Object value);

	/**
	 * Find an item by unique property.
	 * 
	 * @param propertyName
	 *            property name
	 * @param uniqueValue
	 *            property value; must be unique per entity
	 * @return unique items that has this property value
	 * 
	 */
	T findByUniqueProperty(String propertyName, Object uniqueValue);

	/**
	 * Find by a unique example entity. Zeroed properties are excluded.
	 * 
	 * @param exampleInstance
	 *            an example entity to search for
	 * @return unique matching item
	 * 
	 */
	T findByUniqueExample(T exampleInstance);
}
