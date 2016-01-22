<%--
###################################################################################
editItemFull.head.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Acamic Outreach and Continuing Education (AOCE)
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="ajaxForm" value="${editQuestionFullForm.itemForm.subTopicMenuGroupForm}" />
<%--
Prefix of form element names. Because we are inside a nested scope
in editItem.jsp, do -not- prefix this prefix with an additional "editItemFullForm.".
--%>
<c:set var="prefix" value="itemForm.subTopicMenuGroupForm." />

<c:set var="courseMenu"><%=ItemType.COURSE.getCamelCaseName()%>Id</c:set>
<c:set var="topicMenu"><%=ItemType.TOPIC.getCamelCaseName()%>Id</c:set>
<c:set var="subTopicMenu"><%=ItemType.SUB_TOPIC.getCamelCaseName()%>Id</c:set>

<c:set var="conceptTagType"><%=ItemType.CONCEPT%></c:set>
<c:set var="conceptTagName"><bean:message key="content.CONCEPT.label" /></c:set>
<c:set var="conceptTagNames"><bean:message key="content.CONCEPT.labelplural" /></c:set>
<c:set var="conceptTagNameLowerCase">
	<c:out value="${fn:toLowerCase(conceptTagName)}" />
</c:set>

<c:set var="skillTagType"><%=ItemType.SKILL%></c:set>
<c:set var="skillTagName"><bean:message key="content.SKILL.label" /></c:set>
<c:set var="skillTagNames"><bean:message key="content.SKILL.labelplural" /></c:set>
<c:set var="skillTagNameLowerCase">
	<c:out value="${fn:toLowerCase(skillTagName)}" />
</c:set>

<html:xhtml />

<%--============================ JSP body begins here ============================--%>

<%-- ExtJS includes. May later be moved to template.js for all pages in the application. --%>
<script type="text/javascript" src="${contextPath}/include/ext/yui-utilities.js"></script>
<script type="text/javascript" src="${contextPath}/include/ext/ext-yui-adapter.js"></script>
<script type="text/javascript" src="${contextPath}/include/ext/ext-all-debug.js"></script>

<script type="text/JavaScript" src="${contextPath}/js/content/tagGrid.js"></script>

<%-- jQuery Plug-ins --%>
<script type="text/JavaScript" src="${contextPath}/include/jquery/jquery.menugroup.js"></script>

