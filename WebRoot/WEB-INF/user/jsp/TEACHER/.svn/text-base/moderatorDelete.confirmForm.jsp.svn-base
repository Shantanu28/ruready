<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />

<c:set var="pagePrefix" value="${flowKeyPrefix}.confirmForm"/>
<c:set var="groupFieldPrefix" value="net.ruready.business.user.entity.property.GroupField"/>
<c:set var="userFieldPrefix" value="net.ruready.business.user.entity.property.UserField"/>

<center><h1><bean:message key="${pagePrefix}.title" /></h1></center>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">

	<div id="entryForm">
		<common:messages prefix="${pagePrefix}" showRequiredHint="false"/>
		<div class="headerInfo">
			<table cellspacing="0">
				<caption><bean:message key="${pagePrefix}.group.header"/></caption>
				<tr>
					<th><bean:message key="${groupFieldPrefix}.NAME.label"/></th>
					<td>${currentFormObject.userGroup.name}</td>
				</tr>
				<tr>
					<th><bean:message key="${groupFieldPrefix}.SCHOOL.label"/></th>
					<td>${currentFormObject.userGroup.school.description}</td>
				</tr>
				<tr>
					<th><bean:message key="${groupFieldPrefix}.COURSE.label"/></th>
					<td>${currentFormObject.userGroup.course.description}</td>
				</tr>
			</table>
		</div>
		<div class="headerInfo">
			<table cellspacing="0">
				<caption><bean:message key="${pagePrefix}.moderator.header"/></caption>
				<tr>
					<th><bean:message key="${userFieldPrefix}.EMAIL.label"/></th>
					<td>${currentFormObject.matchingUser.moderator.user.email}</td>
				</tr>
				<tr>
					<th><bean:message key="${userFieldPrefix}.namefields.label"/></th>
					<td>${currentFormObject.matchingUser.moderator.user.name.formattedName}</td>
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