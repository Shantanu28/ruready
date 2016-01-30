/*****************************************************************************************
 * Source File: HibernateConfigurationID.java
 ****************************************************************************************/
package net.ruready.eis.factory.entity;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Possible Hibernate configuration types. Used by the HibernateSessionFactory to decide
 * which type of configuration to load.
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
public enum HibernateConfigurationID implements Comparable<HibernateConfigurationID>
{
	// ========================= ENUMERATED TYPES ==========================

	DEFAULT
	{
		@Override
		public Configuration create()
		{
			return new Configuration();
		}
	},

	ANNOTATION
	{
		@Override
		public Configuration create()
		{
			return new AnnotationConfiguration();
		}
	},
	;

	// ========================= FIELDS ====================================

	// ========================= METHODS ===================================

	/**
	 * Return the look-up name of this manager (business service).
	 * 
	 * @return the look-up name of this manager (business service).
	 */
	public abstract Configuration create();

}
