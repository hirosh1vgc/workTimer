package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.V_Timer;

public class V_TimerDAO {

	final String PATH = "jdbc:mysql://localhost:3306/worktimer?characterEncoding=UTF-8&serverTimezone=JST";
	final String USER = "root";
	final String PASS = "root";

	public List<V_Timer> findVtimerByDay (int user_id, int year, int month){
		List<V_Timer> Vtimer = new ArrayList<V_Timer>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// SELECT文を準備
			// DATE_FORMAT関数を利用し年(4桁 : ％Y)月(%c)日(%e)指定で検索
			String sql = "select work_day, work_id, user_id, work_start, work_stop, work_length, work_over, work_detail from v_timer"
					+ " where user_id = ? and date_format(work_day, '%Y') = ? and date_format(work_day, '%c') = ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, user_id);
			pStmt.setInt(2, year);
			pStmt.setInt(3, month);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				LocalDate workDay = (rs.getDate("work_day")).toLocalDate();
				int workId = rs.getInt("work_id");
				int userId = rs.getInt("user_id");
				String workStart = rs.getString("work_start");
				String workStop = rs.getString("work_stop");
				String workLength = rs.getString("work_length");
				String workOver = rs.getString("work_over");
				String detail = rs.getString("work_detail");

				System.out.println("検索workStart:" +workStart);

				V_Timer vt = new V_Timer(workDay, workId, userId, workStart, workStop, workLength, workOver, detail);
				Vtimer.add(vt);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("errorSQL");
			return Vtimer;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return Vtimer;
		}

		return Vtimer;
	}

	public List<V_Timer> findMonthByUserid (int user_id){
		List<V_Timer> Vmonth = new ArrayList<V_Timer>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// SELECT文を準備
			String sql = "select work_day from v_timer where user_id = ?  group by date_format(work_day, '%c');";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, user_id);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				LocalDate workDay = (rs.getDate("work_day")).toLocalDate();
				// y年MM月形式に変換
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("y年MM月");
				String smonth = dtf.format(workDay);

				V_Timer vt = new V_Timer(workDay, smonth);
				Vmonth.add(vt);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("errorSQL");
			return Vmonth;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return Vmonth;
		}

		return Vmonth;
	}

}