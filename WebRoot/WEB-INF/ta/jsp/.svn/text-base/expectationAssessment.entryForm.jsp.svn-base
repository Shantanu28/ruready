<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />

<c:set var="pagePrefix" value="${flowKeyPrefix}.entryForm"/>

<center><h1><bean:message key="${pagePrefix}.title"/></h1></center>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">

	<div id="expectationAssessmentForm">
		<common:messages prefix="${pagePrefix}" showInstructions="true" showRequiredHint="false"/>
		<div class="data">
			<table cellspacing="0">
				<thead>
					<tr>
						<th class="statement"><bean:message key="${pagePrefix}.STATEMENT.label"/></th>
						<c:forEach var="i" begin="1" end="5">
							<th class="option"><bean:message key="${pagePrefix}.SCORE_${i}.label"/></th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${currentFormObject.assessment.testItems}" varStatus="currStatus">
						<tr>
							<td class="statement">
								<em class="number">${currStatus.count}.</em> 
								<span>${item.formulation}(baseline: <fmt:formatNumber type="number" value="${item.baseline}" maxFractionDigits="2"/>)</span>
								<em class="selected"></em>							
							</td>
							<c:forEach var="i" begin="1" end="5">
								<td class="option">
									<html:radio property="assessment.testItems[${currStatus.index}].value" value="${i}.0"/>
								</td>
							</c:forEach>
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