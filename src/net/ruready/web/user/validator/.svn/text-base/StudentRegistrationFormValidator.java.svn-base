package net.ruready.web.user.validator;

import org.springframework.validation.Errors;

import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.common.discrete.Gender;
import net.ruready.web.user.beans.StudentRegistrationFormBean;
import net.ruready.web.user.support.AbstractWebFlowValidator;

public class StudentRegistrationFormValidator extends AbstractWebFlowValidator
{
	private static final String LABEL_KEY_TEMPLATE = "net.ruready.business.user.entity.property.UserField.%s.label";
	
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return StudentRegistrationFormBean.class.isAssignableFrom(clazz);
	}

	public void validate(final Object target, final Errors errors)
	{
		validateEntryForm((StudentRegistrationFormBean) target, errors);
	}

	/**
	 * @param form
	 * @param errors
	 */
	public void validateEntryForm(final StudentRegistrationFormBean form, final Errors errors)
	{
		validateEmail(errors, "user.email", toLabel("EMAIL"));
		validateEnum(errors, "user.gender", toLabel("GENDER"), Gender.class);
		validateEnum(errors, "user.ethnicity", toLabel("ETHNICITY"), Ethnicity.class);
		validateEnum(errors, "user.ageGroup", toLabel("AGE_GROUP"), AgeGroup.class);
		validateEnum(errors, "user.language", toLabel("LANGUAGE"), Language.class);
	}
	
	public void validateSchoolSearchForm(final StudentRegistrationFormBean form, final Errors errors)
	{
		getRules().validateMinLength(
				form.getSchoolSearch().getSearchString(), 
				errors, 
				"searchString", 
				toLabel("SCHOOL"), 
				form.getSchoolSearch().getMinimumSearchStringLength());
	}
	
	private String toLabel(final String shortLabelKey)
	{
		return getLabelFromTemplate(shortLabelKey, LABEL_KEY_TEMPLATE);
	}
}
