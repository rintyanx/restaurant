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
import dao.LoginDAO;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	ログイン画面を表示させる
		RequestDispatcher  dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	セッションスコープを準備
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		//	セッションスコープにユーザが追加されていなければ（ログイン時）
		if(session.getAttribute("user") == null) {
			//	IDとパスワードのパラメータを取得し、ユーザを生成
			String id = request.getParameter("id");
			String pass = request.getParameter("pass");
			User user = new User(id,pass);

			//	データベースからID・PASSが一致するユーザが存在するか確認する
			LoginDAO a = new LoginDAO();
			if(a.login(user)) {	//	ユーザが存在する場合はメイン画面へ
				//	ログインするユーザの作成しているリストをデータベースから持ってくる
				ListDAO b = new  ListDAO();
				user.setList(b.getStoreList(user));
				//	セッションスコープにユーザー情報を追加
				session.setAttribute("user", user);
				//	フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Main.jsp");
				dispatcher.forward(request, response);

			}else {	//	ログイン失敗時
				request.setAttribute("error", "IDかパスワードが間違っています。");
				doGet(request,response);
			}

		}else {	//	ログイン済みの場合は、メイン画面に移動
			if(session.getAttribute("select") != null) {
				session.removeAttribute("select");
			}
			//	フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Main.jsp");
			dispatcher.forward(request, response);

		}


	}

}

