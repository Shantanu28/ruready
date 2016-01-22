/*****************************************************************************************
 * Source File: StrutsUtil.java
 ****************************************************************************************/
package net.ruready.business.content.demo.util;

import java.util.Collection;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.content.demo.manager.DemoCatalogCreator;
import net.ruready.business.content.demo.manager.DemoTagCabinetCreator;
import net.ruready.business.content.demo.manager.DemoWorldCreator;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.question.entity.Answer;
import net.ruready.business.content.question.entity.Choice;
import net.ruready.business.content.question.entity.Hint;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.business.content.world.entity.World;
import net.ruready.common.exception.SystemException;
import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Centralizes creation of mock {@link Item} objects and hierarchies.
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
public class ItemDemoUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ItemDemoUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private ItemDemoUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * Create a base object of this main item type using a hard-coded tree of
	 * nodes. This is useful for testing and site initialization defaulting.
	 * This is a factory method that uses reflection to decide which object to
	 * create based on the generic parameter.
	 * 
	 * @param <T>
	 *            type of main item
	 * @param uniqueName
	 *            base hierarchy's name
	 * @param depenencies
	 *            main items that this main item depend on. See the constructors
	 *            of the individual demo creator classes in this package.
	 * @return the base main item
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends MainItem> T createBase(final Class<T> itemClass,
			final String uniqueName, final MainItem... depenencies)
	{
		// Switch on types
		if (itemClass == Catalog.class)
		{
			return (T) new DemoCatalogCreator((TagCabinet) depenencies[0])
					.createItem(uniqueName);
		}
		else if (itemClass == World.class)
		{
			return (T) new DemoWorldCreator().createItem(uniqueName);
		}
		else if (itemClass == TagCabinet.class)
		{
			return (T) new DemoTagCabinetCreator().createItem(uniqueName);
		}
		else
		{
			throw new SystemException("Cannot create a base object for type " + itemClass);
		}
	}

	/**
	 * Create a mock question object with a specified level and type.
	 * 
	 * @param titleString
	 *            title string that appears in the question's name
	 * @param questionType
	 *            question type (academic/creative)
	 * @param level
	 *            difficulty level
	 * @return mock question object
	 */
	public static Question createMockQuestion(final String titleString,
			final QuestionType questionType, final int level)
	{
		final String prefixString = titleString;

		// Create basic question data
		Question question = new Question();
		question.setName(prefixString + " " + questionType + " " + level);
		question.setComment(null);
		question.setQuestionType(questionType);
		question.setLevel(level);
		question.setFormulation(question.getName() + " - formulation");
		question.setNumberOfChoices(3);
		question.setParameters("a 1:2");
		question.setQuestionPrecision(3);
		question.setVariables("x y z");

		// Add multiple choices
		final Choice correctChoice = new Choice("Choice #1#");
		correctChoice.setCorrect(true);
		question.addChoice(correctChoice);
		question.addChoice(new Choice("Choice #2#"));
		question.addChoice(new Choice("Choice #3#"));

		// Add answers
		question.addAnswer(new Answer("Answer #1#"));
		question.addAnswer(new Answer("Answer #2#"));

		// Add hints
		question.addHint(new Hint("Hint1", "hint2 text auioq", "keyword1 text pieoe",
				"keyword2 text aqqa"));
		question.addHint(new Hint("Hint2", "hint2 text aaaaauioq", "keyword1 text lxpi",
				"keyword2 text xaqa"));

		return question;
	}

	/**
	 * Create a mock expectation question object with a specified level and
	 * type.
	 * 
	 * @param titleString
	 *            title string that appears in the question's name
	 * @return mock expectation question object
	 */
	public static Expectation createMockExpectation(final String titleString,
			final Boolean isNegative)
	{
		// Create basic question data
		Expectation expectation = new Expectation(titleString, null);
		expectation.setFormulation(titleString + " - formulation");
		expectation.setNegative(isNegative);

		return expectation;
	}

	/**
	 * Create mock questions at each type and level of difficulty.
	 * 
	 * @param parent
	 *            parent node to add questions under
	 * @param titleString
	 *            title string that appears in the questions' names
	 * @param numQuestionsPerTypeAndLevel
	 *            number of questions to be added for each type+level
	 *            combination
	 * @param concepts
	 *            list of concepts to attach to each question
	 */
	public static void addMockQuestions(final Item parent, final String titleString,
			final int numQuestionsPerTypeAndLevel,
			final Collection<? extends TagItem> tags)
	{
		// Create mock questions at each type and level of difficulty
		for (QuestionType questionType : QuestionType.values())
		{
			for (int level = 1; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
			{
				for (int q = 0; q < numQuestionsPerTypeAndLevel; q++)
				{
					Item question = ItemDemoUtil.createMockQuestion(parent.getName()
							+ ": " + titleString + " #" + (q + 1) + "#", questionType, level);
					question.addTags(tags);
					parent.addChild(question);
				}
			}
		}
	}

	/**
	 * Create mock expectation questions.
	 * 
	 * @param parent
	 *            parent node to add expectation questions under
	 * @param titleString
	 *            title string that appears in the questions' names
	 * @param numQuestions
	 *            number of expectation questions to be added for each
	 *            type+level combination
	 */
	public static void addMockExpectations(final Item parent, final String titleString,
			final int numQuestions)
	{
		// Create expectation questions
		for (int q = 0; q < numQuestions; q++)
		{
			Item question = ItemDemoUtil.createMockExpectation(titleString + " #"
					+ (q + 1), (q % 2 == 0));
			parent.addChild(question);
		}
	}
}
