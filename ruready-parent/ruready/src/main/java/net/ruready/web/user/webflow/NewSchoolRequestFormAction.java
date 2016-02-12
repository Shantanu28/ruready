package net.ruready.web.user.webflow;

import net.ruready.business.user.entity.User;
import net.ruready.web.user.beans.NewSchoolRequestFormBean;
import net.ruready.web.user.support.WebFlowSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class NewSchoolRequestFormAction extends FormAction implements MessageSourceAware
{

	private final WebFlowSupport webFlowSupport = new WebFlowSupport();
	private MessageSource messageSource;

	public NewSchoolRequestFormAction()
	{
		setFormObjectClass(NewSchoolRequestFormBean.class);
	}
	
	@Override
	protected Object createFormObject(final RequestContext context) throws Exception
	{
		final NewSchoolRequestFormBean form = new NewSchoolRequestFormBean();
		form.setUser((User) context.getFlowScope().get("user", User.class, new User()));
		// TODO pre-populate text area with i18n content
		form.setRequestContent(
				this.messageSource.getMessage(
						getWebFlowSupport().getMessageKey(context, ".template.content"),
						null,
						getWebFlowSupport().getLocale(context)));
		form.setContactMe(true);
		return form;
	}

	public Event sendRequest(final RequestContext context) throws Exception
	{
		final NewSchoolRequestFormBean form = getForm(context);
		// if user's e-mail address is blank (rare case), put "unspecified" in the e-mail message
		String userEmail = form.getUser().getEmail();
		if (StringUtils.isBlank(userEmail))
		{
			userEmail = this.messageSource.getMessage(
					getWebFlowSupport().getMessageKey(context,".mail.email.unspecified"), 
					null, 
					getWebFlowSupport().getLocale(context));
		}
		String contactMe = "";
		if (!form.getContactMe())
		{
			contactMe = " " + this.messageSource.getMessage(
					getWebFlowSupport().getMessageKey(context,".mail.email.nocontact"), 
					null, 
					getWebFlowSupport().getLocale(context));
		}
		final String subject = this.messageSource.getMessage(
				getWebFlowSupport().getMessageKey(context, ".mail.subject"), 
				null, 
				getWebFlowSupport().getLocale(context));
		final String content = this.messageSource.getMessage(
				getWebFlowSupport().getMessageKey(context, ".mail.content"), 
				new Object[] { 
					userEmail, 
					contactMe,
					form.getRequestContent()}, 
				getWebFlowSupport().getLocale(context));
		getWebFlowSupport()
			.getUserBD(context)
			.mailMessage(getHelpUser(context), subject, content);
		getWebFlowSupport().addSuccessMessage(context);
		return success();
	}
	
	private User getHelpUser(final RequestContext context)
	{
		final User user = new User();
		user.setEmail(
			this.messageSource.getMessage(
				"app.layout.links.emailhelp",
				null,
				getWebFlowSupport().getLocale(context)));
		return user;
	}
	
	public Event addMailErrorMessage(final RequestContext context) throws Exception
	{
		getWebFlowSupport().addMessage(
				context, 
				getWebFlowSupport().getMessageKey(context, ".error.mail"));
		return success();
	}
	
	public Event doCancel(final RequestContext context) throws Exception
	{
		getWebFlowSupport().addCancelMessage(context);
		return success();
	}
	
	private WebFlowSupport getWebFlowSupport()
	{
		return webFlowSupport;
	}

	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	
	private NewSchoolRequestFormBean getForm(final RequestContext context) throws Exception
	{
		return (NewSchoolRequestFormBean) getFormObject(context);
	}
}
