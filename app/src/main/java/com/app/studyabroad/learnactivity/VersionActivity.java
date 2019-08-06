package com.app.studyabroad.learnactivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.service.VersionService;
import com.app.studyabroad.util.ApplicationUtil;

import app.com.studyabroad.R;

@SuppressLint("ShowToast")
public class VersionActivity extends Activity{
	private LinearLayout layout;
	private ApplicationUtil  app;
	TextView exitcontent;
	String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.version_dialog);
		initialize();
		setUpModule(getIntent().getExtras());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		return false;
	}
	
	public void nodownload(View v) {//�ݲ�����
		Intent it = new Intent( VersionActivity.this,VersionService.class);
		stopService(it);
    	this.finish();
    }
	
	public void download(View v) {  //����
		Intent it = new Intent(VersionActivity.this,VersionService.class);
		stopService(it);
		this.finish();
		try {
			//������������ļ�
			Intent intent= new Intent();        
		    intent.setAction("android.intent.action.VIEW");    
		    Uri content_url = Uri.parse(url);   
		    intent.setData(content_url);  
		    startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(VersionActivity.this, "�޷��������ص�ַ��", 500).show();
		}
    }

	public void initialize() {
		app = (ApplicationUtil) getApplication();
		app.addActivity(this);
	}

	public void setUpModule(Bundle b) {
		layout=(LinearLayout)findViewById(R.id.version_layout);
		layout.setOnClickListener(setOnClickListener());
		exitcontent = (TextView)findViewById(R.id.exitcontent);
		exitcontent.setText(Html.fromHtml(b.getString("content")));
		url = b.getString("url");
	}

	public void getTheData(Bundle bundle) {
		
	}

	public OnClickListener setOnClickListener() {
		OnClickListener lis = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "ƽ̨�汾���£�", 
						Toast.LENGTH_SHORT).show();	
			}
		};
		return lis;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		app.deThisActivity(VersionActivity.this);
	}
	
}
