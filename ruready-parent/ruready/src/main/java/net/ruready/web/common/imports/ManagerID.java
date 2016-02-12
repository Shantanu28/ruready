/*****************************************************************************************
 * Source File: ManagerID.java
 ****************************************************************************************/
package net.ruready.web.common.imports;

import net.ruready.common.discrete.Identifier;
import net.ruready.web.common.rl.WebAppNames;

/**
 * Possible manager (business service) types (manager identifiers). Provides lookup names
 * for the resource locator.
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
 * @version Jul 25, 2007
 */
public enum ManagerID implements Identifier
{
	// ========================= ENUMERATED TYPES ==========================

	CATALOG_EDIT_ITEM
	{
		public String getType()
		{
			return WebAppNames.RESOURCE_LOCATOR.MANAGER.CATALOG_EDIT_ITEM;
		}
	},

	CATALOG_ROOT
	{
		public String getType()
		{
			return WebAppNames.RESOURCE_LOCATOR.MANAGER.CATALOG_ROOT;
		}
	},

	TRASH_TRASH
	{
		public String getType()
		{
			return WebAppNames.RESOURCE_LOCATOR.MANAGER.TRASH_TRASH;
		}
	};

	// ========================= FIELDS ====================================
	
	// ========================= METHODS ===================================
}
