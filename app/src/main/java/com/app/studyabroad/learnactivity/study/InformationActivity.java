package com.app.studyabroad.learnactivity.study;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.databeen.InformationBeen;
import com.app.studyabroad.learnactivity.databeen.MyTeacherBeen;
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
public class InformationActivity extends BaseActivity {
	private TextView msgtitle; //标题
	private List infos ;
	private ListView checkView;
	private ApplicationUtil appcache; //缓存
	private String stuid;//学生id
	private String result;
	private InformationBeen informationBeen;
	private List<InformationBeen.ConsultListBean> consultList;
	private TextView zx_bt2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		intiData();
		initEvent();
	}



	private void initView() {
		setContentView(R.layout.activity_information);
		msgtitle = (TextView)findViewById(R.id.msgbartitle);
		zx_bt2 = (TextView)findViewById(R.id.zx_bt2);
		checkView = (ListView)findViewById(R.id.check_list);
		msgtitle.setText("信息互动");

	}
	private void intiData() {
		appcache = (ApplicationUtil) getApplication();
		appcache.addActivity(this);
		stuid = appcache.getStuid();//
		Call<ResponseBody> call = serverApi.getInformation(stuid);
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
				Response<ResponseBody> response1 = response;
				if (response.isSuccessful()) { //请求成功
					try {
						result = response.body().string();
						Log.d("Information", result +" result");
						JSONObject obj =new JSONObject(result);
//						String status = obj.getString("status");
						Gson gson = new Gson();
						informationBeen = gson.fromJson(result, InformationBeen
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
	private void initEvent() {
		zx_bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(InformationActivity.this, ConsultationActivity.class));
			}
		});
	}
	private void getData() {
		consultList = informationBeen.getConsultList();

//		infos = new ArrayList();
//		for(int i = 0;i<consultList.size();i++){
//			infos.add(i);
//		}
		CheckAdapter checkAdapter = new CheckAdapter();
		checkView.setAdapter(checkAdapter);
	}

	public void ordertabBack(View v){
		this.finish();
	}
    public void ordertabzx(View v){
        startActivity(new Intent(this, ConsultationActivity.class));
    }
	class CheckAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return consultList.size();
		}

		@Override
		public Object getItem(int position) {
			return consultList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			InformationActivity.ViewHolder holder = null;
			if (convertView == null) {
				holder = new InformationActivity.ViewHolder();
				convertView = View.inflate(getApplicationContext(), R.layout.information_item, null);
				convertView.setTag(holder);
				holder.tyle = (TextView) convertView.findViewById(R.id.informatin_tyle_item);
				holder.time = (TextView) convertView.findViewById(R.id.informatin_time_item);
				holder.quetion = (TextView) convertView.findViewById(R.id.informatin_quetion_item);
				holder.answere = (TextView) convertView.findViewById(R.id.informatin_answer_item);
			} else {
				holder = (InformationActivity.ViewHolder) convertView.getTag();
			}
			String consultType = consultList.get(position).getConsult().getConsultType();
			holder.tyle.setText(consultType);
			holder.time.setText(consultList.get(position).getConsult().getTime());
			holder.quetion.setText("问题："+consultList.get(position).getConsult().getIssue());
			String str ="";
			for(int i = 0;i < consultList.get(position).getReplyList().size();i++){
				str = str + consultList.get(position).getReplyList().get(i).getTeacherName()+":"+consultList.get(position).getReplyList().get(i).getContent()+"\n";
			}
			holder.answere.setText(str);
			return convertView;
		}
	}
	class ViewHolder {
		public TextView tyle;//类型
		public TextView time;//时间
		public TextView quetion;//问题
		public TextView answere;//老师回答
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("onPause",  " onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("onPause",  " onRestart");
		intiData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("onPause",  " onResume");
	}
}
