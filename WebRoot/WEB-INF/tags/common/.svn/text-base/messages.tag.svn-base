<%@ tag display-name="Page messages tag"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%@ attribute name="prefix" required="true" %>
<%-- optional (default=true): show page instruction if no success/error messages are provided --%>
<%@ attribute name="showInstructions" required="false" %>
<%-- optional (default=true): show required hint on instruction/error messages --%>
<%@ attribute name="showRequiredHint" required="false" %>
<html:xhtml />
<%-- Initial page instructions --%>
<c:set var="requiredSymbol"><common:required/></c:set>
<c:set var="requiredHint" value=""/>
<c:if test="${(empty showRequiredHint) || (showRequiredHint == 'true')}">
	<c:set var="requiredHint">
		<p class="required">- <bean:message key="app.requiredFields" arg0="${requiredSymbol}" /></p>
	</c:set>
</c:if>
<%-- determine what type of message to show (error,instruction,success) --%>
<%-- test 1: error messages --%>
<c:set var="hasMessages"><logic:messagesPresent>YES</logic:messagesPresent></c:set>
<c:choose>
	<c:when test="${hasMessages == 'YES'}">
		<c:set var="messageType" value="errorMessages"/>
		<c:set var="explanationHeader"><bean:message key="${prefix}.explanation.error.header"/></c:set>
		<c:set var="explanationMessage"><bean:message key="${prefix}.explanation.error.message"/></c:set>
	</c:when>
	<c:otherwise>
		<%-- test 2: success messages --%>
		<c:set var="hasMessages"><logic:messagesPresent message="true">YES</logic:messagesPresent></c:set>
		<c:choose>
			<c:when test="${hasMessages == 'YES'}">
				<c:set var="messageType" value="messages"/>
				<c:set var="explanationHeader"><bean:message key="${prefix}.explanation.success.header"/></c:set>
				<c:set var="explanationMessage"><bean:message key="${prefix}.explanation.success.message"/></c:set>
			</c:when>
			<c:otherwise>
				<%-- instruction messages --%>
				<c:set var="messageType" value="instructions"/>
				<c:set var="explanationHeader"><bean:message key="${prefix}.explanation.header"/></c:set>
				<c:set var="explanationMessage"><bean:message key="${prefix}.explanation.message"/></c:set>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
<%-- render the messages --%>
<c:if test="${(messageType != 'instructions') || ((empty showInstructions) || (showInstructions == 'true'))}">
<div id="${messageType}">
	<%-- show explanation headers if they are defined for the page and message type --%>
	<c:if test="${!fn:startsWith(explanationHeader,'???')}">
		<h3>${explanationHeader}</h3>
	</c:if>
	<c:if test="${!fn:startsWith(explanationMessage,'???')}">
		<p>${explanationMessage}</p>
	</c:if>
	<%-- show the success/error messages, if there are any --%>
	<c:if test="${messageType != 'instructions'}">
		<ul>
			<html:messages id="messageItem" message="${messageType == 'messages'}">
				<li>${messageItem}</li>
			</html:messages>
		</ul>
	</c:if>
	<%-- Only show the required hint if the message type is instructions or error messages --%>
	<c:if test="${messageType !='messages'}">
		${requiredHint}
	</c:if>
</div>
</c:if>