package net.ruready.common.stack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Vector;

import net.ruready.common.pointer.PubliclyCloneable;

/**
 * Similar to {@link java.util.Stack}, but works correctly with generic types. The
 * original implementation of {@link #clone()} returns a raw {@link java.util.Stack} type,
 * which causes some unchecked warnings.
 * <p>
 * The <code>Stack</code> class represents a last-in-first-out (LIFO) stack of objects.
 * It extends class <tt>Vector</tt> with five operations that allow a vector to be
 * treated as a stack. The usual <tt>push</tt> and <tt>pop</tt> operations are
 * provided, as well as a method to <tt>peek</tt> at the top item on the stack, a method
 * to test for whether the stack is <tt>empty</tt>, and a method to <tt>search</tt>
 * the stack for an item and discover how far it is from the top.
 * <p>
 * When a stack is first created, it contains no items.
 * <p>
 * A more complete and consistent set of LIFO stack operations is provided by the
 * {@link Deque} interface and its implementations, which should be used in preference to
 * this class. For example:
 * 
 * <pre>
 * {
 * 	@code
 * 	Deque&lt;Integer&gt; stack = new ArrayDeque&lt;Integer&gt;();
 * }
 * </pre>
 * 
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 21, 2007
 */
public class GenericStack<E> extends Vector<E> implements PubliclyCloneable,
		AbstractStack<E>
{
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 1224463164541339165L;

	/**
	 * Creates an empty GenericStack.
	 */
	public GenericStack()
	{

	}

	/**
	 * Pushes an item onto the top of this stack. This has exactly the same effect as:
	 * <blockquote>
	 * 
	 * <pre>
	 * addElement(item)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param item
	 *            the item to be pushed onto this stack.
	 * @return the <code>item</code> argument.
	 * @see java.util.Vector#addElement
	 */
	public E push(E item)
	{
		addElement(item);

		return item;
	}

	/**
	 * Removes the object at the top of this stack and returns that object as the value of
	 * this function.
	 * 
	 * @return The object at the top of this stack (the last item of the <tt>Vector</tt>
	 *         object).
	 * @exception EmptyGenericStackException
	 *                if this stack is empty.
	 */
	public synchronized E pop()
	{
		E obj;
		int len = size();

		obj = peek();
		removeElementAt(len - 1);

		return obj;
	}

	/**
	 * Looks at the object at the top of this stack without removing it from the stack.
	 * 
	 * @return the object at the top of this stack (the last item of the <tt>Vector</tt>
	 *         object).
	 * @exception EmptyGenericStackException
	 *                if this stack is empty.
	 */
	public synchronized E peek()
	{
		int len = size();

		if (len == 0)
		{
			throw new EmptyStackException();
		}
		return elementAt(len - 1);
	}

	/**
	 * Tests if this stack is empty.
	 * 
	 * @return <code>true</code> if and only if this stack contains no items;
	 *         <code>false</code> otherwise.
	 */
	public boolean empty()
	{
		return size() == 0;
	}

	/**
	 * Returns the 1-based position where an object is on this stack. If the object
	 * <tt>o</tt> occurs as an item in this stack, this method returns the distance from
	 * the top of the stack of the occurrence nearest the top of the stack; the topmost
	 * item on the stack is considered to be at distance <tt>1</tt>. The
	 * <tt>equals</tt> method is used to compare <tt>o</tt> to the items in this
	 * stack.
	 * 
	 * @param o
	 *            the desired object.
	 * @return the 1-based position from the top of the stack where the object is located;
	 *         the return value <code>-1</code> indicates that the object is not on the
	 *         stack.
	 */
	public synchronized int search(Object o)
	{
		int i = lastIndexOf(o);

		if (i >= 0)
		{
			return size() - i;
		}
		return -1;
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Returns a clone of this stack. Stack elements are not cloned, so this is a shallow
	 * copy, not a deep copy.
	 * 
	 * @return a clone of this stack
	 */
	@Override
	public GenericStack<E> clone()
	{
		GenericStack<E> copy = new GenericStack<E>();
		for (E element : this)
		{
			copy.add(element);
		}
		return copy;
	}

	// ========================= IMPLEMENTATION: AbstractStack<E> ==========

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
	public List<E> elementsAbove(E fence)
	{
		List<E> items = new ArrayList<E>();

		while (!this.isEmpty())
		{
			E top = this.pop();
			if (top.equals(fence))
			{
				break;
			}
			items.add(top);
		}

		return items;
	}

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
	public List<E> elementsAbove(E fence, Comparator<E> comparator)
	{
		List<E> items = new ArrayList<E>();

		while (!this.isEmpty())
		{
			E top = this.pop();
			if (comparator.compare(top, fence) == 0)
			{
				break;
			}
			items.add(top);
		}

		return items;
	}

}
