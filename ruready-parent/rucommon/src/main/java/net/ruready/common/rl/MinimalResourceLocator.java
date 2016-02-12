/*****************************************************************************************
 * Source File: MinimalResourceLocator.java
 ****************************************************************************************/
package net.ruready.common.rl;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Properties;

import net.ruready.common.discrete.Identifier;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.password.DefaultEncryptorFactory;
import net.ruready.common.password.Encryptor;
import net.ruready.common.password.EncryptorID;
import net.ruready.common.rl.parser.ParserService;
import net.ruready.common.rl.parser.ParserServiceID;
import net.ruready.common.search.SearchEngine;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.hsqldb.Session;

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
public class MinimalResourceLocator implements ResourceLocator
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(MinimalResourceLocator.class);

	/**
	 * Singleton instance of this class.
	 */
	private static volatile MinimalResourceLocator instance;

	// ========================= FIELDS ====================================

	private Properties config = new Properties();

	private AbstractEISManager eisManager;

	private DefaultEncryptorFactory encryptorFactory;

	private Encryptor encryptor;

	/**
	 * Readiness state of this locator. It is set to true in
	 * {@link #setUp(String)} and back to false in {@link #tearDown()}.
	 */
	private boolean ready = false;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This is a singleton. Prevents instantiation.
	 */
	protected MinimalResourceLocator()
	{

	}

	/**
	 * Return the single instance of this type.
	 * 
	 * @return the single instance of this type
	 */
	public static ResourceLocator getInstance()
	{
		if ((instance == null) || !instance.ready)
		{
			synchronized (MinimalResourceLocator.class)
			{
				if ((instance == null) || !instance.ready)
				{
					if (instance == null)
					{
						instance = new MinimalResourceLocator();
					}
					// instance exists but is not ready yet, so set it up.
					// Load from properties files whose name is hard-coded in
					// the Names class
					instance
							.setUp(CommonNames.RESOURCE_LOCATOR.MINIMAL_ALONE_CONFIG_FILE);
				}
			}
		}
		return instance;
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
		config = getConfigProperties(configFileName);

		// Initialize log4J
		URL url = Thread
				.currentThread()
				.getContextClassLoader()
				.getResource(
						config
								.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.LOG4J_CONFIG_FILE));
		PropertyConfigurator.configure(url);
		if (logger.isInfoEnabled())
		{
			logger.info("initializing");
		}

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
		// logger.info("Initializing mail configuration '"
		// + config.getProperty(Names.RESOURCE_LOCATOR.PROPERTY.MAIL) + "'");
		// mailConfig = PropertiesUtil.subMap(config,
		// Names.RESOURCE_LOCATOR.PROPERTY.MAIL);

		// Initialize encryption engine
		encryptorFactory = new DefaultEncryptorFactory();
		encryptor = this.getEncryptor(config
				.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.ENCRYPTOR));

		// Initialize DAO factory
		if (eisManager != null)
		{
			eisManager.setUp(config);
		}

		// Initialize the parser service provider
	}

	/**
	 * Shuts down the current resource locator and releases all resources.
	 * 
	 * @see net.ruready.common.rl.ResourceLocator#tearDown()
	 */
	synchronized public void tearDown()
	{
		if (eisManager != null)
		{
			eisManager.tearDown();
		}
	}

	/**
	 * Print information on the configuration of this resource locator.
	 */
	public void info()
	{
		logger.info("Resource Locator configuration:");
		if (config == null)
		{
			logger.info("   <<< Not configured yet >>>");
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
	 * @param <E>
	 * @param <ID>
	 * @param searchableClass
	 * @param context
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getSearchEngine(java.lang.Class,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public <E extends PersistentEntity<ID>, ID extends Serializable> SearchEngine<E> getSearchEngine(
			Class<E> searchableClass, final ApplicationContext context)
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
		return null;
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
	 * @param parserServiceIdentifier
	 * @param args
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getParserService(net.ruready.common.rl.parser.ParserServiceID,
	 *      java.lang.Object[])
	 */
	public ParserService getParserService(final ParserServiceID parserServiceIdentifier,
			Object... args)
	{
		return null;
	}

	// ========================= METHODS ===================================

	/**
	 * This is just a quick way to get some configuration properties to the
	 * factory. Actual implementations of a factory would use their own approach
	 * here. The properties file name is hard-coded in the <code>Names</code>
	 * convention class.
	 * 
	 * @param configFileName
	 *            properties file name to be searched for in the class path
	 * @return the configuration parameters in effect
	 */
	private Properties getConfigProperties(final String configFileName)
	{
		Properties configProps = new Properties();

		InputStream is = null;

		// Load the specified property resource
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			is = classLoader.getResourceAsStream(configFileName);
			if (is != null)
			{
				configProps.load(is);
				is.close();
			}
		}
		catch (Throwable t)
		{
			logger.error("load properties error: " + t.toString());
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (Throwable t)
				{
					logger.error("load properties error: " + t.toString());
				}
			}
		}

		return configProps;
	}

	/**
	 * Returns the DAO factory by the value of the corresponding configuration
	 * property.
	 * 
	 * @param type
	 *            the identifier of the DAO factory
	 * @return DAO factory instance
	 */
	private AbstractEISManager getDAOFactory(String type)
	{
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
	 * @param context
	 *            application context
	 * @return search engine instance
	 */
	private <E extends PersistentEntity<ID>, ID extends Serializable> SearchEngine<E> getSearchEngine(
			String type, final Class<E> searchableClass, final ApplicationContext context)
	{
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
	private Encryptor getEncryptor(String type)
	{
		EncryptorID encryptorID = EncryptorID.valueOf(type);
		if (encryptorID == null)
		{
			logger.warn("Configured encryption engine type ('" + type
					+ "') not supported, using default (no encryption).");
			return encryptorFactory.createType(encryptorID);
		}
		logger.info("Initializing encryption engine '" + encryptorID.getType() + "'");
		return encryptorFactory.createType(encryptorID);
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

	// ========================= GETTERS & SETTERS =========================
}
