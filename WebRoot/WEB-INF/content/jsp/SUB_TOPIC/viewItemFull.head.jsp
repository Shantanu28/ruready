<%--
###################################################################################
viewItemFull.head.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

jQuery AJAX functions used to populate the set of nested drop-down menus for course,
topic and sub-topic in the edit question page.
###################################################################################
--%>

<%--============================ Definitions and declarations ====================--%>

<%@ page language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html:xhtml />

<%--============================ JSP body begins here ============================--%>

<%-- Plug-ins includes --%>

<%-- ExtJS includes. May later be moved to template.js for all pages in the application. --%>
<script type="text/javascript" src="${contextPath}/include/ext/ext-base.js"></script>
<script type="text/javascript" src="${contextPath}/include/ext/ext-all-debug.js"></script>

<script type="text/JavaScript" src="${contextPath}/js/content/itemTree.js"></script>

<%-- 
============================================================
jQuery commands
============================================================
--%>
<script type="text/JavaScript">
	$(function() 
	{
		form = $("#browseQuestionForm");

		//==================================================================
		// Reset DT link: add & set parameters and submit form 
		//==================================================================
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

	}); // document.ready()
</script>

<%-- 
============================================================
ExtJS commands
============================================================
--%>
<script type="text/JavaScript">
Ext.onReady(function() {
	// Create item tree from options
	var itemTree = ItemTreeUI({
		target_element: 'navbar',
		content_element: 'content',
		initItemId: "${param['itemId']}",
		initParentId: "${param['parentId']}",
		
		//=======================
		// Server action paths
		//=======================
		// populates tree nodes in the panel
		nodeUrl: '<html:rewrite module="/content" page="/ajax/explore/tree.do" />',
	 	// populates the content div upon clicking nodes
		contentUrl: '<html:rewrite module="/content" page="/open/explore/viewContent.do" />',
		
		icons:
		{
			ITEM: '<bean:message key="content.ITEM.iconClass" />',
			DEFAULT_TRASH: '<bean:message key="content.DEFAULT_TRASH.iconClass" />'
		},
		
		messages:
		{
			root: '<bean:message key="content.root.label" />'
		}
	});
	
	// Render item tree component
	itemTree.init();
});
</script>
