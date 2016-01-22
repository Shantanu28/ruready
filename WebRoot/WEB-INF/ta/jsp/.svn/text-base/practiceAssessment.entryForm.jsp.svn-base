<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />

<c:set var="pagePrefix" value="${flowKeyPrefix}.entryForm"/>
<c:set var="currentQuestion" value="${currentFormObject.currentQuestion}"/>
<center><h1><bean:message key="${pagePrefix}.pageTitle" arg0="${currentFormObject.currentQuestionNumber}" arg1="${fn:length(currentFormObject.assessment.testItems)}"/></h1></center>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">

	<div id="knowledgeAssessmentForm">
		<common:messages prefix="${pagePrefix}" showInstructions="false" showRequiredHint="false"/>
		<div class="question">
			<div>${currentQuestion.formulation}</div>
		</div>
		<div class="hints">
			<bean:message key="${pagePrefix}.HINT.label"/>
			<ul>
				<c:forEach var="hintLevel" begin="1" end="${currentFormObject.maxHintLevel}">
					<c:choose>
						<c:when test="${hintLevel eq currentFormObject.currentHintNumber}">
							<li class="selected"><html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_showHint${hintLevel}">${hintLevel}</html:link></li>
						</c:when>
						<c:when test="${hintLevel le currentFormObject.availableHintLevel + 1}">
							<li class="available"><html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_showHint${hintLevel}">${hintLevel}</html:link></li>
						</c:when>
						<c:otherwise>
							<li>${hintLevel}</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
			${currentFormObject.currentHint}
		</div>
		<div class="entry">
			<bean:message key="${pagePrefix}.ANSWER.label"/> <html:text property="answer"/>
		</div>
		<c:set var="lastResponseType" value="${currentQuestion.lastResponse.responseType}"/>
		<c:if test="${lastResponseType eq 'TRY'}">
			<div class="lastResponse">
				Last response: ${currentQuestion.lastResponse.answer} Score=<fmt:formatNumber type="percent" value="${currentQuestion.lastResponse.score}" maxFractionDigits="1"/>
			</div>
		</c:if>
		<div class="formControls">
			<html:submit property="_eventId_justATry" styleClass="done">
				<bean:message key="${pagePrefix}.button.justATry" />
			</html:submit>
			<html:submit property="_eventId_finalizeAnswer" styleClass="done">
				<bean:message key="${pagePrefix}.button.finalizeAnswer" />
			</html:submit>
		</div>
	</div>	
</html:form>
