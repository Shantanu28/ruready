<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />

<c:set var="pagePrefix" value="${flowKeyPrefix}.feedback"/>
<c:set var="currentQuestion" value="${currentFormObject.currentQuestion}"/>
<center><h1><bean:message key="${pagePrefix}.pageTitle" arg0="${currentFormObject.currentQuestionNumber}" arg1="${fn:length(currentFormObject.assessment.testItems)}"/></h1></center>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">

	<div id="knowledgeAssessmentForm">
		<div>
			<div>${currentQuestion.formulation}</div>
		</div>
		<div>
			<bean:message key="${pagePrefix}.ANSWER.label"/> ${currentFormObject.answer}
			<em>(score: <fmt:formatNumber type="percent" value="${currentQuestion.score}" maxFractionDigits="1"/>)</em>
		</div>
		<div>
			<c:choose>
				<%-- scores are currently either 1 or 0 --%>
				<c:when test="${currentQuestion.score gt 0}">
					<c:set var="correctnessMessage"><bean:message key="${pagePrefix}.correctness.CORRECT.label"/></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="correctnessMessage"><bean:message key="${pagePrefix}.correctness.INCORRECT.label"/></c:set>
				</c:otherwise>
			</c:choose>
			<bean:message key="${pagePrefix}.correctness.message" arg0="${correctnessMessage}" arg1="${currentQuestion.hintRequestCount}"/>
		</div>
		<div class="formControls">
			<html:submit property="_eventId_continue" styleClass="done">
				<bean:message key="${pagePrefix}.button.continue" />
			</html:submit>
		</div>
	</div>	
</html:form>
