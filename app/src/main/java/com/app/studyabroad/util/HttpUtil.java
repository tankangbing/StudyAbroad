package com.app.studyabroad.util;

import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.app.studyabroad.adapter.MyReceiver;
import com.app.studyabroad.entity.Rsa;

public class HttpUtil {	

	private static final int REQUEST_TIMEOUT = 40*1000;//��������ʱ40��
	private static final int SO_TIMEOUT = 40*1000;  //���õȴ����ݳ�ʱʱ��40��
	private static final List<String> needEncryptionList = Arrays.asList("password","username","userinfomationOldPsw","userinfomationNewPsw");//��Ҫ���ܵ�����

	@SuppressLint("ShowToast")
	public static String getResultByPost(String url,Map<String,Object> map,Context c,Rsa rsa){
		String strResult = "";
		RSAPublicKey publickey  = null;
		int i = MyReceiver.getNetworkState(c);
		if(i == 0 ){
			strResult = "NO_NET";
		}else{
			try{
				if(null != rsa){
					publickey = CodeSecurityUtil.getPublicKey(rsa.getAppRsaModulus(),rsa.getAppRsaPublicExponent());//����app��������
				}
				HttpClient client = getHttpClient(); 
				List <BasicNameValuePair> postParameters = getParams(map,publickey);
				HttpPost request = new HttpPost(url);
				request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
						postParameters,"utf-8");
				request.setEntity(formEntity);
				HttpResponse httpResponse = client.execute(request);
				if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)   
				{  
					strResult = EntityUtils.toString(httpResponse.getEntity());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return strResult;
	}

	public static HttpClient getHttpClient(){
		BasicHttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
		HttpClient client = new DefaultHttpClient(httpParams);  
		return client;  
	}  

	private static List <BasicNameValuePair> getParams(Map<String,Object> map,RSAPublicKey publickey){
		List <BasicNameValuePair> params = new ArrayList <BasicNameValuePair>();
		try {
			for(Map.Entry<String, Object> entry:map.entrySet()){
				String param = entry.getValue()+"";
				if(null != publickey && SysUtil.isListStringElement(needEncryptionList, entry.getKey())){
					param = CodeSecurityUtil.encryptByPublicKey(java.net.URLEncoder.encode(param, "utf-8"), publickey); //��������
				}
				params.add(new BasicNameValuePair(entry.getKey(),param));				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;    
	}

}

