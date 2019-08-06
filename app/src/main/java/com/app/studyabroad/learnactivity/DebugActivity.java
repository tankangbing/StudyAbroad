package com.app.studyabroad.learnactivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.studyabroad.adapter.SendDebug;
import com.app.studyabroad.util.ApplicationUtil;

import app.com.studyabroad.R;

@SuppressLint("ShowToast")
public class DebugActivity extends Activity{
	private LinearLayout layout;
	private ApplicationUtil  app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_dialog);
		initialize();
		setUpModule();
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		return false;
	}
	
	public void dugButNo(View v) {
		new SendDebug(this).clearBugInfo();
		Intent it = new Intent();
		setResult(200, it); //intentΪA�����Ĵ���Bundle��intent����ȻҲ�����Լ������µ�Bundle
    	this.finish();    	
    }
	
	public void dugButOk(View v) {  //������Ϣ �����ʼ�
		if(new SendDebug(DebugActivity.this).sendEmail()){
			Toast.makeText(DebugActivity.this,"������Ϣ�ɹ���", 1000).show();
		}else{
			Toast.makeText(DebugActivity.this,"������Ϣʧ�ܣ�", 1000).show();
		}
		Intent it = new Intent();
		setResult(200, it); //intentΪA�����Ĵ���Bundle��intent����ȻҲ�����Լ������µ�Bundle
		this.finish();
    }

	public void initialize() {
		app = (ApplicationUtil) getApplication();
		app.addActivity(this);
	}

	public void setUpModule() {
		layout=(LinearLayout)findViewById(R.id.debug_layout);
		layout.setOnClickListener(setOnClickListener());
	}

	public void getTheData(Bundle bundle) {
		
	}

	public OnClickListener setOnClickListener() {
		OnClickListener lis = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "�����������Ƿ��ύ������Ϣ��", 
						Toast.LENGTH_SHORT).show();	
			}
		};
		return lis;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
