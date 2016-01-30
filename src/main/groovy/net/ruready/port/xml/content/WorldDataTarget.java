/*****************************************************************************************
 * Source File: FilterPackage.java
 ****************************************************************************************/
package net.ruready.port.xml.content;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.World;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.parser.xml.TagAttachment;
import net.ruready.common.parser.xml.XmlBodyElement;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.stack.AbstractStack;
import net.ruready.common.stack.GenericStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A target object constructed by the {@link WorldDataParser}. Contains a hierarchy of
 * {@link Item}s.
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
 * @version Oct 5, 2007
 */
public class WorldDataTarget implements AbstractStack<TagAttachment>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(WorldDataTarget.class);

	// ========================= FIELDS ====================================

	/**
	 * A world container for the set of countries to be read from the XML file.
	 */
	private final World world = new World();

	/**
	 * a placeholder that keeps track of consumption progress. The map entries' keys are
	 * XML tag names and their values are the corresponding bodies of the XML tags (read
	 * from the parsed XML file).
	 */
	private final AbstractStack<TagAttachment> stack = new GenericStack<TagAttachment>();

	/**
	 * Counts the number of errors/warnings encountered during parsing.
	 */
	private int numErrors = 0;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty school data target.
	 */
	public WorldDataTarget()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ContentPrinter printer = new ContentPrinter(world, stream, new AsciiItemPrinter());
		printer.print(true);
		String s = stream.toString();
		s = s + "Number of Errors: " + numErrors + CommonNames.MISC.NEW_LINE_CHAR;
		return s;
	}

	// ========================= METHODS ===================================

	/**
	 * Print this object's embedded stack.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String printStack()
	{
		return stack.toString();
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.Set#add(java.lang.Object)
	 */
	public void addCountry(Country e)
	{
		world.addChild(e);
	}

	/**
	 * Increments the numErrors property value.
	 */
	public void incrementNumErrors()
	{
		this.numErrors++;
	}

	// ========================= UTILITIES =================================

	/**
	 * Find a required and unique tag by name.
	 * 
	 * @param elements
	 *            list of elements
	 * @param tagName
	 *            tag name (unique identifier)
	 * @return the unique element with the specified tag name
	 */
	public static XmlBodyElement findElementByTagName(final List<TagAttachment> elements,
			final String tagName)
	{
		int index = elements.indexOf(new XmlBodyElement(tagName, null));
		if (index < 0)
		{
			throw new ApplicationException("Did not find required tag '" + tagName + "'");
		}
		return (XmlBodyElement) elements.get(index);
	}

	// ========================= DELEGATE METHODS ==========================

	/**
	 * @return
	 * @see net.ruready.common.parser.xml.AbstractStack#isEmpty()
	 */
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @param fence
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#elementsAbove(java.lang.Object)
	 */
	public List<TagAttachment> elementsAbove(TagAttachment fence)
	{
		return stack.elementsAbove(fence);
	}

	/**
	 * @param fence
	 * @param comparator
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#elementsAbove(java.lang.Object,
	 *      java.util.Comparator)
	 */
	public List<TagAttachment> elementsAbove(TagAttachment fence,
			Comparator<TagAttachment> comparator)
	{
		return stack.elementsAbove(fence, comparator);
	}

	/**
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#peek()
	 */
	public TagAttachment peek()
	{
		return stack.peek();
	}

	/**
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#pop()
	 */
	public TagAttachment pop()
	{
		return stack.pop();
	}

	/**
	 * @param item
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#push(java.lang.Object)
	 */
	public TagAttachment push(TagAttachment item)
	{
		return stack.push(item);
	}

	/**
	 * Return the world property.
	 * 
	 * @return the world
	 */
	public World getWorld()
	{
		return world;
	}

	/**
	 * Returns the numErrors property.
	 * 
	 * @return the numErrors
	 */
	public int getNumErrors()
	{
		return numErrors;
	}
}
