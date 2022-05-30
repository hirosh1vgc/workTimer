package model;

public class WorkStop {

	private int workId;
	private String stopTime;
	private String workLength;
	private String workOver;
	private String workDetail;

	public WorkStop(int workId, String stopTime, String workLength, String workOver, String workDetail) {
		super();
		this.workId = workId;
		this.stopTime = stopTime;
		this.workLength = workLength;
		this.workOver = workOver;
		this.workDetail = workDetail;
	}

	public int getWorkId() {
		return workId;
	}
	public String getStopTime() {
		return stopTime;
	}
	public String getWorkLength() {
		return workLength;
	}
	public String getWorkOver() {
		return workOver;
	}
	public String getWorkdetail() {
		return workDetail;
	}

}