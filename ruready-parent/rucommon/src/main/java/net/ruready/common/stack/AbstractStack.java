/*****************************************************************************************
 * Source File: AbstractStack.java
 ****************************************************************************************/
package net.ruready.common.stack;

import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * Consists of the methods of {@link Stack} methods without the {@link Vector} interface
 * methods, with some additional useful pop/push methods.
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
public interface AbstractStack<E>
{
	// ========================= CONSTANTS =================================

	// ========================= METHODS ===================================

	/**
	 * Pop an element from the stack
	 * 
	 * @return the top element on the stack
	 * @see java.util.Stack#pop()
	 */
	E pop();

	/**
	 * @param item
	 * @return
	 * @see java.util.Stack#push(java.lang.Object)
	 */
	E push(E item);

	/**
	 * @return
	 * @see java.util.Vector#isEmpty()
	 */
	boolean isEmpty();

	/**
	 * @return
	 * @see java.util.Stack#peek()
	 */
	E peek();

	/**
	 * Returns a list of the elements on the stack that appear before a specified fence.
	 * <p>
	 * Sometimes a parser will recognize a list from within a pair of parentheses or
	 * brackets. The parser can mark the beginning of the list with a fence, and then
	 * retrieve all the items that come after the fence with this method.
	 * 
	 * @param fence
	 *            the fence, a marker of where to stop popping the stack
	 * @return the list the elements above the specified fence
	 */
	List<E> elementsAbove(E fence);

	/**
	 * Returns a list of the elements on the stack that appear before a specified fence.
	 * <p>
	 * Sometimes a parser will recognize a list from within a pair of parentheses or
	 * brackets. The parser can mark the beginning of the list with a fence, and then
	 * retrieve all the items that come after the fence with this method.
	 * 
	 * @param fence
	 *            the fence, a marker of where to stop popping the stack
	 * @param comparator
	 *            a comparator that is really used as an equalizer. Objects will be popped
	 *            until the comparison result with the fence returns 0
	 * @return the list the elements above the specified fence
	 */
	List<E> elementsAbove(E fence, Comparator<E> comparator);
}
