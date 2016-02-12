package net.ruready.web.common.support;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
/**
 * Default Struts convenience methods
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
* @author Jeremy Lund
* @version Nov 27, 2007
*/
public class DefaultStrutsSupport
{
	public final Long getIdFromUrl(final HttpServletRequest request, final String idKey)
	{
		Long id = 0L;
		final String idAsString = request.getParameter(idKey);
		if (StringUtils.isEmpty(idAsString))
		{
			return id;
		}
		try
		{
			id = Long.valueOf(idAsString);
		}
		catch (final NumberFormatException nfe)
		{
			id = 0L;
		}
		return id;
	}
	
	public final void addMessages(final HttpServletRequest request, final String messageKey, final Object... args)
	{
		final ActionMessages actionMessages = new ActionMessages();
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, 
				new ActionMessage(messageKey,args));
		request.getSession(false).setAttribute(Globals.MESSAGE_KEY, actionMessages);
	}
	
	public final void addErrors(final HttpServletRequest request, final String messageKey, final Object... args)
	{
		final ActionMessages actionMessages = new ActionMessages();
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage(messageKey,args));
		request.getSession(false).setAttribute(Globals.ERROR_KEY, actionMessages);
	}
	
	public final WebApplicationContext getWebApplicationContext(final HttpServletRequest request)
	{
		return StrutsUtil.getWebApplicationContext(request);
	}
	
	/**
	 * Convenience method for retrieving the system user account
	 * @return the system user's user object
	 */
	protected final User getSystemUser()
	{
		return SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
	}
}
