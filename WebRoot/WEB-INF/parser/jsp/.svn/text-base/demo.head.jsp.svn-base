<%--
###################################################################################
demo.head.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

jQuery AJAX functions used in the parser demo page.
###################################################################################
--%>

<%--============================ Definitions and declarations ====================--%>

<%@ page language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el"%>

<html:xhtml />

<%--============================ JSP body begins here ============================--%>

<%-- Plug-ins includes --%>

<script type="text/JavaScript">
	$(function() 
	{
		form = $("#demoForm");

		<%-- For EE format submissions: convert EE input to MathML content before submission --%>
		function convertToMathML(myform)
		{
			var value = $("#referenceEE").get(0).getPackedContentMathML();
			myform.find("input[@name='referenceString']").val(value);
			var value2 = $("#responseEE").get(0).getPackedContentMathML();
			myform.find("input[@name='responseString']").val(value2);
		};

		//==================================================================
		// Event Handler: changing the format selection (radio-button)
		//==================================================================
		form.find("input[@name='format']").change(function()
		{
			if ($(this).val() == 'TEXT')
			{
				// The user wants to switch from EE to text, convert input expressions
				convertToMathML(form);
			}
			
			// Add the proper action parameter and submit form
	        $('<input type="hidden"></input>')
			.attr('name', 'action_setup_updateFormat')
			.val('true')
			.appendTo(form);

			form.submit();
    	});


		//==================================================================
		// Event Handler: clicking the "Analyze" button
		//==================================================================
		form.find("input[@name='action_analyze']").click(function()
		{
			if (form.find("input[@name='format']").val() == 'EQUATION_EDITOR')
			{
				convertToMathML(form);
			}
			
			// Add the proper action parameter and submit form
	        $('<input type="hidden"></input>')
			.attr('name', 'action_analyze')
			.val('true')
			.appendTo(form);

			form.submit();
    	});

	}); // document.ready()
</script>
