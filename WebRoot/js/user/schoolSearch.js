function findValue(li) {
	if (li.selectValue.length > 0) {
		// set the hidden fields
		$("#schoolId").val(li.extra[0]);
		$("#schoolName").val(li.selectValue);
		hideSchoolEntry();
		$("#entryForm :submit.default")[0].focus();
	}
}
	
function showSchoolEntry(label) {
	$("#schoolId").val("");
	$("#schoolName").val("");
	$("#schoolSearch").val("");
	$("#schoolSearchMatch").hide();
	$("#schoolSearch").show();
	if (label.length > 0) {
		$("#entryForm span.schoolSearch").append('<input type="submit" name="_eventId_schoolRequest" value="' + label + '" class="edit schoolRequest" />');
	}
}

function hideSchoolEntry() {
	if ($("#schoolId").val().length > 0) {
		$("#schoolSearchMatch em").text($("#schoolName").val());
		$("#schoolSearch").hide();
		$("#schoolSearchMatch").show();
		$("#entryForm input.schoolRequest").remove();
	}
}

function selectItem(li) {
	findValue(li);
}

function formatItem(row) {
	return row[0];
}

function setupSchoolAutoComplete(lookupUrl, label) {
	if ($("#schoolId").val().length > 0) {
		hideSchoolEntry();
	}
	else {
		showSchoolEntry(label);
	}
	$("#schoolSearch").autocomplete(
		lookupUrl,
		{
			delay:10,
			minChars:2,
			matchSubset:1,
			onItemSelect:selectItem,
			onFindValue:findValue,
			formatItem:formatItem,
			maxItemsToShow:10,
			autoFill:false,
			mustMatch:0
		});		
		hideSchoolEntry();
		$("#schoolSearchMatch a").click( 
			function() {
				showSchoolEntry(label);
				return false;
			});
}
