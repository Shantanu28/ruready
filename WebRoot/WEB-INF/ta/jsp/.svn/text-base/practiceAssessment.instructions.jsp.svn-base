<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />

<c:set var="pagePrefix" value="${flowKeyPrefix}.instructions"/>
<c:set var="imagePath" value="${pageContext.request.contextPath}/images/ta/toolbar"/>
<c:set var="levelDescription"><bean:message key="question.level${currentFormObject.practiceLevel}.label"/></c:set>
<center><h1><bean:message key="${pagePrefix}.pageTitle" arg0="${currentFormObject.practiceLevel}" arg1="${levelDescription}"/></h1></center>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">
	<div id="knowledgeAssessmentForm">
		<div class="instructions">
			<bean:message key="${pagePrefix}.introduction"/>
			<ul>
				<li><bean:message key="${pagePrefix}.bullet_1"/></li>
				<li><bean:message key="${pagePrefix}.bullet_2"/></li>
				<li><bean:message key="${pagePrefix}.bullet_3"/></li>
				<li><bean:message key="${pagePrefix}.bullet_4"/></li>
				<li><bean:message key="${pagePrefix}.bullet_5"/></li>
			</ul>
			<p><bean:message key="${pagePrefix}.icons.summary"/></p>
			<div class="icons">				
				<div>
					<img src="${imagePath}/stop.png" />
					<bean:message key="ta.toolbar.STOP.label"/>
				</div>
				<div>
					<img src="${imagePath}/sigma.png" />
					<bean:message key="ta.toolbar.ENTRY_INSTRUCTIONS.label"/>
				</div>
				<div>
					<img src="${imagePath}/calculator.png" />
					<bean:message key="ta.toolbar.CALCULATOR.label"/>
				</div>
				<div>
					<img src="${imagePath}/instructions.png" />
					<bean:message key="ta.toolbar.TEST_INSTRUCTIONS.label"/>
				</div>
				<div>
					<img src="${imagePath}/graphic_calculator.png" />
					<bean:message key="ta.toolbar.GRAPHIC_CALCULATOR.label"/>
				</div>
			</div>
			<div class="reminder">
				<bean:message key="${pagePrefix}.reminder"/>
			</div>
		</div>
		<div class="formControls">
			<html:submit property="_eventId_start" styleClass="done">
				<bean:message key="${pagePrefix}.button.start" />
			</html:submit>
		</div>
	</div>
</html:form>
