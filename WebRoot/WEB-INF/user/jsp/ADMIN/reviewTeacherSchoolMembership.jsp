<%--
###################################################################################
main.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Application main internal page. Pops right after logging in at the front page.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%@ taglib uri="http://ruready.net/user" prefix="user"%>

<%--============================ Useful definitions ==============================--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />
<c:set var="pagePrefix" value="${flowKeyPrefix}.confirmForm"/>
<c:set var="groupFieldPrefix" value="net.ruready.business.user.entity.property.GroupField"/>
<c:set var="userFieldPrefix" value="net.ruready.business.user.entity.property.UserField"/>

<%--
Uncomment this section to turn on XHTML validation of this file.
Because this is a JSP snippet, make sure to comment it back out
before running it on the application container.
--%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>

<center><h1><bean:message key="${pagePrefix}.title" /></h1></center>
<html:form>
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">
	
	<div id="entryForm">
		<common:messages prefix="${pagePrefix}" showRequiredHint="true"/>
		<div class="headerInfo">
			<table cellspacing="0">
				<%--<tr>
					<th><bean:message key="${userFieldPrefix}.namefields.label"/></th>
					<td>${currentFormObject.membership.member.user.name}</td>
				</tr>--%>
				<tr>
					<th><bean:message key="${userFieldPrefix}.EMAIL.label"/></th>
					<td>${currentFormObject.membership.member.user.email}</td>
				</tr>
				<tr>
					<th><bean:message key="${userFieldPrefix}.SCHOOL.label"/></th>
					<td>${currentFormObject.membership.school.description}</td>
				</tr>
			</table>
		</div>
		<div>
			<label for="approvalStatus"><bean:message key="${pagePrefix}.approve.label"/><common:required/>:</label>
			<span>
				<html:radio property="approvalStatus" value="APPROVE"/> <bean:message key="${pagePrefix}.field.approve.explanation"/>
				<html:radio property="approvalStatus" value="DENY"/> <bean:message key="${pagePrefix}.field.deny.explanation"/>
			</span>
		</div>
		<div>
			<label for="reason"><bean:message key="${pagePrefix}.reason.label"/><common:required/>:</label>
			<span>
				<html:textarea property="reason" styleId="reason"/>
			</span>
		</div>
		<div class="formControls">
			<html:submit property="_eventId_submit" styleClass="done">
				<bean:message key="${pagePrefix}.button.submit"/>
			</html:submit>
			<html:submit property="_eventId_cancel" styleClass="cancel">
				<bean:message key="${pagePrefix}.button.cancel"/>
			</html:submit>
		</div>
	</div>
</html:form>
