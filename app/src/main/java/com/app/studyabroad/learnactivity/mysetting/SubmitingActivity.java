package com.app.studyabroad.learnactivity.mysetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.entity.User;
import com.app.studyabroad.jdbc.DBManagerToUser;
import com.app.studyabroad.learnactivity.LoginActivity;
import app.com.studyabroad.R;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.FinalStr;
import com.app.studyabroad.util.HttpUtil;
import com.app.studyabroad.util.StringToJson;
import com.app.studyabroad.util.SysUtil;

/**
 * 制作一个正在提交的提示
 * @author Amao
 *
 */

public class SubmitingActivity extends Activity{
	
	//接收数据
	private String userinfomationUid; //账号id
	private String  userinfomationOldPsw; //旧密码
	private String userinfomationNewPsw;//新密码
	
	private DBManagerToUser dbManager; //sqlite操作对象

	//全局参数
	private ApplicationUtil  app;
	
	//ui
	private TextView loadingTitle;
	
	//异步更新ui
	private Handler handler;
	
	private String result;//结果

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.loading);
		initialize();
		setUpModule();
		getTheData(getIntent().getExtras());
		submit();
   }
	
	public void submit(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Map<String,Object> map = new HashMap<String,Object>(0);
            	map.put("userinfomationUid", userinfomationUid);
            	map.put("userinfomationOldPsw", userinfomationOldPsw);
            	map.put("userinfomationNewPsw", userinfomationNewPsw);
            	TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
            	map.put("mobileKey",StringUtils.isNotBlank(TelephonyMgr.getDeviceId())?TelephonyMgr.getDeviceId():"0");
            	result = HttpUtil.getResultByPost(FinalStr.ACCESSPRYPATH+"/edu3/app/urlto/user-u-psw.html", 
            			map,SubmitingActivity.this,app.getRsa());
            	if(SysUtil.isBlank(result) || "NO_NET".equals(result)){
            		msg.what = 0;           		
				}else{
					Map<String, Object> maps = StringToJson.parseJSON2Map(result);
					if("200".equals(maps.get("status")+"")){
						msg.what = 1;
					}else{
						msg.what = 2; 
						Bundle bundle=new Bundle();  
		                bundle.putString("msg", maps.get("msg")+"");//提示信息
		                msg.setData(bundle);
					}
				}
            	SubmitingActivity.this.handler.sendMessage(msg);  
			}
		}).start();
	}

	public void initialize() {
		app = (ApplicationUtil) getApplication();
		app.addActivity(this);
	}

	@SuppressLint({ "ShowToast", "HandlerLeak" })
	public void setUpModule() {
		loadingTitle  = 		(TextView)this.findViewById(R.id.loadingTitle);
		loadingTitle.setText("正在提交数据...");		
		
		handler = new Handler() {  
			@Override
			public void handleMessage(Message msg) {  
	        	switch (msg.what) {
					case 0:
						Toast.makeText(getApplicationContext(), "数据获取异常，请确认网络是否连接正常.",500).show();
						SubmitingActivity.this.finish();
						break;
					case 1:
						Intent it  = new Intent(SubmitingActivity.this,LoginActivity.class);
						dbManager = new DBManagerToUser(getApplicationContext());
						List<User> listuser = dbManager.queryUser();
						if(null != listuser && listuser.size() > 0){
							dbManager.deleteUser();
						}
						dbManager.closeDB();
						Toast.makeText(getApplicationContext(), "修改密码成功,请重新登录",500).show();						
						startActivity(it);
						break;
					case 2:
						Toast.makeText(getApplicationContext(), msg.getData().getString("msg"),500).show();
						SubmitingActivity.this.finish();
						break;
				}       	
	        	super.handleMessage(msg);
	     }
		};
	}

	public void getTheData(Bundle bundle) {
		userinfomationUid 		= 		bundle.getString("userinfomationUid");
		userinfomationOldPsw 	= 		bundle.getString("userinfomationOldPsw");
		userinfomationNewPsw 	= 		bundle.getString("userinfomationNewPsw");
	}

	public OnClickListener setOnClickListener() {
		// TODO 自动生成的方法存根
		return null;
	}
}