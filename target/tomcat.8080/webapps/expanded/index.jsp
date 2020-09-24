<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/style.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.js"></script>
<meta charset="UTF-8">
<title>レストランルーム</title>
</head>
<body>


<h1>レストランルームへようこそ</h1>


<div class = "content">
<form method="get" action="/ts/RegisterServlet">

<button type = "submit">新規登録</button>

</form>

<form method="get" action="/ts/LoginServlet">
<button type = "submit">ログイン</button>
</form>
</div>

</body>
</html>