/*****************************************************************************************
 * Source File: Tag.java
 ****************************************************************************************/
package net.ruready.common.tag;

import java.io.Serializable;

import net.ruready.common.discrete.Identifier;
import net.ruready.common.eis.entity.PersistentEntity;

/**
 * A tag (attribute) that can be added and removed from a {@link Taggable} object. A tag
 * is uniquely identified by an {@link Identifier} object. The {@link Comparable}
 * interface controls how tags are ordered within a tag list of a {@link Taggable} object.
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
public interface Tag<ID extends Serializable, T extends Tag<ID, T, S>, S extends Taggable<ID, T, S>>
		extends PersistentEntity<ID>, Comparable<T>
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Return the parent object of this object. -- Obsolete, because a {@link Tag} now has
	 * a many-to-many relationship to {@link Taggable}.
	 * 
	 * @return the taggable object
	 */
	// S getTaggable();
	//
	/**
	 * Set the parent object of this object. -- Obsolete, because a {@link Tag} now has a
	 * many-to-many relationship to {@link Taggable}.
	 * 
	 * @param taggable
	 *            the taggable to set
	 */
	// void setTaggable(S taggable);
}
