<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html:xhtml />
<c:set var="pagePrefix" value="${flowKeyPrefix}.results"/>
<c:set var="fieldPrefix" value="net.ruready.business.user.entity.property.UserField"/>
<c:set var="imagePath" value="${pageContext.request.contextPath}/images/common/search"/>

<div id="tableContent" class="wide">
	<c:choose>
		<c:when test="${empty results}">
			<div class="empty"><bean:message key="${pagePrefix}.nomatches.explanation"/></div>
		</c:when>
		<c:otherwise>
			<div class="data">
				<c:set var="pageBean" value="${currentFormObject.pagingBean}"/>
				<table cellspacing="0">
					<caption>
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
							<th><bean:message key="${fieldPrefix}.EMAIL.label"/></th>
							<th><bean:message key="${fieldPrefix}.namefields.label"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${results}">
							<tr>								
								<td class="link">
									<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_add&userId=${user.id}">
										<bean:message key="${pagePrefix}.add.altText"/>
									</html:link>
								</td>
								<td>${user.email}</td>
								<td>${user.name.formattedName}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</div>