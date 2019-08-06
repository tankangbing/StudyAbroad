package com.app.studyabroad.learnactivity.study;


import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studyabroad.learnactivity.databeen.AchieveBeen;
import com.app.studyabroad.learnactivity.databeen.MyTeacherBeen;
import com.app.studyabroad.service.ApiServiceSPAQ;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.FinalStr;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.com.studyabroad.R;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@SuppressWarnings("deprecation")
public class MyteacherActivity extends ExpandableListActivity {
	/**
	 * 创建一级条目容器
	 */
	List<Map<String, String>> gruops = new ArrayList<Map<String, String>>();
	/**
	 * 存放内容, 以便显示在列表中
	 */
	List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
	private TextView msgtitle; //标题
	private ApplicationUtil appcache; //缓存
	private String stuid;//学生id
	//网络请求框架
	protected Retrofit retrofit =null;
	protected ApiServiceSPAQ serverApi;
	private static OkHttpClient client = new OkHttpClient().newBuilder()
			.connectTimeout(60, TimeUnit.SECONDS)
			.readTimeout(60, TimeUnit.SECONDS)
			.writeTimeout(60, TimeUnit.SECONDS).build();
	private String result;
	private MyTeacherBeen myTeacherBeen;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
		setContentView(R.layout.activity_myteacher);
		msgtitle = (TextView)findViewById(R.id.msgbartitle);
		msgtitle.setText("我的老师");

	}

	/**
	 * 设置列表内容
	 */
	public void initData() {
		appcache = (ApplicationUtil) getApplication();
		appcache.addActivity(this);
		stuid = appcache.getStuid();//
		//加载retrofit

		retrofit = new Retrofit.Builder()
				.client(client)
				.baseUrl(FinalStr.ACCESSPRYPATH +"/")
				.build();
		//生成接口文件
		serverApi = retrofit.create(ApiServiceSPAQ.class);

		Call<ResponseBody> call = serverApi.getMyteacher(stuid);
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
				Response<ResponseBody> response1 = response;
				if (response.isSuccessful()) { //请求成功
					try {
						result = response.body().string();
						Log.d("Myteacher", result +" result");
						JSONObject obj =new JSONObject(result);
//						String status = obj.getString("status");
						Gson gson = new Gson();
						myTeacherBeen = gson.fromJson(result, MyTeacherBeen.class);
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

		List<MyTeacherBeen.TeacherListBeanX> teacherList = myTeacherBeen.getTeacherList();
		for(int i=0 ;i <teacherList.size();i++){
			Map<String, String> title = new HashMap<String, String>();
			title.put("group",teacherList.get(i).getClassName());
			gruops.add(title);// 创建一级条目标题
			List<MyTeacherBeen.TeacherListBeanX.TeacherListBean> teacherList1 = teacherList.get(i)
					.getTeacherList();
			List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
			for(int j = 0;j<teacherList1.size();j++){
				Map<String, String> title1 = new HashMap<String, String>();
				Map<String, String> title2 = new HashMap<String, String>();
				title1.put("child","教师名称："+ teacherList1.get(j).getTname());
				title1.put("childphone", "联系电话："+ teacherList1.get(j).getPhone());
				childs_1.add(title1);

			}
			childs.add(childs_1);
		}
/*// 创建二个一级条目标题
		Map<String, String> title_1 = new HashMap<String, String>();
		Map<String, String> title_2 = new HashMap<String, String>();
		Map<String, String> title_3 = new HashMap<String, String>();
		title_1.put("group", "语文");
		title_2.put("group", "数学");
		gruops.add(title_1);
		gruops.add(title_2);

		// 创建二级条目内容
		// 内容一
		Map<String, String> title_1_content_1 = new HashMap<String, String>();
		Map<String, String> title_1_content_2 = new HashMap<String, String>();
		Map<String, String> title_1_content_3 = new HashMap<String, String>();
		title_1_content_1.put("child", "工人");
		title_1_content_2.put("child", "学生");
		title_1_content_3.put("child", "农民");

		List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
		childs_1.add(title_1_content_1);
		childs_1.add(title_1_content_2);
		childs_1.add(title_1_content_3);

		// 内容二
		Map<String, String> title_2_content_1 = new HashMap<String, String>();
		Map<String, String> title_2_content_2 = new HashMap<String, String>();
		Map<String, String> title_2_content_3 = new HashMap<String, String>();
		title_2_content_1.put("child", "猩猩");
		title_2_content_2.put("child", "老虎");
		title_2_content_3.put("child", "狮子");
		List<Map<String, String>> childs_2 = new ArrayList<Map<String, String>>();
		childs_2.add(title_2_content_1);
		childs_2.add(title_2_content_2);
		childs_2.add(title_2_content_3);

		childs.add(childs_1);
		childs.add(childs_2);*/

		/**
		 * 创建ExpandableList的Adapter容器 参数: 1.上下文 2.一级集合 3.一级样式文件 4. 一级条目键值
		 * 5.一级显示控件名 6. 二级集合 7. 二级样式 8.二级条目键值 9.二级显示控件名
		 *
		 */
		SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
				this, gruops, R.layout.myteacher_groups_item, new String[] { "group" },
				new int[] { R.id.textGroup }, childs, R.layout.myteacher_childs_item,
				new String[] { "child" ,"childphone"}, new int[] { R.id.textChild,R.id.textPhone});
		// 加入列表
		setListAdapter(sela);
		ExpandableListView expandableListView = getExpandableListView();
		expandableListView.setGroupIndicator(null);
		int count = expandableListView.getCount();
		for (int i = 0;i<count;i++){//默认展开
			expandableListView.expandGroup(i);
		}
	}

	/**
	 * 列表内容按下
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
								int groupPosition, int childPosition, long id) {
//		Toast.makeText(
//				MyteacherActivity.this,
//				"您选择了"
//						+ gruops.get(groupPosition).toString()
//						+ "子编号"
//						+ childs.get(groupPosition).get(childPosition)
//						.toString(), Toast.LENGTH_SHORT).show();
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}

	/**
	 * 二级标题按下
	 */
	@Override
	public boolean setSelectedChild(int groupPosition, int childPosition,
									boolean shouldExpandGroup) {
		return super.setSelectedChild(groupPosition, childPosition,
				shouldExpandGroup);
	}

	/**
	 * 一级标题按下
	 */
	@Override
	public void setSelectedGroup(int groupPosition) {
		super.setSelectedGroup(groupPosition);
	}

	public void ordertabBack(View v){
		this.finish();
	}
}
