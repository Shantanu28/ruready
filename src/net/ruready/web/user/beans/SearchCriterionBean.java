package net.ruready.web.user.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.ruready.business.user.entity.property.UserField;
import net.ruready.common.search.SearchType;

public class SearchCriterionBean implements Serializable
{
	private static final long serialVersionUID = 6383860537848264277L;

	public enum CriterionType { ENUM, TEXT }
	
	private CriterionType criterionType;
	private UserField fieldName;
	private SearchType searchType;
	private String value;
	private Boolean caseSensitive;

	public CriterionType getCriterionType()
	{
		return criterionType;
	}

	public void setCriterionType(final CriterionType criterionType)
	{
		this.criterionType = criterionType;
	}
	
	public UserField getFieldName()
	{
		return fieldName;
	}
	
	public void setFieldName(final UserField fieldName)
	{
		this.fieldName = fieldName;
	}
	
	public SearchType getSearchType()
	{
		return searchType;
	}
	
	public void setSearchType(final SearchType searchType)
	{
		this.searchType = searchType;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(final String value)
	{
		this.value = value;
	}
	
	public Boolean getCaseSensitive()
	{
		return caseSensitive;
	}
	
	public void setCaseSensitive(final Boolean caseSensitive)
	{
		this.caseSensitive = caseSensitive;
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("CriterionType", getCriterionType())
		.append("SearchType", getSearchType())
		.append("Value", getValue())
		.append("CaseSensitive", getCaseSensitive())
		.toString();
	}
}
