/*****************************************************************************************
 * Source File: SubTopicMenuGroupForm.java
 ****************************************************************************************/
package net.ruready.web.content.tag.form;

import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.concept.entity.ConceptCollection;
import net.ruready.business.content.interest.entity.InterestCollection;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.skill.entity.SkillCollection;
import net.ruready.business.content.util.exports.AbstractItemUtilBD;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.entity.Resettable;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.content.item.imports.StrutsItemUtilBD;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Contains drop-down menu tag collection fields of an item. This is currently used in
 * editing a {@link Subject}.
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
public class TagCollectionMenuForm implements ValueBean<Item>, PubliclyCloneable,
		Resettable
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
	private static final Log logger = LogFactory.getLog(TagCollectionMenuForm.class);

	// ========================= FIELDS ====================================

	// -------------------------
	// Drop-down menu data: IDs
	// -------------------------

	/**
	 * ID of CMS interest collection tag object.
	 */
	private Long interestCollectionId;

	/**
	 * ID of CMS concept collection tag object.
	 */
	private Long conceptCollectionId;

	/**
	 * ID of CMS skill collection tag object.
	 */
	private Long skillCollectionId;

	// -------------------
	// Drop-down menu data
	// -------------------
	/**
	 * Drop-down menu option list of interest collections.
	 */
	private OptionList interestCollectionOptions;

	/**
	 * Drop-down menu option list of concept collections.
	 */
	private OptionList conceptCollectionOptions;

	/**
	 * Drop-down menu option list of skill collections.
	 */
	private OptionList skillCollectionOptions;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize an empty form.
	 */
	public TagCollectionMenuForm()
	{

	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TagCollectionMenuForm clone()
	{
		TagCollectionMenuForm dest = new TagCollectionMenuForm();
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
			case SUBJECT:
			{
				Subject subject = (Subject) valueObject;
				initMenuProperties(subject);
				break;
			}
			default:
			{
				// If this is not a Subject, do nothing
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
			case SUBJECT:
			{
				Subject subject = (Subject) valueObject;
				User user = (User) context.get(WebAppNames.CONTEXT.USER);

				// =================================================================
				// Convert drop-down menu selection IDs to Subject fields
				// =================================================================
				AbstractItemUtilBD bdItemUtil = new StrutsItemUtilBD(context, user);

				// Interest collection property
				if (interestCollectionId != null)
				{
					InterestCollection interestCollection = (InterestCollection) bdItemUtil
							.findItemById(interestCollectionId,
									ItemType.INTEREST_COLLECTION);
					subject.setInterestCollection(interestCollection);
				}

				// Concept collection property
				if (conceptCollectionId != null)
				{
					ConceptCollection conceptCollection = (ConceptCollection) bdItemUtil
							.findItemById(conceptCollectionId, ItemType.CONCEPT_COLLECTION);
					subject.setConceptCollection(conceptCollection);
				}

				// Skill collection property
				if (skillCollectionId != null)
				{
					SkillCollection skillCollection = (SkillCollection) bdItemUtil
							.findItemById(skillCollectionId, ItemType.SKILL_COLLECTION);
					subject.setSkillCollection(skillCollection);
				}
				break;
			}
			default:
			{
				// If this is not a Subject, do nothing
				break;
			}
		} // switch (valueObject.getIdentifier())
	}

	// ========================= IMPLEMENTATION: Resettable ================

	/**
	 * Reset the form fields. This is not the same as the Struts ActionForm reset method.
	 * 
	 * @see net.ruready.common.eis.Resettable#reset()
	 */
	public void reset()
	{
		// Make sure we satisfy the validation rules
		interestCollectionId = null;
		conceptCollectionId = null;
		skillCollectionId = null;
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
			case INTEREST_COLLECTION:
			{
				this.setInterestCollectionOptions(options);
				break;
			}

			case CONCEPT_COLLECTION:
			{
				this.setConceptCollectionOptions(options);
				break;
			}

			case SKILL_COLLECTION:
			{
				this.setSkillCollectionOptions(options);
				break;
			}

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
	 * @param itemId
	 *            id of selected item
	 */
	public void setMenuId(final ItemType itemType, final long itemId)
	{
		switch (itemType)
		{
			case INTEREST_COLLECTION:
			{
				this.setInterestCollectionId(itemId);
				break;
			}

			case CONCEPT_COLLECTION:
			{
				this.setConceptCollectionId(itemId);
				break;
			}

			case SKILL_COLLECTION:
			{
				this.setSkillCollectionId(itemId);
				break;
			}

			default:
			{
				logger.warn("Unsupported item type " + itemType);
				break;
			}
		}

	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Initialize drop-down menu properties of this form from a subject's fields.
	 * 
	 * @param subject
	 *            the subject to copy from
	 */
	private void initMenuProperties(Subject subject)
	{
		// ===========================================================
		// Convert from item properties to drop-down menu
		// value properties
		// ===========================================================

		// Interest collection property
		InterestCollection interestCollection = subject.getInterestCollection();
		interestCollectionId = (interestCollection == null) ? null : interestCollection
				.getId();

		// Concept collection property
		ConceptCollection conceptCollection = subject.getConceptCollection();
		conceptCollectionId = (conceptCollection == null) ? null : conceptCollection
				.getId();

		// Skill collection property
		SkillCollection skillCollection = subject.getSkillCollection();
		skillCollectionId = (skillCollection == null) ? null : skillCollection.getId();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the interestCollectionId
	 */
	public Long getInterestCollectionId()
	{
		return interestCollectionId;
	}

	/**
	 * @param interestCollectionId
	 *            the interestCollectionId to set
	 */
	public void setInterestCollectionId(Long interestCollectionId)
	{
		this.interestCollectionId = interestCollectionId;
	}

	/**
	 * @return the conceptCollectionId
	 */
	public Long getConceptCollectionId()
	{
		return conceptCollectionId;
	}

	/**
	 * @param conceptCollectionId
	 *            the conceptCollectionId to set
	 */
	public void setConceptCollectionId(Long conceptCollectionId)
	{
		this.conceptCollectionId = conceptCollectionId;
	}

	/**
	 * @return the skillCollectionId
	 */
	public Long getSkillCollectionId()
	{
		return skillCollectionId;
	}

	/**
	 * @param skillCollectionId
	 *            the skillCollectionId to set
	 */
	public void setSkillCollectionId(Long skillCollectionId)
	{
		this.skillCollectionId = skillCollectionId;
	}

	/**
	 * @return the interestCollectionOptions
	 */
	public OptionList getInterestCollectionOptions()
	{
		return interestCollectionOptions;
	}

	/**
	 * @param interestCollectionOptions
	 *            the interestCollectionOptions to set
	 */
	public void setInterestCollectionOptions(OptionList interestCollectionOptions)
	{
		this.interestCollectionOptions = interestCollectionOptions;
	}

	/**
	 * @return the conceptCollectionOptions
	 */
	public OptionList getConceptCollectionOptions()
	{
		return conceptCollectionOptions;
	}

	/**
	 * @param conceptCollectionOptions
	 *            the conceptCollectionOptions to set
	 */
	public void setConceptCollectionOptions(OptionList conceptCollectionOptions)
	{
		this.conceptCollectionOptions = conceptCollectionOptions;
	}

	/**
	 * @return the skillCollectionOptions
	 */
	public OptionList getSkillCollectionOptions()
	{
		return skillCollectionOptions;
	}

	/**
	 * @param skillCollectionOptions
	 *            the skillCollectionOptions to set
	 */
	public void setSkillCollectionOptions(OptionList skillCollectionOptions)
	{
		this.skillCollectionOptions = skillCollectionOptions;
	}

}
