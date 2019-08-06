package com.app.studyabroad.learnactivity.study;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.Exit;
import com.app.studyabroad.learnactivity.databeen.UserBeen;
import com.app.studyabroad.learnactivity.fragment.HomeFrament;
import com.app.studyabroad.learnactivity.fragment.XinXiFrament;
import com.app.studyabroad.util.ApplicationUtil;
import com.google.gson.Gson;

import app.com.studyabroad.R;

public class CourseActivity extends AppCompatActivity implements View.OnClickListener{
    private HomeFrament homeFrament = null;
    private XinXiFrament xinXiFrament = null;
    private TextView home_tv;
    private TextView my_index_tv;
    private ImageView home_img;
    private ImageView my_index_img;
    private ApplicationUtil appcache; //缓存
    private UserBeen userBeen;//用户信息been
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        home_tv = (TextView) findViewById(R.id.home_tv);
        my_index_tv = (TextView) findViewById(R.id.my_index_tv);
        home_img = (ImageView) findViewById(R.id.home_img);
        my_index_img = (ImageView) findViewById(R.id.my_index_img);
        LinearLayout home_tab = (LinearLayout) findViewById(R.id.home_tab);
        LinearLayout my_index_tab = (LinearLayout) findViewById(R.id.my_index_tab);
        home_tab.setOnClickListener(this);
        my_index_tab.setOnClickListener(this);
        setcache();
        initView();
        initData();
    }


    public void setcache(){
        appcache = (ApplicationUtil) getApplication();
        appcache.addActivity(this);
    }
    private void initView() {
        homeFrament = new HomeFrament();
        xinXiFrament = new XinXiFrament();
        updateTabBarSelectedIndex(0);
    }
    private void initData() {
        String user = appcache.getUser();
        Gson gson = new Gson();
        userBeen = gson.fromJson(user, UserBeen.class);
        String userxm = userBeen.getUserxm();
        String usertype = userBeen.getUsertype();
        String stuid = userBeen.getStuid();//学生id
        if("students".equals(usertype)){//学生
            appcache.setStuid(stuid);//保存学生id
//            studentTv.setText("当前学生："+userxm);
        }else {//家长
//            startActivity(new Intent(CourseActivity.this, QieHuan.class));
//            tyle = true;
//            studentList = userBeen.getStudentList();
//            student.setVisibility(View.VISIBLE);
        }
    }
    private void updateTabBarSelectedIndex(int index) {
        String titles[] = new String[] { "首页", "信息" };

        Fragment fragmentArray[] = new Fragment[] { homeFrament, xinXiFrament };

        TextView tvs[] = new TextView[] { home_tv, my_index_tv };
        ImageView imgs[] = new ImageView[] { home_img, my_index_img };

        int imgResOff[] = new int[] {
                R.drawable.icon_home1,
                R.drawable.icon_info1
        };
        int imgResOn[] = new int[] {
                R.drawable.icon_home0,
                R.drawable.icon_info0
        };

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        for (int i = 0; i < imgs.length; ++i) {
            Fragment fragment = fragmentArray[i];

            if (i == index) {
                imgs[i].setImageResource(imgResOn[i]);
                tvs[i].setTextColor(getResources().getColor(R.color.colorPrimary));

                if (!fragment.isAdded()) {
                    ft.add(R.id.course_fl, fragment);
                } else {
                    ft.show(fragment);
                }
            } else {
                imgs[i].setImageResource(imgResOff[i]);
                tvs[i].setTextColor(getResources().getColor(R.color.color_999999));

                if(fragment.isAdded()){
                    ft.hide(fragment);
                }
            }
        }

        ft.commit();
    }
    public void onClick(View view) {
        int tabArray[] = new int[] { R.id.home_tab, R.id.my_index_tab };
        int index = 0;

        for (int i = 0; i < tabArray.length; ++i) {
            if (view.getId() == tabArray[i]) {
                index = i;
                break;
            }
        }

        updateTabBarSelectedIndex(index);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  //获取 back键
            Intent intent = new Intent();
            intent.setClass(CourseActivity.this,Exit.class);
            Bundle b = new Bundle();
            b.putString("exitTitle", "退出系统");
            b.putString("exitConet", "是否要退出系统？\n");
            intent.putExtras(b);
            startActivity(intent);
        }
        return false;
    }
}
