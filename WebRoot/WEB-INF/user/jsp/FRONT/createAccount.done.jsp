<%--
###################################################################################
createUserDone.jsp

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
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

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

<c:set var="pagePrefix" value="${flowKeyPrefix}.done"/>

<center><h1><bean:message key="${pagePrefix}.title" /></h1></center>
<div id="entryForm">
	<common:messages prefix="${pagePrefix}" showInstructions="false" showRequiredHint="false"/>
	<bean:message key="${pagePrefix}.section1.body" />
	<p />
	<span class="error"> 
		<bean:message key="${pagePrefix}.section2.title" />
	</span>
	<ul>
		<li>
			<bean:message key="${pagePrefix}.section2.bullet1.body" />
		</li>
		<li>
			<bean:message key="${pagePrefix}.section2.bullet2.body" />
		</li>
		<li>
			<bean:message key="${pagePrefix}.section2.bullet3.body" />
		</li>
	</ul>
	
	<%-- Display a message that we will soon be redirected, with a link to return to the main page --%>
	<p/>
	<bean:message key="user.done.redirect" />
	<html:link forward="userHome">
		<bean:message key="user.done.clicktologin" />
	</html:link>
	.
</div>