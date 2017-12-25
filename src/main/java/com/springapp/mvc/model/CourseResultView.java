package com.springapp.mvc.model;

import java.util.List;

public class CourseResultView {
    private long courseId;
    private String courseName;
    private String activeAttribute;
    private List<ExamResultView> examResultViews;

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getActiveAttribute() {
        return activeAttribute;
    }

    public void setActiveAttribute(String activeAttribute) {
        this.activeAttribute = activeAttribute;
    }

    public List<ExamResultView> getExamResultViews() {
        return examResultViews;
    }

    public void setExamResultViews(List<ExamResultView> examResultViews) {
        this.examResultViews = examResultViews;
    }
}
