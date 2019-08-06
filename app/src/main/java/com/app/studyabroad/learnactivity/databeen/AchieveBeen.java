package com.app.studyabroad.learnactivity.databeen;

import java.util.List;

public class AchieveBeen {


    private List<ScoreslistBean> scoreslist;

    public List<ScoreslistBean> getScoreslist() {
        return scoreslist;
    }

    public void setScoreslist(List<ScoreslistBean> scoreslist) {
        this.scoreslist = scoreslist;
    }

    public static class ScoreslistBean {
        /**
         * courselist : [{"coursescore":[{"scores":"77","examType":"单元测试"},{"scores":"80",
         * "examType":"月考"}],"courseName":"大学语文"},{"coursescore":[{"scores":"92",
         * "examType":"单元测试"}],"courseName":"高等数学"},{"coursescore":[{"scores":"95",
         * "examType":"期中考试"},{"scores":"100","examType":"期末考试"}],"courseName":"雅思写作"}]
         * term : 1
         */

        private String term;
        private List<CourselistBean> courselist;

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public List<CourselistBean> getCourselist() {
            return courselist;
        }

        public void setCourselist(List<CourselistBean> courselist) {
            this.courselist = courselist;
        }

        public static class CourselistBean {
            /**
             * coursescore : [{"scores":"77","examType":"单元测试"},{"scores":"80","examType":"月考"}]
             * courseName : 大学语文
             */

            private String courseName;
            private List<CoursescoreBean> coursescore;

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public List<CoursescoreBean> getCoursescore() {
                return coursescore;
            }

            public void setCoursescore(List<CoursescoreBean> coursescore) {
                this.coursescore = coursescore;
            }

            public static class CoursescoreBean {
                /**
                 * scores : 77
                 * examType : 单元测试
                 */

                private String scores;
                private String examType;

                public String getScores() {
                    return scores;
                }

                public void setScores(String scores) {
                    this.scores = scores;
                }

                public String getExamType() {
                    return examType;
                }

                public void setExamType(String examType) {
                    this.examType = examType;
                }
            }
        }
    }
}
