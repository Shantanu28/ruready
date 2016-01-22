<%-- Populates the content division of the item-tree.jsp page --%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<p>
	<a href="${contextPath}/js/demo/tree/item-tree.jsp?itemId=${item.parent.id}">Up level</a>
</p>

<h2>${item.name}</h2>
<p>
	${item.comment}<br/>
</p>
