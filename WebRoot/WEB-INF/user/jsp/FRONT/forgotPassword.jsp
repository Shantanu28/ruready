<%--
###################################################################################
forgotPassword.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Password reminder page.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

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

<%--
======================================================================
Page Title
======================================================================
--%>

<center>
<h1>
	<bean:message key="user.forgotPassword.title" />
</h1>
</center>

<%--
======================================================================
Passsword reminder form
======================================================================
--%>

<html:form action="/open/FRONT/forgotPassword">
	<%--
	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	Display errors if exist
	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	--%>
	<logic:messagesPresent>
		<span class="error" /> 
			<html:errors />
			<c:set var="registrationLink">
				<html:link action="/secure/FRONT/enroll/student" styleClass="biglink">
					<bean:message key="user.loginForm.register"/>
				</html:link>
			</c:set>
			
			<bean:message key="user.loginForm.newuser" arg0="${registrationLink}" />
		</span>
		<p/>
	</logic:messagesPresent>
	
	<%--
	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	Display form fields
	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	--%>
	<bean:message key="user.forgotPassword.instructions" />
	&nbsp;
	
	<%-- ====== Email field ====== --%>

	<%-- Retrieve cookie value --%>
	<bean:cookie id="cookieEmail" name="${WebAppNames_COOKIE_USER_EMAIL}" value="" />
	    		
	<%-- If email bean property is empty, use cookie value. Otherwise use bean value --%>
	<c:choose>
		<c:when test="${empty loginForm.email}">
			<html:text property="email" size="25" styleClass="smalltext" value="${cookieEmail.value}" />
	    </c:when>
		<c:otherwise>
			 <html:text property="email" size="25" styleClass="smalltext" />
		</c:otherwise>
  	</c:choose>
  	
   	&nbsp;&nbsp;&nbsp;
   	
	<html:submit property="action_send" styleClass="done">
		<bean:message key="user.forgotPassword.action.send" />
	</html:submit>
	
</html:form>

<p />
<br/>
<br/>

<%--
======================================================================
Useful links
======================================================================
--%>
<p />
<table width="100%" border="0" cellpadding="5">
<tbody>
	<tr>
		<td class="h3" align="center">
			<bean:message key="user.home.returntohome" />
			<html:link module="" page="">
				<bean:message key="user.home.link" />
			</html:link>
		</td>
	</tr>
</tbody>
</table>
