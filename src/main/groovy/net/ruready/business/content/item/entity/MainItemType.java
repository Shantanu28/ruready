/*****************************************************************************************
 * Source File: MainItemType.java
 ****************************************************************************************/
package net.ruready.business.content.item.entity;

import net.ruready.business.content.main.entity.MainItem;

/**
 * Similar to {@link ItemType}. Serves as a {@link MainItem} useful properties'
 * enumerated factory. Is used in custom {@link MainItem} comparators.
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
 * @version Aug 4, 2007
 */
public enum MainItemType
{
	// ========================= ENUMERATED TYPES ==========================

	/**
	 * A fallback (a generic {@link MainItem} that isn't covered by any other
	 * case below).
	 */
	MAIN_ITEM
	{
		/**
		 * @see MainItemType#getOrder()
		 */
		@Override
		public int getOrder()
		{
			return 0;
		}
	},

	/**
	 * A root shouldn't appear on the same list with other main items. We give
	 * it a large value so that it appears first.
	 */
	ROOT
	{
		/**
		 * @see MainItemType#getOrder()
		 */
		@Override
		public int getOrder()
		{
			return 100000;
		}
	},

	/**
	 * Trash always appears at the end of the list of main items.
	 */
	DEFAULT_TRASH
	{
		/**
		 * @see MainItemType#getOrder()
		 */
		@Override
		public int getOrder()
		{
			return -1;
		}
	},

	/**
	 * Important items have value >= 10.
	 */
	CATALOG
	{
		/**
		 * @see MainItemType#getOrder()
		 */
		@Override
		public int getOrder()
		{
			return 13;
		}
	},

	/**
	 * Important items have value >= 10.
	 */
	WORLD
	{
		/**
		 * @see MainItemType#getOrder()
		 */
		@Override
		public int getOrder()
		{
			return 12;
		}
	},

	/**
	 * Important items have value >= 10.
	 */
	TAG_CABINET
	{
		/**
		 * @see MainItemType#getOrder()
		 */
		@Override
		public int getOrder()
		{
			return 11;
		}
	},

	/**
	 * less important items have value < 10.
	 */
	DOCUMENT_CABINET
	{
		/**
		 * @see MainItemType#getOrder()
		 */
		@Override
		public int getOrder()
		{
			return 1;
		}
	};

	// ========================= CONSTANTS ====================================

	// ========================= CONSTRUCTORS ==============================

	// ========================= STATIC METHODS ============================

	// ========================= METHODS ===================================

	/**
	 * Return a numerical value by which we sort (by descending value) main
	 * items under the root node.
	 * 
	 * @return the order number of this item within a {@link MainItem} list
	 */
	abstract public int getOrder();

	// ========================= GETTERS & SETTERS =========================

	// /**
	// * Return the numerical value of this itemType.
	// *
	// * @return numerical value of this itemType
	// */
	// public long getValue()
	// {
	// return value;
	// }

}
