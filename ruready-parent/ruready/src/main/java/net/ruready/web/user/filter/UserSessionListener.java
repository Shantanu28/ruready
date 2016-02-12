/*****************************************************************************************
 * Source File: UserSessionListener.java
 ****************************************************************************************/
package net.ruready.web.user.filter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.web.common.imports.WebAppResourceLocator;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.user.imports.StrutsUserBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Contains functions that are run on init and shut down of user sessions in the web
 * application. We assume that this listener is called after the Struts action servlet has
 * been initialized.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 24, 2007
 */
public class UserSessionListener implements HttpSessionListener
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(UserSessionListener.class);

	// ========================= IMPLEMENTATION: HttpSessionListener =======

	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event)
	{
		HttpSession session = event.getSession();
		logger.debug("Session @" + session.hashCode() + " ID " + session.getId()
				+ " created");
	}

	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event)
	{
		// Create a context for the scope of this listener's method; session+transaction
		// should be ready to work at this point.
		ResourceLocator rl = WebAppResourceLocator.getInstance();
		WebApplicationContext context = new WebApplicationContext(rl.getDAOFactory());

		// Find user a user ID exists in the session
		HttpSession session = event.getSession();
		Long userId = (Long) session.getAttribute(WebAppNames.SESSION.ATTRIBUTE.USER_ID);

		// MOD JLUND 11/06/2007 - if userId is NULL don't attempt to log the user out
		if (userId != null)
		{
			final AbstractUserBD bdUser = new StrutsUserBD(context);
			final User user = bdUser.findById(userId);
			logger.debug("Session @" + session.hashCode() + " ID " + session.getId()
					+ " destroyed, user "
					+ ((user == null) ? CommonNames.MISC.NOT_APPLICABLE : user.getEmail()));
	
			// Log the user out and update the user counter
			if (user != null)
			{
				bdUser.logout(user);
			}
		}
		// MOD JLUND 11/06/2007
	}

}
