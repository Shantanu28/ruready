/*****************************************************************************************
 * Source File: HibernateSearchEngine.java
 ****************************************************************************************/
package net.ruready.eis.factory.imports;

import java.io.Serializable;
import java.util.List;

import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.search.DefaultSearchEngine;
import net.ruready.common.search.SearchCriteria;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;

/**
 * An implementation of a search engine (by criteria) for Hibernate.
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
 * @version Jul 29, 2007
 */
public class HibernateSearchEngine<E extends PersistentEntity<ID>, ID extends Serializable>
		extends DefaultSearchEngine<E, ID>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateSearchEngine.class);

	// ========================= FIELDS ====================================

	/**
	 * Class of the searchable entity.
	 */
	private final Class<E> searchableClass;

	/**
	 * Produces entity association manager objects.
	 */
	private final ApplicationContext context;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a search engine that will use a resource locator and a DAO factory
	 * to search items in the database.
	 * 
	 * @param searchableClass
	 *            searchable class type
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            web application context
	 */
	public HibernateSearchEngine(final Class<E> searchableClass,
			final ResourceLocator resourceLocator, final ApplicationContext context)
	{
		super(resourceLocator);
		this.searchableClass = searchableClass;
		this.context = context;
	}

	// ========================= IMPLEMENTATION: DefaultSearchEngine<E> ====

	/**
	 * @param searchCriteria
	 * @return
	 * @see net.ruready.common.search.SearchEngine#search(net.ruready.common.search.SearchCriteria)
	 */
	public List<E> search(final SearchCriteria searchCriteria)
	{
		final HibernateDAO<E, ID> searchableDAO = (HibernateDAO<E, ID>) eisManager
				.getDAO(searchableClass, context);

		final Criteria criteria = searchableDAO.createCriteria();
		HibernateCriteriaFactory.createHibernateCriterion(searchCriteria, criteria);
		logger.debug("Search Criteria: " + searchCriteria);
		logger.debug("Hibernate Search Criteria: " + criteria);

		return searchableDAO.findByCriteria(criteria);
	}

	// ========================= PRIVATE METHODS ===========================

}
