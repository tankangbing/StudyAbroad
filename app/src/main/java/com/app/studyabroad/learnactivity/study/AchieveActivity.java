package com.app.studyabroad.learnactivity.study;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.BaseActivity;
import com.app.studyabroad.learnactivity.adapter.ParentAdapter;
import com.app.studyabroad.learnactivity.databeen.AchieveBeen;
import com.app.studyabroad.learnactivity.databeen.ClassCardBeen;
import com.app.studyabroad.learnactivity.entity.ChildEntity;
import com.app.studyabroad.learnactivity.entity.ParentEntity;
import com.app.studyabroad.util.ApplicationUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.com.studyabroad.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class AchieveActivity extends BaseActivity implements ExpandableListView.OnGroupExpandListener,ParentAdapter.OnChildTreeViewClickListener {
	private TextView msgtitle; //标题
	private List infos ;
	private Context mContext;

	private ExpandableListView eList;

	private ArrayList<ParentEntity> parents;

	private ParentAdapter adapter;
	private ApplicationUtil appcache; //缓存
	private String stuid;//学生id
	/**
	 * 创建一级条目容器
	 */
	private List<Map<String, String>> gruops = new ArrayList<Map<String, String>>();
	/**
	 * 存放内容, 以便显示在列表中
	 */
	private List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
	private String result;
	private AchieveBeen achieveBeen;
	private List<AchieveBeen.ScoreslistBean.CourselistBean> courseFristlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			initView();
			intiData();
	}

	private void initEvent() {
		eList = (ExpandableListView) findViewById(R.id.eList);

		eList.setOnGroupExpandListener(this);

		adapter = new ParentAdapter(mContext, parents);

		eList.setAdapter(adapter);

		adapter.setOnChildTreeViewClickListener(this);
		int count = eList.getCount();
		for (int i = 0;i<courseFristlist.size();i++){//默认展开第一学期
			eList.expandGroup(i);
		}
	}

	private void initView() {
		setContentView(R.layout.activity_achieve);
		msgtitle = (TextView)findViewById(R.id.msgbartitle);
//		achieveList = (ExpandableListView)findViewById(R.id.achieve_list);
		msgtitle.setText("我的成绩");
		mContext = this;
	}
	private void intiData() {
		appcache = (ApplicationUtil) getApplication();
		appcache.addActivity(this);
		stuid = appcache.getStuid();//

		Call<ResponseBody> call = serverApi.getAchieve(stuid);
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
				Response<ResponseBody> response1 = response;
				if (response.isSuccessful()) { //请求成功
					try {
						result = response.body().string();
						Log.d(TAG+1, result +" result");
						JSONObject obj =new JSONObject(result);
//						String status = obj.getString("status");
							Gson gson = new Gson();
						achieveBeen = gson.fromJson(result, AchieveBeen.class);
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
		parents = new ArrayList<ParentEntity>();
		parents.clear();
		List<AchieveBeen.ScoreslistBean> scoreslist = achieveBeen.getScoreslist();
		Log.d(TAG+2, scoreslist +" scoreslist");
		courseFristlist = scoreslist.get(0)
				.getCourselist();
		for(int i = 0;i <scoreslist.size();i++){
			ParentEntity parent = new ParentEntity();
			parent.setGroupName("第" + scoreslist.get(i).getTerm()+ "学期的成绩");
			parent.setGroupColor(getResources().getColor(
					android.R.color.black));
			ArrayList<ChildEntity> childs = new ArrayList<ChildEntity>();
			List<AchieveBeen.ScoreslistBean.CourselistBean> courselist = scoreslist.get(i)
					.getCourselist();

			for (int j = 0; j < courselist.size(); j++) {

				ChildEntity child = new ChildEntity();

				child.setGroupName(courselist.get(j).getCourseName());

				child.setGroupColor(getResources().getColor(
						android.R.color.background_dark));

				ArrayList<String> childNames = new ArrayList<String>();

				ArrayList<Integer> childColors = new ArrayList<Integer>();
				List<AchieveBeen.ScoreslistBean.CourselistBean.CoursescoreBean> coursescore =
						courselist.get(j).getCoursescore();
				for (int k = 0; k < coursescore.size(); k++) {

					childNames.add(coursescore.get(k).getScores()+"#"+coursescore.get(k).getExamType());

					childColors.add(Color.parseColor("#ff00ff"));

				}
				childNames.add(0,"成绩#考试类型");
				child.setChildNames(childNames);

				childs.add(child);

			}

			parent.setChilds(childs);

			parents.add(parent);

		}
		initEvent();
		Log.d(TAG+1, parents +" parents");
//		for (int i = 0; i < 2; i++) {
//
//			ParentEntity parent = new ParentEntity();
//
//			parent.setGroupName("第" + (i+1 )+ "学期的成绩");
//
//			parent.setGroupColor(getResources().getColor(
//					android.R.color.white));
//
//			ArrayList<ChildEntity> childs = new ArrayList<ChildEntity>();
//
//			for (int j = 0; j < 8; j++) {
//
//				ChildEntity child = new ChildEntity();
//
//				child.setGroupName("课程名称：  课程"  + j);
//
//				child.setGroupColor(getResources().getColor(
//						android.R.color.background_dark));
//
//				ArrayList<String> childNames = new ArrayList<String>();
//
//				ArrayList<Integer> childColors = new ArrayList<Integer>();
//
//				for (int k = 0; k < 4; k++) {
//
//					childNames.add("子类第" + k + "项");
//
//					childColors.add(Color.parseColor("#ff00ff"));
//
//				}
//
//				child.setChildNames(childNames);
//
//				childs.add(child);
//
//			}
//
//			parent.setChilds(childs);
//
//			parents.add(parent);
//
//		}
	}

	public void ordertabBack(View v){
		this.finish();
	}

	@Override
	public void onGroupExpand(int i) {

	}

	@Override
	public void onPointerCaptureChanged(boolean hasCapture) {

	}
	@Override//item点击事件
	public void onClickPosition(int parentPosition, int groupPosition,
								int childPosition) {
		// do something
//		String childName = parents.get(parentPosition).getChilds()
//				.get(groupPosition).getChildNames().get(childPosition)
//				.toString();
//		Toast.makeText(
//				mContext,
//				"点击的下标为： parentPosition=" + parentPosition
//						+ "   groupPosition=" + groupPosition
//						+ "   childPosition=" + childPosition + "\n点击的是："
//						+ childName, Toast.LENGTH_SHORT).show();
	}
}
