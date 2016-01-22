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

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Title
==================================================================
--%>
<table border="0" width="100%">
<tbody>
	<tr>
		<td align="center">
			<h1>
				<bean:message key="user.studentMain.title" />
			</h1>
		</td>
		<td align="right" width="25%">
			<user:perspectives module="/user" page="/open/ALL/main.do" />
		</td>
	</tr>
</tbody>
</table>

<%--
==================================================================
Main Table with tabs
==================================================================
--%>
<table border="0" width="80%" cellspacing="10">
	<%--
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Tab header row
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	--%>
	<thead>
	<tr>
		<th colspan="2">
			<common:messages prefix="" showInstructions="false" showRequiredHint="false"/>
		</th>
	</tr>
	<tr>
		<th class="tab" width="50%" align="left">
			<bean:message key="user.studentMain.myAccount.title" />
		</th>
		<th class="tab" width="50%" align="left">
			<bean:message key="user.studentMain.myCourses.title" />
		</th>
	</tr>
	</thead>

	<tbody>
	<tr>
		<%--
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		My Account Tab Content
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		--%>
		<td class="regtext" valign="top">
			<ul>
				<li>
					<html:link module="/user" page="/secure/STUDENT/updateUser.do">
						<bean:message key="user.studentMain.myAccount.updateDetails" />
					</html:link>
				</li>
				<li>
					<html:link action="/open/STUDENT/groups/view">
						<bean:message key="user.studentMain.myAccount.manageGroups"/>
					</html:link>
				</li>
				<li>
					<html:link action="/open/STUDENT/schools/view">
						<bean:message key="user.studentMain.myAccount.manageSchools"/>
					</html:link>
				</li>
			</ul>
		</td>

		<%--
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		My Courses Tab Content
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		--%>
		<td class="regtext" valign="top">
			<div id="courseListing">
				<h3><bean:message key="user.studentMain.myCourses.beginCourse"/></h3>
				<c:choose>
					<c:when test="${empty viewBean.availableCourses and empty viewBean.notStartedTranscripts}">
						<p><em><bean:message key="user.studentMain.myCourses.beginCourseEmpty"/></em></p>
					</c:when>
					<c:otherwise>
						<ul class="courses">
							<c:forEach var="course" items="${viewBean.availableCourses}">
								<li>
									<html:link module="/ta" action="/open/STUDENT/transcript/start/course?courseId=${course.id}">${course.description}</html:link>
								</li>
							</c:forEach>
						</ul>
						<ul>
							<c:forEach var="transcript" items="${viewBean.notStartedTranscripts}">
								<li>
									<html:link module="/ta" action="/open/STUDENT/transcript/start/group?transcriptId=${transcript.id}">${transcript.course.description}</html:link> 
									<c:if test="${transcript.transcriptType eq 'GROUP'}">
										<em><bean:message key="user.studentMain.myCourses.fromGroup" arg0="${transcript.group.name}"/></em>
									</c:if>
								</li>
							</c:forEach>
						</ul>
					</c:otherwise>
				</c:choose>
				<c:if test="${not empty viewBean.inProcessTranscripts}">			
					<h3><bean:message key="user.studentMain.myCourses.continueWorkingOn"/></h3>
					<ul>
						<c:forEach var="transcript" items="${viewBean.inProcessTranscripts}">
							<li>
								<html:link module="/ta" action="/open/STUDENT/transcript/continue?transcriptId=${transcript.id}">${transcript.course.description}</html:link> 
								<c:if test="${transcript.transcriptType eq 'GROUP'}">
									<em><bean:message key="user.studentMain.myCourses.fromGroup" arg0="${transcript.group.name}"/></em>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</c:if>
				<c:if test="${not empty viewBean.closedTranscripts}">
					<h3><bean:message key="user.studentMain.myCourses.pastCourses"/></h3>
					<ul>
						<c:forEach var="transcript" items="${viewBean.closedTranscripts}">
							<li>
								<html:link module="/ta" action="/open/STUDENT/transcript/readOnly?transcriptId=${transcript.id}">${transcript.course.description}</html:link> 
								<c:if test="${transcript.transcriptType eq 'GROUP'}">
									<em><bean:message key="user.studentMain.myCourses.fromGroup" arg0="${transcript.group.name}"/></em>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
		</td>
	</tr>
	</tbody>
	
</table>
