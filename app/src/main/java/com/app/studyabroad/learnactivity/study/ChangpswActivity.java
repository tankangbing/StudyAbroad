package com.app.studyabroad.learnactivity.study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.entity.Rsa;
import com.app.studyabroad.jdbc.DBManagerToRsa;
import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.DebugActivity;
import com.app.studyabroad.learnactivity.LoginActivity;
import com.app.studyabroad.learnactivity.MainActivity;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.CodeSecurityUtil;
import com.app.studyabroad.util.FinalStr;
import com.app.studyabroad.util.HttpUtil;
import com.app.studyabroad.util.StringToJson;
import com.app.studyabroad.util.SysUtil;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.studyabroad.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangpswActivity extends BaseActivity{
    private ApplicationUtil appcache; //缓存
    private EditText editPsw;
    private EditText editPsw2;
    private TextView msgtitle; //标题
    private String status;
    private String pwd1;
    private String pwd2;
    private String result;
    private String mobileKey;
    private Rsa rsa;
    private String loginType = "RSA";
    private DBManagerToRsa dbManagerRsa; //sqlite操作对象
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setcache();
        intiData();
    }
    public void setcache(){
        appcache = (ApplicationUtil) getApplication();
        appcache.addActivity(this);
    }
    private void initView() {
        setContentView(R.layout.activity_changpsw);
        msgtitle = (TextView)findViewById(R.id.msgbartitle);
//		achieveList = (ExpandableListView)findViewById(R.id.achieve_list);
        msgtitle.setText("修改密码");
        mContext = this;
        editPsw = (EditText) findViewById(R.id.cpsw_newpsw_edit);
        editPsw2 = (EditText) findViewById(R.id.cpsw_newpsw_edit2);
    }

    private void intiData() {
//        mobileKey = appcache.getMobileKey();
        rsa = appcache.getRsa();

        //登录成功
//保存用户信息
//						bundle.putInt("type", 1);
//						//保存用户信息到缓存
//						hdaplication.setStuid(msg.getData().getString("stuid"));
//						hdaplication.setUserEmail(msg.getData().getString("useremail"));
//						hdaplication.setUserMobile(msg.getData().getString("usermobile"));
//						hdaplication.setUserXm(msg.getData().getString("userxm"));
//						hdaplication.setClassicname(msg.getData().getString("classicname"));
//						hdaplication.setUsername(loginName.getText()+"");
//是否需要记住密码
//有bug时先弹出bug提示框
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
//                        createAlert("登录异常，请检查你的网\n络连接或者联系我们！","#ffffff", R.layout.custom_alert_err,R.id.alert_err_txt);
                        break;
                    case 1://登录成功
                        Toast.makeText(getApplicationContext(),R.string.password_Success,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
//                        intent.setClass(LoginActivity.this,MainActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("result",result);

                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),R.string.password_Failure,Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
//                        createAlert("获取数据异常！","#ffffff",R.layout.custom_alert_err,R.id.alert_err_txt);
//                        ad.dismiss();
                        break;
                    case 4://有bug时先弹出bug提示框
                        Intent intent1 = new Intent();
//                        intent1.setClass(LoginActivity.this,DebugActivity.class);
                        startActivityForResult(intent1, 0);
                        break;
                    case 5:
                      yanzheng();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }
    public void next(View view){
        initRsa();
    }
    private boolean checkInput() {
        pwd1 = editPsw.getText().toString();
        pwd2 = editPsw2.getText().toString();

        if (!SysUtil.isValidPassword(pwd1) || !SysUtil.isValidPassword(pwd2)) {
            Toast.makeText(ChangpswActivity.this,R.string.input_valid_password, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!StringUtils.equals(pwd1, pwd2)) {
            Toast.makeText(ChangpswActivity.this,R.string.two_password_different, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void yanzheng() {
        if (!checkInput()) {
            return;
        }
        final TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        final String MobileKey = StringUtils.isNotBlank(TelephonyMgr.getDeviceId()) ? TelephonyMgr.getDeviceId() : "0";
//        Call<ResponseBody> call = serverApi.reset(appcache.getYanId(),editPsw.getText().toString(),StringUtils.isNotBlank(TelephonyMgr.getDeviceId()) ? TelephonyMgr.getDeviceId() : "0");
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
//
//                if (response.isSuccessful()) { //请求视频信息成功
//                    String json = null;
//                    try {
//                        json = response.body().string();
//                        JSONObject object = new JSONObject(json);
//                        status = object.getString("status");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if("200".equals(status)){//x修改成功
//                        Toast.makeText(ChangpswActivity.this,R.string.password_Success, Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else if ("300".equals(status)) {//验证有误
//                        Toast.makeText(ChangpswActivity.this,R.string.password_Failure, Toast.LENGTH_SHORT).show();
//                    }
//                    finish();
//                }
//
//            }
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(ChangpswActivity.this,R.string.password_Failure, Toast.LENGTH_SHORT).show();
//            }
//        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap<String, Object>(0);
                map.put("id", appcache.getYanId());
                map.put("password", editPsw.getText());
                map.put("type", loginType);
                map.put("mobileKey", MobileKey);
                Log.d("XX", mobileKey + " " + rsa);
                String url = FinalStr.ACCESSPRYPATH + "/edu3/app/change/pwd.html";
                result = HttpUtil.getResultByPost(url,
                        map, ChangpswActivity.this, rsa);
                if (SysUtil.isBlank(result) || "NO_NET".equals(result)) {
//                    sendMessage(0,null);
                } else {
                    Log.d("XX", result);
                    Map<String, Object> maps = StringToJson.parseJSON2Map(result);

                    if ("200".equals(maps.get("status") + "")) {//修改成功


//                            bundle.putString("stuid", maps.get("stuid") + "");
//                            sendMessage(1,bundle);
                    } else if ("300".equals(maps.get("status") + "")) {

//                            bundle.putString("msg", maps.get("msg") + "");
//                            sendMessage(2,bundle);
                    }
                }
                finish();
            }
        }).start();
    }


    public void ordertabBack(View view){
        finish();
    }
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
    private void sendMessage(int str,Bundle b){
        Message msg = new Message();
        msg.what = str;
        if(null != b){
            msg.setData(b);
        }
        ChangpswActivity.this.handler.sendMessage(msg);
    }
}