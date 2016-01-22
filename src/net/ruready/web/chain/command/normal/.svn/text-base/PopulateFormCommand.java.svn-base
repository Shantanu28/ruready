/*****************************************************************************************
 * Source File: PopulateFormCommand.java
 ****************************************************************************************/

package net.ruready.web.chain.command.normal;

import net.ruready.web.common.form.PostPopulatableForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.chain.commands.servlet.PopulateActionForm;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ActionConfig;

/**
 * Adds custom post-population after the standard Struts form population, for forms that
 * implement {@link PostPopulatableForm}.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 5, 2007
 */
public class PopulateFormCommand extends PopulateActionForm
{
	// ========================= IMPLEEMNTATION: BaseActionCommand =========

	/**
	 * @param context
	 * @param actionConfig
	 * @param actionForm
	 * @throws Exception
	 * @see org.apache.struts.chain.commands.servlet.PopulateActionForm#populate(org.apache.struts.chain.contexts.ActionContext,
	 *      org.apache.struts.config.ActionConfig, org.apache.struts.action.ActionForm)
	 */
	@Override
	protected void populate(ActionContext context, ActionConfig actionConfig,
			ActionForm actionForm) throws Exception
	{
		// Standard form population
		super.populate(context, actionConfig, actionForm);

		// If form implements PostPopulatableForm, execute post-population
		if (PostPopulatableForm.class.isAssignableFrom(actionForm.getClass()))
		{
			ServletActionContext saContext = (ServletActionContext) context;
			((PostPopulatableForm) actionForm).postPopulate(saContext.getRequest());
		}
	}
}
