<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "model.User,java.util.ArrayList" %>

<%
   	User user = (User) session.getAttribute("user");
	String error = (String)request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/style.css">
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/button.css">
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/hidden_box.css">
<meta charset="UTF-8">
<title>レストランルーム</title>
</head>
<body>
<h1>レストランルーム</h1>


<div class = "content">
ようこそ、<%= user.getId() %>	<br>

<div id = "main_button">
<button onclick="location.href='ListServlet'">リスト一覧</button>
<!-- <button onclick="location.href='ReviseUserServlet'">ユーザ設定</button> -->
<button onclick="location.href='SearchListServlet'">リスト検索</button>
<button onclick="location.href='LogoutServlet'">ログアウト</button><br>
</div>
</div>
<form action="/ts/SearchServlet" method = "post">

<div class = "content">
<div class = "error">
<%if(error != null) {%>
<%= error %><br>
<%} %>
</div>
店舗名検索<input type = "text" name = "name"><br>
フリーワード（全角スペース区切り）<input type = "text" name = "freeword">

<!-- AND検索<input type = "radio" name = "select" value = "freeword_condition=1" checked>

OR検索<input type = "radio" name = "select" value = "freeword_condition=2">
 -->
<input type="hidden" name="offset_page" value =1>
<input type="hidden" name="flag" value =1>
<button type = "submit">検索</button><br>

</div>

</form>



</body>
</html>