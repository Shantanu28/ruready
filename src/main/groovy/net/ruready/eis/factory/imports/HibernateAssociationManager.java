/*****************************************************************************************
 * Source File: HibernateDAO.java
 ****************************************************************************************/
package net.ruready.eis.factory.imports;

import java.io.Serializable;

import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.manager.AssociationManager;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;

/**
 * An implementation of a generic entity association manager for Hibernate. Assumes that
 * we are inside a session transaction boundary in most of its methods.
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
 * @version Aug 9, 2007
 */
public class HibernateAssociationManager<T extends PersistentEntity<ID>, ID extends Serializable>
		implements AssociationManager<T, ID>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(HibernateAssociationManager.class);

	// ========================= FIELDS ====================================

	/**
	 * Class of the persistent entity.
	 */
	private final Class<T> persistentClass;

	/**
	 * A heavy-weight session factory that can provide new sessions after old ones has
	 * closed or have been corrupted.
	 */
	protected final AbstractHibernateSessionFactory sessionFactory;

	/**
	 * Produces entity association manager objects.
	 */
	protected final ApplicationContext context;

	/**
	 * Lock mode used in read and query operations. Distinguishes between read-write and
	 * read-only transactions.
	 */
	protected final LockMode lockMode;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a DAO from a session and class type.
	 * 
	 * @param persistentClass
	 *            entity's class type
	 * @param sessionFactory
	 *            a heavy-weight session factory that can provide new sessions after old
	 *            ones has closed or have been corrupted.
	 * @param context
	 *            application context
	 */
	public HibernateAssociationManager(final Class<T> persistentClass,
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		this.persistentClass = persistentClass;
		this.sessionFactory = sessionFactory;
		this.context = context;
		this.lockMode = context.isEisLock() ? LockMode.READ : LockMode.NONE;
	}

	// ========================= IMPLEMENTATION: AssociationManager =

	/**
	 * This is a stub method.
	 * 
	 * @param entity
	 * @see net.ruready.common.eis.manager.AssociationManager#removeFromAssociations(net.ruready.common.eis.entity.PersistentEntity)
	 */
	public void removeFromAssociations(T entity)
	{
		// This is a stub method
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the class type for this DAO.
	 * 
	 * @return the class type for this DAO
	 */
	public final Class<T> getPersistentClass()
	{
		return persistentClass;
	}
}
