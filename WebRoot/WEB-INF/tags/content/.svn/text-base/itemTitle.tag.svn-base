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

Displays a title section for item view/edit pages.

This is not a complete HTML page, just a snippet.
###################################################################################
--%>

<%@ tag display-name="Prints a title section for a catalog item"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Reference to (name of) item we are printing --%>
<%@ attribute name="item" required="true" %>
<%-- Type of view: browse or edit --%>
<%@ attribute name="view" required="true" %>
<%-- Scope of item bean --%>
<%@ attribute name="scope" required="false" %>
<%-- Flag for showing parent hierarchy --%>
<%@ attribute name="showParents" required="false" %>

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

<%--============================ Useful definitions ==============================--%>

<%-- Set default values --%>
<c:if test="${empty scope}">
	<c:set var="scope" scope="page" value="page" />
</c:if>

<c:if test="${empty showParents}">
	<c:set var="showParents" scope="page" value="true" />
</c:if>

<c:set var="itemType" scope="page">
	<bean:write name="${item}" property="type" scope="${scope}" />
</c:set>

<%-- ============ Tag code begins here ============ --%>

<%--
==================================================================
Item's Title & parent hierarchy
==================================================================
--%>

<table border="0" width="100%" class="noborder" cellspacing="1">
<tbody>
	<%--
	List item's parents so that we can go up levels by clicking them
	--%>
	<tr>
		<%-- Link to editing children if the item is not read-only --%>	
		<td valign="center" align="left">
			<c:choose>
				<c:when test="${showParents}">
					<content:parents item="${item}" />
				</c:when>
				<c:otherwise>
					&nbsp;
				</c:otherwise>
			</c:choose>
		</td>
	</tr>

	<tr>
		<td class="cellnoborder" width="90%">
			<table border="0" class="noborder">
			<tbody>
				<tr>
					<td align="left">
						<%-- Item's icon --%>
						<common:message key="content.${itemType}.imageClass" 
						altKey="content.ITEM.imageClass" 
						id="imageClass"/>
						<div class="image ${imageClass}"/>
						&nbsp;
					</td>
		
					<%-- Item's title --%>
					<td align="left">					
						<c:choose>
							<c:when test="${view == 'edit'}">
								<i>
								<h1>
									<bean:write name="${item}" property="name" scope="${scope}" />
								</h1>
								<logic:equal name="${item}" scope="${scope}" property="readOnly" value="true">
									<h1>
										(<bean:message key="content.editItemFull.readonly.label" />)
									</h1>
								</logic:equal>
								</i>
							</c:when>
							<c:otherwise>
								<h1>
									<bean:write name="${item}" property="name" scope="${scope}" />
								</h1>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</tbody>
			</table>
		</td>
	</tr>	
</tbody>
</table>
