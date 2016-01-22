<%--
###################################################################################
home.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

User component home. This is the real "home page". Contains the Login form,
useful links, news.
###################################################################################
--%>

<%@ page language="java" %>
<jsp:directive.page import="net.ruready.web.common.rl.WebAppNames"/>
<jsp:directive.page import="org.apache.struts.Globals"/>
<jsp:directive.page import="org.apache.struts.action.ActionErrors"/>
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
"Gateway page"-look
======================================================================
--%>
<c:if test="${!empty sessionScope.bookmark}">
	<%-- Copy global errors from the session to the request for display using standard Struts tags --%>
	<%
		ActionErrors errors = (ActionErrors) request.getSession().getAttribute(WebAppNames.SESSION.ATTRIBUTE.ERRORS);
		if (errors != null)
		{
			request.setAttribute(Globals.ERROR_KEY, errors);
		}
	%>
	<jsp:include page="/WEB-INF/user/jsp/FRONT/loginForm.jsp" flush="true" />
	<p />
	<ul class="smalltext">
		<li>
			<bean:message key="user.home.jscomment" />
		</li>
	</ul>
</c:if>

<%--
======================================================================
Normal look of the front page
Top: Market flash presentation and an explanation of the program
======================================================================
--%>
<c:if test="${empty sessionScope.bookmark}">
<table width="100%" border="0" cellspacing="5">
<tbody>
	<tr>
		<h1>
			<bean:message key="app.title" />:
			<bean:message key="app.header.text1" />
		</h1>
		<h2>
			<bean:message key="app.header.text2" />
		</h2>
		<td valign="top">
			<p class="frontexplanation">
				<bean:message key="user.home.explanation" />
			</p>

			<p class="frontexplanation">
				<bean:message key="user.home.available" />
			</p>
			<p align="center" class="h3">
				<bean:message key="user.home.newstudent"/>
				<html:link action="/secure/FRONT/enroll/student" styleClass="biglink">
					<bean:message key="user.home.createstudent"/>
				</html:link>
				<br />
				<bean:message key="user.home.newteacher"/>
				<html:link action="/secure/FRONT/enroll/teacher" styleClass="biglink">
					<bean:message key="user.home.createteacher"/>
				</html:link>
			</p>
		</td>
		
		<%--
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		Embed marketing Flash presentation
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		--%>
		<td>
			<%-- Old codel quality and plugins tags seem to be deprecated according to
			the MyEclipse HTML validator --%>
			<%--
		    <object classId="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" 
		    codeBase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" 
		    width="240" height="180" hspace="0" vspace="0">
		    	<param name="movie" value="<html:rewrite module='' page='/images/common/ready_v2.swf' />">
		        <param name="quality" value="high">
		        <embed src="<html:rewrite module=''  page='/images/common/ready_v2.swf' />" width="300" 
		        height="225" hspace="5" vspace="5" quality="high" 
		        pluginspage="http://www.macromedia.com/go/getflashplayer" 
		        type="application/x-shockwave-flash">
		        </embed>
		    </object>
			--%>
		    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" 
		    codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" 
		    width="240" height="180" hspace="0" vspace="0">
		    	<param name="movie" value="<html:rewrite module='' page='/images/common/ready_v2.swf' />" />
		        <param name="quality" value="high" />
		        <embed src="<html:rewrite module=''  page='/images/common/ready_v2.swf' />" width="300" 
		        height="225" hspace="5" vspace="5">
		        </embed>
		    </object>
		</td>
	</tr>

	<%--
	Vertical space
	--%>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	
	<%--
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Registered Users: login form
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	--%>
	<tr>
		<td valign="top">
			<center class="h3">
				<bean:message key="user.home.registereduser" />
			</center>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td valign="top" colspan="2">
			<jsp:include page="/WEB-INF/user/jsp/FRONT/loginForm.jsp" flush="true" />
		</td>
	</tr>
</tbody>
</table>

<p />


</c:if>

<%-- News and announcements --%>
<%--
<jsp:include page="/${newsFile}" flush="true" />
--%>
