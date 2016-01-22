/*****************************************************************************************
 * Source File: HibernateEISManager.java
 ****************************************************************************************/
package net.ruready.eis.factory.imports;

import java.io.Serializable;
import java.util.Properties;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.catalog.entity.ContentKnowledge;
import net.ruready.business.content.catalog.entity.ContentType;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.content.catalog.entity.ExpectationCategory;
import net.ruready.business.content.catalog.entity.ExpectationSet;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.catalog.entity.Topic;
import net.ruready.business.content.concept.entity.Concept;
import net.ruready.business.content.concept.entity.ConceptCollection;
import net.ruready.business.content.document.entity.Document;
import net.ruready.business.content.document.entity.DocumentCabinet;
import net.ruready.business.content.document.entity.Folder;
import net.ruready.business.content.interest.entity.Interest;
import net.ruready.business.content.interest.entity.InterestCollection;
import net.ruready.business.content.interest.entity.SubInterest;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.skill.entity.Skill;
import net.ruready.business.content.skill.entity.SkillCollection;
import net.ruready.business.content.tag.entity.HibernateTagItemAssociationManager;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.Federation;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.business.content.world.entity.World;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.eis.manager.AssociationManager;
import net.ruready.common.misc.Singleton;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.content.item.HibernateItemAssociationManager;
import net.ruready.eis.content.item.HibernateItemDAO;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.entity.HibernateSThreadFactory;
import net.ruready.eis.ta.HibernateExpectationAssessmentDAO;
import net.ruready.eis.ta.HibernateKnowledgeAssessmentDAO;
import net.ruready.eis.ta.HibernateStudentTranscriptDAO;
import net.ruready.eis.user.HibernateUserDAO;
import net.ruready.eis.user.HibernateUserGroupDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * A singleton factory that generates generic DAOs. Because it is a singleton, we can
 * write a compilable generic method {@link #getDAO(Class)} that searches for a
 * type-customized DAO in a static map {@link #daos}, or returns a generic
 * CRUD-supporting DAO.
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
public class HibernateEISManager implements AbstractEISManager, Singleton
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateEISManager.class);

	/**
	 * Initialization here bypasses the problem of dead-locking and the need to
	 * synchronize in the alternative implementation that instantiates instance in
	 * getInstance().
	 */
	private volatile static AbstractEISManager instance;

	/**
	 * A heavy-weight session factory that can provide new sessions after old ones has
	 * closed or have been corrupted.
	 */
	private AbstractHibernateSessionFactory sessionFactory;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize the factory. Add custom DAOs to an internal static map.
	 */
	protected HibernateEISManager()
	{

	}

	/**
	 * Get the unique instance of this singleton.
	 * 
	 * @return the unique instance of this singleton
	 */
	public static AbstractEISManager getInstance()
	{
		if (instance == null)
		{
			synchronized (HibernateEISManager.class)
			{
				if (instance == null)
				{
					instance = new HibernateEISManager();
				}
			}
		}
		return instance;
	}

	// ========================= IMPLEMENTATION: AbstractEISManager ========

	/**
	 * Initialize the DAO factory. Must be called before an instance is requested.
	 * 
	 * @param config
	 *            an object containing configuration properties for this factory
	 * @return the unique instance of this singleton
	 */
	synchronized public void setUp(Properties config)
	{
		// this.sessionFactory = HibernateSessionFactory.getInstance();
		// this.sessionFactory = HibernateFTFactory.getInstance();
		// this.sessionFactory = CaveatEmptorHibernateSessionFactory.getInstance();
		this.sessionFactory = HibernateSThreadFactory.getInstance();
		this.sessionFactory.setUp(config);
	}

	/**
	 * Shuts down the current resource locator and releases all resources.
	 * 
	 * @see net.ruready.common.rl.ResourceLocator#tearDown()
	 */
	synchronized public void tearDown()
	{
		sessionFactory.tearDown();
	}

	/**
	 * Initialize the attached context with custom DAOs. Called the first time a
	 * {@link DAO} instance is requested using the {@link #getDAO(Class)} method.
	 * 
	 * @param context
	 *            application context to set up
	 */
	public void setUpContextDAO(final ApplicationContext context)
	{
		// ------------------------------------------------------------------------
		// Add custom DAOs here. Example:
		//
		// context.putDAOInCache(Item.class, new HibernateItemDAO<Item>(Item.class,
		// sessionFactory, context));
		// ------------------------------------------------------------------------

		// daos.put(Node.class, new
		// HibernateNodeDAO(
		// sessionFactory));

		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// Content component
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

		// ########################################
		// Generic item types
		// ########################################
		setCustomItemDAO(Item.class, sessionFactory, context);
		setCustomItemDAO(Root.class, sessionFactory, context);
		setCustomItemDAO(MainItem.class, sessionFactory, context);

		// ########################################
		// Catalog hierarchy types
		// ########################################
		setCustomItemDAO(Catalog.class, sessionFactory, context);
		setCustomItemDAO(Subject.class, sessionFactory, context);
		setCustomItemDAO(Course.class, sessionFactory, context);
		setCustomItemDAO(ContentType.class, sessionFactory, context);

		// ########################################
		// Content knowledge sub-hierarchy types
		// ########################################
		setCustomItemDAO(ContentKnowledge.class, sessionFactory, context);
		setCustomItemDAO(Topic.class, sessionFactory, context);
		setCustomItemDAO(SubTopic.class, sessionFactory, context);
		setCustomItemDAO(Question.class, sessionFactory, context);

		// ########################################
		// Content knowledge sub-hierarchy types
		// ########################################
		setCustomItemDAO(ExpectationSet.class, sessionFactory, context);
		setCustomItemDAO(ExpectationCategory.class, sessionFactory, context);
		setCustomItemDAO(ExpectationCategory.class, sessionFactory, context);
		setCustomItemDAO(Expectation.class, sessionFactory, context);

		// ########################################
		// Question external hierarchy types
		// ########################################

		// ########################################
		// Institution (World) hierarchy types
		// ########################################
		setCustomItemDAO(World.class, sessionFactory, context);
		setCustomItemDAO(Country.class, sessionFactory, context);
		setCustomItemDAO(Federation.class, sessionFactory, context);
		setCustomItemDAO(State.class, sessionFactory, context);
		setCustomItemDAO(School.class, sessionFactory, context);
		setCustomItemDAO(City.class, sessionFactory, context);

		// ########################################
		// Document cabinet hierarchy types
		// ########################################
		setCustomItemDAO(DocumentCabinet.class, sessionFactory, context);
		setCustomItemDAO(Folder.class, sessionFactory, context);
		setCustomItemDAO(Document.class, sessionFactory, context);

		// ########################################
		// Tag hierarchy types
		// ########################################
		setCustomItemDAO(TagCabinet.class, sessionFactory, context);
		setCustomItemDAO(TagItem.class, sessionFactory, context);

		// ########################################
		// Interest hierarchy types
		// ########################################
		setCustomItemDAO(InterestCollection.class, sessionFactory, context);
		setCustomItemDAO(Interest.class, sessionFactory, context);
		setCustomItemDAO(SubInterest.class, sessionFactory, context);

		// ########################################
		// Concept hierarchy types
		// ########################################
		setCustomItemDAO(ConceptCollection.class, sessionFactory, context);
		setCustomItemDAO(Concept.class, sessionFactory, context);

		// ########################################
		// Concept hierarchy types
		// ########################################
		setCustomItemDAO(SkillCollection.class, sessionFactory, context);
		setCustomItemDAO(Skill.class, sessionFactory, context);

		// ########################################
		// Trash Can Sub-Component
		// ########################################
		// Trash types
		setCustomItemDAO(DefaultTrash.class, sessionFactory, context);

		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// User component
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

		context.putDAOInCache(User.class, new HibernateUserDAO(sessionFactory, context));
		context.putDAOInCache(UserGroup.class, new HibernateUserGroupDAO(sessionFactory,
				context));

		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// Test Administration component
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		context.putDAOInCache(StudentTranscript.class, new HibernateStudentTranscriptDAO(sessionFactory, context));
		context.putDAOInCache(ExpectationAssessment.class, new HibernateExpectationAssessmentDAO(sessionFactory, context));
		context.putDAOInCache(KnowledgeAssessment.class, new HibernateKnowledgeAssessmentDAO(sessionFactory, context));
		
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// Miscellaneous classes
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

		// ------------------------------------------------------------------------
		// Printouts
		// ------------------------------------------------------------------------
		// if (logger.isDebugEnabled())
		// {
		// logger.debug("Initialized custom DAOs.");
		// }
		// logger.debug("Custom daos: ");
		// logger.debug(daos);
	}

	/**
	 * Initialize the attached context with custom {@link AssociationManager}s. Called
	 * the first time a {@link AssociationManager} instance is requested using the
	 * {@link #getDAO(Class)} method.
	 * 
	 * @param context
	 *            application context to set up
	 */
	public void setUpContextAssociationManager(final ApplicationContext context)
	{
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// Content component
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

		// ########################################
		// Generic item types
		// ########################################
		context.putAssociationManagerInCache(Item.class,
				new HibernateItemAssociationManager<Item>(Item.class, sessionFactory,
						context));

		// ########################################
		// Tag hierarchy types
		// ########################################
		// Add all types that can be attached as tags to other items here

		// ########################################
		// Interest hierarchy types
		// ########################################
		setCustomTagItemAssociationManager(InterestCollection.class, sessionFactory,
				context);
		setCustomTagItemAssociationManager(Interest.class, sessionFactory, context);
		setCustomTagItemAssociationManager(SubInterest.class, sessionFactory, context);

		// ########################################
		// Concept hierarchy types
		// ########################################
		setCustomTagItemAssociationManager(ConceptCollection.class, sessionFactory,
				context);
		setCustomTagItemAssociationManager(Concept.class, sessionFactory, context);

		// ########################################
		// Concept hierarchy types
		// ########################################
		setCustomTagItemAssociationManager(SkillCollection.class, sessionFactory, context);
		setCustomTagItemAssociationManager(Skill.class, sessionFactory, context);

	}

	/**
	 * Produces a DAO class. If a custom DAO class is not found in the {@link #daos}
	 * class, it is deferred to a default instantiation of {@link HibernateDAO} for the
	 * particular class and identifier types.
	 * 
	 * @param <T>
	 *            class type, e.g. <code>Item.class</code>
	 * @param <ID>
	 *            identifier type, e.g. Long
	 * @param entityClass
	 *            persistent entity type, e.g. <code>Item.class</code>. Must match
	 *            <code><T></code>
	 * @param context
	 *            web application context that caches DAO objects
	 * @return requested DAO
	 * @see net.ruready.common.eis.manager.AbstractEISManager#getDAO(java.lang.Class,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable> DAO<T, ID> getDAO(
			final Class<T> entityClass, final ApplicationContext context)
	{
		// Get the DAO from the map or add it to the map if it is not there yet
		DAO<T, ID> result = context.readDAOFromCache(entityClass);
		if (result == null)
		{
			result = new HibernateDAO<T, ID>(entityClass, sessionFactory, context);
			context.putDAOInCache(entityClass, result);
		}
		return result;
	}

	/**
	 * Produces an entity manager class. If a custom entity manager class is not found in
	 * the {@link #daos} class, it is deferred to a default instantiation of
	 * {@link HibernateAssociationManager} for the particular class and identifier types.
	 * 
	 * @param <T>
	 *            class type, e.g. <code>Item.class</code>
	 * @param <ID>
	 *            identifier type, e.g. Long
	 * @param entityClass
	 *            persistent entity type, e.g. <code>Item.class</code>. Must match
	 *            <code><T></code>
	 * @param context
	 *            web application context that caches DAO objects
	 * @return requested DAO
	 * @return requested entity association manager
	 * @see net.ruready.common.eis.AbstractAssociationManagerFactory#getAssociationManager(java.lang.Class)
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable> AssociationManager<T, ID> getAssociationManager(
			final Class<T> entityClass, final ApplicationContext context)
	{
		// Get the DAO from the map or add it to the map if it is not there yet
		AssociationManager<T, ID> result = context
				.readAssociationManagerFromCache(entityClass);
		if (result == null)
		{
			result = new HibernateAssociationManager<T, ID>(entityClass, sessionFactory,
					context);
			context.putAssociationManagerInCache(entityClass, result);
		}
		return result;
	}

	/**
	 * Check if the proxy or persistent collection is initialized.
	 * 
	 * @param proxy
	 *            a persistable object, proxy, persistent collection or <code>null</code>
	 * @return <code>true</code> if the argument is already initialized, or is not a
	 *         proxy or collection
	 */
	public boolean isInitialized(Object proxy)
	{
		return Hibernate.isInitialized(proxy);
	}

	/**
	 * Close the current Hibernate session managed by the session factory.
	 * 
	 * @throws HibernateException
	 * @see net.ruready.common.eis.manager.AbstractEISManager#closeSession()
	 */
	public void closeSession()
	{
		if (sessionFactory != null)
		{
			sessionFactory.closeSession();
		}
	}

	/**
	 * Open a {@link Session} using the session factory, but don't return it.
	 * 
	 * @see net.ruready.common.eis.manager.AbstractEISManager#openSession()
	 */
	public final void openSession()
	{
		sessionFactory.openSession();
	}

	/**
	 * Directly a {@link Session} from the session factory. This method must be called
	 * after {@link #setUp(Properties)} has been called. This bypasses the standard
	 * {@link AbstractEISManager} interface, so use with caution.
	 * 
	 * @return Hibernate session
	 */
	public final Session getSession()
	{
		return sessionFactory.getSession();
	}

	/**
	 * @see net.ruready.common.eis.exports.AbstractEISBounder#beginTransaction()
	 */
	public void beginTransaction()
	{
		sessionFactory.beginTransaction();
	}

	/**
	 * @see net.ruready.common.eis.exports.AbstractEISBounder#commitTransaction()
	 */
	public void commitTransaction()
	{
		sessionFactory.commitTransaction();
	}

	/**
	 * @see net.ruready.common.eis.exports.AbstractEISBounder#rollbackTransaction()
	 */
	public void rollbackTransaction()
	{
		if (!wasTransactionCommitted() && isTransactionActive())
		{
			sessionFactory.rollbackTransaction();
		}
	}

	/**
	 * @return
	 * @see net.ruready.common.eis.exports.AbstractEISBounder#isTransactionActive()
	 */
	public boolean isTransactionActive()
	{
		return sessionFactory.isTransactionActive();
	}

	/**
	 * @return
	 * @see net.ruready.common.eis.exports.AbstractEISBounder#wasTransactionCommitted()
	 */
	public boolean wasTransactionCommitted()
	{
		return sessionFactory.wasTransactionCommitted();
	}

	/**
	 * @return
	 * @see net.ruready.common.eis.exports.AbstractEISBounder#wasTransactionRolledBack()
	 */
	public boolean wasTransactionRolledBack()
	{
		return sessionFactory.wasTransactionRolledBack();
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * A helper method that puts an {@link Item}'s custom DAO in the custom DAO map.
	 * Assumes the {@link #associationManagers} map is already prepared.
	 * 
	 * @param <T>
	 * @param itemClass
	 *            item type
	 * @param context
	 *            web application context
	 * @param sessionFactory
	 *            A heavy-weight session factory that can provide new sessions after old
	 *            ones has closed or have been corrupted.
	 */
	private static <T extends Item> void setCustomItemDAO(final Class<T> itemClass,
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		context.putDAOInCache(itemClass, new HibernateItemDAO<T>(itemClass,
				sessionFactory, context));
	}

	/**
	 * A helper method that puts an {@link TagItem}'s association entity in the custom
	 * association manager map.
	 * 
	 * @param <T>
	 * @param tagItemClass
	 *            tag item type
	 * @param context
	 *            web application context
	 * @param sessionFactory
	 *            A heavy-weight session factory that can provide new sessions after old
	 *            ones has closed or have been corrupted.
	 */
	private static <T extends TagItem> void setCustomTagItemAssociationManager(
			final Class<T> tagItemClass,
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		context.putAssociationManagerInCache(tagItemClass,
				new HibernateTagItemAssociationManager<T>(tagItemClass, sessionFactory,
						context));
	}
}
