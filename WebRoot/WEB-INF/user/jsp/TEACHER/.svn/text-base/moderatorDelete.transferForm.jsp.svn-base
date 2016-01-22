<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />

<c:set var="pagePrefix" value="${flowKeyPrefix}.transferForm"/>
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
		<c:choose>
			<c:when test="${empty results.users}">
				<div class="nomatches"><bean:message key="${pagePrefix}.nomatches.explanation"/></div>
			</c:when>
			<c:otherwise>
				<div class="data">
					<table cellspacing="0">
						<thead>
							<tr>
								<th class="link"></th>
								<th><bean:message key="${userFieldPrefix}.EMAIL.label"/></th>
								<th><bean:message key="${userFieldPrefix}.namefields.label"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="moderator" items="${results.users}">
								<tr>
									<td class="link">
										<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_assign&userId=${moderator.id}">
											<bean:message key="${pagePrefix}.assign.altText"/>
										</html:link>
									</td>
									<td>${moderator.user.email}</td>
									<td>${moderator.user.name.formattedName}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="formControls">
			<html:submit property="_eventId_cancel" styleClass="cancel">
				<bean:message key="${pagePrefix}.button.cancel" />
			</html:submit>
		</div>
	</div>	
</html:form>