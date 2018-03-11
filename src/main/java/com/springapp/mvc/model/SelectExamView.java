package com.springapp.mvc.model;

public class SelectExamView {
    private long courseId;
    private String courseName;
    private long examId;
    private String examName;
    private String typeOfExam; //ExamType
    private String isExamAttempted; // "Y" or  "N"

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

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getTypeOfExam() {
        return typeOfExam;
    }

    public void setTypeOfExam(String typeOfExam) {
        this.typeOfExam = typeOfExam;
    }

    public String getIsExamAttempted() {
        return isExamAttempted;
    }

    public void setIsExamAttempted(String isExamAttempted) {
        this.isExamAttempted = isExamAttempted;
    }
}
