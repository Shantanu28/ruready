<%--
###################################################################################
auditMessage.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print an audit/logging message of a catalog item.
###################################################################################
--%>

<%@ tag display-name="Prints an audit message"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- resource key (instance variable name) of the message --%>
<%@ attribute name="name" required="true" type="net.ruready.business.content.item.entity.audit.AuditMessage" %>

<%-- ============ Tag output variables ============ --%>

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

<c:set var="formatdatetime" scope="page">
	<bean:message key='app.format.datetime' />
</c:set>

<%-- ============ Tag code begins here ============ --%>

<c:choose>
	<c:when test="${empty name}">
		<bean:message key="content.AuditMessage.notfound" />
	</c:when>
	<c:otherwise>
		<bean:message key="content.AuditMessage.version.label" />:
		<span id="auditMessageVersion">${name.version}</span>
		<br/>
		${name.action}
		<bean:message key="content.AuditMessage.user.connectionlabel" />
		${name.email}
		<bean:message key="content.AuditMessage.date.connectionlabel" />
		<bean:write name="name" property="date" filter="true" format="${formatdatetime}" />
	</c:otherwise>
</c:choose>

