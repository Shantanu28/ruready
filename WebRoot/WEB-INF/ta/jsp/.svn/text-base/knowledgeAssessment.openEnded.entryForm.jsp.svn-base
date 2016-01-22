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
		<div>
			<div>${currentQuestion.formulation}</div>
		</div>
		<div>
			<bean:message key="${pagePrefix}.ANSWER.label"/> <html:text property="answer"/>
		</div>
		<div class="formControls">
			<html:submit property="_eventId_finalizeAnswer" styleClass="done">
				<bean:message key="${pagePrefix}.button.finalizeAnswer" />
			</html:submit>
		</div>
	</div>	
</html:form>
