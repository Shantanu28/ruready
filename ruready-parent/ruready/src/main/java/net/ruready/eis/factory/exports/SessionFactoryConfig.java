/*****************************************************************************************
 * Source File: SessionFactoryConfig.java
 ****************************************************************************************/
package net.ruready.eis.factory.exports;

import net.ruready.eis.factory.entity.HibernateConfigurationID;
import net.ruready.eis.factory.entity.HibernateNamingStrategyID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NamingStrategy;

/**
 * A configuration object for an EIS session factory. Includes all possible configuration
 * properties of all factories.
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
 * @version Aug 11, 2007
 */
public class SessionFactoryConfig
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SessionFactoryConfig.class);

	// ========================= FIELDS ====================================

	/**
	 * Location of configuration file, e.g. hibernate's .cfg.xml. Location should be on
	 * the classpath as Hibernate uses #resourceAsStream style lookup for its
	 * configuration file. The default classpath location of the hibernate config file is
	 * in the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session. Use
	 * {@link #setConfiguration(Configuration)} to update the configuration for the
	 * current session.
	 */
	private String eisConfigFileName;

	/**
	 * Location of the logging system configuration file.
	 */
	private String logConfigFileName;

	/**
	 * Database table naming strategy. All session factories use Hibernate's
	 * {@link NamingStrategy} interface, which is flexible enough.
	 */
	private HibernateNamingStrategyID namingStrategyID;

	// -------------------------------
	// Hibernate specific properties
	// -------------------------------

	/**
	 * Type of configuration (e.g. default, annotation, etc.).
	 */
	private HibernateConfigurationID hibernateConfigurationID;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Only sub-classes can instantiate the singleton. Normally they are also singleton
	 * and use this in their own protected constructors.
	 */
	private SessionFactoryConfig()
	{
		super();
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the eisConfigFileName
	 */
	public String getEisConfigFileName()
	{
		return eisConfigFileName;
	}

	/**
	 * @param eisConfigFileName
	 *            the eisConfigFileName to set
	 */
	public void setEisConfigFileName(String configFileName)
	{
		this.eisConfigFileName = configFileName;
	}

	/**
	 * @return the hibernateConfigurationID
	 */
	public HibernateConfigurationID getHibernateConfigurationID()
	{
		return hibernateConfigurationID;
	}

	/**
	 * @param hibernateConfigurationID
	 *            the hibernateConfigurationID to set
	 */
	public void setHibernateConfigurationID(HibernateConfigurationID configID)
	{
		this.hibernateConfigurationID = configID;
	}

	/**
	 * @return the namingStrategyID
	 */
	public HibernateNamingStrategyID getNamingStrategyID()
	{
		return namingStrategyID;
	}

	/**
	 * @param namingStrategyID
	 *            the namingStrategyID to set
	 */
	public void setNamingStrategyID(HibernateNamingStrategyID namingStrategyID)
	{
		this.namingStrategyID = namingStrategyID;
	}

	/**
	 * @return the logConfigFileName
	 */
	public String getLogConfigFileName()
	{
		return logConfigFileName;
	}

	/**
	 * @param logConfigFileName
	 *            the logConfigFileName to set
	 */
	public void setLogConfigFileName(String logConfigFileName)
	{
		this.logConfigFileName = logConfigFileName;
	}

}
