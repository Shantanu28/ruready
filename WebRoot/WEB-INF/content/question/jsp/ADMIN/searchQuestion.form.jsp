<%--
###################################################################################
searchQuestion.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112-9359

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

This is a JSP snippet, not a full page.
Displays a question search form (part of the search question page).
Must be included within an <html:form> scope.
###################################################################################
--%>

<%@ page language="java"%>
<%@ page import="net.ruready.common.search.Logic"%>
<%@ page import="net.ruready.business.content.item.entity.ItemType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%--============================ Useful definitions ==============================--%>

<%--
Uncomment this section to turn on XHTML validation of this file.
Because this is a JSP snippet, make sure to comment it back out
before running it on the application container.
--%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />

<%--
=================================
AJAX declarations and includes
=================================
--%>

<%-- Save realm parameter if found in search form and pass it to the edit question form --%>
<c:set var="ajaxForm" value="${searchQuestionForm.subTopicMenuGroupForm}" />
<c:set var="prefix" value="itemForm.subTopicMenuGroupForm." />
<c:set var="subjectMenu"><%=ItemType.SUBJECT.getCamelCaseName()%>Id</c:set>
<c:set var="courseMenu"><%=ItemType.COURSE.getCamelCaseName()%>Id</c:set>
<c:set var="topicMenu"><%=ItemType.TOPIC.getCamelCaseName()%>Id</c:set>
<c:set var="subTopicMenu"><%=ItemType.SUB_TOPIC.getCamelCaseName()%>Id</c:set>
<c:choose>
	<c:when test="${ajaxForm.subTopicIdAsLong > 0}">
		<c:set var="realmQueryString">&${prefix}${subTopicMenu}=${ajaxForm.subTopicId}</c:set>
	</c:when>
	<c:when test="${ajaxForm.topicIdAsLong > 0}">
		<c:set var="realmQueryString">&${prefix}${topicMenu}=${ajaxForm.topicId}</c:set>
	</c:when>
	<c:when test="${ajaxForm.courseIdAsLong > 0}">
		<c:set var="realmQueryString">&${prefix}${courseMenu}=${ajaxForm.courseId}</c:set>
	</c:when>
	<c:when test="${ajaxForm.subjectIdAsLong > 0}">
		<c:set var="realmQueryString">&${prefix}${subjectMenu}=${ajaxForm.subjectId}</c:set>
	</c:when>
</c:choose>

<%--============================ JSP body begins here ============================--%>

<%-- Validation errors --%>
<logic:messagesPresent>
	<span class="error"> 
		<html:errors /> 
		<br /> 
	</span>
</logic:messagesPresent>
		
<%-- Display search instructions --%>
<c:set var="searchModeMenu">
	<html:select property="searchMode" styleClass="header">
		<html:option value="<%= Logic.AND.name() %>">
			<bean:message key="net.ruready.common.search.Logic.AND.short" />
		</html:option>
		<html:option value="<%= Logic.OR.name() %>">
			<bean:message key="net.ruready.common.search.Logic.OR.short" />
		</html:option>
	</html:select>	
</c:set>

<%-- Debugging printout of DT form properties --%>
<%--searchQuestionForm=${searchQuestionForm}--%>
<%--<jsp:include page="printFormProps.jsp" />--%>

<%--
#######################################################
Search criteria (type, level, etc.)
#######################################################
--%>
<div id="field">
	<table border="0" cellspacing="1">
	<tbody>
		<tr>
				
		<%-- Left column: question data fields --%>
		<td valign="top">
			<table border="0" cellspacing="2">
				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="question.searchQuestion.criteria" arg0="${searchModeMenu}"/>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<bean:message key="question.level.label" />:
						</td>
						<td>
							<html:text property="level" />
						</td>
					</tr>
					<tr>
						<td>
							<bean:message key="question.typeString.label" />:
						</td>
						<td>
							<html:select property="type">
								<html:option value=""><bean:message key="app.search.any"/></html:option>
								<html:optionsCollection property="questionTypeOptions" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td>
							<bean:message key="question.formulation.label" />:
						</td>
						<td>
							<html:text property="formulation" />
						</td>
					</tr>
					<tr>
						<td>
							<bean:message key="question.id.label" />:
						</td>
						<td>
							<html:text property="id" />
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
	</table>
</div>
<%--
#######################################################
Search-in menus - realm fields
* Which subjects and courses are we searching).
* These are drop-down menus populated by AJAX.
#######################################################
--%>
<div id="item_group">
	<table border="0" cellspacing="2">
		<thead>
			<tr>
				<td colspan="2">
					<bean:message key="question.searchQuestion.realm" />
				</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<bean:message key="content.SUBJECT.label" />:
				</td>
				<td>
					<div id="${subjectMenu}">
						<bean:message key="app.na.label" />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="content.COURSE.label" />:
				</td>
				<td>
					<div id="${courseMenu}">
						<bean:message key="app.na.label" />
						</div>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="content.TOPIC.label" />:
				</td>
				<td>
					<div id="${topicMenu}">
						<bean:message key="app.na.label" />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="content.SUB_TOPIC.label" />:
				</td>
				<td>
					<div id="${subTopicMenu}">
						<bean:message key="app.na.label" />
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<%--
############################################
Submit buttons and display options
############################################
--%>
<div id="submit">
	<html:submit property="action_search" styleClass="done">
		<bean:message key="app.action.search" />
	</html:submit>
	&nbsp;			
	<html:submit property="action_setup_reset" styleClass="cancel">
		<bean:message key="app.action.reset" />
	</html:submit>
	&nbsp;		
	<c:set var="editQuestionUrl">
		/open/editItemFull/QUESTION.do?itemType=QUESTION&customForward=search${realmQueryString}
	</c:set>
	<input type="button"
		value='<bean:message key="question.editQuestion.action.add" />'
		onclick="window.location='<html:rewrite module='/content' page='${editQuestionUrl}'/>'"
		class="edit" />
</div>
