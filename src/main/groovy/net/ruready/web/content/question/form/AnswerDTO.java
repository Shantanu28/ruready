/*******************************************************
 * Source File: AnswerDTO.java
 *******************************************************/
package net.ruready.web.content.question.form;

import java.lang.reflect.InvocationTargetException;

import net.ruready.business.content.question.entity.Answer;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.beanutils.BeanUtils;

public class AnswerDTO
{
	Long id;

	String answerText = CommonNames.MISC.EMPTY_STRING;

	public AnswerDTO(Answer answer)
	{
		try
		{
			BeanUtils.copyProperties(this, answer);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}

	public AnswerDTO()
	{
		super();
	}

	public String getAnswerText()
	{
		return answerText;
	}

	public void setAnswerText(String answerText)
	{
		this.answerText = answerText;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		// struts will try to initialize this to 0 upon form resets. we want
		// this to stay as null in these cases.
		if (id != null && id == 0)
			return;

		this.id = id;
	}

}
