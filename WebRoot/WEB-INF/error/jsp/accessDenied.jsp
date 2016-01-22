<%--
###################################################################################
accessDenied.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Displayed when the user attempts to access a page he/she is not privileged to
see (access denied).
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%--============================ Useful definitions ==============================--%>

<%--
Uncomment this section to turn on XHTML validation of this file.
Because this is a JSP snippet, make sure to comment it back out
before running it on the application container.
--%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />

<%--============================ JSP body begins here ============================--%>

<%-- Error page title --%>
<p />
<h1>
	<bean:message key="error.accessDenied.title" />
</h1>

<%-- Specific error message --%>
<span class="error" />
	<bean:message key="error.accessDenied.description" />
</span>
<br/>
<%-- Specific error message --%>
<span class="error" />
	<html:errors/>
</span>

<p />

	<%-- Display a message with a link to return to the main page --%>
	<bean:message key="error.systemError.returntomainpage" />
	<html:link module="" page="">
		<bean:message key="user.home.link" />
	</html:link>.