package com.app.studyabroad.learnactivity;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.app.studyabroad.entity.Rsa;
import com.app.studyabroad.entity.User;
import com.app.studyabroad.jdbc.DBManagerToRsa;
import com.app.studyabroad.jdbc.DBManagerToUser;
import com.app.studyabroad.learnactivity.alert.MyAlertDialog;
import com.app.studyabroad.learnactivity.databeen.UserBeen;
import com.app.studyabroad.learnactivity.study.CourseActivity;
import com.app.studyabroad.learnactivity.study.SafeActivity;
import com.app.studyabroad.service.VersionService;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.CodeSecurityUtil;
import com.app.studyabroad.util.CommonUtils;
import com.app.studyabroad.util.FileUtil;
import com.app.studyabroad.util.FinalStr;
import com.app.studyabroad.util.HttpUtil;
import com.app.studyabroad.util.StringToJson;
import com.app.studyabroad.util.SysUtil;
import app.com.studyabroad.R;
import com.app.studyabroad.learnactivity.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressLint("ShowToast")
public class LoginActivity extends BaseActivity{

	private Button loginBut; //登录按钮
	private EditText loginName,loginPsw; //用户名，密码
	private String result;//结果
	private Handler handler;
	private ApplicationUtil hdaplication;//全局参数对象
	private CheckBox rememberPsw; //记住密码按钮
	private Boolean isRememberPsw = false;//是否记住了密码
	private DBManagerToUser dbManager; //sqlite操作对象
	private User user = new User();
	private String loginType = "RSA";
	private List<User> listuser;
	private DBManagerToRsa dbManagerRsa; //sqlite操作对象
	private Rsa rsa = null;//rsa 加密算法
	public UserBeen userBeen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		/*基于极光推送的代码暂不删除*/
		//JPushInterface.init(getApplicationContext());//registerMessageReceiver();
		initialize();
		setUpModule();
//		//查看更新情况
		Intent it = new Intent(LoginActivity.this,VersionService.class);
		startService(it);
	}

	@Override
	public void onStart() {
		super.onStart();
		isAutomaticLogin();
//		if(isPopBugDialog()){
//			Message msg = new Message();
//			msg.what = 4;
//			LoginActivity.this.handler.sendMessage(msg);
//		}else{
//
//		}
	}
	//点击登录
	public OnClickListener setOnClickListener() {
		View.OnClickListener lis = new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if(CommonUtils.isTrueClick()){
					String mess = "正在登录中\n请稍等...";
					createAlert(mess,"#ffffff",R.layout.custom_alert_loading,R.id.load_txt);
					initRsa();
				}else{
					Toast.makeText(LoginActivity.this,"你点击的频率太频繁了", 500).show();
				}

			}
		};
		return lis;
	}
public void forget(View view){
	Intent intent = new Intent();
	intent.setClass(LoginActivity.this,SafeActivity.class);
	startActivity(intent);
//	Toast.makeText(LoginActivity.this,"忘记密码", Toast.LENGTH_SHORT).show();
}
	//rsa
	public void initRsa(){
		try {
			dbManagerRsa = new DBManagerToRsa(getApplicationContext());
			List<Rsa> listRsa = dbManagerRsa.queryRsa();
			if(null != listRsa && listRsa.size() > 0){
				rsa = listRsa.get(0);
				sendMessage(5,null);

			}else{ //从V3平台获取Rsa加密算法信息
				new Thread(new Runnable() {
					@Override
					public void run() {
						Map<String,Object> map = new HashMap<String,Object>(0);
						//获取手机唯一标识
						TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
						map.put("mobileKey",StringUtils.isNotBlank(TelephonyMgr.getDeviceId())?TelephonyMgr.getDeviceId():"0");
						String result = HttpUtil.getResultByPost(FinalStr.ACCESSPRYPATH+"/edu3/app/urlto/getRsaKey.html",
								map,getApplicationContext(),null);
						Map<String, Object> maps = StringToJson.parseJSON2Map(result);
						rsa = new Rsa();
						rsa.setId(1);
						rsa.setAppRsaModulus(maps.get("appRsaModulus")+"");
						rsa.setSysRsaModulus(maps.get("sysRsaModulus")+"");
						rsa.setAppRsaPublicExponent(maps.get("appRsaPublicExponent")+"");
						rsa.setSysRsaPrivateExponent(maps.get("sysRsaPrivateExponent")+"");
						dbManagerRsa.saveRsa(rsa);
						dbManagerRsa.closeDB();

						if (maps.size() != 0){
							sendMessage(5,null);
						}else{
							sendMessage(3,null);
						}

					}
				}).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("HandlerLeak")
	public void setUpModule() {
		this.loginBut = (Button)this.findViewById(R.id.login_login_btn);
		this.loginName = (EditText)this.findViewById(R.id.login_user_edit);
		this.loginPsw = (EditText)this.findViewById(R.id.login_passwd_edit);
		rememberPsw = (CheckBox)findViewById(R.id.rememberPsw);
		rememberPsw.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					isRememberPsw = true;
				}else{
					isRememberPsw = false;
				}
			}
		});
		loginBut.setOnClickListener(setOnClickListener());
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 0:
						createAlert("登录异常，请检查你的网\n络连接或者联系我们！","#ffffff",R.layout.custom_alert_err,R.id.alert_err_txt);
						break;
					case 1://登录成功
                        hdaplication.setUser(result);//保存用户信息
						hdaplication.setUserid(msg.getData().getString("userid"));
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this,CourseActivity.class);
						Bundle bundle=new Bundle();
						bundle.putString("result",result);
