/*****************************************************************************************
 * Source File: DefaultTrash.java
 ****************************************************************************************/
package net.ruready.business.content.trash.entity;

import javax.persistence.Entity;

import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.entity.MainItemType;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.common.discrete.Identifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A base class for a trash can. Sub-classes may improve or optimize its default
 * implementation.
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
 * @version Aug 12, 2007
 */
@Entity
public class DefaultTrash extends MainItem implements AbstractTrash, Identifier
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
	private static final Log logger = LogFactory.getLog(DefaultTrash.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty item whose items are held as a sorted tree with toggling. Must be
	 * public because <code>DefaultTrash</code> is a JavaBean.
	 */
	protected DefaultTrash()
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
	public DefaultTrash(String name, String comment)
	{
		super(name, comment);
	}

	// ========================= IMPLEMENTATION: Item =========================

	/**
	 * A trash cannot be modified or deleted. Hence this method doesn't have an effect.
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
		return ItemType.DEFAULT_TRASH;
	}

	/**
	 * Is this type of item unique (i.e. do we allow at most one instance of this type to
	 * exist in the Item hierarchy nor not).
	 * 
	 * @return is this type of item unique
	 */
	@Override
	public boolean isUnique()
	{
		return true;
	}

	// ========================= IMPLEMENTATION: AbstractTrash =============

	/**
	 * Manually empty the entire recycle bin.
	 */
	public void clear()
	{
		logger.info("Emptying trash");
		removeAllChilds();
	}

	/**
	 * An automatic cleaning of the recycle bin. Normally called at fixed time intervals.
	 * In this implementation, this method removes all items that have been deleted more
	 * than a certain time ago.
	 */
	public void expunge()
	{
		logger.info("Expunging trash");
	}

	// ========================= IMPLEMENTATION: MainItem ===================

	/**
	 * @return the type of this MainItem
	 */
	@Override
	public MainItemType getMainItemType()
	{
		return MainItemType.DEFAULT_TRASH;
	}

	// ========================= METHODS ===================================

	/**
	 * Is this item a trash can or not.
	 * 
	 * @returns is this item a trash can or not.
	 */
	@Override
	public boolean isTrash()
	{
		return true;
	}

	// ========================= GETTERS & SETTERS ==========================

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

		// A trash can cannot be modified or deleted
		super.setReadOnly(true);
	}
}
