package com.app.studyabroad.util;

public class VideoUtil {
	
	public static String formatUrlHtml(String url){
		if(!SysUtil.isBlank(url) && url.length()>5){
			String html = url.substring(url.length()-5,url.length());
			String formatUrl = url.substring(0,url.length()-5);
			if(".html".equals(html)){
				url = formatUrl + ".mp4";
			}
			if(url.contains("cs.scutde.net")){
				url = url.replace("cs.scutde.net", "ecs.scutde.net");
			}
			if(url.contains("-new/main")){
				url = url.replace("-new/main", "");
			}
		}
		return url;
	}

}
