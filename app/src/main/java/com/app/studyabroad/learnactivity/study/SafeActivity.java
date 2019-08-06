package com.app.studyabroad.learnactivity.study;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.LoginActivity;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.SysUtil;

import org.json.JSONObject;

import java.io.IOException;

import app.com.studyabroad.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SafeActivity extends BaseActivity{
    private ApplicationUtil appcache; //缓存
    private EditText editPhone;
    private EditText editId;
    private TextView msgtitle; //标题
    private String status;
    private EditText editUseName;

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
        setContentView(R.layout.activity_safe);
        msgtitle = (TextView)findViewById(R.id.msgbartitle);
//		achieveList = (ExpandableListView)findViewById(R.id.achieve_list);
        msgtitle.setText("验证");
        mContext = this;
        editUseName = (EditText) findViewById(R.id.forget_usename_edit);
        editPhone = (EditText) findViewById(R.id.forget_phone_edit);
        editId = (EditText) findViewById(R.id.forget_id_edit);
    }

    private void intiData() {

    }
    public void next(View view){
        if(SysUtil.isBlank(editUseName.getText()+"") ||SysUtil.isBlank(editPhone.getText()+"") || SysUtil.isBlank(editId.getText()+"")){

            Toast.makeText(SafeActivity.this,R.string.msg_Empty, Toast.LENGTH_SHORT).show();
            return;
        }
        //验证
        yanzheng();

    }

    private void yanzheng() {
        Call<ResponseBody> call = serverApi.yanzheng(editUseName.getText().toString(),editPhone.getText().toString(),editId.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                if (response.isSuccessful()) { //请求视频信息成功
                    String json = null;
                    try {
                        json = response.body().string();
                        JSONObject object = new JSONObject(json);
                        status = object.getString("status");
                        String id = object.getString("id");
                        appcache.setYanId(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if("200".equals(status)){//验证通过
                        startActivity(new Intent(SafeActivity.this, ChangpswActivity.class));
                        finish();
                    } else if ("300".equals(status)) {//验证有误
                        Toast.makeText(SafeActivity.this,R.string.verify_sms_code_failed, Toast.LENGTH_SHORT).show();
                    }
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SafeActivity.this,R.string.verify_sms_code_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ordertabBack(View view){
        finish();
    }
}
