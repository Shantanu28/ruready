/*****************************************************************************************
 * Source File: Logic.java
 ****************************************************************************************/
package net.ruready.common.search;

/**
 * Search criteria global arbitration logic. They apply at the root of the
 * criterion hierarchy, e.g. match all criteria or match any of the criteria.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 3, 2007
 */
public enum Logic
{
	// ========================= CONSTANTS =================================

	/**
	 * Match ALL of the criteria.
	 */
	AND
	{
		/**
		 * Return the corresponding criterion to this logic condition.
		 * 
		 * @return the corresponding criterion to this logic condition
		 * @see net.ruready.common.search.Logic#createCriterion()
		 */
		@Override
		public SearchCriterion createCriterion()
		{
			return SearchCriterionFactory.createJunctionCriterion(SearchType.CONJUNCTION);
		}

	},

	/**
	 * Match ANY of the criteria.
	 */
	OR
	{
		/**
		 * Return the corresponding criterion to this logic condition.
		 * 
		 * @return the corresponding criterion to this logic condition
		 * @see net.ruready.common.search.Logic#createCriterion()
		 */
		@Override
		public SearchCriterion createCriterion()
		{
			return SearchCriterionFactory.createJunctionCriterion(SearchType.DISJUNCTION);
		}
	};

	// ========================= FIELDS ====================================

	// ========================= METHODS ===================================

	/**
	 * Return the corresponding criterion to this logic condition.
	 * 
	 * @return the corresponding criterion to this logic condition
	 */
	public abstract SearchCriterion createCriterion();
}
