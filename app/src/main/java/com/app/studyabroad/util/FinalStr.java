package com.app.studyabroad.util;

import android.os.Environment;

public class FinalStr {

	public final static String LOG_TAG = "logtest";

	//测试机
	public final static String IP = "cr.scutde.net"; //ip219.136.9.94
	public final static String HTTP = "https://"; //http服务
	public final static String PORT = ""; //端口号
	public final static String PROJECTNAME = "cglx"; //项目名称
	public final static String ACCESSPRYPATH = HTTP+IP+"/"+PROJECTNAME;

	//本地测试
	/*public final static String IP = "192.168.1.34"; //ip219.136.9.94
	public final static String HTTP = "http://"; //http服务
	public final static String PORT = "8090"; //端口号
	public final static String PROJECTNAME = "hnjk-webapp-abroad"; //项目名称
	public final static String ACCESSPRYPATH = HTTP+IP+":"+PORT+"/"+PROJECTNAME;*/

    /**
	 * ???
	 */
	public final static String TAG_ERR = "err";
	public final static String TAG_DUG = "dug";
	public final static String TAG_INFO = "info";
	public final static String LOGNAME =  "IntentPTLog.log"; //???????
	public final static String LOGINSUBMITBUG = "LoginSbmitBug.txt";//?????bug
	public final static String LOGPATH =  Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/Android/data/com.IntnetLearnPlatform.data/" + "Log/";//????????
	public final static String EMAILPORT = "smtp.163.com";//????
	public final static String SENDEMAILMAN = "hnjk_test2@163.com";//??????
	public final static String SENDEMAILMANPSW = "hnjk654321";//??????????
	public final static String FETCHMAILMAN = "hnjk_test1@163.com";//?????
	
	//jpush ????hnjkjpush  ????hnjkapp
}
