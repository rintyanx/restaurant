<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ page import = "java.util.ArrayList,model.Gnavi,model.User"%>
    <%User user = (User) session.getAttribute("user"); %>
    <%String name = (String) session.getAttribute("name");%>
    <%String freeword = (String) session.getAttribute("freeword");%>
    <%Gnavi gnavi = (Gnavi) session.getAttribute("gnavi");%>
    <% String offset_page = (String) session.getAttribute("offset_page"); %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/result.css">
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/modal.css">
<script src="<%=request.getContextPath() %>/WebContent/js/jquery-3.5.1.js"></script>
<meta charset="UTF-8">
<title>リザルト</title>
</head>
<body>

<script>
$(function(){
	var storeNum ;
	$(".button-link").click(function(){

		storeNum = $(this).val();
		//	キーボード操作などにより、オーバーレイが多重起動するのを防止する
		$( this ).blur() ;	//	ボタンからフォーカスを外す
		if( $( "#modal-overlay" )[0] ) return false ;		//	新しくモーダルウィンドウを起動しない (防止策1)
		//if($("#modal-overlay")[0]) $("#modal-overlay").remove() ;		//	現在のモーダルウィンドウを削除して新しく起動する (防止策2)

		//	オーバーレイを出現させる
		$( "body" ).append( '<div id="modal-overlay"></div>' ) ;
		$( "#modal-overlay" ).fadeIn( "slow" ) ;

		//	コンテンツをセンタリングする
		centeringModalSyncer() ;

		//	コンテンツをフェードインする
		$( "#modal-content" ).fadeIn( "slow" ) ;

		//[#modal-overlay]、または[#modal-close]をクリックしたら…
		$( "#modal-overlay,#modal-close" ).unbind().click( function(){

			//[#modal-content]と[#modal-overlay]をフェードアウトした後に…
			$( "#modal-content,#modal-overlay" ).fadeOut( "slow" , function(){

				//[#modal-overlay]を削除する
				$('#modal-overlay').remove() ;

			} ) ;

		} ) ;

	} ) ;

	//	リサイズされたら、センタリングをする関数[centeringModalSyncer()]を実行する
	$( window ).resize( centeringModalSyncer ) ;

	//	センタリングを実行する関数
	function centeringModalSyncer() {

		//	画面(ウィンドウ)の幅、高さを取得
		var w = $( window ).width() ;
		var h = $( window ).height() ;

		// 	コンテンツ(#modal-content)の幅、高さを取得
		// jQueryのバージョンによっては、引数[{margin:true}]を指定した時、不具合を起こします。
		var cw = $( "#modal-content" ).outerWidth( {margin:true} );
		var ch = $( "#modal-content" ).outerHeight( {margin:true} );
		var cw = $( "#modal-content" ).outerWidth();
		var ch = $( "#modal-content" ).outerHeight();

		//	センタリングを実行する

		$( "#modal-content" ).css( {"left": ((w - cw)/2) + "px","top": ((h - ch)/2) + "px"} ) ;

	}


	<%-- 	リスト登録のajax --%>
	 $('.btn2').on('click',function(){

   	  $.ajax({
             url: "StoreRegisterServlet",
             type: "POST",
             data: {listNum : $(this).val(),
           	  	storeNum :storeNum,
           	  	listName:$('.target').val()}
           }).done(function (result) {
             // 	通信成功時のコールバック
            document.write(result);
           	alert("登録しました。");
           	document.close();
           }).fail(function () {
             // 	通信失敗時のコールバック
             alert("登録失敗\n・店舗登録済み\n・リスト名が重複\n・リスト名が空\nのいずれかが原因です");
           }).always(function (result) {
             //	 常に実行する処理
           });
         });

} ) ;
</script>

