package com.app.studyabroad.learnactivity.databeen;

import java.util.List;

public class UserBeen {


    /**
     * userxm : 李易峰
     * username : 1000011
     * stuid : 402848816bd98ff0016bda0238e50030
     * status : 200
     * userid : 402848816bd98ff0016bda0238a2002f
     * usertype : parents
     * code : 1
     * msg : 验证通过正在跳
     转主页请稍等...
     * studentList : [{"id":"402848816bd98ff0016bda0239740032","stuNo":"08090514121","name":"李兰迪","gender":"女"}]
     */

    private String userxm;
    private String username;
    private String stuid;
    private String status;
    private String userid;
    private String usertype;
    private String code;
    private String msg;
    private List<StudentListBean> studentList;

    public String getUserxm() {
        return userxm;
    }

    public void setUserxm(String userxm) {
        this.userxm = userxm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<StudentListBean> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentListBean> studentList) {
        this.studentList = studentList;
    }

    public static class StudentListBean {
        /**
         * id : 402848816bd98ff0016bda0239740032
         * stuNo : 08090514121
         * name : 李兰迪
         * gender : 女
         */

        private String id;
        private String stuNo;
        private String name;
        private String gender;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStuNo() {
            return stuNo;
        }

        public void setStuNo(String stuNo) {
            this.stuNo = stuNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
