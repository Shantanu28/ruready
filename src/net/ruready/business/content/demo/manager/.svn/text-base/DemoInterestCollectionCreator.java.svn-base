/*****************************************************************************************
 * Source File: DemoCatalogCreator.java
 ****************************************************************************************/
package net.ruready.business.content.demo.manager;

import net.ruready.business.content.interest.entity.Interest;
import net.ruready.business.content.interest.entity.InterestCollection;
import net.ruready.business.content.interest.entity.SubInterest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A factory of a mock interest collection created for testing and upon site
 * initialization.
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
class DemoInterestCollectionCreator implements DemoItemCreator<InterestCollection>
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
	private static final Log logger = LogFactory
			.getLog(DemoInterestCollectionCreator.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * In the future: we might hide constructor in this utility class and use a
	 * facade instead.
	 * </p>
	 */
	public DemoInterestCollectionCreator()
	{

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
	public InterestCollection createItem(String uniqueName)
	{
		// Create some items. Because we only add items,
		// the tree automatically refreshes and appears sorted.
		logger.info("Creating base interest collection");
		InterestCollection interestCollection = null;
		interestCollection = new InterestCollection(uniqueName, "Student interests");

		// =======================================
		// Add some interests and sub-interests
		// =======================================
		String[][] categories = new String[][]
		{
				{ "Arts / Entertainment",
						"Architecture, art, DVD, movies, musical theatre, shopping, shows, theatre" },
				{
						"Computer / Internet",
						"building computers, computer games, computer technology, google whacking, graphic design, Internet surfing information and news, programming software / applications, search information in Wikipedia, web design" },
				{
						"Crafts",
						"crafts, drawing, knitting, making knives, origami, painting, water color painting, wood crafting" },
				{ "Electronics / Engineering",
						"building / fixing computers, engineering, technical engineering" },
				{ "Food", "cooking, food" },
				{ "Games", "Board games, chess, video games, LEGO" },
				{ "Literature / History", "books, history, reading, writing" },
				{ "Model Building / Invention",
						"model aircraft, model cars, model rockets" },
				{
						"Music",
						"concerts, guitar, listen to music, music, music composition, piano, study other peoples music, playing instruments, violin, viola" },
				{ "Performing Arts",
						"acting, dancing, film making, video making, photography, singing, swim dancing" },
				{
						"Sciences",
						"computer science, lab work, logging ideas, mathematics, medical fields, medicine, science, thinking out problems, animals, plants, gardening, biology, veterinary science" },
				{
						"Social Related",
						"business, civil disobedience, debate, politics, spending time with family / friends, current events, travel, other cultures, foreign languages" },
				{
						"Sports / Outdoor / Recreation",
						"aikido, athletic training, baseball, basketball, biking, bicycling, bowling, cheerleading, cricket, fishing, fly-fishing, football, four-wheel, gardening, golf, ice hockey, hiking, hunting, karate, kayaking, marshal arts, min-golf, motorcycle riding, mountain biking, nature, racquetball, rock hounding (collecting), rock climbing, running, skiing, snowboarding, soccer, softball, sport statistics, swim dancing, swimming, Teatwon-Do, tennis, travel, uni-cycling, volleyball, wake boarding, water-polo, waterskiing, weightlifting, wrestling" } };

		for (String[] category : categories)
		{
			addInterestCategory(interestCollection, category[0], category[1]);
		}

		// This will sort children, assign serial #s, etc.
		// interestCollection.refreshAll();

		return interestCollection;
	}

	/**
	 * Add an interest category.
	 * 
	 * @param destination
	 *            interest collection
	 * @param interest
	 *            interest category name
	 * @param subInterestString
	 *            sub-interest names, comma-delimited
	 */
	private static void addInterestCategory(final InterestCollection destination,
			final String interest, final String subInterestString)
	{
		String[] subInterests = subInterestString.split("\\s*,\\s*");
		Interest category = new Interest(interest, null);
		for (String subInterest : subInterests)
		{
			category.addChild(new SubInterest(subInterest, null));
		}
		destination.addChild(category);

	}
}
