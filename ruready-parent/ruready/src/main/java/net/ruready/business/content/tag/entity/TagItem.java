/*****************************************************************************************
 * Source File: TagItem.java
 ****************************************************************************************/
package net.ruready.business.content.tag.entity;

import javax.persistence.Entity;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.common.tag.Tag;
import net.ruready.common.text.TextUtil;
import net.sf.json.JSONString;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A naming interface for tags in the {@link Item} hierarchy. All classes under
 * {@link TagCabinet} must implement this interface.
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
 * @version Jul 21, 2007
 */
@Entity
public abstract class TagItem extends Item implements Tag<Long, TagItem, Item>,
		JSONString
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TagItem.class);

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is
	 * required for Hibernate and portability to other EJB 3 containers. This
	 * constructor populates no properties inside this class.
	 */
	protected TagItem()
	{
		super();
	}

	/**
	 * Create a folder with a name and comment.
	 * 
	 * @param name
	 *            course name
	 * @param comment
	 *            course comment
	 */
	public TagItem(String name, String comment)
	{
		super(name, comment);
	}

	/**
	 * A copy constructor that copied the id, name and comment.
	 * 
	 * @param other
	 *            tag to copy from
	 */
	public TagItem(final TagItem other)
	{
		id = other.id;
		setName(other.getName());
		setComment(other.getComment());
	}

	// ========================= IMPLEMENTATION: PersistentEntity ==========

	// ========================= IMPLEMENTATION: Visitable =================

	/**
	 * Let a visitor process this item. Part of the TreeVisitor pattern. This
	 * calls back the visitor's <code>visit()</code> method with this item
	 * type. Must be overridden by every item sub-class.
	 * 
	 * @param visitor
	 *            item visitor whose <code>visit()</code> method is invoked
	 */
	@Override
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}

	// ========================= IMPLEMENTATION: Comparable<TagItem> =======

	/**
	 * Compare tags by name (lexicographic ordering).
	 * 
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(TagItem o)
	{
		if (o == null)
		{
			return 1;
		}

		// Compare by name
		return ((getName() == null) ? -1 : getName().compareTo(o.getName()));
	}

	// ========================= IMPLEMENTATION: JSONString ================

	/**
	 * @return
	 * @see net.sf.json.JSONString#toJSONString()
	 */
	public String toJSONString()
	{
		return TextUtil.emptyStringBuffer().append("{").append("id: \"").append(getId())
				.append("\", ").append("name: \"").append(getName()).append("\"").append(
						"}").toString();
	}

	// ========================= METHODS ===================================

	/**
	 * Return the class type of the parent folder item type of this item.
	 */
	abstract public Class<? extends Item> getTagParentClass();

	// ========================= GETTERS & SETTERS =========================
}
