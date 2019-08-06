package com.app.studyabroad.entity;

/**
 * RSA��Կ�����㷨ʵ����
 * @author Amao
 *
 */
public class Rsa {
	
	private int id; //id
	private String appRsaModulus; //app rsaģ
	private String sysRsaModulus; //v3 rsaģ
	private String sysRsaPrivateExponent; //app ˽Կָ��
	private String appRsaPublicExponent; //v3 ˽Կָ��
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppRsaModulus() {
		return appRsaModulus;
	}
	public void setAppRsaModulus(String appRsaModulus) {
		this.appRsaModulus = appRsaModulus;
	}
	public String getSysRsaModulus() {
		return sysRsaModulus;
	}
	public void setSysRsaModulus(String sysRsaModulus) {
		this.sysRsaModulus = sysRsaModulus;
	}
	public String getSysRsaPrivateExponent() {
		return sysRsaPrivateExponent;
	}
	public void setSysRsaPrivateExponent(String sysRsaPrivateExponent) {
		this.sysRsaPrivateExponent = sysRsaPrivateExponent;
	}
	public String getAppRsaPublicExponent() {
		return appRsaPublicExponent;
	}
	public void setAppRsaPublicExponent(String appRsaPublicExponent) {
		this.appRsaPublicExponent = appRsaPublicExponent;
	}
	
	

}
