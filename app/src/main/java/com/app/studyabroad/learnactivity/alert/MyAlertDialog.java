package com.app.studyabroad.learnactivity.alert;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.com.studyabroad.R;


public class MyAlertDialog {
	Context context;
	android.app.AlertDialog ad;
	TextView titleView;
	TextView messageView;
	Button alertmsgSure;
	Button alertmsgCancel;
	LinearLayout alertmsgMain;
	
	public MyAlertDialog(Context context) {
		this.context=context;
		ad=new android.app.AlertDialog.Builder(context).create();
		ad.show();
		Window window = ad.getWindow();
		window.setContentView(R.layout.custom_alert_msg);
		titleView=(TextView)window.findViewById(R.id.alertmsgTitle);
		messageView=(TextView)window.findViewById(R.id.alertmsgContent);
		alertmsgSure=(Button)window.findViewById(R.id.alertmsgSure);
		alertmsgCancel = (Button)window.findViewById(R.id.alertmsgCancel);
		alertmsgMain = (LinearLayout)window.findViewById(R.id.alertmsgMain);
		alertmsgMain.setOnClickListener(viewlistener);
	}
	
	public MyAlertDialog(Context context, String str, String color, int layout, int txtid) {
		this.context=context;
		ad=new android.app.AlertDialog.Builder(context).create();
		ad.show();
		Window window = ad.getWindow();
		window.setContentView(layout);
		TextView load_txt = (TextView)window.findViewById(txtid);
		load_txt.setText(str);
		load_txt.setTextColor(Color.parseColor(color));
	}
	
	public MyAlertDialog(Context context, String title, int titleid, String content, int contentid, int layout, int butid) {
		this.context=context;
		ad=new android.app.AlertDialog.Builder(context).create();
		ad.show();
		Window window = ad.getWindow();
		window.setContentView(layout);
		TextView titletxt = (TextView)window.findViewById(titleid); //标题
		titletxt.setText(title);
		TextView contenttxt = (TextView)window.findViewById(contentid); //内容
		contenttxt.setText(Html.fromHtml(content));
		Button closebt = (Button)window.findViewById(butid);
		closebt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ad.dismiss();
			}
		});
	}
		
	private OnClickListener viewlistener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Toast.makeText(context, "点击提示框外部可关闭", 200).show();
		}
	};
	
	public void setAlertmsgSureOnclick(OnClickListener listener,String title){
		alertmsgSure.setText(title);
		alertmsgSure.setOnClickListener(listener);
	}
	
	public void setAlertmsgCancelOnclick(OnClickListener listener,String title){
		alertmsgCancel.setText(title);
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
		messageView.setText(Html.fromHtml(message));
	}
	
	public void show(){
		ad.show();
	}

	public void dismiss() {
		ad.dismiss();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	
}