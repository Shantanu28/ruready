/*****************************************************************************************
 * Source File: AsciiPrinter.java
 ****************************************************************************************/
package net.ruready.port.xml.content;

import java.io.OutputStream;

import net.ruready.business.content.item.entity.Item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Print a CM tree in XML format.
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
 * @version Aug 4, 2007
 */
public class XmlContentPrinter extends ContentPrinter
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(XmlContentPrinter.class);

	// ========================= FIELDS ====================================

	/**
	 * Target name space to print for the root XML element.
	 */
	final String targetNameSpace = "http://ruready.net";

	/**
	 * XML schema instance location path.
	 */
	final String xlmnsXsi = "http://www.w3.org/2001/XMLSchema-instance";

	/**
	 * XML schema file path.
	 */
	final String schemaLocation = "http://ruready.net/schemas/content/world_1_4.xsd";

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a tree printer with default formatting values.
	 * 
	 * @param tree
	 *            the tree to be printed
	 * @param stream
	 *            Output stream to print tree to
	 */
	public XmlContentPrinter(final Item tree, final OutputStream stream)
	{
		super(tree, stream, new XmlItemPrinter());
		this.setIndentPerDepth(2);

		// Fully initialize the attached XML printers
		XmlItemPrinter openTagPrinter = (XmlItemPrinter) itemPrinter;
		openTagPrinter.setXmlContentPrinter(this);
	}

	// ========================= IMPLEMENTATION: TreeVisitor ===============

	// ========================= METHODS ===================================

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Returns the targetNameSpace property.
	 * 
	 * @return the targetNameSpace
	 */
	public String getTargetNameSpace()
	{
		return targetNameSpace;
	}

	/**
	 * Returns the xlmnsXsi property.
	 * 
	 * @return the xlmnsXsi
	 */
	public String getXlmnsXsi()
	{
		return xlmnsXsi;
	}

	/**
	 * Returns the schemaLocation property.
	 * 
	 * @return the schemaLocation
	 */
	public String getSchemaLocation()
	{
		return schemaLocation;
	}

}
