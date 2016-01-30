/*****************************************************************************************
 * Source File: TreeNodeComparatorID.java
 ****************************************************************************************/
package net.ruready.business.common.tree.comparator;

import net.ruready.business.content.main.comparator.MainItemOrderComparator;
import net.ruready.common.discrete.Identifier;

/**
 * Serves as an <code>Item</code> enumerated factory that instantiates different types
 * of items based on their identifier type.
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
 * @version Aug 11, 2007
 */
public enum TreeNodeComparatorID implements Identifier
{
	// ========================= ENUMERATED TYPES ==========================

	NAME
	{
		@Override
		public TreeNodeComparator createComparator()
		{
			return new NameComparator();
		}
	},

	SERIAL_NO
	{
		@Override
		public TreeNodeComparator createComparator()
		{
			return new SNComparator();
		}
	},

	MAIN_ITEM_ORDER
	{
		@Override
		public TreeNodeComparator createComparator()
		{
			return new MainItemOrderComparator();
		}
	};

	// ========================= FIELDS ====================================

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * Return the object's type. This would normally be the object's
	 * <code>getClass().getSimpleName()</code>, but objects might be wrapped by
	 * Hibernate to return unexpected names. As a rule, the type string should not contain
	 * spaces and special characters.
	 */
	public String getType()
	{
		// The enumerated type's name satisfies all the type criteria, so use
		// it.
		return name();
	}

	// ========================= METHODS ===================================

	/**
	 * A factory method that creates a tree node comparator corresponding to this type.
	 * 
	 * @return tree node comparator
	 */
	abstract public TreeNodeComparator createComparator();
}
