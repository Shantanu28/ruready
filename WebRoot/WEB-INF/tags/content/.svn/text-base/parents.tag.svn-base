<%--
###################################################################################
parents.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print the list of parents of a CMS item.
This is not a complete HTML page, just a snippet.
###################################################################################
--%>

<%@ tag display-name="Prints the parent hierarchy of a catalog item"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- resource key (instance variable name) of the item --%>
<%@ attribute name="item" required="true"%>
<%-- Add browsing links to parents or not --%>
<%@ attribute name="addLinks" required="false" rtexprvalue="false" %>
<%-- Parent separator character --%>
<%@ attribute name="separator" required="false" rtexprvalue="false" %>

<%-- ============ Tag output variables ============ --%>

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

<%-- ============ Tag code begins here ============ --%>

<%-- Set default values --%>
<c:if test="${empty addLinks}">
	<c:set var="addLinks" scope="page" value="true" />
</c:if>

<c:if test="${empty separator}">
	<c:set var="separator" scope="page" value="&gt;" />
</c:if>

<bean:size name="${item}" property="parents" scope="request" id="size" />

<logic:iterate id="parent" name="${item}" property="parents"
	scope="request" indexId="index" type="net.ruready.business.content.item.entity.Item">
	
	<c:set var="parentsTagItemId" scope="page">
		<bean:write name="parent" property="id" scope="page" />
	</c:set>
	<c:set var="parentsTagItemType" scope="page">
		<bean:write name="parent" property="type" />
	</c:set>
	<logic:notEqual value="${size-1}" name="index" scope="page">
		<logic:equal name="addLinks" value="true" scope="page">
			<html:link module="/content"
				page="/open/explore/viewFull.do?itemId=${parentsTagItemId}&itemType=${parentsTagItemType}">
				<bean:write name="parent" property="name" scope="page" />
			</html:link>
		</logic:equal>
		<logic:notEqual name="addLinks" value="true" scope="page">
			<bean:write name="parent" property="name" scope="page" />
		</logic:notEqual>
		${separator}
	</logic:notEqual>
	<logic:equal value="${size-1}" name="index" scope="page">
		<bean:write name="parent" property="name" scope="page" />
	</logic:equal>
</logic:iterate>
