/*****************************************************************************************
 * Source File: DemoCatalogCreator.java
 ****************************************************************************************/
package net.ruready.business.content.demo.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.catalog.entity.ContentKnowledge;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.ExpectationCategory;
import net.ruready.business.content.catalog.entity.ExpectationSet;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.catalog.entity.Topic;
import net.ruready.business.content.concept.entity.ConceptCollection;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.interest.entity.InterestCollection;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.skill.entity.SkillCollection;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.content.tag.entity.TagItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A factory of a mock catalog created for testing and upon site initialization.
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
 * @version Aug 1, 2007
 */
public class DemoCatalogCreator implements DemoItemCreator<Catalog>
{
	// ========================= CONSTANTS =================================

	public static final String COURSE_1 = "Calculus";

	public static final String COURSE_1_T1 = "Functions and Their Graphs";

	public static final String COURSE_1_T2 = "Trigonometry";

	public static final String COURSE_1_T2_ST1 = "Computing with Angles";

	public static final String COURSE_2 = "Intermediate Algebra";

	public static final String COURSE_2_T1 = "Functions and Their Graphs";

	public static final String COURSE_2_T1_ST1 = "Sub-topic 1";

	public static final String COURSE_2_T1_ST2 = "Sub-topic 2";

	public static final String COURSE_2_T2 = "Rational Expressions";

	public static final String COURSE_2_T2_ST1 = "Sub-topic 3";

	public static final String COURSE_2_T2_ST2 = "Sub-topic 4";

	public static final String EXPECTATION_1_PREFIX = "Thinking Skill";
	public static final String EXPECTATION_2_PREFIX = "Learning Habit";

	public static final Integer EXPECTATION_1_COUNT = 11;
	public static final Integer EXPECTATION_2_COUNT = 5;

	public static final String QUESTION_PREFIX = "Mock";

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DemoCatalogCreator.class);

	// ========================= FIELDS ====================================

	/**
	 * Tag cabinet to link catalog items to.
	 */
	private final TagCabinet tagCabinet;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a demo catalog creator.
	 * 
	 * @param tagCabinet
	 *            tag cabinet to link catalog items to
	 */
	public DemoCatalogCreator(TagCabinet tagCabinet)
	{
		super();
		this.tagCabinet = tagCabinet;
	}

	// ========================= IMPLEMENTATION: DemoItemCreator ==============

	/**
	 * Initialize a base catalog using a hard-coded tree of nodes. This is
	 * useful for testing and site initialization defaulting.
	 * 
	 * @param uniqueName
	 *            catalog's uniqueName identifier
	 * @see net.ruready.business.content.demo.manager.DemoItemCreator#createItem(java.lang.String)
	 */
	public Catalog createItem(String uniqueName)
	{
		// Create some items. Because we only add items,
		// the tree automatically refreshes and appears sorted.
		logger.info("Creating base catalog");
		Catalog catalog = null;
		Item parent = null;
		Item child = null;

		catalog = new Catalog(uniqueName, "Subjects, courses, topics, questions, ...");
		child = catalog;

		// =======================================
		// Add course #1
		// =======================================
		parent = child;
		child = new Subject("Math", "Introductory mathematics college courses");
		Subject subject = (Subject) child;
		subject.setAbbreviation("MATH");

		// Add to subject some required tags from the tag cabinet
		InterestCollection interestCollection = (InterestCollection) tagCabinet
				.findChild(new InterestCollection("Student interests", null));
		subject.setInterestCollection(interestCollection);

		ConceptCollection conceptCollection = (ConceptCollection) tagCabinet
				.findChild(new ConceptCollection("Math concepts", null));
		subject.setConceptCollection(conceptCollection);

		SkillCollection skillCollection = (SkillCollection) tagCabinet
				.findChild(new SkillCollection("Math skills", null));
		subject.setSkillCollection(skillCollection);

		// Save the first three concepts and skils in a set to be added to all
		// mock questions below
		List<TagItem> selectedTags = new ArrayList<TagItem>();
		{
			Set<Node> allConcepts = conceptCollection.getChildren();
			int i = 0;
			for (Node concept : allConcepts)
			{
				selectedTags.add((TagItem) concept);
				i++;
				if (i == 3)
				{
					break;
				}
			}
		}
		{
			Set<Node> allSkills = skillCollection.getChildren();
			int i = 0;
			for (Node skill : allSkills)
			{
				selectedTags.add((TagItem) skill);
				i++;
				if (i == 3)
				{
					break;
				}
			}
		}

		parent.addChild(child);

		parent = child;
		child = new Course(COURSE_1, "1210");
		parent.addChild(child);

		parent = child;
		child = new ExpectationSet("Expectations", null);
		parent.addChild(child);

		parent = child;
		child = new ExpectationCategory("Thinking Skills", null);
		parent.addChild(child);
		ItemDemoUtil
				.addMockExpectations(child, EXPECTATION_1_PREFIX, EXPECTATION_1_COUNT);

		child = new ExpectationCategory("Learning Habits", null);
		parent.addChild(child);
		ItemDemoUtil
				.addMockExpectations(child, EXPECTATION_2_PREFIX, EXPECTATION_2_COUNT);

		parent = parent.getParent();

		child = new ContentKnowledge("Content Knowledge", null);
		parent.addChild(child);

		parent = child;
		child = new Topic(COURSE_1_T1, null);
		parent.addChild(child);

		child = new Topic(COURSE_1_T2, null);
		parent.addChild(child);

		parent = child;
		child = new SubTopic(COURSE_1_T2_ST1, null);
		parent.addChild(child);
		ItemDemoUtil.addMockQuestions(child, QUESTION_PREFIX, 4, selectedTags);

		// =======================================
		// Add course #2
		// =======================================
		parent = child.getSuperParent(ItemType.SUBJECT);
		child = new Course(COURSE_2, "1010");
		parent.addChild(child);

		parent = child;
		child = new ContentKnowledge("Content Knowledge", null);
		parent.addChild(child);

		parent = child;
		child = new Topic(COURSE_2_T1, null);
		parent.addChild(child);

		parent = child;

		child = new SubTopic(COURSE_2_T1_ST1, null);
		parent.addChild(child);
		ItemDemoUtil.addMockQuestions(child, QUESTION_PREFIX, 2, selectedTags);

		child = new SubTopic(COURSE_2_T1_ST2, null);
		parent.addChild(child);
		ItemDemoUtil.addMockQuestions(child, QUESTION_PREFIX, 2, selectedTags);

		parent = parent.getParent();

		child = new Topic(COURSE_2_T2, null);
		parent.addChild(child);

		parent = child;
		child = new SubTopic(COURSE_2_T2_ST1, null);
		parent.addChild(child);
		ItemDemoUtil.addMockQuestions(child, QUESTION_PREFIX, 2, selectedTags);
		child = new SubTopic(COURSE_2_T2_ST2, null);
		parent.addChild(child);
		ItemDemoUtil.addMockQuestions(child, QUESTION_PREFIX, 2, selectedTags);
		parent = parent.getParent();

		// This will sort children, assign serial #s, etc.
		// catalog.refreshAll();

		return catalog;
	}
}
