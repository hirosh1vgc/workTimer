package model;

public class User {

	private int userId;
	private String userName;
	private String loginId;
	private String password;
	private int userLv;
	private int enrollmentLv;

	public User(int userId, String userName, String loginId, String password, int userLv, int enrollmentLv) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.loginId = loginId;
		this.password = password;
		this.userLv = userLv;
		this.enrollmentLv = enrollmentLv;
	}

	// 新規ユーザー登録用コンストラクタ
	public User(String loginId, String password,  String userName, int userLv) {
		this.loginId = loginId;
		this.password = password;
		this.userName = userName;
		this.userLv = userLv;
	}

	// ユーザー編集用
	public User(String userName, String loginId, String password,  int userLv, int enrollmentLv) {
		this.userName = userName;
		this.loginId = loginId;
		this.password = password;
		this.userLv = userLv;
		this.enrollmentLv = enrollmentLv;
	}

	// ユーザー勤務履歴表示用
	public User(int userId, String userName, String loginId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.loginId = loginId;
	}

	public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getLoginId() {
		return loginId;
	}
	public String getPassword() {
		return password;
	}
	public int getUserLv() {
		return userLv;
	}
	public int getEnrollmentLv() {
		return enrollmentLv;
	}

}