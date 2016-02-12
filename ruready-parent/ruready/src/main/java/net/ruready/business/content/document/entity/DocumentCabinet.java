/*****************************************************************************************
 * Source File: Cabinet.java
 ****************************************************************************************/
package net.ruready.business.content.document.entity;

import javax.persistence.Entity;

import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.entity.MainItemType;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.business.content.main.entity.MainItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The root of the file system hierarchy in the content management system.
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
 * @version Jul 25, 2007
 */
@Entity
public class DocumentCabinet extends MainItem
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
	private static final Log logger = LogFactory.getLog(DocumentCabinet.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty Cabinet whose items are held as a sorted tree with toggling. Must
	 * be public because <code>Cabinet</code> is a JavaBean.
	 */
	public DocumentCabinet()
	{
		super();
	}

	/**
	 * Construct a Cabinet with a name and comment. The unique name identifier is also set
	 * to this name.
	 * 
	 * @param name
	 *            name of this Cabinet
	 * @param comment
	 *            comment on this Cabinet
	 */
	public DocumentCabinet(String name, String comment)
	{
		super(name, comment);
	}

	// ========================= IMPLEMENTATION: Visitable<MathTokenTraversalVisitor> ====

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

	// ========================= METHODS ===================================

	/**
	 * @return the type of this item
	 */
	@Override
	public ItemType getIdentifier()
	{
		return ItemType.DOCUMENT_CABINET;
	}

	// ========================= IMPLEMENTATION: MainItem ===================

	/**
	 * @return the type of this MainItem
	 */
	@Override
	public MainItemType getMainItemType()
	{
		return MainItemType.DOCUMENT_CABINET;
	}

	// ========================= GETTERS & SETTERS ==========================

}
