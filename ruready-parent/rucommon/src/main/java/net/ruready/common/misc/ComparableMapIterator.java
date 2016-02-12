package net.ruready.common.misc;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/**
 * Iterates over entries in a general map according to the key's natural ordering
 * (ascending order of keys). Keys must be {@link Comparable}.
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
public class ComparableMapIterator<K extends Comparable<K>, V> implements
		Iterator<PublicMapEntry<K, V>>
{
	/**
	 * The map to iterate on.
	 */
	private final Map<K, V> map;

	/**
	 * Internal iterator.
	 */
	private final Iterator<K> iterator;

	public ComparableMapIterator(final Map<K, V> map)
	{
		this.map = map;
		// This is where we guarantee that the variables are always returned
		// in alphabetical order. Note: the used tree set is synchronized.
		iterator = Collections.synchronizedSortedSet(new TreeSet<K>(map.keySet()))
				.iterator();
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
