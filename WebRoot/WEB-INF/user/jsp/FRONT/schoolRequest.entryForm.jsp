<%--
###################################################################################
createUser.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Displays a form for creating a new user account (registration).
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
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

<c:set var="pagePrefix" value="${flowKeyPrefix}.entryForm"/>
<%--============================ JSP body begins here ============================--%>

<center><h1><bean:message key="${pagePrefix}.title" /></h1></center>

<%--
==================================================================
Registration form
==================================================================
--%>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">
	
	<div id="entryForm">
		<common:messages prefix="${pagePrefix}"/>
		<%-- Content field --%>
		<div>
			<label for="requestContent"><bean:message key="${pagePrefix}.REQUEST.label"/><common:required/>:</label>
			<span>
				<html:textarea property="requestContent" styleId="requestContent" cols="45" rows="10"/>
			</span>
		</div>
		<div>
			<label>&nbsp;</label>
			<span>
				<input type="hidden" name="_contactMe"/>
				<html:checkbox property="contactMe">
					<bean:message key="${pagePrefix}.NOTIFY.label"/>
				</html:checkbox>
			</span>
		</div>
		<div class="formControls">
			<html:submit property="_eventId_submit" styleClass="done">
				<bean:message key="${pagePrefix}.button.submit" />
			</html:submit>
			<html:submit property="_eventId_cancel" styleClass="cancel">
				<bean:message key="${pagePrefix}.button.cancel" />
			</html:submit>
		</div>
	</div>
</html:form>
