<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/style.css">
<title>レストランルーム</title>
</head>
<body>

<div class = "content">

<h2>ログイン画面</h2>
<p>ログインするユーザのIDとパスワードを入力してください</p>

<form action="/ts/LoginServlet" method = "post">
<p>
ユーザーID：<input type = "text" name = "id"><br>
パスワード：<input type = "password" name = "pass"><br>
</p>
<p><button type="submit">ログイン</button></p>
</form>
</div>
</body>
</html>