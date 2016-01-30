/*****************************************************************************************
 * Source File: HibernateTagItemAssociationManager.java
 ****************************************************************************************/
package net.ruready.business.content.tag.entity;

import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.content.item.HibernateItemAssociationManager;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.imports.HibernateEISManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Tag item association manager. In principle, the generic parameter should be
 * <code>T extends TagItem</code>, but because this class is instantiated inside an
 * Item DAO implementation, Hibernate's proxy will be <code>Item$$CGLIB...</code>
 * because we load entities (including tag item entities) by <code>Item.class</code>.
 * Hence, it is not a sub-class of {@link TagItem} and will throw an exception trying to
 * call the overridden methods of this class, unless the generic parameter's constraint is
 * <code>T extends Item</code>. This is not a big deal because
 * {@link HibernateEISManager} is responsible for creating the correct mapping between
 * types and association manager classes, so it's easy to make sure that only
 * {@link TagItem}s correspond to this manager class.
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
public class HibernateTagItemAssociationManager<T extends Item> extends
		HibernateItemAssociationManager<T>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(HibernateTagItemAssociationManager.class);

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
	public HibernateTagItemAssociationManager(final Class<T> persistentClass,
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(persistentClass, sessionFactory, context);
	}

	// ========================= IMPLEMENTATION: HibernateAM ========

	@SuppressWarnings("unchecked")
	/**
	 * @param <ID>
	 * @param <T>
	 * @param entity
	 * @see net.ruready.eis.factory.imports.HibernateAssociationManager#removeFromAssociations(net.ruready.common.eis.entity.PersistentEntity)
	 */
	@Override
	public void removeFromAssociations(T entity)
	{
		// Remove all associations that Item belongs to
		super.removeFromAssociations(entity);

		// Remove tag from all associations Items through Item.tags
		Long id = entity.getId();
		if (id != null)
		{
			Session session = sessionFactory.getSession();
			Query query = session
					.createQuery("select item from Item item join item.tags tag where tag.id = ?");
			query.setLockMode("item", lockMode);
			query.setLockMode("tag", lockMode);
			query.setParameter(0, id);
			List<Item> items = query.list();
			for (Item item : items)
			{
				item.removeTag(id);
				session.saveOrUpdate(item);
			}

		}
	}

}
