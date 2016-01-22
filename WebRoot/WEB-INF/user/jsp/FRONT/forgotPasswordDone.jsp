<%--
###################################################################################
forgotPasswordDone.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

An information page upon a successful registration.
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

<c:set var="homeLink">
	<html:rewrite module="" page=""/>
</c:set>
<meta http-equiv="Refresh" content="20;URL=${homeLink}" />

<%--
======================================================================
Print main text describing the RUReady program
======================================================================
--%>

<center>
<h1>
	<bean:message key="user.forgotPasswordDone.title" />
</h1>
</center>

<span class="error"> 
	<bean:message key="user.forgotPasswordDone.sentpassword" />
</span>

<%-- Display a message that we will soon be redirected, with a link to return to the main page --%>
<p/>
<bean:message key="user.done.redirect" />
<html:link module="" page="">
	<bean:message key="user.done.clicktologin" />
</html:link>
.

<p/>
<br/>
<br/>

<%--
======================================================================
Display instructions/information
======================================================================
--%>
<span class="error"> 
	<bean:message key="user.forgotPassword.section2.title" />
</span>
<ul>
	<li>
		<bean:message key="user.forgotPassword.section2.bullet0.body" />
		<html:link action="/secure/FRONT/enroll/student">
			<bean:message key="user.loginForm.register"/>
		</html:link>
		.
	</li>
	<li>
		<bean:message key="user.createUserDone.section2.bullet1.body" />
	</li>
	<li>
		<bean:message key="user.createUserDone.section2.bullet2.body" />
	</li>
	<li>
		<bean:message key="user.createUserDone.section2.bullet3.body" />
	</li>
</ul>
