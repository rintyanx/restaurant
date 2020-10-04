<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "model.User,java.util.ArrayList" %>

<%
	ArrayList<User> matchList  = (ArrayList<User>) request.getAttribute("matchList");
%>

<!DOCTYPE html>
<html>
<head>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.js"></script>
<meta charset="UTF-8">
<title>ユーザ検索結果</title>
</head>
<body>

<form method="post" name="form1" action="/ts/LoginServlet">
<a href="javascript:form1.submit()">topへ</a>
</form>

<h3>絞り込み結果</h3>
<% for(User user : matchList) {%>
<form action = "/ts/SearchIDServlet" method = "post">
<%= user.getId()%>
<button type = "submit" name = "userID" value = "<%= user.getId()%>">リスト一覧</button>
<%} %>
</form>
</body>
</html>