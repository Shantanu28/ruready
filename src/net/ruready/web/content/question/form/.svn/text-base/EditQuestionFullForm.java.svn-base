package net.ruready.web.content.question.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.content.concept.entity.Concept;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.skill.entity.Skill;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.text.TextUtil;
import net.ruready.parser.options.exports.ParserOptions;
import net.ruready.web.common.form.PostPopulatableForm;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.EditItemForm;
import net.ruready.web.content.item.form.EditItemFullForm;
import net.ruready.web.content.question.util.EditQuestionUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * A custom form for editing questions.
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
 * @version Jul 30, 2007
 */
public class EditQuestionFullForm extends EditItemFullForm implements PostPopulatableForm
{
	// ========================= CONSTANTS =================================

	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EditQuestionFullForm.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty form.
	 */
	public EditQuestionFullForm()
	{
		super();
	}

	// ========================= IMPLEMENTATION: EditItemFullForm ==========

	/**
	 * @see net.ruready.web.content.item.form.EditItemFullForm#createEditItemForm()
	 */
	@Override
	protected EditItemForm createEditItemForm()
	{
		return new EditQuestionForm();
	}

	/**
	 * @see net.ruready.web.content.item.form.EditItemFullForm#createEditItemFullForm()
	 */
	@Override
	protected EditItemFullForm createEditItemFullForm()
	{
		return new EditQuestionFullForm();
	}

	/**
	 * @see net.ruready.web.content.item.form.EditItemFullForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 * @param mapping
	 * @param request
	 * @see net.ruready.web.content.item.form.EditItemFullForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		EditQuestionForm editQuestionForm = EditQuestionUtil.getQuestionForm(this);

		editQuestionForm.setAnswerDTOs(ListUtils.lazyList(new ArrayList<AnswerDTO>(),
				new Factory()
				{
					public Object create()
					{
						return new AnswerDTO();
					}
				}));

		editQuestionForm.setChoiceDTOs(ListUtils.lazyList(new ArrayList<ChoiceDTO>(),
				new Factory()
				{
					public Object create()
					{
						return new ChoiceDTO();
					}
				}));

		editQuestionForm.setHintDTOs(ListUtils.lazyList(new ArrayList<HintDTO>(),
				new Factory()
				{
					public Object create()
					{
						return new HintDTO();
					}
				}));

	}

	/**
	 * Handle validation of fields only if <code>validate</code> equals
	 * <code>&quot;false&quot;</code> DNE.
	 * 
	 * @see net.ruready.web.content.item.form.EditItemFullForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		// Special case: if there's a validate=true request parameter, force a
		// standard
		// Validator framework validation. Useful for question editing
		// sub-actions.
		String validate = request.getParameter(WebAppNames.ACTION.VALIDATE_TOKEN);
		if ((validate != null) && validate.equals(WebAppNames.ACTION.VALUE_TRUE))
		{
			return super.validate(mapping, request);
		}

		// If a setup or cancel method has been requested, skip validation
		if (!StrutsUtil.isMethodValidated(request))
		{
			return null;
		}

		// Initialize drop-down menus from fields if possible
		EditQuestionForm editQuestionForm = EditQuestionUtil.getQuestionForm(this);
		// editQuestionForm.validate(mapping, request); // Obsolete

		// First validation pass: no parser options present; parametric string
		// form field
		// validation is ignored.
		logger.debug("Validation pass 1");
		ActionErrors errors = super.validate(mapping, request);
		if ((errors != null) && (!errors.isEmpty()))
		{
			return errors;
		}

		if (errors == null)
		{
			// Check if checkboxes should be cleared; because checkbox
			// values are almost never validated, we can put this code
			// at the end of the validate() method. If validation passes,
			// perform these sets. Otherwise, leave the value set in the reset()
			// method as this may be a setup action that's purposely failing
			// validation to go to the JSP first.
		}

		// ==============================================
		// Custom validations: parametric strings
		// ==============================================

		// Create a parser options in the request for subsequent validations of
		// parametric string form fields. This method should not throw an
		// exception
		// because of the first validation pass.
		logger.debug("Creating parser options for validation");
		ParserOptions options = editQuestionForm.createParserOptions();
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.VALIDATOR_PARSER_OPTIONS,
				options);

		// Second validation pass: includes parametric string form field
		// validation through the options request attribute
		logger.debug("Validation pass 2");
		errors = super.validate(mapping, request);
		if ((errors != null) && (!errors.isEmpty()))
		{
			logger.debug("Errors on pass 2; subTopicId = "
					+ editQuestionForm.getSubTopicMenuGroupForm().getSubTopicId());
			return errors;
		}

		// ==============================================
		// Custom validations: bastards
		// ==============================================

		// Validate that all multiple choice fields are filled out.
		boolean existsBlankChoices = false;
		for (ChoiceDTO dto : editQuestionForm.getChoiceDTOs())
		{
			if (dto.getChoiceText().trim().length() == 0)
			{
				existsBlankChoices = true;
				break;
			}
		}

		if (existsBlankChoices)
		{
			if (errors == null)
			{
				errors = new ActionErrors();
			}
			errors.add("existsblankchoices", new ActionMessage(
					"error.question.multChoiceRequired"));
		}

		/*
		 * validate at least one multiple choice is selected
		 */
		/*
		 * boolean multChoiceSelected = false; for (ChoiceDTO dto :
		 * editQuestionForm.getChoiceDTOs()) { if (dto.isCorrect()) {
		 * multChoiceSelected = true; break; } } if (!multChoiceSelected)
		 * errors.add("multChoiceSelected", new ActionMessage(
		 * "error.question.multChoiceNotSelected"));
		 */

