/*****************************************************************************************
 * Source File: Item.java
 ****************************************************************************************/
package net.ruready.business.content.item.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import net.ruready.business.common.tree.comparator.TreeNodeComparatorID;
import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.common.tree.entity.NodePrinter;
import net.ruready.business.common.tree.util.NodeUtil;
import net.ruready.business.content.item.entity.audit.AuditMessage;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.business.content.tag.entity.comparator.TagItemComparator;
import net.ruready.business.content.util.util.ItemTypeUtil;
import net.ruready.common.audit.Auditable;
import net.ruready.common.discrete.Identifiable;
import net.ruready.common.exception.SystemException;
import net.ruready.common.pointer.ShallowCloneable;
import net.ruready.common.tag.Taggable;
import net.ruready.common.util.HashCodeUtil;
import net.ruready.common.visitor.Visitable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * This is the base type for the Content Management System (CMS). All roots in
 * the system are Items. It is a {@link Node} with extra functionality relevant
 * to our CMS.
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
 * @version Aug 1, 2007
 */
@Entity
public class Item extends Node implements Identifiable<ItemType>, Visitable<ItemVisitor>,
		Auditable<Integer, AuditMessage>, Taggable<Long, TagItem, Item>
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
	private static final Log logger = LogFactory.getLog(Item.class);

	// ========================= PERSISTENT FIELDS =========================

	/**
	 * Access attribute.
	 */
	@Column
	private boolean readOnly = false;

	/**
	 * Latest audit message. Also appears in the {@link #messages} set, but is
	 * easily accessed by this direct reference.
	 */
	@OneToOne
	@Cascade(CascadeType.ALL)
	private AuditMessage latestMessage = null;

	/**
	 * A set of audit messages logging the actions performed on this item by
	 * users. Could be large, which is why we use a bi-directional association
	 * with SAVE cascades on both types. Cascading now includes removing.
	 */
	@OneToMany
	@Cascade(CascadeType.ALL)
	@LazyCollection(value = LazyCollectionOption.EXTRA)
	@IndexColumn(name = "index_message")
	private List<AuditMessage> messages = new ArrayList<AuditMessage>();

	/**
	 * List of tags attached to this object. <br>
	 */
	@ManyToMany
	@Cascade(CascadeType.ALL)
	@Sort(type = SortType.COMPARATOR, comparator = TagItemComparator.class)
	@IndexColumn(name = "index_tag")
	private SortedSet<TagItem> tags = new TreeSet<TagItem>(new TagItemComparator());

	// ========================= TRANSIDENT FIELDS =========================

	/**
	 * If true, indicates that this is a yet-unsaved item. The {@link #parentId}
	 * field is then used to determine the parent to add this item under. This
	 * flag takes precedence to the {@link newParent} flag.
	 */
	@Transient
	private boolean newItem = false;

	/**
	 * If true, indicates that this is a saved item but its parent has changed.
	 * This flag is used to determine whether the item should be moved under the
	 * new parent, specified by the {@link #parentId} field.
	 */
	@Transient
	private boolean newParent = false;

	/**
	 * Identifier of the parent item or the prospective parent of a yet-unsaved
	 * item.
	 */
	@Transient
	private Long parentId = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is
	 * required for Hibernate and portability to other EJB 3 containers. This
	 * constructor populates no properties inside this class.
	 */
	public Item()
	{
		super();
	}

	/**
	 * Construct a item with a name and comment.
	 * 
	 * @param name
	 *            name of this item
	 * @param comment
	 *            comment on this item
	 */
	public Item(String name, String comment)
	{
		super(name, comment);
	}

	// ========================= IMPLEMENTATION: ShallowCloneable ==========

	/**
	 * @return
	 * @see net.ruready.business.common.tree.entity.Node#shallowClone()
	 */
	@Override
	public Item shallowClone()
	{
		Item copy = (Item) super.shallowClone();

		// Custom field setting
		copy.latestMessage = null;
		copy.messages = new ArrayList<AuditMessage>();
		copy.parentId = null;

		copy.tags = new TreeSet<TagItem>(new TagItemComparator());
		for (TagItem tag : tags)
		{
			copy.addTag((TagItem) tag.shallowClone());
		}

		return copy;
	}

	/**
	 * Does not copy all fields over - only data fields of this node. Children
	 * are not copied. Identity is not copied.
	 * 
	 * @param destination
	 * @see net.ruready.common.pointer.ShallowCloneable#mergeInto(net.ruready.common.pointer.ShallowCloneable)
	 */
	@Override
	public void mergeInto(final ShallowCloneable destination)
	{
		super.mergeInto(destination);

		Item dest = (Item) destination;
		dest.readOnly = readOnly;

		// Merge tags
		for (TagItem tag : tags)
		{
			TagItem destTag = NodeUtil.findElementByExample(dest.getTags(), tag);
			if (destTag == null)
			{
				// Source child not found, add a copy of its entire tree under
				// the
				// destination item.
				dest.addTag((TagItem) tag.clone());
			}
			else
			{
				// Source child found under the destination item, merge it
				NodeUtil.merge(tag, destTag);
			}
		}
	}

	// ========================= IMPLEMENTATION: Node ======================

	/**
	 * Print the data and other properties of this node.
	 * 
	 * @return data and properties of this node, represented as a string
	 */
	@Override
	public String printData()
	{
		return "[" + getClass().getSimpleName() + "]" + " ID " + id + " V" + getVersion()
				+ " " + getName()
				+ ((getSerialNo() != null) ? (" #" + getSerialNo()) : "");
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Validate child type and add it under this item.
	 * 
	 * @param childRaw
	 *            child to be added
	 * @throws SystemException
	 *             if childRaw is not of a valid child type
	 * @see net.ruready.business.common.tree.entity.Node#addChild(net.ruready.business.common.tree.entity.Node)
	 */
	@Override
	public void addChild(Node childRaw)
	{
		Item child = validateChildType(childRaw);
		super.addChild(child);
	}

	/**
	 * Validate new children type and add it under this item.
	 * 
	 * @param newChildren
	 *            children to be added
	 * @throws SystemException
	 *             if there exists a new child that is not of a valid child type
	 * @see net.ruready.business.common.tree.entity.Node#addChilds(java.util.Collection)
	 */
	@Override
	public void addChilds(Collection<Node> newChildren)
	{
		for (Node child : newChildren)
		{
			validateChildType(child);
		}
		super.addChilds(newChildren);
	}

	/**
	 * Objects are equal if they are non-null and have equal hash codes.
	 * 
	 * @param obj
	 *            other object
	 * @return result of equality:
	 *         <code>(obj == null) ? false : (hashCode() == obj.hashCode())</code>
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		// Hope it's OK to use hashCode() for equality. This guarantees
		// that equal hash codes <==> object equality.
		// logger.debug("equals()");
		return (obj == null) ? false : (hashCode() == obj.hashCode());
	}

	/**
	 * The hash code is computed based on the entity's {@link #id} field, item
	 * type and parent id.
	 * 
	 * @return hash code of the {@link #id} field
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, getIdentifier());
		result = HashCodeUtil.hash(result, getName());
		result = HashCodeUtil.hash(result, (getParent() == null) ? null : getParent()
				.getId());
		// logger.debug("ID " + getIdentifier() + " name " + getName() + "
		// parent.id "
		// + ((getParent() == null) ? null : getParent().getId()) + " hashCode "
		// + result);
		return result;
	}

	// ========================= IMPLEMENTATION: Identifiable =================

	/**
	 * Return the item type. Delegates to <code>ItemType</code>.
	 * 
	 * @see net.ruready.common.discrete.Identifier#getType()
	 */
	public final String getType()
	{
		return getIdentifier().getType();
	}

	/**
	 * @see net.ruready.common.discrete.Identifiable#getIdentifier()
	 * @return the type of this item
	 */
	public ItemType getIdentifier()
	{
		return ItemType.ITEM;
	}

	// ========================= IMPLEMENTATION: Visitable<ItemVisitor> ===

	/**
	 * Let a visitor process this item. Part of the Visitor pattern. This calls
	 * back the visitor's <code>visit()</code> method with this item type.
	 * 
	 * @param visitor
	 *            item visitor whose <code>visit()</code> method is invoked
	 */
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}

	// ========================= IMPLEMENTATION: Auditable<AuditMessage> ===

	/**
	 * @return the latestMessage
	 */
	public AuditMessage getLatestMessage()
	{
		return latestMessage;
	}

	/**
	 * Add a message and set it to be the latest audit message associated with
	 * this object.
	 * 
	 * @param latestMessage
	 *            the message to add
	 * @see net.ruready.common.audit.Auditable#addMessage(net.ruready.common.audit.Message)
	 */
	public void addMessage(AuditMessage message)
	{
		// latestMessage = message.clone();
		latestMessage = message;
		messages.add(message);
		// getMessages().add(message);
	}

	/**
	 * @return the messages
	 */
	public List<AuditMessage> getMessages()
	{
		return messages;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(List<AuditMessage> messages)
	{
		this.messages = messages;
	}

	// ========================= IMPLEMENTATION: Taggable<Long, TagItem> ===

	/**
	 * @see net.ruready.common.tag.Taggable#addTags(java.util.List)
	 */
	public void addTags(Collection<? extends TagItem> newTags)
	{
		tags.addAll(newTags);

		// Set tag parent references
		// for (TagItem tag : newTags)
		// {
		// tag.setTaggable(this);
		// }
	}

	/**
	 * @see net.ruready.common.tag.Taggable#getNumTags()
	 */
	public int getNumTags()
	{
		return tags.size();
	}

	/**
	 * @see net.ruready.common.tag.Taggable#getTags()
	 */
	public SortedSet<TagItem> getTags()
	{
		return tags;
	}

	/**
	 * @see net.ruready.common.tag.Taggable#hasTags()
	 */
	public boolean hasTags()
	{
		return !tags.isEmpty();
	}

	/**
	 * @param tagId
	 * @return
	 * @see net.ruready.common.tag.Taggable#containsTag(java.io.Serializable)
	 */
	public boolean containsTag(Long tagId)
	{
		// Assumes Item.equals() is not overridden by TagItem and compares Items
		// by id
		Item tag = new Item(null, null);
		tag.id = tagId;
		return tags.contains(tag);
	}

	/**
	 * @param tag
	 * @return
	 * @see net.ruready.common.tag.Taggable#containsTag(net.ruready.common.tag.Tag)
	 */
	public boolean containsTag(TagItem tag)
	{
		return tags.contains(tag);
	}

	/**
	 * @see net.ruready.common.tag.Taggable#removeChild(net.ruready.common.tag.Tag)
	 */
	public void removeTag(TagItem tag)
	{
		tags.remove(tag);
	}

	/**
	 * @see net.ruready.common.tag.Taggable#removeAllTags()
	 */
	public void removeAllTags()
	{
		tags.clear();
	}

	/**
	 * @param oldTag
	 * @param newTag
	 * @see net.ruready.common.tag.Taggable#replaceTag(net.ruready.common.tag.Tag,
	 *      net.ruready.common.tag.Tag)
	 */
	public void replaceTag(TagItem oldTag, TagItem newTag)
	{
		boolean found = tags.contains(oldTag);
		if (found)
		{
			tags.remove(oldTag);
			tags.add(newTag);
		}
	}

	/**
	 * @see net.ruready.common.tag.Taggable#addTag(net.ruready.common.tag.Tag)
	 */
	public void addTag(TagItem tag)
	{
		tags.add(tag);
		// tag.setTaggable(this);
	}

	/**
	 * @see net.ruready.common.tag.Taggable#getFirstTag(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <S extends TagItem> S getFirstTag(Class<S> tagClass)
	{
		// Loop over tags in their order in the map; return the first
		// encountered tag of the requested class.
		for (TagItem tag : tags)
		{
			if (tag.getItemClass() == tagClass)
			{
				return (S) tag;
			}
		}

		// Such a tag was not found
		return null;
	}

	/**
	 * @see net.ruready.common.tag.Taggable#getTagById(java.io.Serializable)
	 */
	public TagItem getTagById(Long tagId)
	{
		// Null check and optimization
		if (tagId == null)
		{
			return null;
		}

		for (TagItem tag : tags)
		{
			if (tagId.equals(tag.getId()))
			{
				return tag;
			}
		}
		return null;
	}

	/**
	 * @see net.ruready.common.tag.Taggable#getTags(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <S extends TagItem> List<S> getTags(Class<S> tagClass)
	{
		List<S> tagList = new ArrayList<S>();

		for (TagItem tag : tags)
		{
			if (tag.getItemClass() == tagClass)
			{
				tagList.add((S) tag);
			}
		}

		return tagList;
	}

	/**
	 * @see net.ruready.common.tag.Taggable#removeTag(java.io.Serializable)
	 */
	public void removeTag(Long tagId)
	{
		TagItem tag = getTagById(tagId);
		if (tag != null)
		{
			logger.debug("Removing tag '" + tag.getName() + "' from item '"
					+ this.getName() + "'");
			// tag.setTaggable(null);
			tags.remove(tag);
		}
	}

	/**
	 * @see net.ruready.common.tag.Taggable#removeTags(java.lang.Class)
	 */
	public void removeTags(Class<? extends TagItem> tagClass)
	{
		// Fetch the relevant tags
		List<? extends TagItem> tagsToRemove = this.getTags(tagClass);

		// Remove the tags
		for (TagItem tag : tagsToRemove)
		{
			// tag.setTaggable(null);
			tags.remove(tag);
		}
	}

	// ========================= METHODS ===================================

	/**
	 * Return the class of this item. This bypasses any Hibernate sub-classing
	 * (decorating) by retrieving the class directly from the item ID.
	 * 
	 * @return
	 */
	public Class<? extends Item> getItemClass()
	{
		return getIdentifier().getItemClass();
	}

	/**
	 * Returns the child item type of this item type. Delegates to
	 * <code>ItemType</code>.
	 * 
	 * @return the child type of this item type. Delegates to
	 *         <code>ItemType</code>.
	 */
	public final ItemType getChildType()
	{
		return getIdentifier().getChildType();
	}

	/**
	 * Is this node a leaf or not. This happens if and only if the item's child
	 * type is <code>null</code>.
	 * 
	 * @return the child type of this item type. Delegates to
	 *         <code>ItemType</code>.
	 */
	public final boolean isLeaf()
	{
		return (getChildType() == null);
	}

	/**
	 * Is this item editable through a standard edit page, or can we edit it
	 * only using some custom pages (e.g. by searching for items of this type
	 * and clicking on rows from the result set table). Default to
	 * <code>true</code>.
	 * 
	 * @return is this item editable through a standard edit page
	 */
	public boolean isEditAccessible()
	{
		return true;
	}

	/**
	 * Is this type of item unique (i.e. do we allow at most one instance of
	 * this type to exist in the Item hierarchy nor not).
	 * 
	 * @return is this type of item unique
	 */
	public boolean isUnique()
	{
		return false;
	}

	/**
	 * Is this type of item identifiable by a unique name.
	 * 
	 * @return is this type of item identifiable by a unique name
	 */
	public boolean isUniqueName()
	{
		return false;
	}

	/**
	 * A factory method that creates a child type for this item. The children of
	 * an item are <code>Item</code>s. This might change to a more concrete
	 * type at a later stage.
	 * 
	 * @param childId
	 *            type of child
	 * @param name
	 *            name of this item
	 * @param comment
	 *            comment on this item
	 * @return a child with the specified name and comment. If the child type is
	 *         not allowed under this item type, returns <code>null</code>
	 */
	public Item createChild(final ItemType childId, final String name,
			final String comment)
	{
		return isValidChildTypeForCreation(childId) ? childId.createItem(name, comment)
				: null;
	}

	/**
	 * Is a child type valid for this item. The children of an item are
	 * <code>Item</code>s. This might change to a more concrete type at a
	 * later stage.
	 * 
	 * @param childId
	 *            type of child comment on this item
	 * @return is a child type valid for this item
	 */
	public boolean isValidChildType(final ItemType childId)
	{
		return ItemTypeUtil.isValidChildType(childId, getIdentifier()
				.getAllValidChildren());
	}

	/**
	 * Is a child type valid for this item and allowed to be created by a
	 * non-system user. The children of an item are <code>Item</code>s. This
	 * might change to a more concrete type at a later stage.
	 * 
	 * @param childId
	 *            type of child comment on this item
	 * @return is a child type valid for this item
	 */
	public boolean isValidChildTypeForCreation(final ItemType childId)
	{
		return ItemTypeUtil.isValidChildType(childId, getIdentifier().getChildren());
	}

	/**
	 * Is a child type valid for this item. The children of an item are
	 * <code>Item</code>s. This might change to a more concrete type at a
	 * later stage.
	 * 
	 * @param child
	 * @return child, if valid
	 * @throws SystemException
	 *             if child is not of a valid child type
	 * @see net.ruready.business.common.tree.entity.Node#addChild(net.ruready.business.common.tree.entity.Node)
	 */
	public Item validateChildType(final Node childRaw)
	{
		if (!Item.class.isAssignableFrom(childRaw.getClass()))
		{
			throw new SystemException("Cannot a non-Item child " + childRaw.getClass()
					+ " under an Item");
		}
		Item child = (Item) childRaw;
		if (!isValidChildType(child.getIdentifier()))
		{
			throw new SystemException("Cannot add child of type " + child.getIdentifier()
					+ " under item type " + getIdentifier());
		}
		return child;
	}

	/**
	 * Is this item a trash can or not.
	 * 
	 * @returns is this item a trash can or not.
	 */
	public boolean isTrash()
	{
		return false;
	}

	/**
	 * Get a super-parent of a certain item type.
	 * 
	 * @param parentType
	 *            parent's type
	 * @return super parent of the current item with ID = parentType
	 */
	public Item getSuperParent(final ItemType parentType)
	{
		int height = ItemTypeUtil.getPathLength(parentType, this.getIdentifier());
		return this.getSuperParent(height);
	}

	/**
	 * Return the ID of a super parent of this item.
	 * 
	 * @param height
	 *            number of tree levels to go up to find the parent
	 * @return <code>null</code> if the parent or is <code>null</code>,
	 *         otherwise returns the id of that parent
	 */
	public Long getSuperParentId(final int height)
	{
		Node superParent = super.getSuperParent(height);
		return (superParent == null) ? null : superParent.getId();
	}

	/**
	 * Return the ID of a super parent of this item by parent type.
	 * 
	 * @param parentType
	 *            parent's type
	 * @return <code>null</code> if the parent or is <code>null</code>,
	 *         otherwise returns the id of that parent
	 */
	public Long getSuperParentId(final ItemType parentType)
	{
		int height = ItemTypeUtil.getPathLength(parentType, this.getIdentifier());
		return this.getSuperParentId(height);
	}

	/**
	 * A helper method to set a unique tag. Setting to null means we will remove
	 * the (unique) tag of type interest collection from the tag list.
	 * Otherwise, we either append a new tag of this type of the tag collection,
	 * or replace the existing tag of this type by the new tag argument passed
	 * into this method.
	 * 
	 * @param tagClass
	 *            tag's type
	 * @param tag
	 *            the new tag to set
	 */
	public void setUniqueTag(final Class<? extends TagItem> tagClass, final TagItem tag)
	{
		// Using this method with tag = null means we are removing the (unique)
		// tag of
		// this type from the tag list.
		this.removeTags(tagClass);

		if (tag != null)
		{
			// Add the new tag; remove its id from the list (just in case,
			// although
			// this shouldn't really be possible)
			this.removeTag(tag.getId());
			this.addTag(tag);
		}
	}

	/**
	 * Read the hierarchy of parents of an item into a list. Parents won't be
	 * fully loaded (e.g. eager fetching of arrays in the specific {@link Item}
	 * sub-classes won't be called). Only basic {@link Item} loading will be
	 * performed as we don't know the specific {@link Item} type of the parents
	 * at this point. This method should be called within a session boundary so
	 * that lazy-loaded parents are automatically loaded upon reference.
	 * 
	 * @return list of parents (top-most is the first in the list; the item is
	 *         the last on the list). If the item is not found, returns an empty
	 *         list
	 */
	public List<Item> getParents()
	{
		// Automatically load parents one-by-one with getParent() calls
		List<Item> parents = new ArrayList<Item>();
		Item item = this;
		do
		{
			// Append parent at the beginning of the list
			parents.add(0, item);
			item = item.getParent();
		}
		while (item != null);

		return parents;
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly()
	{
		return readOnly;
	}

	/**
	 * @param readOnly
	 *            the readOnly to set
	 */
	public void setReadOnly(boolean readOnly)
	{
		this.readOnly = readOnly;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId()
	{
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @return the newItem
	 */
	public boolean isNewItem()
	{
		return newItem;
	}

	/**
	 * @param newItem
	 *            the newItem to set
	 */
	public void setNewItem(boolean newItem)
	{
		this.newItem = newItem;
	}

	/**
	 * @return the newParent
	 */
	public boolean isNewParent()
	{
		return newParent;
	}

	/**
	 * @param newParent
	 *            the newParent to set
	 */
	public void setNewParent(boolean newParent)
	{
		this.newParent = newParent;
	}

	/**
	 * @see net.ruready.business.common.tree.entity.Node#getParent()
	 */
	@Override
	public Item getParent()
	{
		return (Item) super.getParent();
	}

	/**
	 * @see net.ruready.business.common.tree.entity.Node#getSuperParent(int)
	 */
	@Override
	public Item getSuperParent(int height)
	{
		return (Item) super.getSuperParent(height);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Perform initializations at the end of each constructor: initialize the
	 * data structure holding the collection of children, set a default printer,
	 * etc.
	 * 
	 * @see net.ruready.business.common.tree.entity.Node#setUp()
	 */
	@Override
	protected void setUp()
	{
		super.setUp();

		// Default printout style
		setPrinter(new NodePrinter(this));

		// Default children ordering
		setComparatorType(TreeNodeComparatorID.NAME);
	}
}
