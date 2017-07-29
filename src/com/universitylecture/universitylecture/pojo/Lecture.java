package com.universitylecture.universitylecture.pojo;

import java.io.Serializable;

public class Lecture implements Serializable{
	
	private static final long serialVersionUID = 1L;

    private String ID;
    private String title;        //标题
    private String time;         //时间
    private String classroom;    //地点
    private String institute;    //学院
    private String introduction; //简介
    private String lecturer;     //主讲人
    private String credit;       //分值
    private String content;      //内容
    private String sponsor;      //讲座主办方
    private String co_sponsor;   //讲座协办方
    private String imagePath;

    public Lecture(String ptitle, String ptime , String pclassroom , String pinstitute , String pintroduction ,
                    String plecturer , String pcredit , String pcontent ,String psponsor,
                    String pco_sponsor,String path){

        title = ptitle;
        time = ptime;
        classroom = pclassroom;
        institute = pinstitute;
        introduction = pintroduction;
        lecturer = plecturer;
        credit = pcredit;
        content = pcontent;
        sponsor = psponsor;
        co_sponsor = pco_sponsor;
        imagePath = path;
    }


    public Lecture(String ptime,String pinstitute) {
        time = ptime;
        institute = pinstitute;
    }

    public Lecture() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getCo_sponsor() {
        return co_sponsor;
    }

    public void setCo_sponsor(String co_sponsor) {
        this.co_sponsor = co_sponsor;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
