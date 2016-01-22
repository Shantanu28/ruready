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
<c:set var="pagePrefix" value="user.userSearch.view.entryForm"/>
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
	
	<div id="entryForm">
		<common:messages prefix="${pagePrefix}" showRequiredHint="false" showInstructions="true"/>
		<div>
			<label for="logic"><bean:message key="${pagePrefix}.match.label"/>:</label>
			<span>
				<c:forEach var="logic" items="${currentFormObject.criteriaOptions.logicOptions}">
					<html:radio property="logic" value="${logic.value}">${logic.label}</html:radio>
				</c:forEach>
			</span>
		</div>
		<c:forEach var="criteria" items="${currentFormObject.criteria}" varStatus="current">
			<div>
				<label for="criteria[${current.index}].value">
					<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_remove&criteriaId=${current.index}">X</html:link>
					<bean:message key="${userFieldPrefix}.${currentFormObject.criteria[current.index].fieldName}.label"/>:
				</label>
				<span class="searchOptions">
					<c:choose>
						<%-- Text search types --%>
						<c:when test="${criteria.criterionType == 'TEXT'}">
							<html:select property="criteria[${current.index}].searchType" styleClass="searchType">
								<html:optionsCollection property="criteriaOptions.textSearchTypeOptions"/>
							</html:select>
							<html:text property="criteria[${current.index}].value" styleId="criteria[${current.index}].value" styleClass="searchValue"/>
							<%-- MySQL is case-insensitive, so hide the checkbox for now --%>
							<%-- Checkbox pattern for Spring:
								 Since checkboxes are not submitted to the server if they are not checked, the hidden field/checkbox convention
								 below ensures that the right state is saved --%>
							<%-- <input type="hidden" name="_criteria[${current.index}].caseSensitive"/>
							<html:checkbox property="criteria[${current.index}].caseSensitive">
								<bean:message key="${pagePrefix}.caseSensitive.label" />
							</html:checkbox>--%>
							<%-- end hide checkbox --%>
						</c:when>
						<%-- Enum search types --%>
						<c:otherwise>
							<html:select property="criteria[${current.index}].searchType" styleClass="searchType">
								<html:optionsCollection property="criteriaOptions.enumSearchTypeOptions"/>
							</html:select>
							<html:select property="criteria[${current.index}].value" styleId="criteria[${current.index}].value" styleClass="searchValue">
								<html:optionsCollection property="valueOptions[${criteria.fieldName}]"/>
							</html:select>
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</c:forEach>
		<div class="formControls">
			<html:submit property="_eventId_add" styleClass="edit">
				<bean:message key="${pagePrefix}.button.add"/>
			</html:submit>
			<html:submit property="_eventId_search" styleClass="done default">
				<bean:message key="${pagePrefix}.button.search"/>
			</html:submit>
			<html:submit property="_eventId_cancel" styleClass="cancel">
				<bean:message key="${pagePrefix}.button.cancel"/>
			</html:submit>
		</div>
	</div>
</html:form>
