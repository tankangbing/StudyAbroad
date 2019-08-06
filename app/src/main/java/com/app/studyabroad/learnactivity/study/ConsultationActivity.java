package com.app.studyabroad.learnactivity.study;


import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.databeen.InformationBeen;
import com.app.studyabroad.learnactivity.databeen.TeacherBeen;
import com.app.studyabroad.learnactivity.view.CommonPopupWindow;
import com.app.studyabroad.util.ApplicationUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.com.studyabroad.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("deprecation")
public class ConsultationActivity extends BaseActivity implements View.OnClickListener{
	private TextView msgtitle; //标题
	private List infos ;
	private ListView checkView;
	private TextView kaoshiTv;
	private TextView teacherTv;
	private TextView activity_check_three_tv;
	private View linearLayout;
	private View linearLayoutTc;
	private View title;
	private CommonPopupWindow window;
	private ListView dataList;
	private List<String> Tyle;
	private List<String> TyleId;
	private List<String> Teacher;
	private List<String> TeacherId;
	private ListPopupWindow listPopupWindow;
	private ApplicationUtil appcache; //缓存
	private String stuid;//学生id
	private String result;
	private TeacherBeen teacherBeen;
	private String selectTyle;
	private List<TeacherBeen.TeacherListBean> teacherList;
	private String selectTeacherId;
	private EditText quetionEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		intiData();
//		initPopupWindow();
	}
	private void initView() {
		setContentView(R.layout.activity_consultation);
		msgtitle = (TextView)findViewById(R.id.msgbartitle);
		msgtitle.setText("我要咨询");
		kaoshiTv = (TextView) findViewById(R.id.consulation_kaoshi_tv);
		teacherTv = (TextView) findViewById(R.id.consulation_teacher_tv);
		activity_check_three_tv = (TextView) findViewById(R.id.activity_check_three_tv);
		quetionEt = (EditText) findViewById(R.id.consulation_quetion_et);
		linearLayout =findViewById(R.id.consulation_layout);
		linearLayoutTc =findViewById(R.id.consulation_layout_tc);
		String packageName = getPackageName();
		title =findViewById(R.id.title);
		teacherTv.setOnClickListener(this);
		kaoshiTv.setOnClickListener(this);
	}
	private void intiData() {
		appcache = (ApplicationUtil) getApplication();
		appcache.addActivity(this);
		stuid = appcache.getStuid();//
		Call<ResponseBody> call = serverApi.getTeacher(stuid);
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
				Response<ResponseBody> response1 = response;
				if (response.isSuccessful()) { //请求成功
					try {
						result = response.body().string();
						Log.d("Teacher", result +" result");
						JSONObject obj =new JSONObject(result);
//						String status = obj.getString("status");
						Gson gson = new Gson();
						teacherBeen = gson.fromJson(result, TeacherBeen
								.class);
						getData();
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

	private void getData() {
		List<TeacherBeen.ConsultTypeListBean> consultTypeList = teacherBeen.getConsultTypeList();
		teacherList = teacherBeen.getTeacherList();
		Tyle=new ArrayList<>();
		TyleId=new ArrayList<>();
		Teacher=new ArrayList<>();
		TeacherId=new ArrayList<>();
		for(int i=0; i<consultTypeList.size(); i++)
		{
			Tyle.add(consultTypeList.get(i).getValue());
			TyleId.add(consultTypeList.get(i).getName());
		}
		for(int i = 0; i< teacherList.size(); i++)
		{
			Teacher.add(teacherList.get(i).getTname());
			TeacherId.add(teacherList.get(i).getTid());
		}
		//初始化
		kaoshiTv.setText(Tyle.get(0));
		teacherTv.setText(Teacher.get(0));
		selectTyle =TyleId.get(0);
		selectTeacherId = TeacherId.get(0);
	}

	private void showPopupWindow(final TextView textView, View view, final List<String> datas,final int tyle) {
		int width = teacherTv.getWidth();
		int check_width = activity_check_three_tv.getWidth();
		int height = teacherTv.getHeight();
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(mContext).inflate(
				R.layout.popup_list, null);
		// 设置按钮的点击事件
		ListView listView = (ListView)contentView.findViewById(R.id.data_list);
		listView.setAdapter(new ArrayAdapter<String>(ConsultationActivity.this, R.layout.item_popup_list, datas));


		final PopupWindow popupWindow = new PopupWindow(contentView,
				width, AbsListView.LayoutParams.WRAP_CONTENT, true);

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
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(tyle == 1){
					selectTyle = TyleId.get(position);
				}else if(tyle==2){
					selectTeacherId = TeacherId.get(position);
				}
				textView.setText(datas.get(position));
				popupWindow.dismiss();
			}
		});
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.class_bottom));

		// 设置好参数之后再show
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			popupWindow.showAsDropDown(view,check_width+20,0,Gravity.BOTTOM);
		}else {
			popupWindow.showAsDropDown(view);
		}

	}
	public void ordertabBack(View v){
		this.finish();
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()){

			case R.id.consulation_kaoshi_tv:
				showPopupWindow(kaoshiTv,linearLayout,Tyle,1);
				break;
			case R.id.consulation_teacher_tv:
				showPopupWindow(teacherTv,linearLayoutTc,Teacher,2);
				break;
			case R.id.consulation_tijiao_btn:

				break;

			default:break;
		}
	}
	public void tijiao(View view){
		String quetion = quetionEt.getText().toString();
		if(quetion.isEmpty()){
			Toast.makeText(mContext,"请输入您要咨询的问题!",Toast.LENGTH_SHORT).show();
			return;
		}
		Call<ResponseBody> call = serverApi.getConsulation(stuid,selectTyle,quetion,selectTeacherId);
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
				Response<ResponseBody> response1 = response;
				if (response.isSuccessful()) { //请求成功
					try {
						String consulationResult = response.body().string();
						Log.d("getConsulation", consulationResult +" consulationResult");
						JSONObject obj =new JSONObject(consulationResult);
						String status = obj.getString("status");
						if("200".equals(status)){
							//提交成功
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
				finish();
			}
			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Log.d("XX","CUOWU");
			}
		});
	}
}
