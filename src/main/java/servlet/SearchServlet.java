package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.APICatch;
import model.Gnavi;
import model.SplitFreeword;



/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;




	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	メイン画面に移動
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Main.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	セッションスコープの準備
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");

		//	検索店舗名を受け取る
		String name = request.getParameter("name");
		//	フリーワードを受け取る
		String freeword = request.getParameter("freeword");

		//	使用できる文字列に変更する
		SplitFreeword b = new SplitFreeword();
		freeword = b.split(freeword);

		//	検索開始ページを受けとる
		String offset_page = request.getParameter("offset_page");

		//	絞り込み内容を受け取る配列を用意
		String[] select = null;

		//	検索ワードが空白の場合のエラー
		if(name.isEmpty() && freeword.isEmpty()) {
			request.setAttribute("error","検索ワードを入力してください。");
			doGet(request, response);
		}

		//	セッションスコープに絞り込み内容がない場合は
		if(session.getAttribute("select") == null || request.getParameter("flag") != null) {
			select = request.getParameterValues("select");//絞り込み内容のパラメータを取得

		}else {//	セッションスコープに絞り込み内容がある場合は、
			//	セッションスコープから同一の内容を取得
			select = (String[]) session.getAttribute("select");
		}

		//	外部APIと接続し、jsonからデータを取得
		APICatch a = new APICatch(name,freeword,offset_page,select);
		Gnavi gnavi = a.getResult();
		if(gnavi != null) {

			//	ぐるなびAPIから持ってきた情報
			session.setAttribute("gnavi", gnavi);
			//	検索店舗名
			session.setAttribute("name", name);
			//	フリーワード内容
			session.setAttribute("freeword", freeword);
			//	絞り込み内容
			session.setAttribute("select", select);
			//	ページ番号
			session.setAttribute("offset_page", offset_page);
			//	結果画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}else {
			boolean flag = true;
			session.setAttribute("flag", flag);
			doGet(request, response);
		}

	}

}
