<%--
###################################################################################
system.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

A generic system error page. Displayed after an ApplicationException has been thrown.
###################################################################################
--%>

<%@ page language="java" %>
<jsp:directive.page import="java.lang.Exception"/>
<jsp:directive.page import="net.ruready.common.util.ExceptionUtil"/>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<p/>
<h1><bean:message key="error.systemError.title"/></h1>

<%-- Specific error message --%>
<span class="error" />
	<html:errors/>
</span>

<p/>

<%-- Display a message with a link to return to the main page --%>
<bean:message key="error.systemError.returntomainpage" />
<html:link module="" page="">
	<bean:message key="user.home.link" />
</html:link>.
<p/>

<hr />

<bean:message key="error.systemError.report" />
<p/>

<% Exception exception = (Exception)request.getAttribute("exception"); %>

<c:if test="${!empty exception}">
	<b><bean:message key="error.systemError.errorMessage"/> ${exception}</b>
	<p>
		<b><bean:message key="error.systemError.stackTrace"/></b> <br/>
		<%= ExceptionUtil.getStackTraceAsString(exception) %>
	</p>
</c:if>
