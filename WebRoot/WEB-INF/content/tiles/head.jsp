<%--
###################################################################################
head.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

A generic content management header file. Includes the ExtJS library.
###################################################################################
--%>

<%--============================ Definitions and declarations ====================--%>

<%@ page language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html:xhtml />

<%--============================ JSP body begins here ============================--%>
<%-- ExtJS includes. May later be moved to template.js for all pages in the application. --%>
<script type="text/javascript"
	src="${contextPath}/include/ext/yui-utilities.js"></script>
<script type="text/javascript"
	src="${contextPath}/include/ext/ext-yui-adapter.js"></script>
<script type="text/javascript"
	src="${contextPath}/include/ext/ext-base.js"></script>
<script type="text/javascript"
	src="${contextPath}/include/ext/ext-all-debug.js"></script>
