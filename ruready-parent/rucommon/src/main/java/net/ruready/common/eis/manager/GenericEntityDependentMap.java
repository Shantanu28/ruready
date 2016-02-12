/*****************************************************************************************
 * Source File: GenericEntityDependentMap.java
 ****************************************************************************************/
package net.ruready.common.eis.manager;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import net.ruready.common.eis.entity.EntityDependent;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.misc.MapIterator;
import net.ruready.common.misc.PublicMapEntry;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A map that holds references to custom {@link EntityDependent} objects (e.g. DAOs or
 * entity managers). Provides type-safe access using generic types.
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
 * @version Aug 1, 2007
 */
public class GenericEntityDependentMap<E extends EntityDependent<?, ?>>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(GenericEntityDependentMap.class);

	/**
	 * Internal map data structure. The map is defined using wild-cards, but access is
	 * restricted using generic get/set methods in this object.
	 */
	private final Map<Class<? extends Serializable>, E> customObjects = new Hashtable<Class<? extends Serializable>, E>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize an empty DAO map.
	 */
	public GenericEntityDependentMap()
	{

	}

	// ========================= ITERATORS =================================

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print the DAO map.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		Iterator<PublicMapEntry<Class<? extends Serializable>, E>> iterator = this
				.iterator();
		while (iterator.hasNext())
		{
			PublicMapEntry<Class<? extends Serializable>, E> entry = iterator.next();
			s.append(entry.getKey().getCanonicalName()).append(CommonNames.MISC.TAB_CHAR)
					.append(entry.getValue()).append(CommonNames.MISC.NEW_LINE_CHAR);
		}

		return s.toString();
	}

	// ========================= METHODS ===================================

	/**
	 * Get an iterator over entries in the map. Variables are returned in alphabetical
	 * variable name.
	 * 
	 * @return variable iterator
	 * @see net.ruready.common.parser.range.RangeMap#iterator()
	 */
	public Iterator<PublicMapEntry<Class<? extends Serializable>, E>> iterator()
	{
		// Declaring an anonymous comparator class
		return new MapIterator<Class<? extends Serializable>, E>(customObjects,
				new Comparator<Class<? extends Serializable>>()
				{

					/**
					 * @see java.util.Comparator#compare(java.lang.Object,
					 *      java.lang.Object)
					 */
					public int compare(Class<? extends Serializable> o1,
							Class<? extends Serializable> o2)
					{
						// Note: symmetric; would have throw a NullPointerException if
						// either key is null, but keys cannot be null so we can skip null
						// checks.
						return o1.getCanonicalName().compareTo(o2.getCanonicalName());
					}
				});
	}

	/**
	 * Produces a DAO class. If a custom DAO class is not found in the {@link #daos}
	 * class, it is deferred to a default instantiation of {@link EntityDependent} for the
	 * particular class and identifier types.
	 * 
	 * @param <T>
	 *            class type, e.g. <code>Item.class</code>
	 * @param <ID>
	 *            identifier type, e.g. Long
	 * @param entityClass
	 * @return requested DAO
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable, D extends EntityDependent<T, ID>> D get(
			Class<T> entityClass)
	{
		@SuppressWarnings("unchecked")
		D result = (D) customObjects.get(entityClass);
		return result;
	}

	/**
	 * Add a DAO to the map. This is a type-safe method.
	 * 
	 * @param <T>
	 *            class type, e.g. <code>Item.class</code>
	 * @param <ID>
	 *            identifier type, e.g. {@link Long}
	 * @param entityClass
	 *            class corresponding to the custom entity-dependent object
	 * @param customObject
	 *            a custom entity-dependent object
	 * @return requested DAO
	 */
	public <T extends PersistentEntity<ID>, ID extends Serializable> void put(
			Class<T> entityClass, E customObject)
	{
		customObjects.put(entityClass, customObject);
	}

	/**
	 * Remove a custom object from the map. This is a type-safe method.
	 * 
	 * @param entityClass
	 *            class corresponding to the custom entity-dependent object
	 * @return requested DAO
	 */
	public void remove(Class<?> entityClass)
	{
		customObjects.remove(entityClass);
	}

}
