/*****************************************************************************************
 * Source File: ItemVisitor.java
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
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.Federation;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.business.content.world.entity.World;
import net.ruready.common.tree.TreeVisitor;
import net.ruready.common.visitor.Visitor;

/**
 * An item's {@link TreeVisitor} Visitor Pattern (from "Gang of Four"). Allows overcoming
 * polymporhism and instance-of anti-patterns and problems for the item hierarchy.
 * Includes all item types from all sub-components.
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
public interface ItemVisitor extends Visitor<Item>
{
	// List all sub-classes of Item here. This is similar to a factory.

	void visit(DocumentCabinet item);

	void visit(Catalog item);

	void visit(ContentType item);

	void visit(Country item);

	void visit(Course item);

	void visit(City item);

	void visit(Document item);

	void visit(Federation item);

	void visit(File item);

	void visit(Folder item);

	void visit(MainItem item);

	void visit(Root item);

	void visit(School item);

	void visit(State item);

	void visit(Subject item);

	void visit(Topic item);

	void visit(SubTopic item);

	void visit(Question item);

	void visit(World item);

}
