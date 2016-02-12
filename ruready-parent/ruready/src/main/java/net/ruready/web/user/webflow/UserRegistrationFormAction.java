package net.ruready.web.user.webflow;

import java.util.ArrayList;
import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.user.beans.AccountFormBean;
import net.ruready.web.user.beans.ResultsPagingBean;
import net.ruready.web.user.beans.SchoolSearchFormBean;
import net.ruready.web.user.beans.UserDemographicOptions;
import net.ruready.web.user.support.WebFlowSupport;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public abstract class UserRegistrationFormAction extends FormAction implements
		MessageSourceAware
{
	private static final String SCHOOL_ID_KEY = "schoolId";
	private static final String SCHOOL_RESULTS_BEAN = "results";

	private static final String SCHOOL_MATCH_ADD__ERROR_MESSAGE = ".addschool.error.message";

	private MessageSource messageSource;

	private final WebFlowSupport webFlowSupport = new WebFlowSupport();

	public Event getNewFormObject(final RequestContext context) throws Exception
	{
		final AccountFormBean form = getForm(context);
		form.setSchoolSearch(new SchoolSearchFormBean());
		form.setOptions(new UserDemographicOptions(getWebFlowSupport()
				.getMessageResources(context)));
		return success();
	}

	public Event assertUserDoesntExist(final RequestContext context) throws Exception
	{
		final AbstractUserBD bdUser = getWebFlowSupport().getUserBD(context);
		final User user = getForm(context).getUser();
		if (bdUser.findByUniqueEmail(user.getEmail()) != null)
		{
			getFormErrors(context).reject(
					WebAppNames.KEY.EXCEPTION.RECORD_EXISTS_EDIT_USER, new Object[]
					{ user.getEmail() }, "");
			return error();
		}
		return success();
	}

	public Event performSchoolSearch(final RequestContext context) throws Exception
	{
		final AccountFormBean form = getForm(context);
		final School school = getSchoolById(context, form.getSchoolSearch().getSchoolId());

		// if a school matches, add to user and return "selected"
		if (school != null)
		{
			form.setSchool(school);
			return result("selected");
		}
		// if there was no match but there was a search string, return "search"
		else if (form.getSchoolSearch().getSearchString().length() > 0)
		{
			return result("search");
		}
		// otherwise, assume that user chose not to add a school, return
		// "emptysearchstring"
		else
		{
			return result("emptysearchstring");
		}
	}

	public Event addMatchingSchool(final RequestContext context) throws Exception
	{
		final AccountFormBean form = getForm(context);
		final School school = getSchoolByUrlId(context);
		if (school == null)
		{
			getFormErrors(context).reject(
					getWebFlowSupport().getMessageKey(context,
							SCHOOL_MATCH_ADD__ERROR_MESSAGE));
			return error();
		}
		form.setSchool(school);
		return success();
	}

	public Event registerUser(final RequestContext context) throws Exception
	{
		final AbstractUserBD bdUser = getWebFlowSupport().getUserBD(context);
		final AccountFormBean form = getForm(context);
		final User user = form.getUser();
		user.setPassword(bdUser.generatePassword(user));
		bdUser.createUser(user);

		if (form.getSchool() != null)
		{
			addSchool(context, user, form.getSchool());
		}
		mailWelcomeMessage(context, user);
		return success();
	}

	public Event lookupSchoolMatches(final RequestContext context) throws Exception
	{
		final AccountFormBean form = getForm(context);
		if (form.getSchoolSearch().getSearchString().length() >= form.getSchoolSearch()
				.getMinimumSearchStringLength())
		{
			final List<School> results = getMatchingSchools(context, form
					.getSchoolSearch().getSearchString());
			context.getFlashScope().put(SCHOOL_RESULTS_BEAN,
					getPagedResults(form.getPagingBean(), results));
		}
		return success();
	}

	public Event addMailErrorMessage(final RequestContext context) throws Exception
	{
		logger.debug("Error while trying to e-mail login information");
		final AccountFormBean form = getForm(context);
		getFormErrors(context).reject(
				getWebFlowSupport().getMessageKey(context, ".error.mail"), new Object[]
				{ form.getUser().getEmail() }, null);
		getWebFlowSupport().exposeErrors(context, getFormErrors(context));
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

	/**
	 * @param context
	 * @param user
	 * @param school
	 */
	protected void addSchool(final RequestContext context, final User user,
			final School school)
	{
	}

	private AccountFormBean getForm(final RequestContext context) throws Exception
	{
		return (AccountFormBean) getFormObject(context);
	}

	private ResultsPagingBean getResultsPagingBean(final RequestContext context)
			throws Exception
	{
		return getForm(context).getPagingBean();
	}

	private void mailWelcomeMessage(final RequestContext context, final User user)
	{
		final AbstractUserBD bdUser = getWebFlowSupport().getUserBD(context);
		final String subject = this.messageSource.getMessage(
				WebAppNames.KEY.MAIL_WELCOME_SUBJECT, null, getWebFlowSupport()
						.getLocale(context));
		final String content = this.messageSource.getMessage(
				WebAppNames.KEY.MAIL_WELCOME_CONTENT, new Object[]
				{ user.getEmail(), bdUser.decryptPassword(user.getPassword()) },
				getWebFlowSupport().getLocale(context));
		bdUser.mailMessage(user, subject, content);
	}

	private List<School> getMatchingSchools(final RequestContext context,
			final String searchString)
	{
		return getWebFlowSupport().getWorldBD(context).findSchoolByPartialName(
				searchString);
	}

	private List<School> getPagedResults(final ResultsPagingBean pagingBean,
			final List<School> results)
	{
		pagingBean.setTotalResults(results.size());
		final List<School> pageResults = new ArrayList<School>();
		for (int i = pagingBean.getCurrentPageFirstResultZeroBased(); i < pagingBean
				.getCurrentPageLastResult(); i++)
		{
			pageResults.add(results.get(i));
		}
		return pageResults;
	}

	private School getSchoolByUrlId(final RequestContext context) throws Exception
	{
		final Long schoolId = getSchoolIdFromUrl(context);
		if (schoolId == null)
			return null;
		return getSchoolById(context, schoolId);
	}

	private Long getSchoolIdFromUrl(final RequestContext context) throws Exception
	{
		return context.getRequestParameters().getLong(SCHOOL_ID_KEY);
	}

	private School getSchoolById(final RequestContext context, final Long schoolId)
	{
		if (schoolId == null)
			return null;
		return getWebFlowSupport().getWorldBD(context).findSchoolById(schoolId);
	}

	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	protected final WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}
}
