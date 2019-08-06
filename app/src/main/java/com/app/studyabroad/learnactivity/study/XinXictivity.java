package com.app.studyabroad.learnactivity.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.SysUtil;

import org.json.JSONException;
import org.json.JSONObject;

import app.com.studyabroad.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XinXictivity extends BaseActivity{
    private ApplicationUtil appcache; //缓存
    private TextView msgtitle; //标题
    private String status;
    private String stuid;//学生id
    private TextView xinxi_stuNo;
    private TextView xinxi_idcard;
    private TextView xinxi_gender;
    private TextView xinxi_grade;
    private TextView xinxi_major;
    private TextView xinxi_classic;
    private TextView xinxi_name;
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
        stuid = appcache.getStuid();//
    }
    private void initView() {
        setContentView(R.layout.activity_xinxi);
        msgtitle = (TextView)findViewById(R.id.msgbartitle);
        xinxi_name = (TextView)findViewById(R.id.xinxi_name);
        xinxi_stuNo = (TextView)findViewById(R.id.xinxi_stuNo);
        xinxi_idcard = (TextView)findViewById(R.id.xinxi_idcard);
        xinxi_gender = (TextView)findViewById(R.id.xinxi_gender);
        xinxi_grade = (TextView)findViewById(R.id.xinxi_grade);
        xinxi_major = (TextView)findViewById(R.id.xinxi_major);
        xinxi_classic = (TextView)findViewById(R.id.xinxi_classic);
        msgtitle.setText("个人信息");
        mContext = this;

    }

    private void intiData() {

        Call<ResponseBody> call = serverApi.getInfos(stuid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                if (response.isSuccessful()) { //请求视频信息成功
                    String json = null;
                    try {
                        json = response.body().string();
                        JSONObject object = new JSONObject(json);

                        setText(object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(XinXictivity.this,R.string.verify_sms_code_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setText(JSONObject object) {
        try {
            String idCard = object.getString("idCard");
            String stuNo = object.getString("stuNo");
            String grade = object.getString("grade");
            String gender = object.getString("gender");
            String classic = object.getString("classic");
            String major = object.getString("major");
            String name = object.getString("name");
            xinxi_stuNo.setText(stuNo);
            xinxi_idcard.setText(idCard);
            xinxi_gender.setText(gender);
            xinxi_grade.setText(grade);
            xinxi_major.setText(major);
            xinxi_classic.setText(classic);
            xinxi_name.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void ordertabBack(View view){
        finish();
    }

}
