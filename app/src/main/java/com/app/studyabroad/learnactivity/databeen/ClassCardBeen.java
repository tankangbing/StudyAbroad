package com.app.studyabroad.learnactivity.databeen;

import java.util.List;

public class ClassCardBeen {


    /**
     * status : 300
     * courseList : [{"zs":"第一周","scheduleList":[{"elesson":4,"times":"10:00-11:40","slesson":2,
     * "day":1,"place":"A栋101","teacherName":"刘老师","courseName":"大学语文"},{"elesson":5,
     * "times":"10:00-11:40","slesson":5,"day":1,"place":"A栋403","teacherName":"王老师",
     * "courseName":"高等数学"},{"elesson":1,"times":"10:00-11:40","slesson":1,"day":2,
     * "place":"A栋707","teacherName":"李老师","courseName":"音乐"}]},{"zs":"第二周",
     * "scheduleList":[{"elesson":2,"times":"10:00-11:40","slesson":2,"day":1,"place":"101",
     * "teacherName":"刘老师","courseName":"地理"},{"elesson":6,"times":"10:00-11:40","slesson":6,
     * "day":3,"place":"B栋209","teacherName":"吴老师","courseName":"政治"}]},{"zs":"第三周",
     * "scheduleList":[{"elesson":2,"times":"10:00-11:40","slesson":1,"day":4,"place":"C栋1楼",
     * "teacherName":"小明老师","courseName":"大学物理"},{"elesson":3,"times":"10:00-11:40","slesson":1,
     * "day":5,"place":"实验室","teacherName":"小易老师","courseName":"物理实验"},{"elesson":4,
     * "times":"10:00-11:40","slesson":3,"day":6,"place":"A操场","teacherName":"体育老师",
     * "courseName":"大学体育"}]}]
     * msg : 该班级该学期未导入课表
     */

    private String status;
    private String msg;
    private List<CourseListBean> courseList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CourseListBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseListBean> courseList) {
        this.courseList = courseList;
    }

    public static class CourseListBean {
        /**
         * zs : 第一周
         * scheduleList : [{"elesson":4,"times":"10:00-11:40","slesson":2,"day":1,
         * "place":"A栋101","teacherName":"刘老师","courseName":"大学语文"},{"elesson":5,
         * "times":"10:00-11:40","slesson":5,"day":1,"place":"A栋403","teacherName":"王老师",
         * "courseName":"高等数学"},{"elesson":1,"times":"10:00-11:40","slesson":1,"day":2,
         * "place":"A栋707","teacherName":"李老师","courseName":"音乐"}]
         */

        private String zs;
        private List<ScheduleListBean> scheduleList;

        public String getZs() {
            return zs;
        }

        public void setZs(String zs) {
            this.zs = zs;
        }

        public List<ScheduleListBean> getScheduleList() {
            return scheduleList;
        }

        public void setScheduleList(List<ScheduleListBean> scheduleList) {
            this.scheduleList = scheduleList;
        }

        public static class ScheduleListBean {
            /**
             * elesson : 4
             * times : 10:00-11:40
             * slesson : 2
             * day : 1
             * place : A栋101
             * teacherName : 刘老师
             * courseName : 大学语文
             */

            private int elesson;
            private String times;
            private int slesson;
            private int day;
            private String place;
            private String teacherName;
            private String courseName;

            public int getElesson() {
                return elesson;
            }

            public void setElesson(int elesson) {
                this.elesson = elesson;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

            public int getSlesson() {
                return slesson;
            }

            public void setSlesson(int slesson) {
                this.slesson = slesson;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getTeacherName() {
                return teacherName;
            }

            public void setTeacherName(String teacherName) {
                this.teacherName = teacherName;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }
        }
    }
}
