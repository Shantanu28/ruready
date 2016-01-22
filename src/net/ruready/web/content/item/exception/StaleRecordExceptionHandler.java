/*****************************************************************************************
 * Source File: StaleRecordExceptionHandler.java
 ****************************************************************************************/
package net.ruready.web.content.item.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.common.eis.exception.StaleRecordException;
import net.ruready.web.common.exception.SafeExceptionHandler;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

/**
 * This handler is invoked on throwing a {@link StaleRecordException}. This indicates
 * that a version conflict has arisen during item editing. We attach the current database
 * version as well as the currently edited version to the request. The item might be a
 * child item of the main item being edited. In this case, we use a different request
 * attribute to indicate that this is a child. Note: the exception handled here <i>must</i>
 * be of {@link StaleRecordException} type.
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
public class StaleRecordExceptionHandler extends SafeExceptionHandler
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(StaleRecordExceptionHandler.class);

	// ========================= IMPLEMENTATION: SafeExceptionHandler =======

	/**
	 * @param e
	 * @param ex
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param mappingName
	 * @param errors
	 * @see net.ruready.web.common.exception.SafeExceptionHandler#handle(java.lang.Exception,
	 *      org.apache.struts.config.ExceptionConfig,
	 *      org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 *      java.lang.String, org.apache.struts.action.ActionErrors)
	 */
	@Override
	protected void handle(Exception e, ExceptionConfig ex, ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response,
			String mappingName, ActionErrors errors) throws Exception
	{
		// ---------------------------------------
		// Read data from the exception & request
		// ---------------------------------------
		// The FindItemFilter should have attached an item
		// to the request by now
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		Item storedItem = bdItem.read(item.getItemClass(), item.getId());
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.STORED_ITEM, storedItem);
	}
}
