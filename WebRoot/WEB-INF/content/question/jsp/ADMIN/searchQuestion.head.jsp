
<%--
###################################################################################
searchQuestion.head.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

jQuery AJAX functions used to populate the set of nested drop-down menus for course, topic
and sub-topic in the edit question page.
###################################################################################
--%>

<%--============================ Definitions and declarations ====================--%>

<%@ page language="java"%>
<%@ page import="net.ruready.business.content.item.entity.ItemType"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="ajaxForm" value="${searchQuestionForm.subTopicMenuGroupForm}" />
<%--
Prefix of form element names (fully qualified, no nested scope here).
--%>
<c:set var="prefix" value="subTopicMenuGroupForm." />

<c:set var="subjectMenu"><%=ItemType.SUBJECT.getCamelCaseName()%>Id</c:set>
<c:set var="courseMenu"><%=ItemType.COURSE.getCamelCaseName()%>Id</c:set>
<c:set var="topicMenu"><%=ItemType.TOPIC.getCamelCaseName()%>Id</c:set>
<c:set var="subTopicMenu"><%=ItemType.SUB_TOPIC.getCamelCaseName()%>Id</c:set>

<html:xhtml />

<%--============================ JSP body begins here ============================--%>

<%-- Plug-ins includes --%>
<script type="text/JavaScript"
	src="${contextPath}/include/jquery/jquery.menugroup.js"></script>

<script type="text/JavaScript">
	$(function() 
	{
		//==================================================================
		// Reset DT link: add & set parameters and submit form 
		//==================================================================
		var form = $("#searchQuestionForm");
		$("#resetDT").click(function()
		{
			// Add the proper action parameter
	        $('<input type="hidden"></input>')
			.attr('name', 'action_setup_resetDT')
			.val('true')
			.appendTo(form);

			// Clear display tag parameter values
			form.find("input[@name='d-16544-p']").val('');
			form.find("input[@name='d-16544-s']").val('');
			form.find("input[@name='d-16544-o']").val('');
			
			form.submit();
			return false;
    	});

		//==================================================================
		// Sub-topic Menu Group Definition
		//==================================================================
	
		// Prepare pre-population action URL query strings
		var onload_populate_queryString = '';
		var onload_browse_queryString = '';
		<c:choose>
			<c:when test="${ajaxForm.subTopicIdAsLong > 0}">
				onload_populate_queryString = '?menu=${subTopicMenu}&value=${ajaxForm.subTopicId}';
				onload_browse_value = '${ajaxForm.subTopicId}';
			</c:when>
			<c:when test="${ajaxForm.topicIdAsLong > 0}">
				<%--
				Existing form contains topic ID, use it to populate menus above it.
				--%>
				onload_populate_queryString = '?menu=${topicMenu}&value=${ajaxForm.topicId}';
				onload_browse_value = '${ajaxForm.topicId}';
			</c:when>
			<c:when test="${ajaxForm.courseIdAsLong > 0}">
				<%--
				Existing form contains course ID, use it to populate menus above it.
				--%>
				onload_populate_queryString = '?menu=${courseMenu}&value=${ajaxForm.courseId}';
				onload_browse_value = '${ajaxForm.courseId}';
			</c:when>
			<c:when test="${ajaxForm.subjectIdAsLong > 0}">
				<%--
				Existing form contains subject ID, use it to populate menus above it.
				--%>
				onload_populate_queryString = '?menu=${subjectMenu}&value=${ajaxForm.subjectId}';
				onload_browse_value = '${ajaxForm.subjectId}';
			</c:when>
		</c:choose>

		// Your server actions might be organized in one object, e.g. a Struts
		// dispatch action class.
		var base_populate_url = '<html:rewrite module="/content" page="/ajax/populateQuestionRealmMenuGroup.do" />';
		var base_browse_url = '<html:rewrite module="/content" page="/ajax/populateBrowseTable.do" />';

		//=========================================
		// Auxiliary Menu on-change event actions
		//=========================================
	
		//==================================================================
		// populate_browse_table_handler()
		// Populates the browse table with question counts.
		//
		// @param xml - XML output of the action
		// @param actions - list of actions for select on-change events
		// @param options - options struct
		//==================================================================
		function populate_browse_table_handler(xml) 
		{
			// alert('browse_table_handler() BEGIN');

			// Populate parent item name <span> element
			var parent_name = $(xml).find('name').eq(0).text();
			$('#browse_item').html(parent_name);		

			// Populate mean + standard deviation <span> element
			var mean_text = $(xml).find('mean').text();
			var std_text = $(xml).find('std').text();
			// Format using Javascript number formatting.
			// @see http://www.pageresource.com/jscript/j_a_03.htm
			var mean_value = parseFloat(mean_text).toFixed(1);
			var std_value = parseFloat(std_text).toFixed(1);
			$('#browse_mean_std').html('('+ mean_value + ' &plusmn; ' + std_value + ')');		

			// Loop over select groups in the XML document and populate
			// <count> element data into browse table cells
			$(xml).find('count').each(function()
			{
				// Find the <span> corresponding to this <count> element	
				var level_text = $(this).find('level').text();
				var type_text = $(this).find('type').text();
				var span_id = 'browse_' + type_text + '_' + level_text;
				var span = '#' + span_id;
				
				// Fill in the <span>'s text
				var value_text = $(this).find('value').text();
				$(span).html(value_text);
			}); // each of find('select')

		}; //browse_table_handler()

		//==================================================================
		// populate_browse_table_handler()
		// Populates the browse table with question counts - AJAX call.
		//==================================================================
		populate_browse_table = function(value)
		{ 
			var populate_url = base_browse_url;
			var value_query = '?value=' + value;
			// alert('populate_browse_table(): calling ' + populate_url + value_query); 
	 		$.ajax({
		    	type: "POST",
				url: populate_url + value_query,
				dataType: "xml",
				success: function(xml) 
				{
					populate_browse_table_handler(xml);
				} // success: function
			}); // $.ajax({
		};
	
		//=========================================
		// Main Menu on-change event actions
		//=========================================
		// Browse table is updated upon onload, subject onchange and course onchange.
		var actions = new Array();
		actions['onload']          = { populate_url: base_populate_url + onload_populate_queryString };
		actions['${subjectMenu}']  = { populate_url: base_populate_url + "?menu=${subjectMenu}" , onchange: populate_browse_table};
		actions['${courseMenu}']   = { populate_url: base_populate_url + "?menu=${courseMenu}", onchange: populate_browse_table};
		actions['${topicMenu}']    = { populate_url: base_populate_url + "?menu=${topicMenu}", onchange: populate_browse_table};
		actions['${subTopicMenu}'] = { onchange: populate_browse_table }; 
		
		//=========================================
		// Activate the question realm menu group
		//=========================================
		options = { name_prefix: '${prefix}' , debug: false};
		$(this).menugroup(actions, options);
		
		// Invoke other menu-related pre-population actions:
		// - Browse table pre-population
		populate_browse_table(onload_browse_value);
		
	}); // document.ready()
</script>
