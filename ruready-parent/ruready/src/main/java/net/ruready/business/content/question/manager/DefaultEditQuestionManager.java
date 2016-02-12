/*****************************************************************************************
 * Source File: DefaultEditQuestionManager.java
 ****************************************************************************************/
package net.ruready.business.content.question.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.entity.audit.AuditAction;
import net.ruready.business.content.item.entity.audit.AuditMessage;
import net.ruready.business.content.item.manager.AbstractEditQuestionManager;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.QuestionCount;
import net.ruready.business.content.question.entity.QuestionStateChangeMessage;
import net.ruready.business.content.question.entity.property.QuestionCountType;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.exception.SystemException;
import net.ruready.common.observer.Message;
import net.ruready.common.observer.Observer;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchEngine;
import net.ruready.eis.content.item.ItemDAO;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An default implementation of the manager (service) handling question
 * operations and in particular DAO operations.
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
 * @version Jul 16, 2007
 */
public class DefaultEditQuestionManager implements AbstractEditQuestionManager, Observer
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultEditQuestionManager.class);

	// ========================= FIELDS ====================================

	/**
	 * Locator of the DAO factory that creates DAOs for Items.
	 */
	protected final ResourceLocator resourceLocator;

	/**
	 * Retrieved from the resource locator.
	 */
	protected final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects.
	 */
	protected final ApplicationContext context;

	/**
	 * The user requesting the DAO operations.
	 */
	private final User user;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a service that will use a resource locator and a DAO factory to
	 * read/write items to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations a resource locator pointing to
	 *            a DAO factory
	 */
	public DefaultEditQuestionManager(final ResourceLocator resourceLocator,
			final ApplicationContext context, final User user)

	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
		this.user = user;
	}

	// ========================= UNSUPPORTED OPS: AbstractEQManager ========

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
	 * Delete an existing question from the the database. If the item doesn't
	 * exist, this method has no effect.
	 * 
	 * @param item
	 *            item to be deleted
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#delete(net.ruready.business.content.item.entity.Item,
	 *      boolean)
	 */
	public void delete(final Question item, final boolean respectLocks)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Deleting question '" + item.getName() + "'");
		}
		// Do not delete item if it is read-only or unique; only notify client
		// of a "soft"
		// error
		if (respectLocks && (item.isReadOnly() || item.isUnique()))
		{
			if (logger.isInfoEnabled())
			{
				logger.info("Ignoring delete() of question '" + item.getName() + "'"
						+ " because it is read-only or unique.");
			}
			return;
		}

		// Fully load item and its parent so that we can remove the item from
		// the
		// association before deleting it.
		ItemDAO<Question> questionDAO = (ItemDAO<Question>) eisManager.getDAO(
				Question.class, context);
		Question question = questionDAO.read(item.getId(), true);
		Item parent = question.getParent();

		// Remove question from any associations before attempting to delete it
		// from the database, because the deleted object would be re-saved by
		// cascade and throw an org.hibernate.ObjectDeletedException.
		question.removeFromParent();

		// Persist changes
		questionDAO.delete(question);
		if (parent != null)
		{
			ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager
					.getDAO(Item.class, context);
			itemDAO.update(parent);
		}
	}

	/**
	 * List all items in the database.
	 * 
	 * @return a list of all items
	 * 
	 * if there a DAO problem occurred
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findAll(java.lang.Class)
	 */
	public <T extends Question> List<T> findAll(Class<T> persistentClass)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Find all questions");
		}
		DAO<T, Long> itemDAO = eisManager.getDAO(persistentClass, context);
		List<T> items = itemDAO.findAll();
		return items;
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param hierarchyRootId
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findAllNonDeleted(java.lang.Class,
	 *      net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends Question> List<T> findAllNonDeleted(Class<T> persistentClass,
			ItemType hierarchyRootId)
	{
		throw new SystemException("findAllNonDeleted(): not supported");
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

		/*
		 * try { // ============================================================ //
		 * Load item from database //
		 * ============================================================ DAO<Question,
		 * Long> dao = eisManager.getDAO(Question.class); Question item =
		 * dao.findById(id, true); dao.close(); //
		 * ============================================================ // Post
		 * processing and population of extra useful properties //
		 * ============================================================ // Order
		 * children // item.refreshAll(); // Load parent hierarchy return item; }
		 * catch (EISException e) { throw new ApplicationException(e.toString(),
		 * e); }
		 */
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
	 * @param id
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#read(java.lang.Class,
	 *      long)
	 */
	public <T extends Question> T read(Class<T> persistentClass, long id)
	{
		throw new SystemException("read(): not supported");
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
	 * @param item
	 * @param moveFromSN
	 * @param moveToSN
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#moveChildLocation(net.ruready.business.content.item.entity.Item,
	 *      int, int)
	 */
	public void moveChildLocation(Question item, int moveFromSN, int moveToSN)
	{
		throw new SystemException("moveChildLocation(): not supported");
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
	 * @param item
	 * @return
	 */
	public List<AuditMessage> readAuditMessages(Question item)
	{
		throw new SystemException("readAuditMessages(): not supported");
	}

	/**
	 * @param item
	 * @param action
	 * @return
	 */
	public AuditMessage readLatestAuditMessage(Question item, AuditAction action)
	{
		throw new SystemException("readLatestAuditMessage(): not supported");
	}

	/**
	 * Update a question.
	 * 
	 * @param item
	 * @param respectLocks
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#update(net.ruready.business.content.item.entity.Item,
	 *      boolean)
	 */
	public void update(Question item, final boolean respectLocks)
	{
		throw new SystemException("update(): not supported");
	}

	/**
	 * Merge a question.
	 * 
	 * @param item
	 * @param respectLocks
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#update(net.ruready.business.content.item.entity.Item,
	 *      boolean)
	 */
	public void merge(Question item, final boolean respectLocks)
	{
		throw new SystemException("merge(): not supported");
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

	/**
	 * @param <T>
	 * @param parent
	 * @param persistentClass
	 * @param childItemType
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildren(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends Question> List<T> findChildren(Item parent,
			Class<T> persistentClass, ItemType childItemType)
	{
		throw new SystemException(
				"findChildren(): not supported. Use Item manager instead.");
	}

	/**
	 * Find the list of children identifiers of a certain item type under a
	 * parent node.
	 * 
	 * @param <T>
	 *            type of child
	 * @param parent
	 *            parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @return list of children IDs under the parent; need not be its direct
	 *         children
	 * 
	 * 
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildrenIds(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Question> List<Long> findChildrenIds(final Item parent,
			final Class<T> persistentClass, final ItemType childItemType)

	{
		throw new SystemException(
				"findChildrenByIds(): not supported. Use Item manager instead.");
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
		throw new SystemException(
				"findChildrenCount(): not supported. Use findQuestionCount() instead.");
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
		throw new SystemException("childrenToList(): not supported");
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
		throw new SystemException("fromJSON(): not supported");
	}

	/**
	 * Find all questions scheduled for deletion.
	 * 
	 * @return list of questions scheduled for deletion
	 * @throws ApplicationException
	 *             if a DAO problem occurred
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#findDeleted()
	 */
	public List<Question> findDeleted()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding all deleted questions...");
		}
		// Set an example
		Question example = new Question(null, null);
		example.delete();
		example.setFormulation(null);

		ItemDAO<Question> questionDAO = (ItemDAO<Question>) eisManager.getDAO(
				Question.class, context);
		List<Question> items = questionDAO.findByExample(example);
		return items;
	}

	/**
	 * Find questions of a certain type and level under a certain node.
	 * 
	 * @param parent
	 *            parent node to look under
	 * @param questionType
	 *            if non-<code>null</code>, searches for this question type
	 *            (academic/creative)
	 * @param level
	 *            if non-<code>null</code>, searches for this difficulty
	 *            level
	 * @param parametric
	 *            if non-<code>null</code>, restricts the set of questions
	 *            to parametric (if <code>true</code>) or non-parametric (if
	 *            <code>false</code>)
	 * @return list of questions of this type, level and under this parent
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#findQuestionCount(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.question.entity.property.QuestionType,
	 *      int)
	 */
	public List<Question> findQuestions(final Item parent,
			final QuestionType questionType, final Integer level, final Boolean parametric)
	{
		List<Object> constraintParams = new ArrayList<Object>();
		String constraintClause = this.generateConstraintClause(parent, questionType,
				level, parametric, constraintParams);
		ItemDAO<Question> itemDAO = (ItemDAO<Question>) eisManager.getDAO(Question.class,
				context);
		return itemDAO.findChildren(parent, Question.class, ItemType.QUESTION,
				constraintClause, constraintParams);
	}

	/**
	 * Find the number of question of a certain type and level under a certain
	 * node.
	 * 
	 * @param parent
	 *            parent node to look under
	 * @param questionType
	 *            if non-<code>null</code>, searches for this question type
	 *            (academic/creative)
	 * @param level
	 *            if non-<code>null</code>, searches for this difficulty
	 *            level
	 * @param parametric
	 *            if non-<code>null</code>, restricts the set of questions
	 *            to parametric (if <code>true</code>) or non-parametric (if
	 *            <code>false</code>)
	 * @return number of questions of this type, level and under this parent
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#findQuestionCount(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.question.entity.property.QuestionType,
	 *      int)
	 */
	public long findQuestionCount(final Item parent, final QuestionType questionType,
			final Integer level, final Boolean parametric)
	{
		List<Object> constraintParams = new ArrayList<Object>();
		String constraintClause = this.generateConstraintClause(parent, questionType,
				level, parametric, constraintParams);
		ItemDAO<Question> itemDAO = (ItemDAO<Question>) eisManager.getDAO(Question.class,
				context);
		return itemDAO.findChildrenCount(parent, Question.class, ItemType.QUESTION,
				constraintClause, constraintParams);
	}

	/**
	 * @param parent
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#generateQuestionCount(net.ruready.business.content.item.entity.Item)
	 */
	public QuestionCount generateQuestionCount(Item parent)
	{
		QuestionCount questionCount = new QuestionCount(parent);

		for (int level = 1; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
		{
			// Fill in basic counts
			for (QuestionType questionType : QuestionType.values())

			{
				long count = this.findQuestionCount(parent, questionType, level, null);
				questionCount.put(questionType.getQuestionCountType(), level, count);
			}

			// Fill in parametric count
			long paramCount = this.findQuestionCount(parent, null, level, true);
			questionCount.put(QuestionCountType.PARAMETRIC, level, paramCount);

			// Total count across types is automatically computed within the
			// question count get() method, so nothing to do here
		}

		// Total count across levels is automatically computed within the
		// question count get() method, so nothing to do here

		return questionCount;
	}

	// ========================= IMPLEMENTATION: AbstractEQManager =========

	/**
	 * Search for a user by criteria.
	 * 
	 * @param searchCriteria
	 *            an object holding a list of search criteria
	 * @return list of questions matching the criteria
	 * @throws ApplicationException
	 *             if a DAO problem occurred
	 */
	public List<Question> findByCriteria(SearchCriteria searchCriteria)
	{
		SearchEngine<Question> searchEngine = resourceLocator.getSearchEngine(
				Question.class, context);
		List<Question> questions = searchEngine.search(searchCriteria);
		return questions;
	}

	/**
	 * Implementation of read. Assumes that item is an instance of Question.
	 * 
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#read(java.lang.Class)
	 */
	public <T extends Question> T read(Class<T> persistentClass, long id, T item)
	{
		// Assumes that item is an instance of Question.
		ItemDAO<T> questionDAO = (ItemDAO<T>) eisManager.getDAO(persistentClass, context);
		questionDAO.readInto(id, item);
		return item;
	}

	// ========================= IMPLEMENTATION: AbstractTrash =============

	/**
	 * Manually empty the entire trash.
	 */
	public void clear()
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Clearing question trash");
		}
		// Find all questions scheduled for deletion
		List<Question> items = findDeleted();

		// Delete all questions
		for (Question item : items)
		{
			this.delete(item, false);
		}
	}

	/**
	 * An automatic cleaning of the trash. Should called at regular time
	 * intervals. Deletes old items from the trash.
	 */
	public void expunge()
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Expunging question trash");
		}
		long recentEnoughTime = new Date().getTime()
				- resourceLocator
						.getPropertyAsLong(WebAppNames.RESOURCE_LOCATOR.PROPERTY.TRASH_EXPUNGE_TIME)
				* CommonNames.TIME.MINS_TO_MS;

		// Find all questions in the database.
		// @todo when the list is large, optimize this find operation.
		List<Question> questions = this.findAll(Question.class); // findDeleted();
		// logger.debug("questions " + questions);

		// Delete all questions that apply using the observer pattern
		for (Question question : questions)
		{
			question.addObserver(this);
			// logger.debug("Question ID " + question.getId() + " current state
			// "
			// + question.getStateID());
			question.timeout(recentEnoughTime);
			question.removeObserver(this);
		}

	} // expunge()

	// ========================= IMPLEMENTATION: Observer ==================

	/**
	 * @see net.ruready.common.observer.Observer#update(net.ruready.common.observer.Message)
	 */
	public void update(Message message)
	{
		// Messages are assumed to come from question state changes only.
		QuestionStateChangeMessage qMessage = (QuestionStateChangeMessage) message;
		Question question = qMessage.getQuestion();
		// Process question state change in expunge()
		if (logger.isInfoEnabled())
		{
			logger.info("Question ID " + question.getId() + " state changed to "
					+ qMessage.getNewStateID());
		}
		switch (qMessage.getNewStateID())
		{
			case EXPIRED:
			{
				this.delete(question, false);
				break;
			}

			default:
			{
				// This should never be reached as long as we query only deleted
				// questions
				// in expunge(), but might we decided to process all questions
				// to remove
				// "NEW" marks on old enough questions.
				this.update(question, true);
				break;
			}
		}
	}

	/**
	 * Generate a constraint clause Find the number of question of a certain
	 * type and level under a certain node.
	 * 
	 * @param parent
	 *            parent node to look under
	 * @param questionType
	 *            if non-<code>null</code>, searches for this question type
	 *            (academic/creative)
	 * @param level
	 *            if non-<code>null</code>, searches for this difficulty
	 *            level
	 * @param parametric
	 *            if non-<code>null</code>, restricts the set of questions
	 *            to parametric (if <code>true</code>) or non-parametric (if
	 *            <code>false</code>)
	 * @param constraintParams
	 *            filled with constraint clause parameter values upon return.
	 *            Must pass in an empty list.
	 * @return HQL constraint clause
	 */
	private String generateConstraintClause(final Item parent,
			final QuestionType questionType, final Integer level,
			final Boolean parametric, List<Object> constraintParams)
	{
		String constraintClause = "1=1";

		if (questionType != null)
		{
			constraintClause = constraintClause + " and (question.questionType = ?)";
			constraintParams.add(questionType);
		}
		if (level != null)
		{
			constraintClause = constraintClause + " and (question.level = ?)";
			constraintParams.add(level);
		}
		if (parametric != null)
		{
			String condition = (parametric) ? "is not null" : "is null";
			constraintClause = constraintClause + " and (question.parameters "
					+ condition + ")";
		}

		return constraintClause;
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @see net.ruready.business.content.catalog.exports.AbstractEditQuestionManager#getUser()
	 */
	public User getUser()
	{
		return user;
	}
}
