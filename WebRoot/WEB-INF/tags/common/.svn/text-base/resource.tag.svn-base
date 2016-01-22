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

* Note 1: make sure to define null="true" for the application resources element
in the Struts config file, otherwise this tag will throw an exception if the
override key is not found.

* Note 2: this tag must be called within a form bean scope.
###################################################################################
--%>
<%@ tag display-name="Resource with override tag" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Default resource key, must be found in the resources file --%>
<%@ attribute name="base" required="true" fragment="true" %>
<%-- Overriding resource key; used if found in the resources file --%>
<%@ attribute name="override" required="true" fragment="true" %>

<%-- ============ Tag output variables ============ --%>
<%-- id of EL variable whose value is assigned by the default/override key --%>
<%@ attribute name="id" required="true" rtexprvalue="false"%>
<%@ variable alias="result" name-from-attribute="id" scope="AT_END" %>

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

<%-- 
This code will catch an exception if the message specified by
the override key is not found.
Make sure to define null="true" for the application resources element
in the Struts config file.
--%>
<c:catch var="e">
	<bean:define id="overrideKey" scope="page">
		<jsp:invoke fragment="override" />
	</bean:define>
	<bean:define id="overrideValue" scope="page">
		<bean:message key="${overrideKey}" />
	</bean:define>
</c:catch>

<c:choose>
	<c:when test="${!empty e}">
		<bean:define id="baseKey" scope="page">
			<jsp:invoke fragment="base" />	
		</bean:define>
		<bean:define id="result">
			<bean:message key="${baseKey}" />
		</bean:define>
	</c:when>
	<c:otherwise>
		<bean:define id="result" value="${overrideValue}" />
	</c:otherwise>
</c:choose>
		
<c:remove var="e" />
