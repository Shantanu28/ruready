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

<input type="hidden" name="method" />
<input type="hidden" name="nodeId" />
<common:tree_node resource="${resource}" bean="${bean}" />
