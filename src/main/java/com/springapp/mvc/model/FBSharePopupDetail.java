package com.springapp.mvc.model;

public class FBSharePopupDetail {
    private Long userId;
    private String userName;
    private String courseName;
    private String examName;
    private String marksSecured;
    private String totalMarks;
    private String totalPoints;
    private String percentage;
    private String percentageDescription;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getMarksSecured() {
        return marksSecured;
    }

    public void setMarksSecured(String marksSecured) {
        this.marksSecured = marksSecured;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPercentageDescription() {
        return percentageDescription;
    }

    public void setPercentageDescription(String percentageDescription) {
        this.percentageDescription = percentageDescription;
    }
}
