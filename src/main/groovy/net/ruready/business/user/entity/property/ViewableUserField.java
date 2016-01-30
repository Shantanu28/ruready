package net.ruready.business.user.entity.property;

import java.sql.Timestamp;
import java.util.Date;

import net.ruready.common.discrete.Gender;
import net.ruready.common.search.NamedField;
import net.ruready.common.search.SearchField;

/**
 * User fields to view and sort by.
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
 * @author Jeremy Lund
 * @version Oct 26, 2007
 */
public enum ViewableUserField implements NamedField
{
	// ========================= CONSTANTS =================================

	ID
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
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
	},

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
	},

	USER_ROLE
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "highestRole";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return RoleType.class;
		}
	},

	REGISTRATION_DATE
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "createdDate";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return Date.class;
		}
	},

	LAST_LOGIN_DATE
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "lastLoggedInDate";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return Timestamp.class;
		}
	},

	IS_LOGGED_IN
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "isLoggedin";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return Boolean.class;
		}
	},

	USER_STATUS
	{
		/**
		 * @return
		 * @see net.ruready.business.user.entity.property.ViewableUserField#getName()
		 */
		@Override
		public String getName()
		{
			return "userStatus";
		}

		/**
		 * Returns the search field's type.
		 * 
		 * @return the search field's type
		 * @see net.ruready.common.search.SearchField#getType()
		 */
		public Class<?> getType()
		{
			return UserStatus.class;
		}
	};

	// ========================= METHODS ===================================

	/**
	 * Explicitly declare the {@link SearchField} methods in an enumerated type.
	 * 
	 * @return
	 * @see net.ruready.common.search.SortField#getName()
	 */
	abstract public String getName();
}
