package net.ruready.web.user.validator;

import net.ruready.business.user.entity.property.UserField;
import net.ruready.web.user.beans.SearchCriteriaBean;
import net.ruready.web.user.support.AbstractWebFlowValidator;

import org.springframework.validation.Errors;

public class UserSearchFormValidator extends AbstractWebFlowValidator
{	
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return SearchCriteriaBean.class.isAssignableFrom(clazz);
	}
	
	/**
	 * @param target
	 * @param errors
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(final Object target, final Errors errors) { }
	
	/**
	 * @param form
	 * @param errors
	 */
	public void validateCriteriaForm(final SearchCriteriaBean form, final Errors errors)
	{
		validateEnum(errors, 
				"selectedFieldName", 
				getLabel("user.userSearch.view.criteriaForm.fieldname.label"), 
				UserField.class);
	}
	
	public void validateColumnsForm(final SearchCriteriaBean form, final Errors errors)
	{
		getRules().validateRequiredSelectMultiple(
				form.getColumns(), 
				errors, 
				"columns", 
				getLabel("user.userSearch.view.columnsForm.fieldnameSingular.label"));
	}
}
