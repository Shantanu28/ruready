/*****************************************************************************************
 * Source File: DefaultResourceLocator.java
 ****************************************************************************************/
package net.ruready.business.imports;

import java.io.Serializable;
import java.net.URL;
import java.util.Properties;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.ruready.common.discrete.Identifier;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.password.DefaultEncryptorFactory;
import net.ruready.common.password.Encryptor;
import net.ruready.common.password.EncryptorFactory;
import net.ruready.common.password.EncryptorID;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.rl.parser.ParserService;
import net.ruready.common.rl.parser.ParserServiceID;
import net.ruready.common.search.SearchEngine;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.PropertiesUtil;
import net.ruready.eis.factory.imports.HibernateEISManager;
import net.ruready.eis.factory.imports.HibernateSearchEngine;
import net.ruready.parser.service.exports.ParserServiceProvider;
import net.ruready.parser.service.exports.SimpleParserServiceProvider;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.commsen.stopwatch.Stopwatch;

/**
 * A default resource locator that reads its properties from a file.
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
 * @version Aug 13, 2007
 */
public abstract class DefaultResourceLocator implements ResourceLocator
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultResourceLocator.class);

	/**
	 * Singleton instance of this class.
	 */
	protected static volatile DefaultResourceLocator instance;

	// ========================= FIELDS ====================================

	/**
	 * Resource locator configuration (property map).
	 */
	protected Properties config = new Properties();

	/**
	 * EIS manager (a factory of DAOs, etc.).
	 */
	protected AbstractEISManager eisManager;

	/**
	 * JavaMail session configuration.
	 */
	protected Properties mailConfig;

	/**
	 * Encryptor factory.
	 */
	protected EncryptorFactory encryptorFactory;

	/**
	 * Active encryptor.
	 */
	protected Encryptor encryptor;

	/**
	 * Parser service provider.
	 */
	protected ParserServiceProvider parserServiceProvider;

	/**
	 * Readiness state of this locator. It is set to true in
	 * {@link #setUp(String)} and back to false in {@link #tearDown()}.
	 */
	protected boolean ready = false;

	/**
	 * Optimization: caches frequently used services.
	 */
	protected LRUMap contextObjects = new LRUMap(5);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This is a singleton. Prevents instantiation.
	 */
	protected DefaultResourceLocator()
	{

	}

	// ========================= IMPLEMENTATION: ResourceLocator ===========

	/**
	 * Set up: configure and initialize the resource locators using a properties
	 * file to specify the type of objects this locator should find.
	 * 
	 * @param configFileName
	 *            properties file name to be searched for in the class path
	 */
	synchronized public void setUp(final String configFileName)
	{
		// Prepare configuration object
		config = PropertiesUtil.loadConfigFile(configFileName);

		// Initialize log4J
		URL url = Thread
				.currentThread()
				.getContextClassLoader()
				.getResource(
						config
								.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.LOG4J_CONFIG_FILE));
		PropertyConfigurator.configure(url);
		logger.info("Initializing");

		// Enable the Stopwatch timing library
		Stopwatch.setActive(true);

		// Initialize session factory
		eisManager = getDAOFactory(config
				.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.DAO_FACTORY));
		if (eisManager != null)
		{
			eisManager.setUp(config);
		}

		// Initialize Mail session
		logger.info("Initializing mail configuration '"
				+ config.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.MAIL) + "'");
		mailConfig = PropertiesUtil.subMap(config,
				CommonNames.RESOURCE_LOCATOR.PROPERTY.MAIL);

		// Initialize encryption engine
		encryptorFactory = new DefaultEncryptorFactory();
		encryptor = this.getEncryptor(config
				.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.ENCRYPTOR));

		// Initialize the parser service provider
		parserServiceProvider = new SimpleParserServiceProvider();

		ready = true;
	}

	/**
	 * Shuts down the current resource locator and releases all resources.
	 */
	synchronized public void tearDown()
	{
		eisManager.tearDown();
		ready = false;
	}

	/**
	 * Print information on the configuration of this resource locator.
	 */
	public void info()
	{
		logger.info("Resource Locator configuration:");
		if (config == null)
		{
			logger.info("<<< Not configured yet >>>");
			return;
		}
		logger
				.info("Log4J config file name : "
						+ config
								.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.LOG4J_CONFIG_FILE));
		logger
				.info("EIS config file name   : "
						+ config
								.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.DAO_FACTORY_CONFIG_FILE_NAME));
	}

	/**
	 * @see net.ruready.common.rl.ResourceLocator#getDAOFactory()
	 */
	public AbstractEISManager getDAOFactory()
	{
		return eisManager;
	}

	/**
	 * Locate and return a database search engine. A template (generic) method.
	 * 
	 * @param <E>
	 * @param <ID>
	 * @param searchableClass
	 *            searchable class type
	 * @param context
	 *            application context, required to instantiate DAOs during
	 *            search operations
	 * @return database search engine instance
	 * @see net.ruready.common.rl.ResourceLocator#getSearchEngine(java.lang.Class,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public <E extends PersistentEntity<ID>, ID extends Serializable> SearchEngine<E> getSearchEngine(
			final Class<E> searchableClass, final ApplicationContext context)
	{
		return this.getSearchEngine(config
				.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.DAO_FACTORY),
				searchableClass, context);
	}

	/**
	 * @param managerIdentifier
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getEJBHome(net.ruready.common.discrete.Identifier)
	 */
	public Object getEJBHome(Identifier managerIdentifier)
	{
		throw new ApplicationException(
				"DefaultResourceLocator does not support EJB home location");
	}

	/**
	 * Returns a Mail Session instance.
	 * 
	 * @return Mail session instance
	 */
	public Session getMailSession()
	{
		String serviceName = CommonNames.RESOURCE_LOCATOR.PROPERTY.MAIL;
		String type = config.getProperty(serviceName);

		if (contextObjects.containsKey(serviceName))
		{
			// Session found in cache
			logger.info("Using cached Mail session");
			return (Session) contextObjects.get(serviceName);
		}
		else if (type == null)
		{
			throw new ApplicationException(
					"DefaultResourceLocator Mail session is not configured");
		}
		else if (type.equals(CommonNames.RESOURCE_LOCATOR.PROPERTY.MAIL_JNDI))
		{
			try
			{
				logger.info("Creating new JNDI Mail session");
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx
						.lookup(CommonNames.JNDI.INITIAL_CONTEXT);
				Session session = (Session) envCtx.lookup(CommonNames.JNDI.MAIL_SESSION);
				// Save in cache
				contextObjects.put(serviceName, session);
				return session;
			}
			catch (NamingException e)
			{
				logger.error("Could not find Mail JNDI resource: " + e.toString());
			}

		}
		else if (type.equals(CommonNames.RESOURCE_LOCATOR.PROPERTY.MAIL_STAND_ALONE))
		{
			logger.info("Creating new stand-alone Mail session");
			Session session = Session.getDefaultInstance(mailConfig, null);
			// Save in cache
			contextObjects.put(serviceName, session);
			return session;
		}
		throw new ApplicationException(
				"DefaultResourceLocator Mail session configuration not recognized: "
						+ type);
	}

	/**
	 * Returns an encryption engine instance.
	 * 
	 * @return encryption engine instance
	 */
	public Encryptor getEncryptor()
	{
		return encryptor;
	}

	/**
	 * @see net.ruready.common.rl.ResourceLocator#getParserService(net.ruready.common.rl.parser.ParserServiceID,
	 *      java.lang.Object[])
	 */
	public ParserService getParserService(final ParserServiceID parserServiceIdentifier,
			final Object... args)
	{
		// In the future, this may be replaced by a web service if necessary
		return parserServiceProvider.createType(parserServiceIdentifier, args);
	}

	// ========================= METHODS ===================================

	/**
	 * Returns the DAO factory by the value of the corresponding configuration
	 * property.
	 * 
	 * @param type
	 *            the identifier of the DAO factory
	 * @return DAO factory instance
	 */
	protected AbstractEISManager getDAOFactory(String type)
	{
		if (type == null)
		{
			return null;
		}
		else if (type.equals(CommonNames.RESOURCE_LOCATOR.PROPERTY.DAO_FACTORY_HIBERNATE))
		{
			logger.info("Initializing DAO factory '" + type + "'");
			return HibernateEISManager.getInstance();
		}
		else
		{
			logger.warn("Configured DAO Factory type ('" + type + "') not supported.");
		}

		return null;
	}

	/**
	 * Returns a database search engine by the value of the corresponding DAO
	 * factory configuration property.
	 * 
	 * @param type
	 *            the identifier of the DAO factory
	 * @param searchableClass
	 *            searchable class type
	 * @return search engine instance
	 */
	private <E extends PersistentEntity<ID>, ID extends Serializable> SearchEngine<E> getSearchEngine(
			final String type, final Class<E> searchableClass,
			final ApplicationContext context)
	{
		if (type == null)
		{
			return null;
		}
		else if (type.equals(CommonNames.RESOURCE_LOCATOR.PROPERTY.DAO_FACTORY_HIBERNATE))
		{
			return new HibernateSearchEngine<E, ID>(searchableClass, this, context);
		}
		else
		{
			logger.warn("Configured DAO Factory type ('" + type + "') not supported.");
		}

		return null;
	}

	/**
	 * Returns the encryption engine by the value of the corresponding
	 * configuration property.
	 * 
	 * @param type
	 *            the identifier of the encryption engine
	 * @return encryption engine instance
	 */
	protected Encryptor getEncryptor(String type)
	{
		EncryptorID encryptorID = EncryptorID.valueOf(type);
		if (encryptorID == null)
		{
			logger.warn("Configured encryption engine type ('" + type
					+ "') not supported, using default (no encryption).");
			return encryptorFactory.createType(encryptorID);
		}
		else
		{
			logger.info("Initializing encryption engine '" + encryptorID.getType() + "'");
			return encryptorFactory.createType(encryptorID);
		}
	}

	/**
	 * Return the configuration properties object of this resource locator.
	 * 
	 * @return the configuration properties object of this resource locator
	 * @see net.ruready.common.rl.ResourceLocator#getConfig()
	 */
	public Properties getConfig()
	{
		return config;
	}

	/**
	 * Searches for the configuration property with the specified key in this
	 * property list. If the key is not found in this property list, the default
	 * property list, and its defaults, recursively, are then checked. The
	 * method returns <code>null</code> if the property is not found.
	 * 
	 * @param key
	 *            the configuration property key.
	 * @return the value in this property list with the specified key value.
	 * @see #getConfig()
	 * @see net.ruready.common.rl.ResourceLocator#getConfigProperty(java.lang.String)
	 */
	public String getProperty(final String key)
	{
		return config.getProperty(key);
	}

	/**
	 * Searches for the property with the specified key in this property list.
	 * If the key is not found in this property list, the default property list,
	 * and its defaults, recursively, are then checked. The method returns the
	 * default value argument if the property is not found.
	 * 
	 * @param key
	 *            the hashtable key.
	 * @param defaultValue
	 *            a default value.
	 * 
	 * @return the value in this property list with the specified key value.
	 * @see net.ruready.common.rl.ResourceLocator#getProperty(java.lang.String,
	 *      java.lang.String)
	 */
	public String getProperty(final String key, final String defaultValue)
	{
		return config.getProperty(key, defaultValue);
	}

	/**
	 * Searches for the configuration property with the specified key in this
	 * property list. If the key is not found in this property list, the default
	 * property list, and its defaults, recursively, are then checked. The
	 * method returns <code>null</code> if the property is not found.
	 * 
	 * @param key
	 *            the configuration property key.
	 * @return the long value in this property list with the specified key
	 *         value. If fails to convert, returns
	 *         <code>INVALID_VALUE_LONG</code>
	 * @see #getConfig()
	 * @see net.ruready.common.rl.ResourceLocator#getConfigPropertyAsLong(java.lang.String)
	 */
	public long getPropertyAsLong(final String key)
	{
		return TextUtil.getStringAsLong(config.getProperty(key));
	}

	// ========================= UTILITIES =================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the ready
	 */
	public boolean isReady()
	{
		return ready;
	}
}
