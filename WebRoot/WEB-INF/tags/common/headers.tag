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

Print request headers. Mainly used for debugging purposes.
###################################################################################
--%>
<%@ tag display-name="Prints request cookies"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%-- ============ Tag input attributes ============ --%>
<%-- Requested protocol (default: http) --%>

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

<%
	java.util.Enumeration<?> headers = request.getHeaderNames();
%>

<h4>
	HTTP Request Headers
</h4>
<table border="1" cellspacing="0" cellpadding="5">

	<thead>
	<tr>
		<th>
			Name
		</th>
		<th>
			Value
		</th>
	</tr>
	</thead>

	<tbody>
	<% while (headers.hasMoreElements()) { %>
		<% String name = (String)headers.nextElement(); %>
		<tr>
			<td class="name">
				<%= name %>
			</td>
			<td>
				<%= request.getHeader(name) %>
			</td>
		</tr>
	<% } %>
	</tbody>
	
</table>