//						bundle.putInt("type", 1);
//						//保存用户信息到缓存
						hdaplication.setUserid(msg.getData().getString("userid"));
//						hdaplication.setStuid(msg.getData().getString("stuid"));
//						hdaplication.setUserEmail(msg.getData().getString("useremail"));
//						hdaplication.setUserMobile(msg.getData().getString("usermobile"));
//						hdaplication.setUserXm(msg.getData().getString("userxm"));
//						hdaplication.setClassicname(msg.getData().getString("classicname"));
//						hdaplication.setUsername(loginName.getText()+"");
						//是否需要记住密码
						if(isRememberPsw){
							try {
								if(null != listuser && listuser.size() > 0){
									dbManager.updateUser(1, loginName.getText()+"", CodeSecurityUtil.getMD5(loginPsw.getText()+""));
								}else{
									user.setId(1);
									user.setUserName(loginName.getText()+"");
									user.setUserPsw(CodeSecurityUtil.getMD5(loginPsw.getText()+""));
									Log.d("XX",loginName.getText() + "!!! " + loginPsw.getText() + "!!!!" +CodeSecurityUtil.getMD5(loginPsw.getText()+""));
									dbManager.saveUser(user);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						intent.putExtras(bundle);
						startActivity(intent);
						break;
					case 2:
						if (ad != null){
							ad.dismiss();
						}

						if(null != listuser && listuser.size() > 0){
							rememberPsw.setChecked(false);
							dbManager.deleteUser();
							loginType = "RSA";
						}

						String mes2 = StringUtils.isNotBlank(msg.getData().getString("msg")) ? msg.getData().getString("msg") : "验证信息错误,\n请输入正确的信息！";
						createAlert(mes2,"#ffffff",R.layout.custom_alert_err,R.id.alert_err_txt);
						break;
					case 3:
						createAlert("获取数据异常！","#ffffff",R.layout.custom_alert_err,R.id.alert_err_txt);
						ad.dismiss();
						break;
					case 4://有bug时先弹出bug提示框
						Intent intent1 = new Intent();
						intent1.setClass(LoginActivity.this,DebugActivity.class);
						startActivityForResult(intent1, 0);
						break;
					case 5:

						hdaplication.setRsa(rsa);

						user.setUserName(loginName.getText()+"");
						user.setUserPsw(loginPsw.getText()+"");
						if(SysUtil.isBlank(user.getUserName()) || SysUtil.isBlank(user.getUserPsw())){
							createAlert("在线学习帐号或者密码不能\n为空，请输入后再登录！","#ffffff",R.layout.custom_alert_err,R.id.alert_err_txt);
						}else{
							try {
								getResult();
							} catch (Exception e) {
								e.printStackTrace();
								Log.e(FinalStr.TAG_ERR, "登录线程异常！");
							}

						}

						break;
				}
				super.handleMessage(msg);
			}
		};
	}

	public void getResult() throws InterruptedException, NoSuchAlgorithmException{
		//是否记住了密码
		if(isRememberPsw){ //记住密码
			//查看用户表里是否有数据
			if(null != listuser && listuser.size() > 0){
				loginType = "MD5";
				user = listuser.get(0);
			}
		}else{
			user.setUserName(loginName.getText()+"");
			user.setUserPsw(loginPsw.getText()+"");
			if(null != listuser && listuser.size() > 0){
				dbManager.deleteUser();
			}
		}
		Thread t = getThread();
		t.start();
		t.join();
	}

	@SuppressWarnings("finally")
	public Thread getThread(){
		Thread t = null;
		try {
			t = new Thread(new Runnable() {
				@Override
				public void run() {
					Map<String,Object> map = new HashMap<String,Object>(0);
					map.put("username",user.getUserName() );
					map.put("password", user.getUserPsw());
					map.put("type", loginType);
					TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
					String mobileKey = StringUtils.isNotBlank(TelephonyMgr.getDeviceId()) ? TelephonyMgr.getDeviceId() : "0";
					map.put("mobileKey",mobileKey);
					hdaplication.setMobileKey(mobileKey);
					Log.d("XX",user.getUserName() + " " + user.getUserPsw() + " "+rsa);
					String url = FinalStr.ACCESSPRYPATH + "/edu3/app/urlto/avoidclose/login-ajax.html";
					result = HttpUtil.getResultByPost(url,
							map,LoginActivity.this,rsa);
					if(SysUtil.isBlank(result) || "NO_NET".equals(result)){
						sendMessage(0,null);
					}else{
						Log.d("XX",result);
						Map<String, Object> maps = StringToJson.parseJSON2Map(result);
						if(!SysUtil.isBlank(maps.get("status")+"") && maps.containsKey("code") && "1".equals(maps.get("code"))){
							Bundle bundle=new Bundle();
							if("200".equals(maps.get("status")+"")){//登陆成功
								bundle.putString("stuid", maps.get("stuid")+"");
								bundle.putString("userid", maps.get("userid")+"");
								sendMessage(1,bundle);
							}else if("300".equals(maps.get("status")+"")){
								bundle.putString("msg", maps.get("msg")+"");
								sendMessage(2,bundle);
							}else{
								sendMessage(3,null);
							}
						}else{
							sendMessage(3,null);
						}
					}
				}
			});
		} catch (Exception e) {
			Log.e(FinalStr.TAG_ERR, "登录线程错误！");
		}finally{
			return t;
		}
	}

	public void isAutomaticLogin(){
		dbManagerRsa = new DBManagerToRsa(getApplicationContext());
		List<Rsa> listRsa = dbManagerRsa.queryRsa();
		if(null != listRsa && listRsa.size() > 0){
			rsa = listRsa.get(0);
		}
		try {
			dbManager = new DBManagerToUser(getApplicationContext());
			listuser = dbManager.queryUser();
			if(null != listuser && listuser.size() > 0){
				rememberPsw.setChecked(true);
				user = listuser.get(0);
				loginName.setText("");
				loginName.setText(user.getUserName());
				loginType = "MD5";
				Thread t = getThread();
				t.start();
				t.join();
			}else{
				rememberPsw.setChecked(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("登录","登录检验是否记住密码错误");
		}
	}

	@SuppressWarnings("finally")
	public boolean isPopBugDialog(){
		boolean b = false;
		try {
			File file = new File(FinalStr.LOGPATH+FinalStr.LOGINSUBMITBUG);
			if(!file.exists()) b = false;
			else{
				String str = FileUtil.readFileByChars(file);
				if("true".equals(str)){
					b = true;
				}else{
					b = false;
				}
			}
		} catch (Exception e) {
			b = false;
		}finally{
			return b;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
//        	hdaplication.exit();
			finish();
		}
		return false;
	}


	@Override
	protected void onResume() {
		//JPushInterface.onResume(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		//JPushInterface.onPause(this);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		Intent it = new Intent(LoginActivity.this,VersionService.class);
		stopService(it);
		super.onDestroy();
		dbManager.closeDB();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
			case 200://提交dug后返回
				isAutomaticLogin();
				break;
		}
	}

	public void initialize() {
		hdaplication = (ApplicationUtil) getApplication();
		hdaplication.addActivity(this);

	}

	MyAlertDialog ad = null;
	private void createAlert(String title,String color,int layout,int id){
		ad=new MyAlertDialog(LoginActivity.this,title,color,layout,id);
		ad.show();
	}



	private void sendMessage(int str,Bundle b){
		Message msg = new Message();
		msg.what = str;
		if(null != b){
			msg.setData(b);
		}
		LoginActivity.this.handler.sendMessage(msg);
	}

	public void getTheData(Bundle bundle) {

	}



	@Override
	public void onStop() {
		super.onStop();
		if(null != ad){
			ad.dismiss();
		}
	}


}
