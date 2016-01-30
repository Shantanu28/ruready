/*****************************************************************************************
 * Source File: HibernateItemDAO.java
 ****************************************************************************************/
package net.ruready.eis.content.item;

import net.ruready.business.content.item.entity.Item;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.imports.HibernateAssociationManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Data access object (DAO) Hibernate implementation for domain model class Item.
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
 * @see net.ruready.item.entity.DemoCatalogCreator
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 1, 2007
 */
public class HibernateItemAssociationManager<T extends Item> extends
		HibernateAssociationManager<T, Long>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(HibernateItemAssociationManager.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a DAO from a session and class type.
	 * 
	 * @param persistentClass
	 *            entity's class type
	 * @param sessionFactory
	 *            a heavy-weight session factory that can provide new sessions after old
	 *            ones has closed or have been corrupted.
	 */
	public HibernateItemAssociationManager(final Class<T> persistentClass,
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(persistentClass, sessionFactory, context);
	}

	// ========================= IMPLEMENTATION: HibernateAM ========

}
