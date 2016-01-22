/*****************************************************************************************
 * Source File: QuestionFormat.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.ruready.common.discrete.Identifier;

/**
 * Supported question formats: multiple choice/open ended. Each type corresponds to an
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 11, 2007
 */
public enum QuestionFormat implements Identifier
{
	// ========================= ENUMERATED CONSTANTS ======================

	MULTIPLE_CHOICE
	{

	},

	OPEN_ENDED
	{

	};

	// ========================= FIELDS ====================================

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * Return the object's type. This would normally be the object's
	 * <code>getClass().getSimpleName()</code>, but objects might be wrapped by
	 * Hibernate to return unexpected names. As a rule, the type string should not contain
	 * spaces and special characters.
	 */
	public String getType()
	{
		// The enumerated type's name satisfies all the type criteria, so use
		// it.
		return name();
	}

	// ========================= METHODS ===================================

	/**
	 * An array with all formats in random order
	 * 
	 * @return An array with all formats in random order
	 */
	public static List<QuestionFormat> shuffledValues()
	{
		List<QuestionFormat> formats = new ArrayList<QuestionFormat>();
		for (QuestionFormat t : values())
		{
			formats.add(t);
		}
		Collections.shuffle(formats);
		return formats;
	}

	/**
	 * A shuffled array with equal number of each format.
	 * 
	 * @param n
	 *            size of array. If not integrally divisible by the number of formats,
	 *            formats will occur only approximately equally in the array.
	 * @return A shuffled array with equal number of each format
	 */
	public static Object[] shuffledValues(final int n)
	{
		int numFormats = QuestionFormat.values().length;
		int nextMultiple = numFormats * (int) Math.ceil((1.0 * n) / numFormats);
		int formatFrequency = nextMultiple / numFormats;

		List<QuestionFormat> formats = new ArrayList<QuestionFormat>();
		int count = 0;
		for (int i = 0; i < formatFrequency; i++)
		{
			for (QuestionFormat t : QuestionFormat.values())
			{
				formats.add(t);
				count++;
				if (count == n)
				{
					break;
				}
			}
			if (count == n)
			{
				break;
			}
		}
		Collections.shuffle(formats);
		return formats.toArray();
	}

	public static List<QuestionFormat> types()
	{
		List<QuestionFormat> e = new ArrayList<QuestionFormat>();
		for (QuestionFormat t : QuestionFormat.values())
		{
			e.add(t);
		}
		return e;
	}

	// ========================= GETTERS & SETTERS =========================

}
