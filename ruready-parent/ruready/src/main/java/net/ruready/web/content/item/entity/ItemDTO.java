package net.ruready.web.content.item.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * A data transfer object for tree nodes that represent CM items.
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
 * @version Nov 22, 2007
 */
public class ItemDTO
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ItemDTO.class);

	// ========================= FIELDS ====================================

	/**
	 * Application's context path.
	 */
	private final String contextPath;

	/**
	 * Message resource bundle.
	 */
	private final MessageSource messageResources;

	/**
	 * Item's unique identifier.
	 */
	private Long id;

	/**
	 * Item's name.
	 */
	private String text;

	/**
	 * Item's type.
	 */
	private ItemType type;

	/**
	 * Is item a leaf in the *item* hierarchy (not the DTO hierarchy).
	 */
	private boolean leaf;

	/**
	 * Should item be expanded in the displayed tree.
	 */
	private boolean expanded = false;

	/**
	 * The internal list of children that we decorate.
	 */
	private final List<ItemDTO> children = new ArrayList<ItemDTO>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a DTO from an item.
	 * 
	 * @param item
	 *            item to copy data from
	 * @param contextPath
	 *            application's context path
	 * @param messageResources
	 *            resource bundle
	 */
	public ItemDTO(final Item item, final String contextPath,
			final MessageSource messageResources)
	{
		super();
		this.contextPath = contextPath;
		this.messageResources = messageResources;
		this.id = item.getId();
		this.text = item.getName();
		this.type = item.getIdentifier();
		this.leaf = !item.hasChildren();
	}

	/**
	 * Construct a DTO with an identifier.
	 * 
	 * @param id
	 *            identifier
	 */
	private ItemDTO(final Long id)
	{
		super();
		this.contextPath = null;
		this.messageResources = null;
		this.id = id;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print an interval.
	 */
	@Override
	public String toString()
	{
		return "id:" + id + " " + "text: \"" + text + "\"" + " leaf: " + leaf;
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ItemDTO other = (ItemDTO) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

	// ========================( IMPLEMENTATION: List ) ====================

	/**
	 * @param index
	 * @param element
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, ItemDTO element)
	{
		children.add(index, element);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(ItemDTO e)
	{
		return children.add(e);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends ItemDTO> c)
	{
		return children.addAll(c);
	}

	/**
	 * @param index
	 * @param c
	 * @return
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends ItemDTO> c)
	{
		return children.addAll(index, c);
	}

	/**
	 * @see java.util.List#clear()
	 */
	public void clear()
	{
		children.clear();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o)
	{
		return children.contains(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c)
	{
		return children.containsAll(c);
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#get(int)
	 */
	public ItemDTO get(int index)
	{
		return children.get(index);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o)
	{
		return children.indexOf(o);
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty()
	{
		return children.isEmpty();
	}

	/**
	 * @return
	 * @see java.util.List#iterator()
	 */
	public Iterator<ItemDTO> iterator()
	{
		return children.iterator();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o)
	{
		return children.lastIndexOf(o);
	}

	/**
	 * @return
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<ItemDTO> listIterator()
	{
		return children.listIterator();
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<ItemDTO> listIterator(int index)
	{
		return children.listIterator(index);
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#remove(int)
	 */
	public ItemDTO remove(int index)
	{
		return children.remove(index);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o)
	{
		return children.remove(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c)
	{
		return children.removeAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c)
	{
		return children.retainAll(c);
	}

	/**
	 * @param index
	 * @param element
	 * @return
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public ItemDTO set(int index, ItemDTO element)
	{
		return children.set(index, element);
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int size()
	{
		return children.size();
	}

	/**
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 * @see java.util.List#subList(int, int)
	 */
	public List<ItemDTO> subList(int fromIndex, int toIndex)
	{
		return children.subList(fromIndex, toIndex);
	}

	/**
	 * @return
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray()
	{
		return children.toArray();
	}

	/**
	 * @param <T>
	 * @param a
	 * @return
	 * @see java.util.List#toArray(T[])
	 */
	public <T> T[] toArray(T[] a)
	{
		return children.toArray(a);
	}

	// ========================= METHODS ====================================

	/**
	 * Return the index of the first encountered element with a specified id.
	 * 
	 * @param anId
	 *            id to find
	 * @return first encountered element with the specified id
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOfId(final Long anId)
	{
		return children.indexOf(new ItemDTO(anId));
	}

	/**
	 * Return the child of the first encountered element with a specified id.
	 * 
	 * @param anId
	 *            id to find
	 * @return first encountered child element with the specified id
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public ItemDTO childWithId(final Long anId)
	{
		return children.get(indexOfId(anId));
	}

	/**
	 * @param childrenOnly
	 * @return
	 * @see net.sf.json.JSONString#toJSONString()
	 */
	public String toJSONString(final boolean childrenOnly)
	{
		return toJSONStringBuffer(childrenOnly).toString();
	}

	// ========================= PRIVATE METHODS ============================

	/**
	 * @param name
	 * @param value
	 * @param quote
	 * @return
	 */
	private static StringBuffer propertyToJSON(final String name, final Object value,
			final boolean quote)
	{
		return TextUtil.emptyStringBuffer().append(name).append(": ").append(
				quote ? "\"" : "").append(value).append(quote ? "\"" : "");
	}

	/**
	 * @return
	 */
	private static StringBuffer JSONSeparator()
	{
		return TextUtil.emptyStringBuffer().append(", ");
	}

	private static StringBuffer collectionToJSON(final Collection<StringBuffer> collection)
	{
		StringBuffer s = TextUtil.emptyStringBuffer().append("[");
		Iterator<StringBuffer> iterator = collection.iterator();
		while (iterator.hasNext())
		{
			StringBuffer childString = iterator.next();
			s.append(childString);
			if (iterator.hasNext())
			{
				s.append(JSONSeparator());
			}
		}
		s.append("]");
		return s;
	}

	/**
	 * Convert an item DTO to JSON.
	 * 
	 * @param childrenOnly
	 *            if true, does not include this item's fields, only its
	 *            children list
	 * @return a string buffer containing the JSON representation of the item
	 *         DTO hierarchy
	 * @see net.sf.json.JSONString#toJSONString()
	 */
	private StringBuffer toJSONStringBuffer(final boolean childrenOnly)
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		if (!childrenOnly)
		{
			s.append("{");
			s.append(propertyToJSON("id", id, false));
			s.append(JSONSeparator());
			s.append(propertyToJSON("text", text, true));
			s.append(JSONSeparator());
			s.append(propertyToJSON("type", type, true));
			s.append(JSONSeparator());
			s.append(propertyToJSON("leaf", leaf, false));
			s.append(JSONSeparator());
			s.append(propertyToJSON("expanded", expanded, false));
			s.append(JSONSeparator());
			// Get icon path
			String altKey = "content.ITEM.icon";
			String key = "content." + type + ".icon";
			String altValue = messageResources.getMessage(altKey, null, "", null);//messageResources.getMessage(altKey);
			String value = messageResources.getMessage(key, null, "", null);//messageResources.getMessage(key);
			String icon = ((value == null) || value.startsWith("???")) ? altValue : value;
			s.append(propertyToJSON("icon", contextPath + icon, true));
		}

		if (!children.isEmpty())
		{
			List<StringBuffer> childrenJSONs = new ArrayList<StringBuffer>();
			for (ItemDTO child : children)
			{
				childrenJSONs.add(child.toJSONStringBuffer(false));
			}
			if (!childrenOnly)
			{
				// Generate JSON property header for the children collection,
				// unless this is the root node in which case
				s.append(JSONSeparator());
				s
						.append(propertyToJSON("children", CommonNames.MISC.EMPTY_STRING,
								false));
			}
			s.append(collectionToJSON(childrenJSONs));
		}

		if (!childrenOnly)
		{
			s.append("}");
		}
		return s;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the id property.
	 * 
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Set a new value for the id property.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Return the text property.
	 * 
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Set a new value for the text property.
	 * 
	 * @param text
	 *            the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Return the children property.
	 * 
	 * @return the children
	 */
	public List<ItemDTO> getChildren()
	{
		return children;
	}

	/**
	 * Return the type property.
	 * 
	 * @return the type
	 */
	public ItemType getType()
	{
		return type;
	}

	/**
	 * Set a new value for the type property.
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(ItemType type)
	{
		this.type = type;
	}

	/**
	 * Return the leaf property.
	 * 
	 * @return the leaf
	 */
	public boolean isLeaf()
	{
		return leaf;
	}

	/**
	 * Set a new value for the leaf property.
	 * 
	 * @param leaf
	 *            the leaf to set
	 */
	public void setLeaf(boolean leaf)
	{
		this.leaf = leaf;
	}

	/**
	 * Return the expanded property.
	 * 
	 * @return the expanded
	 */
	public boolean isExpanded()
	{
		return expanded;
	}

	/**
	 * Set a new value for the expanded property.
	 * 
	 * @param expanded
	 *            the expanded to set
	 */
	public void setExpanded(boolean expanded)
	{
		this.expanded = expanded;
	}

	/**
	 * Returns the messageResources property.
	 * 
	 * @return the messageResources
	 */
	public MessageSource getMessageResources()
	{
		return messageResources;
	}

}
