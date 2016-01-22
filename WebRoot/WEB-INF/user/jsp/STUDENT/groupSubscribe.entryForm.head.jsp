<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html:xhtml />
<script type="text/JavaScript" src="${contextPath}/js/user/formUtil.js"></script>
<script type="text/JavaScript">
	$(document).ready(function(){
		setupForm("entryForm");
	});
</script>