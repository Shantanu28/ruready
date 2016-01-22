<%--
###################################################################################
template.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

A basic template for view pages. This is used by the Struts's Tiles
framework to construct the actual JSPs. This JSP generates an XHTML document.
Note: tomcat 6 does not support jspx files as far as I can tell, so this file
should be a .jsp.
###################################################################################
--%>

<%--========================== Tag Library Declarations ==========================--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%--============================ Useful definitions ==============================--%>

<c:set var="logoClass" scope="page">
	<tiles:getAsString name='logoClass' />
</c:set>

<%--============================ JSP body begins here ============================--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html xhtml="true">

<%--
============================ 
HTML Header Section
============================
--%>
<head>
	<c:set var="titleKey" scope="page">
		<tiles:getAsString name="title" />
	</c:set>
	<title><bean:message key="${titleKey}" /></title>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<%-- Create a message key prefix, if defined in tiles definition --%>
	<c:set var="prefixKey" scope="page">
		<tiles:getAsString name="messageKeyPrefix" ignore="true" />
	</c:set>
	<c:set var="messagePrefix" value="${prefixKey}" scope="request" />

	<%--
	Javascript library includes.
	Note: library ordering matters; ext's css must be included after the library
	includes.
	--%>
	<script type="text/JavaScript" src="${contextPath}/include/jquery/jquery-1.2.1.js"></script>

	<%-- Optional javascript header file (e.g. includes jquery/ext onReady()) --%>
	<tiles:insert attribute="head" ignore="true" />

	<%-- Cascading style sheets --%>
	<link href="${contextPath}/include/ext/css/ext-all.css"
	media="screen" rel="Stylesheet" type="text/css" />
	
	<link href="${contextPath}/css/ruready.css"
	media="screen" rel="stylesheet" type="text/css" />
</head>

<%--
============================ 
HTML Body Section
============================
--%>
<body>
	<div id="page">
		<%-- Header line: title, useful links --%>
		<div id="header">
			<div id="logo" class="${logoClass}" />
			</div>
			<div id="title">
				<c:set var="headerKey" scope="page">
					<tiles:getAsString name="header" />
				</c:set>
				<h1><bean:message key="${headerKey}" /></h1>
			</div>
			<div id="links">
				<tiles:insert attribute="links" />
			</div>
		</div>

		<%-- Top toolbar --%>
		<div id="toolbar">
			<tiles:insert attribute="toolbar" />
		</div>

		<%-- Main area with content --%>
		<div id="main">
			<%-- Navigation bar / tree panel (optional) --%>
			<tiles:insert attribute="navbar" ignore="true" />

			<%-- Main content (mandatory) --%>
			<tiles:insert attribute="content" />
			
			<%-- Secondary content (e.g. a result set in a search page; optional) --%>
			<tiles:insert attribute="secondContent" ignore="true" />
		</div>

		<%-- Footer area --%>
		<div id="footer">
			<tiles:insert attribute="footer" />
		</div>
	</div>
</body>

</html:html>
