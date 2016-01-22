<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<div class="question">${currentQuestion.formulation} <em>(multiple-choice)</em></div>
		<div class="choices">
			<ol>
				<c:forEach var="choice" items="${currentQuestion.choices}">
					<li class="option"><html:radio property="answer" value="${choice.choiceText}"/> ${choice.choiceText} <em>(${choice.isCorrect})</em></li>
				</c:forEach>
			</ol>
		</div>
		<div class="formControls">
			<html:submit property="_eventId_finalizeAnswer" styleClass="done">
				<bean:message key="${pagePrefix}.button.finalizeAnswer" />
			</html:submit>
		</div>
	</div>	
</html:form>