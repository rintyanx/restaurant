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
import model.User;

/**
 * Servlet implementation class ListServlet
 */
@WebServlet("/ListServlet")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	セッションスコープを準備
		HttpSession session = request.getSession();
		//	セッションスコープからユーザ情報を取得
		User user = (User) session.getAttribute("user");
		//	ユーザの作成しているリストをユーザ情報に追加
		ListDAO a = new  ListDAO();
		user.setList(a.getStoreList(user));
		//	セッションスコープにユーザー情報を追加
		session = request.getSession();
		session.setAttribute("user", user);

		//	リスト一覧画面を表示させる
		RequestDispatcher  dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/List.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		セッションスコープを準備
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
//		セッションスコープからユーザ情報と登録したいリスト名を取得
		User user = (User) session.getAttribute("user");
		String listName = request.getParameter("listName");

		//	データベースにリストを登録する
		ListDAO a = new  ListDAO();
		if(a.create(user,listName)) {	//	作成できれば、StoreListを更新する
			user.setList(a.getStoreList(user));

			//	セッションスコープにユーザー情報を追加
			session = request.getSession();
			session.setAttribute("user", user);

			//	フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/List.jsp");
			dispatcher.forward(request, response);

		}else {	//	作成できなければ、doGetを呼び出す
			doGet(request,response);
		}

	}

}
