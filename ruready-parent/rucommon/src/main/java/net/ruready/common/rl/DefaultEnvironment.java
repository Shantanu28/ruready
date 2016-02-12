/*****************************************************************************************
 * Source File: DefaultEnvironment.java
 ****************************************************************************************/
package net.ruready.common.rl;

import java.io.Serializable;
import java.util.Properties;

import net.ruready.common.discrete.Identifier;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.exception.SystemException;
import net.ruready.common.password.Encryptor;
import net.ruready.common.rl.parser.ParserService;
import net.ruready.common.rl.parser.ParserServiceID;
import net.ruready.common.search.SearchEngine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A default implementation of {@link Environment} that delegates to a resource
 * locator. The methods {@link Environment#setUp()} and
 * {@link ResourceLocator#setUp(String)} are left abstract.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 30, 2007
 */
public abstract class DefaultEnvironment implements Environment
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultEnvironment.class);

	// ========================= FIELDS ====================================

	/**
	 * The resource locator to which we delegate.
	 */
	protected ResourceLocator resourceLocator;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a stand-alone setup object. Resource locator is not yet
	 * initialized. Call <code>setUp()</code> to initialize it.
	 */
	public DefaultEnvironment()
	{

	}

	// ========================= IMPLEMENTATION: ResourceLocator ===========

	/**
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getConfig()
	 */
	public Properties getConfig()
	{
		return resourceLocator.getConfig();
	}

	/**
	 * @param key
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getProperty(java.lang.String)
	 */
	public String getProperty(String key)
	{
		return resourceLocator.getProperty(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getProperty(java.lang.String,
	 *      java.lang.String)
	 */
	public String getProperty(String key, String defaultValue)
	{
		return resourceLocator.getProperty(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getPropertyAsLong(java.lang.String)
	 */
	public long getPropertyAsLong(String key)
	{
		return resourceLocator.getPropertyAsLong(key);
	}

	/**
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getDAOFactory()
	 */
	public AbstractEISManager getDAOFactory()
	{
		return resourceLocator.getDAOFactory();
	}

	/**
	 * @param managerIdentifier
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getEJBHome(net.ruready.common.discrete.Identifier)
	 */
	public Object getEJBHome(Identifier managerIdentifier)
	{
		return resourceLocator.getEJBHome(managerIdentifier);
	}

	/**
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getEncryptor()
	 */
	public Encryptor getEncryptor()
	{
		return resourceLocator.getEncryptor();
	}

	/**
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getMailSession()
	 */
	public Object getMailSession()
	{
		return resourceLocator.getMailSession();
	}

	/**
	 * @param parserServiceIdentifier
	 * @param args
	 * @return
	 * @see net.ruready.common.rl.ResourceLocator#getParserService(net.ruready.common.rl.parser.ParserServiceID,
	 *      java.lang.Object[])
	 */
	public ParserService getParserService(ParserServiceID parserServiceIdentifier,
			Object... args)
	{
		return resourceLocator.getParserService(parserServiceIdentifier, args);
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
			Class<E> searchableClass, ApplicationContext context)
	{
		return resourceLocator.getSearchEngine(searchableClass, context);
	}

	/**
	 * @see net.ruready.common.rl.ResourceLocator#info()
	 */
	public void info()
	{
		resourceLocator.info();
	}

	/**
	 * @param configFileName
	 * @see net.ruready.common.rl.ResourceLocator#setUp(java.lang.String)
	 */
	public void setUp(String configFileName)
	{
		throw new SystemException("use setUp() instead");
	}

	// ========================= IMPLEMENTATION: Environment ===============

	/**
	 * Clean up objects.
	 * 
	 * @see net.ruready.common.rl.Environment#tearDown()
	 */
	synchronized public void tearDown()
	{
		resourceLocator.tearDown();
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

}
