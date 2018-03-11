package com.springapp.mvc.model;

import java.util.Map;

public class ExamDetailsPopup {
    private String courseName;
    private String examName;
    private Long examId;
    private Map<String,Long> subjectAndMarks;
    private Long duration;
    private Long totalMarks;
    private String allSubjects;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Map<String, Long> getSubjectAndMarks() {
        return subjectAndMarks;
    }

    public void setSubjectAndMarks(Map<String, Long> subjectAndMarks) {
        this.subjectAndMarks = subjectAndMarks;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Long totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getAllSubjects() {
        return allSubjects;
    }

    public void setAllSubjects(String allSubjects) {
        this.allSubjects = allSubjects;
    }
}