package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.WorkDefault;

public class DefaultDAO {
	final String PATH = "jdbc:mysql://localhost:3306/worktimer?characterEncoding=UTF-8&serverTimezone=JST";
	final String USER = "root";
	final String PASS = "root";

	public WorkDefault findWorkDefault() {
		WorkDefault wd = null;
		// JDBCドライバのロード
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(PATH, USER, PASS)) {
				// SELECT文の準備
				String sql = "select wakeup_time, sleep_time, rest_time from Workdefault";
				PreparedStatement pStmt = conn.prepareStatement(sql);

				ResultSet rs = pStmt.executeQuery();

				while (rs.next()) {
					String wakeup = rs.getString("wakeup_time");
					String sleep = rs.getString("sleep_time");
					int rest = rs.getInt("rest_time");

					wd = new WorkDefault(wakeup, sleep, rest);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("errorSQL");
				return wd;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return wd;
		}

		return wd;
	}

	public boolean editWorkDefault(WorkDefault wd) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// UPDATE文の準備
			String sql = "update Workdefault set wakeup_time=?, sleep_time=?, rest_time=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, wd.getWakeup());
			pStmt.setString(2, wd.getSleep());
			pStmt.setInt(3, wd.getRest());

			// UPDATE文の実行
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