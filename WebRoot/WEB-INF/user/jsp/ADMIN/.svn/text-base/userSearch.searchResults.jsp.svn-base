<%--
###################################################################################
viewGroups.jsp

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
<%@page import="net.ruready.common.search.SortType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<%--============================ Useful definitions ==============================--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />
<c:set var="pagePrefix" value="user.userSearch.view.results"/>
<c:set var="fieldPrefix" value="net.ruready.business.user.entity.property.ViewableUserField"/>
<c:set var="imagePath" value="${pageContext.request.contextPath}/images/common/search"/>

<%--
Uncomment this section to turn on XHTML validation of this file.
Because this is a JSP snippet, make sure to comment it back out
before running it on the application container.
--%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>

<%-- Useful constants --%>
<c:set var="SortType_ASCENDING"><%= SortType.ASCENDING %></c:set>
<c:set var="SortType_DESCENDING"><%= SortType.DESCENDING %></c:set>

<%--============================ JSP body begins here ============================--%>

<div id="tableContent" class="wide">
	<%--<common:messages prefix="${pagePrefix}" showInstructions="false" showRequiredHint="false"/>--%>
	<c:choose>
		<c:when test="${empty results}">
			<div class="empty">
				<bean:message key="${pagePrefix}.nomatches.explanation"/>
			</div>
		</c:when>
		<c:otherwise>
			<div class="formControls">
				<ul>
					<li>
						<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_columns">
							<bean:message key="${pagePrefix}.columns.linkText"/>
						</html:link>
					</li>
				</ul>
			</div>
			<div class="clear"></div>
			<div class="data">
				<c:set var="pageBean" value="${currentFormObject.pagingBean}"/>
				<table cellspacing="0">
					<caption>
						<span>Sorted by: 
							<bean:message key="${fieldPrefix}.${currentFormObject.sortColumn}.label"/>,
							<bean:message key="${pagePrefix}.sort.${currentFormObject.sortOrder}.label"/>
						</span>
						<span>
							<c:if test="${not pageBean.isFirstPage}">
								<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_firstPage" titleKey="${pagePrefix}.first.altText">
									<img src="${imagePath}/resultset_first.png" alt="<bean:message key='${pagePrefix}.first.altText'/>" border="0"/>
								</html:link>
							</c:if>
							<c:if test="${pageBean.hasPreviousPage}">
								<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_prevPage" titleKey="${pagePrefix}.previous.altText">
									<img src="${imagePath}/resultset_previous.png" alt="<bean:message key='${pagePrefix}.previous.altText'/>" border="0" />
								</html:link>
							</c:if>
							Page ${pageBean.currentPage} of ${pageBean.totalPages} <em>(Total Results: ${pageBean.totalResults})</em>
							<c:if test="${pageBean.hasNextPage}">
								<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_nextPage" titleKey="${pagePrefix}.next.altText">
									<img src="${imagePath}/resultset_next.png" alt="<bean:message key='${pagePrefix}.next.altText'/>" border="0" />
								</html:link>
							</c:if>
							<c:if test="${not pageBean.isLastPage}">
								<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_lastPage" titleKey="${pagePrefix}.last.altText">
									<img src="${imagePath}/resultset_last.png" alt="<bean:message key='${pagePrefix}.last.altText'/>" border="0" />
								</html:link>
							</c:if>
						</span>
					</caption>
					<thead>
						<tr>
							<th class="link"></th>
							<c:forEach var="column" items="${currentFormObject.columns}">
								<th>
									<c:choose>
										<c:when test="${column.label eq currentFormObject.sortColumn}">
											<c:choose>
												<c:when test="${currentFormObject.sortOrder eq SortType_ASCENDING}">
													<html:link styleClass="selectedAsc desc" href="?_flowExecutionKey=${flowExecutionKey}&_eventId_sortColumn&sortColumn=${column.label}&sortOrder=${SortType_DESCENDING}" titleKey="${pagePrefix}.sortDescending.altText">
														<bean:message key="${fieldPrefix}.${column.label}.label"/>
													</html:link>
												</c:when>
												<c:otherwise>
													<html:link styleClass="selectedDesc asc" href="?_flowExecutionKey=${flowExecutionKey}&_eventId_sortColumn&sortColumn=${column.label}&sortOrder=${SortType_ASCENDING}" titleKey="${pagePrefix}.sortAscending.altText">
														<bean:message key="${fieldPrefix}.${column.label}.label"/>
													</html:link>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<html:link styleClass="asc" href="?_flowExecutionKey=${flowExecutionKey}&_eventId_sortColumn&sortColumn=${column.label}&sortOrder=${SortType_ASCENDING}" titleKey="${pagePrefix}.sortAscending.altText">
												<bean:message key="${fieldPrefix}.${column.label}.label"/>
											</html:link>										
										</c:otherwise>
									</c:choose>
								</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${results}">
							<tr>
								<td class="link">
									<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_edit" name="user" property="linkParams" titleKey="${pagePrefix}.edit.altText">
										<img src="${imagePath}/user_edit.png" alt="<bean:message key='${pagePrefix}.edit.altText'/>" />
									</html:link>
									<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_delete" name="user" property="linkParams" titleKey="${pagePrefix}.delete.altText">
										<img src="${imagePath}/user_delete.png" alt="<bean:message key='${pagePrefix}.delete.altText'/>" />
									</html:link>
									<c:choose>
										<c:when test="${user.userStatusEnum != 'LOCKED'}">
											<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_lock" name="user" property="linkParams" titleKey="${pagePrefix}.lock.altText">
												<img src="${imagePath}/lock.png" alt="<bean:message key='${pagePrefix}.lock.altText'/>" />
											</html:link>
										</c:when>
										<c:otherwise>
											<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_unlock" name="user" property="linkParams" titleKey="${pagePrefix}.unlock.altText">
												<img src="${imagePath}/lock_open.png" alt="<bean:message key='${pagePrefix}.unlock.altText'/>" />
											</html:link>
										</c:otherwise>
									</c:choose>
									<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_resetPassword" name="user" property="linkParams" titleKey="${pagePrefix}.resetPassword.altText">
										<img src="${imagePath}/key.png" alt="<bean:message key='${pagePrefix}.resetPassword.altText'/>" />
									</html:link>
								</td>
								<c:forEach var="column" items="${currentFormObject.columns}">
									<td><bean:write name="user" property="${column.value}"/></td>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:otherwise>
	</c:choose>	
</div>

