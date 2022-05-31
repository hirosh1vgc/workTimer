package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DefaultDAO;
import dao.V_TimerDAO;
import dao.V_UserDAO;
import model.User;
import model.V_Timer;
import model.V_User;
import model.WorkDefault;

@WebServlet("/AdminMainServlet")
public class AdminMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		session.removeAttribute("scMsg");

		// ログインセッションの有無チェック
		User account = (User)session.getAttribute("account");
		if (account == null) {
			response.sendRedirect("/workTimer/LoginServlet");
		} else {
			int checkLv = account.getUserLv();
			if (checkLv == 1) {
				System.out.println("ログイン状態: 通常ユーザー");
				response.sendRedirect("/workTimer/UserMainServlet");

			} else if (checkLv == 2) {

				// 登録済の全ユーザー情報の取得
				V_UserDAO vDao = new V_UserDAO();
				V_TimerDAO tDao  = new V_TimerDAO();

				List<V_User> alluser = vDao.findAllUser();
				session.setAttribute("alluser", alluser);

				// ログインユーザーIDから勤務記録が存在する月を検索
				int user_id = account.getUserId();
				List <V_Timer> vmonths = tDao.findMonthByUserid(user_id);
				session.setAttribute("vmonths", vmonths);

				// 通常の勤務時間と休憩時間を取得
				DefaultDAO dDao = new DefaultDAO();
				WorkDefault wd = dDao.findWorkDefault();
				session.setAttribute("wd", wd);

				System.out.println("ログイン状態: 管理者");
				String path = "/WEB-INF/jsp/adminMain.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}
		}
	}
}