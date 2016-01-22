<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html:xhtml />
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script type="text/JavaScript" src="${contextPath}/include/jquery/jquery.ifixpng.js"></script>
<script type="text/JavaScript">

	function disableSubmitButton() {
		$("#expectationAssessmentForm :submit")
			.attr("disabled","disabled")
			.removeClass("done")
			.addClass("disabled");
	}
	
	function enableSubmitButton() {
		$("#expectationAssessmentForm :submit")
			.removeAttr("disabled")
			.removeClass("disabled")
			.addClass("done");
	}
	
	function markAsChecked(element) {
		element.parent().parent()
			.addClass("selected")
			.find("td.statement em.selected")
				.ifixpng();
	}
	
	function markAllInitiallyClicked() {
		$("#expectationAssessmentForm tbody td :checked").each(
			function() {
				markAsChecked($(this));
			}
		);
	}
	
	function checkAllClicked() {
		// if all rows have been clicked, enable the submit button
		if ($("#expectationAssessmentForm tbody tr.selected").length ==
			$("#expectationAssessmentForm tbody tr").length) {
			enableSubmitButton();
		}
	}
	
	$(document).ready(function(){
		// custom image location
		$.ifixpng("${contextPath}/images/common/pixel.gif");
		// setup the toolbar
		$("#toolbar img").ifixpng();
		// highlights an option when its TD is hovered over, and makes the click area the whole cell
		$("#expectationAssessmentForm td.option")
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
		
		disableSubmitButton();
		markAllInitiallyClicked();
		checkAllClicked();
		
		// if a radio button is clicked, mark its row as being click and check to see if all questions are answered
		$("#expectationAssessmentForm :radio").click(
			function () {
				markAsChecked($(this));				
				// check to see if all have been clicked yet
				checkAllClicked();
			}
		);
	});
</script>