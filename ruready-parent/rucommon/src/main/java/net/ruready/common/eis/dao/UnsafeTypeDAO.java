/*****************************************************************************************
 * Source File: DAO.java
 ****************************************************************************************/
package net.ruready.common.eis.dao;

import java.io.Serializable;
import java.util.List;

import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.exception.RecordExistsException;

/**
 * A partially type-unsafe abstract generic DAO pattern. Should be extended by concrete
 * DAOs for domain model classes. Depeds on the entity type (T) and assumes that calls
 * that involve the identifier type (ID) cast correctly to and from {@link Serializable}
 * so that the ID type matches the result of {@link PersistentEntity#getId()} for T.
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
@Deprecated
public interface UnsafeTypeDAO<T extends PersistentEntity<?>>
{
	// ========================= CRUD METHODS ==============================

	/**
	 * Create an existing transient to the database. Transient fields will not be
	 * persisted. This method will fail even if the object already exists in the database.
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
	 * Read an entity from the database. Pre-populated fields are overridden if they are
	 * persistent, or unaffected if they are transient.
	 * 
	 * @param id
	 *            unique identifier to search for and load by. Its type must match the
	 *            result of {@link PersistentEntity#getId()} for T
	 * @param entity
	 *            entity to be read
	 * @return persisted entity after saving it
	 * 
	 */
	void read(Serializable id, T entity);

	/**
	 * Delete entity from database.
	 * 
	 * @param entity
	 *            entity to be deleted
	 * 
	 */
	void delete(T entity);

	/**
	 * Close the database connection/session.
	 * 
	 * 
	 */
	void close();

	/**
	 * Is database connection/session closed or not.
	 * 
	 * @return is database connection/session closed or not
	 */
	boolean isClosed();

	/**
	 * Set the working session.
	 * 
	 * @param s
	 *            session to set. Should of the type of persistent layer object that
	 *            implementations of this class work with
	 */
	public void setSession(Object s);

	// ========================= FINDER METHODS ============================

	/**
	 * Find an item by a unique identifier.
	 * 
	 * @param id
	 *            a unique identifier to look for
	 * @param lock
	 *            lock mode
	 * @return item, if found. If not found, attempting to access it will throw an
	 *         <code>org.hibernate.ObjectNotFoundException</code>.
	 * 
	 */
	T findById(Serializable id, final boolean lock);

	/**
	 * Find all items.
	 * 
	 * @return a list of all items
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
	public T findByUniqueExample(T exampleInstance);
}
