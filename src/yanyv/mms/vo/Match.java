package yanyv.mms.vo;

import java.io.File;
import java.util.Date;

public class Match {
	private boolean isWeb = false;
	
	private String mid;
	private String name;
	private int createrUid;
	private int modeId;
	private Date startDate;
	private Date endDate;
	private Date deadline;
	
	private File matchfile;
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCreaterUid() {
		return createrUid;
	}
	public void setCreaterUid(int createrUid) {
		this.createrUid = createrUid;
	}
	public int getModeId() {
		return modeId;
	}
	public void setModeId(int modeId) {
		this.modeId = modeId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public File getMatchfile() {
		return matchfile;
	}
	public void setMatchfile(File matchfile) {
		this.matchfile = matchfile;
	}
	public boolean isWeb() {
		return isWeb;
	}
	public void setWeb(boolean isWeb) {
		this.isWeb = isWeb;
	}
	
	public String toString() {
		if(isWeb) {
			return name;
		} else {
			return matchfile.getName().split("\\.")[0];
		}
	}
	
}
