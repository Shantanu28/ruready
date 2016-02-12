/*******************************************************
 * Source File: ChoiceDTO.java
 *******************************************************/
package net.ruready.web.content.question.form;

import java.lang.reflect.InvocationTargetException;

import net.ruready.business.content.question.entity.Choice;

import org.apache.commons.beanutils.BeanUtils;

public class ChoiceDTO
{

	private Long id;

	private String choiceText;

	private boolean correct;

	public boolean isCorrect()
	{
		return correct;
	}

	public void setCorrect(boolean correct)
	{
		this.correct = correct;
	}

	public ChoiceDTO()
	{
		super();
	}

	public ChoiceDTO(Choice choice)
	{
		try
		{
			BeanUtils.copyProperties(this, choice);
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

	public String getChoiceText()
	{
		return choiceText;
	}

	public void setChoiceText(String choiceText)
	{
		this.choiceText = choiceText;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

}
