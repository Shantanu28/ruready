/*****************************************************************************************
 * Source File: Names.java
 ****************************************************************************************/
package net.ruready.business.content.rl;

/**
 * This interface centralizes constants, labels and names used throughout the
 * content management component.<br>
 * All strings referring to attributes should of course be different from each
 * other.
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
 * @version Jul 25, 2007
 */
public interface ContentNames
{
	// ========================= CONSTANTS =================================

	// If no trailing slash is attached, file names are relative to the
	// "classes" directory of the application.

	// -----------------------------------------------------------------------
	// Unique item names that are initialized upon system start-up
	// -----------------------------------------------------------------------
	public interface UNIQUE_NAME
	{
		/**
		 * There is only one root node. This is its [unique] name.
		 */
		static final String ROOT = "Root";

		/**
		 * There is only one trash can. This is its [unique] name..
		 */
		static final String TRASH = "Trash";

		/**
		 * There is only one world node. This is its [unique] name.
		 */
		static final String WORLD = "World";

		/**
		 * There is only one tag cabinet. This is its [unique] name..
		 */
		static final String TAG_CABINET = "Tag Cabinet";
	}

	// -----------------------------------------------------------------------
	// Demo (base) item names that are initialized upon system start-up if no
	// other items of their type are found
	// -----------------------------------------------------------------------
	public interface BASE_NAME
	{
		/**
		 * A catalog item with this unique name is always created upon
		 * application start-up.
		 */
		static final String CATALOG = "My Catalog";

		/**
		 * An interest collection item with this unique name is always created
		 * upon application start-up.
		 */
		static final String INTEREST_COLLECTION = "Student Interests";

		/**
		 * Default city name for schools in old data files that don't have city
		 * data.
		 */
		static final String CITY = "_UNKNOWN_CITY_";
	}

	// -----------------------------------------------------------------------
	// Paths to dat afiles
	// -----------------------------------------------------------------------
	public interface FILE
	{
		/**
		 * World file to load upon site start-up.
		 */
		final String WORLD_DATA_FILE = "World.xml_1_4";

	}

	// -----------------------------------------------------------------------
	// Question items
	// -----------------------------------------------------------------------
	public interface QUESTION
	{
		/**
		 * Default precision used by the parser to compare expressions.
		 */
		static final int DEFAULT_PRECISION_DIGITS = 3;

		/**
		 * Default number of choices (MC format).
		 */
		public static final int DEFAULT_NUMBER_OF_CHOICES = 6;

		/**
		 * Number of levels (level is 1-based).
		 */
		public static final int HIGHEST_LEVEL = 4;
	}

	// -----------------------------------------------------------------------
	// Discrete types
	// -----------------------------------------------------------------------

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Types of audited actions
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface AUDIT_ACTION
	{
		static final String NAME = "auditAction";

		// Identifier types (short description of each enumerated value)
		static final String CREATED_TYPE = "N";

		static final String UPDATED_TYPE = "U";

		static final String DELETED_TYPE = "D";

		static final String MOVED_TYPE = "M";

		static final String COPIED_TYPE = "C";

		static final String CHILDREN_REORDERED = "O";
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Institution types
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface INSTITUTION_TYPE
	{
		static final String NAME = "institutionType";
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Institution sectors
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface SECTOR
	{
		static final String NAME = "sector";
	}
}
