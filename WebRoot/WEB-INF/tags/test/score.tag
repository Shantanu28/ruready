<%--
###################################################################################
<score> tag

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Prints a formatted score.
###################################################################################
--%>
<%@ tag display-name="check if JSP file exists" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Score variable --%>
<%@ attribute name="score" required="true" %>
<%-- Passing score, determines style --%>
<%@ attribute name="passScore" required="true" %>
<%-- # digits to round score --%>
<%@ attribute name="format" required="false" %>

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

<%-- 
Compute rounded/formatted score
--%>
<c:choose>
	<c:when test="${empty format}">
		<c:set var="formattedScore" scope="page" value="${score}" />
	</c:when>
	<c:otherwise>
		<%
			// Compute rounded score
			String score = (String)(getJspContext().getAttribute("score"));
			String format = (String)(getJspContext().getAttribute("format"));
			Object[] temp = new Object[1];
			temp[0] = Double.parseDouble(score);
		%>
		<c:set var="formattedScore" scope="page">			
			<%= String.format(format, temp) %>
		</c:set>
	</c:otherwise>
</c:choose>
<%-- 
Print rounded score
--%>
<c:choose>
	<c:when test="${score >= passScore}">
		<span class="score_pass">${formattedScore}</span>
	</c:when>
	<c:otherwise>
		<span class="score_fail">${formattedScore}</span>
	</c:otherwise>
</c:choose>
<%
	// Page context should be accessed by getJspContext().
//	Double score = Double.parseDouble((String)(getJspContext().getAttribute("score")));
%>
