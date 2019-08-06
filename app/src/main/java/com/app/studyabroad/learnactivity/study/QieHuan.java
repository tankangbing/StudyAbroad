package com.app.studyabroad.learnactivity.study;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.Exit;
import com.app.studyabroad.learnactivity.GridViewAdapter;
import com.app.studyabroad.learnactivity.MainActivity;
import com.app.studyabroad.learnactivity.databeen.CheckBeen;
import com.app.studyabroad.learnactivity.databeen.UserBeen;
import com.app.studyabroad.learnactivity.mysetting.Setting;
import com.app.studyabroad.util.ApplicationUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.studyabroad.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QieHuan extends BaseActivity{
private TextView msgtitle; //标题
private ListView checkView;
private ApplicationUtil appcache; //缓存
private String stuid;//学生id
private String result;
private CheckBeen checkBeen;
private List<CheckBeen.HalfYearListBean> halfYearList;
private List<CheckBeen.ThreeMonthListBean> threeMonthList;
private List<?> infos = new ArrayList<>();
private List<CheckBeen.AllListBean> allList;
    private UserBeen userBeen;//用户信息been
private int select = 0;
    private List<UserBeen.StudentListBean> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initView();
    intiData();
    }

    private void intiData() {
        appcache = (ApplicationUtil) getApplication();
        appcache.addActivity(this);
        String user = appcache.getUser();
        Gson gson = new Gson();
        userBeen = gson.fromJson(user, UserBeen.class);
        studentList = userBeen.getStudentList();

        CheckAdapter checkAdapter = new CheckAdapter();
        checkView.setAdapter(checkAdapter);
        checkView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                appcache.setStuid(studentList.get(i).getId());//保存学生id
                appcache.setUserXm(studentList.get(i).getName());//保存学生名字
                finish();
            }
        });
    }

    private void initView () {
        setContentView(R.layout.activity_qiehuan);
        msgtitle = (TextView) findViewById(R.id.msgbartitle);
        checkView = (ListView) findViewById(R.id.check_list);
        msgtitle.setText("选择子女");

    }

    class CheckAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return studentList.size();
        }

        @Override
        public Object getItem(int position) {
            return studentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            QieHuan.ViewHolder holder = null;
            if (convertView == null) {
                holder = new QieHuan.ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.qiehuan_item, null);
                convertView.setTag(holder);
//                holder.ivTitle = (TextView) convertView.findViewById(R.id.check_list_time_item);
                holder.tvName = (TextView) convertView.findViewById(R.id.qiehuan_name_tv);

                holder.tvTime = (TextView) convertView.findViewById(R.id.qiehuan_time_tv);
            } else {
                holder = (QieHuan.ViewHolder) convertView.getTag();
            }
//            holder.ivTitle.setText(time + "到课率");
            UserBeen.StudentListBean studentListBean = studentList.get(position);
            holder.tvName.setText(studentListBean.getName());
            holder.tvTime.setText(studentListBean.getStuNo());

            return convertView;
        }
    }
    class ViewHolder {
        public ImageView ivTitle;//时间
        public TextView tvName;//到课率
        public TextView tvTime;//迟到
    }
}
