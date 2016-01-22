package net.ruready.web.user.validator;

import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.discrete.Gender;
import net.ruready.web.user.beans.UserAccountFormBean;
import net.ruready.web.user.beans.UserManagementFormBean;
import net.ruready.web.user.support.AbstractWebFlowValidator;

import org.springframework.validation.Errors;

public class UserManagementFormValidator extends AbstractWebFlowValidator
{
	private static final String LABEL_KEY_TEMPLATE = "net.ruready.business.user.entity.property.UserField.%s.label";
		
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return UserAccountFormBean.class.isAssignableFrom(clazz);
	}

	/**
	 * @param target
	 * @param errors
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(final Object target, final Errors errors) {}
	
	/**
	 * @param form
	 * @param errors
	 */
	public void validateAdminUserEntryForm(final UserAccountFormBean form, final Errors errors)
	{
		validateEmail(errors, "user.email", toLabel("EMAIL"));
		validateFullNameRequired(errors);
		validateEnum(errors, "role", toLabel("USER_ROLE"), RoleType.class);
		validateDemographics(errors);
	}
	
	/**
	 * @param form
	 * @param errors
	 */
	public void validateAdminAnonymousUserEntryForm(final UserAccountFormBean form, final Errors errors)
	{
		validateEmail(errors, "user.email", toLabel("EMAIL"));
		validateFullNameOptional(errors);
		validateEnum(errors, "role", toLabel("USER_ROLE"), RoleType.class);
		validateDemographics(errors);
	}
	
	public void validateAdminCreateEntryForm(final UserAccountFormBean form, final Errors errors)
	{
		final UserManagementFormBean formBean = (UserManagementFormBean) form;
		if (formBean.getRole() == RoleType.STUDENT)
		{
			validateAdminAnonymousUserEntryForm(form, errors);
		}
		else
		{
			validateAdminUserEntryForm(form, errors);
		}
	}
	
	/**
	 * @param form
	 * @param errors
	 */
	public void validateSelfUserEntryForm(final UserAccountFormBean form, final Errors errors)
	{
		validateEmail(errors, "user.email", toLabel("EMAIL"));
		validateFullNameRequired(errors);
		validateDemographics(errors);
	}
	
	/**
	 * @param form
	 * @param errors
	 */
	public void validateSelfAnonymousUserEntryForm(final UserAccountFormBean form, final Errors errors)
	{
		validateEmail(errors, "user.email", toLabel("EMAIL"));
		validateFullNameOptional(errors);
		validateDemographics(errors);
	}
	
	private void validateFullNameRequired(final Errors errors)
	{
		validateFirstOrLastName(errors, "user.firstName", toLabel("FIRST_NAME"));
		validateMiddleInitial(errors, "user.middleInitial", toLabel("MIDDLE_INITIAL"));
		validateFirstOrLastName(errors, "user.lastName", toLabel("LAST_NAME"));
	}
	
	private void validateFullNameOptional(final Errors errors)
	{
		validateFirstOrLastNameOptional(errors, "user.firstName", toLabel("FIRST_NAME"));
		validateMiddleInitial(errors, "user.middleInitial", toLabel("MIDDLE_INITIAL"));
		validateFirstOrLastNameOptional(errors, "user.lastName", toLabel("LAST_NAME"));
	}
	
	private void validateDemographics(final Errors errors)
	{
		validateEnum(errors, "user.gender", toLabel("GENDER"), Gender.class);
		validateEnum(errors, "user.ethnicity", toLabel("ETHNICITY"), Ethnicity.class);
		validateEnum(errors, "user.ageGroup", toLabel("AGE_GROUP"), AgeGroup.class);
		validateEnum(errors, "user.language", toLabel("LANGUAGE"), Language.class);
	}
	
	private String toLabel(final String shortLabelKey)
	{
		return getLabelFromTemplate(shortLabelKey, LABEL_KEY_TEMPLATE);
	}
}
