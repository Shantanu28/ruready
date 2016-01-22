<%--
###################################################################################
viewGroups.jsp

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
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<%--============================ Useful definitions ==============================--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />
<c:set var="pagePrefix" value="user.teacherSchoolMemberships.view"/>
<c:set var="fieldPrefix" value="net.ruready.business.user.entity.property.UserField"/>

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
	<div class="formControls">
		<ul>
			<li>
				<html:link action="/open/TEACHER/schools/add">
					<bean:message key="${pagePrefix}.add.linkText"/>
				</html:link>
			</li>
		</ul>
	</div>
	<div class="clear"></div>
	<c:choose>
		<c:when test="${empty viewBean.members}">
			<div class="emptyPrompt">
				<c:set var="createLink">
					<html:link action="/open/TEACHER/schools/add">
						<bean:message key="${pagePrefix}.empty.prompt.linkText"/>
					</html:link>
				</c:set>
				<bean:message key="${pagePrefix}.empty.prompt" arg0="${createLink}"/>
			</div>
		</c:when>
		<c:otherwise>
			<div class="data">
				<table cellspacing="0">
					<thead>
						<tr>
							<th class="link"></th>
							<th><bean:message key="${fieldPrefix}.SCHOOL.label"/></th>
							<th><bean:message key="${fieldPrefix}.SCHOOL_STATUS.label"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="membership" items="${viewBean.members}">
							<tr>
								<td class="link">
									<html:link action="/open/TEACHER/schools/delete?schoolId=${membership.school.id}">
										<bean:message key="${pagePrefix}.delete.linkText"/>
									</html:link>
								</td>
								<td>${membership.school.description}</td>
								<td><bean:message key="${pagePrefix}.${membership.memberStatus}.label"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:otherwise>
	</c:choose>	
</div>

