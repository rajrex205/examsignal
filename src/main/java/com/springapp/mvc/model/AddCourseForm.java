package com.springapp.mvc.model;

import java.util.List;

public class AddCourseForm {
    private String courseName;
    private String subjectNames;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubjectNames() {
        return subjectNames;
    }

    public void setSubjectNames(String subjectNames) {
        this.subjectNames = subjectNames;
    }
}
