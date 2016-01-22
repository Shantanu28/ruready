package net.ruready.web.user.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.ruready.business.user.entity.property.UserField;
import net.ruready.business.user.entity.property.ViewableUserField;
import net.ruready.common.search.Logic;
import net.ruready.common.search.SortType;
import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionList;

public class SearchCriteriaBean implements Serializable
{
	private static final long serialVersionUID = 4248733932182107358L;
	private Logic logic; 
	private List<SearchCriterionBean> criteria = new ArrayList<SearchCriterionBean>();
	private SearchCriteriaOptions criteriaOptions;
	private Map<UserField, OptionList> valueOptions;
	private UserField selectedFieldName;
	private ViewableUserField sortByFieldName;
	private SortType sortOrder;
	private List<Option> columns = new ArrayList<Option>();
	private List<Boolean> checkedColumns = new ArrayList<Boolean>();
	private ResultsPagingBean pagingBean;
	
	public Logic getLogic()
	{
		return logic;
	}
	
	public void setLogic(final Logic logic)
	{
		this.logic = logic;
	}
	
	public List<SearchCriterionBean> getCriteria()
	{
		return criteria;
	}
	
	public void setCriteria(final List<SearchCriterionBean> criteria)
	{
		this.criteria = criteria;
	}
	
	public SearchCriteriaOptions getCriteriaOptions()
	{
		return criteriaOptions;
	}
	
	public void setCriteriaOptions(final SearchCriteriaOptions criteriaOptions)
	{
		this.criteriaOptions = criteriaOptions;
	}

	public Map<UserField, OptionList> getValueOptions()
	{
		return valueOptions;
	}

	public void setValueOptions(final Map<UserField, OptionList> valueOptions)
	{
		this.valueOptions = valueOptions;
	}

	public UserField getSelectedFieldName()
	{
		return selectedFieldName;
	}

	public void setSelectedFieldName(final UserField selectedFieldName)
	{
		this.selectedFieldName = selectedFieldName;
	}
	
	public ViewableUserField getSortColumn()
	{
		return this.sortByFieldName;
	}
	
	public void setSortColumn(final ViewableUserField sortByFieldName)
	{
		this.sortByFieldName = sortByFieldName;
	}
	
	public SortType getSortOrder()
	{
		return this.sortOrder;
	}
	
	public void setSortOrder(final SortType sortOrder)
	{
		this.sortOrder = sortOrder;
	}
	
	public Boolean hasColumn(final String columnValue)
	{
		for(Option bean : getColumns())
		{
			if (bean.getValue().equals(columnValue))
			{
				return true;
			}
		}
		return false;
	}

	public List<Option> getColumns()
	{		
		return columns;
	}
	
	public List<Boolean> getCheckedColumns()
	{
		return checkedColumns;
	}

	public void setColumns(final List<ViewableUserField> columns)
	{
		final List<Option> viewableUserFields = getCriteriaOptions().getViewableUserFieldList();
		final List<Option> filteredList = new ArrayList<Option>();
		final List<Boolean> checkedList = new ArrayList<Boolean>();
		
		// to prevent NPEs, just create empty lists
		if (columns != null)
		{
			for (Option bean : viewableUserFields)
			{
				if (columns.contains(ViewableUserField.valueOf(bean.getLabel())))
				{
					filteredList.add(bean);
					checkedList.add(true);
				}
				else
				{
					checkedList.add(false);
				}
			}
		}
		this.columns = filteredList;
		this.checkedColumns = checkedList;
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
