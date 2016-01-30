/*****************************************************************************************
 * Source File: DefaultItemVisitor.java
 ****************************************************************************************/
package net.ruready.business.content.item.exports;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.catalog.entity.ContentType;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.catalog.entity.Topic;
import net.ruready.business.content.document.entity.Document;
import net.ruready.business.content.document.entity.DocumentCabinet;
import net.ruready.business.content.document.entity.File;
import net.ruready.business.content.document.entity.Folder;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Federation;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.business.content.world.entity.World;

/**
 * A default empty implementation of an item visitor. All <code>visit()</code> methods
 * do nothing. Includes all item types from all sub-components. This is basically a
 * <i>stub</i>.
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
 * @version Jul 31, 2007
 */
public class DefaultItemVisitor implements ItemVisitor
{
	// ========================= CONSTRUCTORS ==============================

	public DefaultItemVisitor()
	{

	}

	public DefaultItemVisitor(Item item)
	{
		item.accept(this);
	}

	// ========================= IMPLEMENTATION: ItemVisitor ===============

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.document.entity.DocumentCabinet)
	 */
	public void visit(DocumentCabinet item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.catalog.entity.Catalog)
	 */
	public void visit(Catalog item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.catalog.entity.ContentType)
	 */
	public void visit(ContentType item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.world.entity.Country)
	 */
	public void visit(Country item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.catalog.entity.Course)
	 */
	public void visit(Course item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.world.entity.City)
	 */
	public void visit(City item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.document.entity.Document)
	 */
	public void visit(Document item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.world.entity.Federation)
	 */
	public void visit(Federation item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.document.entity.File)
	 */
	public void visit(File item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.document.entity.Folder)
	 */
	public void visit(Folder item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.item.entity.Item)
	 */
	public void visit(Item item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.main.entity.MainItem)
	 */
	public void visit(MainItem item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.main.entity.Root)
	 */
	public void visit(Root item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.world.entity.School)
	 */
	public void visit(School item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.world.entity.State)
	 */
	public void visit(State item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.catalog.entity.Subject)
	 */
	public void visit(Subject item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.catalog.entity.SubTopic)
	 */
	public void visit(SubTopic item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.catalog.entity.Topic)
	 */
	public void visit(Topic item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.question.entity.Question)
	 */
	public void visit(Question item)
	{

	}

	/**
	 * @param item
	 * @see net.ruready.business.content.item.exports.ItemVisitor#visit(net.ruready.business.content.world.entity.World)
	 */
	public void visit(World item)
	{

	}

}
