package com.app.studyabroad.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.app.studyabroad.learnactivity.VersionActivity;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.FinalStr;
import com.app.studyabroad.util.HttpUtil;
import com.app.studyabroad.util.StringToJson;
import com.app.studyabroad.util.SysUtil;

@SuppressLint("HandlerLeak")
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class VersionService extends Service {

    final static String TAG = "[SERVICE]";
    public static final String BROADCASTACTION = "com.hnjk.test";
    Timer timer;
    int i = 0;

    Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0: //������
				//onDestroy();
        		break;
			case 1:
				ApplicationUtil app = (ApplicationUtil) getApplication();
				List<Activity> list = app.getActivityList();
				boolean isshow = true;
				if(null != list){
					for(Activity a : list){
						if(a.getClass().getSimpleName().equals(VersionActivity.class.getSimpleName())){
							isshow = false;
							break;
						}
					}
				}
				if(isshow){
					Intent intent1 = new Intent();
	            	intent1.setClass(VersionService.this,VersionActivity.class);
	            	intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            	Bundle b = new Bundle();
	            	b.putString("content", content(getversion(),msg.getData().getString("version"),msg.getData().getString("content")));
	            	b.putString("url", msg.getData().getString("url"));
	            	intent1.putExtras(b);
	            	startActivity(intent1);
				}
        		break;
			default:
				break;
			}
		}
    };

	private String content(String CtVersion,String newVersion,String updateMsg){
		StringBuffer content = new StringBuffer();
		content.append("当前版本：").append(CtVersion).append("    最新版本：").append(newVersion).append("<br/><br/>").append(updateMsg);
		return content.toString();
	}

	@Override
	public IBinder onBind(Intent paramIntent) {
		Log.d(TAG, "onBind");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(TAG, "onStart");
	}

	private String getversion(){
		PackageManager packageManager = getPackageManager();
    	PackageInfo packInfo;
    	String version = "δ֪";
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(),0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("version", getversion());
				String result = HttpUtil.getResultByPost(FinalStr.ACCESSPRYPATH
						+ "/edu3/app/urlto/search-version.html", map,
						VersionService.this, null);
				Message msg = new Message();
				if (SysUtil.isBlank(result) || "NO_NET".equals(result)) {//��ȡ��Ϣ�쳣
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
						}else{//��ǰ�����°汾
							msg.what = 0;
						}

					} else {//�쳣
						msg.what = 0;
					}
				}
				handler.sendMessage(msg);
			}
		}, 0, 3600* 1000);
	    return super.onStartCommand( intent, flags, startId );
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(null != timer){
			timer.cancel();
		}
		Log.d(TAG, "onDestroy");
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		Log.d(TAG, "onRebind");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged");
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.d(TAG, "onLowMemory");
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		super.onTaskRemoved(rootIntent);
		Log.d(TAG, "onTaskRemoved");
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		Log.d(TAG, "onTrimMemory");
	}

}