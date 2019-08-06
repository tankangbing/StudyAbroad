package com.app.studyabroad.learnactivity.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.LoginActivity;
import com.app.studyabroad.learnactivity.databeen.CheckBeen;
import com.app.studyabroad.learnactivity.databeen.UserBeen;
import com.app.studyabroad.util.ApplicationUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import app.com.studyabroad.R;

public class SplashActivity extends BaseActivity{


    private ImageView imageView;
    private AlphaAnimation mA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initView();
    intiData();
    }

    private void intiData() {
        // 透明度
        mA = new AlphaAnimation(0,1);
        mA.setDuration(2000);
        imageView.startAnimation(mA);
        // 动画的监听
        mA.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画做完
                Intent intent=null;

                // 跳转到主页面
                intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void initView () {
        setContentView(R.layout.activity_splash);
        imageView = (ImageView)findViewById(R.id.splash_iv);

    }

}
