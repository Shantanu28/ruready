<%--
###################################################################################
viewModerators.jsp

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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<%--============================ Useful definitions ==============================--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />
<c:set var="pagePrefix" value="user.groupMembers.view"/>
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

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Title
==================================================================
--%>
<center><h1><bean:message key="${pagePrefix}.title" /></h1></center>

<%--
==================================================================
Main Table with tabs
==================================================================
--%>
<div id="tableContent">
	<common:messages prefix="${pagePrefix}" showInstructions="false" showRequiredHint="false"/>
	<div class="headerInfo">
		<table cellspacing="0">
			<tr>
				<th><bean:message key="${groupFieldPrefix}.NAME.label"/></th>
				<td>${viewBean.group.name}</td>
			</tr>
			<tr>
				<th><bean:message key="${groupFieldPrefix}.SCHOOL.label"/></th>
				<td>${viewBean.group.school.description}</td>
			</tr>
			<tr>
				<th><bean:message key="${groupFieldPrefix}.COURSE.label"/></th>
				<td>${viewBean.group.course.description}</td>
			</tr>
		</table>
	</div>
	<div class="formControls">
		<ul>
			<%--<li>
				<html:link action="/open/TEACHER/moderators/add" name="viewBean" property="group.linkParams">
					<bean:message key="${pagePrefix}.create.linkText"/>
				</html:link>
			</li> --%>
			<li>
				<html:link action="/open/TEACHER/groups/view">
					<bean:message key="${pagePrefix}.groups.view.linkText"/>
				</html:link>
			</li>
		</ul>
	</div>
	<div class="clear"></div>
	<c:choose>
		<c:when test="${empty viewBean.users}">
			<div class="empty">
				<bean:message key="${pagePrefix}.empty.message"/>
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
						<c:forEach var="member" items="${viewBean.users}">
							<tr>
								<td class="link">
									<%--<html:link action="/open/TEACHER/moderators/delete" name="member" property="linkParams">--%>
										<bean:message key="${pagePrefix}.delete.linkText"/>
									<%--</html:link>--%>
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

