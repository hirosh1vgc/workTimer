package servlet;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DefaultDAO;
import dao.UserDAO;
import dao.V_TimerDAO;
import model.User;
import model.V_Timer;
import model.WorkDefault;

@WebServlet("/UserMainServlet")
public class UserMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// ログインセッションの有無チェック
		User account = (User)session.getAttribute("account");

		if (account == null) {
			response.sendRedirect("/workTimer/LoginServlet");
		} else {
			// ログインユーザーIDから勤務記録が存在する月を検索
			int user_id = account.getUserId();
			V_TimerDAO vDao = new V_TimerDAO();
			List<V_Timer> vmonths = vDao.findMonthByUserid(user_id);
			for (V_Timer vm : vmonths) {
				System.out.println("過去の勤務月: "+vm);
			}
			session.setAttribute("vmonths", vmonths);

			// 通常の勤務時間と休憩時間を取得
			DefaultDAO dDao = new DefaultDAO();
			WorkDefault wd = dDao.findWorkDefault();
			session.setAttribute("wd", wd);

			String path = "/WEB-INF/jsp/userMain.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// y年MM月形式に変換
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("y年MM月");

		// 検索ユーザーID
		int user_id = Integer.parseInt(request.getParameter("userid"));
		System.out.println("検索ユーザーID: "+ user_id);

		// actionタグでユーザー権限判定
		String action = request.getParameter("action");
		if (action.equals("userMain") || action.equals("adminMemory")) {

		// 取得した日付から年月の値のみを取り出す
		String strMonth = request.getParameter("monthSelect");
		LocalDate lmonth = LocalDate.parse(strMonth);

		String smonth = dtf.format(lmonth);
		System.out.println("smonth: "+ smonth);
		System.out.println("lmonth: "+ lmonth);

		// 選択した月日情報をインスタンス化
		V_Timer memoryMonth = new V_Timer(lmonth, smonth);
		session.setAttribute("memorymonth", memoryMonth);

		int year = lmonth.getYear();
		int month = lmonth.getMonthValue();

		System.out.println("選択年: "+ year);
		System.out.println("選択月: "+ month);

		// 年月ごとの勤務記録一覧
		V_TimerDAO vDao = new V_TimerDAO();
		List<V_Timer> wMemory = vDao.findVtimerByDay(user_id, year, month);
		session.setAttribute("wmemory", wMemory);

		// 総勤務時間と残業時間を計算
		System.out.println("勤務日数: "+ wMemory.size());
		// 総勤務時間管理用
		Duration total_work = Duration.ZERO;
		// 残業時間管理用
		Duration total_over = Duration.ZERO;

		// 通常の勤務時間を求める
		DefaultDAO dDao = new DefaultDAO();
		WorkDefault wd = dDao.findWorkDefault();
		LocalTime wakeup = LocalTime.parse(wd.getWakeup());
		LocalTime sleep = LocalTime.parse(wd.getSleep());
		int rest = wd.getRest();
		Duration dwork = Duration.between(wakeup, sleep);

		for (V_Timer vt : wMemory) {
			LocalTime start = LocalTime.parse(vt.getWorkStart());
			LocalTime stop = LocalTime.parse(vt.getWorkStop());
			// 勤務時間計算
			Duration length = Duration.between(start, stop);
			Duration work_length = length.minusMinutes(rest);
			total_work = total_work.plus(work_length);
			int wh = length.toHoursPart();
			int wm = length.toMinutesPart();
			System.out.println(vt.getWorkDay() +"の勤務時間: "+ wh +"時間"+ wm +"分");

			// 残業計算
			Duration over_length = length.minus(dwork);
			if (!over_length.isNegative()) {
				total_over = total_over.plus(over_length);
			}
		}

		int tday = (int) total_work.toDaysPart();
		int thour = total_work.toHoursPart();
		int tmin = total_work.toMinutesPart();
		int oday = (int) total_over.toDaysPart();
		int ohour = total_over.toHoursPart();
		int omin = total_over.toMinutesPart();
		String lengthMsg="総勤務時間: "+(tday*24+thour)+"時間"+tmin+"分。総残業時間: "+(oday*24+ohour)+"時間"+omin+"分。";
		session.setAttribute("lengthMsg", lengthMsg);

		String path = "/WEB-INF/jsp/workMemory.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);

		} else if (action.equals("adminMain")) {

			// 選択したユーザーIDから勤務履歴の月を検索
			V_TimerDAO vDao = new V_TimerDAO();
			List<V_Timer> vmonths = vDao.findMonthByUserid(user_id);
			for (V_Timer vm : vmonths) {
				System.out.println("過去の勤務月: "+vm);
			}
			session.setAttribute("vmonths", vmonths);

			UserDAO uDao = new UserDAO();
			User user = uDao.findByUserId(user_id);
			session.setAttribute("memoryuser", user);

			String path = "/WEB-INF/jsp/adminMemory.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
	}

}