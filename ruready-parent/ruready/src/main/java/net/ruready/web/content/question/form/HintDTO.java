/*******************************************************
 * Source File: HintDTO.java
 *******************************************************/
package net.ruready.web.content.question.form;

import java.lang.reflect.InvocationTargetException;

import net.ruready.business.content.question.entity.Hint;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.beanutils.BeanUtils;

public class HintDTO
{
	private Long id;

	private String hint1Text = CommonNames.MISC.EMPTY_STRING;

	private String hint2Text = CommonNames.MISC.EMPTY_STRING;

	private String keyword1Text = CommonNames.MISC.EMPTY_STRING;

	private String keyword2Text = CommonNames.MISC.EMPTY_STRING;

	public HintDTO(Hint hint)
	{
		try
		{
			BeanUtils.copyProperties(this, hint);
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

	public HintDTO()
	{
		super();
	}

	public String getHint1Text()
	{
		return hint1Text;
	}

	public void setHint1Text(String hint1Text)
	{
		this.hint1Text = hint1Text;
	}

	public String getHint2Text()
	{
		return hint2Text;
	}

	public void setHint2Text(String hint2Text)
	{
		this.hint2Text = hint2Text;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKeyword1Text()
	{
		return keyword1Text;
	}

	public void setKeyword1Text(String keyword1Text)
	{
		this.keyword1Text = keyword1Text;
	}

	public String getKeyword2Text()
	{
		return keyword2Text;
	}

	public void setKeyword2Text(String keyword2Text)
	{
		this.keyword2Text = keyword2Text;
	}

}
