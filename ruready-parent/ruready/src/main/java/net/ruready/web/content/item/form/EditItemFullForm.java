/*****************************************************************************************
 * Source File: EditItemFullForm.java
 ****************************************************************************************/
package net.ruready.web.content.item.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * CMS item children editing form. Contains a lazy list of the item's children
 * fields and enables adding a new item. MyEclipse Struts
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
 * @version Jul 31, 2007
 */
public class EditItemFullForm extends ValidatorActionForm implements ValueBean<Item>,
		PubliclyCloneable
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
	private static final Log logger = LogFactory.getLog(EditItemFullForm.class);

	// ========================= FIELDS ====================================

	/**
	 * This item's properties (optional on the edit children page).
	 */
	protected EditItemForm itemForm;

	/**
	 * Children collection properties.
	 */
	private List<EditItemForm> childrenForms = new ArrayList<EditItemForm>();

	/**
	 * If the user wants to add an item, he/she fills in these fields.
	 */
	private EditItemForm newChildForm = createEditItemForm();

	/**
	 * Selected child type for a new child to be added under this item.
	 */
	private String childType;

	/**
	 * For optional re-placement of a child within the children list: serial
	 * number of the child before moving it in the list.
	 */
	private String moveFrom;

	/**
	 * For optional re-placement of a child within the children list: serial
	 * number of the child after moving it in the list.
	 */
	private String moveTo;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty form.
	 */
	public EditItemFullForm()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Creating a new form");
		}
		itemForm = createEditItemForm();
	}

	// ========================= IMPLEMENTATION: ValueBean =================

	/**
	 * @param valueObject
	 * @param applicationContext
	 * @see net.ruready.common.eis.entity.ValueBean#copyFrom(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyFrom(Item valueObject, final ApplicationContext context)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("copyFrom()");
		}
		// Manual property copying from a node

		itemForm.copyFrom(valueObject, context);

		// - Make a new copy ("newChildrenForms") of childrenForm
		// - Clear newChildrenForms(i) and copy itemVO.child(i) ->
		// newChildrenForms(i) for all i
		// - Copy transient properties childrenForms(i) -> newChildrenForms(j)
		// if their ids equal
		List<EditItemForm> newChildrenForms = new ArrayList<EditItemForm>();

		// Process children
		for (Node child : valueObject.getChildren())
		{
			EditItemForm newThisChildForm = createEditItemForm();
			newThisChildForm.copyFrom((Item) child, context);
			newChildrenForms.add(newThisChildForm);

			// TODO: this loop is O(n^2) operations where n = # children.
			// This can be reduced to O(n*log(n)) by sorting both children
			// lists.
			EditItemForm childForm = findChildById(newThisChildForm.getId());
			if (childForm != null)
			{
				childForm.copyTransientPropertiesTo(newThisChildForm);
			}
		}

		childrenForms = newChildrenForms;

		// Do NOT copy newChildForm at this point.
	}

	/**
	 * @param valueObject
	 * @param applicationContext
	 * @see net.ruready.common.eis.entity.ValueBean#copyTo(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyTo(Item valueObject, final ApplicationContext context)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("copyTo()");
		}
		// Manual property copying from a node

		// Copy item properties only if this part of the form is present
		if (!itemForm.isEmpty())
		{
			itemForm.copyTo(valueObject, context);
		}

		// Assuming itemVO.children.size = childrenForm.size, otherwise
		// something is wrong. However, this has caused some unnecessary
		// exceptions when refreshing or going back to the edit page.
		// So copy what can be copied only.

		// if (childrenForms.size() != vo.getNumChildren())
		// {
		// throw new ApplicationException(
		// "copyTo(): cannot copy children because childrenForms.size() ("
		// + childrenForms.size() + ") != vo.getNumChildren() ("
		// + itemVO.getNumChildren() + ")");
		// }

		// Copy childrenForms(i) -> itemVO.child(i) for all i's we can process
		final int minSize = Math.min(childrenForms.size(), valueObject.getNumChildren());
		int i = 0;
		for (Node child : valueObject.getChildren())
		{
			childrenForms.get(i).copyTo((Item) child, context);
			i++;
			if (i == minSize)
			{
				break;
			}
		}
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public EditItemFullForm clone()
	{
		EditItemFullForm dest = createEditItemFullForm();
		// Copy our own properties
		try
		{
			PropertyUtils.copyProperties(dest, this);
		}
		catch (Exception e)
		{
			logger.error("Could not clone form");
		}

		// Deep-copy composite form properties
		dest.setItemForm(itemForm.clone());

		dest.setChildrenForms(new ArrayList<EditItemForm>());
		for (EditItemForm childForm : childrenForms)
		{
			dest.getChildrenForms().add(childForm.clone());
		}

		dest.setNewChildForm(newChildForm.clone());

		return dest;
	}

	/**
	 * Copy version numbers from the item and its children into this form.
	 */
	public void copyVersionFrom(Item itemVO)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("copyVersionFrom()");
		}
		itemForm.setLocalVersion(itemVO.getVersion());

		// - Make a new copy ("newChildrenForms") of childrenForm
		// - Clear newChildrenForms(i) and copy itemVO.child(i) ->
		// newChildrenForms(i) for all i
		// - Copy transient properties childrenForms(i) ->
		// newChildrenForms(j)
		// if their ids equal
		for (Node child : itemVO.getChildren())
		{
			// TODO: this loop is O(n^2) operations where n = # children.
			// This can be reduced to O(n*log(n)) by sorting both children
			// lists.
			EditItemForm childForm = findChildById(child.getId());
			if (childForm != null)
			{
				childForm.setLocalVersion(child.getVersion());
			}
		}

		// Do NOT copy newChildForm at this point.

	}

	/**
	 * Copy local version numbers from the form and its children forms to the
	 * item and its children.
	 */
	public void copyVersionTo(Item itemVO)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("copyVersionFrom()");
		}
		itemVO.setLocalVersion(itemForm.getLocalVersion());

		// - Make a new copy ("newChildrenForms") of childrenForm
		// - Clear newChildrenForms(i) and copy itemVO.child(i) ->
		// newChildrenForms(i) for all i
		// - Copy transient properties childrenForms(i) ->
		// newChildrenForms(j)
		// if their ids equal
		for (Node child : itemVO.getChildren())
		{
			// TODO: this loop is O(n^2) operations where n = # children.
			// This can be reduced to O(n*log(n)) by sorting both children
			// lists.
			EditItemForm childForm = findChildById(child.getId());
			if (childForm != null)
			{
				child.setLocalVersion(childForm.getLocalVersion());
			}
		}

		// Do NOT copy newChildForm at this point.

	}

	// ========================= METHODS ===================================

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		// If a setup method has been requested, skip validation
		if (!StrutsUtil.isMethodValidated(request))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("validate() overridden");
			}
			return null;
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("validate()");
		}
		// Carry out all the Validator framework validations
		ActionErrors errors = super.validate(mapping, request);

		// Custom validations should be added here
		return errors;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		// Carry out all the Validator framework resets
		super.reset(mapping, request);

		// Custom reset operations should be added here
		if (logger.isDebugEnabled())
		{
			logger.debug("reset");
		}

		itemForm.reset();

		childrenForms = ListUtils.lazyList(new ArrayList<EditItemForm>(), new Factory()
		{
			public Object create()
			{
				return createEditItemForm();
			}
		});

		newChildForm.reset();
		resetMoveForm();
	}

	/**
	 * Reset the moveTo and moveFrom parameters.
	 */
	public void resetMoveForm()
	{
		moveTo = null;
		moveFrom = null;
	}

	/**
	 * Check if the item moving sub-form is empty or partially filled.
	 * 
	 * @return is the form empty or partially filled
	 */
	public boolean isEmptyMoveForm()
	{
		return (TextUtil.isEmptyTrimmedString(moveTo) && TextUtil
				.isEmptyTrimmedString(moveFrom));
	}

	/**
	 * Print this form bean.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String s = ReflectionToStringBuilder.toString(this)
				+ CommonNames.MISC.NEW_LINE_CHAR;
		for (EditItemForm childForm : childrenForms)
		{
			s = s + childForm.toString() + CommonNames.MISC.NEW_LINE_CHAR;
		}
		s = s + newChildForm.toString() + CommonNames.MISC.NEW_LINE_CHAR;
		return s;
	}

	/**
	 * Return the child form of this form with a specified child item ID.
	 * 
	 * @param childId
	 *            unique identifier of a child
	 * @return the corresponding child if found, or null if not
	 */
	protected EditItemForm findChildById(long childId)
	{
		for (EditItemForm child : childrenForms)
		{
			if (child.getId() == childId)
			{
				return child;
			}
		}
		return null;
	}

	/**
	 * factory method for EditItemForms. Can be overridden by children of this
	 * class.
	 * 
	 * @return
	 */
	protected EditItemForm createEditItemForm()
	{
		return new EditItemForm();
	}

	/**
	 * Factory method for this class, used by clone method.
	 * 
	 * @return
	 */
	protected EditItemFullForm createEditItemFullForm()
	{
		return new EditItemFullForm();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the childType property.
	 * 
	 * @return the childType
	 */
	public String getChildType()
	{
		return childType;
	}

	/**
	 * Sets a new childType property value.
	 * 
	 * @param childType
	 *            the childType to set
	 */
	public void setChildType(String childType)
	{
		this.childType = childType;
	}

	/**
	 * Returns the moveFrom property.
	 * 
	 * @return the moveFrom
	 */
	public String getMoveFrom()
	{
		return moveFrom;
	}

	/**
	 * Returns the moveFrom property.
	 * 
	 * @return the moveFrom
	 */
	public Integer getMoveFromAsInteger()
	{
		return TextUtil.getStringAsInteger(moveFrom);
	}

	/**
	 * Sets a new moveFrom property value.
	 * 
	 * @param moveFrom
	 *            the moveFrom to set
	 */
	public void setMoveFrom(String moveFrom)
	{
		this.moveFrom = moveFrom;
	}

	/**
	 * Returns the moveTo property.
	 * 
	 * @return the moveTo
	 */
	public String getMoveTo()
	{
		return moveTo;
	}

	/**
	 * Returns the moveTo property.
	 * 
	 * @return the moveTo
	 */
	public Integer getMoveToAsInteger()
	{
		return TextUtil.getStringAsInteger(moveTo);
	}

	/**
	 * Sets a new moveTo property value.
	 * 
	 * @param moveTo
	 *            the moveTo to set
	 */
	public void setMoveTo(String moveTo)
	{
		this.moveTo = moveTo;
	}

	/**
	 * Returns the newChildForm property.
	 * 
	 * @return the newChildForm
	 */
	public EditItemForm getNewChildForm()
	{
		return newChildForm;
	}

	/**
	 * Sets a new newChildForm property value.
	 * 
	 * @param newChildForm
	 *            the newChildForm to set
	 */
	public void setNewChildForm(EditItemForm newChildForm)
	{
		this.newChildForm = newChildForm;
	}

	/**
	 * Returns the childrenForms property.
	 * 
	 * @return the childrenForms
	 */
	public List<EditItemForm> getChildrenForms()
	{
		return childrenForms;
	}

	/**
	 * Sets a new childrenForms property value.
	 * 
	 * @param childrenForms
	 *            the childrenForms to set
	 */
	public void setChildrenForms(List<EditItemForm> childrenForms)
	{
		this.childrenForms = childrenForms;
	}

	/**
	 * Returns the itemForm property.
	 * 
	 * @return the itemForm
	 */
	public EditItemForm getItemForm()
	{
		return itemForm;
	}

	/**
	 * Sets a new itemForm property value.
	 * 
	 * @param itemForm
	 *            the itemForm to set
	 */
	public void setItemForm(EditItemForm itemForm)
	{
		this.itemForm = itemForm;
	}

}
