<%--
###################################################################################
loginForm.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Displays a login form for registered users to enter the site/access a bookmark
of an internal page. Not a complete HTML page.
###################################################################################
--%>

<%@ page language="java"%>
<jsp:directive.page import="net.ruready.web.common.rl.WebAppNames"/>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>
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

<%-- Cookie name conventions --%>
<c:set var="WebAppNames_COOKIE_USER_EMAIL">
	<%= WebAppNames.COOKIE.USER_EMAIL %>
</c:set>

<c:set var="WebAppNames_COOKIE_USER_PASSWORD">
	<%= WebAppNames.COOKIE.USER_PASSWORD %>
</c:set>


<%--============================ JSP body begins here ============================--%>

<script type="text/javascript"
	src="<html:rewrite module='' page='/js/common/formUtil.js' />"></script>
<script language="JavaScript" type="text/javascript">
<!--
function submitActionLogin()
{
	var form = document.getElementById('loginForm');
	addHiddenElement(form, 'action_login', 'true');
	addHiddenElement(form, 'sw', screen.width);
	addHiddenElement(form, 'sh', screen.height);
	form.submit();
}
-->
</script>

<html:form action="/secure/FRONT/login">
	<%--
	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	Hidden form fields
	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	--%>
	<%-- Client stats: screen resolution --%>
	<%--
	<input type="hidden" name="sh" />
	<input type="hidden" name="sw" />
	--%>
	
	<%-- Bookmark to internal page --%>
	<%-- Obsolete - replaced by a session token --%>
	<%--<html:hidden property="bookmark" />--%>
	
	<%--
	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	Display errors if exist
	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	--%>
	<logic:messagesPresent>
		<span class="error"> 
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
	<table border="0">
	<tbody>
		<%-- Email field --%>
		<tr>
	    	<td>
	    		<bean:message key="net.ruready.business.user.entity.property.UserField.EMAIL.label"/>:
	    	</td>
	    	<td>
	    		<%-- Retrieve cookie value --%>
	    		<bean:cookie id="cookieEmail" name="${WebAppNames_COOKIE_USER_EMAIL}" value="" />
	    		
	    		<%-- If email bean property is empty, use cookie value. Otherwise use bean value --%>
	    		<c:choose>
		    		<c:when test="${empty loginForm.email}">
			    		<html:text property="email" size="25" styleClass="smalltext"
				    	value="${cookieEmail.value}" />
				    </c:when>
				    <c:otherwise>
			    		<html:text property="email" size="25" styleClass="smalltext" />
			    	</c:otherwise>
		    	</c:choose>
	    	</td>
		</tr>
					
		<%-- Password field --%>
	  	<tr>
	    	<td>
	    		<bean:message key="net.ruready.business.user.entity.property.UserField.PASSWORD.label"/>:
	    	</td>
	    	<td>
	    		<%-- Retrieve cookie value --%>
	    		<bean:cookie id="cookiePassword" name="${WebAppNames_COOKIE_USER_PASSWORD}" value="" />
	    		
	    		<%-- If password bean property is empty, use cookie value. Otherwise use bean value --%>
	    		<c:choose>
		    		<c:when test="${empty loginForm.password}">
			    		<html:password property="password" size="25" styleClass="smalltext"
				    	value="${cookiePassword.value}" />
				    </c:when>
				    <c:otherwise>
			    		<html:password property="password" size="25" styleClass="smalltext" />
			    	</c:otherwise>
		    	</c:choose>
	    	</td>
	    </tr>	    

		<tr>
			<td colspan="2">
			<%-- This table aligns the checkbox with the middle of the text line --%>
			<table>
			<tbody>
				<tr>
					<td align="center">
						<html:checkbox property="saveCookies" />
					</td>
					<td>
						<bean:message key="user.loginForm.rememberme" />
					</td>
				</tr>
			</tbody>
			</table>
			</td>
		</tr>
		
		<%-- 
		Submit button(s).
		Gather client statistics using javascript variables:
		sw = screen width
		sh = screen height
		 --%>
	    <tr>
	    	<td colspan="2">
	    	
		    	<%-- testing - screen resolution --%>
		    	<%--
					<A HREF="javascript:alert('Your resolution is '+screen.width+'x'+screen.height);">
					Click for your screen resolution</A>
				--%>

				<html:submit property="action_login" styleClass="done"
				onclick="submitActionLogin();">
					<bean:message key="user.loginForm.action.login" />
				</html:submit>
				
				<html:submit property="action_setup_reset" styleClass="cancel">
					<bean:message key="app.action.reset" />
				</html:submit>
	    	
				&nbsp;&nbsp;&nbsp;&nbsp;
	    		
	    		<%-- link to "forgot your password" action --%>
				<html:link module="/user" page="/open/FRONT/forgotPassword.do" styleClass="biglink">
					<bean:message key="user.loginForm.forgotpassword"/>
				</html:link>

				<%-- Bogus pages for JWebUnit --%>				
				<a id="action_login_link" href="javascript:submitActionLogin()"></a>
				
	     	</td>
	   	</tr>
	   </tbody>
	</table>
</html:form>

<%-- For debugging purposes --%>
<%--
<common:cookies />
<common:headers />
--%>
