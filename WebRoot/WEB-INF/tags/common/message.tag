<%--
###################################################################################
<resource> tag

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

This tag sets an EL variable whose value is taken from the application resource
file (through Struts), or from a default value resource key, if the resource is
not found by the provided override key. The default value MUST be found in the
application resources file, while the override key could be omitted there. This
provides an "inheritance-like" behavior for resources.

* Note 1: make sure to define null="false" for the application resources element
in the Struts config file, otherwise this tag will throw an exception if the
override key is not found.
###################################################################################
--%>
<%@ tag display-name="Resource with override tag" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- ============ Tag input attributes ============ --%>
<%@ attribute name="key" required="true" rtexprvalue="true"%>
<%@ attribute name="altKey" required="true" rtexprvalue="true"%>

<%-- ============ Tag input fragment attributes ============ --%>

<%-- ============ Tag output variables ============ --%>
<%-- EL variable whose value is assigned (to be used in this tag file) --%>
<%@ attribute name="id" required="true" rtexprvalue="false"%>
<%@ variable alias="result" name-from-attribute="id" scope="AT_BEGIN" %>

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

<c:set var="keyText"><bean:message key="${key}"/></c:set>
<c:set var="altText"><bean:message key="${altKey}"/></c:set>

<c:choose>
	<c:when test="${fn:startsWith(keyText,'???')}">
		<c:set var="result" value="${altText}" />
	</c:when>
	<c:otherwise>
		<c:set var="result" value="${keyText}" />
	</c:otherwise>
</c:choose>

<c:remove var="keyText"/>
<c:remove var="altText"/>
