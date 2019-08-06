package com.app.studyabroad.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.app.studyabroad.adapter.FloatFrame;
import com.app.studyabroad.adapter.FloatVideoLoad;
import com.app.studyabroad.entity.Rsa;
import com.app.studyabroad.jdbc.DBManagerToRsa;

public class ApplicationUtil extends Application {

	private Handler handler; //用于更新ui
	private String userid; //用户id
	private String userPsw;//缓存用户密码（加密）
	private String username; //用户名称
	private String stuid;
	private String userEmail;//email
	private String userMobile;//电话
	private String userXm;//中文名称
	private String courseId;//课程id
	private String mateid;//视频id
	private String classicname;//层次
	private int msgcount = 0; //消息数
	private int ordercount = 0; //预约数
	private String user; //用户idmobileKey
	private String mobileKey;//
	private String yanId;//验证修改密码通过后id
	private String usertype;//用户类型
	private Boolean isFisrt = false;//用户类型
	/**
	 * 用于悬浮在视频上面的view
	 */
	private View view;
	private WindowManager wm;
	private WindowManager.LayoutParams wmParams;
	private FloatFrame floatFv; //视频暂停/开始浮动对象
	private FloatVideoLoad floatLoad;//视频加载
	private Context c; //上下文对象
	private List<Activity> activityList = new LinkedList<Activity>();

	private Activity videoActivity;
	//private static final String TAG = "JPush";

	private DBManagerToRsa dbManager; //sqlite操作对象
	private Rsa rsa = new Rsa();//rsa 加密算法

	@Override
	public void onCreate() {
		/*CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());*/
		super.onCreate();
//		 Log.d(TAG, "[ExampleApplication] onCreate");
//         JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//         JPushInterface.init(this);     		// 初始化 JPush
//		initRsa(); //初始化Rsa加密算法
	}




	public Activity getVideoActivity() {
		return videoActivity;
	}

	public void setVideoActivity(Activity videoActivity) {
		this.videoActivity = videoActivity;
	}

	public void addActivity(Activity activity){
		if(!activityList.contains(activity)){
			activityList.add(activity);
		}
	}

	public Context getC() {
		return c;
	}

	public void setC(Context c) {
		this.c = c;
	}

	public FloatFrame getFloatFv() {
		return floatFv;
	}

	public void setFloatFv(FloatFrame floatFv) {
		this.floatFv = floatFv;
	}

	public WindowManager.LayoutParams getWmParams() {
		return wmParams;
	}

	public void setWmParams(WindowManager.LayoutParams wmParams) {
		this.wmParams = wmParams;
	}

	public WindowManager getWm() {
		return wm;
	}

	public void setWm(WindowManager wm) {
		this.wm = wm;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	// set方法
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	// get方法
	public Handler getHandler() {
		return handler;
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void exit() { //关闭所有活动的activity
		try {
			for (Activity activity : activityList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			onLowMemory();
			System.exit(0);
		}
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

	private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
	public WindowManager.LayoutParams getWindowParams() {
		return windowParams;
	}

	public FloatVideoLoad getFloatLoad() {
		return floatLoad;
	}

	public void setFloatLoad(FloatVideoLoad floatLoad) {
		this.floatLoad = floatLoad;
	}

	public Rsa getRsa() {
		return rsa;
	}

	public void setRsa(Rsa rsa) {
		this.rsa = rsa;
	}

	public String getUserPsw() {
		return userPsw;
	}

	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserXm() {
		return userXm;
	}

	public void setUserXm(String userXm) {
		this.userXm = userXm;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getMateid() {
		return mateid;
	}

	public void setMateid(String mateid) {
		this.mateid = mateid;
	}

	public String getClassicname() {
		return classicname;
	}

	public void setClassicname(String classicname) {
		this.classicname = classicname;
	}

	public List<Activity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}

	public void deThisActivity(Activity a){
		if(null != activityList && activityList.size() > 0){
			List<Activity> rmlist = new ArrayList<Activity>();
			for(Activity ac :activityList){
				if(a.getClass().getSimpleName().equals(ac.getClass().getSimpleName())){
					rmlist.add(ac);
				}
			}
			activityList.removeAll(rmlist);
			rmlist = null;
		}
	}

	public int getMsgcount() {
		return msgcount;
	}

	public void setMsgcount(int msgcount) {
		this.msgcount = msgcount;
	}

	public int getOrdercount() {
		return ordercount;
	}

	public void setOrdercount(int ordercount) {
		this.ordercount = ordercount;
	}

	public String getMobileKey() {
		return mobileKey;
	}
	public void setMobileKey(String mobileKey) {
		this.mobileKey = mobileKey;
	}
	public String getYanId() {
		return yanId;
	}
	public void setYanId(String yanId) {
		this.yanId = yanId;
	}

	public String getUserType() {
		return usertype;
	}
	public void setUserType(String usertype) {
		this.usertype = usertype;
	}

	public boolean getIsFrist() {
		return isFisrt;
	}
	public void setIsFrist(boolean isFisrt) {
		this.isFisrt = isFisrt;
	}
}
