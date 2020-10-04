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
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/hidden_box.css">
<meta charset="UTF-8">
<title>レストランルーム</title>
</head>
<body>
<h1>レストランルーム</h1>



ようこそ、<%= user.getId() %>	<br>
<a href = "ListServlet">リスト一覧</a>
<a href = "ReviseUserServlet">ユーザ設定</a>
<a href = "SearchListServlet">リスト検索</a>
<a href = "LogoutServlet">ログアウト</a><br>

<form action="/ts/SearchServlet" method = "post">

<div class = "content">
<div class = "error">
<%if(error != null) {%>
<%= error %><br>
<%} %>
</div>
店舗名検索：<input type = "text" name = "name"><br>
フリーワード（全角スペース区切り）：<input type = "text" name = "freeword">
AND検索<input type = "radio" name = "select" value = "freeword_condition=1" checked>
OR検索<input type = "radio" name = "select" value = "freeword_condition=2">
<input type="hidden" name="offset_page" value =1>
<input type="hidden" name="flag" value =1>
<button type = "submit">検索</button><br>

</div>
<div class="disp-box">
            <label for="disp-btn">詳細設定</label>
            <input type="checkbox" id="disp-btn">
            <div class="text">
                ランチ営業あり：<input type="checkbox" name= "select" value = "lunch=1">
                禁煙席あり：<input type="checkbox" name= "select" value = "no_smoking=1">
                カード利用可：<input type="checkbox" name= "select" value = "card=1"><br>

                携帯の電波が入る：<input type="checkbox" name="select" value = "mobilephone=1">
                飲み放題あり：<input type="checkbox" name="select" value = "bottomless_cup=1">
               日曜営業あり：<input type="checkbox" name="select" value = "sunday_open=1"><br>

                テイクアウトあり：<input type="checkbox" name= "select" value = "takeout=1">
                個室あり：<input type="checkbox" name= "select" value = "private_room=1">
              深夜営業あり：<input type="checkbox" name= "select" value = "midnight=1"><br>

             駐車場あり：<input type="checkbox" name="select" value = "parking=1">
               法事利用可：<input type="checkbox" name="select" value = "memorial_service=1">
              誕生日特典あり：<input type="checkbox" name="select" value = "birthday_privilege=1"><br>

                 結納利用可：<input type="checkbox" name= "select" value = "betrothal_present=1">
                キッズメニューあり：<input type="checkbox" name= "select" value = "kids_menu=1">
              電源あり：<input type="checkbox" name= "select" value = "outret=1"><br>

       wifiあり：<input type="checkbox" name="select" value = "wifi=1">
                マイクあり：<input type="checkbox" name="select" value = "microphone=1">
               食べ放題あり：<input type="checkbox" name="select" value = "buffet=1"><br>

       14時以降のランチあり：<input type="checkbox" name= "select" value = "late_lunch=1">
                スポーツ観戦可：<input type="checkbox" name= "select" value = "sports=1">
             朝まで営業あり：<input type="checkbox" name= "select" value = "until_morning=1"><br>

              ランチデザートあり：<input type="checkbox" name="select" value = "lunch_desert=1">
                プロジェクター・スクリーンあり：<input type="checkbox" name="select" value = "projecter_screen=1">
             ペット同伴可：<input type="checkbox" name="select" value = "with_pet=1"><br>

                デリバリーあり：<input type="checkbox" name= "select" value = "deliverly=1">
               土日特別ランチあり：<input type="checkbox" name= "select" value = "special_holiday_lunch=1">
              電子マネー利用可：<input type="checkbox" name= "select" value = "e_money=1"><br>

            ケータリングあり：<input type="checkbox" name="select" value = "caterling=1">
              モーニング・朝ごはんあり：<input type="checkbox" name="select" value = "breakfast=1">
              デザートビュッフェあり：<input type="checkbox" name="select" value = "desert_buffet=1"><br>

                ランチビュッフェあり：<input type="checkbox" name= "select" value = "lunch_buffet=1">
               お弁当あり：<input type="checkbox" name= "select" value = "bento=1">
             ランチサラダバーあり：<input type="checkbox" name= "select" value = "lunch_salad_buffet=1"><br>

   	    ダーツあり：<input type="checkbox" name="select" value = "darts=1">
       Web予約可：<input type="checkbox" name="select" value = "web_reserve=1"><br>
            </div>
        </div>
</form>



</body>
</html>