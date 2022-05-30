package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import model.WorkStart;
import model.WorkStop;

public class TimerDAO {
	final String PATH = "jdbc:mysql://localhost:3306/worktimer?characterEncoding=UTF-8&serverTimezone=JST";
	final String USER = "root";
	final String PASS = "root";

	// 仕事の開始と時間を記録
	public boolean registerWorkStart(WorkStart ws) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// INSERT文の準備
			String sql = "insert into Workstart (user_id, work_day, work_start) values (?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// LocalDate型をsql.Date型に変換
			java.sql.Date sqlwd = java.sql.Date.valueOf(ws.getWorkday());

			pStmt.setInt(1, ws.getUserId());
			pStmt.setDate(2, sqlwd);
			pStmt.setString(3, ws.getWorkstart());

			// INSERT文の実行
			int result = pStmt.executeUpdate();
			if (result!=1) {
				return false;
			}

		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}

	return true;
	}

	// あるユーザーの最新の仕事開始記録を取得
	public WorkStart findLatestWork(int user_id) {

		// 返り値fwtの初期値をnullに設定
		WorkStart fwt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(PATH, USER, PASS)) {
				// SELECT文の準備
				String sql =
					"select work_id, user_id, work_day, work_start from WorkStart where work_id = (select MAX(work_id) from Workstart where user_id = ?)";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setInt(1, user_id);

				//結果表に格納されたレコードの表示
				ResultSet rs = pStmt.executeQuery();

				while (rs.next()) {
					int workid = rs.getInt("work_id");
					int userid = rs.getInt("user_id");
					// sql.Date型をLocalDate型に変換
					LocalDate day = (rs.getDate("work_day")).toLocalDate();
					String start = rs.getString("work_start");

					System.out.println("直近の仕事ID:"+ workid);
					System.out.println("直近の勤務のユーザーID: "+ userid);
					System.out.println("直近の勤務日: "+ day);
					System.out.println("直近の勤務開始時間: "+ start);

					// WorkStartインスタンス生成
					 fwt = new WorkStart(workid, userid, day, start);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("errorSQL");
				return fwt;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return fwt;
		}

		return fwt;
	}

	// 仕事終了時間を取得
	public WorkStop findWorkStop(int timer_id) {

		WorkStop fws = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(PATH, USER, PASS)) {
				// SELECT文の準備
				String sql = "select id, stop_time, work_length, work_over, work_detail from WorkStop where id=?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setInt(1, timer_id);

				//結果表に格納されたレコードの表示
				ResultSet rs = pStmt.executeQuery();

				while (rs.next()) {
					int id = rs.getInt("id");
					String workstop = rs.getString("stop_time");
					String length = rs.getString("work_length");
					String detail = rs.getString("work_detail");
					String over = rs.getString("work_over");

					// WorkStopインスタンス生成
					 fws = new WorkStop(id, workstop, length, over, detail);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("errorSQL");
				return fws;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return fws;
		}

		return fws;
	}

	// 仕事の終了時間と実労働時間を記録
	public boolean registerWorkStop(WorkStop ws) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// INSERT文の準備
			String sql = "insert into Workstop (id, stop_time, work_length, work_over, work_detail) values (?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, ws.getWorkId());
			pStmt.setString(2, ws.getStopTime());
			pStmt.setString(3, ws.getWorkLength());
			pStmt.setString(4, ws.getWorkOver());
			pStmt.setString(5, ws.getWorkdetail());

			// INSERT文の実行
			int result = pStmt.executeUpdate();
			if (result!=1) {
				return false;
			}

		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}

	return true;
	}

}