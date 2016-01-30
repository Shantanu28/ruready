/*****************************************************************************************
 * Source File: UserField.java
 ****************************************************************************************/
package net.ruready.business.user.entity.property;

import java.util.Arrays;
import java.util.List;

import net.ruready.common.discrete.Gender;
import net.ruready.common.search.SearchField;
import net.ruready.common.search.SearchType;

/**
 * User fields to search by. The field's name is the property name in the User
 * class. This also serves an enumerated factory of search criteria.
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
public enum UserField implements SearchField
{
	// ========================= ENUMERATED CONSTANTS ======================

	EMAIL
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "email";
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
		@Override
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.ILIKE });
		}

	},

	GENDER
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "gender";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return Gender.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		@Override
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.EQ });
		}
	},

	AGE_GROUP
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "ageGroup";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return AgeGroup.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		@Override
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.EQ });
		}
	},

	ETHNICITY
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "ethnicity";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return Ethnicity.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		@Override
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.EQ });
		}
	},

	LANGUAGE
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "language";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return Language.class;
		}

		/**
		 * Return the list of permitted search types for this field. This might
		 * be a user-interface restriction rather than a database-driven
		 * restriction.
		 * 
		 * @return list of permitted search types for this field
		 * @see net.ruready.common.search.SearchField#getSearchTypes()
		 */
		@Override
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.EQ });
		}
	},

	FIRST_NAME
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "name.firstName";
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
		@Override
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.ILIKE });
		}
	},

	MIDDLE_INITIAL
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "name.middleInitial";
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
		@Override
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.ILIKE });
		}
	},

	LAST_NAME
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "name.lastName";
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
		@Override
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.ILIKE });
		}
	},

	STUDENT_IDENTIFIER
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "studentIdentifier";
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
		@Override
		public List<SearchType> getSearchTypes()
		{
			return Arrays.asList(new SearchType[]
			{ SearchType.ILIKE });
		}
	};

	// ========================= CONSTANTS =================================

	/**
	 * Return the list of permitted search types for text fields. This might
	 * be a user-interface restriction rather than a database-driven
	 * restriction.
	 * 
	 * @return list of permitted search types for text fields
	 * @see net.ruready.common.search.SearchField#getSearchTypes()
	 */
	public static List<SearchType> getTextSearchTypes()
	{
		return Arrays.asList(new SearchType[]
		{ SearchType.EQ, SearchType.ILIKE });
	}

	// ========================= FIELDS ====================================

	// ========================= METHODS ===================================

	/**
	 * Explicitly declare the {@link SearchField} methods in an enumerated type.
	 * 
	 * @return
	 * @see net.ruready.common.search.SearchField#getName()
	 */
	abstract public String getName();
	
	/**
	 * Explicitly declare the {@link SearchField} methods in an enumerated type.
	 * 
	 * @return list of permitted search types for this field
	 */
	abstract public List<SearchType> getSearchTypes();
}
