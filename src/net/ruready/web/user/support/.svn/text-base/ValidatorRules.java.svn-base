package net.ruready.web.user.support;

import java.util.Collection;

import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.Errors;

public class ValidatorRules
{

	private static final String DEFAULT_ERROR_EMAIL = "error.field.email";
	private static final String DEFAULT_ERROR_ENUM = "error.field.enum";
	private static final String DEFAULT_ERROR_MASK = "error.field.word";
	private static final String DEFAULT_ERROR_MAXLENGTH = "error.field.maxLength";
	private static final String DEFAULT_ERROR_MINLENGTH = "error.field.minlength";
	private static final String DEFAULT_ERROR_REQUIRED_ENTRY = "error.field.null";
	private static final String DEFAULT_ERROR_REQUIRED_SELECT = "error.field.nullselect";
	
	public final Boolean validateRequiredEntry(final String value, final Errors errors, final String field, final String label)
	{
		return validateRequired(value, errors, field, DEFAULT_ERROR_REQUIRED_ENTRY, label);
	}
	
	public final Boolean validateRequiredSelect(final String value, final Errors errors, final String field, final String label)
	{
		return validateRequired(value, errors, field, DEFAULT_ERROR_REQUIRED_SELECT, label);
	}
	
	public final Boolean validateRequiredSelect(final Long value, final Errors errors, final String field, final String label)
	{
		return validateRequired(value, errors, field, DEFAULT_ERROR_REQUIRED_SELECT, label);
	}
	
	public final Boolean validateRequiredSelectMultiple(final Collection<?> values, final Errors errors, final String field, final String label)
	{
		return validateRequiredSelectMultiple(values, errors, field, DEFAULT_ERROR_REQUIRED_SELECT, label);
	}
	
	public final Boolean validateRequiredSelectMultiple(final Collection<?> values, final Errors errors, final String field, final String errorCode, final String label)
	{
		if (values.isEmpty())
		{
			errors.rejectValue(field, errorCode, new Object[] {label}, null);
			return false;
		}
		return true;
	}
	
	public final Boolean validateRequired(final Long value, final Errors errors, final String field, final String errorCode, final String label)
	{
		if (value == null || value < 0L)
		{
			errors.rejectValue(field, errorCode, new Object[] {label}, null);
			return false;
		}
		return true;
	}
	
	public final Boolean validateRequired(final String value, final Errors errors, final String field, final String errorCode, final String label)
	{
		if (GenericValidator.isBlankOrNull(value))
		{
			errors.rejectValue(field, errorCode, new Object[] {label}, null);
			return false;
		}
		return true;
	}
	
	public final Boolean validateEmailValue(final String value, final Errors errors, final String field, final String label)
	{
		return validateEmailValue(value, errors, field, DEFAULT_ERROR_EMAIL, label);
	}
	
	public final Boolean validateEmailValue(final String value, final Errors errors, final String field, final String errorCode, final String label)
	{
		if (!GenericValidator.isEmail(value))
		{
			errors.rejectValue(field, errorCode, new Object[] {label}, null);
			return false;
		}
		return true;
	}
	
	public final Boolean validateMinLength(final String value, final Errors errors, final String field, final String label, final Integer minLength)
	{
		return validateMinLength(value, errors, field, DEFAULT_ERROR_MINLENGTH, label, minLength);
	}
	
	public final Boolean validateMinLength(final String value, final Errors errors, final String field, final String errorCode, final String label, final Integer minLength)
	{
		if (!GenericValidator.minLength(value, minLength))
		{
			errors.rejectValue(field, errorCode, new Object[] { label, minLength }, null);
			return false;
		}
		return true;
	}
	
	public final Boolean validateMaxLength(final String value, final Errors errors, final String field, final String label, final Integer maxLength)
	{
		return validateMaxLength(value, errors, field, DEFAULT_ERROR_MAXLENGTH, label, maxLength);
	}
	
	public final Boolean validateMaxLength(final String value, final Errors errors, final String field, final String errorCode, final String label, final Integer maxLength)
	{
		if (!GenericValidator.maxLength(value, maxLength))
		{
			errors.rejectValue(field, errorCode, new Object[] { label, maxLength }, null);
			return false;
		}
		return true;
	}
	
	public final Boolean validateMask(final String value, final Errors errors, final String field, final String label, final String mask)
	{
		return validateMask(value, errors, field, DEFAULT_ERROR_MASK, label, mask);
	}
	
	public final Boolean validateMask(final String value, final Errors errors, final String field, final String errorCode, final String label, final String mask)
	{
		if (!GenericValidator.isBlankOrNull(value) && !GenericValidator.matchRegexp(value,mask))
		{
			errors.rejectValue(field, errorCode, new Object[] { label }, null);
			return false;
		}
		return true;
	}
	
	public final <E extends Enum<E>> Boolean validateEnum(final String value, final Errors errors, final String field, final String label, final Class<E> clazz)
	{
		return validateEnum(value, errors, field, DEFAULT_ERROR_ENUM, label, clazz);
	}
		
	public final <E extends Enum<E>> Boolean validateEnum(final String value, final Errors errors, final String field, final String errorCode, final String label, final Class<E> clazz)
	{
		try {
			Enum.valueOf(clazz, value);
		}
		catch (final IllegalArgumentException iae) {
			errors.rejectValue(field, errorCode, new Object[] { label }, null);
			return false;
		}
		return true;
	}
}
