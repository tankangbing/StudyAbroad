package com.app.studyabroad.learnactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.com.studyabroad.R;


import com.app.studyabroad.learnactivity.databeen.UserBeen;
import com.app.studyabroad.learnactivity.mysetting.Setting;
import com.app.studyabroad.learnactivity.study.AchieveActivity;
import com.app.studyabroad.learnactivity.study.CheckActivity;
import com.app.studyabroad.learnactivity.study.ClassCardActivity;
import com.app.studyabroad.learnactivity.study.InformationActivity;
import com.app.studyabroad.learnactivity.study.MyteacherActivity;
import com.app.studyabroad.learnactivity.study.XinXictivity;
import com.app.studyabroad.util.ApplicationUtil;
import com.google.gson.Gson;


public class MainActivity extends BaseActivity {

    private ApplicationUtil appcache; //缓存
    private Handler handler; //更新ui
    private int[] studentName;
    private GridView view1;//
    private GridView view2;//学生名字
    RelativeLayout student;
    private TextView studentTv;
    private UserBeen userBeen;//用户信息been
    private boolean tyle = false;//false为学生类型、true为家长
    private List<UserBeen.StudentListBean> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        student = (RelativeLayout)findViewById(R.id.student);
        studentTv = (TextView) findViewById(R.id.main_student_tv);
        view1 = (GridView)findViewById(R.id.gridview);
        view2 = (GridView)findViewById(R.id.gridview2);
        setcache();
        getData();
        updateUi();
        uodateView("0","0");

    }
    public void setcache(){
        appcache = (ApplicationUtil) getApplication();
        appcache.addActivity(this);
    }

    private OnItemClickListener itemclick = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            switch (arg2) {
                case 0:
                    startActivity(new Intent(MainActivity.this, ClassCardActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this,CheckActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, AchieveActivity.class));
                    //startActivity(new Intent(MainActivity.this, CourseTestActivity.class));

                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, InformationActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(MainActivity.this, MyteacherActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(MainActivity.this, Setting.class));
                    break;
                case 6:
                    startActivity(new Intent(MainActivity.this, XinXictivity.class));
                    break;
                default:
                    break;
            }
        }
    };
    private OnItemClickListener itemclick2 = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            view1.setVisibility(View.VISIBLE);
            student.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            String name = studentList.get(arg2).getName();
            String stuid = studentList.get(arg2).getId();
            appcache.setStuid(stuid);
            studentTv.setText("当前学生："+name);
//            uodateView("0","0");
//            Toast.makeText(getApplicationContext(),arg2,Toast.LENGTH_SHORT).show();
        }
    };

    public void updateUi(){
      if(tyle){//家长用户
          view1.setVisibility(View.GONE);
          student.setVisibility(View.GONE);
          view2.setVisibility(View.VISIBLE);
          updateQieView("0","0");
      }
    }

    private void uodateView(String count,String ordercount){
        int[] resId = {R.drawable.main_kc,R.drawable.main_yy,R.drawable.main_jd,R.drawable.main_xx,R.drawable.main_xj,R.drawable.main_sz,R.drawable.myinfo_studentinfo};
        int[] name = {R.string.main_msg,R.string.main_order,R.string.main_course,R.string.main_studyprogress,R.string.main_info,R.string.main_setting,R.string.main_mssge};
        ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < name.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemName", name[i]);
            map.put("itemImage", resId[i]);
            map.put("count", count);
            map.put("ordercount", ordercount);
            item.add(map);
        }
        GridViewAdapter simpleAdapter = new GridViewAdapter
                (this, item, R.layout.activity_main_item, new String[] {"itemName","itemImage"},
                        new int[] {R.id.itemName,R.id.itemImage}) {
        };

        view1.setAdapter(simpleAdapter);
        view1.setOnItemClickListener(itemclick);
    }
    private void updateQieView(String count,String ordercount){
//        studentName = new int[]{R.string.main_msg,R.string.main_order,R.string.main_course,R.string.main_studyprogress,R.string.main_info,R.string.main_setting};

        MyImageAdapter myImageAdapter = new MyImageAdapter(getApplicationContext());
        view2.setAdapter(myImageAdapter);
        view2.setOnItemClickListener(itemclick2);
    }

    public void getData(){
        String user = appcache.getUser();
        Gson gson = new Gson();
        userBeen = gson.fromJson(user, UserBeen.class);
        String userxm = userBeen.getUserxm();
        String usertype = userBeen.getUsertype();
        String stuid = userBeen.getStuid();//学生id

        if("students".equals(usertype)){//学生
            appcache.setStuid(stuid);//保存学生id
            studentTv.setText("当前学生："+userxm);
        }else {//家长
            tyle = true;
            studentList = userBeen.getStudentList();
            student.setVisibility(View.VISIBLE);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  //获取 back键
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Exit.class);
            Bundle b = new Bundle();
            b.putString("exitTitle", "退出系统");
            b.putString("exitConet", "是否要退出系统？\n");
            intent.putExtras(b);
            startActivity(intent);
        }
        return false;
    }

    @Override
    protected void onRestart() {
        uodateView(appcache.getMsgcount()+"",appcache.getOrdercount()+"");
        getData();
        super.onRestart();
    }
    public void qiehuan(View v){
        view1.setVisibility(View.GONE);
        student.setVisibility(View.GONE);
        view2.setVisibility(View.VISIBLE);
        updateQieView("0","0");

    }


    public class MyImageAdapter extends BaseAdapter {
        //传入context对象
        private Context con;
        //构造方法
        public MyImageAdapter(Context context){
            con = context;
        }
        @Override
        public int getCount() {
            return studentList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //将数组中的图片放置到九宫格view中
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MainActivity.ViewHolder holder = null;
            if (convertView == null) {
                holder = new MainActivity.ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.activity_main_item2, null);
                convertView.setTag(holder);
				holder.tvTime = (TextView) convertView.findViewById(R.id.itemName);
            } else {
                holder = (MainActivity.ViewHolder) convertView.getTag();
            }
            String name = studentList.get(position).getName();
            holder.tvTime.setText(name);

            return convertView;
        }
    }
    class ViewHolder {
        public TextView tvTime;
    }
}
