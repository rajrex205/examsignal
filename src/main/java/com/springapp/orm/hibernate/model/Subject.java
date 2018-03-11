package com.springapp.orm.hibernate.model;

public class Subject {
    private long id;
    private long courseID;
    private  String subjectName;
    private String byDefaultActiveCode;

    public String getByDefaultActiveCode() {
        return byDefaultActiveCode;
    }

    public void setByDefaultActiveCode(String byDefaultActiveCode) {
        this.byDefaultActiveCode = byDefaultActiveCode;
    }

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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
