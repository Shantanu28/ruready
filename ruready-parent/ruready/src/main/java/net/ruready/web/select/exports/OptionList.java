/*****************************************************************************************
 * Source File: OptionList.java
 ****************************************************************************************/

package net.ruready.web.select.exports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.pointer.ValueObject;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A decorator of <code>{@link List}&lt;{@link Option}&gt;</code> that is
 * publicly cloneable (i.e. supported deep copy).
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 3, 2007
 */

public class OptionList implements List<Option>, PubliclyCloneable, ValueObject
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
	private static final Log logger = LogFactory.getLog(OptionList.class);

	// ========================= FIELDS ====================================

	/**
	 * The internal list that we decorate.
	 */
	private final List<Option> options = new ArrayList<Option>();

	/**
	 * The value marked as selected. Optional.
	 */
	private String selectedValue;

	/**
	 * The index of the selected values in the list.
	 */
	private int selectedIndex;

	/**
	 * Attributes of the select group.
	 */
	private final Map<String, String> attributes = new HashMap<String, String>();

	/**
	 * Is this selection enabled or not.
	 */
	private boolean enabled;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a new option list.
	 */
	public OptionList()
	{

	}

	/**
	 * Create a new option list.
	 * 
	 * @param enabled
	 *            if false, disables the list
	 */
	public OptionList(final boolean enabled)
	{
		if (!enabled)
		{
			disable();
		}
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @param o
	 * @return
	 * @see java.util.List#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o)
	{
		return options.equals(o);
	}

	/**
	 * @return
	 * @see java.util.List#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return options.hashCode();
	}

	/**
	 * @return
	 * @see java.util.List#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer s = new StringBuffer(options.toString());
		if ((selectedIndex >= 0) && (selectedIndex < options.size()))
		{
			s.append(" Selected: #" + selectedIndex + " " + options.get(selectedIndex)
					+ " selectedValue " + selectedValue);
		}
		return s.toString();
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to
	 * serve as a target (or part of a target) of an assembly.
	 * 
	 * @return a deep copy of this object.
	 */
	@Override
	public OptionList clone()
	{
		OptionList copy = new OptionList();
		for (Option element : options)
		{
			copy.add(new Option(element.getLabel(), element.getValue()));
		}
		return copy;
	}

	// ========================= IMPLEMENTATION: List ======================

	/**
	 * @param index
	 * @param element
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, Option element)
	{
		options.add(index, element);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(Option e)
	{
		return options.add(e);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Option> c)
	{
		return options.addAll(c);
	}

	/**
	 * @param index
	 * @param c
	 * @return
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends Option> c)
	{
		return options.addAll(index, c);
	}

	/**
	 * @see java.util.List#clear()
	 */
	public void clear()
	{
		options.clear();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o)
	{
		return options.contains(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c)
	{
		return options.containsAll(c);
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#get(int)
	 */
	public Option get(int index)
	{
		return options.get(index);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o)
	{
		return options.indexOf(o);
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty()
	{
		return options.isEmpty();
	}

	/**
	 * @return
	 * @see java.util.List#iterator()
	 */
	public Iterator<Option> iterator()
	{
		return options.iterator();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o)
	{
		return options.lastIndexOf(o);
	}

	/**
	 * @return
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<Option> listIterator()
	{
		return options.listIterator();
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<Option> listIterator(int index)
	{
		return options.listIterator(index);
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#remove(int)
	 */
	public Option remove(int index)
	{
		return options.remove(index);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o)
	{
		return options.remove(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c)
	{
		return options.removeAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c)
	{
		return options.retainAll(c);
	}

	/**
	 * @param index
	 * @param element
	 * @return
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Option set(int index, Option element)
	{
		return options.set(index, element);
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int size()
	{
		return options.size();
	}

	/**
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 * @see java.util.List#subList(int, int)
	 */
	public List<Option> subList(int fromIndex, int toIndex)
	{
		return options.subList(fromIndex, toIndex);
	}

	/**
	 * @return
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray()
	{
		return options.toArray();
	}

	/**
	 * @param <T>
	 * @param a
	 * @return
	 * @see java.util.List#toArray(T[])
	 */
	public <T> T[] toArray(T[] a)
	{
		return options.toArray(a);
	}

	// ========================= METHODS ====================================

	/**
	 * Is this element selected or not.
	 * 
	 * @param element
	 *            element in or outside the option list
	 * @return Is this element selected or not.
	 */
	public boolean isSelected(Option element)
	{
		return new Option(null, selectedValue).equals(element);
	}

	// ========================= PRIVATE METHODS ============================

	/**
	 * Carry out operations to properly update the selected value.
	 */
	private void updateSelection()
	{
		selectedIndex = CommonNames.MISC.INVALID_VALUE_INTEGER;
		// Option#equals() comparing values only. Do the same thing
		// here, but in a null-safe way
		if (selectedValue == null)
		{
			return;
		}
		selectedIndex = 0;
		for (Option bean : options)
		{
			if (selectedValue.equals(bean.getValue()))
			{
				return;
			}
			selectedIndex++;
		}
		// selectedIndex = list.indexOf(new Option(null,
		// selectedValue));
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsAttributeName(Object key)
	{
		return attributes.containsKey(key);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsAttributeValue(Object value)
	{
		return attributes.containsValue(value);
	}

	/**
	 * @return
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<String, String>> attributeEntrySet()
	{
		return attributes.entrySet();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public String getAttribute(Object key)
	{
		return attributes.get(key);
	}

	/**
	 * @return
	 * @see java.util.Map#keySet()
	 */
	public Set<String> attributeKeySet()
	{
		return attributes.keySet();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public String setAttribute(String key, String value)
	{
		return attributes.put(key, value);
	}

	/**
	 * @param m
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void setAttributes(Map<? extends String, ? extends String> m)
	{
		attributes.putAll(m);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public String removeAttribute(Object key)
	{
		return attributes.remove(key);
	}

	/**
	 * @return
	 * @see java.util.Map#values()
	 */
	public Collection<String> values()
	{
		return attributes.values();
	}

	/**
	 * Disable the select group.
	 */
	public void disable()
	{
		attributes.put("disabled", "disabled");
		enabled = false;
	}

	/**
	 * Enable the select group.
	 */
	public void enable()
	{
		attributes.remove("disabled");
		enabled = true;
	}

	/**
	 * Return the index of the first encountered empty selection.
	 * 
	 * @return first encountered option with. If not found, returns -1
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOfEmptySelection()
	{
		return indexOfEmptySelection(0);
	}

	/**
	 * Return the index of the first encountered empty selection.
	 * 
	 * @param start
	 *            start testing for emptiness at this index on the list
	 * @return first encountered option with index >= start. If not found,
	 *         returns -1
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOfEmptySelection(final int start)
	{
		for (int i = start; i < options.size(); i++)
		{
			if (options.get(i).isEmpty())
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * Make the value of the first empty selection the selected value, if an
	 * empty selection is found.
	 */
	public void selectEmptySelection()
	{
		int i = indexOfEmptySelection();
		if (i >= 0)
		{
			this.selectedValue = options.get(i).getValue();
			updateSelection();
		}
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the selectedValue
	 */
	public String getSelectedValue()
	{
		return selectedValue;
	}

	/**
	 * @param selectedValue
	 *            the selectedValue to set
	 */
	public void setSelectedValue(String selectedValue)
	{
		this.selectedValue = selectedValue;
		updateSelection();
	}

	/**
	 * @return the selectedIndex
	 */
	public int getSelectedIndex()
	{
		return selectedIndex;
	}

	/**
	 * Returns the attributes property.
	 * 
	 * @return the attributes
	 */
	public Map<String, String> getAttributes()
	{
		return attributes;
	}

	/**
	 * Returns the enabled property.
	 * 
	 * @return the enabled
	 */
	public boolean isEnabled()
	{
		return enabled;
	}
}
