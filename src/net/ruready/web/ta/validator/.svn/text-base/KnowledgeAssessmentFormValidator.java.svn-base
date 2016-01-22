package net.ruready.web.ta.validator;

import net.ruready.web.ta.beans.KnowledgeAssessmentBean;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class KnowledgeAssessmentFormValidator implements Validator
{

	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return KnowledgeAssessmentBean.class.isAssignableFrom(clazz);
	}

	/**
	 * @param target
	 * @param errors
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(final Object target, final Errors errors)
	{
	}
	
	public void validateMultipleChoiceQuestion(final KnowledgeAssessmentBean form, final Errors errors)
	{
		if (StringUtils.isBlank(form.getAnswer()))
		{
			errors.reject("ta.knowledgeAssessment.entryForm.required.error.message");
			return;
		}
	}

	public void validateOpenEndedQuestion(final KnowledgeAssessmentBean form, final Errors errors)
	{
		// TODO make changes to this method once the page is put together
		validateMultipleChoiceQuestion(form, errors);
	}
}
