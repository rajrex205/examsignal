package com.springapp.mvc.model;

import com.springapp.orm.hibernate.model.Question;

public class ExamSubjectView {
    private long subjectId;
    private long courseID;
    private  String subjectName;
    private String byDefaultActiveCode;


    private Question firstQuestion;

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getByDefaultActiveCode() {
        return byDefaultActiveCode;
    }

    public void setByDefaultActiveCode(String byDefaultActiveCode) {
        this.byDefaultActiveCode = byDefaultActiveCode;
    }

    public Question getFirstQuestion() {
        return firstQuestion;
    }

    public void setFirstQuestion(Question firstQuestion) {
        this.firstQuestion = firstQuestion;
    }
}
