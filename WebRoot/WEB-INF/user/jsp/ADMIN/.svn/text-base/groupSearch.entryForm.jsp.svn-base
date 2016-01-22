<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<html:xhtml />

<c:set var="pagePrefix" value="${flowKeyPrefix}.entryForm"/>
<c:set var="fieldPrefix" value="net.ruready.business.user.entity.property.GroupField"/>

<center><h1><bean:message key="${pagePrefix}.title" /></h1></center>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">

	<div id="entryForm">
		<common:messages prefix="${pagePrefix}" showInstructions="false" showRequiredHint="false"/>
		<%-- School search field --%>
		<div>
			<label for="searchString"><bean:message key="${fieldPrefix}.NAME.label"/>:</label>
			<span>
				<html:text styleId="searchString" property="searchString" size="45"/>
			</span>
		</div>
		<div class="formControls">
			<html:submit property="_eventId_submit" styleClass="done">
				<bean:message key="${pagePrefix}.button.submit" />
			</html:submit>
			<html:submit property="_eventId_cancel" styleClass="cancel">
				<bean:message key="${pagePrefix}.button.cancel" />
			</html:submit>
		</div>
	</div>	
</html:form>