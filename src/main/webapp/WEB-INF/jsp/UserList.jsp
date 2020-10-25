<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ page import = "model.User,java.util.ArrayList,model.StoreList" %>

<%
   	User searchUser = (User) session.getAttribute("searchUser");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/style.css">
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/result.css">
<script src="<%=request.getContextPath() %>/WebContent/js/jquery-3.5.1.js"></script>
<meta charset="UTF-8">
<title>リスト検索結果</title>
</head>
<body>
<div class = "content">
<form method="post" name="form1" action="/ts/LoginServlet">
<a href="javascript:form1.submit()">topへ</a>
</form>

<script>
    $(function(){

      //	ボタン押下時の処理
      $('.detail').on('click',function(){

    	  $.ajax({
              url: "UserListDetailServlet",
              type: "POST",
              data: {listID : $(this).val()}
            }).done(function (result) {
              // 	通信成功時のコールバック
            	document.write(result);
            	document.close();
            }).fail(function () {
              // 	通信失敗時のコールバック
              alert("読み込み失敗");
            }).always(function (result) {
              //	 常に実行する処理
            });
          });
    });
</script>

<h3><%=searchUser.getId() %>さんのリスト一覧</h3>

<%for(StoreList list : searchUser.getList()){ %>

<%=list.getListName() %>
<button  value = <%=list.getListID() %> class = "detail">リスト詳細</button><br>
<%} %>
</div>
</body>
</html>