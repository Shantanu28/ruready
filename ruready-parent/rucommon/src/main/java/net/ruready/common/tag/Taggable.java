/*****************************************************************************************
 * Source File: Taggable.java
 ****************************************************************************************/
package net.ruready.common.tag;

import java.io.Serializable;
import java.util.Collection;

/**
 * A taggable object has a map of tag-identifier-to-tag attributes.
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
 * @version Sep 9, 2007
 */
public interface Taggable<ID extends Serializable, T extends Tag<ID, T, S>, S extends Taggable<ID, T, S>>
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS: GETTERS =================

	/**
	 * Returns the number of tags attached to this object.
	 * 
	 * @return Returns the number of tags
	 */
	int getNumTags();

	/**
	 * Returns <code>true</code> if and only if the number of tags is positive.
	 * 
	 * @return <code>true</code> if and only if this object has tags
	 */
	boolean hasTags();

	/**
	 * Return a tag by unique identifier.
	 * 
	 * @param id
	 *            tag identifier
	 * @return the tag; if the tag doesn't exist in this object, returns <code>null</code>
	 */
	T getTagById(ID id);

	/**
	 * Return the first tag matching a tag class. The order of tags can vary across
	 * implementations, so consult with the implementor's API to see which tag is returned
	 * from this method. A typical implementation may sort tags by increasing IDs. This
	 * method is intended to be used in cases where there is a unique tag of a certain
	 * class in a {@link Taggable} object.
	 * 
	 * @param tagClass
	 *            tag class
	 * @return the first tag matching the tag class according to the tag iterator of the
	 *         implementing {@link Taggable} class. If none exist, returns
	 *         <code>null</code>
	 */
	<R extends T> R getFirstTag(Class<R> tagClass);

	/**
	 * Return the list of tags of this node.
	 * 
	 * @return the tags of this node
	 */
	Collection<T> getTags();

	/**
	 * Return all tags matching a tag class.
	 * 
	 * @param tagClass
	 *            tag class
	 * @return all tags matching the tag class. If none exist, returns an empty list
	 */
	<R extends T> Collection<R> getTags(Class<R> tagClass);

	/**
	 * Returns <tt>true</tt> if this set contains the specified element. More formally,
	 * returns <tt>true</tt> if and only if this set contains an element <tt>e</tt>
	 * such that <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 * 
	 * @param tag
	 *            tag element whose presence in this set is to be tested
	 * @return <tt>true</tt> if this set contains the specified element
	 * @throws ClassCastException
	 *             if the type of the specified element is incompatible with this set
	 *             (optional)
	 * @throws NullPointerException
	 *             if the specified element is null and this set does not permit null
	 *             elements (optional)
	 */
	boolean containsTag(T tag);

	/**
	 * Returns <tt>true</tt> if this set contains the specified element. More formally,
	 * returns <tt>true</tt> if and only if this set contains an element <tt>e</tt>
	 * such that <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 * 
	 * @param id
	 *            ID to be found in a tag object to be found (using the
	 * @return <tt>true</tt> if this set contains the specified element
	 * @throws ClassCastException
	 *             if the type of the specified element is incompatible with this set
	 *             (optional)
	 * @throws NullPointerException
	 *             if the specified element is null and this set does not permit null
	 *             elements (optional)
	 */
	boolean containsTag(ID id);

	// ========================= ABSTRACT METHODS: ADDERS ==================

	/**
	 * Set a tag to this object. If a previous tag exists with the same ID, it is replaced
	 * with this tag. The tag's key is its unique identifier. If the key is null, the tag
	 * won't be added/set.
	 * 
	 * @param tag
	 *            tag to add/set
	 */
	void addTag(T tag);

	/**
	 * Add all tag in a list to this tree.
	 * 
	 * @param newTags
	 *            collection to be added
	 */
	void addTags(Collection<? extends T> newTags);

	// ========================= ABSTRACT METHODS: SETTERS =================

	/**
	 * Replace a tag. First, <code>oldTag</code> is found using the
	 * {@link Taggable#getTagById(Serializable)} method. If it is found, it is replaced by
	 * <code>newTag</code>. Otherwise, this method has no effect.
	 * 
	 * @param oldTag
	 *            Tag to be found and replaced.
	 * @param newTag
	 *            New tag to replace <code>oldTag</code>, if it is founed
	 */
	void replaceTag(T oldTag, T newTag);

	// ========================= ABSTRACT METHODS: REMOVERS ================

	/**
	 * Remove a tag from the tag list.
	 * 
	 * @param tag
	 *            The tag to be removed
	 */
	void removeTag(T tag);

	/**
	 * Remove a tag from this object.
	 * 
	 * @param id
	 *            tag identifier
	 */
	void removeTag(ID id);

	/**
	 * Remove all tags with of specified type from this object.
	 * 
	 * @param tagClass
	 *            tag class
	 */
	void removeTags(Class<? extends T> tagClass);

	/**
	 * Remove all tags.
	 */
	void removeAllTags();
}
