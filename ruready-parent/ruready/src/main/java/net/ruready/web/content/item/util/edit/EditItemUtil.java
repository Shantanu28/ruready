/*****************************************************************************************
 * Source File: EditItemUtil.java
 ****************************************************************************************/
package net.ruready.web.content.item.util.edit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.content.trash.exports.AbstractTrashBD;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.EditItemForm;
import net.ruready.web.content.item.form.EditItemFullForm;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.item.imports.StrutsMainItemBD;
import net.ruready.web.content.item.imports.StrutsTrashBD;
import net.ruready.web.content.item.util.transfer.ItemValidationUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * Centralizes utilities related to generic item editing in the content
 * management component.
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
 * @version Jul 29, 2007
 */
public class EditItemUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EditItemUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private EditItemUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * Perform editing changes on a catalog item and display them in a view
	 * without forwarding to the view.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return ActionErrors
	 */
	public static ActionErrors editItemFull(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("[private] editItem()");
		ActionErrors errors = new ActionErrors();

		// The FindItemFilter should have attached an item
		// to the request by now
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		User user = HttpRequestUtil.findUser(request);

		// ---------------------------------------
		// Read data from view
		// ---------------------------------------
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		EditItemFullForm editItemFullForm = (EditItemFullForm) form;
		// logger.debug("editItemFullForm bean: " + editItemFullForm);
		// logger.debug("item: " + item);
		// Make sure initial form data is in request in case an exception
		// is thrown below
		// editItemFullForm.copyFrom(item);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Copy data: form -> entity (including children)
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		context.put(WebAppNames.CONTEXT.USER, user);
		context.put(WebAppNames.CONTEXT.ACTION_ERRORS, errors);
		context.put(WebAppNames.CONTEXT.BD_ITEM, bdItem);

		editItemFullForm.copyTo(item, context);

		context.remove(WebAppNames.CONTEXT.USER);
		context.remove(WebAppNames.CONTEXT.ACTION_ERRORS);
		context.remove(WebAppNames.CONTEXT.BD_ITEM);

		// logger.debug("Item after copying from form = " + item);

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Update item properties
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		if (logger.isDebugEnabled())
		{
			logger.debug("Updating item");
		}
		if (!item.isReadOnly())
		{
			if (item.isNewItem())
			// If item is flagged as new, add it under the parent.
			{
				bdItem.createUnder(item.getParentId(), item);
				// Reset flag
				if (logger.isDebugEnabled())
				{
					logger.debug("Unsetting new item flag");
				}
				item.setNewItem(false);
			}
			else if (item.isNewParent())
			{
				// If the item's parent is flagged as new, item is old,
				// move item under the new parent.

				// Item oldParent = bdItem.read(Item.class, item.getParent()
				// .getId(), new Item("", ""), true);

				Item newParent = bdItem.read(Item.class, item.getParentId());

				// oldParent.getChildren().remove(item);
				// newParent.getChildren().add(item);

				// List<Item> newParentList = new ArrayList<Item>();
				// newParentList.add(newParent);
				// item.setParents(newParentList);

				// bdItem.update(oldParent, true);
				// bdItem.update(newParent, true);
				// bdItem.update(item, true);

				bdItem.moveUnder(newParent, item);
				// Reset flag
				if (logger.isDebugEnabled())
				{
					logger.debug("Unsetting new parent flag");
				}
				item.setNewParent(false);
			}
			else
			{
				// Otherwise, update old item under the same parent
				bdItem.update(item, true);
			}
			// Copy intermediate results : updated entity -> form
			editItemFullForm.copyFrom(item, context);
			editItemFullForm.copyVersionTo(item);
		}
		logger.debug("Updated item; item V" + item.getVersion() + " local V"
				+ item.getLocalVersion());

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Delete/update item's children in database
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		if (logger.isDebugEnabled())
		{
			logger.debug("Updating children");
		}
		for (Node childRaw : item.getChildren())
		{
			Item child = (Item) childRaw;
			if (!child.isReadOnly())
			{
				// TODO: This if-statement prevents MinorApplicationException.
				// Instead, catch this inside the business logic in a more
				// elegant way.
				bdItem.update(child, true);
				// Copy intermediate results : updated entity -> form
				editItemFullForm.copyFrom(item, context);
				editItemFullForm.copyVersionTo(item);
				logger.debug("Updated child " + child.getName() + "; item V"
						+ item.getVersion() + " local V" + item.getLocalVersion());
			}
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Find and soft-delete all children that are marked for deletion
		// using the trash manager
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Find trash
		AbstractMainItemBD bdMainItem = new StrutsMainItemBD(context, user);
		DefaultTrash trash = bdMainItem.readUnique(DefaultTrash.class);

		AbstractTrashBD bdTrash = new StrutsTrashBD(context, user);
		for (EditItemForm childForm : editItemFullForm.getChildrenForms())
		{
			// logger.debug("childForm = " + childForm);
			if (childForm.isDelete())
			{
				Item child = (Item) item.findChildById(childForm.getId());
				if (child == null)
				{
					if (!errors.get(WebAppNames.KEY.CONTENT_NOT_FOUND).hasNext())
					{
						errors.add(WebAppNames.KEY.CONTENT_NOT_FOUND, new ActionMessage(
								WebAppNames.KEY.CONTENT_NOT_FOUND));
					}
				}
				else if (!child.isReadOnly())
				{
					// TODO: This if statement prevents a
					// MinorApplicationException being thrown. Instead, catch
					// this exception inside the business logic in a more
					// elegant way.

					// ******** This is a hack ***********
					// Prevents a bogus version conflict. We need to replace
					// the entire VC mechanism. It is currently very bad.
					trash.setLocalVersion(null);
					item.setLocalVersion(null);
					child.setLocalVersion(null);

					bdTrash.delete(trash, child);
					// Copy intermediate results : updated entity -> form
					editItemFullForm.copyFrom(item, context);
					editItemFullForm.copyVersionTo(item);

					logger.debug("Deleted child " + child.getName() + "; item V"
							+ item.getVersion() + " local V" + item.getLocalVersion());
				}
			}
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// If additional item found and this item can have a child, add the
		// child
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		EditItemForm newChildForm = editItemFullForm.getNewChildForm();
		// logger.debug("newChildForm " + newChildForm + " isEmpty "
		// + newChildForm.isEmpty());
		if (!newChildForm.isEmpty() && (item.getChildType() != null))
		{
			// ******** This is a hack ***********
			// Prevents a bogus version conflict. We need to replace
			// the entire VC mechanism. It is currently very bad.
			item.setLocalVersion(null);

			// Copy intermediate results : updated entity -> form
			editItemFullForm.copyFrom(item, context);
			editItemFullForm.copyVersionTo(item);

			// Instantiate the correct child type according to the item type.
			// and according to the user's child type selection
			if (logger.isDebugEnabled())
			{
				logger.debug("Found child to add; item V" + item.getVersion()
						+ " local V" + item.getLocalVersion());
				logger.debug("Adding a new child '" + newChildForm.getName() + "'");
			}
			Item newChild = item.createChild(ItemType.valueOf(editItemFullForm
					.getChildType()), newChildForm.getName(), newChildForm.getComment());
			if (newChild == null)
			{
				// We are not allowed to create a new child under this type
				// of item. Add an error message.
				errors.add("cannotaddchild", new ActionMessage(
						"error.content.cannotaddchild"));
			}
			else
			{
				// Populate the rest of the new child's fields
				newChildForm.copyTo(newChild, context);
				// logger.debug("newChild = " + newChild);

				// Validate child name or rename it if necessary
				boolean success = ItemValidationUtil.validateChildCreation(item,
						newChild, errors);

				if (success)
				{
					// Default S.N. of new child: end of list
					newChild.setSerialNo(item.getNumChildren() + 1);
					bdItem.createUnder(item.getId(), newChild);

					// Need to refresh item to see the newly added child
					// item = bdItem.read(Item.class, item.getId());
					logger.debug("New item children: " + item.getChildren());

					// Copy intermediate results : updated entity -> form
					editItemFullForm.copyFrom(item, context);
					editItemFullForm.copyVersionTo(item);

					// If custom index specified, move the new item to
					// that location. Otherwise, put it at the end of the array.
					int newSerialNo = newChildForm.getSerialNoAsInteger();
					if (newSerialNo == CommonNames.MISC.INVALID_VALUE_INTEGER)
					{
						newSerialNo = item.getNumChildren();
					}
					logger.debug("Moving new item to new S.N. " + newSerialNo);
					bdItem.moveChildLocation(item, newChild.getSerialNo(), newSerialNo);

					// Copy intermediate results : updated entity -> form
					editItemFullForm.copyFrom(item, context);
					editItemFullForm.copyVersionTo(item);
				}
			}

			// Need to refresh item to see the newly added child
			item = bdItem.read(Item.class, item.getId());
			logger.debug("New item children: " + item.getChildren());

			// Clear the new item form
			editItemFullForm.setNewChildForm(new EditItemForm());
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// If an item move has been requested, move the child
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		if (!editItemFullForm.isEmptyMoveForm())
		{
			// Type conversions; also convert from serial # to array index
			int moveFrom = editItemFullForm.getMoveFromAsInteger();
			int moveTo = editItemFullForm.getMoveToAsInteger();
			bdItem.moveChildLocation(item, moveFrom, moveTo);
			// Done moving, reset this sub-form
			editItemFullForm.resetMoveForm();
		}

		// Copy intermediate results : updated entity -> form
		// editItemFullForm.copyFrom(item, context);
		// editItemFullForm.copyVersionTo(item);

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Prepare miscellaneous objects for the view
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------

		// Attach the updated item and anything needed for the view to work
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, item);

		// Copy results : updated entity -> form
		editItemFullForm.copyFrom(item, context);
		editItemFullForm.copyVersionTo(item);

		return errors;
	}
}
