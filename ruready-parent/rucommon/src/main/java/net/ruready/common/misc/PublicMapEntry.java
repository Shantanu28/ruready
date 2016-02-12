package net.ruready.common.misc;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A publicly-visible map entry (key-value pair). The <tt>Map.entrySet</tt> method
 * returns a collection-view of the map, whose elements are of this class. The <i>only</i>
 * way to obtain a reference to a map entry is from the iterator of this collection-view.
 * These <tt>Map.Entry</tt> objects are valid <i>only</i> for the duration of the
 * iteration; more formally, the behavior of a map entry is undefined if the backing map
 * has been modified after the entry was returned by the iterator, except through the
 * <tt>setValue</tt> operation on the map entry.
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
 * @see Map#entrySet()
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 27, 2007
 */
public class PublicMapEntry<K, V>
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
	private static final Log logger = LogFactory.getLog(PublicMapEntry.class);

	/**
	 * For parsing a string into a parameter map.
	 */
	protected static final String delimiter = " ";

	// ========================= FIELDS ====================================

	/**
	 * Entry key.
	 */
	protected final K key;

	/**
	 * Entry value.
	 */
	protected V value;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a new map entry
	 * 
	 * @param key
	 *            entry key
	 * @param value
	 *            entry value
	 */
	public PublicMapEntry(K key, V value)
	{
		super();
		this.key = key;
		this.value = value;
	}

	// ========================= METHODS (@see Map.Entry<K,V>) =============

	/**
	 * Returns the key corresponding to this entry.
	 * 
	 * @return the key corresponding to this entry
	 * @throws IllegalStateException
	 *             implementations may, but are not required to, throw this exception if
	 *             the entry has been removed from the backing map.
	 */
	public K getKey()
	{
		return key;
	}

	/**
	 * Returns the value corresponding to this entry. If the mapping has been removed from
	 * the backing map (by the iterator's <tt>remove</tt> operation), the results of
	 * this call are undefined.
	 * 
	 * @return the value corresponding to this entry
	 * @throws IllegalStateException
	 *             implementations may, but are not required to, throw this exception if
	 *             the entry has been removed from the backing map.
	 */
	public V getValue()
	{
		return value;
	}

	/**
	 * Replaces the value corresponding to this entry with the specified value (optional
	 * operation). (Writes through to the map.) The behavior of this call is undefined if
	 * the mapping has already been removed from the map (by the iterator's
	 * <tt>remove</tt> operation).
	 * 
	 * @param value
	 *            new value to be stored in this entry
	 * @return old value corresponding to the entry
	 */
	V setValue(V value)
	{
		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}

	/**
	 * Compares the specified object with this entry for equality. Returns <tt>true</tt>
	 * if the given object is also a map entry and the two entries represent the same
	 * mapping. More formally, two entries <tt>e1</tt> and <tt>e2</tt> represent the
	 * same mapping if
	 * 
	 * <pre>
	 * (e1.getKey() == null ? e2.getKey() == null : e1.getKey().equals(e2.getKey()))
	 * 		&amp;&amp; (e1.getValue() == null ? e2.getValue() == null : e1.getValue().equals(
	 * 				e2.getValue()))
	 * </pre>
	 * 
	 * This ensures that the <tt>equals</tt> method works properly across different
	 * implementations of the <tt>Map.Entry</tt> interface.
	 * 
	 * @param o
	 *            object to be compared for equality with this map entry
	 * @return <tt>true</tt> if the specified object is equal to this map entry
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj)
	{
		// Standard checks to ensure the adherence to the general contract of
		// the equals() method
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass()))
		{
			return false;
		}
		// Cast to friendlier version
		PublicMapEntry<K, V> other = (PublicMapEntry<K, V>) obj;

		return (key == null ? other.key == null : key.equals(other.key))
				&& (value == null ? other.value == null : value.equals(other.value));

	}

	/**
	 * Returns the hash code value for this map entry. The hash code of a map entry
	 * <tt>e</tt> is defined to be:
	 * 
	 * <pre>
	 * (e.getKey() == null ? 0 : e.getKey().hashCode())
	 * 		&circ; (e.getValue() == null ? 0 : e.getValue().hashCode())
	 * </pre>
	 * 
	 * This ensures that <tt>e1.equals(e2)</tt> implies that
	 * <tt>e1.hashCode()==e2.hashCode()</tt> for any two Entries <tt>e1</tt> and
	 * <tt>e2</tt>, as required by the general contract of <tt>Object.hashCode</tt>.
	 * 
	 * @return the hash code value for this map entry
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 * @see #equals(Object)
	 */
	@Override
	public int hashCode()
	{
		return (key == null ? 0 : key.hashCode())
				^ (value == null ? 0 : value.hashCode());

	}
}
