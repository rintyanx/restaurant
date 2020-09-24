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
 * Servlet implementation class SearchIDServlet
 */
@WebServlet("/SearchIDServlet")
public class SearchIDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	セッションスコープを準備
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		//	指定したユーザIDのパラメータを取得し、ユーザを生成
		String searchID = request.getParameter("userID");
		User searchUser = new User(searchID);

		//	データベースから、検索ユーザの作成しているリストを持ってくる
		ListDAO b = new  ListDAO();
		searchUser.setList(b.getStoreList(searchUser));
		session.setAttribute("searchUser", searchUser);

		//	フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserList.jsp");
		dispatcher.forward(request, response);

	}

}

