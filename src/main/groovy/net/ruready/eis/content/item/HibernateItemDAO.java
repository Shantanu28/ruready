/*****************************************************************************************
 * Source File: HibernateItemDAO.java
 ****************************************************************************************/
package net.ruready.eis.content.item;

import java.lang.reflect.Constructor;
import java.util.List;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.entity.audit.AuditAction;
import net.ruready.business.content.item.entity.audit.AuditMessage;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.content.util.util.ItemTypeUtil;
import net.ruready.business.user.entity.User;
import net.ruready.common.audit.Versioned;
import net.ruready.common.eis.exception.EISException;
import net.ruready.common.eis.exception.RecordExistsException;
import net.ruready.common.eis.exception.RecordNotFoundException;
import net.ruready.common.eis.exception.StaleRecordException;
import net.ruready.common.eis.manager.AssociationManager;
import net.ruready.common.exception.SystemException;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.text.TextUtil;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.imports.HibernateDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Data access object (DAO) Hibernate implementation for domain model class
 * Item.
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
 * @see net.ruready.item.entity.DemoCatalogCreator
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 1, 2007
 */
public class HibernateItemDAO<T extends Item> extends HibernateDAO<T, Long> implements
		ItemDAO<T>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateItemDAO.class);

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
	public HibernateItemDAO(final Class<T> persistentClass,
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(persistentClass, sessionFactory, context);
	}

	// ========================= IMPLEMENTATION: HibernateDAO =======

	/**
	 * Update an item in the database. Assumes the item already exists in the
	 * database. If it does not, use {@link #create(Item)} instead.
	 * 
	 * @param entity
	 *            item to update
	 * @param userId
	 *            must be non-<code>null</code>
	 * @throws StaleRecordException
	 *             upon version conflict trying to persist entity changes to the
	 *             database
	 * @throws RecordExistsException
	 *             upon constraint violation (e.g. unique record name) trying to
	 *             persist entity changes to the database
	 * @see net.ruready.eis.content.item.ItemDAO#update(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Long)
	 */
	public void update(final Item entity, final Long userId)
	{
		this.update(entity, userId, true);
	}

	/**
	 * Copy the state of the given object onto the persistent object with the
	 * same identifier. If there is no persistent instance currently associated
	 * with the session, it will be loaded. Return the persistent instance. If
	 * the given instance is unsaved, save a copy of and return it as a newly
	 * persistent instance. The given instance does not become associated with
	 * the session. This operation cascades to associated instances if the
	 * association is mapped with <code>cascade="merge"</code>.
	 * 
	 * @param object
	 *            a detached instance with state to be copied
	 * @param userId
	 *            identifier of the user requesting this operation
	 * 
	 * when a HibernateException is thrown, an in particular when a version
	 * conflict is detected, for entities that implement {@link Versioned}
	 */
	public void merge(final Item object, final Long userId)
	{
		Session session = getSession();

		AuditMessage auditMessage = null;
		// ===============================
		// Add an audit message
		// ===============================
		// If we are in a user-aware context:
		// add a message about the creation or updating of this entity
		// Check if there is custom message, if yes use it, otherwise use a
		// default action
		User user = (User) session.get(User.class, userId, lockMode);
		// The audit message refers to the version of the updated
		// entity, hence increment the version #
		AuditAction action = (object.getVersion() == Node.VERSION_SEED) ? AuditAction.CREATED
				: AuditAction.UPDATED;
		auditMessage = new AuditMessage(object, user, action, null,
				object.getVersion() + 1);
		object.addMessage(auditMessage);

		// ===============================
		// Persist changes to the database
		// ===============================
		// Save the entity; cascades to its audit messages as well
		session.merge(object);
		if (logger.isDebugEnabled())
		{
			logger.debug("Successfully merged entity '" + object.getName() + "'");
		}

	}

	/**
	 * @param entity
	 *            root of item tree to be updated
	 * @param userId
	 *            must be non-<code>null</code>
	 * @param generateAuditMessage
	 *            we'll generate an audit message regarding the item update only
	 *            if this flag is <code>true</code>
	 * @throws StaleRecordException
	 *             upon version conflict trying to persist entity changes to the
	 *             database
	 * @throws RecordExistsException
	 *             upon constraint violation (e.g. unique record name) trying to
	 *             persist entity changes to the database
	 * @see net.ruready.eis.content.item.ItemDAO#updateWithChildren(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Long, boolean)
	 */
	public void updateWithChildren(final Item entity, final Long userId,
			final boolean generateAuditMessage)
	{
		update(entity, userId, generateAuditMessage);
		for (Node child : entity.getChildren())
		{
			update((Item) child, userId, generateAuditMessage);
		}
	}

	/**
	 * @param entity
	 * @param userId
	 *            must be non-<code>null</code>
	 * @throws StaleRecordException
	 *             upon version conflict trying to persist entity changes to the
	 *             database
	 * @throws RecordExistsException
	 *             upon constraint violation (e.g. unique record name) trying to
	 *             persist entity changes to the database
	 * @see net.ruready.eis.content.item.ItemDAO#delete(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Long)
	 */
	public void delete(final Item entity, final Long userId)
	{
		Session session = getSession();

		// Version control
		checkVersion(entity);

		// No need to add an audit message because we'll delete all messages
		// with the
		// item right after that

		// ===============================
		// Persist changes to the database
		// ===============================
		// Now delete the entity. Associated messages ARE DELETED TOO.
		// Remove item from any associations before attempting to delete it
		// from the database, because the deleted object would be re-saved by
		// cascade and throw an org.hibernate.ObjectDeletedException.
		entity.removeFromParent();
		session.delete(entity);
	}

	/**
	 * Read an entity from the database. Pre-populated fields are overridden if
	 * they are persistent, or unaffected if they are transient. The number of
	 * children of the item is also loaded. If children loading is specified (<code>cascadeType = CHILDREN</code>),
	 * we load the number of children of each item's child as well. Similarly,
	 * siblings are loaded if specified by the cascadeType parameter (<code>cascadeType = SIBLINGS</code>).
	 * If <code>cascadeType = ALL</code>, the entire item sub-tree hierarchy
	 * is loaded.
	 * 
	 * @param id
	 *            unique identifier to search for and load by
	 * @param entity
	 *            entity to be read
	 * @param cascadeType
	 *            specifies optional cascading (loading children/siblings/entire
	 *            sub-tree as well).
	 * @param removeFromAssociations
	 *            if <code>true</code>, the item tree's associations with
	 *            other objects will be removed (e.g. in preparation for
	 *            deleting the item tree)
	 * @return persisted entity after saving it
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found and
	 *             <code>removeFromAssociations = false</code>
	 * 
	 * if another DAO problem occurred
	 * @see net.ruready.eis.content.item.ItemReadDAO#read(long,
	 *      net.ruready.business.content.item.entity.Item )
	 */
	public T read(final long id, final boolean removeFromAssociations)
	{
		return removeFromAssociations ? this.readAll(id, removeFromAssociations) : this
				.read(id);
	} // read()

	/**
	 * @param entity
	 * 
	 * @see net.ruready.eis.factory.imports.HibernateDAO#update(net.ruready.common.eis.entity.PersistentEntity)
	 */
	@Override
	public void update(final T entity)
	{
		throw new SystemException("Must supply a user");
	}

	/**
	 * @param entity
	 * 
	 * @see net.ruready.eis.factory.imports.HibernateDAO#delete(net.ruready.common.eis.entity.PersistentEntity)
	 */
	@Override
	public void delete(final T entity)
	{
		throw new SystemException("Must supply a user");
	}

	// ========================= IMPLEMENTATION: ItemDAO ===================

	/**
	 * Find the list of children of a certain item type under a parent node.
	 * 
	 * @param <C>
	 *            type of child
	 * @return parent parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @param constraintClause
	 *            if non-null, is appended to the query string
	 * @param constraintParams
	 *            a list of parameter values appearing in the constraint clause
	 * @return list of children under the parent; need not be its direct
	 *         children
	 */
	@SuppressWarnings("unchecked")
	public <C extends T> List<C> findChildren(final Item parent,
			final Class<C> persistentClass, final ItemType childItemType,
			final String constraintClause, final List<?> constraintParams)

	{
		Query query = this.generateFindChildrenQuery(parent, persistentClass,
				childItemType, null, constraintClause, constraintParams);

		// The cast of the result set type is justified because childClassName
		// matches C.
		List<C> list = query.list();
		if (logger.isDebugEnabled())
		{
			logger.debug("Children list = " + list);
		}
		return list;
	}

	/**
	 * Find the list of children identifiers of a certain item type under a
	 * parent node.
	 * 
	 * @param <C>
	 *            type of child
	 * @return parent parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @param constraintClause
	 *            if non-null, is appended to the query string
	 * @param constraintParams
	 *            a list of parameter values appearing in the constraint clause
	 * @return list of children under the parent; need not be its direct
	 *         children
	 */
	@SuppressWarnings("unchecked")
	public <C extends T> List<Long> findChildrenIds(final Item parent,
			final Class<C> persistentClass, final ItemType childItemType,
			final String constraintClause, final List<?> constraintParams)

	{
		Query query = this.generateFindChildrenQuery(parent, persistentClass,
				childItemType, "select id", constraintClause, constraintParams);

		// The cast of the result set type is justified because of the specific
		// select clause.
		List<Long> list = query.list();
		if (logger.isDebugEnabled())
		{
			logger.debug("Children IDs = " + list);
		}
		return list;
	}

	/**
	 * Find the number of children of a certain item type under a parent node
	 * and satisfy some constraints. We assume the constraint results in a
	 * scalar (unique) result set to be returned from this method.
	 * 
	 * @param <C>
	 *            type of child
	 * @return parent parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @param constraintClause
	 *            if non-null, is appended to the query string
	 * @param constraintParams
	 *            a list of parameter values appearing in the constraint clause
	 * @return number of children under the parent and satisfy the constraints
	 *         specified by <code>constraintClause</code>; need not be its
	 *         direct children
	 * 
	 * @see net.ruready.eis.content.item.ItemReadDAO#findChildrenCount(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <C extends T> long findChildrenCount(final Item parent,
			final Class<C> persistentClass, final ItemType childItemType,
			final String constraintClause, final List<?> constraintParams)

	{
		Query query = this.generateFindChildrenQuery(parent, persistentClass,
				childItemType, "select count(*)", constraintClause, constraintParams);

		long count = ((Long) query.uniqueResult()).longValue();
		if (logger.isDebugEnabled())
		{
			logger.debug("count = " + count);
		}
		return count;
	}

	/**
	 * List all items in the database that are not under the trash can. Note:
	 * assumes that the items are under the a tree-structure hierarchy (every
	 * child has one parent).
	 * 
	 * @param hierarchyRootId
	 *            root of hierarchy that the item type belongs to; must be a
	 *            tree structure and must be a {@link MainItem} whose level in
	 *            the item hierarchy equals the trash can's.
	 * @return a list of all non-deleted items
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * 
	 * if there a DAO problem occurred
	 * @see net.ruready.eis.content.item.ItemReadDAO#findAllNonDeleted(net.ruready.business.content.item.entity.ItemType)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllNonDeleted(final ItemType hierarchyRootId)
	{
		// Find the unique trash can
		Session session = getSession();
		Criteria crit = session.createCriteria(DefaultTrash.class);
		crit.add(Restrictions.eq(Node.NAME, ContentNames.UNIQUE_NAME.TRASH));
		crit.setMaxResults(1);
		DefaultTrash trash = (DefaultTrash) crit.uniqueResult();

		// Find all items whose parent.id != trash.id
		// Constructor<T> c = getPersistentClass().getConstructor(String.class,
		// String.class);
		// Criteria criteriaThis = session.createCriteria(getPersistentClass());
		// criteriaThis.add(Example.create(c.newInstance(null,
		// null)).excludeZeroes());
		// criteriaThis.createCriteria("parent").add(Expression.ne("id",
		// trash.getId()));
		// // Non-performant. this is a problem if we return more than 100 rows.
		// // @todo note: we could throw an exception here, and always have ppl
		// // filter their searches to return less than 300 rows...
		// List<T> list = criteriaThis.list();

		// Construct a query and take into account all the possible paths from
		// the
		// trash to this item type
		ItemType itemId;
		try
		{
			Constructor<T> c = getPersistentClass().getConstructor(String.class,
					String.class);
			itemId = c.newInstance(null, null).getIdentifier();
		}
		catch (Exception e)
		{
			throw new SystemException("Problem instantiating item class "
					+ getPersistentClass(), e);
		}
		// Dist(TRASH,itemType) = Dist(ROOT,itemType) because Dist(ROOT,TRASH)=1
		// but
		// add one back because item was moved under the trash can, so down one
		// level
		// with respect to its original hierarchy level.
		int maxPathLength = ItemTypeUtil.getPathLength(hierarchyRootId, itemId) + 1;

		String className = getPersistentClass().getSimpleName();
		String classAlias = className.toLowerCase();
		StringBuffer constraintSnippet = new StringBuffer("parent.id != " + trash.getId());
		StringBuffer constraintClause = new StringBuffer(constraintSnippet);
		for (int i = 1; i < maxPathLength; i++)
		{
			constraintSnippet.insert(0, "parent.");
			constraintClause.append(" and ").append(constraintSnippet);
		}
		String hql = "from " + className + " " + classAlias + " where "
				+ constraintClause;
		if (logger.isDebugEnabled())
		{
			logger.debug("query: " + hql);
		}
		Query query = session.createQuery(hql);
		query.setLockMode(classAlias, lockMode);
		List<T> list = query.list();
		return list;
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Read an entire item tree hierarchy from the database.
	 * 
	 * @param id
	 *            unique identifier to search for the root node
	 * @param removeFromAssociations
	 *            if <code>true</code>, the item tree's associations with
	 *            other objects will be removed (e.g. in preparation for
	 *            deleting the item tree)
	 * @return persisted item hierarchy
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found
	 * 
	 * if another DAO problem occurred
	 */
	protected T readAll(final long id, final boolean removeFromAssociations)
	{
		return readAll(id, true, removeFromAssociations);
	}

	/**
	 * Read an entire item hierarchy from the database. This method is usually
	 * called before deleting a tree, so we don't throw an exception if a record
	 * in the item tree is not found.
	 * 
	 * @param id
	 *            unique identifier to search for the root node
	 * @param rootNode
	 *            is this the root node of the item hierarchy read here or not
	 * @param removeFromAssociations
	 *            if <code>true</code>, the item tree's associations with
	 *            other objects will be removed (e.g. in preparation for
	 *            deleting the item tree)
	 * @return persisted item hierarchy
	 * 
	 * if another DAO problem occurred
	 */
	@SuppressWarnings("unchecked")
	private T readAll(final long id, boolean rootNode,
			final boolean removeFromAssociations)
	{
		// Load entity
		Session session = getSession();
		T entity = (T) session.get(getPersistentClass(), id, lockMode);

		// If entity not found, throw an exception. It should exist at this
		// stage.
		if ((entity == null) || (entity.getId() == null))
		{
			// This method is usually called before deleting a tree, so don't
			// throw an exception if a record in the item tree is not found.
			return null;
		}

		// Load objects associated with the entity outside the item hierarchy
		if (removeFromAssociations)
		{
			// Remove entity from associations
//			AssociationManager manager = context.getAssociationManager(entity
//					.getItemClass());
//			manager.removeFromAssociations(entity);
		}

		for (Node child : entity.getChildren())
		{
			readAll(child.getId(), false, removeFromAssociations);
		}

		return entity;
	}

	/**
	 * Update an item in the database. Assumes the item already exists in the
	 * database. If it does not, use {@link #create(Item)} instead.
	 * 
	 * @param entity
	 *            item to update
	 * @param userId
	 *            must be non-<code>null</code>
	 * @param generateAuditMessage
	 *            we'll generate an audit message regarding the item update only
	 *            if this flag is <code>true</code>
	 * @throws StaleRecordException
	 *             upon version conflict trying to persist entity changes to the
	 *             database
	 * @throws RecordExistsException
	 *             upon constraint violation (e.g. unique record name) trying to
	 *             persist entity changes to the database
	 * @see net.ruready.eis.content.item.ItemDAO#update(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Long)
	 */
	private void update(final Item entity, final Long userId,
			final boolean generateAuditMessage)
	{
		Session session = getSession();

		// super.update() code. Got to duplicate it because of
		// updateWithChildren().
		checkVersion(entity); // Need this call until we use merge() instead
		// of saveOrUpdate()

		AuditMessage auditMessage = null;
		if (generateAuditMessage)
		{
			// ===============================
			// Add an audit message
			// ===============================
			// If we are in a user-aware context:
			// add a message about the creation or updating of this entity
			// Check if there is custom message, if yes use it, otherwise use a
			// default action
			User user = (User) session.get(User.class, userId, lockMode);
			// The audit message refers to the version of the updated
			// entity, hence increment the version #
			AuditAction action = (entity.getVersion() == Node.VERSION_SEED) ? AuditAction.CREATED
					: AuditAction.UPDATED;
			auditMessage = new AuditMessage(entity, user, action, null, entity
					.getVersion() + 1);
			entity.addMessage(auditMessage);
		}

		// ===============================
		// Persist changes to the database
		// ===============================
		// Save the entity; cascades to its audit messages as well
		session.saveOrUpdate(entity);
		session.flush();
		// entity.setLocalVersion(entity.getVersion());

		// Unclear how to make use of this in the current session pattern
		// because we're not yet using detached objects.
		// logger.info("entity " + entity + " in session? " +
		// session.contains(entity));
		// if (entity.getId() == null)
		// {
		// // new
		// session.persist(entity);
		// }
		// else
		// {
		// if (session.contains(entity))
		// {
		// session.saveOrUpdate(entity);
		// }
		// else
		// {
		// // update, takes care of version control
		// session.merge(entity);
		// }
		// }

		if (logger.isDebugEnabled())
		{
			logger.debug("Saved/updated entity '"
					+ entity.getName()
					+ "' V"
					+ entity.getVersion()
					+ ((auditMessage == null) ? "" : (" audit message ID " + auditMessage
							.getId())));
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findByLikeProperty(final String propertyName, final Object value)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding by ilike criteria: " + propertyName + " like '" + value
					+ "'");
		}
		return getSession().createCriteria(getPersistentClass()).add(
				Restrictions.ilike(propertyName, "%" + value + "%")).addOrder(
				Order.asc(propertyName)).list();
	}

	/**
	 * Generate a list to find the list of children of a certain item type under
	 * a parent node, or a view of these children.
	 * 
	 * @param <C>
	 *            type of child
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @param selectClause
	 *            query is prefixed with this optional string; if
	 *            <code>null</code>, query will select <code>(*)</code>
	 *            from the child table
	 * @param constraintClause
	 *            if non-<code>null</code>, is appended to the query string
	 * @param constraintParams
	 *            a list of parameter values appearing in the constraint clause
	 * @return HQL query
	 * 
	 */
	@SuppressWarnings("unchecked")
	private <C extends T> Query generateFindChildrenQuery(final Item parent,
			final Class<C> persistentClass, final ItemType childItemType,
			final String selectClause, final String constraintClause,
			final List<?> constraintParams)
	{
		if (childItemType.getItemClass() != persistentClass)
		{
			throw new EISException("Child class " + persistentClass + " and type "
					+ childItemType + " don't match");
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding children of type " + childItemType + " under parent "
					+ parent.getName() + " type " + parent.getIdentifier() + " id "
					+ parent.getId());
		}

		// ===================================================================
		// Construct and run query that finds the number of children instances
		// whose appropriate parent's id matches parent.id and the optional
		// additional constraint clause.
		// ===================================================================
		// Determine how many ".parent" need to be
		// appended to the query string according to the location of parent and
		// C in the Item tree).
		final String childClassName = persistentClass.getSimpleName();
		final String childAlias = childClassName.toLowerCase();
		String parentSnippet = ItemTypeUtil.getSuperParentFieldName(parent
				.getIdentifier(), childItemType);

		// Construct query string from parts
		StringBuffer queryString = TextUtil.emptyStringBuffer();
		// Append optional select clause
		if (!TextUtil.isEmptyString(selectClause))
		{
			queryString.append(selectClause);
		}
		// Append main query (basically, "select child from table where
		// superParentId=?")
		queryString.append(" from ").append(childClassName).append(" ")
				.append(childAlias).append(" where ").append(childAlias).append(".")
				.append(parentSnippet).append(".id = ?");
		// Append optional constraint clause
		if (!TextUtil.isEmptyString(constraintClause))
		{
			queryString.append(" and (").append(constraintClause).append(")");
		}

		// Construct query object
		Session session = getSession();
		Query query = session.createQuery(queryString.toString());
		// The lock causes weird crashes if the select clause is non-empty so
		// disable it. As if it doesn't recognize the childAlias any more in the
		// query.
		if (selectClause == null)
		{
			query.setLockMode(childAlias, lockMode);
		}
		// Add parameters to query
		int position = 0;
		query.setParameter(position++, parent.getId());
		if (constraintParams != null)
		{
			for (Object param : constraintParams)
			{
				query.setParameter(position++, param);
			}
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("query = " + query);
		}
		return query;
	}
}
