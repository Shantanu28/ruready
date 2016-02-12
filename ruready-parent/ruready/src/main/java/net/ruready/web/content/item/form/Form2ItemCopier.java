/*****************************************************************************************
 * Source File: Form2ItemCopier.java
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
import net.ruready.business.content.question.entity.property.QuestionAction;
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.Federation;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.business.content.world.entity.World;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
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
 * @version Aug 10, 2007
 */
public class Form2ItemCopier extends DefaultItemVisitor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Item2FormCopier.class);

	// ========================= FIELDS ====================================

	/**
	 * Source form to copy data from.
	 */
	private final EditItemForm form;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param dest
	 * @param origin
	 */
	private Form2ItemCopier(Item dest, EditItemForm origin)
	{
		super();
		this.form = origin;
		dest.accept(this);
	}

	/**
	 * @param dest
	 * @param origin
	 */
	public static void copyProperties(Item dest, EditItemForm origin)
	{
		new Form2ItemCopier(dest, origin);
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
		// Copy form -> item properties including type conversions

		// {@link Node} fields
		// Can't set an item's ID

		// If there is no version form field, ignore it
		if (form.getLocalVersion() != null)
		{
			item.setLocalVersion(form.getLocalVersion());
		}
		item.setName(form.getName());
		item.setComment(form.getComment());

		// If form's S.N. is cannot be parsed, ignore it.
		int formSerialNo = form.getSerialNoAsInteger();
		if (formSerialNo == CommonNames.MISC.INVALID_VALUE_INTEGER)
		{
			item.setSerialNo(formSerialNo);
		}

		// Item fields
		// TODO: check if we really need to copy item's read only property from
		// the form.
		item.setReadOnly(form.isReadOnly());
		item.setNewItem(form.isNewItem());
		item.setNewParent(form.isNewParent());
		item.setParentId(form.getParentId());
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
		if (form.getPhoneCode() != null)
		{
			item.setPhoneCode(form.getPhoneCodeAsInteger());
		}
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
		item.setUnivCatalogNumber(form.getUnivCatalogNumber());
		item.setSyllabusUrl(form.getSyllabusUrl());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.busness.content.world.entity.City,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(City item)
	{
		this.visit((Item) item);

		// City fields
		item.setCounty(form.getCounty());
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
		item.setContent(form.getContent());
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.Federation,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Federation item)
	{
		this.visit((Item) item);
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
		item.setAddress1(form.getAddress1());
		item.setAddress2(form.getAddress2());
		item.setZipCode(form.getZipCode());
		item.setPhone1(form.getPhone1());
		item.setPhone2(form.getPhone2());
		item.setFax(form.getFax());
		item.setUrl(form.getUrl());
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
		item.setAbbreviation(form.getAbbreviation());
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
		item.setAbbreviation(form.getAbbreviation());
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
		this.visit((Item) item);

		// If form's level is cannot be parsed, ignore it.
		int formLevel = form.getLevelAsInteger();
		if (formLevel != CommonNames.MISC.INVALID_VALUE_INTEGER)
		{
			item.setLevel(formLevel);
		}

		// TODO: This needs to be fine tuned. there are several operations in
		// the previous actions we'd do object status updates on. need to
		// understand best way to handle this now.
		item.updateState(QuestionAction.EDIT);

		// --------------------------------------
		// Data fields
		// --------------------------------------
		if (form instanceof EditQuestionForm)
		{
			EditQuestionForm editQuestionForm = (EditQuestionForm) form;
			item.setFormulation(editQuestionForm.getFormulation());
			item.setVariables(editQuestionForm.getVariables());
			item.setParameters(editQuestionForm.getParameters());
			item.setQuestionPrecision(TextUtil.getStringAsInteger(editQuestionForm
					.getQuestionPrecision()));
			item.setNumberOfChoices(editQuestionForm.getNumberOfChoices());
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
