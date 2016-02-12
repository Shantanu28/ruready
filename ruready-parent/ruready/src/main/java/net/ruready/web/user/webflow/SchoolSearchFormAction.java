package net.ruready.web.user.webflow;

import java.util.ArrayList;
import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.web.user.beans.ResultsPagingBean;
import net.ruready.web.user.beans.SchoolFormBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class SchoolSearchFormAction extends FormAction
{
	private static final String RESULTS_BEAN = "results";
	private final WebFlowSupport webFlowSupport = new WebFlowSupport();

	public SchoolSearchFormAction()
	{
		setFormObjectClass(SchoolFormBean.class);
	}	
	
	public Event performSearch(final RequestContext context) throws Exception
	{
		final SchoolFormBean form = getForm(context);
		if (form.getSearchString().length() >= form.getMinimumSearchStringLength())
		{
			final List<School> results = getMatchingSchools(
					context, 
					form.getSearchString());
			context.getFlashScope().put(
					RESULTS_BEAN, 
					getPagedResults(form.getPagingBean(), results));
		}
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
	
	protected final List<School> getMatchingSchools(final RequestContext context, final String searchString) throws Exception
	{
		return applyFilterToSchools(context, 
				getWebFlowSupport()
					.getWorldBD(context)
					.findSchoolByPartialName(searchString));
	}
	
	/**
	 * Filters a collection of school by custom criteria
	 * 
	 * This is a hook for subclasses to be able to filter school results by flow-specific functionality.
	 * For instance, a class may want to remove schools from the list that the student already
	 * is a member of.
	 * 
	 * @param context the request context
	 * @param results a collection of schools
	 * @return the filtered collection
	 * @throws Exception
	 */
	protected List<School> applyFilterToSchools(final RequestContext context, final List<School> results) throws Exception
	{
		return results;
	}
	
	/**
	 * Returns a list of schools for the current page
	 * 
	 * @param pagingBean the paging bean holding the current page information
	 * @param results the collection of schools to filter
	 * @return a collection of schools containing the schools for the current page
	 */
	protected final List<School> getPagedResults(final ResultsPagingBean pagingBean, final List<School> results)
	{
		pagingBean.setTotalResults(results.size());
		final List<School> pageResults = new ArrayList<School>();
		for (int i = pagingBean.getCurrentPageFirstResultZeroBased(); i < pagingBean.getCurrentPageLastResult(); i++)
		{
			pageResults.add(results.get(i));
		}
		return pageResults;
	}
	
	private SchoolFormBean getForm(final RequestContext context) throws Exception
	{
		return (SchoolFormBean) getFormObject(context);
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