		/*
		 * validate there are no null answers
		 */
		boolean existsBlankAnswers = false;
		for (AnswerDTO dto : editQuestionForm.getAnswerDTOs())
		{
			if (dto.getAnswerText().trim().length() == 0)
			{
				existsBlankAnswers = true;
				break;
			}
		}

		if (existsBlankAnswers)
		{
			if (errors == null)
			{
				errors = new ActionErrors();
			}
			errors.add("existsBlankAnswers", new ActionMessage(
					"error.question.solutionRequired"));
		}

		boolean existsBlankHints = false;
		for (HintDTO dto : editQuestionForm.getHintDTOs())
		{
			if (dto.getHint1Text().trim().length() == 0
					|| (editQuestionForm.getQuestionTypeAsString().equals("CREATIVE") && dto
							.getHint2Text().trim().length() == 0)

			)
			{
				existsBlankHints = true;
				break;
			}
		}
		if (existsBlankHints)
		{
			if (errors == null)
			{
				errors = new ActionErrors();
			}
			errors.add("existsBlankHints", new ActionMessage(
					"error.question.hintRequired"));
		}

		return errors;
	}

	/**
	 * Copies tags from form into the item after executing the normal edit item
	 * full form <code>copyTo()</code>.
	 * 
	 * @param valueObject
	 * @param context
	 * @see net.ruready.web.content.item.form.EditItemFullForm#copyTo(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void copyTo(Item valueObject, ApplicationContext context)
	{
		super.copyTo(valueObject, context);

		// ====================================================
		// Copy tag data from form into item
		// ====================================================

		EditQuestionForm eqForm = EditQuestionUtil.getQuestionForm(this);

		// Add concept tags to valueObject. Grid editor widget passes data to
		// the request only if the concepts have been changed. If not, ignore
		// and use the ones already loaded into the valueObject object
		if (!TextUtil.isEmptyString(eqForm.getConceptData()))
		{
			List<Concept> concepts = eqForm.getConcepts();
			valueObject.removeTags(Concept.class);
			valueObject.addTags(concepts);
		}

		// Add skill tags to valueObject. Grid editor widget passes data to the
		// request only if the concepts have been changed. If not, ignore and
		// use the ones already loaded into the valueObject object
		if (!TextUtil.isEmptyString(eqForm.getSkillData()))
		{
			List<Skill> skills = eqForm.getSkills();
			valueObject.removeTags(Skill.class);
			valueObject.addTags(skills);
		}
	}

	// ========================= IMPLEMENTATION: PostPopulatableForm =======

	/**
	 * Custom post-population following standard form population by
	 * {@link BeanUtils}. This is where to read the display tag library's
	 * request parameters to our form bean properties.
	 * 
	 * @param request
	 *            client's request object
	 * @see net.ruready.web.common.form.PostPopulatableForm#postPopulate()
	 */
	@SuppressWarnings("unchecked")
	public void postPopulate(final HttpServletRequest request)
	{
		EditQuestionForm editQuestionForm = EditQuestionUtil.getQuestionForm(this);
		editQuestionForm.postPopulate(request);
	}
}
