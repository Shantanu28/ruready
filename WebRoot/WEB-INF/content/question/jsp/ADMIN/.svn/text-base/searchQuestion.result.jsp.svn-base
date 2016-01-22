<%--
###################################################################################
searchQuestion.result.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112-9359

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

A JSP snippet that display a result set table on the search question page.
Must be included within an <html:form> scope.
###################################################################################
--%>

<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ page import="net.ruready.web.common.rl.WebAppNames"%>
<%@page import="net.ruready.business.content.item.entity.ItemType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

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

<c:set var="WEBAPPNAMES_REQUEST_ATTRIBUTE_SEARCH_QUESTION_RESULT">
	<%= ""+ WebAppNames.REQUEST.ATTRIBUTE.SEARCH_QUESTION_RESULT %>
</c:set>

<%-- Save realm parameter if found in search form --%>
<c:set var="ajaxForm" value="${searchQuestionForm.subTopicMenuGroupForm}" />
<c:set var="prefix" value="subTopicMenuGroupForm." />
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

<%-- Date & time printout format --%>
<c:set var="dateformat" scope="page">
	<bean:message key='app.format.table.date' />
</c:set>

<%--============================ JSP body begins here ============================--%>

<%--
############################ 
Javascript script includes
############################ 
--%>

<%-- Event handlers for the display tag result set table --%>
<script type="text/javascript"
src="<html:rewrite module='' page='/js/common/formUtil.js' />"></script>

<%--
############################ 
Empty result set
############################ 
--%>
<bean:size id="size"
	name="${WEBAPPNAMES_REQUEST_ATTRIBUTE_SEARCH_QUESTION_RESULT}"
	scope="request" />
<logic:equal name="size" value="0">
	<bean:message key="app.search.notfound" />
</logic:equal>

<%--
############################ 
Display result header
############################ 
--%>
<logic:greaterThan name="size" value="0">

<%--
#################################
Display result set table
#################################
--%>

<%-- Set properties; they may be retrieved by the table decorator object's thru
pageContext. --%>

<c:set var="baseRequestURI">
	<html:rewrite module='/content' page='/open/searchQuestion.do' />
</c:set>
<c:set var="requestURI">
	<html:rewrite module='/content' page='/open/searchQuestion.do?action_search=true&validate=false' />
</c:set>

<%--
Prepare a link for resetting sorting and pagination
--%>
<c:set var="resetDTLabel">
	<bean:message key="app.action.resetDT" />
</c:set>
<c:set var="resetDTLink">
	<html:link styleId="resetDT" href="${resetDTLabel}">
		${resetDTLabel}
	</html:link>
</c:set>

