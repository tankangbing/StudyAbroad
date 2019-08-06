package com.app.studyabroad.learnactivity.databeen;

import java.util.List;

public class InformationBeen {

    private List<ConsultListBean> consultList;

    public List<ConsultListBean> getConsultList() {
        return consultList;
    }

    public void setConsultList(List<ConsultListBean> consultList) {
        this.consultList = consultList;
    }

    public static class ConsultListBean {
        /**
         * replyList : [{"content":"期末考试大概在每个学期结课的最后一个礼拜","teacherName":"Mr.Liu"}]
         * consult : {"time":"2019-05-29","consultType":"考试","issue":"期末考试是在什么时候呀"}
         */

        private ConsultBean consult;
        private List<ReplyListBean> replyList;

        public ConsultBean getConsult() {
            return consult;
        }

        public void setConsult(ConsultBean consult) {
            this.consult = consult;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class ConsultBean {
            /**
             * time : 2019-05-29
             * consultType : 考试
             * issue : 期末考试是在什么时候呀
             */

            private String time;
            private String consultType;
            private String issue;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getConsultType() {
                return consultType;
            }

            public void setConsultType(String consultType) {
                this.consultType = consultType;
            }

            public String getIssue() {
                return issue;
            }

            public void setIssue(String issue) {
                this.issue = issue;
            }
        }

        public static class ReplyListBean {
            /**
             * content : 期末考试大概在每个学期结课的最后一个礼拜
             * teacherName : Mr.Liu
             */

            private String content;
            private String teacherName;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTeacherName() {
                return teacherName;
            }

            public void setTeacherName(String teacherName) {
                this.teacherName = teacherName;
            }
        }
    }
}
