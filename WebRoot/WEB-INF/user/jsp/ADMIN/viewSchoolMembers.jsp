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
<c:set var="pagePrefix" value="${flowKeyPrefix}.view${viewType}"/>
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
<div id="tableContent">
	<common:messages prefix="${pagePrefix}" showInstructions="false" showRequiredHint="false"/>
	<div class="headerInfo">
		<table cellspacing="0">
			<tr>
				<th><bean:message key="${userFieldPrefix}.SCHOOL.label"/></th>
				<td>${results.school.description}</td>
			</tr>
		</table>
	</div>
	<div class="formControls">
		<ul>
			<li>
				<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_add&schoolId=${results.school.id}">
					<bean:message key="${pagePrefix}.add.linkText"/>
				</html:link>
			</li>
			<li>
				<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_back">
					<bean:message key="${pagePrefix}.schools.view.linkText"/>
				</html:link>
			</li>
		</ul>
	</div>
	<div class="clear"></div>
	<c:choose>
		<c:when test="${empty results.users}">
			<div class="empty">
				<bean:message key="${pagePrefix}.nomatches.explanation"/>
			</div>
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
						<c:forEach var="member" items="${results.users}">
							<tr>
								<td class="link">								
									<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_delete&schoolId=${results.school.id}&userId=${member.user.id}">
										<bean:message key="${pagePrefix}.delete.linkText"/>
									</html:link>
								</td>
								<td>${member.user.email}</td>
								<td>${member.user.name.formattedName}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</div>
