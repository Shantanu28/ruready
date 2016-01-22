package net.ruready.web.user.webflow;

import java.util.ArrayList;
import java.util.List;

import net.ruready.business.user.entity.UserGroup;
import net.ruready.web.user.beans.GroupSearchFormBean;
import net.ruready.web.user.beans.ResultsPagingBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class GroupSearchFormAction extends FormAction
{
	private static final String RESULTS_BEAN = "results";
	
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();
	
	public GroupSearchFormAction()
	{
		setFormObjectClass(GroupSearchFormBean.class);
	}
	
	public Event performSearch(final RequestContext context) throws Exception
	{
		final GroupSearchFormBean form = getForm(context);
		final List<UserGroup> results = getMatchingGroups(context, form.getSearchString());
		context.getFlashScope().put(
				RESULTS_BEAN, 
				getPagedResults(form.getPagingBean(), results));
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
	
	private List<UserGroup> getMatchingGroups(final RequestContext context, final String searchString)
	{
		return getWebFlowSupport().getUserGroupBD(context).findActiveByPartialName(searchString);
	}
	
	private List<UserGroup> getPagedResults(final ResultsPagingBean pagingBean, final List<UserGroup> results)
	{
		pagingBean.setTotalResults(results.size());
		final List<UserGroup> pageResults = new ArrayList<UserGroup>();
		for (int i = pagingBean.getCurrentPageFirstResultZeroBased(); i < pagingBean.getCurrentPageLastResult(); i++)
		{
			pageResults.add(results.get(i));
		}
		return pageResults;
	}
	
	private GroupSearchFormBean getForm(final RequestContext context) throws Exception
	{
		return (GroupSearchFormBean) getFormObject(context);
	}
	
	private ResultsPagingBean getResultsPagingBean(final RequestContext context) throws Exception
	{
		return getForm(context).getPagingBean();
	}
	
	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}
}
