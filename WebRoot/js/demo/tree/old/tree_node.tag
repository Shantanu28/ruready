<%--
###################################################################################
<tree> tag

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

This tag prints a tree object hierarchy to a JSP. Default parameters (indentation,
icons, etc.) of the tag and possible overrides are read from the application
resource file. This tag uses the <tree_node> tag and the Struts nested tags
to print an entire tree.

The tree children collection must be a list, not a set or other collection types.
Otherwise, iterating through the children will produce references to objects like
"children[0].some_property" and that will throw an exception if "children" is not
a list.
###################################################################################
--%>
<%@ tag display-name="Prints a tree" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ruready.net/common" prefix="common" %>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- resource key (instance variable name) of the tree object we are printing --%>
<%@ attribute name="resource" required="true" %>
<%@ attribute name="bean" required="true" %>

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

<%-- ============ Tag code begins here ============ --%>
<%-- Our scope for nesting operations is the current tree node passed in
implicitly from the calling page/tag. --%>
<nested:root>
	<%--  Useful EL aliases --%>
	<c:set var="rootObject" scope="page">
		<nested:writeNesting/>
	</c:set>
	<c:set var="contextImages" scope="page">
		<nested:message key="context.images" />
	</c:set>
	<c:set var="prefixPath">
		<common:path/>
	</c:set>	
	<c:set var="nodeId" scope="page">
		<nested:write property="id" />
	</c:set>
	
	<%-- Compute indentation space --%>
	<c:set var="width_scale">
		<nested:message key="${resource}.indent" />
	</c:set>
	<c:set var="width_prop">
		<nested:write property="indent" />
	</c:set>
	<c:set var="width" value="${width_scale*width_prop}" />
	
	<html:img module="" page="${contextImages}/tree/spacer.gif"
	style="height:20;width:${width};align:absmiddle;border:0" />

	<%--
	================================
	Node is in expanded state
	================================
	--%>
	<nested:equal property="toggle.expanded" value="true">

		<%-- If node has children, draw the expanded node sign --%>
		<nested:equal property="hasChildren" value="true">
			<nested:image property="method" value="toggle"
			onclick="document.${bean}.method.value = 'toggle';
			document.${bean}.nodeId.value = '${nodeId}';
			document.${bean}.submit(); return false;"
			page="${contextImages}/tree/expanded.png" style="align:absmiddle" />
		</nested:equal>

		<nested:notEqual property="hasChildren" value="true">
			<html:img module="" page="${contextImages}/tree/spacer.gif"
			style="height:10;width:11;align:absmiddle" />
		</nested:notEqual>

		<%-- Display node icon. If no customized image found in the
		application resources, use default images. --%>
		<util:resource id="nodeIcon">
			<jsp:attribute name="base">
				util.tree.icon.expanded
			</jsp:attribute>
			<jsp:attribute name="override">
				${resource}.<nested:write property="data.class.name" />.icon.expanded
			</jsp:attribute>
		</util:resource>

		<%-- Display node icon (if clicked, will toggle) --%>
		<nested:image property="method" value="toggle"
		onclick="document.${bean}.method.value = 'toggle';
		document.${bean}.nodeId.value = '${nodeId}';
		document.${bean}.submit(); return false;"
		page="${contextImages}${nodeIcon}" style="align:absmiddle" />

		<%-- Display node editing button --%>
		<nested:image property="method" value="edit"
		onclick="document.${bean}.method.value = 'edit';
		document.${bean}.nodeId.value = '${nodeId}';
		document.${bean}.submit(); return false;"
		page="${contextImages}/tree/red_circle.png"
		style="align:absmiddle;border:0" />
				
		<%-- Print the node data --%>
		<nested:write property="data.name" /><br>

		<%-- Recursion: print the children nodes under this node --%>
		<nested:iterate property="children">
			<common:tree_node resource="${resource}" bean="${bean}" />
		</nested:iterate>
	
	</nested:equal>
	
	<%--
	================================
	Node is in collapsed state
	================================
	--%>
	<nested:equal property="toggle.expanded" value="false">

		<%-- If node has children, draw the collapsed node sign --%>
		<nested:equal property="hasChildren" value="true">
			<nested:image property="method" value="toggle"
			onclick="document.${bean}.method.value = 'toggle';
			document.${bean}.nodeId.value = '${nodeId}';
			document.${bean}.submit(); return false;"
			page="${contextImages}/tree/collapsed.png"
			style="align:absmiddle" />
		</nested:equal>
		<nested:notEqual property="hasChildren" value="true">
			<html:img module="" page="${contextImages}/tree/spacer.gif"
			style="height:10;width:11;align:absmiddle" />
		</nested:notEqual>
		
		<%-- Display node icon. If no customized image found in the
		application resources, use default images. --%>
		<common:resource id="nodeIcon">
			<jsp:attribute name="base">
				util.tree.icon.collapsed
			</jsp:attribute>
			<jsp:attribute name="override">
				${resource}.<nested:write property="data.class.name" />.icon.collapsed
			</jsp:attribute>
		</util:resource>

		<%-- Display node editing button --%>
		<nested:image property="method" value="toggle"
		onclick="document.${bean}.method.value = 'toggle';
		document.${bean}.nodeId.value = '${nodeId}';
		document.${bean}.submit(); return false;"
		page="${contextImages}${nodeIcon}"
		style="align:absmiddle;border:0" />

		<%-- Display node editing button --%>
		<nested:image property="method" value="edit"
		onclick="document.${bean}.method.value = 'edit';
		document.${bean}.nodeId.value = '${nodeId}';
		document.${bean}.submit(); return false;"
		page="${contextImages}/tree/red_circle.png"
		style="align:absmiddle;border:0" />
		
		<nested:write property="data.name" /><br>
	
	</nested:equal>

</nested:root>
