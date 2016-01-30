/*****************************************************************************************
 * Source File: Root.java
 ****************************************************************************************/
package net.ruready.business.content.main.entity;

import javax.persistence.Entity;

import net.ruready.business.common.tree.comparator.TreeNodeComparatorID;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.entity.MainItemType;
import net.ruready.business.content.item.exports.ItemVisitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The unique root node of the content management system. Its sub-items are the main
 * catalogs and trash can.
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
public class Root extends MainItem implements UniqueItem
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
	private static final Log logger = LogFactory.getLog(Root.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected Root()
	{
		super();
	}

	/**
	 * Construct a root node with a name and comment. The unique name identifier is also
	 * set to this name.
	 * 
	 * @param name
	 *            name of this root node
	 * @param comment
	 *            comment on this root node
	 */
	public Root(String name, String comment)
	{
		super(name, comment);
	}

	// ========================= IMPLEMENTATION: Item ======================

	/**
	 * A root cannot be modified or deleted. Hence this method doesn't have an effect.
	 * 
	 * @param readOnly
	 *            the readOnly to set
	 */
	@Override
	public void setReadOnly(boolean readOnly)
	{

	}

	/**
	 * @return the type of this item
	 */
	@Override
	public ItemType getIdentifier()
	{
		return ItemType.ROOT;
	}

	/**
	 * Is this type of item unique (i.e. do we allow at most one instance of this type to
	 * exist in the Item hierarchy nor not). Returns <code>true</code> in accordance
	 * with the contract of the {@link UniqueItem} interface.
	 * 
	 * @return is this type of item unique
	 */
	@Override
	public boolean isUnique()
	{
		return true;
	}

	/**
	 * Let a visitor process this item. Part of the TreeVisitor pattern. This calls back
	 * the visitor's <code>visit()</code> method with this item type. Must be overridden
	 * by every item sub-class.
	 * 
	 * @param visitor
	 *            item visitor whose <code>visit()</code> method is invoked
	 */
	@Override
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}

	// ========================= IMPLEMENTATION: MainItem ===================

	/**
	 * @return the type of this MainItem
	 */
	@Override
	public MainItemType getMainItemType()
	{
		return MainItemType.ROOT;
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Perform initializations at the end of each constructor: initialize the data
	 * structure holding the collection of children, set a default printer, etc.
	 * 
	 * @see net.ruready.business.common.tree.entity.Node#setUp()
	 */
	@Override
	protected void setUp()
	{
		super.setUp();

		// A root cannot be modified or deleted
		super.setReadOnly(true);

		// Main items under the root are sorted by main item order number.
		setComparatorType(TreeNodeComparatorID.MAIN_ITEM_ORDER);
	}
}
