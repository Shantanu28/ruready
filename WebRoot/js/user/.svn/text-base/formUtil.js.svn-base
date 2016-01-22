function setupForm(formId) {
	$("#" + formId + " :text").attr("autocomplete","off");
	$("#" + formId + " :input:first").focus();
}

function setupDefaultSubmit(formId) {
	$("#" + formId).keydown(
		function(e) {
			if (e.keyCode == 13) {
				$("#" + formId + " :input.default").click();
				e.preventDefault();
			}				
		}
	);
}