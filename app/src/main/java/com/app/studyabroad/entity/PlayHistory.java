package com.app.studyabroad.entity;

public class PlayHistory {
	
	private int id;
	private String mateid;//����id
	private String userid;//�û�id
	private int time;//ʱ��
	
	public PlayHistory(){
		super();
	}
	
	public PlayHistory(String a,String b,int c){
		this.mateid = a;
		this.userid = b;
		this.time = c;
	}
	
	
	public String getMateid() {
		return mateid;
	}
	public void setMateid(String mateid) {
		this.mateid = mateid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
