/*****************************************************************************************
 * Source File: QuestionType.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.ruready.common.discrete.Identifier;

/**
 * Possible question types: academic/creative. Each type corresponds to an
 * integer. Database tables follow these conventions.
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
 * @version Aug 12, 2007
 */
public enum QuestionType implements Identifier
{
	// ========================= ENUMERATED CONSTANTS ======================

	ACADEMIC
	{
		/**
		 * Corresponding to a question with one solution path.
		 * 
		 * @see net.ruready.business.content.question.entity.property.QuestionType#numPaths()
		 */
		@Override
		public int numPaths()
		{
			return 1;
		}

		/**
		 * Return the count type corresponding to this question type.
		 * 
		 * @return the count type corresponding to this question type
		 * @see net.ruready.business.content.question.entity.property.QuestionType#getQuestionCountType()
		 */
		@Override
		public QuestionCountType getQuestionCountType()
		{
			return QuestionCountType.ACADEMIC;
		}

	},

	CREATIVE
	{
		/**
		 * Corresponds to a question with two solution paths and a combined
		 * solution path public int numPaths() { return 3; } Two solution paths;
		 * we do not allow multiple paths per response because it's not general
		 * enough for complicated text+math parsing.
		 * 
		 * @see net.ruready.business.content.question.entity.property.QuestionType#numPaths()
		 */
		@Override
		public int numPaths()
		{
			return 2;
		}

		/**
		 * Return the count type corresponding to this question type.
		 * 
		 * @return the count type corresponding to this question type
		 * @see net.ruready.business.content.question.entity.property.QuestionType#getQuestionCountType()
		 */
		@Override
		public QuestionCountType getQuestionCountType()
		{
			return QuestionCountType.CREATIVE;
		}
	};

	// ========================= FIELDS ====================================

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * Return the object's type. This would normally be the object's
	 * <code>getClass().getSimpleName()</code>, but objects might be wrapped
	 * by Hibernate to return unexpected names. As a rule, the type string
	 * should not contain spaces and special characters.
	 */
	public String getType()
	{
		// The enumerated type's name satisfies all the type criteria, so use
		// it.
		return name();
	}

	// ========================= METHODS ===================================

	/**
	 * Number of paths for this type of question.
	 */
	public abstract int numPaths();

	/**
	 * An array with all types in random order
	 * 
	 * @return An array with all types in random order
	 */
	public static Object[] shuffledValues()
	{
		List<QuestionType> types = new ArrayList<QuestionType>();
		for (QuestionType t : values())
		{
			types.add(t);
		}
		Collections.shuffle(types);
		return types.toArray();
	}

	/**
	 * Return the list of all question types.
	 * 
	 * @return list of all question types
	 */
	public static List<QuestionType> types()
	{
		List<QuestionType> e = new ArrayList<QuestionType>();
		for (QuestionType t : QuestionType.values())
		{
			e.add(t);
		}
		return e;
	}

	/**
	 * Return the count type corresponding to this question type.
	 * 
	 * @return the count type corresponding to this question type
	 */
	public abstract QuestionCountType getQuestionCountType();

	// ========================= GETTERS & SETTERS =========================

}
