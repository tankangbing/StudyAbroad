package com.app.studyabroad.adapter;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import app.com.studyabroad.R;


public class AlertDialog {
	Context context;
	android.app.AlertDialog ad;
	TextView titleView;
	TextView messageView;
	Button alertmsgSure;
	Button alertmsgCancel;
	private int type;
	
	public AlertDialog(Context context) {
		this.context=context;
		ad=new android.app.AlertDialog.Builder(context).create();
		ad.show();
		//�ؼ������������,ʹ��window.setContentView,�滻�����Ի��򴰿ڵĲ���
		Window window = ad.getWindow();
		window.setContentView(R.layout.alert_msg);
		titleView=(TextView)window.findViewById(R.id.alertmsgTitle);
		messageView=(TextView)window.findViewById(R.id.alertmsgContent);
		alertmsgSure=(Button)window.findViewById(R.id.alertmsgSure);
		alertmsgCancel = (Button)window.findViewById(R.id.alertmsgCancel);
	}
	
	public void setAlertmsgSureOnclick(View.OnClickListener listener){
		alertmsgSure.setOnClickListener(listener);
	}
	
	public void setAlertmsgCancelOnclick(View.OnClickListener listener){
		alertmsgCancel.setOnClickListener(listener);
	}
	
	public void setTitle(int resId)
	{
		titleView.setText(resId);
	}
	
	public void setTitle(String title) {
		titleView.setText(title);
	}
	
	public void setMessage(int resId) {
		messageView.setText(resId);
	}
 
	public void setMessage(String message)
	{
		messageView.setText(message);
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void alertmsgCancel(View v){
		this.type = 2;
	}
	
	public void alertmsgSure(View v){
		this.type = 1;
	}
	
	/**
	* �رնԻ���
	*/
	public void dismiss() {
		ad.dismiss();
	}
}