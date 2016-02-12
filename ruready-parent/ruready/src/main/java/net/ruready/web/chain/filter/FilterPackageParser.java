/*****************************************************************************************
 * Source File: MathML2MathParserConverter.java
 ****************************************************************************************/
package net.ruready.web.chain.filter;

import java.util.Hashtable;

import net.ruready.common.parser.xml.XmlParser;
import net.ruready.common.parser.xml.helper.DiscardHelper;
import net.ruready.common.parser.xml.helper.Helper;
import net.ruready.common.parser.xml.helper.StringElementHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A converter of an XML file to a filter package object.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public class FilterPackageParser extends XmlParser
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FilterPackageParser.class);

	// ========================= FIELDS ====================================

	// -------------------------------------------------
	// Required input
	// -------------------------------------------------

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize a filter package XML file parser.
	 */
	public FilterPackageParser()
	{
		super();
		this.target = new FilterPackage();
	}

	// ========================= IMPLEMENTATION: XmlParser =================

	/**
	 * @see net.ruready.parser.core.xml.XmlParser#helpers()
	 */
	@Override
	protected Hashtable<String, Helper> helpers()
	{
		Hashtable<String, Helper> helpers = new Hashtable<String, Helper>();

		helpers.put("web-app", new DiscardHelper("web-app"));

		helpers.put("filter", new FilterDefinitionHelper("filter"));
		helpers.put("filter-name", new StringElementHelper("filter-name"));
		helpers.put("filter-class", new FilterClassHelper("filter-class"));
		helpers.put("description", new StringElementHelper("description"));
		helpers.put("error-page", new StringElementHelper("error-page"));

		helpers.put("init-param", new InitParameterHelper("init-param"));
		helpers.put("param-name", new StringElementHelper("param-name"));
		helpers.put("param-value", new StringElementHelper("param-value"));

		helpers.put("filter-mapping", new FilterMappingHelper("filter-mapping"));
		helpers.put("filter-name", new StringElementHelper("filter-name"));
		helpers.put("url-pattern", new StringElementHelper("url-pattern"));

		return helpers;
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the target
	 */
	@Override
	public final FilterPackage getTarget()
	{
		return (FilterPackage) target;
	}

}