<%-- 
============================================================
jQuery commands
============================================================
--%>
<script type="text/JavaScript">
	$(function() 
	{
		//==================================================================
		// Sub-topic Menu Group Definition
		//==================================================================
		// Your server actions might be organized in one object, e.g. a Struts
		// dispatch action class.
		var base_action = '<html:rewrite module="/content" page="/ajax/populateSubTopicMenuGroup.do" />';
	
		// Pre-population action URL on the server
		var onload_populate_url = base_action;
		// alert('subTopicId = ' + '${ajaxForm.subTopicId}');
		<c:choose>
			<c:when test="${ajaxForm.subTopicIdAsLong > 0}">
				<%--
				Existing form contains sub-topic ID, use it to populate menus above it.
				--%>
				onload_populate_url = onload_populate_url + '?menu=${subTopicMenu}&value=${ajaxForm.subTopicId}';
			</c:when>
			<c:when test="${ajaxForm.topicIdAsLong > 0}">
				<%--
				Existing form contains topic ID, use it to populate menus above it.
				--%>
				onload_populate_url = onload_populate_url + '?menu=${topicMenu}&value=${ajaxForm.topicId}';
			</c:when>
			<c:when test="${ajaxForm.courseIdAsLong > 0}">
				<%--
				Existing form contains course ID, use it to populate menus above it.
				--%>
				onload_populate_url = onload_populate_url + '?menu=${courseMenu}&value=${ajaxForm.courseId}';
			</c:when>
		</c:choose>
		
		//alert('Initial Action: ' + onload_populate_url);
	
		// Menu on-change event actions
		var actions = new Array();
		actions['onload']          = { populate_url: onload_populate_url };
		actions['${courseMenu}']   = { populate_url: base_action + "?menu=${courseMenu}" };
		actions['${topicMenu}']    = { populate_url: base_action + "?menu=${topicMenu}" };
		actions['${subTopicMenu}'] = { }; 
	
		// Activate the menu group
		options = { name_prefix: '${prefix}' };
		$(this).menugroup(actions, options);

		//==================================================================
		// Click Event Handler for "Add New Topic" Button
		//==================================================================

		$("#addTopic").click(function()
		{ 
			// Edit question form
			var form = $("#editQuestionFullForm");
			// Get topic selection
        	var selectedParentId = $("#${courseMenu}_select option:selected").eq(0).attr('value');
			if ((selectedParentId == null) || (selectedParentId == ""))
			{
				// alert("invalid");
				$("#addTopicMessage").addClass("error").text("<bean:message key='question.editQuestion.selectCourse' />").show().fadeOut(2000);
				return false;
			}
			else
			{
				// alert("valid");
				var url = "<html:rewrite module='/content' page='/open/editItemFull/QUESTION/addTopic.do'/>";
				var action = url
				+ "?action_addTopic=true&validate=true&itemType=QUESTION&itemId=${itemId}"
				+ "&itemForm.courseId=" + selectedParentId;
      			form.attr("action", action);
				form.submit();      		
			}
    	});

		//==================================================================
		// Click Event Handler for "Add New Sub-Topic" Button
		//==================================================================

		$("#addSubTopic").click(function()
		{ 
			// Edit question form
			var form = $("#editQuestionFullForm");
			// Get topic selection
        	var selectedParentId = $("#${topicMenu}_select option:selected").eq(0).attr('value');
			if ((selectedParentId == null) || (selectedParentId == ""))
			{
				// alert("invalid");
				$("#addSubTopicMessage").addClass("error").text("<bean:message key='question.editQuestion.selectTopic' />").show().fadeOut(2000);
				return false;
			}
			else
			{
				// alert("valid");
				var url = "<html:rewrite module='/content' page='/open/editItemFull/QUESTION/addSubTopic.do'/>";
				var action = url
				+ "?action_addSubTopic=true&validate=true&itemType=QUESTION&itemId=${itemId}"
				+ "&itemForm.topicId=" + selectedParentId;
      			form.attr("action", action);
				form.submit();      		
			}
    	});
	}); // document.ready()

