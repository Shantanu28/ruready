package net.ruready.web.user.beans;

import java.io.Serializable;

public class SchoolSearchFormBean implements Serializable
{
	private static final long serialVersionUID = -255965318450686123L;

	public static final Integer MIN_SEARCH_STRING_LENGTH = 2;
	
	// Contains a matching school ID
	private Long schoolId;
	
	// Contains the matching school name. This is useful if validation fails on
	// another field
	private String schoolName;
	
	// The search string
	private String searchString;

	public SchoolSearchFormBean() {	}
	
	public Integer getMinimumSearchStringLength()
	{
		return MIN_SEARCH_STRING_LENGTH;
	}
	
	public Long getSchoolId()
	{
		return schoolId;
	}

	public void setSchoolId(final Long schoolId)
	{
		this.schoolId = schoolId;
	}

	public String getSchoolName()
	{
		return schoolName;
	}

	public void setSchoolName(final String schoolName)
	{
		this.schoolName = schoolName;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(final String searchString)
	{
		this.searchString = searchString;
	}
}
