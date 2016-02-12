/*****************************************************************************************
 * Source File: EditQuestionForm.java
 ****************************************************************************************/
package net.ruready.web.content.question.form;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.concept.entity.Concept;
import net.ruready.business.content.interest.entity.Interest;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.question.entity.Answer;
import net.ruready.business.content.question.entity.Choice;
import net.ruready.business.content.question.entity.Hint;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.skill.entity.Skill;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.ArrayUtil;
import net.ruready.common.util.EnumUtil;
import net.ruready.parser.options.exports.ParserOptions;
import net.ruready.parser.param.entity.ParametricEvaluationTarget;
import net.ruready.parser.range.ParamRangeParser;
import net.ruready.parser.range.RangeMap;
import net.ruready.parser.service.exports.DefaultParserBD;
import net.ruready.web.common.form.PostPopulatableForm;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.EditItemForm;
import net.ruready.web.content.item.form.Form2ItemCopier;
import net.ruready.web.content.item.form.Item2FormCopier;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.tag.form.TagMenuForm;
import net.ruready.web.parser.imports.StrutsParserBD;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * Supports both add and edit question views. Supports question editing within
 * the catalog framework; note that questions don't have children but we have to
 * adhere to the general paradigm of {@link EditItemForm}.
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
 * @version Aug 1, 2007
 */
