/*****************************************************************************************
 * Source File: StrutsFilter.java
 ****************************************************************************************/
package net.ruready.web.common.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

/**
 * In our Struts view layer, we use Servlet filters that also implement this method to
 * make them look like <code>ActionForm</code> performing validation.
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
 * @version Oct 2, 2007
 */
public interface ValidatedFilter
{
	/**
	 * Perform the entire filtering operation, as if this is a Struts Action Form.
	 * 
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 */
	ActionErrors validate(final ActionErrors errors, final HttpServletRequest request);
}
