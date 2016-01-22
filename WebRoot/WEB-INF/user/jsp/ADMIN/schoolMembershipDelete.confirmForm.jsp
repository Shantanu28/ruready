<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />

<c:set var="pagePrefix" value="${flowKeyPrefix}.confirmForm"/>
<c:set var="fieldPrefix" value="net.ruready.business.user.entity.property.UserField"/>

<center><h1><bean:message key="${pagePrefix}.title" /></h1></center>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">

	<div id="entryForm">
		<common:messages prefix="${pagePrefix}" showRequiredHint="false"/>
		<div class="headerInfo">
			<table cellspacing="0">
				<tr>
					<th><bean:message key="${fieldPrefix}.SCHOOL.label"/></th>
					<td>${currentFormObject.matchingSchool.description}</td>
				</tr>
				<tr>
					<th><bean:message key="${pagePrefix}.EMAIL.label"/></th>
					<td>${currentFormObject.matchingUser.email}</td>
				</tr>
				<tr>
					<th><bean:message key="${pagePrefix}.NAME.label"/></th>
					<td>${currentFormObject.matchingUser.name.formattedName}</td>
				</tr>
			</table>
		</div>
		<div class="formControls">
			<html:submit property="_eventId_yes" styleClass="done">
				<bean:message key="${pagePrefix}.button.yes" />
			</html:submit>
			<html:submit property="_eventId_no" styleClass="cancel">
				<bean:message key="${pagePrefix}.button.no" />
			</html:submit>
		</div>
	</div>	
</html:form>