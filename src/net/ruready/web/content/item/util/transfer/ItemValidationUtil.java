/*****************************************************************************************
 * Source File: StrutsUtil.java
 ****************************************************************************************/
package net.ruready.web.content.item.util.transfer;

import net.ruready.business.content.item.entity.Item;
import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * Centralizes utilities related to custom validation rules on {@link Item}s during
 * editing/transferring.
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
 * @version Jul 29, 2007
 */
public class ItemValidationUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ItemValidationUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private ItemValidationUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * Validate and generate the child name under its destination item. Ideally,
	 * validations belong to the form bean implementation, but this is a custom validation
	 * that need run only for certain actions and certain conditions. Hence we place it in
	 * the action class.
	 * 
	 * @param itemDestination
	 *            prospective parent of child
	 * @param child
	 *            source item to be created under the prospective parent
	 * @param errors
	 *            validation errors (changed upon returning from the method)
	 * @return success code
	 */
	public static boolean validateChildCreation(final Item itemDestination,
			final Item child, final ActionErrors errors)
	{
		boolean success = true;
		Item attemptedNewChild = itemDestination.createChild(child.getIdentifier(), null, null);
		if (attemptedNewChild == null)
		{
			// Cannot add new child under the destination item
			success = false;
			errors.add("cannotaddchilddest", new ActionMessage(
					"error.content.cannotaddchilddest"));
		}
		return success;
	}

}
