package com.app.studyabroad.learnactivity.databeen;

import java.util.List;

public class CheckBeen {


    /**
     * allList : [{"time":"2019年01月","late":1,"truancy":1,"alls":10,"leveal":2,
     * "classRate":"70%"},{"time":"2019年02月","late":2,"truancy":0,"alls":10,"leveal":0,
     * "classRate":"80%"},{"time":"2019年03月","late":0,"truancy":0,"alls":10,"leveal":0,
     * "classRate":"100%"},{"time":"2019年04月","late":3,"truancy":2,"alls":10,"leveal":2,
     * "classRate":"60%"},{"time":"2019年05月","late":6,"truancy":1,"alls":10,"leveal":1,
     * "classRate":"90%"},{"time":"2018年12月","late":1,"truancy":1,"alls":10,"leveal":1,
     * "classRate":"90"}]
     * allRate : 82%
     * halfRate : 82%
     * threeRate : 80%
     * halfYearList : [{"time":"2019年01月","late":1,"truancy":1,"alls":10,"leveal":2,
     * "classRate":"70%"},{"time":"2019年02月","late":2,"truancy":0,"alls":10,"leveal":0,
     * "classRate":"80%"},{"time":"2019年03月","late":0,"truancy":0,"alls":10,"leveal":0,
     * "classRate":"100%"},{"time":"2019年04月","late":3,"truancy":2,"alls":10,"leveal":2,
     * "classRate":"60%"},{"time":"2019年05月","late":6,"truancy":1,"alls":10,"leveal":1,
     * "classRate":"90%"},{"time":"2018年12月","late":1,"truancy":1,"alls":10,"leveal":1,
     * "classRate":"90"}]
     * threeMonthList : [{"time":"2019年03月","late":0,"truancy":0,"alls":10,"leveal":0,
     * "classRate":"100%"},{"time":"2019年04月","late":3,"truancy":2,"alls":10,"leveal":2,
     * "classRate":"60%"},{"time":"2019年05月","late":6,"truancy":1,"alls":10,"leveal":1,
     * "classRate":"90%"}]
     */

    private String allRate;
    private String halfRate;
    private String threeRate;
    private List<AllListBean> allList;
    private List<HalfYearListBean> halfYearList;
    private List<ThreeMonthListBean> threeMonthList;

    public String getAllRate() {
        return allRate;
    }

    public void setAllRate(String allRate) {
        this.allRate = allRate;
    }

    public String getHalfRate() {
        return halfRate;
    }

    public void setHalfRate(String halfRate) {
        this.halfRate = halfRate;
    }

    public String getThreeRate() {
        return threeRate;
    }

    public void setThreeRate(String threeRate) {
        this.threeRate = threeRate;
    }

    public List<AllListBean> getAllList() {
        return allList;
    }

    public void setAllList(List<AllListBean> allList) {
        this.allList = allList;
    }

    public List<HalfYearListBean> getHalfYearList() {
        return halfYearList;
    }

    public void setHalfYearList(List<HalfYearListBean> halfYearList) {
        this.halfYearList = halfYearList;
    }

    public List<ThreeMonthListBean> getThreeMonthList() {
        return threeMonthList;
    }

    public void setThreeMonthList(List<ThreeMonthListBean> threeMonthList) {
        this.threeMonthList = threeMonthList;
    }

    public static class AllListBean {
        /**
         * time : 2019年01月
         * late : 1
         * truancy : 1
         * alls : 10
         * leveal : 2
         * classRate : 70%
         */

        private String time;
        private int late;
        private int truancy;
        private int alls;
        private int leveal;
        private String classRate;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getLate() {
            return late;
        }

        public void setLate(int late) {
            this.late = late;
        }

        public int getTruancy() {
            return truancy;
        }

        public void setTruancy(int truancy) {
            this.truancy = truancy;
        }

        public int getAlls() {
            return alls;
        }

        public void setAlls(int alls) {
            this.alls = alls;
        }

        public int getLeveal() {
            return leveal;
        }

        public void setLeveal(int leveal) {
            this.leveal = leveal;
        }

        public String getClassRate() {
            return classRate;
        }

        public void setClassRate(String classRate) {
            this.classRate = classRate;
        }
    }

    public static class HalfYearListBean {
        /**
         * time : 2019年01月
         * late : 1
         * truancy : 1
         * alls : 10
         * leveal : 2
         * classRate : 70%
         */

        private String time;
        private int late;
        private int truancy;
        private int alls;
        private int leveal;
        private String classRate;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getLate() {
            return late;
        }

        public void setLate(int late) {
            this.late = late;
        }

        public int getTruancy() {
            return truancy;
        }

        public void setTruancy(int truancy) {
            this.truancy = truancy;
        }

        public int getAlls() {
            return alls;
        }

        public void setAlls(int alls) {
            this.alls = alls;
        }

        public int getLeveal() {
            return leveal;
        }

        public void setLeveal(int leveal) {
            this.leveal = leveal;
        }

        public String getClassRate() {
            return classRate;
        }

        public void setClassRate(String classRate) {
            this.classRate = classRate;
        }
    }

    public static class ThreeMonthListBean {
        /**
         * time : 2019年03月
         * late : 0
         * truancy : 0
         * alls : 10
         * leveal : 0
         * classRate : 100%
         */

        private String time;
        private int late;
        private int truancy;
        private int alls;
        private int leveal;
        private String classRate;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getLate() {
            return late;
        }

        public void setLate(int late) {
            this.late = late;
        }

        public int getTruancy() {
            return truancy;
        }

        public void setTruancy(int truancy) {
            this.truancy = truancy;
        }

        public int getAlls() {
            return alls;
        }

        public void setAlls(int alls) {
            this.alls = alls;
        }

        public int getLeveal() {
            return leveal;
        }

        public void setLeveal(int leveal) {
            this.leveal = leveal;
        }

        public String getClassRate() {
            return classRate;
        }

        public void setClassRate(String classRate) {
            this.classRate = classRate;
        }
    }
}
