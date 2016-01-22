/*****************************************************************************************
 * Source File: MainAction.java
 ****************************************************************************************/
package net.ruready.web.user.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

/**
 * Main page setup & view actions.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 5, 2007
 */
public class MainAction extends Action
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(MainAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= METHODS ===================================

	/**
	 * Returns the appropriate {@link ForwardAction} perspective view page
	 * reference for a given user access role.
	 * 
	 * @return the appropriate {@link ForwardAction} perspective view page
	 *         reference for a given user access role
	 */
	private static Map<RoleType, String> getPerspectiveMap()
	{
		Map<RoleType, String> map = new HashMap<RoleType, String>();
		map.put(RoleType.STUDENT, "studentMain");
		map.put(RoleType.TEACHER, "teacherMain");
		// TODO add EDITOR role to this map if we end up using perspectives
		map.put(RoleType.ADMIN, "adminMain");
		return map;
	}

	// ========================= ACTION METHODS ============================
	/**
	 * This action forwards to the system user main page.
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
		// logger.debug("Protocol: " + request.getProtocol());
		// logger.debug("Port: " + request.getServerPort());

		// ---------------------------------------
		// Read data from request & session
		// ---------------------------------------
		HttpSession session = request.getSession(false);
		User user = HttpRequestUtil.findUser(request);
		String newPerspectiveStr = request
				.getParameter(WebAppNames.REQUEST.PARAM.USER_PERSPECTIVE);

		// TODO: remove the perspective stuff

		RoleType requestedPerspective = null;
		try
		{
			requestedPerspective = RoleType.valueOf(newPerspectiveStr);
		}
		catch (final Exception e)
		{
			// TODO: should we really squash the exception?
		}

		RoleType currentPerspective = (RoleType) session
				.getAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_CURRENT_PERSPECTIVE);

		// ---------------------------------------
		// Business ParserInputFormat
		// ---------------------------------------
		// Default next perspective: current session perspective; if not
		// found, use user's default perspective.

		RoleType nextPerspective = (currentPerspective != null) ? currentPerspective
				: user.getDefaultPerspective();

		// Update to update perspective if requested through a request parameter
		if (hasPerspective(requestedPerspective))
		{
			nextPerspective = requestedPerspective;
		}

		logger.info("User perspective: default " + user.getDefaultPerspective()
				+ " current " + currentPerspective + " requested " + requestedPerspective
				+ " next " + nextPerspective);

		// ---------------------------------------
		// Attach results to response & forward
		// ---------------------------------------

		// Forward to different views based on user's "default
		// perspective" preference.
		if (hasPerspective(nextPerspective))
		{
			// Update the current perspective
			session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_CURRENT_PERSPECTIVE,
					nextPerspective);
		}
		else
		{
			// Not sure what happened; clear the current perspective
			session
					.removeAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_CURRENT_PERSPECTIVE);
		}
		return mapping.findForward(getPerspective(nextPerspective));
	}

	private Boolean hasPerspective(final RoleType role)
	{
		return (role != null && getPerspectiveMap().containsKey(role));
	}

	private String getPerspective(final RoleType role)
	{
		if (hasPerspective(role))
		{
			return getPerspectiveMap().get(role);
		}
		// Fallback: default main page
		else
		{
			return "studentMain";
		}
	}
}
