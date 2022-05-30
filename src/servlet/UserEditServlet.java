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
import dao.V_UserDAO;
import model.User;
import model.V_User;

@WebServlet("/UserEditServlet")
public class UserEditServlet extends HttpServlet {
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
				String path = "/WEB-INF/jsp/editUser.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}
		}

	}

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

				if (action.equals("EditInput")) {
				String loginId = request.getParameter("editUser");
				System.out.println("編集予定のlogin_id="+ loginId);

				// 編集予定のユーザー情報をログインIDから検索
				V_UserDAO vDao = new V_UserDAO();
				V_User editUser = vDao.findVUserByLoginid(loginId);
				session.setAttribute("editUser", editUser);

				String path = "/WEB-INF/jsp/editUser.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

				} else if (action.equals("EditConfirm")) {
					// 編集予定のユーザーのログインIDを取得し、編集を行う
					String loginid = request.getParameter("login_id");
					String name = request.getParameter("user_name");
					String pass = request.getParameter("password");
					int userlv = Integer.parseInt(request.getParameter("userlv"));
					int enrollmentlv = Integer.parseInt(request.getParameter("enrollment"));
					System.out.println("編集予定のlogin_id:"+ loginid);
					System.out.println("編集予定のuser_name:"+ name);
					System.out.println("編集予定のpassword:"+ pass);
					System.out.println("編集予定のuserlv:"+ userlv);
					System.out.println("編集予定のenrollment:"+ enrollmentlv);

					User edituser = new User(name, loginid, pass, userlv, enrollmentlv);
					session.setAttribute("edituser", edituser);

					// 入力エラー、重複チェック
					UserLogic ac = new UserLogic();
					String errorMsg = ac.execute(edituser, "edit");
					System.out.println("errorMsg= "+ errorMsg);
					session.setAttribute("errorMsg", errorMsg);

					if (errorMsg.length() <= 0) {
						String path = "/WEB-INF/jsp/editUserConfirm.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(path);
						dispatcher.forward(request, response);
					} else {
						response.sendRedirect("/workTimer/UserEditServlet");
					}

				} else if (action.equals("Edit"))  {

					User eUser = (User)session.getAttribute("edituser");
					UserDAO uDao = new UserDAO();
					boolean editResult = uDao.editUser(eUser);
					System.out.println(editResult);

					if (editResult) {
						session.removeAttribute("edituser");
						session.removeAttribute("errorMsg");
						// 登録完了
						String path = "/WEB-INF/jsp/editUserOK.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(path);
						dispatcher.forward(request, response);
					}
				}
			}
		}
	}

}