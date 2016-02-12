/*****************************************************************************************
 * Source File: HitCounterFilter.java
 ****************************************************************************************/
package net.ruready.web.user.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.Counter;
import net.ruready.business.user.entity.audit.HitMessage;
import net.ruready.business.user.exports.AbstractGlobalPropertyBD;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.user.imports.StrutsGlobalPropertyBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;

/**
 * Decides whether to increment the hit counter for each request of the front page.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 27, 2007
 */
public class HitCounterFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HitCounterFilter.class);

	// ========================= FIELDS ====================================

	/**
	 * If true, saves hits to database.
	 */
	private boolean saveHits = false;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions. Sub-classes
	 * should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public HitCounterFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);
	}

	// ========================= IMPLEMENTATION: FilterAction ==============

	/**
	 * Filter a URL: decide whether to increment the hit counter; if yes, update the hit
	 * count in the request and in the database.
	 * 
	 * @param saContext
	 * @param action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @see net.ruready.web.chain.filter.FilterAction#doFilter(org.apache.struts.chain.contexts.ServletActionContext,
	 *      org.apache.struts.action.Action, org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doFilter(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		// ===========================================
		// Decide whether the process the hit
		// ===========================================
		// Process hit if this is a new session or if the request
		// does not contain an override header (e.g., a web crawler may
		// identify itself by setting that header).
		HttpSession session = request.getSession(true);
		boolean process = true;
		if (WebAppNames.ACTION.VALUE_FALSE.equals(request
				.getAttribute(WebAppNames.REQUEST.HEADER.PROCESS_HIT)))
		{
			// Might be a web crawler, ignore this hit
			process = false;
		}
		else if (WebAppNames.ACTION.VALUE_FALSE.equals(session
				.getAttribute(WebAppNames.SESSION.ATTRIBUTE.PROCESS_HIT)))
		{
			// Look for a session attribute indicating that the hit was already
			// processed in that session
			process = false;
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Process hit? " + process);
		}
		
		// ===========================================
		// Process the hit
		// ===========================================
		AbstractGlobalPropertyBD bdGlobalProperty = new StrutsGlobalPropertyBD(context);
		Counter hitCounter;
		if (process)
		{
			// Make sure hit won't be processed again in this session
			session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.PROCESS_HIT,
					WebAppNames.ACTION.VALUE_FALSE);

			hitCounter = bdGlobalProperty
					.increment(WebAppNames.GLOBAL_PROPERTY.HIT_COUNTER);

			// Add a hit message
			String host = request.getHeader("host");
			String userAgent = request.getHeader("user-agent");
			HitMessage hitMessage = new HitMessage(host, userAgent);
			if (logger.isDebugEnabled())
			{
				logger.debug(hitMessage);
			}
			if (saveHits)
			{
				bdGlobalProperty.saveHitMessage(hitMessage);
			}
		}
		else
		{
			hitCounter = bdGlobalProperty.load(WebAppNames.GLOBAL_PROPERTY.HIT_COUNTER);
		}

		// ===========================================
		// Attach objects to request and forward
		// ===========================================
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.HIT_COUNTER, hitCounter
				.getValue());

		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Hit counter";
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @param saveHits
	 *            the saveHits to set
	 */
	public void setSaveHits(boolean saveHits)
	{
		this.saveHits = saveHits;
	}

}
