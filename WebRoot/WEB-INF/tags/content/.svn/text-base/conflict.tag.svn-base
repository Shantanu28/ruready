<%--
###################################################################################
conflict.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print the database-stored version of a catalog item's property that is in conflict.
###################################################################################
--%>

<%@ tag display-name="Prints the parent hierarchy of a catalog item"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="nested"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Local entity --%>
<%@ attribute name="localEntity" required="true" type="java.lang.Object" %>
<%-- Stored entity --%>
<%@ attribute name="storedEntity" required="true" type="java.lang.Object" %>

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

<c:if test="${storedEntity != localEntity}">
	<br/>
	<span class="stored">${storedEntity}</span>
</c:if>
