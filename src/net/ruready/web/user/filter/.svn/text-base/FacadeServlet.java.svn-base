/*****************************************************************************************
 * Source File: FacadeServlet.java
 ****************************************************************************************/
package net.ruready.web.user.filter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A servlet processing a facade internal URL. This way we can forward from the login
 * action to the facade URL, which will invoke the authorization filter. This filter will
 * forward to the proper URL based on request parameters, so this servlet should never be
 * reached.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 2006-07
 *         Continuing Education , University of Utah . All copyrights reserved. U.S.
 *         Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Mar 19, 2007
 */
public class FacadeServlet extends HttpServlet
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
	private static final Log logger = LogFactory.getLog(FacadeServlet.class);

	// ========================= IMPLEMENTATION (HttpServlet) ==============

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) // throws
	// ServletException,
	// IOException
	{
		doPost(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	{
		logger.error("We shouldn't be here!!!");
	}

}
