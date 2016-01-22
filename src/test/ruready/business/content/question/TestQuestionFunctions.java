/*****************************************************************************************
 * Source File: TestQuestionFunctions.java
 ****************************************************************************************/
package test.ruready.business.content.question;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.item.manager.AbstractEditItemManager;
import net.ruready.business.content.item.manager.DefaultEditItemManager;
import net.ruready.business.content.question.entity.Answer;
import net.ruready.business.content.question.entity.Choice;
import net.ruready.business.content.question.entity.Hint;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.adapter.ObjectToString;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.math.real.RandomUtil;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.util.EnumUtil;
import net.ruready.eis.content.item.ItemDAO;
import net.ruready.parser.arithmetic.entity.numericalvalue.ComplexValue;
import net.ruready.parser.options.entity.DefaultVariableMap;
import net.ruready.parser.options.exports.ParserOptions;
import net.ruready.parser.options.exports.VariableMap;
import net.ruready.parser.param.entity.ParametricEvaluationTarget;
import net.ruready.parser.service.exports.DefaultParserBD;
import net.ruready.web.content.question.form.AnswerDTO;
import net.ruready.web.content.question.form.ChoiceDTO;
import net.ruready.web.content.question.form.EditQuestionForm;
import net.ruready.web.content.question.form.HintDTO;
import net.ruready.web.parser.imports.StrutsParserBD;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.rl.TestEnvTestBase;

