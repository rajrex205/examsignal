package com.springapp.mvc.model;

import java.util.Date;

public class ExamResultView {
    private Date examDate;
    private String examName;
    private long courseId;
    private String courseName;
    private String maxMarks;
    private String totalMarks;

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }
}
