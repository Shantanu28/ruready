package net.ruready.web.ta.validator;

import net.ruready.business.ta.entity.ExpectationAssessmentItem;
import net.ruready.web.ta.beans.ExpectationAssessmentBean;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ExpectationAssessmentFormValidator implements Validator
{
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return ExpectationAssessmentBean.class.isAssignableFrom(clazz);
	}

	public void validate(final Object target, final Errors errors)
	{
		final ExpectationAssessmentBean form = (ExpectationAssessmentBean) target;
		for(ExpectationAssessmentItem item : form.getAssessment().getTestItems())
		{
			if (item.getValue() == null)
			{
				errors.reject("ta.expectationAssessment.entryForm.required.error.message");
				return;
			}
		}
	}

}
