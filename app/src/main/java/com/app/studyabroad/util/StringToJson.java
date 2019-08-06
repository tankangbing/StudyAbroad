package com.app.studyabroad.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.app.studyabroad.entity.QuestionNode;
import com.app.studyabroad.entity.TreeNode;

/**
 * ��stringת����json����
 * @author Amao
 *
 */

public class StringToJson {
	
	private static ObjectMapper objectMapper = null;
	
	 @SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr){
		 Map<String, Object> maps  = new HashMap<String, Object>();
		    try {
		    	objectMapper = new ObjectMapper();
		        maps = objectMapper.readValue(jsonStr, Map.class);
		    } catch (JsonParseException e) {
		        e.printStackTrace();
		    } catch (JsonMappingException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return maps;
	 }
	 
	 
	public static TreeNode parseJSON2TreeNode(String jsonStr){
		 TreeNode t  = new TreeNode();
		    try {
		    	objectMapper = new ObjectMapper();
		        t = objectMapper.readValue(jsonStr, TreeNode.class);
		    } catch (JsonParseException e) {
		        e.printStackTrace();
		    } catch (JsonMappingException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return t;
	}
	public static QuestionNode parseJSON2QuestionNode(String jsonStr){
		QuestionNode t  = new QuestionNode();
		try {
			objectMapper = new ObjectMapper();
			t = objectMapper.readValue(jsonStr, QuestionNode.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	 
	 @SuppressWarnings("unchecked")
	public static List<Map<String, Object>> parseJSON2ListMap(String jsonStr){
		 List<Map<String, Object>> list  = new ArrayList<Map<String, Object>>();
	     try {
	    	list = objectMapper.readValue(jsonStr, List.class);
	     } catch (JsonParseException e) {
	        e.printStackTrace();
	     } catch (JsonMappingException e) {
	        e.printStackTrace();
	     } catch (IOException e) {
	        e.printStackTrace();
	     }
	     return list;
	}
	 
	 @SuppressWarnings("unchecked")
	public static Map<String,List<Map<String, Object>>> parseJSON2MapListMap(String jsonStr){
		Map<String,List<Map<String,Object>>> maps  = new HashMap<String,List<Map<String,Object>>>();
		    try {
		    	objectMapper = new ObjectMapper();
		        maps = objectMapper.readValue(jsonStr, Map.class);
		    } catch (JsonParseException e) {
		        e.printStackTrace();
		    } catch (JsonMappingException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return maps;
	 }
}
