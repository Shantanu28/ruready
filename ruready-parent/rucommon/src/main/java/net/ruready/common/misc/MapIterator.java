package net.ruready.common.misc;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Iterates over entries in a general map according to the key's natural ordering
 * (ascending order of keys). A {@link Comparator} must be passed in; keys need not be
 * {@link Comparable}.
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
 * @see java.util.TreeSet
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 1, 2007
 */
public class MapIterator<K, V> implements Iterator<PublicMapEntry<K, V>>
{
	/**
	 * The map to iterate on.
	 */
	private final Map<K, V> map;

	/**
	 * Internal iterator.
	 */
	private final Iterator<K> iterator;

	/**
	 * Create a map iterator.
	 * 
	 * @param map
	 *            The map to iterate on
	 * @param comparator
	 *            Key comparator to use for key ordering
	 */
	public MapIterator(final Map<K, V> map, final Comparator<K> comparator)
	{
		this.map = map;
		// This is where we guarantee that the variables are always returned
		// in the order specified by the comparator. Note: the used TreeSet is
		// synchronized.
		SortedSet<K> keySet = Collections
				.synchronizedSortedSet(new TreeSet<K>(comparator));
		keySet.addAll(map.keySet());
		iterator = keySet.iterator();
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext()
	{
		return iterator.hasNext();
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public PublicMapEntry<K, V> next()
	{
		K name = iterator.next();
		return new PublicMapEntry<K, V>(name, map.get(name));
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove()
	{
		iterator.remove();
	}

}
