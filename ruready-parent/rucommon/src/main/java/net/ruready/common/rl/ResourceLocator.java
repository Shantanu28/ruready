/*****************************************************************************************
 * Source File: ResourceLocator.java
 ****************************************************************************************/
package net.ruready.common.rl;

import java.io.Serializable;
import java.util.Properties;

import net.ruready.common.discrete.Identifier;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.password.Encryptor;
import net.ruready.common.rl.parser.ParserService;
import net.ruready.common.rl.parser.ParserServiceID;
import net.ruready.common.search.SearchEngine;

/**
 * An abstract resource locator. It provides access to the persistent layer via
 * the DAO Factory. EJB home interface JNDI locators and other global resource
 * locators can later be added as the methods of this interface. Implementations
 * should typically be singletons. The resource locator is typically a
 * singleton. To ensure thread safety, it only provides read-only access through
 * getters. To set properties for resources at run-time, use the
 * {@link ApplicationContext} object.
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
 * @version Jul 21, 2007
 */
public interface ResourceLocator extends Resource<String>
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Print information on the configuration of this resource locator.
	 */
	void info();

	// ------------------------------------------------------------------------
	// Add resource/service references here. Concrete service instantiation
	// is performed by implementation of this interface.
	// ------------------------------------------------------------------------

	// ------------------------- Persistent layer --------------------------

	/**
	 * Locate and return the factory of persistent layer DAOs.
	 * 
	 * @return the factory of persistent layer DAOs
	 */
	AbstractEISManager getDAOFactory();

	/**
	 * Locate and return a database search engine. A template (generic) method.
	 * 
	 * @param searchableClass
	 *            searchable class type
	 * @param context
	 *            application context, required to instantiate DAOs during
	 *            search operations
	 * @return database search engine instance
	 */
	<E extends PersistentEntity<ID>, ID extends Serializable> SearchEngine<E> getSearchEngine(
			final Class<E> searchableClass, final ApplicationContext context);

	// ------------------------- EJB ---------------------------------------

	/**
	 * Locate and return the factory of the EJB home interface. Should be cast
	 * to an EJBHome object if this library is included in this application.
	 * 
	 * @param managerIdentifier
	 *            identifier of the service manager whose EJB interface is
	 *            fetched
	 * @return the factory of persistent layer DAOs
	 */
	Object getEJBHome(Identifier managerIdentifier);

	// ------------------------- JavaMail ----------------------------------

	/**
	 * Returns a Mail Session instance. Should be cast to a JavaMail Session
	 * object if this library is included in this application.
	 * 
	 * @return Mail session instance
	 */
	Object getMailSession();

	// ------------------------- Encryption --------------------------------

	/**
	 * Returns an encryption engine instance.
	 * 
	 * @return encryption engine instance
	 */
	Encryptor getEncryptor();

	// ------------------------- Properties (map) --------------------------

	/**
	 * Return the configuration properties object of this resource locator.
	 * 
	 * @return the configuration properties object of this resource locator
	 */
	Properties getConfig();

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
	 */
	String getProperty(final String key);

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
	 * @see #setProperty
	 * @see #defaults
	 */
	String getProperty(final String key, final String defaultValue);

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
	 */
	long getPropertyAsLong(final String key);

	// ------------------------- Parser ------------------------------------

	/**
	 * Locate and return a parser service.
	 * 
	 * @param parserServiceIdentifier
	 *            parser service identifier (an enumerated factory, type-safe)
	 * @param args
	 *            parser service instantiation arguments. May differ for
	 *            different assemblers. See the documentation of each enumerated
	 *            type.
	 * @return the corresponding parser service instance
	 */
	ParserService getParserService(final ParserServiceID parserServiceIdentifier,
			Object... args);
}
