<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script type="text/JavaScript" src="${contextPath}/js/user/formUtil.js"></script>
<script type="text/JavaScript">
	$(document).ready(function(){
		setupForm("entryForm");
		setupDefaultSubmit("entryForm");
	});
</script>
