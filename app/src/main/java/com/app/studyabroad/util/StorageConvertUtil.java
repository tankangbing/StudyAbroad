package com.app.studyabroad.util;

public class StorageConvertUtil {
	
	public final static String  KB = "kb";
	public final static String  MB = "Mb";
	public final static String  BIT = "b";
	public final static Double CONVERTUNIT = 1024.00;
	
	public static Double convertBitToMb(Long bit){ //bit ת mb
		Double rt = 0.00D;
		try {
			rt = convertBitToKb(bit)/CONVERTUNIT;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rt;
	}
	
	
	public static Double convertBitToKb(Long bit){ //bit ת kb
		Double rt = 0.00D;
		try {
			rt = bit/CONVERTUNIT;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rt;
	}

}
