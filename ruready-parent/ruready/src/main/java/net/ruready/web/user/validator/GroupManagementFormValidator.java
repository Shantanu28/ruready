package net.ruready.web.user.validator;

import org.springframework.validation.Errors;

import net.ruready.web.user.beans.GroupManagementFormBean;
import net.ruready.web.user.support.AbstractWebFlowValidator;

public class GroupManagementFormValidator extends AbstractWebFlowValidator
{
	private static final String LABEL_KEY_TEMPLATE = "net.ruready.business.user.entity.property.GroupField.%s.label";
	
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return GroupManagementFormBean.class.isAssignableFrom(clazz);
	}
	
	/**
	 * @param target
	 * @param errors
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(final Object target, final Errors errors) { }
	
	public void validateCreateGroupForm(final GroupManagementFormBean form, final Errors errors)
	{
		validateGroupName(errors, form.getUserGroup().getName(), "userGroup.name", "NAME");
		validateCourseId(errors, form.getCourseId(), "courseId", "COURSE");
		validateSchoolId(errors, form.getSchoolId(), "schoolId", "SCHOOL");
	}
	
	public void validateEditGroupForm(final GroupManagementFormBean form, final Errors errors)
	{
		validateGroupName(errors, form.getUserGroup().getName(), "userGroup.name", "NAME");		
	}
	
	private Boolean validateGroupName(final Errors errors, final String value, final String field, final String shortLabelKey)
	{
		return (getRules().validateRequiredEntry(value, errors, field, toLabel(shortLabelKey)));
	}

	private Boolean validateCourseId(final Errors errors, final Long value, final String field, final String shortLabelKey)
	{
		return (getRules().validateRequiredSelect(value, errors, field, toLabel(shortLabelKey)));
	}
	
	private Boolean validateSchoolId(final Errors errors, final Long value, final String field, final String shortLabelKey)
	{
		return (getRules().validateRequiredSelect(value, errors, field, toLabel(shortLabelKey)));
	}
	
	private String toLabel(final String shortLabelKey)
	{
		return getLabelFromTemplate(shortLabelKey, LABEL_KEY_TEMPLATE);
	}
}
