/*****************************************************************************************
 * Source File: PlotTreeAction.java
 ****************************************************************************************/
package net.ruready.web.parser.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Generates a PNG plot of a syntax tree.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------<br>
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 16, 2007
 */
public class PlotTreeAction extends Action
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(PlotTreeAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= METHODS ===================================

	// ========================= ACTION METHODS ============================

	/**
	 * Resolve editing conflicts using database-stored copy. Like a cancel
	 * operation, but goes back to the edit view.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return ActionForward forward action to forward the request to
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("execute()");

		// -------------------------------------------
		// Read data from request
		// -------------------------------------------
		String attributeName = request.getParameter(WebAppNames.REQUEST.PARAM.NAME);

		if (attributeName == null)
		{
			logger.error("No attribute name");
			return null;
		}

		// The data should have already been prepared by another action
		// and placed in the session
		HttpSession session = request.getSession(false);
		BufferedImage img = (BufferedImage) session.getAttribute(attributeName);

		// -------------------------------------------
		// Prepare a response
		// -------------------------------------------
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", bos);
			byte[] imageData = bos.toByteArray();
			// If it is null, then there is nothing more we can do
			if (imageData == null)
			{
				return null;
			}

			response.setContentType("image/png");
			response.setContentLength(imageData.length);
			ServletOutputStream sout = response.getOutputStream();
			sout.write(imageData);

			// Dispose of the session image attribute
			session.removeAttribute(attributeName);
		}
		catch (Exception e)
		{
		}

		return null;
	}

	// ========================= PRIVATE METHODS ===========================
}
