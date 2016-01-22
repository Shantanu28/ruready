/*****************************************************************************************
 * Source File: ItemPrinter.java
 ****************************************************************************************/
package net.ruready.port.xml.content;

import java.util.ArrayList;
import java.util.List;

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
import net.ruready.business.content.item.util.ItemPrinter;
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
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Prints an item in a nice ASCII format.
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
 * @version Aug 11, 2007
 */
public class AsciiItemPrinter extends DefaultItemVisitor implements ItemPrinter
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AsciiItemPrinter.class);

	// ========================= FIELDS ====================================

	/**
	 * Item to print.
	 */
	private Item item;

	/**
	 * Space indentation amount.
	 */
	private int indent;

	/**
	 * Next-tree-depth indent property value.
	 */
	private int indentNextDepth;

	/**
	 * Item's serial number.
	 */
	private int number;

	/**
	 * A list of printout lines generated for the item.
	 */
	private List<StringBuffer> lines;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param source
	 *            item to print
	 * @indent indent space indentation amount
	 * @param number
	 *            item's serial number
	 */
	public AsciiItemPrinter()
	{
		super();
	}

	// ========================= IMPLEMENTATION: ItemPrinter ===============

	/**
	 * @return
	 * @see net.ruready.business.content.item.util.ItemPrinter#toStringBufferPre()
	 */
	public StringBuffer toStringBufferPre()
	{
		// Print item header
		StringBuffer s = getItemHeader(item);

		// Append and indent printout lines
		for (StringBuffer line : lines)
		{
			s.append(getIndentString(indent)).append(line).append(
					CommonNames.MISC.NEW_LINE_CHAR);
		}
		return s;
	}

	/**
	 * @return
	 * @see net.ruready.business.content.item.util.ItemPrinter#toStringBufferPost()
	 */
	public StringBuffer toStringBufferPost()
	{
		return TextUtil.emptyStringBuffer();
	}

	/**
	 * Sets a new item property value.
	 * 
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item)
	{
		this.item = item;

		// Generate and cache printouts
		lines = new ArrayList<StringBuffer>();
		item.accept(this);
	}

	/**
	 * Sets a new number property value.
	 * 
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number)
	{
		this.number = number;
	}

	/**
	 * Sets a new indent property value.
	 * 
	 * @param indent
	 *            the indent to set
	 */
	public void setIndent(int indent)
	{
		this.indent = indent;
	}

	/**
	 * Sets a new indentNextDepth property value.
	 * 
	 * @param indentNextDepth
	 *            the indentNextDepth to set
	 */
	public void setIndentNextDepth(int indentNextDepth)
	{
		this.indentNextDepth = indentNextDepth;
	}

	/**
	 * Generate an indent string.
	 * 
	 * @param indent1
	 *            indent amount
	 * @return indent string
	 */
	public StringBuffer getIndentString(final int indent1)
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		int indent2 = indent1;
		for (int i = 0; i < indent2; i++)
		{
			s.append(CommonNames.MISC.SPACE_CHAR);
		}
		return s;
	}

	/**
	 * Print an item's header.
	 * 
	 * @param source
	 *            item to print
	 */
	public StringBuffer getItemHeader(final Item source)
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		s.append(getIndentString(indent)).append(source.getIdentifier()).append(" #" + number)
				.append(": ").append(source.getName());

		if (source.getChildType() != null)
		{
			s.append(" (").append(source.getNumChildren()).append(" ").append(
					source.getChildType()).append("S)");
		}
		s.append(CommonNames.MISC.NEW_LINE_CHAR);
		return s;
	}

	/**
	 * @param source
	 * @return
	 * @see net.ruready.business.content.item.util.ItemPrinter#getItemFooter(net.ruready.business.content.item.entity.Item)
	 */
	public StringBuffer getItemFooter(Item source)
	{
		return TextUtil.emptyStringBuffer();
	}

	/**
	 * Print a field.
	 * 
	 * @param name
	 *            field name
	 * @param value
	 *            field value
	 * @return field printout
	 */
	public StringBuffer getFieldString(final String name, final Object value)
	{
		return new StringBuffer(name).append(": ").append(value);
	}

	// ========================= IMPLEMENTATION: ItemVisitor ===============

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Item item1)
	{
		if (!TextUtil.isEmptyTrimmedString(item1.getComment()))
		{
			lines.add(getFieldString("Comment", item1.getComment()));
		}
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Course,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(City item1)
	{
		this.visit((Item) item1);

		if (item1.getCounty() != null)
		{
			lines.add(getFieldString("County", item1.getCounty()));
		}
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Catalog,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Catalog item1)
	{
		this.visit((MainItem) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.ContentType,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(ContentType item1)
	{
		this.visit((Item) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.Country,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Country item1)
	{
		this.visit((Item) item1);

		if (item1.getPhoneCode() != null)
		{
			lines.add(getFieldString("Phone Code", item1.getPhoneCode()));
		}

		if (item1.getCountyString() != null)
		{
			lines.add(getFieldString("County String", item1.getCountyString()));
		}
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Course,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Course item1)
	{
		this.visit((Item) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.document.entity.Document,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Document item1)
	{
		this.visit((File) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.document.entity.DocumentCabinet,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(DocumentCabinet item1)
	{
		this.visit((MainItem) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.Federation,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Federation item1)
	{
		this.visit((Country) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.document.entity.File,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(File item1)
	{
		this.visit((Item) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.document.entity.Folder,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Folder item1)
	{
		this.visit((File) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.main.entity.MainItem,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(MainItem item1)
	{
		this.visit((Item) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.main.entity.Root,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Root item1)
	{
		this.visit((MainItem) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.School,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(School item1)
	{
		this.visit((Item) item1);

		lines.add(getFieldString("Institution Type", item1.getInstitutionType()));
		if (item1.getSector() != null)
		{
			lines.add(getFieldString("Sector", item1.getSector()));
		}
		if (!TextUtil.isEmptyTrimmedString(item1.getPhone1()))
		{
			lines.add(getFieldString("Primary Phone", item1.getPhone1()));
		}
		if (!TextUtil.isEmptyTrimmedString(item1.getPhone2()))
		{
			lines.add(getFieldString("Alternate Phone", item1.getPhone2()));
		}
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.State,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(State item1)
	{
		this.visit((Item) item1);

		if (!TextUtil.isEmptyTrimmedString(item1.getAbbreviation()))
		{
			lines.add(getFieldString("Abbreviation", item1.getAbbreviation()));
		}
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Subject,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Subject item1)
	{
		this.visit((Item) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Topic,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Topic item1)
	{
		this.visit((Item) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.SubTopic,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(SubTopic item1)
	{
		this.visit((Item) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.UnitLink,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Question item1)
	{
		this.visit((Item) item1);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.World,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(World item1)
	{
		this.visit((MainItem) item1);
	}

	// ========================= METHODS ===================================

	// ========================= PRIVATE METHODS ===========================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the item property.
	 * 
	 * @return the item
	 */
	public Item getItem()
	{
		return item;
	}

	/**
	 * Returns the number property.
	 * 
	 * @return the number
	 */
	public int getNumber()
	{
		return number;
	}

	/**
	 * Returns the indent property.
	 * 
	 * @return the indent
	 */
	public int getIndent()
	{
		return indent;
	}

	/**
	 * Returns the indentNextDepth property.
	 * 
	 * @return the indentNextDepth
	 */
	public int getIndentNextDepth()
	{
		return indentNextDepth;
	}

}
