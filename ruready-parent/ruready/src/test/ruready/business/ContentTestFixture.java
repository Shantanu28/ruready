package test.ruready.business;

import java.util.List;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.World;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.eis.dao.DAO;
import net.ruready.eis.common.tree.NodeDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.rl.StandAloneApplicationContext;

/**
 * A useful fixture for user management test cases.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Jeremy Lund
 * @version Oct 3, 2007
 */
public class ContentTestFixture
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ContentTestFixture.class);

	// ========================= FIELDS ====================================

	private final StandAloneEnvironment environment;

	private final StandAloneApplicationContext context;

	private final AbstractEditItemBD<MainItem> mainEditItemBD;
	private final AbstractEditItemBD<Item> editItemBD;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize a user test fixture.
	 * 
	 * @param environment
	 */
	public ContentTestFixture(final StandAloneEnvironment environment)
	{
		this.environment = environment;
		this.context = environment.getContext();
		this.mainEditItemBD = createMainEditItemBD();
		this.editItemBD = createEditItemBD();
	}

	// ========================= METHODS ===================================

	public final void buildCatalog()
	{
		// run once just in case catalog is in an invalid state
		deleteCatalog();
		logger.info("Creating the demo catalog...");
		TagCabinet tagCabinet = ItemDemoUtil.createBase(TagCabinet.class,
				ContentNames.UNIQUE_NAME.TAG_CABINET);
		final MainItem catalog = ItemDemoUtil.createBase(Catalog.class,
				ContentNames.BASE_NAME.CATALOG, tagCabinet);
		getMainEditItemBD().updateAll(tagCabinet);
		getMainEditItemBD().updateAll(catalog);
	}

	public final void deleteCatalog()
	{
		final MainItem catalog = getMainEditItemBD().findByUniqueProperty(MainItem.class,
				NodeDAO.NAME, ContentNames.BASE_NAME.CATALOG);
		logger.info("Deleting the demo catalog...");
		if (catalog != null)
		{
			getMainEditItemBD().deleteAll(catalog, false);
		}

		final MainItem tagCabinet = getMainEditItemBD().findByUniqueProperty(TagCabinet.class,
				NodeDAO.NAME, ContentNames.UNIQUE_NAME.TAG_CABINET);
		logger.info("Deleting the demo tag cabinet...");
		if (tagCabinet != null)
		{
			getMainEditItemBD().deleteAll(tagCabinet, false);
		}
}

	public final void buildWorld()
	{
		// run once just in case world is in an invalid state
		deleteWorld();
		logger.info("Creating the demo world...");
		final MainItem world = ItemDemoUtil.createBase(World.class,
				ContentNames.UNIQUE_NAME.WORLD);
		getMainEditItemBD().updateAll(world);
	}

	public final void deleteWorld()
	{
		final MainItem world = getMainEditItemBD().findByUniqueProperty(MainItem.class,
				NodeDAO.NAME, ContentNames.UNIQUE_NAME.WORLD);
		logger.info("Deleting the demo world...");
		if (world != null)
		{
			getMainEditItemBD().deleteAll(world, false);
		}
	}

	public final Course getCourse(final String name)
	{
		return getCourseDAO().findByUniqueProperty("name", name);
	}

	public final School getSchool(final String name)
	{
		return getSchoolDAO().findByUniqueProperty("name", name);
	}

	public final SubTopic getSubTopic(final String name)
	{
		return getSubTopicDAO().findByUniqueProperty("name", name);
	}

	public final Expectation getExpectation(final String name)
	{
		return getExpectationDAO().findByUniqueProperty("name", name);
	}

	public final List<Expectation> getExpectationsForCourse(final String name)
	{
		final Course course = getCourse(name);
		return getEditItemBD().findChildren(course, Expectation.class,
				ItemType.EXPECTATION);
	}

	public final Question getQuestion(final String name)
	{
		return getQuestionDAO().findByUniqueProperty("name", name);
	}

	public final List<Question> getQuestionsForCourse(final String name)
	{
		final Course course = getCourse(name);
		return getEditItemBD().findChildren(course, Question.class, ItemType.QUESTION);
	}

	// ========================= PRIVATE METHODS ===========================

	private DAO<Course, Long> getCourseDAO()
	{
		return getDAOFactory().getDAO(Course.class, context);
	}

	private DAO<School, Long> getSchoolDAO()
	{
		return getDAOFactory().getDAO(School.class, context);
	}

	private DAO<SubTopic, Long> getSubTopicDAO()
	{
		return getDAOFactory().getDAO(SubTopic.class, context);
	}

	private DAO<Expectation, Long> getExpectationDAO()
	{
		return getDAOFactory().getDAO(Expectation.class, context);
	}

	private DAO<Question, Long> getQuestionDAO()
	{
		return getDAOFactory().getDAO(Question.class, context);
	}

	private AbstractEISManager getDAOFactory()
	{
		return environment.getDAOFactory();
	}

	private AbstractEditItemBD<MainItem> getMainEditItemBD()
	{
		return this.mainEditItemBD;
	}

	private AbstractEditItemBD<MainItem> createMainEditItemBD()
	{
		return new StandAloneEditItemBD<MainItem>(MainItem.class, context,
				getSystemUser());
	}

	private AbstractEditItemBD<Item> getEditItemBD()
	{
		return this.editItemBD;
	}

	private AbstractEditItemBD<Item> createEditItemBD()
	{
		return new StandAloneEditItemBD<Item>(Item.class, context, getSystemUser());
	}

	private User getSystemUser()
	{
		return SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
	}
}
