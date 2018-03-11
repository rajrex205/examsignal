package com.springapp.orm.hibernate.model;

public class Exam {
    private long id;
    private long courseID;
    private String examName;
    private String examType;
    private int duration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
