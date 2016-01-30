/*****************************************************************************************
 * Source File: QuestionCount.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity;

import java.util.HashMap;
import java.util.Map;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.question.entity.property.QuestionCountType;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.common.math.basic.Pair;
import net.ruready.common.pointer.ValueObject;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.XmlUtil;
import net.ruready.web.common.rl.WebAppNames;

/**
 * A container of {@link Question} object counts within a course. Useful for
 * browsing a course's questions by difficulty level, question type, or other
 * question properties.
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
 * @author Greg Felice <code>&lt;greg.felice@gmail.com&gt;</code>
 * @version Aug 12, 2007
 */
public class QuestionCount implements ValueObject
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A fictitious level number that stores the total number of questions
	 * across all "real" levels. This allows us to use an {@link Integer} key
	 * for levels instead of a more complicated associative array.
	 */
	public static final int LEVEL_TOTAL = 0;

	// ========================= FIELDS ====================================

	/**
	 * Stores the total number of questions for each type and level.
	 */
	private final Map<Pair<QuestionCountType, Integer>, Long> count = new HashMap<Pair<QuestionCountType, Integer>, Long>();

	/**
	 * Parent item under which we look for questions.
	 */
	private final Item parent;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create question counts without relation to a parent.
	 */
	public QuestionCount()
	{
		super();
		this.parent = null;
		initializeCount();
	}

	/**
	 * Create question counts under a parent item.
	 * 
	 * @param parent
	 *            Parent item under which we look for questions
	 */
	public QuestionCount(final Item parent)
	{
		super();
		this.parent = parent;
		initializeCount();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		if (parent == null)
		{
			s.append("Question counts: ");
		}
		else
		{
			s.append("Question counts under '" + parent.getName() + "': ");
		}

		for (QuestionCountType type : QuestionCountType.values())
		{
			s.append(type).append(": {");
			for (int level = LEVEL_TOTAL; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
			{
				s.append("L").append(level).append(": ").append(get(type, level));
				if (level < ContentNames.QUESTION.HIGHEST_LEVEL)
				{
					s.append(", ");
				}
			}
			s.append("} ");
		}
		s.append(" Mean " + getMeanCount() + " Std " + getStdCount());
		return s.toString();
	}

	/**
	 * Result of equality of two question counts.
	 * 
	 * @param o
	 *            The other <code>QuestionCount</code> object.
	 * @return boolean The result of equality.
	 */
	@Override
	public boolean equals(Object obj)
	{
		// Standard checks to ensure the adherence to the general contract of
		// the equals() method
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass()))
		{
			return false;
		}
		// Cast to friendlier version
		QuestionCount other = (QuestionCount) obj;

		// Element-wise count equality, including total counts
		for (QuestionCountType type : QuestionCountType.values())
		{
			for (int level = LEVEL_TOTAL; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
			{
				if (!new Long(this.get(type, level)).equals(other.get(type, level)))
				{
					return false;
				}
			}
		}

		return true;
	}

	// ========================= METHODS ===================================

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public long get(final QuestionCountType type, final int level)
	{
		// If this is a sum across levels, sum up all levels
		if (level == LEVEL_TOTAL)
		{
			long total = 0;
			for (int l = 1; l <= ContentNames.QUESTION.HIGHEST_LEVEL; l++)
			{
				total += get(type, l);
			}
			return total;
		}

		// If this is a a total across types, sum up -question types- (not count
		// types)
		if (type == QuestionCountType.TOTAL)
		{
			long total = 0;
			for (QuestionType questionType : QuestionType.values())
			{
				total += get(questionType.getQuestionCountType(), level);
			}
			return total;
		}

		return count.get(new Pair<QuestionCountType, Integer>(type, level));
	}

	/**
	 * Return the average count over all question types and levels.
	 * 
	 * @return the average count over all question types and levels
	 */
	public double getMeanCount()
	{
		long sum = 0;
		int num = 0;
		for (QuestionType questionType : QuestionType.values())
		{
			for (int level = 1; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
			{
				sum += get(questionType.getQuestionCountType(), level);
				num++;
			}
		}
		return (1.0 * sum) / num;
	}

	/**
	 * Return the standard deviation of counts over all question types and
	 * levels.
	 * 
	 * @return the standard deviation of counts over all question types and
	 *         levels
	 */
	public double getStdCount()
	{
		int num = 0;
		long sum = 0;
		long sumSqr = 0;
		for (QuestionType questionType : QuestionType.values())
		{
			for (int level = 1; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
			{
				long x = get(questionType.getQuestionCountType(), level);
				sum += x;
				sumSqr += x * x;
				num++;
			}
		}
		double mean = (1.0 * sum) / num;
		double variance = (1.0 * (sumSqr - sum * mean)) / (num - 1);
		return Math.sqrt(variance);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public void put(final QuestionCountType type, final int level, final long value)
	{
		count.put(new Pair<QuestionCountType, Integer>(type, level), value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public void remove(final QuestionCountType type, final int level)
	{
		count.remove(new Pair<QuestionCountType, Integer>(type, level));
	}

	/**
	 * Convert question count data to XML. If the parent item is non-null, its
	 * name is appended inside an XML name element.
	 * 
	 * @param groupId
	 *            id of the select group
	 * @return XML representation
	 */
	public StringBuffer toXml(final String groupId)
	{
		StringBuffer xmlString = TextUtil.emptyStringBuffer();

		// Write parent item name
		if (parent != null)
		{
			xmlString.append(XmlUtil.fullTag(WebAppNames.XML.NAME, parent.getName()));
		}

		// Group opening tag
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put(WebAppNames.XML.ATTRIBUTE.ID, groupId);
		xmlString.append(XmlUtil.openTag(WebAppNames.XML.QUESTION_COUNT, attributes));

		// Save mean and standard deviation in their own tags
		xmlString.append(XmlUtil.fullTag(WebAppNames.XML.MEAN,
				CommonNames.MISC.EMPTY_STRING + getMeanCount()));
		xmlString.append(XmlUtil.fullTag(WebAppNames.XML.STD,
				CommonNames.MISC.EMPTY_STRING + getStdCount()));

		// Save counts in separate elements
		for (QuestionCountType type : QuestionCountType.values())
		{
			for (int level = LEVEL_TOTAL; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
			{
				long value = get(type, level);
				xmlString.append(XmlUtil.openTag(WebAppNames.XML.COUNT));
				xmlString.append(XmlUtil.fullTag(WebAppNames.XML.LEVEL,
						CommonNames.MISC.EMPTY_STRING + level));
				xmlString.append(XmlUtil.fullTag(WebAppNames.XML.TYPE,
						CommonNames.MISC.EMPTY_STRING + type));
				xmlString.append(XmlUtil.fullTag(WebAppNames.XML.VALUE,
						CommonNames.MISC.EMPTY_STRING + value));
				xmlString.append(XmlUtil.closeTag(WebAppNames.XML.COUNT));
			}
		}

		// Group closing tag
		xmlString.append(XmlUtil.closeTag(WebAppNames.XML.QUESTION_COUNT));
		return xmlString;
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Initialize count map with zeros.
	 */
	private void initializeCount()
	{
		// Initialize map with zeros
		for (QuestionCountType type : QuestionCountType.values())
		{
			for (int level = LEVEL_TOTAL; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
			{
				put(type, level, 0);
			}
		}
	}

}
