package net.ruready.web.user.beans;

import java.io.Serializable;

public class GroupSearchFormBean implements Serializable
{
	private static final long serialVersionUID = -2235887877844353106L;
	
	private String searchString = "";
	private ResultsPagingBean pagingBean = new ResultsPagingBean(0);
	
	public String getSearchString()
	{
		return searchString;
	}
	
	public void setSearchString(final String searchString)
	{
		this.searchString = searchString;
	}
	
	public ResultsPagingBean getPagingBean()
	{
		return pagingBean;
	}
	
	public void setPagingBean(final ResultsPagingBean pagingBean)
	{
		this.pagingBean = pagingBean;
	}
}
