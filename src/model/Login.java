package model;

public class Login {

	private String loginId;
	private String passward;

	public Login(String loginId, String passward ) {
		super();
		this.loginId = loginId;
		this.passward = passward;
	}

	public String getLoginId() {
		return loginId;
	}
	public String getPassword() {
		return passward;
	}
}