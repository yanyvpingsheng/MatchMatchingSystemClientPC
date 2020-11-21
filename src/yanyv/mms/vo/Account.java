package yanyv.mms.vo;

import java.util.Date;

public class Account {
	private int uid = 0;
	private String name = "";
	private String pass = "";
	private Date date;
	private String email = "";
	private int state;
	
	public Account() {
		
	}
	
	public Account(String name) {
		this.name = name;
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		// TODO Auto-generated method stub
		this.state = state;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
