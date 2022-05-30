package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.LoginLogic;
import model.Login;
import model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.invalidate();
		// ウェルカムページ
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータを取得し、Loginインスタンスに格納
		request.setCharacterEncoding("UTF-8");
		String userId=request.getParameter("user_id");
		String pass=request.getParameter("password");
		System.out.println("user_id: " + userId);
		System.out.println("password: " + pass);
		Login login = new Login(userId, pass);

		// ログイン判定
		LoginLogic bo = new LoginLogic();
		User account = bo.execute(login);

		// セッションスコープの取得し、ログインユーザーの情報を格納
		HttpSession session = request.getSession();
		session.removeAttribute("loginMsg");
		session.setAttribute("account", account);

		// ログイン正常時、権限レベル判定
		if (account != null) {
			System.out.println("ログイン成功");
			String nomal = "UserMainServlet";
			String admin = "AdminMainServlet";
			int checkLv = account.getUserLv();

			if (checkLv == 1) {
				System.out.println("ログイン状態: 通常ユーザー");
				response.sendRedirect(nomal);
			} else if (checkLv == 2) {
				System.out.println("ログイン状態: 管理者");
				response.sendRedirect(admin);
			}
		// ログイン失敗時、リダイレクト
		} else {
			String loginMsg = "ログインに失敗しました。<br>ユーザーIDとパスワードの再度入力をお願いします。";
			session.setAttribute("loginMsg", loginMsg);

			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request,response);
			System.out.println("ログイン失敗");
		}

	}
}