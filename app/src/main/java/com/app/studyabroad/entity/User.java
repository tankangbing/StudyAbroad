package com.app.studyabroad.entity;

/**
 * �û�
 * @author Amao
 *
 */
public class User {
	
	private int id; //id
	private String userName; //�û�����
	private String userPsw; //�û�����  --md5
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPsw() {
		return userPsw;
	}
	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}
	
	
	

}
