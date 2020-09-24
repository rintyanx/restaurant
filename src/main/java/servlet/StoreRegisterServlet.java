package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ListDAO;
import dao.RegisterDAO;
import model.Gnavi;
import model.User;

/**
 * Servlet implementation class StoreRegisterServlet
 */
@WebServlet("/StoreRegisterServlet")
public class StoreRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Ajax通信での呼び出し
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	セッションスコープを準備
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		//	セッションスコープから、ユーザとぐるなびの店舗群を取得
		User user = (User) session.getAttribute("user");
		Gnavi gnavi = (Gnavi) session.getAttribute("gnavi");
		//	登録したい店舗のリスト番号をパラメータから取得
		int storeNum = Integer.parseInt(request.getParameter("storeNum"));

		try {
			//	登録したいリストのリスト番号をパラメータから取得
			int listNum =  Integer.parseInt(request.getParameter("listNum"));

			//	データベースに、店舗を登録
			RegisterDAO a = new RegisterDAO();
			//	登録できた場合
			if(a.registerStore(user, gnavi, listNum, storeNum)) {
				//	検索結果画面に移動
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				dispatcher.forward(request, response);
			}

		}catch(NumberFormatException e) {	//	新規リストを作成して登録する場合は、このエラーが出る

			//	作成したリストの名前をパラメータから取得
			String listName = request.getParameter("listName");
			//	リスト名が空の場合
			if(listName.isEmpty()) {
				//	エラーを出して、登録失敗させる
				Integer.parseInt(request.getParameter("listNum"));

			}else {	//	リスト名が入力されている場合
				//	データベースにリストを登録
				ListDAO a = new  ListDAO();
				if(a.create(user,listName)) {	//	作成できれば、StoreListを更新する
					user.setList(a.getStoreList(user));
					//	データベースに店舗を登録
					RegisterDAO b = new RegisterDAO();
					b.registerStore(user, gnavi,user.getListNum(listName), storeNum);
					//	フォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
					dispatcher.forward(request, response);
				}
			}
		}
	}

}
