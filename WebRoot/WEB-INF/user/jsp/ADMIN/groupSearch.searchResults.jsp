<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html:xhtml />
<c:set var="pagePrefix" value="${flowKeyPrefix}.results"/>
<c:set var="fieldPrefix" value="net.ruready.business.user.entity.property.GroupField"/>
<c:set var="imagePath" value="${pageContext.request.contextPath}/images/common/search"/>

<div id="tableContent" class="wide">
	<div class="formControls">
		<ul>
			<li>
				<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_create">
					<bean:message key="${pagePrefix}.create.altText"/>
				</html:link>
			</li>
		</ul>
	</div>
	<div class="clear"></div>
	<c:choose>
		<c:when test="${results == null}">
		</c:when>
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
							<th><bean:message key="${fieldPrefix}.NAME.label"/></th>
							<th><bean:message key="${fieldPrefix}.COURSE.label"/></th>
							<th><bean:message key="${fieldPrefix}.SCHOOL.label"/></th>
							<th><bean:message key="${fieldPrefix}.MODERATORS.label"/></th>
							<th><bean:message key="${fieldPrefix}.MEMBERS.label"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="group" items="${results}">
							<tr>								
								<td class="link">
									<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_edit&groupId=${group.id}">
										<bean:message key="${pagePrefix}.edit.altText"/>
									</html:link>
									<html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_delete&groupId=${group.id}">
										<bean:message key="${pagePrefix}.delete.altText"/>
									</html:link>
								</td>
								<td>${group.name}</td>
								<td>${group.course.description}</td>
								<td>${group.school.description}</td>
								<td>
									${fn:length(group.moderators)} -
									 <html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_moderators&groupId=${group.id}">
										<bean:message key="${pagePrefix}.moderators.altText"/>
									</html:link>
								</td>
								<td>
									${fn:length(group.membership)} -
									 <html:link href="?_flowExecutionKey=${flowExecutionKey}&_eventId_members&groupId=${group.id}">
										<bean:message key="${pagePrefix}.members.altText"/>
									</html:link>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</div>