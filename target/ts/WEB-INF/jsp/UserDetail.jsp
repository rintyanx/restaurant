<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "model.User" %>

<%
   	User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/hidden_button.css">
<meta charset="UTF-8">
<title>ユーザ情報</title>
</head>
<body>
<h2>ユーザ情報</h2>




<div class="disp-box">


<label for="disp-btn">ユーザ情報変更</label><br><br>
<input type="checkbox" id="disp-btn">

ID:<%=user.getId() %>

<div class="text">
新規ID：<input type = "text" name = "ID">
<button>変更</button>
</div>

<br>

パスワード:表示されません
<div class="text">
２回入力してください<br>
新規パスワード：<input type = "password" name = "PASS" ><br>
新規パスワード：<input type = "password" name = "PASS">
<button>変更</button>
</div>

<br>

年齢：
<%if(user.getAge() == 0) {%>
設定されていません
<%}else{ %>
<%=user.getAge() %>代
<%} %>
<div class="text">
<select name="age">
<option value="">変更しない</option>
<option value="NONSELECT">選択しない</option>
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
<button>変更</button>
</div>
<br>

都道府県：
<%if(user.getAddress() == null) {%>
設定されていません
<%}else{ %>
<%=user.getAddress() %>
<%} %>
<div class="text">
<select name="address">
<option value="">変更しない</option>
<option value="NONSELECT">選択しない</option>
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
<button>変更</button>
</div>
<br>


</div>


</body>
</html>