package model;

import java.time.LocalDate;

public class V_Timer {
	private LocalDate workDay;
	private int workId;
	private int userId;
	private String workStart;
	private String workStop;
	private String workLength;
	private String workOver;
	private String workDetail;
	private String smonth;

	public V_Timer(LocalDate workDay, int workId, int userId, String workStart, String workStop, String workLength,
			String workOver, String workDetail, String smonth) {
		super();
		this.workDay = workDay;
		this.workId = workId;
		this.userId = userId;
		this.workStart = workStart;
		this.workStop = workStop;
		this.workLength = workLength;
		this.workOver = workOver;
		this.workDetail = workDetail;
		this.smonth = smonth;
	}

	public V_Timer(LocalDate workDay, int workId, int userId, String workStart, String workStop, String workLength,
			String workOver, String workDetail) {
		this.workDay = workDay;
		this.workId = workId;
		this.userId = userId;
		this.workStart = workStart;
		this.workStop = workStop;
		this.workLength = workLength;
		this.workOver = workOver;
		this.workDetail = workDetail;
	}

	public V_Timer(LocalDate workDay, String smonth) {
		this.workDay = workDay;
		this.smonth = smonth;
	}

	public LocalDate getWorkDay() {
		return workDay;
	}
	public int getWorkId() {
		return workId;
	}
	public int getUserId() {
		return userId;
	}
	public String getWorkStart() {
		return workStart;
	}
	public String getWorkStop() {
		return workStop;
	}
	public String getWorkLength() {
		return workLength;
	}
	public String getWorkOver() {
		return workOver;
	}
	public String getWorkDetail() {
		return workDetail;
	}
	public String getSmonth() {
		return smonth;
	}

}