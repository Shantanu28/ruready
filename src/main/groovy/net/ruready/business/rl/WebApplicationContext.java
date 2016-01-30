/*****************************************************************************************
 * Source File: WebApplicationContext.java
 ****************************************************************************************/

package net.ruready.business.rl;

import net.ruready.common.eis.manager.AbstractEISManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.ruready.rl.StandAloneApplicationContext;

/**
 * Web application context that is bound to the request. A custom pre-processing command
 * places it in the request scope the beginning of the web layer processing chain. It can
 * be used to set database locking flags, etc. This object assumes we are within a
 * single-thread context. It should be passed as a final instance field to the
 * constructors of all applicable business delegates, managers and DAOs.
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
 * @version Oct 3, 2007
 */

public class WebApplicationContext extends StandAloneApplicationContext
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
	private static final Log logger = LogFactory.getLog(WebApplicationContext.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize this context.
	 */
	public WebApplicationContext(final AbstractEISManager eisFactory)
	{
		super(eisFactory);
		if (logger.isDebugEnabled())
		{
			logger.debug("Initialized new context");
		}
	}
	// ========================= IMPLEMENTATION: Resource ==================

	// ========================= IMPLEMENTATION: AbstractAssociationManager

	// ========================= GETTERS & SETTERS =========================

}
