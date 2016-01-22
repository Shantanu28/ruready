/*****************************************************************************************
 * Source File: SubTopicMenuGroupForm.java
 ****************************************************************************************/
package net.ruready.web.content.tag.form;

import net.ruready.business.content.interest.entity.Interest;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.business.content.util.exports.AbstractItemUtilBD;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.entity.Resettable;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.ArrayUtil;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.content.item.imports.StrutsItemUtilBD;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Contains drop-down menu and multi-box tag fields of an item. This is
 * currently used in editing a {@link Question}.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 3, 2007
 */
public class TagMenuForm implements ValueBean<Item>, PubliclyCloneable, Resettable
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
	private static final Log logger = LogFactory.getLog(TagMenuForm.class);

	// ========================= FIELDS ====================================

	// -----------------------------------
	// Drop-down menu data: selections
	// -----------------------------------

	/**
	 * Selected IDs of CMS interest tag objects.
	 */
	private String[] interestSelection;

	/**
	 * Selected IDs of CMS concept tag objects.
	 */
	// private String[] conceptSelection;
	/**
	 * Selected IDs of CMS skill tag objects.
	 */
	// private String[] skillSelection;
	// -----------------------------------
	// Drop-down menu/multi-box data
	// -----------------------------------
	/**
	 * Drop-down menu option list of interest collections.
	 */
	private OptionList interestOptions;

	/**
	 * Drop-down menu option list of concept collections.
	 */
	// private OptionList conceptOptions;
	/**
	 * Drop-down menu option list of skill collections.
	 */
	// private OptionList skillOptions;
	// ========================= CONSTRUCTORS ==============================
	/**
	 * Initialize an empty form.
	 */
	public TagMenuForm()
	{
		// We might need to initialize arrays like this:
		// interestSelection = new String[] {};
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TagMenuForm clone()
	{
		TagMenuForm dest = new TagMenuForm();
		try
		{
			PropertyUtils.copyProperties(dest, this);
		}
		catch (Exception e)
		{
			logger.error("Could not clone form");
		}
		return dest;
	}

	// ========================= IMPLEMENTATION: ValueBean =================

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyFrom(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyFrom(Item valueObject, final ApplicationContext context)
	{
		logger.debug("copyFrom()");

		// Convert from question parent property to drop-down menu
		// value properties
		switch (valueObject.getIdentifier())
		{
			case QUESTION:
			{
				Question question = (Question) valueObject;
				// ===========================================================
				// Convert from item properties to drop-down menu
				// value properties
				// ===========================================================

				// Interest property
				interestSelection = ArrayUtil.listToStringArray(question
						.getTags(Interest.class));

				// // Concept property
				// conceptSelection = ArrayUtil.listToStringArray(question
				// .getTags(Concept.class));
				//
				// // Skill property
				// skillSelection = ArrayUtil.listToStringArray(question
				// .getTags(Skill.class));

				break;
			}

			default:
			{
				// If this is not a Question, do nothing
				break;
			}
		} // switch (vo.getIdentifier())
	}

	/**
	 * @param valueObject
	 * @param applicationContext
	 * @see net.ruready.common.eis.entity.ValueBean#copyTo(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyTo(Item valueObject, final ApplicationContext context)
	{
		logger.debug("copyTo()");
		switch (valueObject.getIdentifier())
		{
			case QUESTION:
			{
				Question question = (Question) valueObject;
				User user = (User) context.get(WebAppNames.CONTEXT.USER);

				// =================================================================
				// Convert drop-down menu selection IDs to Question fields
				// =================================================================

				// Interest property
				this.setTagsFromSelection(interestSelection, question, Interest.class,
						ItemType.INTEREST, context, user);

				// // Concept property
				// this.setTagsFromSelection(conceptSelection, question,
				// Concept.class,
				// ItemType.CONCEPT, context, user);
				//
				// // Skill property
				// this.setTagsFromSelection(skillSelection, question,
				// Skill.class,
				// ItemType.SKILL, context, user);

				break;

			}
			default:
			{
				// If this is not a Question, do nothing
				break;
			}
		} // switch (vo.getIdentifier())
	}

	// ========================= IMPLEMENTATION: Resettable ================

	/**
	 * Reset the form fields. This is not the same as the Struts ActionForm
	 * reset method.
	 * 
	 * @see net.ruready.common.eis.Resettable#reset()
	 */
	public void reset()
	{
		// Make sure we satisfy the validation rules
		interestSelection = null;
		// conceptSelection = null;
		// skillSelection = null;
		// We might need to initialize arrays like this:
		// interestSelection = new String[] {};
	}

	// ========================= METHODS ===================================

	/**
	 * Set one of the menu data.
	 * 
	 * @param itemType
	 *            tag item type corresponding to the menu
	 * @param options
	 *            menu option list
	 */
	public void setMenu(final ItemType itemType, final OptionList options)
	{
		switch (itemType)
		{
			case INTEREST:
			{
				this.setInterestOptions(options);
				break;
			}

				// case CONCEPT:
				// {
				// this.setConceptOptions(options);
				// break;
				// }
				//
				// case SKILL:
				// {
				// this.setSkillOptions(options);
				// break;
				// }

			default:
			{
				logger.warn("Unsupported item type " + itemType);
				break;
			}
		}

	}

	/**
	 * Set one of the menu ids.
	 * 
	 * @param itemType
	 *            tag item type corresponding to the menu
	 * @param itemSelection
	 *            id of selected item
	 */
	public void setMenuSelection(final ItemType itemType, final String[] itemSelection)
	{
		switch (itemType)
		{
			case INTEREST:
			{
				this.setInterestSelection(itemSelection);
				break;
			}

				// case CONCEPT:
				// {
				// this.setConceptSelection(itemSelection);
				// break;
				// }
				//
				// case SKILL:
				// {
				// this.setSkillSelection(itemSelection);
				// break;
				// }

			default:
			{
				logger.warn("Unsupported item type " + itemType);
				break;
			}
		}

	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Add tags of a certain {@link TagItem} sub-type to a question object based
	 * on a corresponding menu selection in the form.
	 * 
	 * @param <T>
	 *            tag class
	 * @param selection
	 *            menu selection of IDs of the tag type under consideration
	 * @param question
	 *            question object to update
	 * @param tagClass
	 *            tag class type
	 * @param tagType
	 *            corresponding tag item type
	 * @param user
	 *            user requesting the operations
	 */
	private <T extends TagItem> void setTagsFromSelection(final String[] selection,
			final Question question, final Class<T> tagClass, final ItemType tagType,
			final ApplicationContext context, final User user)
	{
		// Remove current tags
		question.removeTags(tagClass);

		// Add tags from form if found
		if (selection != null)
		{
			AbstractItemUtilBD bdItemUtil = new StrutsItemUtilBD(context, user);
			for (String tagIdStr : selection)
			{
				TagItem tag = (TagItem) bdItemUtil.findItemById(TextUtil
						.getStringAsLong(tagIdStr), tagType);
				question.addTag(tag);
			}
		}
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the interestSelection
	 */
	public String[] getInterestSelection()
	{
		return interestSelection;
	}

	/**
	 * @param interestSelection
	 *            the interestSelection to set
	 */
	public void setInterestSelection(String[] interestSelection)
	{
		this.interestSelection = interestSelection;
	}

	/**
	 * @return the interestOptions
	 */
	public OptionList getInterestOptions()
	{
		return interestOptions;
	}

	/**
	 * @param interestOptions
	 *            the interestOptions to set
	 */
	public void setInterestOptions(OptionList interestOptions)
	{
		this.interestOptions = interestOptions;
	}
}
