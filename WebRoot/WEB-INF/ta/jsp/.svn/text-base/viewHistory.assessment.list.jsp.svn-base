<%--
###################################################################################
viewHistory.assessmentList.jsp

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
<%-- For page-specific customizations --%>
<c:set var="pagePrefix" value="${messagePrefix}"/>
<%-- For text that is the same on all assessment lists --%>
<c:set var="generalPrefix" value="ta.viewHistory.assessmentList"/>

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

<div id="tableContent">
	<common:messages prefix="${pagePrefix}" showInstructions="true" showRequiredHint="false"/>
	<div class="data">
		<table cellspacing="0">
			<thead>
				<tr>
					<th class="link"></th>
					<th><bean:message key="${generalPrefix}.NUMBER.label"/></th>
					<th><bean:message key="${generalPrefix}.DATE.label"/></th>
					<th><bean:message key="${generalPrefix}.COMPLETED.label"/></th>
					<th><bean:message key="${generalPrefix}.SCORE.label"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="assessment" items="${historyList}" varStatus="currRow">
					<tr>
						<td class="link">
							<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_details&assessmentId=${currRow.index}">
								<bean:message key="${generalPrefix}.VIEW_DETAILS.linkText"/>
							</html:link>							
						</td>
						<td>${currRow.count}.</td>
						<td>
							<fmt:formatDate value="${assessment.lastUpdated}" pattern="EEE, MMM d, yyyy hh:mm aa"/>
						</td>
						<td>${assessment.status}</td>
						<td>
							<fmt:formatNumber type="percent" value="${assessment.score}" maxFractionDigits="1"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="formControls">
		<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_return">
			<bean:message key="${generalPrefix}.button.RETURN_TO_COURSE"/>
		</html:link>
	</div>
</div>

