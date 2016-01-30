/*****************************************************************************************
 * Source File: ParametricEvaluationAssemblerFactory.java
 ****************************************************************************************/
package net.ruready.business.user.entity.property;

import net.ruready.common.discrete.Gender;
import net.ruready.common.exception.SystemException;
import net.ruready.common.search.MatchType;
import net.ruready.common.search.SearchCriterion;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;

/**
 * A factory that creates {@link SearchCriterion}s for different user fields.
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
public class UserSearchCriterionFactory
{
	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize an arithmetic parser assembler factory.
	 */
	public UserSearchCriterionFactory()
	{

	}

	// ========================= IMPLEMENTATION: SearchCriterionFactory ====

	/**
	 * @see net.ruready.common.search.SearchCriterionFactory#createSearchCriterion(net.ruready.common.search.SearchField,
	 *      net.ruready.common.search.SearchType, java.lang.Object,
	 *      java.lang.Object[])
	 */
	public SearchCriterion createSearchCriterion(final UserField field,
			final SearchType searchType, final Object fieldValue, final Object... options)
	{
		final String stringValue = fieldValue.toString();
		switch (field)
		{
			case EMAIL:
			case FIRST_NAME:
			case MIDDLE_INITIAL:
			case LAST_NAME:
			case STUDENT_IDENTIFIER:
			{
				// Override searchType using the case sensitivity option
				boolean isCaseSensitive = (Boolean) options[0];
				SearchType actualSearchType = isCaseSensitive ? SearchType.LIKE
						: SearchType.ILIKE;
				return SearchCriterionFactory.createStringExpression(actualSearchType,
						field.getName(), stringValue, MatchType.CONTAINS);
			}

			case GENDER:
			{
				return SearchCriterionFactory.<Gender> createSimpleExpression(searchType,
						Gender.class, field.getName(), Gender.valueOf(stringValue));
			}

			case AGE_GROUP:
			{
				return SearchCriterionFactory.<AgeGroup> createSimpleExpression(
						searchType, AgeGroup.class, field.getName(), AgeGroup
								.valueOf(stringValue));
			}

			case ETHNICITY:
			{
				return SearchCriterionFactory.<Ethnicity> createSimpleExpression(
						searchType, Ethnicity.class, field.getName(), Ethnicity
								.valueOf(stringValue));
			}

			case LANGUAGE:
			{
				return SearchCriterionFactory.<Language> createSimpleExpression(
						searchType, Language.class, field.getName(), Language
								.valueOf(stringValue));
			}

			default:
			{
				throw new SystemException("Unsupported field " + field.getName());
			}
		}
	}
}