/**
 * Tests {@link Question} object database persistence. Populates the database
 * with a set of mock questions.
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
public class TestQuestionFunctions extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestQuestionFunctions.class);

	private static String FORMULATION_STRING = "TEST FORMULATION";

	// ========================= FIELDS ====================================

	/**
	 * System user used in all item operations.
	 */
	private User systemUser;

	/**
	 * A context for this environment. Stores some useful attributes.
	 */
	private ApplicationContext context;

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		systemUser = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
		this.context = environment.getContext();
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// ---------------------------------------
		// Clean up - delete all questions
		// ---------------------------------------
		logger.info("Deleting questions ...");
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		List<Question> items = bdItem.findAll(Question.class);
		for (Question item : items)
		{
			bdItem.deleteAll(item, false);
		}
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test basic question creation.
	 */
	@Test
	public void testDAO_createBasic() throws Exception
	{

		// Session session = environment.getSession();

		// use questionDAO
		ItemDAO<Question> questionDAO = createQuestionDAO();

		Question question = createMockQuestion("testDAO_createBasic");

		// store question to the db
		questionDAO.update(question, systemUser.getId());

		// cleanup
		questionDAO.delete(question, systemUser.getId());
	}

	/**
	 * Test basic question creation. Test state loading
	 */
	@Test
	public void testQuestionStateLoading() throws Exception
	{
		// Session session = environment.getSession();

		// use questionDAO
		ItemDAO<Question> questionDAO = createQuestionDAO();

		Question question = createMockQuestion("testDAO_createBasic");
		question.disable();
		question.setQuestionType(QuestionType.CREATIVE);

		// Save question to the database
		questionDAO.update(question, systemUser.getId());

		// Load the question and check if the state was loaded
		Question retrievedQuestion = questionDAO.read(question.getId(), false);
		Assert.assertEquals(question.getId(), retrievedQuestion.getId());
		Assert.assertEquals(question.getStateID(), retrievedQuestion.getStateID());
		logger.debug("Saved question state  = " + question.getStateID());
		logger.debug("Loaded question state = " + retrievedQuestion.getStateID());

		// Use findAll()
		List<Question> questions = questionDAO.findAll();
		Question findAllQuestion = questions.get(0);
		logger.debug("findAll() loaded these questions: " + questions);
		logger.debug("Loaded question state in findAll() = "
				+ findAllQuestion.getStateID() + " " + findAllQuestion.getQuestionType());
		Assert.assertEquals(question.getStateID(), findAllQuestion.getStateID());
		Assert
				.assertEquals(question.getQuestionType(), findAllQuestion
						.getQuestionType());

		// cleanup
		questionDAO.delete(question, systemUser.getId());
	}

	/**
	 * Access a DAO via the DAOFactory.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDAO_createAndRead() throws Exception
	{

		Question question = createMockQuestion("testDAO_createAndRead");

		Question readQuestion = new Question();

		ItemDAO<Question> questionDAO = createQuestionDAO();
		questionDAO.update(question, systemUser.getId());

		questionDAO = createQuestionDAO();
		readQuestion = questionDAO.read(question.getId(), false);

		Assert.assertEquals(question.getId(), readQuestion.getId());

		for (Choice x : readQuestion.getChoices())
			logger.debug("choice id: " + x.getId());

		for (Answer x : readQuestion.getAnswers())
			logger.debug("answer id: " + x.getId());

		for (Hint x : readQuestion.getHints())
			logger.debug("hint id: " + x.getId());

		// cleanup
		questionDAO = createQuestionDAO();
		questionDAO.delete(question, systemUser.getId());
	}

	/**
	 * Access a DAO via the DAOFactory.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDAO_update() throws Exception
	{

		Question question = createMockQuestion("testDAO_saveOrUpdate");

		Question readQuestion = new Question();

		ItemDAO<Question> questionDAO = createQuestionDAO();
		questionDAO.update(question, systemUser.getId());

		questionDAO = createQuestionDAO();
		readQuestion = questionDAO.read(question.getId(), false);
		// questionDAO.read(question.getId(), readQuestion);

		// modify the question
		readQuestion.setFormulation("modifed for update");
		(readQuestion.getAnswers().iterator().next())
				.setAnswerText("modified for update");
		(readQuestion.getAnswers().iterator().next())
				.setAnswerText("modified for update2");

		questionDAO.update(readQuestion, systemUser.getId());

		// cleanup
		questionDAO = createQuestionDAO();
		questionDAO.delete(readQuestion, systemUser.getId());
	}

	/**
	 * Tests findByCriteria. Note this is actually implemented in the
	 * HibernateDAO, but I still want to test it in the context of ItemDAO<Question>.
	 */
	@Test
	public void testDAO_findByExample()
	{
		// Session session = environment.getSession();

		ItemDAO<Question> questionDAO = createQuestionDAO();

		Question question = createMockQuestion("testDAO_findByExample");

		questionDAO.update(question, systemUser.getId());

		// test findbyid
		Question retrievedQuestion = questionDAO.read(question.getId(), true);
		Assert.assertEquals(question.getId(), retrievedQuestion.getId());

		// Test findByExample()
		Question exampleQuestion = new Question();
		exampleQuestion.setFormulation(FORMULATION_STRING);
		exampleQuestion.setId(0L);

		logger.debug("egQuestion id: " + exampleQuestion.getId());

		List<Question> retrievedQuestions = questionDAO.findByExample(exampleQuestion);
		for (Question qq : retrievedQuestions)
		{
			logger.info("findByExample(): question id: " + qq.getId());
			logger.info("findByExample(): question disabled: " + qq.isDisabled());
		}

		// cleanup
		questionDAO.delete(question, systemUser.getId());
	}

	@Test
	public void testMisc_copyProps() throws Exception
	{

		Question question = createMockQuestion("copyProps");

		EditQuestionForm form = new EditQuestionForm();

		BeanUtils.copyProperties(question, form);

		BeanUtils.copyProperties(form, question);
	}

	public void obsoleteTestObjectsAndForms() throws Exception
	{

		Question question = createMockQuestion("copyProps");

		EditQuestionForm form = new EditQuestionForm();

		form.copyFrom(question, context);

		logger.debug(ObjectToString.process(form));
		logger.debug(ObjectToString.process(question));
	}

	/**
	 * test conversion of enum to string and back again to enum
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMisc_enumConversions() throws Exception
	{

		/*
		 * createFromString tests
		 */
		Question question = createMockQuestion("enumConversions");

		String enumBefore = question.getType().toString();
		logger.debug("enum before: " + enumBefore);

		String typeString = question.getType().toString();

		logger.debug("enum string : " + typeString);

		question.setQuestionType(EnumUtil
				.createFromString(QuestionType.class, typeString));

		String enumAfter = question.getType().toString();

		logger.debug("enum after: " + enumAfter);

		Assert.assertEquals(enumBefore, enumAfter);

		/*
		 * valueOf tests
		 */
		question = createMockQuestion("enumConversions");

		enumBefore = question.getType().toString();
		logger.debug("enum before: " + enumBefore);

		typeString = question.getType().toString();

		logger.debug("enum string : " + typeString);

		// question.setType(EnumUtil.createFromString(QuestionType.class,
		// typeString));

		question.setQuestionType(EnumUtil.valueOf(QuestionType.class, typeString));

		enumAfter = question.getType().toString();

		logger.debug("enum after: " + enumAfter);

		Assert.assertEquals(enumBefore, enumAfter);
	}

	/**
	 * Generate questions for the database. All will be generated under a parent
	 * sub-topic
	 */
	public void xxxtestGenerateMockQuestions()
	{
		Question question = createMockQuestion("generate");
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		SubTopic subTopic = getSubTopic();
		bdItem.createUnder(subTopic.getId(), question);
	}

	/**
	 * Right. See if the save and read of question can be accomplished via the
	 * relatively generic AbstractEditItemManager interface.
	 */
	@Test
	public void testDefaultEditItemManager_createAndRead()
	{
		AbstractEditItemManager<Question> manager = new DefaultEditItemManager<Question>(
				Question.class, environment, environment.getContext(), systemUser);

		Question item = createMockQuestion("createAndRead");

		manager.update(item, true);

		Question readQuestion = manager.read(Question.class, item.getId());
		displayQuestion(readQuestion);

		manager.delete(item, true);
	}

	/**
	 * Exercise the BD layer via the AbstractEditItemBD interface.
	 */
	@Test
	public void testStandAloneEditItemBD_createAndRead() throws Exception
	{
		AbstractEditItemBD<Question> bd = new StandAloneEditItemBD<Question>(
				Question.class, environment.getContext(), systemUser);

		Question question = createMockQuestion("createAndRead");

		bd.update(question, true);

		Question readQuestion = bd.read(Question.class, question.getId());

		Assert.assertEquals(question.getId(), readQuestion.getId());

		displayQuestion(readQuestion);

		bd.delete(question, true);
	}

	/**
	 * make sure the copying of data from a form back to a question works
	 * properly.
	 */
	@Test
	public void testStruts_copyTo()
	{
		Question question = createMockQuestion("copyTo");

		EditQuestionForm form = new EditQuestionForm();
		form.copyFrom(question, context);

		Question copiedQuestion = new Question();
		form.copyTo(copiedQuestion, context);

		Assert.assertEquals(question.getId(), copiedQuestion.getId());
		Assert.assertEquals(question.getFormulation(), copiedQuestion.getFormulation());

		Iterator<Choice> copiedChoices = copiedQuestion.getChoices().iterator();
		for (Choice choice : question.getChoices())
		{

			Assert.assertEquals(copiedChoices.next().getChoiceText(), choice
					.getChoiceText());
		}

		Iterator<Answer> copiedAnswers = copiedQuestion.getAnswers().iterator();
		for (Answer answer : question.getAnswers())
		{

			Assert.assertEquals(copiedAnswers.next().getAnswerText(), answer
					.getAnswerText());
		}

		Iterator<Hint> copiedHints = copiedQuestion.getHints().iterator();
		for (Hint hint : question.getHints())
		{

			Assert.assertEquals(copiedHints.next().getHint1Text(), hint.getHint1Text());
		}

	}

	/**
	 * Make sure the copying from an object to a form works properly.
	 */
	@Test
	public void testStruts_copyFrom()
	{
		Question question = createMockQuestion("copyFrom");

		EditQuestionForm form = new EditQuestionForm();

		form.copyFrom(question, context);

		Assert.assertEquals(question.getFormulation(), form.getFormulation());
		Assert.assertEquals(question.getName(), form.getName());

		Iterator<ChoiceDTO> choiceDTOs = form.getChoiceDTOs().iterator();
		for (Choice choice : question.getChoices())
		{

			Assert
					.assertEquals(choiceDTOs.next().getChoiceText(), choice
							.getChoiceText());
		}

		Iterator<AnswerDTO> answerDTOs = form.getAnswerDTOs().iterator();
		for (Answer answer : question.getAnswers())
		{

			Assert
					.assertEquals(answerDTOs.next().getAnswerText(), answer
							.getAnswerText());
		}

		Iterator<HintDTO> hintDTOs = form.getHintDTOs().iterator();
		for (Hint hint : question.getHints())
		{

			Assert.assertEquals(hintDTOs.next().getHint1Text(), hint.getHint1Text());
		}

	}

	/**
	 * Test the automatic evaluation of parametric strings by the parametric
	 * parser.
	 */
	@Test
	public void testParametricParser()
	{
		// Prepare parser options object with some variables
		ParserOptions options = new ParserOptions();
		options.setPrecisionTol(1e-6);
		VariableMap vm = new DefaultVariableMap();
		vm.addNumerical("a", new ComplexValue(8));
		options.setVariables(vm);

		// Evaluate a string
		DefaultParserBD bd = new StrutsParserBD();
		{
			String parametricString = "hello, im greg the #a^2#th.";
			ParametricEvaluationTarget pet = bd.evaluate(parametricString, options);
			// This assertion is subject to round-off errors. Make sure to set
			// precision tolerance to a large enough value.
			logger.debug("result of parser");
			logger.debug(pet);
			String expected = "hello, im greg the 64th.";
			Assert.assertEquals(expected, pet.toString());
			Assert.assertEquals(true, pet.isLegal());
		}

		// Evaluate another string
		{
			String parametricString = "hello, im greg the #a^#th.";
			ParametricEvaluationTarget pet = bd.evaluate(parametricString, options);
			// This assertion is subject to round-off errors. Make sure to set
			// precision tolerance to a large enough value.
			logger.debug("result of parser");
			logger.debug(pet);
			String expected = "hello, im greg the ???th.";
			Assert.assertEquals(expected, pet.toString());
			Assert.assertEquals(false, pet.isLegal());
		}
	}

	/**
	 * Test Hibernate Query-By-Example (QBE) to find all deleted questions.
	 */
	@Test
	public void testQBE()
	{
		logger.debug("Finding all deleted questions...");

		// Set an example
		Question example = new Question(null, null);
		example.delete();
		example.setFormulation(null);

		// Make a deleted question
		ItemDAO<Question> questionDAO = createQuestionDAO();
		Question question = new Question("Something 1", "Something 2");
		question.delete();
		questionDAO.update(question, systemUser.getId());

		// List questions
		List<Question> questions = questionDAO.findByExample(example);

		logger.debug("All questions = " + questionDAO.findAll());
		logger.debug("Deleted questions = " + questions);
		Assert.assertEquals(1, questions.size());
	}

	// ========================= PRIVATE METHODS ===========================

	/*
	 * @Test public void testMisc_Clone() throws Exception { Question question =
	 * createMockQuestion(); Question clone = question.clone();
	 * Equalizer.examineObject(question, clone); }
	 */

	/**
	 * @return
	 */
	private SubTopic getSubTopic()
	{
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		List<SubTopic> subTopics = bdItem.findByName(SubTopic.class, "Trigonometry");
		SubTopic subTopic = subTopics.iterator().next();
		return subTopic;
	}

	/**
	 * Create a mock mock Question object.
	 * 
	 * @param titleString
	 *            title string
	 * @return mock Question object
	 */
	private static Question createMockQuestion(final String titleString)
	{
		return ItemDemoUtil.createMockQuestion(titleString, (RandomUtil.randomInInterval(
				1, 2) == 1) ? QuestionType.ACADEMIC : QuestionType.CREATIVE, RandomUtil
				.randomInInterval(1, ContentNames.QUESTION.HIGHEST_LEVEL));
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String, Object> getIgnoreMap()
	{
		Map<String, Object> ignoreMap = new HashMap<String, Object>();

		ignoreMap.put("getNumChildren", null);
		ignoreMap.put("getSize", null);
		ignoreMap.put("getChildType", null);
		ignoreMap.put("getChildTypes", null);

		return ignoreMap;
	}

	private void displayQuestion(Question question)
	{
		for (Choice x : question.getChoices())
			logger.debug("choice id: " + x.getId());

		for (Answer x : question.getAnswers())
			logger.debug("answer id: " + x.getId());

		for (Hint x : question.getHints())
			logger.debug("hint id: " + x.getId());

	}

	/**
	 * helper method for create question DAO.
	 */
	private ItemDAO<Question> createQuestionDAO()
	{
		AbstractEISManager eisManager = environment.getDAOFactory();
		ItemDAO<Question> questionDAO = (ItemDAO<Question>) eisManager.getDAO(
				Question.class, environment.getContext());
		return questionDAO;
	}
}
