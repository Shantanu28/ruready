package net.ruready.web.user.validator;

import net.ruready.web.user.beans.NewSchoolRequestFormBean;
import net.ruready.web.user.support.AbstractWebFlowValidator;

import org.springframework.validation.Errors;

public class NewSchoolRequestFormValidator extends AbstractWebFlowValidator
{

	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return NewSchoolRequestFormBean.class.isAssignableFrom(clazz);
	}

	/**
	 * @param target
	 * @param errors
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(final Object target, final Errors errors)
	{
		validateRequiredEntry(
				errors, 
				"requestContent", 
				getLabel("user.schoolMemberships.request.entryForm.REQUEST.label"));
	}
}
