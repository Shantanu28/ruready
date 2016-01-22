package net.ruready.web.user.webflow;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserTO;
import net.ruready.business.user.entity.property.UserField;
import net.ruready.business.user.entity.property.UserSearchCriterionFactory;
import net.ruready.business.user.entity.property.ViewableUserField;
import net.ruready.business.user.exports.UserSearchTOBuilder;
import net.ruready.business.user.exports.UserTOBuilder;
import net.ruready.common.search.DefaultSearchCriteria;
import net.ruready.common.search.Logic;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;
import net.ruready.common.search.SortType;
import net.ruready.web.select.exports.OptionList;
import net.ruready.web.user.beans.ResultsPagingBean;
import net.ruready.web.user.beans.SearchCriteriaBean;
import net.ruready.web.user.beans.SearchCriteriaOptions;
import net.ruready.web.user.beans.SearchCriterionBean;
import net.ruready.web.user.beans.UserDemographicOptions;
import net.ruready.web.user.beans.SearchCriterionBean.CriterionType;
import net.ruready.web.user.support.WebFlowSupport;

import org.apache.struts.util.MessageResources;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class UserSearchFormAction extends FormAction
{

	private static final String EXIT_ACTION_PATH = "/user/open/ALL/main.do";

	private static final String PREFIX_ALL = "user.userSearch.view";
	private static final String CANCEL_COLUMNS_MESSAGE = ".columns.cancel.message";
	private static final String CANCEL_CRITERIA_MESSAGE = ".criteria.cancel.message";

	private final WebFlowSupport webFlowSupport = new WebFlowSupport();
	private final UserSearchCriterionFactory searchCriterionFactory = new UserSearchCriterionFactory();

	public UserSearchFormAction()
	{
		setFormObjectClass(SearchCriteriaBean.class);
	}

	/**
	 * Builds the initial form object for use in searching
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Event getSearchFormObject(final RequestContext context) throws Exception
	{
		addExitActionToFlowScope(context);
		final MessageResources messageResources = getWebFlowSupport()
				.getMessageResources(context);
		final SearchCriteriaBean form = getForm(context);

		form.setLogic(Logic.AND);
		form.setCriteriaOptions(new SearchCriteriaOptions(messageResources));
		form.setValueOptions(buildValueOptions(messageResources));
		form.getCriteria().add(getDefaultPrototype());

		form.setSortColumn(ViewableUserField.EMAIL);
		form.setSortOrder(SortType.ASCENDING);

		// initial view columns
		final List<ViewableUserField> columns = Arrays.asList(new ViewableUserField[]
		{ ViewableUserField.EMAIL, ViewableUserField.FIRST_NAME,
				ViewableUserField.MIDDLE_INITIAL, ViewableUserField.LAST_NAME });
		form.setColumns(columns);

		// initial search results bean
		form.setPagingBean(new ResultsPagingBean(0));
		return success();
	}

	/**
	 * Adds a default criteria element.
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Event addCriteria(final RequestContext context) throws Exception
	{
		final SearchCriteriaBean form = getForm(context);
		SearchCriterionBean bean;
		switch (form.getSelectedFieldName())
		{
			case AGE_GROUP:
			case ETHNICITY:
			case GENDER:
			case LANGUAGE:
				bean = getEnumPrototype();
				break;
			case EMAIL:
			case FIRST_NAME:
			case LAST_NAME:
			case MIDDLE_INITIAL:
			case STUDENT_IDENTIFIER:
				bean = getTextPrototype();
				break;
			default:
				throw new BusinessRuleException(
						"addCriteria is not setup to handle UserField \""
								+ form.getSelectedFieldName() + "\"");
		}
		bean.setFieldName(form.getSelectedFieldName());
		form.getCriteria().add(bean);
		form.setSelectedFieldName(null);
		return success();
	}

	/**
	 * Removes a criteria element. If the specified criteriaId is outside the
	 * bounds of the criteria collection, no action is taken. If, after removing
	 * the element, the collection is empty, a prototype criteria element will
	 * be added to the collection.
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Event removeCriteria(final RequestContext context) throws Exception
	{
		final Integer criteriaId = context.getRequestParameters()
				.getInteger("criteriaId");
		final SearchCriteriaBean form = getForm(context);

		if (form.getCriteria().size() > criteriaId)
		{
			form.getCriteria().remove(criteriaId.intValue());
			if (form.getCriteria().isEmpty())
			{
				form.getCriteria().add(getDefaultPrototype());
			}
		}
		return success();
	}

	public Event resetCriteriaType(final RequestContext context) throws Exception
	{
		getForm(context).setSelectedFieldName(null);
		return success();
	}

	public Event performSearch(final RequestContext context) throws Exception
	{
		final SearchCriteriaBean form = getForm(context);
		final List<User> results = getWebFlowSupport().getUserBD(context).findByCriteria(
				createSearchCriteria(form));

		final List<UserTO> filteredResults = getPagedResults(context, form
				.getPagingBean(), results);
		context.getFlashScope().put("results", filteredResults);
		return success();
	}

	public Event doColumnsCancel(final RequestContext context) throws Exception
	{
		getWebFlowSupport().addMessage(context, PREFIX_ALL + CANCEL_COLUMNS_MESSAGE);
		return success();
	}

	public Event doCriteriaCancel(final RequestContext context) throws Exception
	{
		getWebFlowSupport().addMessage(context, PREFIX_ALL + CANCEL_CRITERIA_MESSAGE);
		return success();
	}

	public Event resetSearchPage(final RequestContext context) throws Exception
	{
		getResultsPagingBean(context).setCurrentPage(1);
		return success();
	}

	public Event setFirstPage(final RequestContext context) throws Exception
	{
		getResultsPagingBean(context).gotoFirstPage();
		return success();
	}

	public Event setPreviousPage(final RequestContext context) throws Exception
	{
		getResultsPagingBean(context).gotoPreviousPage();
		return success();
	}

	public Event setNextPage(final RequestContext context) throws Exception
	{
		getResultsPagingBean(context).gotoNextPage();
		return success();
	}

	public Event setLastPage(final RequestContext context) throws Exception
	{
		getResultsPagingBean(context).gotoLastPage();
		return success();
	}

	private List<UserTO> getPagedResults(final RequestContext context,
			final ResultsPagingBean pagingBean, final List<User> results)
	{
		pagingBean.setTotalResults(results.size());

		final MessageResources messageResources = getWebFlowSupport()
				.getMessageResources(context);
		return getUserTOBuilder(messageResources).buildUserTOList(
				results.subList(pagingBean.getCurrentPageFirstResultZeroBased(),
						pagingBean.getCurrentPageLastResult()));
	}

	private SearchCriterionBean getDefaultPrototype()
	{
		return getTextPrototype();
	}

	private SearchCriterionBean getTextPrototype()
	{
		final SearchCriterionBean prototype = new SearchCriterionBean();
		prototype.setCriterionType(CriterionType.TEXT);
		prototype.setFieldName(UserField.EMAIL);
		prototype.setSearchType(SearchType.ILIKE);
		prototype.setValue("");
		prototype.setCaseSensitive(false);
		return prototype;
	}

	private SearchCriterionBean getEnumPrototype()
	{
		final SearchCriterionBean prototype = new SearchCriterionBean();
		prototype.setCriterionType(CriterionType.ENUM);
		prototype.setFieldName(UserField.AGE_GROUP);
		prototype.setSearchType(SearchType.EQ);
		prototype.setValue("");
		prototype.setCaseSensitive(true);
		return prototype;
	}

	private SearchCriteria createSearchCriteria(final SearchCriteriaBean form)
	{
		// AND/OR logic
		final SearchCriteria searchCriteria = new DefaultSearchCriteria(form.getLogic()
				.createCriterion());

		// individual filter criteria
		for (SearchCriterionBean bean : form.getCriteria())
		{
			searchCriteria.add(getSearchCriterionFactory().createSearchCriterion(
					bean.getFieldName(), bean.getSearchType(), bean.getValue(),
					bean.getCaseSensitive()));
		}
		
		// sort orders
		searchCriteria.addSortCriterion(SearchCriterionFactory.createSortCriterion(form
				.getSortColumn().getName(), form.getSortOrder()));
		
		return searchCriteria;
	}

	private Map<UserField, OptionList> buildValueOptions(
			final MessageResources messageResources)
	{
		final Map<UserField, OptionList> valueOptions = new EnumMap<UserField, OptionList>(
				UserField.class);
		final UserDemographicOptions options = new UserDemographicOptions(
				messageResources, true);
		valueOptions.put(UserField.AGE_GROUP, options.getAgeGroupOptions());
		valueOptions.put(UserField.ETHNICITY, options.getEthnicityOptions());
		valueOptions.put(UserField.GENDER, options.getGenderOptions());
		valueOptions.put(UserField.LANGUAGE, options.getLanguageOptions());
		return valueOptions;
	}

	private SearchCriteriaBean getForm(final RequestContext context) throws Exception
	{
		return (SearchCriteriaBean) getFormObject(context);
	}

	private ResultsPagingBean getResultsPagingBean(final RequestContext context)
			throws Exception
	{
		return getForm(context).getPagingBean();
	}

	private void addExitActionToFlowScope(final RequestContext context) throws Exception
	{
		context.getFlowScope().put("exitActionPath", EXIT_ACTION_PATH);
	}

	private UserSearchCriterionFactory getSearchCriterionFactory()
	{
		return searchCriterionFactory;
	}

	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}

	private UserTOBuilder getUserTOBuilder(final MessageResources messageResources)
	{
		return new UserSearchTOBuilder(messageResources);
	}
}
