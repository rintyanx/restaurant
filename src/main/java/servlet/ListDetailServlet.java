package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ListDAO;
import model.Store;
import model.User;

/**
 * Servlet implementation class ListDetail
 */
@WebServlet("/ListDetailServlet")
public class ListDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	セッションスコープを準備
		HttpSession session = request.getSession();
		//	セッションスコープからユーザ情報を取得
		User user = (User) session.getAttribute("user");
		//	リストＩDのパラメータを取得
		String listID = request.getParameter("listID");
		//	データベースからリスト内に登録している店舗を取得
		ListDAO b = new ListDAO();
		ArrayList<Store> list = b.detail(user, listID);

		//	セッションスコープにリスト名と店舗のリストを追加
		session.setAttribute("listName", b.getListName(user, listID));
		session.setAttribute("storeList", list);

		//	結果画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ListDetail.jsp");
		dispatcher.forward(request, response);

	}

}
