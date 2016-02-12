/*****************************************************************************************
 * Source File: SortedSet2List.java
 ****************************************************************************************/
package net.ruready.common.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;

import net.ruready.common.exception.SystemException;

/**
 * This is an adapter that translates the <code>SortedSet</code> interface into the
 * <code>List</code> interface. Adaptation is possible because both are naturally
 * sorted.
 * <p>
 * WARNING: supported get/set operations rely on slow (O(n^2)) iterator loops.
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
 * @version Jul 16, 2007
 */
public class SortedSet2List<E> implements List<E>
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	private SortedSet<E> s;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param s
	 */
	public SortedSet2List(SortedSet<E> s)
	{
		this.s = s;
	}

	// ========================= IMPLEMENTATION: List<E> ===================

	/**
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(E e)
	{
		return s.add(e);
	}

	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, E element)
	{
		// This is slow, but that's as wise as we got when writing this code
		Iterator<E> iterator = iterator();
		for (int i = 0; i < index; i++)
		{
			iterator.next();
		}
		add(element);
	}

	/**
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends E> c)
	{
		return s.addAll(c);
	}

	/**
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends E> c)
	{
		// This is slow, but that's as wise as we got when writing this code
		Iterator<E> iterator = iterator();
		for (int i = 0; i < index; i++)
		{
			iterator.next();
		}
		return addAll(c);
	}

	/**
	 * @see java.util.List#clear()
	 */
	public void clear()
	{
		s.clear();
	}

	/**
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o)
	{
		return s.contains(o);
	}

	/**
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c)
	{
		return s.containsAll(c);
	}

	/**
	 * @see java.util.List#get(int)
	 */
	public E get(int index)
	{
		// This is slow, but that's as wise as we got when writing this code
		Iterator<E> iterator = iterator();
		for (int i = 0; i < index; i++)
		{
			iterator.next();
		}
		return iterator.next();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o)
	{
		throw new SystemException(
				"Cannot find the index of an element of a SortedSet");
	}

	/**
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty()
	{
		return s.isEmpty();
	}

	/**
	 * @see java.util.List#iterator()
	 */
	public Iterator<E> iterator()
	{
		return s.iterator();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o)
	{
		throw new SystemException(
				"Cannot find the last index of an element of a SortedSet");
	}

	/**
	 * @see java.util.List#listIterator()
	 * @throws SystemException
	 */
	public ListIterator<E> listIterator()
	{
		throw new SystemException(
				"SortedSet doesn't implement ListIterator");
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<E> listIterator(int index)
	{
		throw new SystemException(
				"SortedSet doesn't implement ListIterator");
	}

	/**
	 * @see java.util.List#remove(int)
	 */
	public E remove(int index)
	{
		// This is slow, but that's as wise as we got when writing this code
		Iterator<E> iterator = iterator();
		E data = null;
		for (int i = 0; i < index; i++)
		{
			data = iterator.next();
		}
		boolean removed = remove(data);
		return removed ? data : null;
	}

	/**
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o)
	{
		return s.remove(o);
	}

	/**
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c)
	{
		return s.removeAll(c);
	}

	/**
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c)
	{
		return s.retainAll(c);
	}

	/**
	 * @param index
	 * @param element
	 * @return
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public E set(int index, E element)
	{
		throw new SystemException(
				"Cannot set an element of a SortedSet at a certain index");
	}

	/**
	 * @see java.util.List#size()
	 */
	public int size()
	{
		return s.size();
	}

	/**
	 * Returns an <code>ArrayList</code>.
	 * 
	 * @see java.util.List#subList(int, int)
	 */
	public List<E> subList(int fromIndex, int toIndex)
	{
		// This is slow, but that's as wise as we got when writing this code
		Iterator<E> iterator = iterator();
		List<E> list = new ArrayList<E>();

		for (int i = 0; i < fromIndex; i++)
		{
			iterator.next();
		}

		for (int i = fromIndex; i < toIndex; i++)
		{
			list.add(iterator.next());
		}

		return list;
	}

	/**
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray()
	{
		return s.toArray();
	}

	/**
	 * @see java.util.List#toArray(T[])
	 */
	public <T> T[] toArray(T[] a)
	{
		return s.toArray(a);
	}

}
