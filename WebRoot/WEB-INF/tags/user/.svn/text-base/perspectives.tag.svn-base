<%--
###################################################################################
perspectives.tag

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print a list of buttons to move to different perspectives allowed by the
user access roles.

This is not a complete HTML page, just a snippet.
Assumes that there is a user bean in the session and that the list of user roles is
non-empty.
###################################################################################
--%>

<%@ tag display-name="Prints perspective buttons"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- the action that the perspectives refer to (e.g. main page) --%>
<%@ attribute name="module" required="true"%>
<%@ attribute name="page" required="true"%>
<%-- Optional request parameters for the aforemention action --%>
<%@ attribute name="params" required="false"%>

<%-- ============ Tag output variables ============ --%>

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

<%-- ============ Tag code begins here ============ --%>

<%-- Create a sorted list of roles --%>


<c:if test="${fn:length(user.roles) > 1}">
	<c:choose>
		<c:when test="${!empty params}">
			<c:set var="queryString" value="${params}&" />
		</c:when>
		<c:otherwise>
			<c:set var="queryString" value="" />
		</c:otherwise>
	</c:choose>

	<table border="0" class="smalltext">
		<tr>
			<c:forEach var="role" items="${user.roles}">		
				<c:choose>
					<c:when test="${role.name == user.defaultPerspective}">
						<c:set var="iconClass" value="icon_user_perspective_default" />
					</c:when>
					<c:otherwise>
						<c:set var="iconClass" value="icon_user_perspective_notdefault" />
					</c:otherwise>
				</c:choose>
				<td align="center" valign="top">
					<html:link module="/user"
						page="/open/ALL/setDefaultPerspective.do?role=${role.name}">
						<div class="icon perspective ${iconClass}">
					</html:link><br />
					&nbsp;
					<html:link module="${module}" page="${page}?${queryString}role=${role.name}">
						<c:choose>
							<c:when test="${role == sessionScope.currentPerspective}">
								<strong>${role.name}</strong>
							</c:when>
							<c:otherwise>
								${role.name}
							</c:otherwise>
						</c:choose>
					</html:link>
					&nbsp;
				</td>
			</c:forEach>
		</tr>
	</table>

</c:if>
