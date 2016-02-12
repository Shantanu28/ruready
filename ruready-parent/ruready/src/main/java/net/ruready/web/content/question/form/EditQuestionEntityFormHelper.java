/*******************************************************
 * Source File: EditQuestionEntityFormHelper.java
 *******************************************************/
package net.ruready.web.content.question.form;

import java.lang.reflect.InvocationTargetException;

import net.ruready.business.content.question.entity.Question;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EditQuestionEntityFormHelper
{
	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(EditQuestionEntityFormHelper.class);

	public static void copyEntityToForm(Question question, EditQuestionForm form)
	{
		try
		{
			BeanUtils.copyProperties(form, question);
		}
		catch (IllegalAccessException e)
		{
			logger.warn("copyproperties warn: " + e.getMessage());
		}
		catch (InvocationTargetException e)
		{
			logger.warn("copyproperties warn: " + e.getMessage());
		}
	}

	public static void copyFormToEntity(EditQuestionForm form, Question question)
	{
		try
		{
			BeanUtils.copyProperties(question, form);
		}
		catch (IllegalAccessException e)
		{
			logger.warn("copyproperties warn: " + e.getMessage());
		}
		catch (InvocationTargetException e)
		{
			logger.warn("copyproperties warn: " + e.getMessage());
		}
	}

}
