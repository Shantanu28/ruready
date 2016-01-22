<%--
###################################################################################
viewTranscript.assessments.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<%--============================ Useful definitions ==============================--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />
<c:set var="pagePrefix" value="${messagePrefix}"/>
<c:set var="fieldPrefix" value="ta.viewTranscript"/>

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

<div class="transcriptData">
	<div class="data">
		<table cellspacing="0">
			<col class="link"/>
			<col span="2"/>
			<col class="action"/>
			<col class="history"/>
			<col class="score"/>
			<thead>
				<tr>
					<th class="link"></th>
					<th><bean:message key="${fieldPrefix}.LEVEL.label"/></th>
					<th><bean:message key="${fieldPrefix}.DIFFICULTY.label"/></th>
					<th><bean:message key="${fieldPrefix}.ACTION.label"/></th>
					<th><bean:message key="${fieldPrefix}.MASTERED.label"/></th>
					<th><bean:message key="${fieldPrefix}.SCORE.label"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="level" begin="1" end="4">
					<tr>
						<td class="link"></td>
						<td>${level}</td>
						<td><bean:message key="question.level${level}.label"/></td>
						<td></td>
						<td>
							<c:choose>
								<c:when test="${viewBean.hasMasteredPracticeLevel[level-1]}">
									<bean:message key="${fieldPrefix}.MASTERED.YES.label"/>
								</c:when>
								<c:otherwise>
									<bean:message key="${fieldPrefix}.MASTERED.NO.label"/>
								</c:otherwise>
							</c:choose>							
						</td>
						<td>
							<fmt:formatNumber type="percent" value="${viewBean.practiceScores[level-1]}" maxFractionDigits="1"/>
						</td>
					</tr>
				</c:forEach>			
			</tbody>
		</table>
	</div>
</div>
<div class="transcriptData">
	<div class="data">
		<table cellspacing="0">
			<col class="link"/>
			<col/>
			<col class="feedbackAction"/>
			<col class="score"/>
			<thead>
				<tr>
					<th class="link"></th>
					<th><bean:message key="${fieldPrefix}.COMPLETION_STEPS.label"/></th>
					<th><bean:message key="${fieldPrefix}.ACTION.label"/></th>
					<th><bean:message key="${fieldPrefix}.SCORE.label"/></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="link"></td>
					<td><bean:message key="${fieldPrefix}.COMPLETION_STEPS.FEEDBACK.label"/></td>
					<td><bean:message key="${fieldPrefix}.START_ACTION.linkText"/></td>
					<td></td>
				</tr>
				<tr>
					<td class="link"></td>
					<td><bean:message key="${fieldPrefix}.COMPLETION_STEPS.CERTIFICATE.label"/></td>
					<td>
						<c:choose>
							<c:when test="${viewBean.hasPassedCourse}">
								<bean:message key="${fieldPrefix}.PASSED_COURSE.YES.label" arg0="${viewBean.transcript.course.name}"/>
							</c:when>
							<c:otherwise>
								<bean:message key="${fieldPrefix}.PASSED_COURSE.NO.label" arg0="${viewBean.transcript.course.name}"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatNumber type="percent" value="${viewBean.bestScore}" maxFractionDigits="1"/></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>