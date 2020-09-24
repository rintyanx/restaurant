package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RegisterDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	ユーザ登録画面に移動する
		RequestDispatcher  dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Register.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		//	登録したいユーザ情報のパラメータを取得
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String age = request.getParameter("age");
		String address = request.getParameter("address");

		//	データベースにアクセスし、ユーザを登録する
		RegisterDAO a = new RegisterDAO();
		if(a.registerUser(id,pass,age,address)) {	//	登録できた場合は、ログイン画面に移動
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
			dispatcher.forward(request, response);
		}else {	//	登録できなかった場合は。doGetを呼び出し、登録画面に戻す
			doGet(request,response);
		}
	}

}
