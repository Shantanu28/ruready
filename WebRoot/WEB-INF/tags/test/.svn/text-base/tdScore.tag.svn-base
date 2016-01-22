<%--
###################################################################################
<tdScore> tag

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Prints a formatted table <td> cell with a score.
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
<%-- extra style attribute to add to <td> tag --%>
<%@ attribute name="align" required="false" %>

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
			String scoreStr = (String)(getJspContext().getAttribute("score"));
			String passScoreStr = (String)(getJspContext().getAttribute("passScore"));
			String formatStr = (String)(getJspContext().getAttribute("format"));
			double score = Double.parseDouble(scoreStr);
			double passScore = Double.parseDouble(passScoreStr);
			getJspContext().setAttribute("localScore", score);
			getJspContext().setAttribute("localPassScore", passScore);
			Object[] temp = new Object[1];
			temp[0] = score;
		%>
		<c:set var="formattedScore" scope="page">			
			<%= String.format(formatStr, temp) %>
		</c:set>
	</c:otherwise>
</c:choose>
<%-- 
Print rounded score
--%>
<c:choose>
	<c:when test="${localScore >= localPassScore}">
		<c:set var="styleClass" value="score_pass"/>
	</c:when>
	<c:otherwise>
		<c:set var="styleClass" value="score_fail"/>
	</c:otherwise>
</c:choose>

<td class="${styleClass}" align="${align}">${formattedScore}</td>
