/*****************************************************************************************
 * Source File: Helper.java
 ****************************************************************************************/
package net.ruready.common.parser.xml.helper;

import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

/**
 * This class is the top of a hierarchy of classes that help to build a coffee object,
 * based on recognizing elements of an XML markup file.
 * <p>
 * An application that uses a SAX parser can pass control to helpers upon receiving SAX
 * events.
 * <p>
 * Oren: added registration with element end event. More flexible for some applications.
 * All methods are implemented as empty hooks -- this is a stub.
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
 * @author Steven J. Metsker Original: Copyright (c) 2000 Steven J. Metsker. All Rights
 *         Reserved. Steve Metsker makes no representations or warranties about the
 *         fitness of this software for any particular purpose, including the implied
 *         warranty of merchantability.
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 8, 2007
 */
public class GriddyHelper implements Helper
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
	private static final Log logger = LogFactory.getLog(GriddyHelper.class);

	// ========================= FIELDS ====================================

	/**
	 * A buffer of characters accumulated by multiple {@link #characters(String, Object)}
	 * calls during the same tag scope.
	 */
	protected StringBuffer charactersBuffer;

	/**
	 * Name of tag we're processing.
	 */
	protected final String tagName;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a helper that applies a function or operation to its operands.
	 * 
	 * @param tagName
	 *            final String tagName
	 */
	public GriddyHelper(final String tagName)
	{
		super();
		this.tagName = tagName;
	}

	// ========================= METHODS ===================================

	/**
	 * An application that uses a SAX parser should call this method upon receiving a
	 * <code>startElement</code> event.
	 * 
	 * @param atts
	 *            the attributes attached to the element
	 * @param target
	 *            target object to be built/updated
	 */
	public final void startElement(Attributes atts, Object target)
	{
		charactersBuffer = TextUtil.emptyStringBuffer();
		processStartElement(atts, target);
	}

	/**
	 * An application that uses a SAX parser should call this method upon receiving a
	 * <code>characters</code> event.
	 * 
	 * @param s
	 *            tag content string
	 * @param target
	 *            target object to be built/updated
	 */
	public final void characters(String s, Object target)
	{
		charactersBuffer.append(s);
		processCharacters(s, target);
	}

	/**
	 * An application that uses a SAX parser should call this method upon receiving a
	 * <code>endElement</code> event.
	 * 
	 * @param target
	 *            target object to be built/updated
	 */
	public final void endElement(Object target)
	{
		processEndElement(target);
	}

	// ========================= IMPLEMENTATION: TagAttachment =============

	/**
	 * @return
	 * @see net.ruready.common.parser.xml.TagAttachment#getTagName()
	 */
	public final String getTagName()
	{
		return tagName;
	}

	// ========================= HOOKS =====================================

	/**
	 * An application that uses a SAX parser should call this method upon receiving a
	 * <code>startElement</code> event.
	 * 
	 * @param atts
	 *            the attributes attached to the element
	 * @param target
	 *            target object to be built/updated
	 */
	protected void processStartElement(Attributes atts, Object target)
	{

	}

	/**
	 * An application that uses a SAX parser should call this method upon receiving a
	 * <code>characters</code> event.
	 * 
	 * @param s
	 *            tag content string
	 * @param target
	 *            target object to be built/updated
	 */
	protected void processCharacters(String s, Object target)
	{

	}

	/**
	 * An application that uses a SAX parser should call this method upon receiving a
	 * <code>endElement</code> event.
	 * 
	 * @param target
	 *            target object to be built/updated
	 */
	protected void processEndElement(Object target)
	{

	}
}
