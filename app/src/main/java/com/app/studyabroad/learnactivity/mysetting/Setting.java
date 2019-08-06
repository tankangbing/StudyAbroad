package com.app.studyabroad.learnactivity.mysetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.Exit;
import com.app.studyabroad.learnactivity.GridViewAdapter;
import app.com.studyabroad.R;
import com.app.studyabroad.learnactivity.VersionActivity;
import com.app.studyabroad.learnactivity.alert.MyAlertDialog;
import com.app.studyabroad.learnactivity.study.ChangOlepswActivity;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.FinalStr;
import com.app.studyabroad.util.HttpUtil;
import com.app.studyabroad.util.StringToJson;
import com.app.studyabroad.util.SysUtil;

public class Setting extends Activity {

	private TextView msgtitle; //标题
	private ApplicationUtil  appcache; //缓存

	private Handler handler;


	public void setcache(){
		appcache = (ApplicationUtil) getApplication();
		appcache.addActivity(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_gridview_myinfo);
		setcache();
		msgtitle = (TextView)findViewById(R.id.msgbartitle);
		msgtitle.setText("系统设置");
		int[] resId = {R.drawable.updatepsw,R.drawable.exit,R.drawable.updatesys};
		int[] name = {R.string.mysetting_updatepsw,R.string.mysetting_exit,R.string.mysetting_sysupdate};
//		int[] resId = {R.drawable.exit,R.drawable.updatesys};
//		int[] name = {R.string.mysetting_exit,R.string.mysetting_sysupdate};

		ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < name.length; i++) {
		    HashMap<String, Object> map = new HashMap<String, Object>();
		    map.put("itemName", name[i]);
		    map.put("itemImage", resId[i]);
		    item.add(map);
		}
		GridViewAdapter simpleAdapter = new GridViewAdapter
		(this, item, R.layout.activity_main_item, new String[] {"itemName","itemImage"},
		        new int[] {R.id.itemName,R.id.itemImage}) {
		};
		GridView view = (GridView)findViewById(R.id.myinfo_gridview);
		view.setAdapter(simpleAdapter);
		view.setOnItemClickListener(itemclick);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0: //不必更新
					if(null != ad){
						ad.dismiss();
					}
					createAlert("当前版本("+getversion()+")为最新版本！","#ffffff",R.layout.custom_alert_err,R.id.alert_err_txt);
	        		break;
				case 1:
					if(null != ad){
						ad.dismiss();
					}
					Intent intent1 = new Intent();
	            	intent1.setClass(Setting.this,VersionActivity.class);
	            	intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            	Bundle b = new Bundle();
	            	b.putString("content", content(getversion(),msg.getData().getString("version"),msg.getData().getString("content")));
	            	b.putString("url", msg.getData().getString("url"));
	            	intent1.putExtras(b);
	            	startActivity(intent1);
	        		break;
				default:
					break;
				}
			}
	    };
	}

	MyAlertDialog ad = null;
	private void createAlert(String title,String color,int layout,int id){
		ad=new MyAlertDialog(Setting.this,title,color,layout,id);
		ad.show();
	}

	private String content(String CtVersion,String newVersion,String updateMsg){
    	StringBuffer content = new StringBuffer();
    	content.append("当前版本：").append(CtVersion).append("    最新版本：").append(newVersion).append("<br/>").append(updateMsg);
    	return content.toString();
    }

	//BadgeView badge1;
	private OnItemClickListener itemclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);
			switch (arg2) {
			case 0:
				startActivity(new Intent(Setting.this, ChangOlepswActivity.class));

				break;
			case 1:
				Intent intent = new Intent();
				intent.setClass(Setting.this,Exit.class);
				Bundle b = new Bundle();
				b.putString("exitTitle", "退出登录");
				b.putString("exitConet", "是否需要重新登录？\n");
				b.putString("isClearPsw", "Y");//是否清空密码
				intent.putExtras(b);
				startActivity(intent);
				break;
			case 2:
				if(null != ad){
					ad.dismiss();
				}
				ad=new MyAlertDialog(Setting.this,"正在检测版本,请稍等..","#ffffff",R.layout.custom_alert_loading,R.id.load_txt);
				ad.show();
				getdata();
				break;

			default:
				break;
			}
		}
	};

	private String getversion(){
		PackageManager packageManager = getPackageManager();
    	PackageInfo packInfo;
    	String version = "未知";
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(),0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	private void getdata(){
		new Thread(new Runnable() {

			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("version", getversion());
				String result = HttpUtil.getResultByPost(FinalStr.ACCESSPRYPATH
						+ "/edu3/app/urlto/search-version.html", map,
						Setting.this, null);
				Log.d("xx",result);
				Message msg = new Message();
				if (SysUtil.isBlank(result) || "NO_NET".equals(result)) {//获取消息异常
					msg.what = 0;
				} else {
					Map<String, Object> maps = StringToJson
							.parseJSON2Map(result);
					if (maps.containsKey("code")
							&& "1".equals(maps.get("code"))) {
						if(maps.containsKey("isHaveVersion") && "Y".equals(maps.get("isHaveVersion").toString())){
							Bundle bundle = new Bundle();
							bundle.putString("version", maps.get("version")+"");
							bundle.putString("content", maps.get("content")+"");
							bundle.putString("url", maps.get("url")+"");
							msg.setData(bundle);
							msg.what = 1;
						}else{//当前是最新版本
							msg.what = 0;
						}

					} else {//异常
						msg.what = 0;
					}
				}
				handler.sendMessage(msg);

			}
		}).start();
	}

	public void msgdetailBack(View v){
    	this.finish();
    }

}
