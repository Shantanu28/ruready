package net.ruready.web.menugroup.entity;

import javax.servlet.http.HttpServletRequest;

import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.util.HttpRequestUtil;

/**
 * A useful struct that holds data of a menu change event (e.g. on-change).
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 7, 2007
 */
public class MenuEvent
{
	public boolean change = false;
	public long id = CommonNames.MISC.INVALID_VALUE_LONG;
	public String menu;

	/**
	 * Populate the menu event object from request parameters.
	 * 
	 * @param request
	 *            client's request object.
	 */
	public void populate(final HttpServletRequest request)
	{
		menu = request.getParameter("menu");
		id = HttpRequestUtil.getParameterAsLong(request, "value");
		change = HttpRequestUtil.getParameterAsBoolean(request, "change");
	}
}
