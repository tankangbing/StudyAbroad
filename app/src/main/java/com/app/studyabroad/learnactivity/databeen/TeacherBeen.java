package com.app.studyabroad.learnactivity.databeen;

import java.util.List;

public class TeacherBeen {


    private List<ConsultTypeListBean> consultTypeList;
    private List<TeacherListBean> teacherList;

    public List<ConsultTypeListBean> getConsultTypeList() {
        return consultTypeList;
    }

    public void setConsultTypeList(List<ConsultTypeListBean> consultTypeList) {
        this.consultTypeList = consultTypeList;
    }

    public List<TeacherListBean> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherListBean> teacherList) {
        this.teacherList = teacherList;
    }

    public static class ConsultTypeListBean {
        /**
         * name : exam
         * value : 考试
         */

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class TeacherListBean {
        /**
         * tname : 刘老师
         * tid : 402848816b011ae3016b017437320001
         */

        private String tname;
        private String tid;

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}
