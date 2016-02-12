/*****************************************************************************************
 * Source File: Item2FormCopier.java
 ****************************************************************************************/
package net.ruready.business.content.item.entity;

import net.ruready.business.common.tree.entity.Node;
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
import net.ruready.business.content.item.exports.DefaultItemVisitor;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.question.entity.Answer;
import net.ruready.business.content.question.entity.Choice;
import net.ruready.business.content.question.entity.Hint;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Federation;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.business.content.world.entity.World;
import net.ruready.common.exception.ApplicationException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Our own "BeanUtil.copyProperties()" that deeps-copies an item into another item. This
 * is manually implemented for all item entities because some objects (e.g.
 * {@link Question} have special children in addition to their item children (hints,
 * answers, etc.).<br>
 * This class assumes that the destination item's arrays are already allocated to the same
 * size as the source item's. It only copies the data over.
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
@Deprecated
public class Item2ItemCopier extends DefaultItemVisitor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Item2ItemCopier.class);

	// ========================= FIELDS ====================================

	/**
	 * Original item.
	 */
	private Item origin;

	/**
	 * Destination item.
	 */
	private Item dest;

	/**
	 * If true, will not copy the item's id and version fields but set them to
	 * <code>null</code>/invalid values using the
	 * {@link Node#removeIdentity()} method.
	 */
	private final boolean removeIdentity;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param dest
	 *            destination item
	 * @param origin
	 *            original item
	 * @param removeIdentity
	 *            if true, will not copy the item's id and version fields but set them to
	 *            <code>null</code>/invalid values
	 */
	private Item2ItemCopier(Item dest, Item origin, final boolean removeIdentity)
	{
		super();
		// Set fields
		this.origin = origin;
		this.dest = dest;
		this.removeIdentity = removeIdentity;

		// Run copy operation
		this.copyProperties();
	}

	/**
	 * A facade that copies an item to an item.
	 * 
	 * @param dest
	 *            destination item
	 * @param origin
	 *            original item
	 * @param removeIdentity
	 *            if true, will not copy the item's id and version fields but set them to
	 *            <code>null</code>/invalid values using the
	 *            {@link Node#removeIdentity()} method
	 */
	public static void copyProperties(Item dest, Item origin, final boolean removeIdentity)
	{
		// Copy origin (item properties only) -> dest (item properties only)
		new Item2ItemCopier(dest, origin, removeIdentity);
	}

	/**
	 * A facade that copies an entire item tree to an item.
	 * 
	 * @param dest
	 *            destination item tree
	 * @param origin
	 *            original item tree
	 * @param removeIdentity
	 *            if true, will not copy the item's id and version fields but set them to
	 *            <code>null</code>/invalid values using the
	 *            {@link Node#removeIdentity()} method
	 */
	public static void copyTree(Item dest, Item origin, final boolean removeIdentity)
	{
		// Copy origin (item properties only) -> dest (item properties only)
		new Item2ItemCopier(dest, origin, removeIdentity);

		// For each origin child, recursively call this method to copy origin.child
		// (entire tree) -> dest.child (entire tree)
		// logger.debug("dest.getChildren() = " + origin.getChildren());
		for (Node originChildRaw : origin.getChildren())
		{
			// Make sure that destChild.class = origin.class so that we can copy
			// properties without throwing an exception
			Item originChild = (Item) originChildRaw;
			Item destChild = originChild.getIdentifier().createItem(null, null);
			Item2ItemCopier.copyTree(destChild, originChild, removeIdentity);
			dest.addChild(destChild);
		}
	}

	// ========================= IMPLEMENTATION: ItemVisitor ===============

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Item item)
	{
		try
		{
			// Copy properties of item that might have been overridden by
			// database values
			PropertyUtils.copyProperties(dest, item);
		}
		catch (Exception e)
		{
			throw new ApplicationException("copyUnder(): Cannot copy from item ("
					+ item.getClass().getCanonicalName() + ")" + " to itemCopy ( "
					+ dest.getClass().getCanonicalName() + " ): " + e.toString());
		}

		// Remove identity
		if (removeIdentity)
		{
			dest.removeIdentity();
		}
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
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Course,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Course item)
	{
		this.visit((Item) item);
	}


	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.City,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(City item)
	{
		this.visit((Item) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.document.entity.Document,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Document item)
	{
		this.visit((File) item);
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
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.world.entity.State,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(State item)
	{
		this.visit((Item) item);
	}

	/**
	 * @see net.ruready.business.content.item.exports.DefaultItemVisitor#visit(net.ruready.business.content.catalog.entity.Subject,
	 *      java.lang.Object[])
	 */
	@Override
	public void visit(Subject item)
	{
		this.visit((Item) item);
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
		// ===============================================================
		// Copy the basic Item properties to the form.
		// ===============================================================
		this.visit((Item) item);
		// Assuming copy is of the same type of item
		Question questionCopy = (Question) dest;

		// ===============================================================
		// Handle the complicated Question form initialization activities
		// ===============================================================

		// Copy answers
		if (item.numAnswers() != questionCopy.numAnswers())
		{
			throw new ApplicationException(
					"Could not copy question because #answers is not the same: origin "
							+ item.numAnswers() + " dest " + questionCopy.numAnswers());
		}
		for (int i = 0; i < item.numAnswers(); i++)
		{
			Answer answer = item.getAnswer(i);
			Answer answerCopy = questionCopy.getAnswer(i);
			try
			{
				PropertyUtils.copyProperties(answerCopy, answer);
				answerCopy.setId(null);
			}
			catch (Exception e)
			{
				throw new ApplicationException("clone() failed: " + e.getMessage());
			}
		}

		// Copy choices
		if (item.numChoices() != questionCopy.numChoices())
		{
			throw new ApplicationException(
					"Could not copy question because #choices is not the same: origin "
							+ item.numChoices() + " dest " + questionCopy.numChoices());
		}
		for (int i = 0; i < item.numChoices(); i++)
		{
			Choice choice = item.getChoice(i);
			Choice choiceCopy = questionCopy.getChoice(i);
			try
			{
				PropertyUtils.copyProperties(choiceCopy, choice);
				choiceCopy.setId(null);
			}
			catch (Exception e)
			{
				throw new ApplicationException("clone() failed: " + e.getMessage());
			}
		}

		// Copy hints
		if (item.numHints() != questionCopy.numHints())
		{
			throw new ApplicationException(
					"Could not copy question because #hints is not the same: origin "
							+ item.numHints() + " dest " + questionCopy.numHints());
		}
		for (int i = 0; i < item.numHints(); i++)
		{
			Hint hint = item.getHint(i);
			Hint hintCopy = questionCopy.getHint(i);
			try
			{
				PropertyUtils.copyProperties(hintCopy, hint);
				hintCopy.setId(null);
			}
			catch (Exception e)
			{
				throw new ApplicationException("clone() failed: " + e.getMessage());
			}
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

	// ========================= METHODS ===================================

	/**
	 * Run the origin-to-dest copy operation.
	 */
	private void copyProperties()
	{
		origin.accept(this);
	}
}
