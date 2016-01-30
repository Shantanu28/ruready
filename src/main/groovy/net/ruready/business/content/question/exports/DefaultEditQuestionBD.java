package net.ruready.business.content.question.exports;

import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.manager.AbstractEditQuestionManager;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.QuestionCount;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.common.exception.SystemException;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.search.SearchCriteria;

/**
 * A default implementation of the question business delegate.
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
 * @version Aug 12, 2007
 */
public abstract class DefaultEditQuestionBD implements AbstractEditQuestionBD
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractEditQuestionManager manager;

	/**
	 * Use this specific resource locator.
	 */
	protected final ResourceLocator resourceLocator;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param parent
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#generateQuestionCount(net.ruready.business.content.item.entity.Item)
	 */
	public QuestionCount generateQuestionCount(Item parent)
	{
		return manager.generateQuestionCount(parent);
	}

	/**
	 * @param <T>
	 * @param parent
	 * @param persistentClass
	 * @param childItemType
	 * @return
	 * 
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildrenIds(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends Question> List<Long> findChildrenIds(Item parent,
			Class<T> persistentClass, ItemType childItemType)
	{
		return manager.findChildrenIds(parent, persistentClass, childItemType);
	}

	/**
	 * Initialize this BD from fields.
	 * 
	 * @param manager
	 *            wrapped manager
	 * @param resourceLocator
	 *            using this specific resource locator
	 */
	protected DefaultEditQuestionBD(final AbstractEditQuestionManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	// ========================= UNSUPPORTED OPS: AbstractCatalogBD ====

	/**
	 * @param <T>
	 * @param <S>
	 * @param newParent
	 * @param item
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#copyUnder(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public <T extends Question, S extends Question> S copyUnder(T newParent, S item)
	{
		throw new SystemException("copyUnder(): not supported");
	}

	/**
	 * @param parentId
	 * @param item
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#createUnder(long,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public void createUnder(long parentId, Question item)
	{
		throw new SystemException("createUnder(): not supported");
	}

	/**
	 * @param item
	 * @param respectLocks
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#delete(net.ruready.business.content.item.entity.Item,
	 *      boolean)
	 */
	public void delete(Question item, final boolean respectLocks)
	{
		throw new SystemException("delete(): not supported");
	}

	/**
	 * @param item
	 * @param respectLocks
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#deleteAll(net.ruready.business.content.item.entity.Item,
	 *      boolean)
	 */
	public void deleteAll(Question item, final boolean respectLocks)
	{
		throw new SystemException("deleteAll(): not supported");
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findAll(java.lang.Class)
	 */
	public <T extends Question> List<T> findAll(final Class<T> persistentClass)
	{
		throw new SystemException("findAll(): not supported");
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param hierarchyRootId
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findAllNonDeleted(java.lang.Class,
	 *      net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends Question> List<T> findAllNonDeleted(final Class<T> persistentClass,
			final ItemType hierarchyRootId)
	{
		throw new SystemException("findAllNonDeleted(): not supported");
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param item
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByExample(java.lang.Class,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public <T extends Question> List<T> findByExample(Class<T> persistentClass, T item)
	{
		throw new SystemException("findByExample(): not supported");
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param example
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByExampleObject(java.lang.Class,
	 *      java.lang.Object)
	 */
	public <T extends Question> List<T> findByExampleObject(Class<T> persistentClass,
			Object example)
	{
		throw new SystemException("findByExampleObject(): not supported");
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param id
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findById(java.lang.Class,
	 *      long)
	 */
	public <T extends Question> T findById(Class<T> persistentClass, long id)
	{
		throw new SystemException("findById(): not supported");
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param name
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByName(java.lang.Class,
	 *      java.lang.String)
	 */
	public <T extends Question> List<T> findByName(Class<T> persistentClass, String name)
	{
		throw new SystemException("findByName(): not supported");
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param name
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByLikeName(java.lang.Class,
	 *      java.lang.String)
	 */
	public <T extends Question> List<T> findByLikeName(Class<T> persistentClass,
			String name)
	{
		throw new SystemException("findByLikeName(): not supported");
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param propertyName
	 * @param uniqueValue
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByUniqueProperty(java.lang.Class,
	 *      java.lang.String, java.lang.Object)
	 */
	public <T extends Question> T findByUniqueProperty(Class<T> persistentClass,
			String propertyName, Object uniqueValue)
	{
		throw new SystemException("findByUniqueProperty(): not supported");
	}

	/**
	 * @param item
	 * @param moveFromSN
	 * @param moveToSN
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#moveChildLocation(net.ruready.business.content.item.entity.Item,
	 *      int, int)
	 */
	public void moveChildLocation(Question item, int moveFromSN, int moveToSN)
	{
		throw new SystemException("not supported");
	}

	/**
	 * @param newParent
	 * @param item
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#moveUnder(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public void moveUnder(Question newParent, Question item)
	{
		throw new SystemException("moveUnder(): not supported");
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param id
	 * @return
	 */
	public <T extends Question> T readAll(Class<T> persistentClass, long id)
	{
		throw new SystemException("readAll(): not supported");
	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#updateAll(net.ruready.business.content.item.entity.Item)
	 */
	public void updateAll(Question item)
	{
		throw new SystemException("updateAll(): not supported");
	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#mergeAll(net.ruready.business.content.item.entity.Item)
	 */
	public void mergeAll(Question item)
	{
		throw new SystemException("mergeAll(): not supported");
	}

	// ========================= IMPLEMENTATION: AbstractCatalogBD =====

	/**
	 * Basic method delegating search down to the manager layer.
	 * 
	 * @param searchCriteria
	 * @return List of Questions that meet the search criteria.
	 */
	public List<Question> findByCriteria(SearchCriteria searchCriteria)
	{

		return manager.findByCriteria(searchCriteria);
	}

	/**
	 * Delegate the read call to the manager.
	 * 
	 * @param <T>
	 * @param persistentClass
	 * @param id
	 * @param item
	 * @param cascadeType
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#read(java.lang.Class,
	 *      long)
	 */
	public <T extends Question> T read(Class<T> persistentClass, long id)
	{
		return manager.read(persistentClass, id);
	}

	/**
	 * Merge a question: Delegate the call to the manager.
	 */
	public void update(Question item, final boolean respectLocks)
	{
		manager.update(item, respectLocks);
	}

	/**
	 * Merge a question: delegate the call to the manager.
	 */
	public void merge(Question item, final boolean respectLocks)
	{
		manager.merge(item, respectLocks);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildren(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends Question> List<T> findChildren(Item parent,
			Class<T> persistentClass, ItemType childItemType)
	{
		return manager.findChildren(parent, persistentClass, childItemType);
	}

	/**
	 * @param <T>
	 * @param parent
	 * @param persistentClass
	 * @param childItemType
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildrenCount(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends Question> long findChildrenCount(Item parent,
			Class<T> persistentClass, ItemType childItemType)
	{
		return manager.findChildrenCount(parent, persistentClass, childItemType);
	}

	/**
	 * @see net.ruready.business.content.trash.entity.AbstractTrash#clear()
	 */
	public void clear()
	{
		manager.clear();
	}

	/**
	 * @see net.ruready.business.content.trash.entity.AbstractTrash#expunge()
	 */
	public void expunge()
	{
		manager.expunge();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#findDeleted()
	 */
	public List<Question> findDeleted()
	{
		return manager.findDeleted();
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param item
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#childrenToList(java.lang.Class,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public <T extends Question> List<T> childrenToList(Class<T> persistentClass, Item item)
	{
		return manager.childrenToList(persistentClass, item);
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param jsonData
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#fromJSON(java.lang.Class,
	 *      java.lang.String)
	 */
	public <T extends Question> List<T> fromJSON(Class<T> persistentClass, String jsonData)
	{
		return manager.fromJSON(persistentClass, jsonData);
	}

	/**
	 * @param parent
	 * @param questionType
	 * @param level
	 * @param parametric
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#findQuestionCount(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.question.entity.property.QuestionType,
	 *      java.lang.Integer, java.lang.Boolean)
	 */
	public long findQuestionCount(Item parent, QuestionType questionType, Integer level,
			Boolean parametric)
	{
		return manager.findQuestionCount(parent, questionType, level, parametric);
	}

	/**
	 * @param parent
	 * @param questionType
	 * @param level
	 * @param parametric
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#findQuestions(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.question.entity.property.QuestionType,
	 *      java.lang.Integer, java.lang.Boolean)
	 */
	public List<Question> findQuestions(Item parent, QuestionType questionType,
			Integer level, Boolean parametric)
	{
		return manager.findQuestions(parent, questionType, level, parametric);
	}

	// ========================= GETTERS & SETTERS =========================
}
