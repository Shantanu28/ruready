/*****************************************************************************************
 * Source File: XmlParser.java
 ****************************************************************************************/
package net.ruready.common.parser.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

import net.ruready.common.parser.xml.helper.Helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * An abstraction for parser that recognize XML input.
 * <p>
 * This class keeps a hash table whose keys are element names, such as "roast", and whose
 * values are subclasses of <code>Helper</code>, such as <code>RoastHelper</code>.
 * <p>
 * When an object of this class receives an event indicating that the parser has found a
 * new element, it looks up the element name in the hash table, to find the right helper
 * for the element. For example, on seeing the "roast" element, this class sets its
 * <code>helper</code> object to be an instance of <code>RoastHelper</code>. Then,
 * when this class receives characters from the parser, it passes the characters to the
 * current helper.
 * <p>
 * Helpers expect a target object, e.g. a <code>Vector</code> of <code>Coffee
 * </code>
 * objects as the target for helpers.
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
 * @author Steven J. Metsker
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 4, 2007
 */
public abstract class XmlParser extends DefaultHandler
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
	private static final Log logger = LogFactory.getLog(XmlParser.class);

	// ========================= FIELDS ====================================

	/**
	 * Helpers plugged into XML tags by tag name.
	 */
	private final Hashtable<String, Helper> helpers;

	/**
	 * The currently active helper (assembler).
	 */
	private Helper helper;

	/**
	 * Target object. Contains a manipulable stack.
	 */
	protected Object target;

	/**
	 * The parser we are wrapping.
	 */
	private final SAXParser parser;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize an XML parser.
	 */
	public XmlParser()
	{
		super();
		// Initialize handlers
		helpers = this.helpers();

		// Initialize the internally-used SAX parser
		parser = new SAXParser();
		parser.setContentHandler(this);
		parser.setErrorHandler(this);
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Returns the lookup table that tells which helper to use for which element.
	 */
	abstract protected Hashtable<String, Helper> helpers();

	// ========================= IMPLEMENTATION: DefaultHandler ============

	/**
	 * Receive notification of the start of an element.
	 * <p>
	 * If the <code>helpers</code> hash table has a key of for the given element name,
	 * then inform the helper that this element has appeared. Otherwise, throw an
	 * exception.
	 * </p>
	 * 
	 * @param uri
	 *            the uniform resource identifier, ignored
	 * @param local
	 *            the local name, ignored
	 * @param raw
	 *            the raw XML 1.0 name, which is the helper lookup key
	 * @param atts
	 *            the attributes attached to the element
	 * @throws SAXParseException
	 *             if a helper not available for this element
	 */
	@Override
	public final void startElement(final String uri, final String local,
			final String raw, final Attributes atts) throws SAXParseException
	{
		// logger.debug("startElement(" + raw + ")");
		helper = helpers.get(raw);
		if (helper == null)
		{
			throw new SAXParseException("Unrecognized element" + raw, null);
		}
		else
		{
			helper.startElement(atts, target);
		}
	}

	/**
	 * Receive notification of character data inside an element. If there is a helper
	 * ready to go, ask the helper to process these characters.
	 * 
	 * @param ch
	 *            The characters
	 * @param start
	 *            The start position in the character array
	 * @param len
	 *            The number of characters to use from the character array
	 */
	@Override
	public final void characters(char ch[], int start, int len)
	{
		// logger.debug("characters('" + (new String(ch, start, len)) + "' " +
		// start + "," + len + ")");
		if (helper != null)
		{
			helper.characters(new String(ch, start, len), target);
		}
	}

	/**
	 * Receive notification of the end of an element, which means that no helper should be
	 * active. Otherwise, throw an exception.
	 * 
	 * @param uri
	 *            the uniform resource identifier, ignored
	 * @param local
	 *            the local name, ignored
	 * @param raw
	 *            the raw XML 1.0 name, which is the helper lookup key
	 * @throws SAXParseException
	 *             if a helper not available for this element
	 */
	@Override
	public final void endElement(String uri, String local, String raw)
		throws SAXParseException
	{
		// logger.debug("endElement(" + raw + ")");
		helper = helpers.get(raw);
		if (helper == null)
		{
			throw new SAXParseException("Unrecognized element" + raw, null);
		}
		else
		{
			helper.endElement(target);
		}
		helper = null;
	}

	// ========================= METHODS ===================================

	/**
	 * Parser an input source.
	 * 
	 * @param arg0
	 *            input source to be parsed
	 * @throws SAXParseException
	 * @throws IOException
	 * @see org.apache.xerces.parsers.AbstractSAXParser#parse(org.xml.sax.InputSource)
	 */
	public final void parse(InputSource arg0) throws SAXException, IOException
	{
		parser.parse(arg0);
	}

	/**
	 * Parser an XML file.
	 * 
	 * @param arg0
	 *            URL to XML file to be parseds
	 * @throws SAXParseException
	 * @throws IOException
	 * @see org.apache.xerces.parsers.AbstractSAXParser#parse(java.lang.String)
	 */
	public final void parse(String arg0) throws SAXException, IOException
	{
		parser.parse(arg0);
	}

	/**
	 * Parser an XML input source.
	 * 
	 * @param arg0
	 *            XML input source to be parsed
	 * @throws XNIException
	 * @throws IOException
	 * @see org.apache.xerces.parsers.XMLParser#parse(org.apache.xerces.xni.parser.XMLInputSource)
	 */
	public final void parse(XMLInputSource arg0) throws XNIException, IOException
	{
		parser.parse(arg0);
	}

	/**
	 * Parser an XML string.
	 * 
	 * @param arg0
	 *            String to be parsed
	 * @throws SAXParseException
	 * @throws IOException
	 * @see org.apache.xerces.parsers.AbstractSAXParser#parse(java.lang.String)
	 */
	public final void parseString(String arg0) throws SAXException, IOException
	{

		parser.parse(new InputSource(new StringReader(arg0)));
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the target
	 */
	public Object getTarget()
	{
		return target;
	}
}
