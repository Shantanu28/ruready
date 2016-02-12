/*****************************************************************************************
 * Source File: QuestionField.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity.property;

import java.util.Arrays;
import java.util.List;

import net.ruready.business.content.question.entity.state.QuestionStateID;
import net.ruready.common.search.SearchField;
import net.ruready.common.search.SearchType;

/**
 * Question fields to search by. The field's name is the property name in the
 * User class. This also serves an enumerated factory of search criteria.
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
public enum QuestionField implements SearchField
{
	// ========================= CONSTANTS =================================

	/**
	 * Note: must define a super-parent alias before using this field in a
	 * search.
	 * 
	 * @see ItemTypeUtil#addSuperParentAlias(net.ruready.common.search.SearchCriteria,
	 *      net.ruready.business.content.item.entity.ItemType,
	 *      net.ruready.business.content.item.entity.ItemType)
	 */
	SUPER_PARENT_ID
	{
		/**
		 * @see net.ruready.common.search.SearchField#getName()
		 */
		public String getName()
		{
			return "superParent.id";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return Long.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.IN });
		}
	},

	ID
	{
		/**
		 * @see net.ruready.common.search.SearchField#getName()
		 */
		public String getName()
		{
			return "id";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return Long.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.EQ });
		}
	},

	LEVEL
	{
		/**
		 * @see net.ruready.common.search.SearchField#getName()
		 */
		public String getName()
		{
			return "level";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return Integer.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		public List<SearchType> getSearchTypes()
		{
			return Arrays
					.asList(new SearchType[]
					{ SearchType.EQ, SearchType.GT, SearchType.GE, SearchType.LT,
							SearchType.LE });
		}
	},

	QUESTION_TYPE
	{
		/**
		 * @see net.ruready.common.search.SearchField#getName()
		 */
		/**
		 * @see net.ruready.common.search.SearchField#getName()
		 */
		public String getName()
		{
			return "questionType";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return QuestionType.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.EQ });
		}
	},

	FORMULATION
	{
		/**
		 * @see net.ruready.common.search.SearchField#getName()
		 */
		public String getName()
		{
			return "formulation";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return String.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.ILIKE });
		}
	},

	PARAMETRIC
	{
		/**
		 * @see net.ruready.common.search.SearchField#getName()
		 */
		public String getName()
		{
			return "parameters";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return String.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.IS_NULL, SearchType.IS_NOT_NULL });
		}
	},

	/**
	 * Is the question in deleted state. Note: must define the alias
	 * 
	 * <pre>
	 * searchCriteria.addAlias(&quot;question_state&quot;, &quot;state&quot;);
	 * </pre>
	 * 
	 * to use this field in a search.
	 * 
	 * @see ItemTypeUtil#addSuperParentAlias(net.ruready.common.search.SearchCriteria,
	 *      net.ruready.business.content.item.entity.ItemType,
	 *      net.ruready.business.content.item.entity.ItemType)
	 * 
	 */
	QUESTION_STATE_ID
	{
		/**
		 * @see net.ruready.common.search.SearchField#getName()
		 */
		public String getName()
		{
			return "question_state.stateID";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return QuestionStateID.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.EQ, SearchType.IN });
		}
	}

	// ========================= FIELDS ====================================

	// ========================= METHODS ===================================
}
