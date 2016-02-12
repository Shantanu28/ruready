/*****************************************************************************************
 * Source File: HibernateSessionFactory.java
 ****************************************************************************************/
package net.ruready.eis.factory.entity;

import java.util.Properties;

import net.ruready.common.misc.Singleton;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

/**
 * Configures and provides access to Hibernate sessions, tied to the current thread of
 * execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 * <p>
 * FT stands for full thread model (for session, transactions and interceptors).
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
public class HibernateFTFactory implements Singleton, AbstractHibernateSessionFactory
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateFTFactory.class);

	/**
	 * Maximum number of times to attempt factory initialization before failing. Not
	 * currently in use.
	 */
	protected static final int MAX_INITIALIZATION_ATTEMPTS = 5;

	/**
	 * Factory objects: local thread model for sessions.
	 */
	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();

	/**
	 * Factory objects: local thread model for transactions.
	 */
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();

	// ========================= FIELDS ====================================

	/**
	 * The single instance of this singleton class.
	 */
	private volatile static HibernateFTFactory instance;

	/**
	 * Configuration object holding properties.
	 */
	private Properties config = null;

	/**
	 * Factory objects: Hibernate configuration.
	 */
	private Configuration configuration;

	/**
	 * Factory objects: Hibernate session factory (heavy-weight).
	 */
	private SessionFactory sessionFactory;

	/**
	 * Indicates whether the transaction thread model is used or not.
	 */
	private boolean useThreadLocal = true;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Only sub-classes can instantiate the singleton. Normally they are also singleton
	 * and use this in their own protected constructors.
	 */
	private HibernateFTFactory()
	{
		super();
	}

	/**
	 * Get the unique instance of this singleton. Initialize the factory if it is not yet
	 * initialized.
	 * 
	 * @param sessionFactoryConfig
	 *            an object containing configuration properties for this factory
	 * @return the unique instance of this singleton
	 */
	public static HibernateFTFactory getInstance()
	{
		if (instance == null)
		{
			synchronized (HibernateFTFactory.class)
			{
				if (instance == null)
				{
					instance = new HibernateFTFactory();
				}
			}
		}
		return instance;
	}

	// ========================= IMPLEMENTATION: AbstractHBSessionFactory ==

	/**
	 * Initialize the factory. This must be a synchronized and called to effect a new
	 * configuration.
	 * 
	 * @param thisConfig
	 *            an object containing configuration properties for this factory
	 */
	synchronized public void setUp(Properties thisConfig)
	{
		this.config = thisConfig;

		// We use annotations, so instantiate the proper
		// Configuration type
		this.configuration = HibernateConfigurationID
				.valueOf(
						config
								.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.HIBERNATE_CONFIG))
				.create();

		// Use a lower case naming strategy for tables
		this.configuration
				.setNamingStrategy(HibernateNamingStrategyID
						.valueOf(
								config
										.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.HIBERNATE_NAMING_STRATEGY))
						.create());

		// Disable ThreadLocal Session/Transaction handling if CMT is used
		if (org.hibernate.engine.transaction.internal.jta.CMTTransactionFactory.class.getName().equals(
				configuration.getProperty(Environment.TRANSACTION_STRATEGY)))
		{
			useThreadLocal = false;
		}
		if (logger.isInfoEnabled())
		{
			logger.info(useThreadLocal ? "Using local thread model."
					: "Not using local thread model.");
		}
	}

	/**
	 * Closes the current SessionFactory and releases all resources.
	 * <p>
	 * The only other method that can be called on this object after this one is
	 * {@link #rebuildSessionFactory()}.
	 * 
	 * @see net.ruready.eis.factory.entity.AbstractHibernateSessionFactory#tearDown()
	 */
	public void tearDown()
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Shutting down Hibernate.");
		}
		// Close caches and connection pools
		if (sessionFactory != null)
		{
			sessionFactory.close();
		}

		// Clear static variables
		configuration = null;
		sessionFactory = null;

		// Clear ThreadLocal variables
		threadSession.set(null);
		threadTransaction.set(null);
	}

	/**
	 * @see net.ruready.eis.factory.entity.AbstractHibernateSessionFactory#openSession()
	 */
	public void openSession()
	{
		getSession();
	}

	/**
	 * Retrieves the current Session local to the thread. <p/> If no Session is open,
	 * opens a new Session for the running thread. If CMT is used, returns the Session
	 * bound to the current JTA container transaction. Most other operations on this class
	 * will then be no-ops or not supported, the container handles Session and Transaction
	 * boundaries, ThreadLocals are not used.
	 * 
	 * @return Session
	 * @see net.ruready.eis.factory.entity.AbstractHibernateSessionFactory#getSession()
	 */
	public Session getSession()
	{
		if (useThreadLocal)
		{
			Session session = threadSession.get();
			if (session == null || !session.isOpen() || !session.isConnected())
			{
				if (sessionFactory == null)
				{
					rebuildSessionFactory();
				}
				session = (sessionFactory != null) ? sessionFactory.openSession() : null;
				if (logger.isDebugEnabled())
				{
					logger.debug("Opening new Session for this thread: @"
							+ session.hashCode());
				}
				threadSession.set(session);
			}
			return session;
		}
		else
		{
			return sessionFactory.getCurrentSession();
		}
	}

	/**
	 * Flushes and closes the Session local to the thread.
	 * <p>
	 * Is a no-op (with warning) if called in a CMT environment. Should be used in
	 * non-managed environments with resource local transactions, or with EJBs and
	 * bean-managed transactions.
	 */
	public void closeSession()
	{
		if (useThreadLocal)
		{
			Session session = threadSession.get();
			threadSession.set(null);
			Transaction tx = threadTransaction.get();
			if (tx != null && (!tx.wasCommitted() || !tx.wasRolledBack()))
				throw new IllegalStateException(
						"Closing Session but Transaction still open!");
			if (session != null && session.isOpen())
			{
				if (logger.isDebugEnabled())
				{
					logger
							.debug("Closing Session of this thread: @"
									+ session.hashCode());
				}
				session.flush();
				session.close();
			}
		}
		else
		{
			logger.warn("Using CMT/JTA, intercepted superfluous close call.");
		}
	}

	/**
	 * Start a new database transaction.
	 * <p>
	 * Is a no-op (with warning) if called in a CMT environment. Should be used in
	 * non-managed environments with resource local transactions, or with EJBs and
	 * bean-managed transactions. In both cases, it will either start a new transaction or
	 * join the existing ThreadLocal or JTA transaction.
	 */
	public void beginTransaction()
	{
		if (useThreadLocal)
		{
			Transaction tx = threadTransaction.get();
			if (tx == null)
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("Starting new database transaction in this thread.");
				}
				Session session = getSession();
				try
				{
					tx = session.beginTransaction();
				}
				catch (Throwable e)
				{
					this.closeSession();
					this.reset();
					setUp(this.config);
					session = this.getSession();
					tx = session.beginTransaction();
				}
				threadTransaction.set(tx);
			}
		}
		else
		{
			logger.warn("Using CMT/JTA, intercepted superfluous tx begin call.");
		}
	}

	/**
	 * Commit the database transaction.
	 * <p>
	 * Is a no-op (with warning) if called in a CMT environment. Should be used in
	 * non-managed environments with resource local transactions, or with EJBs and
	 * bean-managed transactions. It will commit the ThreadLocal or BMT/JTA transaction.
	 */
	public void commitTransaction()
	{
		if (useThreadLocal)
		{
			Transaction tx = threadTransaction.get();
			try
			{
				if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack())
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("Committing database transaction of this thread.");
					}
					tx.commit();
				}
				threadTransaction.set(null);
			}
			catch (RuntimeException ex)
			{
				logger.error(ex);
				rollbackTransaction();
				throw ex;
			}
		}
		else
		{
			logger.warn("Using CMT/JTA, intercepted superfluous tx commit call.");
		}
	}

	/**
	 * Rollback the database transaction.
	 * <p>
	 * Is a no-op (with warning) if called in a CMT environment. Should be used in
	 * non-managed environments with resource local transactions, or with EJBs and
	 * bean-managed transactions. It will rollback the resource local or BMT/JTA
	 * transaction.
	 */
	public void rollbackTransaction()
	{
		if (useThreadLocal)
		{
			Transaction tx = threadTransaction.get();
			try
			{
				threadTransaction.set(null);
				if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack())
				{
					if (logger.isDebugEnabled())
					{
						logger
								.debug("Trying to rollback database transaction of this thread.");
					}
					tx.rollback();
					if (logger.isDebugEnabled())
					{
						logger.debug("Database transaction rolled back.");
					}
				}
			}
			catch (RuntimeException ex)
			{
				throw new RuntimeException(
						"Might swallow original cause, check ERROR log!", ex);
			}
			finally
			{
//				closeSession();
			}
		}
		else
		{
			logger.warn("Using CMT/JTA, intercepted superfluous tx rollback call.");
		}
	}

	/**
	 * @return
	 * @see net.ruready.eis.factory.entity.AbstractHibernateSessionFactory#isTransactionActive()
	 */
	public boolean isTransactionActive()
	{
		Session session = getSession();
		return (session == null) ? false : session.getTransaction().isActive();
	}

	/**
	 * @return
	 * @see net.ruready.common.eis.exports.AbstractEISBounder#wasTransactionCommitted()
	 */
	public boolean wasTransactionCommitted()
	{
		Session session = getSession();
		return (session == null) ? false : session.getTransaction().wasCommitted();
	}

	/**
	 * @return
	 * @see net.ruready.common.eis.exports.AbstractEISBounder#wasTransactionRolledBack()
	 */
	public boolean wasTransactionRolledBack()
	{
		Session session = getSession();
		return (session == null) ? false : session.getTransaction().wasRolledBack();
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Rebuild hibernate session factory.
	 * <p>
	 * We have experienced some intermittent {@link ExceptionInInitializerError}s thrown
	 * during factory initialization. Attempts initialization several times in case of
	 * such errors before totally failing. We also synchronized this method to be safer.
	 * <p>
	 * Note: I read somewhere that it might help to delete the file:
	 * ${tomcat}/work/Catalina/localhost/ru2/SESSIONS.ser in this case. Added to
	 * build.xml's deployment task.
	 */
	synchronized private void rebuildSessionFactory()
	{
		try
		{
			String configName = config
					.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.DAO_FACTORY_CONFIG_FILE_NAME);
			configuration.configure(configName);
			if (logger.isDebugEnabled())
			{
				logger.debug("Building session factory for configuration '" + configName
						+ "'");
			}
			sessionFactory = configuration.buildSessionFactory();
			if (logger.isInfoEnabled())
			{
				logger.info("Factory initialized successfully.");
			}
		}
		catch (Exception e)
		{
			logger.error("%%%% Error Creating SessionFactory %%%% " + e.getMessage());
			// logger.error(ExceptionUtil.getStackTraceAsString(e));
		}
		catch (Error e)
		{
			// Errors are usually unrecoverable
			logger.error("%%%% Error Creating SessionFactory %%%% " + e.getMessage());
			// logger.error(ExceptionUtil.getStackTraceAsString(e));
		}
	}

	/**
	 * Closes the current SessionFactory but keeps the configuration object.
	 * <p>
	 * The only other method that can be called on this object after this one is
	 * {@link #rebuildSessionFactory()}.
	 * 
	 * @see net.ruready.eis.factory.entity.AbstractHibernateSessionFactory#tearDown()
	 */
	synchronized private void reset()
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Resetting Hibernate.");
		}
		// Close caches and connection pools
		if (sessionFactory != null)
		{
			sessionFactory.close();
		}

		// Clear static variables
		sessionFactory = null;
		configuration = null;

		// Clear ThreadLocal variables
		threadSession.set(null);
		threadTransaction.set(null);
	}

	// ========================= GETTERS & SETTERS =========================

}
