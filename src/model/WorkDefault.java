package model;
public class WorkDefault {

	private String wakeup;
	private String sleep;
	private int rest;

	public WorkDefault(String wakeup, String sleep, int rest) {
		super();
		this.wakeup = wakeup;
		this.sleep = sleep;
		this.rest = rest;
	}

	public String getWakeup() {
		return wakeup;
	}
	public String getSleep() {
		return sleep;
	}
	public int getRest() {
		return rest;
	}

}