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

Displays a toolbar with several buttons for item actions.

This is not a complete HTML page, just a snippet.

Note: the page calling this tag must include the javascript script formUtil.js.
###################################################################################
--%>

<%@ tag display-name="Prints an item action toolbar"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Reference to (name of) item we are printing --%>
<%@ attribute name="item" required="true"%>
<%-- Scope of item bean --%>
<%@ attribute name="scope" required="false"%>
<%-- Show edit button or not --%>
<%@ attribute name="edit" required="false"%>
<%-- Show edit button or not --%>
<%@ attribute name="addquestion" required="false"%>

<%-- ============ Tag output variables ============ --%>

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

<%-- Set default values --%>
<c:if test="${empty scope}">
	<c:set var="scope" scope="page" value="page" />
</c:if>

<c:set var="itemId" scope="request">
	<bean:write name="${item}" property="id" scope="${scope}" />
</c:set>

<c:set var="itemType" scope="page">
	<bean:write name="${item}" property="type" scope="${scope}" />
</c:set>

<c:if test="${empty edit}">
	<c:set var="edit" scope="page" value="true" />
</c:if>

<c:if test="${empty addquestion}">
	<c:set var="addquestion" scope="page" value="false" />
</c:if>

<%-- ============ Tag code begins here ============ --%>

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
<content:buttonUp item="${item}" />
&nbsp;
</td>

<%-- Display edit button if edit flag is set --%>
<logic:equal name="edit" value="true" scope="page">
	<td>
	<%-- The relative path to the editItem JSP in the current directory --%>
	<content:customType prefix="WEB-INF/content/jsp" type="${itemType}"
		postfix="editItem.jsp" id="customPath" />

	<input type="button"
		value="<bean:message key='app.action.edit' />"
		onclick="window.location='<html:rewrite module='/content' page='/open/editItemFull${customPath}.do?itemId=${itemId}&itemType=${itemType}'/>'"
		class="edit" />

				&nbsp;
			</td>
</logic:equal>

<td>
<input type="button"
	value="<bean:message key='content.itemToolbar.transferItem.label' />"
	onclick="window.location='<html:rewrite module='/content' page='/open/transferItem.do?itemSourceId=${itemId}'/>'"
	class="move" />
&nbsp;
</td>


<td>
<input type="button"
	value="<bean:message key='content.itemToolbar.restore.label' />"
	onclick="window.location='<html:rewrite module='/content' page='/open/explore/viewFull.do?itemType=DEFAULT_TRASH'/>'"
	class="cancel" />
&nbsp;
</td>

<%-- Display add question button if flag is set --%>
<logic:equal name="addquestion" value="true" scope="page">
	<td>
	<input type="button"
		value='<bean:message key="question.editQuestion.action.add" />'
		onclick="window.location='<html:rewrite module='/content'
		page='/open/editItemFull/QUESTION.do?itemType=QUESTION&customForward=parentView&parentId=${itemId}'/>'				 
		class="add" />
				&nbsp;
			</td>
</logic:equal>

</tr>
</tbody>
</table>
</form>
