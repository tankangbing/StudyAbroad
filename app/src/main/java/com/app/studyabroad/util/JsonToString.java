package com.app.studyabroad.util;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * ��jsonת����String
 * @author Amao
 *
 */
public class JsonToString {
	
	private static ObjectMapper objectMapper = null;
	
	/**
	 * object תstring  json
	 * @param obj
	 * @return
	 */
	public static String ObjectToString(Object obj){
		String rtstr = "";
		try {
			objectMapper = new ObjectMapper();
			rtstr = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtstr;
	}
	
	/**
	 * Map תString json
	 * @param map
	 * @return
	 */
	public static String MapToJson(Map<String,Object> map) {
		String rtstr = "";
		try {
			objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtstr;
	}
   


}
