<%--
###################################################################################
itemTitle.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

A custom item toolbar for topics. Doesn't display an edit button but has a button
for adding a new question.

This is not a complete HTML page, just a snippet.
###################################################################################
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%--============================ Useful definitions ==============================--%>

<%--
Uncomment this section to turn on XHTML validation of this file.
Because this is a JSP snippet, make sure to comment it back out
before running it on the application container.
--%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />

<c:set var="itemId" scope="request">
	<bean:write name="item" scope="request" property="id" />
</c:set>

<c:set var="itemType" scope="page">
	<bean:write name="item" property="type" />
</c:set>

<%-- =========================== JSP code begins here ============================--%>

<%--
==================================================================
Useful buttons (a "functional toolbar")
==================================================================
--%>
<form>
<table class="noborder" cellspacing="1">
<tbody>
	<tr>
		<%-- Reset button: goes to the root node --%>
		<td>
			<input type="button"
			value="<bean:message key='content.itemToolbar.root.label' />"
			onclick="window.location='<html:rewrite module='/content' page='/open/home.do'/>'"
			class="reset" />
			&nbsp;
		</td>
		
		<%-- Go up one level in the tree hierarchy --%>
		<td>
			<content:buttonUp item="item" />
			&nbsp;
		</td>

		<%-- No Edit button --%>
		
		<%-- Move/transfer action button --%>
		<td>
			<input type="button"
				value="<bean:message key='content.itemToolbar.transferItem.label' />"
				onclick="window.location='<html:rewrite module='/content' page='/open/transferItem.do?itemSourceId=${itemId}'/>'"
				class="move">
			&nbsp;
		</td>
		
		
		<%-- Restore item --%>
		<td>
			<input type="button"
				value="<bean:message key='content.itemToolbar.restore.label' />"
				onclick="window.location='<html:rewrite module='/content' page='/open/explore/viewFull.do?itemType=DEFAULT_TRASH'/>'"
				class="cancel">
			&nbsp;
		</td>
		
		<%-- Display add question button --%>	
		<td>
			<input type="button"
				value='<bean:message key="question.editQuestion.action.add" />'
				onclick="window.location='<html:rewrite module='/content'
				page='/open/editItemFull/QUESTION.do?itemType=QUESTION&customForward=parentView&parentId=${itemId}'/>'"				 
				class="add">
			&nbsp;
		</td>

	</tr>

</tbody>
</table>
</form>
