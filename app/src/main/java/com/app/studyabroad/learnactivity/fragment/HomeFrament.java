package com.app.studyabroad.learnactivity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.Exit;
import com.app.studyabroad.learnactivity.MainActivity;
import com.app.studyabroad.learnactivity.VersionActivity;
import com.app.studyabroad.learnactivity.alert.MyAlertDialog;
import com.app.studyabroad.learnactivity.databeen.UserBeen;
import com.app.studyabroad.learnactivity.mysetting.Setting;
import com.app.studyabroad.learnactivity.study.AchieveActivity;
import com.app.studyabroad.learnactivity.study.ChangOlepswActivity;
import com.app.studyabroad.learnactivity.study.CheckActivity;
import com.app.studyabroad.learnactivity.study.ClassCardActivity;
import com.app.studyabroad.learnactivity.study.CourseActivity;
import com.app.studyabroad.learnactivity.study.InformationActivity;
import com.app.studyabroad.learnactivity.study.MyteacherActivity;
import com.app.studyabroad.learnactivity.study.QieHuan;
import com.app.studyabroad.learnactivity.study.XinXictivity;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.FinalStr;
import com.app.studyabroad.util.HttpUtil;
import com.app.studyabroad.util.StringToJson;
import com.app.studyabroad.util.SysUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import app.com.studyabroad.R;

public class HomeFrament extends Fragment implements View.OnClickListener{
    private Context mContext;
    private LinearLayout viewhome_class_card;
    private LinearLayout home_check;
    private LinearLayout home_achieve;
    private LinearLayout home_myteacher;
    private LinearLayout home_information;
    private TextView msgtitle; //标题
    private TextView main_ziliao_tv;
    private TextView main_qh_bt;
    private ApplicationUtil appcache; //缓存
    private UserBeen userBeen;//用户信息been
    private TextView student_tv;
    private TextView home_changpsw;
    private TextView home_up_banben;
    private TextView home_tuichu;
    MyAlertDialog ad = null;
    private Handler handler;
    private String usertype;

    public  HomeFrament(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.frament_home,container,false);
        mContext = getContext();
        setcache();
        initView(view);
        initData();
        return view;
    }

    public void setcache(){
        appcache= (ApplicationUtil)getActivity().getApplication();
        appcache.addActivity(getActivity());
    }
    private void initView(View view) {
        viewhome_class_card = (LinearLayout)view.findViewById(R.id.home_class_card);
        home_check = (LinearLayout)view.findViewById(R.id.home_check);
        home_achieve = (LinearLayout)view.findViewById(R.id.home_achieve);
        home_myteacher = (LinearLayout)view.findViewById(R.id.home_myteacher);
        home_information = (LinearLayout)view.findViewById(R.id.home_information);
        main_ziliao_tv = (TextView)view.findViewById(R.id.main_ziliao_tv);
        main_qh_bt = (TextView)view.findViewById(R.id.main_qh_bt);
        student_tv = (TextView)view.findViewById(R.id.main_student_tv);
        msgtitle = (TextView)view.findViewById(R.id.msgbartitle);
        home_changpsw = (TextView)view.findViewById(R.id.home_changpsw);
        home_up_banben = (TextView)view.findViewById(R.id.home_up_banben);
        home_tuichu = (TextView)view.findViewById(R.id.home_tuichu);
//		achieveList = (ExpandableListView)findViewById(R.id.achieve_list);
        msgtitle.setText("华南理工大学出国留学");

        viewhome_class_card.setOnClickListener(this);
        home_check.setOnClickListener(this);
        home_achieve.setOnClickListener(this);
        home_myteacher.setOnClickListener(this);
        home_information.setOnClickListener(this);
        main_ziliao_tv.setOnClickListener(this);
        main_qh_bt.setOnClickListener(this);
        home_changpsw.setOnClickListener(this);
        home_up_banben.setOnClickListener(this);
        home_tuichu.setOnClickListener(this);

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
                        intent1.setClass(mContext,VersionActivity.class);
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
    private void initData() {
        String user = appcache.getUser();
        Gson gson = new Gson();
        userBeen = gson.fromJson(user, UserBeen.class);
        String userxm = userBeen.getUserxm();
        usertype = userBeen.getUsertype();
        String stuid = userBeen.getStuid();//学生id


        if("students".equals(usertype)){//学生
            appcache.setStuid(stuid);//保存学生id
            student_tv.setText(userxm);
            main_qh_bt.setVisibility(View.GONE);
        }else{//家长
            appcache.setIsFrist(true);
            main_qh_bt.setVisibility(View.VISIBLE);
            startActivity(new Intent(mContext, QieHuan.class));
        }


    }


    private void createAlert(String title,String color,int layout,int id){
        ad=new MyAlertDialog(mContext,title,color,layout,id);
        ad.show();
    }
    private String getversion(){
        PackageManager packageManager = getActivity().getPackageManager();
        PackageInfo packInfo;
        String version = "未知";
        try {
            packInfo = packageManager.getPackageInfo(getActivity().getPackageName(),0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    private String content(String CtVersion,String newVersion,String updateMsg){
        StringBuffer content = new StringBuffer();
        content.append("当前版本：").append(CtVersion).append("    最新版本：").append(newVersion).append("<br/>").append(updateMsg);
        return content.toString();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_class_card:
                startActivity(new Intent(mContext, ClassCardActivity.class));
                break;
            case R.id.home_check:
                startActivity(new Intent(mContext,CheckActivity.class));
                break;
            case R.id.home_achieve:
                startActivity(new Intent(mContext, AchieveActivity.class));
                break;
            case R.id.home_information:
                startActivity(new Intent(mContext, InformationActivity.class));
                break;
            case R.id.home_myteacher:
                startActivity(new Intent(mContext, MyteacherActivity.class));
                break;
            case R.id.main_ziliao_tv:
                startActivity(new Intent(mContext, XinXictivity.class));
                break;
            case R.id.main_qh_bt:
                startActivity(new Intent(mContext, QieHuan.class));
                break;
            case R.id.home_changpsw:
                startActivity(new Intent(mContext, ChangOlepswActivity.class));
                break;
            case R.id.home_tuichu:
                Intent intent = new Intent();
                intent.setClass(mContext,Exit.class);
                Bundle b = new Bundle();
                b.putString("exitTitle", "退出登录");
                b.putString("exitConet", "是否需要重新登录？\n");
                b.putString("isClearPsw", "Y");//是否清空密码
                intent.putExtras(b);
                startActivity(intent);
                break;
            case R.id.home_up_banben:
                if(null != ad){
                    ad.dismiss();
                }
                ad=new MyAlertDialog(mContext,"正在检测版本,请稍等..","#ffffff",R.layout.custom_alert_loading,R.id.load_txt);
                ad.show();
                getdata();
                break;
            default:
                break;
        }
    }
    private void getdata(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("version", getversion());
                String result = HttpUtil.getResultByPost(FinalStr.ACCESSPRYPATH
                                + "/edu3/app/urlto/search-version.html", map,
                        mContext, null);
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
    @Override
    public void onResume() {
        super.onResume();
        if("students".equals(usertype)){//学生

        }else{//家长
            student_tv.setText(appcache.getUserXm());
        }
    }

}
