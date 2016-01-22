/*****************************************************************************************
 * Source File: LoggedActionWithDispatcher.java
 ****************************************************************************************/

package net.ruready.web.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ActionDispatcher;
import org.apache.struts.actions.DispatchAction;

/**
 * This is the recommended base class for actions throughout the web layer. Uses
 * a decorator of {@link ActionDispatcher} that logs dispatch actions invoked.
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
 * @see https://issues.apache.org/struts/browse/STR-825
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 30, 2007
 */
public abstract class LoggedActionWithDispatcher extends Action
{
	// ========================= CONSTANTS =================================
	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private final static Log logger = LogFactory
			.getLog(LoggedActionWithDispatcher.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1
	// ...
	// Hmmm. Not clear how this is synchronized, but it's still the pattern
	// used in the Struts wiki:
	// http://wiki.apache.org/struts/EventActionDispatcher

	/**
	 * Instantiate event dispatcher. Delegate to the event dispatcher to call an
	 * appropriate event handler. By using a dispatcher, an action class does
	 * not need to extend DispatchAction.
	 */
	protected final LoggedActionDispatcher dispatcher;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Default constructor, probably used by the action Servlet to load this
	 * class whenever necessary.
	 */
	public LoggedActionWithDispatcher()
	{
		super();
		dispatcher = new LoggedActionDispatcher(this);
	}

	// ========================= METHODS ===================================

	/**
	 * The default method to be called if no method parameter is specified for
	 * the dispatcher.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	abstract public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	// ========================= METHODS ===================================

	// ========================= IMPLEMENTATION: Action ====================

	/**
	 * Use event dispatcher to call an appropriate event handler. By using a
	 * dispatcher an action class does not need to extend {@link DispatchAction}.
	 */
	@Override
	public final ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return dispatcher.execute(mapping, form, request, response);
	}

	// ========================= GETTERS & SETTERS =========================
}
