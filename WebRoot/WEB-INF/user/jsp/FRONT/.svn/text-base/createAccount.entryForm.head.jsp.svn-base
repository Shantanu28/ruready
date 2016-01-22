<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<c:set var="schoolLookupUrl"><html:rewrite action="/secure/FRONT/schoolSearch"/></c:set>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="pagePrefix" value="${flowKeyPrefix}.entryForm"/>
<script type="text/JavaScript" src="${contextPath}/include/jquery/jquery.autocomplete.js"></script>
<script type="text/JavaScript" src="${contextPath}/js/user/formUtil.js"></script>
<script type="text/JavaScript" src="${contextPath}/js/user/schoolSearch.js"></script>
<script type="text/JavaScript">
	$(document).ready(function(){
		var label = "<bean:message key='${pagePrefix}.field.school.altText'/>";
		setupForm("entryForm");
		setupSchoolAutoComplete("${schoolLookupUrl}", label);
		$(document).submit(
			function(){
				if ($("#schoolId").val().length==0) {
					$("#schoolSearch").val("");
				}
			});
	});
</script>
