<%--
###################################################################################
updateUser.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah.  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Displays a form for updating user's personal details.
###################################################################################
--%>

<%@ page language="java"%>
<%@page import="net.ruready.web.common.util.HttpRequestUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--============================ Useful definitions ==============================--%>

<%--============================ JSP body begins here ============================--%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html xhtml="true">
	<head>
		<title>Adding a Form Field Using DOM</title>
		<meta http-equiv="keywords" content="enter,your,keywords,here" />
		<meta http-equiv="description"
			content="A short  description of this page." />
		<meta http-equiv="content-type"
			content="text/html; charset=ISO-8859-1" />

		<!--  Common style sheet -->
		<link rel="stylesheet" type="text/css" href="<html:rewrite module='' page='/css/ruready.css'/>" />

		<%-- Javascript script includes --%>
		<script type="text/javascript"
			src="<html:rewrite module='' page='/js/common/formUtil.js' />"></script>
	</head>

	<body>
		<table border="0" align="center" cellpadding="5" cellspacing="0" class="maintable">
		<tbody>
		
		<tr><td>
		<h1>
			Adding a Form Field Using DOM
		</h1>
		Upon clicking &quot;submit&quot;, a hidden form parameter whose value
		is the current date and time will be appended to the form's parameters
		using DOM.
		<form action="" name="myForm" method="post">
			<center>
				<input type="hidden" name="FieldA" value="A normal hidden field" />
				<input type="submit" name="Submit" value="Submit"
					onclick="addHiddenElement(this, 'My Name', 'My value at ' + new Date().toLocaleString());
					submit(); return false; " />
				<input type="reset" name="Reset" value="Reset"
					onclick="window.location.href=''" />
			</center>
		</form>

		<hr />

		<h2>
			Request parameters received:
		</h2>
		<pre><%=HttpRequestUtil.requestParametersToString(request)%></pre>
		</td></tr>
		
		</tbody>
		</table>
	</body>
</html:html>
