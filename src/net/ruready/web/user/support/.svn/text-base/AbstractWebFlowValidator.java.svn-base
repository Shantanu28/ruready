package net.ruready.web.user.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractWebFlowValidator implements Validator, MessageSourceAware
{	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MessageSourceAccessor messageSourceAccessor;
	private final ValidatorRules rules = new ValidatorRules();
	
	protected final Boolean validateRequiredEntry(final Errors errors, final String field, final String label)
	{
		final String value = (String) getValue(errors, field);
		return (getRules().validateRequiredEntry(value, errors, field, label));
	}
	
	protected final Boolean validateEmail(final Errors errors, final String field, final String label)
	{
		final String value = (String) getValue(errors, field);
		return (getRules().validateRequiredEntry(value, errors, field, label) && 
				getRules().validateMinLength(value, errors, field, label, 4) &&
				getRules().validateMaxLength(value, errors, field, label, 40) &&
				getRules().validateEmailValue(value, errors, field, label));
	}
	
	protected final Boolean validateFirstOrLastName(final Errors errors, final String field, final String label)
	{
		final String value = (String) getValue(errors, field);
		return (getRules().validateRequiredEntry(value, errors, field, label) && 
				getRules().validateMaxLength(value, errors, field, label, 40) &&
				getRules().validateMask(value, errors, field, label, "^[a-zA-Z- ]*$"));
	}
	
	protected final Boolean validateFirstOrLastNameOptional(final Errors errors, final String field, final String label)
	{
		final String value = (String) getValue(errors, field);
		return (getRules().validateMaxLength(value, errors, field, label, 40) &&
				getRules().validateMask(value, errors, field, label, "^[a-zA-Z- ]*$"));
	}
	
	protected final Boolean validatePassword(final Errors errors, final String field, final String label)
	{
		final String value = (String) getValue(errors, field);
		return (getRules().validateRequiredEntry(value, errors, field, label) && 
				getRules().validateMinLength(value, errors, field, label, 8) &&
				getRules().validateMaxLength(value, errors, field, label, 30));
	}
	
	protected final Boolean validateMiddleInitial(final Errors errors, final String field, final String label)
	{
		final String value = (String) getValue(errors, field);
		return (getRules().validateMaxLength(value, errors, field, "error.field.onechar", label, 1) &&
				getRules().validateMask(value, errors, field, "error.field.initial", label, "^[A-Z]*$"));
	}
	
	protected final <E extends Enum<E>> Boolean validateEnum(final Errors errors, final String field, final String label, final Class<E> clazz)
	{
		final Object value = getValue(errors, field);
		final String stringValue = (value == null?"":value.toString());
		return (getRules().validateRequiredSelect(stringValue, errors, field, label) && 
				getRules().validateEnum(stringValue, errors, field, label, clazz));
	}
	
	protected final String getLabelFromTemplate(final String shortLabelKey, final String labelKeyTemplate)
	{
		return getArgument(String.format(labelKeyTemplate, shortLabelKey));
	}
	
	protected final String getLabel(final String labelKey)
	{
		return getArgument(labelKey);
	}
	
	protected final Object getValue(final Errors errors, final String field)
	{
		return errors.getFieldValue(field);
	}
	
	private String getArgument(final String messageKey)
	{
		return this.messageSourceAccessor.getMessage(messageKey);
	}
	
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	protected final ValidatorRules getRules()
	{
		return rules;
	}
}
