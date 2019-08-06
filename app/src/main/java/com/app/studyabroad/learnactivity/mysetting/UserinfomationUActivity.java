package com.app.studyabroad.learnactivity.mysetting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import app.com.studyabroad.R;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.SysUtil;

/**
 * 修改个人信息
 * @author Amao
 *
 */
public class UserinfomationUActivity extends Activity{
	
	//界面ui
	private EditText userinfomationUOldPsw;//旧密码
	private EditText userinfomationUNewPsw;//新密码
	private EditText userinfomationUAgNewPsw;//确认新密码
	
	//全局参数
	private ApplicationUtil  app;
	
	//用于异步更新UI
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinformation_update);
		initialize();
		setUpModule();
	}

	public void userinfomationSubmit(View v){
		checkInfo();
	}
	
	public void checkInfo(){
		Message msg = new Message();
		Bundle b = new Bundle();
		String userOldPsw 		= 	getUserinfomationUOldPsw();
		String userNewPsw 		= 	getUserinfomationUNewPsw();
		String userAgNewPsw 	= 	getUserinfomationUAgNewPsw();
		if(SysUtil.isBlank(userOldPsw)){
			msg.what = 1;
			b.putString("nodata", "oldPsw");
		}else if(SysUtil.isBlank(userNewPsw)){
			msg.what = 1;
			b.putString("nodata", "newPsw");
		}else if(SysUtil.isBlank(userAgNewPsw)){
			msg.what = 1;
			b.putString("nodata", "agnewPsw");
		}else if(SysUtil.isTheSame(userNewPsw, userOldPsw)){
			msg.what = 2;
		}else if(!SysUtil.isTheSame(userNewPsw, userAgNewPsw)){
			msg.what = 3;
		}
		msg.setData(b);
		UserinfomationUActivity.this.handler.sendMessage(msg); 
	}
	
	//获取填入的值  （get）开始
	private String getUserinfomationUOldPsw() {
		return userinfomationUOldPsw.getText()+"";
	}

	private String getUserinfomationUNewPsw() {
		return userinfomationUNewPsw.getText()+"";
	}

	private String getUserinfomationUAgNewPsw() {
		return userinfomationUAgNewPsw.getText()+"";
	}
	//（get）结束
	
	public void userinfomationUBack(View v){
		UserinfomationUActivity.this.finish();
	}

	public void initialize() {
		app = (ApplicationUtil) getApplication();
		app.addActivity(this);
	}

	@SuppressLint("HandlerLeak")
	public void setUpModule() {
		userinfomationUOldPsw 		= 	(EditText)this.findViewById(R.id.userinfomationUOldPsw);
		userinfomationUNewPsw 		= 	(EditText)this.findViewById(R.id.userinfomationUNewPsw);
		userinfomationUAgNewPsw = 	(EditText)this.findViewById(R.id.userinfomationUAgNewPsw);
		handler = new Handler() {  
			@Override
			public void handleMessage(Message msg) {  
	        	switch (msg.what) {
		        	case 0: //正常
		        		Intent intent = new Intent();
		                intent.setClass(UserinfomationUActivity.this,SubmitingActivity.class);
		                Bundle bundle=new Bundle();
		                bundle.putString("userinfomationUid", app.getUserid());
		                bundle.putString("userinfomationOldPsw", getUserinfomationUOldPsw());
		                bundle.putString("userinfomationNewPsw", getUserinfomationUNewPsw());
		                intent.putExtras(bundle);
		                startActivity(intent); 
		    			break;
		    		case 1: //有数据未填写
		    			String data = msg.getData().getString("nodata");
		    			if("oldPsw".equals(data)){
		    				userinfomationUOldPsw.setHint("旧密码不能为空");
		    			}else if("newPsw".equals(data)){
		    				userinfomationUNewPsw.setHint("新密码不能为空");
		    			}else if("agnewPsw".equals(data)){
		    				userinfomationUAgNewPsw.setHint("确认密码不能为空");
		    			}
		    			break;
		    		case 2: //旧密码和新密码一致
		    			userinfomationUNewPsw.setText("");
		    			userinfomationUNewPsw.setHint("新密码不能和旧密码相同");
		    			break;
		    		case 3: //新密码未确认
		    			userinfomationUAgNewPsw.setText("");
		    			userinfomationUAgNewPsw.setHint("新密码和确认密码不一致");
		    			break;
	        	}
	        	super.handleMessage(msg);
			}
		};
	}

	public void getTheData(Bundle bundle) {
		// TODO 自动生成的方法存根
		
	}

	public OnClickListener setOnClickListener() {
		// TODO 自动生成的方法存根
		return null;
	}
	
}