<%-- 	ページ番号のajax --%>
<script>
    $(function(){

      //	ボタン押下時の処理
      $('.btn1').on('click',function(){

    	  $.ajax({
              url: "SearchServlet",
              type: "POST",
              data: {offset_page : $(this).val() ,
         	  	 name : '<%=name%>',
         	  	 freeword : '<%=freeword%>'}
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


<div class = "content">

<form method="post" name="form1" action="/ts/LoginServlet">
<a href="javascript:form1.submit()">topへ</a>
</form>

<%if(!name.equals("")){ %>
<h1><%=name %>の検索結果</h1>
<%}%>
<%if(!freeword.equals("")){ %>
<h2>フリーワード：<%=freeword %></h2>
<%} %>
検索ヒット数：<%= gnavi.getTotal_hit_count() %>件<br>
<%if(gnavi.getTotal_hit_count() > 1000){ %>
	上位1000件を表示中<br>
<%} %>
<h2><%= offset_page %>ページ目</h2>
<% for(int i = 0; i < gnavi.getNameList().size();i++){ %>

<a href = <%= gnavi.getUrlList().get(i)%> target = ”_blank”><%= gnavi.getNameList().get(i) %></a>

		<div id="orver">
		<div id="wrap">
		<div id="contents">
		<button id="modal-open" class="button-link" value = "<%= i%>">リスト登録</button><br>
		</div> <!-- contents end -->
	</div> <!-- wrap end -->
</div> <!-- orver end -->

<!--	ここからモーダルウィンドウ -->
<div id="modal-content">
	<div id="modal-content-innar">
	<!--	モーダルウィンドウのコンテンツ開始 -->
<p>登録するリストを選んでください。</p>

	新規リスト：<input type = "text" name = "listName" class = "target" maxlength = 20>
	<button class = "btn2">登録</button><br>

<%for(int j = 0; j < user.getList().size();j++){ %>
<%=user.getList().get(j).getListName()  %><button  value = <%=j %> class = "btn2">登録</button><br>
<%} %>

<p><a id="modal-close" class="button-link">閉じる</a></p>
	</div>
	<!--	 モーダルウィンドウのコンテンツ終了 -->
</div>

<br>

<%} %>

<br>
<div class = "page_num">

<%if(!(Integer.parseInt(offset_page) <= 10)) {%>
<button  value = <%=Integer.parseInt(offset_page) - 10 %> class = "btn1">←10</button>
<%} %>
<%if(gnavi.getTotal_hit_count() % 10 != 0) {%>
	<% for(int i = 1;i <= 10; i++) {%>
		<%if(Integer.parseInt(offset_page) % 10 != 0 ) {%>
			<%if(gnavi.getTotal_hit_count() / 100 == Integer.parseInt(offset_page) / 10 ) {%>
				<%if(i <= 1 + gnavi.getTotal_hit_count() / 10 - (gnavi.getTotal_hit_count() /100 * 10)) {%>
					<button  value = <%=Integer.parseInt(offset_page) / 10 * 10 + i %> class = "btn1"><%=Integer.parseInt(offset_page) / 10 * 10 + i %></button>
				<%} %>
			<%}else{ %>
				<button  value = <%=Integer.parseInt(offset_page) / 10 * 10 + i %> class = "btn1"><%=Integer.parseInt(offset_page) / 10 * 10 + i %></button>
			<%} %>
		<%}else if(Integer.parseInt(offset_page) % 10 == 0){ %>
			<button  value = <%=(Integer.parseInt(offset_page)- 1) / 10 * 10 + i %> class = "btn1"><%=(Integer.parseInt(offset_page)- 1) / 10 * 10 + i%></button>
		<%} %>
	<%} %>
<%if(100 < gnavi.getTotal_hit_count() && Integer.parseInt(offset_page) <= 90 && (Integer.parseInt(offset_page)) <= gnavi.getTotal_hit_count() / 10 - (gnavi.getTotal_hit_count() / 10 % (gnavi.getTotal_hit_count() /100 * 10)) ) {%>
<%if(gnavi.getTotal_hit_count() / 10 - Integer.parseInt(offset_page) < 10){ %>
<button  value = <%= gnavi.getTotal_hit_count() / 10 + 1%> class = "btn1">10→</button>
<%} else{ %>
<button  value = <%=Integer.parseInt(offset_page) + 10 %> class = "btn1">10→</button>
<%} %>
<%} %>
<%} else{%>
	<% for(int i = 1;i <= 10; i++) {%>
		<%if(Integer.parseInt(offset_page) % 10 != 0 ) {%>
			<%if(gnavi.getTotal_hit_count() / 100 == Integer.parseInt(offset_page) / 10 ) {%>
				<%if(i <= gnavi.getTotal_hit_count() / 10 - (gnavi.getTotal_hit_count() /100 * 10)) {%>
					<button  value = <%=Integer.parseInt(offset_page) / 10 * 10 + i %> class = "btn1"><%=Integer.parseInt(offset_page) / 10 * 10 + i %></button>
				<%} %>
			<%}else{ %>
				<button  value = <%=Integer.parseInt(offset_page) / 10 * 10 + i %> class = "btn1"><%=Integer.parseInt(offset_page) / 10 * 10 + i %></button>
			<%} %>
		<%}else if(Integer.parseInt(offset_page) % 10 == 0){ %>
			<button  value = <%=(Integer.parseInt(offset_page)- 1) / 10 * 10 + i %> class = "btn1"><%=(Integer.parseInt(offset_page)- 1) / 10 * 10 + i%></button>
		<%} %>
	<%} %>
<%if(100 < gnavi.getTotal_hit_count() && Integer.parseInt(offset_page) <= 90 && (Integer.parseInt(offset_page)) <= gnavi.getTotal_hit_count() / 10 - (gnavi.getTotal_hit_count() / 10 % (gnavi.getTotal_hit_count() /100 * 10)) ) {%>
<%if(gnavi.getTotal_hit_count() / 10 - Integer.parseInt(offset_page) < 10){ %>
<button  value = <%= gnavi.getTotal_hit_count() / 10%> class = "btn1">10→</button>
<%} else{ %>
<button  value = <%=Integer.parseInt(offset_page) + 10 %> class = "btn1">10→</button>
<%} %>
<%} %>
<%} %>

</div>
</div>
</body>
</html>