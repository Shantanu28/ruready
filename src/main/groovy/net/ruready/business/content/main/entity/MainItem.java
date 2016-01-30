/*****************************************************************************************
 * Source File: MainItem.java
 ****************************************************************************************/
package net.ruready.business.content.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.entity.MainItemType;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.common.pointer.ShallowCloneable;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A main item that sits under the root node of the content management system. Uniquely
 * identified by its {@link #uniqueName} property.
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
 * @version Jul 26, 2007
 */
@Entity
public class MainItem extends Item
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
	private static final Log logger = LogFactory.getLog(MainItem.class);

	// ========================= FIELDS ====================================

	/**
	 * A unique name identifier for this main item.
	 */
	@Column(unique = true, length = 30)
	private String uniqueName;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected MainItem()
	{
		super();
	}

	/**
	 * Construct a main item with a name and comment. The unique name identifier is also
	 * set to this name.
	 * 
	 * @param name
	 *            name of this main item
	 * @param comment
	 *            comment on this main item
	 */
	protected MainItem(String name, String comment)
	{
		super(name, comment);
		this.uniqueName = name;
	}

	// ========================= IMPLEMENTATION: Item ======================

	/**
	 * @return the type of this item
	 */
	@Override
	public ItemType getIdentifier()
	{
		return ItemType.MAIN_ITEM;
	}

	/**
	 * Let a visitor process this item. Part of the Visitor pattern. This calls back the
	 * visitor's <code>visit()</code> method with this item type. Must be overridden by
	 * every item sub-class.
	 * 
	 * @param visitor
	 *            item visitor whose <code>visit()</code> method is invoked
	 */
	@Override
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}

	/**
	 * Print the data and other properties of this node.
	 * 
	 * @return data and properties of this node, represented as a string
	 */
	@Override
	public String printData()
	{
		// TODO: implement the other printData() methods in all item entities
		// using the faster StringBuffer append() rather than String concatenation (the
		// + operation)
		StringBuffer s = new StringBuffer("[");
		s.append(getClass().getSimpleName());
		s.append(" ID ");
		s.append(getId());
		s.append("] ");
		s.append(getName());
		s.append(isReadOnly() ? " READ ONLY" : CommonNames.MISC.EMPTY_STRING);
		return s.toString();
	}

	/**
	 * Is this type of item identifiable by a unique name.
	 * 
	 * @return is this type of item identifiable by a unique name
	 */
	@Override
	public boolean isUniqueName()
	{
		return true;
	}

	/**
	 * Does not copy all fields over - only data fields of this node. Children are not
	 * copied. Identity is not copied.
	 * 
	 * @param destination
	 * @see net.ruready.common.pointer.ShallowCloneable#mergeInto(net.ruready.common.pointer.ShallowCloneable)
	 */
	@Override
	public void mergeInto(final ShallowCloneable destination)
	{
		super.mergeInto(destination);

		MainItem dest = (MainItem) destination;
		dest.uniqueName = uniqueName;
	}

	// ========================= METHODS ====================================

	/**
	 * @return the type of this MainItem
	 */
	public MainItemType getMainItemType()
	{
		return MainItemType.MAIN_ITEM;
	}

	/**
	 * Return a numerical value by which we sort (by descending value) main items under
	 * the root node.
	 * 
	 * @return the order number of this item within a {@link MainItem} list
	 */
	public int getOrder()
	{
		return getMainItemType().getOrder();
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @see net.ruready.business.common.tree.entity.Node#setName(java.lang.String)
	 */
	@Override
	public void setName(String name)
	{
		super.setName(name);
		uniqueName = name;
	}

	/**
	 * @return the uniqueName
	 */
	public String getUniqueName()
	{
		return uniqueName;
	}
}
