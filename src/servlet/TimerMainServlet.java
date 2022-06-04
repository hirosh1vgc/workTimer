package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.DetailLogic;
import bo.LengthLogic;
import dao.DefaultDAO;
import dao.TimerDAO;
import dao.V_UserDAO;
import model.User;
import model.V_User;
import model.WorkDefault;
import model.WorkStart;
import model.WorkStop;

@WebServlet("/TimerMainServlet")
public class TimerMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// ログインセッションの有無チェック
		User account = (User)session.getAttribute("account");

		if (account == null) {
			response.sendRedirect("/workTimer/LoginServlet");
		} else {
			// ログインIDから取得したユーザー情報をセッションスコープに格納
			V_UserDAO vDao = new V_UserDAO();
			V_User logindata = vDao.findVUserByLoginid(account.getLoginId());
			System.out.println("ログインユーザーID:"+account.getLoginId());
			// ログインユーザー情報
			session.setAttribute("logindata", logindata);

			// ログインユーザーの現在日の勤務開始記録の有無を判定
			int userid = logindata.getVUserId();
			TimerDAO tDao = new TimerDAO();
			WorkStart wst = tDao.findLatestWork(userid);

			if (wst != null) {
				// 最終労働開始記録
				session.setAttribute("workstart", wst);

				// 最終労働日が当日か判定
				LocalDate today = LocalDate.now();
				LocalDate lastWorkDay = wst.getWorkday();
				System.out.println("today: " + today);
				System.out.println("最終勤務日: " + lastWorkDay);

				// 当日の勤務終了記録の有無を判定
				int workid = wst.getWorkId();
				WorkStop wsp = tDao.findWorkStop(workid);

				// 当日の勤務開始記録あり
				if (lastWorkDay.isEqual(today)) {

					// 終了記録あり(退勤済)
					if (wsp != null) {
						// 退勤記録
						session.setAttribute("workstop", wsp);

						String path = "/WEB-INF/jsp/workStopOK.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(path);
						dispatcher.forward(request, response);
					// 終了記録なし(勤務中)
					} else {
						String path = "/WEB-INF/jsp/workStop.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(path);
						dispatcher.forward(request, response);
					}
				// 過去日の勤務記録あり
				} else {

					if (wsp != null) {
						String path = "/WEB-INF/jsp/workStart.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(path);
						dispatcher.forward(request, response);
					// 過去日の勤務開始記録がない⇒記録を要求
					} else {
						String path = "/WEB-INF/jsp/workStopConfirm.jsp";
						RequestDispatcher dispatcher = request.getRequestDispatcher(path);
						dispatcher.forward(request, response);
					}
				}
			// 過去の勤務記録なし
			} else {
				String path = "/WEB-INF/jsp/workStart.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");

		session.removeAttribute("scMsg");

		// LocalDate型とString型の変換
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

		TimerDAO tDao = new TimerDAO();
		// 通常の勤務時間と休憩時間を取得
		DefaultDAO dDao = new DefaultDAO();
		WorkDefault wd = dDao.findWorkDefault();

		// actionタグで勤務段階の処理分け
		String action = request.getParameter("action");
		System.out.println("action="+ action);

		// userlvで処理後のリダイレクト先決定
		int userlv = Integer.parseInt(request.getParameter("userlv"));
		System.out.println("userlv: "+ userlv);

		if (action.equals("workStart")) {
			// ユーザーID
			int user_id = Integer.parseInt(request.getParameter("userid"));
			// 勤務開始日
			LocalDate today = LocalDate.now();
			//勤務開始時刻
			LocalTime start_time = LocalTime.now();
			String strSt = dtf.format(start_time);
			System.out.println("勤務開始時間: " + strSt);

			// WorkStartインスタンス化
			WorkStart ws = new WorkStart(user_id, today, strSt);
			session.setAttribute("todayws", ws);

			boolean result =  tDao.registerWorkStart(ws);

			if (result) {
				String sccessMsg = today +"の勤務が開始しました。勤務を終了する場合は勤怠管理をクリックしてください。";
				session.setAttribute("scMsg", sccessMsg);

				String path = "/WEB-INF/jsp/workStop.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);

//				if (userlv == 1) {
//					String path = "/WEB-INF/jsp/userMain.jsp";
//					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//					dispatcher.forward(request, response);
//				} else if (userlv == 2) {
//					String path = "/WEB-INF/jsp/adminMain.jsp";
//					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//					dispatcher.forward(request, response);
//				}

			}

		} else if (action.equals("workStop")) {

			// 勤務開始時情報
			int user_id = Integer.parseInt(request.getParameter("userid"));
			WorkStart ws = tDao.findLatestWork(user_id);
			session.setAttribute("workstart", ws);

			int workid = ws.getWorkId();
			System.out.println("登録するUserID:"+ user_id);
			System.out.println("登録するWorkID:"+ workid);

			// 実際の勤務開始時間と終了時間
			LocalTime start_time = LocalTime.parse(ws.getWorkstart(), dtf);
			LocalTime stop_time = LocalTime.now();
			String strSt = dtf.format(stop_time);
			System.out.println("勤務終了時間: "+ strSt);
			// 勤務内容
			String detail = request.getParameter("detail");

			LengthLogic ll = new LengthLogic();
			String lengths[] = ll.getWorkLength(start_time, stop_time, ws, wd);
			String work_length = lengths[0];
			String over_length = lengths[1];

			WorkStop wp = new WorkStop(workid, strSt, work_length, over_length, detail);
			session.setAttribute("workstop", wp);

			// 勤務内容文字数チェック
			DetailLogic dl = new DetailLogic();
			String errorMsg = dl.execute(detail);
			System.out.println("errorMsg= "+ errorMsg);
			session.setAttribute("errorMsg", errorMsg);

			if (errorMsg.length() <= 0) {
				boolean result = tDao.registerWorkStop(wp);

				if (result) {
					session.removeAttribute("errorMsg");
					// 登録完了
					String path = "/WEB-INF/jsp/workStopOK.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}

			// エラー発生時はメッセージを伴い勤務終了画面にリダイレクト
			} else {
				response.sendRedirect("/workTimer/TimerMainServlet");
			}
		} else if (action.equals("workStopConfirm")) {

			int user_id = Integer.parseInt(request.getParameter("userid"));
			WorkStart ws = tDao.findLatestWork(user_id);
			session.setAttribute("workstart", ws);

			// 登録する仕事Id
			int workid = ws.getWorkId();
			System.out.println("登録するUserID:"+ user_id);
			System.out.println("登録するWorkID:"+ workid);

			// 実際の勤務開始時間と終了時間
			String strStartTime = ws.getWorkstart();
			String strStopTime = request.getParameter("workstop") + ":00";
			System.out.println("退勤時間: "+ strStopTime);

			LocalTime start_time = LocalTime.parse(strStartTime, dtf);
			LocalTime stop_time = LocalTime.parse(strStopTime, dtf);

			// 勤務内容
			String detail = request.getParameter("detail");
			System.out.println("detail:"+ detail);

			LengthLogic ll = new LengthLogic();
			String[] lengths = ll.getWorkLength(start_time, stop_time, ws, wd);
			String work_length = lengths[0];
			String over_length = lengths[1];

			session.removeAttribute("workstop");
			WorkStop wp = new WorkStop(workid, strStopTime, work_length, over_length, detail);
			session.setAttribute("workstop", wp);

			// 勤務内容文字数チェック
			DetailLogic dl = new DetailLogic();
			String errorMsg = dl.execute(detail);
			System.out.println("errorMsg= "+ errorMsg);
			session.setAttribute("errorMsg", errorMsg);

			if (errorMsg.length() <= 0) {

				boolean result = tDao.registerWorkStop(wp);
				if (result) {
					session.removeAttribute("errorMsg");
					session.removeAttribute("todayws");
					// 登録完了
					String path = "/WEB-INF/jsp/workStopOK.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
				}

			// エラー発生時はメッセージを伴い勤務終了画面にリダイレクト
			} else {
				response.sendRedirect("/workTimer/TimerMainServlet");
			}

		}
	}

}