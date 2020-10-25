<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.js"></script>
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/style.css">
<title>リスト検索</title>
</head>
<body>
<div class = "content">
<form method="post" name="form1" action="/ts/LoginServlet">
<a href="javascript:form1.submit()">topへ</a>
</form>

<h1>リスト検索</h1>


ユーザ名からリストを探す<br>
<form action = "/ts/SearchIDServlet" method = "post">
<input type = "text" name = "userID" maxlength = 6 pattern = "^[0-9A-Za-z]+$" required>
<button type = "submit">検索</button>
</form>
<br>

年代・都道府県からユーザを探す<br>
<form action = "/ts/SearchAgeAddressServlet" method = "post">
年齢：
<select name="age">
<option value="">選択しない</option>
<option value="10">１０代</option>
<option value="20">２０代</option>
<option value="30">３０代</option>
<option value="40">４０代</option>
<option value="50">５０代</option>
<option value="60">６０代</option>
<option value="70">７０代</option>
<option value="80">８０代</option>
<option value="90">９０代</option>
<option value="100">１００代</option>
</select>

都道府県：
<select name="address">
<option value="">選択しない</option>
<option value="北海道">北海道</option>
<option value="青森県">青森県</option>
<option value="岩手県">岩手県</option>
<option value="宮城県">宮城県</option>
<option value="秋田県">秋田県</option>
<option value="山形県">山形県</option>
<option value="福島県">福島県</option>
<option value="茨城県">茨城県</option>
<option value="栃木県">栃木県</option>

<option value="群馬県">群馬県</option>
<option value="埼玉県">埼玉県</option>
<option value="千葉県">千葉県</option>
<option value="東京都">東京都</option>
<option value="神奈川県">神奈川県</option>
<option value="新潟県">新潟県</option>
<option value="富山県">富山県</option>
<option value="石川県">石川県</option>
<option value="福井県">福井県</option>
<option value="山梨県">山梨県</option>
<option value="長野県">長野県</option>

<option value="岐阜県">岐阜県</option>
<option value="静岡県">静岡県</option>
<option value="愛知県">愛知県</option>
<option value="三重県">三重県</option>
<option value="滋賀県">滋賀県</option>
<option value="京都府">京都府</option>
<option value="大阪府">大阪府</option>
<option value="兵庫県">兵庫県</option>
<option value="奈良県">奈良県</option>

<option value="和歌山県">和歌山県</option>
<option value="鳥取県">鳥取県</option>
<option value="島根県">島根県</option>
<option value="岡山県">岡山県</option>
<option value="広島県">広島県</option>
<option value="山口県">山口県</option>
<option value="徳島県">徳島県</option>
<option value="香川県">香川県</option>
<option value="愛媛県">愛媛県</option>

<option value="高知県">高知県</option>
<option value="福岡県">福岡県</option>
<option value="佐賀県">佐賀県</option>
<option value="長崎県">長崎県</option>
<option value="熊本県">熊本県</option>
<option value="大分県">大分県</option>
<option value="宮崎県">宮崎県</option>
<option value="鹿児島県">鹿児島県</option>
<option value="沖縄県">沖縄県</option>
</select>
<button type = "submit">検索</button>
</form>
</div>
</body>
</html>