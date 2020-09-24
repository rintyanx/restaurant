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
 * Servlet implementation class ListDeleteServlet
 */
@WebServlet("/ListDeleteServlet")
public class ListDeleteServlet extends HttpServlet {
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

		//	セッションスコープの準備
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");

		//	削除したいリストの番号をパラメータから取得
		int listNum =  Integer.parseInt(request.getParameter("listNum"));
		//	セッションスコープからユーザ情報を取得
		User user = (User) session.getAttribute("user");
		//	データベースから該当リストを削除
		ListDAO a = new ListDAO();
		//	削除がうまくいった場合はフォワードする
		if(a.delete(user, listNum)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/List.jsp");
			dispatcher.forward(request, response);
		}else {	//	削除できなかった場合は、doGetを呼び出し
			doGet(request,response);
		}
	}

}
