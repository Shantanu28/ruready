/*****************************************************************************************
 * Source File: WebAppResourceLocator.java
 ****************************************************************************************/
package net.ruready.web.common.imports;

import java.net.URL;

import net.ruready.business.imports.DefaultResourceLocator;
import net.ruready.business.user.entity.rsa.UserEncryptorFactory;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.util.PropertiesUtil;
import net.ruready.parser.service.exports.SimpleParserServiceProvider;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.commsen.stopwatch.Stopwatch;

/**
 * A resource locator for web applications.
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
 * @version Sep 8, 2007
 */
public class WebAppResourceLocator extends DefaultResourceLocator
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(WebAppResourceLocator.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This is a singleton. Prevents instantiation.
	 */
	protected WebAppResourceLocator()
	{

	}

	/**
	 * Return the single instance of this type.
	 * 
	 * @return the single instance of this type
	 */
	public static ResourceLocator getInstance()
	{
		if (instance == null)
		{
			synchronized (WebAppResourceLocator.class)
			{
				if (instance == null)
				{
					instance = new WebAppResourceLocator();
					// Load from properties files whose name is hard-coded in
					// the Names class
					instance.setUp(WebAppNames.RESOURCE_LOCATOR.WEB_APP_CONFIG_FILE);
				}
			}
		}
		return instance;
	}

	// ========================= IMPLEMENTATION: ResourceLocator ===========

	/**
	 * Set up: configure and initialize the resource locators using a properties file to
	 * specify the type of objects this locator should find.
	 * 
	 * @param configFileName
	 *            properties file name to be searched for in the class path
	 */
	@Override
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
		if (logger.isInfoEnabled())
		{
			logger.info("Initializing");
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
		logger.info("Initializing mail configuration '"
				+ config.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.MAIL) + "'");
		mailConfig = PropertiesUtil.subMap(config,
				CommonNames.RESOURCE_LOCATOR.PROPERTY.MAIL);

		// Initialize encryption engine
		encryptorFactory = new UserEncryptorFactory();
		encryptor = this.getEncryptor(config
				.getProperty(CommonNames.RESOURCE_LOCATOR.PROPERTY.ENCRYPTOR));

		// Initialize the parser service provider
		parserServiceProvider = new SimpleParserServiceProvider();

		ready = true;
	}
}
