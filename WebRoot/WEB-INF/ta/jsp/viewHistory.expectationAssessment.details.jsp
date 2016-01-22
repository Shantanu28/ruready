<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />
<%-- For page-specific customizations --%>
<c:set var="pagePrefix" value="${messagePrefix}"/>
<%-- For text that is the same on all assessment lists --%>
<c:set var="generalPrefix" value="ta.expectationAssessment.results"/>

<center><h1><bean:message key="${pagePrefix}.pageTitle" arg0="${viewBean.transcript.course.name}"/></h1></center>
<div id="expectationAssessmentForm">
	<div class="data">
		<table cellspacing="0">
			<thead>
				<tr>
					<th class="statement"><bean:message key="${generalPrefix}.STATEMENT.label"/></th>
					<th><bean:message key="${generalPrefix}.ANSWER.label"/></th>
					<th><bean:message key="${generalPrefix}.BASELINE.label"/></th>
					<th><bean:message key="${generalPrefix}.SCORE.label"/></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td colspan="3"><bean:message key="${generalPrefix}.FINAL_SCORE.label"/></td>
					<td class="numeric finalScore">					
						<fmt:formatNumber type="percent" value="${assessment.score}" maxFractionDigits="1"/>
					</td>
				</tr>
			</tfoot>
			<tbody>
				<c:forEach var="item" items="${assessment.testItems}" varStatus="currStatus">
					<tr>
						<td class="statement">
							<em>${currStatus.count}.</em> ${item.formulation}
						</td>
						<td class="numeric">						
							<fmt:formatNumber type="number" value="${item.value}" maxFractionDigits="2"/>
						</td>
						<td class="numeric">
							<fmt:formatNumber type="number" value="${item.baseline}" maxFractionDigits="2"/>
						</td>
						<td class="numeric">
							<fmt:formatNumber type="percent" value="${item.score}" maxFractionDigits="1"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="formControls">
		<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_return">
			<bean:message key="${pagePrefix}.button.RETURN_TO_ASSESSMENT_LIST"/>
		</html:link>
	</div>
</div>
	