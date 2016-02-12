/*****************************************************************************************
 * Source File: AddChildFilter.java
 ****************************************************************************************/
package net.ruready.web.content.item.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.common.text.TextUtil;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionListSource;
import net.ruready.web.select.exports.OptionListSourceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.util.MessageResources;

/**
 * Adds data for a drop-down menu that selects which child will be created under the
 * currently edited item.
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
 * @version Aug 8, 2007
 */
public class AddChildFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AddChildFilter.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions. Sub-classes
	 * should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public AddChildFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);
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
	 * @see net.ruready.web.chain.filter.FilterAction#doFilter(org.apache.struts.chain.contexts.ServletActionContext,
	 *      org.apache.struts.action.Action, org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doFilter(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		// -------------------------------------------
		// Read data from request
		// -------------------------------------------
		// WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		// The FindItemFilter should have attached an item to the request by now
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// Use the OLS framework to construct a Option option list
		List<ItemType> allowedTypes = item.getIdentifier().getChildren();
		if (allowedTypes != null)
		{
			OptionListSource enumListOptions = OptionListSourceFactory
					.getOptionListSource(WebAppNames.OLS.TYPE_ENUM_LIST, allowedTypes);

			// If there is only one possible child type, do not add an empty
			// selection option. Otherwise, do. If there are no allowed types, don't
			// attach anything to the request.
			List<Option> VLOptions = (allowedTypes.size() == 1) ? enumListOptions
					.getOptions(true) : enumListOptions.getOptions(false);

			// Modify the selection labels for internationalization
			MessageResources messageResources = this.getResources(request);
			for (Option vl : VLOptions)
			{
				if (TextUtil.isEmptyTrimmedString(vl.getValue()))
				{
					// Empty selection key
					vl.setLabel(messageResources
							.getMessage(WebAppNames.KEY.MESSAGE.OLS_NO_SELECTION_LABEL));
				}
				else
				{
					vl.setLabel(messageResources
							.getMessage(WebAppNames.RESOURCE_LOCATOR.MODULE.CONTENT + "."
									+ vl.getValue() + ".label"));
				}
			}

			// -------------------------------------------
			// Attach results to request
			// -------------------------------------------
			// Attach option list to request
			request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM_CHILD_TYPES,
					VLOptions);
		}

		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Add child Item menu data";
	}
}
