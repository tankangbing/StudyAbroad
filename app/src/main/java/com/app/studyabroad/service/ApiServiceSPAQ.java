package com.app.studyabroad.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 食品安全接口
 */

public interface ApiServiceSPAQ {




    @FormUrlEncoded
    @POST("appControler.do?courseClassList")
    Call<ResponseBody>courseClassList(@Field("classType") String classType,
                                      @Field("page") String page,
                                      @Field("accountId") String accountId);

    @POST("appControler.do?getStartPagePicturePath")
    Call<ResponseBody>getStartPagePicturePath();

    /*
    * 验证信息
    * */
    @FormUrlEncoded
    @POST("edu3/app/ischange/pwd.html")
    Call<ResponseBody>yanzheng(@Field("username") String username,
                                      @Field("phone") String phone,
                                      @Field("idCard") String idCard);
    /*
     * 修改密码
     * */
    @FormUrlEncoded
    @POST("edu3/app/change/pwd.html")
    Call<ResponseBody>reset(@Field("id") String id,
                               @Field("psw") String psw,
                               @Field("mobileKey") String  mobileKey);
    /*
     * 课程表
     * */
    @FormUrlEncoded
    @POST("edu3/app/course/curriculum-list.html")
    Call<ResponseBody>getClassCard(@Field("stuid") String stuid);
    /*
     * 我的成绩
     * */
    @FormUrlEncoded
    @POST("edu3/app/student/scores.html")
    Call<ResponseBody>getAchieve(@Field("stuid") String stuid);
    /*
     * 我的老师
     * */
    @FormUrlEncoded
    @POST("edu3/app/student/steacher.html")
    Call<ResponseBody>getMyteacher(@Field("stuid") String stuid);
    /*
     * 信息互动
     * */
    @FormUrlEncoded
    @POST("edu3/app/student/consult.html")
    Call<ResponseBody>getInformation(@Field("stuid") String stuid);
    /*
     * 我要咨询老师和类型
     * */
    @FormUrlEncoded
    @POST("edu3/app/student/consultinfo.html")
    Call<ResponseBody>getTeacher(@Field("stuid") String stuid);
    /*
     * 提交我要咨询
     * */
    @FormUrlEncoded
    @POST("edu3/app/student/createconsult.html")
    Call<ResponseBody>getConsulation(@Field("stuid") String stuid,
                                    @Field("consultType") String consultType,
                                     @Field("issue") String issue ,
                                     @Field("tid") String tid);
    /*
     * 我的考勤
     * */
    @FormUrlEncoded
    @POST("edu3/app/student/attendance.html")
    Call<ResponseBody>getCheck(@Field("stuid") String stuid);
    /*
     * 我的信息
     * */
    @FormUrlEncoded
    @POST("edu3/app/student/infos.html")
    Call<ResponseBody>getInfos(@Field("stuid") String stuid);
}

