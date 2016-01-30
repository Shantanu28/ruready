/*****************************************************************************************
 * Source File: DefaultItemVisitor.java
 ****************************************************************************************/
package net.ruready.business.content.item.util;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.ItemVisitor;

/**
 * Prints an item to a string buffer.
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
 * @version Jul 31, 2007
 */
public interface ItemPrinter extends ItemVisitor
{

	/**
	 * Sets a new item property value.
	 * 
	 * @param item
	 *            the item to set
	 */
	void setItem(Item item);

	/**
	 * Sets a new number property value.
	 * 
	 * @param number
	 *            the number to set
	 */
	void setNumber(int number);

	/**
	 * Sets a new indent property value.
	 * 
	 * @param indent
	 *            the indent to set
	 */
	void setIndent(int indent);

	/**
	 * Sets a new next-tree-depth indent property value.
	 * 
	 * @param indent
	 *            the indent to set
	 */
	void setIndentNextDepth(int indent);

	/**
	 * Generate an indent string.
	 * 
	 * @param indent
	 *            indent amount
	 * @return indent string
	 */
	StringBuffer getIndentString(final int indent);

	/**
	 * Print an item's header.
	 * 
	 * @param source
	 *            item to print
	 */
	StringBuffer getItemHeader(final Item source);

	/**
	 * Print an item's header.
	 * 
	 * @param source
	 *            item to print
	 */
	StringBuffer getItemFooter(final Item source);

	/**
	 * Print a field.
	 * 
	 * @param name
	 *            field name
	 * @param value
	 *            field value
	 * @return field printout
	 */
	StringBuffer getFieldString(final String name, final Object value);

	/**
	 * Returns a string buffer representation of the object. In general, the
	 * <code>toString</code> method returns a string buffer that "textually represents"
	 * this object. The result should be a concise but informative representation that is
	 * easy for a person to read. Used for pre-traversal printouts of nodes.
	 * 
	 * @see java.lang.Object#toString()
	 */
	StringBuffer toStringBufferPre();

	/**
	 * Returns a string buffer representation of the object. In general, the
	 * <code>toString</code> method returns a string buffer that "textually represents"
	 * this object. The result should be a concise but informative representation that is
	 * easy for a person to read. Used for pre-traversal printouts of nodes.
	 * 
	 * @see java.lang.Object#toString()
	 */
	StringBuffer toStringBufferPost();
}
