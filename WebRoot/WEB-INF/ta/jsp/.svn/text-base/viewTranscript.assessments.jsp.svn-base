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

<%--
==================================================================
Title
==================================================================
--%>
<center><h1><bean:message key="${pagePrefix}.pageTitle" arg0="${viewBean.transcript.course.name}"/></h1></center>

<div class="transcriptData">
	<common:messages prefix="${pagePrefix}" showInstructions="true" showRequiredHint="false"/>
	<div class="data">
		<table cellspacing="0">
			<col class="link"/>
			<col/>
			<col class="action"/>
			<col class="history"/>
			<col class="score"/>
			<thead>
				<tr>
					<th class="link"></th>
					<th><bean:message key="${fieldPrefix}.TEST.label"/></th>
					<th><bean:message key="${fieldPrefix}.ACTION.label"/></th>
					<th><bean:message key="${fieldPrefix}.HISTORY.label"/></th>
					<th><bean:message key="${fieldPrefix}.SCORE.label"/></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="link">
						<%-- if there are more expectation assessments than knowledge assessments or student is at level 0 or 5, show the try me link here --%>
						<c:if test="${(fn:length(viewBean.transcript.expectationAssessments) lt fn:length(viewBean.transcript.knowledgeAssessments)) or ((viewBean.transcript.recommendedLevel eq 0 or viewBean.transcript.recommendedLevel eq 5) and fn:length(viewBean.transcript.expectationAssessments) eq fn:length(viewBean.transcript.knowledgeAssessments))}">
							<div id="tryMeNext">
								<span><bean:message key="${fieldPrefix}.TRY_ME_NEXT.label"/></span>
								<em></em>
							</div>
						</c:if>
					</td>
					<td><bean:message key="${fieldPrefix}.EXPECTATIONS_ASSESSMENT.label"/></td>
					<td>
						<c:if test="${viewBean.isExpectationAssessmentAvailable}">
							<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_startExpectationAssessment">
								<bean:message key="${fieldPrefix}.START_ACTION.linkText"/>
							</html:link>
						</c:if>
					</td>
					<td>
						<c:if test="${viewBean.hasExpectationAssessment}">
							<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_expectationAssessmentHistory">
								<bean:message key="${fieldPrefix}.VIEW_HISTORY_ACTION.linkText"/>
							</html:link>
						</c:if>
					</td>
					<td>
						<fmt:formatNumber type="percent" value="${viewBean.transcript.currentExpectationAssessment.score}" maxFractionDigits="1"/>
					</td>
				</tr>
				<tr>
					<td class="link">
						<%-- if there are more knowledge assessments than expectation assessments, show the try me link here --%>
						<c:if test="${fn:length(viewBean.transcript.knowledgeAssessments) lt fn:length(viewBean.transcript.expectationAssessments)}">
							<div id="tryMeNext">
								<span><bean:message key="${fieldPrefix}.TRY_ME_NEXT.label"/></span>
								<em></em>
							</div>
						</c:if>
					</td>
					<td><bean:message key="${fieldPrefix}.KNOWLEDGE_ASSESSMENT.label"/></td>
					<td>
						<c:if test="${viewBean.isKnowledgeAssessmentAvailable}">
							<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_startKnowledgeAssessment">
								<bean:message key="${fieldPrefix}.START_ACTION.linkText"/>
							</html:link>
						</c:if>
					</td>
					<td>
						<c:if test="${viewBean.hasKnowledgeAssessment}">
							<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_knowledgeAssessmentHistory">
								<bean:message key="${fieldPrefix}.VIEW_HISTORY_ACTION.linkText"/>
							</html:link>
						</c:if>
					</td>
					<td>
						<fmt:formatNumber type="percent" value="${viewBean.transcript.currentKnowledgeAssessment.score}" maxFractionDigits="1"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

