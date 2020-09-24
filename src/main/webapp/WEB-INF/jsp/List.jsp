<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "model.User,java.util.ArrayList,model.StoreList" %>

<%
   	User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/hidden_button.css">
<link rel="stylesheet" href = "<%=request.getContextPath() %>/WebContent/css/modal.css">
<script src="<%=request.getContextPath() %>/WebContent/js/jquery-3.5.1.js"></script>
<meta charset="UTF-8">
<title>レストランルーム</title>
</head>
<body>

<script>
$(function(){
	var listNum ;
	$(".button-link").click(function(){

		listNum = $(this).val();
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
             url: "ListDeleteServlet",
             type: "POST",
             data: {listNum : listNum}
           }).done(function (result) {
             // 	通信成功時のコールバック
            document.write(result);
           	alert("リストを削除しました。");
           	document.close();
           }).fail(function () {
             // 	通信失敗時のコールバック
             alert("読み込み失敗");
           }).always(function (result) {
             //	 常に実行する処理
           });
         });

} ) ;
</script>



<!-- 	リスト詳細のajax -->
<script>
    $(function(){

      //	ボタン押下時の処理
      $('.detail').on('click',function(){

    	  $.ajax({
              url: "ListDetailServlet",
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


<form method="post" name="fm1" action="/ts/LoginServlet">
<a href="javascript:fm1.submit()">topへ</a>
</form>

<form action = "/ts/ListServlet" method = "post">
	<input type = "text" name = "listName">
	<button type = "submit">リスト新規作成</button>
</form>
<h3>リスト一覧</h3>

<div class="disp-box">

<label for="disp-btn">リスト削除</label><br><br>
<input type="checkbox" id="disp-btn">
<%for(int i = 0; i < user.getList().size();i++){ %>

<%=user.getList().get(i).getListName() %>
<button  value = <%=user.getList().get(i).getListID() %> class = "detail">リスト詳細</button>

<div class="text">

<div id="orver">
	<div id="wrap">
		<div id="contents">

<button id="modal-open" class = "button-link" value = <%=i %> >削除</button>

		</div> <!-- contents end -->
	</div> <!-- wrap end -->
</div> <!-- orver end -->

<!--	ここからモーダルウィンドウ -->
<div id="modal-content">
	<div id="modal-content-innar">
	<!--	モーダルウィンドウのコンテンツ開始 -->
<p>本当に リストを削除しますか？</p>

<button  class = "btn2">はい</button>

<button id="modal-close" class="button-link">いいえ</button>
	</div>
	<!--	 モーダルウィンドウのコンテンツ終了 -->
</div>

</div>
<br>
<%} %>

</div>

</body>
</html>