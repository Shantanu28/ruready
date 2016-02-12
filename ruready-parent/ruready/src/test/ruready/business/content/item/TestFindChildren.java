/*****************************************************************************************
 * Source File: TestPersistenceCatalog.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.catalog.entity.ContentKnowledge;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.catalog.entity.Topic;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.QuestionCount;
import net.ruready.business.content.question.entity.property.QuestionCountType;
import net.ruready.business.content.question.entity.property.QuestionField;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.content.question.exports.AbstractEditQuestionBD;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.util.util.ItemTypeUtil;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.search.DefaultSearchCriteria;
import net.ruready.common.search.Logic;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;
import net.ruready.eis.common.tree.NodeDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.imports.StandAloneEditQuestionBD;
import test.ruready.rl.TestEnvTestBase;
import test.ruready.rl.TestingNames;

/**
 * Test finding children of a specified item type under a certain item.
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
 * @version Aug 5, 2007
 */
public class TestFindChildren extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestFindChildren.class);

	/**
	 * Number of each item type to create at each hierarchy level, from course
	 * all the way to sub-topic.
	 */
	private static final Map<ItemType, Long> itemCounts = new HashMap<ItemType, Long>();

	/**
	 * Total number of each item type to create at each hierarchy level, from
	 * course all the way to sub-topic.
	 */
	private static final Map<ItemType, Long> totalItemCounts = new HashMap<ItemType, Long>();

	/**
	 * Fix the question total number of each type and level. For test
	 * completeness, make all counts different and set at least one of them to
	 * 0.
	 */
	private static final QuestionCount expectedQuestionCounts = new QuestionCount();

	static
	{
		// Fix the question total number of each item type from course all the
		// way to sub-topic
		long total = 1;
		total = addToItemCounts(total, ItemType.COURSE, 2);
		total = addToItemCounts(total, ItemType.CONTENT_KNOWLEDGE, 1);
		total = addToItemCounts(total, ItemType.TOPIC, 1);
		total = addToItemCounts(total, ItemType.SUB_TOPIC, 2);
		logger.debug("item counts = " + itemCounts);
		logger.debug("Total counts = " + totalItemCounts);

		// Fix the question total number of each type and level. Make all counts
		// different and set at least one of them to 0.
		// TODO: replace this map by a wrapper object with easy two-key accessor
		// methods
		long num = 2;
		long totalQuestions = 0;
		final long totalSubTopics = totalItemCounts.get(ItemType.SUB_TOPIC);
		for (QuestionType questionType : QuestionType.values())
		{
			for (int level = 1; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
			{
				long numQuestions = num * totalSubTopics;
				expectedQuestionCounts.put(questionType.getQuestionCountType(), level,
						numQuestions);
				totalQuestions += numQuestions;
				// num++;
			}
		}
		logger.info("question counts = " + expectedQuestionCounts);
		totalItemCounts.put(ItemType.QUESTION, totalQuestions);
	}

	// ========================= FIELDS ====================================

	/**
	 * System user used in all item operations.
	 */
	private User systemUser;

	// ========================= IMPLEMENTATION: TestBase ============

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		// ===================================
		// Create a new catalog
		// ===================================
		final long totalSubTopics = totalItemCounts.get(ItemType.SUB_TOPIC);
		systemUser = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
		AbstractEditItemBD<MainItem> bdMainItem = new StandAloneEditItemBD<MainItem>(
				MainItem.class, environment.getContext(), systemUser);

		// Create a new test catalog
		logger.info("Creating a demo catalog ...");
		// MainItem catalog = Catalog
		// .createBaseCatalog(TestingNames.CONTENT.BASE.CATALOG_NAME);
		MainItem catalog = new Catalog(TestingNames.CONTENT.BASE.CATALOG_NAME, null);

		// Save catalog to database
		logger.info("Saving catalog to database ...");
		bdMainItem.updateAll(catalog);

		// ===================================
		// Add some new items
		// ===================================
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		// Reload catalog to get its ID
		catalog = bdMainItem.findByUniqueProperty(MainItem.class, NodeDAO.NAME,
				TestingNames.CONTENT.BASE.CATALOG_NAME);

		Item subject = new Subject("Math", null);
		bdItem.createUnder(catalog.getId(), subject);

		for (int i = 0; i < itemCounts.get(ItemType.COURSE); i++)
		{
			Item course = new Course("New Course " + i, "000" + i);
			bdItem.createUnder(subject.getId(), course);

			for (int j = 0; j < itemCounts.get(ItemType.CONTENT_KNOWLEDGE); j++)
			{
				Item contentType = new ContentKnowledge("Content Knowledge " + i + "/"
						+ j, null);
				bdItem.createUnder(course.getId(), contentType);

				for (int k = 0; k < itemCounts.get(ItemType.TOPIC); k++)
				{
					Item topic = new Topic("Topic " + i + "/" + j + "/" + k, null);
					bdItem.createUnder(contentType.getId(), topic);

					for (int l = 0; l < itemCounts.get(ItemType.SUB_TOPIC); l++)
					{
						Item subTopic = new SubTopic("Sub-Topic " + i + "/" + j + "/" + k
								+ "/" + l, null);
						bdItem.createUnder(topic.getId(), subTopic);

						// Create questions at each type and level of difficulty
						for (QuestionType questionType : QuestionType.values())
						{
							for (int level = 1; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
							{
								for (int q = 0; q < expectedQuestionCounts.get(
										questionType.getQuestionCountType(), level)
										/ totalSubTopics; q++)
								{
									Item question = ItemDemoUtil.createMockQuestion(
											"Mock", questionType, level);
									bdItem.createUnder(subTopic.getId(), question);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// ===================================
		// Delete the test catalog
		// ===================================
		AbstractEditItemBD<MainItem> bdMainItem = new StandAloneEditItemBD<MainItem>(
				MainItem.class, environment.getContext(), systemUser);

		// Delete catalog
		logger.info("Deleting catalog ...");
		MainItem catalog = bdMainItem.findByUniqueProperty(MainItem.class, NodeDAO.NAME,
				TestingNames.CONTENT.BASE.CATALOG_NAME);
		bdMainItem.deleteAll(catalog, false);
	}

	// ========================= TESTING METHODS ===========================

	/**
	 * Test the method that finds all topics under a course (there is one course
	 * in the test catalog).
	 */
	@Test
	public void testFindTopicsInCourse()
	{
		this.testFindTypeInCourse(Topic.class, ItemType.TOPIC);
	}

	/**
	 * Test the method that finds all sub-topics under a course (there is one
	 * course in the test catalog).
	 */
	@Test
	public void testFindSubTopicsInCourse()
	{
		this.testFindTypeInCourse(SubTopic.class, ItemType.SUB_TOPIC);
	}

	/**
	 * Test the method that finds all questions under a course (there is one
	 * course in the test catalog).
	 */
	@Test
	public void testFindQuestionsInCourse()
	{
		this.testFindTypeInCourse(Question.class, ItemType.QUESTION);
	}

	/**
	 * Test counting questions under a course of a specified type and level in
	 * three different ways.
	 */
	@Test
	public void testCountQuestions()
	{
		Course course = getFirstCourse();

		// Count questions for these parameters:
		final QuestionType questionType = QuestionType.ACADEMIC;
		final int level = 1;

		final int expectedNumQuestions = totalItemCounts.get(ItemType.QUESTION)
				.intValue()
				/ totalItemCounts.get(ItemType.COURSE).intValue();
		final long expectedNumQuestionsPerTypeAndLevel = expectedQuestionCounts.get(
				questionType.getQuestionCountType(), level)
				/ totalItemCounts.get(ItemType.COURSE).intValue();

		// Standard bdItem interface methods
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		List<Question> children = bdItem.findChildren(course, Question.class,
				ItemType.QUESTION);
		logger.info("size(children): " + children.size());
		Assert.assertEquals(expectedNumQuestions, children.size());

		// bdQuestion interface method #1
		AbstractEditQuestionBD bdQuestion = new StandAloneEditQuestionBD(environment
				.getContext(), systemUser);
		List<Question> questions = bdQuestion.findQuestions(course, questionType, level,
				null);
		logger.debug("#questions " + questions.size());
		Assert.assertEquals(expectedNumQuestionsPerTypeAndLevel, questions.size());

		// bdQuestion interface method #2
		long count = bdQuestion.findQuestionCount(course, questionType, level, null);
		logger.debug("#questions computed by BD: " + count);
		Assert.assertEquals(expectedNumQuestionsPerTypeAndLevel, count);
	}

	/**
	 * Test finding questions under a course by search criteria.
	 */
	@Test
	public void testFindQuestionsBySearchCriteria()
	{
		Course course = getFirstCourse();

		// Find questions for these parameters:
		final QuestionType questionType = QuestionType.ACADEMIC;
		final int level = 1;

		final long expectedNumQuestionsPerTypeAndLevel = expectedQuestionCounts.get(
				questionType.getQuestionCountType(), level)
				/ totalItemCounts.get(ItemType.COURSE).intValue();

		// -----------------------------------------------------------
		// Form search criteria for the following condition:
		// level = 1 and type = ACADEMIC and course = firstCourse
		// -----------------------------------------------------------
		final SearchCriteria criteria = new DefaultSearchCriteria(Logic.AND
				.createCriterion());
		ItemTypeUtil.addSuperParentAlias(criteria, ItemType.COURSE, ItemType.QUESTION);

		criteria.add(SearchCriterionFactory.createSimpleExpression(SearchType.EQ,
				Integer.class, QuestionField.LEVEL.getName(), level));
		criteria.add(SearchCriterionFactory.createSimpleExpression(SearchType.EQ,
				QuestionType.class, QuestionField.QUESTION_TYPE.getName(), questionType));
		// criteria.add(SearchCriterionFactory.createCollectionExpression(SearchType.IN,
		// Long.class, QuestionField.PARENT_ID.getName(), parentIds));
		criteria.add(SearchCriterionFactory.createCollectionExpression(SearchType.IN,
				Long.class, "superParent.id", Arrays.asList(new Long[]
				{ course.getId() })));

		// Carry out the search and check the results
		AbstractEditQuestionBD bdQuestion = new StandAloneEditQuestionBD(environment
				.getContext(), systemUser);
		List<Question> questions = bdQuestion.findByCriteria(criteria);
		logger.info("size(questions): " + questions.size());
		Assert.assertEquals(expectedNumQuestionsPerTypeAndLevel, questions.size());
	}

	/**
	 * Test {@link QuestionCount} object and services.
	 */
	@Test
	public void testQuestionCount()
	{
		Course course = getFirstCourse();

		// -----------------------------------------------------------
		// Compute the expected counts
		// -----------------------------------------------------------
		QuestionCount expected = new QuestionCount();
		for (int level = 1; level <= ContentNames.QUESTION.HIGHEST_LEVEL; level++)
		{
			long paramCount = 0;
			for (QuestionType questionType : QuestionType.values())
			{
				long count = expectedQuestionCounts.get(questionType
						.getQuestionCountType(), level)
						/ totalItemCounts.get(ItemType.COURSE).intValue();
				expected.put(questionType.getQuestionCountType(), level, count);
				paramCount += count;
			}

			// All questions are parametric
			expected.put(QuestionCountType.PARAMETRIC, level, paramCount);
		}
		logger.info("Expected " + expected);

		// -----------------------------------------------------------
		// Compute question counts using question BD
		// -----------------------------------------------------------
		AbstractEditQuestionBD bdQuestion = new StandAloneEditQuestionBD(environment
				.getContext(), systemUser);
		QuestionCount actual = bdQuestion.generateQuestionCount(course);
		logger.info("Actual   " + actual);
		logger.info("Actual in XML format:\n" + actual.toXml("myGroupId"));
		Assert.assertEquals(expected, actual);
	}

	// ========================= PRIVATE METHODS ============================

	/**
	 * Add one item hierarchy level information to the item count maps.
	 * 
	 * @param total
	 * @param type
	 * @param numItemsPerLevel
	 * @return total number of items at the new item tree level
	 */
	private static long addToItemCounts(final long total, final ItemType type,
			final long numItemsPerLevel)
	{
		itemCounts.put(type, numItemsPerLevel);
		long newTotal = total * numItemsPerLevel;
		totalItemCounts.put(type, newTotal);
		return newTotal;
	}

	/**
	 * Test the method that finds all sub-topics under a course (there is one
	 * course in the test catalog).
	 */
	private <T extends Item> void testFindTypeInCourse(final Class<T> clazz,
			final ItemType type)
	{
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		Course course = getFirstCourse();

		List<T> children = bdItem.findChildren(course, clazz, type);
		logger.info("size(children): " + children.size());
		Assert.assertEquals(totalItemCounts.get(type).intValue()
				/ totalItemCounts.get(ItemType.COURSE).intValue(), children.size());

		// Compute children IDs and compare to children list
		List<Long> childrenIds = bdItem.findChildrenIds(course, clazz, type);
		logger.info("childrenIds: " + childrenIds.size());
		logger.info("size(childrenIds): " + childrenIds.size());
		Assert.assertEquals(childrenIds.size(), children.size());

		// Compute # children and compare to children list
		long childrenCount = bdItem.findChildrenCount(course, clazz, type);
		logger.info("# Children computed by BD: " + childrenCount);
		Assert.assertEquals(totalItemCounts.get(type).intValue()
				/ totalItemCounts.get(ItemType.COURSE).intValue(), childrenCount);
	}

	/**
	 * Get the list of courses in the database.
	 * 
	 * @return list of courses
	 */
	private List<Course> getCourses()
	{
		// Find all courses under the test catalog
		AbstractEditItemBD<MainItem> bdMainItem = new StandAloneEditItemBD<MainItem>(
				MainItem.class, environment.getContext(), systemUser);
		Item catalog = bdMainItem.findByUniqueProperty(MainItem.class, NodeDAO.NAME,
				TestingNames.CONTENT.BASE.CATALOG_NAME);

		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		List<Course> courses = bdItem
				.findChildren(catalog, Course.class, ItemType.COURSE);
		logger.debug("Courses found:" + courses);
		Assert.assertEquals(totalItemCounts.get(ItemType.COURSE).intValue(), courses
				.size());

		return courses;
	}

	/**
	 * Get the first course on the course list in the database.
	 * 
	 * @return first course on the course list in the database
	 */
	private Course getFirstCourse()
	{
		return getCourses().get(0);
	}
}
