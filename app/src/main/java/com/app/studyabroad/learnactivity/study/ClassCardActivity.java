package com.app.studyabroad.learnactivity.study;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.adapter.CourseModel;
import com.app.studyabroad.learnactivity.alert.MyAlertDialog;
import com.app.studyabroad.learnactivity.databeen.ClassCardBeen;
import com.app.studyabroad.learnactivity.view.courseTableTest2Layout;
import com.app.studyabroad.service.ApiServiceSPAQ;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.FinalStr;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import app.com.studyabroad.R;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClassCardActivity extends BaseActivity implements View.OnClickListener{
	private TextView msgtitle; //标题
	private courseTableTest2Layout mCourseTableDefaultLayout;
	List<CourseModel> mList;
	private ImageView weekPre;
	private ImageView weekNet;
	private TextView week;
	private ApplicationUtil appcache; //缓存
	private String stuid;//学生id
	private ClassCardBeen classCardBeen;
	private String result;
	private int select;
	private MyAlertDialog ad = null; //提示层
	private String status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		intiData();
		setListener();
	}

	private void setListener() {
		mCourseTableDefaultLayout.setOnClickCourseListener(new courseTableTest2Layout.OnClickCourseListener() {
			@Override//点击内容
			public void onClickCourse(TextView textView, CourseModel course, int dataPosition, int dayPosition, int timePosition) {
				String name = course.name;
				//显示加载数据
//				ad=new MyAlertDialog(ClassCardActivity.this,name,"#ffffff",R.layout.classcard_loading,R.id.load_txt);
//				ad.show();
				showPopupWindow(textView,name);
			}

			@Override//点击空白的地方
			public void onClickEmptyCourse(TextView textView, int dayPosition, int timePosition) {

			}
		});
	}
    private void showPopupWindow(View view,String course) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.class_ppviewdows_item, null);
        // 设置按钮的点击事件
		Button empty_content = (Button)contentView.findViewById(R.id.empty_content);
        empty_content.setText(course);


		final PopupWindow popupWindow = new PopupWindow(contentView,
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
		empty_content.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.class_bottom));

        // 设置好参数之后再show
//        popupWindow.showAsDropDown(view);//显示在view
		popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

    }

	private void initView() {
		setContentView(R.layout.activity_class);
		msgtitle = (TextView)findViewById(R.id.msgbartitle);
		mCourseTableDefaultLayout = (courseTableTest2Layout) findViewById(R.id.layout_course2);
		weekPre = (ImageView)findViewById(R.id.class_week_pre);//上周
		weekNet = (ImageView)findViewById(R.id.class_week_net);//下周
		week = (TextView)findViewById(R.id.class_week_tv);//显示第几周
		msgtitle.setText("课程表");

		weekPre.setOnClickListener(this);
		weekNet.setOnClickListener(this);
	}
	private void intiData() {
		appcache = (ApplicationUtil) getApplication();
		appcache.addActivity(this);
		stuid = appcache.getStuid();//
		mList = new ArrayList<>();
		select= 0;
		getData();
	}
/*
* getCourseModel
* 1、周几
* 2、内容
* 3、第几节课
* 4、一共几节*/
	private void getDataItem(int select) {
		int jie = 0;
		mList.clear();
		if(classCardBeen.getCourseList().size()>0){
			List<ClassCardBeen.CourseListBean.ScheduleListBean> scheduleList = classCardBeen
					.getCourseList().get(select).getScheduleList();
			week.setText(classCardBeen.getCourseList().get(select).getZs());
			for (int i = 0;i<scheduleList.size();i++){
				ClassCardBeen.CourseListBean.ScheduleListBean courseListBean = scheduleList.get(i);
				int slesson = courseListBean.getSlesson();//开始节
				int elesson = courseListBean.getElesson();//结束
				int esson = elesson - slesson+1;//一共几节
				//==================同一天的课程显示不准============================
				if(i>0){
					if(courseListBean.getDay()==scheduleList.get(i-1).getDay()){
						jie =jie+(scheduleList.get(i - 1).getElesson() - scheduleList.get(i - 1)
								.getSlesson());
						slesson =slesson - jie;
					}
				}
				//==============================================
				mList.add(getCourseModel(courseListBean.getDay(), courseListBean.getCourseName()+"\n"+courseListBean.getPlace()+"\n"+courseListBean.getTeacherName()
						+"\n"+courseListBean.getTimes(),slesson, esson));
			}
		}

		mCourseTableDefaultLayout.setData(mList);
//		mList.add(getCourseModel(1, "数学数学数学数学数学数学数学数学数学数学数学数学", 1, 3));
//		mList.add(getCourseModel(1, "语文", 4-2, 2));
//		mList.add(getCourseModel(1, "语文", 7-2-1, 1));
//		mList.add(getCourseModel(2, "Academic English I" +
//
//				"Maria Calviat北17栋204" +
//				" 08：30 -" +
//				"" +
//				"09：15", 2, 2));
//		mList.add(getCourseModel(2, "数学数学数学数学数学数学数学数学数学数学数学数学", 4, 3));
//
//		mList.add(getCourseModel(3, "Academic English I" +
//
//				"Maria Calviat北17栋204 " +
//
//				"08：30 -" +
//				"" +
//				"09：15", 3, 1));
//		mList.add(getCourseModel(3, "物理", 6, 1));
//
//		mList.add(getCourseModel(4, "数学", 4, 2));
//		mList.add(getCourseModel(4, "数学", 6, 2));
//
//		mList.add(getCourseModel(5, "数学", 1, 2));
//		mList.add(getCourseModel(5, "体育", 3-1, 3));
//		mList.add(getCourseModel(5, "英语", 7-1-2, 1));
//		mCourseTableDefaultLayout.setData(mList);
	}

	private void getData() {
		Call<ResponseBody> call = serverApi.getClassCard(stuid);
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
				Response<ResponseBody> response1 = response;
				if (response.isSuccessful()) { //请求成功
					try {
						result = response.body().string();
						Log.d(TAG, result +" result");
						JSONObject obj =new JSONObject(result);
						status = obj.getString("status");
						if ("200".equals(status)){//

							Gson gson = new Gson();
							classCardBeen = gson.fromJson(result, ClassCardBeen.class);
							getDataItem(select);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e){
						//  java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
					}
				}else{

				}

			}
			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Log.d("XX","CUOWU");
			}
		});
	}
	private CourseModel getCourseModel(int week, String name, int start, int step) {
		CourseModel model = new CourseModel();
		model.week = week;
		model.name = name;
		model.start = start;
		model.step = step;
		return model;
	}
	public void ordertabBack(View v){
		this.finish();
	}

	@Override
	public void onClick(View view) {
		if ("200".equals(status)){
			switch (view.getId()){
				case R.id.class_week_pre://上周
					if(select > 0){
						select --;
					}
					break;
				case R.id.class_week_net://下周
					if(select < classCardBeen.getCourseList().size()-1){
						select ++;
					}
					break;
				default:
					break;
			}
			getDataItem(select);
		}else {

		}
	}
}
