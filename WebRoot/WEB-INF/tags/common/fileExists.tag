<%--
###################################################################################
<fileExists> tag

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

This tag sets an EL boolean variable that is true if and only if a JSP
file exists. The JSP file application-directory-relative-path is an input
attribute.
###################################################################################
--%>
<%@ tag display-name="check if JSP file exists" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Default resource key, must be found in the resources file --%>
<%@ attribute name="path" required="true" %>
<%-- Name of EL variable to be set --%>
<%@ attribute name="id" required="true" rtexprvalue="false" %>

<%-- ============ Tag output variables ============ --%>
<%-- EL variable whose value is assigned (to be used in this tag file) --%>
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

<%-- 
Check whether the file exists under the application directory.
--%>
<%
	// Page context should be accessed by getJspContext().
	String editItemJSP = (String)(getJspContext().getAttribute("path"));
	java.io.File file = new java.io.File(application.getRealPath(editItemJSP));
%>

<%-- Set the output variable --%>
<c:set var="result" value="<%= file.exists() %>" />
