package com.app.studyabroad.learnactivity;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.entity.User;
import com.app.studyabroad.jdbc.DBManagerToUser;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.SysUtil;

import app.com.studyabroad.R;

public class Exit extends Activity{
	private LinearLayout layout;
	private ApplicationUtil  app;
	private DBManagerToUser dbManager; //sqlite��������
	private String isClearPsw = "N";//�Ƿ����������
	
	private String exitTitle;
	private String exitConet;
	private TextView exit_title;
	private TextView exitcontent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog);
		initialize();
		getTheData(getIntent().getExtras());
		setUpModule();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void exitBtnNo(View v) {  
    	this.finish();    	
    }
	
	public void exitBtnOk(View v) {
		//������û�
		if("Y".equals(isClearPsw)){
			dbManager = new DBManagerToUser(getApplicationContext());
			List<User> listuser = dbManager.queryUser();
			if(null != listuser && listuser.size() > 0){
				dbManager.deleteUser();
			}
			dbManager.closeDB();
			Intent it = new Intent(Exit.this,LoginActivity.class);
			startActivity(it);
			finish();
		}else{
			//�ر�����activity
			app.exit();
			android.os.Process.killProcess(android.os.Process.myPid());
		}
      }

	public void initialize() {
		app = (ApplicationUtil) getApplication();
		app.addActivity(this);
	}

	public void setUpModule() {
		layout=(LinearLayout)findViewById(R.id.exit_layout);
		layout.setOnClickListener(setOnClickListener());
	}

	public void getTheData(Bundle bundle) {
		if(null != bundle){
			exit_title	 = (TextView)findViewById(R.id.exit_title);
			exitcontent	 = (TextView)findViewById(R.id.exitcontent);
			isClearPsw = SysUtil.isBlank(bundle.getString("isClearPsw")) ? "N" : bundle.getString("isClearPsw");
			exitTitle = bundle.getString("exitTitle");
			exitConet	= bundle.getString("exitConet");
			exit_title.setText(exitTitle);
			exitcontent.setText(exitConet);
		}
	}

	public OnClickListener setOnClickListener() {
		OnClickListener lis = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�", 
						Toast.LENGTH_SHORT).show();	
			}
		};
		return lis;
	}  
	
}
