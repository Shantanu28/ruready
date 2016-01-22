<%--
###################################################################################
viewContent.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah.
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

A custom view for a topic. This is required so that we never have to pass through
a question's view before editing it (question view doesn't contain anything; Question
is a custom and complex object).
###################################################################################
--%>

<%@ page import="net.ruready.web.common.rl.WebAppNames"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
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
	<%=""
					+ WebAppNames.REQUEST.ATTRIBUTE.SEARCH_QUESTION_RESULT%>
</c:set>

<c:set var="itemId" scope="request">
	<bean:write name="item" scope="request" property="id" />
</c:set>

<c:set var="itemType" scope="page">
	<bean:write name="item" property="type" />
</c:set>

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
==================================================================
Item's Title & parent hierarchy
==================================================================
--%>

<content:itemTitle item="item" view="browse" scope="request" />
<hr align="left" class="itemseparator" noshade="noshade" />
<p />

<%--
==================================================================
Useful buttons (a "functional toolbar")
==================================================================
--%>
<%--
==================================================================
Useful buttons (a "functional toolbar")
==================================================================
--%>
<%--
Check if there is a custom edit item JSP for this item type. If yes,
include it, otherwise include the default edit item JSP.
--%>
<%-- The relative path to the display JSP (for read-only items) in the current directory --%>
<content:customType prefix="WEB-INF/content/jsp" type="${itemType}" postfix="itemToolbar.jsp"
	id="customDisplayItemTypePath" />
<%-- Note: home dir for the following URL is the parent dir (..) --%>
<jsp:include page="..${customDisplayItemTypePath}/itemToolbar.jsp" />
<p />

<%--
==================================================================
List the item's children (if applicable for this item type)
==================================================================
--%>

<%-- Indent the children section --%>
<div id="children">

	<%-- This item may have children --%>
	<logic:notEmpty name="item" property="childType">
		<c:set var="childType" scope="page">
			<bean:write name="item" property="childType" />
		</c:set>
		<c:set var="categories" scope="page">
			<bean:message key="content.${childType}.labelplural" />
		</c:set>

		<%-- Children title and useful links --%>
		<%-- <h2>${categories}</h2> --%>

		<logic:empty name="item" property="children" scope="request">
			<br />
			<bean:message key="content.editItemFull.children.notfound"
				arg0="${categories}" />
		</logic:empty>

		<logic:notEmpty name="item" property="children" scope="request">

			<%-- Generic item's loop over children --%>
			<%--
			<table>
			<tbody>
				<logic:iterate id="child" indexId="ctr" name="item" property="children"
					scope="request" type="net.ruready.business.content.item.entity.Item">
					<tr>
						<c:set var="childId" scope="page">
							<bean:write name="child" scope="page" property="id" />
						</c:set>
						<c:set var="childType" scope="page">
							<bean:write name="child" property="type" />
						</c:set>
						<td>
							<!-- Child item's icon -->
							<common:message key="content.${childType}.iconClass" 
							altKey="content.ITEM.iconClass" 
							id="iconClass"/>
							<div class="icon ${iconClass}" />
						</td>
						<td>
							&nbsp;
							<html:link module="/content"
								page="/open/explore/viewFull.do?itemId=${childId}&itemType=${childType}">
								<bean:write name="child" scope="page" property="name" />
							</html:link>

							<!-- Display number of sub-categories of this child -->
							<logic:greaterThan name="child" scope="page"
								property="numChildren" value="0">
			               	(<bean:write name="child" scope="page"
									property="numChildren" />)
			            </logic:greaterThan>
						</td>
						<td>
							&nbsp;&nbsp;
						</td>
						<td>
							<content:comment name="${child}" />
						</td>
					</tr>
				</logic:iterate>
			</tbody>
			</table>
		--%>

		<%--
		#######################################################################################
		Custom question table, uses the displaytag library as if we came from a question search
		#######################################################################################
		--%>
		<html:form action="/open/browseQuestion.do" method="post">
			<%-- Parent item reference information --%>
			<input type="hidden" name="parentId" value="${item.id}" />
			<input type="hidden" name="parentType" value="${item.type}" />
			
			<logic:messagesPresent>
				<span class="error">
					<html:errors />
					<br />
				</span>
			</logic:messagesPresent>

			<%-- Children title, useful links, notation --%>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr>
					<td>
						<h2>
							${categories}
						</h2>
					</td>
					<td align="right">
						<table border="0" cellpadding="2">
						<tbody>
							<tr>
								<td class="cellnew">
									<bean:message key="question.state.new" />
								</td>
								<td class="cellactive">
									<bean:message key="question.state.active" />
								</td>
								<td class="cellupdated">
									<bean:message key="question.state.updated" />
								</td>
								<td class="celldisabled">
									<bean:message key="question.state.disabled" />
								</td>
								<td class="celldeleted">
									<bean:message key="question.state.deleted" />
								</td>
							</tr>
						</tbody>
						</table>
					</td>
				</tr>
			</tbody>
			</table>

			<%--
			#################################
			Display result set table
			#################################
			--%>
			
			<%-- Set properties; they may be retrieved by the table decorator object's thru
			pageContext. --%>
			
			<c:set var="requestURI">
				<html:rewrite module='/content'
				page='/open/explore/viewFull.do?itemId=${item.id}&itemType=SUB_TOPIC'/>
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
			<input type="hidden" name="d-16544-p" value="${param['d-16544-p']}">
			<input type="hidden" name="d-16544-s" value="${param['d-16544-s']}">
			<input type="hidden" name="d-16544-o" value="${param['d-16544-o']}">
			--%>
			<%-- 
			Adapt display tag library parameter names (that are not JavaBean-compliant because of the 
			dash with some search question form fields that are persisted in that form's scope (normally
			the sessions). 
			--%>
			<input type="hidden" name="d-16544-p"
				value="${browseQuestionForm.displayTagPage}" />
			<input type="hidden" name="d-16544-s"
				value="${browseQuestionForm.displayTagSortColumn}" />
			<input type="hidden" name="d-16544-o"
				value="${browseQuestionForm.displayTagOrder}" />
			
			<%-- Display the table --%>
			<display:table id="row"
			name="${WEBAPPNAMES_REQUEST_ATTRIBUTE_SEARCH_QUESTION_RESULT}"
			sort="list" requestURI="${requestURI}" excludedParams="*"
			pagesize="${browseQuestionForm.resultsPerPage}" class="dt_maintable">
			
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
							<%--
							<c:set var="parentTitle">
								<bean:message key="content.SUB_TOPIC.label" />
							</c:set>
							<display:column title="${parentTitle}" sortable="true"
								headerClass="${headerStyle}" class="${rowStyle}">
								<c:choose>
									<c:when test="${empty row.parent}">
									-
									</c:when>
									<c:otherwise>
										<c:out value="${row.parent.name}" />
									</c:otherwise>
								</c:choose>
							</display:column>
							--%>
							
							<%-- Is question parametric --%>
							<%--
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
							--%>
									
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
											<c:set var="actionUrl" value="/open/browseQuestion.do?parentId=${item.id}&itemType=QUESTION&action_${action.name}=true" />
										</c:otherwise>
									</c:choose>
			
									<%-- If this is a new object, do not include an id parameter --%>
									<c:if test="${!action.newObject}">
										<c:set var="actionUrl"
											value="${actionUrl}&itemId=${row.id}&validate=false" />
									</c:if>
			
									<%-- Add parameters do not depends on the specific action --%>
									<c:set var="actionUrl" value="${actionUrl}&customForward=parentView" />
			
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


			</html:form>
		</logic:notEmpty>
	</logic:notEmpty>
</div>
