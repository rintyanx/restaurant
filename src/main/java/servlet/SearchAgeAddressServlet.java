package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchDAO;
import model.User;

/**
 * Servlet implementation class SearchAgeAddressServlet
 */
@WebServlet("/SearchAgeAddressServlet")
public class SearchAgeAddressServlet extends HttpServlet {
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

		request.setCharacterEncoding("UTF-8");
		//	検索したい年齢と都道府県のパラメータを取得
		String age = request.getParameter("age");
		String address = request.getParameter("address");

		//	入力がなければ、遷移しない
		if(age.isEmpty() && address.isEmpty()) {
			doGet(request,response);
		}else {
			//	データベースから、検索に合ったユーザを追加
			SearchDAO a = new SearchDAO();
			ArrayList<User> userList = a.getUserList(age, address);
			request.setAttribute("matchList", userList);
			//	フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/MatchUser.jsp");
			dispatcher.forward(request, response);

		}

	}

}
