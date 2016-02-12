/*****************************************************************************************
 * Source File: BaseHelper.java
 ****************************************************************************************/

package net.ruready.common.parser.xml.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

/**
 * A verbose helper decorator.
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
 * @version Oct 11, 2007
 */
public class VerboseHelper implements Helper
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(VerboseHelper.class);

	// ========================= FIELDS ====================================

	/**
	 * Name of tag we're processing.
	 */
	private final Helper helper;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a verbose helper decorator.
	 * 
	 * @param tagName
	 *            XML tag name
	 */
	public VerboseHelper(final Helper helper)
	{
		super();
		this.helper = helper;
	}

	// ========================= IMPLEMENTATION: Helper ====================

	/**
	 * @param atts
	 *            the attributes attached to the element
	 * @param target
	 * @see net.ruready.common.parser.xml.helper.Helper#startElement(java.lang.Object)
	 */
	public void startElement(Attributes atts, Object target)
	{
		logger.debug("<" + this.getTagName() + ">" + " startElement()");
		helper.startElement(atts, target);
	}

	/**
	 * @param s
	 * @param target
	 * @see net.ruready.common.parser.xml.helper.Helper#characters(java.lang.String,
	 *      java.lang.Object)
	 */
	public void characters(String s, Object target)
	{
		logger.debug("<" + this.getTagName() + ">" + " characters('" + s + "') ");
		helper.characters(s, target);
	}

	/**
	 * @param target
	 * @see net.ruready.common.parser.xml.helper.Helper#endElement(java.lang.Object)
	 */
	public void endElement(Object target)
	{
		logger.debug("<" + this.getTagName() + ">" + " endElement() ");
		helper.endElement(target);
	}

	// ========================= IMPLEMENTATION: TagAttachment =============

	/**
	 * @return
	 * @see net.ruready.common.parser.xml.TagAttachment#getTagName()
	 */
	public String getTagName()
	{
		return helper.getTagName();
	}

}
