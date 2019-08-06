package com.app.studyabroad.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import com.app.studyabroad.util.DateUtil;
import com.app.studyabroad.util.FinalStr;

public class SendDebug {
	
	private Context mContext;
	
	public SendDebug(Context c){
		mContext = c;
	}

	@SuppressWarnings("finally")
	public Boolean sendEmail(){
		Boolean rtb = true;
		try {
			SendAttachment themail = new SendAttachment(FinalStr.EMAILPORT);
			themail.setNeedAuth(true);
			
			if(themail.setSubject(setTitle()) == false) rtb = false;
			if(themail.setBody(setBody()) == false) rtb = false;
			if(themail.setTo(FinalStr.FETCHMAILMAN) == false) rtb = false;
			//�������ռ��˴���
			if(themail.setFrom(FinalStr.SENDEMAILMAN) == false) rtb = false;
			
			//���͸���
			String filename = FinalStr.LOGPATH+FinalStr.LOGNAME;
			
			//File file=new File(filename);
	        if(themail.addFileAffix(filename) == false) rtb = false;	
			themail.setNamePass(FinalStr.SENDEMAILMAN, FinalStr.SENDEMAILMANPSW);
			if(themail.sendout() == false) rtb = false;
		} catch (Exception e) {
			rtb = false;
			Log.e(TAG, "�����ʼ�ʧ�ܣ� ", e);	
		}finally{
			clearBugInfo();
			return rtb;
		}

	}
	
	//�����ʼ�����
	private String setTitle(){
		return "����ƽ̨BUG��־";
	}
	
	//�����ʼ�����
	private String setBody(){
		StringBuffer body = new StringBuffer("<label>BUG�ύʱ��["+DateUtil.getCurrentTime(new Date(),DateUtil.GetSimpleDateFormat(DateUtil.FMTSTR_CH))+"]</label><ul>");
		Map<String, String> infos = collectDeviceInfo(mContext);
		for (Map.Entry<String, String> entry : infos.entrySet()) {  
          String key = entry.getKey();  
          String value = entry.getValue();  
          body.append("<li>"+key + "=" + value + "</li>");  
		} 
		body.append("</ul>");
		return body.toString();
	}
	
	
    /** 
    * �ռ��豸������Ϣ 
    * @param ctx 
    */  
	private String TAG = FinalStr.TAG_ERR;
    public Map<String, String>  collectDeviceInfo(Context ctx) {  
    	Map<String, String> infos = new HashMap<String, String>(0);
       try {  
           PackageManager pm = ctx.getPackageManager();  
           PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
           if (pi != null) {  
               String versionName = pi.versionName == null ? "null" : pi.versionName;  
               String versionCode = pi.versionCode + "";  
               infos.put("versionName", versionName);  
               infos.put("versionCode", versionCode);  
           }  
       } catch (NameNotFoundException e) {  
           Log.e(TAG, "�ռ��豸������Ϣ ", e);  
       }  
       Field[] fields = Build.class.getDeclaredFields();
       if(null != fields && fields.length > 0){
    	   for (Field field : fields) {  
               try {  
                   field.setAccessible(true);  
                   infos.put(field.getName(), field.get(null).toString());  
               } catch (Exception e) {  
                   Log.e(TAG, "�ռ��豸������Ϣ ", e);  
               }  
           }
       }
       return infos;
    } 
    
    //�����־
    public void clearBugInfo(){
    	try {
    		 File file = new File(FinalStr.LOGPATH+FinalStr.LOGINSUBMITBUG);
      	   	 if(file.exists()){ //�޸��Ƿ��ύBUG������
	      	   	FileOutputStream fos = new FileOutputStream(file);
	            fos.write("false".getBytes());
	            fos.close();  
      	   	 }
//      	   	 file = new File(FinalStr.LOGPATH+FinalStr.LOGNAME);
//      	     if(file.exists()){ //�޸��Ƿ��ύBUG������
//	      	   	file.delete();
//     	   	 }
		} catch (Exception e) {
			Log.e(FinalStr.TAG_ERR, e.getMessage());
		}
    }
	
	
}
