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
<c:set var="pagePrefix" value="${flowKeyPrefix}.entryForm"/>
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
		<common:messages prefix="${pagePrefix}" showRequiredHint="false"/>
		<div class="headerInfo">
			<table cellspacing="0">
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
										<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_add&userId=${moderator.id}">
											<bean:message key="${pagePrefix}.add.altText"/>
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
			<html:submit property="_eventId_submit" styleClass="done">
				<bean:message key="${pagePrefix}.button.submit"/>
			</html:submit>
		</div>
	</div>
</html:form>
