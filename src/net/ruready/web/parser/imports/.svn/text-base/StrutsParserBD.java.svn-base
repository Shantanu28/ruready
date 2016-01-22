/*****************************************************************************************
 * Source File: StrutsParserBD.java
 ****************************************************************************************/
package net.ruready.web.parser.imports;

import net.ruready.parser.service.exports.DefaultParserBD;
import net.ruready.parser.service.manager.DefaultParserManager;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parser demo and other functions BD with a Struts resource locator.
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
public class StrutsParserBD extends DefaultParserBD
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StrutsParserBD.class);

	// ========================= FIELDS ====================================

	// The user requesting the operations
	// Note: The parser demo does not require authentication at this point.
	// private final User user;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a business delegate for this user.
	 */
	public StrutsParserBD()
	{
		super(new DefaultParserManager(WebAppResourceLocator.getInstance()),
				WebAppResourceLocator.getInstance());
	}
	// ========================= IMPLEMENTATION: DefaultParserBD ====

	// ========================= overridden METHODS: DefaultWorldDemoBD ==

	// ========================= GETTERS & SETTERS ==========================

}
