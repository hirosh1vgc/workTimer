package model;

import java.time.LocalDate;

public class WorkStart {

	private int workId;
	private int userId;
	private LocalDate workday;
	private String workstart;

	public WorkStart(int workId, int userId, LocalDate workday, String workstart) {
		super();
		this.workId = workId;
		this.userId = userId;
		this.workday = workday;
		this.workstart = workstart;
	}
	// Workstartテーブル登録用
	public WorkStart(int userId, LocalDate workday, String workstart) {
		this.userId = userId;
		this.workday = workday;
		this.workstart = workstart;
	}

	public int getWorkId() {
		return workId;
	}
	public int getUserId() {
		return userId;
	}
	public LocalDate getWorkday() {
		return workday;
	}
	public String getWorkstart() {
		return workstart;
	}

}