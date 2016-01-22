package net.ruready.web.user.validator;

import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.common.discrete.Gender;
import net.ruready.web.user.beans.TeacherRegistrationFormBean;
import net.ruready.web.user.support.AbstractWebFlowValidator;

import org.springframework.validation.Errors;

public class TeacherRegistrationFormValidator extends AbstractWebFlowValidator
{

private static final String LABEL_KEY_TEMPLATE = "net.ruready.business.user.entity.property.UserField.%s.label";
	
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return TeacherRegistrationFormBean.class.isAssignableFrom(clazz);
	}

	public void validate(final Object target, final Errors errors)
	{
		validateEntryForm((TeacherRegistrationFormBean) target, errors);
	}

	/**
	 * @param form
	 * @param errors
	 */
	public void validateEntryForm(final TeacherRegistrationFormBean form, final Errors errors)
	{
		validateEmail(errors, "user.email", toLabel("EMAIL"));
		validateFirstOrLastName(errors, "user.firstName", toLabel("FIRST_NAME"));
		validateMiddleInitial(errors, "user.middleInitial", toLabel("MIDDLE_INITIAL"));
		validateFirstOrLastName(errors, "user.lastName", toLabel("LAST_NAME"));
		validateEnum(errors, "user.gender", toLabel("GENDER"), Gender.class);
		validateEnum(errors, "user.ethnicity", toLabel("ETHNICITY"), Ethnicity.class);
		validateEnum(errors, "user.ageGroup", toLabel("AGE_GROUP"), AgeGroup.class);
		validateEnum(errors, "user.language", toLabel("LANGUAGE"), Language.class);
	}
	
	public void validateSchoolSearchForm(final TeacherRegistrationFormBean form, final Errors errors)
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
