/*******************************************************
 * Source File: PreventCachingTag.java
 *******************************************************/
package net.ruready.web.common.tag;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
 *          Academic Outreach and Continuing Education (AOCE)
 *          1901 East South Campus Dr., Room 2197-E
 *          University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
 *          AOCE, Room 2197-E, University of Utah
 *            
 * (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * 
 * @version Mar 16, 2006
 * 
 * A custom tag that adds response headers to prevent
 * browser caching. Should fit HTTP 1.0 and HTTP 1.1
 * protocols.
 */
public class PreventCachingTag extends TagSupport
{
	//################## CONSTANTS #######################
	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1;

	// For logging printouts

	//################## METHODS #######################
	@Override
	public int doEndTag() throws JspException
	{
		try
		{
			// Hopefully prevents all caching issues
			HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
			response.setDateHeader("Expires ", 0);
			response.setHeader("Pragma ", "no-cache ");
			response.setHeader("Cache-Control ", "no-store ");
			response.setDateHeader("max-age ", 0);
			response.setDateHeader("Expires ", 0);
		}
		catch (Exception e)
		{
		}
		return super.doEndTag();
	}
}