<%--
#############################################
DisplayTag - Main Table
#############################################
--%>
<display:table id="row"
name="${WEBAPPNAMES_REQUEST_ATTRIBUTE_SEARCH_QUESTION_RESULT}"
sort="list" requestURI="${requestURI}" excludedParams="*"
pagesize="${searchQuestionForm.resultsPerPage}" class="dt_maintable">

	<%--
	#############################################
	DisplayTag Property settings
	#############################################
	--%>
	<display:setProperty name="paging.banner.items_name">
		<bean:message key="content.QUESTION.labelplural" />
	</display:setProperty>

	<display:setProperty name="paging.banner.group_size" value="50" />
	<display:setProperty name="paging.banner.page.separator" value="&nbsp;" />

	<display:setProperty name="paging.banner.first">
		&nbsp;&nbsp;
		<span class="pagelinks">
			<bean:message key="app.search.page.previous" />&nbsp;&nbsp; {0} &nbsp;&nbsp; <strong>
			<a href="{3}"><bean:message key="app.search.page.next" /></a> </strong>
		</span>
		&nbsp;&nbsp; ${resetDTLink}				
	</display:setProperty>

	<display:setProperty name="paging.banner.full">
		&nbsp;&nbsp;
		<span class="pagelinks"> <strong> <a href="{2}"><bean:message
		key="app.search.page.previous" /></a> </strong> &nbsp;&nbsp; {0} &nbsp;&nbsp; <strong> <a href="{3}"><bean:message
		key="app.search.page.next" /></a> </strong> </span>
				&nbsp;&nbsp; ${resetDTLink}				
	</display:setProperty>

				<display:setProperty name="paging.banner.last">
			&nbsp;&nbsp;
			<span class="pagelinks"> <strong> <a href="{2}"><bean:message
									key="app.search.page.previous" /></a> </strong> &nbsp;&nbsp; {0} &nbsp;&nbsp; <bean:message
							key="app.search.page.next" /> </span>
				&nbsp;&nbsp; ${resetDTLink}	
			</display:setProperty>

				<display:setProperty name="paging.banner.onepage">
				&nbsp;&nbsp; ${resetDTLink}				
			</display:setProperty>

				<%--
				<display:setProperty name="paging.banner.some_items_found">
					<span class="pagebanner"> <bean:message key="app.search.notfound" /> </span>
					<p>
				</display:setProperty>
				--%>
				<c:set var="headerStyle" value="dt_column_header" />

				<c:choose>
					<c:when test="${row.new eq true}">
						<c:set var="rowStyle" value="cellnew" />
					</c:when>
					<c:when test="${row.active eq true}">
						<c:set var="rowStyle" value="cellactive" />
					</c:when>
					<c:when test="${row.updated eq true}">
						<c:set var="rowStyle" value="cellupdated" />
					</c:when>
					<c:when test="${row.disabled eq true}">
						<c:set var="rowStyle" value="celldisabled" />
					</c:when>
					<c:when test="${row.deleted eq true}">
						<c:set var="rowStyle" value="celldeleted" />
					</c:when>
					<c:otherwise>
						<c:set var="rowStyle" value="cellthinborder" />
					</c:otherwise>
				</c:choose>

				<%--
				#############################################
				Display data (question field) columns
				#############################################
				--%>

				<%-- Serial row number --%>
				<display:column title="#" headerClass="${headerStyle}"
					class="${rowStyle}">
					<c:out value="${row_rowNum}" />
				</display:column>

				<%-- Question entity unique identifier --%>
				<c:set var="idTitle">
					<bean:message key="question.id.label" />
				</c:set>
				<display:column title="${idTitle}" property="id" sortable="true"
					headerClass="${headerStyle}" class="${rowStyle}"
					style="text-align:center" />

				<%-- Item name --%>
				<%--
				<c:set var="nameTitle">
					<bean:message key="content.Item.name.label" />
				</c:set>
				<display:column title="${nameTitle}" property="name"
					sortable="true" headerClass="${headerStyle}" class="${rowStyle}" />
				--%>
				
				<%-- Question level --%>
				<c:set var="levelTitle">
					<bean:message key="question.level.label" />
				</c:set>
				<display:column title="${levelTitle}" property="level"
					sortable="true" headerClass="${headerStyle}" class="${rowStyle}"
					style="text-align:center" />
				
				<%-- Question type --%>
				<c:set var="typeTitle">
					<bean:message key="question.typeString.label" />
				</c:set>
				<display:column title="${typeTitle}" property="questionType"
					sortable="true" headerClass="${headerStyle}" class="${rowStyle}" />

				<%-- Question formulation. Truncated to 20 chars max. --%>
				<c:set var="formulationTitle">
					<bean:message key="question.formulation.label" />
				</c:set>
				<display:column title="${formulationTitle}"
					sortable="true" headerClass="${headerStyle}" class="${rowStyle}">
					<c:choose>
						<c:when test="${fn:length(row.formulation) <= 10}">
							<c:out value="${row.formulation}" />
						</c:when>
						<c:otherwise>
							<c:out value="${fn:substring(row.formulation,0,7)}" />...
						</c:otherwise>
					</c:choose>
				</display:column>

				<%-- TODO: replace by question.lastestAuditMessage.date --%>
				<c:set var="lastModifiedTitle">
					<bean:message key="question.lastModified.label" />
				</c:set>
				<display:column title="${lastModifiedTitle}"
					sortable="true" headerClass="${headerStyle}" class="${rowStyle}"
					style="text-align:center">
					<bean:write name="row" property="lastModified" scope="page"
							format="${dateformat}" />
				</display:column>

				<%-- Question's parent topic item. --%>
				<c:set var="parentTitle">
					<bean:message key="content.SUB_TOPIC.label" />
				</c:set>
				<display:column title="${parentTitle}" sortable="true"
					headerClass="${headerStyle}" class="${rowStyle}">
					<c:choose>
						<%-- Cover old test cases with orphan questions --%>
						<c:when test="${empty row.parent}">
						-
						</c:when>
						<c:otherwise>
							<c:out value="${row.parent.name}" />
						</c:otherwise>
					</c:choose>
				</display:column>

				<%-- Is question parametric --%>
				<c:set var="parametricTitle">
					<bean:message key="question.parametric.title" />
				</c:set>
				<display:column title="${parametricTitle}" sortable="true"
					headerClass="${headerStyle}" class="${rowStyle}">
					<center>
						<c:choose>
							<c:when test="${row.parametric}">
								<bean:message key="question.parametric.true" />
							</c:when>
							<c:otherwise>
								<bean:message key="question.parametric.false" />
							</c:otherwise>
						</c:choose>
					</center>
				</display:column>

				<%-- Ordered set of permissible actions based on the question's state --%>
				<c:set var="functionsTitle">
					<bean:message key="question.actions.title" />
				</c:set>
				<display:column title="${functionsTitle}"
					headerClass="${headerStyle}" class="${rowStyle}"
					style="text-align:center">
					<bean:size id="numActions" name="row" property="actions"
						scope="page" />
					<logic:iterate id="action" indexId="ctr" name="row"
						property="actions" scope="page"
						type="net.ruready.business.content.question.entity.property.QuestionAction">

					<%--
					##########################################################################
					Build the appropriate Struts handler (action url) for this question action
					##########################################################################
					--%>
					<%--
					Base url depends on whether this is an editing action or not,
					in which case we return to this page after the action is completed.
					--%>
						<c:choose>
							<c:when test="${action.edit}">
								<c:set var="actionUrl"
									value="/open/editItemFull/QUESTION.do?itemType=QUESTION" />
							</c:when>
							<c:otherwise>

								<c:set var="actionUrl"
									value="/open/searchQuestion.do?itemType=QUESTION&action_${action.name}=true${realmQueryString}" />
							</c:otherwise>
						</c:choose>

						<%-- If this is a new object, do not include an id parameter --%>
						<c:if test="${!action.newObject}">
							<c:set var="actionUrl"
								value="${actionUrl}&itemId=${row.id}&validate=false" />
						</c:if>

						<%-- Add parameters do not depends on the specific action --%>
						<c:set var="actionUrl" value="${actionUrl}&customForward=search" />

						<%-- Create a link to the action handler --%>
						<html:link module="/content" page="${actionUrl}">
							<bean:message key="question.action.${action.name}" />
						</html:link>

						<%-- Separator --%>
						<c:if test="${ctr < numActions-1}">
						&nbsp;&nbsp;
					</c:if>

		</logic:iterate>
	</display:column>
</display:table>
</logic:greaterThan>
