package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dao.V_UserDAO;
import model.User;
import model.V_User;

@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");

		// actionタグで削除段階の処理分け
		String action = request.getParameter("action");
		System.out.println("action="+ action);

		// ログインセッションの有無チェック
		User account = (User)session.getAttribute("account");
		if (account == null) {
			response.sendRedirect("/workTimer/LoginServlet");
		} else {
			int checkLv = account.getUserLv();
			if (checkLv == 1) {
				System.out.println("ログイン状態: 通常ユーザー");
				response.sendRedirect("/workTimer/TimerMainServlet");

			} else if (checkLv == 2) {
				System.out.println("ログイン状態: 管理者");

				if (action.equals("DeleteConfirm")) {
					String loginId = request.getParameter("deleteUser");
					System.out.println("削除予定のlogin_id="+ loginId);

					// 削除予定のユーザー情報をログインIDから検索
					V_UserDAO vDao = new V_UserDAO();
					V_User deleteUser = vDao.findVUserByLoginid(loginId);
					session.setAttribute("deleteUser", deleteUser);

					String path = "/WEB-INF/jsp/deleteUser.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);

				} else if (action.equals("Delete")) {
					// 削除予定のユーザーのログインIDを取得し、削除を行う
					String loginid = request.getParameter("loginid");
					UserDAO uDao = new UserDAO();
					uDao.deleteUser(loginid);

					String path = "/WEB-INF/jsp/deleteUserOK.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}
			}
		}
	}

}