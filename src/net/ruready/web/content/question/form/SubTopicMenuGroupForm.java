/*****************************************************************************************
 * Source File: SubTopicMenuGroupForm.java
 ****************************************************************************************/
package net.ruready.web.content.question.form;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.util.exports.AbstractItemUtilBD;
import net.ruready.business.content.util.util.ItemTypeUtil;
import net.ruready.common.eis.entity.Resettable;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsItemUtilBD;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Contains drop-down menu fields of the question's item hierarchy, and related
 * common operations that appear in search and edit question forms.
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
public class SubTopicMenuGroupForm implements ValueBean<Item>, PubliclyCloneable,
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
	private static final Log logger = LogFactory.getLog(SubTopicMenuGroupForm.class);

	// ========================= FIELDS ====================================

	// -------------------------
	// Drop-down menu data: IDs
	// -------------------------

	/**
	 * IDs of CMS hierarchy objects: subject.
	 */
	private String subjectId;

	/**
	 * IDs of CMS hierarchy objects: course.
	 */
	private String courseId;

	/**
	 * IDs of CMS hierarchy objects: topic.
	 */
	private String topicId;

	/**
	 * IDs of CMS hierarchy objects: sub-topic.
	 */
	private String subTopicId;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize an empty form.
	 */
	public SubTopicMenuGroupForm()
	{

	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print this form bean.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return StrutsUtil.formToString(this);
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SubTopicMenuGroupForm clone()
	{
		SubTopicMenuGroupForm dest = new SubTopicMenuGroupForm();
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
	 * @param applicationContext
	 * @see net.ruready.common.eis.entity.ValueBean#copyFrom(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyFrom(Item valueObject, final ApplicationContext context)
	{
		logger.debug("copyFrom()");
		Question question = (Question) valueObject;

		// Convert from question parent property to drop-down menu
		// value properties
		this.initMenuProperties(question, context);
	}

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyTo(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyTo(Item valueObject, final ApplicationContext context)
	{
		logger.debug("copyTo()");
		Question question = (Question) valueObject;

		// =================================================================
		// Topic conversion from drop-down menu value (or by its ID)
		// =================================================================
		if (getSubTopicIdAsLong() == CommonNames.MISC.INVALID_VALUE_LONG)
		{
			question.removeFromParent();
		}
		else
		{
			// Make sure to update the new parent flag in case we move the
			// question under
			// another parent node in EditItemFullAction.
			logger.debug("question.parentId " + question.getParentId()
					+ " form.subTopicId " + subTopicId + " ==? "
					+ (question.getParentId() == getSubjectIdAsLong()) + " equal? "
					+ (subTopicId.equals(question.getParentId())));
			if (!subTopicId.equals(question.getParentId()))
			{
				logger.debug("Setting new parent flag");
				question.setNewParent(true);
			}
			question.setParentId(getSubTopicIdAsLong());
		}
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
		subjectId = null;
		courseId = null;
		topicId = null;
		subTopicId = null;
	}

	// ========================= METHODS ===================================

	/**
	 * Set one of the hierarchical item drop-down menu's selection id.
	 * 
	 * @param itemType
	 *            item type
	 * @param itemId
	 *            id of selected item
	 */
	public void setMenuId(final ItemType itemType, final long itemId)
	{
		final String itemIdStr = CommonNames.MISC.EMPTY_STRING + itemId;
		switch (itemType)
		{
			case SUBJECT:
			{
				this.setSubjectId(itemIdStr);
				break;
			}

			case COURSE:
			{
				this.setCourseId(itemIdStr);
				break;
			}

			case TOPIC:
			{
				this.setTopicId(itemIdStr);
				break;
			}

			case SUB_TOPIC:
			{
				this.setSubTopicId(itemIdStr);
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
	 * Initialize drop-down menu properties from a question's parentId field.
	 * 
	 * @param question
	 *            the question in question
	 * @param context
	 *            web application context
	 */
	void initMenuProperties(final Question question, final ApplicationContext context)
	{
		// ===========================================================
		// Convert from question parent property to drop-down menu
		// value properties
		// ===========================================================
		subTopicId = (question.getParentId() == null) ? null
				: (CommonNames.MISC.EMPTY_STRING + question.getParentId());
		if (subTopicId == null)
		{
			return;
		}
		AbstractItemUtilBD bdItemUtil = new StrutsItemUtilBD(context, null);
		Item subTopic = bdItemUtil.findItemById(getSubTopicIdAsLong(), ItemType.SUB_TOPIC);
		if (subTopic == null)
		{
			return;
		}

		// Cascade subTopic to all parents

		int topicHeight = ItemTypeUtil.getPathLength(ItemType.TOPIC, ItemType.SUB_TOPIC);
		topicId = CommonNames.MISC.EMPTY_STRING + subTopic.getSuperParentId(topicHeight);

		int courseHeight = ItemTypeUtil.getPathLength(ItemType.COURSE, ItemType.SUB_TOPIC);
		courseId = CommonNames.MISC.EMPTY_STRING
				+ subTopic.getSuperParentId(courseHeight);
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the subjectId property.
	 * 
	 * @return the subjectId
	 */
	public String getSubjectId()
	{
		return subjectId;
	}

	/**
	 * @return the subjectId
	 */
	public long getSubjectIdAsLong()
	{
		return TextUtil.getStringAsLong(subjectId);
	}

	/**
	 * Set a new value for the subjectId property.
	 * 
	 * @param subjectId
	 *            the subjectId to set
	 */
	public void setSubjectId(String subjectId)
	{
		this.subjectId = subjectId;
	}

	/**
	 * Return the courseId property.
	 * 
	 * @return the courseId
	 */
	public String getCourseId()
	{
		return courseId;
	}

	/**
	 * @return the courseId
	 */
	public long getCourseIdAsLong()
	{
		return TextUtil.getStringAsLong(courseId);
	}

	/**
	 * Set a new value for the courseId property.
	 * 
	 * @param courseId
	 *            the courseId to set
	 */
	public void setCourseId(String courseId)
	{
		this.courseId = courseId;
	}

	/**
	 * Return the topicId property.
	 * 
	 * @return the topicId
	 */
	public String getTopicId()
	{
		return topicId;
	}

	/**
	 * @return the topicId
	 */
	public long getTopicIdAsLong()
	{
		return TextUtil.getStringAsLong(topicId);
	}

	/**
	 * Set a new value for the topicId property.
	 * 
	 * @param topicId
	 *            the topicId to set
	 */
	public void setTopicId(String topicId)
	{
		this.topicId = topicId;
	}

	/**
	 * Return the subTopicId property.
	 * 
	 * @return the subTopicId
	 */
	public String getSubTopicId()
	{
		return subTopicId;
	}

	/**
	 * @return the subTopicId
	 */
	public long getSubTopicIdAsLong()
	{
		return TextUtil.getStringAsLong(subTopicId);
	}

	/**
	 * Set a new value for the subTopicId property.
	 * 
	 * @param subTopicId
	 *            the subTopicId to set
	 */
	public void setSubTopicId(String subTopicId)
	{
		this.subTopicId = subTopicId;
	}

}