<%-- 
============================================================
ExtJS commands
============================================================
--%>
Ext.onReady(function() {
	// Enable quick tips
	Ext.QuickTips.init();

	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	
	// Concept editing grid
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	

	var conceptGrid = TagGridUI({
		// Pre-population action URL
		url:'/ru2/content/open/tagGrid.do?itemId=${editQuestionFullForm.itemForm.conceptIds}&tagType=${conceptTagType}',
		
		// ID and properties of a division containing the editor grid 
		grid_element: 'conceptGrid',
		height: 200,
		width: 300,
		
		// ID of input element to be populated and updated with this grid's contents 
		form_data_element: 'conceptData',
		
		// A bundle of text messages to be shown for various actions
		messages:
		{
			// Title of the grid panel
			title: '<bean:message key="content.tagGrid.title" arg0="${conceptTagNames}" />',

			// Name of items on the list displayed by the grid
			item: '${conceptTagName}',

			// Message to show while AJAX actions are running
			loadingText: '<bean:message key="app.action.loading" />',
			
			// Message to show while waiting for the server to respond
			waitingText: '<bean:message key="app.action.wait" />',
			
			// Button labels and tool-tips
			buttons:
			{
				add: 
				{
					label: '<bean:message key="content.tagGrid.add.label" arg0="${conceptTagName}" />',
					tooltip: '<bean:message key="content.tagGrid.add.tooltip" arg0="${conceptTagNameLowerCase}" />'
				},

				remove: 
				{
					label: '<bean:message key="content.tagGrid.remove.label" arg0="${conceptTagName}" />',
					tooltip: '<bean:message key="content.tagGrid.remove.tooltip" arg0="${conceptTagNameLowerCase}" />'
				},
				
				newItem: // Don't use the string new, might be a keyword that confuses IE 
				{
					label: '<bean:message key="content.tagGrid.new.label" arg0="${conceptTagName}" />',
					tooltip: '<bean:message key="content.tagGrid.new.tooltip" arg0="${conceptTagNameLowerCase}" />',
					prompt: '<bean:message key="content.tagGrid.new.prompt" arg0="${conceptTagNameLowerCase}" />',
					success: '<bean:message key="content.tagGrid.new.success" arg0="${conceptTagNameLowerCase}" />',
					invalid: '<bean:message key="content.tagGrid.new.invalid" arg0="${conceptTagNameLowerCase}" />',
					accessDenied: '<bean:message key="content.tagGrid.new.accessDenied" arg0="${conceptTagNameLowerCase}" />',
					exists: '<bean:message key="content.tagGrid.new.exists" arg0="${conceptTagNameLowerCase}" />',
					failure: '<bean:message key="content.tagGrid.new.failure" arg0="${conceptTagNameLowerCase}" />'
				}
			}
		}
	});
	
	// Render Tag Grid UI component
	conceptGrid.init();

	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	
	// Skill editing grid
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	

	var skillGrid = TagGridUI({
		// Pre-population action URL
		url:'/ru2/content/open/tagGrid.do?itemId=${editQuestionFullForm.itemForm.skillIds}&tagType=${skillTagType}',
		
		// ID and properties of a division containing the editor grid 
		grid_element: 'skillGrid',
		height: 200,
		width: 300,
		
		// ID of input element to be populated and updated with this grid's contents 
		form_data_element: 'skillData',
		
		// A bundle of text messages to be shown for various actions
		messages:
		{
			// Title of the grid panel
			title: '<bean:message key="content.tagGrid.title" arg0="${skillTagNames}" />',
			
			// Name of items on the list displayed by the grid
			item: '${skillTagName}',

			// Message to show while AJAX actions are running
			loadingText: '<bean:message key="app.action.loading" />',
			
			// Message to show while waiting for the server to respond
			waitingText: '<bean:message key="app.action.wait" />',
			
			// Button labels and tool-tips
			buttons:
			{
				add: 
				{
					label: '<bean:message key="content.tagGrid.add.label" arg0="${skillTagName}" />',
					tooltip: '<bean:message key="content.tagGrid.add.tooltip" arg0="${skillTagNameLowerCase}" />'
				},

				remove: 
				{
					label: '<bean:message key="content.tagGrid.remove.label" arg0="${skillTagName}" />',
					tooltip: '<bean:message key="content.tagGrid.remove.tooltip" arg0="${skillTagNameLowerCase}" />'
				},
				
				newItem: // Don't use the string new, might be a keyword that confuses IE 
				{
					label: '<bean:message key="content.tagGrid.new.label" arg0="${skillTagName}" />',
					tooltip: '<bean:message key="content.tagGrid.new.tooltip" arg0="${skillTagNameLowerCase}" />',
					prompt: '<bean:message key="content.tagGrid.new.prompt" arg0="${skillTagNameLowerCase}" />',
					success: '<bean:message key="content.tagGrid.new.success" arg0="${skillTagNameLowerCase}" />',
					invalid: '<bean:message key="content.tagGrid.new.invalid" arg0="${skillTagNameLowerCase}" />',
					accessDenied: '<bean:message key="content.tagGrid.new.accessDenied" arg0="${skillTagNameLowerCase}" />',
					exists: '<bean:message key="content.tagGrid.new.exists" arg0="${skillTagNameLowerCase}" />',
					failure: '<bean:message key="content.tagGrid.new.failure" arg0="${skillTagNameLowerCase}" />'
				}
			}
		}
	});
	
	// Render Tag Grid UI component
	skillGrid.init();
});
</script>
