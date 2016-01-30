/*****************************************************************************************
 * Source File: DefaultItemVisitor.java
 ****************************************************************************************/
package net.ruready.eis.factory.imports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterion;
import net.ruready.common.search.SortCriterion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

/**
 * Converts a composite {@link SearchCriteria} object to a Hibernate
 * {@link Criteria} object.
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
 * @version Jul 31, 2007
 */
class HibernateCriteriaFactory
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateCriteriaFactory.class);

	// ========================= FIELDS ====================================

	/**
	 * A destination object of the principal searchable entity. Filled with
	 * {@link Criterion}s upon returning from this factory.
	 */
	private final Criteria destination;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a factory.
	 * 
	 * @param searchCriteria
	 *            input set of search criteria
	 * @param destination
	 *            an existing destination object of the principal searchable
	 *            entity. Filled with {@link Criterion}s upon returning from
	 *            the factory
	 */
	private HibernateCriteriaFactory(final SearchCriteria searchCriteria,
			final Criteria destination)
	{
		super();
		this.destination = destination;

		// Set aliases
		addAliases(searchCriteria);

		// Add in the criterion objects
		destination.add(generateHibernateCriterion(searchCriteria));

		// Set sorting options
		addSortOrder(searchCriteria);

		// Set result set limits
		setLimits(searchCriteria);
	}

	/**
	 * Convert a search criterion to a Hibernate Criterion.
	 * 
	 * @param searchCriteria
	 *            input set of search criteria
	 * @param destination
	 *            an existing destination object of the principal searchable
	 *            entity. Filled with {@link Criterion}s upon returning from
	 *            the method
	 */
	public static void createHibernateCriterion(final SearchCriteria searchCriteria,
			final Criteria destination)
	{
		new HibernateCriteriaFactory(searchCriteria, destination);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Apply alias definitions of the search criteria to the Hibernate criteria.
	 * 
	 * @param searchCriteria
	 *            input search criteria
	 */
	private void addAliases(final SearchCriteria searchCriteria)
	{
		for (Map.Entry<String, String> entry : searchCriteria.getAliases().entrySet())
		{
			// Note the argument order reversing between the search framework
			// and Hibernate
			destination.createAlias(entry.getValue(), entry.getKey());
		}
	}

	/**
	 * Convert a search criterion into a Hibernate search criterion (like an
	 * adapter, but simply generates a Hibernate criterion instance).
	 * 
	 * @param searchCriterion
	 *            search criterion
	 * @return corresponding Hibernate search criterion
	 */
	private Criterion generateHibernateCriterion(final SearchCriterion searchCriterion)
	{
		// Convert the children criteria first
		List<Criterion> args = new ArrayList<Criterion>();
		for (SearchCriterion childSearchCriterion : searchCriterion.getCriteria())
		{
			args.add(generateHibernateCriterion(childSearchCriterion));
		}

		// Now convert the parent
		return HibernateCriterionFactory.createHibernateCriterion(searchCriterion, args);
	}

	/**
	 * Apply sort orders of the search criteria to the Hibernate criteria.
	 * 
	 * @param searchCriteria
	 *            input search criteria
	 */
	private void addSortOrder(final SearchCriteria searchCriteria)
	{
		for (SortCriterion criterion : searchCriteria.getSortCriteria())
		{
			switch (criterion.getSortType())
			{
				case ASCENDING:
				{
					destination.addOrder(Order.asc(criterion.getFieldName()));
					break;
				}

				case DESCENDING:
				{
					destination.addOrder(Order.desc(criterion.getFieldName()));
					break;
				}

				default:
				{
					throw new IllegalArgumentException(criterion.getSortType()
							+ " is not a supported sort option.");
				}
			}
		}
	}

	/**
	 * Set result set limits on {@link #destination}.
	 * 
	 * @param searchCriteria
	 *            input search criteria
	 */
	private void setLimits(final SearchCriteria searchCriteria)
	{
		if (searchCriteria.getFirstResult() != null)
		{
			destination.setFirstResult(searchCriteria.getFirstResult());
		}
		if (searchCriteria.getMaxResults() != null)
		{
			destination.setMaxResults(searchCriteria.getMaxResults());
		}
	}
}
