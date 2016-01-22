<%--
###################################################################################
<customType> tag

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

This tag sets an EL boolean variable that is set based on the existence of a
JSP file is not found this item type.

The path to look for this file is by convention "prefix/type/postfix"
where prefix, type and postfix are the requested attributes. Typically
"postfix" is the JSP file name. "prefix" is relative to the application's home
directory (= WEB-INF's parent directory).

The output variable's format is "/type" if the file is found. If it is not,
the output variable is set to "/default". Note that "default" is lower-case on
purpose to guarantee delineation from item types, which are always upper-case
(being enumerated type names).
###################################################################################
--%>
<%@ tag display-name="custom catalog item type path" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Prefix path --%>
<%@ attribute name="prefix" required="true" %>
<%-- Custom item type --%>
<%@ attribute name="type" required="true" %>
<%-- Post path/file name --%>
<%@ attribute name="postfix" required="true" %>
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

<%-- Find the file --%>
<common:fileExists path="${prefix}/${type}/${postfix}" id="customTypeFile" />

<%-- Set the output variable --%>
<c:choose>
	<%-- Custom item type file found --%>
	<c:when test="${customTypeFile}">
		<c:set var="result" value="/${type}" />
	</c:when>
	
	<%-- Custom item type file not found --%>
	<c:otherwise>
		<c:set var="result" value="/default" />
	</c:otherwise>
</c:choose>
