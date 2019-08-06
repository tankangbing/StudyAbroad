package com.app.studyabroad.learnactivity.databeen;

import java.util.List;

public class MyTeacherBeen {


    private List<TeacherListBeanX> teacherList;

    public List<TeacherListBeanX> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherListBeanX> teacherList) {
        this.teacherList = teacherList;
    }

    public static class TeacherListBeanX {
        /**
         * teacherList : [{"phone":"7820569","tname":"王老师"},{"phone":"1857799620","tname":"刘老师"}]
         * className : 出国留学2班++
         */

        private String className;
        private List<TeacherListBean> teacherList;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public List<TeacherListBean> getTeacherList() {
            return teacherList;
        }

        public void setTeacherList(List<TeacherListBean> teacherList) {
            this.teacherList = teacherList;
        }

        public static class TeacherListBean {
            /**
             * phone : 7820569
             * tname : 王老师
             */

            private String phone;
            private String tname;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getTname() {
                return tname;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }
        }
    }
}
