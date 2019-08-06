package com.app.studyabroad.entity;

import com.app.studyabroad.util.CircleImageView;

/**
 * Created by DELL on 2018/1/19.
 */

public class QuestionNode {
    private String answer;
    private int num;
    private int status;
    private String stuanswer;
    private String title;
    private int topic;
    private String aceid;
    private String tempAnswer;
    private CircleImageView btn;
    private int optionNum;

    public CircleImageView getBtn() {
        return btn;
    }

    public void setBtn(CircleImageView btn) {
        this.btn = btn;
    }

    public String getTempAnswer() {
        return tempAnswer == null? "": tempAnswer;
    }

    public void setTempAnswer(String tempAnswer) {
        this.tempAnswer = tempAnswer;
    }

    public String getAceid() {
        return aceid;
    }

    public void setAceid(String aceid) {
        this.aceid = aceid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStuanswer() {
        return stuanswer == null? "": stuanswer;
    }

    public void setStuanswer(String stuanswer) {
        this.stuanswer = stuanswer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public int getOptionNum() {
        return optionNum;
    }

    public void setOptionNum(int optionNum) {
        this.optionNum = optionNum;
    }
}
