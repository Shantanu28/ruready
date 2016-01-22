<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html:xhtml />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script type="text/JavaScript" src="${contextPath}/include/jquery/jquery.ifixpng.js"></script>
<script type="text/JavaScript">

	function disableSubmitButton() {
		$("#knowledgeAssessmentForm :submit")
			.attr("disabled","disabled")
			.removeClass("done")
			.addClass("disabled");
	}
	
	function enableSubmitButton() {
		$("#knowledgeAssessmentForm :submit")
			.removeAttr("disabled")
			.removeClass("disabled")
			.addClass("done");
	}
	
	$(document).ready(function(){
		// custom image location
		$.ifixpng("${contextPath}/images/common/pixel.gif");
		// setup the toolbar
		$("#toolbar img").ifixpng();
		// highlights an option when its TD is hovered over, and makes the click area the whole cell
		$("#knowledgeAssessmentForm li.option")
		.hover(
			function () {
				$(this).addClass("focus");
			},
			function() {
				$(this).removeClass("focus");
			}
		)
		.click(
			function () {
				$("input", this).click();
			}
		);
		
		// disable the submit button if no option has been checked yet
		if ($("#knowledgeAssessmentForm li.option :checked").length == 0) {
			disableSubmitButton();
		}
		
		// if a radio button is clicked, mark its row as being click and check to see if all questions are answered
		$("#knowledgeAssessmentForm li.option :radio").click(
			function () {
				enableSubmitButton();
			}
		);
	});
</script>
