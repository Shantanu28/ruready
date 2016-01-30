/*****************************************************************************************
 * Source File: AuditAction.java
 ****************************************************************************************/
package net.ruready.business.content.item.entity.audit;

import net.ruready.business.common.audit.entity.AbstractAction;
import net.ruready.business.content.rl.ContentNames;

/**
 * Possible audit actions on an entity. They are part of {@link AuditMessage} logging and
 * relate to results of {@link TransferAction} actions.
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
public enum AuditAction implements AbstractAction, Comparable<AuditAction>
{
	// ========================= ENUMERATED TYPES ==========================

	CREATED
	{

		public String getType()
		{
			return ContentNames.AUDIT_ACTION.CREATED_TYPE;
		}
	},

	UPDATED
	{

		public String getType()
		{
			return ContentNames.AUDIT_ACTION.UPDATED_TYPE;
		}
	},

	DELETED
	{

		public String getType()
		{
			return ContentNames.AUDIT_ACTION.DELETED_TYPE;
		}
	},

	MOVED
	{

		public String getType()
		{
			return ContentNames.AUDIT_ACTION.MOVED_TYPE;
		}
	},

	COPIED
	{

		public String getType()
		{
			return ContentNames.AUDIT_ACTION.COPIED_TYPE;
		}
	},
	
	CHILDREN_REORDERED
	{

		public String getType()
		{
			return ContentNames.AUDIT_ACTION.CHILDREN_REORDERED;
		}
	},


	// ========================= FIELDS ====================================

	// ========================= METHODS ===================================

}
