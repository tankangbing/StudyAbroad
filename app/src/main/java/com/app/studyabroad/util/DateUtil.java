package com.app.studyabroad.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
	
	public final static String FMTSTR = "yyyy/MM/dd HH:mm:ss";
	public final static String FMTSTR_CH = "yyyy��MM��dd�� HHʱmm��ss��";
	
	public static SimpleDateFormat GetSimpleDateFormat(String fmt){
		return new SimpleDateFormat(fmt);
	}
	
	/**
	 * ��ǰʱ��
	 * @param d
	 * @return
	 */
	public static String getCurrentTime(Date d,SimpleDateFormat f){
		String rttime = "";
		try {
			rttime = f.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rttime;
	}
	
	/**
	 * �жϵ�ǰʱ��������ݵĵڼ���
	 * @param d
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getCurrentWeek(Date d){
		int day 	= 	d.getDay();//��
		int year 	= 	d.getYear();//��
		int month 	= 	d.getMonth();//��
		int week = 0;
		if(0 < day && day < 8){
			week = 1;
		}else if(8 < day && day < 16){
			week = 2;
		}else if(16 < day && day < 24){
			week = 3;
		}else if(24 < day && day < 32){
			week = 4;
		}
		return year+"��"+month+"��"+"��"+week+"��";
	}

}
