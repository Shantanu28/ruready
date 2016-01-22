<%--
###################################################################################
authentication.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education, University of Utah.
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

This is a dummy JSP that we forward to if the user requested a page that requires
him/her to authenticate. This -redirects- to the main page; the requested URL
(book-mark) is saved as a session token before this redirect.
###################################################################################
--%>

<%--========================== Tag Library Declarations ==========================--%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

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

<%--============================ JSP body begins here ============================--%>

<%--
Note: direct only works if this is not a tile but a full tile definition.
@see http://www.nabble.com/problem-with-logic-redirect-tag-and-tiles-t33410.html
--%>
<logic:redirect forward="userHome" />
