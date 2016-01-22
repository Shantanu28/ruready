<%--
###################################################################################
main.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c)  2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Application main internal page. Pops right after logging in at the front page.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%@ taglib uri="http://ruready.net/user" prefix="user"%>

<%--============================ Useful definitions ==============================--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />
<c:set var="pagePrefix" value="user.userSearch.view.columnsForm"/>
<c:set var="groupFieldPrefix" value="net.ruready.business.user.entity.property.GroupField"/>
<c:set var="userFieldPrefix" value="net.ruready.business.user.entity.property.UserField"/>

<%--
Uncomment this section to turn on XHTML validation of this file.
Because this is a JSP snippet, make sure to comment it back out
before running it on the application container.
--%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>

<center><h1><bean:message key="${pagePrefix}.title" /></h1></center>
<html:form>
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">
	<%-- needed so that validator doesn't trip up --%>
	<html:hidden property="selectedFieldName" value="EMAIL"/>
	<div id="entryForm">
		<common:messages prefix="${pagePrefix}" showRequiredHint="false" showInstructions="true"/>
		<div>
			<label for="fieldName"><bean:message key="${pagePrefix}.fieldname.label"/>:</label>
			<ul class="optionList">
			<c:forEach var="fieldName" items="${currentFormObject.criteriaOptions.viewableFieldNameOptions}" varStatus="fieldStatus">
				<%-- Checkbox pattern for Spring:
					 Since checkboxes are not submitted to the server if they are not checked, the hidden field/checkbox convention
					 below ensures that the right state is saved --%>
				<li>
					<input type="hidden" name="_columns"/>
					<c:choose>
						<c:when test="${currentFormObject.checkedColumns[fieldStatus.index]}">
							<input type="checkbox" name="columns" value="${fieldName.value}" checked="checked" /> ${fieldName.label}
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="columns" value="${fieldName.value}" /> ${fieldName.label}
						</c:otherwise>
					</c:choose>
				</li>
			</c:forEach>
			</ul>
		</div>
		<div class="formControls">
			<html:submit property="_eventId_continue" styleClass="done">
				<bean:message key="${pagePrefix}.button.continue"/>
			</html:submit>
			<html:submit property="_eventId_cancel" styleClass="cancel">
				<bean:message key="${pagePrefix}.button.cancel"/>
			</html:submit>
		</div>
	</div>
</html:form>
