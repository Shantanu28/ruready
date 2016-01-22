<%--
###################################################################################
item-tree.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

An example of a tree representing the CMS item hierarchy.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%--============================ Useful definitions ==============================--%>

<%--============================ JSP body begins here ============================--%>

<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=iso-8859-1">
		<title>Content Management TreePanel Example</title>

		<link rel="stylesheet" type="text/css" href="/ru2/include/ext/css/ext-all.css" />
		
		<script type="text/javascript" src="/ru2/include/ext/ext-base.js"></script>
		<script type="text/javascript" src="/ru2/include/ext/ext-all-debug.js"></script>
		<script type="text/javascript" src="item-tree.js"></script>
		
		<link rel="stylesheet" type="text/css" href="/ru2/css/examples.css" />

		<style type="text/css">
.x-tree .x-panel-body {
	background-color: yellow;
	border: 0;
	margin: 0;
}

#tree {
	float: left;
	width: 250px;
	height: 300px;
	overflow: auto;
	border: 0px solid black;
}

#content {
	float: left;
	margin: 0px;
	width: 400px;
	height: 300px;
	overflow: auto;
	border: 0px solid black;
	background: green;
}

.folder .x-tree-node-icon {
	background: transparent
		url(/ru2/include/ext/images/default/tree/folder.gif);
}

.x-tree-node-expanded .x-tree-node-icon {
	background: transparent
		url(/ru2/include/ext/images/default/tree/folder-open.gif);
}

/* Default item icon */
.icon_item {
	background: url(/ru2/images/content/icon_item.png) no-repeat center;
	/*background: red;*/
	width: 22px;
	height: 22px;
}
</style>

<%-- 
============================================================
ExtJS commands
============================================================
--%>
<script type="text/JavaScript">
Ext.onReady(function() {
	// Create item tree from options
	var itemTree = ItemTreeUI({
		target_element: 'tree',
		content_element: 'content',
		initId: "${param['itemId']}",
		
		// Ajax action paths
		nodeUrl: '<html:rewrite module="/demo" page="/open/tree/viewItem.do" />', // populates tree nodes in the panel
		contentUrl: '<html:rewrite module="/demo" page="/open/tree/viewItem.do" />', // populates the content div upon clicking nodes
		
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
	</head>

	<body>
		<h1>
			Content Management TreePanel Example
		</h1>
		<div id="tree"></div>
		<div id="content"></div>
	</body>
</html>
