/*****************************************************************************************
 * Source File: CustomTypeFilter.java
 ****************************************************************************************/
package net.ruready.web.content.item.filter;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.main.exception.InvalidItemTypeException;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.user.filter.AuthorizationFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;

/**
 * Makes sure that the requested path ends with the item type in the request. This ensures
 * that custom pages are accessed by and only by the corresponding item types. Must be
 * called after {@link FindItemFilter}.
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
public class CustomTypeFilter extends AuthorizationFilter
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CustomTypeFilter.class);

	// ========================= FIELDS ====================================

	/**
	 * The same parameters as for the <content:customType> custom tag: prefix of path to
	 * look for to determine whether this item type has a custom JSP.
	 */
	private String prefix;

	/**
	 * The same parameters as for the <content:customType> custom tag: postfix of path to
	 * look for to determine whether this item type has a custom JSP.
	 */
	private String postfix;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions. Sub-classes
	 * should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public CustomTypeFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);

		// postfix is required; perform check
		this.checkRequiredInitParam("postfix", postfix);

		logger.info("initialized:" + " prefix '" + prefix + "'" + " postfix '" + postfix
				+ "'");
	}

	// ========================= IMPLEMENTATION: FilterAction ==============

	/**
	 * Add a child item type drop-down menu data to the request.
	 * 
	 * @param saContext
	 * @param action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @see net.ruready.web.user.filter.AuthorizationFilter#doFilter(org.apache.struts.chain.contexts.ServletActionContext,
	 *      org.apache.struts.action.Action, org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doFilter(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		// ---------------------------------------
		// Read data from request & parameters
		// ---------------------------------------
		// WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);

		// ---------------------------------------
		// Read data from request
		// ---------------------------------------
		// The FindItemFilter should have attached an item
		// to the request by now
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------
		// Find if there is a custom JSP for this item type
		String itemType = item.getType();
		String fileName = prefix + File.separator + itemType + File.separator + postfix;
		File file = new java.io.File(saContext.getActionServlet().getServletContext()
				.getRealPath(fileName));
		String requestedPath = request.getRequestURI().toString();

		// if ((file.exists() && !requestedPath.endsWith(itemType + ".do"))
		// || (!file.exists() && !requestedPath.endsWith(WebAppNames.ACTION.DEFAULT
		// + ".do")))

		// Use a more forgiving URL pattern: just need to contain the string "itemType".
		// Allows editing sub-actions to go through this filter.
		if ((file.exists() && (requestedPath.indexOf(itemType) < 0))
				|| (!file.exists() && (requestedPath.indexOf(WebAppNames.ACTION.DEFAULT) < 0)))
		{
			logger.debug("requested path " + requestedPath + " item type " + itemType
					+ " DENIED");
			throw new InvalidItemTypeException("Requested path " + requestedPath
					+ " does not match the item type " + itemType
					+ " found in the request", item.getId());
			// return mapping.findForward(errorPage);
		}
		else
		{
			logger.debug("requested path " + requestedPath + " item type " + itemType
					+ " GRANTED");
			return null;
		}
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	@Override
	public String getDescription()
	{
		return "Custom type URL pattern check";
	}

	/**
	 * @see net.ruready.web.chain.filter.FilterAction#reset()
	 */
	@Override
	protected void reset()
	{
		super.reset();
		prefix = WebAppNames.JSP.ROOT_DIR;
		roles = "ALL"; // bogus
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Sets a new prefix property value.
	 * 
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	/**
	 * Sets a new postfix property value.
	 * 
	 * @param postfix
	 *            the postfix to set
	 */
	public void setPostfix(String postfix)
	{
		this.postfix = postfix;
	}

}
