<%--
###################################################################################
<path> tag

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print the the full path prefix for every relative resource used in JSPs.
This includes an optional attribute for the requested protocol. If it is not
given, it defaults to "http". To request an SSL HTTP, use protocol="https".
To request the full qualified URL including host name and port, use full="true".
###################################################################################
--%>
<%@ tag display-name="Path prefix tag" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%-- ============ Tag input attributes ============ --%>
<%-- Requested protocol (default: http) --%>
<%@ attribute name="protocol" required="false" %>
<%@ attribute name="full" required="false" %>

<%-- ============ Tag input fragment attributes ============ --%>

<%-- ============ Tag output attributes ============ --%>

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

<c:choose>
	<c:when test="${!empty protocol}">
		<c:set var="protocolPath" scope="page">${protocol}</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="protocolPath" scope="page">http</c:set>
	</c:otherwise>
</c:choose>

<c:if test="${full}">
	<c:set var="prefix" scope="page">${protocolPath}://<%= request.getServerName() %>:<%= request.getServerPort() %></c:set>
</c:if>

${prefix}<%= request.getContextPath() %>