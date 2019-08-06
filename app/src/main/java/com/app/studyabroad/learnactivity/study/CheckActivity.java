package com.app.studyabroad.learnactivity.study;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.databeen.CheckBeen;
import com.app.studyabroad.learnactivity.databeen.TeacherBeen;
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
public class CheckActivity extends BaseActivity implements View.OnClickListener{
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
	private TextView check_three;
	private TextView check_six;
	private TextView check_all;
	private int select = 0;
	private CheckAdapter checkAdapter;
	private TextView alls_daokelv;
	private TextView alls_time;
	private Drawable rightDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		intiData();

	}
	private void initView() {
		setContentView(R.layout.activity_check);
		msgtitle = (TextView)findViewById(R.id.msgbartitle);
		alls_daokelv = (TextView)findViewById(R.id.alls_daokelv);
//		alls_time = (TextView)findViewById(R.id.alls_time);
		check_three = (TextView) findViewById(R.id.activity_check_three_tv);
		check_six = (TextView) findViewById(R.id.activity_check_six_tv);
		check_all = (TextView) findViewById(R.id.activity_check_all_tv);
		checkView = (ListView)findViewById(R.id.check_list);
		msgtitle.setText("我的考勤");

		check_three.setOnClickListener(this);
		check_six.setOnClickListener(this);
		check_all.setOnClickListener(this);
		rightDrawable = getResources().getDrawable(R.drawable.icon_atten_titlebg);
		rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());

	}
	private void intiData() {
		appcache = (ApplicationUtil) getApplication();
		appcache.addActivity(this);
		stuid = appcache.getStuid();//
		Call<ResponseBody> call = serverApi.getCheck(stuid);
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
				Response<ResponseBody> response1 = response;
				if (response.isSuccessful()) { //请求成功
					try {
						result = response.body().string();
						Log.d("CheckActivity", result +" result");
						JSONObject obj =new JSONObject(result);
//						String status = obj.getString("status");
						Gson gson = new Gson();
                        checkBeen = gson.fromJson(result, CheckBeen.class);
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
        infos.clear();
        allList = checkBeen.getAllList();
        halfYearList = checkBeen.getHalfYearList();
        threeMonthList = checkBeen.getThreeMonthList();

		alls_daokelv.setText(checkBeen.getThreeRate());
//		alls_time.setText("近3个月");
		check_three.setCompoundDrawables(null, null, null, rightDrawable);
		checkAdapter = new CheckAdapter();
		checkView.setAdapter(checkAdapter);
	}

	public void ordertabBack(View v){
		this.finish();
	}

	@Override
	public void onClick(View view) {
		String threeRate = "";
		String halfRate = "";
		String allRate ="";
		if(checkBeen!=null){
			threeRate = checkBeen.getThreeRate();
			halfRate = checkBeen.getHalfRate();
			allRate = checkBeen.getAllRate();
		}
		switch (view.getId()){
			case R.id.activity_check_three_tv:
				select= 0;
				alls_daokelv.setText(threeRate);
//				alls_time.setText("近3个月");
				check_three.setCompoundDrawables(null, null, null, rightDrawable);
				check_six.setCompoundDrawables(null, null, null, null);
				check_all.setCompoundDrawables(null, null, null, null);
				check_three.setTextColor(Color.parseColor("#1d8ae7"));
				check_six.setTextColor(Color.parseColor("#676767"));
				check_all.setTextColor(Color.parseColor("#676767"));
				break;
			case R.id.activity_check_six_tv:
				select = 1;
				alls_daokelv.setText(halfRate);
//				alls_time.setText("近半年");
				check_three.setCompoundDrawables(null, null, null, null);
				check_six.setCompoundDrawables(null, null, null, rightDrawable);
				check_all.setCompoundDrawables(null, null, null, null);
				check_three.setTextColor(Color.parseColor("#676767"));
				check_six.setTextColor(Color.parseColor("#1d8ae7"));
				check_all.setTextColor(Color.parseColor("#676767"));
				break;
			case R.id.activity_check_all_tv:
				select = 2;
				alls_daokelv.setText(allRate);
//				alls_time.setText("全部");
				check_three.setCompoundDrawables(null, null, null, null);
				check_six.setCompoundDrawables(null, null, null, null);
				check_all.setCompoundDrawables(null, null, null, rightDrawable);
				check_three.setTextColor(Color.parseColor("#676767"));
				check_six.setTextColor(Color.parseColor("#676767"));
				check_all.setTextColor(Color.parseColor("#1d8ae7"));
				break;

		}
		checkView.setAdapter(checkAdapter);
	}

	class CheckAdapter extends BaseAdapter {

		private String time;
		private String tvClassRate;
		private String tvLate;
		private String tvLeveal;
		private String tvTruancy;
		private String tvAll;

		@Override
		public int getCount() {
			if(select ==0){
				return threeMonthList.size();
			}else if(select == 1){
				return halfYearList.size();
			}else {
				return allList.size();
			}

		}

		@Override
		public Object getItem(int position) {
			if(select ==0){
				return threeMonthList.get(position);
			}else if(select == 1){
				return halfYearList.get(position);
			}else {
				return allList.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(), R.layout.check_item, null);
				convertView.setTag(holder);
				holder.tvTime = (TextView) convertView.findViewById(R.id.check_list_time_item);
				holder.tvClassRate = (TextView) convertView.findViewById(R.id.check_list_daokelv_item);
				holder.tvLate = (TextView) convertView.findViewById(R.id.check_list_late_item);
				holder.tvLeveal = (TextView) convertView.findViewById(R.id.check_list_leveal_item);
				holder.tvTruancy = (TextView) convertView.findViewById(R.id.check_list_truancy_item);
				holder.tvAll = (TextView) convertView.findViewById(R.id.check_list_alls_item);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			switch (select){
				case 0:
					CheckBeen.ThreeMonthListBean threeMonthListBean = threeMonthList.get(position);
					time = threeMonthListBean.getTime();
					tvClassRate = threeMonthListBean.getClassRate();
					tvLate = threeMonthListBean.getLate()+"";
					tvLeveal = threeMonthListBean.getLeveal()+"";
					tvTruancy = threeMonthListBean.getTruancy()+"";
					tvAll = threeMonthListBean.getAlls()+"";
					break;
				case 1:
					CheckBeen.HalfYearListBean halfYearListBean = halfYearList.get(position);
					time = halfYearListBean.getTime();
					tvClassRate = halfYearListBean.getClassRate();
					tvLate = halfYearListBean.getLate()+"";
					tvLeveal = halfYearListBean.getLeveal()+"";
					tvTruancy = halfYearListBean.getTruancy()+"";
					tvAll = halfYearListBean.getAlls()+"";
					break;
				case 2:
					CheckBeen.AllListBean allListBean = allList.get(position);
					time = allListBean.getTime();
					tvClassRate = allListBean.getClassRate();
					tvLate = allListBean.getLate()+"";
					tvLeveal = allListBean.getLeveal()+"";
					tvTruancy = allListBean.getTruancy()+"";
					tvAll = allListBean.getAlls()+"";
					break;

			}
			holder.tvTime.setText(time+"到课率");
			holder.tvClassRate.setText(tvClassRate);
			holder.tvLate.setText(tvLate);
			holder.tvLeveal.setText(tvTruancy);
			holder.tvTruancy.setText(tvLeveal);
			holder.tvAll.setText(tvAll);

			return convertView;
		}
	}
	class ViewHolder {
		public TextView tvTime;//时间
		public TextView tvClassRate;//到课率
		public TextView tvLate;//迟到
		public TextView tvLeveal;//旷课
		public TextView tvTruancy;//请假
		public TextView tvAll;//总考勤次数
	}

}
