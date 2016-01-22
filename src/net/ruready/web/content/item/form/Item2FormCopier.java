/*****************************************************************************************
 * Source File: Item2FormCopier.java
 ****************************************************************************************/
package net.ruready.web.content.item.form;

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
import net.ruready.business.content.item.exports.DefaultItemVisitor;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.Federation;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.business.content.world.entity.World;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.content.question.form.EditQuestionForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Our own "BeanUtil.copyProperties()". This is manually implemented for all
 * item entities because of a Hibernate inheritance problem: Hibernate wraps all
 * entities so that their original getters and setters don't work in
 * copyProperties() for Item sub-classes.
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
public class Item2FormCopier extends DefaultItemVisitor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Item2FormCopier.class);

	// ========================= FIELDS ====================================

	/**
	 * The target form to copy fields into.
	 */
	private EditItemForm form;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an item-to-form copier
	 * 
	 * @param dest
	 *            destination form
	 * @param origin
	 *            source item
	 */
	private Item2FormCopier(EditItemForm dest, Item origin)
	{
		super();
		this.form = dest;
		origin.accept(this);
	}

	public static void copyProperties(EditItemForm dest, Item origin)
	{
		new Item2FormCopier(dest, origin);
	}

	// ========================= IMPLEMENTATION: ItemVisitor ===============

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Item item)
	{
		// logger.debug("visit(Item)");
		// Copy item -> form properties including type conversions

		// {@link Node} fields
		form.setId(item.getId());
		form.setType(item.getType());
		form.setLocalVersion(item.getVersion());

		form.setName(item.getName());
		form.setComment(item.getComment());
		form.setSerialNo(CommonNames.MISC.EMPTY_STRING + item.getSerialNo());

		// Item fields
		form.setReadOnly(item.isReadOnly());
		form.setNewItem(item.isNewItem());
		form.setNewParent(item.isNewParent());
		form.setParentId(item.getParentId());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.document.entity.DocumentCabinet,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(DocumentCabinet item)
	{
		this.visit((MainItem) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Catalog,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Catalog item)
	{
		this.visit((MainItem) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.ContentType,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(ContentType item)
	{
		this.visit((Item) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.Country,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Country item)
	{
		this.visit((Item) item);

		// Country fields
		form.setPhoneCode(CommonNames.MISC.EMPTY_STRING + item.getPhoneCode());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Course,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Course item)
	{
		this.visit((Item) item);

		// Country fields
		form.setUnivCatalogNumber(item.getUnivCatalogNumber());
		form.setSyllabusUrl(item.getSyllabusUrl());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.City,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(City item)
	{
		this.visit((Item) item);

		// City fields
		form.setCounty(item.getCounty());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.document.entity.Document,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Document item)
	{
		this.visit((File) item);

		// Document fields
		form.setContent(item.getContent());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.Federation,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Federation item)
	{
		this.visit((Country) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.document.entity.File,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(File item)
	{
		this.visit((Item) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.document.entity.Folder,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Folder item)
	{
		this.visit((File) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.main.entity.MainItem,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(MainItem item)
	{
		this.visit((Item) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.main.entity.Root,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Root item)
	{
		this.visit((MainItem) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.School,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(School item)
	{
		this.visit((Item) item);

		// School fields
		form.setAddress1(item.getAddress1());
		form.setAddress2(item.getAddress2());
		form.setZipCode(item.getZipCode());
		form.setPhone1(item.getPhone1());
		form.setPhone2(item.getPhone2());
		form.setFax(item.getFax());
		form.setUrl(item.getUrl());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.State,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(State item)
	{
		this.visit((Item) item);

		// State fields
		form.setAbbreviation(item.getAbbreviation());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Subject,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Subject item)
	{
		this.visit((Item) item);

		// Subject fields
		form.setAbbreviation(item.getAbbreviation());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Topic,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Topic item)
	{
		this.visit((Item) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.SubTopic,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(SubTopic item)
	{
		this.visit((Item) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.UnitLink,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Question item)
	{
		/*
		 * copy the basic Item properties to the form.
		 */
		this.visit((Item) item);

		form.setLevel(CommonNames.MISC.EMPTY_STRING + item.getLevel());

		// --------------------------------------
		// Data fields
		// --------------------------------------
		if (form instanceof EditQuestionForm)
		{
			EditQuestionForm editQuestionForm = (EditQuestionForm) form;
			editQuestionForm.setFormulation(item.getFormulation());
			editQuestionForm.setVariables(item.getVariables());
			editQuestionForm.setParameters(item.getParameters());
			editQuestionForm.setQuestionPrecision(CommonNames.MISC.EMPTY_STRING
					+ item.getQuestionPrecision());
			editQuestionForm.setNumberOfChoices(item.getNumberOfChoices());
		}
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.World,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(World item)
	{
		this.visit((MainItem) item);
	}
}
