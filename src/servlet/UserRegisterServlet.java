package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.UserLogic;
import dao.UserDAO;
import model.User;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// ログインセッションの有無チェック
		User account = (User)session.getAttribute("account");
		if (account == null) {
			response.sendRedirect("/workTimer/LoginServlet");
		} else {
			// ログインユーザーの権限チェック
			int checkLv = account.getUserLv();
			if (checkLv == 1) {
				System.out.println("ログイン状態: 通常ユーザー");
				response.sendRedirect("/workTimer/TimerMainServlet");

			} else if (checkLv == 2) {
				System.out.println("ログイン状態: 管理者");
				String path = "/WEB-INF/jsp/registerUser.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		// セッションスコープの取得
		HttpSession session = request.getSession();

		// actionによる処理分け
		String action = request.getParameter("action");
		System.out.println("action="+ action);
		if(action.equals("Confirm")) {

			String loginid = request.getParameter("login_id");
			String pass = request.getParameter("password");
			String name = request.getParameter("user_name");
			int userlv = Integer.parseInt(request.getParameter("admin"));

			// Userインスタンス
			User user = new User(loginid, pass, name, userlv);

			// セッションスコープに保存
			session.setAttribute("user", user);

			// 入力エラー、重複チェック
			UserLogic uc = new UserLogic();
			String errorMsg = uc.execute(user, "register");
			System.out.println("errorMsg= "+ errorMsg);
			session.setAttribute("errorMsg", errorMsg);

			if (errorMsg.length() <= 0) {
				String path = "/WEB-INF/jsp/registerUserConfirm.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			// エラー発生時はメッセージを伴い入力画面へリダイレクト
			} else {
				response.sendRedirect("/workTimer/UserRegisterServlet");
			}
		} else if (action.equals("Register")) {
			// 登録
			User user = (User)session.getAttribute("user");
			UserDAO uDao = new UserDAO();
			boolean result = uDao.registerUser(user);

			if (result) {
				session.removeAttribute("user");
				session.removeAttribute("errorMsg");
				// 登録完了
				String path = "/WEB-INF/jsp/registerUserOK.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}
		}
	}

}