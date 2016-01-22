<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html:xhtml />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script type="text/JavaScript" src="${contextPath}/include/jquery/jquery.ifixpng.js"></script>
<script type="text/JavaScript">
	
	$(document).ready(function(){
		// custom image location
		$.ifixpng("${contextPath}/images/common/pixel.gif");
		// setup the toolbar
		$("#toolbar img").ifixpng();
		// setup the form
		$("#knowledgeAssessmentForm :text").attr("autocomplete","off");
		$("#knowledgeAssessmentForm :input:first").focus();		
	});
</script>


