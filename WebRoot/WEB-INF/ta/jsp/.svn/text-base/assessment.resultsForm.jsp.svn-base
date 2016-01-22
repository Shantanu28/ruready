<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />

<c:set var="pagePrefix" value="${flowKeyPrefix}.resultsForm"/>
<c:set var="generalPrefix" value="${flowKeyPrefix}.results"/>

<center><h1><bean:message key="${pagePrefix}.title"/></h1></center>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">
	<div id="knowledgeAssessmentForm">
		<common:messages prefix="${pagePrefix}" showInstructions="true" showRequiredHint="false"/>
		<div class="data">
			<table cellspacing="0">
				<thead>
					<tr>
						<th class="statement"><bean:message key="${generalPrefix}.QUESTION.label"/></th>
						<th><bean:message key="${generalPrefix}.SCORE.label"/></th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td><bean:message key="${generalPrefix}.FINAL_SCORE.label"/></td>
						<td class="numeric finalScore">					
							<fmt:formatNumber type="percent" value="${currentFormObject.assessment.score}" maxFractionDigits="1"/>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach var="item" items="${currentFormObject.assessment.testItems}" varStatus="currStatus">
						<tr>
							<td class="statement">
								<em>${currStatus.count}.</em> ${item.formulation}
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
			<html:submit property="_eventId_submit" styleClass="done">
				<bean:message key="${pagePrefix}.button.submit" />
			</html:submit>
		</div>
	</div>
</html:form>
	