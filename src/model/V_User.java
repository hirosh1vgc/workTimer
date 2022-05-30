package model;

public class V_User {
	private int VUserId;
	private String VUserName;
	private String VLoginId;
	private String VPassword;
	private String VUserLv;
	private String VEnrollmentLv;

	public V_User(int VUserId, String VUserName, String VLoginId, String VPassword, String VUserLv, String VEnrollmentLv) {
		this.VUserId = VUserId;
		this.VUserName = VUserName;
		this.VLoginId = VLoginId;
		this.VPassword = VPassword;
		this.VUserLv = VUserLv;
		this.VEnrollmentLv = VEnrollmentLv;
	}

	public int getVUserId() {
		return VUserId;
	}
	public String getVUserName() {
		return VUserName;
	}
	public String getVLoginId() {
		return VLoginId;
	}
	public String getVPassword() {
		return VPassword;
	}
	public String getVUserLv() {
		return VUserLv;
	}
	public String getVEnrollmentLv() {
		return VEnrollmentLv;
	}
}