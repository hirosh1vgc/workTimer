package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Login;
import model.User;

public class UserDAO {
	final String PATH = "jdbc:mysql://localhost:3306/worktimer?characterEncoding=UTF-8&serverTimezone=JST";
	final String USER = "root";
	final String PASS = "root";

	public User findByLogin(Login login) {
		User user = null;
		// JDBCドライバのロード
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(PATH, USER, PASS)) {
				// SELECT文の準備
				String sql = "select user_id, user_name, login_id, password, userlv_id, enrollmentlv_id from user where login_id=? and password=?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, login.getLoginId());
				pStmt.setString(2, login.getPassword());

				ResultSet rs = pStmt.executeQuery();

				//結果表に格納されたレコードの表示
				while (rs.next()) {
					int userid = rs.getInt("user_id");
					String name = rs.getString("user_name");
					String loginid = rs.getString("login_id");
					String password = rs.getString("password");
					int userlv = rs.getInt("userlv_id");
					int enrollmentlv = rs.getInt("enrollmentlv_id");
					// Userインスタンス生成
					user = new User(userid, name, loginid, password, userlv, enrollmentlv);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("errorSQL");
				return user;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return user;
		}

		return user;
	}

	// 既存ログインIDと重複チェック
	public String findByLoginid(String login_id) {
		String loginid  = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			//Select文を準備
			String sql = "select login_id from user where login_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login_id);

			ResultSet rs = pStmt.executeQuery();

			//結果表に格納されたレコードの表示
			while (rs.next()) {
				loginid = rs.getString("login_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("errorSQL");
			return loginid;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return loginid;
		}

		return loginid;
	}

	public boolean registerUser(User user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// INSERT文の準備
			String sql =
				"insert into user (user_name, login_id, password, userlv_id) values (?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, user.getUserName());
			pStmt.setString(2, user.getPassword());
			pStmt.setString(3, user.getLoginId());
			pStmt.setInt(4, user.getUserLv());

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

	public boolean deleteUser(String login_id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// DELETE文の準備
			String sql =
				"delete from user where login_id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, login_id);

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

	public boolean editUser(User edituser) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// UPDATE文の準備
			String sql =
				"update user set user_name=?, password=?, userlv_id=?, enrollmentlv_id=? where login_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, edituser.getUserName());
			pStmt.setString(2, edituser.getPassword());
			pStmt.setInt(3, edituser.getUserLv());
			pStmt.setInt(4, edituser.getEnrollmentLv());
			pStmt.setString(5, edituser.getLoginId());

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

	public User findByUserId(int userid) {
		User user = null;
		// JDBCドライバのロード
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(PATH, USER, PASS)) {
				// SELECT文の準備
				String sql = "select user_name, login_id from user where user_id=?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setInt(1, userid);

				ResultSet rs = pStmt.executeQuery();

				//結果表に格納されたレコードの表示
				while (rs.next()) {
					String name = rs.getString("user_name");
					String loginid = rs.getString("login_id");
					// Userインスタンス生成
					user = new User(userid, name, loginid);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("errorSQL");
				return user;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return user;
		}

		return user;
		}
}