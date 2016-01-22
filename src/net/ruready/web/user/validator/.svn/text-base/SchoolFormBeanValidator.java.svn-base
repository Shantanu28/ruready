package net.ruready.web.user.validator;

import net.ruready.web.user.beans.SchoolFormBean;
import net.ruready.web.user.support.AbstractWebFlowValidator;

import org.springframework.validation.Errors;

public class SchoolFormBeanValidator extends AbstractWebFlowValidator
{
	private static final String LABEL_KEY_TEMPLATE = "net.ruready.business.user.entity.property.UserField.%s.label";
	
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return SchoolFormBean.class.isAssignableFrom(clazz);
	}

	public void validate(final Object target, final Errors errors)
	{
		final SchoolFormBean form = (SchoolFormBean) target;
		getRules().validateMinLength(
				form.getSearchString(), 
				errors, 
				"searchString", 
				toLabel("SCHOOL"), 
				form.getMinimumSearchStringLength());
	}
	
	private String toLabel(final String shortLabelKey)
	{
		return getLabelFromTemplate(shortLabelKey, LABEL_KEY_TEMPLATE);
	}

}