public class EditQuestionForm extends EditItemForm implements QuestionStaticMenuForm,
		PostPopulatableForm
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
	private static final Log logger = LogFactory.getLog(EditQuestionForm.class);

	// ========================= FIELDS ====================================

	// --------------------------------------
	// Data fields
	// --------------------------------------

	/**
	 * Corresponds to the question field with the same name.
	 */
	private String textbook;

	/**
	 * Corresponds to the question field with the same name.
	 */
	private String chapter;

	/**
	 * Corresponds to the question field with the same name.
	 */
	private String questionPage;

	/**
	 * Corresponds to the question field with the same name.
	 */
	private String number;

	/**
	 * Corresponds to the question field with the same name.
	 */
	private String formulation;

	/**
	 * Corresponds to the question field with the same name.
	 */
	private String variables;

	/**
	 * Corresponds to the question field with the same name.
	 */
	private String parameters;

	/**
	 * Corresponds to the question field {@link Question#precisionDigits}
	 */
	private String questionPrecision;

	/**
	 * Corresponds to the question field with the same name.
	 */
	// private boolean commutativeEquality;
	//
	// ---------------------------------------------------------------------
	// Children objects of question (hierarchy external to the catalog tree)
	// a.k.a "bastards"
	// ---------------------------------------------------------------------
	/**
	 * List of solutions.
	 */
	private List<AnswerDTO> answerDTOs = new ArrayList<AnswerDTO>();

	/**
	 * Multiple choices.
	 */
	private List<ChoiceDTO> choiceDTOs = new ArrayList<ChoiceDTO>();

	/**
	 * List of hints.
	 */
	private List<HintDTO> hintDTOs = new ArrayList<HintDTO>();

	/**
	 * List of selected interest IDs.
	 */
	private Long selectedInterests[] = new Long[]
	{};

	// --------------------------------------
	// Helper variables
	// --------------------------------------

	/**
	 * Which choice was selected to the be the correct choice.
	 */
	private int correctChoiceCtr;

	/**
	 * String representation of {@link EditQuestionForm#questionType}.
	 */
	private String questionTypeAsString = QuestionType.ACADEMIC.toString();

	/**
	 * Number of multiple choices.
	 */
	private int numberOfChoices;

	/**
	 * A new topic to add if not found among existing sub-topics.
	 */
	private String addTopic;

	/**
	 * A new sub-topic to add if not found among existing sub-topics.
	 */
	private String addSubTopic;

	// --------------------------------------
	// Drop-down menu data
	// --------------------------------------

	/**
	 * Contains drop-down menu fields of the question's item hierarchy, and
	 * related common operations that appear in search and edit question forms
	 * (course, topic, sub-topic menus).
	 */
	private SubTopicMenuGroupForm subTopicMenuGroupForm = new SubTopicMenuGroupForm();

	/**
	 * Contains drop-down menu fields of the question's tags (interests, skills,
	 * concepts).
	 */
	private TagMenuForm tagMenuForm = new TagMenuForm();

	/**
	 * Drop-down menu option list of question types (e.g. academic, creative).
	 */
	private OptionList questionTypeOptions;

	/**
	 * The list of concepts attached to this question, encoded in JSON format.
	 */
	private String conceptData;

	/**
	 * The list of concept business objects attached to this question.
	 */
	private List<Concept> concepts = new ArrayList<Concept>();

	/**
	 * The list of skills attached to this question, encoded in JSON format.
	 */
	private String skillData;

	/**
	 * The list of skill business objects attached to this question.
	 */
	private List<Skill> skills = new ArrayList<Skill>();

	// --------------------------------------
	// Business object handles
	// --------------------------------------

	/**
	 * Contains tag collections from which we choose the tags of this question.
	 */
	private Subject subject;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty form.
	 */
	public EditQuestionForm()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Creating a new form");
		}
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public EditQuestionForm clone()
	{
		EditQuestionForm dest = new EditQuestionForm();
		try
		{
			PropertyUtils.copyProperties(dest, this);
		}
		catch (Exception e)
		{
			logger.error("Could not clone form");
		}

		dest.setSubTopicMenuGroupForm(subTopicMenuGroupForm.clone());
		dest.setTagMenuForm(tagMenuForm.clone());

		return dest;
	}

	// ========================= IMPLEMENTATION: ValueBean =================

	/**
	 * Needed overrides so we pick up the correct runtime instance variable for
	 * the form.
	 * 
	 * @param valueObject
	 * @param context
	 * @see net.ruready.web.content.item.form.EditItemForm#copyFrom(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.rl.WebApplicationContext)
	 */
	@Override
	public void copyFrom(Item valueObject, final ApplicationContext context)
	{
		logger.debug("copyFrom()");
		Item2FormCopier.copyProperties(this, valueObject);
		Question question = (Question) valueObject;

		// ===========================================================
		// Convert from question parent property to drop-down menu
		// value properties
		// ===========================================================
		subTopicMenuGroupForm.copyFrom(question, context);
		tagMenuForm.copyFrom(question, context);

		// ===========================================================
		// init fields that need type conversion.
		// ===========================================================
		if (question.getQuestionType() != null)
		{
			questionTypeAsString = question.getQuestionType().toString();
		}

		// Version does not adhere to copyProperties() conventions
		setLocalVersion(valueObject.getVersion());

		// ===========================================================
		// Populate bastard DTOs
		// ===========================================================

		// Create the empty ones first, then copy over attributes from the
		// object where appropriate. note that initializeEmptyDTOs
		// relies upon this being initialized to properly set the answerDTOs.
		// ugly, yes. needs to be re-factored.
		initializeDTOs(question);

		// Initialize answers DTO from DB data
		if (question.getAnswers().size() > 0)
		{
			answerDTOs.clear();
			for (Answer answer : question.getAnswers())
			{
				answerDTOs.add(new AnswerDTO(answer));
			}
		}

		// Initialize choice DTO from DB data
		if (question.getChoices().size() > 0)
		{

			int i = 0;
			choiceDTOs.clear();

			for (Choice choice : question.getChoices())
			{
				choiceDTOs.add(new ChoiceDTO(choice));
				if (choice.isCorrect())
					this.setCorrectChoiceCtr(i);
				i++;
			}
			this.numberOfChoices = i;
		}
		else
		{
			// Just set numberofChoices to the default value.
			this.numberOfChoices = ContentNames.QUESTION.DEFAULT_NUMBER_OF_CHOICES;

			for (int z = 0; z < numberOfChoices; z++)
			{
				choiceDTOs.add(new ChoiceDTO());
			}
		}

		// Initialize hint DTO from DB data
		if (question.getHints().size() > 0)
		{

			hintDTOs.clear();
			for (Hint hint : question.getHints())
			{
				hintDTOs.add(new HintDTO(hint));
			}
		}

		// ===========================================================
		// Populate tag data
		// ===========================================================

		// Copy interest tag IDs into the interest selection array
		List<Long> selectedInterestIds = new ArrayList<Long>();
		for (TagItem tag : question.getTags(Interest.class))
		{
			selectedInterestIds.add(tag.getId());
		}
		selectedInterests = selectedInterestIds.toArray(new Long[]
		{});

		// Copy concepts to concept array. In the future we will use the same
		// reference instead of copying the array.
		concepts = new ArrayList<Concept>();
		for (TagItem tag : question.getTags(Concept.class))
		{
			concepts.add(new Concept((Concept)tag));
		}

		// Copy skills to skill array. In the future we will use the same
		// reference instead of copying the array.
		skills = new ArrayList<Skill>();
		for (TagItem tag : question.getTags(Skill.class))
		{
			skills.add(new Skill((Skill)tag));
		}
}

	/**
	 * Initialize a question and it's child objects with data from this form and
	 * its DTO lists. List management is smart about checking for adds, updates,
	 * and deletes when comparing lists of entities to lists of DTOs.
	 * <p>
	 * d Needs to be overridden so that we pick up the correct runtime instance
	 * variable for the form.
	 * 
	 * @param valueObject
	 * @param applicationContext
	 * @see net.ruready.web.content.item.form.EditItemForm#copyTo(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.rl.WebApplicationContext)
	 */
	@Override
	public void copyTo(Item valueObject, final ApplicationContext context)
	{
		logger.debug("copyTo()");

		// Copy over the sub-topic ID field into parent ID inside the form, if
		// this is a new item
		if (isNewItem()
				&& TextUtil.isValidId(subTopicMenuGroupForm.getSubTopicIdAsLong()))
		{
			setParentId(subTopicMenuGroupForm.getSubTopicIdAsLong());
		}

		// ===========================================================
		// Basic property copying (simple type conversions)
		// ===========================================================
		Form2ItemCopier.copyProperties(valueObject, this);

		// Version does not adhere to BeanUtils.copyProperties() conventions
		if (getLocalVersion() != null)
		{
			valueObject.setLocalVersion(getLocalVersion());
		}

		// ===========================================================
		// Conversion from drop-down menu value to IDs/objects
		// ===========================================================
		Question question = (Question) valueObject;
		subTopicMenuGroupForm.copyTo(valueObject, context);
		tagMenuForm.copyTo(valueObject, context);

		// ==================================================================
		// Handle special type conversions
		// ==================================================================
		question.setQuestionType(EnumUtil.createFromString(QuestionType.class,
				this.questionTypeAsString));

		// ==================================================================
		// Copy answer DTOs to answers.
		// ==================================================================
		boolean matchFound = false;
		List<Answer> newAnswers = new ArrayList<Answer>();
		for (AnswerDTO dto : answerDTOs)
		{
			for (Answer existingAnswer : question.getAnswers())
			{
				if (dto.getId() != null && dto.getId().equals(existingAnswer.getId()))
				{
					try
					{
						BeanUtils.copyProperties(existingAnswer, dto);
					}
					catch (IllegalAccessException e)
					{
						throw new RuntimeException(e);
					}
					catch (InvocationTargetException e)
					{
						throw new RuntimeException(e);
					}
					matchFound = true;
					break;
				}
			}
			if (matchFound == false)
			{
				Answer newAnswer = new Answer();
				try
				{
					BeanUtils.copyProperties(newAnswer, dto);
				}
				catch (IllegalAccessException e)
				{
					throw new RuntimeException(e);
				}
				catch (InvocationTargetException e)
				{
					throw new RuntimeException(e);
				}
				newAnswers.add(newAnswer);
			}
			matchFound = false;
		}

		// ==================================================================
		// Check for deletions
		// ==================================================================
		matchFound = false;
		List<Answer> answersToBeRemoved = new ArrayList<Answer>();
		for (Answer existingAnswer : question.getAnswers())
		{
			for (AnswerDTO dto : answerDTOs)
			{
				if (dto.getId() != null && dto.getId().equals(existingAnswer.getId()))
				{
					matchFound = true;
					break;
				}
			}
			if (matchFound == false)
			{
				answersToBeRemoved.add(existingAnswer);
			}
			matchFound = false;
		}
		for (Answer answer : answersToBeRemoved)
			// remove
			question.getAnswers().remove(answer);

		question.getAnswers().addAll(newAnswers);

		/*
		 * copy dto choice to choice
		 */
		matchFound = false;
		List<Choice> newChoices = new ArrayList<Choice>();
		// check for updates, adds
		for (ChoiceDTO dto : choiceDTOs)
		{
			for (Choice existingChoice : question.getChoices())
			{
				if (dto.getId() != null && dto.getId().equals(existingChoice.getId()))
				{
					try
					{
						BeanUtils.copyProperties(existingChoice, dto);
					}
					catch (IllegalAccessException e)
					{
						throw new RuntimeException(e);
					}
					catch (InvocationTargetException e)
					{
						throw new RuntimeException(e);
					}
					matchFound = true;
					break;
				}
			}
			if (matchFound == false) // &&
			// dto.getChoiceText().trim().length() >
			// 0
			{
				Choice newChoice = new Choice();
				try
				{
					BeanUtils.copyProperties(newChoice, dto);
				}
				catch (IllegalAccessException e)
				{
					throw new RuntimeException(e);
				}
				catch (InvocationTargetException e)
				{
					throw new RuntimeException(e);
				}
				newChoices.add(newChoice);
			}
			matchFound = false;
		}
		// set correct choice, before handling deletions...
		int i = 0;
		for (Choice choice : question.getChoices())
		{
			if (i == correctChoiceCtr)
				choice.setCorrect(true);
			else
				choice.setCorrect(false);
			i++;
		}
		// check for deletions
		matchFound = false;
		List<Choice> toBeRemoved = new ArrayList<Choice>();
		for (Choice existingChoice : question.getChoices())
		{
			for (ChoiceDTO dto : choiceDTOs)
			{
				if (dto.getId() != null && dto.getId().equals(existingChoice.getId()))
				{
					matchFound = true;
					break;
				}
			}
			if (matchFound == false)
			{
				toBeRemoved.add(existingChoice);
			}
			matchFound = false;
		}
		for (Choice choice : toBeRemoved)
			// remove
			question.getChoices().remove(choice);

		question.getChoices().addAll(newChoices); // add all with ids of
		// null...

		/*
		 * copy dto hint to hint
		 */
		matchFound = false;
		List<Hint> newHints = new ArrayList<Hint>();
		for (HintDTO dto : hintDTOs)
		{
			for (Hint existingHint : question.getHints())
			{
				if (dto.getId() != null && dto.getId().equals(existingHint.getId()))
				{
					try
					{
						BeanUtils.copyProperties(existingHint, dto);
					}
					catch (IllegalAccessException e)
					{
						throw new RuntimeException(e);
					}
					catch (InvocationTargetException e)
					{
						throw new RuntimeException(e);
					}
					matchFound = true;
					break;
				}
			}
			if (matchFound == false)
			{
				Hint newHint = new Hint();
				try
				{
					BeanUtils.copyProperties(newHint, dto);
				}
				catch (IllegalAccessException e)
				{
					throw new RuntimeException(e);
				}
				catch (InvocationTargetException e)
				{
					throw new RuntimeException(e);
				}
				newHints.add(newHint);
			}
			matchFound = false;
		}
		question.getHints().addAll(newHints);

		// Translate interest selection (IDs) into question tags, using
		// the subject's interest collection
		question.removeTags(Interest.class);
		for (Long tagId : selectedInterests)
		{
			TagItem tag = (TagItem) subject.getInterestCollection().findChildById(tagId);
			question.addTag(tag);
		}
	}

	// ========================= IMPLEMENTATION: ActionForm ================

	/**
	 * Used to initialize drop-down menus. Must return <code>null</code>. All
	 * validations of this form have been moved to the encompassing
	 * {@link EditQuestionFullForm#validate(ActionMapping, HttpServletRequest)}.
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 * @see net.ruready.web.content.item.form.EditItemForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		return null;
	}

	/**
	 * Moved to the encompassing {@link EditQuestionFullForm}.
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 */
	public ActionErrors validateDeprecated(ActionMapping mapping,
			HttpServletRequest request)
	{
		// First validation pass: no parser options present; parametric string
		// form field validation is ignored.
		logger.debug("Validation pass 1");
		ActionErrors errors = super.validate(mapping, request);
		if ((errors != null) && (!errors.isEmpty()))
		{
			return errors;
		}

		// Create a parser options in the request for subsequent validations of
		// parametric string form fields. This method should not throw an
		// exception
		// because of the first validation pass.
		logger.debug("Creating parser options for validation");
		ParserOptions options = this.createParserOptions();
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.VALIDATOR_PARSER_OPTIONS,
				options);

		// Second validation pass: includes parametric string form field
		// validation through the options request attribute
		logger.debug("Validation pass 2");
		return super.validate(mapping, request);
	}

	// ========================= IMPLEMENTATION: EditItemForm ==============

	/**
	 * Method reset to be called by parent forms of this object.
	 * 
	 * @param mapping
	 * @param request
	 */
	@Override
	public void reset()
	{
		// Make sure we satisfy the validation rules
		super.reset();
		if (subTopicMenuGroupForm == null)
		{
			subTopicMenuGroupForm = new SubTopicMenuGroupForm();
		}
		subTopicMenuGroupForm.reset();

		if (tagMenuForm == null)
		{
			tagMenuForm = new TagMenuForm();
		}
		tagMenuForm.reset();

		questionTypeAsString = null;
		selectedInterests = null;
		textbook = null;
		chapter = null;
		questionPage = null;
		number = null;
		formulation = null;
		variables = null;
		parameters = null;
		questionPrecision = null;
		addTopic = null;
		addSubTopic = null;
		conceptData = null;
		concepts = new ArrayList<Concept>();
		skills = new ArrayList<Skill>();
		skillData = null;
	}

	/**
	 * Check if the form is empty or partially filled.
	 * 
	 * @return is the form empty or partially filled
	 */
	@Override
	public boolean isEmpty()
	{
		return false;
	}

	// ========================= IMPLEMENTATION: QuestionStaticMenuForm ====

	/**
	 * @return the questionTypeOptions
	 */
	public OptionList getQuestionTypeOptions()
	{
		return questionTypeOptions;
	}

	/**
	 * @param questionTypeOptions
	 *            the questionTypeOptions to set
	 */
	public void setQuestionTypeOptions(OptionList questionTypeOptions)
	{
		this.questionTypeOptions = questionTypeOptions;
	}

	// ========================= IMPLEMENTATION: PostPopulatableForm =======

	/**
	 * Custom post-population following standard form population by
	 * {@link BeanUtils}. This is where to parser JSON strings into tag lists.
	 * 
	 * @param request
	 *            client's request object
	 * @see net.ruready.web.common.form.PostPopulatableForm#postPopulate()
	 */
	@SuppressWarnings("unchecked")
	public void postPopulate(final HttpServletRequest request)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("postPopulate()");
		}

		// ====================================================
		// Decode tag data into attached business objects
		// ====================================================
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);

		// Decode JSON into tag lists(Grid editor widget passes data to
		// the request only if the tags have been changed, though.)
		if (!TextUtil.isEmptyString(conceptData))
		{
			concepts = bdItem.fromJSON(Concept.class, conceptData);
		}
		if (!TextUtil.isEmptyString(skillData))
		{
			skills = bdItem.fromJSON(Skill.class, skillData);
		}
	}

	// ========================= METHODS ===================================

	/**
	 * Update the choice DTOs based upon user driven changes to the
	 * numberOfChoices field, or the updateQuestionType control.
	 */
	public void updateDTOs()
	{
		/*
		 * update answer and hint DTOs based upon possible changes to the
		 * updateQuestionType
		 */
		int answerCount;
		if (questionTypeAsString.equals("CREATIVE"))
			answerCount = 2;
		else
			// academic
			answerCount = 1;

		if (answerCount == 1 && answerDTOs.size() == 2)
			answerDTOs.remove(1); // remove 2nd DTO

		if (answerCount == 2 && answerDTOs.size() == 1)
			answerDTOs.add(new AnswerDTO()); // add 2nd DTO

		// zero out the values for hint2 and keyword 2. dont want garbage going
		// back to the db.
		if (questionTypeAsString.equals("ACADEMIC"))
		{
			for (HintDTO dto : this.getHintDTOs())
			{
				dto.setHint2Text(null);
				dto.setKeyword2Text(null);
			}
		}

		/*
		 * update choiceDTOs based upon possible changes to the numberOfChoices
		 * field
		 */
		int startSize = getChoiceDTOs().size();
		if (getChoiceDTOs().size() > numberOfChoices) // remove dtos
		{
			for (int i = 0; i < (startSize - numberOfChoices); i++)
				getChoiceDTOs().remove(getChoiceDTOs().size() - 1);

		}
		else if (getChoiceDTOs().size() < numberOfChoices) // add dtos
		{
			for (int i = 0; i < (numberOfChoices - startSize); i++)
				getChoiceDTOs().add(new ChoiceDTO());
		}

	}

	/**
	 * Initialization of empty DTOs for first adds or edits. Also initialize the
	 * course and subTopic list-boxes correctly.
	 * 
	 * @param question
	 */
	private void initializeDTOs(Question question)
	{
		/*
		 * course and subTopic list initialization for first time
		 */

		/*
		 * general DTO work
		 */
		answerDTOs.clear();
		int answerCount;
		if (questionTypeAsString.equals("CREATIVE"))
			answerCount = 2;
		else
			// academic
			answerCount = 1;

		for (int i = 0; i < answerCount; i++)
			answerDTOs.add(new AnswerDTO());

		choiceDTOs.clear();
		for (int i = 0; i < numberOfChoices; i++)
			choiceDTOs.add(new ChoiceDTO());

		hintDTOs.clear();
		hintDTOs.add(new HintDTO());
		hintDTOs.add(new HintDTO());
		hintDTOs.add(new HintDTO());
		hintDTOs.add(new HintDTO());
	}

	/**
	 * Create parser options from some form fields for advanced form validation.
	 * This method assumes that the form has been successfully validated, except
	 * for the parameter range map field.
	 */
	public ParserOptions createParserOptions()
	{
		// Initialize options
		ParserOptions options = new ParserOptions();

		// We don't set variables; this object is used to evaluate
		// parametric strings in the form.
		// VariableMap vm = new DefaultVariableMap(variables, false);
		// options.setVariables(vm);

		// Set variables to a random pick from the parameter range map domain
		ParamRangeParser parser = new ParamRangeParser();
		parser.match(parameters);
		RangeMap paramRanges = parser.getRangeMap();
		options.setVariables(paramRanges.randomPick());

		// Set precision
		options.setPrecisionTol(Double.parseDouble(questionPrecision));

		// Set commutative equality (obsolete)

		return options;
	}

	/**
	 * Evaluate a parametric string.
	 * 
	 * @param parametricString
	 *            string containing parameters to be evaluated
	 * @param options
	 *            parser control options and in particular variable value map
	 * @return true if and only if string is a valid parametric string
	 */
	@Deprecated
	public boolean validateParametricString(final String parametricString,
			final ParserOptions options)
	{
		// Evaluate the string
		DefaultParserBD bd = new StrutsParserBD();
		ParametricEvaluationTarget pet = bd.evaluate(parametricString, options);
		return ((pet != null) && pet.isLegal());
	}

	/**
	 * Set one of the hierarchical item drop-down menu's selection id.
	 * 
	 * @param itemType
	 *            item type
	 * @param itemId
	 *            id of selected item
	 */
	@Override
	public void setMenuId(final ItemType itemType, final long itemId)
	{
		// Form properties subTopicMenuGroupForm, tagMenuForm treat disjoint
		// fields, so do both
		// - at most one of them will have an effect. Hence they can both be
		// called here
		// if necessary.
		subTopicMenuGroupForm.setMenuId(itemType, itemId);
	}

	/**
	 * Set one of the hierarchical item multi-selection menu's.
	 * 
	 * @param itemType
	 *            item type
	 * @param itemSelection
	 *            id of selected items
	 */
	public void setMenuSelection(final ItemType itemType, final String[] itemSelection)
	{
		// Form properties treat disjoint fields, so do both - at most one of
		// them
		// will have an effect.
		tagMenuForm.setMenuSelection(itemType, itemSelection);
	}

	// ========================= PRIVATE METHODS ===========================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @see net.ruready.web.content.item.form.EditItemForm#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id)
	{
		if (id != null && id == 0)
			return;

		super.setId(id);
	}

	/**
	 * @return
	 */
	public String getChapter()
	{
		return chapter;
	}

	/**
	 * @param chapter
	 */
	public void setChapter(String chapter)
	{
		this.chapter = chapter;
	}

	//
	// /**
	// * @return
	// */
	// public boolean isCommutativeEquality()
	// {
	// return commutativeEquality;
	// }
	//
	// /**
	// * @param commutativeEquality
	// */
	// public void setCommutativeEquality(boolean commutativeEquality)
	// {
	// this.commutativeEquality = commutativeEquality;
	// }

	/**
	 * @return
	 */
	public String getFormulation()
	{
		return formulation;
	}

	/**
	 * @param formulation
	 */
	public void setFormulation(String formulation)
	{
		this.formulation = formulation;
	}

	/**
	 * Return the conceptData property.
	 * 
	 * @return the conceptData
	 */
	public String getConceptData()
	{
		return conceptData;
	}

	/**
	 * Set a new value for the conceptData property.
	 * 
	 * @param conceptData
	 *            the conceptData to set
	 */
	public void setConceptData(String conceptData)
	{
		this.conceptData = conceptData;
	}

	/**
	 * Returns the skillData property.
	 * 
	 * @return the skillData
	 */
	public String getSkillData()
	{
		return skillData;
	}

	/**
	 * Sets a new skillData property value.
	 * 
	 * @param skillData
	 *            the skillData to set
	 */
	public void setSkillData(String skillData)
	{
		this.skillData = skillData;
	}

	/**
	 * @return
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return
	 */
	public int getNumberOfChoices()
	{
		return numberOfChoices;
	}

	/**
	 * @return
	 */
	public String getQuestionPage()
	{
		return questionPage;
	}

	/**
	 * @param questionPage
	 */
	public void setQuestionPage(String questionPage)
	{
		this.questionPage = questionPage;
	}

	/**
	 * @return
	 */
	public String getParameters()
	{
		return parameters;
	}

	/**
	 * @param parameters
	 */
	public void setParameters(String parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * @return the questionPrecision
	 */
	public String getQuestionPrecision()
	{
		return questionPrecision;
	}

	/**
	 * @param questionPrecision
	 *            the questionPrecision to set
	 */
	public void setQuestionPrecision(String questionPrecision)
	{
		this.questionPrecision = questionPrecision;
	}

	/**
	 * @return
	 */
	public String getTextbook()
	{
		return textbook;
	}

	/**
	 * @param textbook
	 */
	public void setTextbook(String textbook)
	{
		this.textbook = textbook;
	}

	/**
	 * @return
	 */
	public String getVariables()
	{
		return variables;
	}

	/**
	 * @param variables
	 */
	public void setVariables(String variables)
	{
		this.variables = variables;
	}

	/**
	 * @return
	 */
	public List<AnswerDTO> getAnswerDTOs()
	{
		return answerDTOs;
	}

	/**
	 * @param answerDTOHolder
	 */
	public void setAnswerDTOs(List<AnswerDTO> answerDTOHolder)
	{
		this.answerDTOs = answerDTOHolder;
	}

	/**
	 * @return
	 */
	public List<ChoiceDTO> getChoiceDTOs()
	{
		return choiceDTOs;
	}

	/**
	 * @param choiceDTOs
	 */
	public void setChoiceDTOs(List<ChoiceDTO> choiceDTOs)
	{
		this.choiceDTOs = choiceDTOs;
	}

	/**
	 * @return
	 */
	public List<HintDTO> getHintDTOs()
	{
		return hintDTOs;
	}

	public void setHintDTOs(List<HintDTO> hintDTOs)
	{
		this.hintDTOs = hintDTOs;
	}

	/**
	 * @return
	 */
	public int getCorrectChoiceCtr()
	{
		return correctChoiceCtr;
	}

	/**
	 * @param correctChoiceCtr
	 */
	public void setCorrectChoiceCtr(int correctChoiceCtr)
	{
		this.correctChoiceCtr = correctChoiceCtr;
	}

	/**
	 * Returns the selectedInterests property.
	 * 
	 * @return the selectedInterests
	 */
	public Long[] getSelectedInterests()
	{
		return selectedInterests;
	}

	/**
	 * Sets a new selectedInterests property value.
	 * 
	 * @param selectedInterests
	 *            the selectedInterests to set
	 */
	public void setSelectedInterests(Long[] selectedInterests)
	{
		this.selectedInterests = selectedInterests;
	}

	/**
	 * @param numberOfChoices
	 */
	public void setNumberOfChoices(int numberOfChoices)
	{
		this.numberOfChoices = numberOfChoices;
	}

	/**
	 * 
	 */
	public void setQuestionTypeAsString(String questionTypeAsString)
	{
		this.questionTypeAsString = questionTypeAsString;
	}

	/**
	 * @return
	 */
	public String getQuestionTypeAsString()
	{
		return questionTypeAsString;
	}

	/**
	 * @return the addSubTopic
	 */
	public String getAddSubTopic()
	{
		return addSubTopic;
	}

	/**
	 * @param addSubTopic
	 *            the addSubTopic to set
	 */
	public void setAddSubTopic(String addSubTopic)
	{
		this.addSubTopic = addSubTopic;
	}

	/**
	 * @return the addTopic
	 */
	public String getAddTopic()
	{
		return addTopic;
	}

	/**
	 * @param addTopic
	 *            the addTopic to set
	 */
	public void setAddTopic(String addTopic)
	{
		this.addTopic = addTopic;
	}

	/**
	 * @return the subTopicMenuGroupForm
	 */
	public SubTopicMenuGroupForm getSubTopicMenuGroupForm()
	{
		return subTopicMenuGroupForm;
	}

	/**
	 * @param subTopicMenuGroupForm
	 *            the subTopicMenuGroupForm to set
	 */
	public void setSubTopicMenuGroupForm(SubTopicMenuGroupForm subTopicMenuGroupForm)
	{
		this.subTopicMenuGroupForm = subTopicMenuGroupForm;
	}

	/**
	 * @return the tagMenuForm
	 */
	public TagMenuForm getTagMenuForm()
	{
		return tagMenuForm;
	}

	/**
	 * @param tagMenuForm
	 *            the tagMenuForm to set
	 */
	public void setTagMenuForm(TagMenuForm tagMenuForm)
	{
		this.tagMenuForm = tagMenuForm;
	}

	/**
	 * Returns the subject property.
	 * 
	 * @return the subject
	 */
	public Subject getSubject()
	{
		return subject;
	}

	/**
	 * Sets a new subject property value.
	 * 
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(Subject subject)
	{
		this.subject = subject;
	}

	/**
	 * Returns the concepts property.
	 * 
	 * @return the concepts
	 */
	public List<Concept> getConcepts()
	{
		return concepts;
	}

	/**
	 * Returns the concept IDs delimited by ",".
	 * 
	 * @return a string containing the concept IDs delimited by ","
	 */
	public String getConceptIds()
	{
		List<Long> ids = new ArrayList<Long>();
		for (Item tag : concepts)
		{
			ids.add(tag.getId());
		}
		return ArrayUtil
				.toDelimitedString(ids, WebAppNames.JAVASCRIPT.ARGUMENT_SEPARATOR);
	}

	/**
	 * Sets a new concepts property value.
	 * 
	 * @param concepts
	 *            the concepts to set
	 */
	public void setConcepts(List<Concept> concepts)
	{
		this.concepts = concepts;
	}

	/**
	 * Returns the skills property.
	 * 
	 * @return the skills
	 */
	public List<Skill> getSkills()
	{
		return skills;
	}

	/**
	 * Returns the skill IDs delimited by ",".
	 * 
	 * @return a string containing the skill IDs delimited by ","
	 */
	public String getSkillIds()
	{
		List<Long> ids = new ArrayList<Long>();
		for (Item tag : skills)
		{
			ids.add(tag.getId());
		}
		return ArrayUtil
				.toDelimitedString(ids, WebAppNames.JAVASCRIPT.ARGUMENT_SEPARATOR);
	}

	/**
	 * Sets a new skills property value.
	 * 
	 * @param skills
	 *            the skills to set
	 */
	public void setSkills(List<Skill> skills)
	{
		this.skills = skills;
	}

	// ========================= DELEGATE GETTERS & SETTERS ================

}
