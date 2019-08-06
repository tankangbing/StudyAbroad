package com.app.studyabroad.util;

public class CommonUtils {
	
	/**
	 * �жϵ���Ƿ���Ч
	 */
	
	private static long lastClickTime;
	public static boolean isTrueClick(){
		long time = System.currentTimeMillis();
		long timeout = time - lastClickTime;
		if(0 < timeout && timeout > 800){
			return true;
		}else{
			return false;
		}
	}

}
