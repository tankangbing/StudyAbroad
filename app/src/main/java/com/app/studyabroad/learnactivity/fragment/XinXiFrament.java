package com.app.studyabroad.learnactivity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.databeen.InformationBeen;
import com.app.studyabroad.learnactivity.study.ConsultationActivity;
import com.app.studyabroad.learnactivity.study.InformationActivity;
import com.app.studyabroad.learnactivity.study.XinXictivity;
import com.app.studyabroad.service.ApiServiceSPAQ;
import com.app.studyabroad.util.ApplicationUtil;
import com.app.studyabroad.util.FinalStr;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import app.com.studyabroad.R;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class XinXiFrament extends Fragment{
    private Context mContext;
    private TextView msgtitle; //标题
    private List infos ;
    private ListView checkView;
    private ApplicationUtil appcache; //缓存
    private String stuid;//学生id
    private String result;
    private InformationBeen informationBeen;
    private List<InformationBeen.ConsultListBean> consultList;
    private TextView zx_bt2;

    //网络请求框架
    protected Retrofit retrofit =null;
    protected ApiServiceSPAQ serverApi;
    private static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build();

    public XinXiFrament(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_information,container,false);
        mContext = getContext();
        initView(view);
        intiData();
        initEvent();
       return view;
    }

    private void initView(View view) {
        msgtitle = (TextView)view.findViewById(R.id.msgbartitle);
        zx_bt2 = (TextView)view.findViewById(R.id.zx_bt2);
        Button button1 = (Button)view.findViewById(R.id.button1);
        Button button2 = (Button)view.findViewById(R.id.button2);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        checkView = (ListView)view.findViewById(R.id.check_list);
        msgtitle.setText("信息互动");
    }
    private void intiData() {
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(FinalStr.ACCESSPRYPATH +"/")
                .build();
        //生成接口文件
        serverApi = retrofit.create(ApiServiceSPAQ.class);

        appcache = (ApplicationUtil) getActivity().getApplication();
        appcache.addActivity(getActivity());
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
                startActivity(new Intent(mContext, ConsultationActivity.class));
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


    public void ordertabzx(View v){
        startActivity(new Intent(mContext, ConsultationActivity.class));
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
            XinXiFrament.ViewHolder holder = null;
            if (convertView == null) {
                holder = new XinXiFrament.ViewHolder();
                convertView = View.inflate(mContext, R.layout.information_item, null);
                convertView.setTag(holder);
                holder.tyle = (TextView) convertView.findViewById(R.id.informatin_tyle_item);
                holder.time = (TextView) convertView.findViewById(R.id.informatin_time_item);
                holder.quetion = (TextView) convertView.findViewById(R.id.informatin_quetion_item);
                holder.answere = (TextView) convertView.findViewById(R.id.informatin_answer_item);
            } else {
                holder = (XinXiFrament.ViewHolder) convertView.getTag();
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
    public void onResume() {
        super.onResume();
        intiData();
    }
}
