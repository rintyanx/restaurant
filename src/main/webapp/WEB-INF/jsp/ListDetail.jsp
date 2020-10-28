<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "model.User,java.util.ArrayList,model.Store" %>

<%
   	User user = (User) session.getAttribute("user");
	ArrayList<Store> storeList  = (ArrayList<Store>) session.getAttribute("storeList");
	String listName = (String) session.getAttribute("listName");
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/result.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.js"></script>
<meta charset="UTF-8">
<title> リスト詳細</title>
</head>
<body>
<div class = "content">
<form method="post" name="form1" action="/ts/LoginServlet">
<a href="javascript:form1.submit()">topへ</a>
</form>

<h3><%=listName %> リスト詳細</h3>

<%for(Store store : storeList){ %>
<a href = "<%= store.getUrl()%>" target = ”_blank”><%= store.getName()%></a><br>
<%} %>
</div>
</body>
</html>