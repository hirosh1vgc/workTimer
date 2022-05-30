package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.V_User;

public class V_UserDAO {

	final String PATH = "jdbc:mysql://localhost:3306/worktimer?characterEncoding=UTF-8&serverTimezone=JST";
	final String USER = "root";
	final String PASS = "root";

	// 登録済全ユーザー情報の取得
	public List<V_User> findAllUser(){

		List<V_User> allUser = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// SELECT文を準備
			String sql = "select * from v_user;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int userId = rs.getInt("user_id");
				String userName = rs.getString("user_name");
				System.out.println("全ユーザー名:"+ userName);
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");
				String userLv = rs.getString("user_lv");
				String enrollmentLv = rs.getString("enrollment_lv");

				V_User user = new V_User(userId, userName, loginId, password, userLv, enrollmentLv);
				allUser.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("errorSQL");
			return allUser;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return allUser;
		}

		return allUser;
	}

	// ログインIDからユーザー情報を取得
	public V_User findVUserByLoginid(String loginid) {
		V_User Vuser = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, USER, PASS);
			// SELECT文を準備
			String sql = "select * from v_user where login_id = ?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginid);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int userId = rs.getInt("user_id");
				String userName = rs.getString("user_name");
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");
				String userLv = rs.getString("user_lv");
				String enrollmentLv = rs.getString("enrollment_lv");

				System.out.println("検索ユーザーID:"+ userId);
				System.out.println("検索ユーザー名:"+ userName);
				System.out.println("検索ユーザーログインID:"+ loginId);
				System.out.println("検索パスワード:"+ password);
				System.out.println("検索ユーザー権限:"+ userLv);
				System.out.println("検索ユーザー在籍状況:"+ enrollmentLv);

				Vuser = new V_User(userId, userName, loginId, password, userLv, enrollmentLv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("errorSQL");
			return Vuser;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("errorClass");
			return Vuser;
		}

		return Vuser;
	}
}